<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page import="java.util.Locale, org.apache.commons.lang.math.NumberUtils"%>
<%@ page import="org.apache.struts.util.PropertyMessageResources, com.bagnet.nettracer.tracing.constant.TracingConstants"%>

<%

	Locale myLocale = (Locale) session.getAttribute("org.apache.struts.action.LOCALE");
	PropertyMessageResources myMessages = (PropertyMessageResources) request.getAttribute("org.apache.struts.action.MESSAGE");
	
%>
<script type="text/javascript">
<!--
	function verifyStartingPositionRange() {
		var startingPosition = document.getElementById("starting_position");
		if (startingPosition.value.length < 1) {
			alert('<%=(String) myMessages.getMessage(myLocale, "errors.required", new Object[]{myMessages.getMessage(myLocale, "label.starting.position")})%>');
			startingPosition.focus();
  			return false;			
		} else if (!isPositiveInteger(startingPosition.value)) {
			alert(startingPosition.value + ' <%=(String) myMessages.getMessage(myLocale, "error.validation.validNumber")%>');
			startingPosition.focus();
  			return false;			
		} else if (<%=TracingConstants.LABELS_PER_PAGE%> < startingPosition.value) {
			alert(startingPosition.value + '<%=(String) myMessages.getMessage(myLocale, "errors.range", new Object[]{"", 1, TracingConstants.LABELS_PER_PAGE})%>');
			startingPosition.focus();
  			return false;
		}
		
		return true;
	}

	function validateReqLabelFields(form) {	    
	    for (var j=0;j < form.length; j++) {
	      currentElement = form.elements[j];
			
	      if (currentElement.name == "label") {
				if (0 < currentElement.value.length) {
					 return true;
				}
				
				alert("<%=(String) myMessages.getMessage(myLocale, "colname.label")%>" + " <%=(String) myMessages.getMessage(myLocale, "error.validation.isRequired")%>");
	  		  	currentElement.focus();
	  			return false;
		   }
	    }
	
		return true;
	}
	
	function validateCheckboxes(checkboxName) {	    
		var checkboxes = document.getElementsByName(checkboxName);
		if (checkboxes.length < 1) {
			alert("<%=(String) myMessages.getMessage(myLocale, "colname.label")%>" + " <%=(String) myMessages.getMessage(myLocale, "error.validation.isRequired")%>");
			currentElement = document.getElementById('label_text');
			if (currentElement) {
				currentElement.focus();
			}
  			return false;
		}
		  
	    for (var j=0;j < checkboxes.length; j++) {
	      	currentElement = checkboxes[j];
			if (currentElement.checked) {
				 return true;
			}
	    }
	    
		alert("<%=(String) myMessages.getMessage(myLocale, "error.validation.missingLabel")%>");
		currentElement = checkboxes[0]
		if (currentElement) {
			currentElement.focus();
		}

		return false;
	}
//-->
</script>

  <jsp:include page="/pages/includes/taskmanager_header.jsp" />
  
  <html:form action="label.do" method="post">
    <tr>
      <td id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
				<c:choose>
	        		<c:when test="${not empty label}">
				        <bean:message key="header.label.edit" />
				    </c:when>
				    <c:otherwise>
				    	<bean:message key="header.label.queue" />
				    </c:otherwise>
			    </c:choose>
          </h1>
        </div>
        <div id="pageheaderright">
        	&nbsp;
        </div>                  
      </td>
    </tr>
    
    <tr>      
      <td id="middlecolumn">
        <div id="maincontent">
          <logic:messagesPresent message="true">
			<div>
				<html:messages id="msg" message="true">
					<font color="red"><bean:write name="msg"/><br/></font>
				</html:messages>
			</div>
		  </logic:messagesPresent>
				  
        	<c:choose>
        		<c:when test="${not empty printedLabelIds}">
        			<strong>
        				<bean:message key="confirm.label.print.success" />
        			</strong>
        			<br/>
        			
					<input type="hidden" name="printedLabelIds" value="${printedLabelIds}"/>
					<input type="submit" name="confirm_print" value="<bean:message key="oc.label.yes" />" id="button"/>
					<input type="submit" name="cancel" value="<bean:message key="oc.label.no" />" id="button"/>	            
		            
		            <hr/>
		            
		             <div>
		             	<a href="#" onclick="openWindowWithBar('reporting?outputtype=<%=request.getAttribute("outputtype")%>&reportfile=<bean:write name="reportfile" scope="request"/>','report',800,600);return false;"><b><bean:message key="link.view_report" /></b></a>
		              </div>
		              <script>
		              		openWindowWithBar('reporting?outputtype=<%=request.getAttribute("outputtype")%>&reportfile=<bean:write name="reportfile" scope="request" />','report',800,600);
					</script>
		        </c:when>  
        		<c:when test="${not empty label}">
			        <textarea name="label" id="label_text" cols="40" rows="2" class="textfield" maxlength="100">${label.text}</textarea>
					<input type="hidden" name="label_id" value="${label.id}"/>
					<input type="submit" name="update" value="<bean:message key="button.save" />" id="button" onclick="return validateReqLabelFields(this.form)"/>
					<input type="submit" name="cancel" value="<bean:message key="cancel" />" id="button"/>
			    </c:when>
			    <c:otherwise>
				  <div>
					  <textarea name="label" id="label_text" cols="40" rows="2" class="textfield" maxlength="100"></textarea>
					  <input type="submit" name="create" value="<bean:message key="add" />" id="button" onclick="return validateReqLabelFields(this.form)"/>
		    	  </div>
		    	  
                  <c:if test="${not empty labellist}">
		          	<display:table id="label" name="requestScope.labellist" requestURI="/label.do" sort="external" 
		          		size="<%=NumberUtils.toInt((String)request.getAttribute("rowcount"))%>" pagesize="<%=NumberUtils.toInt((String)request.getAttribute("rowsperpage"))%>"
		          		class="form2" cellspacing="0" cellpadding="0" partialList="true">
		          		 
		          		<display:column titleKey="colname.select" headerClass="header">
		          			<input type="checkbox" name="label_id" value="<bean:write name="label" property="id"/>"/>
		          		</display:column>
		          		
		          		<display:column titleKey="colname.label" property="htmlFormatedText" headerClass="header"/>
		
						<display:column titleKey="header.action" headerClass="header">
		          			<a href="label.do?label_id=<bean:write name="label" property="id"/>&action=edit" ><bean:message key="edit" /></a>
		          			|
		          			<a href="label.do?label_id=<bean:write name="label" property="id"/>&action=delete" ><bean:message key="delete" /></a>
			          	</display:column>
		            </display:table>
		          </c:if>
         		</c:otherwise>
         	</c:choose>         
	
			<c:if test="${not empty labellist and empty label}">
				<table class="form2" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<input type="button" name="checkAllButton" id="button" value="<bean:message key="css.select.all" />"  onclick="checkAllCheckboxes('label_id');"/>
							<br/>
							
							<input type="text" name="starting_position" id="starting_position" value="" size="2" maxlength="2" class="textfield" onchange="verifyStartingPositionRange();">
							&nbsp;
							<bean:message key="label.starting.position" />
							&nbsp;&nbsp;
							<input type="submit" name="print" value="<bean:message key="print" />" id="button" onclick="return validateCheckboxes('label_id') && verifyStartingPositionRange();">
						</td>
				    </tr>
			    </table>
		    </c:if>
         </div>         
	 </td>
	</tr>
</html:form>