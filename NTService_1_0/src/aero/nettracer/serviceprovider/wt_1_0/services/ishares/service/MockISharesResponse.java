package aero.nettracer.serviceprovider.wt_1_0.services.ishares.service;

import java.util.regex.Matcher;

public class MockISharesResponse {

	public static int currentAfResponse = 0;

	public static String mockGetActionFileCountsCommand(String command)
			throws CommandNotProperlyFormedException {
		testCommand(command);
		StringBuffer sb = new StringBuffer();
		sb.append("<HTML\n");
		sb.append("><HEAD>\n");
		sb
				.append("<title>Web Terminal for NIAL MCLOUGHLIN using LNIATA D6BD01 (SHARES B production system . )</title><META http-equiv=msthemecompatible content=no></HEAD>\n");
		sb.append(" <SCRIPT>\n");
		sb.append("<!--\n");
		sb.append("function sf(){document.gs.q.focus();\n");
		sb.append("document.execCommand(\"Overwrite\");}\n");
		sb.append("function poponload()\n");
		sb.append("{\n");
		sb
				.append("testwindow= window.open (\"/cgi-bin/bpass.cgi?Token=EQUIPAJEUS4AFC582B000EA048&\",\"mywindow\",\"location=1,status=1,scrollbars=1,width=1000,menubar=1,left=0,top=400\");\n");
		sb.append("}\n");
		sb.append("function CheckKey(msg,el) {\n");
		sb
				.append("	if ((document.all.q) && (220==event.keyCode) && (event.shiftKey)){}\n");
		sb.append("  else {\n");
		sb.append("	if ((document.all.q) && (13==event.keyCode) ) {\n");
		sb.append("		el.selection=document.selection.createRange();\n");
		sb.append("		setTimeout(\"ProcessEnter('\" + el.id + \"')\",0)}\n");
		sb.append("	if ((document.all.q) && (17==event.keyCode) ) {\n");
		sb.append("		el.selection=document.selection.createRange();\n");
		sb.append("		setTimeout(\"ProcessCTL('\" + el.id + \"')\",0)}\n");
		sb.append("	if ((document.all.q) && (220==event.keyCode) ) {\n");
		sb.append("		el.selection=document.selection.createRange();\n");
		sb.append("		setTimeout(\"ProcessBackSlash('\" + el.id + \"')\",0)}\n");
		sb.append("	if ((document.all.q) && (45==event.keyCode) ) {\n");
		sb.append("	   document.execCommand(\"Overwrite\");\n");
		sb.append("      document.execCommand(\"Overwrite\");}\n");
		sb.append("if (event.keyCode==123)\n");
		sb.append("  {\n");
		sb.append("var x,y,i,z,j,m;\n");
		sb.append("m=0;\n");
		sb.append("y=0;\n");
		sb.append("  while(1)\n");
		sb.append("  {\n");
		sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
		sb.append("   {\n");
		sb.append("    m++;\n");
		sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
		sb.append("   }\n");
		sb.append("   else\n");
		sb.append("     break;\n");
		sb.append("  }\n");
		sb.append("x=caretPos(msg)-m+1;\n");
		sb.append("j=0;\n");
		sb.append("m=0;\n");
		sb.append("y=0;\n");
		sb.append("  while(j<x)\n");
		sb.append("  {\n");
		sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
		sb.append("   {\n");
		sb.append("    m++;\n");
		sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
		sb.append("     if ( y>x)\n");
		sb.append("        m--;\n");
		sb.append("   }\n");
		sb.append("   else\n");
		sb.append("     break;\n");
		sb.append("  }\n");
		sb.append("i=0;\n");
		sb.append(" if (msg.value.indexOf(\":\",x+1+m)!=-1)\n");
		sb.append(" {\n");
		sb.append("  z=msg.value.indexOf(\":\",x+1+m)+1;\n");
		sb.append("  y=0;\n");
		sb.append("  while(y<z)\n");
		sb.append("  {\n");
		sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
		sb.append("   {\n");
		sb.append("    i++;\n");
		sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
		sb.append("     if ( y>z)\n");
		sb.append("        i--;\n");
		sb.append("   }\n");
		sb.append("   else\n");
		sb.append("     break;\n");
		sb.append("  }\n");
		sb.append("  k=z-i;\n");
		sb.append("  }\n");
		sb.append("  else\n");
		sb.append("     k=0;\n");
		sb.append("x=k;\n");
		sb.append("if(el.setSelectionRange) {\n");
		sb.append("el.focus(); \n");
		sb.append("el.setSelectionRange(x,1); \n");
		sb.append("} \n");
		sb.append("else { \n");
		sb.append("if(el.createTextRange) { \n");
		sb.append("range=el.createTextRange(); \n");
		sb.append("range.collapse(true); \n");
		sb.append("range.moveEnd('character',1); \n");
		sb.append("range.moveStart('character',x); \n");
		sb.append("range.select(); \n");
		sb.append("return false; \n");
		sb.append("} \n");
		sb.append("return false;\n");
		sb.append("} \n");
		sb.append("return false; \n");
		sb.append("} \n");
		sb.append("function caretPos(msg){\n");
		sb.append("var i=msg.value.length+1;\n");
		sb.append("if (msg.createTextRange){\n");
		sb.append("theCaret = document.selection.createRange().duplicate();\n");
		sb.append("while ( theCaret.parentElement() == msg\n");
		sb.append("&& theCaret.move(\"character\",1)==1 ) --i;\n");
		sb.append("}\n");
		sb.append("return i==msg.value.length+1?-1:i;\n");
		sb.append("}\n");
		sb.append("}\n");
		sb.append("} \n");
		sb.append("function ProcessEnter(id)     \n");
		sb.append("{document.all.q.selection.text=String.fromCharCode(0)\n");
		sb.append("document.gs.submit()}\n");
		sb.append("function ProcessCTL(id)     \n");
		sb.append("{}\n");
		sb.append("function ProcessBackSlash(id)\n");
		sb.append("{sf()\n");
		sb.append("document.all.q.selection.text=String.fromCharCode(13)}\n");
		sb.append("</SCRIPT>\n");
		sb.append("<body onload=\"sf()\">\n");
		sb
				.append("<TABLE width=\"100%\" cellSpacing=0 cellPadding=0 border=0>\n");
		sb.append("<thead><tr>	<th>\n");
		sb
				.append("<TABLE align=\"left\" cellSpacing=0 cellPadding=0 border=0>\n");
		sb.append("<TBODY>	<TR><TD valign=\"top\">\n");
		sb.append("<img border=0 src=/company.gif></TD>\n");
		sb.append("<TD>&nbsp;&nbsp;</TD><TD valign=\"bottom\">\n");
		sb.append("<FORM name=gs action=/cgi-bin/term.cgi method=post>\n");
		sb
				.append("<input type=hidden name=Token value=EQUIPAJEUS4AFC582B000EA048>\n");
		sb.append("<input type=hidden name=Type value=24>\n");
		sb
				.append("<textarea onkeydown=CheckKey(this,document.getElementById('q')) rows=24 name=q cols=64></textarea><input type=submit value=Send name=btnG style=arial>\n");
		sb
				.append("<input type=reset value=Reset name=B1 style=arial></FORM></TD>\n");
		sb.append("</TR>	</TBODY></TABLE></th>	</tr><tr>	<th>\n");
		sb.append("<hr color=\"#3366CC\"></th></tr></thead>\n");
		sb.append("<tbody><tr>	<td>\n");
		sb
				.append("<font face= \" courier \" ><PRE>MANAGEMENT ACTION FILE - STATION ATLUS - DATE 12NOV \n");
		sb.append("AREA  D1    D2    D3    D4    D5    D6    D7 \n");
		sb.append("FW     0     0     0     0     0     1     0 \n");
		sb.append("AA     3     2     0     0     2     0     0 \n");
		sb.append("WM     0     0     0     0     1     1     0 \n");
		sb.append("SP     0     1     0     3     2     0     1 \n");
		sb.append("AP     1     0     2     0     0     0     1 \n");
		sb.append("END OF REPORT \n");
		sb
				.append("</PRE></td></tr></tbody><tfoot><tr><td><hr color=\"#3366CC\">\n");
		sb.append("</td>	</tr></tfoot></TABLE>\n");
		sb.append(" <td>\n");
		sb
				.append("  <table  style=\"border-collapse:collapse;border-width:0\" width=100%>\n");
		sb.append("    <tr>\n");
		sb
				.append("     <td width=40% style=\"border-style: none; border-width: medium\">\n");
		sb
				.append("      <form name=ls method=POST action=/cgi-bin/logoff.cgi>\n");
		sb
				.append("       <input type=hidden name=Token value=EQUIPAJEUS4AFC582B000EA048>\n");
		sb.append("       <p align=right>\n");
		sb
				.append("       <input type=submit value=Logoff name=B1 style=font-family:Arial>\n");
		sb.append("       </p>\n");
		sb.append("      </form>\n");
		sb.append("     </td>\n");
		sb
				.append("     <td width=10% style=\"border-style: none; border-width: medium\">\n");
		sb
				.append("      <form name=ls method=POST action=/cgi-bin/login.cgi>\n");
		sb
				.append("       <input type=hidden name=Token value=EQUIPAJEUS4AFC582B000EA048>\n");
		sb.append("       <p align=left>\n");
		sb
				.append("       <input type=submit value=Menu name=B1 style=font-family:Arial>\n");
		sb.append("       </p>\n");
		sb.append("      </form>\n");
		sb.append("     </td>\n");
		sb
				.append("      <td width=15% style=\"border-style: none; border-width: medium\">\n");
		sb
				.append("       <form name=ls method=POST action=/cgi-bin/term.cgi>\n");
		sb
				.append("       <input type=hidden name=Token value=EQUIPAJEUS4AFC582B000EA048>\n");
		sb.append("       <p align=left>\n");
		sb
				.append("       <INPUT type=submit value=\"Form size\" name=b style=arial>\n");
		sb.append("       <select size=1 name=Type>\n");
		sb.append("       <option>1</option>\n");
		sb.append("       <option>2</option>\n");
		sb.append("       <option>4</option>\n");
		sb.append("       <option>12</option>\n");
		sb.append("       <option>24</option>\n");
		sb.append("       <option>255</option>\n");
		sb.append("       </select>\n");
		sb.append("       </p>\n");
		sb.append("       </form>\n");
		sb.append("      </td>\n");
		sb
				.append("      <td width=35% style=\"border-style: none; border-width: medium\">\n");
		sb
				.append("       <form name=ls method=POST action=/cgi-bin/term.cgi>\n");
		sb
				.append("       <input type=hidden name=Token value=EQUIPAJEUS4AFC582B000EA048>\n");
		sb.append("       <p align=left>\n");
		sb
				.append("       <input type=submit value=Privacy name=Privacy style=Arial>\n");
		sb.append("       </p>\n");
		sb.append("       </form>\n");
		sb.append("      </td>\n");
		sb.append("      </tr>\n");
		sb.append("    </table>\n");
		sb.append("    </font></td>\n");
		sb.append("</body></html>\n");

		Matcher m = WorldTracerServiceImpl.commandResponsePattern.matcher(sb
				.toString());
		if (m.find()) {
			return m.group(1);
		}
		return null;

	}

	private static String testCommand(String command)
			throws CommandNotProperlyFormedException {
		if (command.contains("{") || command.contains("}")) {
			System.out.println("Command Failure: \n" + command);
			throw new CommandNotProperlyFormedException();
		}
		command = command.replaceAll("\\[.*\\]", "");
		return command;
	}

	public static String mockPxfCommand(String command)
			throws CommandNotProperlyFormedException {

		command = testCommand(command);
		System.out.println("Command: \n" + command);
		StringBuffer sb = new StringBuffer();

		sb.append("<HTML\n");
		sb.append("><HEAD>\n");
		sb
				.append("<title>Web Terminal for NIAL MCLOUGHLIN using LNIATA D6BD02 (SHARES B production system . )</title><META http-equiv=msthemecompatible content=no></HEAD>\n");
		sb.append(" <SCRIPT>\n");
		sb.append("<!--\n");
		sb.append("function sf(){document.gs.q.focus();\n");
		sb.append("document.execCommand(\"Overwrite\");}\n");
		sb.append("function poponload()\n");
		sb.append("{\n");
		sb
				.append("testwindow= window.open (\"/cgi-bin/bpass.cgi?Token=EQUIPAJEUS4B01C2FD000A8EBF&\",\"mywindow\",\"location=1,status=1,scrollbars=1,width=1000,menubar=1,left=0,top=400\");\n");
		sb.append("}\n");
		sb.append("function CheckKey(msg,el) {\n");
		sb
				.append("	if ((document.all.q) && (220==event.keyCode) && (event.shiftKey)){}\n");
		sb.append("  else {\n");
		sb.append("	if ((document.all.q) && (13==event.keyCode) ) {\n");
		sb.append("		el.selection=document.selection.createRange();\n");
		sb.append("		setTimeout(\"ProcessEnter('\" + el.id + \"')\",0)}\n");
		sb.append("	if ((document.all.q) && (17==event.keyCode) ) {\n");
		sb.append("		el.selection=document.selection.createRange();\n");
		sb.append("		setTimeout(\"ProcessCTL('\" + el.id + \"')\",0)}\n");
		sb.append("	if ((document.all.q) && (220==event.keyCode) ) {\n");
		sb.append("		el.selection=document.selection.createRange();\n");
		sb.append("		setTimeout(\"ProcessBackSlash('\" + el.id + \"')\",0)}\n");
		sb.append("	if ((document.all.q) && (45==event.keyCode) ) {\n");
		sb.append("	   document.execCommand(\"Overwrite\");\n");
		sb.append("      document.execCommand(\"Overwrite\");}\n");
		sb.append("if (event.keyCode==123)\n");
		sb.append("  {\n");
		sb.append("var x,y,i,z,j,m;\n");
		sb.append("m=0;\n");
		sb.append("y=0;\n");
		sb.append("  while(1)\n");
		sb.append("  {\n");
		sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
		sb.append("   {\n");
		sb.append("    m++;\n");
		sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
		sb.append("   }\n");
		sb.append("   else\n");
		sb.append("     break;\n");
		sb.append("  }\n");
		sb.append("x=caretPos(msg)-m+1;\n");
		sb.append("j=0;\n");
		sb.append("m=0;\n");
		sb.append("y=0;\n");
		sb.append("  while(j<x)\n");
		sb.append("  {\n");
		sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
		sb.append("   {\n");
		sb.append("    m++;\n");
		sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
		sb.append("     if ( y>x)\n");
		sb.append("        m--;\n");
		sb.append("   }\n");
		sb.append("   else\n");
		sb.append("     break;\n");
		sb.append("  }\n");
		sb.append("i=0;\n");
		sb.append(" if (msg.value.indexOf(\":\",x+1+m)!=-1)\n");
		sb.append(" {\n");
		sb.append("  z=msg.value.indexOf(\":\",x+1+m)+1;\n");
		sb.append("  y=0;\n");
		sb.append("  while(y<z)\n");
		sb.append("  {\n");
		sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
		sb.append("   {\n");
		sb.append("    i++;\n");
		sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
		sb.append("     if ( y>z)\n");
		sb.append("        i--;\n");
		sb.append("   }\n");
		sb.append("   else\n");
		sb.append("     break;\n");
		sb.append("  }\n");
		sb.append("  k=z-i;\n");
		sb.append("  }\n");
		sb.append("  else\n");
		sb.append("     k=0;\n");
		sb.append("x=k;\n");
		sb.append("if(el.setSelectionRange) {\n");
		sb.append("el.focus(); \n");
		sb.append("el.setSelectionRange(x,1); \n");
		sb.append("} \n");
		sb.append("else { \n");
		sb.append("if(el.createTextRange) { \n");
		sb.append("range=el.createTextRange(); \n");
		sb.append("range.collapse(true); \n");
		sb.append("range.moveEnd('character',1); \n");
		sb.append("range.moveStart('character',x); \n");
		sb.append("range.select(); \n");
		sb.append("return false; \n");
		sb.append("} \n");
		sb.append("return false;\n");
		sb.append("} \n");
		sb.append("return false; \n");
		sb.append("} \n");
		sb.append("function caretPos(msg){\n");
		sb.append("var i=msg.value.length+1;\n");
		sb.append("if (msg.createTextRange){\n");
		sb.append("theCaret = document.selection.createRange().duplicate();\n");
		sb.append("while ( theCaret.parentElement() == msg\n");
		sb.append("&& theCaret.move(\"character\",1)==1 ) --i;\n");
		sb.append("}\n");
		sb.append("return i==msg.value.length+1?-1:i;\n");
		sb.append("}\n");
		sb.append("}\n");
		sb.append("} \n");
		sb.append("function ProcessEnter(id)     \n");
		sb.append("{document.all.q.selection.text=String.fromCharCode(0)\n");
		sb.append("document.gs.submit()}\n");
		sb.append("function ProcessCTL(id)     \n");
		sb.append("{}\n");
		sb.append("function ProcessBackSlash(id)\n");
		sb.append("{sf()\n");
		sb.append("document.all.q.selection.text=String.fromCharCode(13)}\n");
		sb.append("</SCRIPT>\n");
		sb.append("<body onload=\"sf()\">\n");
		sb
				.append("<TABLE width=\"100%\" cellSpacing=0 cellPadding=0 border=0>\n");
		sb.append("<thead><tr>	<th>\n");
		sb
				.append("<TABLE align=\"left\" cellSpacing=0 cellPadding=0 border=0>\n");
		sb.append("<TBODY>	<TR><TD valign=\"top\">\n");
		sb.append("<img border=0 src=/company.gif></TD>\n");
		sb.append("<TD>&nbsp;&nbsp;</TD><TD valign=\"bottom\">\n");
		sb.append("<FORM name=gs action=/cgi-bin/term.cgi method=post>\n");
		sb
				.append("<input type=hidden name=Token value=EQUIPAJEUS4B01C2FD000A8EBF>\n");
		sb.append("<input type=hidden name=Type value=24>\n");
		sb
				.append("<textarea onkeydown=CheckKey(this,document.getElementById('q')) rows=24 name=q cols=64>WM PXF XAXUSAP                            /-OK-/16NOV09 2128GMT</textarea><input type=submit value=Send name=btnG style=arial>\n");
		sb
				.append("<input type=reset value=Reset name=B1 style=arial></FORM></TD>\n");
		sb.append("</TR>	</TBODY></TABLE></th>	</tr><tr>	<th>\n");
		sb.append("<hr color=\"#3366CC\"></th></tr></thead>\n");
		sb.append("<tbody><tr>	<td>\n");
		sb
				.append("<font face= \" courier \" ><PRE>&GTWM PXF XAXUSAP                            /-OK-/16NOV09 2128GMT \n");
		sb
				.append("</PRE></td></tr></tbody><tfoot><tr><td><hr color=\"#3366CC\">\n");
		sb.append("</td>	</tr></tfoot></TABLE>\n");
		sb.append(" <td>\n");
		sb
				.append("  <table  style=\"border-collapse:collapse;border-width:0\" width=100%>\n");
		sb.append("    <tr>\n");
		sb
				.append("     <td width=40% style=\"border-style: none; border-width: medium\">\n");
		sb
				.append("      <form name=ls method=POST action=/cgi-bin/logoff.cgi>\n");
		sb
				.append("       <input type=hidden name=Token value=EQUIPAJEUS4B01C2FD000A8EBF>\n");
		sb.append("       <p align=right>\n");
		sb
				.append("       <input type=submit value=Logoff name=B1 style=font-family:Arial>\n");
		sb.append("       </p>\n");
		sb.append("      </form>\n");
		sb.append("     </td>\n");
		sb
				.append("     <td width=10% style=\"border-style: none; border-width: medium\">\n");
		sb
				.append("      <form name=ls method=POST action=/cgi-bin/login.cgi>\n");
		sb
				.append("       <input type=hidden name=Token value=EQUIPAJEUS4B01C2FD000A8EBF>\n");
		sb.append("       <p align=left>\n");
		sb
				.append("       <input type=submit value=Menu name=B1 style=font-family:Arial>\n");
		sb.append("       </p>\n");
		sb.append("      </form>\n");
		sb.append("     </td>\n");
		sb
				.append("      <td width=15% style=\"border-style: none; border-width: medium\">\n");
		sb
				.append("       <form name=ls method=POST action=/cgi-bin/term.cgi>\n");
		sb
				.append("       <input type=hidden name=Token value=EQUIPAJEUS4B01C2FD000A8EBF>\n");
		sb.append("       <p align=left>\n");
		sb
				.append("       <INPUT type=submit value=\"Form size\" name=b style=arial>\n");
		sb.append("       <select size=1 name=Type>\n");
		sb.append("       <option>1</option>\n");
		sb.append("       <option>2</option>\n");
		sb.append("       <option>4</option>\n");
		sb.append("       <option>12</option>\n");
		sb.append("       <option>24</option>\n");
		sb.append("       <option>255</option>\n");
		sb.append("       </select>\n");
		sb.append("       </p>\n");
		sb.append("       </form>\n");
		sb.append("      </td>\n");
		sb
				.append("      <td width=35% style=\"border-style: none; border-width: medium\">\n");
		sb
				.append("       <form name=ls method=POST action=/cgi-bin/term.cgi>\n");
		sb
				.append("       <input type=hidden name=Token value=EQUIPAJEUS4B01C2FD000A8EBF>\n");
		sb.append("       <p align=left>\n");
		sb
				.append("       <input type=submit value=Privacy name=Privacy style=Arial>\n");
		sb.append("       </p>\n");
		sb.append("       </form>\n");
		sb.append("      </td>\n");
		sb.append("      </tr>\n");
		sb.append("    </table>\n");
		sb.append("    </font></td>\n");
		sb.append("</body></html>\n");
		sb.append("\n");

		Matcher m = WorldTracerServiceImpl.commandResponsePattern.matcher(sb
				.toString());
		if (m.find()) {
			return m.group(1);
		}
		return null;

	}

