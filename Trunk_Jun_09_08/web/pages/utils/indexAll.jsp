<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<html>
  <head>
    <title>
      Lucence Index Files
    </title>
    <h1>
      Index All files
    </h1>
  </head>
  <body>
    <html:form action="addOnHandBag.do" method="post">
      <table>
        <tr>
          <td>
            Click the button to Index All files.
          </td>
          <td>
            <html:submit styleId="button" property="indexAllFiles">
              indexAllFiles
            </html:submit>
          </td>
        </tr>
      </html:form>
    </body>
  </html>
