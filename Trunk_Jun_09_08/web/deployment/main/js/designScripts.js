function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);

// BEGIN Macromedia Rollover Scripts

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.0
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && document.getElementById) x=document.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

// END Macromedia Rollover Scripts

function CreateFlashObject() {
	document.write('<SCR' + 'IPT LANGUAGE=VBScript\> \n'); //FS hide this from IE4.5 Mac by splitting the tag
	document.write('on error resume next \n');
	document.write('MM_FlashCanPlay = ( IsObject(CreateObject("ShockwaveFlash.ShockwaveFlash." & MM_contentVersion)))\n');
	document.write('</SCR' + 'IPT\> \n');
}

function WriteFlash() {
	document.write('<OBJECT classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"');
	document.write('  codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0" ');
	document.write(' ID="topMenu" WIDTH="600" HEIGHT="300" ALIGN="">');
	document.write('<PARAM NAME=movie VALUE="flash/flash.swf"><PARAM NAME=loop VALUE=false><PARAM NAME=menu VALUE=false><PARAM NAME=quality VALUE=high><PARAM NAME=bgcolor VALUE=#333333>'); 
	document.write('<EMBED src="flash/flash.swf" loop=false menu=false quality=high bgcolor=#333333  ');
	document.write(' swLiveConnect=FALSE WIDTH="600" HEIGHT="300" NAME="topMenu" ALIGN=""');
	document.write(' TYPE="application/x-shockwave-flash" PLUGINSPAGE="http://www.macromedia.com/go/getflashplayer">');
	document.write('</EMBED>');
	document.write('</OBJECT>');
}

function textCounter(field, countfield, maxlimit) {

    for (var j=0;j<document.forms[0].length;j++) {
      currentElement = document.forms[0].elements[j];
      currentElementName=currentElement.name;
      countFieldName = countfield.name + "2";
      if (currentElementName == countFieldName)
      {
        if (field.value.length > maxlimit) // if the current length is more than allowed
            field.value =field.value.substring(0, maxlimit); // don't allow further input
        else
            currentElement.value = maxlimit - field.value.length;
      }
    }
} // set the display field to remaining number
