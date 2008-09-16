<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="org.apache.struts.action.DynaActionForm" %>
<%@ page import="java.util.Iterator,
                 java.util.TreeMap,
                 com.bagnet.nettracer.tracing.dto.ComponentDTO" %>
<jsp:useBean id="systemMap" scope="request" type="java.util.TreeMap" />
<script type="text/javascript" language="Javascript1.1">
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
function CBG_setControlBox(name) { this.controlBox=name; }

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
      
    for (i=0;i<this.checkboxNames.length;i++) { 
    	this.checkboxNames[i].checked=checked;
    	// added the following lines by matt
    	if (this.checkboxNames[i].disabled) {
    		if (this.checkboxNames[i].checked) {
    			this.formRef[this.checkboxNames[i].name].value = 1;
    		}
    	}
    	
		}
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



<%
                  if (systemMap != null && systemMap.size() > 0) {
%>
<%
                  for (Iterator i = systemMap.keySet().iterator(); i.hasNext(); ) {
                    String   key         = (String)i.next();
                    TreeMap  nextMenu    = (TreeMap)systemMap.get(key);
                    String[] result      = key.split("#");
                    String   name        = result[0];
                    String   parent_ID   = result[1];
                    String   checked     = result[2];
                    String   parent_name = "ABC" + parent_ID;
                    String   controlbox  = parent_name + ".setControlBox(\"" + parent_ID + "\");";
                    String   behavior    = parent_name + ".setMasterBehavior(\"some\");";
                    String   groupbox    = parent_name + ".addToGroup(\"" + parent_ID + "\");";
%>
      var <%= parent_name %> = new CheckBoxGroup();
      <%= controlbox %>
      <%= behavior %>
<%
                  if (nextMenu != null && nextMenu.size() > 0) {
%>
<%
                  for (Iterator j = nextMenu.keySet().iterator(); j.hasNext(); ) {
                    String       key2 = (String)j.next();
                    ComponentDTO cDTO = (ComponentDTO)nextMenu.get(key2);
%>
          <%= cDTO.getHtmlGroup(parent_name) %>
      <%
                }
%>
      <%
                }
%>
  <%
                }
%>
<%
                }
%>

  // -->
</script>
<html:form action="componentAdmin.do" method="post">
  <tr>
    <td colspan="3" id="pageheadercell">
      <div id="pageheaderleft">
        <h1>
          <bean:message key="header.permissionAdmin" />
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
  <!-- END PAGE HEADER/SEARCH -->
  <tr>
    <!-- MIDDLE COLUMN -->
    <td id="middlecolumn">
      <!-- MAIN BODY -->
      <div id="maincontent">
        <h1 class="green">
          <bean:message key="permissions" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#administration/work_with_groups.htm#maintain permissions');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
        </h1>
        <table class="form2" cellspacing="0" cellpadding="0">
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <tr>
            <input type="hidden" name="groupId" value='<%= request.getAttribute("groupId") %>'>
            <td colspan="3">
              <span class="bold"><bean:message key="companyCodeText" />
                :</span>
              <%= request.getAttribute("companyCode") %>|
              <span class="bold"><bean:message key="groupDescriptionText" />
                :</span>
              <%= request.getAttribute("groupName") %>
            </td>
          </tr>
          <tr>
            <td width="7%">
              <b><bean:message key="active" /></b>
            </td>
            <td align="center">
              <b><bean:message key="componentName" /></b>
            </td>
            <td align="center">
              <b><bean:message key="componentDesc" /></b>
            </td>
          </tr>
<%
          if (systemMap != null && systemMap.size() > 0) {
%>
<%
            for (Iterator i = systemMap.keySet().iterator(); i.hasNext(); ) {
              String   key         = (String)i.next();
              TreeMap  nextMenu    = (TreeMap)systemMap.get(key);
              String[] result      = key.split("#");
              String   name        = result[0];
              String   parent_ID   = result[1];
              String   checked     = result[2];
              String   parent_name = "ABC" + parent_ID;
%>
              <TR>
                <TD width="7%">
                  <INPUT TYPE=CHECKBOX NAME="<%= parent_ID %>" <% if (checked.equals("1")) { %> CHECKED <% } %> onClick="<%= parent_name %>.check(this);">
                </td>
                <td colspan="2">
<%
                  String name2 = name.replaceAll(" ", "_");
%>
                  <bean:message key="<%= name2 %>" arg0="" arg1="" />
                </TD>
              </TR>
              <TR>
                <td width="7%">
                  &nbsp;
                </td>
                <td colspan="2">
                  <table>
<%
                    if (nextMenu != null && nextMenu.size() > 0) {
%>
<%
                      for (Iterator j = nextMenu.keySet().iterator(); j.hasNext(); ) {
                        String       key2 = (String)j.next();
                        ComponentDTO cDTO = (ComponentDTO)nextMenu.get(key2);
%>
                        <tr>
                          <td width="5%">
                            <INPUT TYPE=CHECKBOX NAME="<%= cDTO.getComponent_ID() %>" <% if (cDTO.getComponent_Name().toLowerCase().indexOf("claim") != -1) { %> disabled <% } %> <% if (cDTO.isChecked()) { %> CHECKED <% } %> onClick="<%= "ABC" + parent_ID %>.check(this);">
<%
                            if (cDTO.getComponent_Name().toLowerCase().indexOf("claim") != -1) {
%>
                              <INPUT TYPE=hidden NAME="<%= cDTO.getComponent_ID() %>" value="">
<%
                            }
%>
                          </td>
                          <td width="45%">
                            <bean:message key="<%= cDTO.getComponent_Name().replaceAll(" ", "_") %>" />
                          </td>
                          <td width="50%">
<%
                            if (cDTO.getComponent_Description() != null) {
%>
                              <%= cDTO.getComponent_Description() %><%
                            } else {
%>
                              &nbsp;
<%
                            }
%>
                          </td>
                        </tr>
<%
                      }
%>
<%
                    }
%>
                  </table>
<%
                }
%>
<%
              }
%>
            </td>
          </tr>
          <tr>
            <td colspan="3">
              &nbsp;
            </td>
          </tr>
          <tr>
            <td colspan="3" align="center">
              <INPUT Id="button" type="button" value="Back" onClick="history.back()">
              &nbsp;
              <html:submit styleId="button" property="save">
                <bean:message key="button.save" />
              </html:submit>
            </td>
          </tr>
        </table>
      </html:form>
