<%@ page language="java" %>
<%
  org.apache.struts.util.PropertyMessageResources myMessages = (org.apache.struts.util.PropertyMessageResources)
                                                               request.getAttribute("org.apache.struts.action.MESSAGE");
  java.util.Locale                                myLocale   = (java.util.Locale)session.getAttribute(
                                                               "org.apache.struts.action.LOCALE");
%>
<%
  String found = (String)request.getAttribute("found");
%>

  <script language="javascript">
    

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
    <%
    if (found == null) {
    %>
    
	    if (currentElementName.indexOf("customer_firstname") != -1) {  

	      if (currentElement.value.length == 0)
	      {
	        alert("<%= (String)myMessages.getMessage(myLocale, "colname.first_name") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
	        currentElement.focus();
	        return false;
	      }
	    }

      else if (currentElementName.indexOf("customer_lastname") != -1) {  

        if (currentElement.value.length == 0)
        {
          alert("<%= (String)myMessages.getMessage(myLocale, "colname.last_name") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
          currentElement.focus();
          return false;
        }
      }
      
      else if (currentElementName.indexOf("customer_address1") != -1) {  

        if (currentElement.value.length == 0)
        {
          alert("<%= (String)myMessages.getMessage(myLocale, "colname.street_addr") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
          currentElement.focus();
          return false;
        }
      }

      else if (currentElementName.indexOf("customer_city") != -1) {  

        if (currentElement.value.length == 0)
        {
          alert("<%= (String)myMessages.getMessage(myLocale, "colname.city") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
          currentElement.focus();
          return false;
        }
      }
      else if (currentElementName.indexOf("dispDateFoundLost") != -1) {  

        if (currentElement.value.length == 0)
        {
          alert("<%= (String)myMessages.getMessage(myLocale, "colname.date_lost") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
          currentElement.focus();
          return false;
        }
      }
      
      else if (currentElementName.indexOf("location") != -1) {  

        if (currentElement.value.length == 0)
        {
          alert("<%= (String)myMessages.getMessage(myLocale, "colname.found_location") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
          currentElement.focus();
          return false;
        }
      }
      
      
    <%
    }
    %>
      }
    return true;
  }
  

  </script>
