// calendar_lib.js version 1.1b - 12/14/2001
// written by Wesley S. Rast
// copyright 2001, GE Power Systems

calendar=null;
// begin class definitions *****************************************************************
function Calendar(szImgPath,szFontColor,szFileType,bCanReturnPastDate)
{
   this.pToCalendarDiv=null;
   this.pToDateTargetElement=null;
   this.pToDateForm=null;
   this.pToDateTargetFormElement=null;
   this.pToRepositionTarget=null;

   this.systemDate=new Date();

   this.bCanReturnPastDate=(bCanReturnPastDate)?true:false;
   this.bTempPastDate=false;
   this.daysInMonth=new Array(31,28,31,30,31,30,31,31,30,31,30,31);
   this.monthNames=new Array("January","February","March","April","May","June","July","August","September","October","November","December");
   this.szFileType=szFileType;
   this.szFontColor=szFontColor;
   this.szImgPath=szImgPath;

   this.cancelButton=new CancelButton(this);
   this.setButton=new SetButton(this);

   this.yearDial=new YearDial(this);
   this.monthBox= new MonthBox(this);
   this.dayBox= new DayBox(this);
}
Calendar.prototype.isVisible=_isVisible;
Calendar.prototype.getDate=_getDate;
Calendar.prototype.getDaysInMonth=_getDaysInMonth;
Calendar.prototype.reposition=_reposition;
Calendar.prototype.setCalendarDiv=_setCalendarDiv;
Calendar.prototype.setDateTargetElement=_setDateTargetElement;
Calendar.prototype.setDateTargetFormElement=_setDateTargetFormElement;
Calendar.prototype.setFocusOnNextElement=_setFocusOnNextElement;
Calendar.prototype.setRepositionTarget=_setRepositionTarget;
Calendar.prototype.setSelectVisibility=_setSelectVisibility;
Calendar.prototype.setTargetForm=_setTargetForm;
Calendar.prototype.setVisible=_setVisible;
Calendar.prototype.writeCalendarHTML=_writeCalendarHTML;

function CancelButton(pToParent)
{
   this.pToParent=pToParent;
   this.pToCancelImage=null;

   this.cancelOn=new Image();
   this.cancelOff=new Image();
   this.cancelOn.src=this.pToParent.szImgPath+"cancelon"+this.pToParent.szFileType;
   this.cancelOff.src=this.pToParent.szImgPath+"canceloff"+this.pToParent.szFileType;
}
CancelButton.prototype.cancelGetDate=_cancelGetDate;
CancelButton.prototype.cancelOut=_cancelOut;
CancelButton.prototype.cancelOver=_cancelOver;
CancelButton.prototype.initCancelButton=_initCancelButton;
CancelButton.prototype.setCancelImage=_setCancelImage;

function DayBox(pToParent)
{
   this.pToParent=pToParent;
   this.pToDayBoxSelect=null;

   this.day=0;
}
DayBox.prototype.getDay=_getDay;
DayBox.prototype.initDay=_initDay;
DayBox.prototype.populateDayBox=_populateDayBox;
DayBox.prototype.setDay=_setDay;
DayBox.prototype.setDayBoxSelect=_setDayBoxSelect;

function MonthBox(pToParent)
{
   this.pToParent=pToParent;
   this.pToMonthBoxSelect=null;

   this.month=this.pToParent.systemDate.getUTCMonth();
}
MonthBox.prototype.getMonth=_getMonth;
MonthBox.prototype.initMonth=_initMonth;
MonthBox.prototype.populateMonthBox=_populateMonthBox;
MonthBox.prototype.setMonth=_setMonth;
MonthBox.prototype.setMonthBoxSelect=_setMonthBoxSelect;

function SetButton(pToParent)
{
   this.pToParent=pToParent;
   this.pToSetImg=null;

   this.setOn=new Image();
   this.setOff=new Image();
   this.setOn.src=this.pToParent.szImgPath+"seton"+this.pToParent.szFileType;
   this.setOff.src=this.pToParent.szImgPath+"setoff"+this.pToParent.szFileType;
}
SetButton.prototype.setOver=_setOver;
SetButton.prototype.setOut=_setOut;
SetButton.prototype.setDate=_setDate;
SetButton.prototype.setSetImg=_setSetImg;
SetButton.prototype.initSetButton=_initSetButton;

