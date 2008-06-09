/*
Scott Andrew's event attacher
http://www.scottandrew.com/

Modified by Riki 'Fczbkk' Fridrich to work correctly with Opera 7+. You should
find actual version of this script at
http://js.fczbkk.sk/event_attacher/
*/


function addEvent(obj, evType, fn, useCapture){
	// Operu 7+ hacks
	if (window.opera) {
		// Opera doesn't accept attaching events on object window, but accepts them on object document
		if (obj == window) {
			obj = document;
		}
	}
	
	if (obj.addEventListener){
		obj.addEventListener(evType, fn, useCapture);
		return true;
	} else if (obj.attachEvent){
		var r = obj.attachEvent("on"+evType, fn);
		return r;
	} else {
		return false;
	}
}

function removeEvent(obj, evType, fn, useCapture) {
	// Operu 7+ hacks
	if (window.opera) {
		// Opera doesn't accept detaching events on object window, but accepts them on object document
		if (obj == window) {
			obj = document;
		}
	}

	if (obj.removeEventListener) {
		obj.removeEventListener(evType, fn, useCapture);
		return true;
	} else if (obj.detachEvent) {
		var r = obj.detachEvent("on"+evType, fn);
		return r;
	} else {
		return false;
	}
}