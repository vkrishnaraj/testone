package com.bagnet.nettracer.tracing.forms.templates;

import org.apache.struts.validator.ValidatorForm;

public class CommandForm extends ValidatorForm {

	private static final long serialVersionUID = -4596671545672777798L;

	private String command;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
	
}
