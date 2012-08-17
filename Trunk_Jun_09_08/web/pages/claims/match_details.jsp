<%@ page language="java" %>
<%@ page import="com.bagnet.nettracer.tracing.db.ExpensePayout" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="aero.nettracer.fs.model.FsClaim" %>
<%@ page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO" %>
<%
  Agent a = (Agent)session.getAttribute("user");
  String company = a.getCompanycode_ID();

  boolean ntUser = PropertyBMO.isTrue("nt.user");

%>
  
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.match_details" />
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
                <h1>
                	<bean:message key="header.match_details" />
                </h1>
                <logic:notEmpty name="status" scope="request" >
                	<table class="form2" cellpadding="0" cellspacing="0" >
	                <logic:equal name="status" scope="request" value="<%=String.valueOf(TracingConstants.STATUS_SUSPECTED_FRAUD) %>" >
                		<tr class="suspected_fraud">
                			<td>
                				<center><b><bean:message key="match.status.message" /> <bean:message key="suspected.fraud"/></b></center>
                			</td>
	                	</tr>
	                </logic:equal>
	                <logic:equal name="status" scope="request" value="<%=String.valueOf(TracingConstants.STATUS_KNOWN_FRAUD) %>" >
                		<tr class="known_fraud">
                			<td>
                				<center><b><bean:message key="match.status.message" /> <bean:message key="known.fraud" /></b></center>
                			</td>
	                	</tr>
	                </logic:equal>
	                </table>
                </logic:notEmpty>
                
            	<!-- <table class="form2" cellspacing="0" cellpadding="0" >
            		<tr>
            			<td class="header">
            				<bean:message key="claim.match.detail.name" />
            			</td>
            			<td class="header">
            				<bean:message key="claim.match.detail.contact" />
            			</td>
            		</tr>
            		<tr>
            			
            				<td>write scope="request" name="requestAgent" /></td>
            				<td>write scope="request" name="contact" /></td>
           			</tr>
            	</table>-->
                
            	<table class="form2" cellspacing="0" cellpadding="0" > 
            		<tr>
            			<td class="header">
            				<bean:message key="claim.match.detail.item" />
            			</td>
            			<td class="header">
            				<bean:message key="claim.match.detail.original" />
            			</td>
            			<td class="header">
            				<bean:message key="claim.match.detail.match" />
            			</td>
            		</tr>
            		<logic:iterate id="detail" name="match" property="details" scope="request" type="aero.nettracer.fs.model.detection.MatchDetail" >
            			<tr>
            				<td><bean:write name="detail" property="description" /></td>
            				<td><bean:write name="detail" property="content1" /></td>
            				<td><bean:write name="detail" property="content2" /></td>
            			</tr>
            		</logic:iterate>
            	</table>
            	<br/>
            	<br/>
            	<logic:notEmpty scope="request" name="matchClaims">
            		<jsp:include page="/pages/claims/claim_ro.jsp" />
                </logic:notEmpty>
            	<br />
            	<center>
            		<input id="button" type="button" value='<bean:message key="button.back" />' onClick="history.back()">
                </center>        	
                <br />
