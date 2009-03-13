<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.AdminUtils,
                 com.bagnet.nettracer.tracing.db.Company" %>
<%@ page import="java.util.List,
                 java.util.Iterator" %>

    <table class=login2 width="50%" align=center>
      	<tr>
        	<td align="center" colspan=2>
          	<p>
            <h1>
              Log In
            </h1>
            <p>
            </td>
          </tr>
          
          <tr>
        	<td align="center" colspan=2>
          	<p>
            <font color="red"><html:errors/></font>
            <p>
            </td>
          </tr>
          
          
          <tr>
            <td align=right width=50% valign=middle>
              <bean:message key="prompt.company" />
              :
            </td>
            <td>
              <SELECT NAME="companyCode" class="dropdown">
              	<option value="US" selected="selected">WT Test</option>
              	<option value="OW">Owens Group</option>
              </SELECT>
            </td>
          </tr>
          <tr>
            <td align=right valign=middle>
              <bean:message key="prompt.username" />
              :
            </td>
            <td>
              <html:text property="username" size="16" maxlength="25" styleClass="textfield" />
            </td>
          </tr>
          <tr>
            <td align=right valign=middle>
              <bean:message key="prompt.password" />
              :
            </td>
            <td>
              <html:password property="password" size="16" maxlength="18" redisplay="false" styleClass="textfield" />
            </td>
          </tr>
          <tr>
            <td colspan=2 align=center>
              <p>
                <font color=red>
                  <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
                </font>
                <p>
                </td>
              </tr>
              <tr>
                <td colspan=2 align=center>
                  <p>
                    <html:submit value="Log In" styleId="button" />
                    <p>
                    </td>
                  </tr>
                </table>
