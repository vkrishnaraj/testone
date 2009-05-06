<%@ page language="java" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="org.apache.struts.action.Action" %>

<%
  Agent a = (Agent)session.getAttribute("user");
%>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/field_validation.js"></SCRIPT>
<%
  org.apache.struts.util.PropertyMessageResources myMessages = (org.apache.struts.util.PropertyMessageResources)
                                                               request.getAttribute("org.apache.struts.action.MESSAGE");
  java.util.Locale                                myLocale   = (java.util.Locale)session.getAttribute(
                                                               "org.apache.struts.action.LOCALE");
%>
  <script language="javascript">
    

  function checkDate(strng)
  {
    return isDate(strng,'<%= a.getDateformat().getFormat() %>');
  }
  
  function checkTime(strng)
  {
    return isDate(strng,'<%= a.getTimeformat().getFormat() %>');
  }
  
  function validateSearch(form) {
    for (var j=0;j<form.length;j++) {
      currentElement = form.elements[j];
      currentElementName=currentElement.name;
      if (currentElementName.indexOf("s_createtime") != -1)
      {
        if (currentElement.value.length > 0 && !checkDate(currentElement.value))
        {
          alert("<%= (String)myMessages.getMessage(myLocale, "colname.date_range") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.date") %>");
          currentElement.focus();
          return false;
        }
      }
      
      else if (currentElementName.indexOf("e_createtime") != -1)
      {
        if (currentElement.value.length > 0 && !checkDate(currentElement.value))
        {
          alert("<%= (String)myMessages.getMessage(myLocale, "colname.date_range") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.date") %>");
          currentElement.focus();
          return false;
        }
      }
    }
     return true;
  }



  </script>
