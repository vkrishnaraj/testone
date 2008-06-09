<%@ page language="java" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="org.apache.struts.action.Action" %>

<jsp:include page="required_fields_incl.jsp" />
<%
  Agent a = (Agent)session.getAttribute("user");
%>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/field_validation.js"></SCRIPT>
<%
  org.apache.struts.util.PropertyMessageResources myMessages = (org.apache.struts.util.PropertyMessageResources)
                                                               request.getAttribute("org.apache.struts.action.MESSAGE");
  java.util.Locale                                myLocale   = (java.util.Locale)session.getAttribute(
                                                               "org.apache.struts.action.LOCALE");
%>
  <script language="javascript">
    <!--
	// non us countries don't get state drop down
	function checkstate(var1,o,statefield) {
	
		pos = var1.name.indexOf(".");
		if (pos <=0) addr = "";
		else addr = var1.name.substring(0,pos+1);
		state = addr + statefield;
			
		if (var1.value!='US') {
			// disable state
			o.elements[state].disabled = true;
		} else {
			o.elements[state].disabled = false;
		}
	
	}
	
	function validateMessageForm (form)
	{
		 for (var j=0;j<form.length;j++) {
	  	currentElement = form.elements[j];
    	currentElementName=currentElement.name;
		 	if (currentElementName.indexOf("expedite") != -1)
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
	    else if (currentElementName.indexOf("bag_tag") != -1)
	    {
	      if (currentElement.value.length < 1)
	      {
	         alert("<%= (String)myMessages.getMessage(myLocale, "colname.bag_tag_number") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
	         currentElement.focus();
	         return false;
	      }
	        
	      if (currentElement.value.length > 0 && !checkClaimCheck(currentElement.value))
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.bag_tag_number") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.expedite") %>");
	        currentElement.focus();
	        return false;
	      }
	    }
	    else if (currentElementName.indexOf("legfrom") != -1)
	    {
	      if (currentElement.value.length > 0 && !checkLegFrom(currentElement.value))
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.fromto") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.station") %>");
	        currentElement.focus();
	        return false;
	      }
	    }
	    else if (currentElementName.indexOf("legto") != -1)
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
	 	
	 	if (form.name == "forwardMessageForm"){
    	if (!validatereqBEORN(form)) return false;
    }
    
	 	 return true;
  }
  
  
  function validateForwardOHD (form)
	{
		 for (var j=0;j<form.length;j++) {
	  	currentElement = form.elements[j];
    	currentElementName=currentElement.name;
		 	if (currentElementName.indexOf("expedite") != -1)
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
	    else if (currentElementName.indexOf("legfrom") != -1)
	    {
	      if (currentElement.value.length > 0 && !checkLegFrom(currentElement.value))
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.fromto") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.station") %>");
	        currentElement.focus();
	        return false;
	      }
	    }
	    else if (currentElementName.indexOf("legto") != -1)
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
	 	 return true;
  }


  function checkDate(strng)
  {
    return isDate(strng,'<%= a.getDateformat().getFormat() %>');
  }
  
  function checkTime(strng)
  {
    return isDate(strng,'<%= a.getTimeformat().getFormat() %>');
  }
  
  function validateSearch(form) {
  	for (var j=0;j<form.length;j++) {
	    currentElement = form.elements[j];
	    currentElementName=currentElement.name;
	  	if (currentElementName.indexOf("s_createtime") != -1)
	    {
	      if (currentElement.value.length > 0 && !checkDate(currentElement.value))
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.date_range") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.date") %>");
	        currentElement.focus();
	        return false;
	      }
	    }
	    
	    else if (currentElementName.indexOf("e_createtime") != -1)
	    {
	      if (currentElement.value.length > 0 && !checkDate(currentElement.value))
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.date_range") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.date") %>");
	        currentElement.focus();
	        return false;
	      }
	    }
	  }
     return true;
  }
  
  function validateLostFound(form)
  {
    for (var j=0;j<form.length;j++) {
    	currentElement = form.elements[j];
    	currentElementName=currentElement.name;
 	 		if (currentElementName.indexOf("dispDateFoundLost") != -1)
    	{
      	if (currentElement.value.length > 0 && !checkDate(currentElement.value))
      	{
        	alert("<%= (String)myMessages.getMessage(myLocale, "colname.date") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.date") %>"); 
        	currentElement.focus();
        	return false;
      	}
    	}
    	else if (currentElementName.indexOf("email") != -1 )
    	{
      	//is email address valid??
      	if (currentElement.value.length > 0 && !checkEmail(currentElement.value))
      	{
        	alert("<%= (String)myMessages.getMessage(myLocale, "colname.email") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.email") %>");
        	currentElement.focus();
        	return false;
      	}
    	}
    }
    return true;
 	}
 	
  function validateRest(form)
  {
     for (var j=0;j<form.length;j++) {
    currentElement = form.elements[j];
    currentElementName=currentElement.name;
    if (currentElementName.indexOf("email") != -1 && currentElementName.indexOf("email_") == -1)
    {
      //is email address valid??
      if (currentElement.value.length > 0 && !checkEmail(currentElement.value))
      {
        alert("<%= (String)myMessages.getMessage(myLocale, "colname.email") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.email") %>");
        currentElement.focus();
        return false;
      }
    }
    else if (currentElementName.indexOf("expedite") != -1)
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
    else if (currentElementName.indexOf("pnr") != -1)
    {  
      //is record locator valid??
      if (currentElement.value.length > 0 && !checkPnr(""+currentElement.value))
      {
        alert("<%= (String)myMessages.getMessage(myLocale, "colname.pnr") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.pnr") %>");
        currentElement.focus();
        return false;
      }
    }
     else if (currentElementName.indexOf("recordlocator") != -1)
    {  
      //is record locator valid??
      if (currentElement.value.length > 0 && !checkPnr(""+currentElement.value))
      {
        alert("<%= (String)myMessages.getMessage(myLocale, "colname.recordlocator") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.pnr") %>");
        currentElement.focus();
        return false;
      }
    }
     else if (currentElementName.indexOf("ssn") != -1)
    {  
      //is ssn valid??
      if (currentElement.value.length > 0 && !checkSSN(""+currentElement.value))
      {
        alert("<%= (String)myMessages.getMessage(myLocale, "colname.ssn") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.ssn") %>");
        currentElement.focus();
        return false;
      }
    }
   	else if (currentElementName.indexOf("numpassengers") != -1)
    {  
      //is email address valid??
      if (currentElement.value.length > 0 && !checkInteger(currentElement.value))
      {
        alert("<%= (String)myMessages.getMessage(myLocale, "colname.num_pass") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.num_passengers") %>");
        currentElement.focus();
        return false;
      }
    }
    else if (currentElementName.indexOf("numbagchecked") != -1)
    {  
      //is email address valid??
      if (currentElement.value.length > 0 && !checkInteger(currentElement.value))
      {
        alert("<%= (String)myMessages.getMessage(myLocale, "colname.num_bag_checked") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.num_bag_checked") %>");
        currentElement.focus();
        return false;
      }
    }
    else if (currentElementName.indexOf("numbagreceived") != -1)
    {  
      //is email address valid??
      if (currentElement.value.length > 0 && !checkInteger(currentElement.value))
      {
        alert("<%= (String)myMessages.getMessage(myLocale, "colname.bags_rec") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.num_bag_recd") %>");
        currentElement.focus();
        return false;
      }
    } 
    
    else if (currentElementName.indexOf("dlstate") != -1) {
        // have to enter dl state if driver license number is entered 
        // get index
        var pos = currentElementName.indexOf(".");
        var str = currentElementName.substring(0,pos+1) + "driverslicense";

        if (form.elements[str].value.length > 0 && currentElement.value.length ==0) {
            alert("<%= (String)myMessages.getMessage(myLocale, "error.validation.dlicense") %>");
            currentElement.focus();
            return false;
        }
    } 
      
    else if (currentElementName.indexOf("countryofissue") != -1) {
        // have to enter country if common number is entered
        var pos = currentElementName.indexOf(".");
        var str = currentElementName.substring(0,pos+1) + "commonnum";

        if (form.elements[str].value.length > 0 && currentElement.value.length == 0) {
            alert("<%= (String)myMessages.getMessage(myLocale, "error.validation.commonNumber") %>");
            currentElement.focus();
            return false;
        }
    }  

     
    else if (currentElementName.indexOf("companyCode_ID") != -1) {
        // have to enter airline if frequent flyer number is entered
        var pos = currentElementName.indexOf(".");
        var str = currentElementName.substring(0,pos+1) + "membership.membershipnum";

        if (form.elements[str].value.length > 0 && currentElement.value.length == 0) {
            alert("<%= (String)myMessages.getMessage(myLocale, "error.validation.airline") %>");
            currentElement.focus();
            return false;
        }
    }   
    else if (currentElementName.indexOf("zip") != -1)
    {
      //is email address valid??
      if (currentElement.value.length > 0 && !checkZip(currentElement.value))
      {
        alert("<%= (String)myMessages.getMessage(myLocale, "colname.zip") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.zipcode") %>");
        currentElement.focus();
        return false;
      }
    }
    
    else if (currentElementName.indexOf("legfrom") != -1)
    {
      if (currentElement.value.length > 0 && !checkLegFrom(currentElement.value))
      {
        alert("<%= (String)myMessages.getMessage(myLocale, "colname.fromto") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.station") %>");
        currentElement.focus();
        return false;
      }
    }
    else if (currentElementName.indexOf("legto") != -1)
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
     else if (currentElementName.indexOf("disactdeparttime") != -1)
    {
      if (currentElement.value.length > 0 && !checkTime(currentElement.value))
      {
        alert("<%= (String)myMessages.getMessage(myLocale, "colname.actdeptime") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.time") %>"); 
        currentElement.focus();
        return false;
      }
    }
    else if (currentElementName.indexOf("disactarrivetime") != -1)
    {
      if (currentElement.value.length > 0 && !checkTime(currentElement.value))
      {
        alert("<%= (String)myMessages.getMessage(myLocale, "colname.actarrtime") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.time") %>"); 
        currentElement.focus();
        return false;
      }
    }
    else if (currentElementName.indexOf("claimchecknum") != -1)
    {
      if (currentElement.value.length > 0 && !checkClaimCheck(currentElement.value))
      {
        alert("<%= (String)myMessages.getMessage(myLocale, "colname.claimnum") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.claimcheck") %>"); 
        currentElement.focus();
        return false;
      }
    }
    else if (currentElementName.indexOf("dispurchasedate") != -1)
    {
      if (currentElement.value.length > 0 && !checkDate(currentElement.value))
      {
        alert("<%= (String)myMessages.getMessage(myLocale, "colname.purchase_date") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.date") %>"); 
        currentElement.focus();
        return false;
      }
    }
    else if (currentElementName.indexOf("discost") != -1)
    {
      if (currentElement.value.length > 0 && !checkFloat(currentElement.value))
      {
        alert("<%= (String)myMessages.getMessage(myLocale, "colname.cost") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.float") %>"); 
        currentElement.focus();
        return false;
      }
    }
    else if (currentElementName.indexOf("bagTagNumber") != -1)
    {
         if (currentElement.value.length > 0 && !checkClaimCheck(currentElement.value))
      {
        alert("<%= (String)myMessages.getMessage(myLocale, "colname.bag_tag_number") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.bagTag") %>"); 
        currentElement.focus();
        return false;
      }
    }
    else if (currentElementName.indexOf("s_time") != -1 || currentElementName.indexOf("e_time") != -1)
    {
      if (currentElement.value.length > 0 && !checkDate(currentElement.value))
      {
        alert("<%= (String)myMessages.getMessage(myLocale, "colname.date_range") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.date") %>"); 
        currentElement.focus();
        return false;
      }
    }
   
   	// report section
    else if (currentElementName.indexOf("s_createtime") != -1)
    {
      if (currentElement.value.length > 0 && !checkDate(currentElement.value))
      {
        alert("<%= (String)myMessages.getMessage(myLocale, "colname.date_range") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.date") %>");
        currentElement.focus();
        return false;
      }
    }
    
    else if (currentElementName.indexOf("e_createtime") != -1)
    {
      if (currentElement.value.length > 0 && !checkDate(currentElement.value))
      {
        alert("<%= (String)myMessages.getMessage(myLocale, "colname.date_range") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.date") %>");
        currentElement.focus();
        return false;
      }
    }
    }
    
    // required fields validation
    if (form.name == "incidentForm"){
    	if (!validatereq(form)) return false;
    } else if (form.name == "OnHandForm") {
    	if (!validatereqOHD(form)) return false;
    }
     return true;
  }
  
   function deleteAgent (userName,agentId,stationId)
  {
    if (confirm("<%= (String)myMessages.getMessage(myLocale, "check.delete") %>" + userName + "?"))
    {  
      document.agentForm.stationId.value=stationId;
      document.agentForm.delete1.value="1";
      document.agentForm.agentId.value=agentId;
      document.agentForm.submit();
    }
  }
  
  function deleteGroup (groupName,groupId)
  {
    if (confirm("<%= (String)myMessages.getMessage(myLocale, "check.delete") %>" + groupName + "?"))
    {  
      document.groupForm.delete1.value="1";
      document.groupForm.groupID.value=groupId;
      document.groupForm.submit();
    }
  } 

	function deleteCode()
  {
    if (confirm("<%= (String)myMessages.getMessage(myLocale, "check.delete") %>?"))
    {  
			var checked = 0;
    	var codes="";
    
    	for (var j=0;j<document.codeForm.length;j++) 
    	{
      	currentElement = document.codeForm.elements[j];
       	if (currentElement.type=="checkbox")
       	{
        	if (currentElement.checked)
         	{
          	if (checked > 0) 
          		codes += ",";
          	checked +=1;
          	codes +=currentElement.value;
         	}
       	}
     	}

     	if (checked < 1)
     	{
     		alert("<%= (String)myMessages.getMessage(myLocale, "error.validation.missingCodes") %>");
     	}
    	else
     	{
     		document.codeForm.delete1.value="1";
				document.codeForm.loss_code.value=codes;
      	document.codeForm.submit();
    	}
  	}
  } 
  
  
 	function batchReceive()
  {
    if (confirm("<%= (String)myMessages.getMessage(myLocale, "check.receive") %>?"))
    {  
			var checked = 0;
    	var received="";
    
    	for (var j=0;j<document.viewIncomingRequestForm.length;j++) 
    	{
      	currentElement = document.viewIncomingRequestForm.elements[j];
       	if (currentElement.type=="checkbox")
       	{
        	if (currentElement.checked)
         	{
          	if (checked > 0) 
          		received += ",";
          	checked +=1;
          	received +=currentElement.value;
         	}
       	}
     	}

     	if (checked < 1)
     	{
     		alert("<%= (String)myMessages.getMessage(myLocale, "error.validation.missingBags") %>");
     	}
    	else
     	{
     		document.viewIncomingRequestForm.close1.value="1";
				document.viewIncomingRequestForm.ohd_ID.value=received;
      	document.viewIncomingRequestForm.submit();
    	}
  	}
  } 

 	function batchApprove()
  {
    if (confirm("<%= (String)myMessages.getMessage(myLocale, "check.approve") %>?"))
    {  
			var checked = 0;
    	var received="";
    
    	for (var j=0;j<document.interimExpenseRequestForm.length;j++) 
    	{
      	currentElement = document.interimExpenseRequestForm.elements[j];
       	if (currentElement.type=="checkbox")
       	{
        	if (currentElement.checked)
         	{
          	if (checked > 0) 
          		received += ",";
          	checked +=1;
          	received +=currentElement.value;
         	}
       	}
     	}

     	if (checked < 1)
     	{
     		alert("<%= (String)myMessages.getMessage(myLocale, "error.validation.missingRequests") %>");
     	}
    	else
     	{
     		document.interimExpenseRequestForm.approve1.value="1";
				document.interimExpenseRequestForm.payout_ID.value=received;
      	document.interimExpenseRequestForm.submit();
    	}
  	}
  } 


 	function batchDeny()
  {
    if (confirm("<%= (String)myMessages.getMessage(myLocale, "check.deny") %>?"))
    {  
			var checked = 0;
    	var received="";
    
    	for (var j=0;j<document.interimExpenseRequestForm.length;j++) 
    	{
      	currentElement = document.interimExpenseRequestForm.elements[j];
       	if (currentElement.type=="checkbox")
       	{
        	if (currentElement.checked)
         	{
          	if (checked > 0) 
          		received += ",";
          	checked +=1;
          	received +=currentElement.value;
         	}
       	}
     	}

     	if (checked < 1)
     	{
     		alert("<%= (String)myMessages.getMessage(myLocale, "error.validation.missingDenRequests") %>");
     	}
    	else
     	{
     		document.interimExpenseRequestForm.deny1.value="1";
				document.interimExpenseRequestForm.payout_ID.value=received;
      	document.interimExpenseRequestForm.submit();
    	}
  	}
  } 


 	function batchMessageDelete()
	{
    	if (confirm("<%= (String)myMessages.getMessage(myLocale, "check.message.delete") %>?"))
	    {  
			var checked = 0;
	    	var received="";
	    
	    	for (var j=0;j<document.composeForm.length;j++) 
	    	{
	      		currentElement = document.composeForm.elements[j];
	       		if (currentElement.type=="checkbox")
	       		{
	        		if (currentElement.checked)
	         		{
	          		if (checked > 0) 
	          			received += ",";
	          		checked +=1;
	          		received +=currentElement.value;
	         		}
	       		}
	     	}
	
	     	if (checked < 1)
	     	{
	     		alert("<%= (String)myMessages.getMessage(myLocale, "error.validation.missingMessages") %>");
	     	}
	    	else
	     	{
	     		document.composeForm.delete1.value="1";
				document.composeForm.message_ids.value=received;
	      		document.composeForm.submit();
	    	}
		}
	} 

	function batchTaskDelete()
  {
    if (confirm("<%= (String)myMessages.getMessage(myLocale, "check.task.delete") %>?"))
    {  
			var checked = 0;
    	var received="";
    
    	for (var j=0;j<document.taskForm.length;j++) 
    	{
      	currentElement = document.taskForm.elements[j];
       	if (currentElement.type=="checkbox")
       	{
        	if (currentElement.checked)
         	{
          	if (checked > 0) 
          		received += ",";
          	checked +=1;
          	received +=currentElement.value;
         	}
       	}
     	}

     	if (checked < 1)
     	{
     		alert("<%= (String)myMessages.getMessage(myLocale, "error.validation.missingTasks") %>");
     	}
    	else
     	{
     		document.taskForm.delete1.value="1";
				document.taskForm.task_ids.value=received;
      	document.taskForm.submit();
    	}
  	}
  } 

  	
  function deleteStation (stationCode,stationId)
  {
    if (confirm("<%= (String)myMessages.getMessage(myLocale, "check.delete") %>" + stationCode + "?"))
    {  
      document.stationForm.delete1.value="1";
      document.stationForm.stationId.value=stationId;
      document.stationForm.submit();
     }
  } 
  
  function deleteDeliveryCompany (deliveryCompanyId, deliveryCompanyName)
  {
    if (confirm("<%= (String)myMessages.getMessage(myLocale, "check.delivercompany.delete") %> " + deliveryCompanyName + "?"))
    {  
      document.MaintainDeliveryCompanyForm.delete1.value="1";
      document.MaintainDeliveryCompanyForm.deleteDeliveryCompanyId.value=deliveryCompanyId;
      document.MaintainDeliveryCompanyForm.submit();
     }
  } 
  
  function deleteDeliveryCoServiceLevel (serviceLevelId, serviceLevelDescription)
  {
    if (confirm("<%= (String)myMessages.getMessage(myLocale, "check.delivercompany.servicelevel.delete") %> " + serviceLevelDescription + "?"))
    {  
      document.MaintainDeliveryCompanyForm.delete1.value="1";
      document.MaintainDeliveryCompanyForm.deleteServiceLevelID.value=serviceLevelId;
      document.MaintainDeliveryCompanyForm.submit();
     }
  } 


  function deleteDeliveryCompanyStation (stationCode, deliverCoStationId )
  {
    if (confirm("<%= (String)myMessages.getMessage(myLocale, "check.delete") %> " + stationCode + "?"))
    {  
      document.MaintainDeliveryCompanyForm.delete1.value="1";
      document.MaintainDeliveryCompanyForm.deleteDelivCoStation.value=deliverCoStationId;
      document.MaintainDeliveryCompanyForm.submit();
     }
  } 
  
  function deleteShift (shiftName,shiftId)
  {
    if (confirm("<%= (String)myMessages.getMessage(myLocale, "check.delete") %>" + shiftName + "?"))
    {  
      document.shiftForm.delete1.value="1";
      document.shiftForm.shiftId.value=shiftId;
      document.shiftForm.submit();
    }
  } 
  
  function deleteAirport (airportcode,id)
  {
    if (confirm("<%= (String)myMessages.getMessage(myLocale, "check.delete") %>" + airportcode + "?"))
    {  
      document.airportForm.delete1.value="1";
      document.airportForm.id.value=id;
      document.airportForm.submit();
    }
  } 
  
  
  
  function deleteCompany (companyCode)
  {
    if (confirm("<%= (String)myMessages.getMessage(myLocale, "check.delete") %>" + companyCode + "?"))
    {  
      document.companyForm.delete1.value="1";
      document.companyForm.companyCode.value=companyCode;
      document.companyForm.submit();
    }
  } 
  
  
  function saveOHDTemporary(form)
  {
   if (confirm("<%= (String)myMessages.getMessage(myLocale, "check.save.temporary") %>"))
    { 
      document.OnHandForm.savetemp.value="1";
      document.OnHandForm.submit();
    }
  }

  function saveMassOHDTemporary(form)
  {
   if (confirm("<%= (String)myMessages.getMessage(myLocale, "check.save.temporary") %>"))
    { 
      document.OnHandForm.savetemp.value="1";

			submitMassform(form);


      document.OnHandForm.submit();
    }
  }
    
  function saveIncidentTemporary(form)
  {
     if (confirm("<%= (String)myMessages.getMessage(myLocale, "check.save.temporary") %>"))
      { 
        document.incidentForm.savetemp.value="1";
        document.incidentForm.submit();
      }
  }
  
   function submitMassform(form)
  {
  
    var bagtagnumbers="";
    for (var j=0;j<form.length;j++) {
      var currentElement = form.elements[j];
      var currentElementName = currentElement.name;
      if (currentElementName != "bagTagNumber" &&  currentElementName.indexOf("bagTagNumber") != -1)
      {
        if (currentElement.value != "")
        {
          if (bagtagnumbers != "")
          {
            bagtagnumbers += ",";
          }
          if (!checkClaimCheck(currentElement.value))
          {
            alert("<%= (String)myMessages.getMessage(myLocale, "colname.bag_tag_number") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.bagTag") %>"); 
            currentElement.focus();
            return false;
          }
          bagtagnumbers += currentElement.value;
        }
      }
    }
    //No Bag tag number supplied.
    if (bagtagnumbers == "")
    {
      alert("<%= (String)myMessages.getMessage(myLocale, "error.validation.missingBagTag") %>");
      return false;
    } 
    else{
      document.OnHandForm.bagTagNumber.value=bagtagnumbers;
    }
    return true;
  }
 

    
  function submitLzedform()
  {
    var checked = 0;
    var ohd_id="";
    
    for (var j=0;j<document.viewOHDLZedForm.length;j++) {
      
      currentElement = document.viewOHDLZedForm.elements[j];
       if (currentElement.type=="checkbox")
       {
        if (currentElement.checked)
         {
          if (checked > 0) ohd_id += ",";
          checked +=1;
          ohd_id +=currentElement.value;
         }
       }
     }

     if (checked < 1)
     {
       alert("<%= (String)myMessages.getMessage(myLocale, "error.validation.missingForward") %>");
     }
     else
     {
      document.forwardOnHandForm.batch_id.value=ohd_id;
      document.forwardOnHandForm.submit();
    }
  }



  function submitCompareOHDform()
  {
  	
    var checked = 0;
    var audit_ohd_id="";
    
    for (var j=0;j<document.auditOHDForm.length;j++) {
      
      currentElement = document.auditOHDForm.elements[j];
       if (currentElement.type=="checkbox")
       {
        if (currentElement.checked)
         {
          if (checked > 0) audit_ohd_id += ",";
          checked +=1;
          audit_ohd_id +=currentElement.value;
         }
       }
     }

     if (checked < 1)
     {
       alert("<%= (String)myMessages.getMessage(myLocale, "error.validation.missingOHDCompare") %>");
     }
     else
     {
      document.auditOHDForm.audit_ohd_id.value=audit_ohd_id;
      document.auditOHDForm.submit();
    }
	}

  function submitCompareLFDform()
  {
  	
    var checked = 0;
    var audit_lost_found_id="";
    
    for (var j=0;j<document.auditLostFoundForm.length;j++) {
      
      currentElement = document.auditLostFoundForm.elements[j];
       if (currentElement.type=="checkbox")
       {
        if (currentElement.checked)
         {
          if (checked > 0) audit_lost_found_id += ",";
          checked +=1;
          audit_lost_found_id +=currentElement.value;
         }
       }
     }

     if (checked < 1)
     {
       alert("<%= (String)myMessages.getMessage(myLocale, "error.validation.missingLFDCompare") %>");
     }
     else
     {
      document.auditLostFoundForm.audit_id.value=audit_lost_found_id;
      document.auditLostFoundForm.submit();
    }
	}
	
	
	function submitCompareShiftform()
  {
  	
    var checked = 0;
    var id="";
    
    for (var j=0;j<document.auditWorkShiftForm.length;j++) {
      
      currentElement = document.auditWorkShiftForm.elements[j];
       if (currentElement.type=="checkbox")
       {
        if (currentElement.checked)
         {
          if (checked > 0) id += ",";
          checked +=1;
          id +=currentElement.value;
         }
       }
     }

     if (checked < 1)
     {
       alert("<%= (String)myMessages.getMessage(myLocale, "error.validation.missingShiftCompare") %>");
     }
     else
     {
      document.auditWorkShiftForm.audit_id.value=id;
      document.auditWorkShiftForm.submit();
    }
	}
	
	
	function submitCompareLosscodeform()
  {
  	
    var checked = 0;
    var id="";
    
    for (var j=0;j<document.auditLosscodeForm.length;j++) {
      
      currentElement = document.auditLosscodeForm.elements[j];
       if (currentElement.type=="checkbox")
       {
        if (currentElement.checked)
         {
          if (checked > 0) id += ",";
          checked +=1;
          id +=currentElement.value;
         }
       }
     }

     if (checked < 1)
     {
       alert("<%= (String)myMessages.getMessage(myLocale, "error.validation.missingLosscodeCompare") %>");
     }
     else
     {
      document.auditLosscodeForm.audit_id.value=id;
      document.auditLosscodeForm.submit();
    }
	}


	function submitCompareAirportform()
  {
  	
    var checked = 0;
    var id="";
    
    for (var j=0;j<document.auditAirportForm.length;j++) {
      
      currentElement = document.auditAirportForm.elements[j];
       if (currentElement.type=="checkbox")
       {
        if (currentElement.checked)
         {
          if (checked > 0) id += ",";
          checked +=1;
          id +=currentElement.value;
         }
       }
     }

     if (checked < 1)
     {
       alert("<%= (String)myMessages.getMessage(myLocale, "error.validation.missingAirportCompare") %>");
     }
     else
     {
      document.auditAirportForm.audit_id.value=id;
      document.auditAirportForm.submit();
    }
	}
	
	function submitCompareGroupform()
  {
  	
    var checked = 0;
    var id="";
    
    for (var j=0;j<document.auditGroupForm.length;j++) {
      
      currentElement = document.auditGroupForm.elements[j];
       if (currentElement.type=="checkbox")
       {
        if (currentElement.checked)
         {
          if (checked > 0) id += ",";
          checked +=1;
          id +=currentElement.value;
         }
       }
     }

     if (checked < 1)
     {
       alert("<%= (String)myMessages.getMessage(myLocale, "error.validation.missingGroupCompare") %>");
     }
     else
     {
      document.auditGroupForm.audit_id.value=id;
      document.auditGroupForm.submit();
    }
	}
	
	
	function submitCompareAgentform()
  {
  	
    var checked = 0;
    var id="";
    
    for (var j=0;j<document.auditAgentForm.length;j++) {
      
      currentElement = document.auditAgentForm.elements[j];
       if (currentElement.type=="checkbox")
       {
        if (currentElement.checked)
         {
          if (checked > 0) id += ",";
          checked +=1;
          id +=currentElement.value;
         }
       }
     }
     if (checked < 1)
     {
       alert("<%= (String)myMessages.getMessage(myLocale, "error.validation.missingAgentCompare") %>");
     }
     else
     {
      document.auditAgentForm.audit_id.value=id;
      document.auditAgentForm.submit();
    }
	}
  
  function submitCompareStationform()
  {
  	
    var checked = 0;
    var id="";
    
    for (var j=0;j<document.auditStationForm.length;j++) {
      
      currentElement = document.auditStationForm.elements[j];
       if (currentElement.type=="checkbox")
       {
        if (currentElement.checked)
         {
          if (checked > 0) id += ",";
          checked +=1;
          id +=currentElement.value;
         }
       }
     }
     if (checked < 1)
     {
       alert("<%= (String)myMessages.getMessage(myLocale, "error.validation.missingStationCompare") %>");
     }
     else
     {
      document.auditStationForm.audit_id.value=id;
      document.auditStationForm.submit();
    }
	}
	
	
	function submitCompareCompanyform()
  {
    var checked = 0;
    var id="";
    
    for (var j=0;j<document.auditCompanyForm.length;j++) {
      
      currentElement = document.auditCompanyForm.elements[j];
       if (currentElement.type=="checkbox")
       {
        if (currentElement.checked)
         {
          if (checked > 0) id += ",";
          checked +=1;
          id +=currentElement.value;
         }
       }
     }
     if (checked < 1)
     {
       alert("<%= (String)myMessages.getMessage(myLocale, "error.validation.missingCompanyCompare") %>");
     }
     else
     {
      document.auditCompanyForm.audit_id.value=id;
      document.auditCompanyForm.submit();
    }
	}
  
  
  function submitCompareMBRform()
  {
  	
    var checked = 0;
    var audit_incident_id="";
    
    for (var j=0;j<document.auditMBRForm.length;j++) {
      
      currentElement = document.auditMBRForm.elements[j];
       if (currentElement.type=="checkbox")
       {
        if (currentElement.checked)
         {
          if (checked > 0) audit_incident_id += ",";
          checked +=1;
          audit_incident_id +=currentElement.value;
         }
       }
     }

     if (checked < 1)
     {
       alert("<%= (String)myMessages.getMessage(myLocale, "error.validation.missingMBRCompare") %>");
     }
     else
     {
      document.auditMBRForm.audit_incident_id.value=audit_incident_id;
      document.auditMBRForm.submit();
    }
	}

  function submitCompareClaimform()
  {
  	
    var checked = 0;
    var audit_claim_id ="";
    
    for (var j=0;j<document.auditMBRForm.length;j++) {
      
      currentElement = document.auditMBRForm.elements[j];
       if (currentElement.type=="checkbox")
       {
        if (currentElement.checked)
         {
          if (checked > 0) audit_claim_id += ",";
          checked +=1;
          audit_claim_id +=currentElement.value;
         }
       }
     }

     if (checked < 1)
     {
       alert("<%= (String)myMessages.getMessage(myLocale, "error.validation.missingClaimCompare") %>");
     }
     else
     {
      document.auditMBRForm.audit_claim_id.value=audit_claim_id;
      document.auditMBRForm.submit();
    }
	}	  
  
  function validateEditTask(form){
    for (var j=0;j<form.length;j++) {
      currentElement = form.elements[j];
      currentElementName=currentElement.name;
      if (currentElementName.indexOf("description") != -1){
        if (currentElement.value.length < 1){
          alert("<%= (String)myMessages.getMessage(myLocale, "header.tsk_desc") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
          currentElement.focus();
          return false;
        }
      }
      else if (currentElementName.indexOf("dispduedate") != -1)
      {
        if (currentElement.value.length < 1)
        {
          alert("<%= (String)myMessages.getMessage(myLocale, "header.tsk_due_date") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>"); 
          currentElement.focus();
          return false;
        }
        else
        {
          if (currentElement.value.length > 0 && !checkDate(currentElement.value))
          {
            alert("<%= (String)myMessages.getMessage(myLocale, "header.tsk_due_date") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.date") %>"); 
            currentElement.focus();
            return false;
          }
        }
      }
      else if (currentElementName.indexOf("dispreminderdate") != -1)
      {
        if (currentElement.value.length > 0 && !checkDate(currentElement.value))
       	{
       		alert("<%= (String)myMessages.getMessage(myLocale, "header.tsk_reminder_date") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.date") %>"); 
         	currentElement.focus();
         	return false;
        }
      }
      else if (currentElementName.indexOf("dispduetime") != -1)
      {
        if (currentElement.value.length > 0 && !checkTime(currentElement.value))
        {
          alert("<%= (String)myMessages.getMessage(myLocale, "header.tsk_due_time") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.time") %>"); 
          currentElement.focus();
          return false;
        }
      }
      else if (currentElementName.indexOf("dispremindertime") != -1)
      {
        if (currentElement.value.length > 0 && !checkTime(currentElement.value))
        {
          alert("<%= (String)myMessages.getMessage(myLocale, "header.tsk_reminder_time") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.time") %>"); 
          currentElement.focus();
          return false;
        }
      }
       
    }
    return true;
  }

  function validateBDO(form){
    for (var j=0;j<form.length;j++) {
      currentElement = form.elements[j];
      currentElementName=currentElement.name;
      if (currentElementName.indexOf("delivercompany_ID") != -1){
        if (currentElement.value.length < 1){
          alert("<%= (String)myMessages.getMessage(myLocale, "error.choose_deliver") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
          currentElement.focus();
          return false;
        }
      }
      else if (currentElementName.indexOf("servicelevel_ID") != -1)
      {
        if (currentElement.value.length < 1)
        {
          alert("<%= (String)myMessages.getMessage(myLocale, "error.choose_servicelevel") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>"); 
          currentElement.focus();
          return false;
        }
      }
       
    }
    return true;
  }


// -->
  </script>
