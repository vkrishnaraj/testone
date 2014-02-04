/*--------------------------------------------------------------------------
 *  Prototype JavaScript framework
 *  (c) 2005 Sam Stephenson <sam@conio.net>
 *  Prototype is freely distributable under the terms of an MIT-style license.
 *  For details, see the Prototype web site: http://prototype.conio.net/
 *
 *  note: modified & stripped down to be used with moo.fx
 *--------------------------------------------------------------------------*/

var Class = {
	create: function() {
		return function() {
			this.initialize.apply(this, arguments);
		}
	}
}

Object.extend = function(destination, source) {
	for (property in source) destination[property] = source[property];
	return destination;
}

Function.prototype.bind = function(object) {
	var __method = this;
	return function() {
		return __method.apply(object, arguments);
	}
}

Function.prototype.bindAsEventListener = function(object) {
var __method = this;
	return function(event) {
		__method.call(object, event || window.event);
	}
}

function $() {
	if (arguments.length == 1) return get$(arguments[0]);
	var elements = [];
	$c(arguments).each(function(el){
		elements.push(get$(el));
	});
	return elements;

	function get$(el){
		if (typeof el == 'string') el = document.getElementById(el);
		return el;
	}
}

if (!window.Element) var Element = new Object();

Object.extend(Element, {
	remove: function(element) {
		element = $(element);
		element.parentNode.removeChild(element);
	},

	hasClassName: function(element, className) {
		element = $(element);
		if (!element) return;
		var hasClass = false;
		element.className.split(' ').each(function(cn){
			if (cn == className) hasClass = true;
		});
		return hasClass;
	},

	addClassName: function(element, className) {
		element = $(element);
		Element.removeClassName(element, className);
		element.className += ' ' + className;
	},
  
	removeClassName: function(element, className) {
		element = $(element);
		if (!element) return;
		var newClassName = '';
		element.className.split(' ').each(function(cn, i){
			if (cn != className){
				if (i > 0) newClassName += ' ';
				newClassName += cn;
			}
		});
		element.className = newClassName;
	},

	cleanWhitespace: function(element) {
		element = $(element);
		$c(element.childNodes).each(function(node){
			if (node.nodeType == 3 && !/\S/.test(node.nodeValue)) Element.remove(node);
		});
	},

	find: function(element, what) {
		element = $(element)[what];
		while (element.nodeType != 1) element = element[what];
		return element;
	}
});

var Position = {
	cumulativeOffset: function(element) {
		var valueT = 0, valueL = 0;
		do {
			valueT += element.offsetTop  || 0;
			valueL += element.offsetLeft || 0;
			element = element.offsetParent;
		} while (element);
		return [valueL, valueT];
	}
};

document.getElementsByClassName = function(className) {
	var children = document.getElementsByTagName('*') || document.all;
	var elements = [];
	$c(children).each(function(child){
		if (Element.hasClassName(child, className)) elements.push(child);
	});  
	return elements;
}

//useful array functions
Array.prototype.each = function(func){
	for(var i=0;ob=this[i];i++) func(ob, i);
}

function $c(array){
	var nArray = [];
	for (i=0;el=array[i];i++) nArray.push(el);
	return nArray;
}

/*--------------------------------------------------------------------------
 *  moo.fx
 *  by Valerio Proietti (http://mad4milk.net) MIT-style LICENSE.
 *  for more info (http://moofx.mad4milk.net).
 *  Sunday, March 05, 2006
 *  v 1.2.3
 *--------------------------------------------------------------------------*/

