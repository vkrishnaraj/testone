/*
 * Created on Sep 14, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.match;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.cronjob.tracing.OHDTraceThread;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.AirlineMembership;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Item_Inventory;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.Match_Detail;
import com.bagnet.nettracer.tracing.db.OHD_Address;
import com.bagnet.nettracer.tracing.db.OHD_Itinerary;
import com.bagnet.nettracer.tracing.db.OHD_Passenger;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.utils.StringUtils;

/**
 * @@author Administrator
 * 
 * create date - Sep 14, 2004
 */
public class Matcher {
	private static Logger logger = Logger.getLogger(Matcher.class);

	// match details list
	private ArrayList details = new ArrayList();

	// minimum percent to write to database
	private int MIN_PERCENT = 1;
	

	/** ********** weightings ***************** */

	// primary item weights
	private final static double CLAIMCHECKNUM_WEIGHT = 1.5;
	private final static double RECORDLOC_WEIGHT = 1.5;
	private final static double MEMBER_WEIGHT = 1.5;
	private final static double EMAIL_WEIGHT = 1.5;

	// other weightings
	private final static double ADDR_WEIGHT = 0.15;
	private final static double NAME_WEIGHT = 0.15;

	private final static double PHONE_WEIGHT = 0.15;

	private final static double ITINERARY_WEIGHT = 0.1;

	private final static double MANUFACTURER_WEIGHT = 0.1;

	private final static double COLOR_WEIGHT = 0.1;
	private final static double COLOR_2_WEIGHT = 0.08; // secondary color match
	private final static double COLOR_3_WEIGHT = 0.05; // tertiary color match

	private final static double BAGTYPE_WEIGHT = 0.1;
	private final static double BAGTYPE_2_WEIGHT = 0.08; // secondary color match
	private final static double BAGTYPE_3_WEIGHT = 0.05; // tertiary color match

	private final static double XDESC_WEIGHT = 0.1;

	private final static double LVL1_CONTENTS_WEIGHT = 0.3; // content weight with
	// pk matched
	private final static double LVL2_CONTENTS_WEIGHT = 0.5; // contents with pk
	// not matched

	/** ******* percentages ******** */

	// category 1 primary items minimum percentage (if one of the primary match
	// 100%)
	private final static double CAT_ONE_PK_MIN_PERCENT = 80;

	// category 2 min and max
	private final static double CAT_TWO_MIN = 20;
	private final static double CAT_TWO_MAX = 80;
	private final static double CAT_TWO_NO_PK_MAX = 60;

	// can't do pattern matching, so use 10 percent if it is wrong
	private final static double MANU_WRONG = 10; // wrong percent
	private final static double COLOR_WRONG = 10; // wrong percent
	private final static double BAGTYPE_WRONG = 10; // wrong percent
	private final static double XDESC_WRONG = 10; // wrong percent

	// xdesc = x
	private final static int XDESC_NONE = 7;

	private OHDTraceThread ohd_thread;
	private String OHD_ID;
	private String cat_1_claim_match;

	public Matcher(OHDTraceThread ohd_thread, String OHD_ID) {
		this.ohd_thread = ohd_thread;
		this.OHD_ID = OHD_ID;
	}

	/**
	 * determine which category this match should go to.
	 *  
	 */
	public int determineCategory(IncidentElements ie, OHDElements oe, String claimchecknum) {

		String recordlocator = null;
		String membershipnum = null;
		String email = null;

		/** ****** claimcheck ********** */

		if (claimchecknum != null && claimchecknum.length() > 0 && oe.getClaimchecknum() != null && oe.getClaimchecknum().length() > 0) {
			if (claimchecknum.toUpperCase().equals(oe.getClaimchecknum().toUpperCase()))
				return 1;
		}

		/** ********* match record locator ************ */
		recordlocator = ie.getRecordlocator();
		if (recordlocator != null && recordlocator.length() > 0 && oe.getRecordlocator() != null && oe.getRecordlocator().length() > 0) {
			if (recordlocator.toUpperCase().trim().equals(oe.getRecordlocator().toUpperCase().trim())) {
				return 1;
			}
		}

		/** *********** match membershipnum ************ */
		AirlineMembership mem = null;
		String com1 = null;
		String num1 = null;
		for (int i = 0; i < ie.getMemberships().size(); i++) {
			mem = (AirlineMembership) ie.getMemberships().get(i);
			com1 = mem.getCompanycode_ID().toUpperCase().trim();
			num1 = mem.getMembershipnum().toUpperCase().trim();
			if (com1.length() > 0 && mem.getMembershipnum().length() > 0) {
				if (com1.equals(oe.getMembership_company().toUpperCase().trim()) && num1.equals(oe.getMembershipnum().toUpperCase().trim()))
					return 1;
			}
		}

		/** ******** emails********** */

		OHD_Address ohd_a = null;
		Address addr = null;
		boolean mbr_email_entered = false;
		boolean email_matched = false;
		String mbr_info = "", ohd_info = "";

		for (int i = 0; i < oe.getAddresses().size(); i++) {

			ohd_a = (OHD_Address) oe.getAddresses().get(i);

			// loop through mbr addresses and check one at a time
			for (int j = 0; j < ie.getAddresses().size(); j++) {
				addr = (Address) ie.getAddresses().get(j);

				if (!email_matched) {
					if (addr.getEmail().trim().length() > 0 && ohd_a.getEmail().trim().length() > 0) {
						if (ohd_a.getEmail().trim().equalsIgnoreCase(addr.getEmail().trim())) {
							return 1;
						}
					}
				}
			}
		}

		return 2;
	}

	/**
	 * Main matching
	 * 
	 * @@param ie
	 * @@param oe
	 * @@return
	 */

