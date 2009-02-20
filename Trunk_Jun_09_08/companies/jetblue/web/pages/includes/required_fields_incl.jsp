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
<script language="javascript">
  <!--
  
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
    
    returnValue = validatereqWtIncFields(form, formType, false);
    if (returnValue == false) { return returnValue; }
    
    var bagIndices = [];
    var ccCount = 0;
     for (var j=0;j < form.length; j++) {
	  currentElement = form.elements[j];
	  currentElementName=currentElement.name;
	  if (currentElementName.indexOf("].color") != -1) {
    	  bagIndices = bagIndices.concat(currentElementName.substring(left+1, right));

      }
      else if(currentElementName.indexOf("].claimchecknum") != -1 && currentElement.value.length > 0) {
          ccCount += 1;
        }
	}
    if(ccCount > bagIndices.length) {
        alert('<%= (String) myMessages.getMessage(myLocale, "error.validation.too.many.claimchecks")%>');
        document.getElementById("claimcheck[0].claimchecknum").focus();
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
  // -->
</script>
