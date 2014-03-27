<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.forms.MaintainDeliveryCompanyForm" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Depreciation_Category" %>
<%@ page import="com.bagnet.nettracer.tracing.db.GeneralDepreciationRules" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Depreciation_Item" %>
<%@ page import="com.bagnet.nettracer.tracing.forms.ClaimDeprecCalcForm" %>
<%@ page import="com.bagnet.nettracer.tracing.bmo.CompanyBMO" %>
<%@ page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.reporting.ReportingConstants" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%
  Agent a = (Agent)session.getAttribute("user");
  ClaimDeprecCalcForm myform=(ClaimDeprecCalcForm)session.getAttribute("claimDeprecCalcForm");
  boolean ntUser = PropertyBMO.isTrue("nt.user");
  boolean ntfsUser = PropertyBMO.isTrue("ntfs.user");

	ResourceBundle bundle = ResourceBundle.getBundle(
			"com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(a.getCurrentlocale()));
%>
  
  <html:form action="claim_deprec_calc.do" method="post" enctype="multipart/form-data">
    
            <tr>
              <td colspan="3" id="navmenucell">
                <div class="menu">
                  <dl>
                  <% if (ntUser) { %>
                    	<logic:notEmpty name="claimDeprecCalcForm" property="claimDeprec.claim.ntIncident" >
                    <dd>
                      		<a href='searchIncident.do?incident=<bean:write name="claimDeprecCalcForm" property="claimDeprec.claim.ntIncident.incident_ID" />'>
                      <span class="aa">&nbsp;
                          <br />
                          &nbsp;</span>
                        <span class="bb"><bean:message key="menu.incident_info" /></span>
                        <span class="cc">&nbsp;
                          <br />
                          &nbsp;</span></a>
                    </dd>
                        </logic:notEmpty>
                   <% } %>
                    <logic:present name="editinterim" scope="request">
                      <dd>
                        <a href="#"><span class="aab">&nbsp;
                            <br />
                            &nbsp;</span>
                          <span class="bbb"><bean:message key="menu.interim_expense" /></span>
                          <span class="ccb">&nbsp;
                            <br />
                            &nbsp;</span></a>
                      </dd>
                    </logic:present>
                    <logic:notPresent name="editinterim" scope="request">


                      <%
                      if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CLAIM_SETTLEMENT, a)) {
                        if (request.getAttribute("claimSettlementExists")==null) {
                        	String incident = (String) request.getAttribute("incident");
                        	if (incident == null) { %>
                        		<logic:notEmpty name="claimDeprecCalcForm" property="claimDeprec.claim.ntIncident" >
                        			<dd>
			                          <a href='claim_settlement.do?incident_ID=<bean:write name="claimDeprecCalcForm" property="claimDeprec.claim.ntIncident.incident_ID" />'>
			                          	<span class="aa">&nbsp;<br />&nbsp;</span>
				                        <span class="bb"><bean:message key="menu.claim_settlement" /></span>
				                        <span class="cc">&nbsp;<br />&nbsp;</span>
				                      </a>
			                        </dd>
                        		</logic:notEmpty>
                        	<% } else { %>
             
                        <dd>
                          <a href='claim_settlement.do?incident_ID=<bean:write name="incident" scope="request" />'><span class="aa">&nbsp;
                              <br />
                              &nbsp;</span>
                            <span class="bb"><bean:message key="menu.claim_settlement" /></span>
                            <span class="cc">&nbsp;
                              <br />
                              &nbsp;</span></a>
                        </dd>
							<% } 
                        	} else {
%>

                    <dd>
                       <a href='claim_settlement.do?screen=1&incident_ID=<bean:write name="incident" scope="request" />'><span class="aa">&nbsp;
                          <br />
                          &nbsp;</span>
                        <span class="bb"><bean:message key="menu.claim_process" /></span>
                        <span class="cc">&nbsp;
                          <br />
                          &nbsp;</span></a>
                    </dd>
                    
                    <dd>
                      <a href='claim_settlement.do?screen=2&incident_ID=<bean:write name="incident" scope="request" />'><span class="aa">&nbsp;
                          <br />
                          &nbsp;</span>
                        <span class="bb"><bean:message key="menu.claim_customer" /></span>
                        <span class="cc">&nbsp;
                          <br />
                          &nbsp;</span></a>
                    </dd>

                    <dd>
                      <a href='claim_settlement.do?screen=3&incident_ID=<bean:write name="incident" scope="request" />'><span class="aa">&nbsp;
                          <br />
                          &nbsp;</span>
                        <span class="bb"><bean:message key="menu.claim_baggage" /></span>
                        <span class="cc">&nbsp;
                          <br />
                          &nbsp;</span></a>
                    </dd>
<%
                      }
                    }
