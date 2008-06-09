<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%
  Agent a = (Agent)session.getAttribute("user");
%>
  <script language="javascript">
    <!--

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
  // Public methods
  this.setControlBox=CBG_setControlBox;
  this.setMaxAllowed=CBG_setMaxAllowed;
  this.setMasterBehavior=CBG_setMasterBehavior; // all, some
  this.addToGroup=CBG_addToGroup;
  // Private methods
  this.expandWildcards=CBG_expandWildcards;
  this.addWildcardCheckboxes=CBG_addWildcardCheckboxes;
  this.addArrayCheckboxes=CBG_addArrayCheckboxes;
  this.addSingleCheckbox=CBG_addSingleCheckbox;
  this.check=CBG_check;
  }

	// Set the master control checkbox name
	function CBG_setControlBox(name) { 
		this.controlBox=name; 
	}

	// Set the maximum number of checked boxes in the set, and optionally
	// the message to popup when the max is reached.
	function CBG_setMaxAllowed(num,msg) {
	  this.maxAllowed=num;
	  if (msg!=null&&msg!="") { this.maxAllowedMessage=msg; }
	  }

// Set the behavior for the checkbox group master checkbox
//  All: all boxes must be checked for the master to be checked
//  Some: one or more of the boxes can be checked for the master to be checked
function CBG_setMasterBehavior(b) { this.masterBehavior = b.toLowerCase(); }

// Add checkbox wildcards to the checkboxes array
function CBG_addToGroup() {
  if (arguments.length>0) {
    for (var i=0;i<arguments.length;i++) {
      this.checkboxWildcardNames[this.checkboxWildcardNames.length]=arguments[i];
      }
    }
  }

// Expand the wildcard checkbox names given in the addToGroup method
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


// Add checkboxes to the group which match a pattern
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

// Add checkboxes to the group which all have the same name
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

// Runs whenever a checkbox in the group is clicked
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
    
    /* if (!checked) {
        obj.checked = (this.totalSelected>0)?true:false;
        obj.blur();
        }
    */
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
  	o = document.incidentForm;

		for (var j=0;j<o.length;j++) {
			currentElement = o.elements[j];
    	currentElementName=currentElement.name;
    	if (currentElementName.indexOf(group) != -1)
			{
				if (currentElement.checked){
					o.viewhistoryreport.value = currentElement.value;
					break;
				}
			}
		}
  	o.submit();
	}
	
	var sections = new CheckBoxGroup();
  sections.setControlBox("all");
  sections.setMasterBehavior("all");
  sections.addToGroup("passenger");
  sections.addToGroup("passenger_itinerary");
  sections.addToGroup("baggage_itinerary");
  sections.addToGroup("baggage_check");
  sections.addToGroup("baggage_info");
  sections.addToGroup("missing_articles");
 	sections.addToGroup("remarks");
  sections.addToGroup("messages");
 	sections.addToGroup("tasks");	
