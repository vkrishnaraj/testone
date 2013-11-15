<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.db.OHD" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Status" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="java.util.ArrayList" %>

<%
  Agent a = (Agent)session.getAttribute("user");
  boolean collectPosId = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_COLLECT_POS_ID, a);
  boolean invDate=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_TO_BE_INVENTORIED, a);
  boolean wtEnabled=a.getStation().getCompany().getVariable().getWt_enabled()==1;
%>
  
  <%@page import="com.bagnet.nettracer.tracing.forms.SearchIncidentForm"%>
  <%@page import="com.bagnet.nettracer.tracing.utils.TracerProperties"%>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/ajax_forall.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	
	

	function showmanu(o) {
		if (o.value == <%= TracingConstants.MANUFACTURER_OTHER_ID %>) {
			document.getElementById("manu_other").style.visibility = "visible";
		} else {
			document.getElementById("manu_other").style.visibility = "hidden";
		}
	}
	

	
	<logic:present name="incident" scope="request">
		var currentStatusId = "<%=TracingConstants.AJAX_STATUS_INC %>";
	</logic:present>
	<logic:present name="ohd" scope="request">
		var currentStatusId = "<%=TracingConstants.AJAX_STATUS_OHD %>";
	</logic:present>
	

	function getStatusIds(type) {
		if (currentStatusId == type) {
	    	updateFields(currentStatusId);
			return true;
		} else {
		    o = document.searchIncidentForm;
		    o.changeStatuses.value=type;
		    document.getElementById("statusSpan").innerHTML = "<bean:message key="ajax.please_wait" />";
    		postForm("searchIncidentForm", true, function (req) {
    			o.changeStatuses.value = "0";
    			var temp = req.responseText;
			    document.getElementById("statusSpan").innerHTML = temp;
    		});
			if(type==2){
				document.getElementById("assignstation").innerHTML="<bean:message key="colname.holding.station"/>";
				document.getElementById("claimtitle").innerHTML="<bean:message key="match.claimchecknum" />";
			} else {
				document.getElementById("assignstation").innerHTML="<bean:message key="colname.assigned_station"/>";
				document.getElementById("claimtitle").innerHTML="<bean:message key="colname.claimnum" />";
			}
    		currentStatusId = type;
    		updateFields(currentStatusId);
    	}
	}

	function updateFields(type) {
		var posIdTd = document.getElementById("posIdTd");
		var wtIdTd = document.getElementById("wtIdTd");
		var csTd = document.getElementById("csTd");
		
		var colspan = 1;
		if (type != 2) {
			colspan = 2;
			if (posIdTd){
				posIdTd.style.display = "none";
			} 
			if(wtIdTd){
				var selectList=document.getElementsByName("searchtype");
				if(selectList[0].checked){
					wtIdTd.style.display = "inline";
					if(csTd){
						csTd.colSpan="1";
					}
				} else {
					document.getElementById("wt_id").value="";
					wtIdTd.style.display = "none";
					if(csTd){
						csTd.colSpan="2";
					}
				}
			}
			document.getElementById("ohdFields").style.display="none";
		} else {

			if (posIdTd){
				posIdTd.style.display = "inline";
			} else {
				colspan=3;
			}

			if(wtIdTd){
				wtIdTd.style.display = "inline";
				if(csTd){
					csTd.colSpan="1";
				}
			}
			document.getElementById("ohdFields").style.display="block";
		}

		var assignStationTd = document.getElementById("assignStationTd");
		if (assignStationTd) {
			assignStationTd.colSpan = colspan;
		}
	}

  </SCRIPT>
  
  <script language="javascript">
    
function goprev() {
  o = document.searchIncidentForm;
  o.prevpage.value = "1";
  o.pagination.value="1";
  o.submit();
}

function gonext() {
  o = document.searchIncidentForm;
  o.nextpage.value="1";
  o.pagination.value="1";
  o.submit();
}