%>
                      <dd>
                        <a href="claim_resolution.do?claimId=<bean:write name="claimDeprecCalcForm" property="claimDeprec.claim.id"/>"><span class="aa">&nbsp;
                            <br />
                            &nbsp;</span>
                          <span class="bb"><bean:message key="menu.claim_payout" /></span>
                          <span class="cc">&nbsp;
                            <br />
                            &nbsp;</span></a>
                      </dd>
                      <dd>
                      <%
                      if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_VIEW_FRAUD_RESULTS, a)) {
                      	if ((ntUser && ntfsUser) || ntfsUser) { 
                      		if (myform.getClaimDeprec().getClaim().getId() == 0) {
	                      		if (myform.getClaimDeprec().getClaim().getIncident() != null && 
	                      				myform.getClaimDeprec().getClaim().getIncident().getId() != 0) { %>
	                      
			                   <a href='fraud_results.do?incident=<bean:write name="claimDeprecCalcForm" property="claimDeprec.claim.ntIncident.incident_ID" />'><span class="aa">&nbsp;<br />&nbsp;</span>
			                   	<span class="bb"><bean:message key="menu.fraud.checks" /></span>
			                        <span class="cc">&nbsp;
			                          <br />
			                          &nbsp;</span></a>
	                      		<% } 
                      		} else { %>
		                   	<a href='fraud_results.do?claimId=<bean:write name="claimDeprecCalcForm" property="claimDeprec.claim.id" />' ><span class="aa">&nbsp;<br />&nbsp;</span>
		                   	<span class="bb"><bean:message key="menu.fraud.checks" /></span>
	                        <span class="cc">&nbsp;<br />&nbsp;</span></a>
                      	
                      		<% } %>
	                   </dd>
                      <% }
                      }
                      if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CLAIM_PRORATE, a) && ntUser) { %>
                      <logic:notEmpty name="claimDeprecCalcForm" property="claimDeprec.claim.ntIncident" >
                        <dd>
                          <a href='claim_prorate.do?incident=<bean:write name="claimDeprecCalcForm" property="claimDeprec.claim.ntIncident.incident_ID" />'><span class="aa">&nbsp;
                              <br />
                              &nbsp;</span>
                            <span class="bb"><bean:message key="menu.claim_prorate" /></span>
                            <span class="cc">&nbsp;
                              <br />
                              &nbsp;</span></a>
                        </dd>
                      </logic:notEmpty>
<%
                      }
                      %>

                    </logic:notPresent>
                    
                    <% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CLAIM_DEPREC_CALCULATOR, a) && (ntUser || ntfsUser) ) { %>
                      <dd>
                      <a href='#'><span class="aab">&nbsp;
                          <br />
                          &nbsp;</span>
                        <span class="bbb"><bean:message key="menu.claim_deprec_calc" /></span>
                        <span class="ccb">&nbsp;
                          <br />
                          &nbsp;</span></a>
                    </dd>
                    <% } %>
                  </dl>
                </div>
              </td>
            </tr>
    <tr>
      
      <td id="middlecolumn">
        <div id="maincontent">
        
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <logic:present name="saved" scope="request">
          <br>
          <center><font color=green>
            <bean:message key="deprec.calc.saved" />
          </font></center>
        </logic:present>
        <logic:present name="itemDeleted" scope="request">
          <br>
          <center><font color=green>
            <bean:message key="deprec.item.deleted" />
          </font></center>
        </logic:present>
        <br>
          <script type="text/javascript">
			var cal1xx = new CalendarPopup();	
			
			function hideshow(which){
				var field=document.getElementById(which);
				var type=document.getElementById('claimType');
				if (!field)
					return
				if (type.value==<%=TracingConstants.MONTREAL_CONVENTION%> || type.value==<%=TracingConstants.WARSAW_CONVENTION%>)
					field.style.display="block"
				else
					field.style.display="none"
			}
			
			function hide(which){
				which.style.display="none"
			}
				
			function show(which){
				which.style.display="block"
			}
			function checkValue(i, obj) {
				var fieldb = document.getElementById("dep_calc_" + i);
				var fieldc = document.getElementById("dep_value_" + i);
				if (fieldb.value == fieldc.value) {
					obj.style.backgroundColor = 'white'
				} else {
					obj.style.backgroundColor = 'yellow'
				}
			}
			function calculateThis(i) {

				var fielda = document.getElementById("dep_amount_" + i);
				var fieldb = document.getElementById("dep_calc_" + i);
				var fieldc = document.getElementById("dep_value_" + i);
				var fieldd = document.getElementById("calc" + i);
				var fieldf = document.getElementById("date_purch_"+i);
				var fieldg = document.getElementById("dep_content_"+i+"_type");
				var receipt = document.getElementById("dep_content_" + i + "_rec");
				var dateCalculated=document.getElementById("claimDeprec.dispDateCalculate");
				var coc = document.getElementById("dep_content_" + i + "_coc");
				
				
				var n = parseFloat(fielda.value);
				var nowDate;
				if(dateCalculated!=null && dateCalculated.value!=""){
					nowDate=new Date(dateCalculated.value);
				} else {
					nowDate=new Date();
				}
				purchaseDate=new Date(fieldf.value);
				var milliPerYear=1000*60*60*24*365.26;
				if(nowDate.getFullYear()==purchaseDate.getFullYear() && nowDate.getDate()==purchaseDate.getDate() && nowDate.getMonth()==purchaseDate.getMonth()  ){
					yearDiff=1;
				} else {
					yearDiff=(nowDate-purchaseDate)/milliPerYear;
					yearDiff=Math.ceil(yearDiff);
				}

				if(yearDiff<1){
					alert("<%=(String) bundle.getString("error.deprec.calculation")%>");
					yearDiff=1;
				}
				var deprecPerc=0;
				var calcVal=fielda.value;
				<% GeneralDepreciationRules rules=CompanyBMO.getDeprecRules(a.getCompanycode_ID());%>
				if(coc.checked==false){
				<logic:iterate id="category" indexId="i" scope="request" name="categories" type="com.bagnet.nettracer.tracing.db.Depreciation_Category">
					<% Depreciation_Category cat=(Depreciation_Category)category;%>
				if(fieldf.value!=""){
					if(fieldg.value=="<%=cat.getId()%>"){
						<%if(cat.getCalcMethod()==TracingConstants.FLAT_RATE){ %>
							deprecPerc=<%=cat.getFlatRate()%>*yearDiff;
							<% if(cat.getMaxDeprec()!=0) { %>
							if(deprecPerc><%=cat.getMaxDeprec()%>){
								deprecPerc=<%=cat.getMaxDeprec()%>;
							}
							<% } else { %>
							if(deprecPerc>100){
								deprecPerc=100;
							}
							<% } %>
							calcVal=fielda.value - (fielda.value * (deprecPerc/100));
						<%} else if(cat.getCalcMethod()==TracingConstants.DEFINED_RATE){ %>
							deprecPerc=0;
							if(yearDiff>=1 && yearDiff<2){
								deprecPerc=<%=cat.getFirstYear()%>
							}
							else if(yearDiff>=2 && yearDiff<3){
								deprecPerc=<%=cat.getSecondYear()%>
							} 
							else if(yearDiff>=3){
								deprecPerc=<%=cat.getThirdYear()%>
							} 
							if(yearDiff>=4){
								for(var j=0; j<(yearDiff-3); j++){
									deprecPerc+=<%=cat.getEachYear()%>
								}
							} 
							<% if(cat.getMaxDeprec()!=0) { %>
							if(deprecPerc><%=cat.getMaxDeprec()%>){
								deprecPerc=<%=cat.getMaxDeprec()%>;
							}
							<% } else { %>
							if(deprecPerc>100){
								deprecPerc=100;
							}
							<% } %>
							calcVal=fielda.value - (fielda.value * (deprecPerc/100));
						<% } %>
					}
				} 
				
				if(fieldf.value=="" && <%=rules.getNoDates()==TracingConstants.NODATE_SAME_MAX_DEPREC %>){
					if(fieldg.value=="<%=cat.getId()%>"){
					<% if(cat.getMaxDeprec()!=0) { %>
						deprecPerc=<%=cat.getMaxDeprec()%>;
					<% } else { %>
						deprecPerc=100;
					<% } %>
					calcVal=fielda.value - (fielda.value * (deprecPerc/100));
					}
				}
				</logic:iterate>
				/* General Rules Logic*/
				/* If no receipt, check calcVal for appropriate further depreciation */
				if(receipt.value=="0" || (fieldf.value=="" && <%=rules.getNoDates()==TracingConstants.NODATE_SAME_NO_RECEIPT %>)){
					var noReceiptDeprec=0;
					if(calcVal<20){
						noReceiptDeprec=<%=rules.getLessTwentyDeprec()%>
					} else if (calcVal>=20 && calcVal<150){
						noReceiptDeprec=<%=rules.getTwentyOnefiftyDeprec()%>
					} else if (calcVal>=150){
						noReceiptDeprec=<%=rules.getOnefiftyDeprec()%>
					}
					calcVal=calcVal - (fielda.value * (noReceiptDeprec/100));
					if(calcVal<0){
						calcVal=0;
					}
				}
				
				var domesPayValue=<%=PropertyBMO.getValueAsInt(PropertyBMO.DOMESIC_PAYOUT_VALUE)%>;
					if(calcVal>domesPayValue){
						calcVal=domesPayValue;
					}
				} else {
					calcVal=0;
				}
				calcVal=Number(calcVal).toFixed(2);
				fieldb.value = calcVal.toString();
				fieldc.value = fieldb.value;
				fieldd.value = calcVal.toString();
				
				
				calculateTotalValue();
				calculateTotalClaim();
			
			}

			function categoryChange(i) {
				var fieldg = document.getElementById("dep_content_"+i+"_type");
				<logic:iterate id="category" indexId="i" scope="request" name="categories" type="com.bagnet.nettracer.tracing.db.Depreciation_Category">
					<% Depreciation_Category cat=(Depreciation_Category)category;%>
					if(fieldg.value==<%=cat.getId()%> && <%=cat.isNotCoveredCoc()%>){
						document.getElementById("dep_content_" + i + "_coc").checked=true;
					} else if (fieldg.value==<%=cat.getId()%> && !<%=cat.isNotCoveredCoc()%>){
						document.getElementById("dep_content_" + i + "_coc").checked=false;
					}
				</logic:iterate>
			}
			
			function calculateValue(field) {
				var field = document.getElementById(field).value;
				field = parseFloat(field);
				if (isNaN(field)) {
					field = 0;
				}
				
				return field;
			}
			

			function calculateTotalClaim() {
				<% int j=0;%>
				<logic:iterate indexId="i" id="item" name="claimDeprecCalcForm" property="claimDeprec.itemlist">
					 b<%=j%> = calculateValue("dep_amount_<%=j%>");
				<% j++;%>
				</logic:iterate>

				<% j=0;%>
				var e=0;
				<logic:iterate indexId="i" id="item" name="claimDeprecCalcForm" property="claimDeprec.itemlist">
				 	e += b<%=j%>;
				<% j++;%>
				</logic:iterate>
				e = e.toFixed(2);
				document.getElementById('claimed').value = e;
			}

			function calculateTotalValue() {
				<% j=0;%>
				<logic:iterate indexId="i" id="item" name="claimDeprecCalcForm" property="claimDeprec.itemlist">
				var a<%=j%> = calculateValue("dep_value_<%=j%>");
				<% j++;%>
				</logic:iterate>

				<% j=0;%>
				var e=0;
				<logic:iterate indexId="i" id="item" name="claimDeprecCalcForm" property="claimDeprec.itemlist">
				 	e += a<%=j%>;
				<% j++;%>
				</logic:iterate>
				e = e.toFixed(2);
				document.getElementById('payout').value =e;
				var domesPayValue=<%=PropertyBMO.getValueAsInt(PropertyBMO.DOMESIC_PAYOUT_VALUE)%>;
				if (e > domesPayValue) {
					document.getElementById('payout').style.backgroundColor='red';
				} else {
					document.getElementById('payout').style.backgroundColor='white';
				}
			}
			
			function calculateAll(){
				var e=0;
				<logic:iterate indexId="i" id="item" name="claimDeprecCalcForm" property="claimDeprec.itemlist">
				 	calculateThis(e);
				 	e++;
				</logic:iterate>
			}

			function googleshop(index) {
				search=document.getElementById('dep_content_'+index);
				searchn=search.value.replace(" ","+");
				url="https://www.google.com/search?q="+searchn+"&tbm=shop";
				newwindow=window.open(url);
				if (window.focus) {newwindow.focus()}
				return false;
			}
				
			</script>
				<a name="poc"></a>
				<h1 class="green"><bean:message key="header.deprec.calc"/> 
				</h1>
				<table class='form2_ld' cellspacing="0" cellpadding="0">
					<tr>
						<td class="header" width="20%"><strong><bean:message key="colname.claim.id"/>:</strong></td>
						<td><logic:notEmpty name="claimDeprecCalcForm" property="claimDeprec.claim"/>
							<bean:write name="claimDeprecCalcForm" property="claimDeprec.claim.id"/>
						</td>
					</tr>
					<tr>
						<td class="header" width="20%"><strong><bean:message key="colname.incident.id"/>:</strong></td>
						<td><logic:notEmpty name="claimDeprecCalcForm" property="claimDeprec.claim.incident"/>
							<bean:write name="claimDeprecCalcForm" property="claimDeprec.claim.incident.airlineIncidentId"/>
						</td>
					</tr>
					<tr>
						<td class="header" width="20%"><strong><bean:message key="deprec.calc.claim.type"/>:</strong></td>
						<td><html:select styleClass="dropdown" styleId="claimType" name="claimDeprecCalcForm"
						 		property="claimDeprec.claimTypeId" onchange="hideshow('drop_hide_this');">
					 			<html:options collection="claimtypes" property="id" labelProperty="description" />
						</html:select></td>
					</tr>
					<tr id="drop_hide_this" style="display: none">
						<td class="header" width="10%"><strong><bean:message key="deprec.calc.total.weight"/>:</strong></td>
						<td><html:text  size="5" styleClass="textfield" name="claimDeprecCalcForm" property="claimDeprec.totalWeight"/> KG
						</td>
					</tr>

					<tr>
						<td class="header" width="10%"><strong><bean:message key="deprec.calc.date.calculation"/>: </strong></td>
						<td><html:text styleId="calculationDate"  name="claimDeprecCalcForm" property="claimDeprec.dispDateCalculate" onchange="calculateAll();"
								size="8" styleClass="textfield" /> 
								
								<img
								src="deployment/main/images/calendar/calendar_icon.gif"
								id="calendarCalc" name="calendarCalc" height="15" width="20"
								border="0" onmouseover="this.style.cursor='hand'"
								onClick="cal1xx.select2(document.claimDeprecCalcForm, 'claimDeprec.dispDateCalculate','calendarCalc','MM/dd/yyyy'); return false;"> by <%=a.getUsername() %></td>
					</tr>

					<tr>
						<td class="header" width="10%"><strong><bean:message key="deprec.calc.currency"/>: </strong></td>
						<td>
							<html:select name="claimDeprecCalcForm" property="claimDeprec.currency" styleClass="dropdown">
			                  <html:options collection="currencylist" property="currency_ID" labelProperty="id_desc" />
			                </html:select>
						</td>
					</tr>

					<tr>
						<td class="header" width="10%"><strong><bean:message key="deprec.calc.tools"/>:</strong></td>
						<td>
							<span style="float: left"> 
								<a href="http://www.imf.org/external/np/fin/data/rms_five.aspx" target="_blank" >
									<bean:message key="deprec.calc.imf.exchange.rates"/>
								</a>
							</span>
							<logic:notEmpty name="claimDeprecCalcForm" property="claimDeprec.claim">
							<span style="float: right">
								<a href="#" onclick="openReportWindow('reporting?print=<%=ReportingConstants.DEPREC_SUMMARY%>&claimId=<bean:write name="claimDeprecCalcForm" property="claimDeprec.claim.id" />&outputtype=0','LostReceipt',800,600);return false;"><img src="deployment/main/images/nettracer/icon_printrpt.gif" width="12" height="12"><bean:message key="deprec.calc.print.deprec.sum"/></a>
								
							</span>
							</logic:notEmpty>
						</td>
					</tr>

				</table>

				<br />
				<table class='form2_ld' cellspacing="0" cellpadding="0">
					<tr>
						<td class="header" width="30%"><strong><bean:message key="deprec.calc.item.description"/></strong></td>
						<td class="header" width="10%"><strong><bean:message key="deprec.calc.amount.claimed"/></strong></td>
						<td class="header" width="10%"><strong><bean:message key="deprec.calc.date.purchased"/></strong></td>
						<td class="header" width="10%"><strong><bean:message key="deprec.calc.category"/></strong></td>
						<td class="header" width="10%"><strong><bean:message key="deprec.calc.proof.ownership"/></strong></td>
						<td class="header" width="10%"><strong><bean:message key="deprec.calc.coc.covered"/></strong></td>
						<td class="header" width="10%"><strong><bean:message key="deprec.calc.calculated.value"/></strong></td>
						<td class="header" width="10%"><strong><bean:message key="deprec.calc.claim.value"/></strong></td>
						<td class="header" width="10%"><strong><bean:message key="deprec.calc.delete"/></strong></td>  
					</tr>
					<!-- TODO: Add Logic:iterate for list of claim_payout_calculation -->
					<%
							int t1 = 0;
					%>
					<logic:iterate id="deprecItem" indexId="i" name="claimDeprecCalcForm" property="claimDeprec.itemlist" type="com.bagnet.nettracer.tracing.db.Depreciation_Item">
						<tr>
							<td nowrap="nowrap">
							<% String descriptionID="dep_content_"+t1;
								String calcThis="calculateThis("+t1+");";
								String changeCat="categoryChange("+t1+"); "+calcThis;
								String amountClaimId="dep_amount_"+t1;
								String datePurchaseId="date_purch_"+t1;
								String categoryId="dep_content_"+t1+"_type";
								String proofId="dep_content_"+t1+"_rec";
								String cocId="dep_content_"+t1+"_coc";
								String calcId="dep_calc_"+t1;
								String valueId="dep_value_"+t1;
								String checkvalue="checkValue("+t1+",this)";%>
								<html:text styleId="<%=descriptionID %>" size="30" styleClass="textfield" name="deprecItem" property="description"  indexed="true" />
	
								<a onclick="googleshop(<%=t1%>);" >
									<img border=0 src="deployment/main/images/icon/barcode.png" width="16" height="16" />
								</a>
							</td>
	
							<td><html:text size="5" maxlength="200" styleClass="textfield" name="deprecItem" property="dispAmountClaimed"  indexed="true"
								styleId="<%=amountClaimId %>" onblur="<%=calcThis %>" /></td>
	
							<td nowrap="nowrap">
								<html:text styleId="<%=datePurchaseId %>"  name="deprecItem" property="dispDatePurchase"  indexed="true"
								size="8" styleClass="textfield"	onchange="<%=calcThis %>" /> 
								
								<img
								src="deployment/main/images/calendar/calendar_icon.gif"
								id="calendar<%=t1 %>" name="calendar<%=t1 %>" height="15" width="20"
								border="0" onmouseover="this.style.cursor='hand'"
								onClick="cal1xx.select2(document.claimDeprecCalcForm, '<%="deprecItem[" + i + "].dispDatePurchase"%>','calendar<%=t1 %>','MM/dd/yyyy'); return false;"></td>
	
							<td><html:select styleClass="dropdown" indexed="true" styleId="<%=categoryId %>" name="deprecItem" property="categoryId" onchange="<%=changeCat %>">
									<html:option value="0"><bean:message key="select.please_select"/></html:option>
									<html:options collection="categories" property="id" labelProperty="name" />
							</html:select></td>
							
							<td><html:select styleClass="dropdown" styleId="<%=proofId %>" name="deprecItem" property="proofOwnership"  indexed="true" onchange="<%=calcThis %>">
	
									<html:option  value="0"><bean:message key="deprec.option.no.proof"/></html:option >
									<html:option  value="1"><bean:message key="deprec.option.check"/></html:option >
									<html:option  value="2"><bean:message key="deprec.option.photo"/></html:option >
									<html:option  value="3"><bean:message key="deprec.option.appraisal"/></html:option >
									<html:option  value="4"><bean:message key="deprec.option.cc.receipt"/></html:option >
									<html:option  value="5"><bean:message key="deprec.option.receipt"/></html:option >
							</html:select></td>
							<td><input type="checkbox" id="<%=cocId %>" name="deprecItem[<%=t1 %>].notCoveredCoc"  <% if(deprecItem.isNotCoveredCoc()) {%> checked="true" <%} %>
								onclick="<%=calcThis %>" onchange="<%=calcThis %>" /></td>
	
	
							<td><html:text size="5" styleClass="textfield" name="deprecItem" property="dispCalcValue" indexed="true"
								styleId="<%=calcId %>" disabled="true" />
								<input type="hidden" id="calc<%=t1 %>" name="deprecItem[<%=i %>].calcValue" value="<bean:write name="deprecItem" property="calcValue"/>" />
								</td>
	
							<td><html:text size="5" styleClass="textfield" name="deprecItem" property="dispClaimValue"  indexed="true" onchange="calculateTotalValue()"
								styleId="<%=valueId %>" onkeydown="<%=checkvalue %>" onkeyup="<%=checkvalue %>" />
							</td>
							<td>
								  <html:submit styleId="button" property="deleteItem" indexed="true">
				                    <bean:message key="deprec.calc.delete" />
				                  </html:submit>
                  			</td>
						</tr>
						<% t1++; %>
					</logic:iterate>
					<tr>
						<td align="right" valign="middle"><strong><bean:message key="deprec.total.claimed"/>:</strong></td>
						<td><input type="text" value="00.00" size="5" id="claimed" readonly="readonly"
							class="textfield" /></td>
						<td colspan="5" align="right" valign="middle"><strong><bean:message key="deprec.total.payout"/>:</strong></td>

						<td><input type="text" value="00.00" size="5" id="payout" 
							class="textfield" readonly="readonly" /></td>
					</tr>
					<tr>
						<td colspan="7" align="right" valign="middle"><strong><bean:message key="deprec.total.approved.payout"/>:</strong></td>
						<td><html:text  size="5" onkeydown="this.style.backgroundColor='yellow'" styleId="totalApprovedPayout" styleClass="textfield"  name="claimDeprecCalcForm" property="claimDeprec.dispTotalApprovedPayout" /></td>
					</tr>
				</table>
				<center>
				
			<html:submit property="save" styleId="button">
				<bean:message key="deprec.save.items"/>
			</html:submit>
						&nbsp;&nbsp;&nbsp;&nbsp;
					<html:select name="claimDeprecCalcForm" property="addNum" styleClass="dropdown" >
						<html:option value="1">1</html:option>
						<html:option value="2">2</html:option>
						<html:option value="3">3</html:option>
						<html:option value="4">4</html:option>
						<html:option value="5">5</html:option>
					</html:select> &nbsp;
			<html:submit property="addItems" styleId="button" >
				<bean:message key="deprec.add.items"/>
			</html:submit>
				</center></html:form>
				
				
				<script>
					hideshow('drop_hide_this');
					calculateTotalClaim();
					calculateTotalValue();
					var approved=document.getElementById("totalApprovedPayout");
					var totalVal=document.getElementById("payout");
					if(totalVal!=null && approved!=null && totalVal.value!=approved.value){
						approved.style.backgroundColor='yellow';
					}
					
					<% int index=0;
					 for(Depreciation_Item di:myform.getClaimDeprec().getItemlist()){ %>

						var claimVal=document.getElementById("dep_value_"+<%=index%>);
						checkValue(<%=index%>,claimVal);
					<% } %>
				</script>
