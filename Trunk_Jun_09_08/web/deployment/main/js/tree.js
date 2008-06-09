

// object holding all functions and variables
tree = {};

/* ------------------ user customization starts here ------------------ */

// classes used in HTML document to mark important elements
tree.classRoot			= "tree";		// ULs with this class will be transformed into trees
tree.classDefault		= "default";	// LIs with this class will be expanded by default
tree.classLast			= "last";		// this class will be added to all last branches of the tree
												// (this is good for easier CSS formatting of the tree)

// paths to images used in tree nodes
tree.nodeExpand			= "images/treeimages/tree-plus.gif",		// image of expandable node
tree.nodeExpandAlt		= "[ + ] ";
tree.nodeCollapse		= "images/treeimages/tree-minus.gif",		// image of collapsable node
tree.nodeCollapseAlt	= "[ - ] ";
tree.nodeNone			= "images/olapicons/tree-none.gif",		// image for node without children
tree.nodeNoneAlt		= "[ · ] ";

/* ------------------ user customization ends here ------------------ */


// initialisation of the tree
tree.init = function() {
	// find all unordered lists marked as trees
	var uls = tree.getElementsByClassName(document, tree.classRoot, "ul");
	for (var i = 0; i < uls.length; i++) {
		// find all last branches of the tree and mark them
		tree.markLast(uls[i]);
		var uls2 = uls[i].getElementsByTagName("ul");
		for (var j = 0; j < uls2.length; j++) {
			tree.markLast(uls2[j]);
		}
		
		// ad node pictures to all branches
		var lis = uls[i].getElementsByTagName("li");
		for (var j = 0; j < lis.length; j++) {
			tree.addNode(lis[j]);
		}
		
		// collapse all branches at the start
		tree.collapseAll(uls[i]);
		
		// find default branches and expand them		
		var def = tree.getElementsByClassName(uls[i], tree.classDefault, "li");
		for (var j = 0; j < def.length; j++) {
			var path = new Array();
			var step = def[j];
			while (step != uls[i]) {
				if (step.tagName == "LI") {
					tree.expand(step);
				}
				step = step.parentNode;
			}
		}
	}
}

// adds node picture at the beginning of all branches
tree.addNode = function(elm) {
	var uls = elm.getElementsByTagName("ul");
	var image = document.createElement("img");
	if (uls.length > 0) {
		image.src = tree.nodeExpand;
		image.alt = tree.nodeExpandAlt;
		addEvent(image, "click", tree.changeState);
	} else {
		image.src = tree.nodeNone;
		image.alt = tree.nodeNoneAlt;
	}
	elm.insertBefore(image, elm.firstChild);
}

// gets the actual state of branch and changes it
tree.changeState = function(e) {
	e = fixE(e);
	var obj = (e.currentTarget) ? e.currentTarget : e.target;
	while (obj.tagName != "LI") {
		obj = obj.parentNode;
	}
	if (obj.state == "collapsed") {
		tree.expand(obj);
	} else {
		tree.collapse(obj);
	}
	//update click log
	initClickLog();
}

// expands given branch
tree.expand = function(elm) {
	var uls = elm.getElementsByTagName("ul");
	for (var i = 0; i < uls.length; i++) {
		if (uls[i].parentNode == elm) {
			uls[i].style.display = "block";
			uls[i].parentNode.state = "expanded";
			elm.firstChild.src = tree.nodeCollapse;
			elm.firstChild.alt = tree.nodeCollapseAlt;
		}
	}
}

// collapses given branch
tree.collapse = function(elm) {
	var uls = elm.getElementsByTagName("ul");
	for (var i = 0; i < uls.length; i++) {
		if (uls[i].parentNode == elm) {
			uls[i].style.display = "none";
			uls[i].parentNode.state = "collapsed";
			elm.firstChild.src = tree.nodeExpand;
			elm.firstChild.alt = tree.nodeExpandAlt;
		}
	}
}

// collapses all branches in the given tree
tree.collapseAll = function(elm) {
	if (elm.tagName == "LI") {tree.expand(elm);}
	var lis = elm.getElementsByTagName("li");
	for (var i = 0; i < lis.length; i++) {
		tree.collapse(lis[i]);
	}
}

// expands all branches in the given tree
tree.expandAll = function(elm) {
	if (elm.tagName == "LI") {tree.expand(elm);}
	var lis = elm.getElementsByTagName("li");
	for (var i = 0; i < lis.length; i++) {
		tree.expand(lis[i]);
	}
}

// marks the last branch in the given branch as last
tree.markLast = function(elm) {
	var lis = elm.getElementsByTagName("li");
	var i = lis.length - 1;
	while (lis[i].parentNode != elm) {i--;}
	classMagic.add(lis[i], tree.classLast);
}

// returns all elements with given class, that are children of given source element
// attribute tagName is not required, but it speeds up the function a bit
tree.getElementsByClassName = function(srcElm, clName, tName) {
	foundElements = [];
	tName = (tName) ? tName.toUpperCase() : "*";
	allElements = srcElm.getElementsByTagName(tName);
	for (var i = 0; i < allElements.length; i++) {
		if (classMagic.has(allElements[i], clName)) {
			foundElements[foundElements.length] = allElements[i];
		}
	}
	return foundElements;
}

// returns subids of all nodes that have state=expanded
// tree.getExpandedElements = function(srcElm, clName, "li")
tree.getExpandedElements = function(elm) {
	foundElements = [];
	var lis = elm.getElementsByTagName("li");
	for (var i = 0; i < lis.length; i++) {
		if (lis[i].state == "expanded") {
			foundElements[foundElements.length] = lis[i];
		} 
	}
	return foundElements;
}

tree.getElementPropertyAsString = function(elm, delim) {
	str = "";
	for (var i = 0; i < elm.length; i++) {
		str = str + delim + elm[i].subid;
	}
	return str;
}

function initClickLog()
{
	cobj = document.all("CLICK_LOG");
	if(cobj)
	{
		cobj.value = tree.getElementPropertyAsString( tree.getExpandedElements( document.getElementById('first') ), '|');
	}
}


// initialisation of the script on load
addEvent(window, "load", tree.init);
