package com.nettracer.claims.core.model.labels;

import java.util.List;

import com.nettracer.claims.core.model.Label;
import com.nettracer.claims.core.model.Localetext;

public class LabelsGeneralApplication {

	// General
	public static final String GEN_SAVE = "Save";
	public static final String GEN_PREV = "Previous";
	public static final String GEN_FORW = "Forward";
	public static final String GEN_DESC = "Descriptive text";

	private LabelText save;
	private LabelText previous;
	private LabelText forward;
	private LabelText descText;

	public LabelText getSave() {
		return save;
	}

	public void setSave(LabelText save) {
		this.save = save;
	}

	public LabelText getPrevious() {
		return previous;
	}

	public void setPrevious(LabelText previous) {
		this.previous = previous;
	}

	public LabelText getForward() {
		return forward;
	}

	public void setForward(LabelText forward) {
		this.forward = forward;
	}

	public LabelText getDescText() {
		return descText;
	}

	public void setDescText(LabelText descText) {
		this.descText = descText;
	}
	
	public LabelsGeneralApplication(List<Localetext> localetextList, Long baggageState) {

		for (Localetext localetext : localetextList) {
		
			Label currentLabel = localetext.getLabel();
			
			Long requiredFieldStatus = baggageState == 1L ? currentLabel.getDelayedState()
					: baggageState == 2L ? currentLabel.getPilferedState() 
							: currentLabel.getDamagedState();
					
			if (currentLabel.getLabel().equals(GEN_DESC)) {
				setDescText(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(GEN_FORW)) {
				setForward(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(GEN_PREV)) {
				setPrevious(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(GEN_SAVE)) {
				setSave(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			}
			
		}
	}

}
