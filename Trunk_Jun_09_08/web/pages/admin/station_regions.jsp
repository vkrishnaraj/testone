<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>

                  <html:select styleClass="dropdown" name="stationForm" property="station_region">
                    <html:option value=""><bean:message key="select.please_select" /></html:option>
                    <html:option value="EASTERN REGION"><bean:message key="region.east" /></html:option>
                    <html:option value="SOUTHERN REGION"><bean:message key="region.south" /></html:option>
                    <html:option value="NORTHERN REGION"><bean:message key="region.north" /></html:option>
                    <html:option value="WESTERN REGION"><bean:message key="region.western" /></html:option>
                  </html:select>
