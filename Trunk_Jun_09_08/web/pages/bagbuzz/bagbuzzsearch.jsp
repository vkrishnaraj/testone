<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.bagbuzz.BagBuzz" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>

<%@page import="com.bagnet.nettracer.reporting.ReportingConstants"%>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/ajax_forall.js"></SCRIPT>


<%
  Agent a = (Agent)session.getAttribute("user");
%>
<script type="text/javascript">
function newWindow(content){
	var window_dimensions = "toolbars=no,menubar=no,location=no,scrollbars=yes,resizable=yes,status=yes";
	var	newwindow2=window.open('','name',window_dimensions);
	var tmp = newwindow2.document;
		tmp.write(content);
		tmp.close();
}

function writeConsole(content) {
 top.consoleRef=window.open('','myconsole',
  'width=350,height=250'
   +',menubar=0'
   +',toolbar=1'
   +',status=0'
   +',scrollbars=1'
   +',resizable=1');
 top.consoleRef.document.writeln(
  '<html><head><title>Console</title></head>'
   +'<body bgcolor=white onLoad="self.focus()">'
   +content
   +'</body></html>'
 );
 top.consoleRef.document.close();
}

</script>
    <html:form action="bagbuzzeditor.do">
    
  
    
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
              <bean:message key="header.bagbuzzsearch" />
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
      	<logic:present name="admin" scope="request">
      	
  		  <h1 class="green">
      		<bean:message key="header.bagbuzz.admin.category" />
      	  </h1>
          <html:hidden property="admin_view" value="1"/>
          <html:hidden property="editcategory" value="" disabled="true"/>
          <html:hidden property="editcategoryindex" value="" disabled="true"/>
          <html:hidden property="deletecategory" value="" disabled="true"/>
      	  <table class="form2" cellspacing="0" cellpadding="0">
        	<logic:iterate id="bb_cat" indexId="i" name="bb_cat_list" scope="request" type="com.bagnet.nettracer.tracing.db.Category">
      	  	  <tr>
      	  	  	<td><html:text name="bb_cat" property="description" size="13" maxlength="100" styleClass="textfield" indexed="true" /></td>
      	  	  	<td><input type="submit" name="edit_cat_<%=i%>" value="<bean:message key="edit"/>" id="button"
      	  	  			onclick="document.bagbuzzeditorForm.editcategory.value = <%=bb_cat.getId()%>; document.bagbuzzeditorForm.editcategory.disabled = false;document.bagbuzzeditorForm.editcategoryindex.value = <%=i%>; document.bagbuzzeditorForm.editcategoryindex.disabled = false;" /></td>
      	  	  	<% if (bb_cat.getCategoryVal() == 1) { %>
      	  	  		<td><bean:message key="label.bagbuzz.default.category" /></td>
      	  	  	<% } else { %>
      	  	  		<td><input type="submit" name="delete_cat_<%=i%>" value="<bean:message key="delete"/>" onclick="document.bagbuzzeditorForm.deletecategory.value = <%=bb_cat.getId()%>; document.bagbuzzeditorForm.deletecategory.disabled = false;" id="button"/></td>
      	  	  	<% } %>
      	  	  </tr>
      	  	</logic:iterate>
      		<logic:present name="can_add_category" scope="request">
	      	  	<tr>
	      	  		<td><input type="text" id="newCategory" name="newCategory" class="textfield"></td>
	      	  		<td colspan="2"><input type="submit" name="addcategory" value="<bean:message key="add"/>" id="button"/></td>
	      	  	</tr>
	      	</logic:present>
      	  </table>
  		  <h1 class="green">
      		<bean:message key="header.bagbuzz.admin.messages" />
      	  </h1>
          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <b><bean:message key="bagbuzz.colname.description" /></b>
              </td>
              <td>
              	<b><bean:message key="bagbuzz.colname.category" /></b>
              </td>
              <td>
                <b><bean:message key="bagbuzz.colname.date" /></b>
              </td>
              <td>
                <b><bean:message key="bagbuzz.colname.status" /></b>
              </td>
              <td>
                <b><bean:message key="bagbuzz.colname.edit" /></b>
              </td>
              <td>
                <b><bean:message key="bagbuzz.colname.publish" /></b>
              </td>
              <td>
                <b><bean:message key="bagbuzz.colname.unpublish" /></b>
              </td>
              <td>
                <b><bean:message key="bagbuzz.colname.delete" /></b>
              </td>
              <td>
                <b><bean:message key="bagbuzz.colname.copy" /></b>
              </td>
            </tr>
        
        <logic:iterate id="bb" name="bb_list" scope="request" type="com.bagnet.nettracer.tracing.db.bagbuzz.BagBuzz">
        	<tr>
                <td nowrap>
                <a href="bagbuzzsearch.do?view=1&bb_id=<bean:write name="bb" property="bagbuzz_id"/>" onclick="window.open(this.href, 'child', 'height=440,width=800,scrollbars,resizable=yes'); return false"><bean:write name="bb" property="description" /></a><br />
                  &nbsp;
                </td>
                <td>
                	<bean:write name="bb" property="category.description" />
                  	&nbsp;
                </td>
				<td nowrap>
                  <bean:write name="bb" property="created_timestamp" />
                  &nbsp;
                </td>
				<td nowrap>
				<%if (bb.getStatus().getStatus_ID() == 86){ %>
				    <bean:message key="STATUS_KEY_86"/>
				<%} %>
				<%if (bb.getStatus().getStatus_ID() == 87){ %>
					<bean:message key="STATUS_KEY_87"/>
									<%} %>				
				<%if (bb.getStatus().getStatus_ID() == 88){ %>
					<bean:message key="STATUS_KEY_88"/>
									<%} %>
				<%if (bb.getStatus().getStatus_ID() == 95){ %>
					<bean:message key="STATUS_KEY_95"/>
									<%} %>
                  &nbsp;
                </td>
                <td nowrap>
                &nbsp;
                <%if (bb.getStatus().getStatus_ID() == 86 || bb.getStatus().getStatus_ID() == 95){ %>
                  <a href="bagbuzzeditor.do?bb_id=<bean:write name="bb" property="bagbuzz_id"/>" onclick="window.open(this.href, 'child', 'height=650,width=850,scrollbars'); return false"><bean:message key="bagbuzz.colname.edit" /></a><br />
                <%}%>
                </td>
                <td nowrap>
                &nbsp;
                <%if (bb.getStatus().getStatus_ID() == 86 || bb.getStatus().getStatus_ID() == 95){ %>
                  <a href="bagbuzzsearch.do?admin_view=1&publish=1&bb_id=<bean:write name="bb" property="bagbuzz_id"/>"><bean:message key="bagbuzz.colname.publish" /></a><br />
                <%} %>
                </td>
                <td nowrap>
                &nbsp;
                <%if (bb.getStatus().getStatus_ID() == 87){ %>
                  <a href="bagbuzzsearch.do?admin_view=1&unpublish=1&bb_id=<bean:write name="bb" property="bagbuzz_id"/>"><bean:message key="bagbuzz.colname.unpublish" /></a><br />
                <%} %>
                </td>
                <td nowrap>
                &nbsp;
                <%if (bb.getStatus().getStatus_ID() == 86 || bb.getStatus().getStatus_ID() == 95){ %>
                  <a href="bagbuzzsearch.do?admin_view=1&delete=1&bb_id=<bean:write name="bb" property="bagbuzz_id"/>"><bean:message key="bagbuzz.colname.delete" /></a><br />
                <%} %>
                </td>
                <td nowrap>
                &nbsp;
                <%if (bb.getStatus().getStatus_ID() == 86 
                		|| bb.getStatus().getStatus_ID() == 95
                		|| bb.getStatus().getStatus_ID() == 87){ %>
                  <a href="bagbuzzsearch.do?admin_view=1&copy=1&bb_id=<bean:write name="bb" property="bagbuzz_id"/>"><bean:message key="bagbuzz.colname.copy" /></a><br />
                <%} %>
                </td>
            </tr>
        	
        </logic:iterate>
        	  <tr>
                <td align="center" valign="top" colspan="12">
                  <html:submit styleId="button" onclick="window.open('bagbuzzeditor.do', 'child', 'height=650,width=850,scrollbars'); return false">
                    <bean:message key="bagbuzz.neweditor" />
                  </html:submit>
                </td>
              </tr>
        </table>
        </logic:present>
        <logic:notPresent name="admin" scope="request">
                  <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td width="40%">
                <b><bean:message key="bagbuzz.colname.description" /></b>
              </td>
              <td width="30%">
                <b><bean:message key="taskmanager.colname.date" /></b>
              </td>
              <td width="30%">
                <b><bean:message key="bagbuzz.colname.status" /></b>
              </td>
            </tr>
        
        <logic:iterate id="bbt" name="bbt_list" scope="request" type="com.bagnet.nettracer.tracing.db.taskmanager.BagBuzzTask">
        	<tr>
        	    <td nowrap>
                  <a href="bagbuzzsearch.do?view=1&bb_id=<bean:write name="bbt" property="bagBuzz.bagbuzz_id"/>&bbt_id=<bean:write name="bbt" property="task_id"/>" onclick="window.open(this.href, 'child', 'height=440,width=800,scrollbars,resizable=yes'); return false">
                  <%if (bbt.getBagBuzz().getDescription() != null && bbt.getBagBuzz().getDescription().trim().length() > 0){ %>
                  <bean:write name="bbt" property="bagBuzz.description" />
                  <%} else { %>
                  <bean:message key="bagbuzz.description.empty"/>
                  <%} %>
                  </a><br />
                </td>
				<td nowrap>
                  <bean:write name="bbt" property="opened_timestamp" />
                  &nbsp;
                </td>
				<td nowrap>
				<%if (bbt.getStatus().getStatus_ID() == 83){ %>
				    <bean:message key="bagbuzztask.status.new"/>
				<%} %>
				<%if (bbt.getStatus().getStatus_ID() == 84){ %>
					<bean:message key="bagbuzztask.status.read"/>
									<%} %>				
				<%if (bbt.getStatus().getStatus_ID() == 85){ %>
					<bean:message key="bagbuzztask.status.historic"/>
									<%} %>
                </td>
            </tr>
        	
        </logic:iterate>
        </table>
        </logic:notPresent>
        
        </div>
        </td>
        </tr>
        </html:form>
        