<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="org.apache.struts.action.DynaActionForm" %>
<script language="javascript">
  
  function goprev() {
    o = document.companyForm;
    o.prevpage.value = "1";
    o.pagination.value="1";
    o.submit();
  }

  function gonext() {
    o = document.companyForm;
    o.nextpage.value="1";
    o.pagination.value="1";
    o.submit();
  }

  function gopage(i) {
    o = document.companyForm;
    o.currpage.value = i;
    o.pagination.value="1";
    o.submit();

  }
  function updatePagination() {
	    return true;
	}

</script>

<html:form action="companyAdmin.do" method="post">
  <tr>
    <td colspan="3" id="pageheadercell">
      <div id="pageheaderleft">
        <h1>
          <bean:message key="header.companyAdmin" />
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
    
    <td id="middlecolumn">
      
      <div id="maincontent">
        <h1 class="green">
          <bean:message key="header.search_companies" />
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
              <bean:message key="header.companyCode" />
              :
            </td>
            <td colspan="3">
              <html:text property="companySearchName" size="12" maxlength="10" styleClass="textfield" />
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
        <h1 class="green">
          <bean:message key="Companies" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
        </h1>
        <input type="hidden" name="delete1" value="" />
        <input type="hidden" name="companyCode" value="" />
        <table class="form2" cellspacing="0" cellpadding="0">
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <tr>
            <td>
              <b><bean:message key="header.companyCode" /></b>
            </td>
            <td>
              <b><bean:message key="header.companyDesc" /></b>
            </td>
            <td>
              <b><bean:message key="header.editStations" /></b>
            </td>
            <td>
              <b><bean:message key="header.editGroups" /></b>
            </td>
            <td>
              <b><bean:message key="header.editShifts" /></b>
            </td>
            <td>
              <b><bean:message key="header.editAirports" /></b>
            </td>
            <td>
              <b><bean:message key="header.editCodes" /></b>
            </td>
            <td>
              <b><bean:message key="header.editUsers" /></b>
            </td>
            <td>
              <b><bean:message key="header.deleteCompany" /></b>
            </td>
          </tr>
          <logic:present name="companyList" scope="request">
            <logic:iterate id="company" name="companyList">
              <tr>
                <td>
                  <A HREF="companyAdmin.do?edit=1&companyCode=<bean:write name="company" property="companyCode_ID"/>"><bean:write name="company" property="companyCode_ID" /></A>
                </td>
                <td>
                  <bean:write name="company" property="companydesc" />
                </td>
                <td>
                  <A HREF="stationAdmin.do?companyCode=<bean:write name="company" property="companyCode_ID"/>"><bean:message key="manage" /></A>
                </td>
                <td>
                  <A HREF="createGroup.do?companyCode=<bean:write name="company" property="companyCode_ID"/>"><bean:message key="manage" /></A>
                </td>
                <td>
                  <A HREF="shiftAdmin.do?companyCode=<bean:write name="company" property="companyCode_ID"/>"><bean:message key="manage" /></A>
                </td>
                <td>
                  <A HREF="airportAdmin.do?companyCode=<bean:write name="company" property="companyCode_ID"/>"><bean:message key="manage" /></A>
                </td>
                <td>
                  <A HREF="codeAdmin.do?companyCode=<bean:write name="company" property="companyCode_ID"/>"><bean:message key="manage" /></A>
                </td>
                <td>
                  <A HREF="agentAdmin.do?companyCode=<bean:write name="company" property="companyCode_ID"/>"><bean:message key="manage" /></A>
                </td>
                <td>
                  <input type="button" name="a" value="<bean:message key="delete"/>" onclick="deleteCompany('<bean:write name="company" property="companyCode_ID"/>');" id="button">
                </td>
              </tr>
            </logic:iterate>
            
            <tr>
              <td colspan="9">
                <jsp:include page="/pages/includes/pagination_incl.jsp" />
              </td>
            </tr>
          </logic:present>
          <tr>
            <td colspan="9">
              &nbsp;
            </td>
          </tr>
          <tr>
            <td colspan="9" align="center">
              <INPUT Id="button" type="button" value="Back" onClick="history.back()">
              &nbsp;
              <html:submit styleId="button" property="addNew">
                <bean:message key="button.add.company" />
              </html:submit>
            </td>
          </tr>
        </table>
      </html:form>
