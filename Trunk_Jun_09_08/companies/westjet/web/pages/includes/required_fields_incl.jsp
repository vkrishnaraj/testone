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
  
  function validatereqFields(form, formType)
  {
    returnValue = true;
    var addressIndices = [];
    var bagIndices = [];
    var ccCount = 0;
    
    returnValue = validatereqWtIncFields(form, formType, false);
    if (returnValue == false) { return returnValue; }
    
    for (var j=0;j < form.length; j++) {
	  currentElement = form.elements[j];
	  currentElementName=currentElement.name;

      if (currentElementName.indexOf("address1") != -1) {
        var left = currentElementName.indexOf("[");
        var right = currentElementName.indexOf("]");
        addressIndices = addressIndices.concat(currentElementName.substring(left+1, right));
      } 
      else if (currentElementName.indexOf("nonrevenue") != -1) {  
        if (currentElement.value != 0)
        {
          alert('<%=(String) myMessages.getMessage(myLocale,  "error.validation.requiredValue", myMessages.getMessage(myLocale, "colname.non_revenue"), myMessages.getMessage(myLocale, "select.no" ))%>');
          currentElement.focus();
          return false;
        }
      } 
      else if (currentElementName.indexOf("ticketnumber") != -1) {  
        if (currentElement.value.length != 0)
        {
          alert('<%=(String) myMessages.getMessage(myLocale,  "error.validation.leftBlank", myMessages.getMessage(myLocale, "colname.ticket"))%>');
          currentElement.focus();
          return false;
        }
      } 
      else if (currentElementName.indexOf(".jobtitle") != -1) {  
        if (currentElement.value.length != 0)
        {
          alert('<%=(String) myMessages.getMessage(myLocale,  "error.validation.leftBlank", myMessages.getMessage(myLocale, "colname.job_title"))%>');
          currentElement.focus();
          return false;
        }
      } 
      else if (currentElementName.indexOf(".membership") != -1) {  
        if (currentElement.value.length != 0)
        {
          alert('<%=(String) myMessages.getMessage(myLocale,  "error.validation.leftBlank", myMessages.getMessage(myLocale, "membership.information"))%>');
          currentElement.focus();
          return false;
        }
      }
      else if (currentElementName.indexOf("courtesyreport") != -1) {  
          if (currentElement.value != 0)
          {
            alert('<%=(String) myMessages.getMessage(myLocale,  "error.validation.requiredValue", myMessages.getMessage(myLocale, "colname.courtesy_report"), myMessages.getMessage(myLocale, "select.no" ))%>');
            currentElement.focus();
            return false;
          }
        }
      else if (currentElementName.indexOf("disposal_status") != -1) {  
          if (currentElement.value.length != 0)
          {
            alert('<%=(String) myMessages.getMessage(myLocale,  "error.validation.leftBlank", myMessages.getMessage(myLocale, "colname.disposal_status"))%>');
            currentElement.focus();
            return false;
          }
        }
      else if(currentElementName.indexOf("].claimchecknum") != -1 && currentElement.value.length > 0) {
          ccCount += 1;
        }
      else if (currentElementName.indexOf("].color") != -1) {
    	  bagIndices = bagIndices.concat(currentElementName.substring(left+1, right));

      }
        
    } // End FOR LOOP
    
    for (var j=0;j<addressIndices.length;j++) {
      var index = addressIndices[0];
      var mobile = document.getElementById("addresses[" + index + "].mobile");
      var home = document.getElementById("addresses[" + index + "].homephone");
      var work = document.getElementById("addresses[" + index + "].workphone");
          
      if (mobile.value.length == 0 && home.value.length== 0 && work.value.length == 0) {
        alert("<%= (String)myMessages.getMessage(myLocale, "colname.phone") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
        mobile.focus();
        return false;
      }
      break;
    }
    
    if(ccCount < bagIndices.length) {
  	  var answer = confirm('<%=(String) myMessages.getMessage(myLocale,  "confirm.more.bags.than.tags")%>');
	  	if(!answer) {
    		  return false;
	 	 }
	  }
    
  }
    
  function validatereqOHDForm(form) {
    returnValue = true;
    var addressIndices = [];
    
    returnValue = validatereqWtOHDForm(form);
    if (returnValue == false) { return returnValue; }
    
    for (var j=0;j < form.length; j++) {
      currentElement = form.elements[j];
      currentElementName=currentElement.name;


  	 if (currentElementName.indexOf("companycode_ID") != -1) {  
          if (currentElement.value.length != 0)
          {
            alert('<%=(String) myMessages.getMessage(myLocale,  "error.validation.leftBlank", myMessages.getMessage(myLocale, "colname.airline_membership"))%>');
            currentElement.focus();
            return false;
          }
        }
      else if (currentElementName.indexOf("membership") != -1) {  
          if (currentElement.value.length != 0)
          {
            alert('<%=(String) myMessages.getMessage(myLocale,  "error.validation.leftBlank", myMessages.getMessage(myLocale, "membership.information"))%>');
            currentElement.focus();
            return false;
          }
        }
      else if (currentElementName.indexOf("].description") != -1)
      {  
        if (currentElement.value.length == 0)
        {
          alert("<%= (String)myMessages.getMessage(myLocale, "colname.description") %>" + 
          " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
          currentElement.focus();
          return false;
        }
      }
    }
    return true;
  }
   
  function validatereqOHD(form)
  {
    return true;

  }
	
  function validatereqBEORN(form)
  {
  	var theindex = 0;
  	for (var j=0;j<form.length;j++) {
	    currentElement = form.elements[j];
	    currentElementName=currentElement.name;
      
        if (currentElementName.indexOf("lossCode") != -1)
        {  
          if (currentElement.value.length == 0)
          {
            alert("<%= (String)myMessages.getMessage(myLocale, "colname.losscode") %>" + 
            " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
            currentElement.focus();
            return false;
          }
        }
        else if (currentElementName.indexOf("faultStation") != -1)
        {  
          if (currentElement.value.length == 0)
          {
            alert("<%= (String)myMessages.getMessage(myLocale, "colname.faultstation") %>" + 
            " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
            currentElement.focus();
            return false;
          }
        }
        else if (!(currentElementName.indexOf("bagItinerary") != -1)) {
    	    if (currentElementName.indexOf("itinerary") != -1 && currentElementName.indexOf(".legfrom") != -1) {  
    	      if (currentElement.value.length == 0)
    	      {
    	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.fromto") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
    	        currentElement.focus();
    	        return false;
    	      }
    	    } else if (currentElementName.indexOf("itinerary") != -1 && currentElementName.indexOf(".legto") != -1) {
    	      if (currentElement.value.length == 0)
    	      {
    	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.fromto") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
    	        currentElement.focus();
    	        return false;
    	      }
    	    } else if (currentElementName.indexOf("itinerary") != -1 && currentElementName.indexOf(".airline") != -1) {
    	      if (currentElement.value.length == 0)
    	      {
    	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.flightnum") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
    	        currentElement.focus();
    	        return false;
    	      }
    	    } else if (currentElementName.indexOf("itinerary") != -1 && currentElementName.indexOf(".flightnum") != -1) {
    	      if (currentElement.value.length == 0)
    	      {
    	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.flightnum") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
    	        currentElement.focus();
    	        return false;
    	      }
    	    }
          }
	  }
	  return true;
  }
  
  function validateReqBDO(form) {
    returnValue = true;
    
    returnValue = validateBDO(form);
    if (returnValue == false) { return returnValue; }

    for (var j=0;j < form.length; j++) {
      currentElement = form.elements[j];
      currentElementName=currentElement.name;

      if (currentElementName.indexOf("dispdeliverydate") != -1)
      {  
        if (currentElement.value.length == 0)
        {
          alert("<%= (String)myMessages.getMessage(myLocale, "colname.deliverydate") %>" + 
          " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
          currentElement.focus();
          return false;
        }
      }
    }

    return true;
  }
  // -->
</script>
