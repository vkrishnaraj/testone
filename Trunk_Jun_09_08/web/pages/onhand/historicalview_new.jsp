<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.OHD_Photo" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%
  Agent a = (Agent)session.getAttribute("user");
%>
  <script language="javascript">
    

function CheckBoxGroup() {
  this.controlBox=null;
  this.controlBoxChecked=null;
  this.maxAllowed=null;
  this.maxAllowedMessage=null;
  this.masterBehavior=null;
  this.formRef=null;
  this.checkboxWildcardNames=new Array();
  this.checkboxNames=new Array();
  this.totalBoxes=0;
  this.totalSelected=0;

  this.setControlBox=CBG_setControlBox;
  this.setMaxAllowed=CBG_setMaxAllowed;
  this.setMasterBehavior=CBG_setMasterBehavior;
  this.addToGroup=CBG_addToGroup;

  this.expandWildcards=CBG_expandWildcards;
  this.addWildcardCheckboxes=CBG_addWildcardCheckboxes;
  this.addArrayCheckboxes=CBG_addArrayCheckboxes;
  this.addSingleCheckbox=CBG_addSingleCheckbox;
  this.check=CBG_check;
  }


	function CBG_setControlBox(name) { 
		this.controlBox=name; 
	}



	function CBG_setMaxAllowed(num,msg) {
	  this.maxAllowed=num;
	  if (msg!=null&&msg!="") { this.maxAllowedMessage=msg; }
	  }




function CBG_setMasterBehavior(b) { this.masterBehavior = b.toLowerCase(); }


function CBG_addToGroup() {
  if (arguments.length>0) {
    for (var i=0;i<arguments.length;i++) {
      this.checkboxWildcardNames[this.checkboxWildcardNames.length]=arguments[i];
      }
    }
  }


function CBG_expandWildcards() {
  if (this.formRef==null) {alert("ERROR: No form element has been passed.  Cannot extract form name!"); return false; }
  for (var i=0; i<this.checkboxWildcardNames.length;i++) {
    var n = this.checkboxWildcardNames[i];
  var el;
  for (var j=0;j<this.formRef.length;j++) {
    currentElement = this.formRef.elements[j];
    currentElementName=currentElement.name;
    if (currentElementName == n)
    {
      el=currentElement;
      break;
    }
  }
  if (n.indexOf("*")!=-1) { this.addWildcardCheckboxes(n); }
    else if(CBG_nameIsArray(el)) { this.addArrayCheckboxes(n); }
    else { this.addSingleCheckbox(el); }
    }
  }



function CBG_addWildcardCheckboxes(name) {
  var i=name.indexOf("*");
  if ((i==0) || (i==name.length-1)) {
    searchString= (i)?name.substring(0,name.length-1):name.substring(1,name.length);
    for (var j=0;j<this.formRef.length;j++) {
      currentElement = this.formRef.elements[j];
      currentElementName=currentElement.name;
      var partialName = (i)?currentElementName.substring(0,searchString.length) : currentElementName.substring(currentElementName.length-searchString.length,currentElementName.length);
      if (partialName==searchString) {
        if(CBG_nameIsArray(currentElement)) this.addArrayCheckboxes(currentElement);
        else this.addSingleCheckbox(currentElement);
        }
      }
    }
  }


function CBG_addArrayCheckboxes(name) {
  if((CBG_nameIsArray(this.formRef[name])) && (this.formRef[name].length>0)) {
    for (var i=0; i<this.formRef[name].length; i++) { this.addSingleCheckbox(this.formRef[name][i]); }
    }
  }

function CBG_addSingleCheckbox(obj) {
  if (obj != this.formRef[this.controlBox]) {
    this.checkboxNames[this.checkboxNames.length]=obj;
    this.totalBoxes++;
    if (obj.checked) {
      this.totalSelected++;
      }
    }
  }


function CBG_check(obj) {
  var checked=obj.checked;
  if (this.formRef==null) {
    this.formRef=document.forms[0];
    this.expandWildcards();
    
  if (this.controlBox==null || obj.name!=this.controlBox) {
      this.totalSelected += (checked)?-1:1;
      }
    }
  if (this.controlBox!=null&&obj.name==this.controlBox) {
    if (this.masterBehavior=="all") {
      for (i=0;i<this.checkboxNames.length;i++) { this.checkboxNames[i].checked=checked; }
      this.totalSelected=(checked)?this.checkboxNames.length:0;
      }
    else {
      
    for (i=0;i<this.checkboxNames.length;i++) { this.checkboxNames[i].checked=checked; }
    this.totalSelected=(checked)?this.checkboxNames.length:0;

      }
    }
  else {
    if (this.masterBehavior=="all") {
      if (!checked) {
        this.formRef[this.controlBox].checked=false;
        this.totalSelected--;
        }
      else { this.totalSelected++; }
      if (this.controlBox!=null) {
      this.formRef[this.controlBox].checked=(this.totalSelected==this.totalBoxes)?true:false;
        }
      }
    else {
      if (!obj.checked) { this.totalSelected--; } 
      else { this.totalSelected++; }
    if (this.controlBox!=null) {
      for (var j=0;j<this.formRef.length;j++) {
      currentElement = this.formRef.elements[j];
      currentElementName=currentElement.name;
      if (currentElementName == this.controlBox)
      {
        currentElement.checked=(this.totalSelected>0)?true:false;
        break;
      }
    }
    }
      if (this.maxAllowed!=null) {
        if (this.totalSelected>this.maxAllowed) {
          obj.checked=false;
          this.totalSelected--;
          if (this.maxAllowedMessage!=null) { alert(this.maxAllowedMessage); }
          return false;
          }
        }
      }
    }
  }

	function CBG_nameIsArray(obj) {
  	return ((typeof obj.type!="string")&&(obj.length>0)&&(obj[0]!=null)&&(obj[0].type=="checkbox"));
  }
  
	function generateHistoricalView(group) {
  	o = document.OnHandForm;
  	o.viewhistoryreport.value = 'html';
  	o.submit();
	}
	
	var sections = new CheckBoxGroup();
  sections.setControlBox("all");
  sections.setMasterBehavior("all");
  sections.addToGroup("passenger");
  sections.addToGroup("itinerary");
  sections.addToGroup("inventory");
 	sections.addToGroup("remarks");
 	
 	<%
          if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_OHD_PHOTOS, a)) {
%>
  sections.addToGroup("photos");
     	<%
        }