var fx = new Object();
//base
fx.Base = function(){};
fx.Base.prototype = {
	setOptions: function(options) {
	this.options = {
		duration: 500,
		onComplete: '',
		transition: fx.sinoidal
	}
	Object.extend(this.options, options || {});
	},

	step: function() {
		var time  = (new Date).getTime();
		if (time >= this.options.duration+this.startTime) {
			this.now = this.to;
			clearInterval (this.timer);
			this.timer = null;
			if (this.options.onComplete) setTimeout(this.options.onComplete.bind(this), 10);
		}
		else {
			var Tpos = (time - this.startTime) / (this.options.duration);
			this.now = this.options.transition(Tpos) * (this.to-this.from) + this.from;
		}
		this.increase();
	},

	custom: function(from, to) {
		if (this.timer != null) return;
		this.from = from;
		this.to = to;
		this.startTime = (new Date).getTime();
		this.timer = setInterval (this.step.bind(this), 13);
	},

	hide: function() {
		this.now = 0;
		this.increase();
	},

	clearTimer: function() {
		clearInterval(this.timer);
		this.timer = null;
	}
}

//stretchers
fx.Layout = Class.create();
fx.Layout.prototype = Object.extend(new fx.Base(), {
	initialize: function(el, options) {
		this.el = $(el);
		this.el.style.overflow = "hidden";
		this.iniWidth = this.el.offsetWidth;
		this.iniHeight = this.el.offsetHeight;
		this.setOptions(options);
	}
});

fx.Height = Class.create();
Object.extend(Object.extend(fx.Height.prototype, fx.Layout.prototype), {	
	increase: function() {
		if ((this.now == this.to) && (this.to > 0)) {
			this.el.style.height = 'auto';
		} else {
			this.el.style.height = this.now + "px";
		}
	},

	toggle: function() {
		if (this.el.offsetHeight > 0) this.custom(this.el.offsetHeight, 0);
		else this.custom(0, this.el.scrollHeight);
	}
});

fx.Width = Class.create();
Object.extend(Object.extend(fx.Width.prototype, fx.Layout.prototype), {	
	increase: function() {
		this.el.style.width = this.now + "px";
	},

	toggle: function(){
		if (this.el.offsetWidth > 0) this.custom(this.el.offsetWidth, 0);
		else this.custom(0, this.iniWidth);
	}
});

//fader
fx.Opacity = Class.create();
fx.Opacity.prototype = Object.extend(new fx.Base(), {
	initialize: function(el, options) {
		this.el = $(el);
		this.now = 1;
		this.increase();
		this.setOptions(options);
	},

	increase: function() {
		if (this.now == 1 && (/Firefox/.test(navigator.userAgent))) this.now = 0.9999;
		this.setOpacity(this.now);
	},
	
	setOpacity: function(opacity) {
		if (opacity == 0 && this.el.style.visibility != "hidden") this.el.style.visibility = "hidden";
		else if (this.el.style.visibility != "visible") this.el.style.visibility = "visible";
		if (window.ActiveXObject) this.el.style.filter = "alpha(opacity=" + opacity*100 + ")";
		this.el.style.opacity = opacity;
	},

	toggle: function() {
		if (this.now > 0) this.custom(1, 0);
		else this.custom(0, 1);
	}
});

//transitions
fx.sinoidal = function(pos){
	return ((-Math.cos(pos*Math.PI)/2) + 0.5);
	//this transition is from script.aculo.us
}
fx.linear = function(pos){
	return pos;
}
fx.cubic = function(pos){
	return Math.pow(pos, 3);
}
fx.circ = function(pos){
	return Math.sqrt(pos);
}

