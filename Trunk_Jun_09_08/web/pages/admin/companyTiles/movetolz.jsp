<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="org.apache.struts.action.DynaActionForm" %>
		  <% int numberOfLzs = 0;%>
		<input type="hidden" name="pageState" value="<%=request.getAttribute("pageState") %>" />
		
		<html:hidden name="companyForm" property="companyCode" />
		
        <h1 class="green">
          <bean:message key="movetolz.header.settings" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          
        </h1>
        
        
        <logic:present name="saved" scope="request">
			<p align="center">
            	<font color="green"><bean:message key="prompt.company_saved" /></font>
  			</p>
        </logic:present>
          
        
        <logic:messagesPresent message="true">
          <font color=red>
            <html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages>
          </font>
        </logic:messagesPresent>
        
        <table class="form2" cellspacing="0" cellpadding="0">

          
          	  <tr>
	            <td>
	              <bean:message key="movetolz.rowname.mode" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
                  <html:select name="companyForm" property="lz_mode" styleClass="dropdown">
					<html:option value="1"><bean:message key="movetolz.mode.1" /></html:option>
					<html:option value="2"><bean:message key="movetolz.mode.2" /></html:option>
	              </html:select>
	            </td>
	          </tr>
          
          	  <tr>
	            <td>
	              <bean:message key="colname.mbr_to_lz_days" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="mbr_to_lz_days" size="4" maxlength="4" />
	              (Default: 5)
	            </td>
	          </tr>
	
	          <tr>
	            <td>
	              <bean:message key="colname.damaged_to_lz_days" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="damaged_to_lz_days" size="4" maxlength="4" />
	              (Default: 0)
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.miss_to_lz_days" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="miss_to_lz_days" size="4" maxlength="4" />
	              (Default: 0)
	            </td>
	          </tr>
	
	          <tr>
	            <td>
	              <bean:message key="colname.ohd_to_lz_days" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="ohd_to_lz_days" size="4" maxlength="4" />
	              (Default: 5)
	            </td>
	          </tr>
	          
	          <tr>
	            <td>
	              <bean:message key="movetolz.rowname.ohdlz" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	          	  <logic:notEmpty name="stationList" scope="request">
                    <html:select name="companyForm" property="ohd_lz" styleClass="dropdown">
	                  <html:options collection="stationList" property="station_ID" labelProperty="stationcode" />
	                </html:select>
	              </logic:notEmpty>
	            </td>
	          </tr>
	       <tr>
            <td colspan="2">&nbsp;</td>
          </tr>
          <tr>
            <td colspan="2">
              <center><INPUT type="button" Id="button" value="Back" onClick="history.back()">
              &nbsp;
              <html:submit styleId="button" property="save">
                <bean:message key="movetolz.button.savesettings" />
              </html:submit></center>
            </td>
          </tr>

        </table>

        
        <h1 class="green">
          <bean:message key="movetolz.header.listoflzs" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
        </h1>
        
        <table class="form2" cellspacing="0" cellpadding="0">
		  <tr>
            <td colspan="2">
              <b><bean:message key="movetolz.colname.station" /></b>
            </td>
            <td>
              <b><bean:message key="movetolz.colname.percentage" /></b>
            </td>
            <td>
              <b><bean:message key="movetolz.colname.default" /></b>
            </td>
            <td>
              <b><bean:message key="movetolz.colname.remove" /></b>
            </td>
	      </tr>
	      
		  <logic:iterate id="lz" indexId="i" name="companyForm" property="lzStations" type="com.bagnet.nettracer.tracing.db.Lz">
		  <% ++numberOfLzs; %>
		      <tr>
		        <td colspan="2"><bean:write name="lz" property="station.stationcode"/></td>
		        <td>
		          <html:text name="lz" property="percent" size="5" styleClass="textfield" indexed="true" />
		        </td>
		        <td><html:radio property="defaultLz" value="<%=new Integer(lz.getLz_ID()).toString() %>" /></td>
		        <td>
				  <%
				  	if (lz.isIs_default()){
				  %>
				   <bean:message key="movetolz.delete.default" />
				  <%	
				  	} else if (lz.isUsed()) {
				  %>
				   	<bean:message key="movetolz.delete.assigned" />
				  <%
				  	} else {
				  %>
		          	<input type="checkbox" name="lz[<%=i %>].delete"/>
		          	<%
				  	}
				  	%>
		        </td>
		      </tr>
		  </logic:iterate>
	      
	      
	      <tr>
	        <td colspan="5">&nbsp;</td>
	      </tr>
	      
	      <tr>
	        <td colspan="5" align="center">
              <logic:present name="stationList">
                <html:select styleClass="dropdown" name="companyForm" property="new_lz">
                  <html:options collection="stationList" property="station_ID" labelProperty="stationcode" />
                </html:select>
                <html:submit styleId="button" property="addNewLz">
                  <bean:message key="movetolz.button.addnewlz" />
                </html:submit>
              </logic:present>
              &nbsp;&nbsp;
              <input type="submit" name="saveLzList" value="<bean:message key="movetolz.button.savelzlist"/>" onclick="buttonSelected = 'savelzlist'; numberOfLzs=<%=numberOfLzs %>" id="button"/>
	        </td>
	      </tr>
        </table>


        <h1 class="green">
          <bean:message key="movetolz.header.lzassignments" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
        </h1>
        <table class="form2" cellspacing="0" cellpadding="0">
          <tr>
            <td><b><bean:message key="movetolz.colname.station" /></b></td>
            <td><b><bean:message key="movetolz.colname.assignment" /></b></td>
          </tr>
        


		  
		  <logic:iterate id="station" indexId="i" name="stationList" type="com.bagnet.nettracer.tracing.db.Station">
		      <tr>
		        <td><bean:write name="station" property="stationcode"/></td>
		        <td>
		        	<% String dropdownName = "station[" + i + "].lz"; %>
		        	<select name="<%=dropdownName %>" class="dropdown">


		        	<logic:iterate id="lz" indexId="j" name="companyForm" property="lzStations" type="com.bagnet.nettracer.tracing.db.Lz">
		        		<% 
		        			String value = "" + lz.getLz_ID();
		        			String text = lz.getStation().getStationcode();
		        			if (station.getLz().getLz_ID() == lz.getLz_ID()) {
		        		%>
		        			<option value="<%=value %>" selected="selected"><%=text %></option>
		        		<%
		        			} else {
		        		%>
							<option value="<%=value %>"><%=text %></option>
						<% 
							}
		        		%>
		        	</logic:iterate>
		        	</select>
		        </td>
		      </tr>
		  </logic:iterate>
	      
        
        
	       <tr>
            <td colspan="2">&nbsp;</td>
          </tr>
          <tr>
            <td colspan="2">
              <center>
                <html:submit styleId="button" property="saveAssignments">
                  <bean:message key="movetolz.button.saveassignments" />
                </html:submit>
              </center>
            </td>
          </tr>
        </table>

