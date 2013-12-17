<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<html:form action="forward_on_hand.do" method="post">
  <jsp:include page="/pages/includes/taskmanager_header.jsp" />
  <tr>
    
    <td id="middlecolumn">
      
      <div id="maincontent">
        <h1 class="green">
          <bean:message key="header.forward_on_hand_title" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/forward_to_lz.htm#view bag details');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
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
                <A HREF="addOnHandBag.do?ohd_ID=<bean:write name="forwardOnHandForm" property="ohd_ID"/>"><bean:write name="forwardOnHandForm" property="ohd_ID" /></a>
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
                <bean:message key="colname.expedite_number" />
                :
              </td>
              <td>
                <html:text name="forwardOnHandForm" property="expediteNumber" size="20" maxlength="12" disabled="true" />
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
                    <td>
                      <strong>
                        <bean:message key="colname.fromto" />
                      </strong>
                    </td>
                    <td>
                      <strong>
                        <bean:message key="colname.flightnum" />
                        :
                      </strong>
                    </td>
                    <td>
                      <strong>
                        <bean:message key="colname.departdate" />
                      </strong>
                    </td>
                    <td>
                      <strong>
                        <bean:message key="colname.arrdate" />
                      </strong>
                    </td>
                    <td>
                      <strong>
                        <bean:message key="colname.schdeptime" />
                      </strong>
                    </td>
                    <td>
                      <strong>
                        <bean:message key="colname.scharrtime" />
                      </strong>
                    </td>
                  </tr>
                  <logic:iterate indexId="k" id="itinerarylist" name="forwardOnHandForm" property="itinerarylist">
                    <tr>
                      <td>
                      	<html:hidden name="itinerarylist" property="itinerarytype" value="1" indexed="true" />
                      	
                      	<html:text name="itinerarylist" property="legfrom" size="3" maxlength="3" styleClass="textfield" indexed="true" />
                        <a href="#" onclick="openWindow('pages/popups/airportcodes.jsp?key=itinerarylist[<%= k %>].legfrom','airportcode',500,600);return false;"><img src="deployment/main/images/nettracer/airport_codes.gif" border=0></a>
                        /<br>
                        <html:text name="itinerarylist" property="legto" size="3" maxlength="3" styleClass="textfield" indexed="true" />
                        <a href="#" onclick="openWindow('pages/popups/airportcodes.jsp?key=itinerarylist[<%= k %>].legto','airportcode',500,600);return false;"><img src="deployment/main/images/nettracer/airport_codes.gif" border=0></a>

                      </td>
                      <td>
                                          
                        <html:select name="itinerarylist" disabled="true" property="airline" styleClass="dropdown" indexed="true">
                          <html:option value="">
                            <bean:message key="select.please_select" />
                          </html:option>
                          <html:options collection="companylistById" property="companyCode_ID" labelProperty="companyCode_ID" />
                        </html:select>
                        &nbsp;<br>
                        <html:text name="itinerarylist" disabled="true" property="flightnum" size="4" maxlength="4" styleClass="textfield" indexed="true" />
                      </td>
                      <td>
                        <html:text styleClass="textfield" disabled="true" name="itinerarylist" property="disdepartdate" size="10" maxlength="10" indexed="true" />
                      </td>
                      <td>
                        <html:text styleClass="textfield" disabled="true" name="itinerarylist" property="disarrivedate" size="10" maxlength="10" indexed="true" />
                      </td>
                      <td>
                        <html:text styleClass="textfield" disabled="true" name="itinerarylist" property="disschdeparttime" size="8" maxlength="5" indexed="true" />
                      </td>
                      <td>
                        <html:text styleClass="textfield" disabled="true" name="itinerarylist" property="disscharrivetime" size="8" maxlength="5" indexed="true" />
                      </td>
                    </tr>
                  </logic:iterate>
                </table>
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.message" />
                :
              </td>
              <td>
                <html:textarea styleClass="textarea" property="message" cols="60" rows="20" readonly="true" />
              </td>
            </tr>
            <tr>
              <td colspan="2" align="center">
                &nbsp;
              </td>
            </tr>
            <tr>
              <td colspan="2" align="center">
                <INPUT Id="button" type="button" value="Back" onClick="history.back()">
              </td>
            </tr>
          </table>
        </html:form>
