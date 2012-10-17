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
  Agent agent = (Agent)session.getAttribute("user");
%>
                  
                  
                    <tr>
                      <td colspan=2>
                        <strong>
                          <bean:message key="header.report_options" />
                        </strong>
                      </td>
                    </tr>
                    <tr>
                     <logic:notEqual name="reportnum" scope="request" value="7">
                      <td>
                        <bean:message key="colname.report_type" />
                        :
                      </td>
                      </logic:notEqual>
                      <logic:equal name="reportnum" scope="request" value="7">
                      <td>
                        <bean:message key="colname.station.report_type" />
                        :
                      </td>
                      </logic:equal>
                      <td>
                        <html:select property="itemType_ID" styleClass="dropdown">
                          <html:option value="0">
                            <bean:message key="select.all" />
                          </html:option>
                          <html:options collection="mbrreporttypes" property="itemType_ID" labelProperty="description" />
                        </html:select>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <bean:message key="colname.report_date_range" />
                        (
                        <%= agent.getDateformat().getFormat() %>)
                        <font color=red>
                          *
                        </font>
                        :
                      </td>
                      <td>
                        <html:text property="starttime" size="11" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.statReportForm.starttime,'calendar','<%= agent.getDateformat().getFormat() %>'); return false;">-
                        <html:text property="endtime" size="11" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.statReportForm.endtime,'calendar2','<%= agent.getDateformat().getFormat() %>'); return false;"></td>
                    </tr>
                    <logic:equal name="reportnum" scope="request" value="6">
                      <tr>
                        <td>
                          <bean:message key="colname.draftpaiddate_range" />
                          :
                        </td>
                        <td>
                          <html:text property="cstarttime" size="11" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar3" name="calendar3" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.statReportForm.cstarttime,'calendar3','<%= agent.getDateformat().getFormat() %>'); return false;">-
                          <html:text property="cendtime" size="11" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar4" name="calendar4" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.statReportForm.cendtime,'calendar4','<%= agent.getDateformat().getFormat() %>'); return false;"></td>
                      </tr>
                    </logic:equal>             
                    <logic:equal name="reportnum" scope="request" value="7">
                      <tr>
                        <td>
                          <bean:message key="colname.close_date_range" />
                        (
                        <%= agent.getDateformat().getFormat() %>)
                          :
                        </td>
                        <td>
                          <html:text property="cstarttime" size="11" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar3" name="calendar3" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.statReportForm.cstarttime,'calendar3','<%= agent.getDateformat().getFormat() %>'); return false;">-
                          <html:text property="cendtime" size="11" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar4" name="calendar4" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.statReportForm.cendtime,'calendar4','<%= agent.getDateformat().getFormat() %>'); return false;"></td>
                      </tr>
                    </logic:equal>       
                    <logic:equal name="reportnum" scope="request" value="8">
                      <tr>
                        <td>
                          <bean:message key="colname.close_date_range" />
                          :
                        </td>
                        <td>
                          <html:text property="cstarttime" size="11" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar3" name="calendar3" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.statReportForm.cstarttime,'calendar3','<%= agent.getDateformat().getFormat() %>'); return false;">-
                          <html:text property="cendtime" size="11" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar4" name="calendar4" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.statReportForm.cendtime,'calendar4','<%= agent.getDateformat().getFormat() %>'); return false;"></td>
                      </tr>
                    </logic:equal>
                    <logic:notEqual name="reportnum" scope="request" value="7">
                      <tr>
                        <td>
                          <logic:equal name="reportnum" scope="request" value="6">
                            <bean:message key="colname.expense_loc" />
                            :
                          </td>
                        </logic:equal>
                        <logic:notEqual name="reportnum" scope="request" value="6">
                          <bean:message key="colname.assigned_station" />
                          :
                        </td>
                      </logic:notEqual>
                      <td>
                        <html:select property="station_ID" styleClass="dropdown" multiple="true">
                          <html:option value="0">
                            <bean:message key="select.all" />
                          </html:option>
                          <html:options collection="airlineallstationlist" property="station_ID" labelProperty="stationcode" />
                        </html:select>
                      </td>
                    </tr>
                  </logic:notEqual>

                  <logic:notEqual name="reportnum" scope="request" value="5">
                    <logic:notEqual name="reportnum" scope="request" value="8">
                    
                    <tr>
                      <td>
                        <bean:message key="colname.faultstation" />
                        :
                      </td>
                      <td>
                        <html:select property="faultstation_ID" styleClass="dropdown" multiple="true">
                          <html:option value="0">
                            <bean:message key="select.all" />
                          </html:option>
                          <html:options collection="airlineallstationlist" property="station_ID" labelProperty="stationcode" />
                        </html:select>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <bean:message key="colname.losscode" />
                        :
                      </td>
                      <td>
