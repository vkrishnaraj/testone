<%@ page language="java" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="org.apache.struts.action.Action" %>
<%
  Agent a = (Agent)session.getAttribute("user");

  org.apache.struts.util.PropertyMessageResources myMessages = (org.apache.struts.util.PropertyMessageResources)
                                                               request.getAttribute("org.apache.struts.action.MESSAGE");
  java.util.Locale                                myLocale   = (java.util.Locale)session.getAttribute(
                                                               "org.apache.struts.action.LOCALE");
%>
  <script language="javascript">
  <!--
  function validatereq(form)
  {
 		return true;
  }
  
  function validatereqFields(form, formType)
  {
  	return true;
  } 
  
  /******************* ohd *****************/
  function validatereqOHDForm(form) {
  	return true;
  }
  	
  
  function validatereqOHD(form)
  {
	  return true;
	}
	
	
	/***************** BEoRN ****************/
	function validatereqBEORN(form)
  {
	  return true;
	}
	// -->
  </script>
