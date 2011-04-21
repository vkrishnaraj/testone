package com.nettracer.claims.core.model.labels;

import java.util.List;

import com.nettracer.claims.core.model.Label;
import com.nettracer.claims.core.model.Localetext;

public class LabelsSubmitClaimPage {

	// Submit Claim
	public static final String SUB_DESC = "submitClaimDescriptiveText";
	public static final String SUB_HELP = "submitClaimHelp";
	public static final String SUB_REQMESS = "submitClaimRequiredFieldMessage";
	public static final String SUB_USEINFO = "useOfInformation";
	public static final String SUB_RIGHTS = "reservedRights";
	public static final String SUB_ACCEPT = "typeAccept";
	public static final String SUB_CONFIRM = "confirmation";
	public static final String SUB_LOSTDATE = "luggageLostDate";
	public static final String SUB_SIGNATURE = "signature";
	public static final String SUB_SIGNED = "signedDate";
	public static final String SUB_PRINT = "print";
	public static final String SUB_AGREE = "agreement";
	public static final String SUB_SUBMIT = "claimSubmit";

	// For Claim Submission -step 6
	private LabelText descriptiveText;
	private LabelText help;
	private LabelText requiredFieldMessage;
	private LabelText useOfInformation;
	private LabelText reservedRights;
	private LabelText typeAccept;
	private LabelText confirmation;
	private LabelText luggageLostDate;
	private LabelText signature;
	private LabelText signedDate;
	private LabelText print;
	private LabelText agreement;
	private LabelText claimSubmit;

	public LabelText getDescriptiveText() {
		return descriptiveText;
	}

	public void setDescriptiveText(LabelText descriptiveText) {
		this.descriptiveText = descriptiveText;
	}

	public LabelText getHelp() {
		return help;
	}

	public void setHelp(LabelText help) {
		this.help = help;
	}

	public LabelText getRequiredFieldMessage() {
		return requiredFieldMessage;
	}

	public void setRequiredFieldMessage(LabelText requiredFieldMessage) {
		this.requiredFieldMessage = requiredFieldMessage;
	}

	public LabelText getUseOfInformation() {
		return useOfInformation;
	}

	public void setUseOfInformation(LabelText useOfInformation) {
		this.useOfInformation = useOfInformation;
	}

	public LabelText getReservedRights() {
		return reservedRights;
	}

	public void setReservedRights(LabelText reservedRights) {
		this.reservedRights = reservedRights;
	}

	public LabelText getTypeAccept() {
		return typeAccept;
	}

	public void setTypeAccept(LabelText typeAccept) {
		this.typeAccept = typeAccept;
	}

	public LabelText getConfirmation() {
		return confirmation;
	}

	public void setConfirmation(LabelText confirmation) {
		this.confirmation = confirmation;
	}

	public LabelText getLuggageLostDate() {
		return luggageLostDate;
	}

	public void setLuggageLostDate(LabelText luggageLostDate) {
		this.luggageLostDate = luggageLostDate;
	}

	public LabelText getSignature() {
		return signature;
	}

	public void setSignature(LabelText signature) {
		this.signature = signature;
	}

	public LabelText getSignedDate() {
		return signedDate;
	}

	public void setSignedDate(LabelText signedDate) {
		this.signedDate = signedDate;
	}

	public LabelText getPrint() {
		return print;
	}

	public void setPrint(LabelText print) {
		this.print = print;
	}

	public LabelText getAgreement() {
		return agreement;
	}

	public void setAgreement(LabelText agreement) {
		this.agreement = agreement;
	}

	public LabelText getClaimSubmit() {
		return claimSubmit;
	}

	public void setClaimSubmit(LabelText claimSubmit) {
		this.claimSubmit = claimSubmit;
	}
	
	public LabelsSubmitClaimPage(List<Localetext> localetextList, Long baggageState) {

		for (Localetext localetext : localetextList) {
		
			Label currentLabel = localetext.getLabel();
			
			Long requiredFieldStatus = baggageState == 1L ? currentLabel.getDelayedState()
					: baggageState == 2L ? currentLabel.getPilferedState() 
							: currentLabel.getDamagedState();
					
			if (currentLabel.getLabel().equals(SUB_ACCEPT)) {
				setTypeAccept(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(SUB_AGREE)) {
				setAgreement(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(SUB_CONFIRM)) {
				setConfirmation(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(SUB_DESC)) {
				setDescriptiveText(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(SUB_HELP)) {
				setHelp(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(SUB_LOSTDATE)) {
				setLuggageLostDate(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(SUB_PRINT)) {
				setPrint(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(SUB_REQMESS)) {
				setRequiredFieldMessage(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(SUB_RIGHTS)) {
				setReservedRights(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(SUB_SIGNATURE)) {
				setSignature(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(SUB_SIGNED)) {
				setSignedDate(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(SUB_SUBMIT)) {
				setClaimSubmit(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(SUB_USEINFO)) {
				setUseOfInformation(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			}
			
		}
	}

}
