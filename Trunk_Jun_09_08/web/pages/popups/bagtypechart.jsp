<%@ page language="java"import="java.lang.*,
                                java.util.*" %>
<%@ page import="org.apache.struts.util.MessageResources" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%
  Agent            a        = (Agent)session.getAttribute("user");
  String           path     = request.getContextPath();
  String           basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
                              + "/";
  MessageResources messages = MessageResources.getMessageResources(
                              "com.bagnet.nettracer.tracing.resources.ApplicationResources");
%>
  <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
  <html>
    <head>
      <title>
        <%= messages.getMessage(new Locale(a.getDefaultlocale()), "title.bagchart") %>
      </title>
      <script language="javascript">
        <!--
function choosetype(o) {
	var field;

<%
                                                        if (request.getParameter("key") != null) {
%>

	for (i = 0;  i < window.opener.document.forms[0].elements.length; i++ ) {
		if (window.opener.document.forms[0].elements[i].name == '<%= request.getParameter("key") %>') {
			field = window.opener.document.forms[0].elements[i];
			break;
		}
	}
<%
                                                      } else {
%>
	field = window.opener.document.forms[0].bagType;
<%
                                                      }
%>
	if (field != null) {
		for (i = 0; i < field.length; i++) {
			if (field.options[i].value == o) {
				field.options[i].selected = true;
				break;
			}
		}
	}
	
	self.close();
}


function returnFalse(e)
{
    return false;
}

document.oncontextmenu = returnFalse;

