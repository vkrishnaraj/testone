<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%
  Agent a = (Agent)session.getAttribute("user");
%>
  
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
    <form action="select_claim.do" method="post" onsubmit="fillzero(this.incident_ID, 13);">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:write name="selectClaimForm" property="incidentId" />
          </h1>
        </div>
        <div id="pageheaderright">
          <table id="pageheaderright">
            <tr>
              <!--jsp:include page="/pages/includes/mail_incl.jsp" /-->
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
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <h1 class="green">
            <bean:message key="header.select.claim" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#Retrieve.htm#Retrieve_Reports');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <table class="form2" cellpadding="0" cellspacing="0" >
          	<tr>
              <td class="header">
                <bean:message key="colname.claim.id" />
              </td>
              <td class="header">
                  <bean:message key="colname.claim.date.created" />
              </td>
              <td class="header">
                  <bean:message key="colname.claimant.name" />
              </td>
            </tr>
            <logic:iterate id="claim" name="selectClaimForm" property="claims" type="aero.nettracer.fs.model.FsClaim">
            	<tr>
            		<td>
            			<logic:equal name="selectClaimForm" property="type" value="<%=String.valueOf(TracingConstants.LINK_TYPE_FRAUD_RESULTS_PAGE) %>" >
            				<a href="fraud_results.do?claimId=<%=claim.getId() %>"><bean:write name="claim" property="id" /></a>
            			</logic:equal>
            			<logic:equal name="selectClaimForm" property="type" value="<%=String.valueOf(TracingConstants.LINK_TYPE_CLAIM_PAGE) %>" >
            				<a href="claim_resolution.do?claimId=<%=claim.getId() %>"><bean:write name="claim" property="id" /></a>
            			</logic:equal>
            		</td>
            		<td>
            			<%= claim.getDisClaimDate(a.getDateformat().getFormat()) %>
            		</td>
            		<td>
            			<%= claim.getDisClaimantName() %>
            		</td>
            	</tr>
            </logic:iterate>
          </table>
          <center>
          	<html:submit property="createNew" styleId="button">
            	<bean:message key="button.create.claim" />
           	</html:submit>
		  </center>
      </form>
