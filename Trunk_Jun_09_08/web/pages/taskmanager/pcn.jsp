<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested"%>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent"%>

<%
  Agent a = (Agent) session.getAttribute("user");
  org.apache.struts.util.PropertyMessageResources myMessages = (org.apache.struts.util.PropertyMessageResources) request
      .getAttribute("org.apache.struts.action.MESSAGE");
  java.util.Locale myLocale = (java.util.Locale) session.getAttribute("org.apache.struts.action.LOCALE");
%>

<%@page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO"%>
<%@page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%@page import="com.bagnet.nettracer.tracing.db.ProactiveNotification"%>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>

<script language="javascript">

  var cal1xx = new CalendarPopup();
      
  function goprev() {
    o = document.pcnSearchForm;
    o.prevpage.value = "1";
    o.submit();
  }
  
  function gonext() {
    o = document.pcnSearchForm;
    o.nextpage.value="1";
    o.submit();
  }
  
  function gopage(i) {
    o = document.pcnSearchForm;
    o.currpage.value = i;
    o.submit();
  }
  function updatePagination() {
      return true;
  }
  
  function closeNotices()
  {
    if (confirm("<%= (String)myMessages.getMessage(myLocale, "check.acknowledgeclose") %>?"))
    {  
      var checked = 0;
      var received="";
    
      for (var j=0;j<document.pcnSearchForm.length;j++) 
      {
        currentElement = document.pcnSearchForm.elements[j];
        if (currentElement.type=="checkbox")
        {
          if (currentElement.checked)
          {
            if (checked > 0) 
              received += ",";
            checked +=1;
            received +=currentElement.value;
          }
        }
      }

      if (checked < 1)
      {
        alert("<%= (String)myMessages.getMessage(myLocale, "error.validation.noitemsselected") %>");
      }
      else
      {
        document.pcnSearchForm.close.value="1";
        document.pcnSearchForm.select.value=received;
        document.pcnSearchForm.submit();
      }
    }
  } 


  function printNotices()
  {
 
      var checked = 0;
      var received="";
    
      for (var j=0;j<document.pcnSearchForm.length;j++) 
      {
        currentElement = document.pcnSearchForm.elements[j];
        if (currentElement.type=="checkbox")
        {
          if (currentElement.checked)
          {
            if (checked > 0) 
              received += ",";
            checked +=1;
            received +=currentElement.value;
          }
        }
      }

      if (checked < 1)
      {
        alert("<%= (String)myMessages.getMessage(myLocale, "error.validation.noitemsselected") %>");
      }
      else
      {
        document.pcnSearchForm.print.value="1";
        document.pcnSearchForm.select.value=received;
        document.pcnSearchForm.submit();
      }
    
  } 