%>
  sections.addToGroup("messages");
 	sections.addToGroup("tasks");	
 	sections.addToGroup("forward");	
 	sections.addToGroup("request");	
  sections.addToGroup("matches");	

  </script>
  <script language="javascript">
    
function gotoHistoricalReport() {
  o = document.OnHandForm;
	o.historical_report.value = "1";
	o.submit();
}

  </script>
  <html:form action="addOnHandBag.do" method="post">
    <input type="hidden" name="historical_report" value="">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.historical_view" />
            (
            <a href='addOnHandBag.do?ohd_ID=<bean:write name="OnHandForm" property="ohd_id"/>'><bean:write name="OnHandForm" property="ohd_id" /></a>
            )
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
      <td colspan="3" id="navmenucell">
        <div class="menu">
          <dl>
            <dd>
              <a href="#" onclick="gotoHistoricalReport();"><span class="aab">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bbb"><bean:message key="menu.history.options" /></span>
                <span class="ccb">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <logic:present name="reportfile" scope="request">
              <logic:present name="passenger" scope="request">
                <dd>
                  <a href="#passengerinfo"><span class="aa">&nbsp;
                      <br />
                      &nbsp;</span>
                    <span class="bb"><bean:message key="menu.passenger_info" /></span>
                    <span class="cc">&nbsp;
                      <br />
                      &nbsp;</span></a>
                </dd>
              </logic:present>
              <logic:present name="itinerary" scope="request">
                <dd>
                  <a href="#custitin"><span class="aa">&nbsp;
                      <br />
                      &nbsp;</span>
                    <span class="bb"><bean:message key="menu.bag_itinerary" /></span>
                    <span class="cc">&nbsp;
                      <br />
                      &nbsp;</span></a>
                </dd>
              </logic:present>
              <logic:present name="inventory" scope="request">
                <dd>
                  <a href="#centralbag"><span class="aa">&nbsp;
                      <br />
                      &nbsp;</span>
                    <span class="bb"><bean:message key="menu.central_baggage_inventory" /></span>
                    <span class="cc">&nbsp;
                      <br />
                      &nbsp;</span></a>
                </dd>
              </logic:present>
<%
              if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_OHD_PHOTOS, a)) {
%>
                <logic:present name="photos" scope="request">
                  <dd>
                    <a href="#photos"><span class="aa">&nbsp;
                        <br />
                        &nbsp;</span>
                      <span class="bb"><bean:message key="menu.photos" /></span>
                      <span class="cc">&nbsp;
                        <br />
                        &nbsp;</span></a>
                  </dd>
                </logic:present>
<%
              }
