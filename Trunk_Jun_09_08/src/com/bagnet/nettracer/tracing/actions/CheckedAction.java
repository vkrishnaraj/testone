package com.bagnet.nettracer.tracing.actions;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;

import com.bagnet.nettracer.tracing.constant.TracingConstants;

public abstract class CheckedAction extends Action {
	
	private Logger logger = Logger.getLogger(CheckedAction.class);

	protected boolean manageToken(HttpServletRequest request){
		if(request.getParameter(org.apache.struts.taglib.html.Constants.TOKEN_KEY) != null) {
			if(isTokenValid(request, true)) {
				saveToken(request);
				return true;
			}
			return false;
		}
		
		saveToken(request);
		return true;
	}
	
	public static final int ADD_NEW_RECORD = 1;
	public static final int CLOSE_RECORD = 2;
	public static final int UPDATE_RECORD = 3;
	
	//static String newline1 = "+";
	//static String newline2 = System.getProperty("line.separator");
	static String newline = "*";
	static String indent = " ";
	static String tab = "      ";
	
	protected static String leftPad(String input, int size) {
		if (input == null) {
			input = "";
		}
		return org.apache.commons.lang.StringUtils.leftPad(input, size);
	}
	
	protected static String rightPad(String input, int size) {
		if (input == null) {
			input = "";
		}
		return org.apache.commons.lang.StringUtils.rightPad(input, size);
	}
	
	protected void outputToResponse(HttpServletResponse response, byte[] toOutput, int outputType) {
		switch(outputType) {
			case TracingConstants.REPORT_OUTPUT_HTML:
				response.setContentType("text/html");
				break;
			case TracingConstants.REPORT_OUTPUT_PDF:
				response.setContentType("application/pdf");
				break;
			default:
				displayError(response);
				return;
		}
		
		response.setContentLength(toOutput.length);
		writeOutputToResponse(response, toOutput);
	}
	
	private void displayError(HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
	
	private void writeOutputToResponse(HttpServletResponse response, byte[] toOutput) {
		try {
			response.getOutputStream().write(toOutput);
		} catch (IOException ioe) {
			logger.error("Failed to write the output to the response", ioe);
		}
	}
	
}
