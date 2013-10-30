/*
 * Created on Aug 10, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.reporting;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;

import aero.nettracer.fs.model.Person;

import com.bagnet.nettracer.tracing.bmo.ClaimBMO;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.bmo.StatusBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Claim_Depreciation;
import com.bagnet.nettracer.tracing.db.Depreciation_Item;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.Prorate_Itinerary;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.forms.ClaimForm;
import com.bagnet.nettracer.tracing.utils.BagService;

/**
 * @author Sean Fine
 * 
 * Report Class to get the CRAPReport.jrxml and fill in the necessary information
 */
public class CRAPReport {
	private static Logger logger = Logger.getLogger(CRAPReport.class);

	/**
	 *  
	 */
	public CRAPReport() {
		super();
	}

	public static String createReport(ClaimForm theform, ServletContext sc, HttpServletRequest request, int outputtype, String language) {
		ClaimBMO cbmo=new ClaimBMO();
		BagService bs = new BagService();
		Agent user=theform.getClaim().getCreateagent();
		try{
			Map<String, Object> parameters = new HashMap();

			ResourceBundle myResources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user
					.getCurrentlocale()));

			parameters.put("REPORT_RESOURCE_BUNDLE", myResources);

			File logo = new File(sc.getRealPath("/") + "reports/logo.jpg");
			if (logo.exists()) {
				parameters.put("logo", logo.getAbsolutePath());
			}
			
			HashMap<String, Comparable> report_info=new HashMap();
			report_info.put("claim_id", theform.getClaim().getId());
			/**
			 * TODO: determine the values of CIM Match, Date, Airline, and Other in the Last/Delay Section of Sheet
			 * Make sure to confirm with Byron that the information displayed is accurate
			 */
			if(theform.getClaim()!=null && theform.getClaim().getNtIncident()!=null){
				report_info.put("numPax",String.valueOf(theform.getClaim().getNtIncident().getNumpassengers()));
				report_info.put("numBagCK",String.valueOf(theform.getClaim().getNtIncident().getNumbagchecked()));
				report_info.put("numBagRCV",String.valueOf(theform.getClaim().getNtIncident().getNumbagreceived()));
				report_info.put("cimMatch", ""); //What is cim match?
				report_info.put("airline", theform.getClaim().getNtIncident().getStationcreated().getCompany().getCompanyCode_ID());
				report_info.put("date",theform.getClaim().getIncident().getDisOpenDate(user.getDateformat().getFormat()));
				report_info.put("reportNum",  theform.getClaim().getNtIncidentId());
				report_info.put("courtesy", theform.getClaim().getNtIncident().getCourtesyreport()==0?"No":"Yes");
				Status reason=StatusBMO.getStatus(theform.getClaim().getNtIncident().getCourtesyReasonId());
				if(reason!=null){
					report_info.put("reason", reason.getDescription());
				}
				for(ExpensePayout ep:theform.getClaim().getNtIncident().getExpenselist()){
					/**
					 * TODO: Calculate Station Paid(sum total of all dollar figures issues by non CBS stations), 
					 * Gift Cert (Byron to follow up with Southwest), 
					 * Station SLV (all approved/paid vouchers issued by stations), 
					 * CBS SLV (all approved/paid vouchers issued by CBS)
					 */
					
				}
			}
			report_info.put("claimcheck", theform.getClaim().getClaimCheck());
			report_info.put("totalLiability", "$"+TracingConstants.DECIMALFORMAT.format(theform.getClaim().getTotalLiability()));
			report_info.put("excessValueAmt", "$"+TracingConstants.DECIMALFORMAT.format(theform.getClaim().getExcessValueAmt()));
			report_info.put("claimAmount", theform.getClaim().getAmountClaimed() +" "+theform.getClaim().getAmountClaimedCurrency());
			report_info.put("remark", theform.getClaim().getClaimRemark());
			if(theform.getClaim().getCreateagent()!=null){
				report_info.put("cbsrep", theform.getClaim().getCreateagent().getUsername());
			}
			if(theform.getClaim().getClaimant()!=null){
				report_info.put("claimant1first", theform.getClaimant().getFirstName());
				report_info.put("claimant1last", theform.getClaimant().getLastName());
			}
			