	public static String mockExfSuccessCommand(String command)
			throws CommandNotProperlyFormedException {
		command = testCommand(command);
		System.out.println("Command: \n" + command);
		StringBuffer sb = new StringBuffer();

		sb.append("<HTML\n");
		sb.append("><HEAD>\n");
		sb
				.append("<title>Web Terminal for NIAL MCLOUGHLIN using LNIATA D6BD02 (SHARES B production system . )</title><META http-equiv=msthemecompatible content=no></HEAD>\n");
		sb.append(" <SCRIPT>\n");
		sb.append("<!--\n");
		sb.append("function sf(){document.gs.q.focus();\n");
		sb.append("document.execCommand(\"Overwrite\");}\n");
		sb.append("function poponload()\n");
		sb.append("{\n");
		sb
				.append("testwindow= window.open (\"/cgi-bin/bpass.cgi?Token=EQUIPAJEUS4B01C2FD000A8EBF&\",\"mywindow\",\"location=1,status=1,scrollbars=1,width=1000,menubar=1,left=0,top=400\");\n");
		sb.append("}\n");
		sb.append("function CheckKey(msg,el) {\n");
		sb
				.append("	if ((document.all.q) && (220==event.keyCode) && (event.shiftKey)){}\n");
		sb.append("  else {\n");
		sb.append("	if ((document.all.q) && (13==event.keyCode) ) {\n");
		sb.append("		el.selection=document.selection.createRange();\n");
		sb.append("		setTimeout(\"ProcessEnter('\" + el.id + \"')\",0)}\n");
		sb.append("	if ((document.all.q) && (17==event.keyCode) ) {\n");
		sb.append("		el.selection=document.selection.createRange();\n");
		sb.append("		setTimeout(\"ProcessCTL('\" + el.id + \"')\",0)}\n");
		sb.append("	if ((document.all.q) && (220==event.keyCode) ) {\n");
		sb.append("		el.selection=document.selection.createRange();\n");
		sb.append("		setTimeout(\"ProcessBackSlash('\" + el.id + \"')\",0)}\n");
		sb.append("	if ((document.all.q) && (45==event.keyCode) ) {\n");
		sb.append("	   document.execCommand(\"Overwrite\");\n");
		sb.append("      document.execCommand(\"Overwrite\");}\n");
		sb.append("if (event.keyCode==123)\n");
		sb.append("  {\n");
		sb.append("var x,y,i,z,j,m;\n");
		sb.append("m=0;\n");
		sb.append("y=0;\n");
		sb.append("  while(1)\n");
		sb.append("  {\n");
		sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
		sb.append("   {\n");
		sb.append("    m++;\n");
		sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
		sb.append("   }\n");
		sb.append("   else\n");
		sb.append("     break;\n");
		sb.append("  }\n");
		sb.append("x=caretPos(msg)-m+1;\n");
		sb.append("j=0;\n");
		sb.append("m=0;\n");
		sb.append("y=0;\n");
		sb.append("  while(j<x)\n");
		sb.append("  {\n");
		sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
		sb.append("   {\n");
		sb.append("    m++;\n");
		sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
		sb.append("     if ( y>x)\n");
		sb.append("        m--;\n");
		sb.append("   }\n");
		sb.append("   else\n");
		sb.append("     break;\n");
		sb.append("  }\n");
		sb.append("i=0;\n");
		sb.append(" if (msg.value.indexOf(\":\",x+1+m)!=-1)\n");
		sb.append(" {\n");
		sb.append("  z=msg.value.indexOf(\":\",x+1+m)+1;\n");
		sb.append("  y=0;\n");
		sb.append("  while(y<z)\n");
		sb.append("  {\n");
		sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
		sb.append("   {\n");
		sb.append("    i++;\n");
		sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
		sb.append("     if ( y>z)\n");
		sb.append("        i--;\n");
		sb.append("   }\n");
		sb.append("   else\n");
		sb.append("     break;\n");
		sb.append("  }\n");
		sb.append("  k=z-i;\n");
		sb.append("  }\n");
		sb.append("  else\n");
		sb.append("     k=0;\n");
		sb.append("x=k;\n");
		sb.append("if(el.setSelectionRange) {\n");
		sb.append("el.focus(); \n");
		sb.append("el.setSelectionRange(x,1); \n");
		sb.append("} \n");
		sb.append("else { \n");
		sb.append("if(el.createTextRange) { \n");
		sb.append("range=el.createTextRange(); \n");
		sb.append("range.collapse(true); \n");
		sb.append("range.moveEnd('character',1); \n");
		sb.append("range.moveStart('character',x); \n");
		sb.append("range.select(); \n");
		sb.append("return false; \n");
		sb.append("} \n");
		sb.append("return false;\n");
		sb.append("} \n");
		sb.append("return false; \n");
		sb.append("} \n");
		sb.append("function caretPos(msg){\n");
		sb.append("var i=msg.value.length+1;\n");
		sb.append("if (msg.createTextRange){\n");
		sb.append("theCaret = document.selection.createRange().duplicate();\n");
		sb.append("while ( theCaret.parentElement() == msg\n");
		sb.append("&& theCaret.move(\"character\",1)==1 ) --i;\n");
		sb.append("}\n");
		sb.append("return i==msg.value.length+1?-1:i;\n");
		sb.append("}\n");
		sb.append("}\n");
		sb.append("} \n");
		sb.append("function ProcessEnter(id)     \n");
		sb.append("{document.all.q.selection.text=String.fromCharCode(0)\n");
		sb.append("document.gs.submit()}\n");
		sb.append("function ProcessCTL(id)     \n");
		sb.append("{}\n");
		sb.append("function ProcessBackSlash(id)\n");
		sb.append("{sf()\n");
		sb.append("document.all.q.selection.text=String.fromCharCode(13)}\n");
		sb.append("</SCRIPT>\n");
		sb.append("<body onload=\"sf()\">\n");
		sb
				.append("<TABLE width=\"100%\" cellSpacing=0 cellPadding=0 border=0>\n");
		sb.append("<thead><tr>	<th>\n");
		sb
				.append("<TABLE align=\"left\" cellSpacing=0 cellPadding=0 border=0>\n");
		sb.append("<TBODY>	<TR><TD valign=\"top\">\n");
		sb.append("<img border=0 src=/company.gif></TD>\n");
		sb.append("<TD>&nbsp;&nbsp;</TD><TD valign=\"bottom\">\n");
		sb.append("<FORM name=gs action=/cgi-bin/term.cgi method=post>\n");
		sb
				.append("<input type=hidden name=Token value=EQUIPAJEUS4B01C2FD000A8EBF>\n");
		sb.append("<input type=hidden name=Type value=24>\n");
		sb
				.append("<textarea onkeydown=CheckKey(this,document.getElementById('q')) rows=24 name=q cols=64>WM EXF XAXUS AP D1/1                        /-OK-/</textarea><input type=submit value=Send name=btnG style=arial>\n");
		sb
				.append("<input type=reset value=Reset name=B1 style=arial></FORM></TD>\n");
		sb.append("</TR>	</TBODY></TABLE></th>	</tr><tr>	<th>\n");
		sb.append("<hr color=\"#3366CC\"></th></tr></thead>\n");
		sb.append("<tbody><tr>	<td>\n");
		sb
				.append("<font face= \" courier \" ><PRE>&GTWM EXF XAXUS AP D1/1                        /-OK-/ \n");
		sb
				.append("</PRE></td></tr></tbody><tfoot><tr><td><hr color=\"#3366CC\">\n");
		sb.append("</td>	</tr></tfoot></TABLE>\n");
		sb.append(" <td>\n");
		sb
				.append("  <table  style=\"border-collapse:collapse;border-width:0\" width=100%>\n");
		sb.append("    <tr>\n");
		sb
				.append("     <td width=40% style=\"border-style: none; border-width: medium\">\n");
		sb
				.append("      <form name=ls method=POST action=/cgi-bin/logoff.cgi>\n");
		sb
				.append("       <input type=hidden name=Token value=EQUIPAJEUS4B01C2FD000A8EBF>\n");
		sb.append("       <p align=right>\n");
		sb
				.append("       <input type=submit value=Logoff name=B1 style=font-family:Arial>\n");
		sb.append("       </p>\n");
		sb.append("      </form>\n");
		sb.append("     </td>\n");
		sb
				.append("     <td width=10% style=\"border-style: none; border-width: medium\">\n");
		sb
				.append("      <form name=ls method=POST action=/cgi-bin/login.cgi>\n");
		sb
				.append("       <input type=hidden name=Token value=EQUIPAJEUS4B01C2FD000A8EBF>\n");
		sb.append("       <p align=left>\n");
		sb
				.append("       <input type=submit value=Menu name=B1 style=font-family:Arial>\n");
		sb.append("       </p>\n");
		sb.append("      </form>\n");
		sb.append("     </td>\n");
		sb
				.append("      <td width=15% style=\"border-style: none; border-width: medium\">\n");
		sb
				.append("       <form name=ls method=POST action=/cgi-bin/term.cgi>\n");
		sb
				.append("       <input type=hidden name=Token value=EQUIPAJEUS4B01C2FD000A8EBF>\n");
		sb.append("       <p align=left>\n");
		sb
				.append("       <INPUT type=submit value=\"Form size\" name=b style=arial>\n");
		sb.append("       <select size=1 name=Type>\n");
		sb.append("       <option>1</option>\n");
		sb.append("       <option>2</option>\n");
		sb.append("       <option>4</option>\n");
		sb.append("       <option>12</option>\n");
		sb.append("       <option>24</option>\n");
		sb.append("       <option>255</option>\n");
		sb.append("       </select>\n");
		sb.append("       </p>\n");
		sb.append("       </form>\n");
		sb.append("      </td>\n");
		sb
				.append("      <td width=35% style=\"border-style: none; border-width: medium\">\n");
		sb
				.append("       <form name=ls method=POST action=/cgi-bin/term.cgi>\n");
		sb
				.append("       <input type=hidden name=Token value=EQUIPAJEUS4B01C2FD000A8EBF>\n");
		sb.append("       <p align=left>\n");
		sb
				.append("       <input type=submit value=Privacy name=Privacy style=Arial>\n");
		sb.append("       </p>\n");
		sb.append("       </form>\n");
		sb.append("      </td>\n");
		sb.append("      </tr>\n");
		sb.append("    </table>\n");
		sb.append("    </font></td>\n");
		sb.append("</body></html>\n");
		sb.append("\n");

		Matcher m = WorldTracerServiceImpl.commandResponsePattern.matcher(sb
				.toString());
		if (m.find()) {
			return m.group(1);
		}
		return null;
	}

