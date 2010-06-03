<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.forms.MaintainDeliveryCompanyForm" %>
<script language="javascript">
  
  function goprev() {
    o = document.MaintainDeliveryCompanyForm;
    o.prevpage.value = "1";
    o.pagination.value="1";
    o.submit();
  }

  function gonext() {
    o = document.MaintainDeliveryCompanyForm;
    o.nextpage.value="1";
    o.pagination.value="1";
    o.submit();
  }

  function gopage(i) {
    o = document.MaintainDeliveryCompanyForm;
    o.currpage.value = i;
    o.pagination.value="1";
    o.submit();

  }
  function updatePagination() {
	    return true;
	}

</script>

<html:form action="bdoAdmin.do" method="post">
  <tr>
    <td colspan="3" id="pageheadercell">
      <div id="pageheaderleft">
        <h1>
          <bean:message key="header.deliveryAdmin" />
        </h1>
      </div>
      <div id="pageheaderright">
        <table id="pageheaderright">
          <tr>
            <jsp:include page="/pages/includes/mail_incl.jsp" />
            <td>
              <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><bean:message key="Help" /></a>
            </td>
          </tr>
        </table>
      </div>
    </td>
  </tr>
  
  <input type="hidden" name="delete1" value="" />
  <input type="hidden" name="deleteDeliveryCompanyId" value="" />
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
          <bean:message key="header.search_delivery_companies" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
        </h1>
        <br>
        <strong>
          <bean:message key="wildcard" />
        </strong>
        <table class="form2" cellspacing="0" cellpadding="0">
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <tr>
            <td>
              <bean:message key="header.company" />
              :
            </td>
            <td colspan="3">
              <html:text property="companySearchName" size="12" maxlength="15" styleClass="textfield" />
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
          <bean:message key="deliveryCompanies" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
        </h1>
        <table class="form2" cellspacing="0" cellpadding="0">
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <tr>
            <td width="60%">
              <b><bean:message key="header.companyDesc" /></b>
            </td>
            <td width="30%">
              <b><bean:message key="delivercompany.header.integrationtype.br" /></b>
            </td>

            <td width="10%">
              <b><bean:message key="header.deleteDeliveryCompany" /></b>
            </td>
          </tr>
          <logic:present name="deliveryList" scope="request">
            <logic:iterate id="company" name="deliveryList">
              <tr>
                <td width="60%">
                  <A HREF="bdoAdmin.do?edit=1&delivercompany_ID=<bean:write name="company" property="delivercompany_ID"/>"><bean:write name="company" property="name" /></A>
                </td>
                <td width="30%">
                  <bean:write name="company" property="deliveryIntegrationTypeString" />
                  &nbsp;
                </td>
                <td width="10%">
                  <input type="button" name="a" value="<bean:message key="delete"/>" onclick="deleteDeliveryCompany('<bean:write name="company" property="delivercompany_ID"/>','<bean:write name="company" property="name"/>');" id="button">
                </td>
              </tr>
            </logic:iterate>
            
            <tr>
              <td colspan="3">
                <jsp:include page="/pages/includes/pagination_incl.jsp" />

              </td>
            </tr>
            
          </logic:present>
          <tr>
            <td colspan="3">
              &nbsp;
            </td>
          </tr>
          <tr>
            <td colspan="3" align="center">
              <html:submit styleId="button" property="addNew">
                <bean:message key="button.add.deliverycompany" />
              </html:submit>
            </td>
          </tr>
        </table>
      </html:form>
