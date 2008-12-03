<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
  <tr>
    <td>
      <bean:message key="colname.min_interim_approval_check" />
      <font color=red>
	*
      </font>
      :
    </td>
    <td>
      <html:text styleClass="textfield" name="companyForm" property="min_interim_approval_check" size="4" maxlength="4" />
    </td>
  </tr>
  <tr>
    <td>
      <bean:message key="colname.min_interim_approval_voucher" />
      <font color=red>
	*
      </font>
      :
    </td>
    <td>
      <html:text styleClass="textfield" name="companyForm" property="min_interim_approval_voucher" size="4" maxlength="4" />
    </td>
  </tr>
  <tr>
    <td>
      <bean:message key="colname.min_interim_approval_miles" />
      <font color=red>
	*
      </font>
      :
    </td>
    <td>
      <html:text styleClass="textfield" name="companyForm" property="min_interim_approval_miles" size="4" maxlength="4" />
    </td>
  </tr>
