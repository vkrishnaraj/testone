/**
 * @license Copyright (c) 2003-2013, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.html or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	config.height = '400px';
	
	config.toolbar = 'TemplateToolbar';
	 
	config.toolbar_TemplateToolbar =
	[
	 	{ name: 'editing', items : [ 'Source','Find','Replace','-','Scayt' ] },
		{ name: 'clipboard', items : [ 'Cut','Copy','Paste','-','Undo','Redo' ] },
		{ name: 'insert', items : [ 'Table','HorizontalRule','SpecialChar','PageBreak'] },
		'/',
		{ name: 'basicstyles', items : [ 'TextColor','Bold','Italic','Underline','-','RemoveFormat' ] },
		{ name: 'paragraph', items : [ 'NumberedList','BulletedList','-','JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock','-','Outdent','Indent' ] },
		'/',
		{ name: 'styles', items : [ 'Format','Font','FontSize' ] }
	];
	
	config.allowedContent = true;
	
};
