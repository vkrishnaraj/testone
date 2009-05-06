<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.db.OHD" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%
  Agent a = (Agent)session.getAttribute("user");
%>
  
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	


  </SCRIPT>

  <script language="javascript">
    
function goprev() {
  o = document.searchLostAndFoundForm;
  o.prevpage.value = "1";
  o.pagination.value="1";
  o.submit();
}

function gonext() {
  o = document.searchLostAndFoundForm;
  o.nextpage.value="1";
  o.pagination.value="1";
  o.submit();
}

function gopage(i) {
  o = document.searchLostAndFoundForm;
  o.currpage.value = i;
  o.pagination.value="1";
  o.submit();

}
function updatePagination() {
    return true;
}

  </script>
  <html:form action="retrieveLostFound.do" method="post" onsubmit="fillzero(this.file_ref_number, 13); return true;">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.search_lost_found" />
          </h1>
        </div>
        <div id="pageheaderright">
          <table id="pageheaderright">
            <tr>
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
            <bean:message key="header.search_criteria" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#Retrieve.htm#Retrieve_On_Hand');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <br>
          <strong>
            <bean:message key="wildcard" />
          </strong>
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
            <table class="form2" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <bean:message key="colname.lostfound_ref_number" />
                  <br>
                  <html:text property="file_ref_number" size="14" maxlength="13" styleClass="textfield" onblur="fillzero(this,13);" />
                </td>
                <td>
                  <bean:message key="colname.lostfound_status" />
                  <br>
                  <html:select property="report_status_ID" styleClass="dropdown">
                    <html:option value="">
                      <bean:message key="select.all" />
                    </html:option>
                    <html:options collection="oStatusList" property="status_ID" labelProperty="description" />
                  </html:select>
                </td>
                <td>
                  <bean:message key="colname.item_status" />
                  <br>
                  <html:select property="disposal_status_ID" styleClass="dropdown">
                    <html:option value="">
                      <bean:message key="select.all" />
                    </html:option>
                    <html:options collection="dStatusList" property="status_ID" labelProperty="description" />
                  </html:select>
                </td>
                <td>
                  <bean:message key="colname.report_type" />
                  <br>
                  <select name="report_type" class="dropdown">
                    <option value="">
                    <bean:message key="select.all" />
                    <logic:notEmpty name="searchLostAndFoundForm" property="report_type">
                      <logic:equal name="searchLostAndFoundForm" property="report_type" value='<%= "" + TracingConstants.LOST_REPORT %>'>
                        <option value="1" selected>
                        <bean:message key="lost" />
                      </logic:equal>
                      <logic:notEqual name="searchLostAndFoundForm" property="report_type" value='<%= "" + TracingConstants.LOST_REPORT %>'>
                        <option value="1">
                        <bean:message key="lost" />
                      </logic:notEqual>
                      <logic:equal name="searchLostAndFoundForm" property="report_type" value='<%= "" + TracingConstants.FOUND_REPORT %>'>
                        <option value="0" selected>
                        <bean:message key="found" />
                      </logic:equal>
                      <logic:notEqual name="searchLostAndFoundForm" property="report_type" value='<%= "" + TracingConstants.FOUND_REPORT %>'>
                        <option value="0">
                        <bean:message key="found" />
                      </logic:notEqual>
                    </logic:notEmpty>
                    <logic:empty name="searchLostAndFoundForm" property="report_type">
                      <option value="1">
                      <bean:message key="lost" />
                      <option value="0">
                      <bean:message key="found" />
                    </logic:empty>
                  </select>
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.location" />
                  <br>
                  <html:text property="location" size="25" maxlength="100" styleClass="textfield" />
                </td>
                <td colspan=2>
                  <bean:message key="colname.found_by" />
                  <br>
                  <html:text property="finding_agent_name" size="25" maxlength="50" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.filing_station" />
                  <br>
                  <html:select property="filing_station" styleClass="dropdown">
                    <html:option value="">
                      <bean:message key="select.all" />
                    </html:option>
	                  <html:options collection="stationlist" property="station_ID" labelProperty="stationcode" />
	                </html:select>
                </td>
              </tr>
              <tr>
                <td colspan=4>
                  <bean:message key="colname.idescription" />
                  <br>
                  <html:text property="item_description" size="50" maxlength="100" styleClass="textfield" />
              </tr>
              <tr>
                <td colspan=4>
                  <bean:message key="colname.date_lostfound" />
                  (
                  <%= a.getDateformat().getFormat() %>)
                  <br>
                  <html:text property="s_createtime" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.searchLostAndFoundForm.s_createtime,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">-
                  <html:text property="e_createtime" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.searchLostAndFoundForm.e_createtime,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;"></td>
              </tr>
              <tr>
                <td colspan="4" align="center" valign="top">
                  <html:submit property="search" styleId="button">
                    <bean:message key="button.retrieve" />
                  </html:submit>
                  &nbsp;
                  <html:button styleId="button" property="reset" onclick="document.location.href='retrieveLostFound.do';">
                    <bean:message key="button.reset" />
                  </html:button>
                </td>
              </tr>
            </table>
            <logic:present name="lostfoundlist" scope="request">
            
            <h1 class="green">
              <bean:message key="header.search_result" />
              <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#Retrieve.htm#Retrieve_On_Hand');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
            </h1>
            <a name="result"></a>
            <table class="form2" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <strong>
                    <bean:message key="colname.lostfound_ref_number" />
                  </strong>
                </td>
                <td>
                  <strong>
                    <bean:message key="colname.report_type" />
                  </strong>
                </td>
                <td>
                  <strong>
                    <bean:message key="colname.create_date_time" />
                  </strong>
                </td>
                <td>
                  <strong>
                    <bean:message key="colname.filing_station" />
                  </strong>
                </td>
                <td>
                  <strong>
                    <bean:message key="colname.lostfound_status" />
                  </strong>
                </td>
              </tr>
              <logic:iterate id="lfd" name="lostfoundlist">
                <tr>
                  <logic:equal name="lfd" property="report_type" value="1">
                    <td>
                      <A HREF="addLost.do?file_ref_number=<bean:write name="lfd" property="file_ref_number"/>"><bean:write name="lfd" property="file_ref_number" /></a>
                    </td>
                    <td>
                      <bean:message key="lost" />
                    </td>
                  </logic:equal>
                  <logic:equal name="lfd" property="report_type" value="0">
                    <td>
                      <A HREF="addFound.do?file_ref_number=<bean:write name="lfd" property="file_ref_number"/>"><bean:write name="lfd" property="file_ref_number" /></a>
                    </td>
                    <td>
                      <bean:message key="found" />
                    </td>
                  </logic:equal>
                  <td>
                    <bean:write name="lfd" property="dispCreateTime" />
                  </td>
                  <td>
                    <bean:write name="lfd" property="create_station.company.companyCode_ID" />
                    &nbsp;
                    <bean:write name="lfd" property="create_station.stationcode" />
                  </td>
                  <td>
                    <bean:message name="lfd" property="report_status.key" />
                  </td>
                </tr>
              </logic:iterate>
              <input type="hidden" name="search" value="1">
              <tr>
                <td colspan="11">
                  
                  <jsp:include page="/pages/includes/pagination_incl.jsp" />
                  
                </td>
              </tr>
              
            </table>
            <script language=javascript>
              
  document.location.href="#result";

            </script>
          </logic:present>
        </html:form>
