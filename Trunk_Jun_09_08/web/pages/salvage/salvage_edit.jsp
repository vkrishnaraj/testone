<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.DateUtils" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%
	Agent a = (Agent)session.getAttribute("user");
  	String cssFormClass = "form2_dam";
%>
  
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	


  </SCRIPT>
  
  
  <jsp:include page="/pages/includes/validation_search.jsp" />
  <html:form focus="sId" action="salvageEdit.do" method="post" onsubmit="return validateSearch(this);">
  <input type="hidden" name="delete_these_elements" value="" />
  <jsp:include page="/pages/includes/taskmanager_header.jsp" />
    
    <tr>
      
      <td id="middlecolumn">        
        <div id="maincontent">
          <h1 class="green">
            <bean:message key="header.salvage_details" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
	      <span class="reqfield">*</span>
	      <bean:message key="message.required" /> 
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
            <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <bean:message key="colname.salvage_id" />
                  <br>
                  <html:text property="salvage.salvageId" size="10" styleClass="textfield" styleId="sId" disabled="true" />
                </td>
                <td>
                	<bean:message key="colname.salvage_status" />
                	<br>
                	<html:select property="salvage.status" styleClass="dropdown" disabled="true">
	                  <html:option value="0">
	                    <bean:message key="salvage.status_open" />
	                  </html:option>
	                  <html:option value="1">
	                    <bean:message key="salvage.status_closed" />
	                  </html:option>
	                </html:select>
                </td>
                <td nowrap>
                  <bean:message key="colname.salvage_date" />
                  (<%= a.getDateformat().getFormat() %>)
                  <br>
                  <html:text property="disSalvageDate" size="12" maxlength="11" styleClass="textfield" styleId="sDate" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.salvageEditForm.sDate,'calendar','<%= a.getDateformat().getFormat()%>'); return false;">
                </td>
                </tr>
                </table>
                <a name="baginfo"></a>
  <h1 class="green">
      <bean:message key="header.salvage_box_details" />
      <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#damaged_bag_reports/work_damaged_bag_information.htm');return false;">
          <img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0">
      </a>
      </h1>
      <span class="reqfield">*</span>
      <bean:message key="message.required" /> 
      <logic:iterate id="box" indexId="i" name="salvageEditForm" property="salvage.salvageBoxes" type="com.bagnet.nettracer.tracing.db.salvage.SalvageBox">
      <logic:notEqual name="box" property="type" value="0">
        <div id="<%= TracingConstants.JSP_DELETE_SALVAGE_BOX %>_<%=i%>">
        <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
		  <tr>
		  	<td>
		  		<bean:message key="colname.box_id" /><br />
		  		<html:text name="box" property="displayId" size="4" disabled="true" />
		  	</td>
		  	<td>
		  		<bean:message key="colname.description" /><br />
		  		<html:text name="box" property="description" maxlength="255" size="80" />
		  	</td>
		  	<td>
		  		<bean:message key="colname.value" /><br />
				<html:select name="box" property="type" styleClass="dropdown" >
				  <html:option value="" />
                  <html:option value="<%= String.valueOf(TracingConstants.SALVAGE_LOW_VALUE) %>">
                    <bean:message key="salvage.low_value" />
                  </html:option>
                  <html:option value="<%= String.valueOf(TracingConstants.SALVAGE_HIGH_VALUE) %>">
                    <bean:message key="salvage.high_value" />
                  </html:option>
                </html:select>
		  	</td>
		  </tr>
		  <logic:iterate indexId="j" id="item" name="box" property="salvageItems" >
          <tr id="<%= TracingConstants.JSP_DELETE_SALVAGE_ITEM %>_<%=i%>_<%=j%>">
            <td><br />
            	<input type="button" value="<bean:message key="button.delete_salvage_item" />"
            	onclick="hideThisElement('<%=TracingConstants.JSP_DELETE_SALVAGE_ITEM %>_<%=i %>_<%=j%>', 
                '<bean:message key="header.salvage" />', 0)"
            	id="button" >
           	</td>
           	<td>
           		<bean:message key="colname.description" /><br />
		  		<html:text name="item" property="description" maxlength="255" size="80" />
           	</td>
           	<td>
           		<bean:message key="colname.quantity" /><br />
		  		<html:text name="item" property="quantity" size="4" />
           	</td>
          </tr>
          </logic:iterate>
          <tr>
          <td colspan="3">&nbsp;</td>
          </tr>          
          <tr>
          <td colspan="3">
            <select name="additemNum">
	          <option value="1">1</option>
	          <option value="2">2</option>
	          <option value="3">3</option>
	          <option value="4">4</option>
	          <option value="5">5</option>
	        </select>
    
		    <html:submit property="additem" styleId="button">
		      <bean:message key="button.add_salvage_item" />
		    </html:submit>
          </td>
          </tr>
          <tr>
          <td colspan="3">
            <input type="button" value="<bean:message key="button.delete_salvage_box" />" 
            onclick="hideThisDiv('<%=TracingConstants.JSP_DELETE_SALVAGE_BOX %>_<%=i%>', 
            '<bean:message key="header.salvage" />')" >
          </td>
        </tr>
      </table><br />
      </div>
      </logic:notEqual>
    </logic:iterate>
   
    <center>
            <select name="addboxNum">
          <option value="1">1</option>
          <option value="2">2</option>
          <option value="3">3</option>
          <option value="4">4</option>
          <option value="5">5</option>
        </select>
    
    <html:submit property="addbox" styleId="button">
      <bean:message key="button.add_salvage_box" />
    </html:submit></center>
    <script language="JavaScript">
      


// for (var j=0;j<document.salvagesalvageEditForm.length;j++) {
//    currentElement = document.salvagesalvageEditForm.elements[j];
//    currentElementName=currentElement.name;
//    
//    if (currentElementName.indexOf("manufacturer_ID") != -1)
//    {
//    	var pos = currentElementName.indexOf("[");
//			var pos2 = currentElementName.indexOf("]");
//  		pos = currentElementName.substring(pos+1,pos2);
//			if (currentElement.value == TracingConstants.MANUFACTURER_OTHER_ID %>) {
//				document.getElementById("manu_other" + pos).style.visibility = "visible";
//  
//      } else {
//				document.getElementById("manu_other" + pos).style.visibility = "hidden";
//			}
//    }
//    
//	}

	

    </script>
    <br>
    <br>
    &nbsp;&nbsp;&uarr;
    <a href="#"><bean:message key="link.to_top" /></a>
    <br>
    <br>
            <tr>
              <td colspan="3" align="center" valign="top">
                	<html:button property="salvageId" styleId="button" onclick="document.location.href='salvageEdit.do?save=true'">
        				<bean:message key="button.save_salvage" />
        			</html:button>
					&nbsp;
                	<html:button property="salvageId" styleId="button" onclick="document.location.href='salvageEdit.do?submit=true'">
        				<bean:message key="button.submit_salvage" />
        			</html:button>
              </td>
            </tr>
            <tr><td>&nbsp;</td></tr>            
      	</div>
      	</td>
      	</tr>           
    </html:form>
