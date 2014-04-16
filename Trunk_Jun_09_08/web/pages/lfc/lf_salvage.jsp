<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.DateUtils" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.reporting.ReportingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.forms.StatReportForm" %>
<%@ page import="com.bagnet.nettracer.tracing.forms.lfc.SalvageForm" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Date" %>
<%
	Agent agent = (Agent)session.getAttribute("user"); 
	SalvageForm lfSalvageForm=(SalvageForm) session.getAttribute("lfSalvageForm");
  	String cssFormClass = "form2";
  	
  	int divId = 0;
  	String salvageFoundSize=String.valueOf(request.getAttribute("salvagefoundsize"));
%>
  
  
<%@page import="com.bagnet.nettracer.tracing.db.salvage.SalvageItem"%><SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/ajax_forall.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	
	
	function addItemAjax() {
	 var key=event.keyCode || event.which;
	 if (key==9){
		o = document.lfSalvageForm;
		o.addItem.value = o.addBarcode.value;
		o.newBoxId.value = o.addBoxId.value;
		o.divId.value = o.lastDivNum.value;
		if(o.addItem.value){
		postForm("lfSalvageForm", true, function (req) {
			o.addItem.value = "";
			o.divId.value = "";
			jQuery('#barcodeDiv').before(req.responseText);
			document.lfSalvageForm.addBarcode.value="";
			document.lfSalvageForm.addBarcode.focus();
			document.lfSalvageForm.lastDivNum.value = parseInt(document.lfSalvageForm.lastDivNum.value) + 1;
		});
		}
	 }
	}
	
	function boxIdUpdate(key,boxID, foundId){
		 /*var key=event.keyCode || event.which;*/
		 if (key==9){
 			o = document.lfSalvageForm;
 			o.previousBoxId.value = boxID.value;
 			o.boxUpdateFound.value=foundId;
 			postForm("lfSalvageForm", true, function (req) {
 				o.previousBoxId.value = "";
 	 			o.boxUpdateFound.value="";
 	 			jQuery('#barcodeDiv').before(req.responseText);
 	 			
 			});
		 }
	}
	
	function removeItemAjax(divId, foundId) {
		
		o = document.lfSalvageForm;
		o.removeItem.value = foundId;
		postForm("lfSalvageForm", true, function (req) {
			o.removeItem.value = "";
			var response = req.responseText;
			if (!response && response.length != 0) {
				jQuery('#barcodeDiv').before(response);
			} else {
				jQuery('#' + divId).hide();
			}
			document.lfSalvageForm.addBarcode.focus();
		});
	}

	function saveItem() {
		var saveID = document.getElementById("salvageId").value;
		var statusID = document.getElementById("statusId").value;
		document.location.href="lf_salvage.do?save=1&saveID=" + escape(saveID) + "&statusID=" + escape(statusID);
	}

  </SCRIPT>
  
  
  <html:form focus="addBoxId" action="lf_salvage.do" method="post" >
  <input type="hidden" name="addItem" id="addItem" value="" />
  <input type="hidden" name="newBoxId" id="newBoxId" value="" />
  <input type="hidden" name="removeItem" id="removeItem" value="" />
  <input type="hidden" name="divId" id="divId" value="" />
  <input type="hidden" name="boxUpdateFound" id="boxUpdateFound" value="" />
  <input type="hidden" name="previousBoxId" id="previousBoxId" value="" />
    <tr>
        <td colspan="3" id="pageheadercell">
          <div id="pageheaderleft">
            <h1>
            	<bean:message key="header.lf.salvage" />
            </h1>
          </div>
          <div id="pageheaderright">
            <table id="pageheaderright">
              <tr>
              <logic:notEqual name="lfSalvageForm" property="salvage.id" value="0" >
              	<td>
              		<%
              			int reportType = ReportingConstants.RPT_20_CUSTOM_93;
              			String receiptType = "Salvage";
              		%>
              		<a href='#'
		              	onclick="openReportWindow('statReport.do?reportnum=20&customreportnum=93&create=1','<%=receiptType %>',800,600);return false;">
		              		<img src="deployment/main/images/nettracer/icon_printrecpt.gif" width="12" height="12">
		            </a>
           			<a href='#'
          				onclick="openReportWindow('lf_salvage.do?receipt=1&reportnum=20&customreportnum=93&id=<bean:write name="lfSalvageForm" property="salvage.id" />&new=1','<%=receiptType %>',800,600);return false;">
          				Print Receipt
          			</a>
              	</td>
              	</logic:notEqual>
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
                  <center>
                  	<font color="red">
                  		<a href="#" onclick="openReportWindow('reporting?outputtype=<%= request.getAttribute("outputtype") %>&reportfile=<bean:write name="reportfile" scope="request"/>','report',800,600);return false;"><b><bean:message key="link.view_report" /></b></a>
                  	</font>
                  </center>
              </logic:present>
              <div id="messageDiv" >
          <logic:messagesPresent message="true">
          	  <center>
          	  	<font color="red" >
          			<html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages>
          	  	</font>
          	  </center></logic:messagesPresent>
          	  </div>
          <h1 class="green">
            <bean:message key="header.lf.salvage.information" />
          </h1>
	      <span class="reqfield">*&nbsp;</span><bean:message key="message.required" /> 
            <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
            	<tr>
            		<td style="width:25%;">
            			<bean:message key="colname.lf.salvage.id" />
            			<br>
            			<html:text name="lfSalvageForm" property="salvage.id" disabled="true" styleClass="disabledtextfield" styleId="salvageId" size="15"/>
            		</td>
            		<td style="width:25%;">
						<bean:message key="colname.lf.created.date" />
						<br/>
						<input type="text" disabled="true" value="<%= lfSalvageForm.getDisCreatedDate(agent) %>" class="disabledtextfield"  />
						
					</td>
            		<td style="width:25%;">
						<bean:message key="colname.lf.closed.date" />
						<br/>
						<input type="text" disabled="true" value="<%= lfSalvageForm.getDisClosedDate(agent) %>" class="disabledtextfield" />
						
					</td>
					<td style="width:25%;">
						<bean:message key="colname.lf.status" />
						<br/>
       					<html:select name="lfSalvageForm" property="salvage.statusId" styleClass="dropdown" styleId="statusId" >
       						<html:options collection="lfstatuslist" property="status_ID" labelProperty="description" />
       					</html:select>
					</td>
            	</tr>
     		</table>
     		<br>
          	<h1 class="green">
            	<bean:message key="header.lf.salvage.items" />
          	</h1>
	      	<span class="reqfield">*&nbsp;</span><bean:message key="message.required" /> 
   			<div id="salvageItems" >
     		<table class="<%=cssFormClass %>" style="margin-bottom:0;padding-bottom:0;" cellspacing=0 cellpadding=0 >
     			<tr>
     				<td class="header" style="width:20%;" >
     					<bean:message key="colname.lfc.item.salvageboxid" />
     				</td>
     				<td class="header" style="width:15%;" >
     					<bean:message key="colname.lfc.item.id" />
     				</td>
     				<td class="header" style="width:15%;" >
     					<bean:message key="colname.lf.salvage.created.date" />
     				</td>
     				<td class="header" style="width:40%;" >
     					<bean:message key="colname.lfc.found.description" />
     				</td>
     				<td class="header" style="width:10%;" >
     					<bean:message key="colname.lf.action" />
     				</td>
     			</tr>
     		</table>
     			<logic:iterate indexId="i" id="found" name="salvagefounds"  type="com.bagnet.nettracer.tracing.db.lf.LFSalvageFound" >
     			<div id="<%=String.valueOf(divId) %>" >
     			<table class="<%=cssFormClass %>" style="margin:0;padding:0;" cellspacing=0 cellpadding=0 >
     				<tr>
	    				<logic:equal name="lfSalvageForm" property="salvage.status.status_ID" value="<%=String.valueOf(TracingConstants.LF_STATUS_OPEN) %>" >
    	 					<td style="width:20%;">
	    						<input type="text" name='<%="prevBoxId"+divId %>' id='<%="prevBoxId"+divId %>' class="textfield" value="<bean:write name="found" property="salvageBoxId" />" onkeydown="if (event.keyCode==13) {event.keyCode=9; boxIdUpdate(event.keyCode,this,'<bean:write name="found" property="foundBarcode" />')}"/>
     						</td>
	    				</logic:equal>
	    				<logic:equal name="lfSalvageForm" property="salvage.status.status_ID" value="<%=String.valueOf(TracingConstants.LF_STATUS_CLOSED) %>" >
    	 					<td style="width:20%;">
	    						<html:text name="found" property="salvageBoxId" disabled="true" styleClass="textfield" />
     						</td>
	    				</logic:equal>
	    				<td style="width:15%;">
	    					<a href='create_found_item.do?foundId=<bean:write name="found" property="foundID" />' ><bean:write name="found" property="foundBarcode" /></a>
	    				</td>
	    				<td style="width:15%;">
	    					<%= found.getDisCreatedDate(agent.getDateformat().getFormat()) %>
	    				</td>
	    				<td style="width:40%;">
	    					<bean:write name="found" property="extendedSummaryDesc" />
	    				</td>
	    				<td style="width:10%;">
	    					<center>
	     					<logic:equal name="lfSalvageForm" property="salvage.status.status_ID" value="<%=String.valueOf(TracingConstants.LF_STATUS_OPEN) %>" >
		    					<a href='#' onclick="removeItemAjax('<%=divId %>','<bean:write name="found" property="foundBarcode" />')" ><bean:message key="lf.salvage.remove" /></a>
	    						</logic:equal>
	     					<logic:equal name="lfSalvageForm" property="salvage.status.status_ID" value="<%=String.valueOf(TracingConstants.LF_STATUS_CLOSED) %>" >
	     						<bean:message key="lf.salvage.remove" />
	    						</logic:equal>
	    					</center>
	    				</td>
     				</tr>
     				</table>
     			</div>
     			<% divId++; %>
     			</logic:iterate>
     			<logic:notEqual name="lfSalvageForm" property="salvage.id" value="0" >
     			<div id="barcodeDiv" >
     			<table class="<%=cssFormClass %>" style="margin-top:0;padding-top:0;" cellspacing=0 cellpadding=0 >
     			<tr>
     				<td colspan=1 style="width:20%;">
     					<logic:equal name="lfSalvageForm" property="salvage.status.status_ID" value="<%=String.valueOf(TracingConstants.LF_STATUS_OPEN) %>" >
    	 					<input type="text" name="addBoxId" id="addBoxId" class="textfield" value="<%=(request.getAttribute("lastBoxId")!=null?request.getAttribute("lastBoxId"):"") %>" onkeydown="if (event.keyCode==13) {event.keyCode=9; return event.keyCode }" />
     					</logic:equal>
     					<logic:equal name="lfSalvageForm" property="salvage.status.status_ID" value="<%=String.valueOf(TracingConstants.LF_STATUS_CLOSED) %>" >
	     					<input type="text" name="addBoxId" id="addBoxId" class="disabledtextfield" disabled value="<%=(request.getAttribute("lastBoxId")!=null?request.getAttribute("lastBoxId"):"") %>"  />
     					</logic:equal>
     				</td>
     				<td colspan=4>
     					<logic:equal name="lfSalvageForm" property="salvage.status.status_ID" value="<%=String.valueOf(TracingConstants.LF_STATUS_OPEN) %>" >
    	 					<input type="text" name="addBarcode" id="addBarcode" class="textfield" onkeyup="addItemAjax()"  />
     					</logic:equal>
     					<logic:equal name="lfSalvageForm" property="salvage.status.status_ID" value="<%=String.valueOf(TracingConstants.LF_STATUS_CLOSED) %>" >
	     					<input type="text" name="addBarcode" id="addBarcode" class="disabledtextfield" disabled />
     					</logic:equal>
     				</td>
     			</tr>
     		</table>
     		</div>
     		</logic:notEqual>
     			<center>
     			<div style="position:absolute;left:-1000000px;" >
     				<input type="hidden" name="lastDivNum" id="lastDivNum" value="<%=divId %>" />
     				<html:submit styleId="hiddenSubmit" />
     			</div>
     			<html:button property="saveButton" styleId="saveButton" styleClass="button" onclick="this.disabled = true; saveItem();" >
					<bean:message key="button.save" />
				</html:button>
     			</center>
      		</div>
       
      	</td>
      	</tr>
    </html:form>
