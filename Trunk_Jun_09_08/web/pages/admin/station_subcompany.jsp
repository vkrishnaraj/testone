<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="org.apache.struts.action.DynaActionForm" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Station" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%
  Agent a = (Agent)session.getAttribute("user");
%>
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

function sortAgents(sortOrder) {
	o = document.stationForm;
	o.sort.value = sortOrder;
	o.submit();
}
function updatePagination() {
    return true;
}

  </script>
  <html:form action="subCompAdmin.do" method="post">

    <html:hidden name="subCompanyForm" property="id" />
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.editSubcompany" />
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
            <span class="bold"><bean:message key="AddingToSubcompany" />
              :</span>
            <bean:write name="subCompanyForm" property="subcompanyCode" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#administration/maintain_stations.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <table class="form2" cellspacing="0" cellpadding="0">
            <font color=red>
              <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
            </font>
            <tr>
              <td colspan="10">
                <span class="bold"><bean:message key="companyCodeText" />
                  :</span>
                <bean:write name="subCompanyForm" property="companyCode" />
                |
                <span class="bold"><bean:message key="header.stationCode" />
                  :</span>
                <logic:present name="stationList">
                  <html:select styleClass="dropdown" property="id" onchange="submit()">
                    <html:option value="-1">
                      <bean:message key="select.all" />
                    </html:option>
                    <html:options collection="subCompList" property="id" labelProperty="subcompanyCode" />
                  </html:select>
                </logic:present>
                <logic:present name="subCompantForm" property="id">
                  <html:hidden property="id" />
                </logic:present>
              </td>
            </tr>
            <input type="hidden" name="addNewStation" value="1">
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
            <logic:present name="subCompList" scope="request">
            	 <tr>
            	 	<td></td>
		            <td>
		              <b><a href="#" onclick="sortSubcompanies('username');"><bean:message key="header.stationCode" /></a></b>
		            </td>
		            <td>
		              <b><bean:message key="header.stationDesc" /></b>
		            </td>
		            <td>
		              <b><bean:message key="header.active" /></b>
		            </td>
		          </tr>
           		 <logic:present name="stationList" scope="request">
		            <logic:iterate id="station" name="stationList">
		            	<% Station s =(Station)station; %>
		              <tr>
		              <td width="5%">
	                    <INPUT TYPE=CHECKBOX NAME="station_ID" value="<bean:write name="station" property="station_ID"/>" <% HashMap map = (HashMap)request.getAttribute("subCompanyStationMap");  if (map != null && (map.get(String.valueOf(s.getStation_ID()))!=null)) { %> CHECKED <% } %>>
	                  </td>
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
		              </tr>
		            </logic:iterate>
		            </logic:present>
            
              
              
              <tr>
                <td colspan="10">
                  <jsp:include page="/pages/includes/pagination_incl.jsp" />
                </td>
              </tr>
              
              <tr>
                <td colspan="10">
                  &nbsp;
                </td>
              </tr>
              <tr>
                <td colspan="10" align="center">
                  <html:submit styleId="button" property="addStations">
                    <bean:message key="button.add.subcompany.station" />
                  </html:submit>
                </td>
              </tr>
            </logic:present>
            <tr>
              <td colspan="9" align="center">
                <INPUT Id="button" type="button" value="Back" onClick="history.back()">
                &nbsp;
              </td>
            </tr>
          </table>
        </html:form>