%>
              <logic:present name="remarks" scope="request">
                <dd>
                  <a href="#remarks"><span class="aa">&nbsp;
                      <br />
                      &nbsp;</span>
                    <span class="bb"><bean:message key="menu.remarks" /></span>
                    <span class="cc">&nbsp;
                      <br />
                      &nbsp;</span></a>
                </dd>
              </logic:present>
              <logic:present name="requestList" scope="request">
                <dd>
                  <a href="#requests"><span class="aa">&nbsp;
                      <br />
                      &nbsp;</span>
                    <span class="bb"><bean:message key="menu.requests" /></span>
                    <span class="cc">&nbsp;
                      <br />
                      &nbsp;</span></a>
                </dd>
              </logic:present>
              <logic:present name="forward" scope="request">
                <dd>
                  <a href="#forward_log"><span class="aa">&nbsp;
                      <br />
                      &nbsp;</span>
                    <span class="bb"><bean:message key="menu.forward_log" /></span>
                    <span class="cc">&nbsp;
                      <br />
                      &nbsp;</span></a>
                </dd>
              </logic:present>
              <logic:present name="matches" scope="request">
                <dd>
                  <a href="#matches"><span class="aa">&nbsp;
                      <br />
                      &nbsp;</span>
                    <span class="bb"><bean:message key="menu.matches" /></span>
                    <span class="cc">&nbsp;
                      <br />
                      &nbsp;</span></a>
                </dd>
              </logic:present>
              <logic:present name="tasks" scope="request">
                <dd>
                  <a href="#tasks"><span class="aa">&nbsp;
                      <br />
                      &nbsp;</span>
                    <span class="bb"><bean:message key="menu.tasks" /></span>
                    <span class="cc">&nbsp;
                      <br />
                      &nbsp;</span></a>
                </dd>
              </logic:present>
              <logic:present name="messages" scope="request">
                <dd>
                  <a href="#inbox"><span class="aa">&nbsp;
                      <br />
                      &nbsp;</span>
                    <span class="bb"><bean:message key="menu.inbox" /></span>
                    <span class="cc">&nbsp;
                      <br />
                      &nbsp;</span></a>
                </dd>
              </logic:present>
            </logic:present>
          </dl>
        </div>
      </td>
    </tr>
    <tr>
      <logic:notPresent name="reportfile" scope="request">
      
      <td id="middlecolumn">
        
        <div id="maincontent">
          <h1 class="green">
            <bean:message key="header.report_options" />
            <a href="#"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <br>
          <input type="hidden" name="ohd_id" value="<bean:write name="OnHandForm" property="ohd_id"/>">
          <input type="hidden" name="viewhistoryreport" value="">
          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td width="100%">
                <INPUT TYPE=CHECKBOX NAME="all" <% if (request.getAttribute("all") == null || ((String)request.getAttribute("all")).equals("1")) { %> CHECKED <% } %> onClick="sections.check(this);">
                <bean:message key="select.all" />
              </td>
            </tr>
            <tr>
              <td width="100%" NOWRAP>
                <INPUT TYPE=CHECKBOX NAME="passenger" <% if (request.getAttribute("passenger") != null || (request.getAttribute("all") == null || (request.getAttribute("all") != null && ((String)request.getAttribute( "all")).equals("1")))) { %> CHECKED <% } %> onClick="sections.check(this);">
                <bean:message key="header.passenger_info" />
                &nbsp;&nbsp;
                <INPUT TYPE=CHECKBOX NAME="itinerary" <% if (request.getAttribute("itinerary") != null || (request.getAttribute("all") == null || (request.getAttribute("all") != null && ((String)request.getAttribute( "all")).equals("1")))) { %> CHECKED <% } %> onClick="sections.check(this);">
                <bean:message key="header.bag_itinerary" />
                &nbsp;&nbsp;
                <INPUT TYPE=CHECKBOX NAME="inventory" <% if (request.getAttribute("inventory") != null || (request.getAttribute("all") == null || (request.getAttribute("all") != null && ((String)request.getAttribute( "all")).equals("1")))) { %> CHECKED <% } %> onClick="sections.check(this);">
                <bean:message key="header.central_baggage_inventory" />
                &nbsp;&nbsp;
                <INPUT TYPE=CHECKBOX NAME="remarks" <% if (request.getAttribute("remarks") != null || (request.getAttribute("all") == null || (request.getAttribute("all") != null && ((String)request.getAttribute( "all")).equals("1")))) { %> CHECKED <% } %> onClick="sections.check(this);">
                <bean:message key="header.remarks" />
                &nbsp;&nbsp;
<%
                if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_OHD_PHOTOS, a)) {
%>
                  <INPUT TYPE=CHECKBOX NAME="photos" <% if (request.getAttribute("photos") != null || (request.getAttribute("all") == null || (request.getAttribute("all") != null && ((String)request.getAttribute( "all")).equals("1")))) { %> CHECKED <% } %> onClick="sections.check(this);">
                  <bean:message key="header.photos" />
                  &nbsp;&nbsp;
<%
                }
