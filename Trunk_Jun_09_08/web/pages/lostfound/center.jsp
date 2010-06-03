<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.forms.LostFoundIncidentForm" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Status" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%
  Agent a = (Agent)session.getAttribute("user");
%>
  <SCRIPT LANGUAGE="JavaScript">
    function textCounter(field, countfield, maxlimit) {
      if (field.value.length > maxlimit) {
        field.value = field.value.substring(0, maxlimit);
      } else {
        countfield.value = maxlimit - field.value.length;
      }
    }

  </SCRIPT>
  <script language="javascript">
    
function gotoHistoricalReport() {
  o = document.LostAndFoundForm;
	o.historical_report.value = "1";
	o.submit();
}

  </script>
  
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	

  </SCRIPT>
  
    <jsp:include page="/pages/includes/validation_lf.jsp" />
<%
  String found = (String)request.getAttribute("found");
%>
<%
  if (found == null) {
%>
    <form name="LostAndFoundForm" method="post"action="addLost.do" onsubmit="return validateLostFound(this);">
<%
    } else {
%>
      <form name="LostAndFoundForm" method="post" action="addFound.do"  enctype="multipart/form-data" onsubmit="return validateLostFound(this);">
<%
      }
%>
      <input type="hidden" name="historical_report" value="">
      <tr>
        <td colspan="3" id="pageheadercell">
          <div id="pageheaderleft">
            <logic:empty name="found" scope="request">
              <h1>
                <bean:message key="header.add_lost_report_title" />
              </h1>
            </logic:empty>
            <logic:notEmpty name="found" scope="request">
              <h1>
                <bean:message key="header.add_found_report_title" />
              </h1>
            </logic:notEmpty>
          </div>
          <div id="pageheaderright">
            <table id="pageheaderright">
              <tr>
                <td></td>
                <td></td>
                <jsp:include page="/pages/includes/mail_incl.jsp" />
                <td>
                  <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><bean:message key="Help" /></a>
                </td>
              </tr>
            </table>
          </div>
        </td>
      </tr>
      
      
      <tr>
        
        <td id="middlecolumn">
          
          <div id="maincontent">
            <h1 class="green">
              <bean:message key="header.item_info" />
              <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#On_Hand_Bag.htm#Found_and_unclaimed');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
            </h1>
            <p align="right">
              <logic:notEqual name="LostAndFoundForm" property="file_ref_number" value="">
                <A HREF="#" onclick="gotoHistoricalReport();"><b><bean:message key="report_history" /></b></A>
              </logic:notEqual>
            </p>
            <span class="reqfield">*</span>
            <bean:message key="Required" />
            <font color=red>
              <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
            </font>
            <br>
            <table class="form2_lf" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <bean:message key="colname.lostfound_ref_number" />
                  <br>
                  <html:text name="LostAndFoundForm" property="file_ref_number" size="14" styleClass="textfield" disabled="true" />
                </td>
                <td>
                  <bean:message key="colname.create_date_time" />
                  <br>
                  <html:text name="LostAndFoundForm" property="dispCreateTime" size="14" styleClass="textfield" disabled="true" />
                </td>
                <td>
                  <bean:message key="colname.agent_initials" />
                  <br>
                  <html:text name="LostAndFoundForm" property="filing_agent.username" size="10" styleClass="textfield" disabled="true" />
                </td>
                <td>
                  <bean:message key="colname.filing_station" />
                  <br>
                  <input name="LostAndFoundForm" type=text size=10 class="textfield" value="<bean:write name="LostAndFoundForm" property="create_station.company.companyCode_ID"/> <bean:write name="LostAndFoundForm" property="create_station.stationcode"/>" disabled>
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.lostfound_status" />
                  <br>
                  <div id="tohide1">
                  <html:select name="LostAndFoundForm" property="report_status.status_ID" styleClass="dropdown">
                    <html:options collection="oStatusList" property="status_ID" labelProperty="description" />
                  </html:select>
                  </div>
                </td>
                <td>
                  <bean:message key="colname.item_status" />
                  <br>
   
                   <html:select name="LostAndFoundForm" property="disposal_status.status_ID" styleClass="dropdown">
                     <OPTION VALUE=""><bean:message key="select.none" /></option>
                     <html:options collection="dStatusList" property="status_ID" labelProperty="description" />
                   </html:select>

                </td>
                <logic:equal name="LostAndFoundForm" property="report_status.status_ID" value='<%= "" + TracingConstants.LOST_FOUND_CLOSED %>'>
                  <td>
                    <bean:message key="colname.file_close_date" />
                    <br>
                    <html:text name="LostAndFoundForm" property="dispCloseDateTime" size="16" styleClass="textfield" disabled="true" />
                  </td>
                  <td>
                    <bean:message key="colname.closed_by" />
                    <br>
                    <logic:notEmpty name="LostAndFoundForm" property="closing_agent">
                      <html:text name="LostAndFoundForm" property="closing_agent.username" size="10" styleClass="textfield" disabled="true" />
                    </logic:notEmpty>
                    &nbsp;
                  </td>
                </logic:equal>
                <logic:notEqual name="LostAndFoundForm" property="report_status.status_ID" value='<%= "" + TracingConstants.LOST_FOUND_CLOSED %>'>
                  <td colspan="2">
                    &nbsp;
                  </td>
                </logic:notEqual>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.last_name" />
                  <br>
                  <html:text name="LostAndFoundForm" property="customer_lastname" size="20" maxlength="20" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.first_name" />
                  <br>
                  <html:text name="LostAndFoundForm" property="customer_firstname" size="20" maxlength="20" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.mid_initial" />
                  <br>
                  <html:text name="LostAndFoundForm" property="customer_mname" size="1" maxlength="1" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.email" />
                  <br>
                  <html:text name="LostAndFoundForm" property="customer_email" size="30" maxlength="50" styleClass="textfield" />
                </td>
              </tr>
              <tr>
                <td colspan=2>
                  <bean:message key="colname.street_addr1" />
                  <br>
                  <html:text name="LostAndFoundForm" property="customer_address1" size="45" maxlength="50" styleClass="textfield" />
                </td>
                <td colspan=2>
                  <bean:message key="colname.street_addr2" />
                  <br>
                  <html:text name="LostAndFoundForm" property="customer_address2" size="45" maxlength="50" styleClass="textfield" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.city" />
                  <br>
                  <html:text name="LostAndFoundForm" property="customer_city" size="15" maxlength="50" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.state" />
                  <br>
                  <logic:equal name="LostAndFoundForm" property="customer_countrycode_ID" value="US">
                    <html:select name="LostAndFoundForm" property="customer_state_ID" styleClass="dropdown" onchange="updateCountryUS(this, this.form, 'customer_countrycode_ID', 'customer_province');">
                      <html:option value="">
                        <bean:message key="select.none" />
                      </html:option>
                      <html:options collection="statelist" property="value" labelProperty="label" />
                    </html:select>
                  </logic:equal>
                  <logic:equal name="LostAndFoundForm" property="customer_countrycode_ID" value="">
                    <html:select name="LostAndFoundForm" property="customer_state_ID" styleClass="dropdown" onchange="updateCountryUS(this, this.form, 'customer_countrycode_ID', 'customer_province');">
                      <html:option value="">
                        <bean:message key="select.none" />
                      </html:option>
                      <html:options collection="statelist" property="value" labelProperty="label" />
                    </html:select>
                  </logic:equal>
                  <logic:notEqual name="LostAndFoundForm" property="customer_countrycode_ID" value="">
                    <logic:notEqual name="LostAndFoundForm" property="customer_countrycode_ID" value="US">
                      <html:select name="LostAndFoundForm" property="customer_state_ID" styleClass="dropdown" disabled="true" onchange="updateCountryUS(this, this.form, 'customer_countrycode_ID', 'customer_province');">
                        <html:option value="">
                          <bean:message key="select.none" />
                        </html:option>
                        <html:options collection="statelist" property="value" labelProperty="label" />
                      </html:select>
                    </logic:notEqual>
                  </logic:notEqual>
                </td>
                <td>
                  <bean:message key="colname.province" />
                  <br>
                      <logic:equal name="LostAndFoundForm" property="customer_countrycode_ID" value="US">
                  <html:text name="LostAndFoundForm" property="customer_province" size="15" maxlength="100" styleClass="disabledtextfield" disabled="true" />
                      </logic:equal>
                      <logic:equal name="LostAndFoundForm" property="customer_countrycode_ID" value="">
                  <html:text name="LostAndFoundForm" property="customer_province" size="15" maxlength="100" styleClass="textfield" />
                      </logic:equal>
                      <logic:notEqual name="LostAndFoundForm" property="customer_countrycode_ID" value="">
                        <logic:notEqual name="LostAndFoundForm" property="customer_countrycode_ID" value="US">
                  <html:text name="LostAndFoundForm" property="customer_province" size="15" maxlength="100" styleClass="textfield" />
                         </logic:notEqual>
                      </logic:notEqual>
                </td>
                <td>
                  <bean:message key="colname.zip" />
                  <br>
                  <html:text name="LostAndFoundForm" property="customer_zip" size="15" maxlength="11" styleClass="textfield" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.country" />
                  <br>
                  <html:select name="LostAndFoundForm" property="customer_countrycode_ID" styleClass="dropdown" onchange="checkstate(this,this.form,'customer_state_ID', 'customer_province');">
                    <html:option value="">
                      <bean:message key="select.none" />
                    </html:option>
                    <html:options collection="countrylist" property="value" labelProperty="label" />
                  </html:select>
                </td>
                <td>
                  <bean:message key="colname.phone" />
                  <br>
                  <html:text name="LostAndFoundForm" property="customer_tel" size="15" maxlength="25" styleClass="textfield" />
                </td>
                <td colspan="2">
                  <table width="100%">
                    <tr>