function gopage(i) {
  o = document.searchIncidentForm;
  o.currpage.value = i;
  o.pagination.value="1";
  o.submit();

}
function updatePagination() {
    return true;
}

  </script>
  <jsp:include page="/pages/includes/validation_search.jsp" />
  <html:form action="customQuery.do" method="post" focus="claimchecknum" onsubmit="return validateSearch(this);">
    <input type="hidden" name="changeStatuses">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="Custom_Query" />
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
          <h1 class="green">
            <bean:message key="header.record_information" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
            <strong>
              <bean:message key="wildcard" />
            </strong>
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
            
            
            <table class="form2" cellspacing="0" cellpadding="0">
              <tr>
                <td colspan="4" align="center">
                  <b>Search For:</b><br />
                  <input type="radio" name="searchtype" value="1"
                  <logic:present name="incident" scope="request">
                  	<logic:equal name="incident" scope="request" value="1">
                    checked
                    </logic:equal> 
                  </logic:present> onclick="getStatusIds('<%=TracingConstants.AJAX_STATUS_INC %>');"
                  >
                  <bean:message key="header.lostdelay" />
                  &nbsp;&nbsp;
                  
                  <input type="radio" name="searchtype" value="2"
                  <logic:present name="incident" scope="request">
                  	<logic:equal name="incident" scope="request" value="2">
                    checked
                    </logic:equal>
                  </logic:present> onclick="getStatusIds('<%=TracingConstants.AJAX_STATUS_INC %>');"
                  >
                  <bean:message key="header.damaged" />
                  &nbsp;&nbsp;
                  
                  <input type="radio" name="searchtype" value="3"
                  <logic:present name="incident" scope="request">
                  	<logic:equal name="incident" scope="request" value="3">
                    checked
                    </logic:equal>
                  </logic:present> onclick="getStatusIds('<%=TracingConstants.AJAX_STATUS_INC %>');"
                  >
                  <bean:message key="header.missing_articles" />
                  &nbsp;&nbsp;
                  
                  <input type="radio" name="searchtype" value="4"
                  <logic:present name="incident" scope="request">
                  	<logic:equal name="incident" scope="request" value="4">
                    checked
                    </logic:equal>
                  </logic:present> onclick="getStatusIds('<%=TracingConstants.AJAX_STATUS_INC %>');"
                  >
                  <bean:message key="header.allincidents" />
                  &nbsp;&nbsp;
                  
                  <input type="radio" name="searchtype" value="5" 
                  <logic:present name="ohd" scope="request">
                    checked
                  </logic:present> onclick="getStatusIds('<%=TracingConstants.AJAX_STATUS_OHD %>');"
                  >
                  <bean:message key="header.ohdreport" />
                </td>
              </tr>
              <tr>
              	<td colspan="4" bgcolor="white">
              	  &nbsp; 
              	</td>
              </tr>
              <tr>
                <td>
                  <span id="statusSpan">
