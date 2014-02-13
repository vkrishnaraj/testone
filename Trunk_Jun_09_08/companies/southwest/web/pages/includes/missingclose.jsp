<%@page import="java.util.Locale"%>
<%@page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%@page import="org.apache.struts.util.PropertyMessageResources"%>
<%@page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%@ page language="java"%>

<%
	Agent a = (Agent) session.getAttribute("user");
    PropertyMessageResources myMessages = (PropertyMessageResources) request.getAttribute("org.apache.struts.action.MESSAGE");
    Locale  myLocale   = (Locale)session.getAttribute("org.apache.struts.action.LOCALE");
%>

function validateMissingClose(form, doCheck)
{
	if (doCheck == 1 || doSaveCheck == 1) {
	
    	var hasRemarkText  = false;
	    var departStation=false;
	    var transStation=false;
	    var destStation=false;
	    var anyStation=false;
    	for (var j=0; j < form.length; j++) {
	      currentElement = form.elements[j];
	      currentElementName=currentElement.name;
	      
	      if (currentElementName.indexOf("remark[") != -1 && currentElementName.indexOf("remark[") < currentElementName.indexOf("].remarktext")) {
	      	if (!hasRemarkText) {
				hasRemarkText = true;
	    	 }
	  	 } 
	      
	      if (currentElementName.indexOf("].lossCode") != -1) { 
	        if (currentElement.value.length == 0 || currentElement.value == "0") {
	          alert("<%= (String)myMessages.getMessage(myLocale, "colname.loss.code") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
	          currentElement.focus();
	          return false;
	        }
	        
	        if(lossCodeChange!=false){
  				alert("<%=(String)myMessages.getMessage(myLocale, "loss.code.remark")%>" + " <%=(String)myMessages.getMessage(myLocale,	"error.validation.isRequired")%>");
     	
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
		      		for(var m=0; m < paxItinList.childNodes.length; m++){
		      				var paxItin=paxItinList.children[m];
		      				var checkFromStation=false;
		      				var checkToStation=false;
				      		for (var k=0; k < paxItin.childNodes.length; k++) {
						      itinElement = paxItin.children[k];
						      itinElementName=null;
						      if(typeof itinElement != "undefined")
						      	itinElementName=itinElement.name;
						      	
						      	if(typeof itinElementName != "undefined" && itinElementName != null 
				      				&& (itinElementName.indexOf("legto_type") != -1)){
				      				if((departStation && itinElement.value=="<%=TracingConstants.LEG_B_STATION%>") 
				      					|| (transStation && itinElement.value=="<%=TracingConstants.LEG_T_STATION%>")
				      					|| (destStation && itinElement.value=="<%=TracingConstants.LEG_E_STATION%>")){
				      					checkToStation=true;
				      				}
				      			}
				      			if(typeof itinElementName != "undefined" && itinElementName != null 
				      				&& (itinElementName.indexOf("legfrom_type") != -1)){
				      				if((departStation && itinElement.value=="<%=TracingConstants.LEG_B_STATION%>") 
				      					|| (transStation && itinElement.value=="<%=TracingConstants.LEG_T_STATION%>")
				      					|| (destStation && itinElement.value=="<%=TracingConstants.LEG_E_STATION%>")){
				      					checkFromStation=true;
				      				}
				      			}
				      				      			
				      			if(typeof itinElementName != "undefined" && itinElementName != null 
				      				&& itinElementName.indexOf("legfrom") != -1 && itinElementName.indexOf("legfrom_type") == -1){
				      				if(itinElement.value.toUpperCase()==faultStationCode && checkFromStation){
				      					hasPaxItin=true;
				      				}
				      			}
				      			if(typeof itinElementName != "undefined" && itinElementName != null
				      				 && itinElementName.indexOf("legto") != -1 && itinElementName.indexOf("legto_type") == -1){
				      				if(itinElement.value.toUpperCase()==faultStationCode && checkToStation){
				      					hasPaxItin=true;
				      				}
				      			}
				      			
				      			if(anyStation){
				      				hasPaxItin=true;
				      			}
				      		}
			      		}
		      		}
		      		
		      		if(!hasPaxItin){
		  				alert("<%=(String)myMessages.getMessage(myLocale,"error.fault.pax.itin")%>");
		     			currentElement.focus();
		  				return false;
		  			}
	  			} else if (currentElement!=null && currentElement.value.length<1){
		          alert("<%= (String)myMessages.getMessage(myLocale,"colname.fault.station") %>" + " <%= (String)myMessages.getMessage(myLocale,"error.validation.isRequired") %>");
		          currentElement.focus();
		          return false;
	        	}
  			} else if (currentElementName.indexOf("remarktext") != -1) {  
			      if (currentElement.value.length == 0) {
			        alert("<%= (String)myMessages.getMessage(myLocale, "colname.remark") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
				    currentElement.focus();
				    return false;
			      }
			} 
	    }
	    
	    if (!hasRemarkText) {
			alert("<%= (String)myMessages.getMessage(myLocale, "colname.remark") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
			currentElement = document.getElementById('addremark');
			if (currentElement) {
				currentElement.focus();
			} 
			return false;
	     } 
	    validateClose();
    }
	return true;
}