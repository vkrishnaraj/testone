<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.db.OHD_Photo" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%
  Agent a = (Agent)session.getAttribute("user");
  String cssFormClass;
 
  cssFormClass = "form2_dam";

  if (request.getAttribute("lostdelay") != null) {
    cssFormClass = "form2_ld";
  } else {
    if (request.getAttribute("missing") != null) {
      cssFormClass = "form2_pil";
    }
  }
%>

  
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
  function textCounter3(field, maxlimit) {
    if (field.value.length > maxlimit) {
      field.value = field.value.substring(0, maxlimit);
    } 
  }
  
  function checkBagType(pos) {
		var btype=document.getElementById("bagtype"+pos);
		var cr=document.getElementById("childRestraint");
		if(btype.value=="71"){
			document.getElementById("childRestraint"+pos).style.display = "block";
		} else {
			document.getElementById("childRestraint"+pos).style.display = "none";
		}
		
	}

</SCRIPT>

  <SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	

	

	function showmanu(o) {
	
  	var pos = o.name.indexOf("[");
		var pos2 = o.name.indexOf("]");
		pos = o.name.substring(pos+1,pos2);

		if (o.value == <%= TracingConstants.MANUFACTURER_OTHER_ID %>) {
			document.getElementById("manu_other" + pos).style.visibility = "visible";
		} else {
			document.getElementById("manu_other" + pos).style.visibility = "hidden";
		}
	}
	

  </SCRIPT>
  
<%
  int report_type = 0;

  if (request.getAttribute("lostdelay") != null) {
    report_type = 1;
  } else {
    if (request.getAttribute("missing") != null) {
      report_type = 2;
    }
  }
%>
  
  <a name="baginfo"></a>
  <h1 class="green">
<%
    if (report_type == 0) {
%>
      <bean:message key="header.damaged_bag_info" />
      <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#damaged_bag_reports/work_damaged_bag_information.htm');return false;"><%
      } else if (report_type == 1) {
%>
        <bean:message key="header.bag_info" />
        <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_lost_delayed_reports.htm#report info fields');return false;"><%
        } else if (report_type == 2) {
%>
          <bean:message key="header.bag_info" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#missing_articles_reports/work_with_baggage_information_(ma).htm#top');return false;"><%
          }
%>
          <img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
      </h1>
      <span class="reqfield">*</span>
      <bean:message key="message.required" />
      <logic:iterate id="theitem" indexId="i" name="incidentForm" property="itemlist" type="com.bagnet.nettracer.tracing.db.Item">
        <div id="<%=TracingConstants.JSP_DELETE_ITEM %>_<%=i%>">
        <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
          <tr>
            <td valign="top">
              <b><a name='additem<%= i %>'></a>
              <bean:message key="colname.bag_number" />
              : &nbsp;&nbsp;
              <%= theitem.getBagnumber() + 1 %></b>
            </td>
<%
            if (report_type != 1) {
%>
              <td colspan="2">
                <bean:message key="colname.claimnum.req2" />
                <br>
                <html:text name="theitem" property="claimchecknum" size="10" maxlength="10" styleClass="textfield" indexed="true" />
              </td>
<%
            } else {
%>
              <logic:notEqual name="incidentForm" property="incident_ID" value="">
                <td>
                  <bean:message key="colname.matched_claimcheck" />
                  <br>
                  <bean:write name="theitem" property="claimchecknum" />
                  &nbsp;
                </td>
                <td>
                  <bean:message key="colname.matched_ohd" />
                  <br>
                  <logic:notEmpty name="theitem" property="OHD_ID">
                    <a class="matchlink" href='addOnHandBag.do?ohd_ID=<bean:write name="theitem" property="OHD_ID"/>'><bean:write name="theitem" property="OHD_ID" /></a>
                    &nbsp;
                    <logic:notPresent name="cantmatch" scope="request">
                      <input type="submit" name="unmatchbag<%= i %>" value='<bean:message key="button.un_match"/>' id="button">
                    </logic:notPresent>
                  </logic:notEmpty>
                  <logic:empty name="theitem" property="OHD_ID">
                    <logic:notPresent name="cantmatch" scope="request">
                      <html:text name="theitem" property="tempOHD_ID" size="13" maxlength="13" styleClass="textfield" indexed="true" onblur="fillzero(this,13);" />
                      <input type="submit" name="matchbag<%= i %>" value='<bean:message key="button.do_match"/>' id="button" onclick="fillzero(document.incidentForm.tempOHD_ID, 13); return true;">
                    </logic:notPresent>
                  </logic:empty>
                </td>
              </logic:notEqual>
              <logic:equal name="incidentForm" property="incident_ID" value="">
                <td colspan="2">
                  &nbsp;
                </td>
              </logic:equal>
<%
            }
