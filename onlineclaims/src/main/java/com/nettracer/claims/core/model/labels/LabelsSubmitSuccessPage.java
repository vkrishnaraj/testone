package com.nettracer.claims.core.model.labels;

import java.util.List;

import com.nettracer.claims.core.model.Label;
import com.nettracer.claims.core.model.Localetext;

public class LabelsSubmitSuccessPage {

	// Saved Screen
	public static final String SAV_SAVED = "savedMessage";
	public static final String SAV_RETURN = "returnLink";
	public static final String SAV_HELP = "savedScreenHelp";

	// For saved Screen
	private LabelText savedMessage;
	private LabelText returnLink;
	private LabelText help;

	public LabelText getSavedMessage() {
		return savedMessage;
	}

	public void setSavedMessage(LabelText savedMessage) {
		this.savedMessage = savedMessage;
	}

	public LabelText getReturnLink() {
		return returnLink;
	}

	public void setReturnLink(LabelText returnLink) {
		this.returnLink = returnLink;
	}

	public LabelText getHelp() {
		return help;
	}

	public void setHelp(LabelText help) {
		this.help = help;
	}
	
	public LabelsSubmitSuccessPage(List<Localetext> localetextList, Long baggageState) {

		for (Localetext localetext : localetextList) {
		
			Label currentLabel = localetext.getLabel();
			
			Long requiredFieldStatus = baggageState == 1L ? currentLabel.getDelayedState()
					: baggageState == 2L ? currentLabel.getPilferedState() 
							: currentLabel.getDamagedState();
					
			if (currentLabel.getLabel().equals(SAV_HELP)) {
				setHelp(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(SAV_RETURN)) {
				setReturnLink(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(SAV_SAVED)) {
				setSavedMessage(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			}
			
		}
	}

}
