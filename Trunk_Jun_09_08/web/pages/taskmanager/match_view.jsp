<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Match" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%
  Agent a = (Agent)session.getAttribute("user");
  Match m = (Match)request.getAttribute("match");
%>
  <logic:present name="match" scope="request">
    <form action="viewMatches.do">
      <input type="hidden" name="match_ID" value='<bean:write name="match" property="match_id" scope="request"/>'>
      <jsp:include page="/pages/includes/taskmanager_header.jsp" />
      <tr>
        
        <td id="middlecolumn">
          
          <div id="maincontent">
            <h1 class="green">
              <bean:message key="header.view_match" />
              <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/view_trace_results.htm#top');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
            </h1>
            <font color=red>
              <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
            </font>
            <br>
            <logic:present name="already_matched" scope="request">
              <center><font color=green>
                <b><bean:message key="message.matched" />
                !</b>
              </font></center>
            </logic:present>
            <logic:present name="unmatched" scope="request">
              <center><font color=green>
                <b><bean:message key="message.unmatched" />
                !</b>
              </font></center>
            </logic:present>
            <br>
            <table class="form2" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <bean:message key="header.file" />
                  :
                  <br>
                  <A HREF='searchIncident.do?incident=<bean:write name="match" property="mbr.incident_ID" scope="request"/>'><bean:write name="match" property="mbr.incident_ID" scope="request" /></a>
                </td>
                <td>
                  <bean:message key="header.ohd" />
                  :
                  <br>
                  <A HREF='addOnHandBag.do?ohd_ID=<bean:write name="match" property="ohd.OHD_ID" scope="request"/>'><bean:write name="match" property="ohd.OHD_ID" scope="request" /></a>
                </td>
                <td>
                  <bean:message key="header.match_percent" />
                  <br>
                  <bean:write name="match" property="disppercent" scope="request" />
                </td>
                <td>
                  <bean:message key="header.match_date" />
                  :
                  <br>
                  <bean:write name="match" property="dispdate" scope="request" />
                </td>
              </tr>
              <logic:present name="showclaimorbag" scope="request">
                <tr>
                  <td colspan=4>
                    <bean:message key="colname.matched_on" />
                    :
                    <logic:notEqual name="match" scope="request" property="claimchecknum" value="">
                      <bean:message key="colname.claimnum" />
                      : (
                      <bean:write name="match" property="claimchecknum" scope="request" />
                      )
                    </logic:notEqual>
                    <logic:notEqual name="match" scope="request" property="bagnumber" value="-1">
                      &nbsp;&nbsp;
                      <bean:message key="header.bag_info" />
                      : (
                      <bean:message key="colname.bag_number" />
                      <%= m.getBagnumber() + 1 %>)
                    </logic:notEqual>
                  </td>
                </tr>
              </logic:present>
            </table>
            <table class="form2" cellspacing="0" cellpadding="0">
              <tr>
                <td colspan=4>
                  <b><bean:message key="header.match_elements" />
                  :
                </td>
              </tr>
              <tr>
                <td>
                  <b><bean:message key="colname.item" />
                </td>
                <td>
                  <b><bean:message key="colname.percentage" />
                </td>
                <td>
                  <b><bean:message key="colname.mbr_info" />
                </td>
                <td>
                  <b><bean:message key="colname.ohd_info" />
                </td>
              </tr>
              <logic:iterate id="details" name="matchdetails" scope="request" type="com.bagnet.nettracer.tracing.db.Match_Detail">
                <tr>
                  <td>
                    <bean:message key="<%= details.getItem() %>" />
                  </td>
                  <td>
                    <bean:write name="details" property="disppercent" />
                  </td>
                  <td>
                    <bean:write name="details" property="mbr_info" />
                  </td>
                  <td>
                    <bean:write name="details" property="ohd_info" />
                  </td>
                </tr>
              </logic:iterate>
            </table>
            
            
            <logic:present name="choose_claimcheck" scope="request">
              <input type="hidden" name="detailedmatch" value="1">
              <input type="hidden" name="chooseclaimcheck" value="1">
              <br>
              <br>
              <logic:present name="choose_bags" scope="request">
                <logic:present name="choose_claimcheck" scope="request">
                  (
                  <bean:message key="match.desc_nonbag_match1" />
                  <bean:write name="match" property="mbr.incident_ID" scope="request" />
                  <bean:message key="match.desc_nonbag_match2" />
                  <bean:write name="match" property="ohd.OHD_ID" scope="request" />
                  <bean:message key="match.desc_nonbag_match3" />
                  )
                  (
                  <bean:message key="match.desc_nonbag_match" />
                  )
                </logic:present>
              </logic:present>
              <br>
              <br>
              <h1 class="green">
                <bean:message key="colname.choose_claimcheck" />
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/view_trace_results.htm#top');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
              </h1>
              <br>
              <logic:present name="choose_claimcheck" scope="request">
                <logic:notPresent name="choose_bags" scope="request">
                  (
                  <bean:message key="match.desc_bag_match1" />
                  <%= m.getBagnumber() + 1 %>
                  <bean:message key="match.desc_bag_match2" />
                  <bean:write name="match" property="mbr.incident_ID" scope="request" />
                  <bean:message key="match.desc_bag_match3" />
                  <bean:write name="match" property="ohd.OHD_ID" scope="request" />
                  <bean:message key="match.desc_bag_match4" />
                  )
                </logic:notPresent>
              </logic:present>
              <br>
              <table class="form2" cellspacing="0" cellpadding="0">
                <tr>
                  <td>
                    <logic:iterate id="claimchecks" name="claimchecklist" scope="request" type="com.bagnet.nettracer.tracing.db.Incident_Claimcheck">
                      <logic:equal name="claimchecks" property="OHD_ID" value="">
                        <input type="radio" name="selectedclaimcheck" value="<%= claimchecks.getClaimcheck_ID() %>">
                        <bean:write name="claimchecks" property="claimchecknum" />
                        <br>
                      </logic:equal>
                    </logic:iterate>
                  </td>
                </tr>
              </table>
            </logic:present>
            
            
            <logic:present name="choose_bags" scope="request">
              <input type="hidden" name="detailedmatch" value="1">
              <input type="hidden" name="choosebag" value="1">
              <br>
              <br>
              <h1 class="green">
                <bean:message key="colname.choose_bag" />
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/view_trace_results.htm#top');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
              </h1>
              <br>
              <logic:present name="choose_bags" scope="request">
                <logic:notPresent name="choose_claimcheck" scope="request">
                  (
                  <bean:message key="match.desc_claim_match1" />
                  <bean:write name="match" property="claimchecknum" scope="request" />
                  <bean:message key="match.desc_claim_match2" />
                  <bean:write name="match" property="mbr.incident_ID" scope="request" />
                  <bean:message key="match.desc_claim_match3" />
                  <bean:write name="match" property="ohd.OHD_ID" scope="request" />
                  <bean:message key="match.desc_claim_match4" />
                  )
                </logic:notPresent>
              </logic:present>
              <br>
              <logic:iterate id="items" indexId="i" name="itemlist" scope="request" type="com.bagnet.nettracer.tracing.db.Item">
              	<% if(items!=null){ %>
                <table class="form2" cellspacing="0" cellpadding="0">
                  <logic:equal name="items" property="OHD_ID" value="">
                    <tr>
                      <td colspan=3>
                        <input type="radio" name="selectedbag" value="<%= items.getBagnumber() %>">
                        <b>Select this bag
                      </td>
                    </tr>
                    <tr>
                      <td colspan=3>
                        <bean:message key="colname.bag_number" />
                        :
                        <%= items.getBagnumber() + 1 %>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <bean:message key="colname.last_name_onbag" />
                        :
                        <br>
                        <html:text name="items" property="lnameonbag" size="30" disabled="true" />
                      </td>
                      <td>
                        <bean:message key="colname.first_name_onbag" />
                        :
                        <br>
                        <html:text name="items" property="fnameonbag" size="30" disabled="true" />
                      </td>
                      <td>
                        <bean:message key="colname.mid_initial_onbag" />
                        :
                        <br>
                        <html:text name="items" property="mnameonbag" size="30" disabled="true" />
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <bean:message key="colname.arr_airline_id" />
                        :
                        <br>
                        <html:text name="items" property="arrivedonairline_ID" size="30" disabled="true" />
                      </td>
                      <td>
                        <bean:message key="colname.arr_flight_num" />
                        :
                        <br>
                        <html:text name="items" property="arrivedonflightnum" size="30" disabled="true" />
                      </td>
                      <td>
                        <bean:message key="colname.arr_date" />
                        :
                        <br>
                        <html:text name="items" property="disarrivedondate" size="30" disabled="true" />
                      </td>
                    </tr>
                    <tr>
                      <td valign=top>
                        <bean:message key="colname.color" />
                        :
                        <br>
                        <html:text name="items" property="color" size="30" disabled="true" />
                        <br>
                        <br>
                        <bean:message key="colname.bagtype" />
                        :
                        <a href="deployment/main/images/nettracer/charts/IATA_BAG1.JPG" target="top"><bean:message key="chart1" /></a>
                        <a href="deployment/main/images/nettracer/charts/IATA_BAG2.JPG" target="top"><bean:message key="chart2" /></a>
                        <br>
                        <html:text name="items" property="bagtype" size="30" disabled="true" />
                      </td>
                      <td valign=top>
                        <bean:message key="colname.x_desc" />
                        :
                        <br>
                        <!-- <input type="text" size="30" value="<bean:write name="items" property="xdescelement_ID_1"/>,<bean:write name="items" property="xdescelement_ID_2"/>,<bean:write name="items" property="xdescelement_ID_3"/>" disabled>-->
                        <input type="text" size="30" value="<bean:write name="items" property="xdescelement1"/>" disabled><br>
                        <input type="text" size="30" value="<bean:write name="items" property="xdescelement2"/>" disabled><br>
                        <input type="text" size="30" value="<bean:write name="items" property="xdescelement3"/>" disabled><br>
                      </td>
                      <td>
                        <bean:message key="colname.manufacturer" />
                        :
                        <br>
                        <html:text name="items" property="manufacturer" size="30" disabled="true" />
                      </td>
                    </tr>
                    <tr>
                      <td colspan=3>
                        <bean:message key="colname.key_contents" />
                        :
                        <br>
                        
                        <textarea name="keycontents" cols="80" rows="5" readonly="readonly"><logic:iterate id="inventories" name="items" property="inventorylist" type="com.bagnet.nettracer.tracing.db.Item_Inventory"><bean:write name="inventories"  property="description" /><logic:notEqual name="inventories" property="description" value="">, </logic:notEqual></logic:iterate></textarea>
                        
                      </td>
                    </tr>
                  </logic:equal>
                </table>
                <% } %>
              </logic:iterate>
            </logic:present>
            
            <table class="form2" cellspacing="0" cellpadding="0">
              <logic:equal name="match" property="status.status_ID" scope="request" value="<%= "" + TracingConstants.MATCH_STATUS_MATCHED %>">
                <tr>
                  <td align=center>
                    <logic:notPresent name="nobuttons" scope="request">
                      <input type="submit" name="unmatch" value='<bean:message key="button.un_match"/>' id="button">
                      &nbsp;
                    </logic:notPresent>
