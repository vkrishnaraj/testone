<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%
  Agent a = (Agent)session.getAttribute("user");
%>
  <!-- Calendar includes -->
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
    <!--
	var cal1xx = new CalendarPopup();	
	//cal1xx.showNavigationDropdowns();
// -->
  </SCRIPT>
  <!-- calendar stuff ends here -->
  <html:form action="claim_prorate.do" method="post">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.claim_prorate" />
            (
            <bean:write name="incident" scope="request" />
            )
          </h1>
        </div>
        <div id="pageheaderright">
          <table id="pageheaderright">
            <tr>
              <td>
                <a href="#" onclick="openReportWindow('reporting?print=<bean:write name="CLAIM_PRORATE_RPT" scope="request"/>','ProrateReport',800,600);return false;"><img src="deployment/main/images/nettracer/icon_printrpt.gif" width="12" height="12"></a>
              </td>
              <td>
                <a href="#" onclick="openReportWindow('reporting?print=<bean:write name="CLAIM_PRORATE_RPT" scope="request"/>','ProrateReport',800,600);return false;"><bean:message key="link.claim_prorate" /></a>
              </td>
              <jsp:include page="../includes/mail_incl.jsp" />
              <td>
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><bean:message key="Help" /></a>
              </td>
            </tr>
          </table>
        </div>
      </td>
    </tr>
    <!-- END PAGE HEADER/SEARCH -->
    <!-- ICONS MENU -->
    <tr>
      <td colspan="3" id="navmenucell">
        <div class="menu">
          <dl>
            <dd>
              <a href='searchIncident.do?incident=<bean:write name="incident" scope="request"/>'><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="menu.incident_info" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
<%
            if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CLAIM_RESOLUTION, a)) {
%>
              <dd>
                <a href="claim_resolution.do"><span class="aa">&nbsp;
                    <br />
                    &nbsp;</span>
                  <span class="bb"><bean:message key="menu.claim_payout" /></span>
                  <span class="cc">&nbsp;
                    <br />
                    &nbsp;</span></a>
              </dd>
<%
            }
