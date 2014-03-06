var chartWin = null;

function openChart(url)
{
    // compensate for margins
    w = 800;
    h = 50;

	var options = "width=" + w + ",height=" + h + ",menubar=no";
	chartWin = window.open(url,"bagtypechart",options);
}
//<!--
var imgObj;
function checkImg(theURL,winName){

  if (typeof(imgObj) == "object"){
  
    if ((imgObj.width != 0) && (imgObj.height != 0))

      OpenFullSizeWindow(theURL,winName, ",width=" + (imgObj.width+20) + ",height=" + (imgObj.height+30));
    else
     
      setTimeout("checkImg('" + theURL + "','" + winName + "')", 100)
  }
}

function OpenFullSizeWindow(theURL,winName,features) {
  var aNewWin, sBaseCmd;
 
  sBaseCmd = "toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,";

  if (features == null || features == ""){
 
    imgObj = new Image();

    imgObj.src = theURL;

    checkImg(theURL, winName)
  }
  else{

    aNewWin = window.open(theURL,winName, sBaseCmd + features);
  
    aNewWin.focus();
  }
}
//-->
function openChart2(url,width,height,left)
{
    // compensate for margins

    w = width;
    h = height;
    var left = (screen.width/2)-(w/2);
    var top = (screen.height/2)-(h/2);
 
	var options = "width=" + w + ",height=" + h +",left=" + left+",top=" + top;
	chartWin = window.open(url,"bagtypechart",options);
	
}

function closeChart()
{
	if (chartWin != null) chartWin();
}


function openHelp(url)
{
  w = 1000;
   h = 600;
	var options = "width=" + w + ",height=" + h + ",resizable=yes,scrollbars=yes";
	var x = window.open(url,"helpWin",options);
}


function openWindowWithBar(url,name,w,h)
{
    // compensate for margins
    w = w;
    h = h;
 
    var options = "width=" + w + ",height=" + h + ",resizable=yes,scrollbars=yes,toolbar=no,menubar=yes";
    newwin = window.open(url,name,options);
}


function openWindow(url,name,w,h)
{
    // compensate for margins
    w = w;
    h = h;
 
    var options = "width=" + w + ",height=" + h + ",resizable=yes,scrollbars=yes,toolbar=no,menubar=yes";
    newwin = window.open(url,name,options);
}

function openReportWindow(url,name,w,h)
{
    // compensate for margins
    w = w;
    h = h;
 
    var options = "width=" + w + ",height=" + h + ",resizable=yes,scrollbars=yes,menubar=yes";
    newwin = window.open(url,name,options);
}

function fillzero(o,maxlen) {
	if(o == null) {
		return false;
	}
    var currval = o.value;

    if(currval.indexOf('%') != -1) {
    	return false;
    }
    
    // remove white space first
    currval = currval.replace(/\s/gi, '');

    if (currval != null && currval.length > 0 && currval.length < maxlen) {
        // get how many zeros should be inserted
        var len = maxlen - currval.length;
        var zeros = "";
        
        for (i = 0;i<len;i++) {
            zeros += "0";
        }
        
        //jetblue specific test
        var parts = currval.match(/(\w{2,5}B6)(\d+)$/i);
        
        if(parts == null) {
        	parts = currval.match(/(^.*?[A-Z])(\d+)$/i);
        }
        
        if(parts != null) {
        	o.value = (parts[1] + zeros + parts[2]).toUpperCase();
        }
    }    
    return true;
}


function filldata(o,maxlen) 
{

                if(o == null) 
	                {
	                    return false;
	                }
    var currval = o.value;
   
    if(currval.indexOf('%') != -1)
        {
                    return false;
        }
    
 
    currval = currval.replace(/\s/gi, '');
    if (currval != null && currval.length > 0 && currval.length < 12 && currval.indexOf('BDO') < 0) 
            {
    		    var len = maxlen - currval.length -3;
			    var zeros = "";
			    var initialStr = 'BDO';
			    for (i = 0;i<len;i++) 
			            {
			                zeros += "0";
			            }
			     o.value = initialStr + zeros+currval;
	         }
    
    return true;
}



function getDate(type) {
  var D = new Date();
  //window.setTimeout( "runclock()", 1000 );

  var mon = D.getMonth() + 1;
  var day = D.getDate();


  var hours =D.getHours();
  var min =D.getMinutes();
  var sec =D.getSeconds();

  var ampm = "AM";
  if (hours > 12) {
    hours = hours - 12;
    ampm = "PM";
  }

  if (hours == 0) {
    hours = 12;
    ampm = "AM";
  }

  if (mon < 10) mon = "0" + mon;
  if (day < 10) day = "0" + day;
  if (hours < 10) hours = "0" + hours;
  if (min < 10) min = "0" + min;
  if (sec < 10) sec = "0" + sec;

  var currdate = mon + "/" + day + "/" + D.getFullYear();

  var currtime = hours + ":" + min + ":" + sec + " " + ampm;

  if (type == 1) return currdate;
  else return currtime;
}

function hideThisDiv(object, objectType) {
	document.getElementById(object).innerHTML = "";
	del = document.forms[0].delete_these_elements;
	del.value += object + ",";
	jQuery("#dirtyCheck-form").addClass("dirty");
}
 
