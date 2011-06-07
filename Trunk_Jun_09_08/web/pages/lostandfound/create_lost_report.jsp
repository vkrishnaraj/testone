<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.DateUtils" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Status" %>
<%@ page import="java.util.ArrayList" %>
<%
	Agent a = (Agent)session.getAttribute("user");
 	String cssFormClass = "form2_dam";
%>


<%@page import="com.bagnet.nettracer.tracing.db.salvage.SalvageItem"%><SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	

</SCRIPT>
<jsp:include page="/pages/includes/validation_search.jsp" />
<html:form focus="lost.id" action="create_lost_report.do" method="post" onsubmit="return validateSearch(this);">
<input type="hidden" name="delete_these_elements" value="" />
	<tr>
        <td colspan="3" id="pageheadercell">
          <div id="pageheaderleft">
            <h1>
            	<bean:message key="header.lost.report" />
            </h1>
          </div>
          <div id="pageheaderright">
            <table id="pageheaderright">
              <tr>
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
         		<logic:present name="reportfile" scope="request">
            		<center><a href="#" onclick="openReportWindow('reporting?outputtype=<%= request.getAttribute("outputtype") %>&reportfile=<bean:write name="reportfile" scope="request"/>','report',800,600);return false;"><b><bean:message key="link.view_report" /></b></a></center>
           		</logic:present>
         		<logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
				<h1 class="green">
					<bean:message key="header.report.information" />
				</h1>
				<span class="reqfield">*</span>
				<bean:message key="message.required" /> 
				<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0" >
					<tr>
						<td>
							<bean:message key="colname.lf.report.id" />
							<br/>
							<html:text name="lostReportForm" property="lost.id" disabled="true" styleClass="disabledtextbox" />
						</td>
						<td>
							<bean:message key="colname.lf.created.date" />
							<br/>
							<html:text name="lostReportForm" property="disOpenDate" disabled="true" styleClass="disabledtextbox" />
						</td>
						<td>
							<bean:message key="colname.lf.created.agent" />
							<br/>
							<input type="text" value="<%=a.getUsername() %>" disabled="true" class="disabledtextbox" />
						</td>
					</tr>
				</table>
				<br/>
				<h1 class="green">
		        	<bean:message key="header.contact.information" />
		        </h1>
    			<span class="reqfield">*</span>
   				<bean:message key="message.required" /> 
         		<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
         			<tr>
			            <td nowrap colspan="2" >
			              <bean:message key="colname.last_name.req" />
			              <br>
			              <html:text name="lostReportForm" property="lost.client.lastName" size="20" maxlength="20" styleClass="textfield" />
			            </td>
			            <td nowrap colspan="2">
			              <bean:message key="colname.first_name.req" />
			              <br>
			              <html:text name="lostReportForm" property="lost.client.firstName" size="20" maxlength="20" styleClass="textfield" />
			            </td>
			            <td>
			              <bean:message key="colname.mid_initial" />
			              <br>
			              <html:text name="lostReportForm" property="lost.client.middleName" size="1" maxlength="1" styleClass="textfield" />
			            </td>
			        </tr>
	            	<tr>
		                <td colspan=2>
		                  <bean:message key="colname.street_addr1.req" />
		                  <br>
		                  <html:text name="lostReportForm" property="lost.client.address.address1" size="45" maxlength="50" styleClass="textfield" />
		                </td>
		                <td colspan=3>
		                  <bean:message key="colname.street_addr2" />
		                  <br>
		                  <html:text name="lostReportForm" property="lost.client.address.address2" size="35" maxlength="50" styleClass="textfield" />
		                </td>
		              </tr>
		              <tr>
		                <td>
		                  <bean:message key="colname.city.req" />
		                  <br>
		                  <html:text name="lostReportForm" property="lost.client.address.city" size="10" maxlength="50" styleClass="textfield" />
		                </td>
		                <td>
		                  <bean:message key="colname.state" />
		                  <br />
		                  <html:select name="lostReportForm" property="lost.client.address.state" styleClass="dropdown" onchange="" >
		                  	<html:option value="">
			                    <bean:message key="select.none" />
		                    </html:option>
			                <html:options collection="statelist" property="value" labelProperty="label" />
		                  </html:select>
		                </td>
		                <td>
		                  <bean:message key="colname.province" />
		                  <br />
		                  <html:text name="lostReportForm" property="lost.client.address.province" size="10" maxlength="100" styleClass="textfield" />
		                </td>
		                <td>
		                  <bean:message key="colname.zip.req" />
		                  <br>
		                  <html:text name="lostReportForm" property="lost.client.address.zip" size="11" maxlength="11" styleClass="textfield" />
		                </td>
		                <td>
		                  <bean:message key="colname.country" />
		                  <br>
		                  <html:select name="lostReportForm" property="lost.client.address.country" styleClass="dropdown" onchange="checkstate(this,this.form,'state', 'province');">
		                    <html:option value="">
		                      <bean:message key="select.none" />
		                    </html:option>
		                    <html:options name="OnHandForm" collection="countrylist" property="value" labelProperty="label" />
		                  </html:select>
		                </td>
		              </tr>
		              <tr>
		              	<td colspan="2">
		              		<bean:message key="colname.lf.primary.phone" />
		              		<br/>
		              		<html:text name="lostReportForm" property="primaryPhoneNumber" size="15" maxlength="25" styleClass="textfield" />
		              		<html:select name="lostReportForm" property="primaryNumberType" styleClass="dropdown" >
		              			<html:option value=""><bean:message key="option.lf.please.select" /></html:option>
		              			<html:option value="3"><bean:message key="option.lf.home" /></html:option>
		              			<html:option value="4"><bean:message key="option.lf.mobile" /></html:option>
		              			<html:option value="5"><bean:message key="option.lf.work" /></html:option>
		              			<html:option value="6"><bean:message key="option.lf.other" /></html:option>
		              		</html:select>
		              	</td>
		              	<td colspan="3">
		              		<bean:message key="colname.lf.secondary.phone" />
		              		<br/>
		              		<html:text name="lostReportForm" property="secondaryPhoneNumber" size="15" maxlength="25" styleClass="textfield" />
		              		<html:select name="lostReportForm" property="secondaryNumberType" styleClass="dropdown" >
		              			<html:option value=""><bean:message key="option.lf.please.select" /></html:option>
		              			<html:option value="3"><bean:message key="option.lf.home" /></html:option>
		              			<html:option value="4"><bean:message key="option.lf.mobile" /></html:option>
		              			<html:option value="5"><bean:message key="option.lf.work" /></html:option>
		              			<html:option value="6"><bean:message key="option.lf.other" /></html:option>
		              		</html:select>
		              	</td>
		              </tr>
		              <tr>
		              	<td colspan="2" >
		              		<bean:message key="colname.lf.email" />
		              		<br />
		              		<html:text name="lostReportForm" property="lost.client.email" size="35" maxlength="100" styleClass="textfield" />
		              	</td>
		              	<td colspan="3" >
		              		<bean:message key="colname.lf.confirm.email" />
		              		<br />
		              		<html:text name="lostReportForm" property="lost.client.email" size="35" maxlength="100" styleClass="textfield" />
		              	</td>
		              </tr>
				</table>
				<br/>
				<h1 class="green">
					<bean:message key="header.item.information" />
				</h1>
				<span class="reqfield">*</span>
				<bean:message key="message.required" /> 
         		<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
         			<logic:iterate indexId="i" id="item" name="lostReportForm" property="lost.items" type="com.bagnet.nettracer.tracing.db.lf.LFItem" >
         				<tr>
	         				<td>
	         					<bean:message key="colname.lf.brand" />
	         					<br>
	         					<input type="text" name="item[<%=i %>].brand" class="textfield" />
	         				</td>
	         				<td>
	         					<bean:message key="colname.lf.serial" />
	         					<br>
	         					<input type="text" name="item[<%=i %>].serialNumber" class="textfield" />
	         				</td>
	         				<td>
	         					<bean:message key="colname.lf.status" />
	         					<br>
	         					<select name="item[<%=i %>].status" class="dropdown" >
	         						<option value=""><bean:message key="option.lf.please.select" /></option>
	         						<%
	         							ArrayList statusList = (ArrayList) request.getSession().getAttribute("lfstatuslist");
	         							Status status;
	         							for (int j = 0; j < statusList.size(); ++j) {
	         								status = (Status) statusList.get(j);
	         						%>
	         								<option value="<%=status.getStatus_ID() %>"><%=status.getDescription() %></option>
	         						<%	
	         							}
         							%>
	         					</select>
	         				</td>
	         				<td>
	         					<bean:message key="colname.lf.disposition" />
	         					<br>
	         					<select name="item[<%=i %>].status" class="dropdown" >
	         						<option value=""><bean:message key="option.lf.please.select" /></option>
	         						<%
	         							ArrayList dispositionList = (ArrayList) request.getSession().getAttribute("lfdispositionlist");
	         							Status disposition;
	         							for (int j = 0; j < dispositionList.size(); ++j) {
	         								disposition = (Status) dispositionList.get(j);
	         						%>
	         								<option value="<%=disposition.getStatus_ID() %>"><%=disposition.getDescription() %></option>
	         						<%	
	         							}
         							%>
	         					</select>
	         				</td>
	         			</tr>
	         			<tr>
	         				<td>
	         					<bean:message key="colname.lf.category" />
	         					<br>
	         					<select name="item[<%=i %>].category" class="dropdown" >
	         						<option value=""><bean:message key="option.lf.please.select" /></option>
	         					</select>
	         				</td>
	         				<td>
	         					<bean:message key="colname.lf.subcategory" />
	         					<br>
	         					<select name="item[<%=i %>].subCategory" class="dropdown" >
	         						<option value=""><bean:message key="option.lf.please.select" /></option>
	         					</select>
	         				</td>
	         				<td colspan=2>
	         					<bean:message key="colname.lf.color" />
	         					<br>
	         					<select name="item[<%=i %>].lost.color" class="dropdown" >
	         						<option value=""><bean:message key="option.lf.please.select" /></option>
	         					</select>
	         				</td>
	         			</tr>
	         			<tr>
	         				<td colspan=4>
	         					<bean:message key="colname.lf.description" />
	         					<br>
	         					<textarea name="item[<%=i %>].description" cols="80" rows="3" class="textfield" ></textarea>
	         				</td>
	         			</tr>
         			</logic:iterate>
         		</table>
         		<br/>
				<h1 class="green">
					<bean:message key="header.trip.information" />
				</h1>
				<span class="reqfield">*</span>
				<bean:message key="message.required" /> 
				<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0" >
				</table>
				<br/>
				<center>
					<html:submit property="save" styleId="button">
						<bean:message key="button.save" />
					</html:submit>
				</center>
   			</div>
   		</td>
   	</tr>
</html:form>