%>
          </tr>
          <tr>
            <td>
              <bean:message key="colname.last_name_onbag" />
              <br>
              <html:text name="theitem" property="lnameonbag" size="30" maxlength="50" styleClass="textfield" indexed="true" />
            </td>
            <td>
              <bean:message key="colname.first_name_onbag" />
              <br>
              <html:text name="theitem" property="fnameonbag" size="30" maxlength="50" styleClass="textfield" indexed="true" />
            </td>
            <td>
              <bean:message key="colname.mid_initial_onbag" />
              <br>
              <html:text name="theitem" property="mnameonbag" size="1" maxlength="1" styleClass="textfield" indexed="true" />
            </td>
          </tr>
          <tr>
            <td valign="top">
              <bean:message key="colname.color.req" />
            
              <a href="#" onclick="openChart2('pages/popups/bagtypechart.jsp?charttype=3&key=theitem[<%= i %>].color&type=color',800,10,230);return false;"><bean:message key="chart3" /></a>
              <br>
              <html:select name="theitem" property="color" indexed="true" styleClass="dropdown">
                <html:options collection="colorlist" property="value" labelProperty="label" />
              </html:select>
            
              <br>
              <bean:message key="colname.bagtype.req" />
              <a href="#" onclick="openChart2('pages/popups/bagtypechart.jsp?charttype=1&key=theitem[<%= i %>].bagtype&type=bagtype',800,280,230);return false;"><bean:message key="chart1" /></a>
              <a href="#" onclick="openChart2('pages/popups/bagtypechart.jsp?charttype=2&key=theitem[<%= i %>].bagtype&type=bagtype',800,370,230);return false;"><bean:message key="chart2" /></a>
              <br>
              <% String bagpos="bagtype"+i;	
              	String funcCall = "checkBagType(" + i + ")"; %> 
			  <html:select name="theitem" styleId="<%=bagpos%>"	property="bagtype" styleClass="dropdown" indexed="true"	onchange="<%=funcCall%>">
						  <html:options collection="typelist" property="value" labelProperty="label" />
              </html:select>
              <div id="childRestraint<%=i %>" style="display:none">
              	<% if(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_CHILD_RESTRAINT_SYSTEM, a) && theitem.getItemtype_ID()!=2){ %>
              	<bean:message key="colname.child.restraint.system" /><br>
              	 	<html:select name="theitem" property="childRestraint" styleClass="dropdown" indexed="true">
						<html:option value="0"><bean:message key="child.restraint.0"/></html:option>
						<html:option value="1"><bean:message key="child.restraint.1"/></html:option>
						<html:option value="2"><bean:message key="child.restraint.2"/></html:option>
			        </html:select>
			        <% } %>
              </div>
            </td>
            <td valign="top">
              <bean:message key="colname.x_desc" />
              <br>
            
              <html:select name="theitem" property="xdescelement_ID_1" styleClass="dropdown" indexed="true">
                <html:option value="">
                  <bean:message key="select.please_select" />
                </html:option>
                <html:options collection="xdescelementlist" property="XDesc_ID" labelProperty="description" />
              </html:select>
                <a href="#" onclick="openChart2('pages/popups/bagtypechart.jsp?charttype=4&xdescelement=theitem[<%= i %>].xdescelement_ID_1&type=xdescelement_ID_1',800,30,230);return false;"><bean:message key="chart4" /></a>
              <br>
              <html:select name="theitem" property="xdescelement_ID_2" styleClass="dropdown" indexed="true">
                <html:option value="">
                  <bean:message key="select.please_select" />
                </html:option>
                <html:options collection="xdescelementlist" property="XDesc_ID" labelProperty="description" />
              </html:select>
                <a href="#" onclick="openChart2('pages/popups/bagtypechart.jsp?charttype=4&xdescelement=theitem[<%= i %>].xdescelement_ID_2&type=xdescelement_ID_2',800,30,230);return false;"><bean:message key="chart4" /></a>
              <br>
              <html:select name="theitem" property="xdescelement_ID_3" styleClass="dropdown" indexed="true">
                <html:option value="">
                  <bean:message key="select.please_select" />
                </html:option>
                <html:options collection="xdescelementlist" property="XDesc_ID" labelProperty="description" />
              </html:select>
              <a href="#" onclick="openChart2('pages/popups/bagtypechart.jsp?charttype=4&xdescelement=theitem[<%= i %>].xdescelement_ID_3&type=xdescelement_ID_3',800,30,230);return false;"><bean:message key="chart4" /></a>
            </td>
            <td valign="top">
              <bean:message key="colname.manufacturer" />
              <br>
              <html:select name="theitem" property="manufacturer_ID" styleClass="dropdown" indexed="true" onchange='showmanu(this);return true;'>
                <html:option value="">
                  <bean:message key="select.please_select" />
                </html:option>
                <html:options collection="manufacturerlist" property="manufacturer_ID" labelProperty="description" />
              </html:select>
              <div id="manu_other<%= i %>">
                <br>
                <html:text name="theitem" property="manufacturer_other" size="25" maxlength="100" styleClass="textfield" indexed="true" />
              </div>
            </td>
          </tr>
          <!-- provide space for bag weight feature - start -->
	      <%
			    boolean val = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_BAGGAGE_WEIGHT, a);
			    if (val) {
		  %> 
          <tr id="bag_weight">
              <td><bean:message key="colname.bag.weight.and.units" /><br>
					<html:text name="theitem" property="bag_weight" size="8" maxlength="10" styleClass="textfield" indexed="true" />
        			<html:select name="theitem" property="bag_weight_unit" styleClass="dropdown" indexed="true">
						<html:option value="lbs">lbs</html:option>
						<html:option value="kg">kg</html:option>
			        </html:select>
              </td>
              <% if(theitem.getItemtype_ID()==1){ %>
              <td colspan="2">
              <bean:message key="colname.bag.external.desc" /><br>
              	<html:text name="theitem" property="externaldesc" maxlength="50" size="75" styleClass="textfield" indexed="true" />
              </td>
               <% } else {%>
               
              <td colspan="2"></td>
               <%}%>
          </tr>
       	  <% 
			    }
		  %>  
          <!-- provide space for bag weight feature - end -->         
          <tr>
            <td colspan="3">

              <bean:message key="colname.key_contents" />
							<a name='<%= "inventory" + i %>'></a>
              <bean:define id="inventories" name="theitem" property="inventorylist" />
              <logic:iterate id="inventorylist" indexId="j" name="inventories" type="com.bagnet.nettracer.tracing.db.Item_Inventory">
	            <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0" width="100%">
	              <tr id="<%=TracingConstants.JSP_DELETE_INVENTORY %>_<%= i %>_<%= j %>">
	                <td>
	                  <bean:message key="colname.category" /><br>
	                  <html:select property='<%= "inventorylist[" + (i.intValue() * 20 + j.intValue()) + "].categorytype_ID" %>' styleClass="dropdown">
	                    <html:option value="">
	                      <bean:message key="select.please_select" />
	                    </html:option>
	                    <html:options collection="categorylist" property="OHD_CategoryType_ID" labelProperty="description" />
	                  </html:select>
	                </td>
	                <td>
	                  <% if (report_type == 1) { %>
	                  <bean:message key="colname.ld.description" />
	                  <% } else if (report_type == 2) { %>
	                  <bean:message key="colname.pil.description" />
	                  <% } else {%>
	                  <bean:message key="colname.dam.description" />
	                  <% }%>
	                  <br>
	                  <html:text property="<%= "inventorylist[" + (i.intValue() * 20 + j.intValue()) + "].description" %>" size="80" maxlength="255" styleClass="textfield" />
	                </td>
	                <td align="center">&nbsp;<br>
                      <% 
                      String check = "true";
                      if (report_type !=2) {
                        check = "checkDeleteCount(" + i + ", " + report_type + ")";
                      }
                      	%>
                      <input type="button" name="deleteinventory_<%=i %>" value="<bean:message key="button.delete_content"/>" 
                      onclick="if (<%=check %>) {hideThisElement('<%=TracingConstants.JSP_DELETE_INVENTORY %>_<%= i %>_<%= j %>', '<bean:message key="colname.lc.content" />', 0);}" id="button">
	                </td>
	              </tr>
	            </table>
                
	          	</logic:iterate>
	  
		          <center>

                  <select name="addNumInventory[<%=i %>]">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                  </select>
                  <html:submit styleId="button" property="addinventory" indexed="true" >
		            <bean:message key="button.add_content" />
		          </html:submit>
		          <% boolean val2=false;
					if(report_type==1){
						val2=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_REMARK_UPDATE_LD, a);
					} else if (report_type==2){
						val2=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_REMARK_UPDATE_MS, a);
					} else if (report_type==3){
						val2=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_REMARK_UPDATE_DA, a);
					}
					if(!UserPermissions.hasIncidentSavePermission(a, theitem.getIncident()) && val2 && UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ADD_NEW_CONTENTS, a) ) {%>
							<html:submit styleId="button" property="saveadditions"
								indexed="true">
								<bean:message key="button.save" />
							</html:submit>
						<%}%></center>

            </td>
          </tr>
  
          
          
          <tr>
            <td colspan="3">
            	<% 
            	if (report_type == 0) { 
            	%>
            	<bean:message key="colname.damaged_bag_status" />
            	<% 
            	} else { 
            	%>
              <bean:message key="colname.bag_status" />
              <% 
              } 
              %>
              <br>
		      <input type="text" name = "theitem[<%=i %>].status.description" value="<bean:message name="theitem" property="status.key" />" size="25" maxlength="25" styleClass="textfield" readonly="true"/>

              &nbsp;&nbsp;&nbsp;&nbsp;
              <logic:present name="theitem" property="bdo">
              
              <bean:message key="header.bdo" />: <a href="bdo.do?bdo_id=<bean:write name="theitem" property="bdo.BDO_ID" />"><bean:write name="theitem" property="bdo.BDO_ID_ref" /></a>
              <% if (theitem.isBdoEntryCanceled()) { %>
              	(<bean:message key="bdo.canceled"/>)
              <% } %>
              </logic:present>
            </td>
          </tr>