</script>
<html:form action="pcn.do" method="post">
  
  <input type=hidden name="select" value="" />
  <input type=hidden name="close" value="" />
  <input type=hidden name="print" value="" />
  <%
    String sort = (String) request.getAttribute("sort");

    if (sort != null && sort.length() > 0) {
  %>
  <input type=hidden name=sort value='<%= sort %>'>
  <%
    } else {
  %>
  <input type=hidden name=sort value="">
  <%
    }
  %>
  <jsp:include page="/pages/includes/taskmanager_header.jsp" />
  <tr>

    <td id="middlecolumn">

    <div id="maincontent">
    <h1 class="green"><bean:message key="header.search_pcn_notices" /></h1>
    <br>
    <table class="form2" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20%"><bean:message key="header.incomingFlight" />:</td>
        <td>
        <html:select property="missedFlightAirline" styleClass="dropdown">
          <html:option value="">
            <bean:message key="select.all" />
          </html:option>
          <html:options collection="companylistByName"
            property="companyCode_ID" labelProperty="companydesc" />
        </html:select> 
              <html:text property="missedFlightNumber" size="6" maxlength="4" styleClass="textfield" />
        </td>
      </tr>

      <tr>
        <td width="20%"><bean:message key="header.flightDate" />(<%= a.getDateformat().getFormat() %>) :</td>
        <td>
              <html:text property="missedFlightDate" size="11" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.pcnSearchForm.missedFlightDate,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">
        </td>
      </tr>

      
      <tr>
        <td width="20%"><bean:message key="header.status" /> :</td>
        <td><html:select property="status_ID" styleClass="dropdown">
          <html:option value="81"><bean:message key="STATUS_KEY_81" /></html:option>
          <html:option value="82"><bean:message key="STATUS_KEY_82" /></html:option>
        </html:select></td>
      </tr>
      
      <tr>
        <td colspan="2">&nbsp;</td>
      </tr>
      <tr>
        <td colspan="2" align="center" valign="top">
          <html:submit property="search" styleId="button">
            <bean:message key="button.retrieve" />
          </html:submit>
          &nbsp;
          <html:button styleId="button" property="reset" onclick="document.location.href='pcn.do';">
            <bean:message key="button.reset" />
          </html:button>
        </td>
      </tr>
    </table>
    <br>
    <logic:notEmpty name="resultlist">
      <h1 class="green"><bean:message key="header.view_pcn_notices" /></h1>
      <br>
      <table class="form2" cellspacing="0" cellpadding="0">
        <tr>
          <td><strong><bean:message key="colname.select" /></strong></td>
          <td><strong><bean:message key="colname.name" /></strong></td>
          <td><strong><bean:message key="colname.recordlocator" /></strong></td>
          <td><strong><bean:message key="pcn.bagtags" /></strong></td>
          <td><strong><bean:message key="pcn.missedFlight" /></strong></td>
          <td><strong><bean:message key="pcn.incident" /></strong></td>
        </tr>
        <logic:iterate id="result" name="resultlist" type="com.bagnet.nettracer.tracing.db.ProactiveNotification">
          <tr>
            <td><input type="checkbox" name="id" value="<%=Long.toString(result.getPcn_id()) %>" ></td>
            <td><bean:write name="result" property="name" /></td>
            <td><bean:write name="result" property="locator" /></td>
            <td>
              <logic:iterate id="log" name="result" property="ohd_logs" type="com.bagnet.nettracer.tracing.db.OHD_Log">
                <bean:write name="log" property="ohd.claimnum"/><br />
              </logic:iterate>
              &nbsp;
            </td>
            <td>
              <bean:write name="result" property="missedFlightDate" />&nbsp;
              <bean:write name="result" property="missedFlightAirline" /><bean:write name="result" property="missedFlightNumber" />
            </td>
            <td align="center">
              <logic:empty name="result" property="incident">
                <input type="button" value="<bean:message key="pcn.create"/>" onClick="document.location.href='lostDelay.do?doprepopulate=1&recordlocator=<bean:write name="result" property="locator" />&pcn_id=<%=Long.toString(result.getPcn_id()) %>'" id="button">
              </logic:empty>
              <logic:notEmpty name="result" property="incident">
                <a href="searchIncident.do?incident=<bean:write name="result" property="incident.incident_ID"/>"><bean:write name="result" property="incident.incident_ID"/></a>
              </logic:notEmpty>
            </td>
          </tr>
        </logic:iterate>
        <tr>
          <td colspan="6">
            <jsp:include page="/pages/includes/pagination_incl.jsp" />
          </td>
        </tr>
        <tr>
          <td colspan="6">&nbsp;</td>
        </tr>
        <tr>
          <td colspan="6">
<%
                  if (session.getAttribute("cbroStationID").equals("" + a.getStation().getStation_ID())) {
%>
                    <input type="button" value="<bean:message key="button.closenotices" />" onClick="closeNotices()" id="button">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<%
                  }
%>
                 <% if (PropertyBMO.isTrue(PropertyBMO.PROPERTY_TELEX_PRINTER)) { %>
                   <html:text property="printerAddress" />
                   <input type="button" value="<bean:message key="button.printNotices" />" onClick="printNotices()" id="button">
                 <% } %>
          </td>
        </tr>        
        
      </table>
    </logic:notEmpty>
  </html:form>


