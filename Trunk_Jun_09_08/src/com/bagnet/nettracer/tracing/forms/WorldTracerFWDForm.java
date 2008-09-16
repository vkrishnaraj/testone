package com.bagnet.nettracer.tracing.forms;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.apache.struts.validator.ValidatorForm;

import com.bagnet.nettracer.tracing.db.WT_FWD_Log_Itinerary;
import com.bagnet.nettracer.tracing.db.wtq.WtqSegment;

public class WorldTracerFWDForm extends WorldTracerFOHForm {

	private int loss_code;
	private String reason_for_loss;
	private String fault_station;
	private String fault_terminal;

	/**
	 * @return the loss_code
	 */
	public int getLoss_code() {
		return loss_code;
	}

	/**
	 * @param loss_code the loss_code to set
	 */
	public void setLoss_code(int loss_code) {
		this.loss_code = loss_code;
	}

	/**
	 * @return the reason_for_loss
	 */
	public String getReason_for_loss() {
		return reason_for_loss;
	}

	/**
	 * @param reason_for_los the reason_for_loss to set
	 */
	public void setReason_for_loss(String reason_for_loss) {
		this.reason_for_loss = reason_for_loss;
	}

	/**
	 * @return the fault_station
	 */
	public String getFault_station() {
		return fault_station;
	}

	/**
	 * @param fault_station the fault_station to set
	 */
	public void setFault_station(String fault_station) {
		this.fault_station = fault_station;
	}

	/**
	 * @return the fault_terminal
	 */
	public String getFault_terminal() {
		return fault_terminal;
	}

	/**
	 * @param fault_terminal the fault_terminal to set
	 */
	public void setFault_terminal(String fault_terminal) {
		this.fault_terminal = fault_terminal;
	}
}
