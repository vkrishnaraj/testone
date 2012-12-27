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
  <SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	

	function goprev_tag() {
		  o = document.forumSearchForm;
		  o.tag_prevpage.value = "1";
		  o.submit();
		}
		
		function gonext_tag() {
		  o = document.forumSearchForm;
		  o.tag_nextpage.value="1";
		  o.submit();
		}
		
		function gopage_tag(i) {
			  o = document.forumSearchForm;
			  o.tag_currpage.value = i;
			  o.submit();
		}

		function goprev_thread() {
			  o = document.forumSearchForm;
			  o.thread_prevpage.value = "1";
			  o.submit();
			}
			
			function gonext_thread() {
			  o = document.forumSearchForm;
			  o.thread_nextpage.value="1";
			  o.submit();
			}
			
			function gopage_thread(i) {
				  o = document.forumSearchForm;
				  o.thread_currpage.value = i;
				  o.submit();
			}
			
			function callAddTag(i) {
				  o = document.forumSearchForm;
				  o.addTag.value = i;
				  o.submit();
			}

  </SCRIPT>
 
        <html:form action="fraud_forum_search.do" method="post" enctype="multipart/form-data" >
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
                      <bean:message key="fraud.forum.search.title" />
                    </h1>
                    <font color=red>
                      <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
                    </font>
                    <br />
                    <table class="form2" cellspacing="0" cellpadding="0">
                      <tr>
                        <td>
                        	<bean:message key="fraud.forum.search.criteria.agent" />
                        	<br />
                        	<html:text property="searchInfo.createAgent" styleClass="textfield" />
                        </td>
                        <td>
                        	<bean:message key="fraud.forum.search.criteria.airline" />
                        	<br />
                        	<html:text property="searchInfo.createAirline" styleClass="textfield" />
                        </td>
		                <td nowrap>
		                  <bean:message key="fraud.forum.search.criteria.daterange" />
		                  (
		                  <%= a.getDateformat().getFormat() %>)
		                  <br>
		                  <html:text property="start" size="12" maxlength="11" styleClass="textfield" styleId="startTime" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.forumSearchForm.start,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">&nbsp;-
		                  <html:text property="end" size="12" maxlength="11" styleClass="textfield" styleId="endTime" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.forumSearchForm.end,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;">
		                </td>
                      </tr>
                      <tr>
                        <td colspan="3">
                        	<bean:message key="fraud.forum.search.criteria.text" />
                        	<br />
                        	<html:text property="searchInfo.text" styleClass="textfield" style="width: 97%;"/>
                        </td>
                      </tr>
					  <logic:notEmpty name="tag_searchList">
					  <tr>
					  	<td colspan="3">
					  		<logic:iterate indexId="i" id="tag" name="tag_searchList" type="aero.nettracer.fs.model.forum.FsForumTag">
					  			<%=tag.getName() %>&nbsp;&nbsp;&nbsp;
					  		</logic:iterate>
					  	</td>
					  </tr>
					  </logic:notEmpty>
		              <tr>
		                <td colspan="3" align="center" valign="top">
			                  <html:submit property="search" styleId="button">
			                    <bean:message key="button.retrieve" />
			                  </html:submit>
		                </td>
		              </tr>
                    </table>
                    <br />
                    <br />
                    <table>
                    <tr>
                    <td style="width: 25%;">
                    <h1 class="green">
                        	<bean:message key="fraud.forum.search.tag.title" />
                        	</h1>
					<input type="hidden" name="addTag"/>
              <table class="form2" cellspacing="0" cellpadding="0" width="100%">
                <logic:iterate indexId="i" id="tag" name="tag_resultList" type="aero.nettracer.fs.model.forum.FsForumTag" >
                  <tr>
                    <td>
                    	<% if (tag.getNumThreads() > 0) { %>
                      	<a href="#" onclick="callAddTag(<%=tag.getId() %>);return false;" ><%=tag.getName() %></a>
                      	<% } else { %>
                      	<%=tag.getName() %>
                      	<% } %>
                    </td>
                  </tr>
                </logic:iterate> 
					<logic:present name="tag_pages" scope="request">
                <tr>
				   <td>
				   	
					  <logic:equal name="tag_currpage" scope="request" value="0">
					    &lt;
					    <bean:message key="Back" />
					  </logic:equal>
					  <logic:notEqual name="tag_currpage" scope="request" value="0">
					    <a href="#" onclick="goprev_tag();return false;">&lt;
					      <bean:message key="Back" /></a>
					    &nbsp;
					  </logic:notEqual>
					  <logic:iterate id="pages" indexId="i" name="tag_pages" scope="request">
					<%
					int p = i.intValue() + 1;
					int curp = Integer.parseInt((String)request.getAttribute("tag_currpage"));
					if ((curp <= (i.intValue() + 15)) && (curp >=(i.intValue() - 15))) {
						if (request.getAttribute("tag_currpage").equals(i.toString())) {
							out.println("<b>" + p + "</b>");
						} else {
						  out.println("<a href='#' onclick='gopage_tag(" + i.intValue() + ");return false;'>" + p + "</a>&nbsp;");
						}
					}
					%>
					  </logic:iterate>
					  <logic:present name="tag_end" scope="request">
					    <bean:message key="Next" />
					    &gt;
					  </logic:present>
					  <logic:notPresent name="tag_end" scope="request">
					    <a href="#" onclick="gonext_tag();return false;"><bean:message key="Next" />
					      &gt;</a>
					  </logic:notPresent>
					  <br>
										
					<input type="hidden" name="tag_currpage" value='<bean:write name="tag_currpage" scope="request"/>'>
					<input type="hidden" name="tag_nextpage"/>
					<input type="hidden" name="tag_prevpage"/>

				   </td>
			    </tr>
					</logic:present>
              </table>
                    </td>
                    <td style="width: 75%;">
                    <h1 class="green">
                        	<bean:message key="fraud.forum.search.thread.title" />
                        	</h1>
              <table class="form2" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                      <td>
	                     <bean:message key="fraud.forum.search.thread.titlecol" />
                      </td>
	                  <td>
	                     <bean:message key="fraud.forum.search.thread.posts" />
	                  </td>
	                  <td>
	                     <bean:message key="fraud.forum.search.thread.files" />
	                  </td>
	                  <td>
	                     <bean:message key="fraud.forum.search.thread.attachments" />
	                  </td>
	                  <td>
	                     <bean:message key="fraud.forum.search.thread.createdate" />
	                  </td>
	                  <td>
	                     <bean:message key="fraud.forum.search.thread.lastedited" />
	                  </td>
                  </tr>
                <logic:iterate indexId="i" id="thread" name="thread_resultList" type="aero.nettracer.fs.model.forum.FsForumThreadInfo" >
                  <tr>
                      <td>
              			 <a href="fraud_forum_view.do?threadId=<%=thread.getId() %>"><%=thread.getTitle() %></a>
                      </td>
	                  <td>
	                     <%=thread.getNumPosts() %>
	                  </td>
	                  <td>
	                     <%=thread.getNumFiles() %>
	                  </td>
	                  <td>
	                     <%=thread.getNumAttachments() %>
	                  </td>
	                  <td>
	                     <%=thread.getCreateDateDisp() %>
	                  </td>
	                  <td>
	                     <%=thread.getEditedDateDisp() %>
	                  </td>
                  </tr>
                </logic:iterate> 
					<logic:present name="thread_pages" scope="request">
                <tr>
				   <td colspan="6">
				   	
					  <logic:equal name="thread_currpage" scope="request" value="0">
					    &lt;
					    <bean:message key="Back" />
					  </logic:equal>
					  <logic:notEqual name="thread_currpage" scope="request" value="0">
					    <a href="#" onclick="goprev_thread();return false;">&lt;
					      <bean:message key="Back" /></a>
					    &nbsp;
					  </logic:notEqual>
					  <logic:iterate id="pages" indexId="i" name="thread_pages" scope="request">
					<%
					int p = i.intValue() + 1;
					int curp = Integer.parseInt((String)request.getAttribute("thread_currpage"));
					if ((curp <= (i.intValue() + 15)) && (curp >=(i.intValue() - 15))) {
						if (request.getAttribute("thread_currpage").equals(i.toString())) {
							out.println("<b>" + p + "</b>");
						} else {
						  out.println("<a href='#' onclick='gopage_thread(" + i.intValue() + ");return false;'>" + p + "</a>&nbsp;");
						}
					}
					%>
					  </logic:iterate>
					  <logic:present name="thread_end" scope="request">
					    <bean:message key="Next" />
					    &gt;
					  </logic:present>
					  <logic:notPresent name="thread_end" scope="request">
					    <a href="#" onclick="gonext_thread();return false;"><bean:message key="Next" />
					      &gt;</a>
					  </logic:notPresent>
					  <br>
										
					<input type="hidden" name="thread_currpage" value='<bean:write name="thread_currpage" scope="request"/>'>
					<input type="hidden" name="thread_nextpage"/>
					<input type="hidden" name="thread_prevpage"/>

				   </td>
			    </tr>
					</logic:present>
		              <tr>
		                <td colspan="6" align="center" valign="top">
			                  <html:submit property="create" styleId="button">
			                    <bean:message key="fraud.forum.search.button.create" />
			                  </html:submit>
		                </td>
		              </tr>
              </table>
                    </td>
                    </tr>
                    </table>
                    
					
                  </html:form>
					