function YearDial(pToParent)
{
   this.pToParent=pToParent;
   this.pToYearDialInput=null;
   this.pToMinusImage=null;
   this.pToPlusImage=null;

   this.year=this.pToParent.systemDate.getUTCFullYear();

   this.minusOff=new Image();
   this.minusOn=new Image();
   this.plusOn=new Image();
   this.plusOff=new Image();
   this.minusOff.src=this.pToParent.szImgPath+"minusoff"+this.pToParent.szFileType;
   this.minusOn.src=this.pToParent.szImgPath+"minuson"+this.pToParent.szFileType;
   this.plusOff.src=this.pToParent.szImgPath+"plusoff"+this.pToParent.szFileType;
   this.plusOn.src=this.pToParent.szImgPath+"pluson"+this.pToParent.szFileType;
}
YearDial.prototype.decrementYear=_decrementYear;
YearDial.prototype.getYear=_getYear;
YearDial.prototype.incrementYear=_incrementYear;
YearDial.prototype.initYear=_initYear;
YearDial.prototype.minusOver=_minusOver;
YearDial.prototype.minusOut=_minusOut;
YearDial.prototype.plusOver=_plusOver;
YearDial.prototype.plusOut=_plusOut;
YearDial.prototype.setMinusImage=_setMinusImage;
YearDial.prototype.setPlusImage=_setPlusImage;
YearDial.prototype.setYearDialInput=_setYearDialInput;
// end class definitions *****************************************************************



//begin method definitions **************************************************************

function _cancelGetDate() //::CancelButton
{
   this.pToParent.setVisible(false);
   this.pToParent.bTempPastDate=false;
   this.pToParent.setFocusOnNextElement();
}

function _cancelOut() //::CancelButton
{
   this.pToCancelImage.src=this.cancelOff.src;
}

function _cancelOver() //::CancelButton
{
   this.pToCancelImage.src=this.cancelOn.src;
}

function _decrementYear() //::YearDial
{
   if (this.year>this.pToParent.systemDate.getUTCFullYear() || this.pToParent.bCanReturnPastDate || this.pToParent.bTempPastDate)
   {
      this.year=--this.year;
      this.pToYearDialInput.value=this.year;
      this.pToParent.monthBox.populateMonthBox();
      this.pToParent.monthBox.setMonth();
   }
}

function _getDate(szTargetForm,szTargetElementId,szTargetFormElementId,szRepositionTarget,bTempPastDateValid) //::Calendar
{
   if (this.isVisible() && this.pToDateTargetFormElement==document.getElementById(szTargetFormElementId)) {
      return;
   }
   if (szTargetElementId) this.setDateTargetElement(szTargetElementId);
   if (szTargetForm) this.setTargetForm(szTargetForm);
   if (szTargetFormElementId) this.setDateTargetFormElement(szTargetFormElementId);
   if (szRepositionTarget) this.setRepositionTarget(szRepositionTarget);
   else if (szTargetElementId) this.setRepositionTarget(szTargetElementId);
   
	if (bTempPastDateValid) this.bTempPastDate=true;

   this.yearDial.initYear();
   this.monthBox.initMonth();
   this.dayBox.initDay();
   this.setButton.initSetButton();
   this.cancelButton.initCancelButton();

   this.reposition();
   //this.pToCalendarDiv.focus();
}

function _getDaysInMonth(nMonthToQuery) //::Calendar
{
   if (nMonthToQuery==1 && (this.yearDial.getYear()%4==0))
   {
      return 29;
   }
   else return this.daysInMonth[nMonthToQuery];
}

function _getDay() //::DayBox
{
   return this.day;
}

function _getMonth() //::MonthBox
{
   return this.month;
}

function _getYear() //::YearDial
{
   return this.year;
}

function _incrementYear() //::YearDial
{
   this.year=++this.year;
   this.pToYearDialInput.value=this.year;
   this.pToParent.monthBox.populateMonthBox();
   this.pToParent.monthBox.setMonth();
}

function _initCancelButton() //::CancelButton
{
   this.setCancelImage();
}

function _initDay() //::DayBox
{
   this.setDayBoxSelect();
   var nStartingDay=1;

   this.populateDayBox();
   this.pToDayBoxSelect.selectedIndex=0;
   this.day=this.pToDayBoxSelect.options[this.pToDayBoxSelect.selectedIndex].value;
}

function _initMonth() //::MonthBox
{
   this.setMonthBoxSelect();
   this.populateMonthBox();
   this.pToMonthBoxSelect.selectedIndex=0;
   this.month=parseInt(this.pToMonthBoxSelect.options[this.pToMonthBoxSelect.selectedIndex].value);
}

function _initSetButton() //::SetButton
{
   this.setSetImg();
}

