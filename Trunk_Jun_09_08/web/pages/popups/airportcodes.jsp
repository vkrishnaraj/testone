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

  int country = 0;
  String redirectKey = "";
  
  if (request.getParameter("type") != null) {
  	String type = request.getParameter("type");
  	if (type.equals("1")) country = 1;
  	else if (type.equals("2")) country = 2;
  }
  if (request.getParameter("key") != null) {
  	redirectKey = "key=" + request.getParameter("key");
  }
  
  String path     = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
  <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
  <html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
      <title>
        <bean:message key="title.airportcodes" />
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
	if (theForm.id == "dirtyCheck-form") {
		jQuery(theForm).addClass("dirty");
	}
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
                        Airport Codes
                      </h1>
                      Click on the airport to fill the field on the form.
                      <p align=right>
<%
						if (country != 0) {
%>
                        <a href="<%=path %>/pages/popups/airportcodes.jsp?<%=redirectKey%>&type=0">US Airports</a>
                        <br>
<%
						}
						if (country != 1) {
%>
                        <a href="<%=path %>/pages/popups/airportcodes.jsp?<%=redirectKey%>&type=1">Canada Airports</a>
                        <br>
<%
						}
						if (country != 2) {
%>
                        <a href="<%=path %>/pages/popups/airportcodes.jsp?<%=redirectKey%>&type=2">Non-US and Canada Airports</a>
<%
						}
%>
                      </p>
                      <table class="form2" cellspacing="0" cellpadding="0">
                        <tr>
                          <td nowrap>
