<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>

                  <html:select styleClass="dropdown" name="stationForm" property="station_region">
                    <html:option value=""><bean:message key="select.please_select" /></html:option>
                    <html:option value="ATLANTA REGION">Atlanta Region</html:option>
                    <html:option value="NORTHEAST REGION"><bean:message key="region.east" /></html:option>
                    <html:option value="SOUTHEAST AND CARIBBEAN REGION"><bean:message key="region.south" /></html:option>
                    <html:option value="NORTHWEST REGION"><bean:message key="region.north" /></html:option>
                    <html:option value="MIDCENTRAL REGION"><bean:message key="region.western" /></html:option>
                  </html:select>
