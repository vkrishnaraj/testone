<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@page import="com.bagnet.nettracer.tracing.forms.MaintainCompanyForm"%>

<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%
  Agent  loginuser = (Agent)session.getAttribute("user");
%>

<% String a = "", b = "", c = "", d = "", e="", f="", g="";

if (request.getAttribute("pageState").equals(TracingConstants.COMPANY_PAGESTATE_INFO)) {
	a = "b";
} else if (request.getAttribute("pageState").equals(TracingConstants.COMPANY_PAGESTATE_SETTINGS)) {
	b = "b";
} else if (request.getAttribute("pageState").equals(TracingConstants.COMPANY_PAGESTATE_SECURITY)) {
	c = "b";
} else if (request.getAttribute("pageState").equals(TracingConstants.COMPANY_PAGESTATE_AUDITING)) {
	d = "b";
} else if (request.getAttribute("pageState").equals(TracingConstants.COMPANY_PAGESTATE_WEB_SERVICES)) {
	e = "b";
} else if (request.getAttribute("pageState").equals(TracingConstants.COMPANY_PAGESTATE_WORLDTRACER)) {
	f = "b";
} else if (request.getAttribute("pageState").equals(TracingConstants.COMPANY_PAGESTATE_MOVETOLZ)) {
	g = "b";
}
%>
<html:form action="companyAdmin.do" method="post" onsubmit="return validateThisForm(this);">
  <html:javascript formName="companyForm" />
  <% // BELOW JAVASCRIPT OVER-RIDES DEFAULT VALIDATOR CODE  %>
  <script type="text/javascript">
      var buttonSelected = null;
      var numberOfLzs = 0;
      
      function validateThisForm(form) {
        if (buttonSelected == null) {
        	return validateCompanyForm(this);
        } else if (buttonSelected == 'savelzlist' && document.getElementsByName("lz_mode")[0].value == 1) {
        	notDeleted = 0;
        	for (i=0; i<numberOfLzs; ++i) {
        		var deleteElement = document.getElementsByName("lz[" + i + "].delete")[0];
      			if (deleteElement.checked == false) {
      				notDeleted += 1;
      			}
        	}
        	if (notDeleted > 0) {
        		return true;
        	}
        } else if (buttonSelected == 'savelzlist' && document.getElementsByName("lz_mode")[0].value == 2) {
      		var totalPercent = 0;
      		buttonSelected = null;
      		<% // BELOW JAVASCRIPT VALIDATES LZ LIST  %>
      		for (i=0; i<numberOfLzs; ++i) {
      			var ignore = 0;
      			var element = document.getElementsByName("lz[" + i + "].percent")[0];
      			try {
      				var deleteElement = document.getElementsByName("lz[" + i + "].delete")[0];
      				if (deleteElement.checked == true) {
      					ignore = 1;
      				}
      			} catch (err) {
      				<% // Must not ignore default which cannot be deleted. %>
      			}
      			
      			if (ignore == 0) {
	      			var value = element.value;
	      			if (value.length > 0) {
	      			
	                    var tempArray = value.split('.');
	                    //Strip off leading '0'
	                    var zeroIndex = 0;
	                    var joinedString= tempArray.join('');
	                    while (joinedString.charAt(zeroIndex) == '0') {
	                        zeroIndex++;
	                    }
	                    var noZeroString = joinedString.substring(zeroIndex,joinedString.length);
						
	                    if (!jcv_isAllDigits(noZeroString) || tempArray.length > 2) {
	      					alert("<bean:message key="movetolz.alert.percent" />");
	      					element.focus();
	      					return false;
	      				}
	      				totalPercent += parseFloat(value); 
	      			} else {
	      				alert("<bean:message key="movetolz.alert.percent" />");
	      				element.focus();
	      				return false;
	      			} 
	      		}
      		}
      		
      		if (totalPercent == 100) {
      			return true;
      		} else {
      			alert("<bean:message key="movetolz.alert.100percent" /> " + totalPercent);
      			return false;
      		}
      	} else {
      		return true;  
      	}
      }
      
      function validateRequired(form) {
        var isValid = true;
        var focusField = null;
        var i = 0;
        var fields = new Array();

        var oRequired = eval('new ' + jcv_retrieveFormName(form) +  '_required()');

        for (var x in oRequired) {
            if (!jcv_verifyArrayElement(x, oRequired[x])) {
                continue;
            }
            var field = form[oRequired[x][0]];

            if (!jcv_isFieldPresent(field)) {
                //fields[i++] = oRequired[x][1];
                //isValid=false;
            } else if ((field.type == 'hidden' ||
                field.type == 'text' ||
                field.type == 'textarea' ||
                field.type == 'file' ||
                field.type == 'radio' ||
                field.type == 'checkbox' ||
                field.type == 'select-one' ||
                field.type == 'password')) {

                var value = '';
                // get field's value
                if (field.type == "select-one") {
                    var si = field.selectedIndex;
                    if (si >= 0) {
                        value = field.options[si].value;
                    }
                } else if (field.type == 'radio' || field.type == 'checkbox') {
                    if (field.checked) {
                        value = field.value;
                    }
                } else {
                    value = field.value;
                }

                if (trim(value).length == 0) {

                    if ((i == 0) && (field.type != 'hidden')) {
                        focusField = field;
                    }
                    fields[i++] = oRequired[x][1];
                    isValid = false;
                }
            } else if (field.type == "select-multiple") { 
                var numOptions = field.options.length;
                lastSelected=-1;
                for(loop=numOptions-1;loop>=0;loop--) {
                    if(field.options[loop].selected) {
                        lastSelected = loop;
                        value = field.options[loop].value;
                        break;
                    }
                }
                if(lastSelected < 0 || trim(value).length == 0) {
                    if(i == 0) {
                        focusField = field;
                    }
                    fields[i++] = oRequired[x][1];
                    isValid=false;
                }
            } else if ((field.length > 0) && (field[0].type == 'radio' || field[0].type == 'checkbox')) {
                isChecked=-1;
                for (loop=0;loop < field.length;loop++) {
                    if (field[loop].checked) {
                        isChecked=loop;
                        break; // only one needs to be checked
                    }
                }
                if (isChecked < 0) {
                    if (i == 0) {
                        focusField = field[0];
                    }
                    fields[i++] = oRequired[x][1];
                    isValid=false;
                }
            }   
        }
        if (fields.length > 0) {
           jcv_handleErrors(fields, focusField);
        }
        return isValid;
    }
    
    // Trim whitespace from left and right sides of s.
    function trim(s) {
        return s.replace( /^\s*/, "" ).replace( /\s*$/, "" );
    }
    
    </script>
  <tr>
    <td colspan="3" id="pageheadercell">
      <div id="pageheaderleft">
        <h1>
          <bean:message key="header.editCompany" />
        </h1>
      </div>
      <div id="pageheaderright">
        <table id="pageheaderright">
          <tr>
            <jsp:include page="/pages/includes/mail_incl.jsp" />
            <td>
              <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><bean:message key="Help" /></a>
            </td>
          </tr>
        </table>
      </div>
    </td>
  </tr>
  
  <tr>
    <td colspan="3" id="navmenucell">
      <div class="menu">
        <dl>
          <dd>
            <a href="companyAdmin.do?edit=1&companyCode=<%=request.getParameter("companyCode") %>&pageState=1"><span class="aa<%= a %>">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bb<%= a %>"><bean:message key="menu.company.information" /></span>
              <span class="cc<%= a %>">&nbsp;
                <br />
                &nbsp;</span></a>
          </dd>
		  <logic:present name="newCompany" scope="request">
		    <input type="hidden" name="newCompany" value="1" />
		  </logic:present>
		  <logic:notPresent name="newCompany" scope="request">

	          <dd>
	            <a href="companyAdmin.do?edit=1&companyCode=<%=request.getParameter("companyCode") %>&pageState=2"><span class="aa<%= b %>">&nbsp;
	                <br />
	                &nbsp;</span>
	              <span class="bb<%= b %>"><bean:message key="menu.company.application" /></span>
	              <span class="cc<%= b %>">&nbsp;
	                <br />
	                &nbsp;</span></a>
	          </dd>
	          <dd>
	            <a href="companyAdmin.do?edit=1&companyCode=<%=request.getParameter("companyCode") %>&pageState=7"><span class="aa<%= g %>">&nbsp;
	                <br />
	                &nbsp;</span>
	              <span class="bb<%= g %>"><bean:message key="menu.company.moveto" /></span>
	              <span class="cc<%= g %>">&nbsp;
	                <br />
	                &nbsp;</span></a>
	          </dd>
	          <dd>
	            <a href="companyAdmin.do?edit=1&companyCode=<%=request.getParameter("companyCode") %>&pageState=3"><span class="aa<%= c %>">&nbsp;
	                <br />
	                &nbsp;</span>
	              <span class="bb<%= c %>"><bean:message key="menu.company.security" /></span>
	              <span class="cc<%= c %>">&nbsp;
	                <br />
	                &nbsp;</span></a>
	          </dd>
	          <dd>
	            <a href="companyAdmin.do?edit=1&companyCode=<%=request.getParameter("companyCode") %>&pageState=4"><span class="aa<%= d %>">&nbsp;
	                <br />
	                &nbsp;</span>
	              <span class="bb<%= d %>"><bean:message key="menu.company.auditing" /></span>
	              <span class="cc<%= d %>">&nbsp;
	                <br />
	                &nbsp;</span></a>
	          </dd>
	          <dd>
	            <a href="companyAdmin.do?edit=1&companyCode=<%=request.getParameter("companyCode") %>&pageState=5"><span class="aa<%= e %>">&nbsp;
	                <br />
	                &nbsp;</span>
	              <span class="bb<%= e %>"><bean:message key="menu.company.webservices" /></span>
	              <span class="cc<%= e %>">&nbsp;
	                <br />
	                &nbsp;</span></a>
	          </dd>
	          <%
	          	//if (true) {
				if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_WORLD_TRACER, loginuser)) {
			  %>
	          
	          <dd>
	            <a href="companyAdmin.do?edit=1&companyCode=<%=request.getParameter("companyCode") %>&pageState=6"><span class="aa<%= f %>">&nbsp;
	                <br />
	                &nbsp;</span>
	              <span class="bb<%= f %>"><bean:message key="menu.company.worldtracer" /></span>
	              <span class="cc<%= f %>">&nbsp;
	                <br />
	                &nbsp;</span></a>
	          </dd>
	          
	          <% 
	          } 
	          %>
			</logic:notPresent>
        </dl>
      </div>
    </td>
  </tr>
  
  <!-- END PAGE HEADER/SEARCH -->
  <tr>
    <!-- MIDDLE COLUMN -->
    <td id="middlecolumn">
      <!-- MAIN BODY -->
      <div id="maincontent">

          <logic:equal name="pageState" scope="request" value="1">
			<jsp:include page="./companyTiles/information.jsp" />
		  </logic:equal>
		  
		  <logic:equal name="pageState" scope="request" value="2">
			<jsp:include page="./companyTiles/settings.jsp" />
          </logic:equal>
          
          <logic:equal name="pageState" scope="request" value="7">
			<jsp:include page="./companyTiles/movetolz.jsp" />
	      </logic:equal>
          
          <logic:equal name="pageState" scope="request" value="3">
			<jsp:include page="./companyTiles/security.jsp" />
	      </logic:equal>

	      <logic:equal name="pageState" scope="request" value="4">
			<jsp:include page="./companyTiles/auditing.jsp" />
		  </logic:equal>
		  
          <logic:equal name="pageState" scope="request" value="5">
			<jsp:include page="./companyTiles/webservices.jsp" />
	      </logic:equal>
	      
	      
          <logic:equal name="pageState" scope="request" value="6">
			<%
			if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_WORLD_TRACER, loginuser)) {
			%>
				<jsp:include page="./companyTiles/worldtracer.jsp" />
			<%
			} 
			%>	    
	      </logic:equal>
      </html:form>
