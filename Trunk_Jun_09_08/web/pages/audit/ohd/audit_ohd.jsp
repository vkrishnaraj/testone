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
  o = document.auditOHDForm;
  o.prevpage.value = "1";
  o.pagination.value="1";
  o.submit();
}

function gonext() {
  o = document.auditOHDForm;
  o.nextpage.value="1";
  o.pagination.value="1";
  o.submit();
}

function gopage(i) {
  o = document.auditOHDForm;
  o.currpage.value = i;
  o.pagination.value="1";
  o.submit();

}
function updatePagination() {
    return true;
}

  </script>
  <html:form action="audit_ohd.do" method="post" onsubmit="fillzero(this.ohd_ID, 13); return true;">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.audit_ohd" />
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
      <td colspan="3" id="navmenucell">
        <div class="menu">
          <dl>
            <dd>
              <a href="audit_ohd.do"><span class="aab">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bbb"><bean:message key="Audit_On_hand" />
                  <br>
                  &nbsp;</span>
                <span class="ccb">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <dd>
              <a href="audit_unchecked.do"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="Audit_Unchecked_Property" />
                  <br>
                  &nbsp;</span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <dd>
              <a href="audit_mbr.do"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="Audit_Mishandled_Report" />
                  <br>
                  &nbsp;</span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <dd>
              <a href="audit_claims.do"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="Audit_Claims" />
                  <br>
                  &nbsp;</span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <dd>
              <a href="audit_agent.do"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="Audit_Agent" />
                  <br>
                  &nbsp;</span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <dd>
              <a href="audit_station.do"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="Audit_Station" />
                  <br>
                  &nbsp;</span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <dd>
              <a href="audit_shift.do"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="Audit_Shift" />
                  <br>
                  &nbsp;</span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <dd>
              <a href="audit_airport.do"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="Audit_Airport" />
                  <br>
                  &nbsp;</span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <dd>
              <a href="audit_group.do"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="Audit_Group" />
                  <br>
                  &nbsp;</span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <dd>
              <a href="audit_company.do"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="Audit_Company" />
                  <br>
                  &nbsp;</span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <dd>
              <a href="audit_losscode.do"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="Audit_Loss_Code" />
                  <br>
                  &nbsp;</span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
          </dl>
        </div>
      </td>
    </tr>
    <tr>
      
      <td id="middlecolumn">
        
        <div id="maincontent">
          <h1 class="green">
            <bean:message key="header.search_criteria" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
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
                  <bean:message key="colname.on_hand_report_number" />
                  <br>
                  <html:text property="ohd_ID" size="14" maxlength="13" styleClass="textfield" onblur="fillzero(this,13);" />
                </td>
                <td colspan=2>
                  <bean:message key="colname.modifying_agentusername" />
                  <br>
                  <html:text property="agent" size="20" maxlength="20" styleClass="textfield" />
                </td>
                <tr>
                  <td colspan=3>
                    <bean:message key="colname.modified_date_range" />
                    (
                    <%= a.getDateformat().getFormat() %>)
                    <br>
                    <html:text property="s_createtime" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.auditOHDForm.s_createtime,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">-
                    <html:text property="e_createtime" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.auditOHDForm.e_createtime,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;"></td>
                </tr>
                <tr>
                  <td colspan="3" align="center" valign="top">
                    <html:submit property="search" styleId="button">
                      <bean:message key="button.retrieve" />
                    </html:submit>
                    &nbsp;
                    <html:reset styleId="button">
                      <bean:message key="button.reset" />
                    </html:reset>
                  </td>
                </tr>
              </table>
              <logic:present name="onhandlist" scope="request">
              
              <h1 class="green">
                <bean:message key="header.search_result" />
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
              </h1>
              <a name="result"></a>
              <table class="form2" cellspacing="0" cellpadding="0">
                <tr>
                  <td>
                    <strong>
                      <bean:message key="colname.on_hand_report_number" />
                    </strong>
                  </td>
                  <td>
                    <strong>
                      <bean:message key="colname.ohd_create_date" />
                    </strong>
                  </td>
                  <td>
                    <strong>
                      <bean:message key="colname.bag_tag_number" />
                    </strong>
                  </td>
                  <td>
                    <strong>
                      <bean:message key="header.status" />
                    </strong>
                  </td>
                  <td>
                    <strong>
                      <bean:message key="colname.color" />
                    </strong>
                  </td>
                  <td>
                    <strong>
                      <bean:message key="colname.bagtype" />
                    </strong>
                  </td>
                  <td>
                    <strong>
                      <bean:message key="header.companyCode" />
                    </strong>
                  </td>
                  <td>
                    <strong>
                      <bean:message key="colname.found_station" />
                    </strong>
                  </td>
                  <td>
                    <strong>
                      <bean:message key="colname.name" />
                    </strong>
                  </td>
                </tr>
                <logic:iterate id="ohd" name="onhandlist">
                  <tr>
                    <td>
                      <A HREF="audit_ohd.do?detail=1&ohd_ID=<bean:write name="ohd" property="OHD_ID"/>"><bean:write name="ohd" property="OHD_ID" /></a>
                    </td>
                    <td>
                      <bean:write name="ohd" property="displaydate" />
                    </td>
                    <td>
                      <bean:write name="ohd" property="claimnum" />
                      &nbsp;
                    </td>
                    <td>
                      <bean:message name="ohd" property="status.key" />
                    </td>
                    <td>
                      <logic:empty name="ohd" property="color">
                        &nbsp;
                      </logic:empty>
                      <bean:write name="ohd" property="color" />
                    </td>
                    <td>
                      <logic:empty name="ohd" property="type">
                        &nbsp;
                      </logic:empty>
                      <bean:write name="ohd" property="type" />
                    </td>
                    <td>
                      <bean:write name="ohd" property="foundAtStation.company.companyCode_ID" />
                    </td>
                    <td>
                      <bean:write name="ohd" property="foundAtStation.stationcode" />
                    </td>
                    <td>
                      <logic:notEmpty name="ohd" property="passenger">
                        <logic:notEmpty name="ohd" property="passenger.lastname">
                          <bean:write name="ohd" property="passenger.lastname" />
                          ,
                          <bean:write name="ohd" property="passenger.firstname" />
                          &nbsp;
                          <bean:write name="ohd" property="passenger.middlename" />
                          &nbsp;
                        </logic:notEmpty>
                        <logic:empty name="ohd" property="passenger.lastname">
                          &nbsp;
                        </logic:empty>
                      </logic:notEmpty>
                      <logic:empty name="ohd" property="passenger">
                        &nbsp;
                      </logic:empty>
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