function _initYear() //::YearDial
{
   this.setYearDialInput();
   this.setMinusImage();
   this.setPlusImage();
   this.year=this.pToParent.systemDate.getUTCFullYear();
   this.pToYearDialInput.value=this.year;
}

function _minusOut() //::YearDial
{
   this.pToMinusImage.src=this.minusOff.src;
}

function _minusOver() //::YearDial
{
   this.pToMinusImage.src=this.minusOn.src;
}

function _plusOut() //::YearDial
{
   this.pToPlusImage.src=this.plusOff.src;
}

function _plusOver() //::YearDial
{
   this.pToPlusImage.src=this.plusOn.src;
}

function _populateDayBox() //::DayBox
{
   var nStartingDay=1;
   if (this.pToParent.yearDial.getYear()==this.pToParent.systemDate.getUTCFullYear() && this.pToParent.monthBox.getMonth()==this.pToParent.systemDate.getUTCMonth() && !(this.pToParent.bCanReturnPastDate || this.pToParent.bTempPastDate))
   {
      nStartingDay=this.pToParent.systemDate.getUTCDate();
   }
   this.pToDayBoxSelect.length=0;
   for (var i=nStartingDay;i<=this.pToParent.getDaysInMonth(this.pToParent.monthBox.getMonth());i++)
   {
      this.pToDayBoxSelect.options[this.pToDayBoxSelect.length]=new Option(i,i,false,false);
   }
   this.pToDayBoxSelect.selectedIndex=0;
   this.setDay();
}

function _populateMonthBox() //::MonthBox
{
   this.pToMonthBoxSelect.length=0;
   if (this.pToParent.yearDial.getYear()==this.pToParent.systemDate.getUTCFullYear() && (!this.pToParent.bCanReturnPastDate && !this.pToParent.bTempPastDate))
   {
      for (var x=this.pToParent.systemDate.getUTCMonth();x<=11;x++)
      {
         this.pToMonthBoxSelect.options[this.pToMonthBoxSelect.length]=new Option(this.pToParent.monthNames[x]+" - "+(x+1),x,false,false);
      }
   }
   else
   {
      for (var x=0;x<=11;x++)
      {
         this.pToMonthBoxSelect.options[this.pToMonthBoxSelect.length]=new Option(this.pToParent.monthNames[x]+" - "+(x+1),x,false,false);
      }
   }
}

function _reposition() //::Calendar
{
   this.pToCalendarDiv.style.top=getRealTop(this.pToRepositionTarget.id);
   this.pToCalendarDiv.style.left=getRealLeft(this.pToRepositionTarget.id);
   this.setVisible(true);
}

function _setCalendarDiv() //::Calendar
{
   this.pToCalendarDiv=document.getElementById("theCalendar")
}

function _setCancelImage() //::CancelButton
{
   this.pToCancelImage=document.images.cancelImage;
}

function _setDate() //::SetButton
{
   var finalOutput=(this.pToParent.monthBox.getMonth()+1)+"/"+this.pToParent.dayBox.getDay()+"/"+this.pToParent.yearDial.getYear();
   if (this.pToParent.pToDateTargetElement) this.pToParent.pToDateTargetElement.innerHTML=finalOutput;
   if (this.pToParent.pToDateTargetFormElement) this.pToParent.pToDateTargetFormElement.value=finalOutput;
   this.pToParent.setVisible(false);
   this.pToParent.setFocusOnNextElement();
   this.pToParent.bTempPastDate=false;
}

function _setDateTargetElement(szTargetElementId) //::Calendar
{
   this.pToDateTargetElement=document.getElementById(szTargetElementId);
}

function _setDateTargetFormElement(szTargetFormElementId) //::Calendar
{
   this.pToDateTargetFormElement=this.pToTargetForm.elements[szTargetFormElementId];
}

function _setDay() //::DayBox
{
   this.day=this.pToDayBoxSelect.options[this.pToDayBoxSelect.selectedIndex].value;
}

function _setDayBoxSelect() //::DayBox
{
   this.pToDayBoxSelect=document.forms.calendarForm.elements.daySelect;
}

function _setMinusImage() //::YearDial
{
   this.pToMinusImage=document.images.minusImage;
}

function _setMonth() //::MonthBox
{
   this.month=parseInt(this.pToMonthBoxSelect.options[this.pToMonthBoxSelect.selectedIndex].value);
   this.pToParent.dayBox.populateDayBox();
}

