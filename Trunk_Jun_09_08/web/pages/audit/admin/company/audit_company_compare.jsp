<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.OHD_Photo" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.audit.AuditOHDUtils" %>
<%@ page import="java.util.List" %>

<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
  
	var cal1xx = new CalendarPopup();	


</SCRIPT>

<script language="javascript">
  
function goprev() {
  o = document.auditCompanyForm;
  o.prevpage.value = "1";
  o.pagination.value="1";
  o.submit();
}

function gonext() {
  o = document.auditCompanyForm;
  o.nextpage.value="1";
  o.pagination.value="1";
  o.submit();
}

function gopage(i) {
  o = document.auditCompanyForm;
  o.currpage.value = i;
  o.pagination.value="1";
  o.submit();

}
function updatePagination() {
    return true;
}

</script>
<%
  List compareList = (List)request.getAttribute("compareList");
%>
  <html:form action="audit_company.do" method="post">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.audit_company" />
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
              <a href="audit_ohd.do"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="Audit_On_hand" />
                  <br>
                  &nbsp;</span>
                <span class="cc">&nbsp;
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
              <a href="audit_company.do"><span class="aab">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bbb"><bean:message key="Audit_Company" />
                  <br>
                  &nbsp;</span>
                <span class="ccb">&nbsp;
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
          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <strong>
                  <bean:message key="colname.field" />
                </strong>
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <strong>
                    <bean:message key="colname.modifiedOn" />
                    :
                    <bean:write name="audit_lfi" property="displaytime_modified" />
                    (
                    <bean:write name="audit_lfi" property="modifying_agent.username" />
                    )
                  </strong>
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="header.companyDesc" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="companydesc" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.street_addr" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="address1" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.street_addr" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="address2" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.city" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="city" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.state" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="state" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.country" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="country" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.zip" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="zip" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.phone" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="phone" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            
            <tr>
              <td>
                <bean:message key="colname.email" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="email_address" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            
            
            <tr>
              <td>
                <bean:message key="colname.total_threads" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.total_threads" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.seconds_wait" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.seconds_wait" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.min_match_percent" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.min_match_percent" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.mbr_to_lz_days" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.mbr_to_lz_days" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.damaged_to_lz_days" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.damaged_to_lz_days" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.miss_to_lz_days" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.miss_to_lz_days" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            
            <tr>
              <td>
                <bean:message key="colname.ohd_to_lz_days" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.ohd_to_lz_days" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.default_station_code" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.default_station_code" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.default_loss_code" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.default_loss_code" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.email_customer" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <logic:equal name="audit_lfi" property="variable.email_customer" value="true">
                    <bean:message key="select.yes" />
                  </logic:equal>
                  <logic:notEqual name="audit_lfi" property="variable.email_customer" value="false">
                    <bean:message key="select.no" />
                  </logic:notEqual>
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.email_host" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.email_host" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.email_port" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.email_port" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.email_from" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.email_from" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.email_to" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.email_to" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.blind_email" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.blindEmail" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.max_image_file_size" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.max_image_file_size" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.min_interim_approval_check" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.min_interim_approval_check" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.min_interim_approval_voucher" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.min_interim_approval_voucher" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.min_interim_approval_miles" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.min_interim_approval_miles" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.min_interim_approval_cc_refund" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.min_interim_approval_cc_refund" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.min_interim_approval_incidental" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.min_interim_approval_incidental" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.pass_expire_days" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.pass_expire_days" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.account_lockout" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.max_failed_logins" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            
            <tr>
              <td>
                <bean:message key="colname.secure_pw_policy" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <logic:equal name="audit_lfi" property="variable.secure_password" value="1">
                    <bean:message key="select.yes" />
                  </logic:equal>
                  <logic:notEqual name="audit_lfi" property="variable.secure_password" value="1">
                    <bean:message key="select.no" />
                  </logic:notEqual>
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.enable_ohd_auditing" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <logic:equal name="audit_lfi" property="variable.audit_ohd" value="1">
                    <bean:message key="select.yes" />
                  </logic:equal>
                  <logic:notEqual name="audit_lfi" property="variable.audit_ohd" value="1">
                    <bean:message key="select.no" />
                  </logic:notEqual>
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.enable_lost_found_auditing" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <logic:equal name="audit_lfi" property="variable.audit_lost_found" value="1">
                    <bean:message key="select.yes" />
                  </logic:equal>
                  <logic:notEqual name="audit_lfi" property="variable.audit_lost_found" value="1">
                    <bean:message key="select.no" />
                  </logic:notEqual>
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.enable_lost_delayed_auditing" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <logic:equal name="audit_lfi" property="variable.audit_lost_delayed" value="1">
                    <bean:message key="select.yes" />
                  </logic:equal>
                  <logic:notEqual name="audit_lfi" property="variable.audit_lost_delayed" value="1">
                    <bean:message key="select.no" />
                  </logic:notEqual>
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.enable_damaged_auditing" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <logic:equal name="audit_lfi" property="variable.audit_damaged" value="1">
                    <bean:message key="select.yes" />
                  </logic:equal>
                  <logic:notEqual name="audit_lfi" property="variable.audit_damaged" value="1">
                    <bean:message key="select.no" />
                  </logic:notEqual>
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.enable_missing_articles_auditing" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <logic:equal name="audit_lfi" property="variable.audit_missing_articles" value="1">
                    <bean:message key="select.yes" />
                  </logic:equal>
                  <logic:notEqual name="audit_lfi" property="variable.audit_missing_articles" value="1">
                    <bean:message key="select.no" />
                  </logic:notEqual>
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.enable_claims_auditing" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <logic:equal name="audit_lfi" property="variable.audit_claims" value="1">
                    <bean:message key="select.yes" />
                  </logic:equal>
                  <logic:notEqual name="audit_lfi" property="variable.audit_claims" value="1">
                    <bean:message key="select.no" />
                  </logic:notEqual>
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.enable_agent_auditing" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <logic:equal name="audit_lfi" property="variable.audit_agent" value="1">
                    <bean:message key="select.yes" />
                  </logic:equal>
                  <logic:notEqual name="audit_lfi" property="variable.audit_agent" value="1">
                    <bean:message key="select.no" />
                  </logic:notEqual>
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.enable_group_auditing" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <logic:equal name="audit_lfi" property="variable.audit_group" value="1">
                    <bean:message key="select.yes" />
                  </logic:equal>
                  <logic:notEqual name="audit_lfi" property="variable.audit_group" value="1">
                    <bean:message key="select.no" />
                  </logic:notEqual>
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.enable_company_auditing" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <logic:equal name="audit_lfi" property="variable.audit_company" value="1">
                    <bean:message key="select.yes" />
                  </logic:equal>
                  <logic:notEqual name="audit_lfi" property="variable.audit_company" value="1">
                    <bean:message key="select.no" />
                  </logic:notEqual>
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.enable_shift_auditing" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <logic:equal name="audit_lfi" property="variable.audit_shift" value="1">
                    <bean:message key="select.yes" />
                  </logic:equal>
                  <logic:notEqual name="audit_lfi" property="variable.audit_shift" value="1">
                    <bean:message key="select.no" />
                  </logic:notEqual>
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.enable_station_auditing" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <logic:equal name="audit_lfi" property="variable.audit_station" value="1">
                    <bean:message key="select.yes" />
                  </logic:equal>
                  <logic:notEqual name="audit_lfi" property="variable.audit_station" value="1">
                    <bean:message key="select.no" />
                  </logic:notEqual>
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.enable_losscode_auditing" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <logic:equal name="audit_lfi" property="variable.audit_loss_codes" value="1">
                    <bean:message key="select.yes" />
                  </logic:equal>
                  <logic:notEqual name="audit_lfi" property="variable.audit_loss_codes" value="1">
                    <bean:message key="select.no" />
                  </logic:notEqual>
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.enable_airport_auditing" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <logic:equal name="audit_lfi" property="variable.audit_airport" value="1">
                    <bean:message key="select.yes" />
                  </logic:equal>
                  <logic:notEqual name="audit_lfi" property="variable.audit_airport" value="1">
                    <bean:message key="select.no" />
                  </logic:notEqual>
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.enable_delivery_company_auditing" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <logic:equal name="audit_lfi" property="variable.audit_delivery_companies" value="1">
                    <bean:message key="select.yes" />
                  </logic:equal>
                  <logic:notEqual name="audit_lfi" property="variable.audit_delivery_companies" value="1">
                    <bean:message key="select.no" />
                  </logic:notEqual>
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.ws_enabled" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <logic:equal name="audit_lfi" property="variable.ws_enabled" value="1">
                    <bean:message key="select.yes" />
                  </logic:equal>
                  <logic:notEqual name="audit_lfi" property="variable.ws_enabled" value="1">
                    <bean:message key="select.no" />
                  </logic:notEqual>
                  &nbsp;
                </td>

              </logic:iterate>
            </tr>
            
            <tr>
              <td>
                <bean:message key="colname.default.scanner.forward" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.scannerDefaultForward" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            
            <tr>
              <td>
                <bean:message key="colname.default.scanner.back" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.scannerDefaultBack" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            
            <tr>
              <td>
                <bean:message key="colname.auto.close.days.back" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.auto_close_days_back" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            
            <tr>
              <td>
                <bean:message key="colname.auto.close.ld.code" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.auto_close_ld_code" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            
            <tr>
              <td>
                <bean:message key="colname.auto.close.dam.code" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.auto_close_dam_code" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            
            <tr>
              <td>
                <bean:message key="colname.auto.close.pil.code" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.auto_close_pil_code" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            
            <tr>
              <td>
                <bean:message key="colname.auto.close.ld.station" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.auto_close_ld_station" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            
            <tr>
              <td>
                <bean:message key="colname.auto.close.dam.station" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.auto_close_dam_station" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            
            <tr>
              <td>
                <bean:message key="colname.auto.close.pil.station" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.auto_close_pil_station" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>

            <tr>
              <td>
                <bean:message key="colname.incident.lock.mins" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.incident_lock_mins" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            
            <tr>
              <td>
                <bean:message key="colname.pnrlastxdays" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.pnr_last_x_days" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            
            <tr>
              <td>
                <bean:message key="colname.issuance.item.last.x.days" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.issuance_edit_last_x_days" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            
            <tr>
              <td>
                <bean:message key="colname.status_message" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.status_message" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            
            <tr>
              <td>
                <bean:message key="colname.bagdrop.autorefresh.mins.audit" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="variable.bagdrop_autorefresh_mins" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>


          </table>
          <br>
          <center><INPUT type="button" Id="button" value="Back" onClick="history.back()"></center>
        </html:form>
