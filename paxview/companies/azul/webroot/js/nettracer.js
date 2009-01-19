function fillzero(o,maxlen) {
    var currval = o.value;
    var i = 0;
    
    // remove white space first
    currval = stripCharsInBag(currval,' \t\n\r');
    
    if (currval != null && currval.length > 0) {

        // get how many zeros should be inserted
        var len = maxlen - currval.length;
        if (len <= 0) return false;
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