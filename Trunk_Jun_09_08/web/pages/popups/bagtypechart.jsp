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

function choosexdescelement(o) {
	var xdescelementfield;
    var xdescelement_ID;
<%
   
   if (request.getParameter("xdescelement") != null) {
	 
%>

	for (i = 0;  i < window.opener.document.forms[0].elements.length; i++ ) {
		if (window.opener.document.forms[0].elements[i].name == '<%= request.getParameter("xdescelement") %>') {
			xdescelementfield = window.opener.document.forms[0].elements[i];
			
			break;
		}
	}
<%
   } else {
  String type = request.getParameter("type");
  if (type.equals("XDesc1")){
%>

	xdescelementfield = window.opener.document.forms[0].XDesc1;
<%
   }
  if (type.equals("XDesc2")){
%>
    xdescelementfield = window.opener.document.forms[0].XDesc2; 
<%
  }
  if (type.equals("XDesc3")){
%>
    xdescelementfield = window.opener.document.forms[0].XDesc3;
<%	  	  
  }
  if (type.equals("xdescelement_ID_1")){
%>
    xdescelementfield = window.opener.document.forms[0].xdescelement_ID_1;
<%	  
  }
  if (type.equals("xdescelement_ID_2")){
%>
    xdescelementfield = window.opener.document.forms[0].xdescelement_ID_2;
<%	  
  }
  if (type.equals("xdescelement_ID_3")){
%>
    xdescelementfield = window.opener.document.forms[0].xdescelement_ID_3;
<%	  
  }
  if (type.equals("xdescelement_ID1")){
%>
    xdescelementfield = window.opener.document.forms[0].xdescelement_ID1;	  
<%	  
  }
  if (type.equals("xdescelement_ID2")){
%>
    xdescelementfield = window.opener.document.forms[0].xdescelement_ID2;
<%	  
  }
  if (type.equals("xdescelement_ID3")){
%>
    xdescelementfield = window.opener.document.forms[0].xdescelement_ID3;
<%	  
  }
   }
%>
	if (xdescelementfield != null) {
		for (i = 0; i < xdescelementfield.length; i++) {
			if (xdescelementfield.options[i].text.substring(0,1) == o) {
				xdescelementfield.options[i].selected = true;
				break;
			}
		}
	}

self.close();
}


