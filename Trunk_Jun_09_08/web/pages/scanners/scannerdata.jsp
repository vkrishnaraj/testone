<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
  Agent a = (Agent)session.getAttribute("user");
%>


<%@page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%@page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>
<%@page import="com.bagnet.nettracer.tracing.utils.TracerProperties"%>
<html:form action="scannerData.do" method="post">
  <tr>
    <td colspan="3" id="pageheadercell">
      
      
      <%@page import="com.bagnet.nettracer.tracing.db.Agent"%>
      <script src="deployment/main/js/date.js"></script>
      <script src="deployment/main/js/AnchorPosition.js"></script>
      <script src="deployment/main/js/PopupWindow.js"></script>
      <script src="deployment/main/js/popcalendar.js"></script>
      <script src="deployment/main/js/ajax_forall.js"></script>
      <script>
      
      var cal1xx = new CalendarPopup(); 
      

      </script>
      
      
      
      <div id="pageheaderleft">
        <h1>
          <bean:message key="scanner.header" />
        </h1>
      </div>
      <div id="pageheaderright">
        <table id="pageheaderright">
          <tr>
            <jsp:include page="../includes/mail_incl.jsp" />
            <td>
              <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><bean:message key="Help" /></a>
            </td>
          </tr>
        </table>
      </div>
    </td>
  </tr>
  <tr>
    <td id="middlecolumn" >
      <div id="maincontent" align="center">
        <h1 class="green">
          <bean:message key="header.search_criteria" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
        </h1>
        <br>
        <font color=red>
          <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
        </font>
        <table class="form2" cellspacing="0" cellpadding="0">
          <tr>
            <td width="25%">
              <bean:message key="scanner.field.bagTagNumber" />
              <font color=red>*</font>:
            </td>
            <td width="75%">
              <html:text styleClass="textfield" name="scannerDataForm" property="bagTagNumber" size="12" maxlength="10"/>
            </td>
          </tr>
          <tr>
            <td width="25%">
              <bean:message key="scanner.field.startDate" />
              <font color=red>*</font>:
            </td>
            <td width="75%">
              <html:text styleClass="textfield" name="scannerDataForm" property="startDate" size="12" maxlength="10"/>
              <img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.scannerDataForm.startDate,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">
            </td>
          </tr>
          <tr>
            <td width="25%">
              <bean:message key="scanner.field.endDate" />
              <font color=red>*</font>:
            </td>
            <td width="75%">
              <html:text styleClass="textfield" name="scannerDataForm" property="endDate" size="12" maxlength="10"/>
              <img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.scannerDataForm.endDate,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">
            </td>
          </tr>
          <tr>
            <td colspan="2" align="center">
              <html:submit styleId="button" property="search">
                <bean:message key="button.search" />
              </html:submit>
            </td>
          </tr>
        </table>
        
        <logic:present name="resultList" scope="request">
          <div id="pageheaderleft">
            <a name="result"></a>
            <h1 class="green">
              <bean:message key="header.search_result" />
              <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#Retrieve.htm#Retrieve_Reports');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
            </h1>
          </div>
              
          <%
            if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_QUERY_REPORTS, a)) {
          %>
          <div id="pageheaderright">
              <select name="outputtype" class="dropdown">
                <% if (!TracerProperties.isTrue(a.getCompanycode_ID(),TracerProperties.SUPPRESSION_PRINTING_NONHTML)) { %>
                  <option value="0" selected="yes"><bean:message key="radio.pdf" /></option>
                <% } %>
                <option value="1"><bean:message key="radio.html" /></option>
              </select>
              <input type="submit" name="generateReport" id="button" value="<bean:message key="button.generateReport" />">
              <logic:present name="reportfile" scope="request">
                <script>
                  
                    openReportWindow('reporting?outputtype=<%= request.getAttribute("outputtype") %>&reportfile=<bean:write name="reportfile" scope="request" />','report',800,600);

                </script>
              </logic:present>
          </div>
          <%
            }
          %>


<style>
#str1 {
width:expression(120 + "px");
}
#str2 {
width:expression(60 + "px");
}
#str3 {
width:expression(60 + "px");
}
#str4 {
width:expression(200 + "px");
}
  
#str5 {
width:expression(60 + "px");
}
</style>
          <br />
          <logic:empty name="resultList" scope="request">
            <font color="red"><bean:message key="scanner.noresultsfound"/></font>
          </logic:empty>
          <table class="form2" cellspacing="0" cellpadding="0" width="500">
            <tr>
              <td width="60"><b><bean:message key="scanner.tag" /></b></td>
              <td width="120"><b><bean:message key="scanner.string1" /></b></td>
              <td width="60"><b><bean:message key="scanner.string2" /></b></td>
              <td width="60"><b><bean:message key="scanner.string3" /></b></td>
              <td width="200"><b><bean:message key="scanner.string4" /></b></td>
              <td width="60"><b><bean:message key="scanner.ohd.id" /></b></td>
            </tr>
            <c:forEach var="scannerDTO" items="${resultList}">
              <tr>
                <td id="str0">
                  <c:if test='${scannerDTO.tag != null && scannerDTO.tag != ""}'>
                    <c:out value="${scannerDTO.tag}" />
                  </c:if>
                  <c:if test='${scannerDTO.string1 == null && scannerDTO.string1 == ""}'>
                    &nbsp;
                  </c:if>
                </td>
                <td id="str1">
                  <c:if test='${scannerDTO.string1 != null && scannerDTO.string1 != ""}'>
                    <c:out value="${scannerDTO.string1}" />
                  </c:if>
                  <c:if test='${scannerDTO.string1 == null && scannerDTO.string1 == ""}'>
                    &nbsp;
                  </c:if>
                </td>
                <td id="str2">
                  <c:if test='${scannerDTO.string2 != null && scannerDTO.string2 != ""}'>
                    <c:out value="${scannerDTO.string2}" escapeXml="false"/>
                  </c:if>
                  <c:if test='${scannerDTO.string2 == null || scannerDTO.string2 == ""}'>
                    &nbsp;
                  </c:if>
                </td>
                <td id="str3">
                  <c:if test='${scannerDTO.string3 != null && scannerDTO.string3 != ""}'>
                    <c:out value="${scannerDTO.string3}" />
                  </c:if>
                  <c:if test='${scannerDTO.string3 == null || scannerDTO.string3 == ""}'>
                    &nbsp;
                  </c:if>
                </td>
                <td id="str4"> 
                  <c:if test='${scannerDTO.string4 != null && scannerDTO.string4 != ""}'>
                    <c:out value="${scannerDTO.string4}" escapeXml="false"/>
                  </c:if>
                  <c:if test='${scannerDTO.string4 == null || scannerDTO.string4 == ""}'>
                    &nbsp;
                  </c:if>
                </td>
                <td id="str5">
                  <c:if test='${scannerDTO.ohdId != null && scannerDTO.ohdId != ""}'>
                    <a href="addOnHandBag.do?ohd_ID=<c:out value="${scannerDTO.ohdId}" />">
                      <c:out value="${scannerDTO.ohdId}" />
                    </a>
                  </c:if>
                  <c:if test='${scannerDTO.ohdId == null || scannerDTO.ohdId == ""}'>
                    &nbsp;
                  </c:if>
                </td>
              </tr>
            </c:forEach>
          </table>
        </logic:present>
      </div>
    </td>
  </tr>
</html:form>

