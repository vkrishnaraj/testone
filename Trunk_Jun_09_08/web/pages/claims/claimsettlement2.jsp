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
                      <a href="#"><span class="aab">&nbsp;
                          <br />
                          &nbsp;</span>
                        <span class="bbb"><bean:message key="menu.claim_customer" /></span>
                        <span class="ccb">&nbsp;
                          <br />
                          &nbsp;</span></a>
                    </dd>

                    <dd>
                      <a href="claim_settlement.do?screen=3"><span class="aa">&nbsp;
                          <br />
                          &nbsp;</span>
                        <span class="bb"><bean:message key="menu.claim_baggage" /></span>
                        <span class="cc">&nbsp;
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
          
          <h1 class="green">Customer Information</h1>
          
  <table class="form2" cellspacing="0" cellpadding="0" >
    <tr>
      <td colspan="2">
        Last Name<br />
        <input type="text" size="20" value="Smith"/>      
      </td>
      <td colspan="2">
        First Name<br />
        <input type="text" size="20" value="Byron"/>      
      </td>
      <td >
        Salutation<br />
        <select >
          <option>Mr.</option>
        </select>
      </td>
    </tr>

    <tr>
      <td width=33%  colspan="2">
        Street Address<br />
        <input type="text" size="25" value="2675 Paces Ferry Rd."/>     
      </td>
      <td colspan="2">
        Apt/Suite #<br />
         <input type="text" size="20" value="Suite #240"/>      
      </td>
      <td >
        Language<br />
        <select >
          <option>English</option>
        </select>  
      </td>
    </tr>
    <tr>
                <td>
                  City
                  <br>
                  <input type="text" name="addresses[0].city" maxlength="50" size="15" value="" class="textfield">
                </td>
                <td>
                  State/Province
                  <br />
                  
                    <select name="addresses[0].state_ID" onchange="updateCountryUS(this, this.form, 'countrycode_ID', 'province');" class="dropdown"><option value="" selected="selected">Please Select</option>
                      <option value="AL">Alabama</option>
<option value="AK">Alaska</option>
<option value="APO AA">APO(Americas)</option>
<option value="APO AE">APO(EU,ME,AF,CN)</option>
<option value="APO AP">APO(Pacific)</option>
<option value="AZ">Arizona</option>
<option value="AR">Arkansas</option>
<option value="CA">California</option>
<option value="CO">Colorado</option>
<option value="CT">Connecticut</option>
<option value="DE">Delaware</option>
<option value="DC">District of Columbia</option>
<option value="FL">Florida</option>
<option value="GA">Georgia</option>
<option value="HI">Hawaii</option>
<option value="ID">Idaho</option>
<option value="IL">Illinois</option>
<option value="IN">Indiana</option>
<option value="IA">Iowa</option>
<option value="KA">Kansas</option>
<option value="KY">Kentucky</option>
<option value="LA">Louisiana</option>
<option value="ME">Maine</option>
<option value="MH">Marshall Island</option>
<option value="MD">Maryland</option>
<option value="MA">Massachusetts</option>
<option value="MI">Michigan</option>
<option value="MN">Minnesota</option>
<option value="MS">Mississippi</option>
<option value="MO">Missouri</option>
<option value="MT">Montana</option>
<option value="NE">Nebraska</option>
<option value="NV">Nevada</option>
<option value="NH">New Hampshire</option>
<option value="NJ">New Jersey</option>
<option value="NM">New Mexico</option>
<option value="NY">New York</option>
<option value="NC">North Carolina</option>
<option value="ND">North Dakota</option>
<option value="OH">Ohio</option>
<option value="OK">Oklahoma</option>
<option value="OR">Oregon</option>
<option value="PA">Pennsylvania</option>
<option value="RI">Rhode Island</option>
<option value="SC">South Carolina</option>
<option value="SD">South Dakota</option>
<option value="TN">Tennessee</option>
<option value="TX">Texas</option>
<option value="UT">Utah</option>
<option value="VT">Vermont</option>
<option value="VA">Virginia</option> colspan="2"
<option value="WA">Washington</option>
<option value="WV">West Virginia</option>
<option value="WI">Wisconsin</option>
<option value="WY">Wyoming</option></select>
                  
                  
                  
                    
                  
                </td>
                <td>
                  Province
                  <br />
                      
                  <input type="text" name="addresses[0].province" maxlength="100" size="15" value="" disabled="disabled" class="disabledtextfield">
                      
                      
                      
                        
                      
                </td>
                <td>
                  Zip
                  <br>
                  <input type="text" name="addresses[0].zip" maxlength="11" size="15" value="" class="textfield">
                </td>
                <td>
                  Country
                  <br>
                  <select name="addresses[0].countrycode_ID" onchange="checkstate(this,this.form,'state_ID', 'province');" class="dropdown"><option value="">Please Select</option>
                    <option value="AF">Afghanistan</option>
