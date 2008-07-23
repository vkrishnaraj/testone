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
	"<option value=''>"+"<font color=red' style='font-size: 14px'><b>AHL</b></font>"+
	"</option>"+"<option value=''><font color='red' style='font-size: 14px'><b>OHD</b></font>"+
	"</option>"+"</select>"+"&nbsp;&nbsp;"+"<font color='red' style='font-size:14px'>"+"<b>"+"File Reference"+"<b>"+"</font>"
	+"<input type='text' name='' />";
}
function filePartSuspend()
{
	var span=document.getElementById("span");
	span.innerHTML="";
	span.innerHTML="<font color='red' style='font-size: 14px'>"+"<b>"+"FILE TYPE-"+"<b>"+"</font>"
	+"<font  color='black' style='font-size:14px'>AHL</font>"
	+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+"<font color='red' style='font-size:14px'>"+"<b>"+"File Reference"+"<b>"+"</font>"
	+"<input type='text' name='' />";

}
</script>


<!-- calendar stuff ends here -->
<html:form action="worldtracersusrit.do" method="post"
	onsubmit="return (validateForwardOHD(this) && setExpediteNum(this)); ">
		<tr>
		<!-- MIDDLE COLUMN -->
		<td id="middlecolumn"><!-- MAIN BODY -->
		<div id="maincontent">
		<h1 class="green"><bean:message key="colname.worldtracer_sus_rit" />
	    </h1>
		
	
	<table class="form2" cellspacing="0" cellpadding="0">

		<tr>
			<td><bean:message key="colname.worldtracer_sus" /></td>
			<td></td>
			<td><bean:message key="colname.worldtracer_reinstate" /></td>
		</tr>
		<tr>
			<td><input type="radio" name="suspend" value=""
				onclick="fileCompleteSuspend();" checked="checked" /></td>
			<td><bean:message
				key="colname.worldtracer_complete_file_sus_rit" /></td>
			<td><input type="radio" name="suspend" value="" onclick="fileCompleteSuspend();"/></td>
		</tr>
		<tr>
			<td><input type="radio" name="suspend" value=""
				onclick="filePartSuspend();" /></td>
			<td><bean:message key="colname.worldtracer_part_file_sus_rit" /></td>
			<td><input type="radio" name="suspend" value="" onclick="filePartSuspend();"/></td>
			<!--  td><html:radio property="worldtracersusrit" value=""></html:radio></td-->
		</tr>
	</table>
		<center>
			<span id="span"> 
				<font color=red style='font-size: 14px'><b>FILE TYPE<b></font> 
					<select name=''>
						<option value=''>
							<font color='red' style='font-size: 14px'><b>AHL</b></font>
						</option>
						<option value=''>
							<font color='red' style='font-size: 14px'><b>OHD</b></font>
						</option>
					</select> 
					&nbsp;&nbsp;<font color='red' style='font-size: 14px'><b>File Reference<b></font>
			 <input type='text' name='' /> 
			 </span>
			 </center>
	<br>
	<br>
	<center><html:submit styleId="button" property="save">
		<bean:message key="button.sus.rit.submit" />
	</html:submit> <html:reset styleId="button" property="reset">
		<bean:message key="button.sus.rit.reset" />
	</html:reset></center>
</html:form>