<logic:equal name="reportnum" scope="request" value="6">
                        <html:select property="loss_code_combo" styleClass="dropdown" multiple="true">
                          <html:option value="0">
                            <bean:message key="select.please_select" />
                          </html:option>
                          <logic:iterate id="loss" name="losscodes" type="com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code">
<%
                              String t = loss.getDescription();

                              if (t.length() > 80) {
                                t = t.substring(0, 80) + "...";
                              }
%>
                            <option value="<bean:write name="loss" property="loss_code"/>">
                            <bean:write name="loss" property="loss_code" />
                            -
                            <%= t %>
                            </option>
                          </logic:iterate>
                        </html:select>
</logic:equal>
<logic:notEqual name="reportnum" scope="request" value="6">
                        <html:select property="loss_code" styleClass="dropdown">
                          <html:option value="0">
                            <bean:message key="select.please_select" />
                          </html:option>
                          <logic:iterate id="loss" name="losscodes" type="com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code">
<%
                              String t = loss.getDescription();

                              if (t.length() > 80) {
                                t = t.substring(0, 80) + "...";
                              }
%>
                            <option value="<bean:write name="loss" property="loss_code"/>">
                            <bean:write name="loss" property="loss_code" />
                            -
                            <%= t %>
                            </option>
                          </logic:iterate>
                        </html:select>
</logic:notEqual>
                      </td>
                    </tr>
                  </logic:notEqual>
                </logic:notEqual>
    
                <logic:equal name="reportnum" scope="request" value="7">
                  <tr>
                    <td>
                      <bean:message key="colname.departdate" />
                      (
                      <%= agent.getDateformat().getFormat() %>):
                    </td>
                    <td>
                      <html:text property="departdate" size="11" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar3" name="calendar3" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.statReportForm.departdate,'calendar3','<%= agent.getDateformat().getFormat() %>'); return false;"></td>
                  </tr>
                  <tr>
                    <td>
                      <bean:message key="colname.arrdate" />
                      (
                      <%= agent.getDateformat().getFormat() %>):
                    </td>
                    <td>
                      <html:text property="arrivaldate" size="11" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar4" name="calendar4" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.statReportForm.arrivaldate,'calendar4','<%= agent.getDateformat().getFormat() %>'); return false;"></td>
                  </tr>
                  <tr>
                    <td>
                      <bean:message key="colname.b_station" />
                      :
                    </td>
                    <td>
                      <html:select property="b_stationcode" styleClass="dropdown">
                        <html:option value="">
                          <bean:message key="select.all" />
                        </html:option>
                        <html:options collection="airlineallstationlist" property="stationcode" labelProperty="stationcode" />
                      </html:select>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <bean:message key="colname.e_station" />
                      :
                    </td>
                    <td>
                      <html:select property="e_stationcode" styleClass="dropdown">
                        <html:option value="">
                          <bean:message key="select.all" />
                        </html:option>
                        <html:options collection="airlineallstationlist" property="stationcode" labelProperty="stationcode" />
                      </html:select>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <bean:message key="colname.t_station" />
                      :
                    </td>
                    <td>
                      <html:select property="t_stationcode" styleClass="dropdown">
                        <html:option value="">
                          <bean:message key="select.all" />
                        </html:option>
                        <html:options collection="airlineallstationlist" property="stationcode" labelProperty="stationcode" />
                      </html:select>
                    </td>
                  </tr>
                </logic:equal>
                <logic:notEqual name="reportnum" scope="request" value="8">
                  <tr>
                    <td>
                      <bean:message key="colname.status" />
                      :
                    </td>
                    <td>
<logic:equal name="reportnum" scope="request" value="6">
                      <html:select property="status_id_combo" styleClass="dropdown" multiple="true">
                        <html:option value="0">
                          <bean:message key="select.all" />
                        </html:option>
                        <html:options collection="statuslist" property="status_ID" labelProperty="description" />
                      </html:select>
</logic:equal>
<logic:notEqual name="reportnum" scope="request" value="6">
                      <html:select property="status_ID" styleClass="dropdown">
                        <html:option value="0">
                          <bean:message key="select.all" />
                        </html:option>
                        <html:options collection="statuslist" property="status_ID" labelProperty="description" />
                      </html:select>
