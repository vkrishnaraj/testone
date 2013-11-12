<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.db.OHD" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.OHDUtils" %>
<%@page import="java.util.ResourceBundle" %>
<%@page import="java.util.Locale" %>
<%@page import="org.displaytag.tags.TableTagParameters"%>
<%@page import="org.displaytag.util.ParamEncoder"%>
<%@page import="com.bagnet.nettracer.tracing.utils.TracerProperties"%>
<%@page import="org.apache.commons.lang.math.NumberUtils"%>
<%@page import="org.apache.commons.lang.StringUtils"%>

<%
	Agent a = (Agent) session.getAttribute("user");
	org.apache.struts.util.PropertyMessageResources myMessages = (org.apache.struts.util.PropertyMessageResources) request
			.getAttribute("org.apache.struts.action.MESSAGE");
	java.util.Locale myLocale = (java.util.Locale) session.getAttribute("org.apache.struts.action.LOCALE");
	
	boolean showPosId = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_COLLECT_POS_ID, a);
	
	int colspan = showPosId ? 13 : 12;
	ResourceBundle bundle = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", 
			new Locale(a.getCurrentlocale()));

	String sortNum=StringUtils.stripToEmpty((String)request.getAttribute("sortNum"));	
%>

<script>

function goprev() {
  o = document.deliverForm;
  o.prevpage.value = "1";
  o.pagination.value="1";
  o.sortBy.value="<%=sortNum%>"
  o.submit();
}

function gonext() {
  o = document.deliverForm;
  o.nextpage.value="1";
  o.pagination.value="1";
  o.sortBy.value="<%=sortNum%>"
  o.submit();
}

