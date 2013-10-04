<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.forms.BDOForm" %>
<%@ page import="com.bagnet.nettracer.tracing.db.OHD_Photo" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>

<%@page import="com.bagnet.nettracer.reporting.ReportingConstants"%>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/ajax_forall.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
  
	var cal1xx = new CalendarPopup();	
  
    function changebutton() {
      document.BDOForm.saveButton.disabled = true;
      document.BDOForm.saveButton.value = "<bean:message key="ajax.please_wait" />";
      document.BDOForm.save.disabled = false;
    }
    
    function undoChangebutton() {
      document.BDOForm.saveButton.disabled = false;
      document.BDOForm.saveButton.value = "<bean:message key="button.bdo_send" />";
      document.BDOForm.save.disabled = true;
    }


</SCRIPT>

<%
  Agent a = (Agent)session.getAttribute("user");
	BDOForm myform = (BDOForm) session.getAttribute("BDOForm");
%>
  
  <html:form styleId="dirtyCheck-form" action="bdo.do" method="post" onsubmit="return validateReqBDO(this);">
    <logic:notPresent name="onhand" scope="request">
      <logic:notPresent name="mbr" scope="request">
        <logic:notEqual name="BDOForm" property="OHD_ID" value="">
          <input type=hidden name="ohd_id" value="<bean:write name="BDOForm" property="OHD_ID"/>">
        </logic:notEqual>
        <logic:notEqual name="BDOForm" property="incident_ID" value="">
          <input type=hidden name="mbr_id" value="<bean:write name="BDOForm" property="incident_ID"/>">
        </logic:notEqual>
        <logic:notEqual name="BDOForm" property="BDO_ID" value="0">
          <input type=hidden name="bdo_id" value="<bean:write name="BDOForm" property="BDO_ID"/>">
        </logic:notEqual>
      </logic:notPresent>
    </logic:notPresent>
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <logic:notPresent name="show_word_for" scope="request">
              <bean:message key="header.bdo" />
            </logic:notPresent>
            <logic:present name="show_word_for" scope="request">
              <bean:message key="header.bdo_for" />
              (
              <logic:notEqual name="BDOForm" property="incident_ID" value="">
              	<% if((request.getAttribute("mbrtype")!=null && request.getAttribute("mbrtype").equals(TracingConstants.LOST_DELAY+"")) ||
            	(myform.getIncident()!=null && myform.getIncident().getItemtype()!=null && myform.getIncident().getItemtype_ID()==TracingConstants.LOST_DELAY)) {%>
                	<bean:message key="header.bdo_report_LD" />
                <% } %>
                <% if((request.getAttribute("mbrtype")!=null && request.getAttribute("mbrtype").equals(TracingConstants.MISSING_ARTICLES+"")) ||
            	(myform.getIncident()!=null && myform.getIncident().getItemtype()!=null && myform.getIncident().getItemtype_ID()==TracingConstants.MISSING_ARTICLES)) {%>
                	<bean:message key="header.bdo_report_MS" />
                <% } %>
                <% if((request.getAttribute("mbrtype")!=null && request.getAttribute("mbrtype").equals(TracingConstants.DAMAGED_BAG+"")) ||
            	(myform.getIncident()!=null && myform.getIncident().getItemtype()!=null && myform.getIncident().getItemtype_ID()==TracingConstants.DAMAGED_BAG)) {%>
                	<bean:message key="header.bdo_report_DM" />
                <% } %>
                <bean:write name="BDOForm" property="incident_ID" />
              </logic:notEqual>
              <logic:notEqual name="BDOForm" property="OHD_ID" value="">
                <logic:notEqual name="BDOForm" property="incident_ID" value="">
                  &nbsp;&&nbsp;
                </logic:notEqual>
                <bean:message key="header.bdo_bag" />
                <bean:write name="BDOForm" property="OHD_ID" />
              </logic:notEqual>
              )
            </logic:present>
          </h1>
        </div>
        <div id="pageheaderright">
          <table id="pageheaderright">
            <tr>
              <logic:notPresent name="showbdolist" scope="request">
                <logic:present name="showprint" scope="request">
                  <td>
                    <a href="#" onclick="openReportWindow('bdo.do?receipt=1&toprint=<%=ReportingConstants.BDO_RECEIPT_RPT%>&bdo_id=<bean:write name="BDOForm" property="BDO_ID" />','LostReceipt',800,600);return false;"><img src="deployment/main/images/nettracer/icon_printrpt.gif" width="12" height="12"></a>
                  </td>
                  <td>
                    <a href="#" onclick="openReportWindow('bdo.do?receipt=1&toprint=<%=ReportingConstants.BDO_RECEIPT_RPT%>&bdo_id=<bean:write name="BDOForm" property="BDO_ID" />','LostReceipt',800,600);return false;"><bean:message key="print_bdo" /></a>
                    &nbsp;
                  </td>
                  
                </logic:present>
              </logic:notPresent>
              <jsp:include page="/pages/includes/mail_incl.jsp" />
              <td>
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><bean:message key="Help" /></a>
              </td>
            </tr>
          </table>
        </div>
      </td>
    </tr>
    <logic:present name="mbr" scope="request">
    
    <tr>
      <td id="middlecolumn">
        <div id="maincontent">
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <br>
          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td align=center>
                <bean:message key="colname.report_num" />
                <br>
                <html:text property="mbr_id" size="13" maxlength="13" styleClass="textfield" value="" onblur="fillzero(this,13);" />
              </td>
              <tr>
                <td align="center" valign="top" colspan="12">
                  <html:submit property="get_incident" styleId="button" onclick="fillzero(document.BDOForm.mbr_id, 13); return true;">
                    <bean:message key="button.search" />
                  </html:submit>
                </td>
              </tr>
            </table>
            
          </logic:present>
          <logic:present name="onhand" scope="request">
          
          <tr>
            <td id="middlecolumn">
              <div id="maincontent">
                <font color=red>
                  <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
                </font>
                <br>
                <table class="form2" cellspacing="0" cellpadding="0">
                  <tr>
                    <td align=center>
                      <bean:message key="colname.on_hand_report_number" />
                      <br>
                      <html:text property="ohd_id" size="13" maxlength="13" styleClass="textfield" value="" onblur="fillzero(this,13);" />
                    </td>
                    <tr>
                      <td align="center" valign="top" colspan="12">
                        <html:submit property="get_ohd" styleId="button" onclick="fillzero(document.BDOForm.ohd_id, 13); return true;">
                          <bean:message key="button.search" />
                        </html:submit>
                      </td>
                    </tr>
                  </table>
                  
                </logic:present>
                <logic:present name="showbdolist" scope="request">
                
                <jsp:include page="bdo_list_incl.jsp" />
              </logic:present>
              <logic:notPresent name="showbdolist" scope="request">
                <logic:present name="showbdo" scope="request">
                
                <jsp:include page="edit_bdo_incl.jsp" />
                
              </logic:present>
            </logic:notPresent>
          </html:form>