	public boolean doMatching(IncidentElements ie, OHDElements oe, int MIN_PERCENT) {
		this.MIN_PERCENT = MIN_PERCENT;
		
		double percent = 0;
		double nowpercent = 0;
		double lastpercent = 0;
		double total_cat_used = 0;

		// cat1 matches
		String claimchecknum = null;

		boolean matched = false;
		boolean entered = false; // both mbr and ohd item is keyed in, so start
		// matching on that.

		String tempohdname = "";
		String temponbagname = "";

		ArrayList claim_match_al = new ArrayList();
		ArrayList bag_match_al = new ArrayList();

		/** ****** match claimcheck ********** */

		Incident_Claimcheck claimcheck = null;
		Match_Detail md = null;
		double temppercent = 0;

		int catlevel = 0;
		double[] result = new double[2];

		//boolean oneclaimcheck = ie.getClaimchecks().size() > 1 ? false : true;
		//boolean onebag = ie.getItems().size() > 1 ? false : true;
		boolean claimcheck_matched = false;

		for (int i = 0; i < ie.getClaimchecks().size(); i++) {

			// clear details
			details.clear();

			claimcheck = (Incident_Claimcheck) ie.getClaimchecks().get(i);
			claimchecknum = claimcheck.getClaimchecknum();

			// determine category level
			catlevel = determineCategory(ie, oe, claimchecknum);

			// calculate nonbag specific percentages
			if (catlevel == 1)
				result = nonBagSpecificMatching(ie, oe, CAT_ONE_PK_MIN_PERCENT);
			else
				result = nonBagSpecificMatching(ie, oe, MIN_PERCENT);

			percent = result[0];
			total_cat_used = result[1];

			// get claimcheck percentage
			nowpercent = StringCompare.compareStrings(claimchecknum, oe.getClaimchecknum());

			if (nowpercent == 100) {
				// if (nowpercent > 0) {	// for partial claimcheck match
				
				// clone the current list first
				claim_match_al = (ArrayList) details.clone();

				// figure out percentage
				double truepercent = nowpercent;

				// if category 1, and percent is less than 80%, then use 80% as the
				// percent to calculate total
				if (catlevel == 1 && nowpercent < CAT_ONE_PK_MIN_PERCENT) {
					truepercent = CAT_ONE_PK_MIN_PERCENT;
				}

				/*
				 * if (ie.getClaimchecks().size() == 1 && ie.getItems().size() == 1) {
				 * 
				 * addToDetail(TracingConstants.MATCH_CLAIMCHECK, nowpercent,
				 * claimchecknum, oe .getClaimchecknum(), 0); total_cat_used +=
				 * CLAIMCHECKNUM_WEIGHT; percent += truepercent * CLAIMCHECKNUM_WEIGHT; }
				 * else {
				 */

				// add to temporary match detail for claim check only
				addToTempDetail(claim_match_al, TracingConstants.MATCH_CLAIMCHECK, nowpercent, claimchecknum, oe.getClaimchecknum(), 0);
				temppercent = (percent + truepercent * CLAIMCHECKNUM_WEIGHT) / (total_cat_used + CLAIMCHECKNUM_WEIGHT);

				// insert into match history with claimcheck match + nonbagspecific
				// match
				if (catlevel == 2) {
					// add minimum and cap at max
					if (temppercent > CAT_TWO_MAX)
						temppercent *= (CAT_TWO_MAX / 100);
					if (temppercent < CAT_TWO_MIN)
						temppercent += CAT_TWO_MIN;
				}

				if (temppercent > MIN_PERCENT) {
					// insert into history
					try {

						ohd_thread.insertMatchHistory(OHD_ID, catlevel, temppercent, claim_match_al, claimchecknum, -1);
						claimcheck_matched = true;
						logger.info("matching claim check + " + i + " result: " + temppercent);
					} catch (Exception e) {
						logger.error("unable to match claimcheck: " + e);
					}
					claim_match_al = null;
				}

			}

		}
		

		/** ********** match bag information ************* */
		double xnowp1 = 0, xlastp1 = 0, xnowp2 = 0, xlastp2 = 0, xnowp3 = 0, xlastp3 = 0; // xdescelements

		boolean colorentered = false;
		boolean typeentered = false;

		double temptotal_cat = 0;
		tempohdname = "";
		temponbagname = "";
		StringBuffer mbr_xdesc = new StringBuffer();
		StringBuffer ohd_xdesc = new StringBuffer();

		String iecontents = null;
		StringBuffer oecontents = new StringBuffer();
		StringTokenizer st = null;

		// if not one claimcheck, then find level without claimcheck and then
		// percentage
		//if (!oneclaimcheck || !onebag) {
		// determine category level
		catlevel = determineCategory(ie, oe, null);

		// clean details vector
		details.clear();

		// calculate nonbag specific percentages
		if (catlevel == 1)
			result = nonBagSpecificMatching(ie, oe, CAT_ONE_PK_MIN_PERCENT);
		else
			result = nonBagSpecificMatching(ie, oe, MIN_PERCENT);

		percent = result[0];
		total_cat_used = result[1];
		//}

		Item item = null;
		OHD_Passenger ohd_p = null;
		boolean bagitems_matched = false;
			

		for (int i = 0; i < ie.getItems().size(); i++) {
			// reset temppercent and temptotalcat to percent and cat with other nonbag
			// specific items
			temppercent = percent;
			temptotal_cat = total_cat_used;
			lastpercent = 0;

			// get the detail list of nonbag specific items
			bag_match_al = (ArrayList) details.clone();

			item = (Item) ie.getItems().get(i);
			

			// names on bag with ohd names
			for (int j = 0; j < oe.getPassengers().size(); j++) {

				ohd_p = (OHD_Passenger) oe.getPassengers().get(j);
				// compare to name on bag
				if (ohd_p.getFirstname().trim().length() > 0 && ohd_p.getLastname().trim().length() > 0) {
					nowpercent = StringCompare.compareStrings(item.getFnameonbag() + " " + item.getMnameonbag() + " " + item.getLnameonbag(), ohd_p
							.getFirstname()
							+ " " + ohd_p.getMiddlename() + " " + ohd_p.getLastname());
					if (nowpercent > lastpercent) {
						lastpercent = nowpercent;
						tempohdname = ohd_p.getLastname() + ", " + ohd_p.getFirstname() + " " + ohd_p.getMiddlename();
						temponbagname = item.getLnameonbag() + ", " + item.getFnameonbag() + " " + item.getMnameonbag();
					}
				}
			}
			if (lastpercent > 0) {
				addToTempDetail(bag_match_al, TracingConstants.MATCH_ONBAG_NAME, lastpercent, temponbagname, tempohdname, -1);
				temptotal_cat += NAME_WEIGHT;
				temppercent += lastpercent * NAME_WEIGHT;
				bagitems_matched = true;
			}

			
			//manufacturer
			if (oe.getManufacturer_ID() > 0 && item.getManufacturer_ID() > 0) {
				if (item.getManufacturer_ID() == oe.getManufacturer_ID())
					nowpercent = 100;
				else
					nowpercent = MANU_WRONG;

				// if manufacturer is other
				if (item.getManufacturer_ID() == TracingConstants.MANUFACTURER_OTHER_ID) {
					// make it wrong for now
					nowpercent = 0;
				}

				addToTempDetail(bag_match_al, TracingConstants.MATCH_MANUFACTURER, nowpercent, getManu(item.getManufacturer_ID()), getManu(oe
						.getManufacturer_ID()), -1);
				temptotal_cat += MANUFACTURER_WEIGHT;
				temppercent += nowpercent * MANUFACTURER_WEIGHT;
				bagitems_matched = true;
			}

			// color
			if (oe.getColor().trim().length() > 0 && item.getColor().trim().length() > 0) {
				colorentered = true;
				bagitems_matched = true;
				// primary percentage
				if (item.getColor().equals(oe.getColor())) {
					addToTempDetail(bag_match_al, TracingConstants.MATCH_COLOR, 100, item.getColor(), oe.getColor(), -1);
					temptotal_cat += COLOR_WEIGHT;
					temppercent += 100 * COLOR_WEIGHT;
				} else if ((nowpercent = secondaryColorMatch(item.getColor(), oe.getColor())) == 100) {
					// secondary
					addToTempDetail(bag_match_al, TracingConstants.MATCH_COLOR_2, 100, item.getColor(), oe.getColor(), -1);
					temptotal_cat += COLOR_2_WEIGHT;
					temppercent += 100 * COLOR_2_WEIGHT;
				} else if ((nowpercent = tertiaryColorMatch(item.getColor(), oe.getColor())) == 100) {
					// tertiary
					addToTempDetail(bag_match_al, TracingConstants.MATCH_COLOR_3, 100, item.getColor(), oe.getColor(), -1);
					temptotal_cat += COLOR_3_WEIGHT;
					temppercent += 100 * COLOR_3_WEIGHT;
				} else {
					// no match at all
					addToTempDetail(bag_match_al, TracingConstants.MATCH_COLOR, COLOR_WRONG, item.getColor(), oe.getColor(), -1);
					temptotal_cat += COLOR_WEIGHT;
					temppercent += COLOR_WRONG * COLOR_WEIGHT;

				}
			}


			// bag type
			if (oe.getBagtype().trim().length() > 0 && item.getBagtype().trim().length() > 0) {
				typeentered = true;
				bagitems_matched = true;
				// primary percentage
				if (item.getBagtype().equals(oe.getBagtype())) {
					addToTempDetail(bag_match_al, TracingConstants.MATCH_BAGTYPE, 100, getBagtype(item.getBagtype()), getBagtype(oe.getBagtype()), -1);
					temptotal_cat += BAGTYPE_WEIGHT;
					temppercent += 100 * BAGTYPE_WEIGHT;
				} else if ((nowpercent = secondaryTypeMatch(item.getBagtype(), oe.getBagtype())) == 100) {
					// secondary
					addToTempDetail(bag_match_al, TracingConstants.MATCH_BAGTYPE_2, 100, getBagtype(item.getBagtype()), getBagtype(oe.getBagtype()), -1);
					temptotal_cat += BAGTYPE_2_WEIGHT;
					temppercent += 100 * BAGTYPE_2_WEIGHT;
				} else if ((nowpercent = tertiaryTypeMatch(item.getBagtype(), oe.getBagtype())) == 100) {
					// tertiary
					addToTempDetail(bag_match_al, TracingConstants.MATCH_BAGTYPE_3, 100, getBagtype(item.getBagtype()), getBagtype(oe.getBagtype()), -1);
					temptotal_cat += BAGTYPE_3_WEIGHT;
					temppercent += 100 * BAGTYPE_3_WEIGHT;
				} else {
					// no match at all
					addToTempDetail(bag_match_al, TracingConstants.MATCH_BAGTYPE, BAGTYPE_WRONG, getBagtype(item.getBagtype()), getBagtype(oe.getBagtype()), -1);
					temptotal_cat += BAGTYPE_WEIGHT;
					temppercent += BAGTYPE_WRONG * BAGTYPE_WEIGHT;
				}
			}

			// average the three xdescelements
			if (oe.getXdesc1() > 0 && oe.getXdesc1() != XDESC_NONE && (colorentered || typeentered)) {
				ohd_xdesc.append(getXdesc(oe.getXdesc1()) + ", ");

				if (item.getXdescelement_ID_1() > 0 && item.getXdescelement_ID_1() != XDESC_NONE) {
					if (item.getXdescelement_ID_1() == oe.getXdesc1())
						xnowp1 = 100; // match
					else
						xnowp1 = XDESC_WRONG; // no match
					if (xnowp1 > xlastp1)
						xlastp1 = xnowp1;
				}
				if (item.getXdescelement_ID_2() > 0 && item.getXdescelement_ID_2() != XDESC_NONE) {
					if (item.getXdescelement_ID_2() == oe.getXdesc1())
						xnowp1 = 100;
					else
						xnowp1 = XDESC_WRONG;
					if (xnowp1 > xlastp1)
						xlastp1 = xnowp1;
				}
				if (item.getXdescelement_ID_3() > 0 && item.getXdescelement_ID_3() != XDESC_NONE) {
					if (item.getXdescelement_ID_3() == oe.getXdesc1())
						xnowp1 = 100;
					else
						xnowp1 = XDESC_WRONG;
					if (xnowp1 > xlastp1)
						xlastp1 = xnowp1;
				}
			}
			if (oe.getXdesc2() > 0 && oe.getXdesc2() != XDESC_NONE && (colorentered || typeentered)) {
				ohd_xdesc.append(getXdesc(oe.getXdesc2()) + ", ");
				if (item.getXdescelement_ID_1() > 0 && item.getXdescelement_ID_1() != XDESC_NONE) {
					if (item.getXdescelement_ID_1() == oe.getXdesc2())
						xnowp2 = 100;
					else
						xnowp2 = XDESC_WRONG;
					if (xnowp2 > xlastp2)
						xlastp2 = xnowp2;
				}
				if (item.getXdescelement_ID_2() > 0 && item.getXdescelement_ID_2() != XDESC_NONE) {
					if (item.getXdescelement_ID_2() == oe.getXdesc2())
						xnowp2 = 100;
					else
						xnowp2 = XDESC_WRONG;
					if (xnowp2 > xlastp2)
						xlastp2 = xnowp2;
				}
				if (item.getXdescelement_ID_3() > 0 && item.getXdescelement_ID_3() != XDESC_NONE) {
					if (item.getXdescelement_ID_3() == oe.getXdesc2())
						xnowp2 = 100;
					else
						xnowp2 = XDESC_WRONG;
					if (xnowp2 > xlastp2)
						xlastp2 = xnowp2;
				}
			}
			if (oe.getXdesc3() > 0 && oe.getXdesc3() != XDESC_NONE && (colorentered || typeentered)) {
				ohd_xdesc.append(getXdesc(oe.getXdesc3()) + ", ");
				if (item.getXdescelement_ID_1() > 0 && item.getXdescelement_ID_1() != XDESC_NONE) {
					if (item.getXdescelement_ID_1() == oe.getXdesc3())
						xnowp3 = 100;
					else
						xnowp3 = XDESC_WRONG;
					if (xnowp3 > xlastp3)
						xlastp3 = xnowp3;
				}
				if (item.getXdescelement_ID_2() > 0 && item.getXdescelement_ID_2() != XDESC_NONE) {
					if (item.getXdescelement_ID_2() == oe.getXdesc3())
						xnowp3 = 100;
					else
						xnowp3 = XDESC_WRONG;
					if (xnowp3 > xlastp3)
						xlastp3 = xnowp3;
				}
				if (item.getXdescelement_ID_3() > 0 && item.getXdescelement_ID_3() != XDESC_NONE) {
					if (item.getXdescelement_ID_3() == oe.getXdesc3())
						xnowp3 = 100;
					else
						xnowp3 = XDESC_WRONG;
					if (xnowp3 > xlastp3)
						xlastp3 = xnowp3;
				}
			}

			// average out the 3 xdesc elements
			double xlastp = 0;
			int x = 0;

			if (xlastp1 > 0) {
				xlastp += xlastp1;
				mbr_xdesc.append(getXdesc(item.getXdescelement_ID_1()) + ", ");
				x++;
			}
			if (xlastp2 > 0) {
				xlastp += xlastp2;
				mbr_xdesc.append(getXdesc(item.getXdescelement_ID_2()) + ", ");
				x++;
			}
			if (xlastp3 > 0) {
				xlastp += xlastp3;
				mbr_xdesc.append(getXdesc(item.getXdescelement_ID_3()) + ", ");
				x++;
			}
			if (xlastp > 0) {
				xlastp /= x;

				addToTempDetail(bag_match_al, TracingConstants.MATCH_XDESC, xlastp, mbr_xdesc.toString().length() > 2 ? mbr_xdesc.toString().substring(0,
						mbr_xdesc.toString().length() - 2) : "", ohd_xdesc.toString().length() > 2 ? ohd_xdesc.toString().substring(0,
						ohd_xdesc.toString().length() - 2) : "", -1);
				temptotal_cat += XDESC_WEIGHT;
				temppercent += xlastp * XDESC_WEIGHT;
				bagitems_matched = true;
			}
			mbr_xdesc = new StringBuffer(10);
			ohd_xdesc = new StringBuffer(10);

			/** ****** match contents ********** */
			

			// find the highest percentage match.
			lastpercent = 0;
			int numitems = 0;
			double temptotalpercent = 0;
			nowpercent = 0;

			StringBuffer iesb = new StringBuffer();
			StringBuffer oesb = new StringBuffer();
			StringTokenizer iest;
			StringTokenizer oest;

			// create incident content buffer
			for (int j = 0; j < item.getInventorylist().size(); j++) {
				if (j > 0)
					iesb.append("," + ((Item_Inventory) item.getInventorylist().get(j)).getTempcat() + " "
							+ ((Item_Inventory) item.getInventorylist().get(j)).getDescription());
				else
					iesb.append(((Item_Inventory) item.getInventorylist().get(j)).getTempcat() + " "
							+ ((Item_Inventory) item.getInventorylist().get(j)).getDescription());
			}
			iest = new StringTokenizer(iesb.toString(), ",;");

			// create ohd content buffer
			for (int j = 0; j < oe.getContents().size(); j++) {
				if (j > 0)
					oesb.append("," + (String) oe.getContents().get(j));
				else
					oesb.append((String) oe.getContents().get(j));
			}
			oest = new StringTokenizer(oesb.toString(), ",;");
			
			// loop through all ohd comma delimited
			while (oest.hasMoreTokens()) {
				lastpercent = 0;
				String oetoken = oest.nextToken();
				// separate mbr contents by commas
				while (iest.hasMoreTokens()) {
					
					// loop through each comma delimited mbr content strings, keep the
					// highest percentage
					nowpercent = StringCompare.compareStrings(StringUtils.removePronouns(iest.nextToken()), StringUtils.removePronouns(oetoken));
					if (nowpercent > lastpercent) {
						lastpercent = nowpercent;
					}
				}
				if (lastpercent > 10) {
					temptotalpercent += lastpercent;
					numitems++;
				}
			}

			// take final average
			lastpercent = numitems == 0 ? 0 : temptotalpercent / numitems;

			// insert into details
			if (lastpercent > 0) {
				details = new ArrayList();
				addToTempDetail(bag_match_al, TracingConstants.MATCH_CONTENTS, lastpercent, iesb.toString(), oesb.toString(), -1);
				bagitems_matched = true;
				if (catlevel == 1) {
					temptotal_cat += LVL1_CONTENTS_WEIGHT;
					temppercent += lastpercent * LVL1_CONTENTS_WEIGHT;
				} else {
					temptotal_cat += LVL2_CONTENTS_WEIGHT;
					temppercent += lastpercent * LVL2_CONTENTS_WEIGHT;
				}
			}

			temppercent = temppercent / temptotal_cat;

			if (catlevel == 2) {
				// add minimum and cap at max
				if (percent == 0) {
					// no pk matched
					temppercent *= (CAT_TWO_NO_PK_MAX / 100);
				} else {
					// has some pk matched
					temppercent *= (CAT_TWO_MAX / 100);
				}
				if (temppercent < CAT_TWO_MIN)
					temppercent += CAT_TWO_MIN;
			}
			

			// now insert into history only if > min_percent
			// and either bag item has to match or no claim was matched to write to
			// database

			if (temppercent > MIN_PERCENT && (bagitems_matched || !claimcheck_matched)) {
				// insert into history
				try {
					int bagnumber = -1;
					if (bagitems_matched)
						bagnumber = item.getBagnumber();
					ohd_thread.insertMatchHistory(OHD_ID, catlevel, temppercent, bag_match_al, "", bagnumber);

					logger.info("matching bag item + " + i + " result: " + temppercent);
				} catch (Exception e) {
					logger.error("unable to match bag item: " + e);
				}
				bag_match_al = null;
			}
		}

		return true;

	}

