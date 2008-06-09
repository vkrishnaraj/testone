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
        <bean:message key="colname.incident_num" />
      </strong>
    </td>
    <td>
      <strong>
        <bean:message key="colname.report_type" />
      </strong>
    </td>
    <td>
      <strong>
        <bean:message key="colname.incident_create_date" />
        :
      </strong>
    </td>
    <td>
      <strong>
        <bean:message key="header.companyCode" />
        :
      </strong>
    </td>
    <td>
      <strong>
        <bean:message key="colname.stationcreated" />
        :
      </strong>
    </td>
    <td>
      <strong>
        <bean:message key="colname.stationassigned" />
        :
      </strong>
    </td>
    <td>
      <strong>
        <bean:message key="header.status" />
      </strong>
    </td>
    <td>
      <strong>
        <bean:message key="colname.ticket" />
      </strong>
    </td>
    <td>
      <strong>
        <bean:message key="colname.claimnum" />
      </strong>
    </td>
    <td>
      <strong>
        <bean:message key="colname.pass_name" />
      </strong>
    </td>
  </tr>
  <logic:iterate name="result" id="entry" type="com.bagnet.nettracer.search.Result">
    <bean:define id="results" name="entry" property="dataValue" type="com.bagnet.nettracer.tracing.db.Incident" />
    <bean:define id="items" name="results" property="itemlist" />
    <bean:define id="claimchecks" name="results" property="claimcheck_list" />
    <bean:define id="itinerary" name="results" property="itinerary_list" />
    <bean:define id="passengers" name="results" property="passenger_list" />
    <tr>
      <td>
        <bean:write name="entry" property="green" />
        %
      </td>
      <td>
        <a href='searchIncident.do?incident=<bean:write name="results" property="incident_ID"/>'><bean:write name="results" property="incident_ID" /></a>
      </td>
      <td>
        <bean:write name="results" property="itemtype.description" />
      </td>
      <td nowrap>
        <bean:write name="results" property="displaydate" />
      </td>
      <td>
        <bean:write name="results" property="stationcreated.company.companyCode_ID" />
      </td>
      <td>
        <bean:write name="results" property="stationcreated.stationcode" />
      </td>
      <td>
        <bean:write name="results" property="stationassigned.stationcode" />
      </td>
      <td>
        <bean:write name="results" property="status.description" />
      </td>
      <td>
        <logic:empty name="results" property="ticketnumber">
          &nbsp;
        </logic:empty>
        <bean:write name="results" property="ticketnumber" />
      </td>
      <td>
        <logic:iterate id="item_list" name="items" type="com.bagnet.nettracer.tracing.db.Item">
          <logic:notEqual name="item_list" property="claimchecknum" value="">
            <bean:write name="item_list" property="claimchecknum" />
            <br>
          </logic:notEqual>
        </logic:iterate>
        <logic:iterate id="claimcheck_list" name="claimchecks" type="com.bagnet.nettracer.tracing.db.Incident_Claimcheck">
          <logic:notEqual name="claimcheck_list" property="claimchecknum" value="">
            <bean:write name="claimcheck_list" property="claimchecknum" />
            <br>
          </logic:notEqual>
        </logic:iterate>
        &nbsp;
      </td>
      <td>
<%
        boolean hasp = false;
%>
        <logic:iterate id="passenger_list" name="passengers" type="com.bagnet.nettracer.tracing.db.Passenger">
<%
          hasp = false;
%>
          <logic:notEqual name="passenger_list" property="lastname" value="">
            <bean:write name="passenger_list" property="lastname" />
            ,
<%
            hasp = true;
%>
          </logic:notEqual>
          <logic:notEqual name="passenger_list" property="firstname" value="">
<%
            hasp = true;
%>
          </logic:notEqual>
          <bean:write name="passenger_list" property="firstname" />
          <bean:write name="passenger_list" property="middlename" />
<%
          if (hasp) {
%>
            <br>
<%
          }
%>
        </logic:iterate>
        &nbsp;
      </td>
    </tr>
  </logic:iterate>
  <tr>
    <td colspan="11">