<% 
							if (country == 0) {
%>
                            <b>
<%
                            String firstletter = null;
%>
                            <p>
                              <hr height="1" color="black">
                              <a name="us"><b>US Airports</b></a>
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
                                <logic:present name="airportlist" scope="session">
                                  <logic:iterate id="airports" name="airportlist" scope="session" type="com.bagnet.nettracer.tracing.db.Airport">
                                    <logic:equal name="airports" property="country" value="0">
<%
									  String air = airports.getAirport_desc().substring(0, 1).toUpperCase();

                                      if (!air.equalsIgnoreCase(firstletter)) {
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
                                        <a href="#" onclick="choosetype('<bean:write name="airports" property="airport_code"/>');"><bean:write name="airports" property="airport_desc" />
                                          -
                                          <bean:write name="airports" property="airport_code" /></a>
                                        <br>
                                      </logic:equal>
                                    </logic:iterate>
                               </logic:present>
                                    
<% 
							} else if (country == 1) {
%>
                                    
<%
                                    String firstletter = null;
%>
                                    <p>
                                      <hr height="1" color="black">
                                      <a name="canada"><b>Canada Airports</b></a>
                                      <p>
                                        <a href="#CAA">A</a>
                                        &nbsp;|&nbsp;
                                        <a href="#CAB">B</a>
                                        &nbsp;|&nbsp;
                                        <a href="#CAC">C</a>
                                        &nbsp;|&nbsp;
                                        <a href="#CAD">D</a>
                                        &nbsp;|
                                        <a href="#CAE">E</a>
                                        &nbsp;|&nbsp;
                                        <a href="#CAF">F</a>
                                        &nbsp;|&nbsp;
                                        <a href="#CAG">G</a>
                                        &nbsp;|&nbsp;
                                        <a href="#CAH">H</a>
                                        &nbsp;|
                                        <a href="#CAI">I</a>
                                        &nbsp;|&nbsp;
                                        <a href="#CAJ">J</a>
                                        &nbsp;|&nbsp;
                                        <a href="#CAK">K</a>
                                        &nbsp;|&nbsp;
                                        <a href="#CAL">L</a>
                                        &nbsp;|
                                        <a href="#CAM">M</a>
                                        &nbsp;|&nbsp;
                                        <a href="#CAN">N</a>
                                        &nbsp;|&nbsp;
                                        <a href="#CAO">O</a>
                                        &nbsp;|&nbsp;
                                        <a href="#CAP">P</a>
                                        &nbsp;|
                                        <a href="#CAQ">Q</a>
                                        &nbsp;|&nbsp;
                                        <a href="#CAR">R</a>
                                        &nbsp;|&nbsp;
                                        <a href="#CAS">S</a>
                                        &nbsp;|&nbsp;
                                        <a href="#CAT">T</a>
                                        &nbsp;|
                                        <a href="#CAU">U</a>
                                        &nbsp;|&nbsp;
                                        <a href="#CAV">V</a>
                                        &nbsp;|&nbsp;
                                        <a href="#CAW">W</a>
                                        &nbsp;|&nbsp;
                                        <a href="#CAX">X</a>
                                        &nbsp;|
                                        <a href="#CAY">Y</a>
                                        &nbsp;|&nbsp;
                                        <a href="#CAZ">Z</a>
                                        <hr height="1" color="black">
                                        <p>
                                <logic:present name="airportlist" scope="session">
                                  <logic:iterate id="airports" name="airportlist" scope="session" type="com.bagnet.nettracer.tracing.db.Airport">
                                    <logic:equal name="airports" property="country" value="1">
<%
									  String air = airports.getAirport_desc().substring(0, 1).toUpperCase();

                                      if (!air.equalsIgnoreCase(firstletter)) {
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
                                        <b><a name="CA<%= firstletter %>"><%= firstletter %></a></b>
                                        <P>
<%
                                        }
%>
                                        <a href="#" onclick="choosetype('<bean:write name="airports" property="airport_code"/>');"><bean:write name="airports" property="airport_desc" />
                                          -
                                          <bean:write name="airports" property="airport_code" /></a>
                                        <br>
                                      </logic:equal>
                                    </logic:iterate>
                               </logic:present>
                                            
<% 
							} else if (country == 2) {
%>
                                            
<%
                                            String firstletter = null;
%>
                                            <p>
                                              <hr height="1" color="black">
                                              <a name="international"><b>Non-US and Canada Airports</b></a>
                                              <p>
                                                <a href="#INA">A</a>
                                                &nbsp;|&nbsp;
                                                <a href="#INB">B</a>
                                                &nbsp;|&nbsp;
                                                <a href="#INC">C</a>
                                                &nbsp;|&nbsp;
                                                <a href="#IND">D</a>
                                                &nbsp;|
                                                <a href="#INE">E</a>
                                                &nbsp;|&nbsp;
                                                <a href="#INF">F</a>
                                                &nbsp;|&nbsp;
                                                <a href="#ING">G</a>
                                                &nbsp;|&nbsp;
                                                <a href="#INH">H</a>
                                                &nbsp;|
                                                <a href="#INI">I</a>
                                                &nbsp;|&nbsp;
                                                <a href="#INJ">J</a>
                                                &nbsp;|&nbsp;
                                                <a href="#INK">K</a>
                                                &nbsp;|&nbsp;
                                                <a href="#INL">L</a>
                                                &nbsp;|
                                                <a href="#INM">M</a>
                                                &nbsp;|&nbsp;
                                                <a href="#INN">N</a>
                                                &nbsp;|&nbsp;
                                                <a href="#INO">O</a>
                                                &nbsp;|&nbsp;
                                                <a href="#INP">P</a>
                                                &nbsp;|
                                                <a href="#INQ">Q</a>
                                                &nbsp;|&nbsp;
                                                <a href="#INR">R</a>
                                                &nbsp;|&nbsp;
                                                <a href="#INS">S</a>
                                                &nbsp;|&nbsp;
                                                <a href="#INT">T</a>
                                                &nbsp;|
                                                <a href="#INU">U</a>
                                                &nbsp;|&nbsp;
                                                <a href="#INV">V</a>
                                                &nbsp;|&nbsp;
                                                <a href="#INW">W</a>
                                                &nbsp;|&nbsp;
                                                <a href="#INX">X</a>
                                                &nbsp;|
                                                <a href="#INY">Y</a>
                                                &nbsp;|&nbsp;
                                                <a href="#INZ">Z</a>
                                                <hr height="1" color="black">
                                                <p>
                                <logic:present name="airportlist" scope="session">
                                  <logic:iterate id="airports" name="airportlist" scope="session" type="com.bagnet.nettracer.tracing.db.Airport">
                                    <logic:equal name="airports" property="country" value="2">
<%
									  String air = airports.getAirport_desc().substring(0, 1).toUpperCase();

                                      if (!air.equalsIgnoreCase(firstletter)) {
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
                                        <b><a name="IN<%= firstletter %>"><%= firstletter %></a></b>
                                        <P>
<%
                                        }
%>
                                        <a href="#" onclick="choosetype('<bean:write name="airports" property="airport_code"/>');"><bean:write name="airports" property="airport_desc" />
                                          -
                                          <bean:write name="airports" property="airport_code" /></a>
                                        <br>
                                      </logic:equal>
                                    </logic:iterate>
                               </logic:present>
                                                    
<% 
							}
%>
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