<%
	ArrayList ar = null;
	if (request.getAttribute("ohd") != null) {
		ar = (ArrayList)session.getAttribute("ohdStatusList");
		%>
		<bean:message key="colname.ohd_status" />
		<%
	} else {
		ar = (ArrayList)session.getAttribute("statuslist");
		%>
		<bean:message key="colname.status" />
		<%
	}
	if (ar != null && ar.size() > 0) {
      int selectedStatusId = ((SearchIncidentForm)request.getAttribute("searchIncidentForm")).getStatus_ID();
	%>
	<br />
	<html:select property="status_ID" styleClass="dropdown">
		<html:option value="<%= Integer.toString(TracingConstants.OHD_STATUS_ALL) %>"><bean:message key="select.all" /></html:option>
		<%
		if (request.getAttribute("ohd") != null) {
			%>
			<html:option value="<%= Integer.toString(TracingConstants.OHD_STATUS_ACTIVE) %>"><bean:message key="select.all_active" /></html:option>
		<% 
		} 
		%>
		
		<%
		if (request.getAttribute("ohd") != null) {
			%>
		<html:options collection="ohdStatusList" labelProperty="description" property="status_ID"></html:options>
		<% 
		}
		else {
			%>
		<html:options collection="statuslist" labelProperty="description" property="status_ID"></html:options>
		<% } %>
		
	</html:select>
	<% } %>
                  </span>

                  

                </td>

                <td>
                  <bean:message key="colname.recordlocator" />
                  <br>
                  <html:text property="recordlocator" size="20" maxlength="10" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.flightnum" />
                  <br>
                  <html:select property="airline" styleClass="dropdown">
                    <html:option value="">
                      <bean:message key="select.all" />
                    </html:option>
                    <html:options collection="companylistById" property="companyCode_ID" labelProperty="companyCode_ID" />
                  </html:select>
                  &nbsp;
                  <html:text property="flightnum" size="4" maxlength="4" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.date_range" />
                  (
                  <%= a.getDateformat().getFormat() %>)
                  <br>
                  <html:text property="s_createtime" size="10" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.searchIncidentForm.s_createtime,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">-
                  <html:text property="e_createtime" size="10" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.searchIncidentForm.e_createtime,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;"></td>
              </tr>
              <tr>
              	
                <% if(wtEnabled){ %>
	                <td colspan=1 id="wtIdTd">
	                  <bean:message key="colname.wt.id" />
	                  <br>
	                  <html:text property="wt_id" size="20" maxlength="10" styleClass="textfield" />
	                </td>
                <% } %>
				<td colspan="<%=wtEnabled?"1":"2"%>" id="csTd">
				<bean:message key="reports.create.station"/><br/>
              	 <html:select property="stationcreated_ID" styleClass="dropdown">
                    <html:option value="">
                      <bean:message key="select.all" />
                    </html:option>
                    <html:options collection="stationlist"  property="station_ID" labelProperty="stationcode"  />
                  </html:select>
              	 
              	<td id="assignStationTd" colspan=2 >                 
                  <span id="assignstation">	
              	 	<bean:message key="colname.assigned_station"/>
              	 </span><br/>
              	 <html:select property="stationassigned_ID" styleClass="dropdown">
                    <html:option value="">
                      <bean:message key="select.all" />
                    </html:option>
                    <html:options collection="stationlist" property="station_ID" labelProperty="stationcode" />
                  </html:select>
              	</td>
           		<% if (collectPosId) { %>
              	<td id="posIdTd">
              		<bean:message key="colname.posId" />
              		<br>
              		<html:text property="posId" size="8" maxlength="8" styleClass="textfield" />
              	</td>
         		<% } %>
              </tr>
              <tr id="ohdFields" style="display:none">
              	<% if(invDate){ %>
	             	 <td colspan="2">
		             	  <bean:message key="colname.inventory.date"/>
		                  (
		                  <%= a.getDateformat().getFormat() %>)
		                  <br/>
			              <html:text property="s_inventorydate" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar3" name="calendar3" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.searchIncidentForm.s_inventorydate,'calendar3','<%= a.getDateformat().getFormat() %>'); return false;">-
		                  <html:text property="e_inventorydate" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar4" name="calendar4" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.searchIncidentForm.e_inventorydate,'calendar4','<%= a.getDateformat().getFormat() %>'); return false;">
	                
		              </td>
	            <% } %>
	              <td colspan="<%=invDate?"1":"2"%>">
	              	  <bean:message key="colname.routing.date"/>
	                  (
	                  <%= a.getDateformat().getFormat() %>)
	                  <br/>
		              <html:text property="routingdate" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar5" name="calendar5" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.searchIncidentForm.routingdate,'calendar5','<%= a.getDateformat().getFormat() %>'); return false;">
                
	              </td>
	              <td colspan="<%=invDate?"1":"2"%>">
	              	  <bean:message key="colname.routing.station"/>
	                  <br>
	                  <html:text property="routingstation" size="3" maxlength="3" styleClass="textfield" />
	              </td>
              </tr>
            </table>
            <script language="javascript" >
            	updateFields(currentStatusId);
            </script>
            <h1 class="green">
              <bean:message key="header.pax_information" />
              <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
            </h1>
            <table class="form2" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <bean:message key="colname.last_name" />
                  <br>
                  <html:text property="lastname" size="20" maxlength="20" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.first_name" />
                  <br>
                  <html:text property="firstname" size="20" maxlength="20" styleClass="textfield" />
                </td>
                <td colspan="3">
                  <bean:message key="colname.mid_initial" />
                  <br>
                  <html:text property="middlename" size="20" maxlength="1" styleClass="textfield" />
                </td>
              </tr>
			<tr>
                    <td colspan=2>
                      <bean:message key="colname.street_addr1" />
                      <br>
                      <html:text property="address1" size="45" maxlength="50" styleClass="textfield" />
                    </td>
                    <td colspan=3>
                      <bean:message key="colname.street_addr2" />
                      <br>
                      <html:text property="address2" size="45" maxlength="50" styleClass="textfield" />
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <bean:message key="colname.city" />
                      <br>
                      <html:text property="city" size="15" maxlength="50" styleClass="textfield" />
                    </td>
                    <td>
                      <bean:message key="colname.state" />
                      <br>
                      <logic:equal name="searchIncidentForm" property="countrycode_ID" value="US">
                        <html:select property="state_ID" styleClass="dropdown" onchange="updateCountryUS(this, this.form, 'countrycode_ID', 'province');">
                          <html:option value="">
                            <bean:message key="select.none" />
                          </html:option>
                          <html:options collection="statelist" property="value" labelProperty="label" />
                        </html:select>
                      </logic:equal>
                      <logic:equal name="searchIncidentForm" property="countrycode_ID" value="">
                        <html:select property="state_ID" styleClass="dropdown" onchange="updateCountryUS(this, this.form, 'countrycode_ID', 'province');">
                          <html:option value="">
                            <bean:message key="select.none" />
                          </html:option>
                          <html:options collection="statelist" property="value" labelProperty="label" />
                        </html:select>
                      </logic:equal>
                      <logic:notEqual name="searchIncidentForm" property="countrycode_ID" value="">
                        <logic:notEqual name="searchIncidentForm" property="countrycode_ID" value="US">
                          <html:select property="state_ID" styleClass="dropdown" disabled="true" onchange="updateCountryUS(this, this.form, 'countrycode_ID', 'province');">
                            <html:option value="">
                              <bean:message key="select.none" />
                            </html:option>
                            <html:options collection="statelist" property="value" labelProperty="label" />
                          </html:select>
                        </logic:notEqual>
                      </logic:notEqual>
                    </td>
                    <td>
                      <bean:message key="colname.province" />
                      <br>
                      <logic:equal name="searchIncidentForm" property="countrycode_ID" value="US">
                      	<html:text property="province" size="15" maxlength="100" styleClass="disabledtextfield" disabled="true" />
                      </logic:equal>
                      <logic:equal name="searchIncidentForm" property="countrycode_ID" value="">
                      	<html:text property="province" size="15" maxlength="100" styleClass="textfield" disabled="false" />
                      </logic:equal>
                      <logic:notEqual name="searchIncidentForm" property="countrycode_ID" value="">
                        <logic:notEqual name="searchIncidentForm" property="countrycode_ID" value="US">
                        	<html:text property="province" size="15" maxlength="100" styleClass="textfield" disabled="false" />
                         </logic:notEqual>
                      </logic:notEqual>
                      
                    </td>
                    <td>
                      <bean:message key="colname.zip" />
                      <br>
                      <html:text property="zip" size="13" maxlength="11" styleClass="textfield" />
                    </td>
                    <td>
                      <bean:message key="colname.country" />
                      <br>
                      <html:select property="countrycode_ID" styleClass="dropdown" onchange="checkstate(this, this.form, 'state_ID', 'province');">
                        <html:option value="">
                          <bean:message key="select.none" />
                        </html:option>
                        <html:options name="OnHandForm" collection="countrylist" property="value" labelProperty="label" />
                      </html:select>
                    </td>
                  </tr>
              <tr>
                <td>
                  <bean:message key="colname.phone" />
                  <br>
                  <html:text property='phone' size="20" maxlength="25" styleClass="textfield" />
                </td>
                <td colspan="4">
                  <bean:message key="colname.email" />
                  <br>
                  <html:text property="email" size="40" maxlength="50" styleClass="textfield" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.airline_membership" />
                  <br>
                  <html:select property="companycode_ID" styleClass="dropdown">
                    <html:option value="">
                      <bean:message key="select.all" />
                    </html:option>
                    <html:options collection="companylistByName" property="companyCode_ID" labelProperty="companydesc" />
                  </html:select>
                </td>
                <td colspan="4">
                  <bean:message key="colname.membership_number" />
                  <br>
                  <html:text property="membershipnum" size="20" maxlength="20" styleClass="textfield" />
                </td>
              </tr>
            </table>
            <h1 class="green">
              <bean:message key="header.bag_info" />
              <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
            </h1>
            <table class="form2" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <div id="claimtitle">	
                  	<bean:message key="colname.claimnum" />
                  </div>
                  <br>
                  <html:text property="claimchecknum" size="20" maxlength="13" styleClass="textfield" />
                </td>
                <td colspan="2">
                  &nbsp;
                </td>
              </tr>
              <tr>
                <td valign="top">
                  <bean:message key="colname.color" />
                  <a href="#" onclick="openChart2('pages/popups/bagtypechart.jsp?charttype=3&type=color',800,10,230);return false;"><bean:message key="chart3" /></a>
                  <br>
                  <html:select property="color" styleClass="dropdown">
                    <html:options collection="colorlistforsearch" property="value" labelProperty="label" />
                  </html:select>
                  <br>
                  <br>
                  <bean:message key="colname.bagtype" />
                  <a href="#" onclick="openChart2('pages/popups/bagtypechart.jsp?charttype=1&key=bagtype&type=bagtype',800,280);return false;"><bean:message key="chart1" /></a>
                  <a href="#" onclick="openChart2('pages/popups/bagtypechart.jsp?charttype=2&key=bagtype&type=bagtype',800,370);return false;"><bean:message key="chart2" /></a>
                  <br>
                  <html:select property="bagtype" styleClass="dropdown">
                    <html:options collection="typelist" property="value" labelProperty="label" />
                  </html:select>
                </td>
                <td valign="top">
                  <bean:message key="colname.x_desc" />
                  <br>
                  <html:select property="xdescelement_ID1" styleClass="dropdown">
                    <html:option value="">
                      <bean:message key="select.all" />
                    </html:option>
                    <html:options collection="xdescelementlist" property="XDesc_ID" labelProperty="description" />
                  </html:select>
                   <a href="#" onclick="openChart2('pages/popups/bagtypechart.jsp?charttype=4&type=xdescelement_ID1',800,100,230);return false;"><bean:message key="chart4" /></a>
                  <br>
                  <html:select property="xdescelement_ID2" styleClass="dropdown">
                    <html:option value="">
                      <bean:message key="select.all" />
                    </html:option>
                    <html:options collection="xdescelementlist" property="XDesc_ID" labelProperty="description" />
                  </html:select>
                  <a href="#" onclick="openChart2('pages/popups/bagtypechart.jsp?charttype=4&type=xdescelement_ID2',800,100,230);return false;"><bean:message key="chart4" /></a>
                  <br>
                  <html:select property="xdescelement_ID3" styleClass="dropdown">
                    <html:option value="">
                      <bean:message key="select.all" />
                    </html:option>
                    <html:options collection="xdescelementlist" property="XDesc_ID" labelProperty="description" />
                  </html:select>
                  <a href="#" onclick="openChart2('pages/popups/bagtypechart.jsp?charttype=4&type=xdescelement_ID3',800,100,230);return false;"><bean:message key="chart4" /></a>
                </td>
                <td valign="top">
                  <bean:message key="colname.manufacturer" />
                  <br>
                  <html:select property="manufacturer_ID" styleClass="dropdown" onchange='showmanu(this);return true;'>
                    <html:option value="">
                      <bean:message key="select.all" />
                    </html:option>
                    <html:options collection="manufacturerlist" property="manufacturer_ID" labelProperty="description" />
                  </html:select>
                  <div id="manu_other">
                    <br>
                    <html:text property="manufacturer_other" size="25" maxlength="100" styleClass="textfield" />
                  </div>
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.category" />
                  <br>
                  <html:select property="category_ID" styleClass="dropdown">
                    <html:option value="0">
                      <bean:message key="select.all" />
                    </html:option>
                    <html:options collection="categorylist" property="OHD_CategoryType_ID" labelProperty="description" />
                  </html:select>
                </td>
                <td colspan="2">
                  <bean:message key="colname.description" />
                  <br>
                  <html:text property="description" size="80" maxlength="255" styleClass="textfield" />
                </td>
              </tr>
              <tr>
                <td colspan="3" align="center" valign="top">
                  <html:submit property="search" styleId="button">
                    <bean:message key="button.retrieve" />
                  </html:submit>
                  &nbsp;
                  <html:button styleId="button" property="reset" onclick="document.location.href='customQuery.do';">
                    <bean:message key="button.reset" />
                  </html:button>
                </td>
              </tr>
            </table>
            <SCRIPT LANGUAGE="JavaScript">
              




	if (document.searchIncidentForm.manufacturer_ID.value == <%= TracingConstants.MANUFACTURER_OTHER_ID %>) {
		document.getElementById("manu_other").style.visibility = "visible";

	} else {
		document.getElementById("manu_other").style.visibility = "hidden";
	}


            </SCRIPT>
            
            
            <logic:present name="resultlist" scope="request">
              <div id="pageheaderleft">
                <a name="result"></a>
                <h1 class="green">
                  <bean:message key="header.search_result" />
                  <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
                </h1>
              </div>
              
              <%
                if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_QUERY_REPORTS, a)) {
              %>
              <div id="pageheaderright">
                  <select name="outputtype" class="dropdown">
                    <% if (!TracerProperties.isTrue(a.getCompanycode_ID(),TracerProperties.SUPPRESSION_PRINTING_NONHTML)) { %>
                      <option value="0" selected="yes"><bean:message key="radio.pdf" /></option>
                    <% } %>
                    <option value="1"><bean:message key="radio.html" /></option>
                  </select>
                  <input type="submit" name="generateReport" id="button" value="<bean:message key="button.generateReport" />">
                  <logic:present name="reportfile" scope="request">
                    <script language=javascript>
                      
                        openReportWindow('reporting?outputtype=<%= request.getAttribute("outputtype") %>&reportfile=<bean:write name="reportfile" scope="request" />','report',800,600);

                    </script>
                  </logic:present>
              </div>
              <%
                }
              %>
              
              
              <table class="form2" cellspacing="0" cellpadding="0">
                <logic:present name="incident" scope="request">
                  <tr>
                    <td>
                      <strong>
                        <bean:message key="colname.incident_num" />
                      </strong>
                    </td>
                    <td>
                      <strong>
                        <bean:message key="colname.incident_create_date" />
                        :
                      </strong>
                    </td>
                    <td>
                      <strong>
                        <bean:message key="header.companyCode" />
                        :
                      </strong>
                    </td>
                    <td>
                      <strong>
                        <bean:message key="colname.stationcreated" />
                        :
                      </strong>
                    </td>
                    <td>
                      <strong>
                        <bean:message key="colname.stationassigned" />
                        :
                      </strong>
                    </td>

                    <td>
                      <strong>
                        <bean:message key="colname.color" />
                      </strong>
                    </td>
                    <td>
                      <strong>
                        <bean:message key="colname.bagtype" />
                      </strong>
                    </td>
                    
                    <td>
                      <strong>
                        <bean:message key="header.status" />
                      </strong>
                    </td>
                    <logic:notEmpty name="searchIncidentForm" property="flightnum">
                      <td>
                        <strong>
                          <bean:message key="colname.flightnum" />
                        </strong>
                      </td>
                    </logic:notEmpty>
                    <td>
                      <strong>
                        <bean:message key="colname.claimnum" />
                      </strong>
                    </td>
                    <td>
                      <strong>
                        <bean:message key="colname.pass_name" />
                      </strong>
                    </td>
                  </tr>
                  <logic:iterate id="results" name="resultlist" type="com.bagnet.nettracer.tracing.db.Incident">
                    <bean:define id="items" name="results" property="itemlist" />
                    <bean:define id="claimchecks" name="results" property="claimcheck_list" />
                    <bean:define id="passengers" name="results" property="passenger_list" />
                    <tr>
                      <td>
                        <a href='searchIncident.do?incident=<bean:write name="results" property="incident_ID"/>'><bean:write name="results" property="incident_ID" /></a>
                      </td>
                      <td nowrap>
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
                      <td>
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
                      <td>
                        <bean:message name="results" property="status.key" />
                      </td>
                      <logic:notEmpty name="searchIncidentForm" property="flightnum">
                        <td>
                          <bean:write name="searchIncidentForm" property="flightnum" />
                        </td>
                      </logic:notEmpty>
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
                    <td colspan="<logic:notEmpty name="searchIncidentForm" property="flightnum">9</logic:notEmpty> <logic:empty name="searchIncidentForm" property="flightnum">8</logic:empty>">
                    </logic:present>
                    
                    
                    <logic:present name="ohd" scope="request">
                      <tr>
                        <td>
                          <strong>
                            <bean:message key="colname.on_hand_report_number" />
                          </strong>
                        </td>
                        <td>
                          <strong>
                            <bean:message key="colname.ohd_create_date" />
                          </strong>
                        </td>
                        <td>
                          <strong>
                            <bean:message key="colname.bag_tag_number" />
                          </strong>
                        </td>
                        <td>
                          <strong>
                            <bean:message key="header.status" />
                          </strong>
                        </td>
                        <td>
                          <strong>
                            <bean:message key="colname.color" />
                          </strong>
                        </td>
                        <td>
                          <strong>
                            <bean:message key="colname.bagtype" />
                          </strong>
                        </td>
                        <td>
                          <strong>
                            <bean:message key="header.companyCode" />
                          </strong>
                        </td>
                        <td>
                          <strong>
                            <bean:message key="colname.holding_station" />
                          </strong>
                        </td>
                        <td>
                          <strong>
                            <bean:message key="colname.name" />
                          </strong>
                        </td>
                        <td>
                          <strong>
                            <bean:message key="colname.requestOhd" />
                          </strong>
                        </td>
                        <td>
                          <strong>
                            <bean:message key="colname.forwardOhd" />
                          </strong>
                        </td>
                      </tr>
                      <logic:iterate id="ohd" name="resultlist" type="com.bagnet.nettracer.tracing.db.OHD">
                        <tr>
                          <td>
                            <A HREF="addOnHandBag.do?ohd_ID=<bean:write name="ohd" property="OHD_ID"/>"><bean:write name="ohd" property="OHD_ID" /></a>
                          </td>
                          <td>
                            <bean:write name="ohd" property="displaydate" />
                          </td>
                          <td>
                            <bean:write name="ohd" property="claimnum" />
                            &nbsp;
                          </td>
                          <td>
                            <bean:message name="ohd" property="status.key" />
                          </td>
                          <td>
                            <logic:empty name="ohd" property="color">
                              &nbsp;
                            </logic:empty>
                            <bean:write name="ohd" property="color" />
                          </td>
                          <td>
                            <logic:empty name="ohd" property="type">
                              &nbsp;
                            </logic:empty>
                            <bean:write name="ohd" property="type" />
                          </td>
                          <td>
                            <bean:write name="ohd" property="holdingStation.company.companyCode_ID" />
                          </td>
                          <td>
                            <bean:write name="ohd" property="holdingStation.stationcode" />
                          </td>
                          <td>
                            <logic:notEmpty name="ohd" property="passenger">
                              <logic:notEmpty name="ohd" property="passenger.lastname">
                                <bean:write name="ohd" property="passenger.lastname" />
                                ,
                                <bean:write name="ohd" property="passenger.firstname" />
                                &nbsp;
                                <bean:write name="ohd" property="passenger.middlename" />
                                &nbsp;
                              </logic:notEmpty>
                              <logic:empty name="ohd" property="passenger.lastname">
                                &nbsp;
                              </logic:empty>
                            </logic:notEmpty>
                            <logic:empty name="ohd" property="passenger">
                              &nbsp;
                            </logic:empty>
                          </td>
                          <logic:equal name="ohd" property="status.status_ID" value="<%= "" + TracingConstants.OHD_STATUS_OPEN %>">
                            <td>
