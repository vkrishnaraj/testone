<%@ page language="java"%>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%@ page import="org.apache.struts.action.Action"%>

<%
	Agent a = (Agent) session.getAttribute("user");

	org.apache.struts.util.PropertyMessageResources myMessages = (org.apache.struts.util.PropertyMessageResources) request
			.getAttribute("org.apache.struts.action.MESSAGE");
	java.util.Locale myLocale = (java.util.Locale) session
			.getAttribute("org.apache.struts.action.LOCALE");
%>

<%@page import="com.bagnet.nettracer.tracing.forms.IncidentForm"%>
<script language="javascript">
  
  
  <jsp:include page="/pages/worldtracer/wt_required_fields.jsp"/>
 
  function validatereq(form)
  {
    return true;
  }
    function validateWTCompanyForward(form) {
 	
		    return true;
  }
    function  validatereqOHDFields(form) {
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
    
    var bagIndices = [];
    var ccCount = 0;
     for (var j=0;j < form.length; j++) {
	  currentElement = form.elements[j];
	  currentElementName=currentElement.name;
	  if (currentElementName.indexOf("].color") != -1) {
		 var left = currentElementName.indexOf("[");
       	 var right = currentElementName.indexOf("]");
    	  bagIndices = bagIndices.concat(currentElementName.substring(left+1, right));
      }
      else if(currentElementName.indexOf("].claimchecknum") != -1 && currentElement.value.length > 0) {
          ccCount += 1;
        }
	}
    if(ccCount > bagIndices.length) {
        alert('<%= (String) myMessages.getMessage(myLocale, "error.validation.too.many.claimchecks")%>');
        document.getElementById("claimcheck[" + firstClaimcheckIndex +"].claimchecknum").focus();
        return false;
  }
    return true;
  }
    
  function validatereqOHDForm(form) {
    returnValue = true;
        
    returnValue = validatereqWtOHDForm(form);
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

  <% if (session.getAttribute("incidentForm") != null) { %>
    var isAware = <%= ((IncidentForm) session.getAttribute("incidentForm")).isNotifiedOfRequirements()%>;
  <% } else { %>
    var isAware = false;
  <% }%>

  function checkDeleteCount(bagNum) {
	  inputs = document.getElementsByTagName("input");
	  var invCount = 0;
	  for(i = 0; i < inputs.length; i++) {
	    if(inputs[i].id.indexOf("deleteinventory_" + bagNum) == 0) {
	      invCount ++;
	      if(invCount >= 3) break;
	    }
	  }
	  if(invCount < 3 && !isAware) {
	    isAware = confirm("An explanatory remark must be entered when reporting baggage with less than 3 content fields. Would you like to continue?");
	    document.forms[0].notifiedOfRequirements.value = true;
	    return isAware;
	  }
	  return true;
	}
</script>
