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
 
    var options = "width=" + w + ",height=" + h + ",resizable=yes,scrollbars=yes,toolbar=yes,menubar=yes";
    newwin = window.open(url,name,options);
}


function openWindow(url,name,w,h)
{
    // compensate for margins
    w = w;
    h = h;
 
    var options = "width=" + w + ",height=" + h + ",resizable=yes,scrollbars=yes,toolbar=no";
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

    // remove white space first
    currval = stripCharsInBag(currval,' \t\n\r');
    
    
    
    if (currval != null && currval.length > 0) {

        // get how many zeros should be inserted
        var len = maxlen - currval.length;
        if (len <= 0) return;
        var zeros = "";
        for (i = 0;i<len;i++) {
            zeros += "0";
        }

        
        // find out where alpha ends
        var pos = -1;
        for (i=0;i<currval.length;i++) {

            var c = currval.charAt(i);
            if (c == '%') return false;
            
            if (!isLetter(c)) {
                if (i-1<=0) pos = 0; 
                else pos = i;
                i = currval.length;
            }
        }

        if (pos > 0) o.value = currval.substring(0,pos) + zeros + currval.substring(pos);
     }      
     return true;
}

function isLetter (c)
{   return ( ((c >= "a") && (c <= "z")) || ((c >= "A") && (c <= "Z")) )
}

function stripCharsInBag (s, bag)

{   
    if (s == null) return null;
    var i;
    var returnString = "";

    // Search through string's characters one by one.
    // If character is not in bag, append to returnString.

    for (i = 0; i < s.length; i++)
    {   
        // Check that current character isn't whitespace.
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
    }

    return returnString;
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