	public static String mockExfFailureCommand(String command)
			throws CommandNotProperlyFormedException {
		command = testCommand(command);
		System.out.println("Command: \n" + command);
		StringBuffer sb = new StringBuffer();

		sb.append("<HTML\n");
		sb.append("><HEAD>\n");
		sb
				.append("<title>Web Terminal for NIAL MCLOUGHLIN using LNIATA D6BD02 (SHARES B production system . )</title><META http-equiv=msthemecompatible content=no></HEAD>\n");
		sb.append(" <SCRIPT>\n");
		sb.append("<!--\n");
		sb.append("function sf(){document.gs.q.focus();\n");
		sb.append("document.execCommand(\"Overwrite\");}\n");
		sb.append("function poponload()\n");
		sb.append("{\n");
		sb
				.append("testwindow= window.open (\"/cgi-bin/bpass.cgi?Token=EQUIPAJEUS4B01C2FD000A8EBF&\",\"mywindow\",\"location=1,status=1,scrollbars=1,width=1000,menubar=1,left=0,top=400\");\n");
		sb.append("}\n");
		sb.append("function CheckKey(msg,el) {\n");
		sb
				.append("	if ((document.all.q) && (220==event.keyCode) && (event.shiftKey)){}\n");
		sb.append("  else {\n");
		sb.append("	if ((document.all.q) && (13==event.keyCode) ) {\n");
		sb.append("		el.selection=document.selection.createRange();\n");
		sb.append("		setTimeout(\"ProcessEnter('\" + el.id + \"')\",0)}\n");
		sb.append("	if ((document.all.q) && (17==event.keyCode) ) {\n");
		sb.append("		el.selection=document.selection.createRange();\n");
		sb.append("		setTimeout(\"ProcessCTL('\" + el.id + \"')\",0)}\n");
		sb.append("	if ((document.all.q) && (220==event.keyCode) ) {\n");
		sb.append("		el.selection=document.selection.createRange();\n");
		sb.append("		setTimeout(\"ProcessBackSlash('\" + el.id + \"')\",0)}\n");
		sb.append("	if ((document.all.q) && (45==event.keyCode) ) {\n");
		sb.append("	   document.execCommand(\"Overwrite\");\n");
		sb.append("      document.execCommand(\"Overwrite\");}\n");
		sb.append("if (event.keyCode==123)\n");
		sb.append("  {\n");
		sb.append("var x,y,i,z,j,m;\n");
		sb.append("m=0;\n");
		sb.append("y=0;\n");
		sb.append("  while(1)\n");
		sb.append("  {\n");
		sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
		sb.append("   {\n");
		sb.append("    m++;\n");
		sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
		sb.append("   }\n");
		sb.append("   else\n");
		sb.append("     break;\n");
		sb.append("  }\n");
		sb.append("x=caretPos(msg)-m+1;\n");
		sb.append("j=0;\n");
		sb.append("m=0;\n");
		sb.append("y=0;\n");
		sb.append("  while(j<x)\n");
		sb.append("  {\n");
		sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
		sb.append("   {\n");
		sb.append("    m++;\n");
		sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
		sb.append("     if ( y>x)\n");
		sb.append("        m--;\n");
		sb.append("   }\n");
		sb.append("   else\n");
		sb.append("     break;\n");
		sb.append("  }\n");
		sb.append("i=0;\n");
		sb.append(" if (msg.value.indexOf(\":\",x+1+m)!=-1)\n");
		sb.append(" {\n");
		sb.append("  z=msg.value.indexOf(\":\",x+1+m)+1;\n");
		sb.append("  y=0;\n");
		sb.append("  while(y<z)\n");
		sb.append("  {\n");
		sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
		sb.append("   {\n");
		sb.append("    i++;\n");
		sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
		sb.append("     if ( y>z)\n");
		sb.append("        i--;\n");
		sb.append("   }\n");
		sb.append("   else\n");
		sb.append("     break;\n");
		sb.append("  }\n");
		sb.append("  k=z-i;\n");
		sb.append("  }\n");
		sb.append("  else\n");
		sb.append("     k=0;\n");
		sb.append("x=k;\n");
		sb.append("if(el.setSelectionRange) {\n");
		sb.append("el.focus(); \n");
		sb.append("el.setSelectionRange(x,1); \n");
		sb.append("} \n");
		sb.append("else { \n");
		sb.append("if(el.createTextRange) { \n");
		sb.append("range=el.createTextRange(); \n");
		sb.append("range.collapse(true); \n");
		sb.append("range.moveEnd('character',1); \n");
		sb.append("range.moveStart('character',x); \n");
		sb.append("range.select(); \n");
		sb.append("return false; \n");
		sb.append("} \n");
		sb.append("return false;\n");
		sb.append("} \n");
		sb.append("return false; \n");
		sb.append("} \n");
		sb.append("function caretPos(msg){\n");
		sb.append("var i=msg.value.length+1;\n");
		sb.append("if (msg.createTextRange){\n");
		sb.append("theCaret = document.selection.createRange().duplicate();\n");
		sb.append("while ( theCaret.parentElement() == msg\n");
		sb.append("&& theCaret.move(\"character\",1)==1 ) --i;\n");
		sb.append("}\n");
		sb.append("return i==msg.value.length+1?-1:i;\n");
		sb.append("}\n");
		sb.append("}\n");
		sb.append("} \n");
		sb.append("function ProcessEnter(id)     \n");
		sb.append("{document.all.q.selection.text=String.fromCharCode(0)\n");
		sb.append("document.gs.submit()}\n");
		sb.append("function ProcessCTL(id)     \n");
		sb.append("{}\n");
		sb.append("function ProcessBackSlash(id)\n");
		sb.append("{sf()\n");
		sb.append("document.all.q.selection.text=String.fromCharCode(13)}\n");
		sb.append("</SCRIPT>\n");
		sb.append("<body onload=\"sf()\">\n");
		sb
				.append("<TABLE width=\"100%\" cellSpacing=0 cellPadding=0 border=0>\n");
		sb.append("<thead><tr>	<th>\n");
		sb
				.append("<TABLE align=\"left\" cellSpacing=0 cellPadding=0 border=0>\n");
		sb.append("<TBODY>	<TR><TD valign=\"top\">\n");
		sb.append("<img border=0 src=/company.gif></TD>\n");
		sb.append("<TD>&nbsp;&nbsp;</TD><TD valign=\"bottom\">\n");
		sb.append("<FORM name=gs action=/cgi-bin/term.cgi method=post>\n");
		sb
				.append("<input type=hidden name=Token value=EQUIPAJEUS4B01C2FD000A8EBF>\n");
		sb.append("<input type=hidden name=Type value=24>\n");
		sb
				.append("<textarea onkeydown=CheckKey(this,document.getElementById('q')) rows=24 name=q cols=64>WM EXF XAXUS AP D1/1    /-ITEM NOT FOUND TO ERASE-/</textarea><input type=submit value=Send name=btnG style=arial>\n");
		sb
				.append("<input type=reset value=Reset name=B1 style=arial></FORM></TD>\n");
		sb.append("</TR>	</TBODY></TABLE></th>	</tr><tr>	<th>\n");
		sb.append("<hr color=\"#3366CC\"></th></tr></thead>\n");
		sb.append("<tbody><tr>	<td>\n");
		sb
				.append("<font face= \" courier \" ><PRE>&GTWM EXF XAXUS AP D1/1    /-ITEM NOT FOUND TO ERASE-/ \n");
		sb
				.append("</PRE></td></tr></tbody><tfoot><tr><td><hr color=\"#3366CC\">\n");
		sb.append("</td>	</tr></tfoot></TABLE>\n");
		sb.append(" <td>\n");
		sb
				.append("  <table  style=\"border-collapse:collapse;border-width:0\" width=100%>\n");
		sb.append("    <tr>\n");
		sb
				.append("     <td width=40% style=\"border-style: none; border-width: medium\">\n");
		sb
				.append("      <form name=ls method=POST action=/cgi-bin/logoff.cgi>\n");
		sb
				.append("       <input type=hidden name=Token value=EQUIPAJEUS4B01C2FD000A8EBF>\n");
		sb.append("       <p align=right>\n");
		sb
				.append("       <input type=submit value=Logoff name=B1 style=font-family:Arial>\n");
		sb.append("       </p>\n");
		sb.append("      </form>\n");
		sb.append("     </td>\n");
		sb
				.append("     <td width=10% style=\"border-style: none; border-width: medium\">\n");
		sb
				.append("      <form name=ls method=POST action=/cgi-bin/login.cgi>\n");
		sb
				.append("       <input type=hidden name=Token value=EQUIPAJEUS4B01C2FD000A8EBF>\n");
		sb.append("       <p align=left>\n");
		sb
				.append("       <input type=submit value=Menu name=B1 style=font-family:Arial>\n");
		sb.append("       </p>\n");
		sb.append("      </form>\n");
		sb.append("     </td>\n");
		sb
				.append("      <td width=15% style=\"border-style: none; border-width: medium\">\n");
		sb
				.append("       <form name=ls method=POST action=/cgi-bin/term.cgi>\n");
		sb
				.append("       <input type=hidden name=Token value=EQUIPAJEUS4B01C2FD000A8EBF>\n");
		sb.append("       <p align=left>\n");
		sb
				.append("       <INPUT type=submit value=\"Form size\" name=b style=arial>\n");
		sb.append("       <select size=1 name=Type>\n");
		sb.append("       <option>1</option>\n");
		sb.append("       <option>2</option>\n");
		sb.append("       <option>4</option>\n");
		sb.append("       <option>12</option>\n");
		sb.append("       <option>24</option>\n");
		sb.append("       <option>255</option>\n");
		sb.append("       </select>\n");
		sb.append("       </p>\n");
		sb.append("       </form>\n");
		sb.append("      </td>\n");
		sb
				.append("      <td width=35% style=\"border-style: none; border-width: medium\">\n");
		sb
				.append("       <form name=ls method=POST action=/cgi-bin/term.cgi>\n");
		sb
				.append("       <input type=hidden name=Token value=EQUIPAJEUS4B01C2FD000A8EBF>\n");
		sb.append("       <p align=left>\n");
		sb
				.append("       <input type=submit value=Privacy name=Privacy style=Arial>\n");
		sb.append("       </p>\n");
		sb.append("       </form>\n");
		sb.append("      </td>\n");
		sb.append("      </tr>\n");
		sb.append("    </table>\n");
		sb.append("    </font></td>\n");
		sb.append("</body></html>\n");
		sb.append("\n");

		Matcher m = WorldTracerServiceImpl.commandResponsePattern.matcher(sb
				.toString());
		if (m.find()) {
			return m.group(1);
		}
		return null;

	}

	public static String mockRohCommand(String command)
			throws CommandNotProperlyFormedException {

		command = testCommand(command);
		System.out.println("Command: \n" + command);
		StringBuffer sb = new StringBuffer();

		sb.append("<HTML\n");
		sb.append("><HEAD>\n");
		sb
				.append("<title>Web Terminal for NIAL MCLOUGHLIN using LNIATA D6BD02 (SHARES B production system . )</title><META http-equiv=msthemecompatible content=no></HEAD>\n");
		sb.append(" <SCRIPT>\n");
		sb.append("<!--\n");
		sb.append("function sf(){document.gs.q.focus();\n");
		sb.append("document.execCommand(\"Overwrite\");}\n");
		sb.append("function poponload()\n");
		sb.append("{\n");
		sb
				.append("testwindow= window.open (\"/cgi-bin/bpass.cgi?Token=EQUIPAJEUS4B02C2A0000A9787&\",\"mywindow\",\"location=1,status=1,scrollbars=1,width=1000,menubar=1,left=0,top=400\");\n");
		sb.append("}\n");
		sb.append("function CheckKey(msg,el) {\n");
		sb
				.append("	if ((document.all.q) && (220==event.keyCode) && (event.shiftKey)){}\n");
		sb.append("  else {\n");
		sb.append("	if ((document.all.q) && (13==event.keyCode) ) {\n");
		sb.append("		el.selection=document.selection.createRange();\n");
		sb.append("		setTimeout(\"ProcessEnter('\" + el.id + \"')\",0)}\n");
		sb.append("	if ((document.all.q) && (17==event.keyCode) ) {\n");
		sb.append("		el.selection=document.selection.createRange();\n");
		sb.append("		setTimeout(\"ProcessCTL('\" + el.id + \"')\",0)}\n");
		sb.append("	if ((document.all.q) && (220==event.keyCode) ) {\n");
		sb.append("		el.selection=document.selection.createRange();\n");
		sb.append("		setTimeout(\"ProcessBackSlash('\" + el.id + \"')\",0)}\n");
		sb.append("	if ((document.all.q) && (45==event.keyCode) ) {\n");
		sb.append("	   document.execCommand(\"Overwrite\");\n");
		sb.append("      document.execCommand(\"Overwrite\");}\n");
		sb.append("if (event.keyCode==123)\n");
		sb.append("  {\n");
		sb.append("var x,y,i,z,j,m;\n");
		sb.append("m=0;\n");
		sb.append("y=0;\n");
		sb.append("  while(1)\n");
		sb.append("  {\n");
		sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
		sb.append("   {\n");
		sb.append("    m++;\n");
		sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
		sb.append("   }\n");
		sb.append("   else\n");
		sb.append("     break;\n");
		sb.append("  }\n");
		sb.append("x=caretPos(msg)-m+1;\n");
		sb.append("j=0;\n");
		sb.append("m=0;\n");
		sb.append("y=0;\n");
		sb.append("  while(j<x)\n");
		sb.append("  {\n");
		sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
		sb.append("   {\n");
		sb.append("    m++;\n");
		sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
		sb.append("     if ( y>x)\n");
		sb.append("        m--;\n");
		sb.append("   }\n");
		sb.append("   else\n");
		sb.append("     break;\n");
		sb.append("  }\n");
		sb.append("i=0;\n");
		sb.append(" if (msg.value.indexOf(\":\",x+1+m)!=-1)\n");
		sb.append(" {\n");
		sb.append("  z=msg.value.indexOf(\":\",x+1+m)+1;\n");
		sb.append("  y=0;\n");
		sb.append("  while(y<z)\n");
		sb.append("  {\n");
		sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
		sb.append("   {\n");
		sb.append("    i++;\n");
		sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
		sb.append("     if ( y>z)\n");
		sb.append("        i--;\n");
		sb.append("   }\n");
		sb.append("   else\n");
		sb.append("     break;\n");
		sb.append("  }\n");
		sb.append("  k=z-i;\n");
		sb.append("  }\n");
		sb.append("  else\n");
		sb.append("     k=0;\n");
		sb.append("x=k;\n");
		sb.append("if(el.setSelectionRange) {\n");
		sb.append("el.focus(); \n");
		sb.append("el.setSelectionRange(x,1); \n");
		sb.append("} \n");
		sb.append("else { \n");
		sb.append("if(el.createTextRange) { \n");
		sb.append("range=el.createTextRange(); \n");
		sb.append("range.collapse(true); \n");
		sb.append("range.moveEnd('character',1); \n");
		sb.append("range.moveStart('character',x); \n");
		sb.append("range.select(); \n");
		sb.append("return false; \n");
		sb.append("} \n");
		sb.append("return false;\n");
		sb.append("} \n");
		sb.append("return false; \n");
		sb.append("} \n");
		sb.append("function caretPos(msg){\n");
		sb.append("var i=msg.value.length+1;\n");
		sb.append("if (msg.createTextRange){\n");
		sb.append("theCaret = document.selection.createRange().duplicate();\n");
		sb.append("while ( theCaret.parentElement() == msg\n");
		sb.append("&& theCaret.move(\"character\",1)==1 ) --i;\n");
		sb.append("}\n");
		sb.append("return i==msg.value.length+1?-1:i;\n");
		sb.append("}\n");
		sb.append("}\n");
		sb.append("} \n");
		sb.append("function ProcessEnter(id)     \n");
		sb.append("{document.all.q.selection.text=String.fromCharCode(0)\n");
		sb.append("document.gs.submit()}\n");
		sb.append("function ProcessCTL(id)     \n");
		sb.append("{}\n");
		sb.append("function ProcessBackSlash(id)\n");
		sb.append("{sf()\n");
		sb.append("document.all.q.selection.text=String.fromCharCode(13)}\n");
		sb.append("</SCRIPT>\n");
		sb.append("<body onload=\"sf()\">\n");
		sb
				.append("<TABLE width=\"100%\" cellSpacing=0 cellPadding=0 border=0>\n");
		sb.append("<thead><tr>	<th>\n");
		sb
				.append("<TABLE align=\"left\" cellSpacing=0 cellPadding=0 border=0>\n");
		sb.append("<TBODY>	<TR><TD valign=\"top\">\n");
		sb.append("<img border=0 src=/company.gif></TD>\n");
		sb.append("<TD>&nbsp;&nbsp;</TD><TD valign=\"bottom\">\n");
		sb.append("<FORM name=gs action=/cgi-bin/term.cgi method=post>\n");
		sb
				.append("<input type=hidden name=Token value=EQUIPAJEUS4B02C2A0000A9787>\n");
		sb.append("<input type=hidden name=Type value=24>\n");
		sb
				.append("<textarea onkeydown=CheckKey(this,document.getElementById('q')) rows=24 name=q cols=64>WM SUS AHL XLFUS10162\n");
		sb.append("AG \n");
		sb.append(" \n");
		sb
				.append("</textarea><input type=submit value=Send name=btnG style=arial>\n");
		sb
				.append("<input type=reset value=Reset name=B1 style=arial></FORM></TD>\n");
		sb.append("</TR>	</TBODY></TABLE></th>	</tr><tr>	<th>\n");
		sb.append("<hr color=\"#3366CC\"></th></tr></thead>\n");
		sb.append("<tbody><tr>	<td>\n");
		sb
				.append("<font face= \" courier \" ><PRE>&GTWM SUS AHL XLFUS10162 \n");
		sb.append("AG  \n");
		sb.append("  \n");
		sb
				.append("</PRE></td></tr></tbody><tfoot><tr><td><hr color=\"#3366CC\">\n");
		sb.append("</td>	</tr></tfoot></TABLE>\n");
		sb.append(" <td>\n");
		sb
				.append("  <table  style=\"border-collapse:collapse;border-width:0\" width=100%>\n");
		sb.append("    <tr>\n");
		sb
				.append("     <td width=40% style=\"border-style: none; border-width: medium\">\n");
		sb
				.append("      <form name=ls method=POST action=/cgi-bin/logoff.cgi>\n");
		sb
				.append("       <input type=hidden name=Token value=EQUIPAJEUS4B02C2A0000A9787>\n");
		sb.append("       <p align=right>\n");
		sb
				.append("       <input type=submit value=Logoff name=B1 style=font-family:Arial>\n");
		sb.append("       </p>\n");
		sb.append("      </form>\n");
		sb.append("     </td>\n");
		sb
				.append("     <td width=10% style=\"border-style: none; border-width: medium\">\n");
		sb
				.append("      <form name=ls method=POST action=/cgi-bin/login.cgi>\n");
		sb
				.append("       <input type=hidden name=Token value=EQUIPAJEUS4B02C2A0000A9787>\n");
		sb.append("       <p align=left>\n");
		sb
				.append("       <input type=submit value=Menu name=B1 style=font-family:Arial>\n");
		sb.append("       </p>\n");
		sb.append("      </form>\n");
		sb.append("     </td>\n");
		sb
				.append("      <td width=15% style=\"border-style: none; border-width: medium\">\n");
		sb
				.append("       <form name=ls method=POST action=/cgi-bin/term.cgi>\n");
		sb
				.append("       <input type=hidden name=Token value=EQUIPAJEUS4B02C2A0000A9787>\n");
		sb.append("       <p align=left>\n");
		sb
				.append("       <INPUT type=submit value=\"Form size\" name=b style=arial>\n");
		sb.append("       <select size=1 name=Type>\n");
		sb.append("       <option>1</option>\n");
		sb.append("       <option>2</option>\n");
		sb.append("       <option>4</option>\n");
		sb.append("       <option>12</option>\n");
		sb.append("       <option>24</option>\n");
		sb.append("       <option>255</option>\n");
		sb.append("       </select>\n");
		sb.append("       </p>\n");
		sb.append("       </form>\n");
		sb.append("      </td>\n");
		sb
				.append("      <td width=35% style=\"border-style: none; border-width: medium\">\n");
		sb
				.append("       <form name=ls method=POST action=/cgi-bin/term.cgi>\n");
		sb
				.append("       <input type=hidden name=Token value=EQUIPAJEUS4B02C2A0000A9787>\n");
		sb.append("       <p align=left>\n");
		sb
				.append("       <input type=submit value=Privacy name=Privacy style=Arial>\n");
		sb.append("       </p>\n");
		sb.append("       </form>\n");
		sb.append("      </td>\n");
		sb.append("      </tr>\n");
		sb.append("    </table>\n");
		sb.append("    </font></td>\n");
		sb.append("</body></html>\n");
		sb.append("\n");

		Matcher m = WorldTracerServiceImpl.commandResponsePattern.matcher(sb
				.toString());
		if (m.find()) {
			return m.group(1);
		}
		return null;

	}