//-->
      </script>
      <map name="FPMap0">
        <area href="javascript:choosetype('01');" shape="rect" coords="24, 46, 133, 154">
          <area href="javascript:choosetype('02');" shape="rect" coords="135, 46, 241, 154">
            <area href="javascript:choosetype('03');" shape="rect" coords="246, 48, 352, 151">
              <area href="javascript:choosetype('04');" shape="rect" coords="356, 46, 463, 154">
                <area href="javascript:choosetype('05');" shape="rect" coords="24, 158, 130, 266">
                  <area href="javascript:choosetype('06');" shape="rect" coords="136, 159, 240, 264">
                  <!--<area href="javascript:choosetype('-1');" shape="rect" coords="247, 158, 353, 263">//-->
                  <area href="javascript:choosetype('07');" shape="rect" coords="356, 158, 464, 264">
                    <area href="javascript:choosetype('08');" shape="rect" coords="25, 269, 131, 377">
                      <area href="javascript:choosetype('09');" shape="rect" coords="134, 270, 241, 374">
                        <area href="javascript:choosetype('10');" shape="rect" coords="246, 268, 354, 377">
                          <area href="javascript:choosetype('11');" shape="rect" coords="356, 269, 462, 378">
                            <area href="javascript:choosetype('20');" shape="rect" coords="507, 47, 613, 154">
                              <area href="javascript:choosetype('22');" shape="rect" coords="617, 48, 724, 154">
                                <area href="javascript:choosetype('23');" shape="rect" coords="727, 47, 834, 155">
                                  <area href="javascript:choosetype('24');" shape="rect" coords="839, 47, 948, 155">
                                    <area href="javascript:choosetype('25');" shape="rect" coords="505, 158, 613, 263">
                                    <!--<area href="javascript:choosetype('-2');" shape="rect" coords="619, 160, 834, 264">//-->
                                    <area href="javascript:choosetype('26');" shape="rect" coords="838, 159, 947, 264">
                                      <area href="javascript:choosetype('27');" shape="rect" coords="557, 270, 665, 376">
                                        <area href="javascript:choosetype('28');" shape="rect" coords="671, 270, 778, 376">
                                          <area href="javascript:choosetype('29');" shape="rect" coords="787, 269, 896, 377">
                                          </map>
                                          <map name="FPMap1">
                                            <area href="javascript:choosetype('50');" shape="rect" coords="50, 37, 148, 166">
                                              <area href="javascript:choosetype('51');" shape="rect" coords="154, 37, 253, 165">
                                                <area href="javascript:choosetype('52');" shape="rect" coords="257, 39, 352, 165">
                                                  <area href="javascript:choosetype('53');" shape="rect" coords="359, 38, 457, 165">
                                                    <area href="javascript:choosetype('54');" shape="rect" coords="461, 38, 561, 166">
                                                      <area href="javascript:choosetype('55');" shape="rect" coords="567, 38, 665, 165">
                                                        <area href="javascript:choosetype('56');" shape="rect" coords="670, 39, 768, 167">
                                                          <area href="javascript:choosetype('57');" shape="rect" coords="771, 37, 870, 166">
                                                            <area href="javascript:choosetype('58');" shape="rect" coords="875, 39, 971, 165">
                                                              <area href="javascript:choosetype('59');" shape="rect" coords="51, 172, 160, 300">
                                                                <area href="javascript:choosetype('60');" shape="rect" coords="164, 172, 275, 299">
                                                                  <area href="javascript:choosetype('61');" shape="rect" coords="278, 171, 387, 299">
                                                                    <area href="javascript:choosetype('62');" shape="rect" coords="392, 171, 508, 300">
                                                                      <area href="javascript:choosetype('63');" shape="rect" coords="515, 173, 633, 300">
                                                                        <area href="javascript:choosetype('64');" shape="rect" coords="635, 172, 746, 299">
                                                                          <area href="javascript:choosetype('65');" shape="rect" coords="749, 174, 856, 299">
                                                                            <area href="javascript:choosetype('66');" shape="rect" coords="864, 173, 972, 300">
                                                                              <area href="javascript:choosetype('67');" shape="rect" coords="52, 306, 156, 434">
                                                                                <area href="javascript:choosetype('68');" shape="rect" coords="158, 307, 251, 435">
                                                                                  <area href="javascript:choosetype('69');" shape="rect" coords="256, 305, 350, 433">
                                                                                    <area href="javascript:choosetype('70');" shape="rect" coords="353, 306, 453, 436">
                                                                                      <area href="javascript:choosetype('71');" shape="rect" coords="459, 307, 556, 431">
                                                                                        <area href="javascript:choosetype('72');" shape="rect" coords="560, 305, 662, 433">
                                                                                          <area href="javascript:choosetype('80');" shape="rect" coords="665, 307, 765, 432">
                                                                                            <area href="javascript:choosetype('81');" shape="rect" coords="771, 308, 867, 433">
                                                                                              <area href="javascript:choosetype('82');" shape="rect" coords="874, 305, 969, 433">
                                                                                                <area href="javascript:choosetype('83');" shape="rect" coords="52, 439, 146, 566">
                                                                                                  <area href="javascript:choosetype('84');" shape="rect" coords="153, 440, 253, 568">
                                                                                                    <area href="javascript:choosetype('85');" shape="rect" coords="259, 439, 353, 568">
                                                                                                      <area href="javascript:choosetype('90');" shape="rect" coords="362, 439, 446, 566">
                                                                                                        <area href="javascript:choosetype('91');" shape="rect" coords="454, 440, 549, 568">
                                                                                                          <area href="javascript:choosetype('92');" shape="rect" coords="555, 438, 655, 566">
                                                                                                            <area href="javascript:choosetype('93');" shape="rect" coords="660, 437, 761, 565">
                                                                                                              <area href="javascript:choosetype('98');" shape="rect" coords="768, 438, 867, 565">
                                                                                                                <area href="javascript:choosetype('99');" shape="rect" coords="874, 439, 973, 566">
                                                                                                                </map>
                                                                                                              </head>
                                                                                                              <body>
<%
                                                                                                                if (
                                                                                                                        request.getParameter
                                                                                                                        ("charttype")
                                                                                                                        != null
                                                                                                                        && 
                                                                                                                        request.getParameter
                                                                                                                        ("charttype").equals
                                                                                                                        ("2")) {

                                                                                                                        %>
  <img src="../../deployment/main/images/nettracer/charts/IATA_BAG2.JPG" border=0 usemap="#FPMap1"><%
} else {
%>
  <img src="../../deployment/main/images/nettracer/charts/IATA_BAG1.JPG" border=0 usemap="#FPMap0"><%
}
%>
</body>
</html>
