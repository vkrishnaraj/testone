<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="org.apache.struts.action.DynaActionForm" %>
<script language="javascript">
  
  function goprev() {
    o = document.shiftForm;
    o.prevpage.value = "1";
    o.pagination.value="1";
    o.submit();
  }

  function gonext() {
    o = document.shiftForm;
    o.nextpage.value="1";
    o.pagination.value="1";
    o.submit();
  }

  function gopage(i) {
    o = document.shiftForm;
    o.currpage.value = i;
    o.pagination.value="1";
    o.submit();

  }
  function updatePagination() {
	    return true;
	}

</script>
<jsp:include page="/pages/includes/validation_incl.jsp" />
<html:form action="shiftAdmin.do" method="post">
  <tr>
    <td colspan="3" id="pageheadercell">
      <div id="pageheaderleft">
        <h1>
          <bean:message key="header.shiftAdmin" />
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
          <bean:message key="header.search_shifts" />
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
              <bean:message key="header.shift_code" />
              :
            </td>
            <td colspan="3">
              <html:text property="shiftSearchName" size="12" maxlength="10" styleClass="textfield" />
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
          <bean:message key="shifts" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#administration/maintain_shifts.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
        </h1>
        <input type="hidden" name="delete1" value="" />
        <input type="hidden" name="shiftId" value="" />
        <table class="form2" cellspacing="0" cellpadding="0">
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <tr>
            <td>
              <b><bean:message key="header.shift_code" /></b>
            </td>
            <td>
              <b><bean:message key="header.shift_description" /></b>
            </td>
            <td>
              <b><bean:message key="header.locale" /></b>
            </td>
            <td>
              <b><bean:message key="header.companyCode" /></b>
            </td>
            <td>
              <b><bean:message key="header.deleteShift" /></b>
            </td>
          </tr>
          <logic:present name="shiftList" scope="request">
            <logic:iterate id="shift" name="shiftList">
              <tr>
                <td>
                  <A HREF="shiftAdmin.do?edit=1&shiftId=<bean:write name="shift" property="shift_id"/>"><bean:write name="shift" property="shift_code" /></A>
                </td>
                <td>
                  <bean:write name="shift" property="shift_description" />
                  &nbsp;
                </td>
                <td>
                  <bean:write name="shift" property="locale" />
                </td>
                <td>
                  <bean:write name="shift" property="company.companyCode_ID" />
                </td>
                <td>
                  <input type="button" name="a" value="<bean:message key="delete"/>" onclick="deleteShift('<bean:write name="shift" property="shift_description"/>',<bean:write name="shift" property="shift_id"/>);" id="button">
                </td>
              </tr>
            </logic:iterate>
            
            <tr>
              <td colspan="5">
                <jsp:include page="/pages/includes/pagination_incl.jsp" />
              </td>
            </tr>
          </logic:present>
          <tr>
            <td colspan="5">
              &nbsp;
            </td>
          </tr>
          <tr>
            <td colspan="5" align="center">
              <INPUT Id="button" type="button" value="Back" onClick="history.back()">
              &nbsp;
              <html:hidden property="companycode_ID" />
              <html:submit styleId="button" property="addNew">
                <bean:message key="button.add.shift" />
              </html:submit>
            </td>
          </tr>
        </table>
      </html:form>
