<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

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

  <!-- Calendar includes -->
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
  // End -->
</SCRIPT>

  <SCRIPT LANGUAGE="JavaScript">
    <!--
	var cal1xx = new CalendarPopup();	
	//cal1xx.showNavigationDropdowns();
	
	// show manufacturer
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
	
// -->
  </SCRIPT>
  <!-- calendar stuff ends here -->
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
  <!-- bag section --->
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
        <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
          <tr>
            <td valign=top>
              <b><a name='additem<%= i %>'></a>
              <bean:message key="colname.bag_number" />
              : &nbsp;&nbsp;
              <%= theitem.getBagnumber() + 1 %>
            </td>
<%
            if (report_type != 1) {
%>
              <td colspan=2>
                <bean:message key="colname.claimnum.req" />
                <br>
                <html:text name="theitem" property="claimchecknum" size="13" maxlength="13" styleClass="textfield" indexed="true" />
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
                    <a href='addOnHandBag.do?ohd_ID=<bean:write name="theitem" property="OHD_ID"/>'><bean:write name="theitem" property="OHD_ID" /></a>
                    &nbsp;
                    <logic:notPresent name="cantmatch" scope="request">
                      <input type="submit" name="unmatchbag<%= i %>" value='<bean:message key="button.un_match"/>' id="button">
                    </logic:notPresent>
                  </logic:notEmpty>
                  <logic:empty name="theitem" property="OHD_ID">
                    <logic:notPresent name="cantmatch" scope="request">
                      <html:text name="theitem" property="tempOHD_ID" size="13" maxlength="13" styleClass="textfield" indexed="true" onblur="fillzero(this,13);" />
                      <input type="submit" name="matchbag<%= i %>" value='<bean:message key="button.do_match"/>' id="button">
                    </logic:notPresent>
                  </logic:empty>
                </td>
              </logic:notEqual>
              <logic:equal name="incidentForm" property="incident_ID" value="">
                <td colspan=2>
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
            <td valign=top>
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
              <html:select name="theitem" property="bagtype" styleClass="dropdown" indexed="true">
                <html:options collection="typelist" property="value" labelProperty="label" />
              </html:select>
            </td>
            <td valign=top>
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
            <td valign=top>
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
            <td colspan=3>

              <bean:message key="colname.key_contents" />
							<a name='<%= "inventory" + i %>'></a>
              <bean:define id="inventories" name="theitem" property="inventorylist" />
              <logic:iterate id="inventorylist" indexId="j" name="inventories" type="com.bagnet.nettracer.tracing.db.Item_Inventory">
	            <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0" width="100%">
	              <tr>
	                <td>
	                  <bean:message key="colname.category" /><br>
	                  <html:select property='<%= "inventorylist[" + (i.intValue() * 20 + j.intValue()) + "].categorytype_ID" %>' styleClass="dropdown">
	                    <html:option value="">
	                      <bean:message key="select.please_select" />
	                    </html:option>
	                    <html:options collection="categorylist" property="OHD_CategoryType_ID" labelProperty="categorytype" />
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
	                	<input type="submit" name="deleteinventory_<%= i %>_<%= j %>" id="button" value="<bean:message key="button.delete_content"/>">
	      
	                </td>
	              </tr>
	            </table>
	          	</logic:iterate>
	  
		          <center><html:submit styleId="button" property="addinventory" indexed="true" >
		            <bean:message key="button.add_content" />
		          </html:submit></center>

            </td>
          </tr>
  
          
          
          <tr>
            <td colspan=3>
              <bean:message key="colname.bag_status" />
              <br>
              <html:text name="theitem" property="status.description" size="25" maxlength="25" styleClass="textfield" indexed="true" readonly="true" />
              &nbsp;&nbsp;&nbsp;&nbsp;
              <logic:present name="theitem" property="bdo">
              <bean:message key="header.bdo" />: <a href="bdo.do?bdo_id=<bean:write name="theitem" property="bdo.BDO_ID" />"><bean:write name="theitem" property="bdo.BDO_ID_ref" /></a>
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
              <td colspan=3>
              
              

                <bean:message key="colname.resolutiondesc" />
                <br>
                <html:textarea name="theitem" property="resolutiondesc" cols="80" rows="5" styleClass="textarea_medium" indexed="true"  onkeydown="textCounter3(this,255);" onkeyup="textCounter3(this,255);"/>
              </td>
            </tr>
<%
            if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_DAMAGED_PHOTOS, a)) {
%>
              <tr>
                <td colspan=3>
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
                          <td align=center>
                            <a href='showImage?ID=<bean:write name="photolist" property="picpath"/>' target="top"><img src='showImage?ID=<bean:write name="photolist" property="thumbpath"/>'></a>
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
                  </tr>
                </table>
                <br>
                <center><input type="FILE" name='<%= "imagefile" + i %>' />
                &nbsp;
                <html:submit property="uploadPhoto" indexed="true" styleId="button">
                  <bean:message key="header.addPhotos" />
                </html:submit>
              </td>
            </tr>
<%
          }
        }
