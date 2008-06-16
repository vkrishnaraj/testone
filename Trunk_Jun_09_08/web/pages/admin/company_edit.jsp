<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="org.apache.struts.action.DynaActionForm" %>

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
            <jsp:include page="../includes/mail_incl.jsp" />
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
            <a href="companyAdmin.do?edit=1&companyCode=<%=request.getParameter("companyCode") %>"><span class="aa<%= a %>">&nbsp;
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
	          <tr>
	            <td>
	              <bean:message key="header.companyCode" />
	              :
	            </td>
	            <td>
	              <logic:notEqual name="companyForm" property="companyCode" value="">
	                <html:text styleClass="textfield" name="companyForm" property="companyCode" size="5" maxlength="3" readonly="true" />
	              </logic:notEqual>
	              <logic:equal name="companyForm" property="companyCode" value="">
	                <html:text styleClass="textfield" name="companyForm" property="companyCode" size="5" maxlength="3" />
	              </logic:equal>
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="header.companyDesc" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="companyDesc" size="50" maxlength="255" />
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.street_addr" />
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="addr1" size="50" maxlength="100" />
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.street_addr" />
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="addr2" size="50" maxlength="100" />
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.city" />
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="city" size="20" maxlength="100" />
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.state" />
	              :
	            </td>
	            <td>
	              <html:select styleClass="dropdown" name="companyForm" property="state_ID">
	                <html:option value="">
	                  <bean:message key="select.none" />
	                </html:option>
	                <html:options collection="statelist" property="value" labelProperty="label" />
	              </html:select>
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.country" />
	              :
	            </td>
	            <td>
	              <html:select styleClass="dropdown" name="companyForm" property="countrycode_ID">
	                <html:option value="">
	                  <bean:message key="select.none" />
	                </html:option>
	                <html:options collection="countrylist" property="value" labelProperty="label" />
	              </html:select>
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.zip" />
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="zip" size="12" maxlength="11" />
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.phone" />
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="phone" size="20" maxlength="25" />
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.email" />
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="email_address" size="50" maxlength="255" />
	            </td>
	          </tr>
			</logic:equal>
			<logic:equal name="pageState" scope="request" value="2">
	          <tr>
	            <td>
	           	  <html:hidden name="companyForm" property="companyCode" />
	              <bean:message key="colname.total_threads" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="total_threads" size="4" maxlength="4" />
	              (Default: 10)
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.seconds_wait" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="seconds_wait" size="4" maxlength="4" />
	              (Default: 0)
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.min_match_percent" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="min_match_percent" size="4" maxlength="4" />
	              (Default: 50)
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.mbr_to_lz_days" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="mbr_to_lz_days" size="4" maxlength="4" />
	              (Default: 5)
	            </td>
	          </tr>
	
	          <tr>
	            <td>
	              <bean:message key="colname.damaged_to_lz_days" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="damaged_to_lz_days" size="4" maxlength="4" />
	              (Default: 0)
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.miss_to_lz_days" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="miss_to_lz_days" size="4" maxlength="4" />
	              (Default: 0)
	            </td>
	          </tr>
	
	          <tr>
	            <td>
	              <bean:message key="colname.ohd_to_lz_days" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="ohd_to_lz_days" size="4" maxlength="4" />
	              (Default: 5)
	            </td>
	          </tr>
	          <logic:notEmpty name="stationList" scope="request">
	            <tr>
	              <td>
	                <bean:message key="colname.default_station_code" />
	                :
	              </td>
	              <td>
	                <html:select name="companyForm" property="default_station_code" styleClass="dropdown">
	                  <html:options collection="stationList" property="station_ID" labelProperty="stationcode" />
	                </html:select>
	              </td>
	            </tr>
	          </logic:notEmpty>
	          
	          <tr>
	            <td>
	              <bean:message key="colname.default_loss_code" />
	              :
	            </td>
	            <td>
	              
			<html:select name="companyForm" property="default_loss_code" styleClass="dropdown">
	      <html:option value="0">
	        <bean:message key="select.please_select" />
	      </html:option>
	      <html:options collection="losscodes" property="loss_code" labelProperty="combination" />
	    </html:select>
	    
	            </td>
	          </tr>
	          
	          <tr>
	            <td>
	              <bean:message key="colname.email_customer" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <select name="email_customer" style="dropdown">
	                <option value="1">
	                <logic:equal name="companyForm" property="email_customer" value="1">
	                  selected
	                </logic:equal>
	                >
	                <bean:message key="select.yes" />
	                <option value="0">
	                <logic:equal name="companyForm" property="email_customer" value="0">
	                  selected
	                </logic:equal>
	                >
	                <bean:message key="select.no" />
	              </select>
	            </td>
	          </tr>
	
	          <tr>
	            <td>
	              <bean:message key="colname.email_host" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="email_host" size="30" maxlength="100" />
	              (Default: localhost)
	            </td>
	          </tr>
	          
	          <tr>
	            <td>
	              <bean:message key="colname.email_port" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="email_port" size="4" maxlength="10" />
	              (Default: 25)
	            </td>
	          </tr>
	          
	          <tr>
	            <td>
	              <bean:message key="colname.email_from" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="email_from" size="30" maxlength="100" />
	            </td>
	          </tr>
	         	<tr>
	            <td>
	              <bean:message key="colname.email_to" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="email_to" size="30" maxlength="255" />
	            </td>
	          </tr>
	           
	          <tr>
	            <td>
	              <bean:message key="colname.max_image_file_size" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="max_image_file_size" size="5" maxlength="5" />
	            </td>
	          </tr>
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
	          <tr>
	            <td>
	              <bean:message key="colname.bak_nttracer_data_days" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="bak_nttracer_data_days" size="4" maxlength="4" />
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.bak_nttracer_ohd_data_days" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="bak_nttracer_ohd_data_days" size="4" maxlength="4" />
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.bak_nttracer_lostfound_data_days" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="bak_nttracer_lostfound_data_days" size="4" maxlength="4" />
	            </td>
	          </tr>
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