	/**
	 * non bag specific items, finalpercent is the percentage object linked back
	 * to main matching method, cat1min is the minimum for the primary key match
	 * (either 80% if it is cat 1 or 5 percent if it is cat 2
	 * 
	 * @param ie
	 * @param oe
	 * @param finalpercent
	 * @param cat1min
	 * @return
	 */
	public double[] nonBagSpecificMatching(IncidentElements ie, OHDElements oe, double cat1min) {

		double percent = 0;
		double nowpercent = 0;
		double lastpercent = 0;
		double total_cat_used = 0;

		String recordlocator = null;
		String membershipnum = null;
		String membership_company = null;
		String email = null;

		boolean matched = false;
		boolean entered = false; // both mbr and ohd item is keyed in, so start
		// matching on that.

		String tempohdname = "";
		String temponbagname = "";

		/** ********* match record locator ************ */
		recordlocator = ie.getRecordlocator();

		nowpercent = StringCompare.compareStrings(recordlocator, (oe.getRecordlocator()));
		// string match percentage is higher than cat_two_min_percentage
		if (nowpercent >= cat1min && nowpercent > 0) {
			addToDetail(TracingConstants.MATCH_RECORDLOC, nowpercent, recordlocator, oe.getRecordlocator(), -1);
			total_cat_used += RECORDLOC_WEIGHT;
			percent += nowpercent * RECORDLOC_WEIGHT;
		} else if (cat1min > 0 && nowpercent > 0) {
			// add with the nowpercent value, but use cat 1 percent for overall
			// percentage
			addToDetail(TracingConstants.MATCH_RECORDLOC, nowpercent, recordlocator, oe.getRecordlocator(), -1);
			percent += cat1min * RECORDLOC_WEIGHT;
			total_cat_used += RECORDLOC_WEIGHT;

		}

		/** *********** match membershipnum ************ */

		AirlineMembership mem = null;
		String com1 = null;
		String num1 = null;
		String mbrmem = null;
		String ohdmem = null;
		for (int i = 0; i < ie.getMemberships().size(); i++) {
			mem = (AirlineMembership) ie.getMemberships().get(i);
			com1 = mem.getCompanycode_ID().toUpperCase().trim();
			num1 = mem.getMembershipnum().toUpperCase().trim();

			// if company the same, and no number, 90% automatically
			if (!com1.equals("") && com1.equals(oe.getMembership_company()) && (num1.equals("") || oe.getMembershipnum().trim().equals(""))) {
				nowpercent = 90;
			} else if (com1.equals("") || oe.getMembership_company().equals("")) {
				// if no company, don't match this
				nowpercent = 0;
			} else {
				nowpercent = StringCompare.compareStrings(com1 + num1, oe.getMembership_company() + oe.getMembershipnum());
			}

			if (nowpercent > lastpercent) {
				mbrmem = com1 + " " + num1;
				ohdmem = oe.getMembership_company() + " " + oe.getMembershipnum();
				lastpercent = nowpercent;
			}
		}

		if (nowpercent >= cat1min && nowpercent > 0) {
			addToDetail(TracingConstants.MATCH_MEMBER, nowpercent, mbrmem, ohdmem, -1);
			total_cat_used += MEMBER_WEIGHT;
			percent += nowpercent * MEMBER_WEIGHT;
		} else if (cat1min > 0 && nowpercent > 0) {
			addToDetail(TracingConstants.MATCH_MEMBER, nowpercent, mbrmem, ohdmem, -1);
			total_cat_used += MEMBER_WEIGHT;
			percent += cat1min * MEMBER_WEIGHT;
		}

		/** ******** match address and emails********** */
		OHD_Address ohd_a = null;
		Address addr = null;

		nowpercent = 0;
		lastpercent = 0;
		double emailnowp = 0, emaillastp = 0;
		String mbremail = "";
		String ohdemail = "";
		String mbraddr = "";
		String ohdaddr = "";
		for (int i = 0; i < oe.getAddresses().size(); i++) {

			ohd_a = (OHD_Address) oe.getAddresses().get(i);

			// loop through mbr addresses and check one at a time
			for (int j = 0; j < ie.getAddresses().size(); j++) {
				addr = (Address) ie.getAddresses().get(j);
				// mbr addr and ohd addr both are not empty
				if (addr.getAddress1().trim().length() > 0 && addr.getCity().trim().length() > 0 && addr.getState_ID().trim().length() > 0
						&& ohd_a.getAddress1().trim().length() > 0 && ohd_a.getCity().trim().length() > 0 && ohd_a.getState_ID().trim().length() > 0) {
					nowpercent = StringCompare.compareStrings(addr.getAddress1() + " " + addr.getCity() + " " + addr.getState_ID() + " " + addr.getZip(), ohd_a
							.getAddress1()
							+ " " + ohd_a.getCity() + " " + ohd_a.getState_ID() + " " + ohd_a.getZip());

					if (nowpercent > lastpercent) {
						// only keep the highest percentage
						mbraddr = addr.getAddress1().trim().toUpperCase() + " " + addr.getCity().trim().toUpperCase() + " "
								+ addr.getState_ID().trim().toUpperCase() + " " + addr.getZip().trim().toUpperCase();
						ohdaddr = ohd_a.getAddress1().trim().toUpperCase() + " " + ohd_a.getCity().trim().toUpperCase() + " "
								+ ohd_a.getState_ID().trim().toUpperCase() + " " + ohd_a.getZip().trim().toUpperCase();
						lastpercent = nowpercent;
					}

				}

				// email
				emailnowp = StringCompare.compareStrings(addr.getEmail(), ohd_a.getEmail());
				if (emailnowp > emaillastp) {
					emaillastp = emailnowp;
					mbremail = addr.getEmail();
					ohdemail = ohd_a.getEmail();
				}
			}
		}
		if (lastpercent > 0) {
			addToDetail(TracingConstants.MATCH_ADDRESS, lastpercent, mbraddr, ohdaddr, -1);
			total_cat_used += ADDR_WEIGHT;
			percent += lastpercent * ADDR_WEIGHT;
		}

		if (emaillastp >= cat1min && emaillastp > 0) {
			addToDetail(TracingConstants.MATCH_EMAIL, emaillastp, mbremail, ohdemail, -1);
			total_cat_used += EMAIL_WEIGHT;
			percent += emaillastp * EMAIL_WEIGHT;
		} else if (cat1min > 0 && emaillastp > 0) {
			addToDetail(TracingConstants.MATCH_EMAIL, emaillastp, mbremail, ohdemail, -1);
			percent += cat1min * EMAIL_WEIGHT;
			total_cat_used += EMAIL_WEIGHT;
		}

		/** ********** match name ************* */
		//(all three must match, lastname, middle name, and firstname
		OHD_Passenger ohd_p = null;
		Passenger mbr_p = null;
		Item item = null;
		nowpercent = 0;
		lastpercent = 0;
		String tempmbrname = null;
		for (int i = 0; i < oe.getPassengers().size(); i++) {

			ohd_p = (OHD_Passenger) oe.getPassengers().get(i);
			// compare to primary mbr passenger name
			for (int j = 0; j < ie.getPassengers().size(); j++) {
				mbr_p = (Passenger) ie.getPassengers().get(j);

				if (ohd_p.getFirstname().trim().length() > 0 && ohd_p.getLastname().trim().length() > 0) {
					nowpercent = StringCompare.compareStrings(mbr_p.getFirstname() + " " + mbr_p.getMiddlename() + " " + mbr_p.getLastname(), ohd_p
							.getFirstname()
							+ " " + ohd_p.getMiddlename() + " " + ohd_p.getLastname());
					if (nowpercent > lastpercent) {
						lastpercent = nowpercent;
						tempohdname = ohd_p.getLastname() + ", " + ohd_p.getFirstname() + " " + ohd_p.getMiddlename();
						tempmbrname = mbr_p.getLastname() + ", " + mbr_p.getFirstname() + " " + mbr_p.getMiddlename();
					}

				}
			}
		}

		if (lastpercent > 0) {
			addToDetail(TracingConstants.MATCH_NAME, lastpercent, tempmbrname, tempohdname, -1);
			total_cat_used += NAME_WEIGHT;
			percent += lastpercent * NAME_WEIGHT;
		}

		/** ********** match phone number ************* */
		nowpercent = 0;
		lastpercent = 0;
		String mbrphone = "";
		String ohdphone = "";
		String temp1, temp2;
		for (int i = 0; i < ie.getPhonelist().size(); i++) {
			for (int j = 0; j < oe.getPhonelist().size(); j++) {
				temp1 = StringUtils.removeNonNumeric((String) ie.getPhonelist().get(i));
				temp2 = StringUtils.removeNonNumeric((String) oe.getPhonelist().get(j));
				nowpercent = StringCompare.compareStrings(temp1, temp2);
				if (nowpercent > lastpercent) {
					lastpercent = nowpercent;
					mbrphone = (String) ie.getPhonelist().get(i);
					ohdphone = (String) oe.getPhonelist().get(j);
				}
			}
		}
		if (lastpercent > 0) {
			addToDetail(TracingConstants.MATCH_PHONE, lastpercent, mbrphone, ohdphone, -1);
			total_cat_used += PHONE_WEIGHT;
			percent += lastpercent * PHONE_WEIGHT;
		}

		/** ********** match itinerary ************* */
		nowpercent = 0;
		lastpercent = 0;
		String mbriti = "", ohditi = "";
		Itinerary it = null;
		OHD_Itinerary ohd_it = null;
		for (int i = 0; i < ie.getItineraries().size(); i++) {
			it = (Itinerary) ie.getItineraries().get(i);
			for (int j = 0; j < oe.getItineraries().size(); j++) {
				ohd_it = (OHD_Itinerary) oe.getItineraries().get(j);
				if (StringCompare.compareStrings(it.getLegfrom() + it.getLegto() + it.getFlightnum(), ohd_it.getLegfrom() + ohd_it.getLegto()
						+ ohd_it.getFlightnum()) < 100) {
					nowpercent = 0;
				} else
					nowpercent = 100;

				if (nowpercent > lastpercent) {
					lastpercent = nowpercent;
					mbriti = it.getLegfrom() + " " + it.getLegto() + " " + it.getFlightnum() + " "
							+ (it.getDepartdate() == null ? "" : it.getDepartdate().toString()) + " "
							+ (it.getArrivedate() == null ? "" : it.getArrivedate().toString()) + " "
							+ (it.getSchdeparttime() == null ? "" : it.getSchdeparttime().toString()) + " "
							+ (it.getScharrivetime() == null ? "" : it.getScharrivetime().toString()) + " "
							+ (it.getActdeparttime() == null ? "" : it.getActdeparttime().toString()) + " "
							+ (it.getActarrivetime() == null ? "" : it.getActarrivetime().toString());
					ohditi = ohd_it.getLegfrom() + " " + ohd_it.getLegto() + " " + ohd_it.getFlightnum() + " "
							+ (ohd_it.getDepartdate() == null ? "" : ohd_it.getDepartdate().toString()) + " "
							+ (ohd_it.getArrivedate() == null ? "" : ohd_it.getArrivedate().toString()) + " "
							+ (ohd_it.getSchdeparttime() == null ? "" : ohd_it.getSchdeparttime().toString()) + " "
							+ (ohd_it.getScharrivetime() == null ? "" : ohd_it.getScharrivetime().toString()) + " "
							+ (ohd_it.getActdeparttime() == null ? "" : ohd_it.getActdeparttime().toString()) + " "
							+ (ohd_it.getActarrivetime() == null ? "" : ohd_it.getActarrivetime().toString());
				}
			}
		}
		if (lastpercent > 0) {
			addToDetail(TracingConstants.MATCH_ITINERARY, lastpercent, mbriti, ohditi, -1);
			total_cat_used += ITINERARY_WEIGHT;
			percent += lastpercent * ITINERARY_WEIGHT;
		}

		double[] result = { percent, total_cat_used };

		return result;

	}