%>
        <tr>
          <td colspan=3>
            <html:submit styleId="button" property="deleteItem" indexed="true">
              <bean:message key="button.delete_item" />
            </html:submit>
          </td>
        </tr>
      </table>
    </logic:iterate>
    <center><html:submit property="additem" styleId="button">
      <bean:message key="button.add_bag" />
    </html:submit></center>
    <SCRIPT LANGUAGE="JavaScript">
      <!--

	// happens after load
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

	
// -->
    </SCRIPT>
    <br>
    <br>
    &nbsp;&nbsp;&uarr;
    <a href="#"><bean:message key="link.to_top" /></a>
    <br>
    <br>
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
          <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <strong>
                  <bean:message key="colname.createdate" />
                </strong>
              </td>
              <td>
                <strong>
                  <bean:message key="colname.paycode" />
                </strong>
              </td>
              <td>
                <strong>
                  <bean:message key="colname.draft_br" />
                </strong>
              </td>
              <td>
                <strong>
                  <bean:message key="colname.draftreqdate_br" />
                </strong>
              </td>
              <td>
                <strong>
                  <bean:message key="colname.draftpaiddate_br" />
                </strong>
              </td>
              <td>
                <strong>
                  <bean:message key="colname.checkamt_br" />
                </strong>
              </td>
              <td>
                <strong>
                  <bean:message key="colname.voucheramt_br" />
                </strong>
              </td>
              <td>
                <strong>
                  <bean:message key="colname.mileageamt_br" />
                </strong>
              </td>
              <td>
                <strong>
                  <bean:message key="header.status" />
                </strong>
              </td>
              <td>
                <strong>
                  <bean:message key="header.approval_deny_date_br" />
                </strong>
              </td>
              <td>
                <strong>
                  <bean:message key="colname.modify" />
                </strong>
              </td>
            </tr>
<%
            double checktotal   = 0;
            double vouchertotal = 0;
            int    mileagetotal = 0;
            int    i            = -1;
            boolean samecurrency = true;
            String lastcurrency = "";
%>
            <logic:iterate id="expenselist" name="incidentForm" property="expenselist" type="com.bagnet.nettracer.tracing.db.ExpensePayout">
              <bean:define id="expensetype" name="expenselist" property="expensetype" type="com.bagnet.nettracer.tracing.db.ExpenseType" />
              <bean:define id="expenselocation" name="expenselist" property="expenselocation" type="com.bagnet.nettracer.tracing.db.Station" />
<%
              i++;
%>
              <logic:equal name="expensetype" property="expensetype_ID" value="<%= "" + TracingConstants.EXPENSEPAYOUT_INTERIM %>">
<%
                checktotal   += expenselist.getCheckamt();
								if (lastcurrency == "") lastcurrency = expenselist.getCurrency_ID();
								if (!lastcurrency.equals(expenselist.getCurrency_ID())) samecurrency = false;
								
                vouchertotal += expenselist.getVoucheramt();
                mileagetotal += expenselist.getMileageamt();
%>
                <tr>
                  <td>
                    <bean:write name="expenselist" property="discreatedate" />
                  </td>
                  <td>
                    <bean:write name="expenselist" property="paycode" />
                  </td>
                  <td>
                    <bean:write name="expenselist" property="draft" />
                    &nbsp;
                  </td>
                  <td>
                    <bean:write name="expenselist" property="disdraftreqdate" />
                    &nbsp;
                  </td>
                  <td>
                    <bean:write name="expenselist" property="disdraftpaiddate" />
                    &nbsp;
                  </td>
                  <td align="right" nowrap>
                    <bean:write name="expenselist" property="discheckamt" />
                    &nbsp;
                    <bean:write name="expenselist" property="currency_ID" />
                  </td>
                  <td align="right">
                    &nbsp;
                    <bean:write name="expenselist" property="disvoucheramt" />
                  </td>
                  <td align="right">
                    &nbsp;
                    <bean:write name="expenselist" property="mileageamt" />
                  </td>
                  <td valign="top">
                    <bean:write name="expenselist" property="status.description" />
                    &nbsp;
                  </td>
                  <td>
                    <bean:write name="expenselist" property="dispapproval_date" />
                    &nbsp;
                  </td>
                  <logic:notEqual name="incidentForm" property="readonly" value="1">
                    <td>
                      <a href="claim_resolution.do?modifyinterim=1&index=<%= i %>"><bean:message key="colname.modify" /></a>
                    </td>
                  </logic:notEqual>
                </tr>
              </logic:equal>
            </logic:iterate>
            <tr>
              <td>
                <b><bean:message key="colname.total_payout" />
                :
              </td>
              <td colspan="4">
                &nbsp;
              </td>
              <td align="right">
                <%
                if (samecurrency) out.println(TracingConstants.DECIMALFORMAT.format(checktotal)); 
                else  out.println("Multiple Currencies");
                %>
                <p>
                </td>
                <td align="right">
                  <%= TracingConstants.DECIMALFORMAT.format(vouchertotal) %>
                  <p>
                  </td>
                  <td align="right">
                    <%= mileagetotal %>
                    <p>
                    </td>
                  </tr>
                  <logic:notEqual name="incidentForm" property="readonly" value="1">
                    <tr>
                      <td align="center" valign="top" colspan="12">
                        <html:button property="addnewexpense" styleId="button" onclick="document.location.href='claim_resolution.do?addnewinterim=1'">
                          <bean:message key="button.add_payout" />
                        </html:button>
                      </td>
                    </tr>
                  </logic:notEqual>
                </table>
                <br>
                <br>
                &nbsp;&nbsp;&uarr;
                <a href="#"><bean:message key="link.to_top" /></a>
                <br>
                <br>
     </logic:notEqual>
