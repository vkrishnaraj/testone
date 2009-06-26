<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.db.OHD" %>
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
        <bean:message key="colname.on_hand_report_number" />
      </strong>
    </td>
    <td>
      <strong>
        <bean:message key="colname.ohd_create_date" />
      </strong>
    </td>
    <td>
      <strong>
        <bean:message key="colname.bag_tag_number" />
      </strong>
    </td>
    <td>
      <strong>
        <bean:message key="header.status" />
      </strong>
    </td>
    <td>
      <strong>
        <bean:message key="colname.color" />
      </strong>
    </td>
    <td>
      <strong>
        <bean:message key="colname.bagtype" />
      </strong>
    </td>
    <td>
      <strong>
        <bean:message key="header.companyCode" />
      </strong>
    </td>
    <td>
      <strong>
        <bean:message key="colname.found_station" />
      </strong>
    </td>
    <td>
      <strong>
        <bean:message key="colname.name" />
      </strong>
    </td>
    <td>
      <strong>
        <bean:message key="colname.requestOhd" />
      </strong>
    </td>
    <td>
      <strong>
        <bean:message key="colname.forwardOhd" />
      </strong>
    </td>
  </tr>
  <logic:iterate name="result" id="entry" type="com.bagnet.nettracer.search.Result">
    <td>
      <bean:write name="entry" property="green" />
      %
    </td>
    <bean:define id="ohd" name="entry" property="dataValue" type="com.bagnet.nettracer.tracing.db.OHD" />
    <td>
      <A HREF="addOnHandBag.do?ohd_ID=<bean:write name="ohd" property="OHD_ID"/>"><bean:write name="ohd" property="OHD_ID" /></a>
    </td>
    <td>
      <bean:write name="ohd" property="displaydate" />
    </td>
    <td>
      <bean:write name="ohd" property="claimnum" />
      &nbsp;
    </td>
    <td>
      <bean:message name="ohd" property="status.key" />
    </td>
    <td>
      <logic:empty name="ohd" property="color">
        &nbsp;
      </logic:empty>
      <bean:write name="ohd" property="color" />
    </td>
    <td>
      <logic:empty name="ohd" property="type">
        &nbsp;
      </logic:empty>
      <bean:write name="ohd" property="type" />
    </td>
    <td>
      <bean:write name="ohd" property="foundAtStation.company.companyCode_ID" />
    </td>
    <td>
      <bean:write name="ohd" property="foundAtStation.stationcode" />
    </td>
    <td>
      <logic:notEmpty name="ohd" property="passenger">
        <logic:notEmpty name="ohd" property="passenger.lastname">
          <bean:write name="ohd" property="passenger.lastname" />
          ,
          <bean:write name="ohd" property="passenger.firstname" />
          &nbsp;
          <bean:write name="ohd" property="passenger.middlename" />
          &nbsp;
        </logic:notEmpty>
        <logic:empty name="ohd" property="passenger.lastname">
          &nbsp;
        </logic:empty>
      </logic:notEmpty>
      <logic:empty name="ohd" property="passenger">
        &nbsp;
      </logic:empty>
    </td>
    <logic:equal name="ohd" property="status.status_ID" value="<%= "" + TracingConstants.OHD_STATUS_OPEN %>">
      <td>
<%
        if (a.getStation().getStation_ID() != ohd.getHoldingStation().getStation_ID()) {
%>
          <A HREF="request_on_hand.do?ohd_ID=<bean:write name="ohd" property="OHD_ID"/>"><bean:message key="request" /></a>
<%
        } else {
%>
          &nbsp;
<%
        }
%>
      </td>
      <td>
<%
        if (a.getStation().getStation_ID() == ohd.getHoldingStation().getStation_ID()) {
%>
          <A HREF="forward_on_hand.do?ohd_ID=<bean:write name="ohd" property="OHD_ID"/>"><bean:message key="forward" /></a>
<%
        } else {
%>
          &nbsp;
<%
        }
%>
      </td>
    </logic:equal>
    <logic:notEqual name="ohd" property="status.status_ID" value="<%= "" + TracingConstants.OHD_STATUS_OPEN %>">
      <td>
        &nbsp;
      </td>
      <td>
        &nbsp;
      </td>
    </logic:notEqual>
  </tr>
</logic:iterate>
<input type="hidden" name="search" value="1">
<tr>
  <td colspan="12">
