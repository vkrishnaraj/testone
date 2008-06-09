<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>
<!-- These scripts are there for ajax functionality-->
<script type="text/javascript" src="deployment/main/js/prototype-1.4.0.js"></script>
<script type="text/javascript" src="deployment/main/js/scriptaculous.js"></script>
<script type="text/javascript" src="deployment/main/js/overlibmws.js"></script>
<script type="text/javascript" src="deployment/main/js/ajaxtags.js"></script>
<td>
  <div id="portletArea">

    <ajax:portlet source="portlet_1" baseUrl="checkMessages.view" classNamePrefix="portlet" title="" refreshPeriod="100000" />

  </div>
</td>
