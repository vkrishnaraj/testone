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
    
    returnValue = validatereqWtIncFields(form, formType, false);
    if (returnValue == false) { return returnValue; }
    
    for (var j=0;j < form.length; j++) {
	  currentElement = form.elements[j];
	  currentElementName=currentElement.name;

      if (currentElementName.indexOf("address1") != -1) {
        var left = currentElementName.indexOf("[");
        var right = currentElementName.indexOf("]");
        addressIndices = addressIndices.concat(currentElementName.substring(left+1, right));
      } else if (currentElementName.indexOf("].zip") != -1) {  
        
        var pos = currentElementName.indexOf(".");
        var str = currentElementName.substring(0,pos+1) + "countrycode_ID";
 
        if (form.elements[str].value == "US" && currentElement.value.length ==0) {
          alert("<%=(String) myMessages.getMessage(myLocale,
            "colname.zip") + " " + myMessages.getMessage(myLocale,
            "error.state.required")%>");
          currentElement.focus();
          return false;
        }
      } 
      else if (currentElementName.indexOf(".disarrivedate") != -1) {  
        if (currentElement.value.length == 0)
        {
          alert("<%=(String) myMessages.getMessage(myLocale,
              "colname.arrdate")%>" + " <%=(String) myMessages.getMessage(myLocale,
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        }
      } 
      else if (currentElementName.indexOf("numpassengers") != -1)
      {  
        if (currentElement.value.length == 0)
        {
          alert("<%= (String)myMessages.getMessage(myLocale, "colname.num_pass") %>" + 
          " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
          currentElement.focus();
          return false;
        }
      }
      else if (currentElementName.indexOf("numbagchecked") != -1)
      {  
        if (currentElement.value.length == 0)
        {
          alert("<%= (String)myMessages.getMessage(myLocale, "colname.num_bag_checked") %>" + 
          " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
          currentElement.focus();
          return false;
        }
      }
      else if (currentElementName.indexOf("numbagreceived") != -1)
      {  
        if (currentElement.value.length == 0)
        {
          alert("<%= (String)myMessages.getMessage(myLocale, "colname.bags_rec") %>" + 
          " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
          currentElement.focus();
          return false;
        }
      } 
      else if (currentElementName.indexOf("checkedlocation") != -1)
      {  
        if (currentElement.value.length == 0 || currentElement.value == "0")
        {
          alert("<%= (String)myMessages.getMessage(myLocale, "colname.bag_loc") %>" + 
          " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
          currentElement.focus();
          return false;
        }
      }
      
      else if (currentElementName.indexOf("].article") != -1)
      {  
        if (currentElement.value.length == 0 || currentElement.value == "0")
        {
          alert("<%= (String)myMessages.getMessage(myLocale, "colname.article") %>" + 
          " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
          currentElement.focus();
          return false;
        }
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
  }
    
  function validatereqOHDForm(form) {
    returnValue = true;
    var addressIndices = [];
    
    returnValue = validatereqWtOHDForm(form);
    if (returnValue == false) { return returnValue; }
    
    for (var j=0;j < form.length; j++) {
      currentElement = form.elements[j];
      currentElementName=currentElement.name;


      if (currentElementName.indexOf("dispBagArriveDate") != -1)
      {  
        if (currentElement.value.length == 0)
        {
          alert("<%= (String)myMessages.getMessage(myLocale, "colname.bag_arrived_date") %>" + 
          " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
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
