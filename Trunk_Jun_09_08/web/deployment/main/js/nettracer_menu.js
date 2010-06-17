/* top menu */
	
	var currentMenu = null;
	var lastMenuStarter = null;
	var mytimer = null;
	var timerOn = false;
	var opera = window.opera ? true : false;

	if (!document.getElementById)
		document.getElementById = function() { return null; }

	function initialiseDummy(dummy, root) {
		dummy.onmouseover = function() {
			containingMenu = this.parentNode.parentNode;
			for (var x=0;x<containingMenu.childNodes.length;x++) {
				if (containingMenu.childNodes[x].nodeName.toUpperCase()=="LI") {
					if (containingMenu.childNodes[x].getElementsByTagName("ul").length>0) {
						containingMenu.childNodes[x].getElementsByTagName("UL").item(0).style.visibility = 'hidden';
					}
				}
			}
		}
		dummy.onfocus = function() {
			dummy.onmouseover();
		}
	}
	
	function initialiseMenu(menu, starter, root) {
		var leftstarter = false;
	
		if (menu == null || starter == null) return;
			currentMenu = menu;
	
		starter.onmouseover = function() {
			if (currentMenu) {
				if (this.parentNode.parentNode!=currentMenu) {
					currentMenu.style.visibility = "hidden";
					hideAllMenus(currentMenu, root);

				}
				if (this.parentNode.parentNode==root) {
					while (currentMenu.parentNode.parentNode!=root) {
						currentMenu.parentNode.parentNode.style.visibility = "hidden";
						currentMenu = currentMenu.parentNode.parentNode;
					}
				}
				currentMenu = null;
				this.showMenu();
	        	}
		}
	
		menu.onmouseover = function() {
			if (currentMenu) {
				currentMenu = null;
				this.showMenu();
	        	}
		}	
	
		starter.showMenu = function() {
            
			if (!opera) {

				if (this.parentNode.parentNode==root) {
					menu.style.left = this.offsetLeft + "px";
					menu.style.top = this.offsetTop + this.offsetHeight + "px";
					if (menu.offsetWidth < this.offsetWidth) menu.style.width = this.offsetWidth;
				}
				else {
				 	menu.style.left = this.offsetLeft + this.offsetWidth + "px";
				 	menu.style.top = this.offsetTop + "px";
				}
			}
			else {
				var rootOffset = root.offsetLeft;
				if (this.parentNode.parentNode==root) {
					menu.style.left = this.offsetLeft - rootOffset + "px";
					menu.style.width = this.offsetWidth;
					menu.style.top = this.offsetHeight + "px";
				}
				else {
				 	menu.style.left = this.offsetWidth - rootOffset + "px";
				 	menu.style.top = this.offsetTop + "px"; //menu.style.top - menu.style.offsetHeight + "px";
				}

			}
			menu.style.visibility = "visible";
			currentMenu = menu;
		}

		starter.onfocus	 = function() {
			starter.onmouseover();
		}
	
		menu.onfocus	 = function() {
//			currentMenu.style.visibility="hidden";
		}

		menu.showMenu = function() {
            
			menu.style.visibility = "visible";
			currentMenu = menu;
			stopTime();
		}

		menu.hideMenu = function()  {
            
            
			if (!timerOn) {
				mytimer = setInterval("killMenu('" + this.id + "', '" + root.id + "');", 400);
				timerOn = true;
				for (var x=0;x<menu.childNodes.length;x++) {
					if (menu.childNodes[x].nodeName=="LI") {
						if (menu.childNodes[x].getElementsByTagName("UL").length>0) {
							menuItem = menu.childNodes[x].getElementsByTagName("UL").item(0);
							menuItem.style.visibility = "hidden";
						}
					}
				}
			}
		}

		menu.onmouseout = function(event) {
			this.hideMenu();
		}
		menu.onblur = function() {
			this.hideMenu();
		}
		starter.onmouseout = function() {
			
			for (var x=0;x<menu.childNodes.length;x++) {
				if (menu.childNodes[x].nodeName=="LI") {
					if (menu.childNodes[x].getElementsByTagName("UL").length>0) {
						menuItem = menu.childNodes[x].getElementsByTagName("UL").item(0);
						menuItem.style.visibility = "hidden";
					}
				}
			}
			menu.style.visibility = "hidden";
		}
}
	function killMenu(menu, root) {

		var menu = document.getElementById(menu);
		var root = document.getElementById(root);
            
		menu.style.visibility = "hidden";
		for (var x=0;x<menu.childNodes.length;x++) {
			if (menu.childNodes[x].nodeName=="LI") {
				if (menu.childNodes[x].getElementsByTagName("UL").length>0) {
					menuItem = menu.childNodes[x].getElementsByTagName("UL").item(0);
					menuItem.style.visibility = "hidden";
				}
			}
		}
		while (menu.parentNode.parentNode!=root) {
			menu.parentNode.parentNode.style.visibility = "hidden";
			menu = menu.parentNode.parentNode;
		}
		stopTime();
	}
	function stopTime() {
		if (mytimer) {
		 	 clearInterval(mytimer);
			 mytimer = null;
			 timerOn = false;
		}
	} 

	window.onload = function() {
		var root = document.getElementById("menuList");
		var rootOffset = root.offsetLeft;
		getMenus(root, root);
	}

