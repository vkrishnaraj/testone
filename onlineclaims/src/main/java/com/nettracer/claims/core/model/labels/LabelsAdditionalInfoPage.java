package com.nettracer.claims.core.model.labels;

import java.util.List;

import com.nettracer.claims.core.model.Label;
import com.nettracer.claims.core.model.Localetext;

public class LabelsAdditionalInfoPage {

	// Fraud Question
	public static final String ADD_DESC = "fraudQuestionDescriptiveText";
	public static final String ADD_MORECLAIM = "moreClaim";
	public static final String ADD_WHICHAIR = "whichAirline";
	public static final String ADD_DATE = "dateOfClaim";
	public static final String ADD_NAME = "claimantName";
	public static final String ADD_TSA = "TSA information (Missing Article Only)";
	public static final String ADD_TSAINSPECT = "Did the TSA inspect your bag(s)";
	public static final String ADD_NOTE = "Was a note inserted in the bag confirming the bag was searched";
	public static final String ADD_WHEREINSPECT = "Where did the inspection take place:";
	public static final String ADD_COMMENTS = "Additional Comments:";
	public static final String ADD_REQMESS = "fraudQuestionRequiredFieldMessage";
	public static final String ADD_HELP = "fraudQuestionHelp";
	public static final String ADD_ANOTHCLAIM = "anotherClaim";
	public static final String ADD_YESABOVE = "yesAbove";

	// For Fraud Question -- Step 5 of 6
	private LabelText descriptiveText;
	private LabelText moreClaim;
	private LabelText whichAirline;
	private LabelText dateOfClaim;
	private LabelText claimantName;
	private LabelText tsaInfo;
	private LabelText tsaInspect;
	private LabelText bagConfirmNote;
	private LabelText inspectionPlace;
	private LabelText additionalComments;
	private LabelText requiredFieldMessage;
	private LabelText help;
	private LabelText anotherClaim;
	private LabelText yesAbove;

	public LabelText getDescriptiveText() {
		return descriptiveText;
	}

	public void setDescriptiveText(LabelText descriptiveText) {
		this.descriptiveText = descriptiveText;
	}

	public LabelText getMoreClaim() {
		return moreClaim;
	}

	public void setMoreClaim(LabelText moreClaim) {
		this.moreClaim = moreClaim;
	}

	public LabelText getWhichAirline() {
		return whichAirline;
	}

	public void setWhichAirline(LabelText whichAirline) {
		this.whichAirline = whichAirline;
	}

	public LabelText getDateOfClaim() {
		return dateOfClaim;
	}

	public void setDateOfClaim(LabelText dateOfClaim) {
		this.dateOfClaim = dateOfClaim;
	}

	public LabelText getClaimantName() {
		return claimantName;
	}

	public void setClaimantName(LabelText claimantName) {
		this.claimantName = claimantName;
	}

	public LabelText getTsaInfo() {
		return tsaInfo;
	}

	public void setTsaInfo(LabelText tsaInfo) {
		this.tsaInfo = tsaInfo;
	}

	public LabelText getTsaInspect() {
		return tsaInspect;
	}

	public void setTsaInspect(LabelText tsaInspect) {
		this.tsaInspect = tsaInspect;
	}

	public LabelText getBagConfirmNote() {
		return bagConfirmNote;
	}

	public void setBagConfirmNote(LabelText bagConfirmNote) {
		this.bagConfirmNote = bagConfirmNote;
	}

	public LabelText getInspectionPlace() {
		return inspectionPlace;
	}

	public void setInspectionPlace(LabelText inspectionPlace) {
		this.inspectionPlace = inspectionPlace;
	}

	public LabelText getAdditionalComments() {
		return additionalComments;
	}

	public void setAdditionalComments(LabelText additionalComments) {
		this.additionalComments = additionalComments;
	}

	public LabelText getRequiredFieldMessage() {
		return requiredFieldMessage;
	}

	public void setRequiredFieldMessage(LabelText requiredFieldMessage) {
		this.requiredFieldMessage = requiredFieldMessage;
	}

	public LabelText getHelp() {
		return help;
	}

	public void setHelp(LabelText help) {
		this.help = help;
	}

	public LabelText getAnotherClaim() {
		return anotherClaim;
	}

	public void setAnotherClaim(LabelText anotherClaim) {
		this.anotherClaim = anotherClaim;
	}

	public LabelText getYesAbove() {
		return yesAbove;
	}

	public void setYesAbove(LabelText yesAbove) {
		this.yesAbove = yesAbove;
	}
	
	public LabelsAdditionalInfoPage(List<Localetext> localetextList, Long baggageState) {

		for (Localetext localetext : localetextList) {
		
			Label currentLabel = localetext.getLabel();
			
			Long requiredFieldStatus = baggageState == 1L ? currentLabel.getDelayedState()
					: baggageState == 2L ? currentLabel.getPilferedState() 
							: currentLabel.getDamagedState();
					
			if (currentLabel.getLabel().equals(ADD_DESC)) {
				setDescriptiveText(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(ADD_ANOTHCLAIM)) {
				setAnotherClaim(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(ADD_COMMENTS)) {
				setAdditionalComments(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(ADD_DATE)) {
				setDateOfClaim(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(ADD_HELP)) {
				setHelp(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(ADD_MORECLAIM)) {
				setMoreClaim(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(ADD_NAME)) {
				setClaimantName(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(ADD_NOTE)) {
				setBagConfirmNote(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(ADD_REQMESS)) {
				setRequiredFieldMessage(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(ADD_TSA)) {
				setTsaInfo(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(ADD_TSAINSPECT)) {
				setTsaInspect(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(ADD_WHEREINSPECT)) {
				setInspectionPlace(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(ADD_WHICHAIR)) {
				setWhichAirline(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(ADD_YESABOVE)) {
				setYesAbove(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			}
			
		}
	}

}
