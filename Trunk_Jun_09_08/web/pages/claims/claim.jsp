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
	//cal1xx.showNavigationDropdowns();
// -->
  </SCRIPT>
  <!-- calendar stuff ends here -->
  <logic:present name="noincident" scope="request">
    <html:form action="claim_resolution.do" method="post">
    <!-- search for claim from report number-->
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.claim_reportnum" />
          </h1>
        </div>
        <div id="pageheaderright">
          <table id="pageheaderright">
            <tr>
              <jsp:include page="/pages/includes/mail_incl.jsp" />
              <td>
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><bean:message key="Help" /></a>
              </td>
            </tr>
          </table>
        </div>
      </td>
    </tr>
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
                <bean:message key="colname.mbr_report_num" />
                <br>
                <html:text property="incident_ID" size="13" maxlength="13" styleClass="textfield" value="" onblur="fillzero(this,13);" />
              </td>
              <tr>
                <td align="center" valign="top" colspan="12">
                  <html:submit property="getclaim" styleId="button">
                    <bean:message key="button.search" />
                  </html:submit>
                </td>
              </tr>
            </table>
          </html:form>
        </logic:present>
        <!-- regular claim -->
        <logic:notPresent name="noincident" scope="request">
          <html:form action="claim_resolution.do" method="post" onsubmit="return validateClaimForm(this);">
            <html:javascript formName="claimForm" />
            <tr>
              <td colspan="3" id="pageheadercell">
                <div id="pageheaderleft">
                  <logic:present name="editinterim" scope="request">
                    <h1>
                      <bean:message key="header.interim_expense_request" />
                      (
                      <bean:write name="incident" scope="request" />
                      )
                    </h1>
                  </logic:present>
                  <logic:notPresent name="editinterim" scope="request">
                    <h1>
                      <bean:message key="header.claim_payout" />
                      (
                      <bean:write name="incident" scope="request" />
                      )
                    </h1>
                  </logic:notPresent>
                </div>
                <div id="pageheaderright">
                  <table id="pageheaderright">
                    <tr>
                      <logic:notPresent name="editinterim" scope="request">
                        <td>
                          <a href="#" onclick="openReportWindow('reporting?print=<bean:write name="CLAIM_PAYOUT_RPT" scope="request"/>','ExpensePayout',800,600);return false;"><img src="deployment/main/images/nettracer/icon_printrpt.gif" width="12" height="12"></a>
                        </td>
                        <td>
                          <a href="#" onclick="openReportWindow('reporting?print=<bean:write name="CLAIM_PAYOUT_RPT" scope="request"/>','ExpensePayout',800,600);return false;"><bean:message key="link.claim_payout" /></a>
                        </td>
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
                    <logic:present name="editinterim" scope="request">
                      <dd>
                        <a href="#"><span class="aab">&nbsp;
                            <br />
                            &nbsp;</span>
                          <span class="bbb"><bean:message key="menu.interim_expense" /></span>
                          <span class="ccb">&nbsp;
                            <br />
                            &nbsp;</span></a>
                      </dd>
                    </logic:present>
                    <logic:notPresent name="editinterim" scope="request">
                      <dd>
                        <a href="#"><span class="aab">&nbsp;
                            <br />
                            &nbsp;</span>
                          <span class="bbb"><bean:message key="menu.claim_payout" /></span>
                          <span class="ccb">&nbsp;
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
                    </logic:notPresent>
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
                  <logic:notPresent name="editinterim" scope="request">
                    <a name="claimamount"></a>
                    <h1 class="green">
                      <bean:message key="header.claim_amount" />
                      <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_claim_payment.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
                    </h1>
                    <font color=red>
                      <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
                    </font>
                    <br>
                    <table class="form2" cellspacing="0" cellpadding="0">
                      <tr>
                        <td>
                          <bean:message key="colname.claim_amount" />
                          <br>
                          <html:text property="disclaimamount" size="13" maxlength="13" styleClass="textfield" />
                        </td>
                        <td>
                          <bean:message key="colname.currency" />
                          <br>
                          <html:select property="claimcurrency_ID" styleClass="dropdown">
                            <html:options collection="currencylist" property="currency_ID" labelProperty="id_desc" />
                          </html:select>
                        </td>
                        <td colspan="2">
                          <bean:message key="colname.claim_status" />
                          <br>
                          <div id="tohide1">
                          <html:select property="status_ID" styleClass="dropdown">
                            <html:options collection="claimstatuslist" property="status_ID" labelProperty="description" />
                          </html:select>
                          </div>
                        </td>
                      </tr>
                      <tr>
                        <td colspan="2">
                          <bean:message key="colname.pass_name" />
                          <br>
                          <html:text property="passengername" size="40" maxlength="9" styleClass="textfield" disabled="true" />
                        </td>
                        <td colspan="2">
                          <bean:message key="colname.ssn" />
                          <br>
                          <html:text property="ssn" size="15" maxlength="9" styleClass="textfield" />
                        </td>
                      </tr>
                      <tr>
                        <td nowrap>
						              <bean:message key="colname.dlstate" />
						              <br>
						              <div id="tohide2">
						              <html:select property="dlstate" styleClass="dropdown">
						                <html:option value="">
						                  <bean:message key="select.none" />
						                </html:option>
						                <html:options collection="statelist" property="value" labelProperty="label" />
						              </html:select>
						              </div>
						            </td>
						            <td>
						              <bean:message key="colname.drivers" />
						              <br>
						              <html:text property="driverslicense" size="20" maxlength="15" styleClass="textfield" />
						            </td>
						            
						            <td nowrap>
						              <bean:message key="colname.country_of_issue" />
						              <br>
						              <html:select property="countryofissue" styleClass="dropdown">
						                <html:option value="">
						                  <bean:message key="select.none" />
						                </html:option>
						                <html:options collection="countrylist" property="value" labelProperty="label" />
						              </html:select>
						            </td>
												<td nowrap>
						              <bean:message key="colname.common_num" />
						              <br>
						              <html:text property="commonnum" size="22" maxlength="20" styleClass="textfield" />
						            </td>
            
            
                      </tr>
                      <tr>
                        <td colspan=4>
                          <bean:message key="colname.reason" />
                          &nbsp;(
                          <bean:message key="colname.for_audit" />
                          )
                          <br>
                          <html:textarea property="mod_claim_reason" cols="80" rows="3" onkeydown="textCounter(this,this,255);" onkeyup="textCounter(this,this,255);" />
                          <input name='mod_claim_reason2' type="text" id='mod_claim_reason2' value="255" size="4" maxlength="4" disabled />
                        </td>
                      </tr>
                      <tr>
                        <td align="center" colspan=4>
                          <html:submit property="saveclaim" styleId="button">
                            <bean:message key="button.save" />
                          </html:submit>
                        </td>
                      </tr>
                    </table>
                    <br>
                    <br>
                    <a name="expense"></a>
                    <h1 class="green">
                      <bean:message key="header.payout_summary" />
                      <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_claim_payment.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
                    </h1>
                    <table class="form2" cellspacing="0" cellpadding="0">
                      <tr>
                        <td>
                          <strong>
                            <bean:message key="colname.createdate" />
                          </strong>
                        </td>
                        <td>
                          <strong>
                            <bean:message key="colname.agentusername" />
                          </strong>
                        </td>
                        <td>
                          <strong>
                            <bean:message key="colname.expense_type" />
                          </strong>
                        </td>
                        <td>
                          <strong>
                            <bean:message key="colname.draft" />
                          </strong>
                        </td>
                        <td>
                          <strong>
                            <bean:message key="colname.draftreqdate" />
                          </strong>
                        </td>
                        <td>
                          <strong>
                            <bean:message key="colname.draftpaiddate" />
                          </strong>
                        </td>
                        <td>
                          <strong>
                            <bean:message key="colname.checkamt" />
                          </strong>
                        </td>
                        <td>
                          <strong>
                            <bean:message key="header.status" />
                          </strong>
                        </td>
                        <td>
                          <strong>
                            <bean:message key="header.approval_deny_date" />
                          </strong>
                        </td>
                        <td>
                          <strong>
                            <bean:message key="colname.modify" />
                          </strong>
                        </td>
                      </tr>
