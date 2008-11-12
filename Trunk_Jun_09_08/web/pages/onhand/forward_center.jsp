<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.forms.ForwardMessageForm" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%
  Agent a = (Agent)session.getAttribute("user");
%>

<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>

<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/ajax_forall.js"></SCRIPT>
<script langugage="javascript">
  <!--
	function getstations() {
		o = document.incidentForm;
		o.getstation.value="1";
		document.getElementById("faultstationdiv").innerHTML = "<td nowrap><bean:message key="ajax.please_wait" /></td>";
			postForm("incidentForm", true, function (req) { 
				o.getstation.value = "0";
				document.getElementById("faultstationdiv").innerHTML = req.responseText; 
		});
	}
	
    function textCounter(field, countfield, maxlimit) {
      if (field.value.length > maxlimit) {
        field.value = field.value.substring(0, maxlimit);
      } else {
        countfield.value = maxlimit - field.value.length;
      }
    }

	var cal1xx = new CalendarPopup();	

 	function setExpediteNum(form) { return true; }

//-->
</script>


  <jsp:include page="/pages/includes/validation_incl.jsp" />
  <html:form action="forward_message.do" method="post">
    <jsp:include page="/pages/includes/taskmanager_header.jsp" />
    <tr>
      <!-- MIDDLE COLUMN -->
      <td id="middlecolumn">
        <!-- MAIN BODY -->
        <div id="maincontent">
          <h1 class="green">
            <bean:message key="header.forward_message_title" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/view_incoming_requests.htm#forward_requested_bag');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
            <br>
            <table class="form2" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  * <bean:message key="colname.bag_tag_number" />
                  :
                </td>
                <td>
                  <html:text name="forwardMessageForm" property="bag_tag" size="14" maxlength="13" style="textfield" />
                </td>
              </tr>
              <tr>
                <td>
                  * <bean:message key="colname.expedite_number" />
                  :
                </td>
                <td>
                  <html:text name="forwardMessageForm" property="expediteNumber" size="20" maxlength="10" />
                </td>
              </tr>
              <tr>
                <td>
                  * <bean:message key="colname.stationForwardTo" />
                  :
                </td>
                <td>
                    <logic:present name="stationList" scope="request">
                      <html:select name="forwardMessageForm" styleClass="dropdown" property="destStation">
                        <logic:empty name="forwardMessageForm" property="destStation">
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
			  <td>
			    <bean:message key="colname.losscode" />
			  </td>
			  <td>
			    <html:select property="lossCode" styleClass="dropdown">
			      <html:option value="0">
			        <bean:message key="select.please_select" />
			      </html:option>
				  <html:options collection="losscodes" property="loss_code" labelProperty="combination"/>
			    </html:select>
			  </td>
			</tr>

              <tr>
              	<td><bean:message key="colname.faultstation" /></td>
              	<td>
					<html:select property="faultStation" styleClass="dropdown">
					  <html:option value="">
					    <bean:message key="select.please_select" />
					  </html:option>
					  <html:options collection="faultstationlist" property="station_ID" labelProperty="stationcode" />
					</html:select>
              	</td>
              </tr>
              <tr>
              	<td><bean:message key="colname.fwd.specialinstructions" /></td>
              	<td>
                  <html:text name="forwardMessageForm" property="specialInstructions" size="20" maxlength="20" />
              	</td>
              </tr>
              
              <tr>
                <td colspan="2">
                  <table class="form2" cellspacing="0" cellpadding="0">
                    <tr>
                      <td colspan="6">
                        <strong>
                          <bean:message key="header.ohd_bag_itinerary" />
                        </strong>
                      </td>
                    </tr>
                    <tr>
                      <td valign=top>
                        <bean:message key="colname.fromto" />
                      </td>
                      <td valign=top>
                        <bean:message key="colname.flightnum" />
                      </td>
                      <td>
                        <bean:message key="colname.departdate" /><br>
                        (
                        <%= a.getDateformat().getFormat() %>)
                      </td>
                      <td>
                        <bean:message key="colname.arrdate" /><br>
                        (
                        <%= a.getDateformat().getFormat() %>)
                      </td>
                      <td>
                        <bean:message key="colname.schdeptime" /><br>
                        (
                        <%= a.getTimeformat().getFormat() %>)
                      </td>
                      <td>
                        <bean:message key="colname.scharrtime" /><br>
                        (
                        <%= a.getTimeformat().getFormat() %>)
                      </td>
                    </tr>
                    <logic:iterate indexId="k" id="bagItinerary" name="forwardMessageForm" property="bagitinerarylist">
                      <tr>
                        <td nowrap>
                        	<html:hidden name="bagItinerary" property="itinerarytype" value="1" indexed="true" />
     
                          <html:text name="bagItinerary" property="legfrom" size="3" maxlength="3" styleClass="textfield" indexed="true" />
                          <a href="#" onclick="openWindow('pages/popups/airportcodes.jsp?key=bagItinerary[<%= k %>].legfrom','airportcode',500,600);return false;"><img src="deployment/main/images/nettracer/airport_codes.gif" border=0></a>
                          /<br>
                          <html:text name="bagItinerary" property="legto" size="3" maxlength="3" styleClass="textfield" indexed="true" />
                          <a href="#" onclick="openWindow('pages/popups/airportcodes.jsp?key=bagItinerary[<%= k %>].legto','airportcode',500,600);return false;"><img src="deployment/main/images/nettracer/airport_codes.gif" border=0></a>
                            

                        </td>
                        <td>
                        	<html:select name="bagItinerary" property="airline" styleClass="dropdown" indexed="true">
		                    	<html:option value="">
		                      		<bean:message key="select.please_select" />
		                    	</html:option>
		                    	<html:options collection="companylistById" property="companyCode_ID" labelProperty="companyCode_ID" />
		                  	</html:select>
                          &nbsp;<br>
                          <html:text styleClass="textfield" name="bagItinerary" property="flightnum" size="4" maxlength="4" indexed="true" />
                        </td>
                        <td nowrap>
                          <html:text styleClass="textfield" name="bagItinerary" property="disdepartdate" size="11" maxlength="10" indexed="true" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar<%= k %>" name="calendar<%= k %>" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.forwardMessageForm, '<%= "bagItinerary[" + k + "].disdepartdate" %>','calendar<%= k %>','<%= a.getDateformat().getFormat() %>'); return false;"></td>
                        <td nowrap>
                          <html:text styleClass="textfield" name="bagItinerary" property="disarrivedate" size="11" maxlength="10" indexed="true" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2<%= k %>" name="calendar2<%= k %>" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.forwardMessageForm, '<%= "bagItinerary[" + k + "].disarrivedate" %>','calendar2<%= k %>','<%= a.getDateformat().getFormat() %>'); return false;"></td>
                        <td>
                          <html:text styleClass="textfield" name="bagItinerary" property="disschdeparttime" size="8" maxlength="5" indexed="true" />
                        </td>
                        <td>
                          <html:text styleClass="textfield" name="bagItinerary" property="disscharrivetime" size="8" maxlength="5" indexed="true" />
                        </td>
                      </tr>
                      <tr>
                        <td colspan=6>
                          <html:submit styleId="button" property="deleteBag" indexed="true">
                            <bean:message key="button.delete_bag_itinerary" />
                          </html:submit>
                        </td>
                      </tr>
                    </logic:iterate>
                  </table>
                  <center><html:submit styleId="button" property="addbagitinerary">
                    <bean:message key="button.add_bag_itinerary" />
                  </html:submit></center>
                </td>
              </tr>
              <tr>
                <td colspan="2">
                  <table>
                    <tr>
                      <td colspan="6">
                        <strong>
                          <bean:message key="header.forward_itinerary" />
                        </strong>
                      </td>
                    </tr>
                    <tr>
                      <td valign="top">
                        <bean:message key="colname.fromto.req" />
                      </td>
                      <td valign="top">
                        <bean:message key="colname.flightnum.req" />
                      </td>
                      <td>
                        <bean:message key="colname.fwd.departdate.req" /><br>
                        (
                        <%= a.getDateformat().getFormat() %>)
                      </td>
                      <td>
                        <bean:message key="colname.arrdate" /><br>
                        (
                        <%= a.getDateformat().getFormat() %>)
                      </td>
                      <td>
                        <bean:message key="colname.schdeptime" /><br>
                        (
                        <%= a.getTimeformat().getFormat() %>)
                      </td>
                      <td>
                        <bean:message key="colname.scharrtime" /><br>
                        (
                        <%= a.getTimeformat().getFormat() %>)
                      </td>
                    </tr>
                    <logic:iterate indexId="k" id="itinerary" name="forwardMessageForm" property="forwarditinerarylist">
                      <tr>
                        <td nowrap>

     					<html:hidden name="itinerary" property="itinerarytype" value="1" indexed="true" />
     											
                          <html:text name="itinerary" property="legfrom" size="3" maxlength="3" styleClass="textfield" indexed="true" />
                          <a href="#" onclick="openWindow('pages/popups/airportcodes.jsp?key=itinerary[<%= k %>].legfrom','airportcode',500,600);return false;"><img src="deployment/main/images/nettracer/airport_codes.gif" border=0></a>
                          /<br>
                          <html:text name="itinerary" property="legto" size="3" maxlength="3" styleClass="textfield" indexed="true" />
                          <a href="#" onclick="openWindow('pages/popups/airportcodes.jsp?key=itinerary[<%= k %>].legto','airportcode',500,600);return false;"><img src="deployment/main/images/nettracer/airport_codes.gif" border=0></a>
      
                        </td>
                        <td >
                        	<html:select name="itinerary" property="airline" styleClass="dropdown" indexed="true">
		                    	<html:option value="">
		                      		<bean:message key="select.please_select" />
		                    	</html:option>
		                    	<html:options collection="companylistById" property="companyCode_ID" labelProperty="companyCode_ID" />
		                  	</html:select>
                          &nbsp;<br>
                          <html:text styleClass="textfield" name="itinerary" property="flightnum" size="4" maxlength="4" indexed="true" />
                        </td>
                        <td nowrap>
                          <html:text styleClass="textfield" name="itinerary" property="disdepartdate" size="11" maxlength="10" indexed="true" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar3<%= k %>" name="calendar3<%= k %>" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.forwardMessageForm, '<%= "itinerary[" + k + "].disdepartdate" %>','calendar3<%= k %>','<%= a.getDateformat().getFormat() %>'); return false;"></td>
                        <td nowrap>
                          <html:text styleClass="textfield" name="itinerary" property="disarrivedate" size="11" maxlength="10" indexed="true" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar4<%= k %>" name="calendar4<%= k %>" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.forwardMessageForm, '<%= "itinerary[" + k + "].disarrivedate" %>','calendar4<%= k %>','<%= a.getDateformat().getFormat() %>'); return false;"></td>
                        <td>
                          <html:text styleClass="textfield" name="itinerary" property="disschdeparttime" size="8" maxlength="5" indexed="true" />
                        </td>
                        <td>
                          <html:text styleClass="textfield" name="itinerary" property="disscharrivetime" size="8" maxlength="5" indexed="true" />
                        </td>
                      </tr>
                      <tr>
                        <td colspan=6>
                          <html:submit styleId="button" property="deleteForward" indexed="true">
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
                  <input disabled="true" type="text" name="remLen" size="4" maxlength="4" value="1500">
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
                  <html:submit styleId="button" property="save" onclick="return (validateMessageForm(this.form) && setExpediteNum(this.form)); ">
                    <bean:message key="button.forward" />
                  </html:submit>
                </td>
              </tr>
            </table>
          </html:form>
