<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%
	Agent a = (Agent)session.getAttribute("user");
 	String cssFormClass = "form2";
%>


<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/ajax_forall.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();
	
	function getSubCategory() {
		o = document.searchLostFoundForm;
		o.changesubcategory.value = "1";
		document.getElementById("subcategorydiv").innerHTML = "<bean:message key="ajax.please_wait" />";
		postForm("searchLostFoundForm", true, function (req) { 
			o.changesubcategory.value = "0";
			document.getElementById("subcategorydiv").innerHTML = req.responseText; 

		});
	}
    
	function goprev() {
	  o = document.searchLostFoundForm;
	  o.prevpage.value = "1";
	  o.pagination.value="1";
	  o.submit();
	}
	
	function gonext() {
	  o = document.searchLostFoundForm;
	  o.nextpage.value="1";
	  o.pagination.value="1";
	  o.submit();
	}
	
	function gopage(i) {
		  o = document.searchLostFoundForm;
		  o.currpage.value = i;
		  o.pagination.value="1";
		  o.submit();
	}
	
	function updatePagination() {
	    return true;
	}

</script>
<jsp:include page="/pages/includes/validation_search.jsp" />
<html:form action="ntlf_search_lost_found.do?stLFCSearch=1" method="post" >
	<tr>
        <td colspan="3" id="pageheadercell">
          <div id="pageheaderleft">
            <h1>
            	<bean:message key="header.ship.to.lfc" />
            	<input type="hidden" name="found" value="1" />
            </h1>
          </div>
          <div id="pageheaderright">
            <table id="pageheaderright">
              <tr>
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
		        	<bean:message key="header.search_criteria" />
		        </h1>
         		<logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
         		<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
		            <tr>
           				<td align="center">
			              <bean:message key="colname.scope" />:&nbsp;&nbsp;
               				<html:select name="searchLostFoundForm" property="shipToLFCSearchType" styleClass="dropdown" >
               					<html:option value="0"><bean:message key="option.stlfc.two.days.old" /></html:option>
               					<html:option value="1"><bean:message key="option.stlfc.all.open" /></html:option>
               				</html:select>   
			            </td>
			        </tr>
           			<tr>
           				<td>
           					<center>
			              		<html:submit property="search" styleId="button" >
			              			<bean:message key="button.retrieve" />
			              		</html:submit>
			              	</center>
           				</td>
           			</tr>
				</table>
				<br />
	            <h1 class="green">
	                <bean:message key="header.search_result" />
	            </h1>
	            <br/>
                <input type="checkbox" name="select_all" id="select_all" /><bean:message key="css.select.all" />
              	<table class="<%=cssFormClass %>" cellpadding="0" cellspacing="0" >
              		<tr>
              			<td class="header" style="width:5%;">
              				<bean:message key="colname.lfc.ship" />
              			</td>
              			<td class="header" style="width:3%;">
              				<bean:message key="colname.lf.id" />
              			</td>
              			<td class="header" style="width:10%;">
              				<bean:message key="colname.lfc.found.date" />
              			</td>
              			<td class="header" style="width:22%;">
              				<bean:message key="colname.lfc.category" />
              			</td>
              			<td class="header" style="width:25%;">
              				<bean:message key="colname.lf.item.description" />
              			</td>
              			<td class="header" style="width:5%;">
              				<bean:message key="colname.lfc.brand" />
              			</td>
              			<td class="header" style="width:5%;">
              				<bean:message key="colname.lfc.model" />
              			</td>
              			<td class="header" style="width:5%;">
              				<bean:message key="colname.lfc.serial.number" />
              			</td>
              			<td class="header" style="width:5%;">
              				<bean:message key="colname.lfc.color" />
              			</td>
              			<td class="header" style="width:10%;">
              				<bean:message key="colname.lfc.case.color" />
              			</td>
              			<td class="header" style="width:5%;">
              				<bean:message key="colname.lf.status" />
              			</td>
              		</tr>
              		<logic:iterate indexId="i" id="found" name="searchLostFoundForm" property="foundResults" type="com.bagnet.nettracer.tracing.db.lf.LFFound">
              			<tr>
              				<td id='<%="ship" + i %>'>
              					<html:checkbox name="found" property="selected" indexed="true" />
              				</td>
              				<td id='<%="id" + i %>'>
              					<html:hidden name="found" property="id" indexed="true"/>
              					<a id='<%="link" + i %>' href="ntlf_create_found_item.do?foundId=<%=found.getId() %>"><bean:write name="found" property="id" /></a>
              				</td>
              				<td id='<%="date" + i %>'>
              					<%=found.getDisplayDate(a.getDateformat().getFormat()) %>
              				</td>
              				<td id='<%="category" + i %>'>
              					<bean:write name="found" property="item.catDesc" />
              				</td>
              				<td id='<%="desc" + i %>'>
              					<bean:write name="found" property="item.description" />&nbsp;
              				</td>
              				<td id='<%="brand" + i %>'>
              					<bean:write name="found" property="item.brand" />&nbsp;
              				</td>
              				<td id='<%="model" + i %>'>
              					<bean:write name="found" property="item.model" />&nbsp;
              				</td>
              				<td id='<%="serialNum" + i %>'>
              					<bean:write name="found" property="item.serialNumber" />&nbsp;
              				</td>
              				<td id='<%="color" + i %>'>
              					<bean:write name="found" property="item.color" />&nbsp;
              				</td>
              				<td id='<%="caseColor" + i %>'>
              					<bean:write name="found" property="item.caseColor" />&nbsp;
              				</td>
              				<td id='<%="status" + i %>'>
              					<bean:write name="found" property="statusDescription" />
              				</td>
              			</tr>
              		</logic:iterate>
				   	<tr>
						   <td colspan="11">
						   	<jsp:include page="/pages/includes/pagination_incl.jsp" />
						   </td>
				    </tr>
			    </table>
			    <br/>
				<h1 class="green">
		        	<bean:message key="header.ship.selected" />
		        </h1>
         		<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
		            <tr>
           				<td align="center">
		              		<bean:message key="colname.lf.tracking.number" />:&nbsp;&nbsp;
		              		<html:text styleId="trackingNumber" name="searchLostFoundForm" property="trackingNumber" size="20" maxlength="100" styleClass="textfield" /> 
			            </td>
			        </tr>
           			<tr>
           				<td>
           					<center>
			              		<html:submit property="ship" styleId="button" onclick="return validateShipping();">
			              			<bean:message key="button.ship.selected" />
			              		</html:submit>
			              	</center>
           				</td>
           			</tr>
				</table>
				<script language="Javascript">
	            	
					function validateShipping() {
						var trackingElem = document.getElementById("trackingNumber");
						var tracking = trackingElem.value.replace(/^\s+|\s+$/g, '');
						if (tracking.length == 0) {
							alert('<bean:message key="colname.lf.tracking.number" /> <bean:message key="error.validation.isRequired" />');
							trackingElem.focus();
							return false;
						}
					}
					
	              	jQuery("#select_all").click(function(source) { 
	            	    checkboxes = jQuery('input[type="checkbox"]');
	            	    for(var i in checkboxes){
	            	        checkboxes[i].checked = source.target.checked;
	            	    }
	            	});
				
				</script>
   			</div>
 		</td>
   	</tr>
</html:form>