	/**
	 * SECONDARY COLOR MATCH
	 * 
	 * @@param mbrcolor
	 * @@param ohdcolor
	 * @@return
	 */
	private double secondaryColorMatch(String mbrcolor, String ohdcolor) {
		mbrcolor = mbrcolor.trim().toUpperCase();
		ohdcolor = ohdcolor.trim().toUpperCase();

		if (ohdcolor.equals("BE")) {
			if (mbrcolor.equals("BN") || mbrcolor.equals("WT") || mbrcolor.equals("YW") || mbrcolor.equals("MC") || mbrcolor.equals("PR")
					|| mbrcolor.equals("TD"))
				return 100;
		}
		if (ohdcolor.equals("BK")) {
			if (mbrcolor.equals("BU") || mbrcolor.equals("GY") || mbrcolor.equals("MC") || mbrcolor.equals("PR") || mbrcolor.equals("TD"))
				return 100;
		}
		if (ohdcolor.equals("BN")) {
			if (mbrcolor.equals("BE") || mbrcolor.equals("RD") || mbrcolor.equals("MC") || mbrcolor.equals("PR") || mbrcolor.equals("TD"))
				return 100;
		}
		if (ohdcolor.equals("BU")) {
			if (mbrcolor.equals("BK") || mbrcolor.equals("GY") || mbrcolor.equals("MC") || mbrcolor.equals("PR") || mbrcolor.equals("TD"))
				return 100;
		}
		if (ohdcolor.equals("GN")) {
			if (mbrcolor.equals("MC") || mbrcolor.equals("PR") || mbrcolor.equals("TD"))
				return 100;
		}
		if (ohdcolor.equals("GY")) {
			if (mbrcolor.equals("BK") || mbrcolor.equals("BU") || mbrcolor.equals("MC") || mbrcolor.equals("PR") || mbrcolor.equals("TD"))
				return 100;
		}
		if (ohdcolor.equals("MC")) {
			return 100;
		}
		if (ohdcolor.equals("PR")) {
			return 100;
		}
		if (ohdcolor.equals("PU")) {
			if (mbrcolor.equals("RD") || mbrcolor.equals("BU") || mbrcolor.equals("MC") || mbrcolor.equals("TD") || mbrcolor.equals("PR"))
				return 100;
		}
		if (ohdcolor.equals("RD")) {
			if (mbrcolor.equals("BN") || mbrcolor.equals("MC") || mbrcolor.equals("PR") || mbrcolor.equals("TD"))
				return 100;
		}
		if (ohdcolor.equals("TD")) {
			return 100;
		}
		if (ohdcolor.equals("WT")) {
			if (mbrcolor.equals("BE") || mbrcolor.equals("MC") || mbrcolor.equals("PR") || mbrcolor.equals("TD"))
				return 100;
		}
		if (ohdcolor.equals("YW")) {
			if (mbrcolor.equals("BE") || mbrcolor.equals("MC") || mbrcolor.equals("PR") || mbrcolor.equals("TD"))
				return 100;
		}
		return 0;
	}