function gopage(i) {
  o = document.deliverForm;
  o.currpage.value = i;
  o.pagination.value="1";
  o.sortBy.value="<%=sortNum%>"
  o.submit();

}
function updatePagination() {
    return true;
}
  </script>
  <jsp:include page="/pages/includes/taskmanager_header.jsp" />
  
  <html:form action="toBeInventoried.do" method="post">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
				<bean:message key="header.ToBeInventoried" />
          </h1>
        </div>
        <div id="pageheaderright">
        	&nbsp;
        </div>                  
      </td>
    </tr>
    
    <tr>      
      <td id="middlecolumn">
        <div id="maincontent">
          <logic:present name="onhandlist" scope="request">
          	<display:table requestURI="/toBeInventoried.do" name="requestScope.onhandlist" sort="external" 
          		size="<%=NumberUtils.toInt((String)request.getAttribute("rowcount"))%>" pagesize="<%=NumberUtils.toInt((String)request.getAttribute("rowsperpage"))%>"
          		class="form2" cellspacing="0" cellpadding="0" id="ohd" defaultsort="1" partialList="true" >
          		<% OHD o=(OHD)ohd; %>
             	<display:column property="OHD_ID" titleKey="colname.on_hand_report_number" href="addOnHandBag.do" paramId="ohd_ID" sortable="true" sortName="<%=sortNum.equals(TracingConstants.SortParam.OHD_NUM.getParamString())?TracingConstants.SortParam.OHD_NUMREV.getParamString():TracingConstants.SortParam.OHD_NUM.getParamString() %>"/>
             	<display:column titleKey="colname.incident_num" sortable="true" href="searchIncident.do" paramId="incident" sortName="<%=sortNum.equals(TracingConstants.SortParam.OHD_INCIDENT.getParamString())?TracingConstants.SortParam.OHD_INCIDENTREV.getParamString():TracingConstants.SortParam.OHD_INCIDENT.getParamString() %>"><a href='searchIncident.do?incident=<%= OHDUtils.getMBRReportNum((OHD)ohd, "" + a.getStation().getStation_ID()) %>'><%= OHDUtils.getMBRReportNum((OHD)ohd, "" + a.getStation().getStation_ID()) %></a>&nbsp;</display:column>
             	<display:column property="displaydate" titleKey="colname.ohd_create_date" sortable="true" sortName="<%=sortNum.equals(TracingConstants.SortParam.OHD_DATE.getParamString())?TracingConstants.SortParam.OHD_DATEREV.getParamString():TracingConstants.SortParam.OHD_DATE.getParamString() %>"/>
             	<display:column titleKey="colname.ohd_modified_date" sortable="true" sortName="<%=sortNum.equals(TracingConstants.SortParam.OHD_MODDATE.getParamString())?TracingConstants.SortParam.OHD_MODDATEREV.getParamString():TracingConstants.SortParam.OHD_MODDATE.getParamString() %>"><c:out value="${ohd.dispModifiedDate} ${ohd.dispModifiedTime}"/>&nbsp;</display:column>
                <display:column titleKey="colname.bag_tag_number" sortable="true" sortName="<%=sortNum.equals(TracingConstants.SortParam.OHD_BAGTAG.getParamString())?TracingConstants.SortParam.OHD_BAGTAGREV.getParamString():TracingConstants.SortParam.OHD_BAGTAG.getParamString() %>"><c:out value="${ohd.claimnum}"/>&nbsp;</display:column>
                <display:column titleKey="header.status" sortable="true" sortName="<%=sortNum.equals(TracingConstants.SortParam.OHD_STATUS.getParamString())?TracingConstants.SortParam.OHD_STATUSREV.getParamString():TracingConstants.SortParam.OHD_STATUS.getParamString() %>">
                	<% String status=o.getStatus().getKey(); %>
                	<c:out value="<%=bundle.getString(status)%>" />&nbsp;
                </display:column>
                <display:column titleKey="colname.color" sortable="true" sortName="<%=sortNum.equals(TracingConstants.SortParam.OHD_COLOR.getParamString())?TracingConstants.SortParam.OHD_COLORREV.getParamString():TracingConstants.SortParam.OHD_COLOR.getParamString() %>"><c:out value="${ohd.color}"/>&nbsp;</display:column>
                <display:column  titleKey="colname.bagtype" sortable="true" sortName="<%=sortNum.equals(TracingConstants.SortParam.OHD_TYPE.getParamString())?TracingConstants.SortParam.OHD_TYPEREV.getParamString():TracingConstants.SortParam.OHD_TYPE.getParamString() %>"><c:out value="${ohd.type}"/>&nbsp;</display:column>
                <% if (showPosId) { %>
                	<display:column sortable="true" titleKey="colname.posId" sortName="<%=sortNum.equals(TracingConstants.SortParam.OHD_POSID.getParamString())?TracingConstants.SortParam.OHD_POSIDREV.getParamString():TracingConstants.SortParam.OHD_POSID.getParamString() %>"><c:out value="${ohd.posId}"/>&nbsp;</display:column>
                <% } %>
                <display:column titleKey="colname.found_destination" sortable="true" sortName="<%=sortNum.equals(TracingConstants.SortParam.OHD_DESTINATION.getParamString())?TracingConstants.SortParam.OHD_DESTINATIONREV.getParamString():TracingConstants.SortParam.OHD_DESTINATION.getParamString() %>"><c:out value="${ohd.dispDestination}"/>&nbsp;</display:column>
				<display:column  titleKey="colname.name" sortable="true" sortName="<%=sortNum.equals(TracingConstants.SortParam.OHD_NAME.getParamString())?TracingConstants.SortParam.OHD_NAMEREV.getParamString():TracingConstants.SortParam.OHD_NAME.getParamString() %>" >
					<% if(o.getPassenger().getFirstname()!=null && !o.getPassenger().getFirstname().isEmpty()){ %>
						<c:out value="${ohd.passenger.lastname}, ${ohd.passenger.firstname} ${ohd.passenger.middlename}"/>&nbsp;
					<% } else { %>
						<c:out value="${ohd.passenger.lastname}"/>&nbsp;
					<% } %>
               	</display:column> 
               	<display:column titleKey="colname.storage_location" sortable="true" sortName="<%=sortNum.equals(TracingConstants.SortParam.OHD_COMMENTS.getParamString())?TracingConstants.SortParam.OHD_COMMENTSREV.getParamString():TracingConstants.SortParam.OHD_COMMENTS.getParamString() %>" style="width:12em;word-wrap:break-word;"><c:out value="${ohd.storage_location}"/>&nbsp;</display:column>
               	<display:column titleKey="header.action">
          		    <%
                        if (o != null && 
	                      o.getStatus().getStatus_ID() == TracingConstants.OHD_STATUS_IN_TRANSIT ||
	                      o.getStatus().getStatus_ID() == TracingConstants.OHD_STATUS_MATCH_IN_TRANSIT
	                    ) {
	                %>
	                  <a href="toBeInventoried.do?ohd_ID=<bean:write name="ohd" property="OHD_ID"/>&cancelFwd=1" ><bean:message key="link.cancelForward" /></a>
	                <% } else {%>
	                  &nbsp;
	                <% } %> 
          		</display:column>
               	<display:footer>
	               	<tr>
		                <td colspan="<%=colspan %>">
		                 	<jsp:include page="/pages/includes/pagination_incl.jsp" />
		                </td>
	              	</tr>
                 </display:footer>
            </display:table>
          </logic:present>
         </div>
	 </td>
	</tr>
</html:form>