function _setMonthBoxSelect() //::MonthBox
{
   this.pToMonthBoxSelect=document.forms.calendarForm.elements.monthSelect;
}

function _setOut()
{
   this.pToSetImg.src=this.setOff.src;
}

function _setOver()
{
   this.pToSetImg.src=this.setOn.src;
}

function _setPlusImage()
{
   this.pToPlusImage=document.images.plusImage;
}

function _setRepositionTarget(szRepositionTarget)
{
   this.pToRepositionTarget=document.getElementById(szRepositionTarget);
}

function _setTargetForm(szTargetFormName)
{
   this.pToTargetForm=document.forms[szTargetFormName];
}

function _setSetImg()
{
   this.pToSetImg=document.images.setImg;
}

function _setYearDialInput()
{
   this.pToYearDialInput=document.forms.calendarForm.elements.yearInput;
}

function _writeCalendarHTML()
{
   var htmlString='<div id="theCalendar">'+
         '<form name="calendarForm" action="" method="post">'+
            '<table class="calendarTable" border="0" cellpadding="0" cellspacing="0">'+
            '<tr>'+
            '<td class="calendarULCell"><img src="'+this.szImgPath+'ul'+this.szFileType+'" width="8" height="23" border="0" alt=""></td>'+
            '<td class="calendarTitleCell" background="'+this.szImgPath+'u'+this.szFileType+'"><img src="'+this.szImgPath+'title'+this.szFileType+'" width="113" height="23" border="0" alt=""></td>'+
            '<td class="calendarCancelCell" onClick="calendar.cancelButton.cancelGetDate();" onMouseOver="calendar.cancelButton.cancelOver();" onMouseOut="calendar.cancelButton.cancelOut();">'+
               '<img name="cancelImage" src="'+this.szImgPath+'canceloff'+this.szFileType+'" width="21" height="23" border="0" alt="Click here to close this pop-up and not change the date." title="Click here to close this pop-up and not change the date."></td>'+
            '<td class="calendarURCell"><img src="'+this.szImgPath+'ur'+this.szFileType+'" width="8" height="23" border="0" alt=""></td>'+
            '</tr>'+
            '<tr>'+
            '<td background="'+this.szImgPath+'l'+this.szFileType+'"><img src="../images/spacer.gif" width="1" height="1" /></td>'+
            '<td class="calendarContentCell" style="color:'+this.szFontColor+';" colspan="2" background="'+this.szImgPath+'background'+this.szFileType+'">\n'+
               'Year: <input disabled type="text" size="8" name="yearInput" value="" /><img class="calendarButton" '+
                  'name="minusImage" src="'+this.szImgPath+'minusoff'+this.szFileType+'" align="absmiddle" width="27" height="27" border="0" alt="Click here to decrement the year." title="Click here to decrement the year." '+
                  'onMouseOver="calendar.yearDial.minusOver();" onMouseOut="calendar.yearDial.minusOut();" onClick="calendar.yearDial.decrementYear();" /><img class="calendarButton" '+
                  'name="plusImage" src="'+this.szImgPath+'plusoff'+this.szFileType+'" align="absmiddle" width="27" height="27" border="0" alt="Click here to increment the year." title="Click here to increment the year." '+
                  'onMouseOver="calendar.yearDial.plusOver();" onMouseOut="calendar.yearDial.plusOut();" onClick="calendar.yearDial.incrementYear();" /><br />'+
               '<img src="'+this.szImgPath+'spacer'+this.szFileType+'" width="1" height="8" alt="" title="" /><br />'+
               'Month: <select name="monthSelect" onChange="calendar.monthBox.setMonth();"></select>&nbsp;&nbsp;'+
               '<span id="dayDiv">'+
                  'Day: <select name="daySelect" onChange="calendar.dayBox.setDay();"></select>'+
               '</span><br />'+
               '<table border="0" cellpadding="0" cellspacing="0" width="100%">'+
               '<tr>'+
               '<td class="calendarSetImgCell"><img name="setImg" src="'+this.szImgPath+'setoff'+this.szFileType+'" width="158" height="18" border="0"'+
                  'onMouseOver="calendar.setButton.setOver();" onMouseOut="calendar.setButton.setOut();" onClick="calendar.setButton.setDate();" '+
                  'alt="Click to set this date." title="Click to set this date." /></td>'+
               '<tr>'+
               '</table>'+
            '</td>'+
            '<td background="'+this.szImgPath+'r'+this.szFileType+'"><img src="../images/spacer.gif" width="1" height="1" /></td>'+
            '</tr>'+
            '<tr>'+
            '<td><img src="'+this.szImgPath+'bl'+this.szFileType+'" width="8" height="8" border="0" alt=""></td>'+
            '<td colspan="2" background="'+this.szImgPath+'b'+this.szFileType+'"><img src="'+this.szImgPath+'spacer'+this.szFileType+'" width="1" height="1" /></td>'+
            '<td><img src="'+this.szImgPath+'br'+this.szFileType+'" width="8" height="8" border="0" alt=""></td>'+
            '</tr>'+
            '</table>'+
         '</form>'+
      '</div>';
      document.getElementById("calendarPlaceHolder").innerHTML=htmlString;
}

