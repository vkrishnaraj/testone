package com.nettracer.claims.core.model.labels;

import java.util.List;

import com.nettracer.claims.core.model.Label;
import com.nettracer.claims.core.model.Localetext;

public class LabelsLoginPage {

	// Passenger Login
	public static final String LOG_CLAIM = "Claim Number";
	public static final String LOG_LASTN = "Last Name";
	public static final String LOG_DIFF = "Try a different image";
	public static final String LOG_CODE = "Type the code shown ";
	public static final String LOG_CONTINUE = "Continue";
	public static final String LOG_VALUENOGREATER = "Value Can't be greater than";

	// Labels For Passenger Login Page --not in use yet
	private LabelText claimNumber;
	private LabelText lastName;
	private LabelText tryDiffImage;
	private LabelText captchaText;
	private LabelText continueButton;
	private LabelText validateLength;

	public LabelText getClaimNumber() {
		return claimNumber;
	}

	public void setClaimNumber(LabelText claimNumber) {
		this.claimNumber = claimNumber;
	}

	public LabelText getLastName() {
		return lastName;
	}

	public void setLastName(LabelText lastName) {
		this.lastName = lastName;
	}

	public LabelText getTryDiffImage() {
		return tryDiffImage;
	}

	public void setTryDiffImage(LabelText tryDiffImage) {
		this.tryDiffImage = tryDiffImage;
	}

	public LabelText getCaptchaText() {
		return captchaText;
	}

	public void setCaptchaText(LabelText captchaText) {
		this.captchaText = captchaText;
	}

	public LabelText getContinueButton() {
		return continueButton;
	}

	public void setContinueButton(LabelText continueButton) {
		this.continueButton = continueButton;
	}

	public LabelText getValidateLength() {
		return validateLength;
	}

	public void setValidateLength(LabelText validateLength) {
		this.validateLength = validateLength;
	}
	
	public LabelsLoginPage(List<Localetext> localetextList, Long baggageState) {

		for (Localetext localetext : localetextList) {
		
			Label currentLabel = localetext.getLabel();
			
			Long requiredFieldStatus = baggageState == 1L ? currentLabel.getDelayedState()
					: baggageState == 2L ? currentLabel.getPilferedState() 
							: currentLabel.getDamagedState();
					
			if (currentLabel.getLabel().equals(LOG_CLAIM)) {
				setClaimNumber(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(LOG_CODE)) {
				setCaptchaText(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(LOG_CONTINUE)) {
				setContinueButton(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(LOG_DIFF)) {
				setTryDiffImage(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(LOG_LASTN)) {
				setLastName(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(LOG_VALUENOGREATER)) {
				setValidateLength(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			}
			
		}
	}

}
