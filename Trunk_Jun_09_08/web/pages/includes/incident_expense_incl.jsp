<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>

          <table class='<%= request.getParameter("formCss") %>' cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <strong>
                  <bean:message key="colname.createdate" />
                </strong>
              </td>
              <td>
                <strong>
                  <bean:message key="colname.paycode" />
                </strong>
              </td>
              <td>
                <strong>
                  <bean:message key="colname.draft_br" />
                </strong>
              </td>
              <td>
                <strong>
                  <bean:message key="colname.draftreqdate_br" />
                </strong>
              </td>
              <td>
                <strong>
                  <bean:message key="colname.draftpaiddate_br" />
                </strong>
              </td>
              <td>
                <strong>
                  <bean:message key="colname.checkamt_br" />
                </strong>
              </td>
              <td>
                <strong>
                  <bean:message key="colname.voucheramt_br" />
                </strong>
              </td>
              <td>
                <strong>
                  <bean:message key="colname.mileageamt_br" />
                </strong>
              </td>
              <td>
                <strong>
                  <bean:message key="header.status" />
                </strong>
              </td>
              <td>
                <strong>
                  <bean:message key="header.approval_deny_date_br" />
                </strong>
              </td>
              <td>
                <strong>
                  <bean:message key="colname.modify" />
                </strong>
              </td>
            </tr>
<%
            double checktotal   = 0;
            double vouchertotal = 0;
            int    mileagetotal = 0;
            int    i            = -1;
            boolean samecurrency = true;
            String lastcurrency = "";
%>
            <logic:iterate id="expenselist" name="incidentForm" property="expenselist" type="com.bagnet.nettracer.tracing.db.ExpensePayout">
              <bean:define id="expensetype" name="expenselist" property="expensetype" type="com.bagnet.nettracer.tracing.db.ExpenseType" />
              <bean:define id="expenselocation" name="expenselist" property="expenselocation" type="com.bagnet.nettracer.tracing.db.Station" />
<%
              i++;
              if(expenselist.getStatus().getStatus_ID() != TracingConstants.EXPENSEPAYOUT_STATUS_DENIED) {
                checktotal   += expenselist.getCheckamt();
								if (lastcurrency == "") lastcurrency = expenselist.getCurrency_ID();
								if (!lastcurrency.equals(expenselist.getCurrency_ID())) samecurrency = false;
								
                if (expenselist.getStatus().getStatus_ID() == TracingConstants.EXPENSEPAYOUT_STATUS_PAID ) 
                  vouchertotal += expenselist.getVoucheramt();
                mileagetotal += expenselist.getMileageamt();
                }
%>
                <tr>
                  <td>
                    <bean:write name="expenselist" property="discreatedate" />
                  </td>
                  <td>
                    <bean:write name="expenselist" property="paycode" />
                  </td>
                  <td>
                    <bean:write name="expenselist" property="draft" />
                    &nbsp;
                  </td>
                  <td>
                    <bean:write name="expenselist" property="disdraftreqdate" />
                    &nbsp;
                  </td>
                  <td>
                    <bean:write name="expenselist" property="disdraftpaiddate" />
                    &nbsp;
                  </td>
                  <td align="right" nowrap>
                    <bean:write name="expenselist" property="discheckamt" />
                    &nbsp;
                    <bean:write name="expenselist" property="currency_ID" />
                  </td>
                  <td align="right">
                    &nbsp;
                    <bean:write name="expenselist" property="disvoucheramt" />
                  </td>
                  <td align="right">
                    &nbsp;
                    <bean:write name="expenselist" property="mileageamt" />
                  </td>
                  <td valign="top">
                    <bean:message name="expenselist" property="status.key" />
                    &nbsp;
                  </td>
                  <td>
                    <bean:write name="expenselist" property="dispapproval_date" />
                    &nbsp;
                  </td>
                  <logic:notEqual name="incidentForm" property="readonly" value="1">
                    <td>
                      <a href="EditExpense.do?expense_id=<bean:write name='expenselist' property='expensepayout_ID'/>"><bean:message key="colname.modify" /></a>
                    </td>
                  </logic:notEqual>
                </tr>
            </logic:iterate>
            <tr>
              <td>
                <b><bean:message key="colname.total_payout" />
                :
              </td>
              <td colspan="4">
                &nbsp;
              </td>
              <td align="right">
                <%
                if (samecurrency) out.println(TracingConstants.DECIMALFORMAT.format(checktotal)); 
                else  out.println("Multiple Currencies");
                %>
                <p>
                </td>
                <td align="right">
                  <%= TracingConstants.DECIMALFORMAT.format(vouchertotal) %>
                  <p>
                  </td>
                  <td align="right">
                    <%= mileagetotal %>
                    <p>
                    </td>
                  </tr>
                  <logic:notEmpty name="incidentForm" property="readonly" >
                  <logic:notEqual name="incidentForm" property="readonly" value="1">
                    <tr>
                      <td align="center" valign="top" colspan="12">
                        <html:button property="addnewexpense" styleId="button" 
                        	onclick="document.location.href='CreateExpense.do'">
                          <bean:message key="button.add_payout" />
                        </html:button>
                      </td>
                    </tr>
                  </logic:notEqual>
                  </logic:notEmpty>
                </table>
