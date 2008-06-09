<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.OHD_Photo" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>NetTracer Passenger View</title>


<link href="<%=request.getContextPath()%>/deployment/main/css/nettracerstyles1.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/deployment/main/css/formstyles.css" rel="stylesheet" type="text/css">
<script language="javascript" src="<%=request.getContextPath()%>/deployment/main/js/nettracer.js"></script>

</head>

<body>

<table cellspacing="0" id="bodytable"> 
  <tr> 
    <td id="topcell">
    
<bean:define id="theform" name="IncidentForm" scope="request" />

<form method="post" action="passengerviewonly.do" name="searchform">
  <table cellspacing="0" id="toplayouttable" width="100%"> 
      <tr> 
        <td id="header" class="cssstandardthreecolumn">
      		<div id="headercontent">      </div>
    		</td> 
      </tr> 
      <!-- HORIZONTAL MENU --> 
      <tr> 
        <td id="headermenucell">
      
    		</td>
      </tr> 
        <!-- END HORIZONTAL MENU -->
    <!-- PAGE HEADER/SEARCH --> 
    

		  <tr>
		    <td  id="pageheadercell">
		      <div id="pageheaderleft">
		        <h1>
		          <bean:message key="header.pvo.report" />
		        </h1>
		      </div>
		    </td>
		  </tr>
		  <!-- END PAGE HEADER/SEARCH -->
		  <!--- search -->
  
    	
        
      <logic:notPresent name="incident" scope="request">
      <tr>
      <!-- MIDDLE COLUMN -->
      	<td id="middlecolumn" >
        <!-- MAIN BODY -->
	      
	      <div id="maincontent">
	        <font color=red>
	          <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
	          <table class="form2" cellspacing="0" cellpadding="0">
	           	<tr>
              	<td colspan="2" width="700" align="center">
              	
              	<table border="0" align="center">
              	<tr><td align="left">
              	<b>
              	If you have notified one of our representatives that your bags were delayed, you received an Incident Number. 
              	
              	You can use that number to check the status of your bag in the form below.
								<br/><br/>
								 
								Enter the 10 digit incident Number (you do not have to enter the leading zeros),
								located on the Receipt provided by our Representative and your last name in the form then click Search.
								</b>
              	</td></tr>
              	</table>
              	
              	</td>
              </tr>
              
	            <tr>
	              <td nowrap>
	                <bean:message key="colname.incident_num" />
	                :
	                <br>
	                <input type="text" name="incident_ID" size="14" class="textfield" maxlength="13" onblur="fillzero(this,13);">
	              </td>
	              <td nowrap>
	                <bean:message key="colname.last_name" />
	                :
	                <br>
	                <input type="text" name="name" size="14" class="textfield">
	              </td>
	            </tr>
	            <tr>
	              <td colspan="2" align="center" valign="top">
	                <input type="submit" name="search" value="<bean:message key="button.retrieve"/>" id="button">
	                &nbsp;
	                <input type="reset" name="reset" value="<bean:message key="button.reset"/>" id="button">
	              </td>
	            </tr>
	          </table>
	          
	          <p>
            <a href="mailto:BaggageService@expressjet.com">BaggageService@expressjet.com</a>
	        </div>
	      
	      
	      </td>
	    </tr>
      </logic:notPresent>
        
       <logic:present name="incident" scope="request">
        <!-- ICONS MENU -->
        <tr>
          <td colspan="3" id="navmenucell">
            <div class="menu">
              <dl>
                <dd>
                  <a href="#incidentinfo"><span class="aa">&nbsp;
                      <br />
                      &nbsp;</span>
                    <span class="bb"><bean:message key="menu.incident_info" /></span>
                    <span class="cc">&nbsp;
                      <br />
                      &nbsp;</span></a>
                </dd>
                <dd>
                  <a href="#contact"><span class="aa">&nbsp;
                      <br />
                      &nbsp;</span>
                    <span class="bb"><bean:message key="menu.contact_info" /></span>
                    <span class="cc">&nbsp;
                      <br />
                      &nbsp;</span></a>
                </dd>
                <dd>
                  <a href="#passit"><span class="aa">&nbsp;
                      <br />
                      &nbsp;</span>
                    <span class="bb"><bean:message key="menu.itinerary" /></span>
                    <span class="cc">&nbsp;
                      <br />
                      &nbsp;</span></a>
                </dd>
                <dd>
                  <a href="#baginfo"><span class="aa">&nbsp;
                      <br />
                      &nbsp;</span>
                    <span class="bb"><bean:message key="menu.bag_info" /></span>
                    <span class="cc">&nbsp;
                      <br />
                      &nbsp;</span></a>
                </dd>
                <logic:present name="missing" scope="request">
                  <dd>
                    <a href="#missingarticles"><span class="aa">&nbsp;
                        <br />
                        &nbsp;</span>
                      <span class="bb"><bean:message key="menu.ma" /></span>
                      <span class="cc">&nbsp;
                        <br />
                        &nbsp;</span></a>
                  </dd>
                </logic:present>
              </dl>
            </div>
          </td>
        </tr>
        <!-- END ICONS MENU -->
        <!-- END ICONS MENU -->
        <tr>
          <!-- MIDDLE COLUMN -->
          <td id="middlecolumn">
            <!-- MAIN BODY -->
            <div id="maincontent">
              <a name="incidentinfo"></a>
              <h1 class="green">
                <bean:message key="header.incident_info" />
              </h1>
              <br>
              <table class="form2" cellspacing="0" cellpadding="0">
                <tr>
                  <td nowrap>
                    <bean:message key="colname.incident_num" />
                    :
                    <br>
                    <html:text name="theform" property="incident_ID" size="14" styleClass="textfield" readonly="true" />
                  </td>
                  <td nowrap>
                    <bean:message key="colname.incident_create_date" />
                    :
                    <br>
                    <html:text name="theform" property="dispcreatetime" size="20" styleClass="textfield" readonly="true" />
                  </td>
                  <td nowrap>
                    <bean:message key="colname.status" />
                    :
                    <br>
                    <html:text name="theform" property="status.description" size="20" styleClass="textfield" readonly="true" />
                  </td>
                </tr>
              </table>
              <a name="contact"></a>
              <h1 class="green">
                <bean:message key="header.passenger_info" />
              </h1>
              <logic:iterate id="passenger" name="theform" property="passengerlist" indexId="i" type="com.bagnet.nettracer.tracing.db.Passenger">
                <table class="form2" cellspacing="0" cellpadding="0">
                  <tr>
                    <td colspan=5>
