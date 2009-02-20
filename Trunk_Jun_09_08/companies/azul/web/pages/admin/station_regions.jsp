<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
  
  <html:select styleClass="dropdown" name="stationForm" property="station_region">
    <html:option value=""><bean:message key="select.please_select" /></html:option>
    <html:option value="Norte"><bean:message key="region.1" /></html:option>
    <html:option value="Nordeste"><bean:message key="region.2" /></html:option>
    <html:option value="Centro-Oeste"><bean:message key="region.3" /></html:option>
    <html:option value="Sudeste"><bean:message key="region.4" /></html:option>
    <html:option value="Sul"><bean:message key="region.5" /></html:option>
  </html:select>
