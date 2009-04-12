<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Incident" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.reporting.ReportingConstants" %>


<%@page import="com.bagnet.nettracer.tracing.utils.TracerProperties"%>

<%
boolean isPPLC = Integer.toString(ReportingConstants.PPLC_RPT).equals(request.getParameter("toprint"));
 %>
 
<SCRIPT LANGUAGE="JavaScript">
  <!--
	
	function generateReceipt(frm) {
	
	<%
		if (request.getParameter("toprint") != null) {
	%>
		toprint = '<%=request.getParameter("toprint")%>';
	<%
		} else {
	%>
		toprint = '<%=ReportingConstants.LOST_DELAY_RECEIPT%>';
	<%
		}
  
        if (request.getParameter("bdo_id") != null ) {
	%>
        ref_id = '&bdo_id=<%=(String) request.getParameter("bdo_id") %>';
    <%
        } else {
    %>
        ref_id = '';
    <%
        }
    %>
		var output = "";
        var language = "";
    
		for (var j=0;j < frm.length; j++) {
          currentElement = frm.elements[j];
          currentElementName=currentElement.name;
          
          if (currentElementName.indexOf("outputtype") != -1) {
            var checked = currentElement.checked;
            if (checked) {
              output = currentElement.value;
            }
          }
          
          if (currentElementName.indexOf("language") != -1) {
            language = currentElement.value;
          }
        }
    
        
		openWindowWithBar('reporting?print=' + toprint + '&outputtype=' + output + "&language=" + language + ref_id,'Receipt',800,600);
        
		return self.close();
		

	}
// -->
</SCRIPT>
<link href="deployment/main/css/nettracerstyles1.css" rel="stylesheet" type="text/css">
<link href="deployment/main/css/formstyles.css" rel="stylesheet" type="text/css">
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/nettracer.js"></SCRIPT>
<table cellspacing="0" id="bodytable">
  <tr>
    <td id="topcell">
      <table cellspacing="0" id="toplayouttable">
        <tr>
          <td id="header" class="cssstandardthreecolumn">
            <div id="headercontent"></div>
          </td>
        </tr>
        <!-- HORIZONTAL MENU -->
        <tr>
          <td colspan="3" id="headermenucell"></td>
        </tr>
        <!-- END HORIZONTAL MENU -->
        <!-- PAGE HEADER/SEARCH -->
        <!-- MIDDLE COLUMN -->
        <td id="middlecolumn" width=760>
          <!-- MAIN BODY -->
          <div id="maincontent">
            <h1 class="green">
            <% 
            if (isPPLC) {
             %>
            	<bean:message key="header.pplc_options" />
            	<%
            	} else {
            	 %>
              <bean:message key="header.receipt_options" />
              <%
              }
               %>
              <a href="#"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
            </h1>
            <FORM NAME="xyz" method="post" action="" onSubmit="">
              <table class="form2" cellspacing="0" cellpadding="0">
                <tr>
                  <td>
                   <% 
            if (isPPLC) {
             %>
            	<bean:message key="colname.pplc_output_type" />
            	<%
            	} else {
            	 %>
                    <bean:message key="colname.receipt_output_type" />
                    <%
              }
               %>
                    :
                  </td>
                  <td>
                   <%
                   if (isPPLC) {
             %>
                       <input type="radio" value="0" name="outputtype" checked="checked">
	                   <bean:message key="radio.pdf" />
	                   <%} else if (!TracerProperties.isTrue(TracerProperties.SUPPRESSION_PRINTING_NONHTML)) { %>
                       <input type="radio" value="0" name="outputtype" checked="checked">
	                   <bean:message key="radio.pdf" />
                      <input type="radio" value="1" name="outputtype">          
                      <bean:message key="radio.html" />
    	                <logic:present name="advanced" scope="request">
    	                  <input type="radio" value="2" name="outputtype">	
    	                  <bean:message key="radio.xls" />
    	                  <input type="radio" value="3" name="outputtype">	
    	                  <bean:message key="radio.csv" />
    	                  <input type="radio" value="4" name="outputtype">	
    	                  <bean:message key="radio.xml" />
                        </logic:present>
                    <% } else { %>
                      <input type="radio" value="1" name="outputtype" checked="checked">          
                      <bean:message key="radio.html" />
                    <% } %>
                  </td>
                </tr>
                <tr>
                  <td>
                  <%
                   if (isPPLC) {
             %>
            	<bean:message key="colname.pplc_language" />
            	<%
            	} else {
            	 %>
                    <bean:message key="colname.receipt_language" />
                    <% } %>
                    :
                  </td>
                  <td>
                    <select name="language" class="dropdown">
                      <logic:iterate id="locale" name="receiptLocaleList" scope="session">
                        <option value='<bean:write name="locale" property="value"/>'>
                        <bean:write name="locale" property="label" />
                      </logic:iterate>
                    </select>
                  </td>
                </tr>
                <tr>
                  <td colspan="2">
                    &nbsp;
                  </td>
                </tr>
                <tr>
                  <td align="center" colspan="2">
                  <%
                   if (isPPLC) {
             %>
             <INPUT type='button' value='<bean:message key="button.createpplc"/>' id="button" onClick="generateReceipt(this.form);">
             <% } else { %>
                    <INPUT type='button' value='<bean:message key="button.createreceipt"/>' id="button" onClick="generateReceipt(this.form);">
                     <% } %>
                  </td>
                </tr>
              </table>
            </FORM>
          </div>
          <!-- END MAIN BODY -->
        </td>
        <!-- END MIDDLE COLUMN -->
      </tr>
    </table>
  </td>
</tr>
<tr>
  <td id="bottomcell">
    <table width=100% border=0>
      <tr>
        <td>
          <div id="copyright">
            <bean:message key="copyright.line1" />
            <br />
            <bean:message key="copyright.line2" />
          </div>
        </td>
        <td align=right width=1>
          <bean:message key="footer.current_version" />
        </td>
      </tr>
    </table>
  </td>
</tr>
</table>
