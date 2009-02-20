<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


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
                  <bean:message key="colname.incidental_br" />
                </strong>
              </td>
              <td>
                <strong>
                  <bean:message key="colname.draft_br" />
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
                  <bean:message key="colname.cc_refund_br" />
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
	    double    cctotal = 0;
	    double incTotal = 0;
            int    i            = -1;
            boolean samecurrency = true;
            String lastcurrency = "";
%>
            <logic:iterate id="expenselist" name="incidentForm" property="expenselist" type="com.bagnet.nettracer.tracing.db.ExpensePayout">
              <bean:define id="expensetype" name="expenselist" property="expensetype" type="com.bagnet.nettracer.tracing.db.ExpenseType" />
              <bean:define id="expenselocation" name="expenselist" property="expenselocation" type="com.bagnet.nettracer.tracing.db.Station" />
<%
              i++;
                checktotal   += expenselist.getCheckamt();
								if (lastcurrency == "") lastcurrency = expenselist.getCurrency_ID();
								if (!lastcurrency.equals(expenselist.getCurrency_ID())) samecurrency = false;
								
                vouchertotal += expenselist.getVoucheramt();
		cctotal += expenselist.getCreditCardRefund();
		if(expenselist.getIncidentalAmountClaimed() > 0.01) {
			incTotal += expenselist.getIncidentalAmountClaimed();
		}
		else {
			incTotal += expenselist.getIncidentalAmountAuth();
		}
		
%>
                <tr>
                  <td>
                    <bean:write name="expenselist" property="discreatedate" />
                  </td>
                  <td>
                    <bean:write name="expenselist" property="paycode" />
                  </td>
		<td align="right">
                    &nbsp;
                 <c:choose>
		        <c:when test="${expenselist.incidentalAmountClaimed > 0.01}" >
		 	  <fmt:formatNumber value="${expenselist.incidentalAmountClaimed}" type="currency" currencySymbol="" />
                  	</c:when>
                  	<c:otherwise>
		 	  <fmt:formatNumber value="${expenselist.incidentalAmountAuth}" type="currency" currencySymbol="" />
                  	</c:otherwise>
                  </c:choose>
                  </td>
			  
                  <td>
                    <bean:write name="expenselist" property="draft" />
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
		   <fmt:formatNumber value="${expenselist.creditCardRefund}" type="currency" currencySymbol="" />
                  </td>
                  <td valign="top">
                    <bean:write name="expenselist" property="status.description" />
                    &nbsp;
                  </td>
                  <td>
                    <bean:write name="expenselist" property="dispapproval_date" />
                    &nbsp;
                  </td>
                  <logic:notEqual name="incidentForm" property="readonly" value="1">
                    <td>
                      <a href="claim_resolution.do?modifyinterim=1&index=<%= i %>"><bean:message key="colname.modify" /></a>
                    </td>
                  </logic:notEqual>
                </tr>
            </logic:iterate>
            <tr>
              <td>
                <b><bean:message key="colname.total_payout" />
                :
              </td>
              <td >
                &nbsp;
              </td>
                <td align="right">
                  <%= TracingConstants.DECIMALFORMAT.format(incTotal) %>
                  <p>
                  </td>
              <td colspan="2">
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
                    <%= TracingConstants.DECIMALFORMAT.format(cctotal) %>
                    <p>
                    </td>
                  </tr>
                  <logic:notEqual name="incidentForm" property="readonly" value="1">
                    <tr>
                      <td align="center" valign="top" colspan="12">
                        <html:button property="addnewexpense" styleId="button" onclick="document.location.href='claim_resolution.do?addnewinterim=1'">
                          <bean:message key="button.add_payout" />
                        </html:button>
                      </td>
                    </tr>
                  </logic:notEqual>
                </table>
