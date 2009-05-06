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

  </SCRIPT>

<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	


  </SCRIPT>

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
	
	fileref=document.getElementById("fileReference");
	fileref.innerHTML = "";
}
function filePartSuspend()
{
	var span=document.getElementById("span");
	span.innerHTML="";
	span.innerHTML="<font color='red' style='font-size: 14px'>"+"<b>"+"FILE TYPE-"+"<b>"+"</font>"
	+"<font  color='black' style='font-size:14px'>AHL</font>"
	+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+"<font color='red' style='font-size:14px'>"+"<b>"+"File Reference"+"<b>"+"</font>"
	+"<input type='text' name='fileReference' value=''/>";
	
	fileref=document.getElementById("fileReference");
	fileref.innerHTML = "";
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



<html:form action="worldtracersusrit.do" method="post" onsubmit="return (validateForwardOHD(this) && setExpediteNum(this));">
		<tr>
		
		<td id="middlecolumn">
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
            <logic:empty name="partialahlsuccess" scope="request">
             <logic:empty name="susbaggageall" scope="request">
            
            <logic:notEmpty name="completeSuccess" scope="request">
           		<logic:equal name="completeSuccess" value="1" scope="request">
            		<font color="green"><bean:message key="update.suspend.successful"/></font>
            		<br>
           	    </logic:equal>
           	    <logic:equal name="completeSuccess" value="2" scope="request">
            		<font color="red"><bean:message key="update.suspend.fail"/></font>
            		<br>
            	</logic:equal>
         	</logic:notEmpty>
         	</logic:empty>
         	</logic:empty>
         	
         	<logic:empty name="partialahlsuccess" scope="request">
         	 <logic:notEmpty name="susbaggageall" scope="request">
            		<font color="green"><bean:message key="suspend.all.baggage"/></font>
            		<br>
         	</logic:notEmpty>
			</logic:empty>
           
           	<logic:notEmpty name="ohdsuccess" scope="request">
         		<logic:equal name="ohdsuccess" value="1" scope="request">
            		<font color="green"><bean:message key="insert.ohd.success"/></font>
            		<br>
           	    </logic:equal>
            	<logic:equal name="ohdsuccess" value="2" scope="request">
            	    <font color="red"><bean:message key="insert.ohd.fail"/></font>
            	    <br>
            	</logic:equal>
            </logic:notEmpty>   
           		<logic:notEmpty name="partialahlsuccess" scope="request">
         			<logic:equal name="partialahlsuccess" value="1" scope="request">
            		<font color="green"><bean:message key="partial.ahl.success"/></font>
            		<br>
           	   	    </logic:equal>
            	<logic:equal name="partialahlsuccess" value="2" scope="request">
            	     <font color="red"><bean:message key="partial.ahl.fail"/></font>
            	     <br>
            	</logic:equal>
            </logic:notEmpty> 
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
				<logic:notEmpty name="radio" scope="request">
         			<logic:equal name="radio" value="completeSUS" scope="request">
            			checked="checked"
           	    	</logic:equal>
           	    </logic:notEmpty>
				/>
				</td>
			<td><bean:message
				key="colname.worldtracer_complete_file_sus_rit" /></td>
			<td><input type="radio" name="suspend" value="" onclick="fileCompleteSuspend();"/></td>
		</tr>
		<tr>
			<td><input type="radio" name="suspend" value="partSUS"
				onclick="filePartSuspend();" 
				<logic:notEmpty name="radio" scope="request">
         			<logic:equal name="radio" value="partSUS" scope="request">
            			checked="checked"
           	    	</logic:equal>
           	    </logic:notEmpty>
				/>
				</td>
			<td><bean:message key="colname.worldtracer_part_file_sus_rit" /></td>
			<td><input type="radio" name="suspend" value="" onclick="filePartSuspend();"/></td>
		</tr>
	</table>
		<center>
			<span id="span"> 
			<logic:notEmpty name="span" scope="request">
				<logic:equal name="span" value="completeSUS" scope="request">
					<font color=red style='font-size: 14px'><b>FILE TYPE<b></font> 
					<select name='ahlORohd'>
				<logic:notEmpty name="ahlOrohd" scope="request">	
				<logic:equal name="ahlOrohd" value="ahl" scope="request">
					<option value='ahl' selected>
					AHL
					</option>
					<option value='ohd'>
					OHD
					</option>
				</logic:equal>
				<logic:equal name="ahlOrohd" value="ohd" scope="request">
					<option value='ahl'>
					AHL
					</option>
					<option value='ohd' selected>
					OHD
					</option>
				</logic:equal>
				</logic:notEmpty>
					</select> 
					&nbsp;&nbsp;<font color='red' style='font-size: 14px'><b>File Reference<b></font>
					<input type="text" name="fileReference" value=<bean:write name='filereference' scope='request'/>>
				</logic:equal>
			</logic:notEmpty>
			<logic:notEmpty name="span" scope="request">
				<logic:equal name="span" value="partSUS" scope="request">
					<font color=red style='font-size: 14px'><b>FILE TYPE-<b></font> 
					<font color='black' style='font-size: 14px'><b>AHL</b></font>
					&nbsp;&nbsp;<font color='red' style='font-size: 14px'><b>File Reference<b></font>
					<input type="text" name="fileReference" value=<bean:write name='filereference' scope='request'/>>
				</logic:equal>
			</logic:notEmpty>
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
		
		<td id="middlecolumn">
		<div id="maincontent">
		<h1 class="green">
			<logic:notEmpty name="span" scope="request">
         		<logic:equal name="span" value="completeSUS" scope="request">
            		<bean:message key="colname.worldtracer_sus_rit_completeSUS" />
           	    </logic:equal>
            	<logic:equal name="span" value="partSUS" scope="request">
            	   <bean:message key="colname.worldtracer_sus_rit_partialSUS" />
            	</logic:equal>
            </logic:notEmpty>
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
				<tr>
					<td>
						<input type="hidden" name="judgecheckboxisornot" value="1"/>
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