<%
                              if (a.getStation().getStation_ID() != ((OHD)ohd).getHoldingStation().getStation_ID()) {
%>
                                <A HREF="request_on_hand.do?ohd_ID=<bean:write name="ohd" property="OHD_ID"/>"><bean:message key="request" /></a>
<%
                              } else {
%>
                                &nbsp;
<%
                              }
%>
                            </td>
                            <td>
<%
                              if (a.getStation().getStation_ID() == ((OHD)ohd).getHoldingStation().getStation_ID()) {
%>
                                <A HREF="forward_on_hand.do?ohd_ID=<bean:write name="ohd" property="OHD_ID"/>"><bean:message key="forward" /></a>
<%
                              } else {
%>
                                &nbsp;
<%
                              }
%>
                            </td>
                          </logic:equal>
                          <logic:notEqual name="ohd" property="status.status_ID" value="<%= "" + TracingConstants.OHD_STATUS_OPEN %>">
                            <td>
                              &nbsp;
                            </td>
                            <td>
                              &nbsp;
                            </td>
                          </logic:notEqual>
                        </tr>
                      </logic:iterate>
                      
                      <tr>
                        <td colspan="11">
                        </logic:present>
                        
                        <jsp:include page="/pages/includes/pagination_incl.jsp" />
                        
                      </td>
                    </tr>
                  </table>
                  <script language=javascript>
                    
  document.location.href="#result";

                  </script>
                </logic:present>
              </html:form>
