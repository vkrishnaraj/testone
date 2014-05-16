
var HBUsePageContents=false;var gServer="kanachat.delta.com";var gUseDynamicStartPage=false;var gDSPLauncherPageName="/shared/components/responseLiveLauncher.html";var gChatWindowWidth=300;var gChatWindowHeight=500;var gChatWindowProperties="width="+gChatWindowWidth+",height="+gChatWindowHeight+",menubar=no,location=no,directories=no,status=no,toolbar=no,scrollbars=auto,resizable=yes,screenX=10,screenY=10,left=10,top=10";var gHTTP="http://";var gHTTPS="https://";var gChatLaunchWindow=null;function startChat(iChannel,server_name,attached_data,custom_values,agent_only_values,go_immediately_into_queue)
{var chatLaunchMode="CHAT_ONLY";launchChat(iChannel,server_name,attached_data,custom_values,agent_only_values,go_immediately_into_queue,chatLaunchMode);}
function startChatAndCobrowse(iChannel,server_name,attached_data,custom_values,agent_only_values,go_immediately_into_queue)
{var chatLaunchMode="COBROWSE";launchChat(iChannel,server_name,attached_data,custom_values,agent_only_values,go_immediately_into_queue,chatLaunchMode);}
function startChatWithEscalation(iChannel,server_name,attached_data,custom_values,agent_only_values,go_immediately_into_queue)
{var chatLaunchMode="COBROWSE_ESCALATION";launchChat(iChannel,server_name,attached_data,custom_values,agent_only_values,go_immediately_into_queue,chatLaunchMode);}
function launchChat(iChannel,serverName,attachedData,prefillValues,agentOnlyValues,enterOnQueuePage,chatLaunchMode)
{if(alreadyChatting())
{alert("You are already in a session.");var win=getChatWindow();if(null!=win&&!win.closed)
{win.focus();}
return;}
openInitialWindow();finishLaunchingChat(iChannel,serverName,attachedData,prefillValues,agentOnlyValues,enterOnQueuePage,chatLaunchMode)}
function finishLaunchingChat(iChannel,serverName,attachedData,prefillValues,agentOnlyValues,enterOnQueuePage,chatLaunchMode)
{var chatWin=getChatWindow();saveChatParamaters(chatWin,iChannel,serverName,attachedData,prefillValues,agentOnlyValues,enterOnQueuePage,chatLaunchMode);if(alreadyConavigating())
{handleRecursiveLaunch(iChannel,serverName,chatWin);return;}
if(browserDoesNotSupportDOM2()||isIEonMac())
{handleUnsupportedBrowser(iChannel,serverName,chatWin);return;}
if(window.location.protocol=="https:")
createAndSubmitForm();else
checkSSLEnabledAndSubmit(iChannel,serverName);}
function createAndSubmitForm()
{var chatWin=getChatWindow();var launchChatForm=createForm(chatWin.serverName,chatWin);addFormField(launchChatForm,chatWin,"CHAT_WINDOW_WIDTH",gChatWindowWidth);addFormField(launchChatForm,chatWin,"CHAT_WINDOW_PROPERTIES",gChatWindowProperties);addFormField(launchChatForm,chatWin,"ICHANNEL_ID",chatWin.iChannel);addFormField(launchChatForm,chatWin,"ATTACHED_DATA",chatWin.attachedData);addFormField(launchChatForm,chatWin,"ENTER_ON_QUEUE_PAGE",chatWin.enterOnQueuePage);addFormField(launchChatForm,chatWin,"CHAT_LAUNCH_MODE",chatWin.chatLaunchMode);addFormField(launchChatForm,chatWin,"AGENT_VISIBLE_DATA",createAgentVisibleDataString(chatWin.agentOnlyValues));addFormField(launchChatForm,chatWin,"REFERRER_URL",window.location.href);if(typeof(gUseDynamicStartPage)!='undefined'&&gUseDynamicStartPage)
addFormField(launchChatForm,chatWin,"DSP_LAUNCHER_PAGE_NAME",gDSPLauncherPageName);var prefillValues=chatWin.prefillValues;for(prefillItem in prefillValues)
{addFormField(launchChatForm,chatWin,prefillItem,prefillValues[prefillItem]);}
launchChatForm.submit();}
var gKanaLiveDebug=false;function openInitialWindow()
{var newWin=window.open("",getChatWindowName(),getWinProperties());try{newWin.document.write("<html><body></body></html>");newWin.document.close();}catch(e){if(gKanaLiveDebug)
alert(e);}
setChatWindow(newWin);}
function createForm(serverName,win)
{var launchChatForm=win.document.createElement("form");launchChatForm.method="post";launchChatForm.target="_self";launchChatForm.action=gHTTPS+serverName+"/CONAV/CHAT/ChatPreLaunch";win.document.body.appendChild(launchChatForm);return launchChatForm;}
var DATA_SEPARATOR="_HB_";function createAgentVisibleDataString(agentOnlyValues)
{var agentVisibleData="";for(agentItem in agentOnlyValues)
{if(isNonEmptyString(agentVisibleData))
agentVisibleData+=DATA_SEPARATOR;agentVisibleData+=agentItem+DATA_SEPARATOR+agentOnlyValues[agentItem];}
return agentVisibleData;}
function addFormField(launchChatForm,win,name,value)
{if(!isNonEmptyString(name)||!isNonEmptyString(value))
return;var elementObj=win.document.createElement("input");elementObj.type="hidden";elementObj.name=name;elementObj.value=value;launchChatForm.appendChild(elementObj);}
function alreadyChatting()
{var theCookieString=document.cookie;return(checkChatWindowExists()||(isNonEmptyString(theCookieString)&&theCookieString.indexOf("hbuidv23=")>-1));}
function checkChatWindowExists()
{var win=getChatWindow();return(null!=win&&!win.closed);}
function alreadyConavigating()
{return(typeof(isHipboneSharedWindow)!="undefined");}
function handleRecursiveLaunch(iChannel,serverName,win)
{win.location.href=gHTTPS+serverName+"/CONAV/chat/errorpages/recursiveConav.jsp?ICHANNEL_ID="+iChannel;}
function browserDoesNotSupportDOM2()
{return(null==document.getElementById);}
function handleUnsupportedBrowser(iChannel,serverName,win)
{win.location.href=gHTTPS+serverName+"/CONAV/CHAT/ChatPreLaunch?ICHANNEL_ID="+iChannel;}
function checkSSLEnabledAndSubmit(iChannel,serverName)
{createAndSubmitForm();var testImg=new Image(1,1);testImg.onerror=function(){handleChatNoSSL(iChannel,serverName);}
testImg.src=gHTTPS+serverName+"/CONAV/HTD/Default/shared/images/blank.gif?time="+(new Date()).getTime();}
function handleChatNoSSL(iChannel,serverName)
{var link=gHTTP+serverName+"/CONAV/chat/errorpages/sslDisabled.jsp?ICHANNEL_ID="+iChannel;window.open(link,getChatWindowName(),getWinProperties());getChatWindow().close();}
function saveChatParamaters(chatWin,iChannel,serverName,attachedData,prefillValues,agentOnlyValues,enterOnQueuePage,chatLaunchMode)
{chatWin.iChannel=iChannel;chatWin.serverName=serverName;chatWin.attachedData=attachedData;chatWin.prefillValues=prefillValues;chatWin.agentOnlyValues=agentOnlyValues;chatWin.enterOnQueuePage=enterOnQueuePage;chatWin.chatLaunchMode=chatLaunchMode;}
function setChatWindow(chatWin)
{window.gChatLaunchWindow=chatWin;}
function getChatWindow()
{return window.gChatLaunchWindow;}
function getChatWindowName()
{return"_blank";}
function getWinProperties()
{return gChatWindowProperties;}
function isIE()
{return(navigator.userAgent.toLowerCase().indexOf("msie")!=-1);}
function isMac()
{return(navigator.userAgent.toLowerCase().indexOf("mac")!=-1);}
function isIEonMac()
{return(isIE()&&isMac())}
function isNonEmptyString(str)
{if(null==str||str=="")
return false;return true;}
function goToAfterLaunchUrl()
{if(typeof(window.gAfterLaunchURL)=="undefined")
return;window.location.href=window.gAfterLaunchURL;}