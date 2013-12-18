<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>

<%
  Agent a = (Agent)session.getAttribute("user");
  String cssFormClass = "form2";
%>

      <%@page import="com.bagnet.nettracer.tracing.utils.TracerProperties"%>
      <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
        <% if (TracerProperties.isTrue(a.getCompanycode_ID(),TracerProperties.RESERVATION_BY_BAGTAG)) { %>
          <tr>
            <td colspan="3"><strong><bean:message key="please_enter_one" /></strong><br />&nbsp;</td>
          </tr>
          <tr>
            <td width="25%"><bean:message key="colname.bag_tag_number" />:</td>
            <td><html:text property="bagTagNumber" size="15" maxlength="12" styleClass="textfield"/>
            	
              <% if (TracerProperties.isTrue(a.getCompanycode_ID(),TracerProperties.RESERVATION_POPULATION_SEARCH) && request.getAttribute("lostdelay") != null) { %>
                &nbsp;<html:submit property="prepopSearch" styleId="button" onclick="buttonSelected = 'prepopSearch'">
                  <bean:message key="button.prepopulate.search" />
                </html:submit>
              <% } %>
            </td>
          </tr>
        <% } %>
          
        <tr>
          <td><bean:message key="colname.recordlocator" />:</td>
          <td><html:text property="recordlocator" size="15" styleClass="textfield" value="" maxlength="6"/></td>
        </tr>
        <tr>
          <td align="center" valign="top" colspan="12"><html:submit
            property="doprepopulate1" styleId="button" onclick="buttonSelected = 'prepopulate'">
            <bean:message key="button.populate" />
          </html:submit> <html:submit property="skip_prepopulate" styleId="button"  onclick="buttonSelected = null">
            <bean:message key="button.skip_populate"/>
          </html:submit></td>
        </tr>
      </table>
      
      <logic:present name="pnrlist" scope="request">
      	
      	<h1 class="green">
          <bean:message key="pnr.prepop.matched.incresults" />
        </h1>
        <bean:message key="pnr.prepop.matched.message" />
        <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
          <tr>
          		  <td><b><bean:message key="colname.incident_num" /></b></td>
                  <td><b><bean:message key="colname.report_type" /></b></td>
                  <td><b><bean:message key="colname.incident_create_date" /></b></td>
                  <td><b><bean:message key="colname.companycreated" /></b></td>
                  <td><b><bean:message key="colname.stationcreated" /></b></td>
                  <td><b><bean:message key="colname.stationassigned" /></b></td>
                  <td><b><bean:message key="header.status" /></b></td>
			      <td><b><bean:message key="colname.color" /></b></td>
			      <td><b><bean:message key="colname.bagtype" /></b></td>
                  <td><b><bean:message key="colname.claimnum" /></b></td>
                  <td><b><bean:message key="colname.pass_name" /></b></td>
                  <%if(a.getStation().getCompany().getVariable().getWt_enabled()==1){%>
                  <td><b><bean:message key="colname.worldtracer_id"/></b></td>
                  <%}%>
          </tr>
          <logic:iterate id="pnrInc" name="pnrlist" type="com.bagnet.nettracer.tracing.db.Incident">
            <bean:define id="items" name="pnrInc" property="itemlist" />
                  <bean:define id="claimchecks" name="pnrInc" property="claimcheck_list" />
                  <bean:define id="itinerary" name="pnrInc" property="itinerary_list" />
                  <bean:define id="passengers" name="pnrInc" property="passenger_list" />
                  <tr>
                    <td>
                      <a href='searchIncident.do?incident=<bean:write name="pnrInc" property="incident_ID"/>'><bean:write name="pnrInc" property="incident_ID" /></a>
                    </td>
                    <td>
                      <bean:message name="pnrInc" property="itemtype.key" />
                    </td>
                    <td>
                      <bean:write name="pnrInc" property="displaydate" />
                    </td>
                    <td>
                      <bean:write name="pnrInc" property="stationcreated.company.companyCode_ID" />
                    </td>
                    <td>
                      <bean:write name="pnrInc" property="stationcreated.stationcode" />
                    </td>
                    <td>
                      <bean:write name="pnrInc" property="stationassigned.stationcode" />
                    </td>
                    <td>
                      <bean:message name="pnrInc" property="status.key" />
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
                    <%
                	if(a.getStation().getCompany().getVariable().getWt_enabled()==1){
                    %>
                    <td>
                     	<logic:empty name="pnrInc" property="wt_id">
                            &nbsp;
                        </logic:empty>
                        <logic:notEmpty name="pnrInc" property="wt_id">
                        	 <bean:write name="pnrInc" property="wt_id" />
                        </logic:notEmpty>
                    </td>
                    <%
                	}
                    %>
                  </tr>
          </logic:iterate>
         <tr>
			<td align="center" valign="top" colspan="12"><html:submit
					property="pnrpopulate" styleId="button"
					onclick="buttonSelected = 'pnrpopulate'">
					<bean:message key="button.populate.continue" />
				</html:submit> 
			</td>
		</tr>
        </table>
      </logic:present>