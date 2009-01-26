var chartWin = null;

function openChart(url)
{
    // compensate for margins
    w = 800;
    h = 50;

	var options = "width=" + w + ",height=" + h + ",menubar=no";
	chartWin = window.open(url,"bagtypechart",options);
}
<!--
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
    l = left;
	var options = "width=" + w + ",height=" + h +",left=" + l+",top=1";
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
    var currval = o.value;

    if(currval.indexof('%') != -1) {
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
        
        var parts = str.match(/(^.*?[A-Z])(\d+)$/i);
        
        if(parts != null) {
        	o.value = (parts[1] + zeros + parts[2]).toUpperCase();
        }
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