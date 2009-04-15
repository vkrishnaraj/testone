<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>NetTracer - Manual Queue</title>
</head>
<body>
<table align="center" width="98%" border="0" height="98%"
  cellpadding="0" cellspacing="0">
  <tr height="100%">
    <td align="center" colspan="3">
    <table border="0" width="100%" height="100%" valign="top"
      cellpadding="0" cellspacing="0">
      <tr>
        <td>
        <h1>CREATE AHL FOR: ATLUS00000001</h1>
        </td>
      </tr>
      <tr bgcolor="#E0E0E0">
        <td><b>Data</b></td>
      </tr>
      <tr height="100%">
        <td><iframe src="html_intro.asp" width="100%" height="100%">
        <p>Your browser does not support iframes.</p>
        </iframe>
        <p />&nbsp;
        </td>
      </tr>

      <tr bgcolor="#E0E0E0">
        <td><b>Required Input</b></td>
      </tr>
      <tr>
        <td>

        <table>
          <tr>
            <td><b>WorldTracer AHL ID:</b></td>
            <td><input type="text" size="20" /></td>
          </tr>

          <tr>
            <td></td>
            <td><input type="submit" name="submit" value="Submit"></td>
          </tr>


        </table>



        </td>
      </tr>
    </table>
    </td>
  </tr>
  <tr>
    <td align="left"></td>
    <td align="left"></td>
    <td align="right"><input type="submit" name="cancel"
      value="Skip Queue Item">&nbsp;<input type="submit"
      name="cancel" value="Cancel Queue Item">&nbsp;<input
      type="submit" name="return" value="Return to NetTracer"></td>
  </tr>
  <tr>
    <td align="center" colspan="3">&nbsp;
    <p /><font size="1">Copyright © Owens Technologies
    Incorporated, 2003-2009</font>
    </td>
  </tr>
</table>
</body>
</html>
