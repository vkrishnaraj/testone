<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>

<%@page import="com.bagnet.nettracer.tracing.forms.RegionForm"%>
<%@page import="com.bagnet.nettracer.tracing.db.Station"%>
<%@page import="java.util.List"%>


<html:form action="region.do" method="post">
		

<tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
              Maintain Regions
          </h1>
        </div>
        </td>
        </tr>
        
        <tr>
      <td id="middlecolumn">
        <div id="maincontent">
        <h1 class="green"><bean:message key="regions.manage.title" /></h1>
        <table class="form2">
			<tr>
				<td><bean:message key="regions.region" /></td>
				<td><bean:message key="regions.director" /></td>
				<td><bean:message key="regions.goal" /></td>
				<td><bean:message key="regions.action" /></td>
			</tr>
			<logic:iterate id="region" name="regionForm" property="regions">
			<logic:notEqual value="0" name="region" property="id">
			<tr>
				<td>
					<bean:write name="region" property="name" />
				</td>
				<td>
					<bean:write name="region" property="director" />
				</td>
				<td>
					<bean:write name="region" property="target" />
				</td>
				<td nowrap>
					<a href="region.do?edit_region=<bean:write name="region" property="id"/>"><bean:message key="regions.edit" /></a>
					<a href="region.do?delete_region=<bean:write name="region" property="id"/>" onclick="return confirm('Confirm Delete')"><bean:message key="regions.delete" /></a>
				</td>
			</tr>
			</logic:notEqual>
			</logic:iterate>
			  <tr>
                <td align="center" valign="top" colspan="12">
                  <html:submit styleId="button" property="new_region">
                    <bean:message key="regions.add" />
                  </html:submit>
                </td>
              </tr>
		</table>
		
		<h1 class="green"><bean:message key="regions.manage.stations" /></h1>
		<table border="0">
		<logic:iterate id="region" name="regionForm" property="regions" type="com.bagnet.nettracer.tracing.db.Region">
		<tr>
		<td>
		<table class="form2">
			<tr>
				<td align="center" valign="top" colspan="12">
					<b><bean:write name="region" property="name" /></b>
				</td>
			</tr>
				
			<tr>
				<td>
					<bean:message key="regions.station" />
				</td>
				<td>
					<bean:message key="regions.region" />
				</td>
				<td>
					<bean:message key="regions.goal" />
				</td>
			</tr>

			<logic:iterate indexId="i" id="station" name="regionForm" property="stations" type="com.bagnet.nettracer.tracing.db.Station">
			<% if((station.getRegion() == null && region.getId() == 0) || (station.getRegion() != null && station.getRegion().getId() == region.getId())){
				
				String propStationCode = "station["+station.getStation_ID()+"].stationcode";
				String propGoal = "station["+station.getStation_ID()+"].goal";
				String propRegion = "station["+station.getStation_ID()+"].region.id";
				%>
				<tr>
						<td>
							<bean:write name="regionForm" property="<%=propStationCode%>"/>
						</td>
						<td>
						<html:select name="regionForm" property="<%=propRegion%>" styleClass="dropdown" >
							<logic:iterate id="regionlist" name="regionForm" property="regions" type="com.bagnet.nettracer.tracing.db.Region">
							  <option value="<bean:write name="regionlist" property="id" />" <% if (station.getRegion().getId() == regionlist.getId()) { %> selected <% } %>>
			                    <bean:write name="regionlist" property="name" />
			                  </option>
							</logic:iterate>
						</html:select>
						</td>
						<td>
					<html:text name="regionForm" property="<%=propGoal%>" size="40" maxlength="40" styleClass="textfield" />
						</td>
					</tr>
					<% } %>
			</logic:iterate>
		
		</table>
		</td>
		</tr>
		</logic:iterate>
		<tr>
		<td align="center" valign="top" colspan="12">
                  <html:submit styleId="button" property="update_stations">
                    <bean:message key="regions.save" />
                  </html:submit>
                  </td>
                  </tr>
</table>
		</div>
		</td>
		</tr>
</html:form>