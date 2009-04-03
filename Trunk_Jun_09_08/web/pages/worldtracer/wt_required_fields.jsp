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

  function validatereqWtIncFields(form, formType, requireContents)
  {
    <%
      if (a.getStation().getCompany().getVariable().getWt_enabled() != 1) {
    %>
        return true;
    <%
      } else {
    %>
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
      
      if (currentElementName.indexOf("[0].lastname") != -1) {  
        if (currentElement.value.length == 0)
        {
          alert("<%=(String) myMessages.getMessage(myLocale,
              "colname.last_name")%>" + " <%=(String) myMessages.getMessage(myLocale,
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        }
      }
      else if (currentElementName.indexOf("[0].firstname") != -1) {
        if (currentElement.value.length == 0)
        {
          alert("<%= (String)myMessages.getMessage(myLocale, 
            "colname.first_name") %>" + " <%= (String)myMessages.getMessage(myLocale, 
            "error.validation.isRequired") %>");
          currentElement.focus();
          return false;
        }
      }
    else if (currentElementName.indexOf("[0].address1") != -1) {
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
      } else if (currentElementName.indexOf("[0]address1") != -1) {
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
      
      else if (currentElementName.indexOf("[0].city") != -1) {  
        if (currentElement.value.length == 0)
        {
          alert("<%=(String) myMessages.getMessage(myLocale, "colname.city")%>" + " <%=(String) myMessages.getMessage(myLocale,
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        } 
      } 
      else if (currentElementName.indexOf("[0].countrycode_ID") != -1) {
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
      else if (currentElementName.indexOf("[0].state_ID") != -1) {  
        // check country
        var pos = currentElementName.indexOf(".");
          var str = currentElementName.substring(0,pos+1) + "countrycode_ID";
  
          if (form.elements[str].value == "US" && currentElement.value.length ==0) {
            alert("<%=(String) myMessages.getMessage(myLocale,
                  "colname.state") + " " + myMessages.getMessage(myLocale,
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
        if (currentElement.value.length == 0 && reqContentFields)
        {
          alert("<%=(String) myMessages.getMessage(myLocale,
              "colname.category")%>" + " <%=(String) myMessages.getMessage(myLocale,
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        }
        
      }
      else if (currentElementName.indexOf("].description") != -1) {  
        if (currentElement.value.length == 0 && reqContentFields)
        {
          alert("<%=(String) myMessages.getMessage(myLocale,
              "colname.ld.description")%>" + " <%=(String) myMessages.getMessage(myLocale,
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        } 
      }
      else if (currentElementName.indexOf("].lvlofdamage") != -1) {  
        if (currentElement.value.length == 0)
        {
          alert("<%=(String) myMessages.getMessage(myLocale,
              "colname.lvldamage")%>" + " <%=(String) myMessages.getMessage(myLocale,
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        } 
      }
      else if(currentElementName.indexOf("].claimchecknum") != -1 && currentElement.value.length > 0) {
        ccCount += 1;
      } 
    }
  
  var bag0 = document.getElementById("theitem[0].lnameonbag");
  
  if(bag0 == null) {
    alert("<%=(String) myMessages.getMessage(myLocale,
              "header.bag_info")%>" + " <%=(String) myMessages.getMessage(myLocale,
              "error.validation.isRequired")%>");
    return false;
  }

  if(ccCount > bagIndices.length) {
        alert('<%= (String) myMessages.getMessage(myLocale, "error.validation.too.many.claimchecks")%>');
        document.getElementById("claimcheck[0].claimchecknum").focus();
        return false;
  }
    
    
    for (var j=0; j < bagIndices.length; j++) {
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
    <%
      }
    %>
  }
  
  function validatereqWtOHDForm(form) {
    <%
      if (a.getStation().getCompany().getVariable().getWt_enabled() != 1) {
    %>
        return true;
    <%
      } else {
    %>

  	var theindex = 0;
    for (var j=0;j < form.length; j++) {
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
    <%
      }
    %>
  }