ie6Hover = function() {
	var ieULs = document.getElementById('nav').getElementsByTagName('ul');

	for (j=0; j<ieULs.length; j++) {
		if (location.protocol=="https:") {
			ieULs[j].innerHTML = ('<iframe src="blank.htm" scrolling="no" frameborder="0"></iframe>' + ieULs[j].innerHTML);
		}
		else {
			ieULs[j].innerHTML = ('<iframe src="about:blank" scrolling="no" frameborder="0"></iframe>' + ieULs[j].innerHTML);
		}

		var ieMat = ieULs[j].firstChild;
		ieMat.style.width=ieULs[j].offsetWidth+"px";
		ieMat.style.height=ieULs[j].offsetHeight+"px";	
		ieULs[j].style.zIndex="99";
	}

	var ieLIs = document.getElementById('nav').getElementsByTagName('li');

	for (var i=0; i<ieLIs.length; i++) {
		if ( ieLIs[i] ) {

			ieLIs[i].onmouseover=function() { this.className+=" ie6hover"; }
			ieLIs[i].onmouseout=function() { this.className=this.className.replace(' ie6hover', ''); }
		}
	}
}

if (window.attachEvent) window.attachEvent('onload', ie6Hover);
