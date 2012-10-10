<%@ page language="java" %>
<%
  org.apache.struts.util.PropertyMessageResources myMessages = (org.apache.struts.util.PropertyMessageResources)
                                                               request.getAttribute("org.apache.struts.action.MESSAGE");
  java.util.Locale                                myLocale   = (java.util.Locale)session.getAttribute(
                                                               "org.apache.struts.action.LOCALE");
  Agent a = (Agent)session.getAttribute("user");
%>

  <%@page import="com.bagnet.nettracer.tracing.db.Agent"%>
<script language="javascript">
    

  function checkDate(strng)
  {
    return isDate(strng,'<%= a.getDateformat().getFormat() %>');
  }
  
  function checkTime(strng)
  {
    return isDate(strng,'<%= a.getTimeformat().getFormat() %>');
  }

  function validateLostFound(form)
  {
    for (var j=0;j<form.length;j++) {
      currentElement = form.elements[j];
      currentElementName=currentElement.name;
      if (currentElementName.indexOf("dispDateFoundLost") != -1)
      {
        if (currentElement.value.length > 0 && !checkDate(currentElement.value))
        {
          alert("<%= (String)myMessages.getMessage(myLocale, "colname.date") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.date") %>"); 
          currentElement.focus();
          return false;
        }
      }
      else if (currentElementName.indexOf("email") != -1 )
      {

        if (currentElement.value.length > 0 && !checkEmail(currentElement.value))
        {
          alert("<%= (String)myMessages.getMessage(myLocale, "colname.email") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.email") %>");
          currentElement.focus();
          return false;
        }
      }
      else if (currentElementName.indexOf("category_id") != -1)
      {  
        if (currentElement.value == 0)
        {
          alert("<%= (String)myMessages.getMessage(myLocale, "colname.lf.category") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>"); 
          currentElement.focus();
          return false;
        }
      }
    }
    return true;
  }
  

  </script>
