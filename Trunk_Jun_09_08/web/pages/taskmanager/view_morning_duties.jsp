<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%
  Agent a = (Agent)session.getAttribute("user");
  int day = 0;
  
  request.setAttribute("", null);
%>
  
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	


  </SCRIPT>
  
  <script language="javascript">
    
function goprev() {
  o = document.generaltaskForm;
  o.prevpage.value = "1";
  o.pagination.value="1";
  o.submit();
}

function gonext() {
  o = document.generaltaskForm;
  o.nextpage.value="1";
  o.pagination.value="1";
  o.submit();
}

function gopage(i) {
	  o = document.generaltaskForm;
	  o.currpage.value = i;
	  o.pagination.value="1";
	  o.submit();
}
function updatePagination() {
    return true;
}

  </script>
  
  
  <jsp:include page="/pages/includes/validation_search.jsp" />
  <html:form action="GeneralTask.do?tasklist=4">
  
  <jsp:include page="/pages/includes/taskmanager_header.jsp" />
  
    
    <tr>
      
      <td id="middlecolumn">
        
        <div id="maincontent">
          <h1 class="green">
            <logic:equal name="day" scope="request" value="2">
            <%day=2; %>
                      <bean:message key="TASK_LABEL_2DAY" />
          </logic:equal>
            <logic:equal name="day" scope="request" value="3">
             <%day=3; %>
          <bean:message key="TASK_LABEL_3DAY" />
          </logic:equal>
             <logic:equal name="day" scope="request" value="4">
           <%day=4; %>
          <bean:message key="TASK_LABEL_4DAY" />
          </logic:equal>
          
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <br>

          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
            <table class="form2" cellspacing="0" cellpadding="0">
            </table>
            
            <!-- BEGIN -->
            

            
            <logic:present name="pauselist" scope="request">
              <h1 class="green">
                <bean:message key="header.morningduties.pauselist" />
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#Retrieve.htm#Retrieve_Reports');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
              </h1>
              <a name="result"></a>
              <table class="form2" cellspacing="0" cellpadding="0" width="500">
                <tr>
                  <td>
                    <b>
                      <bean:message key="colname.incident_file_num_br" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.report_type" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.incident_create_date_short" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.companycreated" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.stationcreated" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.stationassigned" />
                    </b>
                  </td>
                  <td >
                    <b>
                      <bean:message key="header.status" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.color" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.bagtype" />
                    </b>
                  </td>
                  
                  <td>
                    <b>
                      <bean:message key="colname.itinerary" />
                    </b>
                  </td>
                  <td >
                    <b>
                      <bean:message key="colname.claimnum" />
                    </b>
                  </td>
                  <td >
                    <b>
                      <bean:message key="colname.pass_name" />
                    </b>
                  </td>
                </tr>
                <logic:iterate id="results" name="pauselist" type="com.bagnet.nettracer.tracing.db.Incident">
                  <bean:define id="items" name="results" property="itemlist" />
                  <bean:define id="claimchecks" name="results" property="claimcheck_list" />
                  <bean:define id="itinerary" name="results" property="itinerary_list" />
                  <bean:define id="passengers" name="results" property="passenger_list" />
                  <tr>
                    <td>
                      <a href='GeneralTask.do?gettask=<%=day%>&incident=<bean:write name="results" property="incident_ID"/>'><bean:write name="results" property="incident_ID" /></a>
                    </td>
                    <td>
                      <bean:message name="results" property="itemtype.key" />
                    </td>
                    <td>
                      <bean:write name="results" property="displaydate" />
                    </td>
                    <td>
                      <bean:write name="results" property="stationcreated.company.companyCode_ID" />
                    </td>
                    <td>
                      <bean:write name="results" property="stationcreated.stationcode" />
                    </td>
                    <td>
                      <bean:write name="results" property="stationassigned.stationcode" />
                    </td>
                    <td>
                      <bean:message name="results" property="status.key"/>
                    </td>
                    
                    <td>
                      <logic:iterate id="item_list" name="items" type="com.bagnet.nettracer.tracing.db.Item">
                        <logic:present name="item_list" property="color">
                          <logic:notEqual name="item_list" property="color" value="">
                            <bean:write name="item_list" property="color" />
                            <br>
                          </logic:notEqual>
                        </logic:present>
                      </logic:iterate>
                      &nbsp;
                    </td>

                    <td >
                      <logic:iterate id="item_list" name="items" type="com.bagnet.nettracer.tracing.db.Item">
                        <logic:present name="item_list" property="bagtype">
                          <logic:notEqual name="item_list" property="bagtype" value="">
                            <bean:write name="item_list" property="bagtype" />
                            <br>
                          </logic:notEqual>
                        </logic:present>
                      </logic:iterate>
                      &nbsp;
                    </td>


                    <td nowrap>
                      <logic:iterate id="itin_list" name="itinerary" type="com.bagnet.nettracer.tracing.db.Itinerary">
                          <logic:equal name="itin_list" property="itinerarytype" value="0">
                          <bean:write name="itin_list" property="airline" />
                          <bean:write name="itin_list" property="flightnum" />
                          <bean:write name="itin_list" property="departdate" />
                            <br>
                          </logic:equal>
                      </logic:iterate>
                      &nbsp;
                    </td>
                    <td>
                      <logic:iterate id="item_list" name="items" type="com.bagnet.nettracer.tracing.db.Item">
                        <logic:present name="item_list" property="claimchecknum">
	                        <logic:notEqual name="item_list" property="claimchecknum" value="">
	                          <bean:write name="item_list" property="claimchecknum" />
	                          <br>
	                        </logic:notEqual>
	                      </logic:present>
                      </logic:iterate>
                      <logic:iterate id="claimcheck_list" name="claimchecks" type="com.bagnet.nettracer.tracing.db.Incident_Claimcheck">
                        <logic:notEqual name="claimcheck_list" property="claimchecknum" value="">
                          <bean:write name="claimcheck_list" property="claimchecknum" />
                          <br>
                        </logic:notEqual>
                      </logic:iterate>
                      &nbsp;
                    </td>
                    <td>
