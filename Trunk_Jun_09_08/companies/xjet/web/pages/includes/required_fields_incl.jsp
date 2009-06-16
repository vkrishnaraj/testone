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
  
  
  function validatereqFields(form, formType)
  {
  	return true;
  } 
      function validateWTCompanyForward(form) {
 	
		    return true;
  }
    function  validatereqOHDFields(form) {
		     return true;
 }
  function validatereq(form)
  {
  	var theindex = 0;
  	for (var j=0;j<form.length;j++) {
	    currentElement = form.elements[j];
	    currentElementName=currentElement.name;
	    if (currentElementName.indexOf("recordlocator") != -1) {  

	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.recordlocator") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("lastname") != -1) {  
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
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.street_addr") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("homephone") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.home_ph") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
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
		        alert("<%= (String)myMessages.getMessage(myLocale, "colname.state") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
		        currentElement.focus();
		        return false;
	      	}
	    } else if (currentElementName.indexOf("itinerarytype") != -1) {  
	      if (currentElement.value == 0) theindex = currentElementName.substring(13,14);
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
	    } else if (currentElementName.indexOf("[" + theindex + "].claimchecknum") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.claimnum") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
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
  
 		return true;
  }
  

  function validatereqOHDForm(form) {
  	return true;
  }
  
  function validatereqOHD(form)
  {
  	var theindex = 0;
  	for (var j=0;j<form.length;j++) {
	    currentElement = form.elements[j];
	    currentElementName=currentElement.name;
	    if (currentElementName.indexOf("bagTagNumber") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.bag_tag_number") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("bagColor") != -1) {  
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
	    } else if (currentElementName.indexOf("dispBagArriveDate") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.bag_arrived_date") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	    }
	  }
	  return true;
	}
	
	function validatereqBEORN(form)
  {
  	var theindex = 0;
  	for (var j=0;j<form.length;j++) {
	    currentElement = form.elements[j];
	    currentElementName=currentElement.name;
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
	    } else if (currentElementName.indexOf("itinerary") != -1 && currentElementName.indexOf(".disdepartdate") != -1) {
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.departdate") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	    }
	  }
	  return true;
	}

    function checkDeleteCount(bagNum) { return true; }
  </script>
