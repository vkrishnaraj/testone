<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.DateUtils" %>
<%@ page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%
  Agent a = (Agent)session.getAttribute("user");

  boolean ntUser = PropertyBMO.isTrue("nt.user");
%>
  
  <script src="deployment/main/js/date.js"></script>
  <script src="deployment/main/js/AnchorPosition.js"></script>
  <script src="deployment/main/js/PopupWindow.js"></script>
  <script src="deployment/main/js/popcalendar.js"></script>
  <script>
    
	var cal1xx = new CalendarPopup();	
    
	function goprev() {
	  o = document.searchClaimForm;
	  o.prevpage.value = "1";
	  o.pagination.value="1";
	  o.submit();
	}
	
	function gonext() {
	  o = document.searchClaimForm;
	  o.nextpage.value="1";
	  o.pagination.value="1";
	  o.submit();
	}
	
	function gopage(i) {
		  o = document.searchClaimForm;
		  o.currpage.value = i;
		  o.pagination.value="1";
		  o.submit();
	}
	
	function updatePagination() {
	    return true;
	}
	
  </script>
  
  <jsp:include page="/pages/includes/validation_search.jsp" />
  <html:form focus="claimId" action="search_claim.do" method="post" onsubmit="fillzero(this.claimId, 13); return validateSearch(this);">
  
  <jsp:include page="/pages/includes/taskmanager_header.jsp" />
    
    <tr>
      
      <td id="middlecolumn">
        
        <div id="maincontent">
          <h1 class="green">
            <bean:message key="header.claim.details" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
            <table class="form2" cellspacing="0" cellpadding="0">
            	<tr>
            		<td>
            			<bean:message key="colname.claim.id" />
            			<br />
            			<logic:equal name="searchClaimForm" property="claimId" value="0" >
            				<html:text name="searchClaimForm" property="claimId" value="" size="5" styleClass="textfield" styleId="sId" onblur="fillzero(this.claimId,13);" />
            			</logic:equal>
            			<logic:greaterThan name="searchClaimForm" property="claimId" value="0" >
            				<html:text name="searchClaimForm" property="claimId" size="5" styleClass="textfield" styleId="sId" onblur="fillzero(this.claimId,13);" />
            			</logic:greaterThan>
            		</td>
            		<td>
            			<bean:message key="colname.incident.id" />
            			<br />
            			<html:text name="searchClaimForm" property="incidentId" size="13" styleClass="textfield" styleId="iId" onblur="fillzero(this.incidentId,13);" />
            		</td>
            		<td nowrap>
		                <bean:message key="colname.claim.date.range" />
		                (<%= a.getDateformat().getFormat() %>)
		                <br>
		                <html:text property="s_createtime" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.searchClaimForm.s_createtime,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">&nbsp;-
		                <html:text property="e_createtime" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.searchClaimForm.e_createtime,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;">
	                </td>
            	</tr>
              </table>
              <br />
			  <h1 class="green">
              	<bean:message key="header.claimant.details" />
				<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_claim_payment.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
              </h1>
              <table class="form2" cellspacing="0" cellpadding="0" >
				<tr>
					<td colspan=2>
						<bean:message key="colname.last_name" />
						<br/>
						<html:text name="searchClaimForm" property="lastName" size="20" maxlength="20" styleClass="textfield" />
					</td>
					<td colspan=2>
						<bean:message key="colname.first_name" />
						<br/>
						<html:text name="searchClaimForm" property="firstName" size="20" maxlength="20" styleClass="textfield" />
					</td>
					<td>
		              <bean:message key="colname.mid_initial" />
		              <br>
		              <html:text name="searchClaimForm" property="middleName" size="1" maxlength="1" styleClass="textfield" />
		            </td>
				</tr>
              <tr>
                <td colspan=2>
                  <bean:message key="colname.street_addr1" />
                  <br>
                  <html:text name="searchClaimForm" property="address1" size="40" maxlength="50" styleClass="textfield" />
                </td>
                <td colspan=3>
                  <bean:message key="colname.street_addr2" />
                  <br>
                  <html:text name="searchClaimForm" property="address2" size="35" maxlength="50" styleClass="textfield" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.city" />
                  <br>
                  <html:text name="searchClaimForm" property="city" size="15" maxlength="50" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.state" />
                  <br />
                  <logic:equal name="searchClaimForm" property="country" value="US">
                    <html:select name="searchClaimForm" property="state" styleClass="dropdown" onchange="updateCountryUS(this, this.form, 'country', 'province');" >
                      <html:option value="">
                        <bean:message key="select.none" />
                      </html:option>
                      <html:options collection="statelist" property="value" labelProperty="label" />
                    </html:select>
                  </logic:equal>
                  <logic:equal name="searchClaimForm" property="country" value="">
                    <html:select name="searchClaimForm" property="state" styleClass="dropdown" onchange="updateCountryUS(this, this.form, 'country', 'province');" >
                      <html:option value="">
                        <bean:message key="select.none" />
                      </html:option>
                      <html:options collection="statelist" property="value" labelProperty="label" />
                    </html:select>
                  </logic:equal>
                  <logic:notEqual name="searchClaimForm" property="country" value="">
                    <logic:notEqual name="searchClaimForm" property="country" value="US">
                      <html:select name="searchClaimForm" property="state" styleClass="dropdown" disabled="true" onchange="updateCountryUS(this, this.form, 'country', 'province');" >
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
                  <br />
                      <logic:equal name="searchClaimForm" property="country" value="US">
                  		<html:text name="searchClaimForm" property="province" size="15" maxlength="100" styleClass="disabledtextfield" disabled="true" />
                      </logic:equal>
                      <logic:equal name="searchClaimForm" property="country" value="">
                  <html:text name="searchClaimForm" property="province" size="15" maxlength="100" styleClass="textfield" />
                      </logic:equal>
                      <logic:notEqual name="searchClaimForm" property="country" value="">
                        <logic:notEqual name="searchClaimForm" property="country" value="US">
                  <html:text name="searchClaimForm" property="province" size="15" maxlength="100" styleClass="textfield" />
                         </logic:notEqual>
                      </logic:notEqual>
                </td>
                <td>
                  <bean:message key="colname.zip.req" />
                  <br>
                  <html:text name="searchClaimForm" property="zip" size="15" maxlength="11" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.country" />
                  <br>
                  <html:select name="searchClaimForm" property="country" styleClass="dropdown" onchange="checkstate(this,this.form,'state', 'province');">
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
             		<br />
             		<html:text name="searchClaimForm" property="phone" size="15" maxlength="25" styleClass="textfield" />
             	</td>
             	<td colspan="2">
             		<bean:message key="colname.email" />
             		<br />
             		<html:text name="searchClaimForm" property="emailAddress" size="35" maxlength="100" styleClass="textfield" />
             	</td>
             	<td colspan="2">
             		<bean:message key="colname.dob" />
             		(<%= a.getDateformat().getFormat() %>)
             		<br />
             		<html:text property="startDateOfBirth" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar3" name="calendar3" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.searchClaimForm.startDateOfBirth,'calendar3','<%= a.getDateformat().getFormat() %>'); return false;">&nbsp;-
             		<html:text property="endDateOfBirth" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar4" name="calendar4" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.searchClaimForm.endDateOfBirth,'calendar4','<%= a.getDateformat().getFormat() %>'); return false;">
             	</td>
             </tr>
              <tr>
              <td colspan="5">
              	<center>
              		<html:submit property="search" styleId="button" >
              			<bean:message key="button.retrieve" />
              		</html:submit>
              		&nbsp;&nbsp;
              		<html:reset styleId="button">
	                  	<bean:message key="button.reset" />
	                </html:reset>
              	</center>
              	</td>
              	</tr>
              </table>
              <br />
              <h1 class="green">
                <bean:message key="header.search_result" />
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#Retrieve.htm#Retrieve_Reports');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
              </h1>
              <table class="form2" cellpadding="0" cellspacing="0" >
              	<tr>
                  <td class="header">
                    <bean:message key="colname.claim.id" />
                  </td>
                  <td class="header">
                    <bean:message key="colname.incident.id" />
                  </td>
                  <td class="header">
                      <bean:message key="colname.claim.date.created" />
                  </td>
                  <td class="header">
                      <bean:message key="colname.claimant.name" />
                  </td>
                </tr>
                <logic:iterate id="results" name="resultList" type="aero.nettracer.fs.model.FsClaim">
                	<tr>
                		<td>
                			<a href="claim_resolution.do?claimId=<%=results.getId() %>"><bean:write name="results" property="id" /></a>
                		</td>
                		<% if (ntUser) { %>
                		<td>
                			<logic:notEmpty name="results" property="ntIncidentId" >
                				<a href='searchIncident.do?incident=<bean:write name="results" property="ntIncidentId" />' >
                					<bean:write name="results" property="ntIncidentId" />
                				</a>
                			</logic:notEmpty>
                			<logic:empty name="results" property="ntIncidentId" >
                				<bean:message key="colname.na" />
                			</logic:empty>
                		</td>
                		<% } else { %>
                		<td>
                			<logic:notEmpty name="results" property="incident.airlineIncidentId" >
                				<bean:write name="results" property="incident.airlineIncidentId" />
                			</logic:notEmpty>
                			<logic:empty name="results" property="incident.airlineIncidentId" >
                				<bean:message key="colname.na" />
                			</logic:empty>
                		</td>
                		<% } %>
                		<td>
                			<%= results.getDisClaimDate(a.getDateformat().getFormat()) %>
                		</td>
                		<td>
                			<%= results.getDisClaimantName() %>
                		</td>
                	</tr>
                </logic:iterate>
               	<tr>
                	<td colspan="11">
                  		<jsp:include page="/pages/includes/pagination_incl.jsp" />
                	</td>
              	</tr>
              </table>
              <script>
					document.location.href="#result";
			  </script>
          </div>
          </td>
          </tr>
          </html:form>
