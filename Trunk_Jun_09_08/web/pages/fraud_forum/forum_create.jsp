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
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%
  Agent a = (Agent)session.getAttribute("user");

ResourceBundle bundle = ResourceBundle.getBundle(
		"com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(a.getCurrentlocale()));
	
  boolean ntUser = PropertyBMO.isTrue("nt.user");
  boolean ntfsUser = PropertyBMO.isTrue("ntfs.user");

%>
  
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
			
			function removeTag(i) {
				  o = document.forumViewForm;
				  o.removeTag.value = i;
				  o.submit();
			}
			
			function validatePost(form) {
				for (var j=0;j < form.length; j++) {
					currentElement = form.elements[j];
					currentElementName=currentElement.name;

					if (currentElementName.indexOf("thread.title") != -1) {  
				 		if (currentElement.value.length == 0) {
						    alert("<%= (String)bundle.getString( "fraud.forum.create.thread.title") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
						    currentElement.focus();
						    return false;
				  		}
					} else if (currentElementName.indexOf("newTitle") != -1) {  
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
				if (document.getElementsByName("removeTag").length == 0) {
				    alert("<%= (String)bundle.getString( "fraud.forum.create.thread.tags") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
				    document.getElementById("tag").focus();
				    return false;
				}
                if (document.getElementsByName("newFileID")[0].value.length > 0) {
                    return window.confirm("<%= (String)bundle.getString( "fraud.forum.view.link.warning") %>");
                }
				return true;
			}

  </SCRIPT>
 
        <html:form action="fraud_forum_create.do" method="post" enctype="multipart/form-data" >
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
                      <bean:message key="fraud.forum.create.title" />
                    </h1>
                    <font color=red>
                      <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
                    </font>
                    <br />
                    <table class="form2" cellspacing="0" cellpadding="0">
                      <tr>
                        <td>
                        	<bean:message key="fraud.forum.create.thread.title" />
                        	<br />
                        	<html:text name="forumViewForm" property="thread.title" styleClass="textfield" style="width:95%;" maxlength="100" />
                        </td>
                      </tr>
                  <!--    <tr>
                        <td>
                        	<bean:message key="fraud.forum.create.thread.tags" />
                        	<br />
				                  <select name="newTagID" id="tag" class="dropdown" >
				                  		<option value="0"><bean:message key="select.none"/></option>
                					<logic:iterate indexId="i" id="tag" name="forumViewForm" property="tags" type="aero.nettracer.fs.model.forum.FsForumTag" >
                			   			<option value="<%=tag.getId() %>" ><%=tag.getName() %></option>
									</logic:iterate>
				                  </select>&nbsp;
			                <html:submit property="addTag" styleId="button">
			                    <bean:message key="fraud.forum.create.thread.add.tag" />
			                </html:submit>
                        </td>
                      </tr> 
                      <logic:notEmpty name="forumViewForm" property="thread.tags">   --> 
                      <tr>
                        <td>
                        	<bean:message key="fraud.forum.create.thread.tags" />
                        	<br />
                			<logic:iterate indexId="i" id="ttag" name="forumViewForm" property="thread.tags" type="aero.nettracer.fs.model.forum.FsForumTag" >
                			  <select name="thread.tags[<%=i%>].id" id="tag[<%=i%>]" class="dropdown" >
				                  		<option value="0"><bean:message key="select.none"/></option>
                					<logic:iterate indexId="j" id="tag" name="forumViewForm" property="tags" type="aero.nettracer.fs.model.forum.FsForumTag" >
                			   			<option value="<%=tag.getId() %>" <% if (ttag.getId()==tag.getId()) {%>selected <%}%>><%=tag.getName() %></option>
									</logic:iterate>
				              </select>&nbsp;<a href="#" onclick="removeTag(<%=i %>)">[X]</a>&nbsp;&nbsp;&nbsp;
                			</logic:iterate>
			                <html:submit property="addTag" styleId="button">
			                    <bean:message key="fraud.forum.create.thread.add.tag" />
			                </html:submit>
                			<input type="hidden" name="removeTag" />
                		</td>
                	  </tr>
                	<!--   </logic:notEmpty>  -->
                    </table>
                    <br />
            		<jsp:include page="/pages/fraud_forum/forum_post.jsp" />
                    
					
                  </html:form>
					