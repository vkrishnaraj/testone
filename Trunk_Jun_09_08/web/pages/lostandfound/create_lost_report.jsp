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
<%@ page import="org.apache.struts.util.LabelValueBean" %>
<%@ page import="com.bagnet.nettracer.tracing.db.lf.LFCategory" %>
<%@ page import="com.bagnet.nettracer.tracing.db.lf.LFSubCategory" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%
	Agent a = (Agent)session.getAttribute("user");
 	String cssFormClass = "form2_dam";
 	
 	ArrayList categoryList = (ArrayList) request.getSession().getAttribute("lfcategorylist");
%>


<%@page import="com.bagnet.nettracer.tracing.db.salvage.SalvageItem"%><SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();
	
	function getCategories() {
		var categories;
		<% 
			LFCategory currCategory;
			LFSubCategory subCategory;
			String categoryListJson = "{";
			for (int i = 0; i < categoryList.size(); ++i) {
				currCategory = (LFCategory) categoryList.get(i);
				categoryListJson += "\"" + currCategory.getDescription() + "\": [";
				Iterator iterator = currCategory.getSubcategories().iterator();
				while (iterator.hasNext()) {
					subCategory = (LFSubCategory) iterator.next();
					categoryListJson += "\"" + subCategory.getDescription() + "\"";
					if (iterator.hasNext()) {
						categoryListJson += ",";
					}
				}
				categoryListJson += "],";				
		   	}
			categoryListJson = categoryListJson.substring(0,categoryListJson.length() - 1) + "}";
	   	%>
	   	categories = <%=categoryListJson %>;
	   	alert(categories.Phone[0]);
	   	
	    
	   	var subCategoryArray = new Array(<%=categoryList.size()%>);
	   	<%
	   	   for (int i = 0; i < categoryList.size(); ++i) {
	   		   currCategory = (LFCategory) categoryList.get(i); %>
	   		   subCategoryArray[i] = new Array(<%=currCategory.getSubcategories().size() %>);
	   		   	   	   	
	   	<% } %>
	}
	
	function fieldChanged(field) {
		var state = document.getElementById('state');
		var province = document.getElementById('province');
		var country = document.getElementById('country');
	
		if (field == "state") {
			stateChanged(state, province, country);
		} else if (field == "province") {
			provinceChanged(state, province, country);
		} else if (field == "country") {
			countryChanged(state, province, country);
		}
	}
	
	function stateChanged(state, province, country) {
		if (state.value == "") {
			province.disabled = false;	
			province.className = "textfield";
		} else {
			province.value = "";
			province.disabled = true;
			province.className = "disabledtextfield";
			country.value = "US";
		}
	}
	
	function provinceChanged(state, province, country) {
		if (province.value == "") {
			state.disabled = false;
		} else {
			state.value = "";
			state.disabled = true;
		}
	}
	
	function countryChanged(state, province, country) {
		if (country.value == "") {
			state.disabled = false;
			province.disabled = false;
			province.className = "textfield";
		} else if (country.value == "US") {
			state.disabled = false;
			province.value = "";
			province.disabled = true;
			province.className = "disabledtextfield";
		} else {
			state.value = "";
			state.disabled = true;
			province.disabled = false;
			province.className = "textfield";
		}
	}
	
	function updateSubCategories() {
		
	}

