<%@page import="com.bagnet.nettracer.tracing.forms.forum.ForumViewForm"%>
<%@page import="com.bagnet.nettracer.tracing.forms.ClaimForm"%>
<%@ page language="java" %>
<%@ page import="com.bagnet.nettracer.tracing.db.ExpensePayout" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/tags/struts-nested" prefix="nested" %>

<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO" %>
<%@ page import="com.bagnet.nettracer.tracing.forms.forum.ForumViewForm" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%
  Agent a = (Agent)session.getAttribute("user");

ResourceBundle bundle = ResourceBundle.getBundle(
		"com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(a.getCurrentlocale()));
	
  boolean ntUser = PropertyBMO.isTrue("nt.user");
  boolean ntfsUser = PropertyBMO.isTrue("ntfs.user");
  
  ForumViewForm fform = (ForumViewForm) session.getAttribute("forumViewForm");

%>
  
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
			
			function validatePost(form) {
				for (var j=0;j < form.length; j++) {
					currentElement = form.elements[j];
					currentElementName=currentElement.name;

					if (currentElementName.indexOf("newTitle") != -1) {  
				 		if (currentElement.value.length == 0) {
						    alert("<%= (String)bundle.getString( "fraud.forum.view.reply.title") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
						    currentElement.focus();
						    return false;
				  		}
					} else if (currentElementName.indexOf("newText") != -1) {  
				      	if (currentElement.value.length == 0) {
					        alert("<%= (String)bundle.getString( "fraud.forum.view.reply.text") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
					        currentElement.focus();
					        return false;
				      	}
					}
				}
                if (document.getElementsByName("newFileID")[0].value.length > 0) {
                    return window.confirm("<%= (String)bundle.getString( "fraud.forum.view.link.warning") %>");
                }
                return true;
			}

  </SCRIPT>
 
        <html:form action="fraud_forum_view.do" method="post" enctype="multipart/form-data" >
            <tr>
              <td colspan="3" id="pageheadercell">
                <div id="pageheaderleft">
                    <h1>
                      <bean:message key="fraud.forum.title" />
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
                        <bean:write name="forumViewForm" property="thread.title" />
                    </h1>
                    <font color=red>
                      <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
                    </font>
                    <br />
                    <table class="form2" cellspacing="0" cellpadding="0">
                      <tr>
                        <td>
                        	<bean:message key="fraud.forum.view.thread.agent" />
                        	<br />
                        	<bean:write name="forumViewForm" property="thread.createAgent" />
                        </td>
                        <td>
                        	<bean:message key="fraud.forum.view.thread.airline" />
                        	<br />
                        	<bean:write name="forumViewForm" property="thread.createAirline" />
                        </td>
                        <td>
                        	<bean:message key="fraud.forum.view.thread.createdate" />
                        	<br />
                        	<bean:write name="forumViewForm" property="createDateDisp" />
                        </td>
                      </tr>
                     </table>
                      <logic:notEmpty name="forumViewForm" property="thread.posts">
                    <h1 class="green">
                        	<bean:message key="fraud.forum.view.post" />
                     </h1>
                	<logic:iterate indexId="i" id="post" name="forumViewForm" property="thread.posts" type="aero.nettracer.fs.model.forum.FsForumPost" >
                    <table class="form2" cellspacing="0" cellpadding="0">
                      <tr>
                        <td>
                        	<strong><bean:write name="post" property="title" /></strong><br/>
                        	<span style="font-size:8px;" >
                        	<bean:message key="fraud.forum.view.post.agent" />&nbsp;
                        	<bean:write name="post" property="createAgent" />&nbsp;(
                        	<bean:write name="post" property="createAirline" />)&nbsp;On&nbsp;
                        	<bean:write name="post" property="createDateDisp" />
                        	</span>
                        </td>
                      </tr>
                      <tr>
                        <td style="width: 500px;">
                        	<bean:write name="post" property="textReadonly" filter="false"/>
                        </td>
                      </tr>
                      <logic:notEmpty name="post" property="claims">
                      <tr>
                        <td>
				                  <bean:message key="fraud.forum.view.post.files" />
				                  &nbsp;&nbsp;&nbsp;
                			<logic:iterate indexId="i" id="file" name="post" property="claims" type="aero.nettracer.fs.model.forum.FsForumPost_Claim" >
                			<% if (a.getCompanycode_ID().equals(file.getClaim_airline())) { %>
                			  <a href="claim_resolution.do?claimId=<%=file.getClaim_id() %>"><%=file.getClaim_airline() %><%=file.getClaim_id() %></a>
                			<% } else { %>
                			  <a href="fraud_forum_claim.do?claimId=<%=file.getClaim_id() %>&airline=<%=file.getClaim_airline() %>"><%=file.getClaim_airline() %><%=file.getClaim_id() %></a>
                			<% } %>&nbsp;&nbsp;&nbsp;
                			</logic:iterate>
                		</td>
                	  </tr>
                	  </logic:notEmpty>
                	  <% if (!"US".equals(a.getCompanycode_ID())) { %>
                	  <logic:notEmpty name="post" property="attachments">
                      <tr>
				                <td>
				                  <bean:message key="fraud.forum.view.post.attachments" />
				                 	 
				                      <logic:iterate indexId="i" id="attachment" name="post" property="attachments" type="aero.nettracer.fs.model.FsAttachment" >
										    &nbsp;&nbsp;&nbsp;
										  	<logic:notEqual name="attachment" property="id" value="-1">
				                           	<a href='retrieveAttachment?ID=<bean:write name="attachment" property="id"/>' target="top"><bean:write name="attachment" property="description"/></a>
				                           	</logic:notEqual>
				                           	
										  	<logic:equal name="attachment" property="id" value="-1">
										  		<bean:write name="attachment" property="description"/>
										  	</logic:equal>
										
				                      </logic:iterate>
				                  				              </td>
				            </tr>
				            </logic:notEmpty>
				            <% } %>
                      </table>
                	</logic:iterate>
                	</logic:notEmpty>
                    <br />
            		<jsp:include page="/pages/fraud_forum/forum_post.jsp" />
					
                  </html:form>
					