<%
          if (report_type == 0) {
%>
            <tr>
              <td>
                <bean:message key="colname.damagedesc.req" />
                <br>
                <html:text name="theitem" property="damage" size="30" maxlength="255" styleClass="textfield" indexed="true" />
              </td>
              <td>
                <bean:message key="colname.lvldamage" />
                <br>
                <html:select name="theitem" property="lvlofdamage" styleClass="dropdown" indexed="true">
                  <html:option value="">
                    <bean:message key="select.please_select" />
                  </html:option>
                  <html:option value="<%=String.valueOf(TracingConstants.DAMAGE_MINOR) %>">
                    <bean:message key="select.minor" />
                  </html:option>
                  <html:option value="<%=String.valueOf(TracingConstants.DAMAGE_MAJOR) %>">
                    <bean:message key="select.major" />
                  </html:option>
                  <html:option value="<%=String.valueOf(TracingConstants.DAMAGE_COMPLETE) %>">
                    <bean:message key="select.complete" />
                  </html:option>
                </html:select>
              	<% if (request.getAttribute("damaged") != null && UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ISSUE_REPLACEMENT_BAGS, a)) { %>
	          		<br />
         			<bean:message key="replacement.bag.issued" /><br/>
         			<html:select name="theitem" property="replacementBagIssued" styleId="replacementBagIssued" styleClass="dropdown" indexed="true" >
						<html:option value="-1"><bean:message key="select.please_select" /></html:option>
						<html:option value="<%=String.valueOf(TracingConstants.YES) %>"><bean:message key="select.yes" /></html:option>
						<html:option value="<%=String.valueOf(TracingConstants.NO) %>"><bean:message key="select.no" /></html:option>
					</html:select>
         		<% } %>
              </td>
              <td>
                <bean:message key="colname.cost" />
                <br>
                <html:text name="theitem" property="discost" size="13" maxlength="12" styleClass="textfield" indexed="true" />
                <br>
                <bean:message key="colname.currency" />
                <br>
                <html:select name="theitem" property="currency_ID" styleClass="dropdown" indexed="true">
                  <html:options collection="currencylist" property="currency_ID" labelProperty="id_desc" />
                </html:select>
              </td>
            </tr>
            <tr>
              <td colspan="3">
              
              

                <bean:message key="colname.resolutiondesc" />
                <br>
                <html:textarea name="theitem" property="resolutiondesc" cols="80" rows="5" styleClass="textarea_medium" indexed="true"  onkeydown="textCounter3(this,255);" onkeyup="textCounter3(this,255);"/>
              </td>
            </tr>