function choosetype(o) {
	var field;
	var type;
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
   String type = request.getParameter("type");
   if (type.equals("bagType")){
%>
	field = window.opener.document.forms[0].bagType;
<%
    }
   if (type.equals("bagColor")){
%>
    field = window.opener.document.forms[0].bagColor;
<%	   
   }
   if (type.equals("color")){
%>
    field = window.opener.document.forms[0].color;
<%	   	   
   }
   if (type.equals("bagtype")){
%>
    field = window.opener.document.forms[0].bagtype;
<%	   
   }
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


      </script>

      <map name="FPMap0">
        <area href="javascript:choosetype('01');" shape="rect" coords="4,16,95,99">
          <area href="javascript:choosetype('02');" shape="rect" coords="101,16,193,100">
            <area href="javascript:choosetype('03');" shape="rect" coords="199,16,290,99">
              <area href="javascript:choosetype('05');" shape="rect" coords="297,17,389,100">
                <area href="javascript:choosetype('06');" shape="rect" coords="2,107,95,190">
                  <area href="javascript:choosetype('07');" shape="rect" coords="298,106,389,189">
                  
                  <area href="javascript:choosetype('08');" shape="rect" coords="4,197,96,279">
                    <area href="javascript:choosetype('09');" shape="rect" coords="101,197,194,279">
                      <area href="javascript:choosetype('10');" shape="rect" coords="200,197,292,279">
                        <area href="javascript:choosetype('12');" shape="rect" coords="297,198,391,280">
                        
                          <area href="javascript:choosetype('20');" shape="rect" coords="411,17,534,99">
                            <area href="javascript:choosetype('22');" shape="rect" coords="539,17,622,100">
                              <area href="javascript:choosetype('22');" shape="rect" coords="626,15,708,100">
                                <area href="javascript:choosetype('22');" shape="rect" coords="715,16,797,98">
                                  <area href="javascript:choosetype('23');" shape="rect" coords="411,107,512,188">
                                    <area href="javascript:choosetype('25');" shape="rect" coords="697,106,798,189">
                                    
                                    <area href="javascript:choosetype('26');" shape="rect" coords="410,197,501,280">
                                      <area href="javascript:choosetype('27');" shape="rect" coords="506,196,601,278">
                                        <area href="javascript:choosetype('28');" shape="rect" coords="607,197,699,279">
                                          <area href="javascript:choosetype('29');" shape="rect" coords="705,197,797,279">
                                          </map>
                                          <map name="FPMap1">
                                            <area href="javascript:choosetype('50');" shape="rect" coords="7,8,80,91">
                                              <area href="javascript:choosetype('51');" shape="rect" coords="85,8,160,89">
                                                <area href="javascript:choosetype('52');" shape="rect" coords="166,6,240,90">
                                                  <area href="javascript:choosetype('53');" shape="rect" coords="245,7,319,90">
                                                    <area href="javascript:choosetype('54');" shape="rect" coords="323,7,399,90">
                                                      <area href="javascript:choosetype('55');" shape="rect" coords="404,7,479,90">
                                                        <area href="javascript:choosetype('56');" shape="rect" coords="483,7,557,91">
                                                          <area href="javascript:choosetype('57');" shape="rect" coords="563,6,637,90">
                                                            <area href="javascript:choosetype('58');" shape="rect" coords="641,6,716,91">
                                                              <area href="javascript:choosetype('59');" shape="rect" coords="722,8,795,90">
                                                                <area href="javascript:choosetype('60');" shape="rect" coords="8,98,81,182">
                                                                  <area href="javascript:choosetype('61');" shape="rect" coords="87,99,160,183">
                                                                    <area href="javascript:choosetype('62');" shape="rect" coords="167,99,240,185">
                                                                      <area href="javascript:choosetype('63');" shape="rect" coords="245,98,319,182">
                                                                        <area href="javascript:choosetype('64');" shape="rect" coords="325,98,398,184">
                                                                          <area href="javascript:choosetype('65');" shape="rect" coords="405,98,478,182">
                                                                            <area href="javascript:choosetype('66');" shape="rect" coords="484,97,557,184">
                                                                              <area href="javascript:choosetype('67');" shape="rect" coords="562,98,637,182">
                                                                                <area href="javascript:choosetype('68');" shape="rect" coords="642,97,716,182">
                                                                                  <area href="javascript:choosetype('69');" shape="rect" coords="723,96,796,182">
                                                                                    <area href="javascript:choosetype('71');" shape="rect" coords="8,191,82,275">
                                                                                      <area href="javascript:choosetype('72');" shape="rect" coords="86,190,160,276">
                                                                                        <area href="javascript:choosetype('73');" shape="rect" coords="167,191,239,276">
                                                                                          <area href="javascript:choosetype('74');" shape="rect" coords="245,191,319,278">
                                                                                            <area href="javascript:choosetype('75');" shape="rect" coords="324,192,397,276">
                                                                                              <area href="javascript:choosetype('81');" shape="rect" coords="405,192,507,277">
                                                                                                <area href="javascript:choosetype('82');" shape="rect" coords="510,191,636,277">
                                                                                                  <area href="javascript:choosetype('83');" shape="rect" coords="641,189,716,276">
                                                                                                    <area href="javascript:choosetype('85');" shape="rect" coords="722,191,795,276">
                                                                                                      <area href="javascript:choosetype('89');" shape="rect" coords="6,283,82,369">
                                                                                                        <area href="javascript:choosetype('90');" shape="rect" coords="88,284,152,368">
                                                                                                          <area href="javascript:choosetype('92');" shape="rect" coords="157,283,229,370">
                                                                                                            <area href="javascript:choosetype('93');" shape="rect" coords="233,283,313,368">
                                                                                                              <area href="javascript:choosetype('94');" shape="rect" coords="318,282,393,369">
                                                                                                               <area href="javascript:choosetype('95');" shape="rect" coords="398,283,465,368">
                                                                                                                <area href="javascript:choosetype('96');" shape="rect" coords="469,283,536,369">
                                                                                                                 <area href="javascript:choosetype('97');" shape="rect" coords="540,284,615,369">
                                                                                                                  <area href="javascript:choosetype('98');" shape="rect" coords="620,284,693,368">
                                                                                                                   <area href="javascript:choosetype('99');" shape="rect" coords="697,284,795,370">
                                                                                                                </map>
                                                                                                                <map name="FPMap2">
                                                                                                                  <area href="javascript:choosetype('WT');" shape="rect" coords="3,11,62,50">
                                                                                                                   <area href="javascript:choosetype('BK');" shape="rect" coords="70,12,130,51">
                                                                                                                    <area href="javascript:choosetype('GY');" shape="rect" coords="137,12,197,50">
                                                                                                                     <area href="javascript:choosetype('BU');" shape="rect" coords="202,14,262,49">
                                                                                                                      <area href="javascript:choosetype('PU');" shape="rect" coords="271,10,330,52">
                                                                                                                       <area href="javascript:choosetype('RD');" shape="rect" coords="338,13,399,50">
                                                                                                                        <area href="javascript:choosetype('YW');" shape="rect" coords="405,14,466,49">
                                                                                                                         <area href="javascript:choosetype('BE');" shape="rect" coords="473,13,534,50">
                                                                                                                          <area href="javascript:choosetype('BN');" shape="rect" coords="538,13,599,52">
                                                                                                                           <area href="javascript:choosetype('GN');" shape="rect" coords="606,12,666,50">
                                                                                                                            <area href="javascript:choosetype('MC');" shape="rect" coords="673,13,732,50">
                                                                                                                             <area href="javascript:choosetype('PR');" shape="rect" coords="739,15,799,52">

                                                                                                                     </map>
                                                                                                                <map name="FPMap3">
                                                                                                                  <area href="javascript:choosexdescelement('D');" shape="rect" coords="7,12,52,71">
                                                                                                                   <area href="javascript:choosexdescelement('L');" shape="rect" coords="56,15,102,72">
                                                                                                                    <area href="javascript:choosexdescelement('M');" shape="rect" coords="106,14,151,70">
                                                                                                                     <area href="javascript:choosexdescelement('R');" shape="rect" coords="153,14,200,71">
                                                                                                                      <area href="javascript:choosexdescelement('T');" shape="rect" coords="202,14,249,70">
                                                                                                                       <area href="javascript:choosexdescelement('B');" shape="rect" coords="271,13,315,71">
                                                                                                                        <area href="javascript:choosexdescelement('K');" shape="rect" coords="320,14,398,72">
                                                                                                                         <area href="javascript:choosexdescelement('C');" shape="rect" coords="424,15,502,69">
                                                                                                                          <area href="javascript:choosexdescelement('H');" shape="rect" coords="507,14,583,71">
                                                                                                                           <area href="javascript:choosexdescelement('S');" shape="rect" coords="587,14,663,70">
                                                                                                                            <area href="javascript:choosexdescelement('W');" shape="rect" coords="668,15,744,72">
                                                                                                                             <area href="javascript:choosexdescelement('X');" shape="rect" coords="749,15,793,70">

                                                                                                                     </map>
</head>
<body marginwidth="0" marginheight="0" leftmargin="0"  topmargin="0" onblur=focus()>
<%

if (
        request.getParameter("charttype")!= null && 
        request.getParameter("charttype").equals("2")) {
%>
  <img src="../../deployment/main/images/nettracer/charts/IATA_BAG2.JPG" border=0 usemap="#FPMap1"><%
} 
else if (
        request.getParameter("charttype")!= null && 
        request.getParameter("charttype").equals("1")) {
%>
  <img src="../../deployment/main/images/nettracer/charts/IATA_BAG1.JPG" border=0 usemap="#FPMap0"><%
}
else if (
        request.getParameter("charttype")!= null && 
        request.getParameter("charttype").equals("3")){
%>
  <img src="../../deployment/main/images/nettracer/charts/IATA_COLOR.JPG" border=0 usemap="#FPMap2"><%
}
else if (
        request.getParameter("charttype")!= null && 
        request.getParameter("charttype").equals("4")){
%>
  <img src="../../deployment/main/images/nettracer/charts/IATA_XDESC.JPG" border=0 usemap="#FPMap3">
<%
} else {
%>
<img src="../../deployment/main/images/nettracer/charts/IATA_BAG1.JPG" border=0 usemap="#FPMap0">
<%
}
%>
</body>
</html>
