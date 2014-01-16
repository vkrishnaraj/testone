<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page import="com.bagnet.nettracer.tracing.forms.ForwardOnHandForm" %> 
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>

<%@ page import="java.util.Map,
				com.bagnet.nettracer.tracing.db.OHD"%>
<%
  Agent a = (Agent)session.getAttribute("user");

  boolean noticePermission = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_FORWARD_NOTICES, a);
%>

  <SCRIPT SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  
<%@page import="com.mysql.jdbc.IterateBlock"%>
<SCRIPT SRC="deployment/main/js/field_validation.js"></SCRIPT>
  <SCRIPT>
    function textCounter(field, countfield, maxlimit) {
      if (field.value.length > maxlimit) {
        field.value = field.value.substring(0, maxlimit);
      } else {
        countfield.value = maxlimit - field.value.length;
      }
    }

  function checkDate(strng)
  {
    return isDate(strng,'<%= a.getDateformat().getFormat() %>');
  }
  
  function checkTime(strng)
  {
    return isDate(strng,'<%= a.getTimeformat().getFormat() %>');
  }
    
  var cal1xx = new CalendarPopup();
  
  </SCRIPT>
  <jsp:include page="validateForwardOhd.jsp" />


  
  <html:form action="forward_on_hand.do" method="post" onsubmit="return validateForwardOHD(this);">
    <jsp:include page="/pages/includes/taskmanager_header.jsp" />
    <tr>
      
      <td id="middlecolumn">
        
        <div id="maincontent">
          <h1 class="green">
            <bean:message key="header.forward_on_hand_title" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
            <br>
            <table class="form2" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <bean:message key="colname.on_hand_report_number" />
                  :
                </td>
                <td>
                  <logic:iterate name="forwardOnHandForm"  property="ohdList" id="ohd" indexId="i" type="org.apache.struts.util.LabelValueBean">
                  <%
						@SuppressWarnings("unchecked")
						Map<String, OHD> ohdMap = (Map<String, OHD>)request.getAttribute("ohdMap");
						OHD ohdObj = (ohdMap == null || ohd.getLabel() == null) ? null : ohdMap.get(ohd.getLabel());
						if (ohdObj != null) {
							String bagTag =  StringUtils.stripToEmpty(ohdObj.getClaimnum());
							String color =  StringUtils.stripToEmpty(ohdObj.getColor());
							String type =  StringUtils.stripToEmpty(ohdObj.getType());
		                    %>
							<div style="float:right; width:200px;">
								<% if (!bagTag.isEmpty()) {%>
				        			<b>Bag Tag:</b> <span style="padding:18px;"><%=bagTag%></span><br/>
				        		<%}%>
				        		
								<% if (!color.isEmpty() || !type.isEmpty()) {%>
				        			<b>Color/Type:</b> <%=color%>/<%=type%><br/>
				        		<%}%>
				        		
								<% if (!ohdObj.getFullName().isEmpty()) {%>
				            		<b>Name:</b> 
				            		<span style="padding:27px;"><c:out value="<%=ohdObj.getFullName()%>" /></span>
				        		<%}%>
				           </div>
				     <%}%>                  
                  
                    <a href="addOnHandBag.do?ohd_ID=<bean:write name="ohd" property="label" />"><bean:write name="ohd" property="label" /></a>
                    &nbsp;
                      <bean:message key="colname.expedite_number" />
                      :
                      <input type="text" name="ohdList[<%=i %>].value" class="textfield" value="<%=ohd.getValue() %>"/>
				     <hr style='clear:both; border:1px solid #fff;<%=(ohdMap == null || i < ohdMap.size()-1)?"":"visibility:hidden;"%>'/>
                  </logic:iterate>
                  
                </td>
              </tr>
              <logic:notEmpty name="forwardOnHandForm" property="bag_request_id">
                <tr>
                  <td>
                    <bean:message key="colname.bagRequestId" />
                    :
                  </td>
                  <td>
                    <A HREF="request_on_hand.do?showRequest=1&request_ID=<bean:write name="forwardOnHandForm" property="bag_request_id"/>"><bean:message key="view" /></a>
                  </td>
                </tr>
              </logic:notEmpty>
              <tr>
                <td>
                  <bean:message key="colname.stationForwardTo" />
                  <font color=red>
                    *
                  </font>
                  :
                </td>
                <td>
                	<logic:present name="stationList" scope="request">
                      <html:select name="forwardOnHandForm" styleClass="dropdown" property="destStation">
                        <logic:empty name="forwardOnHandForm" property="destStation">
                          <html:option value="">
                            <bean:message key="select.please_select" />
                          </html:option>
                        </logic:empty>
                        <html:options collection="stationList" property="station_ID" labelProperty="stationcode" />
                      </html:select>
                    </logic:present>

                </td>
              </tr>
              <tr>
                <td colspan="2">
                  <table>
                    <tr>
                      <td colspan="7">
                        <strong>
                          <bean:message key="header.forward_itinerary" />
                        </strong>
                      </td>
                    </tr>
                    <tr>
                      <td valign="top">
                        <strong>
                          <bean:message key="colname.foh.fromto" />
                          <font color=red>
                    *
                  </font>
                        </strong>
                      </td>
                      <%
                        if (noticePermission) {
                      %>
                      <td valign="top">
                        <strong>
                          <bean:message key="colname.notifydestination" />
                        </strong>
                      </td>
                      <%
                        }
                      %>
                      <td valign="top">
                        <strong>
                          <bean:message key="colname.foh.flightnum" />
                          <font color=red>
                    *
                  </font>
                        </strong>
                      </td>
                      <td nowrap>
                        <strong>
                          <bean:message key="colname.foh.departdate" /><br>
                          (
                          <%= a.getDateformat().getFormat() %>)
                        </strong>
                      </td>
                      <td nowrap>
                        <strong>
                          <bean:message key="colname.arrdate" /><br>
                          (
                          <%= a.getDateformat().getFormat() %>)
                        </strong>
                      </td>
                      <td nowrap>
                        <strong>
                          <bean:message key="colname.schdeptime" /><br>
                          (
                          <%= a.getTimeformat().getFormat() %>)
                        </strong>
                      </td>
                      <td nowrap>
                        <strong>
                          <bean:message key="colname.scharrtime" /><br>
                          (
                          <%= a.getTimeformat().getFormat() %>)
                        </strong>
                      </td>
                    </tr>
                    <logic:iterate indexId="k" id="itinerarylist" name="forwardOnHandForm" property="itinerarylist">
                      <tr>
                        <td nowrap>
                          <html:hidden name="itinerarylist" property="itinerarytype" value="1" indexed="true" />
                          
                          <html:text name="itinerarylist" property="legfrom" size="3" maxlength="3" styleClass="textfield" indexed="true" />
	                        <a href="#" onclick="openWindow('pages/popups/airportcodes.jsp?key=itinerarylist[<%= k %>].legfrom','airportcode',500,600);return false;"><img src="deployment/main/images/nettracer/airport_codes.gif" border=0></a>
	                        /<br>
	                        <html:text name="itinerarylist" property="legto" size="3" maxlength="3" styleClass="textfield" indexed="true" />
	                        <a href="#" onclick="openWindow('pages/popups/airportcodes.jsp?key=itinerarylist[<%= k %>].legto','airportcode',500,600);return false;"><img src="deployment/main/images/nettracer/airport_codes.gif" border=0></a>

                        </td>
                        <%
                          if (noticePermission) {
                        %>
                        <td>
                            <html:checkbox name="itinerarylist" indexed="true" property="notify"></html:checkbox>
                        </td>
                        <%
                          }
                        %>
                        <td>
                        
                            <logic:empty name="itinerarylist" property="airline">
                              <jsp:setProperty name="itinerarylist" property="airline" value="<%= a.getCompanycode_ID() %>"/>
                            </logic:empty>
                        	
                        	<html:select name="itinerarylist" property="airline" styleClass="dropdown" indexed="true">
		                    	<html:option value="">
		                      		<bean:message key="select.please_select" />
		                    	</html:option>
		                    	<html:options collection="companylistById" property="companyCode_ID" labelProperty="companyCode_ID" />
		                  	</html:select>
		                  	
                          &nbsp;<br>
                          <html:text styleClass="textfield" name="itinerarylist" property="flightnum" size="4" maxlength="4" indexed="true" />
                        </td>
                        <td nowrap>
                          <html:text styleClass="textfield" name="itinerarylist" property="disdepartdate" size="11" maxlength="10" indexed="true" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar<%= k %>" name="calendar<%= k %>" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.forwardOnHandForm, '<%= "itinerarylist[" + k + "].disdepartdate" %>','calendar<%= k %>','<%= a.getDateformat().getFormat() %>'); return false;"></td>
                        <td nowrap>
                          <html:text styleClass="textfield" name="itinerarylist" property="disarrivedate" size="11" maxlength="10" indexed="true" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2<%= k %>" name="calendar2<%= k %>" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.forwardOnHandForm, '<%= "itinerarylist[" + k + "].disarrivedate" %>','calendar2<%= k %>','<%= a.getDateformat().getFormat() %>'); return false;"></td>
                        <td>
                          <html:text styleClass="textfield" name="itinerarylist" property="disschdeparttime" size="8" maxlength="5" indexed="true" />
                        </td>
                        <td>
                          <html:text styleClass="textfield" name="itinerarylist" property="disscharrivetime" size="8" maxlength="5" indexed="true" />
                        </td>
                      </tr>
                      <tr>
                        <%
                          if (noticePermission) {
                        %>
                        <td colspan="7">
                        <%
                          } else  {
                        %>
                        <td colspan="6">
                        <%
                          }
                        %>
                          <html:submit styleId="button" property="deleteBag" indexed="true">
                            <bean:message key="button.delete_forward_itinerary" />
                          </html:submit>
                        </td>
                      </tr>
                    </logic:iterate>
                  </table>
                  <center><html:submit styleId="button" property="additinerary">
                    <bean:message key="button.add_forward_itinerary" />
                  </html:submit></center>
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.message" />
                  :
                </td>
                <td>
                  <html:textarea styleClass="textarea" property="message" cols="60" rows="16" onkeydown="textCounter(this.form.message,this.form.remLen,1500);" onkeyup="textCounter(this.form.message,this.form.remLen,1500);" />
                  <input disabled="true" type="text" name=remLen size=4 maxlength=4 value="1500">
                </td>
              </tr>
              <tr>
                <td colspan="2">
                  &nbsp;
                </td>
              </tr>
              <tr>
                <td colspan="2" align="center">
                  <INPUT type="button" Id="button" value="Back" onClick="history.back()">
                  &nbsp;
                  <html:submit styleId="button" property="save">
                    <bean:message key="button.forward" />
                  </html:submit>
                </td>
                <td>
                  &nbsp;
                </td>
              </tr>
            </table>
          </html:form>