<%
          }
            if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_DAMAGED_PHOTOS, a)) {
%>
              <tr>
                <td colspan="3">
                  <bean:message key="header.photos" />
                  :
                  <br>
                  <a name='<%= "upload" + i %>'></a>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
<%
                    int k = 0;
%>
                    <logic:present name="theitem" property="photolist">
                      <bean:define id="photos" name="theitem" property="photolist" />
                      <logic:iterate id="photolist" indexId="j" name="photos" type="com.bagnet.nettracer.tracing.db.Item_Photo">
<%
                        if (k % 3 == 0) {
%>
                          <tr align="center">
<%
                          }
%>
                          <td align="center">
                            <a href='showImage?ID=<bean:write name="photolist" property="picpath"/>' target="top"><img src='showImage?ID=<bean:write name="photolist" property="thumbpath"/>'></a>
                            <br>
                            <a href='showImage?ID=<bean:write name="photolist" property="picpath"/>' target="top"><bean:write name="photolist" property="fileName"/></a>
                            <br>
                            <input type="submit" name="removePhoto_<%= i %>_<%= j %>" id="button" value="<bean:message key="button.delete_photo"/>">
                          </td>
<%
                          if (k % 3 == 2) {
%>
                          </tr>
<%
                        }
                        k++;
%>
                      </logic:iterate>
                    </logic:present>
                  
                </table>
                <br>
                <center><input type="FILE" name='<%= "imagefile" + i %>' />
                &nbsp;
                <html:submit property="uploadPhoto" indexed="true" styleId="button">
                  <bean:message key="header.addPhotos" />
                </html:submit></center>
              </td>
            </tr>
<%
          }