function writeStyleSheet()
{
   var styleString='<style>'+
      '.calendarButton {}'+
      '.calendarCancelCell {width:21px}'+
      '.calendarContentCell {font-family:"Verdana", "Arial", "Helvetica", "sans-serif";font-size:12px;font-weight:bold;}'+
      '.calendarSetImgCell {text-align:center;padding-top:8px;}'+
      '.calendarTable {width:300px}'+
      '.calendarTitleCell {width:100%;text-align:left;}'+
      '.calendarULCell {width:8px}'+
      '.calendarURCell {width:8px}'+
      '#theCalendar {position:absolute;top:300px;left:300px;display:block;visibility:hidden;z-Index:500;}'+
      '</style>';
   document.write(styleString);
}
//end method definitions ***************************************************************


function initCalendar(szSkinPath,szFontColor,szFileType,bPastDateValid)
{
   var url = document.jsform.ServerPath.value;
   var skinPath=(szSkinPath)?szSkinPath:url + "images/calendarskins/defaultskin/";
   var fontColor=(szFontColor)?szFontColor:"#000000";
   var fileType=(szFileType)?szFileType:".gif";
   var pastDateValid=(bPastDateValid)?bPastDateValid:false;
   calendar=new Calendar(skinPath,fontColor,fileType,bPastDateValid);
   calendar.writeCalendarHTML();
   calendar.setCalendarDiv();
}

writeStyleSheet();


/* New functions defined by doug nelson.
  _setSelectVisibility(isVis):  sets the visibility of select boxes on a form.
  _isVisible():  tells whether the calendar is visible;
  _setVisible(isVis):  sets the visibilty
*/
function _setSelectVisibility(isVis)
{
   if (isIE5 || isIE6)
	{
      var selectArray=document.getElementsByTagName("select");
      for (var n=0; n<selectArray.length; n++)
		{
         var thisElem = selectArray[n];
         if (thisElem.nodeName=='SELECT' && thisElem.form)
			{
            if (isVis)
				{
               if (thisElem.getAttribute("hiddenByCalendar")=="true")
					{
                  thisElem.setAttribute("hiddenByCalendar", "false");
                  thisElem.style.visibility="visible";
               }
				}
				else if (thisElem.form.name!="calendarForm")
				{
               var noCSSHide=(isIE5 || isIE6)?determineCSSVisibility(thisElem):true;
               var noCSSDisplayNone=(isIE5 || isIE6)?determineCSSDisplay(thisElem):true;
               var noMenuHide=(isIE5 || isIE6)?(thisElem.getAttribute('noMenuHide')=="true"):false;
               var elemVis = ((thisElem.style.visibility!="hidden") && noCSSHide && noCSSDisplayNone && !noMenuHide)?true:false;
               if (elemVis)
					{
                  thisElem.setAttribute("hiddenByCalendar", "true");
                  thisElem.style.visibility="hidden";
               }
            }
         }
      }
   }
}

function _isVisible()
{
   if (this.pToParent)
	{
      return (this.pToParent.pToCalendarDiv.style.visibility=="visible");
   }
   return false;
}

function _setVisible(isVis)
{
   if (isVis)
	{
      this.setSelectVisibility(false);
      this.pToCalendarDiv.style.visibility="visible";
   }
	else
	{
      this.pToCalendarDiv.style.visibility="hidden";
      this.setSelectVisibility(true);
   }
}

function _setFocusOnNextElement()
{
	var anElem = this.pToDateTargetFormElement;
	var aForm = anElem.form;
	for (var i=0; i<aForm.elements.length; i++)
	{
		if (aForm.elements[i]==this.pToDateTargetFormElement)
		{
			anElem=aForm.elements[i+1];
			break;
		}
	}
	if (anElem!=null && determineCSSDisplay(anElem) && determineCSSVisibility(anElem))
	{
	  anElem.focus();
	}
	else window.focus();
}
