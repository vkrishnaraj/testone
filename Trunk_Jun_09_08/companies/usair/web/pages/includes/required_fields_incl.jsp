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
		} else {
			returnValue = false;
			departureField.focus();
			alert("<%= (String)bundle.getString( "error.usair.arrivalanddeparturedates.rule") %>");
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
    var pasTable=document.getElementById("passid");
    var expressItin=document.getElementById("passItin");
  	var pasItinTable=document.getElementById("hidexItinerary");
  	var bagItinTable=document.getElementById("bagItin");
  	if(pasTable==null) 	{
  	  alert("<%=(String) bundle.getString("error.minimum.passengers")%>");
  	  document.getElementsByName("addPassenger")[0].focus();
  	  return false;
  	}
  	if(pasItinTable==null && expressItin==null)	{
  	  alert('<%=(String) bundle.getString("error.minimum.passitin")%>');
  	  document.getElementsByName("addpassit")[0].focus();
      return false;
  	}
  	if(bagItinTable==null && expressItin==null)	{
  	  alert('<%=(String) bundle.getString("error.minimum.bagitin")%>');
  	  document.getElementsByName("addbagit")[0].focus();
      return false;
  	}
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
 
        if (form.elements[str].value == "US" && currentElement.value.length ==0) {
          alert("<%=(String) bundle.getString(
            "colname.zip") + " " + bundle.getString(
            "error.state.required")%>");
          currentElement.focus();
          return false;
        }
      }
      
	 	else if (currentElementName.indexOf("numRonKitsIssued") != -1) {
			if (currentElement.value == -1) {
				alert("<%= (String)bundle.getString("please.select.a.value.for") %>" + " " + " <%= (String)bundle.getString("number.ron.kits.issued") %>");
		        currentElement.focus();
		        return false;
			}
		}
	
	    else if (currentElementName.indexOf("replacementBagIssued") != -1) {
			if (currentElement.value == -1) {
				alert("<%= (String)bundle.getString("please.select.a.value.for") %>" + " " + " <%= (String)bundle.getString("replacement.bag.issued") %>");
		        currentElement.focus();
		        return false;
			}
		}
      
      
      else if (currentElementName.indexOf(".email") != -1) {  
        if (!checkEmail(currentElement.value))
        {
          alert("<%=(String) bundle.getString(
              "ld_pass_email")%>" + " <%=(String) bundle.getString(
              "error.validation.email")%>");
          currentElement.focus();
          return false;
        }
      } 
      else if (currentElementName.indexOf(".disarrivedate") != -1) {  
        if (currentElement.value.length == 0)
        {
          alert("<%=(String) bundle.getString(
              "colname.arrdate")%>" + " <%=(String) bundle.getString(
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        } 
      } 
      else if (currentElementName.indexOf("numpassengers") != -1)
      {  
        if (currentElement.value.length == 0)
        {
          alert("<%= (String)bundle.getString( "colname.num_pass") %>" + 
          " <%= (String)bundle.getString( "error.validation.isRequired") %>");
          currentElement.focus();
          return false;
        }
      }
      else if (currentElementName.indexOf("numbagchecked") != -1)
      {  
        if (currentElement.value.length == 0)
        {
          alert("<%= (String)bundle.getString( "colname.num_bag_checked") %>" + 
          " <%= (String)bundle.getString( "error.validation.isRequired") %>");
          currentElement.focus();
          return false;
        }
      }
      else if (currentElementName.indexOf("numbagreceived") != -1)
      {  
        if (currentElement.value.length == 0)
        {
          alert("<%= (String)bundle.getString( "colname.bags_rec") %>" + 
          " <%= (String)bundle.getString( "error.validation.isRequired") %>");
          currentElement.focus();
          return false;
        }
      } 
      else if (currentElementName.indexOf("checkedlocation") != -1)
      {  
        if (currentElement.value.length == 0 || currentElement.value == "0")
        {
          alert("<%= (String)bundle.getString( "colname.bag_loc") %>" + 
          " <%= (String)bundle.getString( "error.validation.isRequired") %>");
          currentElement.focus();
          return false;
        }
      }
      
      else if (currentElementName.indexOf("].article") != -1)
      {  
        if (currentElement.value.length == 0 || currentElement.value == "0")
        {
          alert("<%= (String)bundle.getString( "colname.article") %>" + 
          " <%= (String)bundle.getString( "error.validation.isRequired") %>");
          currentElement.focus();
          return false;
        }
      }
      else if (currentElementName.indexOf("theitinerary[") != -1 && currentElementName.indexOf("disdepartdate") != -1) {  
          
          var left = currentElementName.indexOf("[");
          var right = currentElementName.indexOf("]");
          myItineraryDateIndex=currentElementName.substring(left+1, right);
	      if (currentElement.value.length != 0)
	      {
		    retVal = validateUSAirArrivalAndDepartureDatesRule(myItineraryDateIndex,'theitinerary[');
		    if (retVal == false) { return false; }
	      }
      } 
      
    }
    

    var mobile = document.getElementById("addresses[" + firstAddressIndex + "].mobile");
    var home = document.getElementById("addresses[" + firstAddressIndex + "].homephone");
    var work = document.getElementById("addresses[" + firstAddressIndex + "].workphone");
        
    if (mobile.value.length == 0 && home.value.length== 0 && work.value.length == 0) {
      alert("<%= (String)bundle.getString( "colname.phone") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
      mobile.focus();
      return false;
    }

  }
  
  function validatereqOHDForm(form) {
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
    for (var j=0;j < form.length;j++) {
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
  	for (var j=0;j< form.length;j++) {
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
