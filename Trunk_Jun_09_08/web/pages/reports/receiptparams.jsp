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
	%>
		
		var checked=frm.outputtype[0].checked;
		
		var output="";
		if (checked)
			output="0";
		else
		{
			checked=frm.outputtype[1].checked;		
			if (checked)
				output="1";
			else
				output="0";	
		}

		openWindowWithBar('reporting?print=' + toprint + '&outputtype=' + output,'Receipt',800,600);
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
              <bean:message key="header.receipt_options" />
              <a href="#"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
            </h1>
            <FORM NAME="xyz" method="post" action="" onSubmit="">
              <table class="form2" cellspacing="0" cellpadding="0">
                <tr>
                  <td>
                    <bean:message key="colname.receipt_output_type" />
                    :
                  </td>
                  <td>
                    <input type="radio" value="0" name="outputtype" checked>
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
                  </td>
                </tr>
                <tr>
                  <td>
                    <bean:message key="colname.receipt_language" />
                    :
                  </td>
                  <td>
                    <select name="language" class="dropdown">
                      <logic:iterate id="locale" name="localelist" scope="session">
                        <option value='<bean:write name="locale" property="value"/>'>
                        <logic:equal name="locale" property="value" value="en">
                          selected
                        </logic:equal>
                        >
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
                    <INPUT type='button' value='<bean:message key="button.createreceipt"/>' id="button" onClick="generateReceipt(this.form);">
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
