<%@page import="java.text.MessageFormat"%>
<%@ page contentType="text/javascript" %> 
<%@ page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%@ page import="org.apache.struts.action.Action"%>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.List" %>

<%@page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%@page import="com.bagnet.nettracer.tracing.utils.OHDUtils"%>
<%@page import="com.bagnet.nettracer.tracing.db.OHD"%>
<%
	Agent a = (Agent) session.getAttribute("user");

	org.apache.struts.util.PropertyMessageResources myMessages = (org.apache.struts.util.PropertyMessageResources) request
			.getAttribute("org.apache.struts.action.MESSAGE");
	java.util.Locale myLocale = (java.util.Locale) session
			.getAttribute("org.apache.struts.action.LOCALE");
	
	ResourceBundle bundle = ResourceBundle.getBundle(
			"com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(a.getCurrentlocale()));
%>
  
  
  <jsp:include page="/pages/worldtracer/wt_required_fields.jsp"/>
 
  function validatereq(form) {
    return true;
  }
    
  function validateWTCompanyForward(form) {
 	return true;
  }
    
  function  validatereqOHDFields(form) {
	 var closed = false;
	 
    for (var j=0;j < form.length; j++) {
	    currentElement = form.elements[j];
	    currentElementName=currentElement.name;
		if (currentElement.name == "status.status_ID") {
			statusId = currentElement.options[currentElement.selectedIndex].value;
			if (statusId == <%= TracingConstants.OHD_STATUS_CLOSED %>) {
    			closed = true;
  			}
		} else if (currentElementName.indexOf("bagColor") != -1) {
			if (currentElement.value.length == 0) {
		        alert("<%=(String) bundle.getString(
										"colname.color")%>" + " <%=(String) bundle.getString("error.validation.isRequired")%>");
		        currentElement.focus();
		        return false;
			}
	    } else if (currentElementName.indexOf("bagType") != -1) {
			if (currentElement.value.length == 0) {
		        alert("<%=(String) bundle.getString("colname.bagtype")%>" + " <%=(String) bundle.getString("error.validation.isRequired")%>");
		        currentElement.focus();
		        return false;
			}
	     }
	 }
	 
	 if (closed == true) {
		 for (var j=0; j < form.length; j++) {
		 	currentElement = form.elements[j];
		 	currentElementName=currentElement.name;
		    if (currentElement.name == "disposal_status.status_ID" && currentElement.options[currentElement.selectedIndex].value == "") {
		    	alert('<%=(String) bundle.getString("colname.disposal_status") + " " + bundle.getString("error.validation.isRequired") %>');
            	currentElement.focus();
            	return false;
		    }
		 }
	 }
	 
	 return true;
  }

  function  validatereqPXFFields(form) {
	     return true;
  }
  function validatereqFields(form, formType)
  {
    returnValue = true;

	var isEdit = false;
    var hasRemarkText  = false;
	var baggageItinerary = false;
    var hasPassengerItinerary = false;
    var hasPermanentAddressChecked =  false;
    var firstPermanentAddressCheckboxId = -1;
    
    var firstPaxIndex = -1;
    var firstAddressIndex = -1;
    var firstItemIndex = -1;
    var firstClaimcheckIndex = -1;
    var i = -1;
    var departStation=false;
    var transStation=false;
    var destStation=false;
    var anyStation=false;
    
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
      } else if (currentElementName.indexOf("].status.description") != -1) {
        i++;
      } else if (currentElementName.indexOf("].lossCode") != -1) {
      		if(lossCodeChange!=false){
  				alert("<%=(String) bundle
					.getString( "loss.code.remark")%>" + " <%=(String) bundle.getString(
					"error.validation.isRequired")%>");
     		
  				return false;
  			}
  			if (currentElement!=null && currentElement.value=="0" && ppucheck==i){
	          alert("<%= (String)bundle.getString("colname.loss.code") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
	          currentElement.focus();
	          return false;
        	}
        	
        	departStation=codeStationMap[currentElement.value+"depart"];
        	transStation=codeStationMap[currentElement.value+"transfer"];
        	destStation=codeStationMap[currentElement.value+"destination"];
        	anyStation=codeStationMap[currentElement.value+"any"];
        } else if (currentElementName.indexOf("].faultStation_id") != -1) {
      		if(currentElement.value!=null && currentElement.value.length>0){
	      		hasPaxItin=false;
	      		faultStationCode=null;
	      		if(typeof currentElement.options!="undefined"){
	      			faultStationCode=currentElement.options[currentElement.selectedIndex].text;
	      		
		      		var paxItinList=document.getElementById("pax_itin");
		      		var paxItinNum=0;
		      		var currentPaxItin=0;
		      		for(var m=0; m < paxItinList.childNodes.length; m++){
		      			if(m%2!=0){
		      			
		      				var paxItin=paxItinList.children[m];
		      				if(paxItin.childNodes.length>0){
		      						paxItinNum++;
		      					}
		      				}
		      		}
		      		
		      		for(var m=0; m < paxItinList.childNodes.length; m++){
		      			if(m%2!=0){
		      				var paxItin=paxItinList.children[m];
		      				if(paxItin.childNodes.length>0){
		      					currentPaxItin++;
		      					for (var k=0; k < paxItin.children[1].children[0].children[0].children[0].childNodes.length; k++) {
							      itinElement = paxItin.children[1].children[0].children[0].children[0].children[k];
							      itinElementName=null;
							      if(typeof itinElement != "undefined")
							      	itinElementName=itinElement.name;
							      		
					      			if(typeof itinElementName != "undefined" && itinElementName != null && itinElementName.indexOf("].legfrom") != -1){
					      				if(itinElement.value.toUpperCase()==faultStationCode && ((currentPaxItin==1 && departStation) || (currentPaxItin!=1 && transStation) )){
					      					hasPaxItin=true;
					      				}
					      			}
					      			if(typeof itinElementName != "undefined" && itinElementName != null && itinElementName.indexOf("].legto") != -1){
					      				if(itinElement.value.toUpperCase()==faultStationCode && ((currentPaxItin!=paxItinNum && transStation) || (currentPaxItin==paxItinNum && destStation) )){
					      					hasPaxItin=true;
					      				}
					      			}
					      			
					      			if(anyStation){
					      				hasPaxItin=true;
					      			}
					      		}
				      		}
			      		}
		      		}
		      		
		      		if(!hasPaxItin){
		  				alert("<%=(String) bundle
							.getString( "error.fault.pax.itin")%>");
		     			currentElement.focus();
		  				return false;
		  			}
	  			}
  			} else {
  				if (currentElement!=null && currentElement.value.length<1 && ppucheck==i){
		          alert("<%= (String)bundle.getString("colname.fault.station") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
		          currentElement.focus();
		          return false;
	        	}
  			}
      	} else if (currentElementName.indexOf("addresses[") != -1 && currentElementName.indexOf("addresses[") < currentElementName.indexOf("].permanent")) {
	      	if (!hasPermanentAddressChecked) {
	      		if (currentElement.checked) {
	      			hasPermanentAddressChecked = currentElement.checked;
	      		} else if (firstPermanentAddressCheckboxId == -1) {
	      			firstPermanentAddressCheckboxId = currentElement.id; //save the first checkbox
	      		}
	      	}
	   } else if (currentElementName.indexOf("remark[") != -1 && currentElementName.indexOf("remark[") < currentElementName.indexOf("].remarktext")) {
	      	if (!hasRemarkText) {
				hasRemarkText = true;
	    	 }
	   } else if (currentElementName.indexOf("].itinerarytype") != -1) {
	      	if (currentElement.value == '0') { //Passenger Itinerary --> value=0
	      		if (!hasPassengerItinerary) {
	      			hasPassengerItinerary = true;
	      		}
	      	} else if (currentElement.value == '1') { //Baggage Itinerary --> value=1
	      		if (!baggageItinerary) {
	      			baggageItinerary = true;
	      		}
	      	}
	   } else if (currentElementName == 'incident_ID' && 0 < currentElement.value.length) {
	    	isEdit = true;
	   }
    }
    
    if (!hasRemarkText && !isEdit) {
		alert("<%= (String)bundle.getString( "colname.remark") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
		currentElement = document.getElementById('addremark');
		if (currentElement) {
			currentElement.focus();
		} 
		return false;
    } else if (!hasPermanentAddressChecked) {
		if (firstPermanentAddressCheckboxId == -1) {
			alert("<%= (String)bundle.getString( "colname.is_permanent") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
			currentElement = document.getElementById('addPassengerNum');
		} else {
			alert("<%= (String)bundle.getString( "colname.is_permanent") %>" + " <%= (String)bundle.getString( "button.check") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
			currentElement = document.getElementById(firstPermanentAddressCheckboxId);
		}
		if (currentElement) {
			currentElement.focus();
		} 
		return false;
    } else if (!hasPassengerItinerary) {
		alert("<%= (String)bundle.getString( "header.itinerary") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
		currentElement = document.getElementById('addpassitNum');
		if (currentElement) {
			currentElement.focus();
		} 
		return false;
    } else if (!baggageItinerary) {
		alert("<%= (String)bundle.getString( "header.bag_itinerary") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
		currentElement = document.getElementById('addbagitNum');
		if (currentElement) {
			currentElement.focus();
		} 
		return false;
    }
    
    returnValue = validatereqWtIncFields(form, formType, false, firstPaxIndex, firstAddressIndex, firstItemIndex, firstClaimcheckIndex);
    if (returnValue == false) { return returnValue; }
    
    for (var j=0;j < form.length; j++) {
	  	currentElement = form.elements[j];
	  	currentElementName=currentElement.name;
		
		//incident validation
		if (currentElementName.indexOf("recordlocator") != -1) {  
		      if (currentElement.value.length == 0) {
		        alert("<%= (String)bundle.getString( "colname.recordlocator") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
			    currentElement.focus();
			    return false;
		      }
		} 
		
		//passenger validation
		else if (currentElementName.indexOf("].lastname") != -1) {
			if (currentElement.value.length == 0)
	        {
	          alert("<%=(String) bundle.getString(
	              "colname.last_name")%>" + " <%=(String) bundle.getString(
	              "error.validation.isRequired")%>");
	          currentElement.focus();
	          return false;
	        }  
		}
		else if (currentElementName.indexOf("].firstname") != -1) {
			if (currentElement.value.length == 0)
	        {
	          alert("<%=(String) bundle.getString(
	              "colname.first_name")%>" + " <%=(String) bundle.getString(
	              "error.validation.isRequired")%>");
	          currentElement.focus();
	          return false;
	        }    
		}
		else if (currentElementName.indexOf("passenger["+firstPaxIndex+"].salutation")!=-1){
		      if (currentElement.value.length == 0 || currentElement.value == '0') {
		        alert("<%= (String)bundle.getString( "colname.salutation") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
			    currentElement.focus();
			    return false;
		      }
		} else if (currentElementName.indexOf("addresses["+firstAddressIndex+"].zip") != -1) {  
		      if (currentElement.value.length == 0) {
		        alert("<%= (String)bundle.getString( "colname.zip") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
			    currentElement.focus();
			    return false;
		      }
		} else if (currentElementName.indexOf("addresses["+firstAddressIndex+"].mobile") != -1) {
			  var homephone = document.getElementsByName(currentElementName.replace('].mobile', '].homephone'))[0].value;
			  var workphone = document.getElementsByName(currentElementName.replace('].mobile', '].workphone'))[0].value;
			  var mobile = document.getElementsByName(currentElementName)[0].value;		  
		      if (homephone.length == 0 && workphone.length == 0 && mobile.length == 0) {
		        alert("<%= (String)bundle.getString( "colname.phone") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
			    currentElement.focus();
			    return false;
		      }
		}
		
		//baggage validation
		else if (currentElementName.indexOf("claimchecknum") != -1  && formType != 'pilfered') {  
		      if (currentElement.value.length == 0) {
		        alert("<%= (String)bundle.getString( "colname.claimnum") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
			    currentElement.focus();
			    return false;
		      }
		} else if (currentElementName.indexOf("manufacturer_ID") != -1  && formType != 'pilfered') {  
		      if (currentElement.value.length == 0) {
		        alert("<%= (String)bundle.getString( "colname.manufacturer") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
			    currentElement.focus();
			    return false;
		      }
		}
		
		//remark validation
		else if (currentElementName.indexOf("remarktext") != -1 && !isEdit) {  
		      if (currentElement.value.length == 0) {
		        alert("<%= (String)bundle.getString( "colname.remark") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
			    currentElement.focus();
			    return false;
		      }
		}
		
		//missing articles validation
		else if (currentElementName.indexOf("article[") != -1) {
			  if (currentElementName.indexOf("].statusId") != -1) {   
			      if (currentElement.value.length == 0 || currentElement.value == '0') {
			        alert("<%= (String)bundle.getString( "colname.item.status") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
				    currentElement.focus();
				    return false;
			      }
		      } else if (currentElementName.indexOf("].article") != -1) {   
			      if (currentElement.value.length == 0) {
			        alert("<%= (String)bundle.getString( "colname.article") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
				    currentElement.focus();
				    return false;
			      }
		      } else if (currentElementName.indexOf("].description") != -1) {   
			      if (currentElement.value.length == 0) {
			        alert("<%= (String)bundle.getString( "colname.desc") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
				    currentElement.focus();
				    return false;
			      }
		      }
		}
		
		//baggage check information validation
		else if (currentElementName.indexOf("numpassengers") != -1) { 
			  <%int minPassengers = 1;%> 
		      if (!minimumInteger(currentElement.value, <%=minPassengers%>)) {
		        alert("<%= (String)bundle.getString( "colname.num_pass") %>" + " <%= MessageFormat.format(bundle.getString( "error.validation.minimum.requiredValue"), minPassengers) %>");
			    currentElement.focus();
			    return false;
		      }
		      
		} else if (currentElementName.indexOf("numbagchecked") != -1) { 
			  <%int minBagChecked = 1;%> 
		      if (!minimumInteger(currentElement.value, <%=minBagChecked%>)) {
		        alert("<%= (String)bundle.getString( "colname.num_bag_checked") %>" + " <%= MessageFormat.format(bundle.getString( "error.validation.minimum.requiredValue"), minBagChecked) %>");
			    currentElement.focus();
			    return false;
		      }
		} else if (currentElementName.indexOf("numbagreceived") != -1) { 
			  <%int minBagReceived = 0;%> 
		      if (!minimumInteger(currentElement.value, <%=minBagReceived%>)) {
		        alert("<%= (String)bundle.getString( "colname.bags_rec") %>" + " <%= MessageFormat.format(bundle.getString( "error.validation.minimum.requiredValue"), minBagReceived) %>");
			    currentElement.focus();
			    return false;
		      }
		}

	}
    
    return true;
  }
    
  function validatereqOHDForm(form) {
    returnValue = true;
    
    for (var j=0;j < form.length; j++) {
	    currentElement = form.elements[j];
	    currentElementName=currentElement.name;
	    if (currentElementName.indexOf("disposal_status.status_ID") != -1) {
      		var disposeStatus=currentElement.options[currentElement.selectedIndex].value;
      		if(disposeStatus!="" && disposeStatus==<%=TracingConstants.ITEM_STATUS_DISPOSED_LOCALLY%>){
      			if(disposeLocal!=false){
      				alert("<%=(String) bundle
							.getString( "disposal.remark")%>" + " <%=(String) bundle.getString(
							"error.validation.isRequired")%>");
	        		
      				return false;
      			}
      			
      		}
      	}
    }
    
    return validatereqOHDFields(form);
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
    var i=0;
    for (var j=0;j < form.length;j++) {
      currentElement = form.elements[j];
      currentElementName=currentElement.name;
      
	  if(currentElementName.indexOf("bagchosen") != -1){
	  	if(currentElement.id.length > "bagchosen".length){
	  		i=currentElement.id.substring("bagchosen".length,currentElement.id.length);
	  	}
	  
	  } else if (currentElementName.indexOf("disppickupdate") != -1)
      {
      		if(currentElement.value.length == 0){
	      		alert("<%= (String)bundle.getString("colname.pickupdatetime") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
	          	currentElement.focus();
	          	return false;
      		} else if (!checkDate(currentElement.value)) {
			        alert("<%= (String)bundle.getString("colname.pickupdatetime") %>" + " <%= (String)bundle.getString("error.validation.date") %>");
			        currentElement.focus();
			        return false;
		    }
      } else if(currentElementName.indexOf("disppickuptime") != -1){
     		if(currentElement.value.length == 0){
	      		alert("<%= (String)bundle.getString("colname.pickupdatetime") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
	          	currentElement.focus();
	          	return false;
      		} else if (!checkTime(currentElement.value)) {
			        alert("<%= (String)bundle.getString("colname.pickupdatetime") %>" + " <%= (String)bundle.getString("error.validation.time") %>");
			        currentElement.focus();
			        return false;
		    }
      } else if(currentElementName.indexOf("].noAddFees") != -1){
     	otherSelect=document.getElementById("theitem["+i+"].other");
      	specialConditions=document.getElementById("theitem["+i+"].specialCondition");
      	
      	if(currentElement.checked && (otherSelect.value!="0" || specialConditions.value!="0")){
      		alert("<%= (String)bundle.getString("error.noadds.with.otherselect") %>");
			        currentElement.focus();
			        return false;
      	}
      	
      	if(!currentElement.checked && otherSelect.value=="0" && specialConditions.value=="0"){
      		alert("<%= (String)bundle.getString("error.must.select.noadds.other") %>");
			        currentElement.focus();
			        return false;
      	}
	    
      } else if(currentElementName.indexOf("].specialCondition") != -1 || currentElementName.indexOf("].other") != -1){
      	noAddFees=document.getElementById("theitem["+i+"].noAddFees");
      	if(currentElement.value!="0" && noAddFees.checked){
      		alert("<%= (String)bundle.getString("error.noadds.with.otherselect") %>");
			        currentElement.focus();
			        return false;
      	}
      
      } else if (currentElementName.indexOf("].lossCode") != -1){
        var isbagchosen=document.getElementById("bagchosen"+i);
        if ((isbagchosen==null || isbagchosen.checked==true) && currentElement!=null && currentElement.value=="0"){
          alert("<%= (String)bundle.getString("colname.loss.code") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
          currentElement.focus();
          return false;
        }
      }
      else if (currentElementName.indexOf("].faultStation_id") != -1)
      {
      
        var isbagchosen=document.getElementById("bagchosen"+i);
        if ((isbagchosen==null || isbagchosen.checked==true) && currentElement.value.length < 1)
        {
          alert("<%= (String)bundle.getString("colname.fault.station") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>"); 
          currentElement.focus();
          return false;
        }
      }
      else if (currentElementName.indexOf("remark") != -1)
      {
      	if(lossCodeChange!=false){
	      	trimValue=currentElement.value.replace(/\s*/g, "");
	        if (trimValue.length < 1)
	        {
			  alert("<%=(String) bundle.getString( "loss.code.remark")%>" + " <%=(String) bundle.getString("error.validation.isRequired")%>");
	          currentElement.focus();
	          return false;
	        }
        }
      }
    }
    returnValue = validateBDO(form);
    if (returnValue == false) { return returnValue; }

    return true;
  }
  
  function validateReqForward(form) {
  	return true;
  }
  
  function validateTemplateForm(form) {
  		var name = document.getElementById("name");
  		if (name.value == null || name.value.length == 0) {
  			alert('<%=(String) bundle.getString( "colname.template.name") + " " + (String) bundle.getString("error.validation.isRequired")%>');
  			name.focus();
  			return false;
 		}
 		return true;
  }
  
  function checkDeleteCount(bagNum, report_type) { return true; }
  function checkOhdDeleteCount(bagNum) { return true; }
  function validateClose(){ return true; }