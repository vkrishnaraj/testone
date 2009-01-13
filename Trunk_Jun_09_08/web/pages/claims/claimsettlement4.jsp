<%@ page language="java" %>
<%@ page import="com.bagnet.nettracer.tracing.db.ExpensePayout" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.OHD_Photo" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
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

    // -->
  </SCRIPT>
            <tr>
              <td colspan="3" id="navmenucell">
                <div class="menu">
                  <dl>
                    <dd>
                      <a href='searchIncident.do?incident='><span class="aa">&nbsp;
                          <br />
                          &nbsp;</span>
                        <span class="bb"><bean:message key="menu.incident_info" /></span>
                        <span class="cc">&nbsp;
                          <br />
                          &nbsp;</span></a>
                    </dd>

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
                      if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CLAIM_PRORATE, a)) {
%>
                        <dd>
                          <a href="claim_prorate.do"><span class="aa">&nbsp;
                              <br />
                              &nbsp;</span>
                            <span class="bb"><bean:message key="menu.claim_prorate" /></span>
                            <span class="cc">&nbsp;
                              <br />
                              &nbsp;</span></a>
                        </dd>
<%
                      }
%>
                    <dd>
                      <a href="claim_settlement.do?screen=1"><span class="aa">&nbsp;
                          <br />
                          &nbsp;</span>
                        <span class="bb"><bean:message key="menu.claim_process" /></span>
                        <span class="cc">&nbsp;
                          <br />
                          &nbsp;</span></a>
                    </dd>
                    
                    <dd>
                      <a href="claim_settlement.do?screen=2"><span class="aa">&nbsp;
                          <br />
                          &nbsp;</span>
                        <span class="bb"><bean:message key="menu.claim_customer" /></span>
                        <span class="cc">&nbsp;
                          <br />
                          &nbsp;</span></a>
                    </dd>

                    <dd>
                      <a href="claim_settlement.do?screen=3"><span class="aa">&nbsp;
                          <br />
                          &nbsp;</span>
                        <span class="bb"><bean:message key="menu.claim_baggage" /></span>
                        <span class="cc">&nbsp;
                          <br />
                          &nbsp;</span></a>
                    </dd>
                    
                                        <dd>
                      <a href="#"><span class="aab">&nbsp;
                          <br />
                          &nbsp;</span>
                        <span class="bbb"><bean:message key="menu.claim_summary" /></span>
                        <span class="ccb">&nbsp;
                          <br />
                          &nbsp;</span></a>
                    </dd>

                  </dl>

            
            
            <!-- END ICONS MENU -->
            <tr>
              <!-- MIDDLE COLUMN -->
              <td id="middlecolumn">
                <!-- MAIN BODY -->
                <div id="maincontent">

          <html:form action="claim_settlement.do" method="post">

          <h1 class="green">Disposition</h1>
          
  <table class="form2" cellspacing="0" cellpadding="0" border="0">
	  <tr>
		  <td>Date Offer Sent<br/>        <input name="dateTakeover" type="text" size="10" value="01/01/2009"/>
        <img src="deployment/main/images/calendar/calendar_icon.gif" id="itcalendar0" name="itcalendar0" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.claimSettlementForm, 'dateTakeover','itcalendar0','MM/dd/yyyy'); return false;"> </td>
<td>Amount Claimed (USD)<br/><input type="text" value="0.00" size="10"/></td>
		  <td><strong>Total Paid (USD)</strong><br/><input type="text" value="0.00" size="10"/></td>
	    </tr>
	  <tr>
		  <td>Offer Sent Via<br/><select><option>Email</option></select></td>
		  <td>Amount Offered (USD)<br/><input type="text" value="0.00" size="10"/></td>
		  <td><strong>Total Paid Vouchers</strong><br/><input type="text" value="0.00" size="10"/></td>
	    </tr>
	  <tr>
		  <td>Release Due<br/>        <input name="dateTakeover" type="text" size="10" value="01/01/2009"/>
        <img src="deployment/main/images/calendar/calendar_icon.gif" id="itcalendar0" name="itcalendar0" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.claimSettlementForm, 'dateTakeover','itcalendar0','MM/dd/yyyy'); return false;"> </td>
		  <td>Audit VO Offered (USD)<br/><input type="text" value="0.00" size="10"/></td>
		  <td><strong>Total Paid Travel Certificates</strong><br/><input type="text" value="0.00" size="10"/></td>
	    </tr>
	  <tr>
		  <td>Revisit Requested<br/>        <input name="dateTakeover" type="text" size="10" value="01/01/2009"/>
        <img src="deployment/main/images/calendar/calendar_icon.gif" id="itcalendar0" name="itcalendar0" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.claimSettlementForm, 'dateTakeover','itcalendar0','MM/dd/yyyy'); return false;"> </td>
		  <td>Claim Status<br/><input type="text" size="10" value="Open" disabled/></td>
        
		  <td>&nbsp;</td>
	    </tr>
	  <tr>
		  <td>Revisited By<br/><input type="text" value="jbuser1" size="10"/></td>
		  <td>Date Status Change<br/> <input name="dateTakeover" type="text" size="10" value="01/01/2009"/>
        <img src="deployment/main/images/calendar/calendar_icon.gif" id="itcalendar0" name="itcalendar0" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.claimSettlementForm, 'dateTakeover','itcalendar0','MM/dd/yyyy'); return false;"> </td>
		  <td>&nbsp;</td>
	    </tr>
  </table>

  
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td align="center" valign="top">
                  <br>
                  <html:submit property="save" styleId="button">
                    <bean:message key="button.save" />
                  </html:submit>
                </td>
              </tr>
        </table>

	  <p />&nbsp; 
          
          <h1 class="green">Payment Summary</h1>


          <table class='form2' cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <strong>
                  Date
                </strong>
              </td>
              <td>
                <strong>
                  Code
                </strong>
              </td>
              <td>
                <strong>
                  Draft<br>Number
                </strong>
              </td>
              <td>
                <strong>
                  Draft Requested<br>Date
                </strong>
              </td>
              <td>
                <strong>
                  Draft Issued<br>Date
                </strong>
              </td>
              <td>
                <strong>
                  Draft<br>Amount
                </strong>
              </td>
              <td>
                <strong>
                  Voucher<br>Amount
                </strong>
              </td>
              <td>
                <strong>
                  Mileage<br>Amount
                </strong>
              </td>
              <td>
                <strong>
                  Status
                </strong>
              </td>
              <td>
                <strong>
                  Approve/Deny<br>Date
                </strong>
              </td>
              <td>
                <strong>
                  Modify
                </strong>
              </td>
            </tr>

            
            <tr>
              <td>
                <b>Total
                :
              </td>
              <td colspan="4">
                &nbsp;
              </td>
              <td align="right">
                0.00

                <p>
                </td>
                <td align="right">
                  0.00
                  <p>
                  </td>
                  <td align="right">
                    0
                    <p>
                    </td>
                  </tr>
                  
                    <tr>
                      <td align="center" valign="top" colspan="12">
                        <input type="button" name="addnewexpense" value="Add Additional Payment" onclick="document.location.href='claim_resolution.do?addnewinterim=1'" id="button">
                      </td>
                    </tr>
                  
                </table>


          
          </html:form>
  
                  
            
