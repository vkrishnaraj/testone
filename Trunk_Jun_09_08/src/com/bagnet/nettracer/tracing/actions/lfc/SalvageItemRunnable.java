package com.bagnet.nettracer.tracing.actions.lfc;

import java.util.Calendar;

import aero.nettracer.lf.services.LFServiceWrapper;
import aero.nettracer.lf.services.exception.NonUniqueBarcodeException;
import aero.nettracer.lf.services.exception.UpdateException;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.lf.SubCompanyDAO;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.db.lf.Subcompany;

public class SalvageItemRunnable implements Runnable {

	@Override
	public void run() {
		SalvageItemContainer sic;
		
		while (true) {
			try {
				sic = SalvageManager.getQueue().take();
				LFFound found = sic.getFound();
			
				if (found == null) {
					continue;
				}
				
				// make sure the item isn't already associated with a salvage
				if (sic.getFound().getSalvage() != null) {
					continue;
				}
				
				Calendar salvageCutoff = Calendar.getInstance();
				salvageCutoff.setTimeInMillis(System.currentTimeMillis());
	
				int salvageDays;

				Subcompany subcomp=SubCompanyDAO.loadSubcompany(found.getCompanyId());
				if (found.getItem().getValue() == TracingConstants.LFC_ITEM_HIGH_VALUE) {
					if(subcomp!=null){
						salvageDays = subcomp.getSalvage_High();
					} else {
						salvageDays = PropertyBMO.getValueAsInt("lf.high.value.salvage.days");
					}
				} else {
					if(subcomp!=null){
						salvageDays = subcomp.getSalvage_Low();
					} else {
						salvageDays = PropertyBMO.getValueAsInt("lf.low.value.salvage.days");
					}
				}
				
				salvageCutoff.add(Calendar.DAY_OF_MONTH, (-1 * salvageDays));
				
				Calendar rxDate = Calendar.getInstance();
				rxDate.setTime(sic.getFound().getReceivedDate());
				
				// the found item was received less than 60 days ago so it can't be salvaged
				if (salvageCutoff.before(rxDate)) {
					continue;
				}
				
				int status;
				int disposition;
				int location;
				if (sic.isAddItem()) {
					status = TracingConstants.LF_STATUS_CLOSED;
					disposition = TracingConstants.LF_DISPOSITION_SALVAGED;
					location = TracingConstants.LF_LOCATION_SALVAGED;
				} else {
					status = TracingConstants.LF_STATUS_OPEN;
					if (found.getItem().getDeliveryRejected() || found.getItem().getLost() != null) {
						disposition = TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED;
					} else {
						disposition = TracingConstants.LF_DISPOSITION_OTHER;
					}
					location = TracingConstants.LF_LOCATION_SALVAGED;
				}
				
				found.setStatusId(status);
				found.getItem().setDispositionId(disposition);
				found.setItemLocation(location);
				found.setSalvage(sic.getSalvage());
				
				if (found.getItem().getLost() != null) {
					LFLost lost = found.getItem().getLost();
					lost.setStatusId(status);
					lost.getItem().setDispositionId(disposition);
				LFServiceWrapper.getInstance().saveOrUpdateLostReport(lost, sic.getAgent());
				}
				
				LFServiceWrapper.getInstance().saveOrUpdateFoundItem(found, sic.getAgent());
				
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			} catch (NonUniqueBarcodeException nube) {
				nube.printStackTrace();
			} catch (UpdateException ue) {
				ue.printStackTrace();
			}
		
		}
		
	}
	
}
