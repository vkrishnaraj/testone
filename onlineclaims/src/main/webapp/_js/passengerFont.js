function decreaseFontSize() {
	
   var p = document.getElementsByTagName('span');
   
   for(i=0;i<p.length;i++) {
      if(p[i].style.fontSize) {
         var s = parseInt(p[i].style.fontSize.replace("pt",""));
         s=8;
      } else {
         var s = 8;
      }
      p[i].style.fontSize = s+"pt";
   }   
   
  
  
   var para = document.getElementsByTagName('p');
   for(i=0;i<para.length;i++) {
	      if(para[i].style.fontSize) {
	         var s = parseInt(para[i].style.fontSize.replace("pt",""));
	         s=8;
	      } else {
	         var s = 8;
	      }
	      para[i].style.fontSize = s+"pt";
	   } 
   setId();
   
}


	function mediumFontSize() {
	   var p = document.getElementsByTagName('span');
	   for(i=0;i<p.length;i++) {
		   if(p[i].style.fontSize) {
		         var s = parseInt(p[i].style.fontSize.replace("pt",""));
		         s=10;
		      } else {
		         var s = 10;
		      }
		      
		      p[i].style.fontSize = s+"pt";
	   }
	   
	 
	  
	   
	   var para = document.getElementsByTagName('p');
	   for(i=0;i<para.length;i++) {
		      if(para[i].style.fontSize) {
		         var s = parseInt(para[i].style.fontSize.replace("pt",""));
		         s=10;
		      } else {
		         var s = 10;
		      }
		      para[i].style.fontSize = s+"pt";
		   } 
	   
	   setId();
	}




function increaseFontSize() {
	   var p = document.getElementsByTagName('span');
	   for(i=0;i<p.length;i++) {
	      if(p[i].style.fontSize) {
	         var s = parseInt(p[i].style.fontSize.replace("pt",""));
	         s = 12;
	      } else {
	         var s = 12;
	      }
	      
	      p[i].style.fontSize = s+"pt";
	   }
	   
	  
	
	   
	   var para = document.getElementsByTagName('p');
	   for(i=0;i<para.length;i++) {
		      if(para[i].style.fontSize) {
		         var s = parseInt(para[i].style.fontSize.replace("pt",""));
		         s=12;
		      } else {
		         var s = 12;
		      }
		      para[i].style.fontSize = s+"pt";
		   } 
	   
	   setId();
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
