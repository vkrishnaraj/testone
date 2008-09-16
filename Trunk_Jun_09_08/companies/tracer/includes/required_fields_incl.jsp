<%@ page language="java" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="org.apache.struts.action.Action" %>
<%
	Agent a = (Agent) session.getAttribute("user");

	org.apache.struts.util.PropertyMessageResources myMessages = (org.apache.struts.util.PropertyMessageResources) request
			.getAttribute("org.apache.struts.action.MESSAGE");
	java.util.Locale myLocale = (java.util.Locale) session
			.getAttribute("org.apache.struts.action.LOCALE");
%>
  <script language="javascript">
  <!--
  function validatereq(form)
  {
 		return true;
  }
  
  function validatereqFields(form, formType)
  {
  	return true;
  }
  function validatereqWtIncFields(form, formType)
  {
    var theindex = 0;
  	var addressIndices = [];
  	var bagIndices = [];
  	for (var j=0;j<form.length;j++) {
	    currentElement = form.elements[j];
	    currentElementName=currentElement.name;
		
		if (currentElementName.indexOf("lastname") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%=(String) myMessages.getMessage(myLocale,
							"colname.last_name")%>" + " <%=(String) myMessages.getMessage(myLocale,
							"error.validation.isRequired")%>");
	        currentElement.focus();
	        return false;
	      }
	      else if ( currentElement.value.search(/[a-zA-Z ]/)) {
	      	alert("<%=(String) myMessages.getMessage(myLocale,
							"colname.last_name")%>" + " <%=(String) myMessages.getMessage(myLocale,
							"error.validation.alpha")%>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("address1") != -1) {
	      var left = currentElementName.indexOf("[");
	      var right = currentElementName.indexOf("]");
	      addressIndices = addressIndices.concat(currentElementName.substring(left+1, right));
	        
	      if (currentElement.value.length == 0)
	      {
	        alert("<%=(String) myMessages.getMessage(myLocale,
							"colname.street_addr")%>" + " <%=(String) myMessages.getMessage(myLocale,
							"error.validation.isRequired")%>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("address1") != -1) {
	      var left = currentElementName.indexOf("[");
	      var right = currentElementName.indexOf("]");
	      addressIndices = addressIndices.concat(currentElementName.substring(left+1, right));
	        
	      if (currentElement.value.length == 0)
	      {
	        alert("<%=(String) myMessages.getMessage(myLocale,
							"colname.street_addr")%>" + " <%=(String) myMessages.getMessage(myLocale,
							"error.validation.isRequired")%>");
	        currentElement.focus();
	        return false;
	      }
	    }
	    
	    else if (currentElementName.indexOf("city") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%=(String) myMessages.getMessage(myLocale, "colname.city")%>" + " <%=(String) myMessages.getMessage(myLocale,
							"error.validation.isRequired")%>");
	        currentElement.focus();
	        return false;
	      } 
	    } 
	    else if (currentElementName.indexOf("countrycode_ID") != -1) {
	      addressIndices = addressIndices.concat(currentElementName.substring(left+1, right));
	        
	      if (currentElement.value.length == 0)
	      {
	        alert("<%=(String) myMessages.getMessage(myLocale,
							"colname.country")%>" + " <%=(String) myMessages.getMessage(myLocale,
							"error.validation.isRequired")%>");
	        currentElement.focus();
	        return false;
	      }
	    }
	    else if (currentElementName.indexOf("].state_ID") != -1) {  
	    	// check country
	    	var pos = currentElementName.indexOf(".");
	        var str = currentElementName.substring(0,pos+1) + "countrycode_ID";
	
	        if (form.elements[str].value == "US" && currentElement.value.length ==0) {
		        alert("<%=(String) myMessages.getMessage(myLocale,
									"colname.state") + myMessages.getMessage(myLocale,
                  "error.state.required")%>");
		        currentElement.focus();
		        return false;
	      	}
	    }	    
	    else if (currentElementName.indexOf("].description") != -1 &&
	               currentElementName.indexOf("inventorylist[") != -1 &&
	               (formType == 'damaged' || formType == 'pilfered') ) 
		{
	      if (currentElement.value.length == 0)
	      {
	        alert("<%=(String) myMessages.getMessage(myLocale,
							"colname.description")%>" + " <%=(String) myMessages.getMessage(myLocale,
							"error.validation.isRequired")%>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("].damage") != -1) {
	      if (currentElement.value.length == 0)
	      {
	        alert("<%=(String) myMessages.getMessage(myLocale,
							"colname.damagedesc")%>" + " <%=(String) myMessages.getMessage(myLocale,
							"error.validation.isRequired")%>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("itinerarytype") != -1) {  
	      if (currentElement.value == 0 || currentElement.value == 1) theindex = currentElementName.substring(13,14);
	    } else if (currentElementName.indexOf("[" + theindex + "].legfrom") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%=(String) myMessages
							.getMessage(myLocale, "colname.fromto")%>" + " <%=(String) myMessages.getMessage(myLocale,
							"error.validation.isRequired")%>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("[" + theindex + "].legto") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%=(String) myMessages
							.getMessage(myLocale, "colname.fromto")%>" + " <%=(String) myMessages.getMessage(myLocale,
							"error.validation.isRequired")%>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("[" + theindex + "].airline") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%=(String) myMessages.getMessage(myLocale,
							"colname.flightnum")%>" + " <%=(String) myMessages.getMessage(myLocale,
							"error.validation.isRequired")%>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("[" + theindex + "].flightnum") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%=(String) myMessages.getMessage(myLocale,
							"colname.flightnum")%>" + " <%=(String) myMessages.getMessage(myLocale,
							"error.validation.isRequired")%>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("[" + theindex + "].disdepartdate") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%=(String) myMessages.getMessage(myLocale,
							"colname.departdate")%>" + " <%=(String) myMessages.getMessage(myLocale,
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
	        alert("<%=(String) myMessages.getMessage(myLocale,
									"colname.color")%>" + " <%=(String) myMessages.getMessage(myLocale,
							"error.validation.isRequired")%>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("].bagtype") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%=(String) myMessages.getMessage(myLocale,
							"colname.bagtype")%>" + " <%=(String) myMessages.getMessage(myLocale,
							"error.validation.isRequired")%>");
	        currentElement.focus();
	        return false;
	      }
	      
	    }
	    else if (currentElementName.indexOf("].xdescelement_ID_1") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%=(String) myMessages.getMessage(myLocale,
									"colname.x_desc")%>" + " <%=(String) myMessages.getMessage(myLocale,
							"error.validation.isRequired")%>");
	        currentElement.focus();
	        return false;
	      }
	    }
	   else if (currentElementName.indexOf("numpassengers") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%=(String) myMessages.getMessage(myLocale,
							"colname.num_pass")%>" + " <%=(String) myMessages.getMessage(myLocale,
							"error.validation.isRequired")%>");
	        currentElement.focus();
	        return false;
	      }
	      
	    }
	   	else if (currentElementName.indexOf("].categorytype_ID") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%=(String) myMessages.getMessage(myLocale,
							"colname.category")%>" + " <%=(String) myMessages.getMessage(myLocale,
							"error.validation.isRequired")%>");
	        currentElement.focus();
	        return false;
	      }
	      
	    }
	  	else if (currentElementName.indexOf("].description") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%=(String) myMessages.getMessage(myLocale,
							"colname.ld.description")%>" + " <%=(String) myMessages.getMessage(myLocale,
							"error.validation.isRequired")%>");
	        currentElement.focus();
	        return false;
	      } 
	    }
  	}
	
	var bag0 = document.getElementById("theItem[0].lnameonbag");
	
	if(bag0 == null) {
		alert("<%=(String) myMessages.getMessage(myLocale,
							"header.bag_info")%>" + " <%=(String) myMessages.getMessage(myLocale,
							"error.validation.isRequired")%>");
		return false;
	}
  	
  	for (var j=0;j<bagIndices.length;j++) {
  	    var index = bagIndices[j] * 20;
  		var contents = document.getElementById("inventoryList[" + index + "].description");
  		  		
  		if (!contents) {
	        alert("<%=(String) myMessages.getMessage(myLocale,
							"colname.key_contents")%>" + " <%=(String) myMessages.getMessage(myLocale,
							"error.validation.isRequired")%>");
			document.getElementById("theItem[" + j + "].lnameonbag").focus();
	        return false;
  		}
  	}
  	
  	return true;
  } 
  
  /******************* ohd *****************/
  
   function validatereqOHDForm(form) {
   	return true;
   }
   
  function validatereqWtOHDForm(form) {
  	var theindex = 0;
    for (var j=0;j<form.length;j++) {
	    currentElement = form.elements[j];
	    currentElementName=currentElement.name;
		
		if (currentElementName.indexOf("itinerarytype") != -1) {  
	      if (currentElement.value == 0 || currentElement.value == 1) theindex = currentElementName.substring(14,15);
	    } else if (currentElementName.indexOf("[" + theindex + "].legfrom") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%=(String) myMessages
							.getMessage(myLocale, "colname.fromto")%>" + " <%=(String) myMessages.getMessage(myLocale,
							"error.validation.isRequired")%>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("[" + theindex + "].legto") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%=(String) myMessages
							.getMessage(myLocale, "colname.fromto")%>" + " <%=(String) myMessages.getMessage(myLocale,
							"error.validation.isRequired")%>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("[" + theindex + "].airline") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%=(String) myMessages.getMessage(myLocale,
							"colname.flightnum")%>" + " <%=(String) myMessages.getMessage(myLocale,
							"error.validation.isRequired")%>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("[" + theindex + "].flightnum") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%=(String) myMessages.getMessage(myLocale,
							"colname.flightnum")%>" + " <%=(String) myMessages.getMessage(myLocale,
							"error.validation.isRequired")%>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("[" + theindex + "].disdepartdate") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%=(String) myMessages.getMessage(myLocale,
							"colname.departdate")%>" + " <%=(String) myMessages.getMessage(myLocale,
							"error.validation.isRequired")%>");
	        currentElement.focus();
	        return false;
	      }
	    }
	    else if (currentElementName.indexOf("bagColor") != -1) { 
	      if (currentElement.value.length == 0)
	      {
	        alert("<%=(String) myMessages.getMessage(myLocale,
									"colname.color")%>" + " <%=(String) myMessages.getMessage(myLocale,
							"error.validation.isRequired")%>");
	        currentElement.focus();
	        return false;
	      }
	    } else if (currentElementName.indexOf("bagType") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%=(String) myMessages.getMessage(myLocale,
							"colname.bagtype")%>" + " <%=(String) myMessages.getMessage(myLocale,
							"error.validation.isRequired")%>");
	        currentElement.focus();
	        return false;
	      }
	      
	    }
	    else if (currentElementName.indexOf("XDesc1") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%=(String) myMessages.getMessage(myLocale,
									"colname.x_desc")%>" + " <%=(String) myMessages.getMessage(myLocale,
							"error.validation.isRequired")%>");
	        currentElement.focus();
	        return false;
	      }
	    }
	    else if (currentElementName.indexOf("XDesc2") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%=(String) myMessages.getMessage(myLocale,
									"colname.x_desc")%>" + " <%=(String) myMessages.getMessage(myLocale,
							"error.validation.isRequired")%>");
	        currentElement.focus();
	        return false;
	      }
	    }
	    else if (currentElementName.indexOf("XDesc3") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%=(String) myMessages.getMessage(myLocale,
									"colname.x_desc")%>" + " <%=(String) myMessages.getMessage(myLocale,
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
	
	
	/***************** BEoRN ****************/
	function validatereqBEORN(form)
  {
	  return true;
	}
	// -->
  </script>
