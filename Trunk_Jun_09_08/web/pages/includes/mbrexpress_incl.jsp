<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>
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
  <tr>
    
    <td id="middlecolumn">
      <div id="maincontent">
        <h1 class="green">
          <bean:message key="header.incident_info" />
<%
          if (report_type == 0) {
%>
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#damaged_bag_reports/creating_exdamage_bag_report.htm');return false;"><%
            } else if (report_type == 1) {
%>
              <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/express_lost_delayed_bag_reports.htm');return false;"><%
              } else if (report_type == 2) {
%>
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#missing_articles_reports/work_with_express_missing_articles_reports.htm');return false;"><%
                }
%>
                <img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
            </h1>
            <span class="reqfield">*</span>
            <bean:message key="message.required" />
            <font color="red">
              <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
            </font>
            <br>
            <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <bean:message key="colname.recordlocator.req" />
                  :
                </td>
                <td colspan="2">
                  <bean:message key="colname.ticket" />
                  :
                </td>
              </tr>
              <tr>
                <td>
                  <input type="hidden" name="express" value="1">
                  <html:text property="recordlocator" size="30" maxlength="10" styleClass="textfield" />
                </td>
                <td colspan="2">
                  <html:text property="ticketnumber" size="30" maxlength="14" styleClass="textfield" />
                </td>
              </tr>
            </table>
            <br>
            <br>
            &nbsp;&nbsp;&uarr;
            <a href="#"><bean:message key="link.to_top" /></a>
            <br>
            <br>
            <jsp:include page="/pages/includes/contactinfo_incl.jsp" />
            <a name="passit"></a>
            <h1 class="green">
              <bean:message key="header.itinerary" />
<%
              if (report_type == 0) {
%>
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#damaged_bag_reports/work_passenger_itineraries.htm#add passenger itinerary');return false;"><%
                } else if (report_type == 1) {
%>
                  <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_passenger_itineraries_(ld).htm#add passenger itinerary');return false;"><%
                  } else if (report_type == 2) {
%>
                    <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#missing_articles_reports/work_with_passenger_itineraries_(ma).htm#add passenger itinerary');return false;"><%
                    }
%>
                    <img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
                </h1>
                <span class="reqfield">*</span>
                <bean:message key="message.required" />
               <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0" >
                <logic:iterate id="theitinerary" indexId="k" name="incidentForm" property="itinerarylist">
                    <logic:equal name="theitinerary" property="itinerarytype" value="0">
                      <tr>
                        <td>
                          <bean:message key="colname.pax.fromto.req" />
                        </td>
                        <td>
                          <bean:message key="colname.pax.flightnum.req" />
                        </td>
                        <td>
                          <bean:message key="colname.pax.departdate.req" />
                          (
                          <%= a.getDateformat().getFormat() %>)
                        </td>
                        <td>
                          <bean:message key="colname.pax.arrdate.req" />
                          (
                          <%= a.getDateformat().getFormat() %>)
                        </td>
                      </tr>
                      <tr id="<%=TracingConstants.JSP_DELETE_ITINERARY %>_<%=k %>">
                        <td id="passItin">
                          <html:hidden name="theitinerary" property="itinerarytype" value="0" indexed="true" />
                          <html:text name="theitinerary" property="legfrom" size="3" maxlength="3" styleClass="textfield" indexed="true" />
                          <a href="#" onclick="openWindow('pages/popups/airportcodes.jsp?key=theitinerary[<%= k %>].legfrom','airportcode',500,600);return false;"><img src="deployment/main/images/nettracer/airport_codes.gif" border="0"></a>
                          /
                          <html:text name="theitinerary" property="legto" size="3" maxlength="3" styleClass="textfield" indexed="true" />
                          <a href="#" onclick="openWindow('pages/popups/airportcodes.jsp?key=theitinerary[<%= k %>].legto','airportcode',500,600);return false;"><img src="deployment/main/images/nettracer/airport_codes.gif" border="0"></a>
                        </td>
                        <td>
                          <html:select name="theitinerary" property="airline" styleClass="dropdown" indexed="true">
                            <html:option value="">
                              <bean:message key="select.please_select" />
                            </html:option>
                            <html:options collection="companylistById" property="companyCode_ID" labelProperty="companyCode_ID" />
                          </html:select>
                          &nbsp;
                          <html:text name="theitinerary" property="flightnum" size="4" maxlength="4" styleClass="textfield" indexed="true" />
                        </td>
                        <td>
                          <html:text name="theitinerary" property="disdepartdate" size="10" maxlength="10" styleClass="textfield" indexed="true" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="itcalendar<%= k %>" name="itcalendar<%= k %>" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.incidentForm, '<%= "theitinerary[" + k + "].disdepartdate" %>','itcalendar<%= k %>','<%= a.getDateformat().getFormat() %>'); return false;"></td>
                        <td>
                          <html:text name="theitinerary" property="disarrivedate" size="10" maxlength="10" styleClass="textfield" indexed="true" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="itcalendar2<%= k %>" name="itcalendar2<%= k %>" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.incidentForm, '<%= "theitinerary[" + k + "].disarrivedate" %>','itcalendar2<%= k %>','<%= a.getDateformat().getFormat() %>'); return false;"></td>
                      </tr>
                      <tr id="<%=TracingConstants.JSP_DELETE_ITINERARY %>_<%=k %>_1">
                        <td colspan="4">
                          <input type="button" value="<bean:message key="button.delete_pass_itinerary" />" onclick="hideThisElement('<%=TracingConstants.JSP_DELETE_ITINERARY %>_<%=k%>', '<bean:message key="colname.itinerary" />', 1)" id="button">
                        </td>
                      </tr>
                    </logic:equal>
                  </logic:iterate>
            	</table>
                <center><select name="addpassitNum">
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
                  <option value="4">4</option>
                  <option value="5">5</option>
          </select> <html:submit property="addpassit" styleId="button">
                  <bean:message key="button.add_cust_itinerary" />
                </html:submit></center>
                <br>
                <br>
                &nbsp;&nbsp;&uarr;
                <a href="#"><bean:message key="link.to_top" /></a>
                <br>
                <br>