<%
                      if (found != null) {
%>
                        <td>
                          <bean:message key="colname.found_by" />
                          <br>
                          <html:text name="LostAndFoundForm" property="finding_agent_name" size="25" maxlength="100" styleClass="textfield" />
                        </td>
<%
                      }
%>
                      <td>
<%
                        if (found == null) {
%>
                          <bean:message key="colname.date_lost" />
<%
                        } else {
%>
                          <bean:message key="colname.date_found" />
<%
                        }
%>
                        (
                        <%= a.getDateformat().getFormat() %>)
                        <br>
                        <html:text name="LostAndFoundForm" property="dispDateFoundLost" size="11" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.LostAndFoundForm.dispDateFoundLost,'calendar','<%= a.getDateformat().getFormat() %>'); return false;"></td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td colspan="2">
<%
                  if (found == null) {
%>
                    <bean:message key="colname.lost_location" />
<%
                  } else {
%>
                    <bean:message key="colname.found_location" />
<%
                  }
%>
                  <br>
                  <html:textarea rows="7" cols="40" name="LostAndFoundForm" property="location" onkeydown="textCounter(this.form.location,this.form.remLen,255);" onkeyup="textCounter(this.form.location,this.form.remLen,255);" />
                  <input disabled="true" type="text" name=remLen size=4 maxlength=4 value="255">
                </td>
                <td colspan="2">
                  <bean:message key="colname.idescription" />
                  <br>
                  <html:textarea rows="7" cols="40" name="LostAndFoundForm" property="item_description" onkeydown="textCounter(this.form.item_description,this.form.remLen2,255);" onkeyup="textCounter(this.form.item_description,this.form.remLen2,255);" />
                  <input disabled="true" type="text" name=remLen2 size=4 maxlength=4 value="255">
                </td>
              </tr>
              
              