//composition effect: widht/height/opacity
fx.Combo = Class.create();
fx.Combo.prototype = {
	setOptions: function(options) {
		this.options = {
			opacity: true,
			height: true,
			width: false
		}
		Object.extend(this.options, options || {});
	},

	initialize: function(el, options) {
		this.el = $(el);
		this.setOptions(options);
		if (this.options.opacity) {
			this.o = new fx.Opacity(el, options);
			options.onComplete = null;
		}
		if (this.options.height) {
			this.h = new fx.Height(el, options);
			options.onComplete = null;
		}
		if (this.options.width) this.w = new fx.Width(el, options);
	},
	
	toggle: function() { this.checkExec('toggle'); },

	hide: function(){ this.checkExec('hide'); },
	
	clearTimer: function(){ this.checkExec('clearTimer'); },
	
	checkExec: function(func){
		if (this.o) this.o[func]();
		if (this.h) this.h[func]();
		if (this.w) this.w[func]();
	},
	
	//only if width+height
	resizeTo: function(hto, wto) {
		if (this.h && this.w) {
			this.h.custom(this.el.offsetHeight, this.el.offsetHeight + hto);
			this.w.custom(this.el.offsetWidth, this.el.offsetWidth + wto);
		}
	},

	customSize: function(hto, wto) {
		if (this.h && this.w) {
			this.h.custom(this.el.offsetHeight, hto);
			this.w.custom(this.el.offsetWidth, wto);
		}
	}
}

fx.Accordion = Class.create();
fx.Accordion.prototype = {
	setOptions: function(options) {
		this.options = {
			delay: 100,
			opacity: false
		}
		Object.extend(this.options, options || {});
	},

	initialize: function(togglers, elements, options) {
		this.elements = elements;
		this.setOptions(options);
		var options = options || '';
		this.fxa = [];
		if (options && options.onComplete) options.onFinish = options.onComplete;
		elements.each(function(el, i){
			options.onComplete = function(){
				if (el.offsetHeight > 0) el.style.height = '1%';
				if (options.onFinish) options.onFinish(el);
			}
			this.fxa[i] = new fx.Combo(el, options);
			this.fxa[i].hide();
		}.bind(this));

		togglers.each(function(tog, i){
			if (typeof tog.onclick == 'function') var exClick = tog.onclick;
			tog.onclick = function(){
				if (exClick) exClick();
				this.showThisHideOpen(elements[i]);
			}.bind(this);
		}.bind(this));
	},

	showThisHideOpen: function(toShow){
		this.elements.each(function(el, j){
			if (el.offsetHeight > 0 && el != toShow) this.clearAndToggle(el, j);
			if (el == toShow && toShow.offsetHeight == 0) setTimeout(function(){this.clearAndToggle(toShow, j);}.bind(this), this.options.delay);
		}.bind(this));
	},

	clearAndToggle: function(el, i){
		this.fxa[i].clearTimer();
		this.fxa[i].toggle();
	}
}

/*--------------------------------------------------------------------------
 *  moo.ajax
 *  by Valerio Proietti (http://mad4milk.net) MIT-style LICENSE.
 *  for more info (http://moofx.mad4milk.net).
 *  based on prototype's ajax class
 *  to be used with prototype.lite, moofx.mad4milk.net.
 *--------------------------------------------------------------------------*/

ajax = Class.create();
ajax.prototype = {
	initialize: function(url, options){
		this.transport = this.getTransport();
		this.postBody = options.postBody || '';
		this.method = options.method || 'post';
		this.onComplete = options.onComplete || null;
		this.update = $(options.update) || null;
		this.request(url);
	},

	request: function(url){
		this.transport.open(this.method, url, true);
		this.transport.onreadystatechange = this.onStateChange.bind(this);
		if (this.method == 'post') {
			this.transport.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
			if (this.transport.overrideMimeType) this.transport.setRequestHeader('Connection', 'close');
		}
		this.transport.send(this.postBody);
	},

	onStateChange: function(){
		if (this.transport.readyState == 4 && this.transport.status == 200) {
			if (this.onComplete) 
				setTimeout(function(){this.onComplete(this.transport);}.bind(this), 10);
			if (this.update)
				setTimeout(function(){this.update.innerHTML = this.transport.responseText;}.bind(this), 10);
			this.transport.onreadystatechange = function(){};
		}
	},

	getTransport: function() {
		if (window.ActiveXObject) return new ActiveXObject('Microsoft.XMLHTTP');
		else if (window.XMLHttpRequest) return new XMLHttpRequest();
		else return false;
	}
};