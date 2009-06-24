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

<script language="javascript">
    
  function goprev() {
    o = document.searchForwardNoticeForm;
    o.prevpage.value = "1";
    o.submit();
  }
  
  function gonext() {
    o = document.searchForwardNoticeForm;
    o.nextpage.value="1";
    o.submit();
  }
  
  function gopage(i) {
    o = document.searchForwardNoticeForm;
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
    
      for (var j=0;j<document.searchForwardNoticeForm.length;j++) 
      {
        currentElement = document.searchForwardNoticeForm.elements[j];
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
        document.searchForwardNoticeForm.close.value="1";
        document.searchForwardNoticeForm.select.value=received;
        document.searchForwardNoticeForm.submit();
      }
    }
  } 


</script>
<html:form action="forwardNotices.do" method="post">
  
  <input type=hidden name="select" value="">
  <input type=hidden name="close" value="">
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
    <h1 class="green"><bean:message key="header.search_forward_notices" /></h1>
    <br>
    <table class="form2" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20%"><bean:message key="header.status" /> :</td>
        <td><html:select property="status" styleClass="dropdown">
          <html:option value="-1"><bean:message key="select.all" /></html:option>
          <html:option value="0"><bean:message key="STATUS_KEY_12" /></html:option>
          <html:option value="1"><bean:message key="STATUS_KEY_13" /></html:option>
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
          <html:button styleId="button" property="reset" onclick="document.location.href='searchOnHand.do';">
            <bean:message key="button.reset" />
          </html:button>
        </td>
      </tr>
    </table>
    <br>
    <logic:notEmpty name="resultlist">
      <h1 class="green"><bean:message key="header.view_forward_notices" /></h1>
      <br>
      <table class="form2" cellspacing="0" cellpadding="0">
        <tr>
          <td><strong><bean:message key="colname.select" /></strong></td>
          <td><strong><bean:message key="colname.ohd_ID" /></strong></td>
          <td><strong><bean:message key="details" /></strong></td>
          <td><strong><bean:message key="colname.forwarddate" /></strong></td>
          <td><strong><bean:message key="colname.itinerary" /></strong></td>
          <td><strong><bean:message key="header.tsk_status" /></strong></td>
        </tr>
        <logic:iterate id="result" name="resultlist" type="com.bagnet.nettracer.tracing.db.ForwardNotice">
          <tr>
	    <td><input type="checkbox" name="id" value="<%=Long.toString(result.getId()) %>" ></td>
            <td><a href='addOnHandBag.do?ohd_ID=<bean:write name="result" property="forward.ohd.OHD_ID" />'><bean:write name="result" property="forward.ohd.OHD_ID" /></a></td>
            <td><a href="forward_on_hand.do?showForward=1&forward_id=<bean:write name="result" property="forward.OHDLog_ID"/>"><bean:message key="details" /></a></td>
            <td><bean:write name="result" property="forward.dispForwardTime" /></td>
            <td><bean:write name="result" property="dispItinerary" filter="false"/>&nbsp;</td>
            <td><bean:message name="result" property="key" /></td>
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
                    <input type="button" value="<bean:message key="button.closenotices" />" onClick="closeNotices()" id="button">
<%
                  }
%>
          </td>
        </tr>        
      </table>
    </logic:notEmpty>
  </html:form>