%>
        <tr>
          <td colspan="3">
            <input type="button" value="<bean:message key="button.delete_item" />" 
            onclick="hideThisDiv('<%=TracingConstants.JSP_DELETE_ITEM %>_<%=i%>', 
            '<bean:message key="header.bag" />')" id="button">
          </td>
        </tr>
      </table>
      </div>
    </logic:iterate>
    <center>
            <select name="additemNum">
          <option value="1">1</option>
          <option value="2">2</option>
          <option value="3">3</option>
          <option value="4">4</option>
          <option value="5">5</option>
        </select>
    
    <html:submit property="additem" styleId="button">
      <bean:message key="button.add_bag" />
    </html:submit></center>
    <script language="JavaScript">
      


 for (var j=0;j<document.incidentForm.length;j++) {
    currentElement = document.incidentForm.elements[j];
    currentElementName=currentElement.name;
    
    if (currentElementName.indexOf("manufacturer_ID") != -1)
    {
    	var pos = currentElementName.indexOf("[");
			var pos2 = currentElementName.indexOf("]");
  		pos = currentElementName.substring(pos+1,pos2);
			if (currentElement.value == <%= TracingConstants.MANUFACTURER_OTHER_ID %>) {
				document.getElementById("manu_other" + pos).style.visibility = "visible";
  
      } else {
				document.getElementById("manu_other" + pos).style.visibility = "hidden";
			}
    }
    
	}

	

    </script>
    <br>
    <br>
    &nbsp;&nbsp;&uarr;
    <a href="#"><bean:message key="link.to_top" /></a>
    <br>
    <br>
    <jsp:include page="/pages/lostdelay/ld_rfl_incl.jsp" />
    <logic:notEqual name="incidentForm" property="incident_ID" value="">
      <a name="interimexpense"></a>
      <h1 class="green">
        <bean:message key="header.interim_expense" />
<%
        if (report_type == 0) {
%>
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#damaged_bag_reports/interim_expense.htm');return false;"><%
          } else if (report_type == 1) {
%>
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/interim_expense.htm#top');return false;"><%
            } else if (report_type == 2) {
%>
              <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#missing_articles_reports/interim_expense.htm#top');return false;"><%
              }
%>
              <img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <span class="reqfield">*</span>
          <bean:message key="message.required" />
      <jsp:include page="/pages/includes/incident_expense_incl.jsp" >
      	<jsp:param name="formCss" value="<%= cssFormClass %>" />
      </jsp:include>
                <br>
                <br>
                &nbsp;&nbsp;&uarr;
                <a href="#"><bean:message key="link.to_top" /></a>
                <br>
                <br>
     </logic:notEqual>
<script> <logic:iterate id="theitem" indexId="i" name="incidentForm" property="itemlist" type="com.bagnet.nettracer.tracing.db.Item">
     		checkBagType(<%=i%>);
		</logic:iterate></script>
