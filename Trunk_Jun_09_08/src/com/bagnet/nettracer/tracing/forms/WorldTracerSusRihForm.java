package com.bagnet.nettracer.tracing.forms;

import org.apache.struts.validator.ValidatorForm;

/**
 * @author Ankur Gupta
 * 
 * This class represents the form that is used for active tracing form.
 */
public class WorldTracerSusRihForm extends ValidatorForm {

	private String file_referance; //incident's id

	public String getFile_referance() {
		return file_referance;
	}

	public void setFile_referance(String file_referance) {
		this.file_referance = file_referance;
	}
	}