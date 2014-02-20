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
	 	{ name: 'editing', items : [ 'Source','Find','Replace' ] },
		{ name: 'clipboard', items : [ 'Cut','Copy','Paste','-','Undo','Redo' ] },
		{ name: 'insert', items : [ 'Table','HorizontalRule','SpecialChar','PageBreak','Image'] },
		'/',
		{ name: 'basicstyles', items : [ 'TextColor','Bold','Italic','Underline','-','RemoveFormat' ] },
		{ name: 'paragraph', items : [ 'NumberedList','BulletedList','-','JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock','-','Outdent','Indent' ] },
		'/',
		{ name: 'styles', items : [ 'Format','Font','FontSize' ] }
	];
	
	config.allowedContent = true;
	
	config.disableNativeSpellChecker = false;
	
	config.removePlugins = 'contextmenu';
	
	var _FileBrowserLanguage	= 'php' ;
	var _QuickUploadLanguage	= 'php' ;

	var _FileBrowserExtension = 'php' ;
	var _QuickUploadExtension = 'php' ;
	
	config.filebrowserBrowseUrl = CKEDITOR.basePath + 'filemanager/browser/default/browser.html?Connector=' + encodeURIComponent( CKEDITOR.basePath + 'filemanager/connectors/' + _FileBrowserLanguage + '/connector.' + _FileBrowserExtension ) ;
	config.filebrowserUploadUrl = CKEDITOR.basePath + 'filemanager/connectors/' + _QuickUploadLanguage + '/upload.' + _QuickUploadExtension ;
	
	config.filebrowserImageBrowseUrl = CKEDITOR.basePath + 'filemanager/browser/default/browser.html?Type=Image&Connector=' + encodeURIComponent( CKEDITOR.basePath + 'filemanager/connectors/' + _FileBrowserLanguage + '/connector.' + _FileBrowserExtension ) ;
	config.filebrowserImageUploadUrl = CKEDITOR.basePath + 'filemanager/connectors/' + _QuickUploadLanguage + '/upload.' + _QuickUploadExtension + '?Type=Image' ;
	
	config.filebrowserFlashBrowseUrl = CKEDITOR.basePath + 'filemanager/browser/default/browser.html?Type=Flash&Connector=' + encodeURIComponent( CKEDITOR.basePath + 'filemanager/connectors/' + _FileBrowserLanguage + '/connector.' + _FileBrowserExtension ) ;
	config.filebrowserFlashUploadUrl = CKEDITOR.basePath + 'filemanager/connectors/' + _QuickUploadLanguage + '/upload.' + _QuickUploadExtension + '?Type=Flash' ;
	
};
