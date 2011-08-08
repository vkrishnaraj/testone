function decreaseFontSize() {
   var s = 8;
   var p = document.getElementsByTagName('span');
   for(i=0;i<p.length;i++) {
      p[i].style.fontSize = s+"pt";
   }   
  
   var para = document.getElementsByTagName('p');
   for(i=0;i<para.length;i++) {
	      para[i].style.fontSize = s+"pt";
	   } 
   
   var li = document.getElementsByTagName('li');
   for(i=0;i<li.length;i++) {
	      li[i].style.fontSize = s+"pt";
	   } 
   
   setId();
   hiddenFontSize = document.getElementById("appForm:appFontSize");
   hiddenFontSize.value = "small";
   
}


	function mediumFontSize() {
	   var s = 10;
	   var p = document.getElementsByTagName('span');
	   for(i=0;i<p.length;i++) {
		      p[i].style.fontSize = s+"pt";
	   }
	   
	   var para = document.getElementsByTagName('p');
	   for(i=0;i<para.length;i++) {
		      para[i].style.fontSize = s+"pt";
		   } 
	   
	   var li = document.getElementsByTagName('li');
	   for(i=0;i<li.length;i++) {
		      li[i].style.fontSize = s+"pt";
		   } 
	   
	   setId();
	   hiddenFontSize = document.getElementById("appForm:appFontSize");
	   hiddenFontSize.value = "medium";
	}




function increaseFontSize() {
	   var s = 12;
	   var p = document.getElementsByTagName('span');
	   for(i=0;i<p.length;i++) {
	      p[i].style.fontSize = s+"pt";
	   }
	   
	   var para = document.getElementsByTagName('p');
	   for(i=0;i<para.length;i++) {
		      para[i].style.fontSize = s+"pt";
		   } 
	   
	   var li = document.getElementsByTagName('li');
	   for(i=0;i<li.length;i++) {
		      li[i].style.fontSize = s+"pt";
		   } 
	   
	   setId();
	   hiddenFontSize = document.getElementById("appForm:appFontSize");
	   hiddenFontSize.value = "large";
	}

function setInitialFontSize() {
	var fontSize = document.getElementById("appForm:appFontSize").value;
	if (fontSize == "small") {
		decreaseFontSize()
	} else if (fontSize == "medium") {
		mediumFontSize()
	} else if (fontSize == "large") {
		increaseFontSize()
	}
}


	function setId(){
			var id1 = document.getElementById("font1");
		   var id2 = document.getElementById("font2");
		   var id3 = document.getElementById("font3");
		   
		   var a = parseInt(id1.style.fontSize.replace("pt",""));
		   a=8;
		   
		   var b = parseInt(id2.style.fontSize.replace("pt",""));
		   b=10;
		   
		   var c = parseInt(id3.style.fontSize.replace("pt",""));
		   c=12;
		   
		   id1.style.fontSize = a+"pt";
		   id2.style.fontSize = b+"pt";
		   id3.style.fontSize = c+"pt";
		   
		  
	
	}
