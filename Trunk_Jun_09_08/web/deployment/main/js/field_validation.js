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
    var objRegExp  = /(^[a-z A-Z0-9]*$)/;
    return objRegExp.test(strng);
}

function checkClaimCheck(strng)
{ 
    var objRegExp  = /(^\d{10}$|^[a-zA-Z0-9]{2}\d{6}$)/;
    return objRegExp.test(strng);
}

function checkPnr(strng)
{
	var objRegExp  = /(^[a-z A-Z0-9\-]*$)/;
  return objRegExp.test(strng);
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
 
 function checkInteger(argvalue)
 {
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

var whitespace = " \t\n\r";

function checkEmail (s)
{   if (isEmpty(s)) 
       return true;
   
    // is s whitespace?
    if (isWhitespace(s)) return true;
    
    // there must be >= 1 character before @, so we
    // start looking at character position 1 
    // (i.e. second character)
    var i = 1;
    var sLength = s.length;

    // look for @
    while ((i < sLength) && (s.charAt(i) != "@"))
    { i++
    }

    if ((i >= sLength) || (s.charAt(i) != "@")) return false;
    else i += 2;

    // look for .
    while ((i < sLength) && (s.charAt(i) != "."))
    { i++
    }

    // there must be at least one character after the .
    if ((i >= sLength - 1) || (s.charAt(i) != ".")) return false;
    else return true;
}

function isEmpty(s)
{   return ((s == null) || (s.length == 0))
}

function isWhitespace (s)

{   var i;

    // Is s empty?
    if (isEmpty(s)) return true;

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