<%@ page contentType="text/javascript" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%@ page import="org.apache.struts.action.Action"%>
<%@page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>

<%
Agent a = (Agent) session.getAttribute("user");
ResourceBundle bundle = ResourceBundle.getBundle(
					"com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(a.getCurrentlocale()));

%>


  
  <jsp:include page="/pages/worldtracer/wt_required_fields.jsp"/>
 
  function validatereq(form)
  {
    return true;
  }
  
  function validatereqFields(form, formType)
  {
    var theindex = 0;
    var addressIndices = [];
    var bagIndices = [];
    var reqContentFields = true;
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
    
    for (var j=0;j < form.length; j++) {
	  currentElement = form.elements[j];
	  currentElementName=currentElement.name;

	    if (currentElementName.indexOf("recordlocator") != -1) {  

	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)bundle.getString( "colname.recordlocator") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	    }

      if (currentElementName.indexOf("[" + firstPaxIndex + "].lastname") != -1) {  
        if (currentElement.value.length == 0)
        {
          alert("<%=(String) bundle.getString(
              "colname.last_name")%>" + " <%=(String) bundle.getString(
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        }
      }
      else if (currentElementName.indexOf("[" + firstPaxIndex + "].firstname") != -1) {
        if (currentElement.value.length == 0)
        {
          alert("<%= (String)bundle.getString( 
            "colname.first_name") %>" + " <%= (String)bundle.getString( 
            "error.validation.isRequired") %>");
          currentElement.focus();
          return false;
        }
      }
      
    else if (currentElementName.indexOf("[" + firstAddressIndex + "].address1") != -1) {
        var left = currentElementName.indexOf("[");
        var right = currentElementName.indexOf("]");
        addressIndices = addressIndices.concat(currentElementName.substring(left+1, right));
          
        if (currentElement.value.length == 0)
        {
          alert("<%=(String) bundle.getString(
              "colname.street_addr")%>" + " <%=(String) bundle.getString(
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        }
      } else if (currentElementName.indexOf("[" + firstAddressIndex + "]address1") != -1) {
        var left = currentElementName.indexOf("[");
        var right = currentElementName.indexOf("]");
        addressIndices = addressIndices.concat(currentElementName.substring(left+1, right));
          
        if (currentElement.value.length == 0)
        {
          alert("<%=(String) bundle.getString(
              "colname.street_addr")%>" + " <%=(String) bundle.getString(
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        }
      }
      
      else if (currentElementName.indexOf("[" + firstAddressIndex + "].city") != -1) {  
        if (currentElement.value.length == 0)
        {
          alert("<%=(String) bundle.getString( "colname.city")%>" + " <%=(String) bundle.getString(
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        } 
      } 
      else if (currentElementName.indexOf("[" + firstAddressIndex + "].countrycode_ID") != -1) {
        addressIndices = addressIndices.concat(currentElementName.substring(left+1, right));
          
        if (currentElement.value.length == 0)
        {
          alert("<%=(String) bundle.getString(
              "colname.country")%>" + " <%=(String) bundle.getString(
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        }
      }
      else if (currentElementName.indexOf("[" + firstAddressIndex + "].state_ID") != -1) {  

        var pos = currentElementName.indexOf(".");
          var str = currentElementName.substring(0,pos+1) + "countrycode_ID";
  
          if (form.elements[str].value == "US" && currentElement.value.length ==0) {
            alert("<%=(String) bundle.getString(
                  "colname.state") + " " + bundle.getString(
                  "error.state.required")%>");
            currentElement.focus();
            return false;
          }
      }
      
      else if (currentElementName.indexOf("].description") != -1 &&
                 currentElementName.indexOf("inventorylist[") != -1 &&
                 (formType == 'damaged' || formType == 'pilfered') && reqContentFields) 
    {
        if (currentElement.value.length == 0)
        {
          alert("<%=(String) bundle.getString(
              "colname.description")%>" + " <%=(String) bundle.getString(
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        }
      } else if (currentElementName.indexOf("].damage") != -1) {
        if (currentElement.value.length == 0)
        {
          alert("<%=(String) bundle.getString(
              "colname.damagedesc")%>" + " <%=(String) bundle.getString(
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        }
      } else if (currentElementName.indexOf("itinerarytype") != -1) {  
        if (currentElement.value == 0 || currentElement.value == 1) theindex = currentElementName.substring(13,14);
      } else if (currentElementName.indexOf("[" + theindex + "].legfrom") != -1) {  
        if (currentElement.value.length == 0)
        {
          alert("<%=(String) bundle
              .getString( "colname.fromto")%>" + " <%=(String) bundle.getString(
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        }
      } else if (currentElementName.indexOf("[" + theindex + "].legto") != -1) {  
        if (currentElement.value.length == 0)
        {
          alert("<%=(String) bundle
              .getString( "colname.fromto")%>" + " <%=(String) bundle.getString(
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        }
      } else if (currentElementName.indexOf("[" + theindex + "].airline") != -1) {  
        if (currentElement.value.length == 0)
        {
          alert("<%=(String) bundle.getString(
              "colname.flightnum")%>" + " <%=(String) bundle.getString(
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        }
      } else if (currentElementName.indexOf("[" + theindex + "].flightnum") != -1) {  
        if (currentElement.value.length == 0)
        {
          alert("<%=(String) bundle.getString(
              "colname.flightnum")%>" + " <%=(String) bundle.getString(
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        }
      } else if (currentElementName.indexOf("[" + theindex + "].disdepartdate") != -1) {  
        if (currentElement.value.length == 0)
        {
          alert("<%=(String) bundle.getString(
              "colname.departdate")%>" + " <%=(String) bundle.getString(
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        }
      } else if (currentElementName.indexOf("].color") != -1) { 
        var left = currentElementName.indexOf("[");
        var right = currentElementName.indexOf("]");
        bagIndices = bagIndices.concat(currentElementName.substring(left+1, right)); 
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

      else if (currentElementName.indexOf("address1") != -1) {
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
      else if (currentElementName.indexOf("].categorytype_ID") != -1) {  
        if (currentElement.value.length == 0)
        {
          alert("<%=(String) bundle.getString(
              "colname.category")%>" + " <%=(String) bundle.getString(
              "error.validation.isRequired")%>");
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
    
    var theindex = 0;
    var reqContentFields = true;
    
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
      
      else if (currentElementName.indexOf("itinerarytype") != -1) {  
        if (currentElement.value == 0 || currentElement.value == 1) theindex = currentElementName.substring(14,15);
      } else if (currentElementName.indexOf("[" + theindex + "].legfrom") != -1) {  
        if (currentElement.value.length == 0)
        {
          alert("<%=(String) bundle
              .getString( "colname.fromto")%>" + " <%=(String) bundle.getString(
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        }
      } else if (currentElementName.indexOf("[" + theindex + "].legto") != -1) {  
        if (currentElement.value.length == 0)
        {
          alert("<%=(String) bundle
              .getString( "colname.fromto")%>" + " <%=(String) bundle.getString(
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        }
      } else if (currentElementName.indexOf("[" + theindex + "].airline") != -1) {  
        if (currentElement.value.length == 0)
        {
          alert("<%=(String) bundle.getString(
              "colname.flightnum")%>" + " <%=(String) bundle.getString(
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        }
      } else if (currentElementName.indexOf("[" + theindex + "].flightnum") != -1) {  
        if (currentElement.value.length == 0)
        {
          alert("<%=(String) bundle.getString(
              "colname.flightnum")%>" + " <%=(String) bundle.getString(
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        }
      } else if (currentElementName.indexOf("[" + theindex + "].disdepartdate") != -1) {  
        if (currentElement.value.length == 0)
        {
          alert("<%=(String) bundle.getString(
              "colname.departdate")%>" + " <%=(String) bundle.getString(
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        }
      }
      else if (currentElementName.indexOf("bagColor") != -1) { 
        if (currentElement.value.length == 0)
        {
          alert("<%=(String) bundle.getString(
                  "colname.color")%>" + " <%=(String) bundle.getString(
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        }
      } else if (currentElementName.indexOf("bagType") != -1) {  
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
  
  function validateReqBDO(form) {
    returnValue = true;
    
    returnValue = validateBDO(form);
    if (returnValue == false) { return returnValue; }


    return true;
  }
  
  function checkDeleteCount(bagNum, report_type) { return true; }
  function checkOhdDeleteCount(bagNum) { return true; }
