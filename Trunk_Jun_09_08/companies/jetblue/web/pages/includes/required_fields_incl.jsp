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

<%@page import="com.bagnet.nettracer.tracing.forms.IncidentForm"%>

  
  
  <jsp:include page="/pages/worldtracer/wt_required_fields.jsp"/>

function validatereq(form) {
	return true;
}

function validateWTCompanyForward(form) {
	return true;
}

function validatereqOHDFields(form) {
	return true;
}

function validatereqFields(form, formType) {
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
	if (returnValue == false) {
		return returnValue;
	} /* INSERT STARTS HERE */
	returnValue = validatereqWtIncFieldsClone(form, formType, false, firstPaxIndex, firstAddressIndex, firstItemIndex, firstClaimcheckIndex);
	if (returnValue == false) {
		return returnValue;
	} /* INSERT ENDS HERE */
	var bagIndices = [];
	var ccCount = 0;
	for (var j=0;j < form.length; j++) {
		currentElement = form.elements[j];
		currentElementName=currentElement.name;
		if (currentElementName.indexOf("].color") != -1) {
			var left = currentElementName.indexOf("[");
			var right = currentElementName.indexOf("]");
			bagIndices = bagIndices.concat(currentElementName.substring(left+1, right));
		} else if(currentElementName.indexOf("].claimchecknum") != -1 && currentElement.value.length > 0) {
			ccCount += 1;
		}
	}
	if(ccCount > bagIndices.length) {
		alert('<%= (String) bundle.getString( "error.validation.too.many.claimchecks")%>');
        document.getElementById("claimcheck[" + firstClaimcheckIndex +"].claimchecknum").focus();
        return false;
 	}
    return true;
}
    
function validatereqOHDForm(form) {
    returnValue = true;
    var theindex = 0;    
    returnValue = validatereqWtOHDForm(form);
    
    if (returnValue == false) {
    	return returnValue;
    }
    return true;
	/*
    for (var j=0;j < form.length; j++) {
        currentElement = form.elements[j];
        currentElementName=currentElement.name;

        if (currentElementName.indexOf("dispBagArriveDate") != -1) {  
            if (currentElement.value.length == 0) {
            	alert("<%=(String) bundle.getString(
                  	"colname.bag_arrived_date")%>" + " <%=(String) bundle.getString(
                  	"error.validation.isRequired")%>");
              	currentElement.focus();
              	return false;
            }
        } else if (currentElementName.indexOf("bagColor") != -1) {
            if (currentElement.value.length == 0) {
              	alert("<%=(String) bundle.getString(
                  	"colname.color")%>" + " <%=(String) bundle.getString(
                  	"error.validation.isRequired")%>");
              	currentElement.focus();
              	return false;
            }
        } else if (currentElementName.indexOf("bagType") != -1) {
            if (currentElement.value.length == 0) {
              	alert("<%=(String) bundle.getString(
                  	"colname.bagtype")%>" + " <%=(String) bundle.getString(
                  	"error.validation.isRequired")%>");
              	currentElement.focus();
              	return false;
            }
        } else if (currentElementName.indexOf("itinerarytype") != -1) {  
            if (currentElement.value == 0 || currentElement.value == 1) {
            	theindex = currentElementName.substring(13,14);
            }
        } else if (currentElementName.indexOf("[" + theindex + "].legfrom") != -1) {  
            if (currentElement.value.length == 0) {
              	alert("<%=(String) bundle.getString("colname.fromto")%>" + " <%=(String) bundle.getString(
                  	"error.validation.isRequired")%>");
              	currentElement.focus();
              	return false;
            }
        } else if (currentElementName.indexOf("[" + theindex + "].legto") != -1) {  
            if (currentElement.value.length == 0) {
              	alert("<%=(String) bundle.getString("colname.fromto")%>" + " <%=(String) bundle.getString(
                  	"error.validation.isRequired")%>");
              	currentElement.focus();
              	return false;
            }
        } else if (currentElementName.indexOf("[" + theindex + "].airline") != -1) {  
            if (currentElement.value.length == 0) {
              	alert("<%=(String) bundle.getString(
                  	"colname.flightnum")%>" + " <%=(String) bundle.getString(
                  	"error.validation.isRequired")%>");
              	currentElement.focus();
              	return false;
            }
        } else if (currentElementName.indexOf("[" + theindex + "].flightnum") != -1) {  
            if (currentElement.value.length == 0) {
              	alert("<%=(String) bundle.getString(
                  	"colname.flightnum")%>" + " <%=(String) bundle.getString(
                  	"error.validation.isRequired")%>");
              	currentElement.focus();
              	return false;
            }
        } else if (currentElementName.indexOf(".legfrom") != -1) {  
            if (currentElement.value.length == 0) {
               	alert("<%=(String) bundle.getString("colname.fromto")%>" + " <%=(String) bundle.getString(
                   	"error.validation.isRequired")%>");
               	currentElement.focus();
               	return false;
            }
        } else if (currentElementName.indexOf(".legto") != -1) {  
            if (currentElement.value.length == 0) {
               	alert("<%=(String) bundle.getString("colname.fromto")%>" + " <%=(String) bundle.getString(
                   	"error.validation.isRequired")%>");
               	currentElement.focus();
               	return false;
            }
        } else if (currentElementName.indexOf(".airline") != -1) {  
            if (currentElement.value.length == 0) {
               	alert("<%=(String) bundle.getString(
                   	"colname.flightnum")%>" + " <%=(String) bundle.getString(
                   	"error.validation.isRequired")%>");
               	currentElement.focus();
               	return false;
            }
        } else if (currentElementName.indexOf(".flightnum") != -1) {  
        	if (currentElement.value.length == 0) {
            	alert("<%=(String) bundle.getString(
                   	"colname.flightnum")%>" + " <%=(String) bundle.getString(
                   	"error.validation.isRequired")%>");
               	currentElement.focus();
               	return false;
        	}
		}
	}*/
}
   
