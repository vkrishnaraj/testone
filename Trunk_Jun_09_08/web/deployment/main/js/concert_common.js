function doClientAction(widget, widgetName, functionName, id)
{
	switch(functionName) {
		case 'ALERT':
			doAlert(widget,widgetName,id);
			break;
		case 'SETDIVVALUE':
			doSetDivValue(widget,widgetName,id);
			break;
		default:
			break;
	}
}

function doSubmitAction(widget, widgetName, functionName, id)
{
	var divs = findVariable(id +"_divsubmit");
	if (divs)
	{
		if (document.submitForm)
		{
			copyDivsForSubmit(divs);
			document.submitForm.ACTIONID.value = id;
			document.submitForm.submit();
		}
	}
}

function copyDivsForSubmit(divs)
{
	var contentToCopy = "";
	var divId = "";
	if (divs)
	{
		if (divs = ":all")
		{
			var contents = "";
			var submitDiv = document.all["SUBMITDIV"];
			if (submitDiv)
			{
				var allDivs = findVariable("ALLDIVS");
				if (allDivs)
				{
					divList = allDivs.split("|");
					for (var i=0; i<divList.length; i++)
					{
						nextDiv =document.all["DIV"+divList[i]];
						if (nextDiv)
						{
							var divContents = nextDiv.innerHTML;
							divContents = removeImageTags(divContents);
							contentToCopy += divContents;
						}
					}
				}
				submitDiv.innerHTML = contentToCopy;	
			}
		}
	} 
}


function removeImageTags(contents)
{
	var treg = new RegExp("<\\s*img[^>]*>","gi");
	return contents.replace(treg,"");
}
function doAlert(widget, widgetName, id)
{
	var message = findVariable(id + "_message");
	alert(message);
}

function doSetDivValue(widget, widgetName, id)
{
}

