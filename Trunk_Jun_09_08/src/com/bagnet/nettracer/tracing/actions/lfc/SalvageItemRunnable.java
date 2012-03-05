package com.bagnet.nettracer.tracing.actions.lfc;

import java.util.Calendar;

import aero.nettracer.lf.services.LFServiceBean;
import aero.nettracer.lf.services.exception.NonUniqueBarcodeException;
import aero.nettracer.lf.services.exception.UpdateException;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.db.lf.LFSalvage;

public class SalvageItemRunnable implements Runnable {

	private LFSalvage salvage;
	private LFFound found;
	private Agent user;
	private boolean addItem;
	
	public SalvageItemRunnable(LFSalvage salvage, LFFound found, Agent user, boolean addItem) {
		this.salvage = salvage;
		this.found = found;
		this.user = user;
		this.addItem = addItem;
	}

	@Override
	public void run() {
		LFServiceBean serviceBean = new LFServiceBean();
		try {
			if (found == null) {
				return;
			}
			
			// make sure the item isn't already associated with a salvage
			if (found.getSalvage() != null) {
				return;
			}
			
			Calendar salvageCutoff = Calendar.getInstance();
			salvageCutoff.setTimeInMillis(System.currentTimeMillis());

			int salvageDays;
			if (found.getItem().getValue() == TracingConstants.LFC_ITEM_HIGH_VALUE) {
				salvageDays = PropertyBMO.getValueAsInt("lf.high.value.salvage.days");
			} else {
				salvageDays = PropertyBMO.getValueAsInt("lf.low.value.salvage.days");
			}
			salvageCutoff.add(Calendar.DAY_OF_MONTH, (-1 * salvageDays));
			
			Calendar rxDate = Calendar.getInstance();
			rxDate.setTime(found.getReceivedDate());
			
			// the found item was received less than 60 days ago so it can't be salvaged
			if (salvageCutoff.before(rxDate)) {
				return;
			}
			
			int status;
			int disposition;
			int location;
			if (addItem) {
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
			found.setSalvage(salvage);
			
			if (found.getItem().getLost() != null) {
				LFLost lost = found.getItem().getLost();
				lost.setStatusId(status);
				lost.getItem().setDispositionId(disposition);
				serviceBean.saveOrUpdateLostReport(lost, user);
			}
			
			serviceBean.saveOrUpdateFoundItem(found, user);
			
		} catch (NonUniqueBarcodeException nube) {
			nube.printStackTrace();
		} catch (UpdateException ue) {
			ue.printStackTrace();
		}
		
	}
	
}