function validatereqOHD(form) {
	return true;
}
  
function validatereqBEORN(form) {
	return true;
}
  
function validateReqBDO(form) {
	returnValue = true;
    
    returnValue = validateBDO(form);
    if (returnValue == false) {
    	return returnValue;
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
	inputs = document.getElementsByTagName("input");
	var invCount = 0;
	for(i = 0; i < inputs.length; i++) {
		if(inputs[i].id.indexOf("deleteinventory_" + bagNum) == 0) {
	    	invCount ++;
	    	if(invCount >= 3) break;
	    }
	}
	if(invCount < 3 && !isAware) {
	    isAware = confirm("An explanatory remark must be entered when reporting baggage with less than 3 content fields. Would you like to continue?");
	    document.forms[0].notifiedOfRequirements.value = true;
	    return isAware;
	}
	return true;
}

function validatereqWtIncFieldsClone(form, formType, requireContents, firstPaxIndex, firstAddressIndex, firstItemIndex, firstClaimcheckIndex) {
	var reqContentFields = true;
    var theindex = 0;
    var addressIndices = [];
    var bagIndices = [];
    var ccCount = 0;
    for (var j=0;j < form.length; j++) {
    	currentElement = form.elements[j];
    	currentElementName=currentElement.name;
      
    	if ((formType == 'damaged' || formType == 'pilfered') && !requireContents) {
        	reqContentFields = false;
      	}

      	if (currentElementName.indexOf("recordlocator") != -1) {  
          	if (currentElement.value.length == 0) {
            	alert("<%=(String) bundle.getString(
                	"colname.recordlocator")%>" + " <%=(String) bundle.getString(
                	"error.validation.isRequired")%>");
            	currentElement.focus();
            	return false;
          	}
      	} else if (currentElementName.indexOf("["+firstPaxIndex+"].lastname") != -1) {  
        	if (currentElement.value.length == 0) {
          		alert("<%=(String) bundle.getString(
              		"colname.last_name")%>" + " <%=(String) bundle.getString(
              		"error.validation.isRequired")%>");
          		currentElement.focus();
          		return false;
        	}
      	} else if (currentElementName.indexOf("["+firstPaxIndex+"].firstname") != -1) {
        	if (currentElement.value.length == 0) {
          		alert("<%= (String)bundle.getString( 
            		"colname.first_name") %>" + " <%= (String)bundle.getString( 
            		"error.validation.isRequired") %>");
          		currentElement.focus();
          		return false;
        	}
      	} else if (currentElementName.indexOf("["+firstAddressIndex+"].address1") != -1) {
        	var left = currentElementName.indexOf("[");
        	var right = currentElementName.indexOf("]");
        	addressIndices = addressIndices.concat(currentElementName.substring(left+1, right));
          
        	if (currentElement.value.length == 0) {
          		alert("<%=(String) bundle.getString(
              		"colname.street_addr")%>" + " <%=(String) bundle.getString(
              		"error.validation.isRequired")%>");
          		currentElement.focus();
          		return false;
        	}
      	} else if (currentElementName.indexOf("["+firstAddressIndex+"]address1") != -1) {
        	var left = currentElementName.indexOf("[");
        	var right = currentElementName.indexOf("]");
        	addressIndices = addressIndices.concat(currentElementName.substring(left+1, right));
          
        	if (currentElement.value.length == 0) {
          		alert("<%=(String) bundle.getString(
              		"colname.street_addr")%>" + " <%=(String) bundle.getString(
              		"error.validation.isRequired")%>");
          		currentElement.focus();
          		return false;
        	}
      	} else if (currentElementName.indexOf("["+firstAddressIndex+"].city") != -1) {  
        	if (currentElement.value.length == 0) {
          		alert("<%=(String) bundle.getString( "colname.city")%>" + " <%=(String) bundle.getString(
              		"error.validation.isRequired")%>");
          		currentElement.focus();
          		return false;
        	} 
      	} else if (currentElementName.indexOf("["+firstAddressIndex+"].zip") != -1) {  
          	if (currentElement.value.length == 0) {
            	alert("<%=(String) bundle.getString( "colname.zip")%>" + " <%=(String) bundle.getString(
                	"error.validation.isRequired")%>");
            	currentElement.focus();
            	return false;
          	} 
      	} else if (currentElementName.indexOf("["+firstAddressIndex+"].countrycode_ID") != -1) {
        	addressIndices = addressIndices.concat(currentElementName.substring(left+1, right));
          
        	if (currentElement.value.length == 0) {
          		alert("<%=(String) bundle.getString(
              		"colname.country")%>" + " <%=(String) bundle.getString(
              		"error.validation.isRequired")%>");
          		currentElement.focus();
          		return false;
        	}
      	} else if (currentElementName.indexOf("["+firstAddressIndex+"].state_ID") != -1) {  
	     	var pos = currentElementName.indexOf(".");
          	var str = currentElementName.substring(0,pos+1) + "countrycode_ID";
  
          	if (form.elements[str].value == "US" && currentElement.value.length ==0) {
            	alert("<%=(String) bundle.getString(
                  	"colname.state") + " " + bundle.getString(
                  	"error.state.required")%>");
            	currentElement.focus();
            	return false;
          	}
/*     	} else if (currentElementName.indexOf("].description") != -1 && currentElementName.indexOf("inventorylist[") != -1) {
        	if (currentElement.value.length == 0) {
          		alert("<%=(String) bundle.getString(
              		"colname.description")%>" + " <%=(String) bundle.getString(
              		"error.validation.isRequired")%>");
          		currentElement.focus();
          		return false;
        	}  */
      	} else if (currentElementName.indexOf("].damage") != -1) {
        	if (currentElement.value.length == 0) {
          		alert("<%=(String) bundle.getString(
              		"colname.damagedesc")%>" + " <%=(String) bundle.getString(
              		"error.validation.isRequired")%>");
          		currentElement.focus();
          		return false;
        	}
      	} else if (currentElementName.indexOf("itinerarytype") != -1) {  
        	if (currentElement.value == 0 || currentElement.value == 1) {
        		theindex = currentElementName.substring(13,14);
        	}
      	} else if (currentElementName.indexOf("[" + theindex + "].legfrom") != -1) {  
        	if (currentElement.value.length == 0) {
         		alert("<%=(String) bundle.getString("colname.fromto")%>" + " <%=(String) bundle.getString(
             		"error.validation.isRequired")%>");
          		currentElement.focus();
          		return false;
        	}
      	} else if (currentElementName.indexOf("[" + theindex + "].legto") != -1) {  
        	if (currentElement.value.length == 0) {
         		alert("<%=(String) bundle.getString("colname.fromto")%>" + " <%=(String) bundle.getString(
              		"error.validation.isRequired")%>");
          		currentElement.focus();
          		return false;
        	}
      	} else if (currentElementName.indexOf("[" + theindex + "].airline") != -1) {  
        	if (currentElement.value.length == 0) {
          		alert("<%=(String) bundle.getString(
             		"colname.flightnum")%>" + " <%=(String) bundle.getString(
              		"error.validation.isRequired")%>");
          		currentElement.focus();
          		return false;
        	}
      	} else if (currentElementName.indexOf("[" + theindex + "].flightnum") != -1) {  
        	if (currentElement.value.length == 0) {
          		alert("<%=(String) bundle.getString(
              		"colname.flightnum")%>" + " <%=(String) bundle.getString(
              		"error.validation.isRequired")%>");
          		currentElement.focus();
          		return false;
        	}
      	} else if (currentElementName.indexOf("[" + theindex + "].disdepartdate") != -1) {  
        	if (currentElement.value.length == 0) {
          		alert("<%=(String) bundle.getString(
              		"colname.departdate")%>" + " <%=(String) bundle.getString(
              		"error.validation.isRequired")%>");
          		currentElement.focus();
          		return false;
        	}
   /* 	} else if (currentElementName.indexOf("[" + theindex + "].disarrivedate") != -1) {  
          	if (currentElement.value.length == 0) {
            	alert("<%=(String) bundle.getString(
                	"colname.arrdate")%>" + " <%=(String) bundle.getString(
                	"error.validation.isRequired")%>");
            	currentElement.focus();
            	return false;
          	} */
      	} else if (currentElementName.indexOf("].color") != -1) { 
          	var left = currentElementName.indexOf("[");
          	var right = currentElementName.indexOf("]");
          	bagIndices = bagIndices.concat(currentElementName.substring(left+1, right)); 
          	if (currentElement.value.length == 0) {
          		alert("<%=(String) bundle.getString(
                	"colname.color")%>" + " <%=(String) bundle.getString(
              		"error.validation.isRequired")%>");
          		currentElement.focus();
          		return false;
          	}
      	} else if (currentElementName.indexOf("].bagtype") != -1) {  
        	if (currentElement.value.length == 0) {
          		alert("<%=(String) bundle.getString(
              		"colname.bagtype")%>" + " <%=(String) bundle.getString(
              		"error.validation.isRequired")%>");
          		currentElement.focus();
          		return false;
        	}
       	} else if (currentElementName.indexOf("].damage") != -1) {  
          	if (currentElement.value.length == 0) {
            	alert("<%=(String) bundle.getString(
                	"colname.damage")%>" + " <%=(String) bundle.getString(
                	"error.validation.isRequired")%>");
            	currentElement.focus();
            	return false;
          	}
      	} else if (currentElementName.indexOf("].lvlofdamage") != -1) {  
          	if (currentElement.value.length == 0) {
            	alert("<%=(String) bundle.getString(
                	"colname.lvldamage")%>" + " <%=(String) bundle.getString(
                	"error.validation.isRequired")%>");
            	currentElement.focus();
            	return false;
          	}
      	} else if (currentElementName.indexOf("["+firstItemIndex+"].article") != -1) {  
          	if (currentElement.value.length == 0) {
            	alert("<%=(String) bundle.getString(
                	"colname.article")%>" + " <%=(String) bundle.getString(
                	"error.validation.isRequired")%>");
            	currentElement.focus();
            	return false;
          	}
      	} else if (currentElementName.indexOf("].xdescelement_ID_1") != -1) {  
        	if (currentElement.value.length == 0) {
          		alert("<%=(String) bundle.getString(
                  	"colname.x_desc")%>" + " <%=(String) bundle.getString(
              		"error.validation.isRequired")%>");
          		currentElement.focus();
          		return false;
        	}
      	} else if (currentElementName.indexOf("numpassengers") != -1) {  
        	if (currentElement.value.length == 0) {
          		alert("<%=(String) bundle.getString(
              		"colname.num_pass")%>" + " <%=(String) bundle.getString(
              		"error.validation.isRequired")%>");
          		currentElement.focus();
          		return false;
        	}
/*      } else if (currentElementName.indexOf("].categorytype_ID") != -1) {  
        	if (currentElement.value.length == 0 && reqContentFields) {
      			alert("NOT WT!!");
          		alert("<%=(String) bundle.getString(
              		"colname.category")%>" + " <%=(String) bundle.getString(
              		"error.validation.isRequired")%>");
          		currentElement.focus();
          		return false;
        	}
	    } else if (currentElementName.indexOf("].description") != -1) {  
        	if (currentElement.value.length == 0 && reqContentFields) {
      			alert("NOT WT!!");
          		alert("<%=(String) bundle.getString(
              		"colname.ld.description")%>" + " <%=(String) bundle.getString(
              		"error.validation.isRequired")%>");
          		currentElement.focus();
          		return false;
        	} */
      	} else if (currentElementName.indexOf("].lvlofdamage") != -1) {  
        	if (currentElement.value.length == 0) {
          		alert("<%=(String) bundle.getString(
              		"colname.lvldamage")%>" + " <%=(String) bundle.getString(
              		"error.validation.isRequired")%>");
          		currentElement.focus();
          		return false;
        	} 
      	} else if(currentElementName.indexOf("].claimchecknum") != -1) {
      		if (currentElement.value.length > 0) {
        		ccCount += 1;
        	} else {
          		alert("<%=(String) bundle.getString(
              		"colname.claimnum")%>" + " <%=(String) bundle.getString(
              		"error.validation.isRequired")%>");
          		currentElement.focus();
          		return false;
        	}
      	} 
    }
    	
 	var bag0 = document.getElementsByName("theitem["+firstItemIndex+"].lnameonbag");
 	if(bag0 == null) {
    	alert("<%=(String) bundle.getString(
            "header.bag_info")%>" + " <%=(String) bundle.getString(
            "error.validation.isRequired")%>");
    	return false;
  	}

  	if(ccCount > bagIndices.length) {
        alert('<%= (String) bundle.getString( "error.validation.too.many.claimchecks")%>');
        document.getElementById("claimcheck["+firstClaimcheckIndex+"].claimchecknum").focus();
        return false;
  	}
    
    
/*	for (var j=0; j < bagIndices.length; j++) {
      	var index = bagIndices[j] * 20;
      	var contents = document.getElementsByName("inventorylist[" + index + "].description");
    
      	if (!contents && reqContentFields) {
      		alert("NOT WT!!");
        	alert("<%=(String) bundle.getString(
          		"colname.key_contents")%>" + " <%=(String) bundle.getString(
          		"error.validation.isRequired")%>");
        	document.getElementsByName("theitem[" + j + "].lnameonbag")[0].focus();
        	return false;
      	}
    } */
    
    return true;
}


