function fillzero(o,maxlen) {
    var currval = o.value;

    if(currval.indexOf('%') != -1) {
    	return false;
    }
    
    // remove white space first
    currval = currval.replace(/\s/gi, '');

    if (currval != null && currval.length > 0) {
        // get how many zeros should be inserted
        var len = maxlen - currval.length;
        if (len <= 0) return;
        var zeros = "";
        for (i = 0;i<len;i++) {
            zeros += "0";
        }
        
        //jetblue specific test
        var parts = currval.match(/(\w{2,5}B6)(\d+)$/i);
        
        if(parts == null) {
        	parts = currval.match(/(^.*?[A-Z])(\d+)$/i);
        }
        
        if(parts != null) {
        	o.value = (parts[1] + zeros + parts[2]).toUpperCase();
        }
    }    
    return true;
}

function formatForm(form) {
	fillzero(form.claimnumber, 13);
	return true;
}