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
<%@ page import="com.bagnet.nettracer.tracing.forms.lfc.DeliveredFoundForm" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Date" %>
<%
	Agent agent = (Agent)session.getAttribute("user"); 
	DeliveredFoundForm lfDeliveredFoundForm=(DeliveredFoundForm) session.getAttribute("lfDeliveredFoundForm");
  	String cssFormClass = "form2";
  	
  	int divId = 0;
  	String salvageFoundSize=String.valueOf(request.getAttribute("salvagefoundsize"));
/*  	String salvageFoundSize=String.valueOf(Integer.valueOf(request.getAttribute("salvagefoundsize"))-1);*/
%>
  
  
<%@page import="com.bagnet.nettracer.tracing.db.salvage.SalvageItem"%><SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/ajax_forall.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	

	function addItemAjax() {
	 var key=event.keyCode || event.which;
	 if (key==9){
		o = document.lfDeliveredFoundForm;
		o.addDeliveredItem.value = o.addBarcode.value;
		o.newRemark.value = o.newRemark.value;
		o.divId.value = o.lastDivNum.value;
		if(o.addDeliveredItem.value){
		postForm("lfDeliveredFoundForm", true, function (req) {
			o.addDeliveredItem.value = "";
			o.divId.value = "";
			jQuery('#barcodeDiv').before(req.responseText);
			document.lfDeliveredFoundForm.addBarcode.value="";
			document.lfDeliveredFoundForm.addBarcode.focus();
			document.lfDeliveredFoundForm.lastDivNum.value = parseInt(document.lfDeliveredFoundForm.lastDivNum.value) + 1;
		});
		}
	 }
	}
	
	function removeItemAjax(divId, foundId) {
		
		o = document.lfDeliveredFoundForm;
		o.removeItem.value = foundId;
		postForm("lfDeliveredFoundForm", true, function (req) {
			o.removeItem.value = "";
			var response = req.responseText;
			if (!response && response.length != 0) {
				jQuery('#barcodeDiv').before(response);
			} else {
				jQuery('#' + divId).hide();
			}
			document.lfDeliveredFoundForm.addBarcode.focus();
		});
	}

  </SCRIPT>
  
  
  <html:form focus="newRemark" action="lf_delivered_found.do" method="post" >
  <input type="hidden" name="addDeliveredItem" id="addDeliveredItem" value="" />
  <input type="hidden" name="newRemove" id="newBoxId" value="" />
  <input type="hidden" name="divId" id="divId" value="" />
  <input type="hidden" name="boxUpdateFound" id="boxUpdateFound" value="" />
  <input type="hidden" name="previousBoxId" id="previousBoxId" value="" />
    <tr>
        <td colspan="3" id="pageheadercell">
          <div id="pageheaderleft">
            <h1>
            	<bean:message key="header.lf.delivered.found" />
            </h1>
          </div>
          <div id="pageheaderright">
            <table id="pageheaderright">
              <tr>
              	<td>
              		<%
              			int reportType = ReportingConstants.RPT_20_CUSTOM_93;
              			String receiptType = "Delivered Found";
              		%>
              		<a href='#'
		              	onclick="openReportWindow('statReport.do?reportnum=20&customreportnum=93&create=1','<%=receiptType %>',800,600);return false;">
		              		<img src="deployment/main/images/nettracer/icon_printrecpt.gif" width="12" height="12">
		            </a>
              	</td>
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
            	<bean:message key="header.lf.delivered.found.items" />
          	</h1>
	      	<span class="reqfield">*&nbsp;</span><bean:message key="message.required" /> 
   			<div id="deliveredFoundItems" >
     		<table class="<%=cssFormClass %>" style="margin-bottom:0;padding-bottom:0;" cellspacing=0 cellpadding=0 >
     			<tr>
     				<td class="header" >
     					<bean:message key="colname.lfc.item.delivered.found.result" />
     				</td>
     			</tr>
     		</table>
   			<div id="barcodeDiv" >
	   			<table class="<%=cssFormClass %>" style="margin-top:0;padding-top:0;" cellspacing=0 cellpadding=0 >
	     			<tr>
	     				<td colspan=1 style="width:33%;">
	     						<bean:message key="lf.delivery.remark"/>: <input type="text" name="newRemark" id="newRemark" class="textfield" value="<%=(request.getAttribute("newRemark")!=null?request.getAttribute("newRemark"):"") %>" onkeydown="if (event.keyCode==13) {event.keyCode=9; return event.keyCode }" />
	     				</td>
	     				<td colspan=1 style="width:40%;">
	     						<bean:message key="lf.found.barcode"/>: <input type="text" name="addBarcode" id="addBarcode" class="textfield" onkeyup="addItemAjax()"  />
	     				</td>
	     				<td colspan=1>
	     					<center>
	     						<html:submit property="save" styleId="saveButton" styleClass="button" >
									<bean:message key="button.save" />
								</html:submit>
							</center>
	     				</td>
	     			</tr>
	     		</table>
     		</div>
     			<center>
	     			<div style="position:absolute;left:-1000000px;" >
	     				<input type="hidden" name="lastDivNum" id="lastDivNum" value="<%=divId %>" />
	     				<html:submit styleId="hiddenSubmit" />
	     			</div>
     			</center>
      		</div>
       
      	</td>
      	</tr>
    </html:form>