<%
  if (found != null) {
%>              
              <tr>
                <td colspan="4">
                  <bean:message key="header.photos" />
                  <br>
              
            		<a name="photos"></a>
           
		            <table class="form2_lf" cellspacing="0" cellpadding="0">
		              <tr>
		                <td>
		                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
		<%
		                    int k = 0;
		%>
		                    <logic:present name="LostAndFoundForm" property="photoList">
		                      <logic:iterate id="photo" name="LostAndFoundForm" property="photoList" type="com.bagnet.nettracer.tracing.db.LostAndFound_Photo">
		<%
		                        if (k % 3 == 0) {
		%>
		                          <tr align="center">
		<%
		                          }
		%>
		                          <td align=center>
		                            <a href='showImage?ID=<bean:write name="photo" property="picpath"/>' target="top"><img src='showImage?ID=<bean:write name="photo" property="thumbpath"/>'></a>
		                            <br>
		                            <a href='showImage?ID=<bean:write name="photo" property="picpath"/>' target="top"><bean:write name="photo" property="fileName"/></a>
                            		<br>
		                            <html:submit styleId="button" property="deletePhoto" indexed="true">
		                              <bean:message key="button.delete_photo" />
		                            </html:submit>
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
		                  </tr>
		                </table>
		                <br>
		                <center><input type="FILE" name="theFile1" />
		                &nbsp;
		                <html:submit property="uploadPhoto" styleId="button">
		                  <bean:message key="header.addPhotos" />
		                </html:submit>
		              </td>
		            </tr>
		          </table>

              	</td>
              </tr>
              
<%
  }
%>             
              <tr>
                <td colspan="4">
                  <bean:message key="colname.remarks" />
                  <br>
                  <html:textarea rows="10" cols="88" name="LostAndFoundForm" property="remark" onkeydown="textCounter(this.form.remark,this.form.remLen3,1500);" onkeyup="textCounter(this.form.remark,this.form.remLen3,1500);" />
                  <input disabled="true" type="text" name=remLen3 size=4 maxlength=4 value="1500">
                </td>
              </tr>
            </table>
          </div>
          <br>
          <center>
            <logic:notEqual name="LostAndFoundForm" property="file_ref_number" value="">
              <input type="button" Id="button" value="Back" onClick="history.back()">&nbsp;
            </logic:notEqual>
            <html:submit styleId="button" property="save" styleId="button">
              <bean:message key="button.save" />
            </html:submit>
          </center>
        </form>
