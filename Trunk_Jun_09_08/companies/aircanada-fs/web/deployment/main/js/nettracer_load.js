function getLoadingContent() {
	jQuery('#dialog').dialog('option', 'buttons', {} );
	var h = '<div style="margin-top: 50px; text-align: center">';
	h += '<img width="64" height="64" src="/american/deployment/main/images/loading2.gif" /><br />';
	h += '<h5>Loading...</h5>';
	h += '</div>';
	return h;
}

function getSavingContent() {
	jQuery('#dialog').dialog('option', 'buttons', {} );
	var h = '<div style="margin-top: 50px; text-align: center">';
	h += '<img width="64" height="64" src="/american/deployment/main/images/loading2.gif" /><br />';
	h += '<h5>Saving...</h5>';
	h += '</div>';
	return h;
}