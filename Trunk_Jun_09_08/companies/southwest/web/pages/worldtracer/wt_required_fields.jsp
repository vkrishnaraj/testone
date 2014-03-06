<%@ page language="java" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="org.apache.struts.action.Action" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>

<%
Agent a = (Agent) session.getAttribute("user");
ResourceBundle bundle = ResourceBundle.getBundle(
					"com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(a.getCurrentlocale()));

%>


  function validatereqWtIncFields(form, formType, requireContents, firstPaxIndex, firstAddressIndex, firstItemIndex, firstClaimcheckIndex)
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
    
    var langKey = "";
    var langKeyLabel = "";

    for (var j=0;j < form.length; j++) {
      currentElement = form.elements[j];
      currentElementName=currentElement.name;
      
      if ((formType == 'damaged' || formType == 'pilfered') && !requireContents) {
        reqContentFields = false;
      }
      
      if (currentElementName.indexOf("["+firstPaxIndex+"].lastname") != -1) {  
        if (currentElement.value.length == 0)
        {
          alert("<%=(String) bundle.getString(
              "colname.last_name")%>" + " <%=(String) bundle.getString(
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        }
      }
      else if (currentElementName.indexOf("["+firstPaxIndex+"].firstname") != -1) {
        if (currentElement.value.length == 0)
        {
          alert("<%= (String)bundle.getString( 
            "colname.first_name") %>" + " <%= (String)bundle.getString( 
            "error.validation.isRequired") %>");
          currentElement.focus();
          return false;
        }
      
	} else if (currentElementName.indexOf("languageKey") != -1) {
		langKey = currentElement.value;
		langKeyLabel = currentElement.options[currentElement.selectedIndex].text
	      
	} else if (currentElementName.indexOf("languageFreeFlow") != -1) {  
	      if ('other' == langKey){ 
	          if (currentElement.value.length == 0)
	          {
	             alert("<%= (String)bundle.getString("spoken.language.label") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
	             currentElement.focus();
	             return false;
	          }
	      } else {
	        currentElement.value = langKeyLabel;
	      }
	      
	      
	}
	      
    else if (currentElementName.indexOf("["+firstAddressIndex+"].address1") != -1) {
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
      } else if (currentElementName.indexOf("["+firstAddressIndex+"]address1") != -1) {
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
      
      else if (currentElementName.indexOf("["+firstAddressIndex+"].city") != -1) {  
        if (currentElement.value.length == 0)
        {
          alert("<%=(String) bundle.getString( "colname.city")%>" + " <%=(String) bundle.getString(
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        } 
      } 
      else if (currentElementName.indexOf("["+firstAddressIndex+"].countrycode_ID") != -1) {
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
      else if (currentElementName.indexOf("["+firstAddressIndex+"].state_ID") != -1) {  
        
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
      } else if (currentElementName.indexOf("].color") != -1 && formType != 'pilfered' ) { 
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
      } else if (currentElementName.indexOf("].bagtype") != -1 &&  formType != 'pilfered' ) {  
        if (currentElement.value.length == 0)
        {
          alert("<%=(String) bundle.getString(
              "colname.bagtype")%>" + " <%=(String) bundle.getString(
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        }
        
      }
      else if (currentElementName.indexOf("].xdescelement_ID_1") != -1) {  
        if (currentElement.value.length == 0)
        {
          alert("<%=(String) bundle.getString(
                  "colname.x_desc")%>" + " <%=(String) bundle.getString(
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        }
      }
     else if (currentElementName.indexOf("numpassengers") != -1) {  
        if (currentElement.value.length == 0)
        {
          alert("<%=(String) bundle.getString(
              "colname.num_pass")%>" + " <%=(String) bundle.getString(
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        }
        
      }
      else if (currentElementName.indexOf("].categorytype_ID") != -1) {  
        if (currentElement.value.length == 0 && reqContentFields)
        {
          alert("<%=(String) bundle.getString(
              "colname.category")%>" + " <%=(String) bundle.getString(
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        }
        
      }
      else if (currentElementName.indexOf("].description") != -1) {  
        if (currentElement.value.length == 0 && reqContentFields)
        {
          alert("<%=(String) bundle.getString(
              "colname.ld.description")%>" + " <%=(String) bundle.getString(
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        } 
      }
      else if (currentElementName.indexOf("].lvlofdamage") != -1) {  
        if (currentElement.value.length == 0)
        {
          alert("<%=(String) bundle.getString(
              "colname.lvldamage")%>" + " <%=(String) bundle.getString(
              "error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        } 
      }
      else if(currentElementName.indexOf("].claimchecknum") != -1 && currentElement.value.length > 0) {
        ccCount += 1;
      } 
    }
  
  var bag0 = document.getElementById("theitem["+firstItemIndex+"].lnameonbag");
  
  if(bag0 == null) {
    alert("<%=(String) bundle.getString(
              "header.bag_info")%>" + " <%=(String) bundle.getString(
              "error.validation.isRequired")%>");
    return false;
  }

  if(ccCount > bagIndices.length && !(formType == 'damaged' || formType == 'pilfered')) {
        alert('<%= (String) bundle.getString( "error.validation.too.many.claimchecks")%>');
        document.getElementById("claimcheck["+firstClaimcheckIndex+"].claimchecknum").focus();
        return false;
  }
    
    
    for (var j=0; j < bagIndices.length; j++) {
      var index = bagIndices[j] * 20;
      var contents = document.getElementById("inventorylist[" + index + "].description");
            
      if (!contents && reqContentFields) {
        alert("<%=(String) bundle.getString(
          "colname.key_contents")%>" + " <%=(String) bundle.getString(
          "error.validation.isRequired")%>");
        document.getElementById("theitem[" + j + "].lnameonbag").focus();
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
	    else if (currentElementName.indexOf("XDesc1") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%=(String) bundle.getString(
									"colname.x_desc")%>" + " <%=(String) bundle.getString(
							"error.validation.isRequired")%>");
	        currentElement.focus();
	        return false;
	      }
	    }
	    else if (currentElementName.indexOf("XDesc2") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%=(String) bundle.getString(
									"colname.x_desc")%>" + " <%=(String) bundle.getString(
							"error.validation.isRequired")%>");
	        currentElement.focus();
	        return false;
	      }
	    }
	    else if (currentElementName.indexOf("XDesc3") != -1) {  
	      if (currentElement.value.length == 0)
	      {
	        alert("<%=(String) bundle.getString(
									"colname.x_desc")%>" + " <%=(String) bundle.getString(
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
  
  function  validatereqPXFFields(form) {
	     return true;
  }