<option value="AL">Albania</option>
<option value="DZ">Algeria</option>
<option value="AS">American Samoa</option>
<option value="AD">Andorra</option>
<option value="AO">Angola</option>
<option value="AI">Anguilla</option>
<option value="AQ">Antarctica</option>
<option value="AG">Antigua, Barbuda</option>
<option value="AR">Argentina</option>
<option value="AM">Armenia</option>
<option value="AW">Aruba</option>
<option value="AU">Australia</option>
<option value="AT">Austria</option>
<option value="AZ">Azerbaijan</option>
<option value="BS">Bahamas</option>
<option value="BH">Bahrain</option>
<option value="BD">Bangladesh</option>
<option value="BB">Barbados</option>
<option value="BY">Belarus</option>
<option value="BE">Belgium</option>
<option value="BZ">Belize</option>
<option value="BJ">Benin</option>
<option value="BM">Bermuda</option>
<option value="BT">Bhutan</option>
<option value="BO">Bolivia</option>
<option value="BA">Bosnia, Herzegovina</option>
<option value="BW">Botswana</option>
<option value="BV">Bouvet Island</option>
<option value="BR">Brazil</option>
<option value="IO">British Indian Ocea.</option>>
<option value="AE">United Arab Emirate.</option>
<option value="GB">United Kingdom</option>
<option value="US" selected="selected">United States</option>
<option value="UM">United States Minor.</option>
<option value="UY">Uruguay</option>>
<option value="AE">United Arab Emirate.</option>
<option value="GB">United Kingdom</option>
<option value="US" selected="selected">United States</option>
<option value="UM">United States Minor.</option>
<option value="UY">Uruguay</option>
<option value="UZ">Uzbekistan</option>
<option value="VU">Vanuatu</option>
<option value="VE">Venezuela</option>
<option value="VN">Vietnam</option>
<option value="VG">Virgin Is.(British)</option>
<option value="VI">Virgin Is.(USA)</option>
<option value="WF">Wallis &amp; Futuna Isl.</option>
<option value="EH">Western Sahara</option>
<option value="YE">Yemen</option>
<option value="YU">Yugoslavia</option>
<option value="ZR">Zaire (Congo)</option>
<option value="ZM">Zambia</option>
<option value="ZW">Zimbabwe</option></select>
</td>
</tr>


              <tr>
                <td>
                  Home Phone
                  <br>
                  <input type="text" name="addresses[0].homephone" maxlength="25" size="15" value="" class="textfield">
                </td>
                <td>
                  Business Phone
                  <br>
                  <input type="text" name="addresses[0].workphone" maxlength="25" size="15" value="" class="textfield">
                </td>
                <td>
                  Mobile Phone
                  <br>
                  <input type="text" name="addresses[0].mobile" maxlength="25" size="15" value="" class="textfield">
                </td>
                <td>
                  Pager
                  <br>
                  <input type="text" name="addresses[0].pager" maxlength="25" size="15" value="" class="textfield">
                </td>
                <td>
                  Fax
                  <br>
                  <input type="text" name="addresses[0].altphone" maxlength="25" size="15" value="" class="textfield">
                </td>
              </tr>



    <tr>
      <td colspan="3">
        Email Address<br />
        <input type="text" size="45" value="bsmith@nettracer.aero"/>
	</td>
	<td colspan="2">TrueBlue Number<br /><input type="text" /></td>

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
  
                  
            