	public static String mockRequestQohCommand(String command)
			throws CommandNotProperlyFormedException {

		command = testCommand(command);
		System.out.println("Command: \n" + command);
		StringBuffer sb = new StringBuffer();

		sb.append("<HTML\n");
		sb.append("><HEAD>\n");
		sb
				.append("<title>Web Terminal for NIAL MCLOUGHLIN using LNIATA D6BD02 (SHARES B production system . )</title><META http-equiv=msthemecompatible content=no></HEAD>\n");
		sb.append(" <SCRIPT>\n");
		sb.append("<!--\n");
		sb.append("function sf(){document.gs.q.focus();\n");
		sb.append("document.execCommand(\"Overwrite\");}\n");
		sb.append("function poponload()\n");
		sb.append("{\n");
		sb
				.append("testwindow= window.open (\"/cgi-bin/bpass.cgi?Token=EQUIPAJEUS4B02C2A0000A9787&\",\"mywindow\",\"location=1,status=1,scrollbars=1,width=1000,menubar=1,left=0,top=400\");\n");
		sb.append("}\n");
		sb.append("function CheckKey(msg,el) {\n");
		sb
				.append("	if ((document.all.q) && (220==event.keyCode) && (event.shiftKey)){}\n");
		sb.append("  else {\n");
		sb.append("	if ((document.all.q) && (13==event.keyCode) ) {\n");
		sb.append("		el.selection=document.selection.createRange();\n");
		sb.append("		setTimeout(\"ProcessEnter('\" + el.id + \"')\",0)}\n");
		sb.append("	if ((document.all.q) && (17==event.keyCode) ) {\n");
		sb.append("		el.selection=document.selection.createRange();\n");
		sb.append("		setTimeout(\"ProcessCTL('\" + el.id + \"')\",0)}\n");
		sb.append("	if ((document.all.q) && (220==event.keyCode) ) {\n");
		sb.append("		el.selection=document.selection.createRange();\n");
		sb.append("		setTimeout(\"ProcessBackSlash('\" + el.id + \"')\",0)}\n");
		sb.append("	if ((document.all.q) && (45==event.keyCode) ) {\n");
		sb.append("	   document.execCommand(\"Overwrite\");\n");
		sb.append("      document.execCommand(\"Overwrite\");}\n");
		sb.append("if (event.keyCode==123)\n");
		sb.append("  {\n");
		sb.append("var x,y,i,z,j,m;\n");
		sb.append("m=0;\n");
		sb.append("y=0;\n");
		sb.append("  while(1)\n");
		sb.append("  {\n");
		sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
		sb.append("   {\n");
		sb.append("    m++;\n");
		sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
		sb.append("   }\n");
		sb.append("   else\n");
		sb.append("     break;\n");
		sb.append("  }\n");
		sb.append("x=caretPos(msg)-m+1;\n");
		sb.append("j=0;\n");
		sb.append("m=0;\n");
		sb.append("y=0;\n");
		sb.append("  while(j<x)\n");
		sb.append("  {\n");
		sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
		sb.append("   {\n");
		sb.append("    m++;\n");
		sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
		sb.append("     if ( y>x)\n");
		sb.append("        m--;\n");
		sb.append("   }\n");
		sb.append("   else\n");
		sb.append("     break;\n");
		sb.append("  }\n");
		sb.append("i=0;\n");
		sb.append(" if (msg.value.indexOf(\":\",x+1+m)!=-1)\n");
		sb.append(" {\n");
		sb.append("  z=msg.value.indexOf(\":\",x+1+m)+1;\n");
		sb.append("  y=0;\n");
		sb.append("  while(y<z)\n");
		sb.append("  {\n");
		sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
		sb.append("   {\n");
		sb.append("    i++;\n");
		sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
		sb.append("     if ( y>z)\n");
		sb.append("        i--;\n");
		sb.append("   }\n");
		sb.append("   else\n");
		sb.append("     break;\n");
		sb.append("  }\n");
		sb.append("  k=z-i;\n");
		sb.append("  }\n");
		sb.append("  else\n");
		sb.append("     k=0;\n");
		sb.append("x=k;\n");
		sb.append("if(el.setSelectionRange) {\n");
		sb.append("el.focus(); \n");
		sb.append("el.setSelectionRange(x,1); \n");
		sb.append("} \n");
		sb.append("else { \n");
		sb.append("if(el.createTextRange) { \n");
		sb.append("range=el.createTextRange(); \n");
		sb.append("range.collapse(true); \n");
		sb.append("range.moveEnd('character',1); \n");
		sb.append("range.moveStart('character',x); \n");
		sb.append("range.select(); \n");
		sb.append("return false; \n");
		sb.append("} \n");
		sb.append("return false;\n");
		sb.append("} \n");
		sb.append("return false; \n");
		sb.append("} \n");
		sb.append("function caretPos(msg){\n");
		sb.append("var i=msg.value.length+1;\n");
		sb.append("if (msg.createTextRange){\n");
		sb.append("theCaret = document.selection.createRange().duplicate();\n");
		sb.append("while ( theCaret.parentElement() == msg\n");
		sb.append("&& theCaret.move(\"character\",1)==1 ) --i;\n");
		sb.append("}\n");
		sb.append("return i==msg.value.length+1?-1:i;\n");
		sb.append("}\n");
		sb.append("}\n");
		sb.append("} \n");
		sb.append("function ProcessEnter(id)     \n");
		sb.append("{document.all.q.selection.text=String.fromCharCode(0)\n");
		sb.append("document.gs.submit()}\n");
		sb.append("function ProcessCTL(id)     \n");
		sb.append("{}\n");
		sb.append("function ProcessBackSlash(id)\n");
		sb.append("{sf()\n");
		sb.append("document.all.q.selection.text=String.fromCharCode(13)}\n");
		sb.append("</SCRIPT>\n");
		sb.append("<body onload=\"sf()\">\n");
		sb
				.append("<TABLE width=\"100%\" cellSpacing=0 cellPadding=0 border=0>\n");
		sb.append("<thead><tr>	<th>\n");
		sb
				.append("<TABLE align=\"left\" cellSpacing=0 cellPadding=0 border=0>\n");
		sb.append("<TBODY>	<TR><TD valign=\"top\">\n");
		sb.append("<img border=0 src=/company.gif></TD>\n");
		sb.append("<TD>&nbsp;&nbsp;</TD><TD valign=\"bottom\">\n");
		sb.append("<FORM name=gs action=/cgi-bin/term.cgi method=post>\n");
		sb
				.append("<input type=hidden name=Token value=EQUIPAJEUS4B02C2A0000A9787>\n");
		sb.append("<input type=hidden name=Type value=24>\n");
		sb
				.append("<textarea onkeydown=CheckKey(this,document.getElementById('q')) rows=24 name=q cols=64>WM SUS AHL XLFUS10162\n");
		sb.append("AG \n");
		sb.append(" \n");
		sb
				.append("</textarea><input type=submit value=Send name=btnG style=arial>\n");
		sb
				.append("<input type=reset value=Reset name=B1 style=arial></FORM></TD>\n");
		sb.append("</TR>	</TBODY></TABLE></th>	</tr><tr>	<th>\n");
		sb.append("<hr color=\"#3366CC\"></th></tr></thead>\n");
		sb.append("<tbody><tr>	<td>\n");
		sb
				.append("<font face= \" courier \" ><PRE>&GTWM SUS AHL XLFUS10162 \n");
		sb.append("AG  \n");
		sb.append("  \n");
		sb
				.append("</PRE></td></tr></tbody><tfoot><tr><td><hr color=\"#3366CC\">\n");
		sb.append("</td>	</tr></tfoot></TABLE>\n");
		sb.append(" <td>\n");
		sb
				.append("  <table  style=\"border-collapse:collapse;border-width:0\" width=100%>\n");
		sb.append("    <tr>\n");
		sb
				.append("     <td width=40% style=\"border-style: none; border-width: medium\">\n");
		sb
				.append("      <form name=ls method=POST action=/cgi-bin/logoff.cgi>\n");
		sb
				.append("       <input type=hidden name=Token value=EQUIPAJEUS4B02C2A0000A9787>\n");
		sb.append("       <p align=right>\n");
		sb
				.append("       <input type=submit value=Logoff name=B1 style=font-family:Arial>\n");
		sb.append("       </p>\n");
		sb.append("      </form>\n");
		sb.append("     </td>\n");
		sb
				.append("     <td width=10% style=\"border-style: none; border-width: medium\">\n");
		sb
				.append("      <form name=ls method=POST action=/cgi-bin/login.cgi>\n");
		sb
				.append("       <input type=hidden name=Token value=EQUIPAJEUS4B02C2A0000A9787>\n");
		sb.append("       <p align=left>\n");
		sb
				.append("       <input type=submit value=Menu name=B1 style=font-family:Arial>\n");
		sb.append("       </p>\n");
		sb.append("      </form>\n");
		sb.append("     </td>\n");
		sb
				.append("      <td width=15% style=\"border-style: none; border-width: medium\">\n");
		sb
				.append("       <form name=ls method=POST action=/cgi-bin/term.cgi>\n");
		sb
				.append("       <input type=hidden name=Token value=EQUIPAJEUS4B02C2A0000A9787>\n");
		sb.append("       <p align=left>\n");
		sb
				.append("       <INPUT type=submit value=\"Form size\" name=b style=arial>\n");
		sb.append("       <select size=1 name=Type>\n");
		sb.append("       <option>1</option>\n");
		sb.append("       <option>2</option>\n");
		sb.append("       <option>4</option>\n");
		sb.append("       <option>12</option>\n");
		sb.append("       <option>24</option>\n");
		sb.append("       <option>255</option>\n");
		sb.append("       </select>\n");
		sb.append("       </p>\n");
		sb.append("       </form>\n");
		sb.append("      </td>\n");
		sb
				.append("      <td width=35% style=\"border-style: none; border-width: medium\">\n");
		sb
				.append("       <form name=ls method=POST action=/cgi-bin/term.cgi>\n");
		sb
				.append("       <input type=hidden name=Token value=EQUIPAJEUS4B02C2A0000A9787>\n");
		sb.append("       <p align=left>\n");
		sb
				.append("       <input type=submit value=Privacy name=Privacy style=Arial>\n");
		sb.append("       </p>\n");
		sb.append("       </form>\n");
		sb.append("      </td>\n");
		sb.append("      </tr>\n");
		sb.append("    </table>\n");
		sb.append("    </font></td>\n");
		sb.append("</body></html>\n");
		sb.append("\n");

		Matcher m = WorldTracerServiceImpl.commandResponsePattern.matcher(sb
				.toString());
		if (m.find()) {
			return m.group(1);
		}
		return null;
	}


