<%@ page language="java" %>
<%@ page import="com.bagnet.nettracer.tracing.db.ExpensePayout" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.OHD_Photo" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%
  Agent a = (Agent)session.getAttribute("user");
%>
  <!-- Calendar includes -->
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
    <!--
  var cal1xx = new CalendarPopup(); 

    // -->
  </SCRIPT>
            <tr>
              <td colspan="3" id="navmenucell">
                <div class="menu">
                  <dl>
                    <dd>
                      <a href='searchIncident.do?incident='><span class="aa">&nbsp;
                          <br />
                          &nbsp;</span>
                        <span class="bb"><bean:message key="menu.incident_info" /></span>
                        <span class="cc">&nbsp;
                          <br />
                          &nbsp;</span></a>
                    </dd>

                    <dd>
                      <a href="claim_resolution.do"><span class="aa">&nbsp;
                          <br />
                          &nbsp;</span>
                        <span class="bb"><bean:message key="menu.claim_payout" /></span>
                        <span class="cc">&nbsp;
                          <br />
                          &nbsp;</span></a>
                    </dd>
<%
                      if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CLAIM_PRORATE, a)) {
%>
                        <dd>
                          <a href="claim_prorate.do"><span class="aa">&nbsp;
                              <br />
                              &nbsp;</span>
                            <span class="bb"><bean:message key="menu.claim_prorate" /></span>
                            <span class="cc">&nbsp;
                              <br />
                              &nbsp;</span></a>
                        </dd>
<%
                      }
%>

                    <dd>
                      <a href="claim_settlement.do?screen=1"><span class="aa">&nbsp;
                          <br />
                          &nbsp;</span>
                        <span class="bb"><bean:message key="menu.claim_process" /></span>
                        <span class="cc">&nbsp;
                          <br />
                          &nbsp;</span></a>
                    </dd>
                    
                    <dd>
                      <a href="claim_settlement.do?screen=2"><span class="aa">&nbsp;
                          <br />
                          &nbsp;</span>
                        <span class="bb"><bean:message key="menu.claim_customer" /></span>
                        <span class="cc">&nbsp;
                          <br />
                          &nbsp;</span></a>
                    </dd>

                    <dd>
                      <a href="#"><span class="aab">&nbsp;
                          <br />
                          &nbsp;</span>
                        <span class="bbb"><bean:message key="menu.claim_baggage" /></span>
                        <span class="ccb">&nbsp;
                          <br />
                          &nbsp;</span></a>
                    </dd>
                    
                                        <dd>
                      <a href="claim_settlement.do?screen=4"><span class="aa">&nbsp;
                          <br />
                          &nbsp;</span>
                        <span class="bb"><bean:message key="menu.claim_summary" /></span>
                        <span class="cc">&nbsp;
                          <br />
                          &nbsp;</span></a>
                    </dd>

                  </dl>

            
            
            <!-- END ICONS MENU -->
            <tr>
              <!-- MIDDLE COLUMN -->
              <td id="middlecolumn">
                <!-- MAIN BODY -->
                <div id="maincontent">

          <html:form action="claim_settlement.do" method="post">


          <h1 class="green">Baggage Information</h1>
          
	  <table class="form2" cellspacing="0" cellpadding="0" >
		  <tr>
			  <td colspan="2" class="header">
				  <strong>Baggage Information on File</strong>
			  </td>
		  </tr>
    <tr>
      <td width=33%>
        Number of Bags Reported Lost
      </td>
      <td width=66%>
      	2
      </td>
    </tr>
    <tr>
      <td width=33%>
        Bag Tag Numbers on File
      </td>
      <td width=66%>
      	B6000001,  B6000002
      </td>
    </tr>


  </table>

          <p />&nbsp;

  <!-- BAG 1 -->
  <h1 class="green">Bag Number: 1</h1>

  	  <table class="form2" cellspacing="0" cellpadding="0" >
		  <tr>
			  <td colspan="2" class="header">
				  <strong>Baggage Description on File</strong>
			  </td>
		  </tr>
    <tr>
      <td width=33%>
        Color 
      </td>
      <td width=66%>
      	BU
      </td>
    </tr>
    <tr>
      <td width=33%>
        Manufacturer
      </td>
      <td width=66%>
	Tummi
      </td>
    </tr>
    <tr>
      <td width=33%>
        Bag Status
      </td>
      <td width=66%>
	Open
      </td>
    </tr>
		  <tr>
			  <td colspan="2" class="header">
				  <strong>Contents</strong>
			  </td>
		  </tr>
		  <tr>
			  <td colspan="2">