<%
                      if (i.intValue() > 0) {
%>
                        <b><bean:message key="colname.addi_pass_info" />
                        :
<%
                      } else {
%>
                        <b><bean:message key="colname.pri_pass_info" />
                        :
<%
                      }
%>
                    </td>
                  </tr>
                  <tr>
                    <td nowrap colspan=2>
                      <bean:message key="colname.last_name" />
                      :
                      <br>
                      <html:text name="passenger" property="lastname" size="20" maxlength="20" indexed="true" styleClass="textfield" readonly="true" />
                    </td>
                    <td nowrap colspan=2>
                      <bean:message key="colname.first_name" />
                      :
                      <br>
                      <html:text name="passenger" property="firstname" size="20" maxlength="20" indexed="true" styleClass="textfield" readonly="true" />
                    </td>
                    <td>
                      <bean:message key="colname.mid_initial" />
                      :
                      <br>
                      <html:text name="passenger" property="middlename" size="1" maxlength="1" indexed="true" styleClass="textfield" readonly="true" />
                    </td>
                  </tr>
                  <tr>
                    <td nowrap>
                      <bean:message key="colname.airline_membership" />
                      :
                      <br>
                      <html:text name="passenger" property="membership.companycode_ID" size="3" maxlength="3" indexed="true" styleClass="textfield" readonly="true" />
                    </td>
                    <td nowrap>
                      <bean:message key="colname.membership_status" />
                      :
                      <br>
                      <html:text name="passenger" indexed="true" property="membership.membershipstatus" size="20" maxlength="20" styleClass="textfield" readonly="true" />
                    </td>
                    <td nowrap colspan=3>
                      <bean:message key="colname.membership_number" />
                      :
                      <br>
                      <html:text name="passenger" indexed="true" property="membership.membershipnum" size="30" maxlength="20" styleClass="textfield" readonly="true" />
                    </td>
                  </tr>
                  <logic:present name="passenger" property="addresses">
                    <logic:iterate indexId="k" name="passenger" id="addresses" property="addresses" type="com.bagnet.nettracer.tracing.db.Address">
                      <tr>
                        <td colspan=2>
                          <bean:message key="colname.street_addr1" />
                          :
                          <br>
                          <html:text name="addresses" property="address1" size="45" maxlength="50" styleClass="textfield" indexed="true" readonly="true" />
                        </td>
                        <td colspan=3>
                          <bean:message key="colname.street_addr2" />
                          :
                          <br>
                          <html:text name="addresses" property="address2" size="45" maxlength="50" styleClass="textfield" indexed="true" readonly="true" />
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <bean:message key="colname.city" />
                          :
                          <br>
                          <html:text name="addresses" property="city" size="15" maxlength="50" styleClass="textfield" indexed="true" readonly="true" />
                        </td>
                        <td>
                          <bean:message key="colname.state" />
                          :
                          <br>
                          <html:text name="addresses" property="state_ID" size="2" maxlength="2" styleClass="textfield" indexed="true" readonly="true" />
                        </td>
                        <td>
                          <bean:message key="colname.province" />
                          :
                          <br>
                          <html:text name="addresses" property="province" size="15" maxlength="100" styleClass="textfield" indexed="true" readonly="true" />
                        </td>
                        <td>
                          <bean:message key="colname.zip" />
                          :
                          <br>
                          <html:text name="addresses" property="zip" size="15" maxlength="11" styleClass="textfield" indexed="true" readonly="true" />
                        </td>
                        <td>
                          <bean:message key="colname.country" />
                          :
                          <br>
                          <html:text name="addresses" property="countrycode_ID" size="3" maxlength="3" styleClass="textfield" indexed="true" readonly="true" />
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <bean:message key="colname.home_ph" />
                          :
                          <br>
                          <html:text name="addresses" property="homephone" size="15" maxlength="25" styleClass="textfield" indexed="true" readonly="true" />
                        </td>
                        <td>
                          <bean:message key="colname.business_ph" />
                          :
                          <br>
                          <html:text name="addresses" property="workphone" size="15" maxlength="25" styleClass="textfield" indexed="true" readonly="true" />
                        </td>
                        <td>
                          <bean:message key="colname.mobile_ph" />
                          :
                          <br>
                          <html:text name="addresses" property="mobile" size="15" maxlength="25" styleClass="textfield" indexed="true" readonly="true" />
                        </td>
                        <td>
                          <bean:message key="colname.pager_ph" />
                          :
                          <br>
                          <html:text name="addresses" property="pager" size="15" maxlength="25" styleClass="textfield" indexed="true" readonly="true" />
                        </td>
                        <td>
                          <bean:message key="colname.alt_ph" />
                          :
                          <br>
                          <html:text name="addresses" property="altphone" size="15" maxlength="25" styleClass="textfield" indexed="true" readonly="true" />
                        </td>
                      </tr>
                      <tr>
                        <td colspan=2 width=50%>
                          <bean:message key="colname.hotel" />
                          :
                          <br>
                          <html:text name="addresses" property="hotel" size="45" maxlength="50" styleClass="textfield" indexed="true" readonly="true" />
                        </td>
                        <td colspan=3 width=50%>
                          <bean:message key="colname.email" />
                          :
                          <br>
                          <html:text name="addresses" property="email" size="45" maxlength="50" styleClass="textfield" indexed="true" readonly="true" />
                        </td>
                      </tr>
                    </logic:iterate>
                  </logic:present>
                </table>
              </logic:iterate>
              <br>
              <br>
              &nbsp;&nbsp;&uarr;
              <a href="#"><bean:message key="link.to_top" /></a>
              <br>
              <br>
              <a name="passit"></a>
              <h1 class="green">
                <bean:message key="header.itinerary" />
              </h1>
              <logic:iterate id="theitinerary" indexId="k" name="theform" property="itinerarylist">
                <logic:equal name="theitinerary" property="itinerarytype" value="0">
                  <html:hidden name="theitinerary" property="itinerarytype" value="0" indexed="true" />
                  <table class="form2" cellspacing="0" cellpadding="0">
                    <tr>
                      <td>
                        <bean:message key="colname.fromto" />
                        :
                        <br>
                        <html:text name="theitinerary" property="legfrom" size="3" maxlength="3" styleClass="textfield" indexed="true" readonly="true" />
                        /
                        <html:text name="theitinerary" property="legto" size="3" maxlength="3" styleClass="textfield" indexed="true" readonly="true" />
                      </td>
                      <td>
                        <bean:message key="colname.flightnum" />
                        :
                        <br>
                        <html:text name="theitinerary" property="airline" size="3" maxlength="3" styleClass="textfield" indexed="true" readonly="true" />
                        &nbsp;
                        <html:text name="theitinerary" property="flightnum" size="4" maxlength="4" styleClass="textfield" indexed="true" readonly="true" />
                      </td>
                      <td nowrap>
                        <bean:message key="colname.departdate" />
                        :
                        <br>
                        <html:text name="theitinerary" property="disdepartdate" size="11" maxlength="10" styleClass="textfield" indexed="true" readonly="true" />
                      </td>
                      <td nowrap>
                        <bean:message key="colname.arrdate" />
                        :
                        <br>
                        <html:text name="theitinerary" property="disarrivedate" size="11" maxlength="10" styleClass="textfield" indexed="true" readonly="true" />
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <bean:message key="colname.schdeptime" />
                        :
                        <br>
                        <html:text name="theitinerary" property="disschdeparttime" size="5" maxlength="5" styleClass="textfield" indexed="true" readonly="true" />
                      </td>
                      <td>
                        <bean:message key="colname.scharrtime" />
                        :
                        <br>
                        <html:text name="theitinerary" property="disscharrivetime" size="5" maxlength="5" styleClass="textfield" indexed="true" readonly="true" />
                      </td>
                      <td>
                        <bean:message key="colname.actdeptime" />
                        :
                        <br>
                        <html:text name="theitinerary" property="disactdeparttime" size="5" maxlength="5" styleClass="textfield" indexed="true" readonly="true" />
                      </td>
                      <td>
                        <bean:message key="colname.actarrtime" />
                        :
                        <br>
                        <html:text name="theitinerary" property="disactarrivetime" size="5" maxlength="5" styleClass="textfield" indexed="true" readonly="true" />
                      </td>
                    </tr>
                  </table>
                </logic:equal>
              </logic:iterate>
              <a name="bagit"></a>
              <h1 class="green">
                <bean:message key="header.bag_itinerary" />
              </h1>
              <logic:iterate id="theitinerary" indexId="k" name="theform" property="itinerarylist">
                <logic:equal name="theitinerary" property="itinerarytype" value="1">
                  <html:hidden name="theitinerary" property="itinerarytype" value="1" indexed="true" />
                  <table class="form2" cellspacing="0" cellpadding="0">
                    <tr>
                      <td>
                        <bean:message key="colname.fromto" />
                        :
                        <br>
                        <html:text name="theitinerary" property="legfrom" size="3" maxlength="3" styleClass="textfield" indexed="true" readonly="true" />
                        /
                        <html:text name="theitinerary" property="legto" size="3" maxlength="3" styleClass="textfield" indexed="true" readonly="true" />
                      </td>
                      <td>
                        <bean:message key="colname.flightnum" />
                        :
                        <br>
                        <html:text name="theitinerary" property="airline" size="3" maxlength="3" styleClass="textfield" indexed="true" readonly="true" />
                        &nbsp;
                        <html:text name="theitinerary" property="flightnum" size="4" maxlength="4" styleClass="textfield" indexed="true" readonly="true" />
                      </td>
                      <td nowrap>
                        <bean:message key="colname.departdate" />
                        :
                        <br>
                        <html:text name="theitinerary" property="disdepartdate" size="11" maxlength="10" styleClass="textfield" indexed="true" readonly="true" />
                      </td>
                      <td nowrap>
                        <bean:message key="colname.arrdate" />
                        :
                        <br>
                        <html:text name="theitinerary" property="disarrivedate" size="11" maxlength="10" styleClass="textfield" indexed="true" readonly="true" />
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <bean:message key="colname.schdeptime" />
                        :
                        <br>
                        <html:text name="theitinerary" property="disschdeparttime" size="5" maxlength="5" styleClass="textfield" indexed="true" readonly="true" />
                      </td>
                      <td>
                        <bean:message key="colname.scharrtime" />
                        :
                        <br>
                        <html:text name="theitinerary" property="disscharrivetime" size="5" maxlength="5" styleClass="textfield" indexed="true" readonly="true" />
                      </td>
                      <td>
                        <bean:message key="colname.actdeptime" />
                        :
                        <br>
                        <html:text name="theitinerary" property="disactdeparttime" size="5" maxlength="5" styleClass="textfield" indexed="true" readonly="true" />
                      </td>
                      <td>
                        <bean:message key="colname.actarrtime" />
                        :
                        <br>
                        <html:text name="theitinerary" property="disactarrivetime" size="5" maxlength="5" styleClass="textfield" indexed="true" readonly="true" />
                      </td>
                    </tr>
                  </table>
                </logic:equal>
              </logic:iterate>
              <br>
              <br>
              &nbsp;&nbsp;&uarr;
              <a href="#"><bean:message key="link.to_top" /></a>
              <br>
              <br>
              <logic:present name="lostdelay" scope="request">
              <!-- claimcheck numbers -->
              <a name="claimcheck"></a>
              <h1 class="green">
                <bean:message key="colname.claimnum" />
              </h1>
              <table class="form2" cellspacing="0" cellpadding="0">
                <logic:iterate id="claimcheck" indexId="i" name="theform" property="claimchecklist">
                  <tr>
                    <td width=30% nowrap>
                      <bean:message key="colname.claimnum" />
                      :
                    </td>
                    <td>
                      <html:text name="claimcheck" property="claimchecknum" size="13" maxlength="13" styleClass="textfield" indexed="true" readonly="true" />
                    </td>
                  </tr>
                </logic:iterate>
              </table>
            </logic:present>
            <a name="baginfo"></a>
            <h1 class="green">
              <logic:present name="damaged" scope="request">
                <bean:message key="header.damaged_bag_info" />
              </logic:present>
              <logic:notPresent name="damaged" scope="request">
                <bean:message key="header.bag_info" />
              </logic:notPresent>
            </h1>
            <logic:iterate id="theitem" indexId="i" name="theform" property="itemlist" type="com.bagnet.nettracer.tracing.db.Item">
              <table class="form2" cellspacing="0" cellpadding="0">
                <tr>
                  <td valign=top
                    <logic:present name="lostdelay" scope="request">
                      colspan=3
                    </logic:present>
                    >
                    <b><bean:message key="colname.bag_number" />
                    : &nbsp;&nbsp;
                    <%= theitem.getBagnumber() + 1 %>
                  </td>
                  <logic:notPresent name="lostdelay" scope="request">
                    <td colspan=2>
                      <bean:message key="colname.claimnum" />
                      :
                      <br>
                      <html:text name="theitem" property="claimchecknum" size="13" maxlength="13" styleClass="textfield" indexed="true" readonly="true" />
                    </td>
                  </logic:notPresent>
                </tr>
                <tr>
                  <td colspan=3>
                    <bean:message key="colname.bag_status" />
                    <br>
                    <html:text name="theitem" property="status.description" size="25" maxlength="25" styleClass="textfield" indexed="true" readonly="true" />
                  </td>
                </tr>
                <tr>
                  <td>
                    <bean:message key="colname.last_name_onbag" />
                    :
                    <br>
                    <html:text name="theitem" property="lnameonbag" size="30" maxlength="50" styleClass="textfield" indexed="true" readonly="true" />
                  </td>
                  <td>
                    <bean:message key="colname.first_name_onbag" />
                    :
                    <br>
                    <html:text name="theitem" property="fnameonbag" size="30" maxlength="50" styleClass="textfield" indexed="true" readonly="true" />
                  </td>
                  <td>
                    <bean:message key="colname.mid_initial_onbag" />
                    :
                    <br>
                    <html:text name="theitem" property="mnameonbag" size="1" maxlength="1" styleClass="textfield" indexed="true" readonly="true" />
                  </td>
                </tr>
                <tr>
                  <td valign=top>
                    <bean:message key="colname.color" />
                    :
                    <br>
                    <html:text name="theitem" property="color" size="5" maxlength="3" styleClass="textfield" indexed="true" readonly="true" />
                    <br>
                    <br>
                    <bean:message key="colname.bagtype" />
                    :
                    <br>
                    <html:text name="theitem" property="bagtype" size="5" maxlength="3" styleClass="textfield" indexed="true" readonly="true" />
                  </td>
                  <td valign=top>
                    <bean:message key="colname.x_desc" />
                    :
                    <br>
                    <html:text name="theitem" property="xdescelement1" size="30" styleClass="textfield" indexed="true" readonly="true" />
                    <br>
                    <html:text name="theitem" property="xdescelement2" size="30" styleClass="textfield" indexed="true" readonly="true" />
                    <br>
                    <html:text name="theitem" property="xdescelement3" size="30" styleClass="textfield" indexed="true" readonly="true" />
                  </td>
                  <td valign=top>
                    <bean:message key="colname.manufacturer" />
                    :
                    <br>
                    <html:text name="theitem" property="manuname" size="30" styleClass="textfield" indexed="true" readonly="true" />
                    <div id="manu_other<%= i %>">
                      <br>
                      <html:text name="theitem" property="manufacturer_other" size="30" styleClass="textfield" indexed="true" readonly="true" />
                    </div>
                  </td>
                </tr>
                <tr>
                  <td colspan=3>
                    <bean:message key="colname.key_contents" />
                    :
                    <br>
                    
                    <bean:message key="colname.key_contents" />
										<a name='<%= "inventory" + i %>'></a>
			              <bean:define id="inventories" name="theitem" property="inventorylist" />
			              
			              <logic:iterate id="inventorylist" indexId="j" name="inventories" type="com.bagnet.nettracer.tracing.db.Item_Inventory">
				            <table class="form2" cellspacing="0" cellpadding="0" width="100%">
				              <tr>
				                <td>
				                  <bean:message key="colname.category" /><br>
				                  <html:select name="inventorylist" property="categorytype_ID" styleClass="dropdown">
				                  	<html:option value="">None</html:option>
				                    <html:options collection="categorylist" property="OHD_CategoryType_ID" labelProperty="categorytype" />
				                  </html:select>
				                </td>
				                <td>
				                  <bean:message key="colname.description" />
				                  <br>
				                  <html:text name="inventorylist" property="description" size="80" maxlength="255" styleClass="textfield"  readonly="true" />
				                </td>
				              </tr>
				            </table>
				          	</logic:iterate>
	

                  </td>
                </tr>
                <logic:present name="damaged" scope="request">
                  <tr>
                    <td colspan=3>
                      <bean:message key="colname.damagedesc" />
                      :
                      <br>
                      <html:text name="theitem" property="damage" size="30" maxlength="255" styleClass="textfield" indexed="true" readonly="true" />
                    </td>
                  </tr>
                </logic:present>
              </table>
            </logic:iterate>
            <br>
            <br>
            &nbsp;&nbsp;&uarr;
            <a href="#"><bean:message key="link.to_top" /></a>
            <br>
            <br>
            <logic:present name="missing" scope="request">
            <!-- missing articles -->
            <a name="missingarticles"></a>
            <h1 class="green">
              <bean:message key="header.ma" />
            </h1>
            <logic:iterate id="article" indexId="i" name="theform" property="articlelist">
              <table class="form2" cellspacing="0" cellpadding="0">
                <tr>
                  <td>
                    <bean:message key="colname.article" />
                    :
                    <br>
                    <html:text name="article" property="article" size="30" maxlength="50" styleClass="textfield" indexed="true" readonly="true" />
                  </td>
                </tr>
                <tr>
                  <td>
                    <bean:message key="colname.desc" />
                    :
                    <br>
                    <html:textarea name="article" property="description" cols="80" rows="5" styleClass="textarea_medium" indexed="true" readonly="true" />
                  </td>
                </tr>
              </table>
            </logic:iterate>
            <br>
            <br>
            &nbsp;&nbsp;&uarr;
            <a href="#"><bean:message key="link.to_top" /></a>
            <br>
            <br>
          </logic:present>
        </div>
      </td>
    </tr>
    
   	</logic:present>

    <SCRIPT LANGUAGE="JavaScript">
      <!--

	// happens after load
 for (var j=0;j<document.searchform.length;j++) {
    currentElement = document.searchform.elements[j];
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
    
    </table>
    
    </form>
    
  </td>
</tr>
<tr>
  <td bgcolor=white>
  
  <img src="<%=request.getContextPath()%>/deployment/main/images/nettracer/poweredby_net_tracer.jpg"  alt="Powered by Net Tracer" class="imgAlignBottom">
  
  
  
  
  </td>
</tr>
</table>
</body>
</html>

