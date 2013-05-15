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
                      <td>
                        <bean:message key="colname.report_type" />
                        :
                      </td>
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
					<tr>
                    <td>
                      <bean:message key="colname.mbr.print" /><font color=red>
                          *
                        </font>
                      :
                    </td>
                    <td>
                    <table>
                    	<tr>
	                    	<td>
		                    	
		                      	<html:checkbox property="showAll"/><bean:message key="colname.show.all"/>
	                      	</td>
	                      	</tr><tr>
	                      	<td>
		                    	
		                      	<html:checkbox property="showAssignCity"/><bean:message key="colname.show.assigncity"/>
	                      	</td>
	                    	<td>
		                    	
                      			<html:checkbox property="showType"/><bean:message key="colname.show.type"/>
	                      	</td>
	                    	<td>
		                    	
                      			<html:checkbox property="showLastName"/><bean:message key="colname.show.lastname"/>
	                      	</td>
	                    	<td>
		                    	
                      			<html:checkbox property="showFirstName"/><bean:message key="colname.show.firstname"/>
	                      	</td>
	                      	</tr><tr>
	                    	<td>
		                    	
                      			<html:checkbox property="showReportID"/><bean:message key="colname.show.reportid"/>
	                      	</td>
	                    	<td>
		                    	
                      			<html:checkbox property="showDate"/><bean:message key="colname.show.date"/>
	                      	</td>
	                    	<td>
		                    	
                      			<html:checkbox property="showTime"/><bean:message key="colname.show.time"/>
	                      	</td>
	                    	<td>
		                    	
                      			<html:checkbox property="showItinerary"/><bean:message key="colname.show.itinerary"/>
	                      	</td>
	                      	</tr><tr>
	                    	<td>
                      			<html:checkbox property="showDestination"/>
		                    	<bean:message key="colname.show.destination"/>
	                      	</td>
	                    	<td>
                      			<html:checkbox property="showStatus"/><bean:message key="colname.show.status"/>	
	                      	</td>
	                    	<td>
		                    	
                      			<html:checkbox property="showFaultCity"/><bean:message key="colname.show.faultcity"/>
	                      	</td>
	                    	<td>
		                    	
                      			<html:checkbox property="showLossCode"/><bean:message key="colname.show.losscode"/>
	                      	</td>
                      </tr>
                      </table>
                    </td>
                  </tr>

                      <tr>
                        <td>
                          <bean:message key="colname.assigned_station" />:
                        </td>
                      <td>
                        <html:select property="station_ID" styleClass="dropdown" multiple="true">
                          <html:option value="0">
                            <bean:message key="select.all" />
                          </html:option>
                          <html:options collection="airlineallstationlist" property="station_ID" labelProperty="stationcode" />
                        </html:select>
                      </td>
                    </tr>

                    <tr>
                      <td>
                        <bean:message key="reports.faultCompany" />
                        :
                      </td>
                      <td>
                        <html:select property="company_ID" styleClass="dropdown" multiple="true">
                          <html:options collection="companylistById" property="companyCode_ID" labelProperty="companyCode_ID" />
                        </html:select>
                      </td>
                    </tr>
                    
                    <tr>
                      <td>
                        <bean:message key="colname.faultstation" />:
                        <br />(Only valid for <%=agent.getCompanycode_ID() %> exclusive searches)
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
                      </td>
                    </tr>

    
                  <tr>
                    <td>
                      <bean:message key="colname.status" />
                      :
                    </td>
                    <td>
                      <html:select property="status_id_combo" styleClass="dropdown" multiple="true">
                        <html:option value="0">
                          <bean:message key="select.all" />
                        </html:option>
                        <html:options collection="statuslist" property="status_ID" labelProperty="description" />
                      </html:select>
                    </td>
                  </tr>
                
                  <tr>
                    <td>
                      <bean:message key="colname.agentusername" />
                      :
                    </td>
                    <td>
                      <html:text property="agent" size="12" maxlength="10" styleClass="textfield" />
                    </td>
                  </tr>
                
                  <tr>
                    <td>
                      <bean:message key="colname.report_groupby" />
                      :
                    </td>
                    <td>
                      <html:radio property="groupby" value="0" />
                      <bean:message key="radio.assigned" />
                      <html:radio property="groupby" value="1" />
                      <bean:message key="radio.fault" />
                    </td>
                  </tr>                
                
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
                
              