package com.nettracer.claims.core.model.labels;

import java.util.List;

import com.nettracer.claims.core.model.Label;
import com.nettracer.claims.core.model.Localetext;

public class LabelsFileUploadPage {

	// File Upload
	public static final String UPL_DESC = "Descriptive text";
	public static final String UPL_UPLOAD = "File Upload";
	public static final String UPL_SELECT = "File Selection";
	public static final String UPL_RECEIPT = "Receipts to be uploaded";
	public static final String UPL_ADDREC = "Add Receipts";
	public static final String UPL_REMOVE = "Remove";
	public static final String UPL_INSTRUCT = "File Instruction";
	public static final String UPL_HELP = "File Help";
	public static final String UPL_REQMESS = "Required Field Message";
	public static final String UPL_BROWSE = "Browse";

	private LabelText descriptiveText;
	private LabelText fileUpload;
	private LabelText fileSelection;
	private LabelText receiptsToBeUploaded;
	private LabelText addReceipts;
	private LabelText remove;
	private LabelText fileInstruction;
	private LabelText help;
	private LabelText requiredFieldMessage;
	private LabelText browse;

	public LabelText getDescriptiveText() {
		return descriptiveText;
	}

	public void setDescriptiveText(LabelText descriptiveText) {
		this.descriptiveText = descriptiveText;
	}

	public LabelText getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(LabelText fileUpload) {
		this.fileUpload = fileUpload;
	}

	public LabelText getFileSelection() {
		return fileSelection;
	}

	public void setFileSelection(LabelText fileSelection) {
		this.fileSelection = fileSelection;
	}

	public LabelText getReceiptsToBeUploaded() {
		return receiptsToBeUploaded;
	}

	public void setReceiptsToBeUploaded(LabelText receiptsToBeUploaded) {
		this.receiptsToBeUploaded = receiptsToBeUploaded;
	}

	public LabelText getAddReceipts() {
		return addReceipts;
	}

	public void setAddReceipts(LabelText addReceipts) {
		this.addReceipts = addReceipts;
	}

	public LabelText getRemove() {
		return remove;
	}

	public void setRemove(LabelText remove) {
		this.remove = remove;
	}

	public LabelText getFileInstruction() {
		return fileInstruction;
	}

	public void setFileInstruction(LabelText fileInstruction) {
		this.fileInstruction = fileInstruction;
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

	public LabelText getBrowse() {
		return browse;
	}

	public void setBrowse(LabelText browse) {
		this.browse = browse;
	}
	
	public LabelsFileUploadPage(List<Localetext> localetextList, Long baggageState) {

		for (Localetext localetext : localetextList) {
		
			Label currentLabel = localetext.getLabel();
			
			Long requiredFieldStatus = baggageState == 1L ? currentLabel.getDelayedState()
					: baggageState == 2L ? currentLabel.getPilferedState() 
							: currentLabel.getDamagedState();
					
			if (currentLabel.getLabel().equals(UPL_ADDREC)) {
				setAddReceipts(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(UPL_BROWSE)) {
				setBrowse(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(UPL_DESC)) {
				setDescriptiveText(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(UPL_HELP)) {
				setHelp(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(UPL_INSTRUCT)) {
				setFileInstruction(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(UPL_RECEIPT)) {
				setReceiptsToBeUploaded(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(UPL_REMOVE)) {
				setRemove(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(UPL_REQMESS)) {
				setRequiredFieldMessage(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(UPL_SELECT)) {
				setFileSelection(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(UPL_UPLOAD)) {
				setFileUpload(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			}
			
		}
	}

}
