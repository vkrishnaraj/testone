/*
 * Created on Sep 2, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.LossCodeBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Airport;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code;
import com.bagnet.nettracer.tracing.db.DeliverCo_Station;
import com.bagnet.nettracer.tracing.db.DeliverCompany;
import com.bagnet.nettracer.tracing.db.Deliver_ServiceLevel;
import com.bagnet.nettracer.tracing.db.Depreciation_Category;
import com.bagnet.nettracer.tracing.db.GeneralDepreciationRules;
import com.bagnet.nettracer.tracing.db.IATA_irregularity_code;
import com.bagnet.nettracer.tracing.db.ItemType;
import com.bagnet.nettracer.tracing.db.Lz;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.UserGroup;
import com.bagnet.nettracer.tracing.db.WTCompany;
import com.bagnet.nettracer.tracing.db.Work_Shift;
import com.bagnet.nettracer.tracing.db.audit.Audit_Airport;
import com.bagnet.nettracer.tracing.db.audit.Audit_DeliverCo_Station;
import com.bagnet.nettracer.tracing.db.audit.Audit_DeliverCompany;
import com.bagnet.nettracer.tracing.db.audit.Audit_Deliver_ServiceLevel;
import com.bagnet.nettracer.tracing.db.audit.Audit_Station;
import com.bagnet.nettracer.tracing.db.audit.Audit_Work_Shift;
import com.bagnet.nettracer.tracing.db.lf.Subcompany;
import com.bagnet.nettracer.tracing.utils.audit.AuditAirportUtils;
import com.bagnet.nettracer.tracing.utils.audit.AuditDeliveryCompanyUtils;
import com.bagnet.nettracer.tracing.utils.audit.AuditStationUtils;
import com.bagnet.nettracer.tracing.utils.audit.AuditWorkshiftUtils;

/**
 * @author Ankur Gupta
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class HibernateUtils {

	private static Logger logger = Logger.getLogger(HibernateUtils.class);
	
	public static void save(Object obj) {
		save(obj, null);
	}
	
	/**
	 * 
	 * @param obj
	 */
	public static void save(Object obj, Session sess) {
		boolean sessionNull = (sess == null);
		Transaction t = null;
		try {
			if (sessionNull) {
				sess = HibernateWrapper.getSession().openSession();
			}
			t = sess.beginTransaction();
			sess.saveOrUpdate(obj);
			t.commit();
		} catch (Exception e) {
			logger.error("Error Saving: ", e);
			e.printStackTrace();
			if (t != null) {
				try {
					t.rollback();
				} catch (Exception ex) {
					logger.error("Error Saving: ", ex);
				}
			}
		} finally {
			
			if (sess != null && sessionNull) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * @param obj
	 */
	public static boolean delete(Object obj, Session sess) {
		boolean sessionNull = (sess == null);

		
		Transaction t = null;
		try {
			if (sessionNull) {
				sess = HibernateWrapper.getSession().openSession();
			}
			t = sess.beginTransaction();
			sess.delete(obj);
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (t != null) {
				try {
					t.rollback();
					
				} catch (Exception ex) {
				}
			}
			return false;
		} finally {
			if (sess != null && sessionNull) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @param obj
	 */
	public static boolean delete(Object obj) {
		return delete(obj, null);
	}

	/**
	 * 
	 * @param obj
	 */
	public static void saveNew(Object obj) {
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			sess.save(obj);
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (t != null) {
				try {
					t.rollback();
				} catch (Exception ex) {
				}
			}
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * @param obj
	 * @throws Exception
	 */
	public static void saveCompany(Company obj, String pageState, boolean saveLzList) throws Exception {
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			//Figure out if new or old.
			boolean isNew = false;
			Criteria cri = sess.createCriteria(Company.class).add(
					Restrictions.eq("companyCode_ID", obj.getCompanyCode_ID()));
			@SuppressWarnings("unchecked")
			List<Company> results = cri.list();
			if (results == null || results.size() <= 0) isNew = true;
			if (isNew) {
				//create a new company with guest group and LZ station.

				UserGroup g = new UserGroup();
				g.setCompanycode_ID(obj.getCompanyCode_ID());
				g.setDescription("Guest");
				g.setDescription2("Default Guest group for " + obj.getCompanyCode_ID());

				UserGroup g1 = new UserGroup();
				g1.setCompanycode_ID(obj.getCompanyCode_ID());
				g1.setDescription("LZ");
				g1.setDescription2("Default LZ group for " + obj.getCompanyCode_ID());

				UserGroup g2 = new UserGroup();
				g2.setCompanycode_ID(obj.getCompanyCode_ID());
				g2.setDescription("Claims");
				g2.setDescription2("Default Claims group for " + obj.getCompanyCode_ID());

				UserGroup g3 = new UserGroup();
				g3.setCompanycode_ID(obj.getCompanyCode_ID());
				g3.setDescription("Admin");
				g3.setDescription2("Default Admin group for " + obj.getCompanyCode_ID());

				//Setup the company specific codes
				List<IATA_irregularity_code> iata_codes = LossCodeBMO.getIATACodes(0, 0);
				
				List<Company_specific_irregularity_code> comp_irr_codes = new ArrayList<Company_specific_irregularity_code>();
				
				if (iata_codes != null && iata_codes.size() > 0) {
					for (Iterator<IATA_irregularity_code> i = iata_codes.iterator(); i.hasNext();) {
						IATA_irregularity_code code = (IATA_irregularity_code) i.next();

						List<ItemType> itemTypes = IncidentUtils.retrieveItemTypes();

						if (itemTypes != null && itemTypes.size() > 0) {
							for (Iterator<ItemType> itemIter = itemTypes.iterator(); itemIter.hasNext();) {

								ItemType itemType = (ItemType) itemIter.next();
								Company_specific_irregularity_code csCode = new Company_specific_irregularity_code();

								csCode.setCompany(obj);
								csCode.setDescription(code.getDescription());
								csCode.setLoss_code(code.getLoss_code());
								csCode.setReport_type(itemType.getItemType_ID());
								comp_irr_codes.add(csCode);
								
							}
						}
					}
				}
				if (comp_irr_codes.size() > 0) obj.setIrregularity_codes(comp_irr_codes);

				
				//Give access to certain things --> Component policies.
				sess.save(obj);

				Station s = new Station();
				s.setStationcode("LZ");
				s.setStationdesc("Default LZ station for " + obj.getCompanyCode_ID());
				s.setCompany(obj);
				s.setActive(true);
				
				Lz lz = new Lz();
				lz.setPercent_load(100);
				lz.setStation(s);
				lz.setIs_default(true);
				lz.setCompanyCode_ID(obj.getCompanyCode_ID());
				
				

				Station s1 = new Station();
				s1.setStationcode("CLAIM");
				s1.setStationdesc("Default CLAIM station for " + obj.getCompanyCode_ID());
				s1.setCompany(obj);
				s1.setActive(true);

				sess.save(obj.getVariable());
				
				sess.save(g);
				sess.save(g1);
				sess.save(g2);
				sess.save(g3);

				sess.save(s);
				sess.save(s1);
				
				sess.save(lz);
			} else {
				Company c = (Company) results.get(0);
				
				if (pageState.equals(TracingConstants.COMPANY_PAGESTATE_INFO)) {
					c.setCompanydesc(obj.getCompanydesc());
					c.setAddress1(obj.getAddress1());
					c.setAddress2(obj.getAddress2());
					c.setCity(obj.getCity());
					c.setState_ID(obj.getState_ID());
					c.setCountrycode_ID(obj.getCountrycode_ID());
					c.setZip(obj.getZip());
					c.setPhone(obj.getPhone());
					c.setEmail_address(obj.getEmail_address());
				}

				if (c.getVariable() != null) {
					if (pageState.equals(TracingConstants.COMPANY_PAGESTATE_SETTINGS)) {
						c.getVariable().setMin_match_percent(obj.getVariable().getMin_match_percent());
						c.getVariable().setSeconds_wait(obj.getVariable().getSeconds_wait());
						c.getVariable().setTotal_threads(obj.getVariable().getTotal_threads());
						c.getVariable().setDefault_station_code(obj.getVariable().getDefault_station_code());
						c.getVariable().setDefault_loss_code(obj.getVariable().getDefault_loss_code());
						c.getVariable().setEmail_customer(obj.getVariable().isEmail_customer());
						c.getVariable().setAutoCloseOhd(obj.getVariable().isAutoCloseOhd());
						c.getVariable().setEmail_host(obj.getVariable().getEmail_host());
						c.getVariable().setEmail_port(obj.getVariable().getEmail_port());
						c.getVariable().setEmail_from(obj.getVariable().getEmail_from());
						c.getVariable().setEmail_to(obj.getVariable().getEmail_to());
						c.getVariable().setBlindEmail(obj.getVariable().getBlindEmail());

						c.getVariable().setMax_image_file_size(obj.getVariable().getMax_image_file_size());

						c.getVariable().setMin_interim_approval_check(
								obj.getVariable().getMin_interim_approval_check());
						c.getVariable().setMin_interim_approval_voucher(
								obj.getVariable().getMin_interim_approval_voucher());
						c.getVariable().setMin_interim_approval_miles(
								obj.getVariable().getMin_interim_approval_miles());
						c.getVariable().setMin_interim_approval_cc_refund(
								obj.getVariable().getMin_interim_approval_cc_refund());
						c.getVariable().setMin_interim_approval_incidental(
								obj.getVariable().getMin_interim_approval_incidental());
						
						
						c.getVariable().setScannerDefaultBack(obj.getVariable().getScannerDefaultBack());
						c.getVariable().setScannerDefaultForward(obj.getVariable().getScannerDefaultForward());
						
						c.getVariable().setAuto_close_days_back(obj.getVariable().getAuto_close_days_back());
						c.getVariable().setAuto_close_ohd_days_back(obj.getVariable().getAuto_close_ohd_days_back());
						c.getVariable().setAuto_close_ld_code(obj.getVariable().getAuto_close_ld_code());
						c.getVariable().setAuto_close_dam_code(obj.getVariable().getAuto_close_dam_code());
						c.getVariable().setAuto_close_pil_code(obj.getVariable().getAuto_close_pil_code());
						c.getVariable().setAuto_close_ld_station(obj.getVariable().getAuto_close_ld_station());
						c.getVariable().setAuto_close_dam_station(obj.getVariable().getAuto_close_dam_station());
						c.getVariable().setAuto_close_pil_station(obj.getVariable().getAuto_close_pil_station());
						
						c.getVariable().setPnr_last_x_days(obj.getVariable().getPnr_last_x_days());
						c.getVariable().setIncident_lock_mins(obj.getVariable().getIncident_lock_mins());
						c.getVariable().setIssuance_edit_last_x_days(obj.getVariable().getIssuance_edit_last_x_days());
						c.getVariable().setFraudReview(obj.getVariable().isFraudReview());
						c.getVariable().setBagdrop_autorefresh_mins(obj.getVariable().getBagdrop_autorefresh_mins());
					}
					
					if (pageState.equals(TracingConstants.COMPANY_PAGESTATE_MOVETOLZ)) {
						if (saveLzList) {
							c.getVariable().setLz_mode(obj.getVariable().getLz_mode());							
						} else {
							c.getVariable().setMbr_to_lz_days(obj.getVariable().getMbr_to_lz_days());
							c.getVariable().setDamaged_to_lz_days(obj.getVariable().getDamaged_to_lz_days());
							c.getVariable().setMiss_to_lz_days(obj.getVariable().getMiss_to_lz_days());
							c.getVariable().setOhd_to_lz_days(obj.getVariable().getOhd_to_lz_days());
							c.getVariable().setOhd_lz(obj.getVariable().getOhd_lz());
						}
					}
					
					if (pageState.equals(TracingConstants.COMPANY_PAGESTATE_SECURITY)) {
						c.getVariable().setPass_expire_days(obj.getVariable().getPass_expire_days());
						c.getVariable().setMax_failed_logins(obj.getVariable().getMax_failed_logins());
						c.getVariable().setSecure_password(obj.getVariable().getSecure_password());
						c.getVariable().setMin_pass_size(obj.getVariable().getMin_pass_size());
						c.getVariable().setPass_x_history(obj.getVariable().getPass_x_history());
					}

					if (pageState.equals(TracingConstants.COMPANY_PAGESTATE_AUDITING)) {
						c.getVariable().setAudit_ohd(obj.getVariable().getAudit_ohd());
						c.getVariable().setAudit_lost_found(obj.getVariable().getAudit_lost_found());
						c.getVariable().setAudit_lost_delayed(obj.getVariable().getAudit_lost_delayed());
						c.getVariable().setAudit_damaged(obj.getVariable().getAudit_damaged());
						c.getVariable().setAudit_missing_articles(obj.getVariable().getAudit_missing_articles());
						c.getVariable().setAudit_claims(obj.getVariable().getAudit_claims());
						c.getVariable().setAudit_agent(obj.getVariable().getAudit_agent());
						c.getVariable().setAudit_group(obj.getVariable().getAudit_group());
						c.getVariable().setAudit_company(obj.getVariable().getAudit_company());
						c.getVariable().setAudit_shift(obj.getVariable().getAudit_shift());
						c.getVariable().setAudit_station(obj.getVariable().getAudit_station());
						c.getVariable().setAudit_loss_codes(obj.getVariable().getAudit_loss_codes());
						c.getVariable().setAudit_airport(obj.getVariable().getAudit_airport());
						c.getVariable().setAudit_delivery_companies(obj.getVariable().getAudit_delivery_companies());
					}
					
					if (pageState.equals(TracingConstants.COMPANY_PAGESTATE_WEB_SERVICES)) {
						c.getVariable().setWebs_enabled(obj.getVariable().getWebs_enabled());
					}
					
					if (pageState.equals(TracingConstants.COMPANY_PAGESTATE_WORLDTRACER))  {
						c.getVariable().setWt_user(obj.getVariable().getWt_user());
						c.getVariable().setWt_pass(obj.getVariable().getWt_pass());
						c.getVariable().setMbr_to_wt_days(obj.getVariable().getMbr_to_wt_days());
						c.getVariable().setOhd_to_wt_hours(obj.getVariable().getOhd_to_wt_hours());
						c.getVariable().setOal_inc_hours(obj.getVariable().getOal_inc_hours());
						c.getVariable().setOal_ohd_hours(obj.getVariable().getOal_ohd_hours());
						c.getVariable().setWt_url(obj.getVariable().getWt_url());
						c.getVariable().setWt_airlinecode(obj.getVariable().getWt_airlinecode());
						c.getVariable().setWt_enabled(obj.getVariable().getWt_enabled());
						c.getVariable().setWt_write_enabled(obj.getVariable().getWt_write_enabled());
						c.getVariable().setAuto_wt_amend(obj.getVariable().isAuto_wt_amend());
					}
					if (pageState.equals(TracingConstants.COMPANY_PAGESTATE_STATUSMESSAGE)) {
						c.getVariable().setStatus_message(obj.getVariable().getStatus_message());
					}
				}
				sess.saveOrUpdate(c);

				//save the new company specific variable
				if (c.getVariable() == null) sess.save(obj.getVariable());
			}
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (t != null) {
				t.rollback();
			}
			throw e;
		} finally {
			if (sess != null) sess.close();
		}
	}

	/**
	 * 
	 * @param obj
	 * @throws Exception
	 */
	public static void saveStation(Station obj, Agent user) throws Exception {
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			//Figure out if new or old.
			boolean isNew = obj.getStation_ID() != 0 ? false: true;
			if (isNew) {
				sess.save(obj);
			} else {
				sess.saveOrUpdate(obj);
			}

			if (user.getStation().getCompany().getVariable().getAudit_station() == 1) {
				Audit_Station audit_station = AuditStationUtils.getAuditStation(obj, user);
				if (audit_station != null) {
					sess.save(audit_station);
				}
			}

			t.commit();
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			throw e;
		} finally {
			if (sess != null) sess.close();
		}
	}
	
	/**
	 * 
	 * @param obj
	 * @throws Exception
	 */
	public static void saveSubCompany(Subcompany obj, Agent user) throws Exception {
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			//Figure out if new or old.
			boolean isNew = obj.getId() != 0 ? false: true;
			if (isNew) {
				sess.save(obj);
			} else {
				sess.saveOrUpdate(obj);
			}

//			if (user.getStation().getCompany().getVariable().getAudit_station() == 1) {
//				Audit_Station audit_station = AuditStationUtils.getAuditStation(obj, user);
//				if (audit_station != null) {
//					sess.save(audit_station);
//				}
//			}

			t.commit();
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			throw e;
		} finally {
			if (sess != null) sess.close();
		}
	}
	
	/**
	 * 
	 * @param obj
	 * @throws Exception
	 */
	public static void saveDepreciationCategory(Depreciation_Category obj, Agent user) throws Exception {
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			//Figure out if new or old.
			boolean isNew = obj.getId() != 0 ? false: true;
			if (isNew) {
				sess.save(obj);
			} else {
				sess.saveOrUpdate(obj);
			}

			t.commit();
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			throw e;
		} finally {
			if (sess != null) sess.close();
		}
	}


	/**
	 * 
	 * @param obj
	 * @throws Exception
	 */
	public static void saveDepreciationRules(GeneralDepreciationRules obj, Agent user) throws Exception {
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			//Figure out if new or old.
			boolean isNew = obj.getId() != 0 ? false: true;
			if (isNew) {
				sess.save(obj);
			} else {
				sess.saveOrUpdate(obj);
			}

			t.commit();
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			throw e;
		} finally {
			if (sess != null) sess.close();
		}
	}
	
	/**
	 * 
	 * @param obj
	 * @throws Exception
	 */
	public static boolean saveDeliverCo_Station(DeliverCo_Station obj, Agent user) throws Exception {
		Session sess = null;
		Transaction t = null;
		boolean retValue = true; 
		try {
			sess = HibernateWrapper.getSession().openSession();
			
			
			Criteria cri = sess.createCriteria(DeliverCo_Station.class).add(Restrictions.eq("station_ID", 
					obj.getStation_ID())).add(Restrictions.eq("delivercompany", 
							obj.getDelivercompany()));
					
			@SuppressWarnings("unchecked")
			List<DeliverCo_Station> results = cri.list();
			if (results == null || results.size() <= 0) {
				sess.save(obj);
				t = sess.beginTransaction();

				sess.save(obj);
				
				if (user.getStation().getCompany().getVariable().getAudit_delivery_companies() == 1) {
					
					Audit_DeliverCo_Station audit_dcs = AuditDeliveryCompanyUtils.getAuditDeliverCoStation(obj, user);
					if (audit_dcs != null) {
						sess.save(audit_dcs);
					}
				}

				t.commit();

			} else {
				retValue = false;
			}

			
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			throw e;
		} finally {
			if (sess != null) sess.close();
		}
		return retValue;
	}

	
	/**
	 * 
	 * @param obj
	 * @throws Exception
	 */
	public static void saveServiceLevel(Deliver_ServiceLevel obj, Agent user) throws Exception {
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			//Figure out if new or old.
			boolean isNew = false;
			Criteria cri = sess.createCriteria(Deliver_ServiceLevel.class).add(
					Restrictions.eq("servicelevel_ID", obj.getServicelevel_ID()));
			@SuppressWarnings("unchecked")
			List<Deliver_ServiceLevel> results = cri.list();
			if (results == null || results.size() <= 0) isNew = true;

			Deliver_ServiceLevel objRef = null;
			if (isNew) {
				sess.save(obj);
				objRef = obj;
			} else {
				Deliver_ServiceLevel c = (Deliver_ServiceLevel) results.get(0);
				c.setDelivercompany(obj.getDelivercompany());
				c.setDescription(obj.getDescription());
				c.setService_code(obj.getService_code());
				sess.saveOrUpdate(c);
				objRef = c;
			}

			if (user.getStation().getCompany().getVariable().getAudit_delivery_companies() == 1) {
				Audit_Deliver_ServiceLevel audit_dsl = AuditDeliveryCompanyUtils.getAuditDeliver_ServiceLevel(objRef, user);
				if (audit_dsl != null) {
					sess.save(audit_dsl);
				}
			}

			t.commit();
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			throw e;
		} finally {
			if (sess != null) sess.close();
		}
	}
	
	
	/**
	 * 
	 * @param obj
	 * @throws Exception
	 */
	public static DeliverCompany saveDeliveryCompany(DeliverCompany obj, Agent user) throws Exception {
		Session sess = null;
		Transaction t = null;
		DeliverCompany retValue = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			//Figure out if new or old.
			boolean isNew = false;
			Criteria cri = sess.createCriteria(DeliverCompany.class).add(
					Restrictions.eq("delivercompany_ID", obj.getDelivercompany_ID()));
			@SuppressWarnings("unchecked")
			List<DeliverCompany> results = cri.list();
			if (results == null || results.size() <= 0) isNew = true;

			DeliverCompany objRef = null;
			if (isNew) {
				sess.save(obj);
				objRef = obj;
			} else {
				DeliverCompany c = (DeliverCompany) results.get(0);
				c.setAddress(obj.getAddress());
				c.setName(obj.getName());
				c.setPhone(obj.getPhone());
				c.setDelivery_integration_type(obj.getDelivery_integration_type());
				c.setIntegration_key(obj.getIntegration_key());
				sess.saveOrUpdate(c);
				objRef = c;
			}
			if (user.getStation().getCompany().getVariable().getAudit_delivery_companies() == 1) {
				Audit_DeliverCompany audit_dc = AuditDeliveryCompanyUtils.getAuditDeliverCompany(objRef, user);
				if (audit_dc != null) {
					//HibernateUtils.saveNew(audit_dc);
					sess.save(audit_dc);
				}
			}
			retValue = objRef;

			t.commit();
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			throw e;
		} finally {
			if (sess != null) sess.close();
		}
		return retValue;
	}
	
	/**
	 * 
	 * @param obj
	 * @throws Exception
	 */
	public static void saveShift(Work_Shift obj, Agent user) throws Exception {
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			//Figure out if new or old.
			boolean isNew = false;
			Criteria cri = sess.createCriteria(Work_Shift.class).add(
					Restrictions.eq("shift_code", obj.getShift_code())).add(
							Restrictions.eq("locale", obj.getLocale()));
			cri.createCriteria("company").add(
					Restrictions.eq("companyCode_ID", obj.getCompany().getCompanyCode_ID()));
			@SuppressWarnings("unchecked")
			List<Work_Shift> results = cri.list();
			Work_Shift objRef = null;

			if (results == null || results.size() <= 0) isNew = true;
			if (isNew) {
				sess.save(obj);
				objRef = obj;
			} else {
				Work_Shift s = (Work_Shift) results.get(0);
				s.setLocale(obj.getLocale());
				s.setShift_code(obj.getShift_code());
				s.setShift_description(obj.getShift_description());
				sess.saveOrUpdate(s);
				objRef = s;
			}

			if (user.getStation().getCompany().getVariable().getAudit_shift() == 1) {
				Audit_Work_Shift audit_shift = AuditWorkshiftUtils.getAuditShift(objRef, user);
				if (audit_shift != null) {
					sess.save(audit_shift);
				}
			}
			t.commit();
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			throw e;
		} finally {
			if (sess != null) sess.close();
		}
	}

	/**
	 * 
	 * @param obj
	 * @throws Exception
	 */
	public static void saveAirport(Airport obj, Agent user) throws Exception {
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			//Figure out if new or old.
			boolean isNew = obj.getId() != 0 ? false : true;
			if (isNew) {
				sess.save(obj);
			} else {
				sess.saveOrUpdate(obj);
			}

			if (user.getStation().getCompany().getVariable().getAudit_airport() == 1) {
				Audit_Airport audit_airport = AuditAirportUtils.getAuditAirport(obj, user);
				if (audit_airport != null) {
					sess.save(audit_airport);
				}
			}

			t.commit();
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			throw e;
		} finally {
			if (sess != null) sess.close();
		}
	}

	public static boolean deleteCode(Company_specific_irregularity_code obj) {
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			sess.delete(obj);
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (t != null) {
				try {
					t.rollback();
					
				} catch (Exception ex) {
				}
			}
			return false;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	/**
	 * 
	 * @param obj
	 * @param isNew
	 * @throws Exception
	 */
	public static void saveCode(Company_specific_irregularity_code obj, boolean isNew)
			throws Exception {
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			if (isNew) sess.save(obj);
			else {
				sess.saveOrUpdate(obj);
			}
			t.commit();
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			throw e;
		} finally {
			if (sess != null) sess.close();
		}
	}

	/**
	 * 
	 * @param obj
	 * @param groupId
	 * @throws Exception
	 */
	public static void saveGroup(UserGroup obj, String groupId, Agent user) throws Exception {
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();

			if (groupId == null || groupId.length() < 1) {
				sess.save(obj);
			} else {
				Criteria cri = sess.createCriteria(UserGroup.class).add(
						Restrictions.eq("userGroup_ID", new Integer(groupId)));
				UserGroup g = (UserGroup) cri.list().get(0);
				g.setDescription(obj.getDescription());
				g.setDescription2(obj.getDescription2());
				g.setBsoLimit(obj.getBsoLimit());
				g.setLuvLimit(obj.getLuvLimit());
				sess.saveOrUpdate(g);
			}
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (t != null) {
				t.rollback();
			}
			throw e;
		} finally {
			if (sess != null) sess.close();
		}
	}

	/**
	 * 
	 * @param cl
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List retrieveAll(Class cl) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(cl);
			return cri.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * saveWTCarriers - saves a list of WTCompany objects
	 * @param wtCompanyList - List of WTCompany Objects to be saved
	 */
	public static void saveWTCarriers(List<WTCompany> wtCompanyList) {

			Session sess = null;
			Transaction tx=null;
			try {
				sess = HibernateWrapper.getSession().openSession();
				tx = sess.beginTransaction();
				for(WTCompany wtc:wtCompanyList)
					sess.saveOrUpdate(wtc);
				tx.commit();
				sess.flush();

			} catch (Exception e) {
				e.printStackTrace();
				if (tx != null) {
					tx.rollback();
				}
			} finally {
				if (sess != null) {
					try {
						sess.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		
	}

}