<table width="100%">
	              <tr>
	                <td>
	                  Content Category<br>
	                  <select name="inventorylist[0].categorytype_ID" class="dropdown"><option value="">Please Select</option>
	                    <option value="1">Alcohol</option>
<option value="2">Art</option>
<option value="3">Audio</option>
<option value="4">Book</option>
<option value="5">Coat/Jacket</option>
<option value="6">Computer</option>
<option value="7">Cosmetics</option>
<option value="8">Currency</option>
<option value="9">Dress</option>
<option value="10">Electric</option>
<option value="11">Food</option>
<option value="12">Footwear</option>
<option value="13">Gift</option>
<option value="14">Hair</option>
<option value="15">Handbag</option>
<option value="16">Headwear</option>
<option value="17">Household</option>
<option value="18">Infant</option>
<option value="19">Jewelry</option>
<option value="20">Linen</option>
<option value="21">Mechanic</option>
<option value="47">Medicine</option>
<option value="23">Music</option>
<option value="24">Nature</option>
<option value="25">Optics</option>
<option value="26">Papers</option>
<option value="27">Photo</option>
<option value="28">Religious</option>
<option value="29">Shirt</option>
<option value="30">Skirt</option>
<option value="31">Sleepwear</option>
<option value="32">Sport</option>
<option value="33">Sportswear</option>
<option value="34">Suit</option>
<option value="36">Sweater</option>
<option value="37">Timepiece</option>
<option value="38">Tobacco</option>
<option value="39">Tools</option>
<option value="40">Toys</option>
<option value="41">Trousers</option>
<option value="46">Undergarments</option>
<option value="42">Uniform</option>
<option value="43">Video</option>
<option value="45">Weapon</option>
<option value="44">Weather</option></select>
	                </td>
	                <td>
	                  
	                  Content Description
	                  
	                  <br>
	                  <input type="text" name="inventorylist[0].description" maxlength="255" size="60" value="" class="textfield">
	                </td>
	                <td align="center">&nbsp;<br>
	                	<input type="submit" name="deleteinventory_0_0" id="button" value="Delete Content">
	      
	                </td>
	              </tr>
	              <tr>
	                <td>
	                  Content Category<br>
	                  <select name="inventorylist[0].categorytype_ID" class="dropdown"><option value="">Please Select</option>
	                    <option value="1">Alcohol</option>
<option value="2">Art</option>
<option value="3">Audio</option>
<option value="4">Book</option>
<option value="5">Coat/Jacket</option>
<option value="6">Computer</option>
<option value="7">Cosmetics</option>
<option value="8">Currency</option>
<option value="9">Dress</option>
<option value="10">Electric</option>
<option value="11">Food</option>
<option value="12">Footwear</option>
<option value="13">Gift</option>
<option value="14">Hair</option>
<option value="15">Handbag</option>
<option value="16">Headwear</option>
<option value="17">Household</option>
<option value="18">Infant</option>
<option value="19">Jewelry</option>
<option value="20">Linen</option>
<option value="21">Mechanic</option>
<option value="47">Medicine</option>
<option value="23">Music</option>
<option value="24">Nature</option>
<option value="25">Optics</option>
<option value="26">Papers</option>
<option value="27">Photo</option>
<option value="28">Religious</option>
<option value="29">Shirt</option>
<option value="30">Skirt</option>
<option value="31">Sleepwear</option>
<option value="32">Sport</option>
<option value="33">Sportswear</option>
<option value="34">Suit</option>
<option value="36">Sweater</option>
<option value="37">Timepiece</option>
<option value="38">Tobacco</option>
<option value="39">Tools</option>
<option value="40">Toys</option>
<option value="41">Trousers</option>
<option value="46">Undergarments</option>
<option value="42">Uniform</option>
<option value="43">Video</option>
<option value="45">Weapon</option>
<option value="44">Weather</option></select>
	                </td>
	                <td>
	                  
	                  Content Description
	                  
	                  <br>
	                  <input type="text" name="inventorylist[0].description" maxlength="255" size="60" value="" class="textfield">
	                </td>
	                <td align="center">&nbsp;<br>
	                	<input type="submit" name="deleteinventory_0_0" id="button" value="Delete Content">
	      
	                </td>
	              </tr>
	              <tr>
	                <td>
	                  Content Category<br>
	                  <select name="inventorylist[0].categorytype_ID" class="dropdown"><option value="">Please Select</option>
	                    <option value="1">Alcohol</option>