function hideThisElement(objectName, objectType, num) {
	var obj = document.getElementById(objectName);
	removeElement(obj);
	if (num > 0) {
		for (i=1; i <= num; ++i) {
			var additionalObj = document.getElementById(objectName + "_" + i);
			removeElement(additionalObj);
		}
	}
	del = document.forms[0].delete_these_elements;
	del.value += objectName + ",";
	jQuery("#dirtyCheck-form").addClass("dirty");
}

function removeElement(obj) {
	var parent = obj.parentElement;
	parent.removeChild(obj);
}


function getProcessingContent() {
	var h = '<div style="margin-top: 5px; text-align: center">';
	h += '<h5>Please wait...</h5>';
	h += '</div>';
	return h;
}

function getLoadingContent() {
	jQuery('#dialog').dialog('option', 'buttons', {} );
	var h = '<div style="margin-top: 50px; text-align: center">';
	h += '<img width="64" height="64" src="/tracer/deployment/main/images/loading2.gif" /><br />';
	h += '<h5>Loading...</h5>';
	h += '</div>';
	return h;
}

//Why are there two getLoadingContents?
function getLoadingContent() {
	jQuery('#dialog').dialog('option', 'buttons', {} );
	var h = '<div style="margin-top: 50px; text-align: center">';
	h += '<img width="64" height="64" src="/tracer/deployment/main/images/loading2.gif" /><br />';
	h += '<h5>Loading...</h5>';
	h += '</div>';
	return h;
}

function getSavingContent() {
	jQuery('#dialog').dialog('option', 'buttons', {} );
	var h = '<div style="margin-top: 50px; text-align: center">';
	h += '<img width="64" height="64" src="/tracer/deployment/main/images/loading2.gif" /><br />';
	h += '<h5>Saving...</h5>';
	h += '</div>';
	return h;
}

var slideUpContainerState = 0; 
var slideUpContainerLoaded = 0;
var slideUpContainerShown = 0;

function loadSlideupContainer(url) {
	jQuery("#sliderContentFrame").load(url);
	var slideUpElement = document.getElementById("slideUpContainer");
	slideUpElement.style.display = "inline";
	slideUpElement.style.bottom="-340px";
	if (slideUpContainerShown == 0){
		document.getElementById("slideUpContainerVisible").value = 'yes';
		document.getElementById("openLink").style.display = "none";
		document.getElementById("minimazeLink").style.display = "inline";
		document.getElementById("switchLink").style.display = "inline";
		document.getElementById("closeLink").style.display = "inline";
		document.getElementById("sliderContentFrame").style.display = "inline";			
		var slideInnerElement = document.getElementById("sliderInner");
		slideUpElement.style.width="";
		slideInnerElement.style.padding="";
	}
	slideUpElement.style.top="";
	var slideUpContainer = jQuery(slideUpElement);
	slideUpContainerLoaded = 1;
	slideUpContainer.animate({"bottom":"+=342px"},"slow");
	document.getElementById("slideUpContainerVisible").value = 'yes';
	slideUpContainerShown = 1;
    	
}

function handleEvent() {
	var slideUpElement = document.getElementById("slideUpContainer");
	var slideUpContainer = jQuery(slideUpElement);
	var slideInnerElement = document.getElementById("sliderInner");
	
	if (slideUpContainerShown == 1){
		if (slideUpContainerState == 1) {
			slideUpElement.style.top="";
			slideUpContainerState = 0;
		}
		slideUpElement.style.bottom="-300px";
		slideUpElement.style.width="100px";
		slideInnerElement.style.padding="10px 0px 20px";
		document.getElementById("slideUpContainerVisible").value = 'no';
		document.getElementById("minimazeLink").style.display = "none";
		document.getElementById("openLink").style.display = "inline";
		document.getElementById("switchLink").style.display = "none";
		document.getElementById("closeLink").style.display = "none";

		document.getElementById("sliderContentFrame").style.display = "none";
		slideUpContainerShown = 0;
	} else {
		if (slideUpContainerState == 0) {
			slideUpElement.style.bottom="0px";
		} else {
			slideUpElement.style.top="0px";
		}
		slideUpElement.style.width="";
		slideInnerElement.style.padding="";
		document.getElementById("slideUpContainerVisible").value = 'yes';
		document.getElementById("openLink").style.display = "none";
		document.getElementById("minimazeLink").style.display = "inline";
		document.getElementById("switchLink").style.display = "inline";
		document.getElementById("closeLink").style.display = "inline";
		document.getElementById("sliderContentFrame").style.display = "inline";
		slideUpContainerShown = 1;
	}
}

function switchLocation() {
	var slideUpElement = document.getElementById("slideUpContainer");
	
	if (slideUpContainerState == 0) {
		slideUpElement.style.bottom="";
		slideUpElement.style.top="0px";
		slideUpContainerState = 1;
	} else {
		slideUpElement.style.top="";
		slideUpElement.style.bottom="0px";
		slideUpContainerState = 0;
	}
}

function closeSubPage()
{
	slideUpContainerShown = 0;
	slideUpContainerLoaded = 0;
	document.getElementById("slideUpContainer").style.display = "none";
}


function checkAllCheckboxes(checkboxName) {	    
	var checkboxes = document.getElementsByName(checkboxName);
	for (i = 0; i < checkboxes.length; i++) {
		checkboxes[i].checked = true;
	}
	
	return true;
}