<%
                      double checktotal   = 0;
                      double vouchertotal = 0;
                      int    mileagetotal = 0;
                      int    i            = -1;
                      boolean samecurrency = true;
            					String lastcurrency = "";
%>
                      <logic:iterate id="expenselist" name="claimForm" property="expenselist" type="com.bagnet.nettracer.tracing.db.ExpensePayout">
                        <bean:define id="expensetype" name="expenselist" property="expensetype" type="com.bagnet.nettracer.tracing.db.ExpenseType" />
                        <bean:define id="expenselocation" name="expenselist" property="expenselocation" type="com.bagnet.nettracer.tracing.db.Station" />
<%
                        checktotal   += expenselist.getCheckamt();
                        
                        if (lastcurrency == "") lastcurrency = expenselist.getCurrency_ID();
												if (!lastcurrency.equals(expenselist.getCurrency_ID())) samecurrency = false;
								
                        vouchertotal += expenselist.getVoucheramt();
                        mileagetotal += expenselist.getMileageamt();
                        i++;
%>
                        <tr>
                          <td>
                            <bean:write name="expenselist" property="discreatedate" />
                          </td>
                          <td>
                            <bean:write name="expenselist" property="agent.username" />
                          </td>
                          <td>
                            <bean:write name="expensetype" property="description" />
                          </td>
                          <td>
                            <bean:write name="expenselist" property="draft" />
                            &nbsp;
                          </td>
                          <td>
                            <bean:write name="expenselist" property="disdraftreqdate" />
                            &nbsp;
                          </td>
                          <td>
                            <bean:write name="expenselist" property="disdraftpaiddate" />
                            &nbsp;
                          </td>
                          <td align="right" nowrap>
                            <bean:write name="expenselist" property="discheckamt" />
                            &nbsp;
                            <bean:write name="expenselist" property="currency_ID" />
                          </td>
                          <td valign="top">
                            <bean:write name="expenselist" property="status.description" />
                            &nbsp;
                          </td>
                          <td valign="top">
				                    <bean:write name="expenselist" property="dispapproval_date" />
				                    &nbsp;
				                  </td>
                          <td>
                            <a href="claim_resolution.do?modify=1&index=<%= i %>#editpayout"><bean:message key="colname.modify" /></a>
                          </td>
                        </tr>
                      </logic:iterate>
                      <tr>
                        <td>
                          <b><bean:message key="colname.total_payout" />
                          :
                        </td>
                        <td colspan="5">
                          &nbsp;
                        </td>
                        <td align="right">
                          <%
					                if (samecurrency) out.println(TracingConstants.DECIMALFORMAT.format(checktotal)); 
					                else  out.println("Multiple Currencies");
					                %>
                          <p>
                          </td>
                          <td colspan=2>
                            &nbsp;
                          </td>
                        </tr>
                        <tr>
                          <td align="center" valign="top" colspan="9">
                            <html:submit property="addnew" styleId="button">
                              <bean:message key="button.add_payout" />
                            </html:submit>
                          </td>
                        </tr>
                      </table>
                    </logic:notPresent>
                    <logic:present name="edit" scope="request">
                      <br>
                      <br>
                      <a name="editpayout"></a>
                      <h1 class="green">
                        <logic:empty name="index" scope="request">
                          <bean:message key="header.addnew_payout" />
                        </logic:empty>
                        <logic:present name="index" scope="request">
                          <bean:message key="header.edit_payout" />
                        </logic:present>
                        <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_claim_payment.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
                      </h1>
                      <table class="form2" cellspacing="0" cellpadding="0">
                        <tr>
                          <td>
                            <bean:message key="colname.createdate" />
                            <br>
                            <html:text property="createdate" size="15" styleClass="textfield" disabled="true" />
                          </td>
                          <td>
                            <bean:message key="colname.agentusername" />
                            <br>
                            <html:text property="expcreateagent" size="15" styleClass="textfield" disabled="true" />
                          </td>
                          <td>
                            <bean:message key="colname.stationcreated_nobr" />
                            <br>
                            <html:text property="expcreatestation" size="15" styleClass="textfield" disabled="true" />
                          </td>
                        </tr>
                        <tr>
                          <td>
                            <bean:message key="colname.expense_loc" />
                            <br>
                            <logic:present name="editinterim" scope="request">
                              <html:text property="expenselocationdesc" size="15" styleClass="textfield" disabled="true" />
                            </logic:present>
                            <logic:notPresent name="editinterim" scope="request">
                              <logic:present name="index" scope="request">
                                <html:text property="expenselocationdesc" size="15" styleClass="textfield" disabled="true" />
                              </logic:present>
                              <logic:empty name="index" scope="request">
                                <html:select property="expenselocation_ID" styleClass="dropdown">
                                  <html:options collection="stationlist" property="station_ID" labelProperty="stationcode" />
                                </html:select>
                              </logic:empty>
                            </logic:notPresent>
                          </td>
                          <td>
                            <bean:message key="colname.expense_type" />
                            <br>

                              <logic:present name="index" scope="request">
                                <html:text property="expensetypedesc" size="15" styleClass="textfield" disabled="true" />
                              </logic:present>
                              <logic:empty name="index" scope="request">
                                <html:select property="expensetype_ID" styleClass="dropdown">
                                  <html:options collection="expensetypelist" property="expensetype_ID" labelProperty="description" />
                                </html:select>
                              </logic:empty>
                            
                          </td>
                          <td>
                          

                            <bean:message key="colname.paycode" />
                            <br>
                            <html:select property="paycode" styleClass="dropdown">
                              <html:option value="ADV">
                                <bean:message key="claim.interim" />
                              </html:option>
                              <html:option value="DEL">
                                <bean:message key="claim.delivery" />
                              </html:option>
                              <html:option value="FIN">
                                <bean:message key="claim.final" />
                              </html:option>
                              <html:option value="INS">
                                <bean:message key="claim.insurance" />
                              </html:option>
                              <html:option value="OTH">
                                <bean:message key="claim.other" />
                              </html:option>
                            </html:select>
                          </td>
                        </tr>
                        <tr>
                          <td>
                            <bean:message key="colname.draft" />
                            <br>
                            <html:text property="draft" size="15" maxlength="10" styleClass="textfield" />
                          </td>
                          <td>
                            <bean:message key="colname.draftreqdate" />
                            (
                            <%= a.getDateformat().getFormat() %>)
                            <br>
                            <html:text property="disdraftreqdate" size="15" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.claimForm.disdraftreqdate,'calendar','<%= a.getDateformat().getFormat() %>'); return false;"></td>
                          <td>
                            <bean:message key="colname.draftpaiddate" />
                            (
                            <%= a.getDateformat().getFormat() %>)
                            <br>
                            <html:text property="disdraftpaiddate" size="15" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.claimForm.disdraftpaiddate,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;"></td>
                        </tr>
                        <tr>
                          <td>
                            <bean:message key="colname.checkamt" />
                            <br>
                            <html:text property="discheckamt" size="11" maxlength="10" styleClass="textfield" />
                            <br>
                            <bean:message key="colname.currency" />
                            <br>
                            <html:select property="currency_ID" styleClass="dropdown">
                              <html:options collection="currencylist" property="currency_ID" labelProperty="id_desc" />
                            </html:select>
                          </td>
                          <td>
                            <bean:message key="colname.voucheramt" />
                            <br>
                            <html:text property="disvoucheramt" size="15" maxlength="10" styleClass="textfield" />
                          </td>
                          <td>
                            <bean:message key="colname.mileageamt" />
                            <br>
                            <html:text property="mileageamt" size="15" maxlength="10" styleClass="textfield" />
                          </td>
                        </tr>
                        <tr>
                          <td colspan=3>
                            <bean:message key="colname.comments" />
                            <br>
                            <html:textarea property="comments" cols="80" rows="5" onkeydown="textCounter(this,this,255);" onkeyup="textCounter(this,this,255);" />
                            <input name='comments2' type="text" id='comments2' value="255" size="4" maxlength="4" disabled="true" />
                          </td>
                        </tr>
                        <logic:present name="index" scope="request">
                          <tr>
                            <td colspan=3>
                              <bean:message key="colname.reason" />
                              :(
                              <bean:message key="colname.for_audit" />
                              )
                              <br>
                              <html:textarea property="mod_exp_reason" cols="80" rows="3" onkeydown="textCounter(this,this,255);" onkeyup="textCounter(this,this,255);" />
                              <input name='mod_exp_reason2' type="text" id='mod_exp_reason2' value="255" size="4" maxlength="4" disabled="true" />
                            </td>
                          </tr>
                        </logic:present>
                        <tr>
                          <td align="center" valign="top" colspan=3>
                            <logic:present name="editinterim" scope="request">
                              <logic:present name="approveinterim" scope="request">
                                <html:hidden property="disclaimamount" value="" />
                                <html:submit property="saveapproveinterim" styleId="button">
                                  <bean:message key="button.approveinterim" />
                                </html:submit>
                                <html:submit property="savedenyinterim" styleId="button">
                                  <bean:message key="button.denyinterim" />
                                </html:submit>
                              </logic:present>
                              <logic:notPresent name="approveinterim" scope="request">
                                <html:hidden property="disclaimamount" value="" />
                                <html:hidden property="expensestatus_ID" value="<%= "" + TracingConstants.EXPENSEPAYOUT_STATUS_PENDING %>" />
                                <html:submit property="saveinterim" styleId="button">
                                  <bean:message key="button.request_for_approval" />
                                </html:submit>
                              </logic:notPresent>
                            </logic:present>
                            <logic:notPresent name="editinterim" scope="request">
                              <html:hidden property="expensestatus_ID" value="<%= "" + TracingConstants.EXPENSEPAYOUT_STATUS_APPROVED %>" />
                              <html:submit property="save" styleId="button">
                                <bean:message key="button.save" />
                              </html:submit>
                            </logic:notPresent>
                          </td>
                        </tr>
                      </table>
                      <script language=javascript>
                        <!--
	<logic:empty name="index" scope="request">
	//document.claimForm.expensetype_ID.focus();
	document.location.href="#editpayout";
	</logic:empty>
	<logic:present name="index" scope="request">
	document.claimForm.paycode.focus();
	</logic:present>
	//-->
                      </script>
                    </logic:present>
                  </html:form>
                </logic:notPresent>
