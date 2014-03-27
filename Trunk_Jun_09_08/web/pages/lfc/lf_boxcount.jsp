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
<%@ page import="com.bagnet.nettracer.tracing.forms.lfc.BoxCountForm" %>
<%@ page import="java.util.Date" %>
<%
	Agent agent = (Agent)session.getAttribute("user"); 
  	String cssFormClass = "form2";
  	
  	int divId = 0;
%>
  
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/ajax_forall.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	
	
	
	/*people.sort(sortByFirstName);*/
	 
	 

	 function addCountAjax() {
		 	o = document.lfBoxCountForm;
			o.divId.value = o.lastDivNum.value;
			var button=document.getElementById('addbutton');
			button.disabled=true;
			var list=document.getElementById('countList');
			staList=document.getElementById('stationLists');
			
			var staId=staList.options[staList.selectedIndex].value;
			var newSta=staList.options[staList.selectedIndex].innerHTML;
			o.addStationName.value=staId;
			postForm("lfBoxCountForm", true, function (req) {
				var row=document.getElementById('countId'+o.addStation.value);
				if(row!=null){
						row.innerHTML=parseInt(row.innerHTML)+1;
				}
				else
				{	var textDiv='headerId';
					var text=req.responseText;
					text=text.substring(125,text.length-25);
					for(var i=1, crow; crow=list.rows[i]; i++) {
						
						if(typeof(crow.cells[1])!=='undefined'){
							if(crow.cells[1].firstChild.innerHTML > newSta)
							{
								break;
							}
							textDiv=crow.id;
							
						}
					}
					jQuery('#'+textDiv).after(text);
				}
				
				o.divId.value = "";
				o.addStationName.value="";
				button.disabled=false;
				document.lfBoxCountForm.addStation.focus();
				
			
			});
				
		}
		
		function remStationAjax(remStation) {
			
			o = document.lfBoxCountForm;
			o.removeStation.value = remStation;
			var removelink=document.getElementById('rem'+remStation);
			removelink.disabled=true;
			postForm("lfBoxCountForm", true, function (req) {
				var row=document.getElementById('countId'+o.removeStation.value);
				if(row!=null){
					row.innerHTML=parseInt(row.innerHTML)-1;
				}
				
				if(row.innerHTML=="0")
				{
					var zeroRow=document.getElementById(o.removeStation.value);
					var zeroParent=zeroRow.parentNode;
					zeroParent.removeChild(zeroRow);
				}
				o.removeStation.value = "";
				removelink.disabled=false;
				document.lfBoxCountForm.addStation.focus();
			});
		}

  </SCRIPT>
  
  
  <html:form focus="dateStringTime" action="lf_boxcount.do" method="post" >
  <input type="hidden" name="addStationName" id="addStationName" value="" />
  <input type="hidden" name="removeStation" id="removeStation" value="" />
  <input type="hidden" name="divId" id="divId" value="" />
  	<tr>
  		<td colspan="3" id="pageheadercell">
          <div id="pageheaderleft">
            <h1>
            	<bean:message key="header.lf.boxcount" />
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
            <bean:message key="header.lf.boxcount" />
          </h1>
	      <span class="reqfield">*&nbsp;</span><bean:message key="message.required" /> 
            <table id="pageheaderright">
              <tr>
              	<td><center>
              		<bean:message key="colname.lf.boxcount.datecount" />&nbsp;(<%= agent.getDateformat().getFormat() %>)&nbsp;<span class="reqfield">*</span>
		    		<br/>
              		 <html:text property="dateString" name="lfBoxCountForm" size="12" maxlength="11" styleClass="textfield" styleId="dateStringTime" />
              		 <img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.lfBoxCountForm.dateString,'calendar','<%= agent.getDateformat().getFormat() %>'); return false;">
              		 
              		
	    			<html:submit property="load" styleId="loadButton" styleClass="button">
						<bean:message key="button.load" />
					</html:submit>
					</center>
              	</td>
              </tr>
              
            </table>
            <br/>
            <logic:notEqual name="lfBoxCountForm" property="container.id" value="0" >
          	<h1 class="green">
            	<bean:message key="header.lf.boxcount.station" />
          	</h1>
          	<div >
     				<table class="<%=cssFormClass %>" style="margin:0;padding:0;" cellspacing=0 cellpadding=0 >
	     				<tr>
		    				<td style="width:20%;">
		    					<bean:message key="colname.lf.boxcount.sourcestation" />
		    					<br/>
		    					<logic:present name="stationList" scope="request">
			                      <html:select styleId="stationLists" name="lfBoxCountForm" styleClass="dropdown" property="addStation">
			                        <logic:empty name="lfBoxCountForm" property="addStation">
			                          <html:option value="">
			                            <bean:message key="select.please_select" />
			                          </html:option>
			                        </logic:empty>
			                        <html:options collection="stationList" property="station_ID" labelProperty="stationcode" />
			                      </html:select>
			                    </logic:present>
		    				</td>
		    				<td style="width:20%;">
		    				<br/>
							<center>
	     						<input type="button" id="addbutton" href='#' onclick="addCountAjax()" value="<bean:message key="colname.lf.boxcount.addcount"/>" />
	    					</center>
		    				</td>
	     				</tr>
     				</table>
   				</div>
	      	<!-- <span class="reqfield">*&nbsp;</span><bean:message key="message.required" /> -->
   			<table class="<%=cssFormClass %>" style="margin-bottom:0;padding-bottom:0;" cellspacing=0 cellpadding=0 id="countList">
     			<tr id="headerId">
     				<td class="header" style="width:20%;" >
     					<bean:message key="colname.lf.boxcount.count" />
     				</td>
     				<td class="header" style="width:20%;" >
     					<bean:message key="colname.lf.boxcount.sourcename" />
     				</td>
     				<td class="header" style="width:40%;" >
     					<bean:message key="colname.lf.action" />
     				</td>
     			</tr>
	     			<!-- <table class="<%=cssFormClass %>" style="margin:0;padding:0;" cellspacing=0 cellpadding=0 >-->
     				<logic:iterate indexId="i" id="count" name="boxCounts"  type="com.bagnet.nettracer.tracing.db.lf.LFBoxCount" >
	              	<% if(count.getBoxCount()>0) {%>
	              		<tr id="<%=count.getSourceId() %>" >
	     				
	     					
		    				<td style="width:20%;">
		    					<span id="countId<%=count.getSourceId() %>">
		    					<bean:write property="boxCount" name="count"/>
		    					</span>
		    				</td>
		    				<td style="width:20%;">
		    					<span id="stationName">
		    						<bean:write  name="count" property="sourceName" />
		    					</span>
		    				</td>
		    				<td style="width:40%;">
		    					<center>
		     						<a href='#' id="rem<%=count.getSourceId()%>" onclick="remStationAjax('<bean:write name="count" property="sourceId" />')" ><bean:message key="lf.salvage.remove" /></a>
		    					</center>
		    				</td>
	     				
	     			<% divId++; %>
	     			</tr>
	     			<% } %>
	     			</logic:iterate>
     				<!-- </table>-->
	     			
     		</table>
      		</logic:notEqual>
     		
     		
     			<center>
     			<div style="position:absolute;left:-1000000px;" >
     				<input type="hidden" name="lastDivNum" id="lastDivNum" value="<%=divId %>" />
     				<html:submit styleId="hiddenSubmit" />
     			</div>
     			</center>
       
      	</td>
      	</tr>
  </html:form>