<%
                if (report_type == 1) {
%>
                  
                  <a name="claimcheck"></a>
                  <a name='addclaimcheck'></a>
                  <h1 class="green">
                    <bean:message key="colname.claimnum.req" />
                    <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_baggage_check_information_(ld).htm#add claim check');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
                  </h1>
                  <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
                    <logic:iterate id="claimcheck" indexId="i" name="incidentForm" property="claimchecklist">
                      <tr id="claimcheck_<%=i %>">
                        <td width="30%" nowrap="nowrap">
                          <bean:message key="colname.claimnum" />
                          :
                        </td>
                        <td>
                          <html:text name="claimcheck" property="claimchecknum" size="13" maxlength="12" styleClass="textfield" indexed="true" />
                        </td>
                      </tr>
                      <tr id="claimcheck_<%=i %>_1">
                        <td colspan="2">
<input type="button" value="<bean:message key="button.delete_claim" />" onclick="hideThisElement('<%=TracingConstants.JSP_DELETE_CLAIMCHECK %>_<%=i%>', '<bean:message
                key="colname.claimnum.req" />', 1)" id="button">
              
                        </td>
                      </tr>
                    </logic:iterate>
                  </table>
                  <center>
                  <select name="addclaimcheckNum">
          <option value="1">1</option>
          <option value="2">2</option>
          <option value="3">3</option>
          <option value="4">4</option>
          <option value="5">5</option>
        </select>
        
                  <html:submit property="addclaimcheck" styleId="button">
                    <bean:message key="button.add_claimcheck" />
                  </html:submit></center>
                  <br>
                  <br>
                  &nbsp;&nbsp;&uarr;
                  <a href="#"><bean:message key="link.to_top" /></a>
                  <br>
                  <br>
<%
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
                          <td>
                            <b><a name='additem<%= i %>'></a>
                            <bean:message key="colname.bag_number" />
                            : &nbsp;&nbsp;
                            <%= theitem.getBagnumber() + 1 %></b>
                          </td>
<%
                          if (report_type != 1) {
%>
                            <td colspan="2">
                              <bean:message key="colname.claimnum2" />
                              :
                              <html:text name="theitem" property="claimchecknum" size="13" maxlength="12" styleClass="textfield" indexed="true" />
                            </td>
<%
                          } else {
%>
                            <td colspan="2">
                              &nbsp;
                            </td>
<%
                          }
%>
                        </tr>
                        <tr>
                          <td valign="top">
                            <bean:message key="colname.color.req" />
                            :
                            <a href="#" onclick="openChart2('pages/popups/bagtypechart.jsp?charttype=3&key=theitem[<%= i %>].color&type=color',800,30,230);return false;"><bean:message key="chart3" /></a>
                            <br>
                            <html:select name="theitem" property="color" indexed="true" styleClass="dropdown">
                              <html:options collection="colorlist" property="value" labelProperty="label" />
                            </html:select>
                            <br>
                            <br>
                            <bean:message key="colname.bagtype.req" />
                            :
                            <a href="#" onclick="openChart2('pages/popups/bagtypechart.jsp?charttype=1&key=theitem[<%= i %>].bagtype&type=bagtype',800,280);return false;"><bean:message key="chart1" /></a>
                            <a href="#" onclick="openChart2('pages/popups/bagtypechart.jsp?charttype=2&key=theitem[<%= i %>].bagtype&type=bagtype',800,370);return false;"><bean:message key="chart2" /></a>
                            <br>
                            <html:select name="theitem" property="bagtype" styleClass="dropdown" indexed="true">
                              <html:options collection="typelist" property="value" labelProperty="label" />
                            </html:select>
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
                        <tr>
                          <td colspan="3">
                            <bean:message key="colname.key_contents" />
														<a name='<%= "inventory" + i %>'></a>
							              <bean:define id="inventories" name="theitem" property="inventorylist" />
							              <logic:iterate id="inventorylist" indexId="j" name="inventories" type="com.bagnet.nettracer.tracing.db.Item_Inventory">
								            <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0" width="100%">
								              <tr id="<%=TracingConstants.JSP_DELETE_INVENTORY %>_<%= i %>_<%= j %>">
								                <td>
								                  <bean:message key="colname.category.req" /><br>
								                  <html:select property='<%= "inventorylist[" + (i.intValue() * 20 + j.intValue()) + "].categorytype_ID" %>' styleClass="dropdown">
								                    <html:option value="">
								                      <bean:message key="select.please_select" />
								                    </html:option>
								                    <html:options collection="categorylist" property="OHD_CategoryType_ID" labelProperty="description" />
								                  </html:select>
								                </td>
								                <td>
<%
                          							if (report_type == 0) {
%>
														<bean:message key="colname.dam.description" />
<%
                          							} else if (report_type == 1) {
%>
														<bean:message key="colname.ld.description" />
<%
                          							} else {
%>
														<bean:message key="colname.pil.description" />
<%
                          							}
%>
								                  
								                  <br>
								                  <html:text property="<%= "inventorylist[" + (i.intValue() * 20 + j.intValue()) + "].description" %>" size="70" maxlength="255" styleClass="textfield" />
								                </td>
								                <td align="center">&nbsp;<br>
                                <% 
                      String check = "true";
                      if (report_type !=2) {
                        check = "checkDeleteCount(" + i + " ," + report_type + ")";
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
                  </select> <html:submit styleId="button" property="addinventory" indexed="true" >
									            <bean:message key="button.add_content" />
									          </html:submit></center>
                          </td>
                        </tr>
<%
                        if (report_type == 0) {
%>
                          <tr>
                            <td>
                              <bean:message key="colname.damagedesc.req" />
                            </td>
                            <td>
                              <bean:message key="colname.lvldamage" />
                            </td>
                            <td>
                              <bean:message key="colname.cost" />
                            </td>
                          </tr>
                          <tr>
                            <td valign="top">
                              <html:text name="theitem" property="damage" size="30" maxlength="255" styleClass="textfield_wide" indexed="true" />
                            </td>
                            <td valign="top">
                              <html:select name="theitem" property="lvlofdamage" styleClass="dropdown" indexed="true">
                                <html:option value="">
                                  <bean:message key="select.please_select" />
                                </html:option>
                                <html:option value="0">
                                  <bean:message key="select.minor" />
                                </html:option>
                                <html:option value="1">
                                  <bean:message key="select.major" />
                                </html:option>
                                <html:option value="2">
                                  <bean:message key="select.complete" />
                                </html:option>
                              </html:select>
                            </td>
                            <td valign="top" nowrap="nowrap">
                              <html:text name="theitem" property="discost" size="13" maxlength="12" styleClass="textfield" indexed="true" />
                              &nbsp;
                              <bean:message key="colname.currency" />
                              :
                              &nbsp;
                              <html:select name="theitem" property="currency_ID" styleClass="dropdown" indexed="true">
                                <html:options collection="currencylist" property="currency_ID" labelProperty="id_desc" />
                              </html:select>
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
                    <center><select name="additemNum">
          <option value="1">1</option>
          <option value="2">2</option>
          <option value="3">3</option>
          <option value="4">4</option>
          <option value="5">5</option>
        </select> <html:submit property="additem" styleId="button">
                      <bean:message key="button.add_bag" />
                    </html:submit></center>
                    <SCRIPT LANGUAGE="JavaScript">
                      


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

	

                    </SCRIPT>
                    <br>
                    <br>
                    &nbsp;&nbsp;&uarr;
                    <a href="#"><bean:message key="link.to_top" /></a>
                    <br>
                    <br>
<%
                    if (report_type == 2) {
%>
                      
                      <a name="missingarticles"></a>
                      <h1 class="green">
                        <bean:message key="header.ma" />
                        <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#missing_articles_reports/work_with_express_missing_articles_reports.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
                      </h1>
                      <span class="reqfield">*</span>
                      <bean:message key="message.required" />
                      <logic:iterate id="article" indexId="i" name="incidentForm" property="articlelist">
                        <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
                          <tr>
                            <td>
                              <a name='addarticle<%= i %>'></a>
                              
                              <bean:message key="colname.article.req" />
                              :
                            </td>
                            <td>
                              <html:text name="article" property="article" size="30" maxlength="50" styleClass="textfield" indexed="true" />
                            </td>
                          </tr>
                          <tr>
                            <td valign="top">
                              <bean:message key="colname.desc" />
                              :
                            </td>
                            <td>
                              <html:textarea name="article" property="description" cols="55" rows="5" styleClass="textarea_medium" indexed="true" />
                            </td>
                          </tr>
                          <tr>
                            <td>
                              <bean:message key="colname.purchase_date" />
                              :
                            </td>
                            <td>
                              <html:text name="article" property="dispurchasedate" size="15" maxlength="10" styleClass="textfield" indexed="true" />
                            </td>
                          </tr>
                          <tr>
                            <td>
                              <bean:message key="colname.cost" />
                              :
                            </td>
                            <td>
                              <html:text name="article" property="discost" size="13" maxlength="12" styleClass="textfield" indexed="true" />
                              &nbsp;
                              <bean:message key="colname.currency" />
                              :
                              <html:select name="article" property="currency_ID" styleClass="dropdown" indexed="true">
                                <html:options collection="currencylist" property="currency_ID" labelProperty="currency_ID" />
                              </html:select>
                            </td>
                          </tr>
                          <tr>
                            <td>
                              <html:submit styleId="button" property="deleteArticle" indexed="true">
                                <bean:message key="button.delete_article" />
                              </html:submit>
                            </td>
                          </tr>
                          <tr>
                            <td height="1" colspan="2">
                              &nbsp;
                            </td>
                          </tr>
                        </table>
                      </logic:iterate>
                      <center><html:submit property="addarticles" styleId="button">
                        <bean:message key="button.add_articles" />
                      </html:submit></center>
                      <br>
                      <br>
                      &nbsp;&nbsp;&uarr;
                      <a href="#"><bean:message key="link.to_top" /></a>
                      <br>
                      <br>
<%
                    }
%>
                    <jsp:include page="/pages/includes/remark_incl.jsp" />
                  </div>
                  <logic:notEqual name="incidentForm" property="readonly" value="1">
                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td align="center" valign="top">
                          <br>
<%
                          if (report_type == 0) {
%>
                            <html:submit property="save" styleId="button"  onclick="return validatereqFields(this.form, 'damaged');">
                             <logic:notEqual name="incidentForm" property="incident_ID" value="">
                              <bean:message key="button.save" />
                              </logic:notEqual>
                              <logic:equal name="incidentForm" property="incident_ID" value="">
                              <bean:message key="button.savetracing" />
                              </logic:equal>
                              
                            </html:submit>
<%
                          } else if(report_type == 2) {
%>
                            <html:submit property="save" styleId="button"  onclick="return validatereqFields(this.form, 'pilfered');">
                               <logic:notEqual name="incidentForm" property="incident_ID" value="">
                              <bean:message key="button.save" />
                              </logic:notEqual>
                              <logic:equal name="incidentForm" property="incident_ID" value="">
                              <bean:message key="button.savetracing" />
                              </logic:equal>
                            </html:submit>
<%
                          } else {
%>
                            <logic:notEqual name="incidentForm" property="incident_ID" value="">
                              <html:submit property="save" styleId="button" onclick="return validatereqFields(this.form, 'lostdelay');">
                                <bean:message key="button.save" />
                              </html:submit>
                            </logic:notEqual>
                            <logic:equal name="incidentForm" property="incident_ID" value="">
                             <%
                	if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CREATE_TEMP_INCIDENTS, a)) {
                	%>
                              <html:submit property="savetemp" styleId="button" onclick="return validatereqFields(this.form, 'lostdelay');">
                                <bean:message key="button.savetemp" />
                              </html:submit>
	                           &nbsp;&nbsp;&nbsp;
	                               <% } %>
                              <html:submit property="savetracing" styleId="button" onclick="return validatereqFields(this.form, 'lostdelay');">
                                <bean:message key="button.savetracing" />
                              </html:submit>
                            </logic:equal>
<%
                          }
%>
                        </td>
                      </tr>
                    </table>
                  </logic:notEqual>
