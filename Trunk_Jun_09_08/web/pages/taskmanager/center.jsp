<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="org.apache.struts.action.DynaActionForm" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.bagnet.nettracer.tracing.db.taskmanager.GeneralTask" %>
<%@ page import="java.util.Iterator" %>
<%@ taglib prefix="nt" uri="http://nettracerTags"%> 
<%
	Agent a = (Agent) session.getAttribute("user");
	boolean hasLoadFoundPermission = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_LFC_LOAD_FOUND_FROM_TASK_MANAGER, a);
%>
<jsp:include page="/pages/includes/taskmanager_header.jsp" />
<tr>
  
  <td id="middlecolumn">
    
    <div id="maincontent">
      <html:form action="/logon">
        <input type="hidden" name="taskmanager" value="1">
        	<logic:messagesPresent message="true">
          	  <center>
          	  	<font color="red" >
          			<html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages>
          	  	</font>
          	  </center></logic:messagesPresent>
          	<div class="headerleft">
          		<h1 class="green">
	            	<bean:message key="header.taskhome" />
    	        	<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
    	        </h1>
        	</div>
        	
        	<div class="headerright">
				<logic:present name="cbroStationID" scope="session">
		            <bean:message key="Station" /> : 
		            <select name="cbroStation" onchange="submit()" class="textfield">
		              <logic:iterate id="station" name="stationlist" scope="session" type="com.bagnet.nettracer.tracing.db.Station">
		                <option value="<bean:write name="station" property="station_ID"/>" <% if (session.getAttribute("cbroStationID").equals("" + station.getStation_ID())) { %> selected <% } %>>
		                <bean:write name="station" property="stationcode" />
		                </option>
		              </logic:iterate>
		            </select>
		        </logic:present>
        	</div>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		
				<%@ page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO" %>
				<%@ page import="com.bagnet.nettracer.tracing.db.Agent"%>
				<%@ page import="org.hibernate.Session"%>
				<%@ page import="com.bagnet.nettracer.hibernate.HibernateWrapper"%>
				<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>
				<%@ page import="org.hibernate.SQLQuery"%>
				<%@ page import="org.hibernate.Hibernate"%>
				<%@ page import="java.util.List"%>
				<%
				boolean ntUser = PropertyBMO.isTrue("nt.user");
				boolean ntfsUser = PropertyBMO.isTrue("ntfs.user");
				boolean lfUser = PropertyBMO.isTrue("lf.user");
				
				ResourceBundle bundle = ResourceBundle.getBundle(
						"com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(a.getCurrentlocale()));
				
				boolean p1 = UserPermissions.hasPermission("System Return Rates", a);
				boolean p2 = UserPermissions.hasPermission("Station Return Rates", a);


				if (p1 || p2) {
					Session sess = null;
					try {
						int station_id = a.getStation().getStation_ID();
						sess = HibernateWrapper.getDirtySession().openSession();
						SQLQuery query = sess.createSQLQuery("select textArea1, textArea2 from z_task_manager_notice where station_id = " + station_id);
						query.addScalar("textArea1", Hibernate.STRING);	
						query.addScalar("textArea2", Hibernate.STRING);
						List queryResults = query.list();
						if (queryResults != null && queryResults.size() > 0) {
							Object[] array = (Object[]) queryResults.get(0);
							
							if (p1 && array.length >= 2 && array[1] != null) {
								String system = ((String)array[1]).replaceAll("000000000000","");
								%><%=system %><%

							}
							if (p2 && array.length >= 1 && array[0] != null) {
								String station = ((String)array[0]).replaceAll("000000000000","");
								station = station.replaceAll("n/a%","n/a");
								%><%=station %><%

							}
						}
					} catch (Exception e) {
						System.out.println("Error displaying return rate statistics: " + e);
					} finally {
						sess.close();
					}
				}

				%>
		
		
	
		<center>
		<h2>
		<%
			List list = (List)request.getAttribute("taskManagerAlerts");
			if(list != null){
				Iterator i = list.iterator();
				while(i.hasNext()){
					GeneralTask task = (GeneralTask)i.next();
					out.println(task.getAlert());
				}
			
			}
		%>
		</h2>
		</center>
		
		<% if (hasLoadFoundPermission) { %> 
			<script>
			
				function loadFoundItemPage() {
					var barcode = document.getElementById("barcode");
					if (!barcode || barcode.value.length == 0 
								 || isNaN(barcode.value) 
								 || barcode.value.indexOf('-') != -1 
								 || barcode.value.indexOf('.') != -1) {
						alert('<%=bundle.getString("barcode.required") %>');
						return false;
					} else {
						document.getElementById("loadBarcode").value = barcode.value;
						document.forms["logonForm"].submit();
						return true;
					}
				}
			
			</script>
			<input type="hidden" name="loadBarcode" id="loadBarcode" value="" />
			<table class="form2" cellspacing="0" cellpadding="0" >
				<tr>
					<td class="header" >
						<b><bean:message key="load.found.item" /></b>
					</td>
				</tr>
				<tr>
					<td>
						<center>
							<input type="text" id="barcode" class="textfield" />&nbsp;&nbsp;
							<html:submit styleId="button" onclick="loadFoundItemPage();" >
								<bean:message key='button.load' />
							</html:submit>
						</center>
					</td>
				</tr>
			</table>
		<% } %>
		
        <table class="form2" cellspacing="0" cellpadding="0">
          <logic:present name="activityList" scope="session">
             <% if (ntUser) { %>
            <logic:iterate id="activityDTO" name="activityList" type="com.bagnet.nettracer.tracing.dto.ActivityDTO">
              <logic:equal name="activityDTO" property="group" value="-1">
              		<logic:equal name="activityDTO" property="entries" value="0">
              		</logic:equal>
              		<logic:greaterThan name="activityDTO" property="entries" value="0">
              			<bean:define id="captchaFlag" value="yes"/>
              		</logic:greaterThan>
              </logic:equal>
            </logic:iterate>
            
            <logic:equal name="activityDTO" property="displayCaptcha" value="true">
            	Display Captcha
            </logic:equal>   
            <logic:present name="captchaFlag"> 
	            <tr>
	              <td class="header" style="background-color: red">
	                <b><bean:message key="tasks.captchas" /></b>
	              </td>
	              <td class="header" style="background-color: red">
	                <b><bean:message key="entries" /></b>
	              </td>
	            </tr>
	            <logic:iterate id="activityDTO" name="activityList" type="com.bagnet.nettracer.tracing.dto.ActivityDTO">
	              <logic:equal name="activityDTO" property="group" value="-1">
	                <tr>
	                  <td>
	                    <a href='<bean:write name="activityDTO" property="activityloc"/>'><bean:message key='<%= activityDTO.getActivityinfo().replaceAll(" ", "_") %>' /></a>
	                  </td>
	                  <td>
	                    <bean:write name="activityDTO" property="entries" />
	                  </td>
	                </tr>
	              </logic:equal>
	            </logic:iterate>
	            
	            <tr>
				  <td colspan="2" class="white">&nbsp;</td>
				</tr>
            </logic:present>
                     
            <tr>
              <td class="header">
                <b><bean:message key="tasks.incidents" /></b>
              </td>
              <td class="header">
                <b><bean:message key="entries" /></b>
              </td>
            </tr>
            <logic:iterate id="activityDTO" name="activityList" type="com.bagnet.nettracer.tracing.dto.ActivityDTO">
              <logic:equal name="activityDTO" property="group" value="1">
                <tr>
                  <td>
            <%
				String myKey = activityDTO.getActivityinfo().replaceAll(" ", "_");
				String myCaptionForLink = (String)bundle.getString( myKey);
				
				String myIndent = "";
				String mySearch = "&nbsp;";
				String result = "";
				
				int myIndex;
				int totalNumberOfCharacters = myCaptionForLink.length();
				do {
					myIndex = myCaptionForLink.indexOf(mySearch);
					if (myIndex != -1) {
						result = myCaptionForLink.substring(0, myIndex);
						myIndent += mySearch;
						result += myCaptionForLink.substring(myIndex + mySearch.length());
						myCaptionForLink = result;
					}
				} while (myIndex != -1);
			%>
                    <%=myIndent %><a href='<bean:write name="activityDTO" property="activityloc"/>'><%=myCaptionForLink %></a>
                  </td>
                  <td>
                    <bean:write name="activityDTO" property="entries" />
                  </td>
                </tr>
              </logic:equal>
            </logic:iterate>

			<tr>
			  <td colspan="2" class="white">&nbsp;</td>
			</tr>

            <tr>
              <td class="header">
                <b><bean:message key="tasks.ohds" /></b>
              </td>
              <td class="header">
                <b><bean:message key="entries" /></b>
              </td>
            </tr>
            <logic:iterate id="activityDTO" name="activityList" type="com.bagnet.nettracer.tracing.dto.ActivityDTO">
              <logic:equal name="activityDTO" property="group" value="2">
                <tr>
                  <td>
                    <a href='<bean:write name="activityDTO" property="activityloc"/>'><bean:message key='<%= activityDTO.getActivityinfo().replaceAll(" ", "_") %>' /></a>
                  </td>
                  <td>
                    <bean:write name="activityDTO" property="entries" />
                  </td>
                </tr>
              </logic:equal>
            </logic:iterate>
			<% } %>
			<% if (ntUser || ntfsUser) { %>
			<tr>
			  <td colspan="2" class="white">&nbsp;</td>
			</tr>
            <tr>
              <td class="header">
                <b><bean:message key="tasks.claims" /></b>
              </td>
              <td class="header">
                <b><bean:message key="entries" /></b>
              </td>
            </tr>
            <logic:iterate id="activityDTO" name="activityList" type="com.bagnet.nettracer.tracing.dto.ActivityDTO">
              <logic:equal name="activityDTO" property="group" value="3">
                <tr>
                  <td>
                    <a href='<bean:write name="activityDTO" property="activityloc"/>'><bean:message key='<%= activityDTO.getActivityinfo().replaceAll(" ", "_") %>' /></a>
                  </td>
                  <td>
                    <bean:write name="activityDTO" property="entries" />
                  </td>
                </tr>
              </logic:equal>
            </logic:iterate>
			<% } %>
			<% if (lfUser) { %>
			<tr>
			  <td colspan="2" class="white">&nbsp;</td>
			</tr>
            <tr>
              <td class="header">
                <b><bean:message key="tasks.lost.found" /></b>
              </td>
              <td class="header" width="120">
                <b>
                	<bean:message key="entries" />
                </b>
              </td>
            </tr>
            <logic:iterate id="activityDTO" name="activityList" type="com.bagnet.nettracer.tracing.dto.ActivityDTO">
              <logic:equal name="activityDTO" property="group" value="5">
                <tr>
                  <td>
                    <a href='<bean:write name="activityDTO" property="activityloc"/>'><bean:message key='<%= activityDTO.getActivityinfo().replaceAll(" ", "_") %>' /></a>
                  </td>
                  <td>
                    <span style="float:left" ><bean:write name="activityDTO" property="entries" /></span>
                    <logic:equal name="activityDTO" property="highPriority" value="true">
		    			<span style="float:right" ><font color="red"><i><b><bean:message key="status.urgent" /> (<bean:write name="activityDTO" property="highPriorityNumber" />)</b></i></font>&nbsp;&nbsp;&nbsp;</span>
		    		</logic:equal>
                  </td>
                </tr>
              </logic:equal>
            </logic:iterate>
			<% } %>
			
			<% if (ntUser) { %>
			<tr>
			  <td colspan="2" class="white">&nbsp;</td>
			</tr>
            <tr>
              <td class="header">
                <b><bean:message key="tasks.other" /></b>
              </td>
              <td class="header" width="120">
                <b>
                	<bean:message key="entries" />
                </b>
              </td>
            </tr>
            <logic:iterate id="activityDTO" name="activityList" type="com.bagnet.nettracer.tracing.dto.ActivityDTO">
              <logic:equal name="activityDTO" property="group" value="4">
                <tr>
                  <td>
                    <a href='<bean:write name="activityDTO" property="activityloc"/>'><bean:message key='<%= activityDTO.getActivityinfo().replaceAll(" ", "_") %>' /></a>
                  </td>
                  <td>
                    <span style="float:left" ><bean:write name="activityDTO" property="entries" /></span>
                    <logic:equal name="activityDTO" property="highPriority" value="true">
		    			<span style="float:right" ><font color="red"><i><b><bean:message key="status.urgent" /> (<bean:write name="activityDTO" property="highPriorityNumber" />)</b></i></font>&nbsp;&nbsp;&nbsp;</span>
		    		</logic:equal>
                  </td>
                </tr>
              </logic:equal>
            </logic:iterate>
			<% } %>
          </logic:present>
        </table>
      </html:form>
<script>
	if (document.getElementById("barcode") != null) {
		document.getElementById("barcode").focus();
	}
</script>