<%
                    if (a.getStation().getStation_ID() == m.getOhd().getHoldingStation().getStation_ID()) {
%>
                      <input type=button id="button" value='<bean:message key="colname.forwardOhd_nobr"/>' onclick='document.location.href="forward_on_hand.do?ohd_ID=<bean:write name="match" property="ohd.OHD_ID"/>";return false;'>
<%
                    } else {
%>
                      <input type=button id="button" value='<bean:message key="colname.init_roh"/>' onclick='document.location.href="request_on_hand.do?ohd_ID=<bean:write name="match" property="ohd.OHD_ID"/>&mbr_ID=<bean:write name="match" property="mbr.incident_ID"/>&match_ID=<bean:write name="match" property="match_id"/>";return false;'>
<%
                    }
%>
                  </td>
                </tr>
              </logic:equal>
              <logic:present name="cbroStationID" scope="session">
<%
                if (session.getAttribute("cbroStationID").equals("" + a.getStation().getStation_ID())) {
%>
                  <logic:notPresent name="nobuttons" scope="request">
                    <tr>
                      <td align=center>
                        <logic:notPresent name="already_matched" scope="request">
                          <logic:equal name="match" property="status.status_ID" scope="request" value="<%= "" + TracingConstants.MATCH_STATUS_OPEN %>">
                            <html:submit property="domatch" styleId="button">
                              <bean:message key="button.do_match" />
                            </html:submit>
                          </logic:equal>
                          <logic:notPresent name="choose_claimcheck" scope="request">
                            <logic:notPresent name="choose_bags" scope="request">
                              <logic:equal name="match" property="status.status_ID" scope="request" value="<%= "" + TracingConstants.MATCH_STATUS_REJECTED %>">
                                <html:submit property="unreject" styleId="button">
                                  <bean:message key="button.unreject" />
                                </html:submit>
                              </logic:equal>
                              <logic:equal name="match" property="status.status_ID" scope="request" value="<%= "" + TracingConstants.MATCH_STATUS_OPEN %>">
                                <html:submit property="reject" styleId="button">
                                  <bean:message key="button.reject" />
                                </html:submit>
                              </logic:equal>
                            </logic:notPresent>
                          </logic:notPresent>
                        </logic:notPresent>
                      </td>
                    </tr>
                  </logic:notPresent>
<%
                }
