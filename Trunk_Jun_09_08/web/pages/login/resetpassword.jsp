<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Company" %>

<% 
Agent a = (Agent) session.getAttribute("usertemp");
ResourceBundle bundle = ResourceBundle.getBundle(
		"com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(a.getCurrentlocale()));
Company c = com.bagnet.nettracer.tracing.bmo.CompanyBMO.getCompany(a.getCompanycode_ID());
String passSecure = bundle.getString("error.security.password.secure").replace("{0}","" + (c!=null?c.getVariable().getMin_pass_size():8));
String passMin = bundle.getString("error.security.password.minimal").replace("{0}","" + (c!=null?c.getVariable().getMin_pass_size():8));
%>

<html:form action="/passreset" focus="password1" onsubmit="return validatePassresetForm(this);">
  <html:javascript formName="passresetForm" />
  <div id=mainlogin>
    <table class=login2 align="center">
      <tr>
        <td colspan=2>
        <p>
          <h1 align="center">
            <bean:message key="header.reset_password" />
          </h1>
         </p>
        </td>
      </tr>
			<tr>
        <td align=right valign=middle>
          <bean:message key="prompt.username" />
          :
        </td>
        <td>
        	<bean:write name="usertemp" property="username" scope="session"/>
        </td>
      </tr>
      <tr>
        <td align=right valign=middle>
          <bean:message key="prompt.password" />
          :
        </td>
        <td>
          <html:password property="password1" size="16" maxlength="20" redisplay="false" styleClass="textfield" />
        </td>
      </tr>
      <tr>
        <td align=right valign=middle>
          <bean:message key="header.agentConfirmPassword" />
          :
        </td>
        <td>
          <html:password property="password2" size="16" maxlength="18" redisplay="false" styleClass="textfield" />
        </td>
      </tr>
      
      <tr>
        <td colspan=2 align=center>
        <p>
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <table align="center" style="width: 60%">
            <tr>
              <td align="left">
		       	  <font color="red">
		            <logic:present name="minimalPolicy" scope="request">
		            	<%=passMin%>
		            </logic:present>
		            <logic:present name="securePolicy" scope="request">
		         		<%=passSecure%>
		            </logic:present>
				  </font>
              </td>
            </tr>
          </table>
        </p>
        </td>
      </tr>
      <tr>
        <td colspan=2 align=center>
        <p>
        <html:submit property="resetpass" styleId="button">
          Reset Password
        </html:submit>
        <p>
        </td>
      </tr>

    </table>
  </div>
</html:form>