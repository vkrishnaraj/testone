<%@page import="com.bagnet.nettracer.tracing.forms.ClaimForm"%>
<%@ page language="java" %>
<%@ page import="com.bagnet.nettracer.tracing.db.ExpensePayout" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.OHD_Photo" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO" %>
<%@ page import="aero.nettracer.fs.model.Person" %>
<%@ page import="aero.nettracer.fs.model.FsReceipt" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.DateUtils" %>
<%@ page import="org.apache.struts.util.LabelValueBean" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Company" %>
<%@ page import="java.util.ArrayList" %>
<%
  Agent a = (Agent)session.getAttribute("user");
  ArrayList<LabelValueBean> stateList = (ArrayList<LabelValueBean>) session.getAttribute("statelist");
  ArrayList<Company> companyList = (ArrayList<Company>) session.getAttribute("companylistByName");
  ArrayList<LabelValueBean> countryList = (ArrayList<LabelValueBean>) session.getAttribute("countrylist");
	
  boolean ntUser = PropertyBMO.isTrue("nt.user");
  boolean ntfsUser = PropertyBMO.isTrue("ntfs.user");

%>
  
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	
	
    function changebutton() {
        document.claimForm.saveclaim.disabled = true;
        document.claimForm.saveclaim.value = "<bean:message key="ajax.please_wait" />";
        document.claimForm.save.disabled = false;
      }
      
      function undoChangebutton() {
        document.claimForm.saveclaim.disabled = false;
        document.claimForm.saveclaim.value = "<bean:message key="button.claim.resolution.save" />";
        document.claimForm.save.disabled = true;
      }

      function show(name,link1,link2) {
    	  jQuery(name).show();
    	  toggleLinks(link1,link2);
    	  document.getElementById(name).value = "true";
      }
      
      function hide(name,link1,link2) {
    	  jQuery(name).hide();
    	  toggleLinks(link1,link2);
    	  document.getElementById(name).value = "false";
      }
      
      function toggleLinks(link1,link2) {
	  	  if (jQuery(link1).is(':visible')) {
	  		  jQuery(link1).hide();
	  		  jQuery(link2).show();
	  	  } else {
	  		  jQuery(link1).show();
	  		  jQuery(link2).hide();
	  	  }
  	  }
      
      function setField(fieldId) {
    	  var field = document.getElementById(fieldId);
    	  field.value = "1";
      }
      
      function fieldChanged(fieldId) {
    	  if (fieldId.indexOf("state") != -1) {
    		  var state = document.getElementById(fieldId);
    		  var province = document.getElementById(fieldId.replace("state", "province"));
    		  var country = document.getElementById(fieldId.replace("state", "country"));
    		  stateChanged(state, province, country);
    	  } else if (fieldId.indexOf("province") != -1) {
    		  var state = document.getElementById(fieldId.replace("province", "state"));
    		  var province = document.getElementById(fieldId);
    		  var country = document.getElementById(fieldId.replace("province", "country"));
    		  provinceChanged(state, province, country);
    	  } else if (fieldId.indexOf("country") != -1) {
    		  var state = document.getElementById(fieldId.replace("country", "state"));
    		  var province = document.getElementById(fieldId.replace("country", "province"));
    		  var country = document.getElementById(fieldId);
    		  countryChanged(state, province, country);
    	  }
      }
      
      function stateChanged(state, province, country) {
    	 if (!state || !province || !country) {
    		 return;
    	 }
    	 
  		if (state.value == "") {
  			province.disabled = false;	
  			province.className = "textfield";
  		} else {
  			province.value = "";
  			province.disabled = true;
  			province.className = "disabledtextfield";
  			country.value = "US";
  		}
  	}
  	
  	function provinceChanged(state, province, country) {
    	 if (!state || !province) {
    		 return;
    	 }
  		if (province.value == "") {
  			state.disabled = false;
  		} else {
  			state.value = "";
  			state.disabled = true;
  		}
  	}
  	
  	function countryChanged(state, province, country) {
    	 if (!state || !province || !country) {
    		 return;
    	 }
  		if (country.value == "") {
  			state.disabled = false;
  			province.disabled = false;
  			province.className = "textfield";
  		} else if (country.value == "US") {
  			state.disabled = false;
  			province.value = "";
  			province.disabled = true;
  			province.className = "disabledtextfield";
  		} else {
  			state.value = "";
  			state.disabled = true;
  			province.disabled = false;
  			province.className = "textfield";
  		}
  	}
      
  </SCRIPT>
 
        <html:form action="Commonsfileuploadservlet" method="post" enctype="multipart/form-data" >
          
					
					<% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_SHARED_ATTACHMENTS, a)) { %>
					 <h1 class="green">
                   		<bean:message key="header.shared.files" />
					 </h1>
					<table class="form2" cellpadding="0" cellspacing="0">
				              <tr>
				                <td colspan="3">
				                  <bean:message key="header.attachments" />
				                  :
				                  <br>
				                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
									<%
				                    int k = 0;
									%>
				                    <logic:present name="attachmentsh">
				                      <bean:define id="attachment" name="attachments" />
				                      <logic:iterate id="attachments" indexId="j" name="attachment" type="com.bagnet.nettracer.tracing.db.Attachment">
										<%
				                        if (k % 3 == 0) {
										%>
				                          <tr align="center">
										<%
				                          }
										%>
				                          <td align="center">
				                            <!--<a href='showImage?ID=bean:write name="attachmentlist" property="picpath"/>' target="top"></a>  <img src='showImage?ID=bean:write name="photolist" property="thumbpath"/>'> -->
				                            <a href='showImage?ID=<bean:write name="attachment" property="attachment_id"/>' target="top"><bean:write name="attachment" property="description"/></a>
				                            <br>
				                            <input type="submit" name="removeAttachment_<%= j %>" id="button" value="<bean:message key="button.delete_attachment"/>">
				                          </td>
										<%
				                          if (k % 3 == 2) {
										%>
				                          </tr>
										<%
				                        }
				                        k++;
										%>
				                      </logic:iterate>
				                    </logic:present>
				                  
				                </table>
				                <br>
				                <center><input type="FILE" name='<%= "attachfile" %>' />
				                &nbsp;
				                <html:submit property="uploadAttachment" styleId="button">
				                  <bean:message key="header.addAttachments" />
				                </html:submit></center>
				              </td>
				            </tr>
			            </table>
						<%
				          }
						%>
					
					
                    <center>
                    <html:submit property="save" styleId="button">
                      <bean:message key="button.save" />
                    </html:submit>
                    <logic:notEmpty name="back" scope="request" >
                    	&nbsp;&nbsp;
	            		<input id="button" type="button" value='<bean:message key="claim.button.back" />' onClick="history.back()">
                    </logic:notEmpty>
                    </center>
                  </html:form>
					