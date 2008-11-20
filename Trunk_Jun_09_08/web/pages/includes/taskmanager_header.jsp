<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<tr>
  <td colspan="3" id="pageheadercell">
    <div id="pageheaderleft">
      <h1>
        <bean:message key="header.taskmanager" />
      </h1>
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
<!-- END PAGE HEADER/SEARCH -->
<!-- ICONS MENU -->
<logic:present name="activityList" scope="session">
  <tr>
    <td colspan="3" id="navmenucell">
      <div class="menu">
        <dl>
<%
          String m = "";

          if (request.getAttribute("maintask") != null) {
            m = "b";
          }
%>
          <dd>
            <a href="logon.do?taskmanager=1"><span class="aa<%= m %>">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bb<%= m %>"><bean:message key="menu.taskmanagerhome" /></span>
              <span class="cc<%= m %>">&nbsp;
                <br />
                &nbsp;</span></a>
          </dd>
          <logic:iterate id="activityDTO" name="activityList" type="com.bagnet.nettracer.tracing.dto.ActivityDTO">
<%
            String b = "";

            if (request.getAttribute("highlite") != null && activityDTO.getActivityinfo().equalsIgnoreCase((String)
                    request.getAttribute("highlite"))) {
              b = "b";
%>
              <dd>
                <a href="<bean:write name="activityDTO" property="activityloc"/>"><span class="aa<%= b %>">&nbsp;
                    <br />
                    &nbsp;</span>
                  <span class="bb<%= b %>"><bean:message key='<%= activityDTO.getActivityinfomenu().replaceAll(" ", "*") %>' /></span>
                  <span class="cc<%= b %>">&nbsp;
                    <br />
                    &nbsp;</span></a>
              </dd>
<%
            }
%>
          </logic:iterate>
        </dl>
      </div>
    </td>
  </tr>
</logic:present>
<!-- END ICONS MENU -->