</logic:notEqual>
                    </td>
                  </tr>
                </logic:notEqual>
                <logic:equal name="reportnum" scope="request" value="4">
                  <tr>
                    <td>
                      <bean:message key="colname.boarded" />
                      <font color=red>
                        *
                      </font>
                      :
                    </td>
                    <td>
                      <html:text property="boarded" size="12" maxlength="10" styleClass="textfield" />
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <bean:message key="colname.per" />
                      :
                    </td>
                    <td>
                      <html:text property="perpassengers" size="12" maxlength="10" styleClass="textfield" />
                      &nbsp;
                      <bean:message key="colname.passengers" />
                    </td>
                  </tr>
                </logic:equal>
                <logic:equal name="reportnum" scope="request" value="5">
                  <tr>
                    <td>
                      <bean:message key="colname.show_top" />
                      :
                    </td>
                    <td>
                      <html:select property="numtop" styleClass="dropdown">
                        <html:option value="5">
                          <bean:message key="select.top_5" />
                        </html:option>
                        <html:option value="10">
                          <bean:message key="select.top_10" />
                        </html:option>
                      </html:select>
                    </td>
                  </tr>
                </logic:equal>
                <logic:equal name="reportnum" scope="request" value="6">
                  <tr>
                    <td>
                      <bean:message key="colname.expense_type" />
                      :
                    </td>
                    <td>
                      <html:select property="expensetype_id_combo" styleClass="dropdown" multiple="true">
                        <html:option value="0">
                          <bean:message key="select.all" />
                        </html:option>
                        <html:options collection="expensetypelist" property="expensetype_ID" labelProperty="description" />
                      </html:select>
                    </td>
                  </tr>
                  <!-- new code starts -->
                  <tr>
                    <td>
                      <bean:message key="colname.primary.sort.order" />
                      :
                    </td>
                    <td>
                      <html:select property="primary_sort_order" styleClass="dropdown">
                        <html:option value="by_create_station">
                          <bean:message key="select.primary.sort.order.create.station" />
                        </html:option>
                        <html:option value="by_agent_payment_station">
                          <bean:message key="select.primary.sort.order.agent.payment.station" />
                        </html:option>
                        <html:option value="by_fault_station">
                          <bean:message key="select.primary.sort.order.fault.station" />
                        </html:option>
                      </html:select>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <bean:message key="colname.secondary.sort.order" />
                      :
                    </td>
                    <td>
                      <html:select property="secondary_sort_order" styleClass="dropdown">
                        <html:option value="by_incident_id">
                          <bean:message key="select.secondary.sort.order.incident.id" />
                        </html:option>
                        <html:option value="by_expense_agent_username">
                          <bean:message key="select.secondary.sort.order.expense.agent.username" />
                        </html:option>
                      </html:select>
                    </td>
                  </tr>
                  <!-- new code ends -->
                </logic:equal>
                <logic:notEqual name="reportnum" scope="request" value="5">
                  <tr>
                    <td>
                      <bean:message key="colname.agentusername" />
                      :
                    </td>
                    <td>
                      <html:text property="agent" size="12" maxlength="10" styleClass="textfield" />
                    </td>
                  </tr>
                </logic:notEqual>
                <logic:equal name="reportnum" scope="request" value="3">
                  <tr>
                    <td>
                      <bean:message key="colname.report_options" />
                      :
                    </td>
                    <td>
                      <html:radio property="sumordet" value="0" />
                      <bean:message key="radio.summary" />
                      <html:radio property="sumordet" value="1" />
                      <bean:message key="radio.detail" />
                    </td>
                  </tr>
                </logic:equal>
                <logic:equal name="reportnum" scope="request" value="7">
                  <tr>
                    <td>
                      <bean:message key="colname.report_options" />
                      :
                    </td>
                    <td>
                      <html:radio property="sumordet" value="0" />
                      <bean:message key="radio.summary" />
                      <html:radio property="sumordet" value="1" />
                      <bean:message key="radio.detail" />
                    </td>
                  </tr>
                </logic:equal>
                <logic:equal name="reportnum" scope="request" value="8">
                
                <tr>
                  <td>
                    <bean:message key="colname.date_break_down" />
                    :
                  </td>
                  <td>
                    <html:radio property="sumordet" value="0" />
                    <bean:message key="radio.nobreak" />
                    <html:radio property="sumordet" value="1" />
                    <bean:message key="radio.24hour" />
                  </td>
                </tr>
              </logic:equal>
              