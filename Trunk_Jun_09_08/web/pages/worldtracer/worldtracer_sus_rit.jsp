<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested"%>
<%@ page
	import="com.bagnet.nettracer.tracing.forms.WorldTracerSusRitForm"%>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%
	Agent a = (Agent) session.getAttribute("user");
%>
<SCRIPT LANGUAGE="JavaScript">
    function textCounter(field, countfield, maxlimit) {
      if (field.value.length > maxlimit) {
        field.value = field.value.substring(0, maxlimit);
      } else {
        countfield.value = maxlimit - field.value.length;
      }
    }
    // End -->
  </SCRIPT>
<!-- Calendar includes -->
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    <!--
	var cal1xx = new CalendarPopup();	
	//cal1xx.showNavigationDropdowns();
// -->
  </SCRIPT>
<!-- calendar stuff ends here -->
<script language="javascript">

function getreason(choose){
  var choosestr = choose.options[choose.selectedIndex].text;
  var reason = choosestr.substring(4);
  document.all.reason_for_loss.value = reason;
}
function fileCompleteSuspend()
{
	var span=document.getElementById("span");
	span.innerHTML="";
	span.innerHTML="<font color=red style='font-size: 14px'>"+"<b>"+"FILE TYPE"+"<b>"+"</font>"
	+"<select name=''>"+
	"<option value='ahl' selected>"+"<font color=red' style='font-size: 14px'><b>AHL</b></font>"+
	"</option>"+"<option value='ohd'><font color='red' style='font-size: 14px'><b>OHD</b></font>"+
	"</option>"+"</select>"+"&nbsp;&nbsp;"+"<font color='red' style='font-size:14px'>"+"<b>"+"File Reference"+"<b>"+"</font>"
	+"<input type='text' name='fileReference' value=''/>";
}
function filePartSuspend()
{
	var span=document.getElementById("span");
	span.innerHTML="<font color='red' style='font-size: 14px'>"+"<b>"+"FILE TYPE-"+"<b>"+"</font>"
	+"<font  color='black' style='font-size:14px'>AHL</font>"
	+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+"<font color='red' style='font-size:14px'>"+"<b>"+"File Reference"+"<b>"+"</font>"
	+"<input type='text' name='fileReference' value=''/>";

}
function fileReferenceHaveOrNot()
{
	fileref=document.getElementById("fileReference");

	var fileReferenceData=document.worldTracerSusRitForm.fileReference.value;

	if(fileReferenceData==null||fileReferenceData == "")
	{
		fileref.innerHTML="<font color='red'>please put a fileReference</font>";

	}
	else
	{
		document.worldTracerSusRitForm.submit();
	}
	
	
}
</script>


