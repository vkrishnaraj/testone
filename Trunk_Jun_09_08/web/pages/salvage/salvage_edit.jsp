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
  
  
<%@page import="com.bagnet.nettracer.tracing.db.salvage.SalvageItem"%><SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
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
                	<html:select name="salvageEditForm" property="salvage.status" styleClass="dropdown" disabled="true">
	                  <html:option value="0">
	                    <bean:message key="salvage.status_open" />
	                  </html:option>
	                  <html:option value="1">
	                    <bean:message key="salvage.status_closed" />
	                  </html:option>
	                </html:select>
                </td>
                <td nowrap="nowrap">
                  <bean:message key="colname.salvage_date" />
                  (<%= a.getDateformat().getFormat() %>)
                  <br>
                  <html:text property="disSalvageDate" size="12" maxlength="11" styleClass="textfield" styleId="sDate" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.salvageEditForm.sDate,'calendar','<%= a.getDateformat().getFormat()%>'); return false;">
                </td>
                </tr>
                </table>
                
                
                
                <!--  BEGINNING OF OHDS -->
                  <h1 class="green">
      <bean:message key="header.salvage_onhands" />
      </h1>
      <span class="reqfield">*</span>
      <bean:message key="message.required" /> 
      
      

        <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
		  <tr>
		  	<td>
		  		<b><bean:message key="label.salvage_ohd" /></b><br />
		  	</td>
		  	<td>
		  		<b><bean:message key="label.salvage_ohd_action" /></b><br />
		  	</td>
		  </tr>
		  <logic:iterate id="salvageOhd" indexId="i" name="salvageEditForm" property="salvage.ohdReferences" type="com.bagnet.nettracer.tracing.db.salvage.SalvageOHDReference">
          <tr id="<%= TracingConstants.JSP_DELETE_SALVAGE_OHD %>_<%=i%>">
           	<td>
           		<bean:write name="salvageOhd" property="ohdId"/>
           	</td>
           	<td>
            	<input type="button" value="<bean:message key="button.delete_salvage_ohd" />"
            	onclick="hideThisElement('<%=TracingConstants.JSP_DELETE_SALVAGE_OHD %>_<%=i %>', 
                '<bean:message key="header.salvage" />', 0)"
            	id="button" >
           	</td>
          </tr>
          </logic:iterate>
          <tr>
          <td colspan="3">&nbsp;</td>
          </tr>          
          <tr>
          <td colspan="3" align="middle">
          <input type="text" size="13" value="" name="onhand">&nbsp;
		 	<input type="submit" name="addOnhands" value='<bean:message key="button.add_salvage_ohd" />' id="button"/>
          </td>
          </tr>
      </table><br />
      
      
    <p>&nbsp;</p>
   
    
                <!-- BEGINNING OF LOOSE ITEMS -->
                  <h1 class="green">
      <bean:message key="header.salvage_miscellaneous_items" />
      </h1>
      <span class="reqfield">*</span>
      <bean:message key="message.required" /> 
      
      
      <logic:iterate id="salvageBox" indexId="i" name="salvageEditForm" property="salvage.salvageBoxes" type="com.bagnet.nettracer.tracing.db.salvage.SalvageBox">
      <logic:equal name="salvageBox" property="type" value="0">
        <div id="<%= TracingConstants.JSP_DELETE_SALVAGE_BOX %>_<%=i%>">
        <a name="anchor_<%=i %>"></a>
        <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">

		  <logic:iterate indexId="j" id="salvageItem" name="salvageBox" property="salvageItems" >
          <tr id="<%= TracingConstants.JSP_DELETE_SALVAGE_ITEM %>_<%=i%>_<%=j%>">
            <td><br />
            	<input type="button" value="<bean:message key="button.delete_salvage_item" />"
            	onclick="hideThisElement('<%=TracingConstants.JSP_DELETE_SALVAGE_ITEM %>_<%=i %>_<%=j%>', 
                '<bean:message key="header.salvage" />', 0)"
            	id="button" >
           	</td>
           	<td>
           		<bean:message key="colname.description" /><br />
           		<input type="text" name="salvageBox[<%=i %>].salvageItem[<%=j %>].description" maxlength="255" size="60" value="<%=((SalvageItem)salvageItem).getDescription()%>" class="textfield">

           	</td>
           	<td>
           		<bean:message key="colname.quantity" /><br />
           		           		<input type="text" name="salvageBox[<%=i %>].salvageItem[<%=j %>].quantity" maxlength="255" size="4" value="<%=((SalvageItem)salvageItem).getQuantity()%>" class="textfield">
		  		
           	</td>
          </tr>
          </logic:iterate>
          <tr>
          <td colspan="3">&nbsp;</td>
          </tr>          
          <tr>
          <td colspan="3" align="middle">
            <input type="text" size="13" value="" name="addLostFoundId[<%=i %>]">&nbsp;
		 	<input type="submit" name="addLostFoundButton[<%=i %>]" value='<bean:message key="button.add_lost_found_item" />' id="button"/>
          </td>
          </tr>
      </table><br />
      </div>
      </logic:equal>
    </logic:iterate>
                <p>&nbsp;</p>
                
                <!--  BEGINNING OF BOXES -->
  <h1 class="green">
      <bean:message key="header.salvage_box_details" />
      </h1>
      <span class="reqfield">*</span>
      <bean:message key="message.required" /> 
      <logic:iterate id="salvageBox" indexId="i" name="salvageEditForm" property="salvage.salvageBoxes" type="com.bagnet.nettracer.tracing.db.salvage.SalvageBox">
      <logic:notEqual name="salvageBox" property="type" value="0">
        <div id="<%= TracingConstants.JSP_DELETE_SALVAGE_BOX %>_<%=i%>">
        <a name="anchor_<%=i %>"></a>
        <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
		  <tr>
		  	<td>
		  		<bean:message key="colname.box_id" /><br />
		  		<html:text name="salvageBox" property="displayId" size="4" disabled="true" indexed="true" styleClass="textfield"/>
		  	</td>
		  	<td>
		  		<bean:message key="colname.description" /><br />
		  		<html:text name="salvageBox" property="description" maxlength="255" size="80" indexed="true" styleClass="textfield"/>
		  	</td>
		  	<td>
		  		<bean:message key="colname.value" /><br />
				<html:select name="salvageBox" property="type" styleClass="dropdown" indexed="true">
                  <html:option value="<%= String.valueOf(TracingConstants.SALVAGE_LOW_VALUE) %>">
                    <bean:message key="salvage.low_value" />
                  </html:option>
                  <html:option value="<%= String.valueOf(TracingConstants.SALVAGE_HIGH_VALUE) %>">
                    <bean:message key="salvage.high_value" />
                  </html:option>
                </html:select>
		  	</td>
		  </tr>
		  <logic:iterate indexId="j" id="salvageItem" name="salvageBox" property="salvageItems" >
          <tr id="<%= TracingConstants.JSP_DELETE_SALVAGE_ITEM %>_<%=i%>_<%=j%>">
            <td><br />
            	<input type="button" value="<bean:message key="button.delete_salvage_item" />"
            	onclick="hideThisElement('<%=TracingConstants.JSP_DELETE_SALVAGE_ITEM %>_<%=i %>_<%=j%>', 
                '<bean:message key="header.salvage" />', 0)"
            	id="button" >
           	</td>
           	<td>
           		<bean:message key="colname.description" /><br />
           		<input type="text" name="salvageBox[<%=i %>].salvageItem[<%=j %>].description" maxlength="255" size="60" value="<%=((SalvageItem)salvageItem).getDescription()%>" class="textfield">

           	</td>
           	<td>
           		<bean:message key="colname.quantity" /><br />
           		           		<input type="text" name="salvageBox[<%=i %>].salvageItem[<%=j %>].quantity" maxlength="255" size="4" value="<%=((SalvageItem)salvageItem).getQuantity()%>" class="textfield">
		  		
           	</td>
          </tr>
          </logic:iterate>
          <tr>
          <td colspan="3">&nbsp;</td>
          </tr>          
          <tr>
          <td colspan="3" align="middle">
            <select name="addItemNum[<%=i %>]">
	          <option value="1">1</option>
	          <option value="2">2</option>
	          <option value="3">3</option>
	          <option value="4">4</option>
	          <option value="5">5</option>
	        </select>

		    <input type="submit" name="addItems[<%=i %>]" value='<bean:message key="button.add_salvage_item" />' id="button" />
		 	&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" size="13" value="" name="addLostFoundId[<%=i %>]">&nbsp;
		 	<input type="submit" name="addLostFoundButton[<%=i %>]" value='<bean:message key="button.add_lost_found_item" />' id="button"/>
          </td>
          </tr>
          <tr>
          <td colspan="3">
            <input type="button" value="<bean:message key="button.delete_salvage_box" />" 
            onclick="hideThisDiv('<%=TracingConstants.JSP_DELETE_SALVAGE_BOX %>_<%=i %>', 
            '<bean:message key="header.salvage" />')" id="button">
          </td>
        </tr>
      </table><br />
      </div>
      </logic:notEqual>
    </logic:iterate>
   
    <center>
        <select name="addBoxNum">
          <option value="1">1</option>
          <option value="2">2</option>
          <option value="3">3</option>
          <option value="4">4</option>
          <option value="5">5</option>
        </select>
    
    <input type="submit" name="addBoxes" value='<bean:message key="button.add_salvage_box" />' id="button" />
    
    <p>&nbsp;</p>
    <hr/>

  <p>&nbsp;</p>
              <logic:equal name="salvageEditForm" property="salvage.status" value="0">
              <input type="submit" name="save" value='<bean:message key="button.save_salvage" />' id="button" />
                	
        			
					<p>&nbsp;</p>
					<bean:message key="salvage.pickedUpByLName" />:&nbsp;
					<html:text property="salvage.pickedUpByLName" size="30" styleId="textfield"/>
					&nbsp;<input type="button" name="completeSalvageButton"
                      value='<bean:message key="button.submit_salvage" />'
                      id="button" onclick="if(confirm('<bean:message key="button.submit_salvage.confirm" />')) {document.forms[0].completeSalvage.value='1'; document.forms[0].submit();}"/>
                	<input type="hidden" name="completeSalvage" value="0"/>
                	</logic:equal>
            
        </center>
                        
      	</div>
      	</td>
      	</tr>           
    </html:form>
