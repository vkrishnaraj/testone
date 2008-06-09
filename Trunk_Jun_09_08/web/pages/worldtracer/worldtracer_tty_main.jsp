<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested"%>
<%@ page import="com.bagnet.nettracer.tracing.forms.WorldTracerTTYForm"%>
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
</script>

  
<!-- calendar stuff ends here -->

<html:form action="worldtracertty.do" method="post"
	onsubmit="return (validateForwardOHD(this) && setExpediteNum(this)); ">

	<tr>
		<!-- MIDDLE COLUMN -->
		<td id="middlecolumn"><!-- MAIN BODY -->
		<div id="maincontent">
		<h1 class="green"><bean:message
			key="header.tty_to_worldtracer_title" /> </h1>
		<font color=red> 
		 <logic:messagesPresent message="true">
			<html:messages id="msg" message="true">				
				<bean:write name="msg" />
			</html:messages>
		 </logic:messagesPresent> 
		 <br>
		 </font>
		 
		<table class="form2" cellspacing="0" cellpadding="0">	

			<tr>
				<td colspan="4"><bean:message key="Teletype_Address" /><font color=red> * </font>:</td>
			</tr>
			<tr>
				<td><html:text name="worldTracerTTYForm"
					property="teletype_address1" size="15" maxlength="8" /></td>
				<td><html:text name="worldTracerTTYForm"
					property="teletype_address2" size="15" maxlength="8" /></td>
				<td><html:text name="worldTracerTTYForm"
					property="teletype_address3" size="15" maxlength="8" /></td>
				<td><html:text name="worldTracerTTYForm"
					property="teletype_address4" size="15" maxlength="8" /></td>
			</tr>
			<tr>
				<td><bean:message key="Origin_Address" /><font color=red> * </font>:</td>
				<td>
				<html:text name="worldTracerTTYForm"
					property="origin_address" size="15" maxlength="15" /></td>
				<td><bean:message key="Chargeable_Airline_Code" /><font color=red> * </font>:</td>
				<td colspan=2>
				<html:text name="worldTracerTTYForm"
					property="airline_code" size="5" maxlength="3" /></td>
			</tr>

			<tr>
				<td colspan="1"><bean:message key="File_Type" /> :</td>
				<td colspan="3">
				<html:select name="worldTracerTTYForm"
					property="file_type1" styleClass="dropdown">
					<html:option value=""></html:option>
					<html:option value="AHL">AHL</html:option>
					<html:option value="OHD">OHD</html:option>
					<html:option value="DPR">DPR</html:option>
				</html:select>
				&nbsp;
				<html:select name="worldTracerTTYForm"
					property="file_type2" styleClass="dropdown">
					<html:option value=""></html:option>
					<html:option value="AHL">AHL</html:option>
					<html:option value="OHD">OHD</html:option>
					<html:option value="DPR">DPR</html:option>
				</html:select>
				&nbsp;
				<html:select name="worldTracerTTYForm"
					property="file_type3" styleClass="dropdown">
					<html:option value=""></html:option>
					<html:option value="AHL">AHL</html:option>
					<html:option value="OHD">OHD</html:option>
					<html:option value="DPR">DPR</html:option>
				</html:select>
				&nbsp;
				<html:select name="worldTracerTTYForm"
					property="file_type4" styleClass="dropdown">
					<html:option value=""></html:option>
					<html:option value="AHL">AHL</html:option>
					<html:option value="OHD">OHD</html:option>
					<html:option value="DPR">DPR</html:option>
				</html:select>
				</td>
			</tr>
		    <tr>
				<td colspan="1"><bean:message key="File_Reference" /> :</td>
				<td colspan="3">
                <html:text name="worldTracerTTYForm"
					property="file_reference1" size="15" maxlength="12" />
				&nbsp;
				<html:text name="worldTracerTTYForm"
					property="file_reference2" size="15" maxlength="12" />
				<html:text name="worldTracerTTYForm"
					property="file_reference3" size="15" maxlength="12" />
				<html:text name="worldTracerTTYForm"
					property="file_reference4" size="15" maxlength="12" />
					
				</td>
			</tr>
		    <tr>
				<td colspan="1"><bean:message key="Text" /> <font color=red> * </font>:</td>
				<td colspan="3">
                <html:textarea name="worldTracerTTYForm"
					property="text" cols="60" rows="8" />					
				</td>
			</tr>
			<tr>
				<td colspan="4" align="center">
				<INPUT type="button" Id="button"
					value="Back" onClick="history.back()"> &nbsp; 
					<html:submit
					styleId="button" property="save">
					<bean:message key="button.tty" />
				</html:submit>
			    </td>
			</tr>
		</table>
</html:form>
