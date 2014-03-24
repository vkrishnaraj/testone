


<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>


<script type="text/javascript" src="deployment/main/js/fckeditor/fckeditor.js"></script>
<script type="text/javascript">
var oFCKeditor;

String.prototype.trim = function () {
    return this.replace(/^\s*/, "").replace(/\s*$/, "");
};


function displaymessage()
{

alert(document.forms[0].FCKeditor1.value.trim());
alert(oFCKeditor.CreateHtml());
return true;
}

function validate(){
	if(document.forms[0].description.value.trim().length > 0){
		return true;
	} else {
		alert("Title is required");
		return false;
	}
}

function limitText(limitField, limitCount, limitNum) {
	if (limitField.value.length > limitNum) {
		limitField.value = limitField.value.substring(0, limitNum);
	} else {
		limitCount.value = limitNum - limitField.value.length;
	}
}
</script>
<script type="text/javascript">
window.onload = function()
{
var oFCKeditor = new FCKeditor( 'data' ) ;
oFCKeditor.BasePath = 'deployment/main/js/fckeditor/' ;
oFCKeditor.ToolbarSet = 'bagbuzz';
oFCKeditor.Height	= 450 ;
oFCKeditor.Width    = 800 ;
oFCKeditor.ReplaceTextarea() ;
};

</script>



<html:form action="bagbuzzsearch.do">
<input type="hidden" name="countdown" value="256"/>
<table>
<tr>
<td>
<h1><bean:message key="header.bagbuzzeditor" /></h1>
</td>
</tr>
<tr><td>
	<bean:message key="bagbuzz.colname.category" />&nbsp;
              <html:select name="bagbuzz" property="category.id" styleClass="dropdown">
                <html:options collection="bb_category_list" property="value" labelProperty="label" />
              </html:select>
</td></tr>
<tr>
<td>
<bean:message key="bagbuzz.colname.description"/>  
<textarea style="overflow:hidden;" rows="1" cols="20" name="description" onKeyDown="limitText(this.form.description,this.form.countdown,256);"  onKeyUp="limitText(this.form.description,this.form.countdown,256);"> <bean:write name="bagbuzz" property="description" /></textarea>
</td>
</tr>
<tr>
<td>
<textarea id="data" name="data"><bean:write name="bagbuzz" property="data" /></textarea>

</td>
</tr>
<tr>
<td align="left" valign="top" colspan="12">
<input type="hidden" id="bb_id" name="bb_id" value="<bean:write name="bagbuzz" property="bagbuzz_id"/>"/>
<html:submit property="save" styleId="button" onclick="return validate()">
    <bean:message key="bagbuzz.save" />
</html:submit>
</td>
</tr>
</table>

</html:form>