	/**
	 * TERTIARY COLOR MATCHING
	 * 
	 * @@param mbrcolor
	 * @@param ohdcolor
	 * @@return
	 */
	private double tertiaryColorMatch(String mbrcolor, String ohdcolor) {
		mbrcolor = mbrcolor.trim().toUpperCase();
		ohdcolor = ohdcolor.trim().toUpperCase();

		if (ohdcolor.equals("BE")) {
			if (mbrcolor.equals("GN"))
				return 100;
		}
		if (ohdcolor.equals("BK")) {
			if (mbrcolor.equals("BN") || mbrcolor.equals("GN"))
				return 100;
		}
		if (ohdcolor.equals("BN")) {
			if (mbrcolor.equals("BK") || mbrcolor.equals("GN") || mbrcolor.equals("GY"))
				return 100;
		}
		if (ohdcolor.equals("BU")) {
			if (mbrcolor.equals("GN"))
				return 100;
		}
		if (ohdcolor.equals("GN")) {
			if (mbrcolor.equals("GY") || mbrcolor.equals("BN") || mbrcolor.equals("BE") || mbrcolor.equals("BU") || mbrcolor.equals("BK"))
				return 100;
		}
		if (ohdcolor.equals("GY")) {
			if (mbrcolor.equals("BN") || mbrcolor.equals("GN"))
				return 100;
		}
		if (ohdcolor.equals("PU")) {
			if (mbrcolor.equals("BK") || mbrcolor.equals("BN"))
				return 100;
		}
		if (ohdcolor.equals("RD")) {
			if (mbrcolor.equals("YW"))
				return 100;
		}
		if (ohdcolor.equals("YW")) {
			if (mbrcolor.equals("RD"))
				return 100;
		}
		return 0;
	}