</SCRIPT>
<jsp:include page="/pages/includes/validation_search.jsp" />
<html:form focus="lost.id" action="create_lost_report.do" method="post" onsubmit="return validateLfReportForm(this);">
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
							<html:text name="lostReportForm" property="lost.id" disabled="true" styleClass="disabledtextfield" />
						</td>
						<td>
							<bean:message key="colname.lf.created.date" />
							<br/>
							<html:text name="lostReportForm" property="disOpenDate" disabled="true" styleClass="disabledtextfield" />
						</td>
						<td>
							<bean:message key="colname.lf.created.agent" />
							<br/>
							<input type="text" value="<%=a.getUsername() %>" disabled class="disabledtextfield" />
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
         				<td>
         					<bean:message key="colname.lf.vantive.number" />
         					<br>
         					<html:text name="lostReportForm" property="lost.client.vantiveNumber" size="10" styleClass="textfield" />
         				</td>
			            <td>
			              <bean:message key="colname.last_name.req" />&nbsp;<span class="reqfield">*</span>
			              <br>
			              <html:text name="lostReportForm" property="lost.client.lastName" size="20" maxlength="20" styleClass="textfield" />
			            </td>
			            <td nowrap colspan="2">
			              <bean:message key="colname.first_name.req" />&nbsp;<span class="reqfield">*</span>
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
		                  <bean:message key="colname.street_addr1.req" />&nbsp;<span class="reqfield">*</span>
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
		                  <bean:message key="colname.city.req" />&nbsp;<span class="reqfield">*</span>
		                  <br>
		                  <html:text name="lostReportForm" property="lost.client.address.city" size="10" maxlength="50" styleClass="textfield" />
		                </td>
		                <td>
		                  <bean:message key="colname.state" />
		                  <br />
		                  <html:select name="lostReportForm" property="lost.client.address.state" styleId="state" styleClass="dropdown" onchange="fieldChanged('state');" >
		                  	<html:option value="">
			                    <bean:message key="select.none" />
		                    </html:option>
			                <html:options collection="statelist" property="value" labelProperty="label" />
		                  </html:select>
		                </td>
		                <td>
		                  <bean:message key="colname.province" />
		                  <br />
		                  <html:text name="lostReportForm" property="lost.client.address.province" size="10" maxlength="100" styleId="province" styleClass="textfield" onchange="fieldChanged('province');" />
		                </td>
		                <td>
		                  <bean:message key="colname.zip.req" />
		                  <br>
		                  <html:text name="lostReportForm" property="lost.client.address.zip" size="11" maxlength="11" styleClass="textfield" />
		                </td>
		                <td>
		                  <bean:message key="colname.country" />&nbsp;<span class="reqfield">*</span>
		                  <br>
		                  <html:select name="lostReportForm" property="lost.client.address.country" styleId="country" styleClass="dropdown" onchange="fieldChanged('country');">
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
		              		<html:text name="lostReportForm" property="lost.client.confirmEmail" size="35" maxlength="100" styleClass="textfield" />
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
	         					<bean:message key="colname.lf.status" />&nbsp;<span class="reqfield">*</span>
	         					<br>
	         					<select name="item[<%=i %>].statusId" class="dropdown" >
	         						<option value=""><bean:message key="option.lf.please.select" /></option>
	         						<%
	         							ArrayList statusList = (ArrayList) request.getSession().getAttribute("lfstatuslist");
	         							Status status;
	         							for (int j = 0; j < statusList.size(); ++j) {
	         								status = (Status) statusList.get(j);
	         						%>
	         								<option value="<%=status.getStatus_ID() %>" <% if (item.getStatus().getStatus_ID() == status.getStatus_ID()) { %>selected<% } %> ><%=status.getDescription() %></option>
	         						<%	
	         							}
         							%>
	         					</select>
	         				</td>
	         				<td>
	         					<bean:message key="colname.lf.disposition" />
	         					<br>
	         					<select name="item[<%=i %>].dispositionId" class="dropdown" >
	         						<option value=""><bean:message key="option.lf.please.select" /></option>
	         						<%
	         							ArrayList dispositionList = (ArrayList) request.getSession().getAttribute("lfdispositionlist");
	         							Status disposition;
	         							int dispositionId = -1;
	         							if (item.getDisposition() != null) {
	         								dispositionId = item.getDisposition().getStatus_ID();
	         							}
	         							
	         							for (int j = 0; j < dispositionList.size(); ++j) {
	         								disposition = (Status) dispositionList.get(j);
	         						%>
	         								<option value="<%=disposition.getStatus_ID() %>" <% if (dispositionId == disposition.getStatus_ID()) { %>selected<% } %> ><%=disposition.getDescription() %></option>
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
	         					<input type="hidden" name="changesubcategories">
	         					<select name="item[<%=i %>].category" class="dropdown" onchange="updateSubCategories();" id="categories" >
	         						<option value=""><bean:message key="option.lf.please.select" /></option>
	         						<%
	         							LFCategory category;
	         							for (int j = 0; j < categoryList.size(); ++j) {
	         								category = (LFCategory) categoryList.get(j);
	         						%>
	         								<option value="<%=category.getId() %>" ><%=category.getDescription() %></option>
	         						<%	
	         							}
         							%>
	         					</select>
	         				</td>
	         				<td>
	         					<bean:message key="colname.lf.subcategory" />
	         					<br>
	         					<div id="subcategoriesdiv" >
		         					<select name="item[<%=i %>].subCategory" class="dropdown" disabled id="subcategories" >
		         						<option value=""><bean:message key="option.lf.please.select" /></option>
		         					</select>
	         					</div>
	         				</td>
	         				<td colspan=2>
	         					<bean:message key="colname.lf.color" />
	         					<br>
	         					<select name="item[<%=i %>].color" class="dropdown" >
	         						<option value=""><bean:message key="option.lf.please.select" /></option>
	         						<%
	         							ArrayList colorList = (ArrayList) request.getSession().getAttribute("lfcolorlist");
	         							LabelValueBean color;
	         							for (int j = 0; j < colorList.size(); ++j) {
	         								color = (LabelValueBean) colorList.get(j);
	         						%>
	         								<option value="<%=color.getValue() %>" <% if (item.getColor() == color.getValue()) { %>selected<% } %> ><%=color.getLabel() %></option>
	         						<%	
	         							}
         							%>
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
					<tr>
						<td>
							<bean:message key="colname.lf.agreement.number" />
							<br>
							<html:text name="lostReportForm" property="lost.reservation.agreementNumber" size="10" styleClass="textfield" />
						</td>
						<td>
							<bean:message key="colname.lf.mva.number" />
							<br>
							<html:text name="lostReportForm" property="lost.reservation.mvaNumber" size="10" styleClass="textfield" />
						</td>
						<td>
							<bean:message key="colname.lf.rental.location" />&nbsp;<span class="reqfield">*</span>
							<br>
		            		<html:select name="lostReportForm" property="lost.reservation.pickupLocationId" styleClass="dropdown" >
		            			<html:option value=""><bean:message key="option.lf.please.select" /></html:option>
		            			<html:options collection="stationlist" property="station_ID" labelProperty="stationcode" />
		            		</html:select>
						</td>
						<td>
							<bean:message key="colname.lf.dropoff.location" />&nbsp;<span class="reqfield">*</span>
							<br>
		            		<html:select name="lostReportForm" property="lost.reservation.dropoffLocationId" styleClass="dropdown" >
		            			<html:option value=""><bean:message key="option.lf.please.select" /></html:option>
		            			<html:options collection="stationlist" property="station_ID" labelProperty="stationcode" />
		            		</html:select>
						</td>
					</tr>
					<tr>
						<td colspan=4>
							<bean:message key="colname.lf.where.did.you.leave.it" />
							<br>
							<html:text name="lostReportForm" property="lost.remarks" size="80" styleClass="textfield" />
						</td>
					</tr>
				</table>
				<br/>
				<center>
					<html:submit property="save" styleId="button">
						<bean:message key="button.save" />
					</html:submit>
				</center>
				<script>
					fieldChanged('state');
					fieldChanged('country');
					getCategories();
             	</script>
   			</div>
   		</td>
   	</tr>
</html:form>
