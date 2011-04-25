<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.TracerProperties"%>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%
  Agent a = (Agent)session.getAttribute("user");
	org.apache.struts.util.PropertyMessageResources myMessages = (org.apache.struts.util.PropertyMessageResources)
	request.getAttribute("org.apache.struts.action.MESSAGE");
	java.util.Locale                                myLocale   = (java.util.Locale)session.getAttribute(
	"org.apache.struts.action.LOCALE");

%>
  
  <%@page import="com.bagnet.nettracer.tracing.bmo.RequestOhdBMO"%>
<script language="javascript">
    
function goprev() {
  o = document.viewIncomingRequestForm;
  o.prevpage.value = "1";
  o.submit();
}

function gonext() {
  o = document.viewIncomingRequestForm;
  o.nextpage.value="1";
  o.submit();
}

function gopage(i) {
  o = document.viewIncomingRequestForm;
  o.currpage.value = i;
  o.submit();
}
function updatePagination() {
    return true;
}


  function batchReceive()
  {
    if (confirm("<%= (String)myMessages.getMessage(myLocale, "check.receive") %>?"))
    {  
      var checked = 0;
      var received="";
    
      for (var j=0;j<document.viewIncomingRequestForm.length;j++) 
      {
        currentElement = document.viewIncomingRequestForm.elements[j];
        if (currentElement.type=="checkbox")
        {
          if (currentElement.checked)
          {
            if (checked > 0) 
              received += ",";
            checked +=1;
            received +=currentElement.value;
          }
        }
      }

      if (checked < 1)
      {
        alert("<%= (String)myMessages.getMessage(myLocale, "error.validation.missingBags") %>");
      }
      else
      {
        document.viewIncomingRequestForm.close1.value="1";
        document.viewIncomingRequestForm.ohd_ID.value=received;
        document.viewIncomingRequestForm.submit();
      }
    }
  } 


  </script>
  <html:form action="incomingBags.do" method="post" onsubmit="fillzero(this.ohd_num, 13); return true;">
    <jsp:include page="/pages/includes/taskmanager_header.jsp" />
    <tr>
      
      <td id="middlecolumn">
        
        <div id="maincontent">
          <h1 class="green">
            <bean:message key="header.fraud_requests" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/incoming_bags.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>

          <br>
          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td colspan="11" align="center">
                &nbsp;
              </td>
            </tr>                  
            <tr>
            		<tr>
            			<td>
            				<b><bean:message key="colname.reference.id" /></b>
            			</td>
            			<td>
            				<b><bean:message key="colname.reference.type" /></b>
            			</td>
            			<td>
            				<b><bean:message key="colname.fraudresults.company" /></b>
            			</td>
            			<td>
            				<b><bean:message key="colname.fraudresults.claim_date" /></b>
            			</td>
            			<td>
            				<b><bean:message key="colname.fraudresults.match_summary" /></b>
            			</td>
            			<td>
            				<b><bean:message key="colname.fraudresults.details" /></b>
            			</td>
            		</tr>
            </tr>
            <logic:present name="requestList" scope="request">
              <logic:iterate id="requested" name="requestList" type="aero.nettracer.fs.model.detection.AccessRequest">
                <tr>
            			<td>
            				<bean:write name="requested" property="requestedDate" />
            			</td>
            			<td>
            				<b><bean:message key="colname.reference.type" /></b>
            			</td>
            			<td>
            				<b><bean:message key="colname.fraudresults.company" /></b>
            			</td>
            			<td>
            				<b><bean:message key="colname.fraudresults.claim_date" /></b>
            			</td>
            			<td>
            				<b><bean:message key="colname.fraudresults.match_summary" /></b>
            			</td>
            			<td>
            				<b><bean:message key="colname.fraudresults.details" /></b>
            			</td>

                </tr>
              </logic:iterate>
              
              <tr>
                <td colspan="11">
                  <jsp:include page="/pages/includes/pagination_incl.jsp" />
                  
                </td>
              </tr>
              
            </logic:present>
            <tr>
              <td colspan="11" align="center">
                &nbsp;
              </td>
            </tr>
          </table>
          
        </html:form>
