<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

  <tr>
    <td colspan="3" id="pageheadercell">
      <div id="pageheaderleft">
        <h1>
          <bean:message key="header.view_external_links" />
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
  	<td id="middlecolumn">
  		
  		<div id="maincontent">
	        <h1 class="green">
	          <bean:message key="header.externalLinks" />
	          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/view_created_requests.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
	        </h1>
	        <table class="links" cellspacing="0" cellpadding="0">
	        	<logic:present name="linkList" scope="request">
		            <logic:iterate id="link" name="linkList">
		              <tr>
		                <td class="link_image">
		                  <A HREF="<bean:write name="link" property="link_address"/>" target="blank" ><img src="deployment/main/images/nettracer/<bean:write name="link" property="image"/>"> </A>
		                </td>
		                <td class="link_description">
		                  <A HREF="<bean:write name="link" property="link_address"/>" target="blank" ><bean:write name="link" property="description"/> </A>
		                </td>
		              </tr>
		            </logic:iterate>
	            </logic:present>
	        </table>
        </div>
        
	</td>
  </tr>