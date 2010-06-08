<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested"%>

<script language="JavaScript">
    function spellCheck()
    {
        
        var elements = new Array(0);

        
        elements[elements.length] = document.PaxCommunicationForm.agentNewComment;

        
        startSpellCheck( "deployment/spellcheck/", elements );
    }

</script>

<tr>
  <td colspan="3" id="pageheadercell">
  <div id="pageheaderleft">
  <h1><bean:message key="header.pax.communication" /> ( <bean:write
    name="PaxCommunicationForm" property="incident_id" scope="request" /> )</h1>
  </div>
  <div id="pageheaderright">
  <table id="pageheaderright">
    <tr>
      <td><a href="#"
        onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><bean:message
        key="Help" /></a></td>
    </tr>
  </table>
  </div>
  </td>
</tr>

<tr>
  <td colspan="3" id="navmenucell">
  <div class="menu">
  <dl>
    <dd><a href="searchIncident.do?incident=<bean:write name="PaxCommunicationForm" property="incident_id" />"> <span class="aa">&nbsp; <br />
    &nbsp;</span> <span class="bb"><bean:message
      key="menu.incident_info" /></span> <span class="cc">&nbsp; <br />
    &nbsp;</span></a></dd>
    <dd><a href="paxCommunication.do?incident_id=<bean:write name="PaxCommunicationForm" property="incident_id" />"> <span class="aab">&nbsp; <br />
    &nbsp;</span> <span class="bbb"><bean:message key="menu.pax.communication" /></span> <span
      class="ccb">&nbsp; <br />
    &nbsp;</span></a></dd>
  </dl>
  </div>
  </td>
</tr>
<tr>
  <td id="middlecolumn"><html:form action="paxCommunication.do" method="post" >
    <html:hidden property="incident_id"/>
    <html:hidden property="id"/>

    <table class="form2" cellspacing="0" cellpadding="0">
      <tr>
        <td align="center">
          <logic:equal name="PaxCommunicationForm" property="ifPaxEmailNotOnFile" value="YES">
			<h1><font color="red"><bean:message key="passenger.email.not.on.file" /></font></h1><BR />
          </logic:equal>
          <logic:equal name="PaxCommunicationForm" property="addNewAgentCommentSuccess" value="YES">
			<h1>New Message Added!</h1><BR />
          </logic:equal>
          <logic:equal name="PaxCommunicationForm" property="acknowledgeNewPaxCommentSuccess" value="YES">
			<h1>New Pax Comments Acknowledged!</h1><BR />
          </logic:equal>
         </td>
      </tr>
      <tr>
        <td align="center"><html:textarea property="text"
          name="PaxCommunicationForm" cols="80" rows="15" style="background-color: #EDEDED" readonly="true"></html:textarea> <br />
        </td>
      </tr>
     <logic:notEqual name="PaxCommunicationForm" property="readOnly" value="true">
     <!-- 
      <logic:equal name="PaxCommunicationForm" property="isThereNewPaxComment" value="YES">
      <tr>
        <td align="left">
        	<html:radio property="agentActionType" value="Acknowledge" />
        	Acknowledge
        </td>
      </tr>
      <tr>
        <td align="left">
        	<html:radio property="agentActionType" value="Respond" />
        	Respond
        </td>
      </tr>
      </logic:equal> 
      -->
      <tr><td><center><bean:message key="please.type.new.message.here" /></center></td></tr>
            <tr><td align="right"><button id="button" type="button" onclick="openspellchecker(); return false;">Spell Check</button></td></tr>
      <tr>
        <td align="center"><html:textarea property="agentNewComment" 
          name="PaxCommunicationForm" cols="80" rows="7" styleId="agentNewComment"></html:textarea><br />
          <logic:notEqual name="PaxCommunicationForm" property="isThereNewPaxComment" value="YES">
            <html:submit property="save" styleId="button">
              <bean:message key="button.send.message" />
            </html:submit>
		  </logic:notEqual>
          <logic:equal name="PaxCommunicationForm" property="isThereNewPaxComment" value="YES">
            <html:submit property="save" styleId="button">
              <bean:message key="button.acknowledge.or.respond" />
            </html:submit>
		  </logic:equal>
        </td>
      </tr>
      
<div id="modalalertdiv" style="display:none;">

<script type="text/javascript">

function openspellchecker(){
		jQuery.ui.dialog.defaults.bgiframe = true;
       		jQuery("#dialog").dialog({bgiframe : true,
 				autoOpen: false, modal: true, draggable: true, resizable: false, 
 				width: 370, height: 340, title: 'NetTracer - Spell Check', position: ['center',100] 
		});
		jQuery('#dialog-inner-content').html('<iframe src ="deployment/spellcheck/spellcheck-entry.jsp?op=1&element_0=forms[0].elements[\'agentNewComment\']" width="100%" height="300"><p>Your browser does not support iframes.</p></iframe>');	
		jQuery("#dialog").dialog("open");	

} 

function closespellchecker() {
	jQuery("#dialog").dialog("close");
}

</script>
     </logic:notEqual>
    </table>
  </html:form>