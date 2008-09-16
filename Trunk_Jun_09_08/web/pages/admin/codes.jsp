<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="org.apache.struts.action.DynaActionForm" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.AdminUtils" %>
<script language="javascript">
  <!--
  function goprev() {
    o = document.codeForm;
    o.prevpage.value = "1";
    o.pagination.value="1";
    o.submit();
  }

  function gonext() {
    o = document.codeForm;
    o.nextpage.value="1";
    o.pagination.value="1";
    o.submit();
  }

  function gopage(i) {
    o = document.codeForm;
    o.currpage.value = i;
    o.pagination.value="1";
    o.submit();
  }
  function updatePagination() {
	    return true;
	}
// -->
</script>
<jsp:include page="/pages/includes/validation_incl.jsp" />
<html:form action="codeAdmin.do" method="post">
  <tr>
    <td colspan="3" id="pageheadercell">
      <div id="pageheaderleft">
        <h1>
          <bean:message key="header.codeAdmin" />
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
  <!-- END PAGE HEADER/SEARCH -->
<%
  String sort = (String)request.getAttribute("sort");

  if (sort != null && sort.length() > 0) {
%>
    <input type=hidden name=sort value='<%= sort %>'>
<%
  } else {
%>
    <input type=hidden name=sort value="">
<%
  }
%>
  <tr>
    <!-- MIDDLE COLUMN -->
    <td id="middlecolumn">
      <!-- MAIN BODY -->
      <div id="maincontent">
        <h1 class="green">
          <bean:message key="header.search_codes" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/view_created_requests.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
        </h1>
        <span class="reqfield">*</span>
        <bean:message key="Required" />
        <table class="form2" cellspacing="0" cellpadding="0">
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <tr>
            <td>
              <bean:message key="header.code_number" />
              :
            </td>
            <td>
              <html:text property="codeSearchName" size="3" maxlength="2" styleClass="textfield" />
            </td>
            <td>
              <bean:message key="header.report_type" />
              :
            </td>
            <td>
              <html:select name="codeForm" property="report_type" styleClass="dropdown">
                <html:option value="-1">
                  <bean:message key="select.all" />
                </html:option>
                <html:options collection="reportTypes" property="itemType_ID" labelProperty="description" />
              </html:select>
            </td>
          </tr>
          <tr>
            <td colspan="4" align="center">
              <br>
              <html:submit styleId="button" property="search">
                <bean:message key="button.search" />
              </html:submit>
              &nbsp;
              <html:reset styleId="button">
                <bean:message key="button.reset" />
              </html:reset>
            </td>
          </tr>
        </table>
        <br>
        <input type="hidden" name="delete1" value="" />
        <input type="hidden" name="loss_code" value="" />
        <h1 class="green">
          <bean:message key="header.editCodes" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#administration/maintain_loss_codes.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
        </h1>
        <table class="form2" cellspacing="0" cellpadding="0">
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <tr>
            <td>
              <b><bean:message key="header.deleteCode" /></b>
            </td>
            <td>
              <b><bean:message key="header.code_number" /></b>
            </td>
            <td>
              <b><bean:message key="header.report_type" /></b>
            </td>
            <td>
              <b><bean:message key="header.code_description" /></b>
            </td>
            <td>
              <b><bean:message key="header.locale" /></b>
            </td>
            <td>
              <b><bean:message key="header.companyCode" /></b>
            </td>
          </tr>
          <logic:present name="codeList" scope="request">
            <logic:iterate id="code" name="codeList" type="com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code">
              <tr>
                <td>
                  <input type="checkbox" name="dCode" value="<bean:write name="code" property="code_id"/>">
                </td>
                <td>
                  <A HREF="codeAdmin.do?edit=1&code_id=<bean:write name="code" property="code_id"/>"><bean:write name="code" property="loss_code" /></A>
                </td>
<%
                String report_desc = AdminUtils.getReportDescription(code.getReport_type(), code.getLocale());
%>
                <td>
                  &nbsp;
                  <%= report_desc %>
                </td>
                <td>
                  <bean:write name="code" property="description" />
                </td>
                <td>
                  <bean:write name="code" property="locale" />
                </td>
                <td>
                  <bean:write name="code" property="company.companyCode_ID" />
                </td>
              </tr>
            </logic:iterate>
            <!-- pagination -->
            <tr>
              <td colspan="6">
                <jsp:include page="/pages/includes/pagination_incl.jsp" />
              </td>
            </tr>
          </logic:present>
          <tr>
            <td colspan="6">
              &nbsp;
            </td>
          </tr>
          <tr>
            <td align="center" colspan="6">
              <INPUT Id="button" type="button" value="Back" onClick="history.back()">
              &nbsp;
              <html:hidden property="companycode_ID" />
              &nbsp;
              <input type="button" name="batch" value="Batch Delete" Id="button" onClick="deleteCode()">
              &nbsp;
              <html:submit styleId="button" property="addNew">
                <bean:message key="button.add.code" />
              </html:submit>
            </td>
          </tr>
        </table>
      </html:form>
