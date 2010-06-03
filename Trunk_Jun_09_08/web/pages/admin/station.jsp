<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="org.apache.struts.action.DynaActionForm" %>
<script language="javascript">
  
  function goprev() {
    o = document.stationForm;
    o.prevpage.value = "1";
    o.pagination.value="1";
    o.submit();
  }

  function gonext() {
    o = document.stationForm;
    o.nextpage.value="1";
    o.pagination.value="1";
    o.submit();
  }

  function gopage(i) {
    o = document.stationForm;
    o.currpage.value = i;
    o.pagination.value="1";
    o.submit();

  }
  function updatePagination() {
	    return true;
	}

</script>

<html:form action="stationAdmin.do" method="post">
  <tr>
    <td colspan="3" id="pageheadercell">
      <div id="pageheaderleft">
        <h1>
          <bean:message key="header.stationAdmin" />
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
  
  <input type="hidden" name="delete1" value="" />
  <input type="hidden" name="stationId" value="" />
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
          <bean:message key="header.search_stations" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/view_created_requests.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
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
              <bean:message key="header.stationCode" />
              :
            </td>
            <td colspan="3">
              <html:text property="stationSearchName" size="12" maxlength="10" styleClass="textfield" />
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
          <bean:message key="stations" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#administration/maintain_stations.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
        </h1>
        <table class="form2" cellspacing="0" cellpadding="0">
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <tr>
            <td colspan="5">
              <span class="bold"><bean:message key="header.active" />
                :</span>
                <html:select name="stationForm" property="active" styleClass="dropdown" onchange="submit()">
                  <html:option value="-1">
                    <bean:message key="select.all" />
                  </html:option>
                  <html:option value="true">
                  	<bean:message key="active" />
                  </html:option>
                  <html:option value="false">
                  	<bean:message key="inactive" />
                  </html:option>
                </html:select>
            </td>
          </tr>
          <tr>
            <td>
              <b><bean:message key="header.stationCode" /></b>
            </td>
            <td>
              <b><bean:message key="header.stationDesc" /></b>
            </td>
            <td>
              <b><bean:message key="header.active" /></b>
            </td>
            <td>
              <b><bean:message key="header.deleteStation" /></b>
            </td>
          </tr>
          <logic:present name="stationList" scope="request">
            <logic:iterate id="station" name="stationList">
              <tr>
                <td>
                  <A HREF="stationAdmin.do?edit=1&stationId=<bean:write name="station" property="station_ID"/>"><bean:write name="station" property="stationcode" /></A>
                </td>
                <td>
                  <bean:write name="station" property="stationdesc" />
                </td>
                <td>
                  <logic:equal name="station" property="active" value="true">
                    <bean:message key="select.yes" />
                  </logic:equal>
                  <logic:notEqual name="station" property="active" value="true">
                    <bean:message key="select.no" />
                  </logic:notEqual>
                </td>
                <td>
                  <input type="button" name="a" value="<bean:message key="delete"/>" onclick="deleteStation('<bean:write name="station" property="stationcode"/>',<bean:write name="station" property="station_ID"/>);" id="button">
                </td>
              </tr>
            </logic:iterate>
            
            <tr>
              <td colspan="4">
                <jsp:include page="/pages/includes/pagination_incl.jsp" />
              </td>
            </tr>
            
          </logic:present>
          <tr>
            <td colspan="7">
              &nbsp;
            </td>
          </tr>
          <tr>
            <td colspan="4" align="center">
              <html:hidden property="companyCode" />
              <INPUT Id="button" type="button" value="Back" onClick="history.back()">
              &nbsp;
              <html:submit styleId="button" property="addNew">
                <bean:message key="button.add.station" />
              </html:submit>
            </td>
          </tr>
        </table>
      </html:form>
