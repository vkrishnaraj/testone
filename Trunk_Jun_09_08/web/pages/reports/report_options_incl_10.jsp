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
       <bean:message key="colname.onhand_date_range" />
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
         <bean:message key="colname.found_station_nobr" />
         :
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
       <bean:message key="colname.holding_station_nobr" />
       :
     </td>
     <td>
       <html:select property="holdingstation_ID" styleClass="dropdown" multiple="true">
         <html:option value="0">
           <bean:message key="select.all" />
         </html:option>
         <html:options collection="airlineallstationlist" property="station_ID" labelProperty="stationcode" />
       </html:select>
     </td>
   </tr>


  <tr>
    <td>
      <bean:message key="colname.status" />
      :
    </td>
    <td>
      <html:select property="status_ID" styleClass="dropdown">
        <html:option value="0">
          <bean:message key="select.all" />
        </html:option>
        <html:options collection="ohdStatusList" property="status_ID" labelProperty="description" />
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
                
              