<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%
  Agent a = (Agent)session.getAttribute("user");
%>
  <tr>
    <td>
      <strong>
        <bean:message key="colname.score" />
      </strong>
    </td>
    <td>
      <strong>
        <bean:message key="colname.file_ref_number" />
      </strong>
    </td>
    <td>
      <strong>
        <bean:message key="colname.report_type" />
      </strong>
    </td>
    <td>
      <strong>
        <bean:message key="colname.create_date_time" />
      </strong>
    </td>
    <td>
      <strong>
        <bean:message key="colname.filing_station" />
      </strong>
    </td>
    <td>
      <strong>
        <bean:message key="colname.status" />
      </strong>
    </td>
  </tr>
  <logic:iterate name="result" id="entry" type="com.bagnet.nettracer.search.Result">
    <bean:define id="lfd" name="entry" property="dataValue" type="com.bagnet.nettracer.tracing.db.LostAndFoundIncident" />
    <tr>
      <td>
        <bean:write name="entry" property="green" />
        %
      </td>
      <logic:equal name="lfd" property="report_type" value="1">
        <td>
          <A HREF="addLost.do?file_ref_number=<bean:write name="lfd" property="file_ref_number"/>"><bean:write name="lfd" property="file_ref_number" /></a>
        </td>
        <td>
          <bean:message key="lost" />
        </td>
      </logic:equal>
      <logic:equal name="lfd" property="report_type" value="0">
        <td>
          <A HREF="addFound.do?file_ref_number=<bean:write name="lfd" property="file_ref_number"/>"><bean:write name="lfd" property="file_ref_number" /></a>
        </td>
        <td>
          <bean:message key="found" />
        </td>
      </logic:equal>
      <td>
        <bean:write name="lfd" property="dispCreateTime" />
      </td>
      <td>
        <bean:write name="lfd" property="create_station.company.companyCode_ID" />
        &nbsp;
        <bean:write name="lfd" property="create_station.stationcode" />
      </td>
      <td>
        <bean:write name="lfd" property="report_status.description" />
      </td>
    </tr>
  </logic:iterate>
  <input type="hidden" name="search" value="1">
  <tr>
    <td colspan="12">
