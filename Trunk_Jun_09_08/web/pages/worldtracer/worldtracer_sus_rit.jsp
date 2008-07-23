<%@ page language="java" contentType="text/html; charset=GBK"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<script language="javascript" type="text/javascript">

	function fileCompleteSuspend()
{
	var span=document.getElementById("span");
	span.innerHTML="";
	span.innerHTML="<font color=red style='font-size: 14px'>"+"<b>"+"FILE TYPE"+"<b>"+"</font>"
	+"<select name=''>"+
	"<option value=''>"+"<font color='#0000FF' style='font-size: 14px'><b>AHL</b></font>"+
	"</option>"+"<option value=''><font color='#0000FF' style='font-size: 14px'><b>OHD</b></font>"+
	"</option>"+"</select>"+"&nbsp;&nbsp;"+"<font color='red' style='font-size:14px'>"+"<b>"+"File Reference"+"<b>"+"</font>"
	+"<input type='text' name='' />";
}
function filePartSuspend()
{
	var span=document.getElementById("span");
	span.innerHTML="";
	span.innerHTML="<font color='#003366' style='font-size: 14px'>"+"<b>"+"FILE TYPE-"+"<b>"+"</font>"
	+"<font  color='#999999' style='font-size:14px'><b>"+"AHL"+"</b></font>"+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+"<font color='red' style='font-size:14px'>"+"<b>"+"File Reference"+"<b>"+"</font>"
	+"<input type='text' name='' />";

}
</script>
</head>

<body onload="fileCompleteSuspend();">
<form>
<font size="3" style="font-family:'宋体'"><b>SUS - Suspend / RIT - Reinstate and Amend File</b></font>
<br />
	<table border="1">
	    <tr>
			<td>
				<font color="#0000FF" style="font-size:14px"><b>SUSPEND</b></font>
			</td>
			<td>
				
			</td>
			<td>
				<font color="#0000FF" style="font-size:14px"><b>REINSTATE</b></font>
			</td>
		</tr>
		<tr>
			<td id="completesuspend" align="right">
				<input type="radio" name="suspend" value="" onclick="fileCompleteSuspend();" checked="checked"/>
			</td>
			<td align="center">
				<font color="#0000FF" style="font-size:14px"><b>Complete File SUS / RIT with Amend</b></font>
			</td>
			<td id="completereinstate">
				<input type="radio" name="suspend" value="" />
			</td>
		</tr>
		<tr>
			<td id="partsuspend" align="right">
				<input type="radio" name="suspend" value=""  onclick="filePartSuspend();"/>
			</td>
			<td align="center">
				<font color="#0000FF" style="font-size:14px"><b>Partial File SUS / RIT with Amend</b></font>
			</td>
			<td id="partreinstate">
				<input type="radio" name="suspend" value="" />
			</td>
		</tr>
	</table>
	<br />
	<span id="span">
	</span>
	<br />
	<br />
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="SUBMIT" />&nbsp;&nbsp;&nbsp;<input type="reset" value="RESET" />
	</form>
</body>
</html>
