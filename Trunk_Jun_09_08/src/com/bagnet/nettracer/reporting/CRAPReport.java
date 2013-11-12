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

import aero.nettracer.fs.model.Person;

import com.bagnet.nettracer.tracing.bmo.ClaimBMO;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.bmo.StatusBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Claim_Depreciation;
import com.bagnet.nettracer.tracing.db.Depreciation_Item;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.Prorate_Itinerary;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.forms.ClaimForm;

/**
 * @author Sean Fine
 * 
 * Report Class to get the CRAPReport.jrxml and fill in the necessary information
 */
public class CRAPReport {

	public CRAPReport() {
		super();
	}

	public static String createReport(ClaimForm theform, ServletContext sc, HttpServletRequest request, int outputtype, String language) {
		ClaimBMO cbmo=new ClaimBMO();
		Agent user=theform.getClaim().getCreateagent();
		try{
			Map<String, Object> parameters = new HashMap<String, Object>();

			ResourceBundle myResources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user
					.getCurrentlocale()));

			parameters.put("REPORT_RESOURCE_BUNDLE", myResources);

			File logo = new File(sc.getRealPath("/") + "reports/logo.jpg");
			if (logo.exists()) {
				parameters.put("logo", logo.getAbsolutePath());
			}
			
			HashMap<String, String> report_info=new HashMap<String, String>();
			report_info.put("claim_id", String.valueOf(theform.getClaim().getId()));
			
			double stationPaid=0;
			double cbsslv=0;
			double stationslv=0;
			
			if(theform.getClaim()!=null && theform.getClaim().getNtIncident()!=null){
				report_info.put("numPax",String.valueOf(theform.getClaim().getNtIncident().getNumpassengers()));
				report_info.put("numBagCK",String.valueOf(theform.getClaim().getNtIncident().getNumbagchecked()));
				report_info.put("numBagRCV",String.valueOf(theform.getClaim().getNtIncident().getNumbagreceived()));
				report_info.put("airline", theform.getClaim().getNtIncident().getStationcreated().getCompany().getCompanyCode_ID());
				report_info.put("date",theform.getClaim().getIncident().getDisOpenDate(user.getDateformat().getFormat()));
				report_info.put("reportNum",  theform.getClaim().getNtIncidentId());
				report_info.put("courtesy", theform.getClaim().getNtIncident().getCourtesyreport()==0?"No":"Yes");
				if(theform.getClaim().getCreateagent()!=null){
					report_info.put("cbsrep", theform.getClaim().getCreateagent().getUsername());
				}
				Status reason=StatusBMO.getStatus(theform.getClaim().getNtIncident().getCourtesyReasonId());
				if(reason!=null){
					report_info.put("reason", reason.getDescription());
				}
				
				Station cbsStation=StationBMO.getStationByCode(TracingConstants.CBS, user.getCompanycode_ID());
				for(ExpensePayout ep:theform.getClaim().getNtIncident().getExpenselist()){
					boolean epApproved=ep.getStatus().getStatus_ID()==TracingConstants.EXPENSEPAYOUT_STATUS_APPROVED;
					boolean epDelivered=ep.getExpensetype().getExpensetype_ID()==TracingConstants.EXPENSEPAYOUT_DELIVERY;
					boolean epPaid=ep.getStatus().getStatus_ID()==TracingConstants.EXPENSEPAYOUT_STATUS_PAID;
					if(epApproved || epPaid){
						if(cbsStation!=null && ep.getExpenselocation().getStation_ID()==cbsStation.getStation_ID()){
							cbsslv+=ep.getVoucheramt();
						} else {
							if(ep.getVoucheramt()>0){
								stationslv+=ep.getVoucheramt();
							} else if(!epDelivered){
								stationPaid+=ep.getCheckamt()+ep.getCreditCardRefund();
							}
						}
					}
				}
			}
			report_info.put("claimStatus", theform.getClaim().getStatus().getDescription());
			report_info.put("claimcheck", theform.getClaim().getClaimCheck());
			report_info.put("totalLiability", "$"+TracingConstants.DECIMALFORMAT.format(theform.getClaim().getTotalLiability()));
			report_info.put("excessValueAmt", "$"+TracingConstants.DECIMALFORMAT.format(theform.getClaim().getExcessValueAmt()));
			
			if(theform.getClaim().getAmountClaimedCurrency().equals(TracingConstants.USD_CURRENCY_CODE)){
				report_info.put("claimAmount", "$"+TracingConstants.DECIMALFORMAT.format(theform.getClaim().getAmountClaimed()));
			} else {
				report_info.put("claimAmount", theform.getClaim().getAmountClaimed() +" "+theform.getClaim().getAmountClaimedCurrency());
			}
			
			report_info.put("remark", theform.getClaim().getClaimRemark());
			
			if(theform.getClaim().getClaimants()!=null && theform.getClaim().getClaimants().size()>0){
				int claimantCount=0;
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
				List<Prorate_Itinerary> prorations=new ArrayList<Prorate_Itinerary>();
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
			double depreciation=0;
			double exclusion=0;
			double adjClaim=0;
			if(cd!=null){
				if(cd.getItemlist()!=null && cd.getItemlist().size()>0){
					List<Depreciation_Item> depreciations=new ArrayList<Depreciation_Item>();
					List<Depreciation_Item> exclusions=new ArrayList<Depreciation_Item>();
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
				adjClaim=theform.getClaim().getAmountClaimed()-stationPaid-exclusion-depreciation;
			}
			report_info.put("stationpaid", "$"+TracingConstants.DECIMALFORMAT.format(stationPaid));
			report_info.put("cbsslv", "$"+TracingConstants.DECIMALFORMAT.format(cbsslv));
			report_info.put("stationslv", "$"+TracingConstants.DECIMALFORMAT.format(stationslv));
			report_info.put("exclusion", "$"+TracingConstants.DECIMALFORMAT.format(exclusion));
			report_info.put("depreciation", "$"+TracingConstants.DECIMALFORMAT.format(depreciation));
			report_info.put("adjclaim", "$"+TracingConstants.DECIMALFORMAT.format(adjClaim));
			
			/* Logic for the Title */
			String crapTitle="Lost";
			if(theform.getClaim().isIx()){
				crapTitle+=" IX";
			}
			if(theform.getClaim().isCarryon()){
				crapTitle+=" Carry-On";
			}
			report_info.put("crapTitle",crapTitle);
			parameters.put("report_info", report_info);
			
			/* Required to populate the report */
			List<String> t = new ArrayList<String>();
			t.add("");
			ReportBMO bmo = new ReportBMO(request);
			return bmo.getReportFile(t, parameters, "crap_report", sc.getRealPath("/"), outputtype);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}