


<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>





		<!-- 
			Include the WYSIWYG javascript files
		-->
		<script type="text/javascript" src="deployment/main/js/openwysiwyg_v1.4.7/scripts/wysiwyg.js"></script>
		<script type="text/javascript" src="deployment/main/js/openwysiwyg_v1.4.7/scripts/wysiwyg-settings.js"></script>
<!--		<link href="/tracer/deployment/main/js/openwysiwyg_v1.4.7/styles/wysiwyg.css" rel="stylesheet" type="text/css" />-->


<script language="javascript1.2">
  
   // attach the editor to the textarea with the identifier 'textarea1'.
   WYSIWYG.attach('textarea1');
</script>


<html:form action="bagbuzzsearch.do">
<!--    <tr>-->
<!--      <td id="middlecolumn">-->
<!--<div id="maincontent">-->
<table>
<tr>
<td>
<h1>BagBuzz Editor</h1>
</td>
</tr>
<tr>
<td>
Description:  
<textarea style="overflow:hidden;" rows="1" cols="20" name="description"> <bean:write name="bagbuzz" property="description" /></textarea>
<input type="FILE" name="theFile1" />
<html:submit
          property="uploadPhoto" styleId="button">
          <bean:message key="header.addPhotos" />
</html:submit>
</td>
</tr>
<tr>
<td>
<textarea type="hidden" class="tableTextareaEditor" id="textarea1" name="data" style=width:600px;height:320px;">
<bean:write name="bagbuzz" property="data" />
</textarea>
</td>
</tr>
<tr>
<td align="left" valign="top" colspan="12">
<input type="hidden" id="bb_id" name="bb_id" value="<bean:write name="bagbuzz" property="bagbuzz_id"/>"/>
<html:submit property="save" styleId="button">
    <bean:message key="bagbuzz.save" />
</html:submit>
</td>
</tr>
</table>
<!--</div>-->
<!--</td>-->
<!--</tr>-->
</html:form>