<!-- calendar stuff ends here -->
<html:form action="worldtracersusrit.do" method="post" onsubmit="return (validateForwardOHD(this) && setExpediteNum(this));">
		<tr>
		<!-- MIDDLE COLUMN -->
		<td id="middlecolumn"><!-- MAIN BODY -->
		<div id="maincontent">
		<h1 class="green"><bean:message key="colname.worldtracer_sus_rit" />
	    </h1>
	    <div id="fileReference">
	    <font color=red>
            <logic:messagesPresent message="true">
           		 <html:messages id="msg" message="true">
            		<br/><bean:write name="msg"/><br/>
            		</html:messages>
            </logic:messagesPresent>
            <%
            if(null!=request.getAttribute("completeSuccess")&&!"".equals(request.getAttribute("completeSuccess"))&&request.getAttribute("completeSuccess").equals("1"))
            {
            %>
            <font color="green"><bean:message key="update.suspend.successful"/></font>
            <%
            }
            %>
             <%
            if(null!=request.getAttribute("completeSuccess")&&!"".equals(request.getAttribute("completeSuccess"))&&request.getAttribute("completeSuccess").equals("2"))
            {
            %>
            <font color="red"><bean:message key="update.suspend.fail"/></font>
            <%
            }
            %>
          </font>
	    </div>
	<table class="form2" cellspacing="0" cellpadding="0">

		<tr>
			<td><bean:message key="colname.worldtracer_sus" /></td>
			<td></td>
			<td><bean:message key="colname.worldtracer_reinstate" /></td>
		</tr>
		<tr>
			<td><input type="radio" name="suspend" value="completeSUS"
				onclick="fileCompleteSuspend();" 
				<%
					if(request.getAttribute("radio").equals("completeSUS"))
					{
				%>
					checked="checked" />
				<%
					}
				%>
				</td>
			<td><bean:message
				key="colname.worldtracer_complete_file_sus_rit" /></td>
			<td><input type="radio" name="suspend" value="" onclick="fileCompleteSuspend();"/></td>
		</tr>
		<tr>
			<td><input type="radio" name="suspend" value="partSUS"
				onclick="filePartSuspend();" 
				<%
					if(request.getAttribute("radio").equals("partSUS"))
					{
				%>
					checked="checked" />
				<%
					}
				%>
				</td>
			<td><bean:message key="colname.worldtracer_part_file_sus_rit" /></td>
			<td><input type="radio" name="suspend" value="" onclick="filePartSuspend();"/></td>
		</tr>
	</table>
		<center>
			<span id="span"> 
			<%
			if(request.getAttribute("span").equals("completeSUS"))
			{
			%>
				<font color=red style='font-size: 14px'><b>FILE TYPE<b></font> 
					<select name='ahlORohd'>
						<option value='ahl' 
			<%
			if(request.getAttribute("ahlOrohd").equals("ahl"))
			{
			%>
			selected>
			<%
			}
			%>
							<font color='red' style='font-size: 14px'><b>AHL</b></font>
						</option>
						<option value='ohd'
			<%
			if(request.getAttribute("ahlOrohd").equals("ohd"))
			{
			%>
			selected>
			<%
			}
			%>
						<font color='red' style='font-size: 14px'><b>OHD</b></font>
						</option>
					</select> 
					&nbsp;&nbsp;<font color='red' style='font-size: 14px'><b>File Reference<b></font>
					<input type="text" name="fileReference" value=<%=request.getAttribute("filereference")%>>
			<%
			}
			%>
			<%
			if(request.getAttribute("span").equals("partSUS"))
			{
			%>
			<font color=red style='font-size: 14px'><b>FILE TYPE-<b></font> 
					<font color='black' style='font-size: 14px'><b>AHL</b></font>
					&nbsp;&nbsp;<font color='red' style='font-size: 14px'><b>File Reference<b></font>
					<input type="text" name="fileReference" value=<%=request.getAttribute("filereference")%>>
			<%
			}
			%>
			 </span>
			 </center>
	<br>
	<br>
	<center><html:button styleId="button" property="save" onclick="fileReferenceHaveOrNot();">
		<bean:message key="button.sus.rit.submit" />
	</html:button> <html:reset styleId="button" property="reset">
		<bean:message key="button.sus.rit.reset" />
	</html:reset></center>
	<logic:notEmpty name="partresultlist">
		<tr>
		<!-- MIDDLE COLUMN -->
		<td id="middlecolumn"><!-- MAIN BODY -->
		<div id="maincontent">
		<h1 class="green">
		<%
		if(request.getAttribute("span").equals("completeSUS"))
		{
		%>
		<bean:message key="colname.worldtracer_sus_rit_completeSUS" />
		<%
		}
		%>
		<%
		if(request.getAttribute("span").equals("partSUS"))
		{
		%>
		<bean:message key="colname.worldtracer_sus_rit_partialSUS" />
		<%
		}
		%>
	    </h1>
	<table class="form2" cellspacing="0" cellpadding="0">
		<logic:present name="partresultlist" scope="request">
			<logic:iterate id="resultlist" name="partresultlist" type="com.bagnet.nettracer.tracing.db.Item">
				<tr colspan="3">
					<td>
						<input type="checkbox" name="judgepartsuspend" value='<bean:write name="resultlist" property="item_ID"/>'>
						&nbsp;&nbsp;BagNumber:<bean:write name="resultlist" property="bagnumber"/>
					</td>
				</tr>
				<tr>
					<td>status<br><input type="text" value='<bean:write name="resultlist" property="status" />' readonly/></td>
					<td>claimchecknum<br><input type="text" value='<bean:write name="resultlist" property="claimchecknum" />' readonly/></td>
				</tr>
				<tr>
					<td>lnameonbag<br><input type="text" value='<bean:write name="resultlist" property="lnameonbag" />' readonly/></td>
					<td>fnameonbag<br><input type="text" value='<bean:write name="resultlist" property="fnameonbag" />' readonly/></td>
					<td>mnameonbag<br><input type="text" value='<bean:write name="resultlist" property="mnameonbag" />' readonly/></td>
				</tr>
				<tr>
					<td>
						Color/Type<br>
						<input type="text" value='<bean:write name="resultlist" property="color" />' readonly/>/
						<input type="text" value='<bean:write name="resultlist" property="bagtype" />' readonly/>
					</td>
				</tr>
		</logic:iterate>
				</logic:present>
	</table>
	<br>
	<br>
	<center><html:submit styleId="button" property="save">
		<bean:message key="button.sus.rit.submit" />
	</html:submit> <html:reset styleId="button" property="reset">
		<bean:message key="button.sus.rit.reset" />
	</html:reset></center>
	</logic:notEmpty>
	<input type="hidden" name="hidden" value="hidden">
</html:form>
