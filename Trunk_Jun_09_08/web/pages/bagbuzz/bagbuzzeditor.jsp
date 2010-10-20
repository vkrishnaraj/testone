


<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>


<script type="text/javascript" src="deployment/main/js/fckeditor/fckeditor.js"></script>
<script type="text/javascript">
function displaymessage()
{
alert("Hello World!");
}
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
</td>
</tr>
<tr>
<td>
<script type="text/javascript">
<!--
// Automatically calculates the editor base path based on the _samples directory.
// This is usefull only for these samples. A real application should use something like this:
// oFCKeditor.BasePath = '/fckeditor/' ;	// '/fckeditor/' is the default value.
var sBasePath = 'deployment/main/js/fckeditor/';

var oFCKeditor = new FCKeditor( 'FCKeditor1' ) ;
oFCKeditor.BasePath	= sBasePath ;
oFCKeditor.Height	= 450 ;
oFCKeditor.Width    = 600 ;
oFCKeditor.Value	= '<p>This is some <strong>sample text<\/strong>. You are using <a href="http://www.fckeditor.net/">FCKeditor<\/a>.<\/p>' ;
oFCKeditor.Create() ;

function save(){
	request.data = oFCKeditor.Value;
}
//-->
		</script>

</td>
</tr>
<tr>
<td align="left" valign="top" colspan="12">
<input type="hidden" id="bb_id" name="bb_id" value="<bean:write name="bagbuzz" property="bagbuzz_id"/>"/>
<html:submit property="save" styleId="button" onclick="displaymessage()">
    <bean:message key="bagbuzz.save" />
</html:submit>
</td>
</tr>
</table>
<!--</div>-->
<!--</td>-->
<!--</tr>-->
</html:form>