<option value="2">Art</option>
<option value="3">Audio</option>
<option value="4">Book</option>
<option value="5">Coat/Jacket</option>
<option value="6">Computer</option>
<option value="7">Cosmetics</option>
<option value="8">Currency</option>
<option value="9">Dress</option>
<option value="10">Electric</option>
<option value="11">Food</option>
<option value="12">Footwear</option>
<option value="13">Gift</option>
<option value="14">Hair</option>
<option value="15">Handbag</option>
<option value="16">Headwear</option>
<option value="17">Household</option>
<option value="18">Infant</option>
<option value="19">Jewelry</option>
<option value="20">Linen</option>
<option value="21">Mechanic</option>
<option value="47">Medicine</option>
<option value="23">Music</option>
<option value="24">Nature</option>
<option value="25">Optics</option>
<option value="26">Papers</option>
<option value="27">Photo</option>
<option value="28">Religious</option>
<option value="29">Shirt</option>
<option value="30">Skirt</option>
<option value="31">Sleepwear</option>
<option value="32">Sport</option>
<option value="33">Sportswear</option>
<option value="34">Suit</option>
<option value="36">Sweater</option>
<option value="37">Timepiece</option>
<option value="38">Tobacco</option>
<option value="39">Tools</option>
<option value="40">Toys</option>
<option value="41">Trousers</option>
<option value="46">Undergarments</option>
<option value="42">Uniform</option>
<option value="43">Video</option>
<option value="45">Weapon</option>
<option value="44">Weather</option></select>
	                </td>
	                <td>
	                  
	                  Content Description
	                  
	                  <br>
	                  <input type="text" name="inventorylist[0].description" maxlength="255" size="60" value="" class="textfield">
	                </td>
	                <td align="center">&nbsp;<br>
	                	<input type="submit" name="deleteinventory_0_0" id="button" value="Delete Content">
	      
	                </td>
	              </tr>
	      </table>


		          <center><input type="submit" name="addinventory[0]" value="Add Additional Content" id="button"></center>
	      
			  </td>
		  </tr>


	  </table>

  <h1 class="green">Bag Number: 2</h1>

  	  <table class="form2" cellspacing="0" cellpadding="0" >
		  <tr>
			  <td colspan="2" class="header">
				  <strong>Baggage Description on File</strong>
			  </td>
		  </tr>
    <tr>
      <td width=33%>
        Color 
      </td>
      <td width=66%>
      	BK
      </td>
    </tr>
    <tr>
      <td width=33%>
        Manufacturer
      </td>
      <td width=66%>
	American Tourister
      </td>
    </tr>
    <tr>
      <td width=33%>
        Bag Status
      </td>
      <td width=66%>
	      Matched - <a href="">ATLB600000001</a> 
      </td>
    </tr>
		  <tr>
			  <td colspan="2" class="header">
				  <strong>Contents</strong>
			  </td>
		  </tr>
		  <tr>
			  <td colspan="2">
<table width="100%">
	              <tr>
	                <td>
	                  Content Category<br>
	                  <select name="inventorylist[0].categorytype_ID" class="dropdown"><option value="">Please Select</option>
	                    <option value="1">Alcohol</option>
<option value="2">Art</option>
<option value="3">Audio</option>
<option value="4">Book</option>
<option value="5">Coat/Jacket</option>
<option value="6">Computer</option>
<option value="7">Cosmetics</option>
<option value="8">Currency</option>
<option value="9">Dress</option>
<option value="10">Electric</option>
<option value="11">Food</option>
<option value="12">Footwear</option>
<option value="13">Gift</option>
<option value="14">Hair</option>
<option value="15">Handbag</option>
<option value="16">Headwear</option>
<option value="17">Household</option>
<option value="18">Infant</option>
<option value="19">Jewelry</option>
<option value="20">Linen</option>
<option value="21">Mechanic</option>
<option value="47">Medicine</option>
<option value="23">Music</option>
<option value="24">Nature</option>
<option value="25">Optics</option>
<option value="26">Papers</option>
<option value="27">Photo</option>
<option value="28">Religious</option>
<option value="29">Shirt</option>
<option value="30">Skirt</option>
<option value="31">Sleepwear</option>
<option value="32">Sport</option>
<option value="33">Sportswear</option>
<option value="34">Suit</option>
<option value="36">Sweater</option>
<option value="37">Timepiece</option>
<option value="38">Tobacco</option>
<option value="39">Tools</option>
<option value="40">Toys</option>
<option value="41">Trousers</option>
<option value="46">Undergarments</option>
<option value="42">Uniform</option>
<option value="43">Video</option>
<option value="45">Weapon</option>
<option value="44">Weather</option></select>
	                </td>
	                <td>
	                  
	                  Content Description
	                  
	                  <br>
	                  <input type="text" name="inventorylist[0].description" maxlength="255" size="60" value="" class="textfield">
	                </td>
	                <td align="center">&nbsp;<br>
	                	<input type="submit" name="deleteinventory_0_0" id="button" value="Delete Content">
	      
	                </td>
	              </tr>
	              <tr>
	                <td>
	                  Content Category<br>
	                  <select name="inventorylist[0].categorytype_ID" class="dropdown"><option value="">Please Select</option>
	                    <option value="1">Alcohol</option>