%>
                <INPUT TYPE=CHECKBOX NAME="forward" <% if (request.getAttribute("forward") != null || (request.getAttribute("all") == null || (request.getAttribute("all") != null && ((String)request.getAttribute( "all")).equals("1")))) { %> CHECKED <% } %> onClick="sections.check(this);">
                <bean:message key="header.forward_log" />
                &nbsp;&nbsp;
                <INPUT TYPE=CHECKBOX NAME="request" <% if (request.getAttribute("request") != null || (request.getAttribute("all") == null || (request.getAttribute("all") != null && ((String)request.getAttribute( "all")).equals("1")))) { %> CHECKED <% } %> onClick="sections.check(this);">
                <bean:message key="header.requests" />
                &nbsp;&nbsp;
                <INPUT TYPE=CHECKBOX NAME="matches" <% if (request.getAttribute("matches") != null || (request.getAttribute("all") == null || (request.getAttribute("all") != null && ((String)request.getAttribute( "all")).equals("1")))) { %> CHECKED <% } %> onClick="sections.check(this);">
                <bean:message key="header.matches" />
                &nbsp;&nbsp;
                <INPUT TYPE=CHECKBOX NAME="messages" <% if (request.getAttribute("messages") != null || (request.getAttribute("all") == null || (request.getAttribute("all") != null && ((String)request.getAttribute( "all")).equals("1")))) { %> CHECKED <% } %> onClick="sections.check(this);">
                <bean:message key="header.messages" />
                &nbsp;&nbsp;
                <INPUT TYPE=CHECKBOX NAME="tasks" <% if (request.getAttribute("tasks") != null || (request.getAttribute("all") == null || (request.getAttribute("all") != null && ((String)request.getAttribute( "all")).equals("1")))) { %> CHECKED <% } %> onClick="sections.check(this);">
                <bean:message key="header.tasks" />
              </td>
            </tr>
            <tr>
              <td colspan="2" align="center" valign="top" bgcolor=white>
                <br>
                <html:submit property="create" onclick="generateHistoricalView('history_type')" styleId="button">
                  <bean:message key="button.createreport" />
                </html:submit>
              </td>
            </tr>
          </table>
        </logic:notPresent>
        <logic:present name="reportfile" scope="request">
        
        <td id="middlecolumn">
          
          <div id="maincontent">
            <table class="form2" cellspacing="0" cellpadding="0">
              <a name="baginfo"></a>
              <h1 class="green">
                <bean:message key="header.bag_info" />
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#On_Hand_Bag.htm#Found_and_unclaimed');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
              </h1>
              <table class="form2" cellspacing="0" cellpadding="0">
                <tr>
                  <td>
                    <bean:message key="colname.on_hand_report_number" />
                    :
                  </td>
                  <td>
                    <bean:write name="OnHandForm" property="ohd_id" />
                  </td>
                </tr>
                <tr>
                  <td>
                    <bean:message key="colname.status" />
                    :
                  </td>
                  <td>
                    <bean:message name="OnHandForm" property="status.key" />
                  </td>
                </tr>
                <tr>
                  <td>
                    <bean:message key="colname.found_station" />
                    :
                  </td>
                  <td>
                    <bean:write name="OnHandForm" property="found_company" />
                    <bean:write name="OnHandForm" property="found_station" />
                  </td>
                </tr>
                <tr>
                  <td>
                    <bean:message key="colname.holding_station" />
                    :
                  </td>
                  <td>
                    <bean:write name="OnHandForm" property="holding_company" />
                    <bean:write name="OnHandForm" property="holding_station" />
                  </td>
                </tr>
                <tr>
                  <td>
                    <bean:message key="colname.agent_initials" />
                    :
                  </td>
                  <td>
                    <bean:write name="OnHandForm" property="agent_initials" />
                  </td>
                </tr>
                <tr>
                  <td>
                    <bean:message key="colname.found_date_time" />
                    :
                  </td>
                  <td>
                    <bean:write name="OnHandForm" property="dispFoundTime" />
                  </td>
                </tr>
                <tr>
                  <td>
                    <bean:message key="colname.bag_arrived_date" />
                    :
                  </td>
                  <td>
                    <logic:empty name="OnHandForm" property="dispBagArriveDate">
                      &nbsp;
                    </logic:empty>
                    <logic:notEmpty name="OnHandForm" property="dispBagArriveDate">
                      <bean:write name="OnHandForm" property="dispBagArriveDate" />
                    </logic:notEmpty>
                  </td>
                </tr>
                <logic:notEmpty name="OnHandForm" property="status">
                  <logic:equal name="OnHandForm" property="status.status_ID" value="<%= "" + TracingConstants.OHD_STATUS_CLOSED %>">
                    <tr>
                      <td>
                        <bean:message key="colname.file_close_date" />
                        :
                      </td>
                      <td>
                        <bean:write name="OnHandForm" property="dispCloseDate" />
                        &nbsp;
                      </td>
                    </tr>
                  </logic:equal>
                </logic:notEmpty>
                <tr>
                  <td>
                    <bean:message key="colname.storage_location" />
                    :
                  </td>
                  <td>
                    <logic:empty name="OnHandForm" property="storage_location">
                      &nbsp;
                    </logic:empty>
                    <logic:notEmpty name="OnHandForm" property="storage_location">
                      <bean:write name="OnHandForm" property="storage_location" />
                    </logic:notEmpty>
                    &nbsp;
                  </td>
                </tr>
                <tr>
                  <td>
                    <bean:message key="colname.bag_tag_number" />
                    :
                  </td>
                  <td>
                    <bean:write name="OnHandForm" property="bagTagNumber" />
                    &nbsp;
                  </td>
                </tr>
                <tr>
                  <td>
                    <bean:message key="colname.airline_membership" />
                    :
                  </td>
                  <td>
                    <bean:write name="OnHandForm" property="companycode_ID" />
                    &nbsp;
                    <bean:write name="OnHandForm" property="membershipnum" />
                    &nbsp;
                    <bean:write name="OnHandForm" property="membershipstatus" />
                  </td>
                </tr>
                <tr>
                  <td>
                    <bean:message key="colname.pnr" />
                    :
                  </td>
                  <td>
                    <bean:write name="OnHandForm" property="pnr" />
                    &nbsp;
                  </td>
                </tr>
                <tr>
                  <td>
                    <bean:message key="colname.color" />
                    :
                  </td>
                  <td>
                    <bean:write name="OnHandForm" property="bagColor" />
                    &nbsp;
                  </td>
                </tr>
                <tr>
                  <td>
                    <bean:message key="colname.bagtype" />
                    :
                  </td>
                  <td>
                    <bean:write name="OnHandForm" property="bagType" />
                    &nbsp;
                  </td>
                </tr>
                <tr>
                  <td>
                    <bean:message key="colname.x_desc" />
                    :
                  </td>
                  <td>
                    <bean:write name="ede1" scope="request" />
                    &nbsp;
                  </td>
                </tr>
                <tr>
                  <td>
                    &nbsp;
                  </td>
                  <td>
                    <bean:write name="ede2" scope="request" />
                    &nbsp;
                  </td>
                </tr>
                <tr>
                  <td>
                    &nbsp;
                  </td>
                  <td>
                    <bean:write name="ede3" scope="request" />
                    &nbsp;
                  </td>
                </tr>
                <tr>
                  <td>
                    <bean:message key="colname.manufacturer" />
                    :
                  </td>
                  <td>
                    <bean:write name="manuf" scope="request" />
                    &nbsp;
                  </td>
                </tr>
              </table>
              <br>
              <logic:present name="passenger" scope="request">
                <a name="passengerinfo"></a>
                <h1 class="green">
                  <bean:message key="header.passenger_info" />
                  <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#On_Hand_Bag.htm#Found_and_unclaimed');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
                </h1>
                <table class="form2" cellspacing="0" cellpadding="0">
                  <logic:iterate id="passenger" name="OnHandForm" property="passengerList" indexId="i" type="com.bagnet.nettracer.tracing.db.OHD_Passenger">
                    <tr>
                      <td>
                        <bean:message key="colname.passenger" />
                      </td>
                      <td>
                        <table class="form2" cellspacing="0" cellpadding="0">
                          <tr>
                            <td>
                              <bean:message key="colname.pass_name" />
                              :
                            </td>
                            <td>
                              <bean:message key="colname.last_name" />
                              :
                              <br>
                              <bean:write name="passenger" property="lastname" />
                              &nbsp;
                            </td>
                            <td>
                              <bean:message key="colname.first_name" />
                              :
                              <br>
                              <bean:write name="passenger" property="firstname" />
                              &nbsp;
                            </td>
                            <td>
                              <bean:message key="colname.mid_initial" />
                              :
                              <br>
                              <bean:write name="passenger" property="middlename" />
                              &nbsp;
                            </td>
                          </tr>
                          <logic:present name="passenger" property="addresses">
                            <logic:iterate indexId="k" name="passenger" id="addresses" property="addresses" type="com.bagnet.nettracer.tracing.db.OHD_Address">
                              <tr>
                                <td>
                                  <bean:message key="colname.street_addr" />
                                  :
                                </td>
                                <td>
                                  <bean:write name="addresses" property="address1" />
                                  &nbsp;
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  <bean:message key="colname.street_addr" />
                                  :
                                </td>
                                <td>
                                  <bean:write name="addresses" property="address2" />
                                  &nbsp;
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  <bean:message key="colname.city" />
                                  :
                                </td>
                                <td>
                                  <bean:write name="addresses" property="city" />
                                  &nbsp;
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  <bean:message key="colname.state" />
                                  :
                                </td>
                                <td>
                                  <html:select name="addresses" property="state_ID" disabled="true" styleClass="dropdown">
                                    <html:option value="">
                                      <bean:message key="select.none" />
                                    </html:option>
                                    <html:options collection="statelist" property="value" labelProperty="label" />
                                  </html:select>
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  <bean:message key="colname.province" />
                                  :
                                </td>
                                <td>
                                  <html:text property='<%= "addresses[" + (i.intValue() * 20 + k.intValue()) + "].province" %>' size="25" maxlength="100" styleClass="textfield" />
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  <bean:message key="colname.zip" />
                                  :
                                </td>
                                <td>
                                  <bean:write name="addresses" property="zip" />
                                  &nbsp;
                                </tr>
                                <tr>
                                  <td>
                                    <bean:message key="colname.country" />
                                    :
                                  </td>
                                  <td>
                                    <html:select name="addresses" property="countrycode_ID" disabled="true" styleClass="dropdown">
                                      <html:option value="">
                                        <bean:message key="select.none" />
                                      </html:option>
                                      <html:options name="OnHandForm" collection="countrylist" property="value" labelProperty="label" />
                                    </html:select>
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <bean:message key="colname.home_ph" />
                                    :
                                  </td>
                                  <td>
                                    <bean:write name="addresses" property="homephone" />
                                    &nbsp;
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <bean:message key="colname.business_ph" />
                                    :
                                  </td>
                                  <td>
                                    <bean:write name="addresses" property="workphone" />
                                    &nbsp;
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <bean:message key="colname.mobile_ph" />
                                    :
                                  </td>
                                  <td>
                                    <bean:write name="addresses" property="mobile" />
                                    &nbsp;
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <bean:message key="colname.pager_ph" />
                                    :
                                  </td>
                                  <td>
                                    <bean:write name="addresses" property="pager" />
                                    &nbsp;
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <bean:message key="colname.alt_ph" />
                                    :
                                  </td>
                                  <td>
                                    <bean:write name="addresses" property="altphone" />
                                    &nbsp;
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <bean:message key="colname.email" />
                                    :
                                  </td>
                                  <td>
                                    <bean:write name="addresses" property="email" />
                                    &nbsp;
                                  </td>
                                </tr>
                                <tr>
                                  <td colspan="4" bgcolor="white">
                                    &nbsp;
                                  </td>
                                </tr>
                              </logic:iterate>
                            </logic:present>
                          </table>
                        </td>
                      </tr>
                    </logic:iterate>
                  </table>
                  <br>
                </logic:present>
                <logic:present name="itinerary" scope="request">
                  <a name="custitin"></a>
                  <h1 class="green">
                    <bean:message key="header.bag_itinerary" />
                    <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#On_Hand_Bag.htm#Found_and_unclaimed');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
                  </h1>
                  <table class="form2" cellspacing="0" cellpadding="0">
                    <logic:iterate id="itinerarylist" name="OnHandForm" property="itinerarylist">
                      <tr>
                        <td align="left">
                          <bean:message key="colname.fromto" />
                          :
                          <bean:write name="itinerarylist" property="legfrom" />
                          &nbsp;
                        </td>
                        <td align="left">
                          <bean:message key="colname.flightnum" />
                          :
                          <bean:write name="itinerarylist" property="flightnum" />
                          &nbsp;
                        </td>
                        <td align="left">
                          <bean:message key="colname.departdate" />
                          :
                          <bean:write name="itinerarylist" property="disdepartdate" />
                          &nbsp;
                        </td>
                        <td align="left">
                          <bean:message key="colname.arrdate" />
                          :
                          <bean:write name="itinerarylist" property="disarrivedate" />
                          &nbsp;
                        </td>
                      </tr>
                      <tr>
                        <td align="left">
                          <bean:message key="colname.schdeptime" />
                          :
                          <bean:write name="itinerarylist" property="disschdeparttime" />
                          &nbsp;
                        </td>
                        <td align="left">
                          <bean:message key="colname.scharrtime" />
                          :
                          <bean:write name="itinerarylist" property="disscharrivetime" />
                          &nbsp;
                        </td>
                        <td align="left">
                          <bean:message key="colname.actdeptime" />
                          :
                          <bean:write name="itinerarylist" property="disactdeparttime" />
                          &nbsp;
                        </td>
                        <td align="left">
                          <bean:message key="colname.actarrtime" />
                          :
                          <bean:write name="itinerarylist" property="disactarrivetime" />
                          &nbsp;
                        </td>
                      </tr>
                    </logic:iterate>
                  </table>
                  <br>
                </logic:present>
                <logic:present name="inventory" scope="request">
                  <a name="centralbag"></a>
                  <h1 class="green">
                    <bean:message key="header.central_baggage_inventory" />
                    <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#On_Hand_Bag.htm#Found_and_unclaimed');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
                  </h1>
                  <table class="form2" cellspacing="0" cellpadding="0">
                    <logic:iterate indexId="i" id="itemlist" name="OnHandForm" property="itemlist">
                      <tr>
                        <td>
                          <bean:message key="colname.category" />
                        </td>
                        <td>
                          <html:select name="itemlist" property="OHD_categorytype_ID" indexed="true" styleClass="dropdown" disabled="true">
                            <html:option value="">
                              <bean:message key="select.please_select" />
                            </html:option>
                            <html:options collection="categorylist" property="OHD_CategoryType_ID" labelProperty="description" />
                          </html:select>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <bean:message key="colname.description" />
                          :
                        </td>
                        <td>
                          <bean:write name="itemlist" property="description" />
                          &nbsp;
                        </td>
                      </tr>
                    </logic:iterate>
                  </table>
                  <br>
                </logic:present>
                <logic:present name="photos" scope="request">