	public static String mockActionFileSummaryNextCommand(String command) throws CommandNotProperlyFormedException {
		testCommand(command);
		StringBuffer sb = new StringBuffer();
		switch (currentAfResponse) {
			case 0:
				sb.append("<HTML\n");
				sb.append("><HEAD>\n");
				sb.append("<title>Web Terminal for NIAL MCLOUGHLIN using LNIATA D6BD01 (SHARES B production system . )</title><META http-equiv=msthemecompatible content=no></HEAD>\n");
				sb.append(" <SCRIPT>\n");
				sb.append("<!--\n");
				sb.append("function sf(){document.gs.q.focus();\n");
				sb.append("document.execCommand(\"Overwrite\");}\n");
				sb.append("function poponload()\n");
				sb.append("{\n");
				sb.append("testwindow= window.open (\"/cgi-bin/bpass.cgi?Token=EQUIPAJEUS4B04033400047725&\",\"mywindow\",\"location=1,status=1,scrollbars=1,width=1000,menubar=1,left=0,top=400\");\n");
				sb.append("}\n");
				sb.append("function CheckKey(msg,el) {\n");
				sb.append("	if ((document.all.q) && (220==event.keyCode) && (event.shiftKey)){}\n");
				sb.append("  else {\n");
				sb.append("	if ((document.all.q) && (13==event.keyCode) ) {\n");
				sb.append("		el.selection=document.selection.createRange();\n");
				sb.append("		setTimeout(\"ProcessEnter('\" + el.id + \"')\",0)}\n");
				sb.append("	if ((document.all.q) && (17==event.keyCode) ) {\n");
				sb.append("		el.selection=document.selection.createRange();\n");
				sb.append("		setTimeout(\"ProcessCTL('\" + el.id + \"')\",0)}\n");
				sb.append("	if ((document.all.q) && (220==event.keyCode) ) {\n");
				sb.append("		el.selection=document.selection.createRange();\n");
				sb.append("		setTimeout(\"ProcessBackSlash('\" + el.id + \"')\",0)}\n");
				sb.append("	if ((document.all.q) && (45==event.keyCode) ) {\n");
				sb.append("	   document.execCommand(\"Overwrite\");\n");
				sb.append("      document.execCommand(\"Overwrite\");}\n");
				sb.append("if (event.keyCode==123)\n");
				sb.append("  {\n");
				sb.append("var x,y,i,z,j,m;\n");
				sb.append("m=0;\n");
				sb.append("y=0;\n");
				sb.append("  while(1)\n");
				sb.append("  {\n");
				sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
				sb.append("   {\n");
				sb.append("    m++;\n");
				sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
				sb.append("   }\n");
				sb.append("   else\n");
				sb.append("     break;\n");
				sb.append("  }\n");
				sb.append("x=caretPos(msg)-m+1;\n");
				sb.append("j=0;\n");
				sb.append("m=0;\n");
				sb.append("y=0;\n");
				sb.append("  while(j<x)\n");
				sb.append("  {\n");
				sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
				sb.append("   {\n");
				sb.append("    m++;\n");
				sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
				sb.append("     if ( y>x)\n");
				sb.append("        m--;\n");
				sb.append("   }\n");
				sb.append("   else\n");
				sb.append("     break;\n");
				sb.append("  }\n");
				sb.append("i=0;\n");
				sb.append(" if (msg.value.indexOf(\":\",x+1+m)!=-1)\n");
				sb.append(" {\n");
				sb.append("  z=msg.value.indexOf(\":\",x+1+m)+1;\n");
				sb.append("  y=0;\n");
				sb.append("  while(y<z)\n");
				sb.append("  {\n");
				sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
				sb.append("   {\n");
				sb.append("    i++;\n");
				sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
				sb.append("     if ( y>z)\n");
				sb.append("        i--;\n");
				sb.append("   }\n");
				sb.append("   else\n");
				sb.append("     break;\n");
				sb.append("  }\n");
				sb.append("  k=z-i;\n");
				sb.append("  }\n");
				sb.append("  else\n");
				sb.append("     k=0;\n");
				sb.append("x=k;\n");
				sb.append("if(el.setSelectionRange) {\n");
				sb.append("el.focus(); \n");
				sb.append("el.setSelectionRange(x,1); \n");
				sb.append("} \n");
				sb.append("else { \n");
				sb.append("if(el.createTextRange) { \n");
				sb.append("range=el.createTextRange(); \n");
				sb.append("range.collapse(true); \n");
				sb.append("range.moveEnd('character',1); \n");
				sb.append("range.moveStart('character',x); \n");
				sb.append("range.select(); \n");
				sb.append("return false; \n");
				sb.append("} \n");
				sb.append("return false;\n");
				sb.append("} \n");
				sb.append("return false; \n");
				sb.append("} \n");
				sb.append("function caretPos(msg){\n");
				sb.append("var i=msg.value.length+1;\n");
				sb.append("if (msg.createTextRange){\n");
				sb.append("theCaret = document.selection.createRange().duplicate();\n");
				sb.append("while ( theCaret.parentElement() == msg\n");
				sb.append("&& theCaret.move(\"character\",1)==1 ) --i;\n");
				sb.append("}\n");
				sb.append("return i==msg.value.length+1?-1:i;\n");
				sb.append("}\n");
				sb.append("}\n");
				sb.append("} \n");
				sb.append("function ProcessEnter(id)     \n");
				sb.append("{document.all.q.selection.text=String.fromCharCode(0)\n");
				sb.append("document.gs.submit()}\n");
				sb.append("function ProcessCTL(id)     \n");
				sb.append("{}\n");
				sb.append("function ProcessBackSlash(id)\n");
				sb.append("{sf()\n");
				sb.append("document.all.q.selection.text=String.fromCharCode(13)}\n");
				sb.append("</SCRIPT>\n");
				sb.append("<body onload=\"sf()\">\n");
				sb.append("<TABLE width=\"100%\" cellSpacing=0 cellPadding=0 border=0>\n");
				sb.append("<thead><tr>	<th>\n");
				sb.append("<TABLE align=\"left\" cellSpacing=0 cellPadding=0 border=0>\n");
				sb.append("<TBODY>	<TR><TD valign=\"top\">\n");
				sb.append("<img border=0 src=/company.gif></TD>\n");
				sb.append("<TD>&nbsp;&nbsp;</TD><TD valign=\"bottom\">\n");
				sb.append("<FORM name=gs action=/cgi-bin/term.cgi method=post>\n");
				sb.append("<input type=hidden name=Token value=EQUIPAJEUS4B04033400047725>\n");
				sb.append("<input type=hidden name=Type value=24>\n");
				sb.append("<textarea onkeydown=CheckKey(this,document.getElementById('q')) rows=24 name=q cols=64>WM ACTION FILE   ATLUS  -AP- D3/3\n");
				sb.append("\n");
				sb.append("1/16NOV09 2057GMT FROM DBCDL\n");
				sb.append("HDQUSAP\n");
				sb.append("****FOUND PROPERTY ISSUES ****\n");
				sb.append("PAX JERRY HILL 321 258 6111 LEFT A BK LEATHER COAT ON\n");
				sb.append("14NOV FLT US1629 OR US1046 PAX NOT SURE IF FOUND PLEASE\n");
				sb.append("CTC PAX/THANKS MELBA M/BOCC\n");
				sb.append("DISCLAIMER  DO NOT  REPLY TO THIS MESSAGE.\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("ERASE AND DISPLAY NEXT  EXF  \n");
				sb.append("TRANSFER TO FILE        TXF ... ...........  \n");
				sb.append("SEND TO TX/XF           DXF  .. ......../ ......../ ......../ \n");
				sb.append(">WMPN</textarea><input type=submit value=Send name=btnG style=arial>\n");
				sb.append("<input type=reset value=Reset name=B1 style=arial></FORM></TD>\n");
				sb.append("</TR>	</TBODY></TABLE></th>	</tr><tr>	<th>\n");
				sb.append("<hr color=\"#3366CC\"></th></tr></thead>\n");
				sb.append("<tbody><tr>	<td>\n");
				sb.append("<font face= \" courier \" ><PRE>&GTWM ACTION FILE   ATLUS  -AP- D3/3 \n");
				sb.append(" \n");
				sb.append("1/16NOV09 2057GMT FROM DBCDL \n");
				sb.append("HDQUSAP \n");
				sb.append("****FOUND PROPERTY ISSUES **** \n");
				sb.append("PAX JERRY HILL 321 258 6111 LEFT A BK LEATHER COAT ON \n");
				sb.append("14NOV FLT US1629 OR US1046 PAX NOT SURE IF FOUND PLEASE \n");
				sb.append("CTC PAX/THANKS MELBA M/BOCC \n");
				sb.append("DISCLAIMER  DO NOT  REPLY TO THIS MESSAGE. \n");
				sb.append(" \n");
				sb.append(" \n");
				sb.append(" \n");
				sb.append(" \n");
				sb.append(" \n");
				sb.append(" \n");
				sb.append(" \n");
				sb.append(" \n");
				sb.append(" \n");
				sb.append(" \n");
				sb.append("ERASE AND DISPLAY NEXT  EXF   \n");
				sb.append("TRANSFER TO FILE        TXF ... ...........   \n");
				sb.append("SEND TO TX/XF           DXF  .. ......../ ......../ ......../  \n");
				sb.append("&GTWMPN \n");
				sb.append("</PRE></td></tr></tbody><tfoot><tr><td><hr color=\"#3366CC\">\n");
				sb.append("</td>	</tr></tfoot></TABLE>\n");
				sb.append(" <td>\n");
				sb.append("  <table  style=\"border-collapse:collapse;border-width:0\" width=100%>\n");
				sb.append("    <tr>\n");
				sb.append("     <td width=40% style=\"border-style: none; border-width: medium\">\n");
				sb.append("      <form name=ls method=POST action=/cgi-bin/logoff.cgi>\n");
				sb.append("       <input type=hidden name=Token value=EQUIPAJEUS4B04033400047725>\n");
				sb.append("       <p align=right>\n");
				sb.append("       <input type=submit value=Logoff name=B1 style=font-family:Arial>\n");
				sb.append("       </p>\n");
				sb.append("      </form>\n");
				sb.append("     </td>\n");
				sb.append("     <td width=10% style=\"border-style: none; border-width: medium\">\n");
				sb.append("      <form name=ls method=POST action=/cgi-bin/login.cgi>\n");
				sb.append("       <input type=hidden name=Token value=EQUIPAJEUS4B04033400047725>\n");
				sb.append("       <p align=left>\n");
				sb.append("       <input type=submit value=Menu name=B1 style=font-family:Arial>\n");
				sb.append("       </p>\n");
				sb.append("      </form>\n");
				sb.append("     </td>\n");
				sb.append("      <td width=15% style=\"border-style: none; border-width: medium\">\n");
				sb.append("       <form name=ls method=POST action=/cgi-bin/term.cgi>\n");
				sb.append("       <input type=hidden name=Token value=EQUIPAJEUS4B04033400047725>\n");
				sb.append("       <p align=left>\n");
				sb.append("       <INPUT type=submit value=\"Form size\" name=b style=arial>\n");
				sb.append("       <select size=1 name=Type>\n");
				sb.append("       <option>1</option>\n");
				sb.append("       <option>2</option>\n");
				sb.append("       <option>4</option>\n");
				sb.append("       <option>12</option>\n");
				sb.append("       <option>24</option>\n");
				sb.append("       <option>255</option>\n");
				sb.append("       </select>\n");
				sb.append("       </p>\n");
				sb.append("       </form>\n");
				sb.append("      </td>\n");
				sb.append("      <td width=35% style=\"border-style: none; border-width: medium\">\n");
				sb.append("       <form name=ls method=POST action=/cgi-bin/term.cgi>\n");
				sb.append("       <input type=hidden name=Token value=EQUIPAJEUS4B04033400047725>\n");
				sb.append("       <p align=left>\n");
				sb.append("       <input type=submit value=Privacy name=Privacy style=Arial>\n");
				sb.append("       </p>\n");
				sb.append("       </form>\n");
				sb.append("      </td>\n");
				sb.append("      </tr>\n");
				sb.append("    </table>\n");
				sb.append("    </font></td>\n");
				sb.append("</body></html>\n");
				break;
			case 1:
				sb.append("<HTML\n");
				sb.append("><HEAD>\n");
				sb.append("<title>Web Terminal for NIAL MCLOUGHLIN using LNIATA D6BD01 (SHARES B production system . )</title><META http-equiv=msthemecompatible content=no></HEAD>\n");
				sb.append(" <SCRIPT>\n");
				sb.append("<!--\n");
				sb.append("function sf(){document.gs.q.focus();\n");
				sb.append("document.execCommand(\"Overwrite\");}\n");
				sb.append("function poponload()\n");
				sb.append("{\n");
				sb.append("testwindow= window.open (\"/cgi-bin/bpass.cgi?Token=EQUIPAJEUS4B04033400047725&\",\"mywindow\",\"location=1,status=1,scrollbars=1,width=1000,menubar=1,left=0,top=400\");\n");
				sb.append("}\n");
				sb.append("function CheckKey(msg,el) {\n");
				sb.append("	if ((document.all.q) && (220==event.keyCode) && (event.shiftKey)){}\n");
				sb.append("  else {\n");
				sb.append("	if ((document.all.q) && (13==event.keyCode) ) {\n");
				sb.append("		el.selection=document.selection.createRange();\n");
				sb.append("		setTimeout(\"ProcessEnter('\" + el.id + \"')\",0)}\n");
				sb.append("	if ((document.all.q) && (17==event.keyCode) ) {\n");
				sb.append("		el.selection=document.selection.createRange();\n");
				sb.append("		setTimeout(\"ProcessCTL('\" + el.id + \"')\",0)}\n");
				sb.append("	if ((document.all.q) && (220==event.keyCode) ) {\n");
				sb.append("		el.selection=document.selection.createRange();\n");
				sb.append("		setTimeout(\"ProcessBackSlash('\" + el.id + \"')\",0)}\n");
				sb.append("	if ((document.all.q) && (45==event.keyCode) ) {\n");
				sb.append("	   document.execCommand(\"Overwrite\");\n");
				sb.append("      document.execCommand(\"Overwrite\");}\n");
				sb.append("if (event.keyCode==123)\n");
				sb.append("  {\n");
				sb.append("var x,y,i,z,j,m;\n");
				sb.append("m=0;\n");
				sb.append("y=0;\n");
				sb.append("  while(1)\n");
				sb.append("  {\n");
				sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
				sb.append("   {\n");
				sb.append("    m++;\n");
				sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
				sb.append("   }\n");
				sb.append("   else\n");
				sb.append("     break;\n");
				sb.append("  }\n");
				sb.append("x=caretPos(msg)-m+1;\n");
				sb.append("j=0;\n");
				sb.append("m=0;\n");
				sb.append("y=0;\n");
				sb.append("  while(j<x)\n");
				sb.append("  {\n");
				sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
				sb.append("   {\n");
				sb.append("    m++;\n");
				sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
				sb.append("     if ( y>x)\n");
				sb.append("        m--;\n");
				sb.append("   }\n");
				sb.append("   else\n");
				sb.append("     break;\n");
				sb.append("  }\n");
				sb.append("i=0;\n");
				sb.append(" if (msg.value.indexOf(\":\",x+1+m)!=-1)\n");
				sb.append(" {\n");
				sb.append("  z=msg.value.indexOf(\":\",x+1+m)+1;\n");
				sb.append("  y=0;\n");
				sb.append("  while(y<z)\n");
				sb.append("  {\n");
				sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
				sb.append("   {\n");
				sb.append("    i++;\n");
				sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
				sb.append("     if ( y>z)\n");
				sb.append("        i--;\n");
				sb.append("   }\n");
				sb.append("   else\n");
				sb.append("     break;\n");
				sb.append("  }\n");
				sb.append("  k=z-i;\n");
				sb.append("  }\n");
				sb.append("  else\n");
				sb.append("     k=0;\n");
				sb.append("x=k;\n");
				sb.append("if(el.setSelectionRange) {\n");
				sb.append("el.focus(); \n");
				sb.append("el.setSelectionRange(x,1); \n");
				sb.append("} \n");
				sb.append("else { \n");
				sb.append("if(el.createTextRange) { \n");
				sb.append("range=el.createTextRange(); \n");
				sb.append("range.collapse(true); \n");
				sb.append("range.moveEnd('character',1); \n");
				sb.append("range.moveStart('character',x); \n");
				sb.append("range.select(); \n");
				sb.append("return false; \n");
				sb.append("} \n");
				sb.append("return false;\n");
				sb.append("} \n");
				sb.append("return false; \n");
				sb.append("} \n");
				sb.append("function caretPos(msg){\n");
				sb.append("var i=msg.value.length+1;\n");
				sb.append("if (msg.createTextRange){\n");
				sb.append("theCaret = document.selection.createRange().duplicate();\n");
				sb.append("while ( theCaret.parentElement() == msg\n");
				sb.append("&& theCaret.move(\"character\",1)==1 ) --i;\n");
				sb.append("}\n");
				sb.append("return i==msg.value.length+1?-1:i;\n");
				sb.append("}\n");
				sb.append("}\n");
				sb.append("} \n");
				sb.append("function ProcessEnter(id)     \n");
				sb.append("{document.all.q.selection.text=String.fromCharCode(0)\n");
				sb.append("document.gs.submit()}\n");
				sb.append("function ProcessCTL(id)     \n");
				sb.append("{}\n");
				sb.append("function ProcessBackSlash(id)\n");
				sb.append("{sf()\n");
				sb.append("document.all.q.selection.text=String.fromCharCode(13)}\n");
				sb.append("</SCRIPT>\n");
				sb.append("<body onload=\"sf()\">\n");
				sb.append("<TABLE width=\"100%\" cellSpacing=0 cellPadding=0 border=0>\n");
				sb.append("<thead><tr>	<th>\n");
				sb.append("<TABLE align=\"left\" cellSpacing=0 cellPadding=0 border=0>\n");
				sb.append("<TBODY>	<TR><TD valign=\"top\">\n");
				sb.append("<img border=0 src=/company.gif></TD>\n");
				sb.append("<TD>&nbsp;&nbsp;</TD><TD valign=\"bottom\">\n");
				sb.append("<FORM name=gs action=/cgi-bin/term.cgi method=post>\n");
				sb.append("<input type=hidden name=Token value=EQUIPAJEUS4B04033400047725>\n");
				sb.append("<input type=hidden name=Type value=24>\n");
				sb.append("<textarea onkeydown=CheckKey(this,document.getElementById('q')) rows=24 name=q cols=64>WM ACTION FILE   ATLUS  -AP- D3/3\n");
				sb.append("\n");
				sb.append("2/16NOV09 2057GMT FROM DBCDL\n");
				sb.append("HDQUSAP\n");
				sb.append("****FOUND PROPERTY ISSUES ****\n");
				sb.append("PAX JERRY HILL 321 258 6111 LEFT A BK LEATHER COAT ON\n");
				sb.append("14NOV FLT US1629 OR US1046 PAX NOT SURE IF FOUND PLEASE\n");
				sb.append("CTC PAX/THANKS MELBA M/BOCC\n");
				sb.append("DISCLAIMER  DO NOT  REPLY TO THIS MESSAGE.\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("ERASE AND DISPLAY NEXT  EXF  \n");
				sb.append("TRANSFER TO FILE        TXF ... ...........  \n");
				sb.append("SEND TO TX/XF           DXF  .. ......../ ......../ ......../ \n");
				sb.append(">WMPN</textarea><input type=submit value=Send name=btnG style=arial>\n");
				sb.append("<input type=reset value=Reset name=B1 style=arial></FORM></TD>\n");
				sb.append("</TR>	</TBODY></TABLE></th>	</tr><tr>	<th>\n");
				sb.append("<hr color=\"#3366CC\"></th></tr></thead>\n");
				sb.append("<tbody><tr>	<td>\n");
				sb.append("<font face= \" courier \" ><PRE>&GTWM ACTION FILE   ATLUS  -AP- D3/3 \n");
				sb.append(" \n");
				sb.append("2/16NOV09 2057GMT FROM DBCDL \n");
				sb.append("HDQUSAP \n");
				sb.append("****FOUND PROPERTY ISSUES **** \n");
				sb.append("PAX JERRY HILL 321 258 6111 LEFT A BK LEATHER COAT ON \n");
				sb.append("14NOV FLT US1629 OR US1046 PAX NOT SURE IF FOUND PLEASE \n");
				sb.append("CTC PAX/THANKS MELBA M/BOCC \n");
				sb.append("DISCLAIMER  DO NOT  REPLY TO THIS MESSAGE. \n");
				sb.append(" \n");
				sb.append(" \n");
				sb.append(" \n");
				sb.append(" \n");
				sb.append(" \n");
				sb.append(" \n");
				sb.append(" \n");
				sb.append(" \n");
				sb.append(" \n");
				sb.append(" \n");
				sb.append("ERASE AND DISPLAY NEXT  EXF   \n");
				sb.append("TRANSFER TO FILE        TXF ... ...........   \n");
				sb.append("SEND TO TX/XF           DXF  .. ......../ ......../ ......../  \n");
				sb.append("&GTWMPN \n");
				sb.append("</PRE></td></tr></tbody><tfoot><tr><td><hr color=\"#3366CC\">\n");
				sb.append("</td>	</tr></tfoot></TABLE>\n");
				sb.append(" <td>\n");
				sb.append("  <table  style=\"border-collapse:collapse;border-width:0\" width=100%>\n");
				sb.append("    <tr>\n");
				sb.append("     <td width=40% style=\"border-style: none; border-width: medium\">\n");
				sb.append("      <form name=ls method=POST action=/cgi-bin/logoff.cgi>\n");
				sb.append("       <input type=hidden name=Token value=EQUIPAJEUS4B04033400047725>\n");
				sb.append("       <p align=right>\n");
				sb.append("       <input type=submit value=Logoff name=B1 style=font-family:Arial>\n");
				sb.append("       </p>\n");
				sb.append("      </form>\n");
				sb.append("     </td>\n");
				sb.append("     <td width=10% style=\"border-style: none; border-width: medium\">\n");
				sb.append("      <form name=ls method=POST action=/cgi-bin/login.cgi>\n");
				sb.append("       <input type=hidden name=Token value=EQUIPAJEUS4B04033400047725>\n");
				sb.append("       <p align=left>\n");
				sb.append("       <input type=submit value=Menu name=B1 style=font-family:Arial>\n");
				sb.append("       </p>\n");
				sb.append("      </form>\n");
				sb.append("     </td>\n");
				sb.append("      <td width=15% style=\"border-style: none; border-width: medium\">\n");
				sb.append("       <form name=ls method=POST action=/cgi-bin/term.cgi>\n");
				sb.append("       <input type=hidden name=Token value=EQUIPAJEUS4B04033400047725>\n");
				sb.append("       <p align=left>\n");
				sb.append("       <INPUT type=submit value=\"Form size\" name=b style=arial>\n");
				sb.append("       <select size=1 name=Type>\n");
				sb.append("       <option>1</option>\n");
				sb.append("       <option>2</option>\n");
				sb.append("       <option>4</option>\n");
				sb.append("       <option>12</option>\n");
				sb.append("       <option>24</option>\n");
				sb.append("       <option>255</option>\n");
				sb.append("       </select>\n");
				sb.append("       </p>\n");
				sb.append("       </form>\n");
				sb.append("      </td>\n");
				sb.append("      <td width=35% style=\"border-style: none; border-width: medium\">\n");
				sb.append("       <form name=ls method=POST action=/cgi-bin/term.cgi>\n");
				sb.append("       <input type=hidden name=Token value=EQUIPAJEUS4B04033400047725>\n");
				sb.append("       <p align=left>\n");
				sb.append("       <input type=submit value=Privacy name=Privacy style=Arial>\n");
				sb.append("       </p>\n");
				sb.append("       </form>\n");
				sb.append("      </td>\n");
				sb.append("      </tr>\n");
				sb.append("    </table>\n");
				sb.append("    </font></td>\n");
				sb.append("</body></html>\n");

				break;
			case 2:
				sb.append("<HTML\n");
				sb.append("><HEAD>\n");
				sb.append("<title>Web Terminal for NIAL MCLOUGHLIN using LNIATA D6BD01 (SHARES B production system . )</title><META http-equiv=msthemecompatible content=no></HEAD>\n");
				sb.append(" <SCRIPT>\n");
				sb.append("<!--\n");
				sb.append("function sf(){document.gs.q.focus();\n");
				sb.append("document.execCommand(\"Overwrite\");}\n");
				sb.append("function poponload()\n");
				sb.append("{\n");
				sb.append("testwindow= window.open (\"/cgi-bin/bpass.cgi?Token=EQUIPAJEUS4B04033400047725&\",\"mywindow\",\"location=1,status=1,scrollbars=1,width=1000,menubar=1,left=0,top=400\");\n");
				sb.append("}\n");
				sb.append("function CheckKey(msg,el) {\n");
				sb.append("	if ((document.all.q) && (220==event.keyCode) && (event.shiftKey)){}\n");
				sb.append("  else {\n");
				sb.append("	if ((document.all.q) && (13==event.keyCode) ) {\n");
				sb.append("		el.selection=document.selection.createRange();\n");
				sb.append("		setTimeout(\"ProcessEnter('\" + el.id + \"')\",0)}\n");
				sb.append("	if ((document.all.q) && (17==event.keyCode) ) {\n");
				sb.append("		el.selection=document.selection.createRange();\n");
				sb.append("		setTimeout(\"ProcessCTL('\" + el.id + \"')\",0)}\n");
				sb.append("	if ((document.all.q) && (220==event.keyCode) ) {\n");
				sb.append("		el.selection=document.selection.createRange();\n");
				sb.append("		setTimeout(\"ProcessBackSlash('\" + el.id + \"')\",0)}\n");
				sb.append("	if ((document.all.q) && (45==event.keyCode) ) {\n");
				sb.append("	   document.execCommand(\"Overwrite\");\n");
				sb.append("      document.execCommand(\"Overwrite\");}\n");
				sb.append("if (event.keyCode==123)\n");
				sb.append("  {\n");
				sb.append("var x,y,i,z,j,m;\n");
				sb.append("m=0;\n");
				sb.append("y=0;\n");
				sb.append("  while(1)\n");
				sb.append("  {\n");
				sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
				sb.append("   {\n");
				sb.append("    m++;\n");
				sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
				sb.append("   }\n");
				sb.append("   else\n");
				sb.append("     break;\n");
				sb.append("  }\n");
				sb.append("x=caretPos(msg)-m+1;\n");
				sb.append("j=0;\n");
				sb.append("m=0;\n");
				sb.append("y=0;\n");
				sb.append("  while(j<x)\n");
				sb.append("  {\n");
				sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
				sb.append("   {\n");
				sb.append("    m++;\n");
				sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
				sb.append("     if ( y>x)\n");
				sb.append("        m--;\n");
				sb.append("   }\n");
				sb.append("   else\n");
				sb.append("     break;\n");
				sb.append("  }\n");
				sb.append("i=0;\n");
				sb.append(" if (msg.value.indexOf(\":\",x+1+m)!=-1)\n");
				sb.append(" {\n");
				sb.append("  z=msg.value.indexOf(\":\",x+1+m)+1;\n");
				sb.append("  y=0;\n");
				sb.append("  while(y<z)\n");
				sb.append("  {\n");
				sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
				sb.append("   {\n");
				sb.append("    i++;\n");
				sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
				sb.append("     if ( y>z)\n");
				sb.append("        i--;\n");
				sb.append("   }\n");
				sb.append("   else\n");
				sb.append("     break;\n");
				sb.append("  }\n");
				sb.append("  k=z-i;\n");
				sb.append("  }\n");
				sb.append("  else\n");
				sb.append("     k=0;\n");
				sb.append("x=k;\n");
				sb.append("if(el.setSelectionRange) {\n");
				sb.append("el.focus(); \n");
				sb.append("el.setSelectionRange(x,1); \n");
				sb.append("} \n");
				sb.append("else { \n");
				sb.append("if(el.createTextRange) { \n");
				sb.append("range=el.createTextRange(); \n");
				sb.append("range.collapse(true); \n");
				sb.append("range.moveEnd('character',1); \n");
				sb.append("range.moveStart('character',x); \n");
				sb.append("range.select(); \n");
				sb.append("return false; \n");
				sb.append("} \n");
				sb.append("return false;\n");
				sb.append("} \n");
				sb.append("return false; \n");
				sb.append("} \n");
				sb.append("function caretPos(msg){\n");
				sb.append("var i=msg.value.length+1;\n");
				sb.append("if (msg.createTextRange){\n");
				sb.append("theCaret = document.selection.createRange().duplicate();\n");
				sb.append("while ( theCaret.parentElement() == msg\n");
				sb.append("&& theCaret.move(\"character\",1)==1 ) --i;\n");
				sb.append("}\n");
				sb.append("return i==msg.value.length+1?-1:i;\n");
				sb.append("}\n");
				sb.append("}\n");
				sb.append("} \n");
				sb.append("function ProcessEnter(id)     \n");
				sb.append("{document.all.q.selection.text=String.fromCharCode(0)\n");
				sb.append("document.gs.submit()}\n");
				sb.append("function ProcessCTL(id)     \n");
				sb.append("{}\n");
				sb.append("function ProcessBackSlash(id)\n");
				sb.append("{sf()\n");
				sb.append("document.all.q.selection.text=String.fromCharCode(13)}\n");
				sb.append("</SCRIPT>\n");
				sb.append("<body onload=\"sf()\">\n");
				sb.append("<TABLE width=\"100%\" cellSpacing=0 cellPadding=0 border=0>\n");
				sb.append("<thead><tr>	<th>\n");
				sb.append("<TABLE align=\"left\" cellSpacing=0 cellPadding=0 border=0>\n");
				sb.append("<TBODY>	<TR><TD valign=\"top\">\n");
				sb.append("<img border=0 src=/company.gif></TD>\n");
				sb.append("<TD>&nbsp;&nbsp;</TD><TD valign=\"bottom\">\n");
				sb.append("<FORM name=gs action=/cgi-bin/term.cgi method=post>\n");
				sb.append("<input type=hidden name=Token value=EQUIPAJEUS4B04033400047725>\n");
				sb.append("<input type=hidden name=Type value=24>\n");
				sb.append("<textarea onkeydown=CheckKey(this,document.getElementById('q')) rows=24 name=q cols=64>WM ACTION FILE   ATLUS  -AP- D3/3\n");
				sb.append("\n");
				sb.append("3/16NOV09 2058GMT FROM DBCDL\n");
				sb.append("HDQUSAP\n");
				sb.append("****FOUND PROPERTY ISSUES ****\n");
				sb.append("PAX JERRY HILL 321 258 6111 LEFT A BK LEATHER COAT ON\n");
				sb.append("14NOV FLT US1629 OR US1046 PAX NOT SURE IF FOUND PLEASE\n");
				sb.append("CTC PAX/THANKS MELBA M/BOCC\n");
				sb.append("DISCLAIMER  DO NOT  REPLY TO THIS MESSAGE.\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("ERASE AND DISPLAY NEXT  EXF  \n");
				sb.append("TRANSFER TO FILE        TXF ... ...........  \n");
				sb.append("SEND TO TX/XF           DXF  .. ......../ ......../ ......../ \n");
				sb.append(">WMPN</textarea><input type=submit value=Send name=btnG style=arial>\n");
				sb.append("<input type=reset value=Reset name=B1 style=arial></FORM></TD>\n");
				sb.append("</TR>	</TBODY></TABLE></th>	</tr><tr>	<th>\n");
				sb.append("<hr color=\"#3366CC\"></th></tr></thead>\n");
				sb.append("<tbody><tr>	<td>\n");
				sb.append("<font face= \" courier \" ><PRE>&GTWM ACTION FILE   ATLUS  -AP- D3/3 \n");
				sb.append(" \n");
				sb.append("3/16NOV09 2058GMT FROM DBCDL \n");
				sb.append("HDQUSAP \n");
				sb.append("****FOUND PROPERTY ISSUES **** \n");
				sb.append("PAX JERRY HILL 321 258 6111 LEFT A BK LEATHER COAT ON \n");
				sb.append("14NOV FLT US1629 OR US1046 PAX NOT SURE IF FOUND PLEASE \n");
				sb.append("CTC PAX/THANKS MELBA M/BOCC \n");
				sb.append("DISCLAIMER  DO NOT  REPLY TO THIS MESSAGE. \n");
				sb.append(" \n");
				sb.append(" \n");
				sb.append(" \n");
				sb.append(" \n");
				sb.append(" \n");
				sb.append(" \n");
				sb.append(" \n");
				sb.append(" \n");
				sb.append(" \n");
				sb.append(" \n");
				sb.append("ERASE AND DISPLAY NEXT  EXF   \n");
				sb.append("TRANSFER TO FILE        TXF ... ...........   \n");
				sb.append("SEND TO TX/XF           DXF  .. ......../ ......../ ......../  \n");
				sb.append("&GTWMPN \n");
				sb.append("</PRE></td></tr></tbody><tfoot><tr><td><hr color=\"#3366CC\">\n");
				sb.append("</td>	</tr></tfoot></TABLE>\n");
				sb.append(" <td>\n");
				sb.append("  <table  style=\"border-collapse:collapse;border-width:0\" width=100%>\n");
				sb.append("    <tr>\n");
				sb.append("     <td width=40% style=\"border-style: none; border-width: medium\">\n");
				sb.append("      <form name=ls method=POST action=/cgi-bin/logoff.cgi>\n");
				sb.append("       <input type=hidden name=Token value=EQUIPAJEUS4B04033400047725>\n");
				sb.append("       <p align=right>\n");
				sb.append("       <input type=submit value=Logoff name=B1 style=font-family:Arial>\n");
				sb.append("       </p>\n");
				sb.append("      </form>\n");
				sb.append("     </td>\n");
				sb.append("     <td width=10% style=\"border-style: none; border-width: medium\">\n");
				sb.append("      <form name=ls method=POST action=/cgi-bin/login.cgi>\n");
				sb.append("       <input type=hidden name=Token value=EQUIPAJEUS4B04033400047725>\n");
				sb.append("       <p align=left>\n");
				sb.append("       <input type=submit value=Menu name=B1 style=font-family:Arial>\n");
				sb.append("       </p>\n");
				sb.append("      </form>\n");
				sb.append("     </td>\n");
				sb.append("      <td width=15% style=\"border-style: none; border-width: medium\">\n");
				sb.append("       <form name=ls method=POST action=/cgi-bin/term.cgi>\n");
				sb.append("       <input type=hidden name=Token value=EQUIPAJEUS4B04033400047725>\n");
				sb.append("       <p align=left>\n");
				sb.append("       <INPUT type=submit value=\"Form size\" name=b style=arial>\n");
				sb.append("       <select size=1 name=Type>\n");
				sb.append("       <option>1</option>\n");
				sb.append("       <option>2</option>\n");
				sb.append("       <option>4</option>\n");
				sb.append("       <option>12</option>\n");
				sb.append("       <option>24</option>\n");
				sb.append("       <option>255</option>\n");
				sb.append("       </select>\n");
				sb.append("       </p>\n");
				sb.append("       </form>\n");
				sb.append("      </td>\n");
				sb.append("      <td width=35% style=\"border-style: none; border-width: medium\">\n");
				sb.append("       <form name=ls method=POST action=/cgi-bin/term.cgi>\n");
				sb.append("       <input type=hidden name=Token value=EQUIPAJEUS4B04033400047725>\n");
				sb.append("       <p align=left>\n");
				sb.append("       <input type=submit value=Privacy name=Privacy style=Arial>\n");
				sb.append("       </p>\n");
				sb.append("       </form>\n");
				sb.append("      </td>\n");
				sb.append("      </tr>\n");
				sb.append("    </table>\n");
				sb.append("    </font></td>\n");
				sb.append("</body></html>\n");

				break;
			case 4:
				sb.append("<HTML\n");
				sb.append("><HEAD>\n");
				sb.append("<title>Web Terminal for NIAL MCLOUGHLIN using LNIATA D6BD01 (SHARES B production system . )</title><META http-equiv=msthemecompatible content=no></HEAD>\n");
				sb.append(" <SCRIPT>\n");
				sb.append("<!--\n");
				sb.append("function sf(){document.gs.q.focus();\n");
				sb.append("document.execCommand(\"Overwrite\");}\n");
				sb.append("function poponload()\n");
				sb.append("{\n");
				sb.append("testwindow= window.open (\"/cgi-bin/bpass.cgi?Token=EQUIPAJEUS4B04033400047725&\",\"mywindow\",\"location=1,status=1,scrollbars=1,width=1000,menubar=1,left=0,top=400\");\n");
				sb.append("}\n");
				sb.append("function CheckKey(msg,el) {\n");
				sb.append("	if ((document.all.q) && (220==event.keyCode) && (event.shiftKey)){}\n");
				sb.append("  else {\n");
				sb.append("	if ((document.all.q) && (13==event.keyCode) ) {\n");
				sb.append("		el.selection=document.selection.createRange();\n");
				sb.append("		setTimeout(\"ProcessEnter('\" + el.id + \"')\",0)}\n");
				sb.append("	if ((document.all.q) && (17==event.keyCode) ) {\n");
				sb.append("		el.selection=document.selection.createRange();\n");
				sb.append("		setTimeout(\"ProcessCTL('\" + el.id + \"')\",0)}\n");
				sb.append("	if ((document.all.q) && (220==event.keyCode) ) {\n");
				sb.append("		el.selection=document.selection.createRange();\n");
				sb.append("		setTimeout(\"ProcessBackSlash('\" + el.id + \"')\",0)}\n");
				sb.append("	if ((document.all.q) && (45==event.keyCode) ) {\n");
				sb.append("	   document.execCommand(\"Overwrite\");\n");
				sb.append("      document.execCommand(\"Overwrite\");}\n");
				sb.append("if (event.keyCode==123)\n");
				sb.append("  {\n");
				sb.append("var x,y,i,z,j,m;\n");
				sb.append("m=0;\n");
				sb.append("y=0;\n");
				sb.append("  while(1)\n");
				sb.append("  {\n");
				sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
				sb.append("   {\n");
				sb.append("    m++;\n");
				sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
				sb.append("   }\n");
				sb.append("   else\n");
				sb.append("     break;\n");
				sb.append("  }\n");
				sb.append("x=caretPos(msg)-m+1;\n");
				sb.append("j=0;\n");
				sb.append("m=0;\n");
				sb.append("y=0;\n");
				sb.append("  while(j<x)\n");
				sb.append("  {\n");
				sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
				sb.append("   {\n");
				sb.append("    m++;\n");
				sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
				sb.append("     if ( y>x)\n");
				sb.append("        m--;\n");
				sb.append("   }\n");
				sb.append("   else\n");
				sb.append("     break;\n");
				sb.append("  }\n");
				sb.append("i=0;\n");
				sb.append(" if (msg.value.indexOf(\":\",x+1+m)!=-1)\n");
				sb.append(" {\n");
				sb.append("  z=msg.value.indexOf(\":\",x+1+m)+1;\n");
				sb.append("  y=0;\n");
				sb.append("  while(y<z)\n");
				sb.append("  {\n");
				sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
				sb.append("   {\n");
				sb.append("    i++;\n");
				sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
				sb.append("     if ( y>z)\n");
				sb.append("        i--;\n");
				sb.append("   }\n");
				sb.append("   else\n");
				sb.append("     break;\n");
				sb.append("  }\n");
				sb.append("  k=z-i;\n");
				sb.append("  }\n");
				sb.append("  else\n");
				sb.append("     k=0;\n");
				sb.append("x=k;\n");
				sb.append("if(el.setSelectionRange) {\n");
				sb.append("el.focus(); \n");
				sb.append("el.setSelectionRange(x,1); \n");
				sb.append("} \n");
				sb.append("else { \n");
				sb.append("if(el.createTextRange) { \n");
				sb.append("range=el.createTextRange(); \n");
				sb.append("range.collapse(true); \n");
				sb.append("range.moveEnd('character',1); \n");
				sb.append("range.moveStart('character',x); \n");
				sb.append("range.select(); \n");
				sb.append("return false; \n");
				sb.append("} \n");
				sb.append("return false;\n");
				sb.append("} \n");
				sb.append("return false; \n");
				sb.append("} \n");
				sb.append("function caretPos(msg){\n");
				sb.append("var i=msg.value.length+1;\n");
				sb.append("if (msg.createTextRange){\n");
				sb.append("theCaret = document.selection.createRange().duplicate();\n");
				sb.append("while ( theCaret.parentElement() == msg\n");
				sb.append("&& theCaret.move(\"character\",1)==1 ) --i;\n");
				sb.append("}\n");
				sb.append("return i==msg.value.length+1?-1:i;\n");
				sb.append("}\n");
				sb.append("}\n");
				sb.append("} \n");
				sb.append("function ProcessEnter(id)     \n");
				sb.append("{document.all.q.selection.text=String.fromCharCode(0)\n");
				sb.append("document.gs.submit()}\n");
				sb.append("function ProcessCTL(id)     \n");
				sb.append("{}\n");
				sb.append("function ProcessBackSlash(id)\n");
				sb.append("{sf()\n");
				sb.append("document.all.q.selection.text=String.fromCharCode(13)}\n");
				sb.append("</SCRIPT>\n");
				sb.append("<body onload=\"sf()\">\n");
				sb.append("<TABLE width=\"100%\" cellSpacing=0 cellPadding=0 border=0>\n");
				sb.append("<thead><tr>	<th>\n");
				sb.append("<TABLE align=\"left\" cellSpacing=0 cellPadding=0 border=0>\n");
				sb.append("<TBODY>	<TR><TD valign=\"top\">\n");
				sb.append("<img border=0 src=/company.gif></TD>\n");
				sb.append("<TD>&nbsp;&nbsp;</TD><TD valign=\"bottom\">\n");
				sb.append("<FORM name=gs action=/cgi-bin/term.cgi method=post>\n");
				sb.append("<input type=hidden name=Token value=EQUIPAJEUS4B04033400047725>\n");
				sb.append("<input type=hidden name=Type value=24>\n");
				sb.append("<textarea onkeydown=CheckKey(this,document.getElementById('q')) rows=24 name=q cols=64></textarea><input type=submit value=Send name=btnG style=arial>\n");
				sb.append("<input type=reset value=Reset name=B1 style=arial></FORM></TD>\n");
				sb.append("</TR>	</TBODY></TABLE></th>	</tr><tr>	<th>\n");
				sb.append("<hr color=\"#3366CC\"></th></tr></thead>\n");
				sb.append("<tbody><tr>	<td>\n");
				sb.append("<font face= \" courier \" ><PRE> \n");
				sb.append(" \n");
				sb.append("ACTION FILE DISPLAY COMPLETE \n");
				sb.append("</PRE></td></tr></tbody><tfoot><tr><td><hr color=\"#3366CC\">\n");
				sb.append("</td>	</tr></tfoot></TABLE>\n");
				sb.append(" <td>\n");
				sb.append("  <table  style=\"border-collapse:collapse;border-width:0\" width=100%>\n");
				sb.append("    <tr>\n");
				sb.append("     <td width=40% style=\"border-style: none; border-width: medium\">\n");
				sb.append("      <form name=ls method=POST action=/cgi-bin/logoff.cgi>\n");
				sb.append("       <input type=hidden name=Token value=EQUIPAJEUS4B04033400047725>\n");
				sb.append("       <p align=right>\n");
				sb.append("       <input type=submit value=Logoff name=B1 style=font-family:Arial>\n");
				sb.append("       </p>\n");
				sb.append("      </form>\n");
				sb.append("     </td>\n");
				sb.append("     <td width=10% style=\"border-style: none; border-width: medium\">\n");
				sb.append("      <form name=ls method=POST action=/cgi-bin/login.cgi>\n");
				sb.append("       <input type=hidden name=Token value=EQUIPAJEUS4B04033400047725>\n");
				sb.append("       <p align=left>\n");
				sb.append("       <input type=submit value=Menu name=B1 style=font-family:Arial>\n");
				sb.append("       </p>\n");
				sb.append("      </form>\n");
				sb.append("     </td>\n");
				sb.append("      <td width=15% style=\"border-style: none; border-width: medium\">\n");
				sb.append("       <form name=ls method=POST action=/cgi-bin/term.cgi>\n");
				sb.append("       <input type=hidden name=Token value=EQUIPAJEUS4B04033400047725>\n");
				sb.append("       <p align=left>\n");
				sb.append("       <INPUT type=submit value=\"Form size\" name=b style=arial>\n");
				sb.append("       <select size=1 name=Type>\n");
				sb.append("       <option>1</option>\n");
				sb.append("       <option>2</option>\n");
				sb.append("       <option>4</option>\n");
				sb.append("       <option>12</option>\n");
				sb.append("       <option>24</option>\n");
				sb.append("       <option>255</option>\n");
				sb.append("       </select>\n");
				sb.append("       </p>\n");
				sb.append("       </form>\n");
				sb.append("      </td>\n");
				sb.append("      <td width=35% style=\"border-style: none; border-width: medium\">\n");
				sb.append("       <form name=ls method=POST action=/cgi-bin/term.cgi>\n");
				sb.append("       <input type=hidden name=Token value=EQUIPAJEUS4B04033400047725>\n");
				sb.append("       <p align=left>\n");
				sb.append("       <input type=submit value=Privacy name=Privacy style=Arial>\n");
				sb.append("       </p>\n");
				sb.append("       </form>\n");
				sb.append("      </td>\n");
				sb.append("      </tr>\n");
				sb.append("    </table>\n");
				sb.append("    </font></td>\n");
				sb.append("</body></html>\n");

				break;
			case 3:
				sb.append(">WM ACTION FILE   PHXUS  -WM- D1/2 \n");
				sb.append(" ");
				sb.append("1/  A/PHXUS46289 O/ORDUA39034 SCORE - 62 CT/FD/RT \n");
				sb.append("A/MOSINSKI   JM   LO208609  BK22HXX GDN/MUC/ORD LO395/16NOV \n");
				sb.append("O/                          BK22XXX ORD         YY/16NOV \n");
				sb.append("A/BR   UA2229/16NOV \n");
				sb.append("O/FI   X62BQZ \n");
				sb.append(" \n");
				sb.append("A/CC01 COAT \n");
				sb.append("\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("\n");
				sb.append("DISPLAY FILES           DMF   DISPLAY MATCH CONTENTS  DMC   ");
				sb.append("ERASE AND DISPLAY NEXT  EXF   TRANSFER TO FILE        TXF   ");
				sb.append("SEND TO TX/XF           DXF  .. ......../ ......../ ......../  ");
				sb.append(">WMPN ");
				sb.append("");

				break;
			
		}

		currentAfResponse += 1;
		if (currentAfResponse > 4) {
			currentAfResponse += 0;
		}
		Matcher m = WorldTracerServiceImpl.commandResponsePattern.matcher(sb.toString());
		if (m.find()) {
			return m.group(1);
		}
		return null;
	}
	
		public static String mockRequestBDOCommand_1(String command) throws CommandNotProperlyFormedException {
		
		command = testCommand(command);
		System.out.println("Command: \n" + command);
		StringBuffer sb = new StringBuffer();


		sb.append("<HTML\n");
		sb.append("><HEAD>\n");
		sb.append("<title>Web Terminal for NIAL MCLOUGHLIN using LNIATA D6BD00 (SHARES B production system . )</title><META http-equiv=msthemecompatible content=no></HEAD>\n");
		sb.append(" <SCRIPT>\n");
		sb.append("<!--\n");
		sb.append("function sf(){document.gs.q.focus();\n");
		sb.append("document.execCommand(\"Overwrite\");}\n");
		sb.append("function poponload()\n");
		sb.append("{\n");
		sb.append("testwindow= window.open (\"/cgi-bin/bpass.cgi?Token=EQUIPAJEUS4B0560B7000D06BC&\",\"mywindow\",\"location=1,status=1,scrollbars=1,width=1000,menubar=1,left=0,top=400\");\n");
		sb.append("}\n");
		sb.append("function CheckKey(msg,el) {\n");
		sb.append("	if ((document.all.q) && (220==event.keyCode) && (event.shiftKey)){}\n");
		sb.append("  else {\n");
		sb.append("	if ((document.all.q) && (13==event.keyCode) ) {\n");
		sb.append("		el.selection=document.selection.createRange();\n");
		sb.append("		setTimeout(\"ProcessEnter('\" + el.id + \"')\",0)}\n");
		sb.append("	if ((document.all.q) && (17==event.keyCode) ) {\n");
		sb.append("		el.selection=document.selection.createRange();\n");
		sb.append("		setTimeout(\"ProcessCTL('\" + el.id + \"')\",0)}\n");
		sb.append("	if ((document.all.q) && (220==event.keyCode) ) {\n");
		sb.append("		el.selection=document.selection.createRange();\n");
		sb.append("		setTimeout(\"ProcessBackSlash('\" + el.id + \"')\",0)}\n");
		sb.append("	if ((document.all.q) && (45==event.keyCode) ) {\n");
		sb.append("	   document.execCommand(\"Overwrite\");\n");
		sb.append("      document.execCommand(\"Overwrite\");}\n");
		sb.append("if (event.keyCode==123)\n");
		sb.append("  {\n");
		sb.append("var x,y,i,z,j,m;\n");
		sb.append("m=0;\n");
		sb.append("y=0;\n");
		sb.append("  while(1)\n");
		sb.append("  {\n");
		sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
		sb.append("   {\n");
		sb.append("    m++;\n");
		sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
		sb.append("   }\n");
		sb.append("   else\n");
		sb.append("     break;\n");
		sb.append("  }\n");
		sb.append("x=caretPos(msg)-m+1;\n");
		sb.append("j=0;\n");
		sb.append("m=0;\n");
		sb.append("y=0;\n");
		sb.append("  while(j<x)\n");
		sb.append("  {\n");
		sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
		sb.append("   {\n");
		sb.append("    m++;\n");
		sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
		sb.append("     if ( y>x)\n");
		sb.append("        m--;\n");
		sb.append("   }\n");
		sb.append("   else\n");
		sb.append("     break;\n");
		sb.append("  }\n");
		sb.append("i=0;\n");
		sb.append(" if (msg.value.indexOf(\":\",x+1+m)!=-1)\n");
		sb.append(" {\n");
		sb.append("  z=msg.value.indexOf(\":\",x+1+m)+1;\n");
		sb.append("  y=0;\n");
		sb.append("  while(y<z)\n");
		sb.append("  {\n");
		sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
		sb.append("   {\n");
		sb.append("    i++;\n");
		sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
		sb.append("     if ( y>z)\n");
		sb.append("        i--;\n");
		sb.append("   }\n");
		sb.append("   else\n");
		sb.append("     break;\n");
		sb.append("  }\n");
		sb.append("  k=z-i;\n");
		sb.append("  }\n");
		sb.append("  else\n");
		sb.append("     k=0;\n");
		sb.append("x=k;\n");
		sb.append("if(el.setSelectionRange) {\n");
		sb.append("el.focus(); \n");
		sb.append("el.setSelectionRange(x,1); \n");
		sb.append("} \n");
		sb.append("else { \n");
		sb.append("if(el.createTextRange) { \n");
		sb.append("range=el.createTextRange(); \n");
		sb.append("range.collapse(true); \n");
		sb.append("range.moveEnd('character',1); \n");
		sb.append("range.moveStart('character',x); \n");
		sb.append("range.select(); \n");
		sb.append("return false; \n");
		sb.append("} \n");
		sb.append("return false;\n");
		sb.append("} \n");
		sb.append("return false; \n");
		sb.append("} \n");
		sb.append("function caretPos(msg){\n");
		sb.append("var i=msg.value.length+1;\n");
		sb.append("if (msg.createTextRange){\n");
		sb.append("theCaret = document.selection.createRange().duplicate();\n");
		sb.append("while ( theCaret.parentElement() == msg\n");
		sb.append("&& theCaret.move(\"character\",1)==1 ) --i;\n");
		sb.append("}\n");
		sb.append("return i==msg.value.length+1?-1:i;\n");
		sb.append("}\n");
		sb.append("}\n");
		sb.append("} \n");
		sb.append("function ProcessEnter(id)     \n");
		sb.append("{document.all.q.selection.text=String.fromCharCode(0)\n");
		sb.append("document.gs.submit()}\n");
		sb.append("function ProcessCTL(id)     \n");
		sb.append("{}\n");
		sb.append("function ProcessBackSlash(id)\n");
		sb.append("{sf()\n");
		sb.append("document.all.q.selection.text=String.fromCharCode(13)}\n");
		sb.append("</SCRIPT>\n");
		sb.append("<body onload=\"sf()\">\n");
		sb.append("<TABLE width=\"100%\" cellSpacing=0 cellPadding=0 border=0>\n");
		sb.append("<thead><tr>	<th>\n");
		sb.append("<TABLE align=\"left\" cellSpacing=0 cellPadding=0 border=0>\n");
		sb.append("<TBODY>	<TR><TD valign=\"top\">\n");
		sb.append("<img border=0 src=/company.gif></TD>\n");
		sb.append("<TD>&nbsp;&nbsp;</TD><TD valign=\"bottom\">\n");
		sb.append("<FORM name=gs action=/cgi-bin/term.cgi method=post>\n");
		sb.append("<input type=hidden name=Token value=EQUIPAJEUS4B0560B7000D06BC>\n");
		sb.append("<input type=hidden name=Type value=12>\n");
		sb.append("<textarea onkeydown=CheckKey(this,document.getElementById('q')) rows=12 name=q cols=64>WM BDO AHL XAXUS10485  /1515GMT/19NOV09\n");
		sb.append("DS XAXUS01 - SPEEDY DELIVERY TEST\n");
		sb.append("US AIRWAYS-XAX TEST\n");
		sb.append("SVC LVL STD/TN\n");
		sb.append("NM01 TOMTESTER\n");
		sb.append("PA01 123 TESTER WAY\n");
		sb.append("DD        .DW \n");
		sb.append("LD01 \n");
		sb.append("CT01 BK22\n");
		sb.append(" /RECHARGE -   /SIGNATURE FOR RECEIPT OF 1 BAG   .RT CLT\n");
		sb.append("TX                                       .AG \n");
		sb.append("</textarea><input type=submit value=Send name=btnG style=arial>\n");
		sb.append("<input type=reset value=Reset name=B1 style=arial></FORM></TD>\n");
		sb.append("</TR>	</TBODY></TABLE></th>	</tr><tr>	<th>\n");
		sb.append("<hr color=\"#3366CC\"></th></tr></thead>\n");
		sb.append("<tbody><tr>	<td>\n");
		sb.append("<font face= \" courier \" ><PRE>&GTWM BDO AHL XAXUS10485  /1515GMT/19NOV09 \n");
		sb.append("DS XAXUS01 - SPEEDY DELIVERY TEST \n");
		sb.append("US AIRWAYS-XAX TEST \n");
		sb.append("SVC LVL STD/TN \n");
		sb.append("NM01 TOMTESTER \n");
		sb.append("PA01 123 TESTER WAY \n");
		sb.append("DD        .DW  \n");
		sb.append("LD01  \n");
		sb.append("CT01 BK22 \n");
		sb.append(" /RECHARGE -   /SIGNATURE FOR RECEIPT OF 1 BAG   .RT CLT \n");
		sb.append("TX                                       .AG  \n");
		sb.append("</PRE></td></tr></tbody><tfoot><tr><td><hr color=\"#3366CC\">\n");
		sb.append("</td>	</tr></tfoot></TABLE>\n");
		sb.append(" <td>\n");
		sb.append("  <table  style=\"border-collapse:collapse;border-width:0\" width=100%>\n");
		sb.append("    <tr>\n");
		sb.append("     <td width=40% style=\"border-style: none; border-width: medium\">\n");
		sb.append("      <form name=ls method=POST action=/cgi-bin/logoff.cgi>\n");
		sb.append("       <input type=hidden name=Token value=EQUIPAJEUS4B0560B7000D06BC>\n");
		sb.append("       <p align=right>\n");
		sb.append("       <input type=submit value=Logoff name=B1 style=font-family:Arial>\n");
		sb.append("       </p>\n");
		sb.append("      </form>\n");
		sb.append("     </td>\n");
		sb.append("     <td width=10% style=\"border-style: none; border-width: medium\">\n");
		sb.append("      <form name=ls method=POST action=/cgi-bin/login.cgi>\n");
		sb.append("       <input type=hidden name=Token value=EQUIPAJEUS4B0560B7000D06BC>\n");
		sb.append("       <p align=left>\n");
		sb.append("       <input type=submit value=Menu name=B1 style=font-family:Arial>\n");
		sb.append("       </p>\n");
		sb.append("      </form>\n");
		sb.append("     </td>\n");
		sb.append("      <td width=15% style=\"border-style: none; border-width: medium\">\n");
		sb.append("       <form name=ls method=POST action=/cgi-bin/term.cgi>\n");
		sb.append("       <input type=hidden name=Token value=EQUIPAJEUS4B0560B7000D06BC>\n");
		sb.append("       <p align=left>\n");
		sb.append("       <INPUT type=submit value=\"Form size\" name=b style=arial>\n");
		sb.append("       <select size=1 name=Type>\n");
		sb.append("       <option>1</option>\n");
		sb.append("       <option>2</option>\n");
		sb.append("       <option>4</option>\n");
		sb.append("       <option>12</option>\n");
		sb.append("       <option>24</option>\n");
		sb.append("       <option>255</option>\n");
		sb.append("       </select>\n");
		sb.append("       </p>\n");
		sb.append("       </form>\n");
		sb.append("      </td>\n");
		sb.append("      <td width=35% style=\"border-style: none; border-width: medium\">\n");
		sb.append("       <form name=ls method=POST action=/cgi-bin/term.cgi>\n");
		sb.append("       <input type=hidden name=Token value=EQUIPAJEUS4B0560B7000D06BC>\n");
		sb.append("       <p align=left>\n");
		sb.append("       <input type=submit value=Privacy name=Privacy style=Arial>\n");
		sb.append("       </p>\n");
		sb.append("       </form>\n");
		sb.append("      </td>\n");
		sb.append("      </tr>\n");
		sb.append("    </table>\n");
		sb.append("    </font></td>\n");
		sb.append("</body></html>\n");
		sb.append(" \n");
		sb.append("\n");


		

		Matcher m = WorldTracerServiceImpl.commandResponsePattern.matcher(sb.toString());
		if (m.find()) {
			return m.group(1);
		}
		return null;

	}
	public static String mockRequestBDOCommand_2(String command) throws CommandNotProperlyFormedException {
		
		command = testCommand(command);
		System.out.println("Command: \n" + command);
		StringBuffer sb = new StringBuffer();

		sb.append("<HTML\n");
		sb.append("><HEAD>\n");
		sb.append("<title>Web Terminal for NIAL MCLOUGHLIN using LNIATA D6BD00 (SHARES B production system . )</title><META http-equiv=msthemecompatible content=no></HEAD>\n");
		sb.append(" <SCRIPT>\n");
		sb.append("<!--\n");
		sb.append("function sf(){document.gs.q.focus();\n");
		sb.append("document.execCommand(\"Overwrite\");}\n");
		sb.append("function poponload()\n");
		sb.append("{\n");
		sb.append("testwindow= window.open (\"/cgi-bin/bpass.cgi?Token=EQUIPAJEUS4B0560B7000D06BC&\",\"mywindow\",\"location=1,status=1,scrollbars=1,width=1000,menubar=1,left=0,top=400\");\n");
		sb.append("}\n");
		sb.append("function CheckKey(msg,el) {\n");
		sb.append("	if ((document.all.q) && (220==event.keyCode) && (event.shiftKey)){}\n");
		sb.append("  else {\n");
		sb.append("	if ((document.all.q) && (13==event.keyCode) ) {\n");
		sb.append("		el.selection=document.selection.createRange();\n");
		sb.append("		setTimeout(\"ProcessEnter('\" + el.id + \"')\",0)}\n");
		sb.append("	if ((document.all.q) && (17==event.keyCode) ) {\n");
		sb.append("		el.selection=document.selection.createRange();\n");
		sb.append("		setTimeout(\"ProcessCTL('\" + el.id + \"')\",0)}\n");
		sb.append("	if ((document.all.q) && (220==event.keyCode) ) {\n");
		sb.append("		el.selection=document.selection.createRange();\n");
		sb.append("		setTimeout(\"ProcessBackSlash('\" + el.id + \"')\",0)}\n");
		sb.append("	if ((document.all.q) && (45==event.keyCode) ) {\n");
		sb.append("	   document.execCommand(\"Overwrite\");\n");
		sb.append("      document.execCommand(\"Overwrite\");}\n");
		sb.append("if (event.keyCode==123)\n");
		sb.append("  {\n");
		sb.append("var x,y,i,z,j,m;\n");
		sb.append("m=0;\n");
		sb.append("y=0;\n");
		sb.append("  while(1)\n");
		sb.append("  {\n");
		sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
		sb.append("   {\n");
		sb.append("    m++;\n");
		sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
		sb.append("   }\n");
		sb.append("   else\n");
		sb.append("     break;\n");
		sb.append("  }\n");
		sb.append("x=caretPos(msg)-m+1;\n");
		sb.append("j=0;\n");
		sb.append("m=0;\n");
		sb.append("y=0;\n");
		sb.append("  while(j<x)\n");
		sb.append("  {\n");
		sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
		sb.append("   {\n");
		sb.append("    m++;\n");
		sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
		sb.append("     if ( y>x)\n");
		sb.append("        m--;\n");
		sb.append("   }\n");
		sb.append("   else\n");
		sb.append("     break;\n");
		sb.append("  }\n");
		sb.append("i=0;\n");
		sb.append(" if (msg.value.indexOf(\":\",x+1+m)!=-1)\n");
		sb.append(" {\n");
		sb.append("  z=msg.value.indexOf(\":\",x+1+m)+1;\n");
		sb.append("  y=0;\n");
		sb.append("  while(y<z)\n");
		sb.append("  {\n");
		sb.append("   if (msg.value.indexOf(\"\r\",y)!=-1)\n");
		sb.append("   {\n");
		sb.append("    i++;\n");
		sb.append("    y=msg.value.indexOf(\"\r\",y)+1;\n");
		sb.append("     if ( y>z)\n");
		sb.append("        i--;\n");
		sb.append("   }\n");
		sb.append("   else\n");
		sb.append("     break;\n");
		sb.append("  }\n");
		sb.append("  k=z-i;\n");
		sb.append("  }\n");
		sb.append("  else\n");
		sb.append("     k=0;\n");
		sb.append("x=k;\n");
		sb.append("if(el.setSelectionRange) {\n");
		sb.append("el.focus(); \n");
		sb.append("el.setSelectionRange(x,1); \n");
		sb.append("} \n");
		sb.append("else { \n");
		sb.append("if(el.createTextRange) { \n");
		sb.append("range=el.createTextRange(); \n");
		sb.append("range.collapse(true); \n");
		sb.append("range.moveEnd('character',1); \n");
		sb.append("range.moveStart('character',x); \n");
		sb.append("range.select(); \n");
		sb.append("return false; \n");
		sb.append("} \n");
		sb.append("return false;\n");
		sb.append("} \n");
		sb.append("return false; \n");
		sb.append("} \n");
		sb.append("function caretPos(msg){\n");
		sb.append("var i=msg.value.length+1;\n");
		sb.append("if (msg.createTextRange){\n");
		sb.append("theCaret = document.selection.createRange().duplicate();\n");
		sb.append("while ( theCaret.parentElement() == msg\n");
		sb.append("&& theCaret.move(\"character\",1)==1 ) --i;\n");
		sb.append("}\n");
		sb.append("return i==msg.value.length+1?-1:i;\n");
		sb.append("}\n");
		sb.append("}\n");
		sb.append("} \n");
		sb.append("function ProcessEnter(id)     \n");
		sb.append("{document.all.q.selection.text=String.fromCharCode(0)\n");
		sb.append("document.gs.submit()}\n");
		sb.append("function ProcessCTL(id)     \n");
		sb.append("{}\n");
		sb.append("function ProcessBackSlash(id)\n");
		sb.append("{sf()\n");
		sb.append("document.all.q.selection.text=String.fromCharCode(13)}\n");
		sb.append("</SCRIPT>\n");
		sb.append("<body onload=\"sf()\">\n");
		sb.append("<TABLE width=\"100%\" cellSpacing=0 cellPadding=0 border=0>\n");
		sb.append("<thead><tr>	<th>\n");
		sb.append("<TABLE align=\"left\" cellSpacing=0 cellPadding=0 border=0>\n");
		sb.append("<TBODY>	<TR><TD valign=\"top\">\n");
		sb.append("<img border=0 src=/company.gif></TD>\n");
		sb.append("<TD>&nbsp;&nbsp;</TD><TD valign=\"bottom\">\n");
		sb.append("<FORM name=gs action=/cgi-bin/term.cgi method=post>\n");
		sb.append("<input type=hidden name=Token value=EQUIPAJEUS4B0560B7000D06BC>\n");
		sb.append("<input type=hidden name=Type value=12>\n");
		sb.append("<textarea onkeydown=CheckKey(this,document.getElementById('q')) rows=12 name=q cols=64>WM BDO AHL XAXUS10485                   /-OK-/19NOV09 1516GMT</textarea><input type=submit value=Send name=btnG style=arial>\n");
		sb.append("<input type=reset value=Reset name=B1 style=arial></FORM></TD>\n");
		sb.append("</TR>	</TBODY></TABLE></th>	</tr><tr>	<th>\n");
		sb.append("<hr color=\"#3366CC\"></th></tr></thead>\n");
		sb.append("<tbody><tr>	<td>\n");
		sb.append("<font face= \" courier \" ><PRE>&GTWM BDO AHL XAXUS10485                   /-OK-/19NOV09 1516GMT \n");
		sb.append("</PRE></td></tr></tbody><tfoot><tr><td><hr color=\"#3366CC\">\n");
		sb.append("</td>	</tr></tfoot></TABLE>\n");
		sb.append(" <td>\n");
		sb.append("  <table  style=\"border-collapse:collapse;border-width:0\" width=100%>\n");
		sb.append("    <tr>\n");
		sb.append("     <td width=40% style=\"border-style: none; border-width: medium\">\n");
		sb.append("      <form name=ls method=POST action=/cgi-bin/logoff.cgi>\n");
		sb.append("       <input type=hidden name=Token value=EQUIPAJEUS4B0560B7000D06BC>\n");
		sb.append("       <p align=right>\n");
		sb.append("       <input type=submit value=Logoff name=B1 style=font-family:Arial>\n");
		sb.append("       </p>\n");
		sb.append("      </form>\n");
		sb.append("     </td>\n");
		sb.append("     <td width=10% style=\"border-style: none; border-width: medium\">\n");
		sb.append("      <form name=ls method=POST action=/cgi-bin/login.cgi>\n");
		sb.append("       <input type=hidden name=Token value=EQUIPAJEUS4B0560B7000D06BC>\n");
		sb.append("       <p align=left>\n");
		sb.append("       <input type=submit value=Menu name=B1 style=font-family:Arial>\n");
		sb.append("       </p>\n");
		sb.append("      </form>\n");
		sb.append("     </td>\n");
		sb.append("      <td width=15% style=\"border-style: none; border-width: medium\">\n");
		sb.append("       <form name=ls method=POST action=/cgi-bin/term.cgi>\n");
		sb.append("       <input type=hidden name=Token value=EQUIPAJEUS4B0560B7000D06BC>\n");
		sb.append("       <p align=left>\n");
		sb.append("       <INPUT type=submit value=\"Form size\" name=b style=arial>\n");
		sb.append("       <select size=1 name=Type>\n");
		sb.append("       <option>1</option>\n");
		sb.append("       <option>2</option>\n");
		sb.append("       <option>4</option>\n");
		sb.append("       <option>12</option>\n");
		sb.append("       <option>24</option>\n");
		sb.append("       <option>255</option>\n");
		sb.append("       </select>\n");
		sb.append("       </p>\n");
		sb.append("       </form>\n");
		sb.append("      </td>\n");
		sb.append("      <td width=35% style=\"border-style: none; border-width: medium\">\n");
		sb.append("       <form name=ls method=POST action=/cgi-bin/term.cgi>\n");
		sb.append("       <input type=hidden name=Token value=EQUIPAJEUS4B0560B7000D06BC>\n");
		sb.append("       <p align=left>\n");
		sb.append("       <input type=submit value=Privacy name=Privacy style=Arial>\n");
		sb.append("       </p>\n");
		sb.append("       </form>\n");
		sb.append("      </td>\n");
		sb.append("      </tr>\n");
		sb.append("    </table>\n");
		sb.append("    </font></td>\n");
		sb.append("</body></html>\n");
		sb.append(" \n");
		sb.append("\n");



		Matcher m = WorldTracerServiceImpl.commandResponsePattern.matcher(sb.toString());
		if (m.find()) {
			return m.group(1);
		}
		return null;

	}
	
}
