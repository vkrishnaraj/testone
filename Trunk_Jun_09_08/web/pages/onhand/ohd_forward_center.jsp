<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/tags/struts-nested" prefix="nested" %>

<%@ page import="com.bagnet.nettracer.tracing.forms.ForwardOnHandForm" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>

<%
  Agent a = (Agent)session.getAttribute("user");
org.apache.struts.util.PropertyMessageResources myMessages = (org.apache.struts.util.PropertyMessageResources)
request.getAttribute("org.apache.struts.action.MESSAGE");
java.util.Locale                                myLocale   = (java.util.Locale)session.getAttribute(
"org.apache.struts.action.LOCALE");
  boolean noticePermission = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_FORWARD_NOTICES, a);
%>

  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  
<%@page import="com.mysql.jdbc.IterateBlock"%>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/field_validation.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
    function textCounter(field, countfield, maxlimit) {
      if (field.value.length > maxlimit) {
        field.value = field.value.substring(0, maxlimit);
      } else {
        countfield.value = maxlimit - field.value.length;
      }
    }
    
  function validateForwardOHD (form)
	{
		 for (var j=0;j<form.length;j++) {
	  	currentElement = form.elements[j];
    	currentElementName=currentElement.name;
	    if (currentElementName.indexOf("expedite") != -1 && currentElement.type != "hidden")
	    {
	      if (currentElement.value.length < 1)
	      {
	         alert("<%= (String)myMessages.getMessage(myLocale, "colname.expedite_number") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
	         currentElement.focus();
	         return false;
	      }
	        
	      if (currentElement.value.length > 0 && !checkExpedite(currentElement.value))
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.expedite_number") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.expedite") %>");
	        currentElement.focus();
	        return false;
	      }
	    }
	    else if (currentElementName.indexOf("destStation") != -1)
	    {
	      if (currentElement.value.length < 1)
	      {
	         alert("<%= (String)myMessages.getMessage(myLocale, "colname.stationForwardTo") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
	         currentElement.focus();
	         return false;
	      }
	    }
	    else if (currentElementName.indexOf("legfrom") != -1)
	    {
	      if (currentElement.value.length > 0 && !checkLegFrom(currentElement.value))
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.fromto") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.station") %>");
	        currentElement.focus();
	        return false;
	      }
	    }
	    else if (currentElementName.indexOf("legto") != -1)
	    {
	      if (currentElement.value.length > 0 && !checkLegFrom(currentElement.value))
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.fromto") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.station") %>"); 
	        currentElement.focus();
	        return false;
	      }
	    }
	    else if (currentElementName.indexOf("flightnum") != -1)
	    {
	      if (currentElement.value.length > 0 && !checkFlightNum(currentElement.value))
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.flightnum") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.flightNum") %>");
	        currentElement.focus();
	        return false;
	      }
	    }
	    else if (currentElementName.indexOf("dispBagArriveDate") != -1)
	    {
	      if (currentElement.value.length > 0 && !checkDate(currentElement.value))
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.bag_arrived_date") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.date") %>");
	        currentElement.focus();
	        return false;
	      }
	    }
	    else if (currentElementName.indexOf("disdepartdate") != -1)
	    {
	      if (currentElement.value.length > 0 && !checkDate(currentElement.value))
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.departdate") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.date") %>");
	        currentElement.focus();
	        return false;
	      }
	    }
	    else if (currentElementName.indexOf("disarrivedate") != -1)
	    {
	      if (currentElement.value.length > 0 && !checkDate(currentElement.value))
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.arrdate") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.date") %>"); 
	        currentElement.focus();
	        return false;
	      }
	    }
	    else if (currentElementName.indexOf("disschdeparttime") != -1)
	    {
	      if (currentElement.value.length > 0 && !checkTime(currentElement.value))
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.schdeptime") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.time") %>"); 
	        currentElement.focus();
	        return false;
	      }
	    }
	    else if (currentElementName.indexOf("disscharrivetime") != -1)
	    {
	      if (currentElement.value.length > 0 && !checkTime(currentElement.value))
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.scharrtime") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.time") %>"); 
	        currentElement.focus();
	        return false;
	      }
	    } 
	 	}
    
     i = 0;
     
     while (true) {
       var format = '<%= a.getDateformat().getFormat() %>' + ' ' + '<%= a.getTimeformat().getFormat() %>';
       var arrDate = document.getElementsByName("itinerarylist[" + i +"].disarrivedate");
       var arrTime = document.getElementsByName("itinerarylist[" + i +"].disscharrivetime");
       ++i;
       var depDate = document.getElementsByName("itinerarylist[" + i +"].disdepartdate");
       var depTime = document.getElementsByName("itinerarylist[" + i +"].disschdeparttime");


       if (arrDate.length < 1 ||arrTime.length < 1 || depDate.length < 1 || depTime.length < 1 ||
        arrDate[0].value.length < 1 ||arrTime[0].value.length < 1 || depDate[0].value.length < 1 || depTime[0].value.length < 1
  ) {
          break;
       }
       
       var arrival = arrDate[0].value + ' ' + arrTime[0].value;
       var departure = depDate[0].value + ' ' + depTime[0].value;

       if (compareDates(arrival,format,departure,format) > -1) {
          alert("<%= (String)myMessages.getMessage(myLocale, "error.validation.departbeforearrive") %>"); 
        currentElement.focus();
        return false;
       }
     }
    
    if (!validateReqForward(form)) return false;
	 	 return true;
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
  


  
  <html:form action="forward_on_hand.do" method="post" onsubmit="return (validateForwardOHD(this) && setExpediteNum(this)); ">
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
                    <a href="addOnHandBag.do?ohd_ID=<bean:write name="ohd" property="label" />"><bean:write name="ohd" property="label" /></a>
                    &nbsp;
                      <bean:message key="colname.expedite_number" />
                      :
                      <input type="text" name="ohdList[<%=i %>].value" class="textfield" value="<%=ohd.getValue() %>"/>
                      <br />
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
                	<html:select name="forwardOnHandForm" property="companyCode" styleClass="dropdown" onchange="submit()" >
                    	<html:options collection="companylistById" property="companyCode_ID" labelProperty="companyCode_ID" />
                  	</html:select>
                  	
                  &nbsp;

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