			if(theform.getClaim().getClaimants()!=null && theform.getClaim().getClaimants().size()>1){
				int claimantCount=1;
				for(Person claimant:theform.getClaim().getClaimants()){
					claimantCount++;
					report_info.put("claimant"+claimantCount+"first",claimant.getFirstName());
					report_info.put("claimant"+claimantCount+"last",claimant.getLastName());
					if(claimantCount>=3){
						break;
					}
				}
			}
			
			if(theform.getClaim().getClaimprorate()!=null && theform.getClaim().getClaimprorate().getProrate_itineraries()!=null){
				List prorations=new ArrayList();
				for(Object obj:theform.getClaim().getClaimprorate().getProrate_itineraries()){
					Prorate_Itinerary pi=(Prorate_Itinerary)obj;
					if(pi.getFlightnum()!=null && !pi.getFlightnum().isEmpty() && pi.getDepartdate()!=null){
						prorations.add(pi);
					}
				}
				parameters.put("prorations", new JRBeanCollectionDataSource(prorations));
				parameters.put("prorationReport", ReportBMO.getCompiledReport("proratereport", sc.getRealPath("/")));
			}
			
			Claim_Depreciation cd=cbmo.getClaimDeprec(theform.getClaim().getId());
			double totalclaimed=0;
			double stationPaid=0;
			double depreciation=0;
			double exclusion=0;
			double adjClaim=0;
			if(cd!=null){
				if(cd.getItemlist()!=null && cd.getItemlist().size()>0){
					List depreciations=new ArrayList();
					List exclusions=new ArrayList();
					for(Depreciation_Item di:cd.getItemlist()){
						if(di.isNotCoveredCoc()){
							exclusion+=di.getAmountClaimed();
							exclusions.add(di);
						} else {
							depreciation+=(di.getAmountClaimed()-di.getClaimValue());
							depreciations.add(di);
						}
					}
					parameters.put("depreciations", new JRBeanCollectionDataSource(depreciations));
					parameters.put("exclusions", new JRBeanCollectionDataSource(exclusions));
					parameters.put("depreciationReport", ReportBMO.getCompiledReport("deprecReport", sc.getRealPath("/")));
					parameters.put("exclReport", ReportBMO.getCompiledReport("exclReport", sc.getRealPath("/")));
				}
				totalclaimed=cd.getTotalClaim();
				adjClaim=cd.getTotalApprovedPayout();
			}
			report_info.put("totalclaimed", "$"+TracingConstants.DECIMALFORMAT.format(totalclaimed));
			report_info.put("stationpaid", "$"+TracingConstants.DECIMALFORMAT.format(stationPaid));
			report_info.put("exclusion", "$"+TracingConstants.DECIMALFORMAT.format(exclusion)); //what determines what's excluded
			report_info.put("depreciation", "$"+TracingConstants.DECIMALFORMAT.format(depreciation)); //what determines what's depreciated
			report_info.put("adjclaim", "$"+TracingConstants.DECIMALFORMAT.format(adjClaim));
			String crapTitle="Lost";
			if(theform.getClaim().isIx()){
				crapTitle+=" IX";
			}
			if(theform.getClaim().isCarryon()){
				crapTitle+=" Carry-On";
			}
			report_info.put("crapTitle",crapTitle);
			parameters.put("report_info", report_info);
			
			
			List t = new ArrayList();
			t.add("");
			ReportBMO bmo = new ReportBMO(request);
			return bmo.getReportFile(t, parameters, "crap_report", sc.getRealPath("/"), outputtype);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}