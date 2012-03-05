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
<%@ page import="java.util.Date" %>
<%
	Agent agent = (Agent)session.getAttribute("user"); 
  	String cssFormClass = "form2";
  	
  	int divId = 0;
%>
  
  
<%@page import="com.bagnet.nettracer.tracing.db.salvage.SalvageItem"%><SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/ajax_forall.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	

	function addItemAjax() {
		o = document.lfSalvageForm;
		o.addItem.value = o.addBarcode.value;
		o.divId.value = o.lastDivNum.value;
		postForm("lfSalvageForm", true, function (req) {
			o.addItem.value = "";
			o.divId.value = "";
			jQuery('#barcodeDiv').before(req.responseText);
			document.lfSalvageForm.addBarcode.value="";
			document.lfSalvageForm.addBarcode.focus();
			document.lfSalvageForm.lastDivNum.value = document.lfSalvageForm.lastDivNum.value + 1;
		});
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

  </SCRIPT>
  
  
  <html:form focus="addBarcode" action="lf_salvage.do" method="post" >
  <input type="hidden" name="addItem" id="addItem" value="" />
  <input type="hidden" name="removeItem" id="removeItem" value="" />
  <input type="hidden" name="divId" id="divId" value="" />
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
						<html:text name="lfSalvageForm" property="disCreatedDate" disabled="true" styleClass="disabledtextfield" styleId="createdDate" size="15"/>
					</td>
            		<td style="width:25%;">
						<bean:message key="colname.lf.closed.date" />
						<br/>
						<html:text name="lfSalvageForm" property="disClosedDate" disabled="true" styleClass="disabledtextfield" styleId="closedDate" size="15"/>
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
     					<bean:message key="colname.lfc.item.id" />
     				</td>
     				<td class="header" style="width:20%;" >
     					<bean:message key="colname.lf.salvage.created.date" />
     				</td>
     				<td class="header" style="width:40%;" >
     					<bean:message key="colname.lfc.found.description" />
     				</td>
     				<td class="header" style="width:20%;" >
     					<bean:message key="colname.lf.action" />
     				</td>
     			</tr>
     		</table>
     			<logic:iterate indexId="i" id="found" name="lfSalvageForm" property="salvage.items" type="com.bagnet.nettracer.tracing.db.lf.LFFound" >
     			<div id="<%=String.valueOf(divId) %>" >
     			<table class="<%=cssFormClass %>" style="margin:0;padding:0;" cellspacing=0 cellpadding=0 >
     				<tr>
	    				<td style="width:20%;">
	    					<a href='create_found_item.do?foundId=<bean:write name="found" property="id" />' ><bean:write name="found" property="barcode" /></a>
	    				</td>
	    				<td style="width:20%;">
	    					<bean:write name="found" property="disReceivedDate" />
	    				</td>
	    				<td style="width:40%;">
	    					<bean:write name="found" property="extendedSummaryDesc" />
	    				</td>
	    				<td style="width:20%;">
	    					<center>
	     					<logic:equal name="lfSalvageForm" property="salvage.status.status_ID" value="<%=String.valueOf(TracingConstants.LF_STATUS_OPEN) %>" >
		    					<a href='#' onclick="removeItemAjax('<%=divId %>','<bean:write name="found" property="barcode" />')" ><bean:message key="lf.salvage.remove" /></a>
	    						<!-- input type="button" class="button" id="button_<%=divId %>" onclick="removeItemAjax('<%=divId %>','<bean:write name="found" property="id" />')" value='<bean:message key="lf.salvage.remove" />' -->
	    					</logic:equal>
	     					<logic:equal name="lfSalvageForm" property="salvage.status.status_ID" value="<%=String.valueOf(TracingConstants.LF_STATUS_CLOSED) %>" >
	     						<bean:message key="lf.salvage.remove" />
	    						<!-- input type="button" class="button" id="button_<%=divId %>" onclick="removeItemAjax('<%=divId %>','<bean:write name="found" property="id" />')" value='<bean:message key="lf.salvage.remove" />' disabled -->
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
     				<td colspan=4>
     					<logic:equal name="lfSalvageForm" property="salvage.status.status_ID" value="<%=String.valueOf(TracingConstants.LF_STATUS_OPEN) %>" >
    	 					<input type="text" name="addBarcode" id="addBarcode" class="textfield" />
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
     			<html:submit property="save" styleId="saveButton" styleClass="button" >
					<bean:message key="button.save" />
				</html:submit>
     			</center>
      		</div>
       
      	</td>
      	</tr>
    </html:form>