<option value="2">Art</option>
<option value="3">Audio</option>
<option value="4">Book</option>
<option value="5">Coat/Jacket</option>
<option value="6">Computer</option>
<option value="7">Cosmetics</option>
<option value="8">Currency</option>
<option value="9">Dress</option>
<option value="10">Electric</option>
<option value="11">Food</option>
<option value="12">Footwear</option>
<option value="13">Gift</option>
<option value="14">Hair</option>
<option value="15">Handbag</option>
<option value="16">Headwear</option>
<option value="17">Household</option>
<option value="18">Infant</option>
<option value="19">Jewelry</option>
<option value="20">Linen</option>
<option value="21">Mechanic</option>
<option value="47">Medicine</option>
<option value="23">Music</option>
<option value="24">Nature</option>
<option value="25">Optics</option>
<option value="26">Papers</option>
<option value="27">Photo</option>
<option value="28">Religious</option>
<option value="29">Shirt</option>
<option value="30">Skirt</option>
<option value="31">Sleepwear</option>
<option value="32">Sport</option>
<option value="33">Sportswear</option>
<option value="34">Suit</option>
<option value="36">Sweater</option>
<option value="37">Timepiece</option>
<option value="38">Tobacco</option>
<option value="39">Tools</option>
<option value="40">Toys</option>
<option value="41">Trousers</option>
<option value="46">Undergarments</option>
<option value="42">Uniform</option>
<option value="43">Video</option>
<option value="45">Weapon</option>
<option value="44">Weather</option></select>
	                </td>
	                <td>
	                  
	                  Content Description
	                  
	                  <br>
	                  <input type="text" name="inventorylist[0].description" maxlength="255" size="60" value="" class="textfield">
	                </td>
	                <td align="center">&nbsp;<br>
	                	<input type="submit" name="deleteinventory_0_0" id="button" value="Delete Content">
	      
	                </td>
	              </tr>
	              <tr>
	                <td>
	                  Content Category<br>
	                  <select name="inventorylist[0].categorytype_ID" class="dropdown"><option value="">Please Select</option>
	                    <option value="1">Alcohol</option>
<option value="2">Art</option>
<option value="3">Audio</option>
<option value="4">Book</option>
<option value="5">Coat/Jacket</option>
<option value="6">Computer</option>
<option value="7">Cosmetics</option>
<option value="8">Currency</option>
<option value="9">Dress</option>
<option value="10">Electric</option>
<option value="11">Food</option>
<option value="12">Footwear</option>
<option value="13">Gift</option>
<option value="14">Hair</option>
<option value="15">Handbag</option>
<option value="16">Headwear</option>
<option value="17">Household</option>
<option value="18">Infant</option>
<option value="19">Jewelry</option>
<option value="20">Linen</option>
<option value="21">Mechanic</option>
<option value="47">Medicine</option>
<option value="23">Music</option>
<option value="24">Nature</option>
<option value="25">Optics</option>
<option value="26">Papers</option>
<option value="27">Photo</option>
<option value="28">Religious</option>
<option value="29">Shirt</option>
<option value="30">Skirt</option>
<option value="31">Sleepwear</option>
<option value="32">Sport</option>
<option value="33">Sportswear</option>
<option value="34">Suit</option>
<option value="36">Sweater</option>
<option value="37">Timepiece</option>
<option value="38">Tobacco</option>
<option value="39">Tools</option>
<option value="40">Toys</option>
<option value="41">Trousers</option>
<option value="46">Undergarments</option>
<option value="42">Uniform</option>
<option value="43">Video</option>
<option value="45">Weapon</option>
<option value="44">Weather</option></select>
	                </td>
	                <td>
	                  
	                  Content Description
	                  
	                  <br>
	                  <input type="text" name="inventorylist[0].description" maxlength="255" size="60" value="" class="textfield">
	                </td>
	                <td align="center">&nbsp;<br>
	                	<input type="submit" name="deleteinventory_0_0" id="button" value="Delete Content">
	      
	                </td>
	              </tr>
	      </table>


		          <center><input type="submit" name="addinventory[0]" value="Add Additional Content" id="button"></center>
	      
			  </td>
		  </tr>


  </table>





  
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td align="center" valign="top">
                  <br>
                  <html:submit property="save" styleId="button">
                    <bean:message key="button.save" />
                  </html:submit>
                </td>
              </tr>
            </table>
          <p />

	            </html:form>
  
                  
            
