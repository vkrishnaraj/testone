<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<tr>
  <td colspan="3" id="pageheadercell">
    <div id="pageheaderleft">
      <h1>
        <bean:message key="header.taskmanager" />
      </h1>
    </div>
    <div id="pageheaderright">
      <table id="pageheaderright">
        <tr>
          <td></td>
          <td></td>
          <jsp:include page="/pages/includes/mail_incl.jsp" />
          <td>
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><bean:message key="Help" /></a>
          </td>
        </tr>
      </table>
    </div>
  </td>
</tr>
<!-- END PAGE HEADER/SEARCH -->
<!-- ICONS MENU -->
<logic:present name="activityList" scope="session">
  <tr>
    <td colspan="3" id="navmenucell">
      <div class="menu">
        <dl>
<%
          String m = "";
		
			String cw = "",fw = "",aa="",wm="",sp="",ap="",cm="",em="",lm="",pr="";
	
			String addon = "";
			
          if (request.getAttribute("maintask") != null) {
            m = "b";
          }
          
          if (request.getParameter("ahl_id") != null && ((String)request.getParameter("ahl_id")).length() == 10) {
          	addon = "&ahl_id=" + request.getParameter("ahl_id");
          }
          if (request.getParameter("ohd_id") != null && ((String)request.getParameter("ohd_id")).length() == 10) {
          	addon += "&ohd_id=" + request.getParameter("ohd_id");
          }
          
          
          if (request.getAttribute("wt_type") != null && request.getAttribute("wt_type").equals("FW")) {
          	fw = "b";
          } else if (request.getAttribute("wt_type") != null && request.getAttribute("wt_type").equals("AA")) {
          	aa = "b";
          } else if (request.getAttribute("wt_type") != null && request.getAttribute("wt_type").equals("WM")) {
          	wm = "b";
          } else if (request.getAttribute("wt_type") != null && request.getAttribute("wt_type").equals("SP")) {
          	sp = "b";
          } else if (request.getAttribute("wt_type") != null && request.getAttribute("wt_type").equals("AP")) {
          	ap = "b";
          } else if (request.getAttribute("wt_type") != null && request.getAttribute("wt_type").equals("CM")) {
          	cm = "b";  	
		   } else if (request.getAttribute("wt_type") != null && request.getAttribute("wt_type").equals("EM")) {
		  	 em = "b";
		   } else if (request.getAttribute("wt_type") != null && request.getAttribute("wt_type").equals("LM")) {
		  	 lm = "b";
		   } else if (request.getAttribute("wt_type") != null && request.getAttribute("wt_type").equals("PR")) {
		  	 pr = "b";
		   }else if(request.getAttribute("wt_type")!=null&&request.getAttribute("wt_type").equals("CW")){
		   	 cw="b";
		   }
%>
          <dd>
            <a href="logon.do?taskmanager=1"><span class="aa<%= m %>">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bb<%= m %>"><bean:message key="menu.taskmanagerhome" /></span>
              <span class="cc<%= m %>">&nbsp;
                <br />
                &nbsp;</span></a>
          </dd>
          <dd>
            <a href="worldtracercount.do?viewaction=cw"><span class="aa<%= cw %>">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bb<%= cw %>"><bean:message key="menu.wt_count" /></span>
              <span class="cc<%= cw %>">&nbsp;
                <br />
                &nbsp;</span></a>
         	</dd>
           <dd>
            <a href="worldtraceraf.do?viewaction=fw<%=addon %>"><span class="aa<%= fw %>">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bb<%= fw %>"><bean:message key="menu.wt_fw" /></span>
              <span class="cc<%= fw %>">&nbsp;
                <br />
                &nbsp;</span></a>
         	</dd>
         	<dd>
            <a href="worldtraceraf.do?viewaction=aa<%=addon %>"><span class="aa<%= aa %>">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bb<%= aa %>"><bean:message key="menu.wt_aa" /></span>
              <span class="cc<%= aa %>">&nbsp;
                <br />
                &nbsp;</span></a>
         	</dd>
         	<dd>
            <a href="worldtraceraf.do?viewaction=wm<%=addon %>"><span class="aa<%= wm %>">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bb<%= wm %>"><bean:message key="menu.wt_wm" /></span>
              <span class="cc<%= wm %>">&nbsp;
                <br />
                &nbsp;</span></a>
         	</dd>
         	<dd>
            <a href="worldtraceraf.do?viewaction=em<%=addon %>"><span class="aa<%= em %>">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bb<%= em %>"><bean:message key="menu.wt_em" /></span>
              <span class="cc<%= em %>">&nbsp;
                <br />
                &nbsp;</span></a>
         	</dd>
         	<dd>
            <a href="worldtraceraf.do?viewaction=sp<%=addon %>"><span class="aa<%= sp %>">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bb<%= sp %>"><bean:message key="menu.wt_sp" /></span>
              <span class="cc<%= sp %>">&nbsp;
                <br />
                &nbsp;</span></a>
         	</dd>
         	<dd>
            <a href="worldtraceraf.do?viewaction=ap<%=addon %>"><span class="aa<%= ap %>">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bb<%= ap %>"><bean:message key="menu.wt_ap" /></span>
              <span class="cc<%= ap %>">&nbsp;
                <br />
                &nbsp;</span></a>
         	</dd>
         	<dd>
            <a href="worldtraceraf.do?viewaction=cm<%=addon %>"><span class="aa<%= cm %>">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bb<%= cm %>"><bean:message key="menu.wt_cm" /></span>
              <span class="cc<%= cm %>">&nbsp;
                <br />
                &nbsp;</span></a>
         	</dd>
         	<dd>
            <a href="worldtraceraf.do?viewaction=lm<%=addon %>"><span class="aa<%= lm %>">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bb<%= lm %>"><bean:message key="menu.wt_lm" /></span>
              <span class="cc<%= lm %>">&nbsp;
                <br />
                &nbsp;</span></a>
         	</dd>
         	<dd>
            <a href="worldtraceraf.do?viewaction=pr<%=addon %>"><span class="aa<%= pr %>">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bb<%= pr %>"><bean:message key="menu.wt_pr" /></span>
              <span class="cc<%= pr %>">&nbsp;
                <br />
                &nbsp;</span></a>
         	</dd>
          
          
        </dl>
      </div>
    </td>
  </tr>
</logic:present>
<!-- END ICONS MENU -->