%>
            <dd>
              <a href="#"><span class="aab">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bbb"><bean:message key="menu.claim_prorate" /></span>
                <span class="ccb">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
          </dl>
        </div>
      </td>
    </tr>
    <!-- END ICONS MENU -->
    <tr>
      <!-- MIDDLE COLUMN -->
      <td id="middlecolumn">
        <!-- MAIN BODY -->
        <div id="maincontent">
          <center><h1 class="green">
            <bean:write name="claimProrateForm" property="companyName" />
            <br>
            <bean:message key="header.claim_prorate" />
          </h1></center>
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <font color="green">
            <logic:present name="success" scope="request">
              <bean:message key="resultmsg.claim_prorate.success" />
            </logic:present>
          </font>
          <br>
          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td width=30%>
                <bean:message key="colname.createdate" />
                :
              </td>
              <td>
                <bean:write name="claimProrateForm" property="displaydate" />
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.pass_name" />
                :
              </td>
              <td>
                <bean:write name="claimProrateForm" property="passname" />
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.file_reference" />
                :
              </td>
              <td>
                <bean:write name="claimProrateForm" property="file_reference" />
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.claim_type" />
                :
              </td>
              <td>
                <bean:write name="claimProrateForm" property="claimtype" />
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.ticket" />
                :
              </td>
              <td>
                <bean:write name="claimProrateForm" property="ticketnumber" />
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.baggage_check" />
                :
              </td>
              <td>
                <logic:iterate id="claimchecknumlist" name="claimProrateForm" property="claimchecknumlist">
                  <bean:write name="claimchecknumlist" property="claimchecknum" />
                  <br>
                </logic:iterate>
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <html:checkbox property="pir_attached" value="1" />
                &nbsp;
                <html:hidden property="pir_attached" value="0" />
                <bean:message key="colname.pir_attached" />
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <html:checkbox property="claim_attached" value="1" />
                &nbsp;
                <html:hidden property="claim_attached" value="0" />
                <bean:message key="colname.claim_attached" />
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <html:checkbox property="confirmpayment_attached" value="1" />
                &nbsp;
                <html:hidden property="confirmpayment_attached" value="0" />
                <bean:message key="colname.confirmpayment_attached" />
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <table border=0 width="100%" cellpadding="2" cellspacing="0">
                  <tr>
                    <td>
                      <strong>
                        <bean:message key="colname.airlineflightdate" />
                      </strong>
                    </td>
                    <td>
                      <strong>
                        <bean:message key="colname.from" />
                      </strong>
                    </td>
                    <td>
                      <strong>
                        <bean:message key="colname.to" />
                      </strong>
                    </td>
                    <td>
                      <strong>
                        <bean:message key="colname.statute_miles" />
                      </strong>
                    </td>
                    <td>
                      <strong>
                        <bean:message key="colname.percentage" />
                      </strong>
                    </td>
                    <td>
                      <strong>
                        <bean:message key="colname.share" />
                      </strong>
                    </td>
                    <td>
                      <strong>
                        <bean:message key="colname.currency" />
                      </strong>
                    </td>
                  </tr>
                  <logic:iterate id="itinerarylist" indexId="k" name="claimProrateForm" property="itinerarylist">
                    <tr>
                      <td align="left" nowrap>
                        <html:select name="itinerarylist" property="airline" styleClass="dropdown" indexed="true">
                          <html:option value="">
                            <bean:message key="select.please_select" />
                          </html:option>
                          <html:options collection="companylist" property="companyCode_ID" labelProperty="companyCode_ID" />
                        </html:select>
                        &nbsp;
                        <html:text name="itinerarylist" property="flightnum" size="8" maxlength="7" styleClass="textfield" indexed="true" />
                        <html:text name="itinerarylist" property="disdepartdate" size="10" maxlength="10" styleClass="textfield" indexed="true" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar<%= k %>" name="calendar<%= k %>" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.claimProrateForm, '<%= "itinerarylist[" + k + "].disdepartdate" %>','calendar<%= k %>','<%= a.getDateformat().getFormat() %>'); return false;"></td>
                      <td align="left" nowrap>
                        <html:text name="itinerarylist" property="legfrom" size="3" maxlength="3" styleClass="textfield" indexed="true" />
                        <a href="#" onclick="openWindow('pages/popups/airportcodes.jsp?key=itinerarylist[<%= k %>].legfrom','airportcode',500,600);"><img src="deployment/main/images/nettracer/airport_codes.gif" border=0></a>
                      </td>
                      <td align="left" nowrap>
                        <html:text name="itinerarylist" property="legto" size="3" maxlength="3" styleClass="textfield" indexed="true" />
                        <a href="#" onclick="openWindow('pages/popups/airportcodes.jsp?key=itinerarylist[<%= k %>].legto','airportcode',500,600);"><img src="deployment/main/images/nettracer/airport_codes.gif" border=0></a>
                      </td>
                      <td align="left">
                        <html:text name="itinerarylist" property="dismiles" size="11" maxlength="11" styleClass="textfield" indexed="true" />
                      </td>
                      <td align="left">
                        <html:text name="itinerarylist" property="dispercentage" size="6" maxlength="6" styleClass="textfield" indexed="true" onchange="calculatetotal(this.form);" />
                        %
                      </td>
                      <td align="left">
                        <html:text name="itinerarylist" property="disshare" size="11" maxlength="11" styleClass="textfield" indexed="true" onchange="calculatetotal(this.form);" />
                      </td>
                      <td align="left">
                        <html:select name="itinerarylist" property="currency_ID" styleClass="dropdown" indexed="true">
                          <html:options collection="currencylist" property="currency_ID" labelProperty="currency_ID" />
                        </html:select>
                      </td>
                    </tr>
                  </logic:iterate>
                  <tr>
                    <td align="center">
                      <b><bean:message key="column.total" />
                    </td>
                    <td colspan="3" align="left">
                      &nbsp;
                    </td>
                    <td align="left">
                      <html:text property="distotal_percentage" size="6" maxlength="6" styleClass="textfield" onchange="calculatetotal(this.form);" />
                      %
                    </td>
                    <td align="left">
                      <html:text property="distotal_share" size="11" maxlength="11" styleClass="textfield" onchange="calculatetotal(this.form);" />
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <html:checkbox property="all_prorate" value="1" />
                &nbsp;
                <html:hidden property="all_prorate" value="0" />
                <bean:message key="colname.all_prorate" />
                :
                <br>
                <html:textarea property="all_prorate_reason" cols="65" rows="3" />
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <html:checkbox property="remit" value="1" />
                &nbsp;
                <html:hidden property="remit" value="0" />
                <bean:message key="colname.please_remit" />
                <html:text property="disremit_amount" size="11" maxlength="11" styleClass="textfield" />
                <html:select property="currency_ID" styleClass="dropdown">
                  <html:options collection="currencylist" property="currency_ID" labelProperty="id_desc" />
                </html:select>
                (
                <bean:message key="colname.amount_currency" />
                )
                <br>
                <bean:message key="colname.directly_to" />
                :
                <br>
                <html:textarea property="remit_to" cols="90" rows="3" />
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <html:checkbox property="clearing_bill" value="1" />
                &nbsp;
                <html:hidden property="clearing_bill" value="0" />
                <bean:message key="colname.billed_through" />
              </td>
            </tr>
            <tr>
              <td align="right" colspan="2">
                <bean:message key="colname.prorate_officer" />
                :
                <html:text property="prorate_officer" size="60" maxlength="255" />
              </td>
            </tr>
            <tr>
              <td align="right" colspan="2">
                <bean:message key="colname.sita_address" />
                :
                <html:text property="sita_address" size="60" maxlength="255" />
              </td>
            </tr>
            <tr>
              <td align="right" colspan="2">
                <bean:message key="colname.or_fax_number" />
                :
                <html:text property="fax_number" size="60" maxlength="50" />
              </td>
            </tr>
            <tr>
              <td align="center" valign="top" colspan=2>
                <br>
                <html:submit property="save" styleId="button">
                  <bean:message key="button.save" />
                </html:submit>
              </td>
            </tr>
          </table>
        </html:form>
        <script language="javascript">
          <!--
function calculatetotal(o) {
	var percenttotal = 0;
	var sharetotal = 0;

	
 var formElements = o.elements;


	for (i=0; i<formElements.length; i++) {
		if (formElements[i].name.indexOf(".dispercentage") > 0) {
			percenttotal += eval(formElements[i].value);
		}
		if (formElements[i].name.indexOf(".disshare") > 0) {
			sharetotal += eval(formElements[i].value);
		}
	}
	
	o.distotal_percentage.value = percenttotal;
	o.distotal_share.value = sharetotal;

}

//-->
        </script>
