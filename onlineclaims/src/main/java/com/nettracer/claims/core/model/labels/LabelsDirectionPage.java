package com.nettracer.claims.core.model.labels;

import java.util.List;

import com.nettracer.claims.core.model.Label;
import com.nettracer.claims.core.model.Localetext;

public class LabelsDirectionPage {

	// Direction
	public static final String DIR_DESC = "Descriptive text";
	public static final String DIR_FORWARD = "Forward";

	private LabelText descText;
	private LabelText forward;

	public LabelText getDescText() {
		return descText;
	}

	public void setDescText(LabelText descText) {
		this.descText = descText;
	}

	public LabelText getForward() {
		return forward;
	}

	public void setForward(LabelText forward) {
		this.forward = forward;
	}
	
	public LabelsDirectionPage(List<Localetext> localetextList, Long baggageState) {

		for (Localetext localetext : localetextList) {
		
			Label currentLabel = localetext.getLabel();
			
			Long requiredFieldStatus = baggageState == 1L ? currentLabel.getDelayedState()
					: baggageState == 2L ? currentLabel.getPilferedState() 
							: currentLabel.getDamagedState();
					
			if (currentLabel.getLabel().equals(DIR_DESC)) {
				setDescText(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(DIR_FORWARD)) {
				setForward(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			}
			
		}
	}

}