<%
                  if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_OHD_PHOTOS, a)) {
%>
                    <a name="photos"></a>
                    <h1 class="green">
                      <bean:message key="header.photos" />
                      <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#On_Hand_Bag.htm#Found_and_unclaimed');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
                    </h1>
                    <table class="form2" cellspacing="0" cellpadding="0">
                      <tr align="center">
                        <logic:iterate id="photo" name="OnHandForm" property="photoList">
                          <td>
                            <a href='showImage?ID=<%= ((OHD_Photo)photo).getPicpath() %>' target="top"><img src="showImage?ID=<%= ((OHD_Photo)photo).getThumbpath() %>"></a>
                          </td>
                        </logic:iterate>
                      </tr>
                    </table>
<%
                  }
%>
                </logic:present>
                <logic:present name="remarks" scope="request">
                  <a name="remarks"></a>
                  <h1 class="green">
                    <bean:message key="header.remarks" />
                    <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#On_Hand_Bag.htm#Found_and_unclaimed');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
                  </h1>
                  <table class="form2" cellspacing="0" cellpadding="0">
                    <logic:iterate id="remarklist" indexId="i" name="OnHandForm" property="remarklist">
                      <tr>
                        <td valign="top">
                          <a name='addremark<%= i %>'></a>
                          <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="41%">
                                <bean:message key="colname.date" />
                                :
                                <bean:write name="remarklist" property="dispcreatetime" />
                              </td>
                              <bean:define id="agent" name="remarklist" property="agent" type="com.bagnet.nettracer.tracing.db.Agent" />
                              <td width="60%">
                                <bean:message key="colname.station" />
                                :
                                <bean:write name="agent" property="companycode_ID" />
                                &nbsp;
                                <bean:write name="agent" property="station.stationcode" />
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <bean:message key="colname.agent" />
                                :
                                <bean:write name="agent" property="username" />
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                      <tr>
                        <td valign="top">
                          <bean:write name="remarklist" property="remarktext" />
                          &nbsp;
                        </td>
                      </tr>
                    </logic:iterate>
                  </table>
                  <br>
                </logic:present>
                <logic:present name="requestList" scope="request">
                  <a name="requests"></a>
                  <h1 class="green">
                    <bean:message key="header.requests" />
                    <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#On_Hand_Bag.htm#Found_and_unclaimed');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
                  </h1>
                  <table class="form2" cellspacing="0" cellpadding="0">
                    <logic:iterate id="request2" indexId="i" name="requestList" scope="request">
                      <tr>
                        <td valign="top">
                          <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td>
                                <bean:message key="colname.requesting_agent" />
                                :
                                <bean:write name="request2" property="requestee" />
                              </td>
                              <td>
                                <bean:message key="colname.company" />
                                :
                                <bean:write name="request2" property="requestingCompany" />
                              </td>
                              <td>
                                <bean:message key="colname.station" />
                                :
                                <bean:write name="request2" property="requestingStation" />
                              </td>
                            </tr>
                            <tr>
                              <td colspan="2">
                                <bean:message key="colname.date_time" />
                                :
                                <bean:write name="request2" property="timeRequested" />
                              </td>
                              <td>
                                <bean:message key="colname.status" />
                                :
                                <bean:write name="request2" property="requestStatus" />
                              </td>
                            </tr>
                            <tr>
                              <td colspan="3" valign="top">
                                <bean:write name="request2" property="reason" />
                                &nbsp;
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                    </logic:iterate>
                  </table>
                  <br>
                </logic:present>
                <logic:present name="forward" scope="request">
                  <a name="forward_log"></a>
                  <h1 class="green">
                    <bean:message key="header.forward_log" />
                    <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#On_Hand_Bag.htm#Found_and_unclaimed');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
                  </h1>
                  <table class="form2" cellspacing="0" cellpadding="0">
                    <logic:iterate id="forward" indexId="i" name="forward" scope="request">
                      <tr>
                        <td valign="top">
                          <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td>
                                <bean:message key="colname.destination_company" />
                                :
                                <bean:write name="forward" property="destCompany" />
                              </td>
                              <td>
                                <bean:message key="colname.station" />
                                :
                                <bean:write name="forward" property="destStation" />
                              </td>
                            </tr>
                            <tr>
                              <td>
                                <bean:message key="header.expedite" />
                                :
                                <bean:write name="forward" property="expeditenum" />
                              </td>
                              <td>
                                <bean:message key="colname.date_time" />
                                :
                                <bean:write name="forward" property="dispForwardTime" />
                              </td>
                            </tr>
                            <tr>
                              <td colspan="2">
                                <table>
                                  <tr>
                                    <td colspan="6">
                                      <bean:message key="header.bag_itinerary" />
                                    </td>
                                  </tr>
                                  <tr>
                                    <td>
                                      <bean:message key="colname.fromto" />
                                    </td>
                                    <td>
                                      <bean:message key="colname.flightnum" />
                                    </td>
                                    <td>
                                      <bean:message key="colname.departdate" />
                                    </td>
                                    <td>
                                      <bean:message key="colname.arrdate" />
                                    </td>
                                    <td>
                                      <bean:message key="colname.schdeptime" />
                                    </td>
                                    <td>
                                      <bean:message key="colname.scharrtime" />
                                    </td>
                                  </tr>
                                  <logic:present name="forward" property="itinerarylist">
                                    <logic:iterate id="itinerarylist" name="forward" property="itinerarylist">
                                      <tr>
                                        <td>
                                          <html:hidden name="itinerarylist" property="itinerarytype" value="1" indexed="true" />
                                          <html:text styleClass="textfield" name="itinerarylist" property="legfrom" size="3" maxlength="3" indexed="true" />
                                          /
                                          <html:text styleClass="textfield" name="itinerarylist" property="legto" size="3" maxlength="3" indexed="true" />
                                        </td>
                                        <td>
                                          <html:text styleClass="textfield" readonly="true" name="itinerarylist" property="flightnum" size="8" maxlength="7" indexed="true" />
                                        </td>
                                        <td>
                                          <html:text styleClass="textfield" readonly="true" name="itinerarylist" property="disdepartdate" size="10" maxlength="10" indexed="true" />
                                        </td>
                                        <td>
                                          <html:text styleClass="textfield" readonly="true" name="itinerarylist" property="disarrivedate" size="10" maxlength="10" indexed="true" />
                                        </td>
                                        <td>
                                          <html:text styleClass="textfield" readonly="true" name="itinerarylist" property="disschdeparttime" size="8" maxlength="5" indexed="true" />
                                        </td>
                                        <td>
                                          <html:text styleClass="textfield" readonly="true" name="itinerarylist" property="disscharrivetime" size="8" maxlength="5" indexed="true" />
                                        </td>
                                      </tr>
                                    </logic:iterate>
                                  </logic:present>
                                </table>
                              </td>
                            </tr>
                            <tr>
                              <td colspan="2" valign="top">
                                <bean:write name="forward" property="message" />
                                &nbsp;
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                    </logic:iterate>
                  </table>
                  <br>
                </logic:present>
                <logic:present name="matches" scope="request">
                  <a name="matches"></a>
                  <h1 class="green">
                    <bean:message key="header.matches" />
                    <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#On_Hand_Bag.htm#Found_and_unclaimed');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
                  </h1>
                  <table class="form2" cellspacing="0" cellpadding="0">
                    <logic:iterate id="match" indexId="i" name="matches" scope="request">
                      <tr>
                        <td valign="top">
                          <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td>
                                <bean:message key="header.match_category" />
                                :
                                <bean:write name="match" property="reportCategory" />
                              </td>
                              <td>
                                <bean:message key="header.file" />
                                :
                                <bean:write name="match" property="reportNumber" />
                              </td>
                            </tr>
                            <tr>
                              <td>
                                <bean:message key="colname.date_time" />
                                :
                                <bean:write name="match" property="dispdate" />
                              </td>
                              <td>
                                <bean:message key="header.match_percent" />
                                :
                                <bean:write name="match" property="reportPercentage" />
                              </td>
                            </tr>
                            <tr>
                              <td colspan="2">
                                <bean:message key="header.match_elements" />
                              </tr>
                              <tr>
                                <td colspan="2">
                                  <table>
                                    <tr>
                                      <td>
                                        <bean:message key="colname.item" />
                                      </td>
                                      <td>
                                        <bean:message key="colname.percentage" />
                                      </td>
                                      <td>
                                        <bean:message key="colname.mbr_info" />
                                      </td>
                                      <td>
                                        <bean:message key="colname.ohd_info" />
                                      </td>
                                    </tr>
                                    <logic:present name="match" property="displayDetails">
                                      <logic:iterate id="details" name="match" property="displayDetails" type="com.bagnet.nettracer.tracing.db.Match_Detail">
                                        <tr>
                                          <td>
                                            <bean:message key="<%= details.getItem() %>" />
                                          </td>
                                          <td>
                                            <bean:write name="details" property="disppercent" />
                                          </td>
                                          <td>
                                            <bean:write name="details" property="mbr_info" />
                                          </td>
                                          <td>
                                            <bean:write name="details" property="ohd_info" />
                                          </td>
                                        </tr>
                                      </logic:iterate>
                                    </logic:present>
                                  </table>
                                </td>
                              </tr>
                            </table>
                          </td>
                        </tr>
                      </logic:iterate>
                    </table>
                    <br>
                  </logic:present>
                  <logic:present name="messages" scope="request">
                    <a name="inbox"></a>
                    <h1 class="green">
                      <bean:message key="header.messages" />
                      <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#On_Hand_Bag.htm#Found_and_unclaimed');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
                    </h1>
                    <table class="form2" cellspacing="0" cellpadding="0">
                      <logic:iterate id="message" indexId="i" name="messages" scope="request">
                        <tr>
                          <td valign="top">
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td>
                                  <bean:message key="header.tsk_desc" />
                                  :
                                  <bean:write name="message" property="subject" />
                                </td>
                                <td>
                                  <bean:message key="header.tsk_created_by" />
                                  :
                                  <bean:write name="message" property="createdBy" />
                                </td>
                                <td>
                                  <bean:message key="colname.station" />
                                  :
                                  <bean:write name="message" property="stationString" />
                                </td>
                              </tr>
                              <tr>
                                <td colspan="3">
                                  <bean:message key="header.sent" />
                                  :
                                  <bean:write name="message" property="disp_send_date" />
                                </td>
                              </tr>
                              <tr>
                                <td colspan="3">
                                  <bean:message key="header.to" />
                                  :
                                  <bean:write name="message" property="messageTo" />
                                </td>
                              </tr>
                              <tr>
                                <td colspan="3" valign="top">
                                  <bean:write name="message" property="message" />
                                  &nbsp;
                                </td>
                              </tr>
                            </table>
                          </td>
                        </tr>
                      </logic:iterate>
                    </table>
                    <br>
                  </logic:present>
                  <logic:present name="tasks" scope="request">
                    <a name="tasks"></a>
                    <h1 class="green">
                      <bean:message key="header.tasks" />
                      <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#On_Hand_Bag.htm#Found_and_unclaimed');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
                    </h1>
                    <table class="form2" cellspacing="0" cellpadding="0">
                      <logic:iterate id="task" indexId="i" name="tasks" scope="request">
                        <tr>
                          <td valign="top">
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td>
                                  <bean:message key="header.tsk_desc" />
                                  :
                                  <bean:write name="task" property="description" />
                                </td>
                                <td>
                                  <bean:message key="header.tsk_created_by" />
                                  :
                                  <bean:write name="task" property="createdBy" />
                                </td>
                                <td>
                                  <bean:message key="colname.station" />
                                  :
                                  <bean:write name="task" property="stationString" />
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  <bean:message key="header.tsk_due_date" />
                                  :
                                  <bean:write name="task" property="dispduedate" />
                                </td>
                                <td>
                                  <bean:message key="header.tsk_due_time" />
                                  :
                                  <bean:write name="task" property="dispduetime" />
                                </td>
                                <td>
                                  <bean:message key="header.tsk_status" />
                                  :
                                  <bean:write name="task" property="statusString" />
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  <bean:message key="header.tsk_reminder_date" />
                                  :
                                  <bean:write name="task" property="dispreminderdate" />
                                </td>
                                <td>
                                  <bean:message key="header.tsk_reminder_time" />
                                  :
                                  <bean:write name="task" property="dispremindertime" />
                                </td>
                                <td>
                                  <bean:message key="header.tsk_priority" />
                                  :
                                  <bean:write name="task" property="priorityString" />
                                </td>
                              </tr>
                              <tr>
                                <td colspan="3" valign="top">
                                  <bean:message key="header.tsk_remarks" />
                                  :
                                  <bean:write name="task" property="remarks" />
                                  &nbsp;
                                </td>
                              </tr>
                            </table>
                          </td>
                        </tr>
                      </logic:iterate>
                    </table>
                    <br>
                  </logic:present>
                </logic:present>
              </html:form>