<%
                      boolean hasp = false;
%>
                      <logic:iterate id="passenger_list" name="passengers" type="com.bagnet.nettracer.tracing.db.Passenger">
<%
                        hasp = false;
%>
                        <logic:notEqual name="passenger_list" property="lastname" value="">
                          <bean:write name="passenger_list" property="lastname" />
                          ,
<%
                          hasp = true;
%>
                        </logic:notEqual>
                        <logic:notEqual name="passenger_list" property="firstname" value="">
<%
                          hasp = true;
%>
                        </logic:notEqual>
                        <bean:write name="passenger_list" property="firstname" />
                        <bean:write name="passenger_list" property="middlename" />
<%
                        if (hasp) {
%>
                          <br>
<%
                        }
%>
                      </logic:iterate>
                      &nbsp;
                    </td>
                  </tr>
                </logic:iterate>
                
              </table>
            </logic:present>
            
            <!-- END -->
            
            <logic:notPresent name="resultlist" scope="request">
            <center>
               <h1 class="green">No new tasks</h1>
              </center>
              </logic:notPresent>
            <logic:present name="resultlist" scope="request">
            <center>
            	<input type="button" value="Start Working!" onclick='document.location.href="GeneralTask.do?gettask=<%=day%>";return true;' id="button">
            </center>
            <br/>
              <h1 class="green">
                <bean:message key="header.morningduties.incidentlist" />
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#Retrieve.htm#Retrieve_Reports');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
              </h1>
              <a name="result"></a>
              <table class="form2" cellspacing="0" cellpadding="0" width="500">
                <tr>
                  <td>
                    <b>
                      <bean:message key="colname.incident_file_num_br" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.report_type" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.incident_create_date_short" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.companycreated" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.stationcreated" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.stationassigned" />
                    </b>
                  </td>
                  <td >
                    <b>
                      <bean:message key="header.status" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.color" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.bagtype" />
                    </b>
                  </td>
                  
                  <td>
                    <b>
                      <bean:message key="colname.itinerary" />
                    </b>
                  </td>
                  <td >
                    <b>
                      <bean:message key="colname.claimnum" />
                    </b>
                  </td>
                  <td >
                    <b>
                      <bean:message key="colname.pass_name" />
                    </b>
                  </td>
                </tr>
                <logic:iterate id="results" name="resultlist" type="com.bagnet.nettracer.tracing.db.Incident">
                  <bean:define id="items" name="results" property="itemlist" />
                  <bean:define id="claimchecks" name="results" property="claimcheck_list" />
                  <bean:define id="itinerary" name="results" property="itinerary_list" />
                  <bean:define id="passengers" name="results" property="passenger_list" />
                  <tr>
                    <td>
                      <a href='GeneralTask.do?gettask=<%=day%>&incident=<bean:write name="results" property="incident_ID"/>'><bean:write name="results" property="incident_ID" /></a>
                    </td>
                    <td>
                      <bean:message name="results" property="itemtype.key" />
                    </td>
                    <td>
                      <bean:write name="results" property="displaydate" />
                    </td>
                    <td>
                      <bean:write name="results" property="stationcreated.company.companyCode_ID" />
                    </td>
                    <td>
                      <bean:write name="results" property="stationcreated.stationcode" />
                    </td>
                    <td>
                      <bean:write name="results" property="stationassigned.stationcode" />
                    </td>
                    <td>
                      <bean:message name="results" property="status.key"/>
                    </td>
                    
                    <td>
                      <logic:iterate id="item_list" name="items" type="com.bagnet.nettracer.tracing.db.Item">
                        <logic:present name="item_list" property="color">
                          <logic:notEqual name="item_list" property="color" value="">
                            <bean:write name="item_list" property="color" />
                            <br>
                          </logic:notEqual>
                        </logic:present>
                      </logic:iterate>
                      &nbsp;
                    </td>

                    <td >
                      <logic:iterate id="item_list" name="items" type="com.bagnet.nettracer.tracing.db.Item">
                        <logic:present name="item_list" property="bagtype">
                          <logic:notEqual name="item_list" property="bagtype" value="">
                            <bean:write name="item_list" property="bagtype" />
                            <br>
                          </logic:notEqual>
                        </logic:present>
                      </logic:iterate>
                      &nbsp;
                    </td>


                    <td nowrap>
                      <logic:iterate id="itin_list" name="itinerary" type="com.bagnet.nettracer.tracing.db.Itinerary">
                          <logic:equal name="itin_list" property="itinerarytype" value="0">
                          <bean:write name="itin_list" property="airline" />
                          <bean:write name="itin_list" property="flightnum" />
                          <bean:write name="itin_list" property="departdate" />
                            <br>
                          </logic:equal>
                      </logic:iterate>
                      &nbsp;
                    </td>
                    <td>
                      <logic:iterate id="item_list" name="items" type="com.bagnet.nettracer.tracing.db.Item">
                        <logic:present name="item_list" property="claimchecknum">
	                        <logic:notEqual name="item_list" property="claimchecknum" value="">
	                          <bean:write name="item_list" property="claimchecknum" />
	                          <br>
	                        </logic:notEqual>
	                      </logic:present>
                      </logic:iterate>
                      <logic:iterate id="claimcheck_list" name="claimchecks" type="com.bagnet.nettracer.tracing.db.Incident_Claimcheck">
                        <logic:notEqual name="claimcheck_list" property="claimchecknum" value="">
                          <bean:write name="claimcheck_list" property="claimchecknum" />
                          <br>
                        </logic:notEqual>
                      </logic:iterate>
                      &nbsp;
                    </td>
                    <td>
<%
                      boolean hasp = false;
%>
                      <logic:iterate id="passenger_list" name="passengers" type="com.bagnet.nettracer.tracing.db.Passenger">
<%
                        hasp = false;
%>
                        <logic:notEqual name="passenger_list" property="lastname" value="">
                          <bean:write name="passenger_list" property="lastname" />
                          ,
<%
                          hasp = true;
%>
                        </logic:notEqual>
                        <logic:notEqual name="passenger_list" property="firstname" value="">
<%
                          hasp = true;
%>
                        </logic:notEqual>
                        <bean:write name="passenger_list" property="firstname" />
                        <bean:write name="passenger_list" property="middlename" />
<%
                        if (hasp) {
%>
                          <br>
<%
                        }
%>
                      </logic:iterate>
                      &nbsp;
                    </td>
                  </tr>
                </logic:iterate>
                <tr>
					<td colspan="12"> <jsp:include
						page="/pages/includes/pagination_incl.jsp" /> 
					</td>
				</tr>
              </table>
            </logic:present>
          </html:form>
