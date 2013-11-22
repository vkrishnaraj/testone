<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-nested" prefix="nested"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script language="javascript">
    
    function gotoHistoricalReport(form) {
        o = document.incidentForm;
    	o.historical_report.value = "1";
    	clearBeforeUnload();  
    	o.submit();
    }
    function disableButton(aButton) {
    	aButton.disabled = true;
    	aButton.value= "<bean:message key='ajax.please_wait' />";
    }
    
    function enableButton(aButton, label) {
    	aButton.disabled = false;
    	aButton.value=label;
    }
    
     
	function disableButtons() {
		if (document.incidentForm.saveButton) {
			disableButton(document.incidentForm.saveButton);
		}
		if (document.incidentForm.limitSaveButton) {
			disableButton(document.incidentForm.saveadditionsbutton);
		}
		if (document.incidentForm.wtbutton) {
			disableButton(document.incidentForm.wtbutton);
		}
		if (document.incidentForm.saveremarkButton) {
			disableButton(document.incidentForm.saveremarkButton);
		}
		if (document.incidentForm.savetracingButton) {
			disableButton(document.incidentForm.savetracingButton);
		}

	}

	function enableButtons() {

		if (document.incidentForm.saveButton) {
			<logic:notEqual name="incidentForm" property="incident_ID" value="">
			enableButton(document.incidentForm.saveButton,
					"<bean:message key='button.save' />");
			</logic:notEqual>
			<logic:equal name="incidentForm" property="incident_ID" value="">
			enableButton(document.incidentForm.saveButton,
					"<bean:message key='button.saveincident' />");
			</logic:equal>

		}

		if (document.incidentForm.limitSaveButton) {
			enableButton(document.incidentForm.saveadditionsbutton,
					"<bean:message key='button.save' />");
		}
		if (document.incidentForm.wtbutton) {
			enableButton(document.incidentForm.wtbutton,
					"<bean:message key='button.savetoWT' />");
		}
		if (document.incidentForm.saveremarkButton) {
			enableButton(document.incidentForm.saveremarkButton,
					"<bean:message key='button.saveremark' />");
		}
		if (document.incidentForm.savetracingButton) {
			enableButton(document.incidentForm.savetracingButton,
					"<bean:message key='button.savetracing' />");
		}
	}
	
</script>