function getMenus(elementItem, root) {
	var selectedItem;
	var menuStarter;
	var menuItem;
	for (var x=0;x<elementItem.childNodes.length;x++) {
		if (elementItem.childNodes[x].nodeName.toUpperCase()=="LI") {
			if (elementItem.childNodes[x].getElementsByTagName("ul").length>0) {
				menuStarter = elementItem.childNodes[x].getElementsByTagName("A").item(0);
				menuItem = elementItem.childNodes[x].getElementsByTagName("UL").item(0);
				getMenus(menuItem, root);
				initialiseMenu(menuItem, menuStarter, root);
			}
			else {
				initialiseDummy(elementItem.childNodes[x].getElementsByTagName("A").item(0), root);
			}
		}
	}
}
function hideAllMenus(elementItem, root) {
	for (var x=0;x<elementItem.childNodes.length;x++) {
		if (elementItem.childNodes[x].nodeName.toUpperCase()=="LI") {
			if (elementItem.childNodes[x].getElementsByTagName("ul").length>0) {
				elementItem.childNodes[x].getElementsByTagName("UL").item(0).style.visibility = 'hidden';
				hideAllMenus(elementItem.childNodes[x].getElementsByTagName("UL").item(0), root);
			}
		}
	}
}


function loadQuickSearchModal() {

jQuery("#dialog").dialog({bgiframe : true,
				autoOpen: false, modal: true, draggable: false, resizable: false, 
				width: 700, height: 500, title: 'Quick Search' 
	});

	jQuery('#dialog-inner-content').html('<div style="text-align: center; padding: 5 5 5 5; border-bottom: 2px blue solid;"><strong>Please enter your search criteria</strong><br/><p/><input type="text" name="search" class="textfield" id="quickSearchQuery3" onKeyDown="quickSearchKey3();"/>&nbsp; <button type="button" id="button" onclick="quickSearchKey4();">Search</button><br />');	
	jQuery("#dialog").dialog("open");
	var currentElement = document.getElementById("dialog-inner-content");
	currentElement.focus();
}


function quickSearchKey4(e) {
		var searchContent = document.getElementById('quickSearchQuery3').value;
		document.getElementById('quickSearchQuery2').value= searchContent;
		quickSearchKey2();
		}

function quickSearchKey3(e) {
	var key = event.keyCode;
	if (key == 13 && lock == false) {
		lock = true;
		var searchContent = document.getElementById('quickSearchQuery3').value;
		searchContent = searchContent.replace(/^\s+|\s+$/g,"");
		document.getElementById('quickSearchQuery2').value= searchContent;
		quickSearchKey2(true);
	}
}

var lock = false;
function quickSearchKey(e) {
	var key = event.keyCode;
	if (key == 13 && lock == false) {
		lock = true;
		quickSearchKey2(false);
		
	}
}

function quickSearchKey2(skip) {
		var searchContent = document.getElementById('quickSearchQuery2').value;
		if (!skip) {
			searchContent = searchContent.replace(/^\s+|\s+$/g,"");
		}
		searchContent = searchContent.replace(/%/g,"%25");
		if (searchContent.indexOf(':') >= 1) {
			var id = searchContent.substr(searchContent.indexOf(':') + 1,searchContent.length);
			if (searchContent.indexOf('I:') == 0 || searchContent.indexOf('i:') == 0) {
				window.document.location.href='searchIncident.do?incident='+id;
			} else if (searchContent.indexOf('O:') == 0 || searchContent.indexOf('o:') == 0) {
				window.document.location.href='addOnHandBag.do?ohd_ID='+id;
			} else if (searchContent.indexOf('A:') == 0 || searchContent.indexOf('a:') == 0) {
				window.document.location.href='agentAdmin.do?searchAgentUsername='+id;
			} else if (searchContent.indexOf('S:') == 0 || searchContent.indexOf('s:') == 0) {
				window.document.location.href='stationAdmin.do?edit=1&searchStationCode='+id;
			} else if (searchContent.indexOf('AI:') == 0 || searchContent.indexOf('ai:') == 0) {
				window.document.location.href='audit_mbr.do?detail=1&incident_ID='+id;
			} else if (searchContent.indexOf('AO:') == 0 || searchContent.indexOf('ao:') == 0) {
				window.document.location.href='audit_ohd.do?detail=1&ohd_ID='+id;
			}
		} else {

       		jQuery.ui.dialog.defaults.bgiframe = true;
       		jQuery("#dialog").dialog({bgiframe : true,
 				autoOpen: false, modal: true, draggable: false, resizable: false, 
 				width: 700, height: 500, title: 'Quick Search' 
		});
		jQuery('#dialog-inner-content').html(getLoadingContent());	
		jQuery("#dialog").dialog("open");	
		jQuery('#dialog-inner-content').load("quickSearch.do?search=" + searchContent, {}, function() { /* AFTER LOADING CONTENT */});
		lock = false;
	}
}

function qPrepopulateIncident(type, content, pnrOrTag) {
	var id = ''
	
	if (pnrOrTag == 0) {
		id = 'doprepopulate=11&qPrepopulate=1&recordlocator=' + content;
	} else {
		id = 'doprepopulate=11&qPrepopulate=1&bagTagNumber=' + content;
	}
	
	if (type == 1) {
		window.document.location.href='lostDelay.do?'+id;
	} else if (type == 2) {
		window.document.location.href='missing.do?'+id;
	} else if (type == 3) {
		window.document.location.href='damaged.do?'+id;
	} else if (type == 4) {
		window.document.location.href='addOnHandBag.do?'+id;
	}
  
}