	private double secondaryTypeMatch(String mbrbt, String ohdbt) {
		mbrbt = mbrbt.trim().toUpperCase();
		ohdbt = ohdbt.trim().toUpperCase();

		if (ohdbt.equals("01")) {
			if (mbrbt.equals("02"))
				return 100;
		}
		if (ohdbt.equals("02")) {
			if (mbrbt.equals("01") || mbrbt.equals("22"))
				return 100;
		}
		if (ohdbt.equals("03")) {
			if (mbrbt.equals("04") || mbrbt.equals("05"))
				return 100;
		}
		if (ohdbt.equals("04")) {
			if (mbrbt.equals("03"))
				return 100;
		}
		if (ohdbt.equals("05")) {
			if (mbrbt.equals("03") || mbrbt.equals("04") || mbrbt.equals("23"))
				return 100;
		}
		if (ohdbt.equals("06")) {
			if (mbrbt.equals("07"))
				return 100;
		}
		if (ohdbt.equals("07")) {
			if (mbrbt.equals("06"))
				return 100;
		}
		if (ohdbt.equals("08")) {
			if (mbrbt.equals("09"))
				return 100;
		}
		if (ohdbt.equals("09")) {
			if (mbrbt.equals("08") || mbrbt.equals("51") || mbrbt.equals("96"))
				return 100;
		}
		if (ohdbt.equals("11")) {
			if (mbrbt.equals("52") || mbrbt.equals("12"))
				return 100;
		}
		if (ohdbt.equals("12")) {
			if (mbrbt.equals("11"))
				return 100;
		}
		if (ohdbt.equals("22")) {
			if (mbrbt.equals("02"))
				return 100;
		}
		if (ohdbt.equals("23")) {
			if (mbrbt.equals("05"))
				return 100;
		}
		if (ohdbt.equals("24")) {
			if (mbrbt.equals("27"))
				return 100;
		}
		if (ohdbt.equals("27")) {
			if (mbrbt.equals("24") || mbrbt.equals("96"))
				return 100;
		}
		if (ohdbt.equals("50")) {
			if (mbrbt.equals("51"))
				return 100;
		}
		if (ohdbt.equals("51")) {
			if (mbrbt.equals("09") || mbrbt.equals("50"))
				return 100;
		}
		if (ohdbt.equals("52")) {
			if (mbrbt.equals("11") || mbrbt.equals("12"))
				return 100;
		}
		if (ohdbt.equals("55")) {
			if (mbrbt.equals("93"))
				return 100;
		}
		if (ohdbt.equals("63")) {
			if (mbrbt.equals("75"))
				return 100;
		}
		if (ohdbt.equals("66")) {
			if (mbrbt.equals("67"))
				return 100;
		}
		if (ohdbt.equals("67")) {
			if (mbrbt.equals("66"))
				return 100;
		}
		if (ohdbt.equals("70")) {
			if (mbrbt.equals("73") || mbrbt.equals("74"))
				return 100;
		}
		if (ohdbt.equals("73")) {
			if (mbrbt.equals("70") || mbrbt.equals("74"))
				return 100;
		}
		if (ohdbt.equals("74")) {
			if (mbrbt.equals("70") || mbrbt.equals("73"))
				return 100;
		}
		if (ohdbt.equals("75")) {
			if (mbrbt.equals("63"))
				return 100;
		}
		if (ohdbt.equals("80")) {
			if (mbrbt.equals("81"))
				return 100;
		}
		if (ohdbt.equals("81")) {
			if (mbrbt.equals("80"))
				return 100;
		}
		if (ohdbt.equals("82")) {
			if (mbrbt.equals("84"))
				return 100;
		}
		if (ohdbt.equals("84")) {
			if (mbrbt.equals("82"))
				return 100;
		}
		if (ohdbt.equals("91")) {
			if (mbrbt.equals("94") || mbrbt.equals("95"))
				return 100;
		}
		if (ohdbt.equals("93")) {
			if (mbrbt.equals("55") || mbrbt.equals("96"))
				return 100;
		}
		if (ohdbt.equals("94")) {
			if (mbrbt.equals("91"))
				return 100;
		}
		if (ohdbt.equals("95")) {
			if (mbrbt.equals("91"))
				return 100;
		}
		if (ohdbt.equals("96")) {
			if (mbrbt.equals("93") || mbrbt.equals("27") || mbrbt.equals("9"))
				return 100;
		}
		return 0;
	}

