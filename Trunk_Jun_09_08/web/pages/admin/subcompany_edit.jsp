<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="org.apache.struts.action.DynaActionForm" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<script language="javascript">
  
  function goprev() {
    o = document.subCompanyForm;
    o.prevpage.value = "1";
    o.pagination.value="1";
    o.edit.value = "1";
    o.submit();
  }

  function gonext() {
    o = document.subCompanyForm;
    o.nextpage.value="1";
    o.pagination.value="1";
    o.edit.value = "1";
    o.submit();
  }

  function gopage(i) {
    o = document.subCompanyForm;
    o.currpage.value = i;
    o.pagination.value="1";
    o.edit.value = "1";
    o.submit();

  }
  function updatePagination() {
	    o.edit.value = "1";
	    return true;
	}

</script>

<%
  Agent a = (Agent)session.getAttribute("user");
%>
  <html:form action="subCompAdmin.do" method="post">
    <html:hidden name="subCompanyForm" property="id" />
    <input type="hidden" name="edit" value="">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.editSubcompany" />
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
      
      <td id="middlecolumn">
        
        <div id="maincontent">
          <h1 class="green">
            <bean:message key="subComp" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <table class="form2" cellspacing="0" cellpadding="0">
            <font color=red>
              <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
            </font>
            <tr>
              <td>
                <bean:message key="header.subCompanyCode" />
                <font color=red>
                  *:
                </td>
                <logic:empty name="subCompanyForm" property="subcompanyCode">
                <td>
                  <html:text styleClass="textfield" property="subcompanyCode" size="12" maxlength="3" />
                </td>
                </logic:empty >
                
                <logic:notEmpty name="subCompanyForm" property="subcompanyCode">
                <td>
                  <html:text styleClass="textfield" property="subcompanyCode" disabled="true" size="12" maxlength="3" />
                </td>
               	 <html:hidden name="subCompanyForm"  property="subcompanyCode"/>
                </logic:notEmpty>
              </tr>
              <tr>
                <td>
                  <bean:message key="header.subCompanyName" />
                  <font color=red>
                    *
                  </font>
                  :
                </td>
                <logic:empty name="subCompanyForm" property="subcompanyCode">
                <td>
                  <html:text styleClass="textfield" property="name" size="20" maxlength="50" />
                </td>
                </logic:empty>
                <logic:notEmpty name="subCompanyForm" property="subcompanyCode" >
                <td>
                  <html:text styleClass="textfield" property="name" disabled="true" size="20" maxlength="50" />
                </td>
               	 <html:hidden property="name"/>
                </logic:notEmpty>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.emailsubject" />
                  :
                </td>
                <td>
                  <html:text styleClass="textfield" property="email_Subject" size="20" maxlength="200" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.emailnotice1" />
                  :
                </td>
                <td>
                  <html:text styleClass="textfield" property="email_Notice_1" size="20" maxlength="200" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.emailnotice2" />
                  :
                </td>
                <td>
                  <html:text styleClass="textfield" property="email_Notice_2" size="20" maxlength="200" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.emailnotice3" />
                  :
                </td>
                <td>
                  <html:text styleClass="textfield" property="email_Notice_3" size="20" maxlength="200" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.emailnotice4" />
                  :
                </td>
                <td>
                  <html:text styleClass="textfield" property="email_Notice_4" size="20" maxlength="200" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.emailnotice5" />
                  :
                </td>
                <td>
                  <html:text styleClass="textfield" property="email_Notice_5" size="20" maxlength="200" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.autocloselow" />
                  :
                </td>
                <td>
                  <html:text styleClass="textfield" property="auto_Close_Low" size="20" maxlength="3" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.autoclosehigh" />
                  :
                </td>
                <td>
                  <html:text styleClass="textfield" property="auto_Close_High" size="20" maxlength="3" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.salvagelow" />
                  :
                </td>
                <td>
                  <html:text styleClass="textfield" property="salvage_Low" size="20" maxlength="3" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.salvagehigh" />
                  :
                </td>
                <td>
                  <html:text styleClass="textfield" property="salvage_High" size="20" maxlength="3" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.shippingsurcharge" />
                  :
                </td>
                <td>
                  <html:text styleClass="textfield" property="shippingSurcharge" size="20" maxlength="3" />
                </td>
              </tr>
              <tr>
                <td colspan="2" align="center">
                  &nbsp;
                </td>
              </tr>
              <tr>
                <td colspan="2" align="center">
                  <INPUT Id="button" type="button" value="Back" onClick="history.back()">
                  &nbsp;
                  <html:submit styleId="button" property="save">
                    <bean:message key="button.save" />
                  </html:submit>
                </td>
              </tr>
            </table>
            <br>
            <logic:present name="subCompanyForm" property="id">
              <h1 class="green">
                <bean:message key="subcompany_stations" />
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
              </h1>
              <table class="form2" cellspacing="0" cellpadding="0">
                <logic:present name="stationList" scope="request">
                  
		          <tr>
		            <td>
		              <b><bean:message key="header.stationCode" /></b>
		            </td>
		            <td>
		              <b><bean:message key="header.stationDesc" /></b>
		            </td>
		            <td>
		              <b><bean:message key="header.active" /></b>
		            </td>
		          </tr>
		            <logic:iterate id="station" name="stationList">
		              <tr>
		                <td>
		                  <A HREF="stationAdmin.do?edit=1&stationId=<bean:write name="station" property="station_ID"/>"><bean:write name="station" property="stationcode" /></A>
		                </td>
		                <td>
		                  <bean:write name="station" property="stationdesc" />
		                </td>
		                <td>
		                  <logic:equal name="station" property="active" value="true">
		                    <bean:message key="select.yes" />
		                  </logic:equal>
		                  <logic:notEqual name="station" property="active" value="true">
		                    <bean:message key="select.no" />
		                  </logic:notEqual>
		                </td>
		              </tr>
		            </logic:iterate>
		            
		            <tr>
		              <td colspan="4">
		                <jsp:include page="/pages/includes/pagination_incl.jsp" />
		              </td>
		            </tr>
	              
                </logic:present>
                <tr>
                  <td colspan="8" align="center">
                    <html:submit styleId="button" property="addNewStation">
                      <bean:message key="button.add.subcompany.station" />
                    </html:submit>
                  </td>
                </tr>
              </table>
            </logic:present>
          </html:form>
