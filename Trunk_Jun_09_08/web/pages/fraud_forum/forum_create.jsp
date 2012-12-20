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
<%
  Agent a = (Agent)session.getAttribute("user");
	
  boolean ntUser = PropertyBMO.isTrue("nt.user");
  boolean ntfsUser = PropertyBMO.isTrue("ntfs.user");

%>
  
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
 
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
                        	<html:text name="forumViewForm" property="thread.title" size="5" styleClass="textfield" />
                        </td>
                      </tr>
                      <tr>
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
                      <logic:notEmpty name="forumViewForm" property="thread.tags">
                      <tr>
                        <td>
                			<logic:iterate indexId="i" id="tag" name="forumViewForm" property="thread.tags" type="aero.nettracer.fs.model.forum.FsForumTag" >
                			  <%=tag.getName() %>&nbsp;
                			</logic:iterate>
                		</td>
                	  </tr>
                	  </logic:notEmpty>
                    </table>
                    <br />
            		<jsp:include page="/pages/fraud_forum/forum_post.jsp" />
                    
					
                  </html:form>
					