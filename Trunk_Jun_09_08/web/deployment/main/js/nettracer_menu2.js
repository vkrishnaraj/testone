function createShims() {
	var ieULs = document.getElementById("menuList").getElementsByTagName('ul');
	var footerDiv = document.getElementById("footer");
	for (j=0; j<ieULs.length; j++) {
		if (ieULs[j].id == "menubuilder0" || ieULs[j].id == "menubuilder4" || ieULs[j].id == "menubuilder6" || ieULs[j].id == "menubuilder7" || ieULs[j].id == "menubuilder8" || ieULs[j].id == "menubuilder9" || ieULs[j].id == "menubuilder10" ) {
			var ieMat=document.createElement('iframe');
			ieMat.src="javascript:false";

	
			ieMat.style.width=ieULs[j].offsetWidth+"px";
			ieMat.style.height=ieULs[j].offsetHeight+"px";
			ieMat.style.zIndex="-1";
			ieMat.style.position="absolute";
			ieMat.style.filter="progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)";
			ieULs[j].insertBefore(ieMat, ieULs[j].childNodes[0]);
			ieULs[j].style.zIndex="101";
		}
	}
	if (footerDiv != null) {
			var ieMat=document.createElement('iframe');
			ieMat.src="javascript:false";
	
			ieMat.style.width=footerDiv.offsetWidth+"px";
			ieMat.style.height=footerDiv.offsetHeight+"px";
			ieMat.style.zIndex="-1";
			footerDiv.insertBefore(ieMat, footerDiv.childNodes[0]);
			footerDiv.style.zIndex="101";
	}
}

if (window.attachEvent && navigator.appVersion.substr(22,3) == "6.0") window.attachEvent('onload', createShims);
