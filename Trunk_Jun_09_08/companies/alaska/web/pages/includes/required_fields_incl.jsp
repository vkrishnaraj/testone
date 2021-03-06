<%@ page contentType="text/javascript" %> 
<%@ page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%@ page import="org.apache.struts.action.Action"%>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>

<%
	Agent a = (Agent) session.getAttribute("user");

ResourceBundle bundle = ResourceBundle.getBundle(
		"com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(a.getCurrentlocale()));

%>
<%@page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>

  
  
  <jsp:include page="/pages/worldtracer/wt_required_fields.jsp"/>
 
  function validatereq(form)
  {
    return true;
  }

  function validateUSAirArrivalAndDepartureDatesRule(i,arrayName)
  {
	returnValue = true;
	
	var format = '<%= a.getDateformat().getFormat() %>';
	var arrivalField = document.getElementById(arrayName + i +"].disarrivedate");
    var departureField = document.getElementById(arrayName + i +"].disdepartdate");
       	
    if(arrivalField!=null && departureField!=null) {
      	var arrival=arrivalField.value;
        var departure=departureField.value;
          	
   		var myDateDepart=getDateFromFormat(departure,format);
   		var myDateArrive=getDateFromFormat(arrival,format);
    	   
		var one_day_plus_one=1000*60*60*24 + 1;
		var diffDateDepartDateArrive = myDateArrive - myDateDepart;
		if((diffDateDepartDateArrive < one_day_plus_one) && (diffDateDepartDateArrive >= 0)) {
			returnValue = true;
		} 
    }
       	
	
    return returnValue;
  }
  
  function validatereqFields(form, formType)
  {
    returnValue = true;
    
    var firstPaxIndex = -1;
    var firstAddressIndex = -1;
    var firstItemIndex = -1;
    var firstClaimcheckIndex = -1;
    var myItineraryDateIndex = -1;
    
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
    
    
    for (var j=0;j < form.length; j++) {
	  currentElement = form.elements[j];
	  currentElementName=currentElement.name;

      if (currentElementName.indexOf("address1") != -1) {
        var left = currentElementName.indexOf("[");
        var right = currentElementName.indexOf("]");

      } else if (currentElementName.indexOf("[" + firstAddressIndex + "].zip") != -1) {  
        
        var pos = currentElementName.indexOf(".");
        var str = currentElementName.substring(0,pos+1) + "countrycode_ID";
 
      }

      else if (currentElementName.indexOf("theitinerary[") != -1 && currentElementName.indexOf("disdepartdate") != -1) {  
          
          var left = currentElementName.indexOf("[");
          var right = currentElementName.indexOf("]");
          myItineraryDateIndex=currentElementName.substring(left+1, right);
	      if (currentElement.value.length != 0)
	      {
		    retVal = validateUSAirArrivalAndDepartureDatesRule(myItineraryDateIndex,'theitinerary[');
		    
	      }
      } 
      
      else if (currentElementName.indexOf("].color") != -1) { 
        var left = currentElementName.indexOf("[");
        var right = currentElementName.indexOf("]");
       
        if (currentElement.value.length == 0)
        {
          alert("<%=(String) bundle.getString(
                  "colname.color")%>" + " <%=(String) bundle.getString(
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        }
      } else if (currentElementName.indexOf("].bagtype") != -1) {  
        if (currentElement.value.length == 0)
        {
          alert("<%=(String) bundle.getString(
              "colname.bagtype")%>" + " <%=(String) bundle.getString(
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        }
        
      }
    }
    

    var mobile = document.getElementById("addresses[" + firstAddressIndex + "].mobile");
    var home = document.getElementById("addresses[" + firstAddressIndex + "].homephone");
    var work = document.getElementById("addresses[" + firstAddressIndex + "].workphone");

  }
    
  function validatereqOHDForm(form) {
    return true;
    
    
    returnValue = true;
    var addressIndices = [];

    
    for (var j=0;j < form.length; j++) {
      currentElement = form.elements[j];
      if (currentElement.name == "status.status_ID") {
        statusId = currentElement.options[currentElement.selectedIndex].value;
        if (statusId == <%= TracingConstants.OHD_STATUS_CLOSED %>) {
          return true;
        }
      }
    }

    
    returnValue = validatereqWtOHDForm(form);
    if (returnValue == false) { return returnValue; }
    
    for (var j=0;j < form.length; j++) {
      currentElement = form.elements[j];
      currentElementName=currentElement.name;


      if (currentElementName.indexOf("dispBagArriveDate") != -1)
      {  
        if (currentElement.value.length == 0)
        {
          alert("<%= (String)bundle.getString( "colname.bag_arrived_date") %>" + 
          " <%= (String)bundle.getString( "error.validation.isRequired") %>");
          currentElement.focus();
          return false;
        }
      }
      
      else if (currentElementName.indexOf("].description") != -1)
      {  
        if (currentElement.value.length == 0)
        {
          alert("<%= (String)bundle.getString( "colname.description") %>" + 
          " <%= (String)bundle.getString( "error.validation.isRequired") %>");
          currentElement.focus();
          return false;
        }
      }
      else if (currentElementName.indexOf("itinerarylist[") != -1 && currentElementName.indexOf("disdepartdate") != -1) {  
          
          var left = currentElementName.indexOf("[");
          var right = currentElementName.indexOf("]");
          myItineraryDateIndex=currentElementName.substring(left+1, right);
	      if (currentElement.value.length != 0)
	      {
		    retVal = validateUSAirArrivalAndDepartureDatesRule(myItineraryDateIndex,'itinerarylist[');
		    if (retVal == false) { return false; }
	      }
      } 
    }
    return true;
  }
   
  function validatereqOHD(form)
  {
    return true;

  }
	
  function validateReqForward(form) {
        var theindex = 0;
    for (var j=0;j<form.length;j++) {
      currentElement = form.elements[j];
      currentElementName=currentElement.name;
      
        if (!(currentElementName.indexOf("bagItinerary") != -1)) {
          if (currentElementName.indexOf("itinerary") != -1 && currentElementName.indexOf(".legfrom") != -1) {  
            if (currentElement.value.length == 0)
            {
              alert("<%= (String)bundle.getString( "colname.fromto") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
              currentElement.focus();
              return false;
            }
          } else if (currentElementName.indexOf("itinerary") != -1 && currentElementName.indexOf(".legto") != -1) {
            if (currentElement.value.length == 0)
            {
              alert("<%= (String)bundle.getString( "colname.fromto") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
              currentElement.focus();
              return false;
            }
          } else if (currentElementName.indexOf("itinerary") != -1 && currentElementName.indexOf(".airline") != -1) {
            if (currentElement.value.length == 0)
            {
              alert("<%= (String)bundle.getString( "colname.flightnum") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
              currentElement.focus();
              return false;
            }
          } else if (currentElementName.indexOf("itinerary") != -1 && currentElementName.indexOf(".flightnum") != -1) {
            if (currentElement.value.length == 0)
            {
              alert("<%= (String)bundle.getString( "colname.flightnum") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
              currentElement.focus();
              return false;
            }
          } else if (currentElementName.indexOf("disdepartdate") != -1) {
            if (currentElement.value.length == 0)
            {
              alert("<%= (String)bundle.getString( "colname.departdate") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
              currentElement.focus();
              return false;
            }
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
      
        if (currentElementName.indexOf("lossCode") != -1)
        {  
          if (currentElement.value.length == 0)
          {
            alert("<%= (String)bundle.getString( "colname.losscode") %>" + 
            " <%= (String)bundle.getString( "error.validation.isRequired") %>");
            currentElement.focus();
            return false;
          }
        }
        else if (currentElementName.indexOf("faultStation") != -1)
        {  
          if (currentElement.value.length == 0)
          {
            alert("<%= (String)bundle.getString( "colname.faultstation") %>" + 
            " <%= (String)bundle.getString( "error.validation.isRequired") %>");
            currentElement.focus();
            return false;
          }
        }
        else if (!(currentElementName.indexOf("bagItinerary") != -1)) {
    	    if (currentElementName.indexOf("itinerary") != -1 && currentElementName.indexOf(".legfrom") != -1) {  
    	      if (currentElement.value.length == 0)
    	      {
    	        alert("<%= (String)bundle.getString( "colname.fromto") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
    	        currentElement.focus();
    	        return false;
    	      }
    	    } else if (currentElementName.indexOf("itinerary") != -1 && currentElementName.indexOf(".legto") != -1) {
    	      if (currentElement.value.length == 0)
    	      {
    	        alert("<%= (String)bundle.getString( "colname.fromto") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
    	        currentElement.focus();
    	        return false;
    	      }
    	    } else if (currentElementName.indexOf("itinerary") != -1 && currentElementName.indexOf(".airline") != -1) {
    	      if (currentElement.value.length == 0)
    	      {
    	        alert("<%= (String)bundle.getString( "colname.flightnum") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
    	        currentElement.focus();
    	        return false;
    	      }
    	    } else if (currentElementName.indexOf("itinerary") != -1 && currentElementName.indexOf(".flightnum") != -1) {
    	      if (currentElement.value.length == 0)
    	      {
    	        alert("<%= (String)bundle.getString( "colname.flightnum") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
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
          alert("<%= (String)bundle.getString( "colname.deliverydate") %>" + 
          " <%= (String)bundle.getString( "error.validation.isRequired") %>");
          currentElement.focus();
          return false;
        }
      }
    }

    return true;
  }

  function checkDeleteCount(bagNum, report_type) { return true; }
  function checkOhdDeleteCount(bagNum) { return true; }
