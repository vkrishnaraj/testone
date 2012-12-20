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
<%
  Agent a = (Agent)session.getAttribute("user");
	
  boolean ntUser = PropertyBMO.isTrue("nt.user");
  boolean ntfsUser = PropertyBMO.isTrue("ntfs.user");
  
  ForumViewForm fform = (ForumViewForm) session.getAttribute("forumViewForm");

%>
  
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
					
                    <h1 class="green">
                      <bean:message key="fraud.forum.view.reply" />
                    </h1>
                    <br />
                    <table class="form2" cellspacing="0" cellpadding="0">
                      <tr>
                        <td>
                        	<bean:message key="fraud.forum.view.reply.title" />
                        	<br />
                        	<html:text property="newTitle" styleClass="textfield" style="width: 97%;"/>
                        </td>
                      </tr>
                      <tr>
                        <td>
                        	<bean:message key="fraud.forum.view.reply.text" />
                        	<br />
	         					<textarea name="newText" cols="80" rows="3" class="textfield" 
	         					><%=fform.getNewText() == null ? "" : fform.getNewText() %></textarea>
                        </td>
                      </tr>
                      <tr>
                        <td>
                        	<bean:message key="fraud.forum.view.reply.files" />
                        	<br />
                        	<html:text property="newFileID" styleClass="textfield"/>&nbsp;
			                <html:submit property="linkFile" styleId="button">
			                    <bean:message key="fraud.forum.view.link.button" />
			                </html:submit>
                        </td>
                      </tr>
                      <logic:notEmpty name="forumViewForm" property="newFiles">
                      <tr>
                        <td>
                			<logic:iterate indexId="i" id="file" name="forumViewForm" property="newFiles" type="aero.nettracer.fs.model.FsClaim" >
                			  <a href="claim_resolution.do?claimId=<%=file.getId() %>"><%=file.getId() %></a>&nbsp;
                			</logic:iterate>
                		</td>
                	  </tr>
                	  </logic:notEmpty>
				      <tr>
				        <td>
				            <bean:message key="header.attachments" />:<br>
				            <table width="100%" border="0" cellspacing="0" cellpadding="0">
				               <tr align="center">
								  <td align="left" width="75%">
					                 <bean:message key="colname.attachname"/>
					              </td>
				                  <td align="left">
					                 <bean:message key="colname.action"/>
					              </td>
				               </tr>
				               <logic:iterate indexId="i" id="attachment" name="forumViewForm" property="newAttachments" type="aero.nettracer.fs.model.FsAttachment" >
								  <tr align="center">
									 <td align="left">
					                     <a href='retrieveAttachment?ID=<bean:write name="attachment" property="id"/>' target="top"><bean:write name="attachment" property="description"/></a>
					                 </td>
				                     <td align="left">
					                     <input type="submit" name="removeAttachment_<%= i %>" id="button" value="<bean:message key="button.delete_attachment"/>">
					                 </td>
				                  </tr>
							   </logic:iterate>
				            </table>
				            <br>
				            <center><input type="FILE" name='<%= "attachfile" %>' />
				                &nbsp;
				               <html:submit property="uploadAttachment" styleId="button">
				                  <bean:message key="header.addAttachments" />
				               </html:submit> 
				           </center>
				        </td>
				      </tr>
		              <tr>
		                <td align="center" valign="top">
			                  <html:submit property="create" styleId="button">
			                    <bean:message key="fraud.forum.view.reply.button" />
			                  </html:submit>
		                </td>
		              </tr>
                    </table>