	private double tertiaryTypeMatch(String mbrbt, String ohdbt) {
		mbrbt = mbrbt.trim().toUpperCase();
		ohdbt = ohdbt.trim().toUpperCase();

		if (ohdbt.equals("01")) {
			if (mbrbt.equals("03"))
				return 100;
		}
		if (ohdbt.equals("03")) {
			if (mbrbt.equals("01"))
				return 100;
		}
		if (ohdbt.equals("04")) {
			if (mbrbt.equals("05"))
				return 100;
		}
		if (ohdbt.equals("05")) {
			if (mbrbt.equals("04"))
				return 100;
		}
		if (ohdbt.equals("06")) {
			if (mbrbt.equals("07"))
				return 100;
		}
		if (ohdbt.equals("07")) {
			if (mbrbt.equals("06"))
				return 100;
		}
		if (ohdbt.equals("08")) {
			if (mbrbt.equals("29") || mbrbt.equals("64"))
				return 100;
		}
		if (ohdbt.equals("09")) {
			if (mbrbt.equals("29") || mbrbt.equals("64") || mbrbt.equals("51") || mbrbt.equals("93"))
				return 100;
		}
		if (ohdbt.equals("10")) {
			if (mbrbt.equals("50") || mbrbt.equals("55") || mbrbt.equals("80") || mbrbt.equals("81") || mbrbt.equals("82") || mbrbt.equals("83")
					|| mbrbt.equals("84") || mbrbt.equals("92"))
				return 100;
		}
		if (ohdbt.equals("20")) {
			if (mbrbt.equals("24"))
				return 100;
		}
		if (ohdbt.equals("22")) {
			if (mbrbt.equals("23"))
				return 100;
		}
		if (ohdbt.equals("23")) {
			if (mbrbt.equals("22"))
				return 100;
		}
		if (ohdbt.equals("24")) {
			if (mbrbt.equals("20") || mbrbt.equals("25"))
				return 100;
		}
		if (ohdbt.equals("25")) {
			if (mbrbt.equals("08") || mbrbt.equals("24") || mbrbt.equals("26") || mbrbt.equals("27") || mbrbt.equals("68") || mbrbt.equals("93") || mbrbt.equals("97"))
				return 100;
		}
		if (ohdbt.equals("26")) {
			if (mbrbt.equals("25"))
				return 100;
		}
		if (ohdbt.equals("27")) {
			if (mbrbt.equals("25") || mbrbt.equals("93"))
				return 100;
		}
		if (ohdbt.equals("29")) {
			if (mbrbt.equals("08") || mbrbt.equals("09"))
				return 100;
		}
		if (ohdbt.equals("50")) {
			if (mbrbt.equals("10"))
				return 100;
		}
		if (ohdbt.equals("54")) {
			if (mbrbt.equals("60"))
				return 100;
		}
		if (ohdbt.equals("55")) {
			if (mbrbt.equals("10"))
				return 100;
		}
		if (ohdbt.equals("59")) {
			if (mbrbt.equals("06") || mbrbt.equals("07"))
				return 100;
		}
		if (ohdbt.equals("64")) {
			if (mbrbt.equals("08") || mbrbt.equals("09"))
				return 100;
		}
		if (ohdbt.equals("65")) {
			if (mbrbt.equals("97"))
				return 100;
		}
		if (ohdbt.equals("68")) {
			if (mbrbt.equals("25"))
				return 100;
		}
		if (ohdbt.equals("69")) {
			if (mbrbt.equals("99"))
				return 100;
		}
		if (ohdbt.equals("71")) {
			if (mbrbt.equals("72"))
				return 100;
		}
		if (ohdbt.equals("72")) {
			if (mbrbt.equals("71"))
				return 100;
		}
		if (ohdbt.equals("80")) {
			if (mbrbt.equals("10") || mbrbt.equals("81") || mbrbt.equals("82"))
				return 100;
		}
		if (ohdbt.equals("81")) {
			if (mbrbt.equals("10") || mbrbt.equals("80") || mbrbt.equals("82"))
				return 100;
		}
		if (ohdbt.equals("82")) {
			if (mbrbt.equals("10") || mbrbt.equals("81") || mbrbt.equals("80") || mbrbt.equals("84"))
				return 100;
		}
		if (ohdbt.equals("83")) {
			if (mbrbt.equals("10"))
				return 100;
		}
		if (ohdbt.equals("84")) {
			if (mbrbt.equals("10") || mbrbt.equals("82"))
				return 100;
		}
		if (ohdbt.equals("91")) {
			if (mbrbt.equals("99"))
				return 100;
		}
		if (ohdbt.equals("92")) {
			if (mbrbt.equals("10"))
				return 100;
		}
		if (ohdbt.equals("93")) {
			if (mbrbt.equals("9") || mbrbt.equals("25") || mbrbt.equals("27"))
				return 100;
		}
		if (ohdbt.equals("94")) {
			if (mbrbt.equals("95"))
				return 100;
		}
		if (ohdbt.equals("95")) {
			if (mbrbt.equals("94"))
				return 100;
		}
		if (ohdbt.equals("97")) {
			if (mbrbt.equals("65") || mbrbt.equals("25"))
				return 100;
		}
		if (ohdbt.equals("99")) {
			if (mbrbt.equals("69") || mbrbt.equals("91"))
				return 100;
		}

		return 0;
	}

