<%@ page contentType="text/javascript" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="org.apache.struts.action.Action" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>

<%
Agent a = (Agent) session.getAttribute("user");
ResourceBundle bundle = ResourceBundle.getBundle(
					"com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(a.getCurrentlocale()));

%>

  
  function validatereq(form) {
  	return true;
  }
  
  function validatereqFields(form, formType)
  {
	var bagIndices = [];
	
  	var theindex = 0;
  	var addressIndices = [];
  	for (var j=0;j<form.length;j++) {
  		
	    currentElement = form.elements[j];
	    currentElementName=currentElement.name;
	    var left = currentElementName.indexOf("[");
	    var right = currentElementName.indexOf("]");
		
		if (currentElementName.indexOf("lastname") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)bundle.getString( "colname.last_name") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("].color") != -1) {
	    	  bagIndices = bagIndices.concat(currentElementName.substring(left+1, right));
	    } else if (currentElementName.indexOf("firstname") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)bundle.getString( "colname.first_name") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("address1") != -1) {
	      
	      addressIndices = addressIndices.concat(currentElementName.substring(left+1, right));
	        
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)bundle.getString( "colname.street_addr") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("city") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)bundle.getString( "colname.city") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      } 
	    } else if (currentElementName.indexOf("].state_ID") != -1) {  

	    	var pos = currentElementName.indexOf(".");
	        var str = currentElementName.substring(0,pos+1) + "countrycode_ID";
	
	        if (form.elements[str].value == "US" && currentElement.value.length ==0) {
		        alert("<%= (String)bundle.getString( "colname.state") %>" + " is required if country is set to 'United States'");
		        currentElement.focus();
		        return false;
	      	}
		//new code starts
		
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

	    else if (currentElementName.indexOf("].categorytype_ID") != -1 &&
	               currentElementName.indexOf("inventorylist[") != -1 &&
	               formType == 'damaged')  {  
	          if (currentElement.value.length == 0)
	          {
	            alert("<%= (String)bundle.getString( "colname.category") %>" + 
	            " <%= (String)bundle.getString( "error.validation.isRequired") %>");
	            currentElement.focus();
	            return false;
	          }
	    }else if (formType != 'pilfered' && currentElementName.indexOf("].categorytype_ID") != -1) {
	          
	          if (currentElement.value.length == 0)
	          {
	            alert("<%= (String)bundle.getString( "colname.category") %>" + 
	            " <%= (String)bundle.getString( "error.validation.isRequired") %>");
	            currentElement.focus();
	            return false;
	          }
	    } else if (formType != 'pilfered' && currentElementName.indexOf("].description") != -1) {  
	          if (currentElement.value.length == 0)
	          {
	            alert("<%= (String)bundle.getString( "colname.description") %>" + 
	            " <%= (String)bundle.getString( "error.validation.isRequired") %>");
	            currentElement.focus();
	            return false;
	          }
	    } else if (formType == 'pilfered' && currentElementName.indexOf("].article") != -1) {  
	          if (currentElement.value.length == 0)
	          {
	            alert("<%= (String)bundle.getString( "colname.article") %>" + 
	            " <%= (String)bundle.getString( "error.validation.isRequired") %>");
	            currentElement.focus();
	            return false;
	          }
	    //new code ends     
	    } else if (currentElementName.indexOf("].description") != -1 &&
	               currentElementName.indexOf("inventorylist[") != -1 &&
	               (formType == 'damaged' ) ) 
		{
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)bundle.getString( "colname.description") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("].damage") != -1) {
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)bundle.getString( "colname.damagedesc") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("itinerarytype") != -1) {  
	      if (currentElement.value == 0 || currentElement.value == 1) theindex = currentElementName.substring(13,14);
	    } else if (currentElementName.indexOf("[" + theindex + "].legfrom") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)bundle.getString( "colname.fromto") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("[" + theindex + "].legto") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)bundle.getString( "colname.fromto") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("[" + theindex + "].airline") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)bundle.getString( "colname.flightnum") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("[" + theindex + "].flightnum") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)bundle.getString( "colname.flightnum") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("[" + theindex + "].disdepartdate") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)bundle.getString( "colname.departdate") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("].color") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)bundle.getString( "colname.color") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("].bagtype") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)bundle.getString( "colname.bagtype") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	    }
	
  	}

//	for (var j=0; j<bagIndices.length; j++) {
//		var index = bagIndices[j];
//		found = false;
//		for (var k = 0; k < iBagIndices.length; k++) {
//			if (iBagIndices[k] == index) {
//				found = true;
//			}
//		}
//		if (false) {
//			alert("<%= (String)bundle.getString( "error.minimum.one.description") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
//			return false;
//		}
//	}
	
  	for (var j=0;j<addressIndices.length;j++) {
  	    var index = addressIndices[j];
  		var mobile = document.getElementById("addresses[" + index + "].mobile");
  		var home = document.getElementById("addresses[" + index + "].homephone");
  		var work = document.getElementById("addresses[" + index + "].workphone");
  		  		
  		if (mobile.value.length == 0 && home.value.length== 0 && work.value.length == 0) {
	        alert("<%= (String)bundle.getString( "colname.mobile_ph") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
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
	        alert("<%= (String)bundle.getString( "colname.color") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("bagType") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)bundle.getString( "colname.bagtype") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
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


	  
	  function checkDeleteCount(bagNum, report_type)(bagNum) {
		  inputs = document.getElementsByTagName("input");
		  var invCount = 0;
		  for(i = 0; i < inputs.length; i++) {
		    if(inputs[i].name.indexOf("deleteinventory_" + bagNum) == 0) {
		      invCount ++;
		      if(invCount >= 2) break;
		    }
		  }
		  if(invCount < 2) {
		    alert("<%= (String)bundle.getString( "error.minimum.one.description") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
		    return false;
		  }
		  return true;
		}


  function validateReqBDO(form) {
    returnValue = true;
    
    returnValue = validateBDO(form);
    if (returnValue == false) { return returnValue; }


    return true;
  }
  function checkOhdDeleteCount(bagNum) { return true; }
