<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page language="java"import="java.lang.*,
                                java.util.*" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%
  Agent a = (Agent)session.getAttribute("user");

  if (a == null) {
    a = new Agent();
  }

  String path     = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
  <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
  <html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
      <title>
        <bean:message key="title.airlines" />
      </title>
      <link href="<%= path %>/deployment/main/css/nettracerstyles1.css" rel="stylesheet" type="text/css">
      <link href="<%= path %>/deployment/main/css/formstyles.css" rel="stylesheet" type="text/css">
	  <script language="javascript" src="<%=path%>/deployment/main/js/jquery-1.8.2.min.js"></script>
      <script language="javascript">
        
function choosetype(o) {
	var field = null;

<%
    if (request.getParameter("key") != null) {
%>

	for (i = 0;  i < window.opener.document.forms[0].elements.length; i++ ) {
		if (window.opener.document.forms[0].elements[i].name == '<%= request.getParameter("key") %>') {
			field = window.opener.document.forms[0].elements[i];
			field.value=o;
			break;
		}
	}

	var theForm = window.opener.document.forms[0];
	jQuery(theForm).addClass("dirty");
<%
  }
  if (request.getParameter("submitform") != null) {
%>
		window.opener.document.forms[0].submit();
<%
  }
%>



	
	self.close();
}


function returnFalse(e)
{
    return false;
}

document.oncontextmenu = returnFalse;


      </script>
    </head>
    <body>
      <logic:present name="user" scope="session">
        <table cellspacing="0" id="bodytable">
          <tr>
            <td id="topcell">
              <table cellspacing="0" id="toplayouttable">
                <tr>
                  
                  <td id="middlecolumn">
                    
                    <div id="maincontent">
                      <h1 class="green">
                        Airlines
                      </h1>
                      Click on the airline to fill the field on the form.
                      <table class="form2" cellspacing="0" cellpadding="0">
                        <tr>
                          <td nowrap>
                            <b>
<%
                            String firstletter = null;
%>
                            <p>
                              <hr height="1" color="black">
                              
                              <p>
                                <a href="#USA">A</a>
                                &nbsp;|&nbsp;
                                <a href="#USB">B</a>
                                &nbsp;|&nbsp;
                                <a href="#USC">C</a>
                                &nbsp;|&nbsp;
                                <a href="#USD">D</a>
                                &nbsp;|
                                <a href="#USE">E</a>
                                &nbsp;|&nbsp;
                                <a href="#USF">F</a>
                                &nbsp;|&nbsp;
                                <a href="#USG">G</a>
                                &nbsp;|&nbsp;
                                <a href="#USH">H</a>
                                &nbsp;|
                                <a href="#USI">I</a>
                                &nbsp;|&nbsp;
                                <a href="#USJ">J</a>
                                &nbsp;|&nbsp;
                                <a href="#USK">K</a>
                                &nbsp;|&nbsp;
                                <a href="#USL">L</a>
                                &nbsp;|
                                <a href="#USM">M</a>
                                &nbsp;|&nbsp;
                                <a href="#USN">N</a>
                                &nbsp;|&nbsp;
                                <a href="#USO">O</a>
                                &nbsp;|&nbsp;
                                <a href="#USP">P</a>
                                &nbsp;|
                                <a href="#USQ">Q</a>
                                &nbsp;|&nbsp;
                                <a href="#USR">R</a>
                                &nbsp;|&nbsp;
                                <a href="#USS">S</a>
                                &nbsp;|&nbsp;
                                <a href="#UST">T</a>
                                &nbsp;|
                                <a href="#USU">U</a>
                                &nbsp;|&nbsp;
                                <a href="#USV">V</a>
                                &nbsp;|&nbsp;
                                <a href="#USW">W</a>
                                &nbsp;|&nbsp;
                                <a href="#USX">X</a>
                                &nbsp;|
                                <a href="#USY">Y</a>
                                &nbsp;|&nbsp;
                                <a href="#USZ">Z</a>
                                <hr height="1" color="black">
                                <p>
                                  <logic:iterate id="airlines" name="companylistByName" scope="session" type="com.bagnet.nettracer.tracing.db.Company">
<%
                                    String air = airlines.getCompanydesc().substring(0, 1).toUpperCase();

                                    if (!air.equals(firstletter)) {
                                      if (firstletter != null) {
%>
                                        <p align=right>
                                          &nbsp;&nbsp;&uarr;
                                          <a href="#"><bean:message key="link.to_top" /></a>
                                        </p>
                                        <br>
<%
                                      }
                                      firstletter = air;
%>
                                      <b><a name="US<%= firstletter %>"><%= firstletter %></a></b>
                                      <P>
<%
                                      }
%>
                                      <a href="#" onclick="choosetype('<bean:write name="airlines" property="companyCode_ID"/>');"><bean:write name="airlines" property="companydesc" />
                                        -
                                        <bean:write name="airlines" property="companyCode_ID" /></a>
                                      <br>
                                    </logic:iterate>
                                    
                                  </td>
                                </tr>
                              </table>
                            </div>
                            <br>
                            <center><INPUT Id="button" type="button" value="<bean:message key="button.close"/>" onClick="self.close();">
                            
                          </td>
                          
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
              </logic:present>
            </body>
          </html>
