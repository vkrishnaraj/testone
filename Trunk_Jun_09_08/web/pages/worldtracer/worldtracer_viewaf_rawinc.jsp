<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>NetTracer</title>

<link href="<%=request.getContextPath()%>/deployment/main/css/nettracerstyles1.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/deployment/main/css/formstyles.css" rel="stylesheet" type="text/css">

<script language="javascript">

function goinc() {
	var url;
	
	<logic:present name="wt_raw_incident" scope="request">
		url = "searchIncident.do?wt_id=<bean:write name="wt_raw_incident" scope="request"/>";
	</logic:present>
	<logic:present name="wt_raw_ohd" scope="request">
		url = "searchOnHand.do?wt_id=<bean:write name="wt_raw_ohd" scope="request"/>";
	</logic:present>
	window.opener.location.href=url;
	self.close();


} 

</script>
	
</head>

<body <logic:present name="wt_raw_hasinc" scope="request">onload="goinc();"</logic:present>>
<div id="maincontent">

<logic:notPresent name="wt_raw_hasinc" scope="request">
<table width="100%">
<tr>
  <td id="pageheadercell">
    <div id="pageheaderleft">
      <h1>
        <bean:message key="header.file_detail" />
        &nbsp;
        (
        <logic:present name="wt_raw_incident" scope="request">
        	<bean:write name="wt_raw_incident" scope="request"/>
        </logic:present>
        <logic:present name="wt_raw_ohd" scope="request">
        	<bean:write name="wt_raw_ohd" scope="request"/>
        </logic:present>
        )
      </h1>
    </div>

  </td>
</tr>
<tr>
	<td>

		<%
		String res = (String)request.getAttribute("wt_raw");
		if (res.indexOf("<input type=") > 0) {
			res = res.substring(0,res.indexOf("<input type="));
		}
       	out.println(res);
       	%>
	</td>
</tr>
<tr>
	<td nowrap>
		<script language="javascript">
		<!--
		function gopage(url) {
			window.opener.location.href=url;
			self.close();
		}
		//-->
		</script>
		
		<logic:present name="wt_raw_incident" scope="request">
			<INPUT id="button" type="button" value="<bean:message key="button.import_to_nt" />" onClick="gopage('searchIncident.do?wt_id=<bean:write name="wt_raw_incident" scope="request"/>')">

        </logic:present>
        <logic:present name="wt_raw_ohd" scope="request">
        	<INPUT id="button" type="button" value="<bean:message key="button.import_to_nt" />" onClick="gopage('searchOnHand.do?wt_id=<bean:write name="wt_raw_ohd" scope="request"/>')">
        </logic:present>

		
	</td>
</tr>


</table>
</logic:notPresent>

</div>
</body>
</html>