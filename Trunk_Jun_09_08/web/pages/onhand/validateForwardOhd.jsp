<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>

<%
Agent a = (Agent)session.getAttribute("user");
org.apache.struts.util.PropertyMessageResources myMessages = (org.apache.struts.util.PropertyMessageResources)
request.getAttribute("org.apache.struts.action.MESSAGE");
java.util.Locale                                myLocale   = (java.util.Locale)session.getAttribute(
"org.apache.struts.action.LOCALE");
%>

<SCRIPT LANGUAGE="JavaScript">
function validateForwardOHD (form)
	{
		 for (var j=0;j<form.length;j++) {
	  	currentElement = form.elements[j];
    	currentElementName=currentElement.name;
	    if (currentElementName.indexOf("expedite") != -1 && currentElement.type != "hidden")
	    {
	      if (currentElement.value.length < 1)
	      {
	         alert("<%= (String)myMessages.getMessage(myLocale, "colname.expedite_number") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
	         currentElement.focus();
	         return false;
	      }
	        
	      if (currentElement.value.length > 0 && !checkExpedite(currentElement.value))
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.expedite_number") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.expedite") %>");
	        currentElement.focus();
	        return false;
	      }
	    }
	    else if (currentElementName.indexOf("destStation") != -1)
	    {
	      if (currentElement.value.length < 1)
	      {
	         alert("<%= (String)myMessages.getMessage(myLocale, "colname.stationForwardTo") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
	         currentElement.focus();
	         return false;
	      }
	    }
	    else if (currentElementName.indexOf("legfrom") != -1 && currentElementName.indexOf("legfrom_type") == -1)
	    {
	      if (currentElement.value.length > 0 && !checkLegFrom(currentElement.value))
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.fromto") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.station") %>");
	        currentElement.focus();
	        return false;
	      }
	    }
	    else if (currentElementName.indexOf("legto") != -1 && currentElementName.indexOf("legto_type") == -1)
	    {
	      if (currentElement.value.length > 0 && !checkLegFrom(currentElement.value))
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.fromto") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.station") %>"); 
	        currentElement.focus();
	        return false;
	      }
	    }
	    else if (currentElementName.indexOf("flightnum") != -1)
	    {
	      if (currentElement.value.length > 0 && !checkFlightNum(currentElement.value))
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.flightnum") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.flightNum") %>");
	        currentElement.focus();
	        return false;
	      }
	    }
	    else if (currentElementName.indexOf("dispBagArriveDate") != -1)
	    {
	      if (currentElement.value.length > 0 && !checkDate(currentElement.value))
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.bag_arrived_date") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.date") %>");
	        currentElement.focus();
	        return false;
	      }
	    }
	    else if (currentElementName.indexOf("disdepartdate") != -1)
	    {
	      if (currentElement.value.length > 0 && !checkDate(currentElement.value))
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.departdate") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.date") %>");
	        currentElement.focus();
	        return false;
	      }
	    }
	    else if (currentElementName.indexOf("disarrivedate") != -1)
	    {
	      if (currentElement.value.length > 0 && !checkDate(currentElement.value))
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.arrdate") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.date") %>"); 
	        currentElement.focus();
	        return false;
	      }
	    }
	    else if (currentElementName.indexOf("disschdeparttime") != -1)
	    {
	      if (currentElement.value.length > 0 && !checkTime(currentElement.value))
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.schdeptime") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.time") %>"); 
	        currentElement.focus();
	        return false;
	      }
	    }
	    else if (currentElementName.indexOf("disscharrivetime") != -1)
	    {
	      if (currentElement.value.length > 0 && !checkTime(currentElement.value))
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.scharrtime") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.time") %>"); 
	        currentElement.focus();
	        return false;
	      }
	    } 
	 	}
    
     i = 0;
     
     while (true) {
       var format = '<%= a.getDateformat().getFormat() %>' + ' ' + '<%= a.getTimeformat().getFormat() %>';
       var arrDate = document.getElementsByName("itinerarylist[" + i +"].disarrivedate");
       var arrTime = document.getElementsByName("itinerarylist[" + i +"].disscharrivetime");
       ++i;
       var depDate = document.getElementsByName("itinerarylist[" + i +"].disdepartdate");
       var depTime = document.getElementsByName("itinerarylist[" + i +"].disschdeparttime");


       if (arrDate.length < 1 ||arrTime.length < 1 || depDate.length < 1 || depTime.length < 1 ||
        arrDate[0].value.length < 1 ||arrTime[0].value.length < 1 || depDate[0].value.length < 1 || depTime[0].value.length < 1
  ) {
          break;
       }
       
       var arrival = arrDate[0].value + ' ' + arrTime[0].value;
       var departure = depDate[0].value + ' ' + depTime[0].value;

       if (compareDates(arrival,format,departure,format) > -1) {
          alert("<%= (String)myMessages.getMessage(myLocale, "error.validation.departbeforearrive") %>"); 
        currentElement.focus();
        return false;
       }
     }
    
    if (!validateReqForward(form)) return false;
	 	 return true;
  }
  
</SCRIPT>