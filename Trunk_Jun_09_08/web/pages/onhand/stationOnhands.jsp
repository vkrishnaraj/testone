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

<%
	Agent a = (Agent) session.getAttribute("user");
	org.apache.struts.util.PropertyMessageResources myMessages = (org.apache.struts.util.PropertyMessageResources) request
			.getAttribute("org.apache.struts.action.MESSAGE");
	java.util.Locale myLocale = (java.util.Locale) session.getAttribute("org.apache.struts.action.LOCALE");
	
	boolean showPosId = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_COLLECT_POS_ID, a);
	
	int colspan = showPosId ? 14 : 13;
	ResourceBundle bundle = ResourceBundle.getBundle(
			"com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(a.getCurrentlocale()));

%>

<script language="javascript">

function goprev() {
  o = document.deliverForm;
  o.prevpage.value = "1";
  o.pagination.value="1";
  o.sortBy.value="<%=request.getAttribute("sortNum").toString()%>";
  o.submit();
}

function gonext() {
  o = document.deliverForm;
  o.nextpage.value="1";
  o.pagination.value="1";
  o.sortBy.value="<%=request.getAttribute("sortNum").toString()%>";
  o.submit();
}

function gopage(i) {
  o = document.deliverForm;
  o.currpage.value = i;
  o.pagination.value="1";
  o.sortBy.value="<%=request.getAttribute("sortNum").toString()%>";
  o.submit();

}
function updatePagination() {
    return true;
}

function submitForwardForm()
{
  var checked = 0;
  var ohd_id="";
  
  for (var j=0;j<document.deliverForm.length;j++) {
    
    currentElement = document.deliverForm.elements[j];
     if (currentElement.type=="checkbox")
     {
      if (currentElement.checked)
       {
        if (checked > 0) ohd_id += ",";
        checked +=1;
        ohd_id +=currentElement.value;
       }
     }
   }

   if (checked < 1)
   {
     alert("<%= (String)myMessages.getMessage(myLocale, "error.validation.missingForward") %>");
   }
   else
   {
    document.forwardOnHandForm.batch_id.value=ohd_id;
    document.forwardOnHandForm.submit();
  }
}
  </script>
  <jsp:include page="/pages/includes/taskmanager_header.jsp" />
  
  <html:form action="onhands.do" method="post">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.On-hand*Bags" />
          </h1>
        </div>
            <%
              if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_QUERY_REPORTS, a)) {
            %>
            <div id="pageheaderright">
              <select style="font-size:9px;	border:1px solid #569ECD;margin:2px 0px 1px 0px;display:inline;" name="outputtype">
                <% if (!TracerProperties.isTrue(a.getCompanycode_ID(),TracerProperties.SUPPRESSION_PRINTING_NONHTML)) { %>
                  <option value="0" selected="selected"><bean:message key="radio.pdf" /></option>
                <% } %>
                <option value="1"><bean:message key="radio.html" /></option>
              </select>
              <input onclick='document.deliverForm.sortBy.value="<%=request.getAttribute("sortNum").toString()%>";' type="submit" name="generateReport" id="button" value="<bean:message key="button.generateReport" />">&nbsp;&nbsp;
              <logic:present name="reportfile" scope="request">
                <script language="javascript">
                    openReportWindow('reporting?outputtype=<%= request.getAttribute("outputtype") %>&reportfile=<bean:write name="reportfile" scope="request" />','report',800,600);
                </script>
              </logic:present>
            </div>
            <%
              }
            %>                    
      </td>
    </tr>
    
    <tr>
      
      <td id="middlecolumn">
        <div id="maincontent">
          <logic:present name="onhandlist" scope="request">
          	<display:table requestURI="/onhands.do" name="requestScope.onhandlist" sort="external" 
          		size="<%=Integer.valueOf(request.getAttribute("rowcount").toString())%>" pagesize="<%=Integer.valueOf(request.getAttribute("rowsperpage").toString())%>"
          		class="form2" cellspacing="0" cellpadding="0" id="ohd" defaultsort="1" partialList="true" >
          		<% OHD o=(OHD)ohd; %>
          		<display:column titleKey="colname.forwardOhd">
          		    <%  if (o != null && 
                          o.getStatus().getStatus_ID() != TracingConstants.OHD_STATUS_IN_TRANSIT &&
                          o.getStatus().getStatus_ID() != TracingConstants.OHD_STATUS_MATCH_IN_TRANSIT
                        ) { %>
          			<input type="checkbox" name="ohd" value="${ohd.OHD_ID}"/>
          			<% } %>
          			&nbsp;
          		</display:column>
             	<display:column property="OHD_ID" titleKey="colname.on_hand_report_number" href="addOnHandBag.do" paramId="ohd_ID" sortable="true" sortName="<%=request.getAttribute("sortNum").equals(TracingConstants.SortParam.OHD_NUM.getParamString())?TracingConstants.SortParam.OHD_NUMREV.getParamString():TracingConstants.SortParam.OHD_NUM.getParamString() %>"/>
             	<display:column titleKey="colname.incident_num" sortable="true" href="searchIncident.do" paramId="incident" sortName="<%=request.getAttribute("sortNum").equals(TracingConstants.SortParam.OHD_INCIDENT.getParamString())?TracingConstants.SortParam.OHD_INCIDENTREV.getParamString():TracingConstants.SortParam.OHD_INCIDENT.getParamString() %>"><a href='searchIncident.do?incident=<%= OHDUtils.getMBRReportNum((OHD)ohd, "" + a.getStation().getStation_ID()) %>'><%= OHDUtils.getMBRReportNum((OHD)ohd, "" + a.getStation().getStation_ID()) %></a>&nbsp;</display:column>
             	<display:column property="displaydate" titleKey="colname.ohd_create_date" sortable="true" sortName="<%=request.getAttribute("sortNum").equals(TracingConstants.SortParam.OHD_DATE.getParamString())?TracingConstants.SortParam.OHD_DATEREV.getParamString():TracingConstants.SortParam.OHD_DATE.getParamString() %>"/>
             	<display:column titleKey="colname.ohd_modified_date" sortable="true" sortName="<%=request.getAttribute("sortNum").equals(TracingConstants.SortParam.OHD_MODDATE.getParamString())?TracingConstants.SortParam.OHD_MODDATEREV.getParamString():TracingConstants.SortParam.OHD_MODDATE.getParamString() %>"><c:out value="${ohd.dispModifiedDate} ${ohd.dispModifiedTime}"/>&nbsp;</display:column>
                <display:column titleKey="colname.bag_tag_number" sortable="true" sortName="<%=request.getAttribute("sortNum").equals(TracingConstants.SortParam.OHD_BAGTAG.getParamString())?TracingConstants.SortParam.OHD_BAGTAGREV.getParamString():TracingConstants.SortParam.OHD_BAGTAG.getParamString() %>"><c:out value="${ohd.claimnum}"/>&nbsp;</display:column>
                <display:column titleKey="header.status" sortable="true" sortName="<%=request.getAttribute("sortNum").equals(TracingConstants.SortParam.OHD_STATUS.getParamString())?TracingConstants.SortParam.OHD_STATUSREV.getParamString():TracingConstants.SortParam.OHD_STATUS.getParamString() %>">
                	<% String status=o.getStatus().getKey(); %>
                	<c:out value="<%=bundle.getString(status)%>" />&nbsp;
                </display:column>
                <display:column titleKey="colname.color" sortable="true" sortName="<%=request.getAttribute("sortNum").equals(TracingConstants.SortParam.OHD_COLOR.getParamString())?TracingConstants.SortParam.OHD_COLORREV.getParamString():TracingConstants.SortParam.OHD_COLOR.getParamString() %>"><c:out value="${ohd.color}"/>&nbsp;</display:column>
                <display:column  titleKey="colname.bagtype" sortable="true" sortName="<%=request.getAttribute("sortNum").equals(TracingConstants.SortParam.OHD_TYPE.getParamString())?TracingConstants.SortParam.OHD_TYPEREV.getParamString():TracingConstants.SortParam.OHD_TYPE.getParamString() %>"><c:out value="${ohd.type}"/>&nbsp;</display:column>
                <% if (showPosId) { %>
                	<display:column sortable="true" titleKey="colname.posId" sortName="<%=request.getAttribute("sortNum").equals(TracingConstants.SortParam.OHD_POSID.getParamString())?TracingConstants.SortParam.OHD_POSIDREV.getParamString():TracingConstants.SortParam.OHD_POSID.getParamString() %>"><c:out value="${ohd.posId}"/>&nbsp;</display:column>
                <% } %>
                <display:column titleKey="colname.found_destination" sortable="true" sortName="<%=request.getAttribute("sortNum").equals(TracingConstants.SortParam.OHD_DESTINATION.getParamString())?TracingConstants.SortParam.OHD_DESTINATIONREV.getParamString():TracingConstants.SortParam.OHD_DESTINATION.getParamString() %>"><c:out value="${ohd.dispDestination}"/>&nbsp;</display:column>
				<display:column  titleKey="colname.name" sortable="true" sortName="<%=request.getAttribute("sortNum").equals(TracingConstants.SortParam.OHD_NAME.getParamString())?TracingConstants.SortParam.OHD_NAMEREV.getParamString():TracingConstants.SortParam.OHD_NAME.getParamString() %>" >
					<% if(o.getPassenger().getFirstname()!=null && !o.getPassenger().getFirstname().isEmpty()){ %>
						<c:out value="${ohd.passenger.lastname}, ${ohd.passenger.firstname} ${ohd.passenger.middlename}"/>&nbsp;
					<% } else { %>
						<c:out value="${ohd.passenger.lastname}"/>&nbsp;
					<% } %>
               	</display:column> 
               	<display:column titleKey="colname.storage_location" sortable="true" sortName="<%=request.getAttribute("sortNum").equals(TracingConstants.SortParam.OHD_COMMENTS.getParamString())?TracingConstants.SortParam.OHD_COMMENTSREV.getParamString():TracingConstants.SortParam.OHD_COMMENTS.getParamString() %>" style="width:12em;word-wrap:break-word;"><c:out value="${ohd.storage_location}"/>&nbsp;</display:column>
               	<display:column titleKey="header.action">
          		    <%
                        if (o != null && 
	                      o.getStatus().getStatus_ID() == TracingConstants.OHD_STATUS_IN_TRANSIT ||
	                      o.getStatus().getStatus_ID() == TracingConstants.OHD_STATUS_MATCH_IN_TRANSIT
	                    ) {
	                %>
	                  <a href="onhands.do?ohd_ID=<bean:write name="ohd" property="OHD_ID"/>&cancelFwd=1" ><bean:message key="link.cancelForward" /></a>
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
               		<tr>
                        <td colspan="<%=colspan %>">
                          &nbsp;
                        </td>
                      </tr>
                      <tr>
                        <td colspan="<%=colspan %>" align="center">
                          <logic:present name="cbroStationID" scope="session">
          					<%
                            if (session.getAttribute("cbroStationID").equals("" + a.getStation().getStation_ID())) {
          					%>
                              <input type="button" name="batch" value="<bean:message key="button.batchForward" />" Id="button" onClick="submitForwardForm()">
          					<%
                            }
         					 %>
                          </logic:present>
                          <logic:notPresent name="cbroStationID" scope="session">
                            <input type="button" name="batch" value="<bean:message key="button.batchForward" />" Id="button" onClick="submitForwardForm()">
                          </logic:notPresent>
                        </td>
                      </tr>
                 </display:footer>
            </display:table>
          </logic:present>
        </html:form>
        <html:form action="forward_on_hand.do" method="post">
          <input type="hidden" name="batch" value="1">
          <input type="hidden" name="batch_id" value="">
        </html:form>