// -->
  </script>
  <html:form action="missing.do" method="post">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.historical_view" />
            (
            <bean:write name="incidentForm" property="incident_ID" />
            )
          </h1>
        </div>
        <div id="pageheaderright">
          <table id="pageheaderright">
            <tr>
              <jsp:include page="../includes/mail_incl.jsp" />
              <td>
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><bean:message key="Help" /></a>
              </td>
            </tr>
          </table>
        </div>
      </td>
    </tr>
    <!-- END PAGE HEADER/SEARCH -->
    <!-- ICONS MENU -->
    <tr>
      <td colspan="3" id="navmenucell">
        <div class="menu">
          <dl>
            <dd>
              <a href='searchIncident.do?incident=<bean:write name="incidentForm" property="incident_ID"/>'><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="menu.ma_report" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <dd>
              <a href="#"><span class="aab">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bbb"><bean:message key="menu.history" /></span>
                <span class="ccb">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
          </dl>
        </div>
      </td>
    </tr>
    <tr>
      <!-- MIDDLE COLUMN -->
      <td id="middlecolumn">
        <!-- MAIN BODY -->
        <div id="maincontent">
          <a name="claimamount"></a>
          <h1 class="green">
            <bean:message key="header.report_options" />
            <a href="#"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <br>
          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td width="100%">
                <strong>
                  <bean:message key="header.report_sections" />
                </strong>
              </td>
            </tr>
            <tr>
              <td width="100%">
                <INPUT TYPE=CHECKBOX NAME="all" <% if (request.getAttribute("all") == null || ((String)request.getAttribute("all")).equals("1")) { %> CHECKED <% } %> onClick="sections.check(this);">
                <bean:message key="select.all" />
              </td>
            </tr>
            <tr>
              <td width="100%" nowrap>
                <INPUT TYPE=CHECKBOX NAME="passenger" <% if (request.getAttribute("passenger") != null || (request.getAttribute("all") == null || (request.getAttribute("all") != null && ((String)request.getAttribute("all")).equals ("1")))) { %> CHECKED <% } %> onClick="sections.check(this);">
                <bean:message key="header.passenger_info" />
                &nbsp;&nbsp;
                <INPUT TYPE=CHECKBOX NAME="passenger_itinerary" <% if (request.getAttribute("passenger_itinerary") != null || (request.getAttribute("all") == null || (request.getAttribute("all") != null && ((String)request.getAttribute("all")).equals ("1")))) { %> CHECKED <% } %> onClick="sections.check(this);">
                <bean:message key="header.itinerary" />
                &nbsp;&nbsp;
                <INPUT TYPE=CHECKBOX NAME="baggage_itinerary" <% if (request.getAttribute("baggage_itinerary") != null || (request.getAttribute("all") == null || (request.getAttribute("all") != null && ((String)request.getAttribute("all")).equals ("1")))) { %> CHECKED <% } %> onClick="sections.check(this);">
                <bean:message key="header.bag_itinerary" />
                &nbsp;&nbsp;
                <INPUT TYPE=CHECKBOX NAME="baggage_check" <% if (request.getAttribute("baggage_check") != null || (request.getAttribute("all") == null || (request.getAttribute("all") != null && ((String)request.getAttribute("all")).equals ("1")))) { %> CHECKED <% } %> onClick="sections.check(this);">
                <bean:message key="header.checked_bag_info" />
                &nbsp;&nbsp;
                <INPUT TYPE=CHECKBOX NAME="baggage_info" <% if (request.getAttribute("baggage_info") != null || (request.getAttribute("all") == null || (request.getAttribute("all") != null && ((String)request.getAttribute("all")).equals ("1")))) { %> CHECKED <% } %> onClick="sections.check(this);">
                <bean:message key="header.bag_info" />
                &nbsp;&nbsp;
                <br>
                <INPUT TYPE=CHECKBOX NAME="missing_articles" <% if (request.getAttribute("missing_articles") != null || (request.getAttribute("all") == null || (request.getAttribute("all") != null && ((String)request.getAttribute("all")).equals ("1")))) { %> CHECKED <% } %> onClick="sections.check(this);">
                <bean:message key="header.ma" />
                &nbsp;&nbsp;
                
                <INPUT TYPE=CHECKBOX NAME="remarks" <% if (request.getAttribute("remarks") != null || (request.getAttribute("all") == null || (request.getAttribute("all") != null && ((String)request.getAttribute("all")).equals ("1")))) { %> CHECKED <% } %> onClick="sections.check(this);">
                <bean:message key="header.remarks" />
                &nbsp;&nbsp;
                
                <INPUT TYPE=CHECKBOX NAME="messages" <% if (request.getAttribute("messages") != null || (request.getAttribute("all") == null || (request.getAttribute("all") != null && ((String)request.getAttribute("all")).equals ("1")))) { %> CHECKED <% } %> onClick="sections.check(this);">
                <bean:message key="header.messages" />
                &nbsp;&nbsp;
                <INPUT TYPE=CHECKBOX NAME="tasks" <% if (request.getAttribute("tasks") != null || (request.getAttribute("all") == null || (request.getAttribute("all") != null && ((String)request.getAttribute("all")).equals ("1")))) { %> CHECKED <% } %> onClick="sections.check(this);">
                <bean:message key="header.tasks" />
              </td>
            </tr>
            <input type="hidden" name="incident_ID" value="<bean:write name="incidentForm" property="incident_ID"/>">
            <input type="hidden" name="viewhistoryreport" value="">
            <tr>
              <td width="100%">
                <strong>
                  <bean:message key="colname.report_output_type" />
                </strong>
              </td>
            </tr>
            <tr>
              <td>
                <input type="radio" name="outputtype" <% if (request.getAttribute("outputtype").equals("0")) { %> checked <% } %> value="0">
                <bean:message key="radio.pdf" />
                <input type="radio" name="outputtype" <% if (request.getAttribute("outputtype").equals("1")) { %> checked <% } %> value="1">
                <bean:message key="radio.html" />
                <input type="radio" name="outputtype" <% if (request.getAttribute("outputtype").equals("2")) { %> checked <% } %> value="2">
                <bean:message key="radio.xls" />
                <input type="radio" name="outputtype" <% if (request.getAttribute("outputtype").equals("3")) { %> checked <% } %> value="3">
                <bean:message key="radio.csv" />
                <input type="radio" name="outputtype" <% if (request.getAttribute("outputtype").equals("4")) { %> checked <% } %> value="4">
                <bean:message key="radio.xml" />
              </td>
            </tr>
            <tr>
              <td colspan="2" align="center" valign="top" bgcolor=white>
                <br>
                <INPUT type="button" value="Back" id="button" onClick="history.back()">
                &nbsp;
                <html:submit property="create" onclick="generateHistoricalView('outputtype')" styleId="button">
                  <bean:message key="button.createreport" />
                </html:submit>
              </td>
            </tr>
            <logic:present name="reportfile" scope="request">
            <!-- result -->
            <tr>
              <td colspan=2 align=center bgcolor=white>
                <br>
                <br>
                <a href="#" onclick="openWindow('reporting?outputtype=<%= request.getAttribute("outputtype") %>&reportfile=<bean:write name="reportfile" scope="request"/>','report',800,600);return false;"><b><bean:message key="link.view_report" /></a>
              </td>
            </tr>
          </logic:present>
        </table>
        <logic:present name="reportfile" scope="request">
          <script language=javascript>
            <!--
		
		openWindow('reporting?outputtype=<%= request.getAttribute("outputtype") %>&reportfile=<bean:write name="reportfile" scope="request" />','report',800,600);
		
		//-->
          </script>
        </logic:present>
      </html:form>
