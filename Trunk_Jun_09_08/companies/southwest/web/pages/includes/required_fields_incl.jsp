<%@ page contentType="text/javascript" %> 
<%@ page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%@ page import="org.apache.struts.action.Action"%>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>

<%@page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>

<%
	Agent a = (Agent) session.getAttribute("user");

	org.apache.struts.util.PropertyMessageResources myMessages = (org.apache.struts.util.PropertyMessageResources) request
			.getAttribute("org.apache.struts.action.MESSAGE");
	java.util.Locale myLocale = (java.util.Locale) session
			.getAttribute("org.apache.struts.action.LOCALE");
	
	ResourceBundle bundle = ResourceBundle.getBundle(
			"com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(a.getCurrentlocale()));
%>
  
  
  <jsp:include page="/pages/worldtracer/wt_required_fields.jsp"/>
 
  function validatereq(form) {
    return true;
  }
    
  function validateWTCompanyForward(form) {
 	return true;
  }
    
  function  validatereqOHDFields(form) {
	 var closed = false;
	 
	 for (var j=0;j < form.length; j++) {
     	currentElement = form.elements[j];
		if (currentElement.name == "status.status_ID") {
			statusId = currentElement.options[currentElement.selectedIndex].value;
			if (statusId == <%= TracingConstants.OHD_STATUS_CLOSED %>) {
    			closed = true;
  			}
		}
	 } 
	 if (closed == true) {
		 for (var j=0; j < form.length; j++) {
		 	currentElement = form.elements[j];
		 	currentElementName=currentElement.name;
		    if (currentElement.name == "disposal_status.status_ID" && currentElement.options[currentElement.selectedIndex].value == "") {
		    	alert('<%=(String) bundle.getString("colname.disposal_status") + " " + bundle.getString("error.validation.isRequired") %>');
            	currentElement.focus();
            	return false;
		    }
		 }
	 }
	 
	 return true;
  }

  function  validatereqPXFFields(form) {
	     return true;
  }
  function validatereqFields(form, formType)
  {
    returnValue = true;
    
    var firstPaxIndex = -1;
    var firstAddressIndex = -1;
    var firstItemIndex = -1;
    var firstClaimcheckIndex = -1;
    
    for (var j=0;j < form.length; j++) {
      currentElement = form.elements[j];
      currentElementName=currentElement.name;
      if (firstPaxIndex == -1 && currentElementName.indexOf("].lastname") != -1) {
        var left = currentElementName.indexOf("[");
        var right = currentElementName.indexOf("]");
        firstPaxIndex = currentElementName.substring(left+1, right);
      } else if (firstItemIndex == -1 && currentElementName.indexOf("].lnameonbag") != -1) {
        var left = currentElementName.indexOf("[");
        var right = currentElementName.indexOf("]");
        firstItemIndex = currentElementName.substring(left+1, right);
      } else if (firstClaimcheckIndex == -1 && currentElementName.indexOf("claimcheck[") != -1) {
        var left = currentElementName.indexOf("[");
        var right = currentElementName.indexOf("]");
        firstClaimcheckIndex = currentElementName.substring(left+1, right);
      } else if (firstAddressIndex == -1 && currentElementName.indexOf("addresses[") != -1) {
        var left = currentElementName.indexOf("[");
        var right = currentElementName.indexOf("]");
        firstAddressIndex = currentElementName.substring(left+1, right);
      }
    }
    
    returnValue = validatereqWtIncFields(form, formType, false, firstPaxIndex, firstAddressIndex, firstItemIndex, firstClaimcheckIndex);
    if (returnValue == false) { return returnValue; }
    
    return true;
  }
    
  function validatereqOHDForm(form) {
    returnValue = true;
        
    returnValue = validatereqWtOHDForm(form);
    if (validatereqOHDFields(form) == false) { return false; }
    if (returnValue == false) { return returnValue; }
  }
   
  function validatereqOHD(form)
  {

    return true;
  }
	
  function validatereqBEORN(form)
  {
	  return true;
  }
  
  function validateReqBDO(form) {
    returnValue = true;
    
    returnValue = validateBDO(form);
    if (returnValue == false) { return returnValue; }

    return true;
  }
  
  function validateReqForward(form) {
  	return true;
  }
