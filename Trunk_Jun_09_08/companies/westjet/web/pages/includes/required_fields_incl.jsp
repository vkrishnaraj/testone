<%@ page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%@ page import="org.apache.struts.action.Action"%>
<%@ page import="com.bagnet.nettracer.tracing.forms.IncidentForm"%>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>

<%
	Agent a = (Agent) session.getAttribute("user");

ResourceBundle bundle = ResourceBundle.getBundle(
		"com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(a.getCurrentlocale()));
%>



  
  
<%@page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%><jsp:include page="/pages/worldtracer/wt_required_fields.jsp"/>
 
 String.prototype.trim = function () {
    return this.replace(/^\s*/, "").replace(/\s*$/, "");
 }
 
 
 function  validatereqOHDFields(form) {
 
    for (var j=0;j<form.length;j++) {
       currentElement = form.elements[j];
	   currentElementName=currentElement.name;
	   if (currentElementName.indexOf("teletype_address1") != -1) {
		      if (currentElement.value.length < 1) {
		         alert("<%=(String) bundle.getString(
							"Teletype_Address")%>" + " <%=(String) bundle.getString(
							"error.validation.isRequired")%>");
		         currentElement.focus();
		         return false;
		      }
		}
		if (currentElementName.indexOf("teletype[0]") != -1) {
		      if (currentElement.value.length < 1) {
		         alert("<%=(String) bundle.getString(
							"Teletype_Address")%>" + " <%=(String) bundle.getString(
							"error.validation.isRequired")%>");
		         currentElement.focus();
		         return false;
		      }
		}
	}
	return true;
 }
 
  function validatereq(form)
  {
    return true;
  }
  
  function validateWTCompanyForward(form) {
 	 for (var j=0;j<form.length;j++) {
		  	currentElement = form.elements[j];
	    	currentElementName=currentElement.name;
			if (currentElementName.indexOf("bagtag") != -1)
		    {
		      if (currentElement.value.length < 1)
		      {
		         alert("<%=(String) bundle.getString( "Tag_Number")%>" + " <%=(String) bundle.getString(
							"error.validation.isRequired")%>");
		         currentElement.focus();
		         return false;
		      }
		        
		      else if (!checkClaimCheck(currentElement.value))
		      {
		        alert("<%=(String) bundle.getString( "Tag_Number")%>" + " <%=(String) bundle.getString(
							"error.validation.expedite")%>");
		        currentElement.focus();
		        return false;
		      }
		    }
		    else if (currentElementName.indexOf("teletype_address1") != -1)
		    {
		      if (currentElement.value.length < 1)
		      {
		         alert("<%=(String) bundle.getString(
							"Teletype_Address")%>" + " <%=(String) bundle.getString(
							"error.validation.isRequired")%>");
		         currentElement.focus();
		         return false;
		      }
		     }
	 }
		    return true;
  }
  
  function validatereqFields(form, formType)
  {
    returnValue = true;
    var addressIndices = [];
    var bagIndices = [];
    var ccCount = 0;
    

    var firstPaxIndex = -1;
    var firstAddressIndex = -1;
    var firstItemIndex = -1;
    var firstClaimcheckIndex = -1;
    
    isRemarkAbsent = true;
    
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
	} else if (currentElementName.indexOf("lastname") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)bundle.getString( "colname.last_name") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	} else if (currentElementName.indexOf("firstname") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)bundle.getString( "colname.first_name") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	} else if (currentElementName.indexOf("address1") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)bundle.getString( "colname.street_addr") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      } else {
	        var left = currentElementName.indexOf("[");
	        var right = currentElementName.indexOf("]");
	        addressIndices = addressIndices.concat(currentElementName.substring(left+1, right));	      
	      }
      } else if (currentElementName.indexOf("city") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)bundle.getString( "colname.city") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	  } else if (currentElementName.indexOf("countrycode_ID") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)bundle.getString( "colname.country") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	  } else if (currentElementName.indexOf("airline") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)bundle.getString( "colname.airline") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	  }
	  else if (currentElementName.indexOf("addresses[0].email") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)bundle.getString( "colname.email") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }

	      if (currentElement.value.indexOf("@noemail.com") != -1) {
	    	  var emailCustomer = document.getElementById("email_customer");
	    	  if (emailCustomer != null) {
	    		  emailCustomer.checked = false;
		      }
	    	  
	      }
	  }
     
      else if (currentElementName.indexOf("nonrevenue") != -1) {  
        if (currentElement.value != 0)
        {
          alert('<%=(String) bundle.getString("error.validation.requiredValue") + " " + bundle.getString( "colname.non_revenue") + " " + bundle.getString( "select.no" )%>');
          currentElement.focus();
          return false;
        }
      } 
      else if (currentElementName.indexOf("ticketnumber") != -1) {  
        if (currentElement.value.length != 0)
        {
          alert('<%=(String) bundle.getString("error.validation.leftBlank") + " " + bundle.getString("colname.ticket") %>');
          currentElement.focus();
          return false;
        }
      } 
      else if (currentElementName.indexOf(".jobtitle") != -1) {  
        if (currentElement.value.length != 0)
        {
          alert('<%=(String) bundle.getString("error.validation.leftBlank") + " " + bundle.getString("colname.job_title") %>');
          currentElement.focus();
          return false;
        }
      }       
      else if (currentElementName.indexOf("numpassengers") != -1) {  
        if (currentElement.value.length == 0)
        {
          alert('<%=(String) bundle.getString("colname.num_pass") + " " + (String) bundle.getString("error.validation.isRequired") %>');
          currentElement.focus();
          return false;
        }
      }
      
      else if (currentElementName.indexOf("courtesyreport") != -1) {  
          if (currentElement.value != 0)
          {
            alert('<%=(String) bundle.getString("error.validation.requiredValue") + " " + bundle.getString("colname.courtesy_report") + " " + bundle.getString( "select.no" ) %>');
            currentElement.focus();
            return false;
          }
        }
      else if (currentElementName.indexOf("disposal_status") != -1) {  
          if (currentElement.value.length != 0)
          {
            alert('<%=(String) bundle.getString("error.validation.leftBlank") + " " + bundle.getString("colname.disposal_status") %>');
            currentElement.focus();
            return false;
          }
        }
      else if(currentElementName.indexOf("].claimchecknum") != -1 && currentElement.value.length > 0) {
          ccCount += 1;
        }
      else if (currentElementName.indexOf("].claimchecknum") != -1) {
        if (currentElement.value.length == 0)
        {
          alert('<%= (String)bundle.getString("colname.bag_tag_number") + " " + (String)bundle.getString("error.validation.isRequired") %>');
          currentElement.focus();
          return false;
        }    
      }
      else if (currentElementName.indexOf("].color") != -1) {
    	  bagIndices = bagIndices.concat(currentElementName.substring(left+1, right));
      }
      else if (formType != 'pilfered' && formType != 'damaged' && currentElementName.indexOf("].description") != -1)
      {  
        if (currentElement.value.length == 0)
        {
          alert('<%= (String)bundle.getString("colname.description") + " " + (String)bundle.getString("error.validation.isRequired") %>');
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
      
      else if (currentElementName.indexOf("].remarktext") != -1 
      && currentElementName.indexOf("].remarktext2") < 0
      && currentElement.value.length > 0){
        isRemarkAbsent = false;
      }
    } 
    
    if (form.remarkEnteredWhenNotifiedOfRequirements.value != 'false' && isRemarkAbsent && checkContentCount(bagIndices) == false){
    	alert('<%= (String)bundle.getString("explanatory.remark") + " " + (String)bundle.getString("error.validation.isRequired") %>');
		return false; 
    }
    
    
    for (var j=0;j<addressIndices.length;j++) {
      var index = addressIndices[0];
      var mobile = document.getElementById("addresses[" + index + "].mobile");
      var home = document.getElementById("addresses[" + index + "].homephone");
      var work = document.getElementById("addresses[" + index + "].workphone");
          
      if (mobile.value.length == 0 && home.value.length== 0 && work.value.length == 0) {
        alert("<%= (String)bundle.getString( "colname.phone") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
        mobile.focus();
        return false;
      }
      break;
    }
    
    if(ccCount < bagIndices.length) {
  	  var answer = confirm('<%=(String) bundle.getString(  "confirm.more.bags.than.tags")%>');
	  	if(!answer) {
    		  return false;
	 	 }
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


  	 if (currentElementName.indexOf(".legfrom") != -1) {  
          if (currentElement.value.length == 0)
          {
               alert("<%= (String)bundle.getString( "colname.fromto") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
               currentElement.focus();
               return false;
          }
      } else if (currentElementName.indexOf(".legto") != -1) {  
          if (currentElement.value.length == 0)
          {
               alert("<%= (String)bundle.getString( "colname.fromto") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
               currentElement.focus();
               return false;
          }
      } else if (currentElementName.indexOf(".airline") != -1) {  
             if (currentElement.value.length == 0)
             {
	     		alert("<%= (String)bundle.getString( "colname.airline") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
               currentElement.focus();
               return false;
             }
       } else if (currentElementName.indexOf(".flightnum") != -1) {  
             if (currentElement.value.length == 0)
             {
	     		alert("<%= (String)bundle.getString( "colname.flightnum") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
               currentElement.focus();
               return false;
             }
       } else if (currentElementName.indexOf(".disdepartdate") != -1) {  
             if (currentElement.value.length == 0)
             {
	     		alert("<%= (String)bundle.getString( "colname.departdate") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
               currentElement.focus();
               return false;
             }
       } else if (currentElementName.indexOf("bagColor") != -1) {
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
        else if (!(currentElementName.indexOf("itinerary") != -1)) {
    	    if (currentElementName.indexOf("bagItinerary") != -1 && currentElementName.indexOf(".legfrom") != -1) {  
    	      if (currentElement.value.length == 0)
    	      {
    	        alert("<%= (String)bundle.getString( "colname.fromto") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
    	        currentElement.focus();
    	        return false;
    	      }
    	    } else if (currentElementName.indexOf("bagItinerary") != -1 && currentElementName.indexOf(".legto") != -1) {
    	      if (currentElement.value.length == 0)
    	      {
    	        alert("<%= (String)bundle.getString( "colname.fromto") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
    	        currentElement.focus();
    	        return false;
    	      }
    	    } else if (currentElementName.indexOf("bagItinerary") != -1 && currentElementName.indexOf(".airline") != -1) {
    	      if (currentElement.value.length == 0)
    	      {
    	        alert("<%= (String)bundle.getString( "colname.flightnum") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
    	        currentElement.focus();
    	        return false;
    	      }
    	    } else if (currentElementName.indexOf("bagItinerary") != -1 && currentElementName.indexOf(".flightnum") != -1) {
    	      if (currentElement.value.length == 0)
    	      {
    	        alert("<%= (String)bundle.getString( "colname.flightnum") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
    	        currentElement.focus();
    	        return false;
    	      }
    	    } else if (currentElementName.indexOf("bagItinerary") != -1 && currentElementName.indexOf(".disdepartdate") != -1) {
    	      if (currentElement.value.length == 0)
    	      {
    	        alert("<%= (String)bundle.getString( "colname.departdate") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
    	        currentElement.focus();
    	        return false;
    	      }
    	    }  else if (currentElementName.indexOf("bagItinerary") != -1 && currentElementName.indexOf(".disarrivedate") != -1) {
    	      if (currentElement.value.length == 0)
    	      {
    	        alert("<%= (String)bundle.getString( "colname.adate") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
    	        currentElement.focus();
    	        return false;
    	      }
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
    	    } else if (currentElementName.indexOf("itinerary") != -1 && currentElementName.indexOf(".disdepartdate") != -1) {
    	      if (currentElement.value.length == 0)
    	      {
    	        alert("<%= (String)bundle.getString( "colname.departdate") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
    	        currentElement.focus();
    	        return false;
    	      }
    	    } else if (currentElementName.indexOf("itinerary") != -1 && currentElementName.indexOf(".disarrivedate") != -1) {
    	      if (currentElement.value.length == 0)
    	      {
    	        alert("<%= (String)bundle.getString( "colname.adate") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
    	        currentElement.focus();
    	        return false;
    	      }
    	    }
          }
	  }
	  return true;
  }
  
  function validateClose(){
  		return window.confirm("Have you verified any Service Credit(s) for this file has been created?");
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
  function validateReqForward(form) {
  	return true;
  }

  <% if (session.getAttribute("incidentForm") != null) { %>
    var isAware = <%= ((IncidentForm) session.getAttribute("incidentForm")).isNotifiedOfRequirements()%>;
  <% } else { %>
    var isAware = false;
  <% }%>
  
  
  function checkDeleteCount(bagNum, report_type) {
  	  if (report_type == 1){ 
	  inputs = document.getElementsByTagName("input");
	  var invCount = 0;
	  for(i = 0; i < inputs.length; i++) {
	    if(inputs[i].name.indexOf("deleteinventory_" + bagNum) == 0) {
	      invCount ++;
	      if(invCount >= 4) break;
	    }
	  }
	  if(invCount <= 3 && !isAware) {
	    isAware = confirm("An explanatory remark must be entered when reporting baggage with less than 3 content fields. Would you like to continue?");
	    document.forms[0].notifiedOfRequirements.value = true;
	    document.forms[0].remarkEnteredWhenNotifiedOfRequirements.value = true;
	    return isAware;
	  }
	  }
	  return true;
	}

  function checkOhdDeleteCount() {
	  return true;
	}
  function checkContentCount(bi){
      
      inputs = document.getElementsByTagName("input");
	  var invCount = 0;
	  var ret = true;
	  for (var j = 0; j < bi.length; j++){
	  	invCount = 0;
	  	for(i = 0; i < inputs.length; i++) {
	   	  if(inputs[i].name.indexOf("deleteinventory_" + bi[j]) == 0) {
	      	invCount ++;
	      } 
	    }
	    if(invCount < 3){
	    	ret = false;
	    }
	  }
	  return ret;
  }
