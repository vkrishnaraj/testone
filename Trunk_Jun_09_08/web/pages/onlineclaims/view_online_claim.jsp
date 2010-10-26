<%@page import="com.bagnet.nettracer.tracing.db.onlineclaims.OnlineClaim"%>
<%@page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>

<%
  Agent a = (Agent)session.getAttribute("user");
  OnlineClaim c = (OnlineClaim)request.getAttribute("claim");
%>

<tr><td>
<br/>
<div id="maincontent">

  <jsp:include page="/pages/onlineclaims/online_claim_data.jsp" />
  
</div>
<br/>