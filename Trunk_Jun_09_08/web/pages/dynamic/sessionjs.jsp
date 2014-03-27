<%@ page contentType="text/javascript" %> 
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>
<%@page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@page import="java.util.TimeZone"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Iterator,java.util.LinkedHashMap" %>
<%@page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@page import="com.bagnet.nettracer.tracing.utils.DateUtils" %>
<%@page import="java.util.ResourceBundle" %>
<%@page import="java.util.Locale" %>

<% 
	Agent a = (Agent)session.getAttribute("user");
	LinkedHashMap menu_links = (LinkedHashMap)session.getAttribute("menu_links");
   int total_menu = 0;
		long expireTime = new Date().getTime() + (90 * 60 * 1000);
		SimpleDateFormat sdf = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss z");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		String expireStr = sdf.format(new Date(expireTime));
		response.setHeader("Expires", expireStr);
		ResourceBundle bundle = ResourceBundle.getBundle(
				"com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(a.getCurrentlocale()));

%>

<jsp:include page="/pages/includes/required_fields_incl.jsp" />

	function validateFsClaimForm(form) {
		var checkFields = true;
		if (getFieldByName("incidentId", form) != null && getFieldByName("incidentId", form).value.length > 0) {
			checkFields = false;
		}
		
		if (document.getElementById('addednames').value == 1
		 || document.getElementById('addedreceipts').value == 1
		 || document.getElementById('addedips').value == 1
		 || document.getElementById('addedsegs').value == 1
		 || document.getElementById('addedphones').value == 1
		 || document.getElementById('addedattachments').value==1) {
			checkFields = false;
		}
		
		if (checkFields) {
			for (var i = 0; i < form.length; ++i) {
			
				currentElement = form.elements[i];
				elementName = currentElement.name;
				
				if (elementName == "claimant.lastName") {
					if (currentElement.value.length == 0) {
						alert("<%= (String)bundle.getString("colname.last_name") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
				        currentElement.focus();
				        return false;
					}
				} else if (elementName == "claimant.firstName") {
					if (currentElement.value.length == 0) {
						alert("<%= (String)bundle.getString("colname.first_name") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
				        currentElement.focus();
				        return false;
					}
				} else if (elementName == "address1") {
					if (currentElement.value.length == 0) {
						alert("<%= (String)bundle.getString("colname.street_addr1") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
				        currentElement.focus();
				        return false;
					}
				} else if (elementName == "city") {
					if (currentElement.value.length == 0) {
						alert("<%= (String)bundle.getString("colname.city") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
				        currentElement.focus();
				        return false;
					}
				} else if (elementName == "country") {
					country = currentElement.value;
					
					if (country == "US") {
						element = getFieldByName("state", form);
						if (element.value.length == 0) {
							alert("<%= (String)bundle.getString("colname.state") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
					        element.focus();
					        return false;
						}
						
						element = getFieldByName("zip", form);
						if (element.value.length == 0) {
							alert("<%= (String)bundle.getString("colname.zip") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
					        element.focus();
					        return false;
						}
					}
				
				} else if (elementName.indexOf("ipAddress") != -1 && elementName.indexOf("association") == -1) {
					var regex = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/g;
					if (currentElement.value.match(regex) == null) {
						alert("<%= (String)bundle.getString("claim.colname.ipAddress") %>" + " <%= (String)bundle.getString("error.validation.ipAddress") %>");
					    element.focus();
					    return false;
					}
				}
			
			}
		}
        return true;
	
	}
	
	function validateLfReportForm(form) {
		for (var i = 0; i < form.length; ++i) {
		
			currentElement = form.elements[i];
			elementName = currentElement.name;
			
			if (elementName == "lost.companyId") {
				if (currentElement.value == '' || currentElement.value == '<%=TracingConstants.LF_ABG_COMPANY_ID %>') {
					alert("<%= (String)bundle.getString("colname.lf.company") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
			        currentElement.focus();
			        return false;
				}
			} else if (elementName == "lost.client.lastName") {
				if (currentElement.value.length == 0) {
					alert("<%= (String)bundle.getString("colname.last_name") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
			        currentElement.focus();
			        return false;
				}
			} else if (elementName == "lost.client.firstName") {
				if (currentElement.value.length == 0) {
					alert("<%= (String)bundle.getString("colname.first_name") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
			        currentElement.focus();
			        return false;
				}
/* BEGIN ADDRESS VALIDATION */
			} else if (elementName == "lost.client.address.decryptedAddress1") {
				if (currentElement.value.length == 0) {
					alert("<%= (String)bundle.getString("colname.street_addr1") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
			        currentElement.focus();
			        return false;
				}
			} else if (elementName == "lost.client.address.decryptedCity") {
				if (currentElement.value.length == 0) {
					alert("<%= (String)bundle.getString("colname.city") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
			        currentElement.focus();
			        return false;
				}
			} else if (elementName == "lost.client.address.country") {
				country = currentElement.value;
				
				if (country == "US") {
					element = getFieldByName("lost.client.address.decryptedState", form);
					if (element.value.length == 0) {
						alert("<%= (String)bundle.getString("colname.state") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
				        element.focus();
				        return false;
					}
					
					element = getFieldByName("lost.client.address.decryptedZip", form);
					if (element.value.length == 0) {
						alert("<%= (String)bundle.getString("colname.zip") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
				        element.focus();
				        return false;
					}
				}
/* END ADDRESS VALIDATION */			
			
			} else if (elementName == "lost.client.decryptedEmail") {
				if (currentElement.value != getFieldByName("lost.client.confirmEmail", form).value) {
					alert("<%= (String)bundle.getString("colname.lf.email") %>" + " <%= (String)bundle.getString("error.validation.cannot.confirm.email") %>");
			        currentElement.focus();
			        return false;
				}
			} else if (elementName == "lost.statusId") {
				if (currentElement.value == '-1') {
					alert("<%= (String)bundle.getString("colname.lf.status") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
			        currentElement.focus();
			        return false;
				}
			} else if (elementName.indexOf("originId") != -1) {
				if (currentElement.value.length == 0) {
					alert("<%= (String)bundle.getString("colname.lf.segment.origin") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
			        currentElement.focus();
			        return false;
				}
			} else if (elementName.indexOf("destinationId") != -1) {
				if (currentElement.value.length == 0) {
					alert("<%= (String)bundle.getString("colname.lf.segment.destination") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
			        currentElement.focus();
			        return false;
				}
			}

		}
		var country = getFieldByName("lost.client.address.country",form).value;
		var primaryPhone="";
		var secondaryPhone="";
		if(country=="US" && getFieldByName("primaryAreaNumber",form).value.length>0 && getFieldByName("primaryExchangeNumber",form).value.length>0 && getFieldByName("primaryLineNumber",form).value.length>0){
			primaryPhone =  getFieldByName("primaryAreaNumber",form).value+getFieldByName("primaryExchangeNumber",form).value+getFieldByName("primaryLineNumber",form).value;
		} else if(country!="US" && getFieldByName("primaryInternationalNumber",form).value.length >0 && getFieldByName("primaryLineNumber",form).value.length>0){
			primaryPhone =  getFieldByName("primaryInternationalNumber",form).value+getFieldByName("primaryLineNumber",form).value;
		}
		
		if(country=="US" && getFieldByName("secondaryAreaNumber",form).value.length>0 && getFieldByName("secondaryExchangeNumber",form).value.length>0 && getFieldByName("secondaryLineNumber",form).value.length>0){
			secondaryPhone =  getFieldByName("secondaryAreaNumber",form).value+getFieldByName("secondaryExchangeNumber",form).value+getFieldByName("secondaryLineNumber",form).value;
		} else if(country!="US" && getFieldByName("secondaryInternationalNumber",form).value.length >0 && getFieldByName("secondaryAreaNumber",form).value.length>0 && getFieldByName("secondaryExchangeNumber",form).value.length>0 && getFieldByName("secondaryLineNumber",form).value.length>0){
			secondaryPhone =  getFieldByName("secondaryInternationalNumber",form).value+getFieldByName("secondaryAreaNumber",form).value+getFieldByName("secondaryExchangeNumber",form).value+getFieldByName("secondaryLineNumber",form).value;
		}
		
		var email = getFieldByName("lost.client.decryptedEmail", form).value;
		if (primaryPhone.length == 0 && secondaryPhone.length == 0 && email.length == 0) {
			alert("<%= (String)bundle.getString("colname.lf.phone.or.email") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
			return false;
		}
		
        return true;
	
	}
	
	function validateId(fieldName) {
		var id = document.getElementById(fieldName);
		if (id.value.length == 0) {
			alert("<%= (String)bundle.getString("colname.id") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
			return false;
		}
		return true;
	}
	
	function validateFoundItemForm(form) {
		var currentElement;
		for (var i = 0; i < form.length; ++i) {
		
			currentElement = form.elements[i];
			elementName = currentElement.name;
			
			if (elementName == "disReceivedDate") {
				if (currentElement.value.length == 0) {
					alert("<%= (String)bundle.getString("colname.lf.received.date") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
			        currentElement.focus();
			        return false;
				}
			} else if (elementName == "found.companyId") {
				if (currentElement.value == '' || currentElement.value == '<%=TracingConstants.LF_ABG_COMPANY_ID %>') {
					alert("<%= (String)bundle.getString("colname.lf.company") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
			        currentElement.focus();
			        return false;
				}
			} else if (elementName == "found.locationId") {
				if (currentElement.value == '' || currentElement.value == '0') {
					alert("<%= (String)bundle.getString("colname.lf.found.location") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
			        currentElement.focus();
			        return false;
				}
			} else if (elementName == "found.statusId") {
				if (currentElement.value == '-1') {
					alert("<%= (String)bundle.getString("colname.lf.status") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
			        currentElement.focus();
			        return false;
				}
			} else if (elementName == "found.barcode") {
				if (!currentElement.value) {
					alert("<%= (String)bundle.getString("colname.lf.report.id") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
					currentElement.focus();
			        return false;
				}
				if (isNaN(currentElement.value) || currentElement.value.indexOf('-') != -1 || currentElement.value.indexOf('.') != -1) {
					alert("<%= (String)bundle.getString("colname.lf.report.id") %>" + " <%= (String)bundle.getString("error.validation.validNumber") %>");
					currentElement.focus();
			        return false;
				}
			} else if (elementName.indexOf("category") != -1) {
				if (currentElement.value == 0) {
					alert("<%= (String)bundle.getString("colname.lfc.category") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
			        currentElement.focus();
			        return false;
				}			
			} else if (elementName == "found.checkNumber") {
				if (isNaN(currentElement.value) || currentElement.value.indexOf('-') != -1) { 
					alert("<%=(String) bundle.getString("lf.colname.check.number") %>" + " " + "<%=(String) bundle.getString("error.message.must.be.a.number") %>");
					currentElement.focus();
					return false;
				}
			} else if (elementName == "dispCheckAmount") {
				if (currentElement.value != "" && currentElement.value != "0") {
					if (isNaN(currentElement.value) || currentElement.value.indexOf('-') != -1) {
						alert("<%=(String) bundle.getString("lf.colname.check.amount") %>" + " " + "<%=(String) bundle.getString("error.message.must.be.a.number") %>");
						currentElement.focus();
						return false;
					} else {
						var trackingNumber = document.getElementById("trackingNumber");
						var checkNumber = document.getElementById("checkNumber");
						
						if (trackingNumber && (!trackingNumber.value || !checkNumber ||
							trackingNumber.value.length == 0 || checkNumber.value.length == 0)) {
							alert("<%=(String) bundle.getString("error.message.check.tracking.required") %>");
							currentElement.focus();
							return false;
								
						}
					}
				}				
			}
		}
		return true;
	}
	
	function validateDeliveryForm(form) {
		var element = getFieldByName("delivery.trackingNumber", form);
		if (element.value == null || element.value.length == 0) {
			alert("<%= (String)bundle.getString("colname.lf.tracking.number") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
			return false;
		}
		return true;
	}
	
	function getFieldByName(elementName, form) {
		for (var i = 0; i < form.length; ++i) {
			if (form.elements[i].name == elementName) {
				return form.elements[i];
			}
		}
		return null;
	}
		
  function checkDate(strng)
  {
    return isDate(strng,'<%= a.getDateformat().getFormat() %>');
  }
  
  function checkTime(strng)
  {
    return isDate(strng,'<%= a.getTimeformat().getFormat() %>');
  }
   	
  function validateRest(form)
  {
     for (var j=0;j<form.length;j++) {
    currentElement = form.elements[j];
    currentElementName=currentElement.name;
    if (currentElementName.indexOf("email") != -1 && currentElementName.indexOf("email_") == -1)
    {

      if (currentElement.value.length > 0 && !checkEmail(currentElement.value))
      {
        alert("<%= (String)bundle.getString("colname.email") %>" + " <%= (String)bundle.getString("error.validation.email") %>");
        currentElement.focus();
        return false;
      }
    }
    else if (currentElementName.indexOf("expedite") != -1)
    {
      if (currentElement.value.length < 1 && currentElementName.indexOf("expediteTagNum") == -1)
      {
         alert("<%= (String)bundle.getString("colname.expedite_number") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
         currentElement.focus();
         return false;
      }
        
      if (currentElement.value.length > 0 && !checkExpedite(currentElement.value))
      {
        alert("<%= (String)bundle.getString("colname.expedite_number") %>" + " <%= (String)bundle.getString("error.validation.expedite") %>");
        currentElement.focus();
        return false;
      }
    }
    else if (currentElementName.indexOf("pnr") != -1)
    {  

      if (currentElement.value.length > 0 && !checkPnr(""+currentElement.value))
      {
        alert("<%= (String)bundle.getString("colname.pnr") %>" + " <%= (String)bundle.getString("error.validation.pnr") %>");
        currentElement.focus();
        return false;
      }
    }
     else if (currentElementName.indexOf("recordlocator") != -1)
    {  

      if (currentElement.value.length > 0 && !checkPnr(""+currentElement.value))
      {
        alert("<%= (String)bundle.getString("colname.recordlocator") %>" + " <%= (String)bundle.getString("error.validation.pnr") %>");
        currentElement.focus();
        return false;
      }
    }
     else if (currentElementName.indexOf("ssn") != -1)
    {  

      if (currentElement.value.length > 0 && !checkSSN(""+currentElement.value))
      {
        alert("<%= (String)bundle.getString("colname.ssn") %>" + " <%= (String)bundle.getString("error.validation.ssn") %>");
        currentElement.focus();
        return false;
      }
    }
   	else if (currentElementName.indexOf("numpassengers") != -1)
    {  

      if (currentElement.value.length > 0 && !checkInteger(currentElement.value))
      {
        alert("<%= (String)bundle.getString("colname.num_pass") %>" + " <%= (String)bundle.getString("error.validation.num_passengers") %>");
        currentElement.focus();
        return false;
      }
    }
    else if (currentElementName.indexOf("numbagchecked") != -1)
    {  

      if (currentElement.value.length > 0 && !checkInteger(currentElement.value))
      {
        alert("<%= (String)bundle.getString("colname.num_bag_checked") %>" + " <%= (String)bundle.getString("error.validation.num_bag_checked") %>");
        currentElement.focus();
        return false;
      }
    }
    else if (currentElementName.indexOf("numbagreceived") != -1)
    {  

      if (currentElement.value.length > 0 && !checkInteger(currentElement.value))
      {
        alert("<%= (String)bundle.getString("colname.bags_rec") %>" + " <%= (String)bundle.getString("error.validation.num_bag_recd") %>");
        currentElement.focus();
        return false;
      }
    } 
    
    else if (currentElementName.indexOf("dlstate") != -1) {


        var pos = currentElementName.indexOf(".");
        var str = currentElementName.substring(0,pos+1) + "decriptedDriversLicense";
        if (!form.elements[str] || form.elements[str] == null) {
        	str = currentElementName.substring(0,pos+1) + "redactedDriversLicense";
        }
		
        if (form.elements[str].value.length > 0 && currentElement.value.length ==0) {
        	var dlProvince = document.getElementById(currentElementName.substring(0,pos+1) + 'driversLicenseProvince');
        	if (!dlProvince || dlProvince.value == null || dlProvince.value.length == 0) {
	            alert("<%= (String)bundle.getString("error.validation.dlicense") %>");
	            currentElement.focus();
	            return false;
            }
        }
    } 
      
    else if (currentElementName.indexOf("countryofissue") != -1) {

        var pos = currentElementName.indexOf(".");
        var str = currentElementName.substring(0,pos+1) + "commonnum";

        if (form.elements[str].value.length > 0 && currentElement.value.length == 0) {
            alert("<%= (String)bundle.getString("error.validation.commonNumber") %>");
            currentElement.focus();
            return false;
        }
    }  

    else if (currentElementName.indexOf("companyCode_ID") != -1) {
        var pos = currentElementName.indexOf(".");
        var str = currentElementName.substring(0,pos+1) + "membership.membershipnum";
        if (form.elements[str].value.length > 0 && currentElement.value.length == 0) {
            alert("<%= (String)bundle.getString("error.validation.airline") %>");
            currentElement.focus();
            return false;
        }
    }   
      
    else if (currentElementName.indexOf("zip") != -1)
    {

      if (currentElement.value.length > 0 && !checkZip(currentElement.value))
      {
        alert("<%= (String)bundle.getString("colname.zip") %>" + " <%= (String)bundle.getString("error.validation.zipcode") %>");
        currentElement.focus();
        return false;
      }
    }
    
    else if (currentElementName.indexOf("legfrom") != -1 && currentElementName.indexOf("legfrom_type") == -1)
    {
      if (currentElement.value.length > 0 && !checkLegFrom(currentElement.value))
      {
        alert("<%= (String)bundle.getString("colname.fromto") %>" + " <%= (String)bundle.getString("error.validation.station") %>");
        currentElement.focus();
        return false;
      }
    }
    else if (currentElementName.indexOf("legto") != -1 && currentElementName.indexOf("legto_type") == -1)
    {
      if (currentElement.value.length > 0 && !checkLegFrom(currentElement.value))
      {
        alert("<%= (String)bundle.getString("colname.fromto") %>" + " <%= (String)bundle.getString("error.validation.station") %>"); 
        currentElement.focus();
        return false;
      }
    }
    
    else if (currentElementName.indexOf("flightnum") != -1)
    {
      if (currentElement.value.length > 0 && !checkFlightNum(currentElement.value))
      {
        alert("<%= (String)bundle.getString("colname.flightnum") %>" + " <%= (String)bundle.getString("error.validation.flightNum") %>");
        currentElement.focus();
        return false;
      }
    }  else if (currentElementName.indexOf("dispBagArriveDate") != -1) {
		if (currentElement.value.length > 0) {
		      if (!checkDate(currentElement.value)) {
			        alert("<%= (String)bundle.getString("colname.bag_arrived_date") %>" + " <%= (String)bundle.getString("error.validation.date") %>");
			        currentElement.focus();
			        return false;
		      }

			  /*bag arrival date validation*/
		      var now = '<%= DateUtils.formatDate(new Date(), a.getDateformat().getFormat(), a.getDefaultlocale(), null) %>';
   			  var format = '<%= a.getDateformat().getFormat() %>';
   			  var datesDifference = compareDates(currentElement.value, format, now, format);		      
		      if (datesDifference == 1) {		
			        alert("<%= (String)bundle.getString("colname.bag_arrived_date") %>" + " <%= (String)bundle.getString("error.validation.date.future") %>");
			        currentElement.focus();
			        return false;
		      }
	    }
    } else if (currentElementName.indexOf("disdepartdate") != -1) {
      if (currentElement.value.length > 0 && !checkDate(currentElement.value))
      {
        alert("<%= (String)bundle.getString("colname.departdate") %>" + " <%= (String)bundle.getString("error.validation.date") %>");
        currentElement.focus();
        return false;
      }
    }
    else if (currentElementName.indexOf("disarrivedate") != -1)
    {
      if (currentElement.value.length > 0 && !checkDate(currentElement.value))
      {
        alert("<%= (String)bundle.getString("colname.arrdate") %>" + " <%= (String)bundle.getString("error.validation.date") %>"); 
        currentElement.focus();
        return false;
      }
    }
    else if (currentElementName.indexOf("disschdeparttime") != -1)
    {
      if (currentElement.value.length > 0 && !checkTime(currentElement.value))
      {
        alert("<%= (String)bundle.getString("colname.schdeptime") %>" + " <%= (String)bundle.getString("error.validation.time") %>"); 
        currentElement.focus();
        return false;
      }
    }
    else if (currentElementName.indexOf("disscharrivetime") != -1)
    {
      if (currentElement.value.length > 0 && !checkTime(currentElement.value))
      {
        alert("<%= (String)bundle.getString("colname.scharrtime") %>" + " <%= (String)bundle.getString("error.validation.time") %>"); 
        currentElement.focus();
        return false;
      }
    }
     else if (currentElementName.indexOf("disactdeparttime") != -1)
    {
      if (currentElement.value.length > 0 && !checkTime(currentElement.value))
      {
        alert("<%= (String)bundle.getString("colname.actdeptime") %>" + " <%= (String)bundle.getString("error.validation.time") %>"); 
        currentElement.focus();
        return false;
      }
    }
    else if (currentElementName.indexOf("disactarrivetime") != -1)
    {
      if (currentElement.value.length > 0 && !checkTime(currentElement.value))
      {
        alert("<%= (String)bundle.getString("colname.actarrtime") %>" + " <%= (String)bundle.getString("error.validation.time") %>"); 
        currentElement.focus();
        return false;
      }
    }
    else if (currentElementName.indexOf("claimchecknum") != -1)
    {
      if (currentElement.value.length > 0 && !checkClaimCheck(currentElement.value))
      {
        alert("<%= (String)bundle.getString("colname.claimnum") %>" + " <%= (String)bundle.getString("error.validation.claimcheck") %>"); 
        currentElement.focus();
        return false;
      }
    }
    else if (currentElementName.indexOf("dispurchasedate") != -1)
    {
      if (currentElement.value.length > 0 && !checkDate(currentElement.value))
      {
        alert("<%= (String)bundle.getString("colname.purchase_date") %>" + " <%= (String)bundle.getString("error.validation.date") %>"); 
        currentElement.focus();
        return false;
      }
    }
    else if (currentElementName.indexOf("discost") != -1)
    {
      if (currentElement.value.length > 0 && !checkFloat(currentElement.value))
      {
        alert("<%= (String)bundle.getString("colname.cost") %>" + " <%= (String)bundle.getString("error.validation.float") %>"); 
        currentElement.focus();
        return false;
      }
    }
        else if (currentElementName.indexOf("bag_weight") != -1 && currentElementName.indexOf("bag_weight_unit") == -1)
    {
      if (currentElement.value.length > 0 && !checkFloat(currentElement.value))
      {
        alert("<%= (String)bundle.getString("colname.bag.weight.and.units") %>" + " <%= (String)bundle.getString("error.validation.float") %>"); 
        currentElement.focus();
        return false;
      }
    }
    else if (currentElementName.indexOf("overall_weight") != -1 && currentElementName.indexOf("overall_weight_unit") == -1)
    {
      if (currentElement.value.length > 0 && !checkFloat(currentElement.value))
      {
        alert("<%= (String)bundle.getString("colname.total.weight.and.units") %>" + " <%= (String)bundle.getString("error.validation.float") %>"); 
        currentElement.focus();
        return false;
      }
    }
    else if (currentElementName.indexOf("bagTagNumber") != -1)
    {
         if (currentElement.value.length > 0 && !checkClaimCheck(currentElement.value))
      {
        alert("<%= (String)bundle.getString("colname.bag_tag_number") %>" + " <%= (String)bundle.getString("error.validation.bagTag") %>"); 
        currentElement.focus();
        return false;
      }
    }
    else if (currentElementName.indexOf("s_time") != -1 || currentElementName.indexOf("e_time") != -1)
    {
      if (currentElement.value.length > 0 && !checkDate(currentElement.value))
      {
        alert("<%= (String)bundle.getString("colname.date_range") %>" + " <%= (String)bundle.getString("error.validation.date") %>"); 
        currentElement.focus();
        return false;
      }
    }
   

    else if (currentElementName.indexOf("s_createtime") != -1)
    {
      if (currentElement.value.length > 0 && !checkDate(currentElement.value))
      {
        alert("<%= (String)bundle.getString("colname.date_range") %>" + " <%= (String)bundle.getString("error.validation.date") %>");
        currentElement.focus();
        return false;
      }
    }
    
    else if (currentElementName.indexOf("e_createtime") != -1)
    {
      if (currentElement.value.length > 0 && !checkDate(currentElement.value))
      {
        alert("<%= (String)bundle.getString("colname.date_range") %>" + " <%= (String)bundle.getString("error.validation.date") %>");
        currentElement.focus();
        return false;
      }
    }
    
    else if (currentElementName.indexOf("courtesyreport") != -1) {
    	var courtesyReason = document.getElementById("courtesyReasonId");
    	if (courtesyReason) { 
    		if (currentElement.options[currentElement.selectedIndex].value == 1 && courtesyReason.options[courtesyReason.selectedIndex].value == 0) {
		        alert("<%= (String)bundle.getString("colname.courtesy.reason") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
		        courtesyReason.focus();
		        return false;
    		}
    	}       
    }
    
    else if (currentElementName.indexOf("courtesyReasonId") != -1) {
    	var courtesyReasonValue = currentElement.options[currentElement.selectedIndex].value;
    	if (courtesyReasonValue == 900) {
			var courtesyDescription = document.getElementById("courtesyDescription");
			if (courtesyDescription.value == null || courtesyDescription.value.length == 0) {
		        alert("<%= (String)bundle.getString("colname.courtesy.description") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
		        courtesyDescription.focus();
		        return false;
			}
		}
    }
    else if(currentElementName.indexOf("bagtype")!=-1){
    
    	var bagtypeValue = currentElement.options[currentElement.selectedIndex].value;
    	if (bagtypeValue == 94 || bagtypeValue == 95) {
    		var pos=currentElement.id.substring(7);
    		var deviceTypeValue=document.getElementById("deviceType"+pos);
    		var deviceCheckValue=document.getElementById("deviceCheck"+pos);
    		
	   		if (deviceTypeValue!=null) {
		    	if (deviceTypeValue.value == 0) {
			        alert("<%= (String)bundle.getString("colname.assist.device.type") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
			        deviceTypeValue.focus();
			        return false;
				}
		    }
	    
	     	if (deviceCheckValue!=null) {
		    	
				if (deviceCheckValue.value == null || deviceCheckValue.value.length == 0) {
			        alert("<%= (String)bundle.getString("colname.assist.device.check") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
			        deviceCheckValue.focus();
			        return false;
				}
				
		    }
	    }
    }
    
    }
    

    if (form.name == "incidentForm"){
    	if (!validatereq(form)) return false;
    	
    } else if (form.name == "OnHandForm") {
    	if (!validatereqOHD(form)) return false;
    }
     return true;
  }
  
  

 	function batchApprove()
  {
    if (confirm("<%= (String)bundle.getString("check.approve") %>?"))
    {  
			var checked = 0;
    	var received="";
    
    	for (var j=0;j<document.searchExpenseForm.length;j++) 
    	{
      	currentElement = document.searchExpenseForm.elements[j];
       	if (currentElement.type=="checkbox")
       	{
        	if (currentElement.checked)
         	{
          	if (checked > 0) 
          		received += ",";
          	checked +=1;
          	received +=currentElement.value;
         	}
       	}
     	}

     	if (checked < 1)
     	{
     		alert("<%= (String)bundle.getString("error.validation.missingRequests") %>");
     	}
    	else
     	{
     		document.searchExpenseForm.approve1.value="1";
			document.searchExpenseForm.payout_ID.value=received;
      		document.searchExpenseForm.submit();
    	}
  	}
  } 


 	function batchDeny()
  {
    if (confirm("<%= (String)bundle.getString("check.deny") %>?"))
    {  
			var checked = 0;
    	var received="";
    
    	for (var j=0;j<document.searchExpenseForm.length;j++) 
    	{
      	currentElement = document.searchExpenseForm.elements[j];
       	if (currentElement.type=="checkbox")
       	{
        	if (currentElement.checked)
         	{
          	if (checked > 0) 
          		received += ",";
          	checked +=1;
          	received +=currentElement.value;
         	}
       	}
     	}

     	if (checked < 1)
     	{
     		alert("<%= (String)bundle.getString("error.validation.missingDenRequests") %>");
     	}
    	else
     	{
     		document.searchExpenseForm.deny1.value="1";
				document.searchExpenseForm.payout_ID.value=received;
      	document.searchExpenseForm.submit();
    	}
  	}
  } 



	function batchTaskDelete()
  {
    if (confirm("<%= (String)bundle.getString("check.task.delete") %>?"))
    {  
			var checked = 0;
    	var received="";
    
    	for (var j=0;j<document.taskForm.length;j++) 
    	{
      	currentElement = document.taskForm.elements[j];
       	if (currentElement.type=="checkbox")
       	{
        	if (currentElement.checked)
         	{
          	if (checked > 0) 
          		received += ",";
          	checked +=1;
          	received +=currentElement.value;
         	}
       	}
     	}

     	if (checked < 1)
     	{
     		alert("<%= (String)bundle.getString("error.validation.missingTasks") %>");
     	}
    	else
     	{
     		document.taskForm.delete1.value="1";
				document.taskForm.task_ids.value=received;
      	document.taskForm.submit();
    	}
  	}
  } 

  	
  function deleteStation (stationCode,stationId)
  {
    if (confirm("<%= (String)bundle.getString("check.delete") %>" + stationCode + "?"))
    {  
      document.stationForm.delete1.value="1";
      document.stationForm.stationId.value=stationId;
      document.stationForm.submit();
     }
  } 
  
  
  function deleteSubcompany (subCompCode,id)
  {
    if (confirm("<%= (String)bundle.getString("check.delete") %>" + subCompCode + "?"))
    {  
      document.subCompanyForm.delete1.value="1";
      document.subCompanyForm.id.value=id;
      document.subCompanyForm.submit();
     }
  } 
  
  function deleteDeliveryCompany (deliveryCompanyId, deliveryCompanyName)
  {
    if (confirm("<%= (String)bundle.getString("check.delivercompany.delete") %> " + deliveryCompanyName + "?"))
    {  
      document.MaintainDeliveryCompanyForm.delete1.value="1";
      document.MaintainDeliveryCompanyForm.deleteDeliveryCompanyId.value=deliveryCompanyId;
      document.MaintainDeliveryCompanyForm.submit();
     }
  } 
  
  function deleteDeliveryCoServiceLevel (serviceLevelId, serviceLevelDescription)
  {
    if (confirm("<%= (String)bundle.getString("check.delivercompany.servicelevel.delete") %> " + serviceLevelDescription + "?"))
    {  
      document.MaintainDeliveryCompanyForm.delete1.value="1";
      document.MaintainDeliveryCompanyForm.deleteServiceLevelID.value=serviceLevelId;
      document.MaintainDeliveryCompanyForm.submit();
     }
  } 


  function deleteDeliveryCompanyStation (stationCode, deliverCoStationId )
  {
    if (confirm("<%= (String)bundle.getString("check.delete") %> " + stationCode + "?"))
    {  
      document.MaintainDeliveryCompanyForm.delete1.value="1";
      document.MaintainDeliveryCompanyForm.deleteDelivCoStation.value=deliverCoStationId;
      document.MaintainDeliveryCompanyForm.submit();
     }
  } 
  
  function deleteShift (shiftName,shiftId)
  {
    if (confirm("<%= (String)bundle.getString("check.delete") %>" + shiftName + "?"))
    {  
      document.shiftForm.delete1.value="1";
      document.shiftForm.shiftId.value=shiftId;
      document.shiftForm.submit();
    }
  } 
  
  function deleteAirport (airportcode,id)
  {
    if (confirm("<%= (String)bundle.getString("check.delete") %>" + airportcode + "?"))
    {  
      document.airportForm.delete1.value="1";
      document.airportForm.id.value=id;
      document.airportForm.submit();
    }
  } 
  
  
  
  function deleteCompany (companyCode)
  {
    if (confirm("<%= (String)bundle.getString("check.delete") %>" + companyCode + "?"))
    {  
      document.companyForm.delete1.value="1";
      document.companyForm.companyCode.value=companyCode;
      document.companyForm.submit();
    }
  } 
  
  
  function saveOHDTemporary(form)
  {
   if (confirm("<%= (String)bundle.getString("check.save.temporary") %>"))
    { 
      document.OnHandForm.savetemp.value="1";
      document.OnHandForm.submit();
    }
  }

  function saveMassOHDTemporary(form)
  {
   if (confirm("<%= (String)bundle.getString("check.save.temporary") %>"))
    { 
      document.OnHandForm.savetemp.value="1";

			submitMassform(form);


      document.OnHandForm.submit();
    }
  }
    
  function saveIncidentTemporary(form)
  {
     if (confirm("<%= (String)bundle.getString("check.save.temporary") %>"))
      { 
        document.incidentForm.savetemp.value="1";
        document.incidentForm.submit();
      }
  }
  
   function submitMassform(form)
  {
  
    var bagtagnumbers="";
    for (var j=0;j < form.length;j++) {
      var currentElement = form.elements[j];
      var currentElementName = currentElement.name;
      if (currentElementName != "bagTagNumber" &&  currentElementName.indexOf("bagTagNumber") != -1)
      {
        if (currentElement.value != "")
        {
          if (bagtagnumbers != "")
          {
            bagtagnumbers += ",";
          }
          if (!checkClaimCheck(currentElement.value))
          {
            alert("<%= (String)bundle.getString("colname.bag_tag_number") %>" + " <%= (String)bundle.getString("error.validation.bagTag") %>"); 
            currentElement.focus();
            return false;
          }
          bagtagnumbers += currentElement.value;
        }
      }
    }

    if (bagtagnumbers == "")
    {
      alert("<%= (String)bundle.getString("error.validation.missingBagTag") %>");
      return false;
    } 
    else{
      document.OnHandForm.bagTagNumber.value=bagtagnumbers;
    }
    return true;
  }
 

    
  function submitLzedform()
  {
    var checked = 0;
    var ohd_id="";
    
    for (var j=0;j<document.viewOHDLZedForm.length;j++) {
      
      currentElement = document.viewOHDLZedForm.elements[j];
       if (currentElement.type=="checkbox")
       {
        if (currentElement.checked)
         {
          if (checked > 0) ohd_id += ",";
          checked +=1;
          ohd_id +=currentElement.value;
         }
       }
     }

     if (checked < 1)
     {
       alert("<%= (String)bundle.getString("error.validation.missingForward") %>");
     }
     else
     {
      document.forwardOnHandForm.batch_id.value=ohd_id;
      document.forwardOnHandForm.submit();
    }
  }



  function submitCompareOHDform()
  {
  	
    var checked = 0;
    var audit_ohd_id="";
    
    for (var j=0;j<document.auditOHDForm.length;j++) {
      
      currentElement = document.auditOHDForm.elements[j];
       if (currentElement.type=="checkbox")
       {
        if (currentElement.checked)
         {
          if (checked > 0) audit_ohd_id += ",";
          checked +=1;
          audit_ohd_id +=currentElement.value;
         }
       }
     }

     if (checked < 1)
     {
       alert("<%= (String)bundle.getString("error.validation.missingOHDCompare") %>");
     }
     else
     {
      document.auditOHDForm.audit_ohd_id.value=audit_ohd_id;
      document.auditOHDForm.submit();
    }
	}

  function submitCompareLFDform()
  {
  	
    var checked = 0;
    var audit_lost_found_id="";
    
    for (var j=0;j<document.auditLostFoundForm.length;j++) {
      
      currentElement = document.auditLostFoundForm.elements[j];
       if (currentElement.type=="checkbox")
       {
        if (currentElement.checked)
         {
          if (checked > 0) audit_lost_found_id += ",";
          checked +=1;
          audit_lost_found_id +=currentElement.value;
         }
       }
     }

     if (checked < 1)
     {
       alert("<%= (String)bundle.getString("error.validation.missingLFDCompare") %>");
     }
     else
     {
      document.auditLostFoundForm.audit_id.value=audit_lost_found_id;
      document.auditLostFoundForm.submit();
    }
	}
	
	
	function submitCompareShiftform()
  {
  	
    var checked = 0;
    var id="";
    
    for (var j=0;j<document.auditWorkShiftForm.length;j++) {
      
      currentElement = document.auditWorkShiftForm.elements[j];
       if (currentElement.type=="checkbox")
       {
        if (currentElement.checked)
         {
          if (checked > 0) id += ",";
          checked +=1;
          id +=currentElement.value;
         }
       }
     }

     if (checked < 1)
     {
       alert("<%= (String)bundle.getString("error.validation.missingShiftCompare") %>");
     }
     else
     {
      document.auditWorkShiftForm.audit_id.value=id;
      document.auditWorkShiftForm.submit();
    }
	}
	
	
	function submitCompareLosscodeform()
  {
  	
    var checked = 0;
    var id="";
    
    for (var j=0;j<document.auditLosscodeForm.length;j++) {
      
      currentElement = document.auditLosscodeForm.elements[j];
       if (currentElement.type=="checkbox")
       {
        if (currentElement.checked)
         {
          if (checked > 0) id += ",";
          checked +=1;
          id +=currentElement.value;
         }
       }
     }

     if (checked < 1)
     {
       alert("<%= (String)bundle.getString("error.validation.missingLosscodeCompare") %>");
     }
     else
     {
      document.auditLosscodeForm.audit_id.value=id;
      document.auditLosscodeForm.submit();
    }
	}


	function submitCompareAirportform()
  {
  	
    var checked = 0;
    var id="";
    
    for (var j=0;j<document.auditAirportForm.length;j++) {
      
      currentElement = document.auditAirportForm.elements[j];
       if (currentElement.type=="checkbox")
       {
        if (currentElement.checked)
         {
          if (checked > 0) id += ",";
          checked +=1;
          id +=currentElement.value;
         }
       }
     }

     if (checked < 1)
     {
       alert("<%= (String)bundle.getString("error.validation.missingAirportCompare") %>");
     }
     else
     {
      document.auditAirportForm.audit_id.value=id;
      document.auditAirportForm.submit();
    }
	}
	
	function submitCompareGroupform()
  {
  	
    var checked = 0;
    var id="";
    
    for (var j=0;j<document.auditGroupForm.length;j++) {
      
      currentElement = document.auditGroupForm.elements[j];
       if (currentElement.type=="checkbox")
       {
        if (currentElement.checked)
         {
          if (checked > 0) id += ",";
          checked +=1;
          id +=currentElement.value;
         }
       }
     }

     if (checked < 1)
     {
       alert("<%= (String)bundle.getString("error.validation.missingGroupCompare") %>");
     }
     else
     {
      document.auditGroupForm.audit_id.value=id;
      document.auditGroupForm.submit();
    }
	}
	
	
	function submitCompareAgentform()
  {
  	
    var checked = 0;
    var id="";
    
    for (var j=0;j<document.auditAgentForm.length;j++) {
      
      currentElement = document.auditAgentForm.elements[j];
       if (currentElement.type=="checkbox")
       {
        if (currentElement.checked)
         {
          if (checked > 0) id += ",";
          checked +=1;
          id +=currentElement.value;
         }
       }
     }
     if (checked < 1)
     {
       alert("<%= (String)bundle.getString("error.validation.missingAgentCompare") %>");
     }
     else
     {
      document.auditAgentForm.audit_id.value=id;
      document.auditAgentForm.submit();
    }
	}
  
  function submitCompareStationform()
  {
  	
    var checked = 0;
    var id="";
    
    for (var j=0;j<document.auditStationForm.length;j++) {
      
      currentElement = document.auditStationForm.elements[j];
       if (currentElement.type=="checkbox")
       {
        if (currentElement.checked)
         {
          if (checked > 0) id += ",";
          checked +=1;
          id +=currentElement.value;
         }
       }
     }
     if (checked < 1)
     {
       alert("<%= (String)bundle.getString("error.validation.missingStationCompare") %>");
     }
     else
     {
      document.auditStationForm.audit_id.value=id;
      document.auditStationForm.submit();
    }
	}
	
	
	function submitCompareCompanyform()
  {
    var checked = 0;
    var id="";
    
    for (var j=0;j<document.auditCompanyForm.length;j++) {
      
      currentElement = document.auditCompanyForm.elements[j];
       if (currentElement.type=="checkbox")
       {
        if (currentElement.checked)
         {
          if (checked > 0) id += ",";
          checked +=1;
          id +=currentElement.value;
         }
       }
     }
     if (checked < 1)
     {
       alert("<%= (String)bundle.getString("error.validation.missingCompanyCompare") %>");
     }
     else
     {
      document.auditCompanyForm.audit_id.value=id;
      document.auditCompanyForm.submit();
    }
	}
  
  
  function submitCompareMBRform()
  {
  	
    var checked = 0;
    var audit_incident_id="";
    
    for (var j=0;j<document.auditMBRForm.length;j++) {
      
      currentElement = document.auditMBRForm.elements[j];
       if (currentElement.type=="checkbox")
       {
        if (currentElement.checked)
         {
          if (checked > 0) audit_incident_id += ",";
          checked +=1;
          audit_incident_id +=currentElement.value;
         }
       }
     }

     if (checked < 1)
     {
       alert("<%= (String)bundle.getString("error.validation.missingMBRCompare") %>");
     }
     else
     {
      document.auditMBRForm.audit_incident_id.value=audit_incident_id;
      document.auditMBRForm.submit();
    }
	}

  function submitCompareClaimform()
  {
  	
    var checked = 0;
    var audit_claim_id ="";
    
    for (var j=0;j<document.auditMBRForm.length;j++) {
      
      currentElement = document.auditMBRForm.elements[j];
       if (currentElement.type=="checkbox")
       {
        if (currentElement.checked)
         {
          if (checked > 0) audit_claim_id += ",";
          checked +=1;
          audit_claim_id +=currentElement.value;
         }
       }
     }

     if (checked < 1)
     {
       alert("<%= (String)bundle.getString("error.validation.missingClaimCompare") %>");
     }
     else
     {
      document.auditMBRForm.audit_claim_id.value=audit_claim_id;
      document.auditMBRForm.submit();
    }
	}	  
  
  function validateEditTask(form){
    for (var j=0;j < form.length;j++) {
      currentElement = form.elements[j];
      currentElementName=currentElement.name;
      if (currentElementName.indexOf("description") != -1){
        if (currentElement.value.length < 1){
          alert("<%= (String)bundle.getString("header.tsk_desc") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
          currentElement.focus();
          return false;
        }
      }
      else if (currentElementName.indexOf("dispduedate") != -1)
      {
        if (currentElement.value.length < 1)
        {
          alert("<%= (String)bundle.getString("header.tsk_due_date") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>"); 
          currentElement.focus();
          return false;
        }
        else
        {
          if (currentElement.value.length > 0 && !checkDate(currentElement.value))
          {
            alert("<%= (String)bundle.getString("header.tsk_due_date") %>" + " <%= (String)bundle.getString("error.validation.date") %>"); 
            currentElement.focus();
            return false;
          }
        }
      }
      else if (currentElementName.indexOf("dispreminderdate") != -1)
      {
        if (currentElement.value.length > 0 && !checkDate(currentElement.value))
       	{
       		alert("<%= (String)bundle.getString("header.tsk_reminder_date") %>" + " <%= (String)bundle.getString("error.validation.date") %>"); 
         	currentElement.focus();
         	return false;
        }
      }
      else if (currentElementName.indexOf("dispduetime") != -1)
      {
        if (currentElement.value.length > 0 && !checkTime(currentElement.value))
        {
          alert("<%= (String)bundle.getString("header.tsk_due_time") %>" + " <%= (String)bundle.getString("error.validation.time") %>"); 
          currentElement.focus();
          return false;
        }
      }
      else if (currentElementName.indexOf("dispremindertime") != -1)
      {
        if (currentElement.value.length > 0 && !checkTime(currentElement.value))
        {
          alert("<%= (String)bundle.getString("header.tsk_reminder_time") %>" + " <%= (String)bundle.getString("error.validation.time") %>"); 
          currentElement.focus();
          return false;
        }
      }
       
    }
    return true;
  }

  function validateBDO(form){
    for (var j=0;j < form.length;j++) {
      currentElement = form.elements[j];
      currentElementName=currentElement.name;
      if (currentElementName.indexOf("delivercompany_ID") != -1){
        if (currentElement.value.length < 1){
          alert("<%= (String)bundle.getString("error.choose_deliver") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
          currentElement.focus();
          return false;
        }
      }
      else if (currentElementName.indexOf("servicelevel_ID") != -1)
      {
        if (currentElement.value.length < 1)
        {
          alert("<%= (String)bundle.getString("error.choose_servicelevel") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>"); 
          currentElement.focus();
          return false;
        }
      }
      else if (currentElementName.indexOf("cost") != -1)
      {
        if (currentElement.value.length > 0 && checkFloat(currentElement.value) != true)
        {
          alert("<%= (String)bundle.getString("colname.bdo.cost") %>" + " <%= (String)bundle.getString("error.validation.float") %>"); 
          currentElement.focus();
          return false;
        }
      }
      
      else if (currentElementName.indexOf("address1") != -1)
      {
        if (currentElement.value.length < 1)
        {
          alert("<%= (String)bundle.getString("colname.street_addr") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>"); 
          currentElement.focus();
          return false;
        }
      }
      

      else if (currentElementName.indexOf("city") != -1)
      {
        if (currentElement.value.length < 1)
        {
          alert("<%= (String)bundle.getString("colname.city") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>"); 
          currentElement.focus();
          return false;
        }
      }
      
      else if (currentElementName.indexOf("].state_ID") != -1) {  
      	var pos = currentElementName.indexOf(".");
          var str = currentElementName.substring(0,pos+1) + "countrycode_ID";
  
          if (form.elements[str].value == "US" && currentElement.value.length ==0) {
  	        alert("<%=(String) bundle.getString(
  								"colname.state") + bundle.getString(
                  "error.state.required")%>");
  	        currentElement.focus();
  	        return false;
          }
      }	    


      else if (currentElementName.indexOf("passenger[0].lastname") != -1) {  
        if (currentElement.value.length == 0)
        {
	        alert("<%=(String) bundle.getString(
							"colname.last_name")%>" + " <%=(String) bundle.getString(
							"error.validation.isRequired")%>");
          currentElement.focus();
          return false;
        }
      }
      
      else if (currentElementName.indexOf("zip") != -1)
      {
      	var pos = currentElementName.indexOf(".");
        var str = currentElementName.substring(0,pos+1) + "countrycode_ID";
	    if (form.elements[str].value == "US" && currentElement.value.length ==0)
        {
          alert("<%= (String)bundle.getString("colname.zip") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
          currentElement.focus();
          return false;
        }
      }
      
      
       
    }
    return true;
  }


function validateEditBagDrop(){
	if (!checkTime(document.getElementsByName("editBagDropTime")[0].value)){
		  alert("<%= (String)bundle.getString("colname.bagdrop.bagdroptime") %>" + " <%= (String)bundle.getString("error.validation.time") %>"); 
		  currentElement.focus();
		  return false;
	}
	if (!checkDate(document.getElementsByName("editBagDropDate")[0].value)){
		  alert("<%= (String)bundle.getString("colname.bagdrop.bagdropdate") %>" + " <%= (String)bundle.getString("error.validation.date") %>"); 
		  currentElement.focus();
		  return false;
	}
	return true;
}

var fsar = ['.7em', '.75em', '.8em', '.85em'];
<% 
	if (session.getAttribute("fsarIdx") == null) {
		session.setAttribute("fsarIdx", new Integer(0));
	}

	if (session.getAttribute("sizeToggle") == null) {
		session.setAttribute("sizeToggle", new Integer(0));
	}
%>
	
var fsarIdx = <%=session.getAttribute("fsarIdx")%>;
var sizeToggle = <%=session.getAttribute("sizeToggle")%>;


function changeFontSize() {
	fsarIdx < (fsar.length - 1) ? fsarIdx += 1: fsarIdx = 0;
	document.body.style.fontSize = fsar[fsarIdx];
	jQuery.get("toggle.do", {toggleType: 0, fsarIdx: fsarIdx, fontSize: fsar[fsarIdx]} );
}

function toggleTagSearch() {
	jQuery.get("toggle.do", {toggleType: 1} );
}
	
	
function resizeFooter() {
	var amount = '200px';
	if (sizeToggle == 1) amount = '30px';
	sizeToggle == 0 ? sizeToggle = 1 : sizeToggle = 0;
	
		
	document.getElementById('footer').style.height = amount;
	document.body.style.paddingBottom = amount;

}

function hasurlfocus(col) {

	
	var r = document.body.createControlRange();
	var x = document.getElementById("menucol_" + col + ".0");
	
	
	x.contentEditable = 'true';
	r.addElement(x);
	x.contentEditable = 'false';
	r.select();
	

}

function nourlfocus(col) {
	var x = document.getElementById("menucol_" + col + ".0");
	x.focus();

}

function goleft() {
	var currentFocus = document.activeElement.id;

	if (currentFocus != null && currentFocus.length > 0) {
		
		var index = currentFocus.indexOf("_");
		var index2 = currentFocus.indexOf(".");

		var num;
		if (index > 0 && currentFocus.indexOf("menucol")>=0) {
			num = currentFocus.substring(index+1,index2);
			num = num - 1;
			
			if (num == 0) {
				var x = document.getElementById("menucol_0.0");
				x.focus();
				return;
			}
			if (num < 0) num = <%=total_menu%> - 1;

			var x = document.getElementById("menucol_" + num + ".0");
	
			x.focus();
			

		}
		
	}
	return false;

}

function goright() {
	var currentFocus = document.activeElement.id;
	if (currentFocus != null && currentFocus.length > 0) {
		
		var index = currentFocus.indexOf("_");
		var index2 = currentFocus.indexOf(".");

		var num;
		if (index > 0 && currentFocus.indexOf("menucol")>=0) {
			num = currentFocus.substring(index+1,index2);
			num++;

			if (num > (<%=total_menu%> - 1)) {
				var x = document.getElementById("menucol_0.0");
				x.focus();
				return;
			}
			
			var x = document.getElementById("menucol_" + num + ".0");
			x.focus();

		}
		
	}
	return false;
}

function goup() {
	var currentFocus = document.activeElement.id;
	if (currentFocus != null && currentFocus.length > 0) {
		
		var index = currentFocus.indexOf(".");

		var num;
		if (index > 0 && currentFocus.indexOf("menucol")>=0) {
			num = currentFocus.substring(index+1);
			num=num-1;
			if (num<0) {
				num = 0;
				for (i=20;i>0;i=i-1) {
					if (document.getElementById(currentFocus.substring(0,index+1) + i) != null) {
						num = i;
						break;
					}
				}
			}
			
			var r = document.body.createControlRange();
			var x = document.getElementById(currentFocus.substring(0,index+1) + num);
			x.focus();
	
		}
		
	}
	return false;
}

function godown() {
	var currentFocus = document.activeElement.id;
	if (currentFocus != null && currentFocus.length > 0) {
		
		var index = currentFocus.indexOf(".");

		var num;
		if (index > 0 && currentFocus.indexOf("menucol")>=0) {
			num = currentFocus.substring(index+1);
			num++;
			if (document.getElementById(currentFocus.substring(0,index+1) + num) == null) {
				num = 0;
			}
			
			var r = document.body.createControlRange();
			var x = document.getElementById(currentFocus.substring(0,index+1) + num);
			x.focus();
	
		}
		
	}
	return false;
}


document.onkeydown = function(){
	var currentFocus = document.activeElement.id;

	if (window.event && window.event.keyCode == 117 ) {
		window.event.keyCode = 505;
		document.location.href="<%=request.getContextPath()%>/lostDelay.do?express=1";
		return;
	}

	if (window.event && window.event.keyCode == 118 ) {
		window.event.keyCode = 505;
		document.location.href="<%=request.getContextPath()%>/damaged.do?express=1";
		return;
	}

	if (window.event && window.event.keyCode == 119 ) {
		window.event.keyCode = 505;
		document.location.href="<%=request.getContextPath()%>/missing.do?express=1";
		return;
	}	

	if (window.event && window.event.keyCode == 120 ) {
		window.event.keyCode = 505;
		document.location.href="<%=request.getContextPath()%>/expressOnHandBag.do?express=1";
		return;
	}	

	if (window.event && window.event.keyCode == 121 ) {
		window.event.keyCode = 505;
		document.location.href="<%=request.getContextPath()%>/logon.do?taskmanager=1";
		return;
	}		


	if (window.event && window.event.keyCode == 123 ) {
		window.event.keyCode = 505;
		openHelp('pages/WebHelp/nettracerhelp.htm');
		return;
	}		
	

	if (window.event && window.event.keyCode == 77 && window.event.ctrlKey) {
		hasurlfocus(0);
	}
    
	if (window.event && window.event.keyCode == 69 && window.event.ctrlKey) {
		window.event.keyCode = 505;
		nourlfocus(1);
	}

 	if (window.event && window.event.keyCode == 71 && window.event.ctrlKey) {
 		window.event.keyCode = 505;
		nourlfocus(2);
	}

	if (window.event && window.event.keyCode == 73 && window.event.ctrlKey) {
		window.event.keyCode = 505;
		nourlfocus(3);
	}

	if (window.event && window.event.keyCode == 79 && window.event.ctrlKey) {
		window.event.keyCode = 505;
		nourlfocus(4);
	}

	if (window.event && window.event.keyCode == 85 && window.event.ctrlKey) {
		window.event.keyCode = 505;
		nourlfocus(5);
	}

	if (window.event && (window.event.keyCode == 113 || (window.event.keyCode == 83 && window.event.ctrlKey))) {
		window.event.keyCode = 505;
		loadQuickSearchModal();
	}
	
	if (window.event && (window.event.keyCode == 114 || (window.event.keyCode == 72 && window.event.ctrlKey))) {
		window.event.keyCode = 505;
		loadQuickHistoryModal();
	}
	
	if (window.event && (window.event.keyCode == 78 && window.event.ctrlKey)) {
		window.event.keyCode = 505;
		document.location.href="shelved_trace_results.do?getNextItem=1";
	}

	if (window.event && window.event.keyCode == 84 && window.event.ctrlKey) {
		window.event.keyCode = 505;
		nourlfocus(6);
	}

	if (window.event && window.event.keyCode == 82 && window.event.ctrlKey) {
		window.event.keyCode = 505;
		nourlfocus(7);
	}

	if (window.event && window.event.keyCode == 68 && window.event.ctrlKey) {
		window.event.keyCode = 505;
		nourlfocus(8);
	}

	if (window.event && window.event.keyCode == 76 && window.event.ctrlKey) {
		window.event.keyCode = 505;
		nourlfocus(9);
	}

	if(window.event && window.event.keyCode == 37 && currentFocus.indexOf("menucol") >= 0) { 
		window.event.keyCode = 505;
		goleft();
	}
	

	if(window.event && window.event.keyCode == 39 && currentFocus.indexOf("menucol") >= 0) { 
		window.event.keyCode = 505;
		goright();
	}
	

	if(window.event && window.event.keyCode == 38 && currentFocus.indexOf("menucol") >= 0) { 
		window.event.keyCode = 505;
		goup();
	}	

	if(window.event && window.event.keyCode == 40 && currentFocus.indexOf("menucol") >= 0) { 
		window.event.keyCode = 505;
		godown();
	}
	

	if (window.event && window.event.keyCode == 27) {
		var root = document.getElementById("menuList");
		hideAllMenus(root,root);
	}

	if (document.activeElement.id == "barcode" && window.event && window.event.keyCode == 13) {
		window.event.keyCode = 9;
	}

	if (document.activeElement.id == "addBarcode" && window.event && window.event.keyCode == 13) {
		window.event.keyCode = 9;
		addItemAjax();
	}

	if(window.event && window.event.keyCode == 505) { 
		return false; 
	}
};

function verifyPaginationLimit(field) {
	if (field.value < 5) {
		window.alert("This value must be 5 or greater.");
		field.value = 5;
		field.form.submit();
	}
}

function populateBagInfo(form) {
	var arrivalAirline = document.getElementsByName("theitem[0].arrivedonairline_ID")[0].value;
	var arrivalFlight = document.getElementsByName("theitem[0].arrivedonflightnum")[0].value;
	var arrivalDate = document.getElementsByName("theitem[0].disarrivedondate")[0].value;

	for (var i = 1;; ++i) {
		var element = document.getElementById("theitem[" + i + "].arrivedonairline_ID");
		if (!element) {
			break;
		}
		
		document.getElementById("theitem[" + i + "].arrivedonairline_ID").value = arrivalAirline;
		document.getElementById("theitem[" + i + "].arrivedonflightnum").value = arrivalFlight;
		document.getElementById("theitem[" + i + "].disarrivedondate").value = arrivalDate;
	}
	
}

function submitPrintRequest(url, options) {
	if (!url || url == null) return;

	var output;
	var reportOptionsDialog = jQuery("#reportOptionsDiv").dialog({
								height: 50,
								width: 200,
								title: '<%=(String)bundle.getString("print.options.dialog.title") %>',
								modal: true,
								buttons: {
									Ok: function() {
										jQuery(this).dialog("close");
										var outputSelect = document.getElementById("outputSelect");
										output = outputSelect.options[outputSelect.selectedIndex].value;
										window.open(url+'&outputtype='+output, '', options);										
									},
									Cancel: function() {
										jQuery(this).dialog("close");
									}
								}
							});
	reportOptionsDialog.dialog("open");
}