%>
              </logic:present>
              <logic:notPresent name="cbroStationID" scope="session">
                <logic:notPresent name="nobuttons" scope="request">
                  <tr>
                    <td align=center>
                      <logic:notPresent name="already_matched" scope="request">
                        <logic:equal name="match" property="status.status_ID" scope="request" value="<%= "" + TracingConstants.MATCH_STATUS_OPEN %>">
                          <html:submit property="domatch" styleId="button">
                            <bean:message key="button.do_match" />
                          </html:submit>
                        </logic:equal>
                        <logic:notPresent name="choose_claimcheck" scope="request">
                          <logic:notPresent name="choose_bags" scope="request">
                            <logic:equal name="match" property="status.status_ID" scope="request" value="<%= "" + TracingConstants.MATCH_STATUS_REJECTED %>">
                              <html:submit property="unreject" styleId="button">
                                <bean:message key="button.unreject" />
                              </html:submit>
                            </logic:equal>
                            <logic:equal name="match" property="status.status_ID" scope="request" value="<%= "" + TracingConstants.MATCH_STATUS_OPEN %>">
                              <html:submit property="reject" styleId="button">
                                <bean:message key="button.reject" />
                              </html:submit>
                            </logic:equal>
                          </logic:notPresent>
                        </logic:notPresent>
                      </logic:notPresent>
                    </td>
                  </tr>
                </logic:notPresent>
              </logic:notPresent>
              <tr>
                <td align="center">
                  <INPUT type="button" value="Back" onClick="history.back()" id="button">
                  &nbsp;
                  <INPUT type="button" value="Return to View Matches" onClick="document.location.href='viewMatches.do';return false;" Id="button">
                </td>
              </tr>
            </table>
          </form>
        </logic:present>
