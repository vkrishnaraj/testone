<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib prefix="nt" uri="http://nettracerTags"%> 

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>

<%
  String cssFormClass;
 
  cssFormClass = "form2_dam";

  if (request.getAttribute("lostdelay") != null) {
    cssFormClass = "form2_ld";
  } else {
    if (request.getAttribute("missing") != null) {
      cssFormClass = "form2_pil";
    }
  }
%>
<tr>
  
  <td id="middlecolumn">
    
    <table class="form2" cellspacing="0" cellpadding="0">
      <tr>
        <td>
          <h1 class="green">
            <logic:present name="lostdelay" scope="request">
              <bean:message key="message.lostdelay.update.success" />
              <p>
                <bean:message key="colname.incident_num" />
                :
                <a href='lostDelay.do?incident_ID=<bean:write name="Incident_ID" scope="request"/>'><bean:write name="Incident_ID" scope="request" /></a>
              <p>

              </logic:present>
              <logic:present name="missingarticles" scope="request">
                <bean:message key="message.missingarticles.update.success" />
                <p>
                  <bean:message key="colname.incident_num" />
                  :
                  <a href='missing.do?incident_ID=<bean:write name="Incident_ID" scope="request"/>'><bean:write name="Incident_ID" scope="request" /></a>
                </logic:present>
                <logic:present name="damaged" scope="request">
                  <bean:message key="message.damaged.update.success" />
                  <p>
                    <bean:message key="colname.incident_num" />
                    :
                    <a href='damaged.do?incident_ID=<bean:write name="Incident_ID" scope="request"/>'><bean:write name="Incident_ID" scope="request" /></a>
                  </logic:present>
                </td>
              </tr>
            </table>
            <c:if test="${!empty sessionTaskContainer and !empty nt:instanceOf(sessionTaskContainer,'com.bagnet.nettracer.tracing.db.taskmanager.MorningDutiesTask')}">
              <logic:present name="sessionTaskContainer" scope="session">
              <div id="maincontent">
             <jsp:include page="/pages/includes/general_task_incl.jsp" />
             </div>
             </logic:present>
             </c:if>
