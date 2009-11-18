<%@ page language="java" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="org.apache.struts.action.Action" %>
<%
  Agent a = (Agent)session.getAttribute("user");

  org.apache.struts.util.PropertyMessageResources myMessages = (org.apache.struts.util.PropertyMessageResources)
                                                               request.getAttribute("org.apache.struts.action.MESSAGE");
  java.util.Locale                                myLocale   = (java.util.Locale)session.getAttribute(
                                                               "org.apache.struts.action.LOCALE");
%>
  <script language="javascript">
  
  
  function validatereq(form) {
  	return true;
  }
  
  function validatereqFields(form, formType)
  {
  	var theindex = 0;
  	var addressIndices = [];
  	for (var j=0;j<form.length;j++) {
	    currentElement = form.elements[j];
	    currentElementName=currentElement.name;
		
		if (currentElementName.indexOf("lastname") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.last_name") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("firstname") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.first_name") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("address1") != -1) {
	      var left = currentElementName.indexOf("[");
	      var right = currentElementName.indexOf("]");
	      addressIndices = addressIndices.concat(currentElementName.substring(left+1, right));
	        
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.street_addr") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("city") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.city") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      } 
	    } else if (currentElementName.indexOf("].state_ID") != -1) {  

	    	var pos = currentElementName.indexOf(".");
	        var str = currentElementName.substring(0,pos+1) + "countrycode_ID";
	
	        if (form.elements[str].value == "US" && currentElement.value.length ==0) {
		        alert("<%= (String)myMessages.getMessage(myLocale, "colname.state") %>" + " is required if country is set to 'United States'");
		        currentElement.focus();
		        return false;
	      	}
	    } else if (currentElementName.indexOf("].description") != -1 &&
	               currentElementName.indexOf("inventorylist[") != -1 &&
	               (formType == 'damaged' || formType == 'pilfered') ) 
		{
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.description") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("].damage") != -1) {
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.damagedesc") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("itinerarytype") != -1) {  
	      if (currentElement.value == 0 || currentElement.value == 1) theindex = currentElementName.substring(13,14);
	    } else if (currentElementName.indexOf("[" + theindex + "].legfrom") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.fromto") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("[" + theindex + "].legto") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.fromto") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("[" + theindex + "].airline") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.flightnum") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("[" + theindex + "].flightnum") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.flightnum") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("[" + theindex + "].disdepartdate") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.departdate") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("].color") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.color") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("].bagtype") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.bagtype") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	    }
	
  	}
	
  	for (var j=0;j<addressIndices.length;j++) {
  	    var index = addressIndices[j];
  		var mobile = document.getElementById("addresses[" + index + "].mobile");
  		var home = document.getElementById("addresses[" + index + "].homephone");
  		var work = document.getElementById("addresses[" + index + "].workphone");
  		  		
  		if (mobile.value.length == 0 && home.value.length== 0 && work.value.length == 0) {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.mobile_ph") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
	        mobile.focus();
	        return false;
  		}
  	}
  
 		return true;
  }
	
 function validatereqOHDForm(form) {
   	var theindex = 0;
  	for (var j=0;j<form.length;j++) {
	    currentElement = form.elements[j];
	    currentElementName=currentElement.name;
		if (currentElementName.indexOf("bagColor") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.color") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("bagType") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.bagtype") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
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
	  return true;
	}

  function checkDeleteCount(bagNum) { return true; }
</script>
