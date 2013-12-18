function checkLegFrom(strng)
{
  var objRegExp  = /(^[a-zA-Z]*$)/;
  return objRegExp.test(strng);
}
  
function checkFlightNum(strng)
{
  var objRegExp  = /(^[a-z A-Z0-9]*$)/;
  return objRegExp.test(strng);
}

function checkExpedite(strng)
{     
    var objRegExp  = /(^\d{10}$|^[a-zA-Z0-9]{2}\d{6}$|^\d{12}$)/;
    return objRegExp.test(strng);
}

function checkClaimCheck(strng)
{ 
    var objRegExp  = /(^UTB\d{1,8}$|^\d{10}$|^[a-zA-Z0-9]{2}\d{6}$|^\d{12}$)/;
    return objRegExp.test(strng.toUpperCase());
}

function checkPnr(strng)
{
	var objRegExp  = /(^[a-z A-Z0-9\-]*$)/;
  return objRegExp.test(strng);
}


function checkFloatGreaterThan0(value)
{ 
    // remove '.' before checking digits
    var tempArray = value.split('.');
    var joinedString= tempArray.join('');

    if (!checkInteger(joinedString)) {
      return false;
    } 
    else 
    {
      var iValue = parseFloat(value);
      if (iValue > 0) {
      	return true;
      }
      
    }   
    return false;
}

function checkFloat(value)
{ 
    // remove '.' before checking digits
    var tempArray = value.split('.');
    var joinedString= tempArray.join('');

    if (!checkInteger(joinedString)) {
      return false;
    } 
    else 
    {
      var iValue = parseFloat(value);
      if (isNaN(iValue)) {
        return false;
      }
    }   
    return true;
}

function checkZip( strValue ) {
  
  var objRegExp  = /(^[a-z A-Z0-9\-]*$)/;

  //check for valid US Zipcode
  return objRegExp.test(strValue);
 }

 function checkPhone(strng)
 {
  var objRegExp = /(^[()a-z A-Z0-9\-]*$)/;
  return objRegExp.test(strng);
 }

 function checkSSN(strng)
 {
  var objRegExp = /^(\d{9}$)/;
  return objRegExp.test(strng);
 }
 
 function checkInteger(argvalue) {
	 if (argvalue == null) {
		 return false;
	 } 
	 
    argvalue = argvalue.toString();
    var validChars = "0123456789";
    var startFrom = 0;
    if (argvalue.substring(0, 2) == "0x") {
       validChars = "0123456789abcdefABCDEF";
       startFrom = 2;
    } else if (argvalue.charAt(0) == "0") {
       validChars = "01234567";
       startFrom = 1;
    } else if (argvalue.charAt(0) == "-") {
        startFrom = 1;
    }
    
    for (var n = startFrom; n < argvalue.length; n++) {
        if (validChars.indexOf(argvalue.substring(n, n+1)) == -1) return false;
    }
    return true;
}
  
 function isPositiveInteger(argvalue) {
	 if (!checkInteger(argvalue)) {
		 return false;
	 } 
	 
	 var iValue = parseInt(argvalue,10);
	 return !isNaN(iValue) && iValue > -1;
} 
 
 function minimumInteger(argvalue, minimum) {
	 if (!isPositiveInteger(argvalue) || !isPositiveInteger(minimum)) {
		 return false;
	 } 

	 var iValue = parseInt(argvalue, 10);
	 var iMinimum = parseInt(minimum, 10);
	 return !isNaN(iValue) && !isNaN(iMinimum) && iValue >= iMinimum;
} 
 

var whitespace = " \t\n\r";

function checkEmail (s)
{   if (isEmpty1(s)) 
       return true;
   
    // is s whitespace?
    if (isWhitespace(s)) return true;
    
	var objRegExp  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	return objRegExp.test(s);

}

function isEmpty1(s)
{   return ((s == null) || (s.length == 0))
}

function isWhitespace (s)

{   var i;

    // Is s empty?
    if (isEmpty1(s)) return true;

    // Search through string's characters one by one
    // until we find a non-whitespace character.
    // When we do, return false; if we don't, return true.

    for (i = 0; i < s.length; i++)
    {   
        // Check that current character isn't whitespace.
        var c = s.charAt(i);

        if (whitespace.indexOf(c) == -1) return false;
    }

    // All characters are whitespace.
    return true;
}

	function checkstate(var1,o,statefield, province_field) {
	
		pos = var1.name.indexOf(".");
		if (pos <=0) addr = "";
		else addr = var1.name.substring(0,pos+1);
		state = addr + statefield;
		province = addr + province_field;
			
		if (!var1.value) {
			//no country, both state and province enabled
			o.elements[province].className = 'textfield';
			o.elements[province].disabled = false;
			
			o.elements[state].disabled = false;
			
		} else if (var1.value == 'US') {
			//US - enable state, disable province
			o.elements[state].disabled = false;
			o.elements[province].value = '';
			o.elements[province].disabled = true;
			o.elements[province].className = 'disabledtextfield';
		} else {
			//International - disable state, enable province
			o.elements[state].selectedIndex = 0;
			o.elements[state].disabled = true;			
			o.elements[province].className = 'textfield';
			o.elements[province].disabled = false;
		}
	}
	
	function languagefreeflow(var1,o,field){
		pos = var1.name.indexOf(".");
		if (pos <=0) addr = "";
		else addr = var1.name.substring(0,pos+1);
		lang = addr + field;
		
		if(var1.value == 'other'){
			o.elements[lang].style.display='block';
			o.elements[lang].value='';
		} else {
			o.elements[lang].style.display='none';
			o.elements[lang].value=var1.options[var1.selectedIndex].text;
		}
	}
	
	function updateCountryUS(var1, myform, countryField, provinceField) {
		if (var1.value) {
			pos = var1.name.indexOf(".");
			if (pos <=0) addr = "";
			else addr = var1.name.substring(0,pos+1);
			country = addr + countryField;
			province = addr + provinceField;
			
			myform.elements[province].disabled = true;
			myform.elements[province].className = 'disabledtextfield';
			myform.elements[country].value = 'US';
		}
	}
	