	private void addToDetail(String item, double p, String mbr_info, String ohd_info, int loc) {

		Match_Detail md = new Match_Detail();
		md.setItem(item);
		md.setPercentage(p);
		md.setMbr_info(mbr_info);
		md.setOhd_info(ohd_info);

		// if requested to be inserted at the front, then do so (claimcheck, pks)
		if (loc != 0)
			details.add(md);
		else
			details.add(loc, md);
	}

	private void addToTempDetail(ArrayList al, String item, double p, String mbr_info, String ohd_info, int loc) {
		Match_Detail md = new Match_Detail();
		md.setItem(item);
		md.setPercentage(p);
		md.setMbr_info(mbr_info);
		md.setOhd_info(ohd_info);

		// if requested to be inserted at the front, then do so (claimcheck, pks)
		if (loc != 0)
			al.add(md);
		else
			al.add(loc, md);
	}

	/**
	 * get xdesc description
	 */
	private static ArrayList xdesclist = new ArrayList();

	private String getXdesc(int xdesc_ID) {
		if (xdesc_ID == 0)
			return "";
		if (xdesclist.size() == 0) {
			try {
				Statement stmt = ohd_thread.getOhd_conn().createStatement();
				ResultSet rs = null;

				String sql = "select description from xdescelement order by xdesc_id";
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					xdesclist.add(rs.getString("description"));
				}
				rs.close();
				stmt.close();
			} catch (Exception e) {
				return Integer.toString(xdesc_ID);
			}
		}

		return (String) xdesclist.get(xdesc_ID - 1);
	}

	/**
	 * get bagtype description
	 * 
	 * @return
	 */

	private String getBagtype(String bagtype) {
		if (bagtype == null || bagtype.equals(""))
			return "";
		int temp = Integer.parseInt(bagtype);
		if (temp >= 1 && temp <= 12)
			return "(" + bagtype + ") Non-Zippered type bags";
		if (temp >= 20 && temp <= 29)
			return "(" + bagtype + ") Zippered type bags";
		if (temp >= 50 && temp <= 99)
			return "(" + bagtype + ") Miscellaneous articles";
		return "";
	}

	/**
	 * get manufacturer descriptiion
	 */
	private static ArrayList manulist = new ArrayList();

	private String getManu(int manu_ID) {
		if (manu_ID == 0)
			return "";
		if (manulist.size() == 0) {
			try {
				Statement stmt = ohd_thread.getOhd_conn().createStatement();
				ResultSet rs = null;

				String sql = "select description from manufacturer order by manufacturer_ID";
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					manulist.add(rs.getString("description"));
				}
				rs.close();
				stmt.close();
			} catch (Exception e) {
				return Integer.toString(manu_ID);
			}
		}

		return (String) manulist.get(manu_ID - 1);
	}

	/**
	 * @@return Returns the details.
	 */
	public ArrayList getDetails() {
		return details;
	}

	/**
	 * @@param details The details to set.
	 */
	public void setDetails(ArrayList details) {
		this.details = details;
	}

	/**
	 * @return Returns the cat_1_claim_match.
	 */
	public String getCat_1_claim_match() {
		return cat_1_claim_match;
	}

	/**
	 * @param cat_1_claim_match
	 *          The cat_1_claim_match to set.
	 */
	public void setCat_1_claim_match(String cat_1_claim_match) {
		this.cat_1_claim_match = cat_1_claim_match;
	}

}