<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<script type="text/javascript">
String.prototype.trim = function () {
    return this.replace(/^\s*/, "").replace(/\s*$/, "");
};

function validate(){
	if(document.forms[0].regionName.value.trim().length > 0){
		alert(docuement.form[0].name.value);
		return true;
	} else {
		alert("Region name is required.");
		return false;
	}
}

</script>

<html:form action="region.do" method="post">

<tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
              <bean:message key="regions.edit.title" />
          </h1>
        </div>
        </td>
        </tr>
        
        <tr>
      <td id="middlecolumn">
			<div id="maincontent">
				<table class="form2">
					<tr>
					<td><bean:message key="regions.name" /></td>
					<td> <html:text styleId="regionName" name="regionForm" property="editRegion.name" 
					size="40" maxlength="40" styleClass="textfield" ><bean:write name="regionForm" property="editRegion" /></html:text></td>
					</tr>
										<tr>
					<td>
						<bean:message key="regions.director" />
					</td>
					<td>
					 <html:text name="regionForm" property="editRegion.director"
					size="40" maxlength="40" styleClass="textfield" />
					</td>
					</tr>
					<tr>
					<tr>
					<td>
						<bean:message key="regions.goal" />
					</td>
					<td>
					 <html:text name="regionForm" property="editRegion.target"
					size="40" maxlength="40" styleClass="textfield" />
					</td>
					<tr>
						<td align="center" valign="top" colspan="12">
						<html:submit styleId="button" property="save_region"  onclick="return validate();"><bean:message key="regions.save" /></html:submit>
						</td>
					</tr>
					
				</table>

			</div>
		</td>
      </tr>
      </html:form>