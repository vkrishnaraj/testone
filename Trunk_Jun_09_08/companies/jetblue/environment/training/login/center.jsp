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
<html:form action="/logon" focus="username" onsubmit="return validateLogonForm(this);">
  <div id=mainlogin>

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
              	<option value="B6" selected="selected">JetBlue Airlines</option>
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
              <html:text property="username" size="16" maxlength="18" styleClass="textfield" />
            </td>
          </tr>
          <tr>
            <td align=right valign=middle>
              <bean:message key="prompt.password" />
              :
            </td>
            <td>
              <html:password property="password" size="16" maxlength="25" redisplay="false" styleClass="textfield" />
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
                
      <table class=login2 width="50%" align=center>
		<tr><td width="780">

Thank you for evaluating NetTracer, an intuitive baggage tracing application.  We encourage you to try out all of the features contained within this application.  If you need assistance while using the application, click on the question mark icons to bring up the context sensitive help.  Please keep in mind that this instance of the application is for demonstration purposes only and the data will be periodically refreshed. 
<p>
If you have any questions, please do not hesitate to call or email us, we are happy to answer all of your questions.
<p>
The NetTracer Team 
<br><br><br>
<center>The information contained in this site is confidential and falls under the executed nondisclosure agreement between jetBlue airlines and The Owens Group.</center>
 
		</td></tr>
		</table>
                
              </div>
            </html:form>
            <html:javascript formName="logonForm" />
