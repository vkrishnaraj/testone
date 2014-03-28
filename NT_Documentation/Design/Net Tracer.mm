<map version="freeplane 1.2.0">
<!--To view this file, download free mind mapping software Freeplane from http://freeplane.sourceforge.net -->
<node TEXT="Net Tracer" ID="ID_534664009" CREATED="1395953148856" MODIFIED="1395953174509"><hook NAME="MapStyle">
    <properties show_note_icons="true"/>

<map_styles>
<stylenode LOCALIZED_TEXT="styles.root_node">
<stylenode LOCALIZED_TEXT="styles.predefined" POSITION="right">
<stylenode LOCALIZED_TEXT="default" MAX_WIDTH="600" COLOR="#000000" STYLE="as_parent">
<font NAME="SansSerif" SIZE="10" BOLD="false" ITALIC="false"/>
</stylenode>
<stylenode LOCALIZED_TEXT="defaultstyle.details"/>
<stylenode LOCALIZED_TEXT="defaultstyle.note"/>
<stylenode LOCALIZED_TEXT="defaultstyle.floating">
<edge STYLE="hide_edge"/>
<cloud COLOR="#f0f0f0" SHAPE="ROUND_RECT"/>
</stylenode>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.user-defined" POSITION="right">
<stylenode LOCALIZED_TEXT="styles.topic" COLOR="#18898b" STYLE="fork">
<font NAME="Liberation Sans" SIZE="10" BOLD="true"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.subtopic" COLOR="#cc3300" STYLE="fork">
<font NAME="Liberation Sans" SIZE="10" BOLD="true"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.subsubtopic" COLOR="#669900">
<font NAME="Liberation Sans" SIZE="10" BOLD="true"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.important">
<icon BUILTIN="yes"/>
</stylenode>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.AutomaticLayout" POSITION="right">
<stylenode LOCALIZED_TEXT="AutomaticLayout.level.root" COLOR="#000000">
<font SIZE="18"/>
</stylenode>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,1" COLOR="#0033ff">
<font SIZE="16"/>
</stylenode>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,2" COLOR="#00b439">
<font SIZE="14"/>
</stylenode>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,3" COLOR="#990000">
<font SIZE="12"/>
</stylenode>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,4" COLOR="#111111">
<font SIZE="10"/>
</stylenode>
</stylenode>
</stylenode>
</map_styles>
</hook>
<node TEXT="Performance" POSITION="right" ID="ID_518362160" CREATED="1395953177913" MODIFIED="1395953184920">
<node TEXT="Concurrency" ID="ID_1022828901" CREATED="1395953305345" MODIFIED="1395953310197"/>
<node TEXT="Response Time per request" ID="ID_1387673948" CREATED="1395953312136" MODIFIED="1395953322780"/>
<node TEXT="Data size per request" ID="ID_989379258" CREATED="1395953325321" MODIFIED="1395954732389"/>
</node>
<node TEXT="Security" POSITION="left" ID="ID_736632079" CREATED="1395953188425" MODIFIED="1395953206469">
<node TEXT="Authentication" ID="ID_483258380" CREATED="1395953276697" MODIFIED="1395955667070"><richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      There are 2 authentication entry points into the system
    </p>
    <p>
      
    </p>
    <p>
      SSO using SAML requests
    </p>
    <p>
      Page Login
    </p>
    <p>
      
    </p>
    <p>
      the authentication uses the agent table to verify that the user is valid. Also, the password is encrypted in the database and is decrypted when authentication is needed.
    </p>
  </body>
</html>
</richcontent>
</node>
<node TEXT="Authorization" ID="ID_1885897148" CREATED="1395953284521" MODIFIED="1395955727486"><richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      permissions table holds all the permissions that user needs to access any section of the system
    </p>
  </body>
</html>
</richcontent>
</node>
<node TEXT="Accountablility" ID="ID_212076688" CREATED="1395953292145" MODIFIED="1395955772312">
<icon BUILTIN="flag"/>
<richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      NEED more investigation
    </p>
  </body>
</html>
</richcontent>
</node>
</node>
<node TEXT="Development" POSITION="right" ID="ID_1270863797" CREATED="1395953209497" MODIFIED="1395953232444">
<node TEXT="CVS" ID="ID_175907763" CREATED="1395954347643" MODIFIED="1395954988896"><richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      All clients are currently under one project making it difficult to identify customizations between clients.
    </p>
  </body>
</html>
</richcontent>
</node>
<node TEXT="Eclipse" ID="ID_1160052764" CREATED="1395954359691" MODIFIED="1395954364127"/>
<node TEXT="Hudson" ID="ID_1341489170" CREATED="1395954365619" MODIFIED="1395954861280"><richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      Hudson is used for continues integration.
    </p>
    <p>
      
    </p>
    <p>
      1) Building Code after Checking
    </p>
    <p>
      2) Selenium integration tests.
    </p>
    <p>
      
    </p>
    <p>
      
    </p>
    <p>
      Currently the server builds 3 times a day
    </p>
  </body>
</html>
</richcontent>
</node>
<node TEXT="JUnit" ID="ID_1727251702" CREATED="1395954370267" MODIFIED="1395954915273"><richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      Tests reviewed do not do null testing. Also, TDD is not being followed strictly.
    </p>
  </body>
</html>
</richcontent>
</node>
<node TEXT="Ant" ID="ID_735319666" CREATED="1395954990495" MODIFIED="1395955122191"><richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      There is one build script used for all clients.
    </p>
    <p>
      
    </p>
    <p>
      This makes the build process error prone.
    </p>
    <p>
      
    </p>
    <p>
      Recommend that each client have a separate script which will allow the developer to clearly understand what build is being created.
    </p>
    <p>
      
    </p>
    <p>
      EAR files are currently named the same. This will need to be changed so that it is understood by looking at the name of the ear what client the ear is targeted for.
    </p>
  </body>
</html>
</richcontent>
</node>
<node TEXT="Identified Issues" ID="ID_1721114344" CREATED="1396015953367" MODIFIED="1396015961403">
<node TEXT="Number of ant tasks" ID="ID_1353235492" CREATED="1396015963311" MODIFIED="1396016462717"><richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      With the number of ant tasks currently in the build,there is a high likely hood that a developer will build the wrong ear and not know that it had been done.
    </p>
    <p>
      This can be alleviated by splitting the build into client builds.
    </p>
  </body>
</html>
</richcontent>
</node>
<node TEXT="CVS project missing jars" ID="ID_628113508" CREATED="1396015978676" MODIFIED="1396016036354"><richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      all the jackson jars were missing from the eclipse class path.
    </p>
  </body>
</html>
</richcontent>
</node>
<node TEXT="JAR dependency managemnet" ID="ID_1109436096" CREATED="1396016038135" MODIFIED="1396016269788"><richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      As dependent jars are upgraded, the eclipse project needs to be manually updated.
    </p>
    <p>
      
    </p>
    <p>
      To correct this, we should be using either ivy ant task or convert the build script from ant to maven project
    </p>
    <p>
      
    </p>
    <p>
      Also there has been discussions to use gradle. More research will need to be done to be able to verify the viability of this.
    </p>
  </body>
</html>
</richcontent>
</node>
</node>
</node>
<node TEXT="Architecture" POSITION="right" ID="ID_1429562792" CREATED="1395953347057" MODIFIED="1395953359260">
<node TEXT="Current" ID="ID_507679415" CREATED="1395953361096" MODIFIED="1395953366436">
<node TEXT="JSP" ID="ID_333078780" CREATED="1395953373430" MODIFIED="1395953865484"><richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      1) Generates lots of network traffic when the user needs to refresh the screen
    </p>
    <p>
      2) Poor user experience since the page will be refresh on the server side and sent back to the user
    </p>
    <p>
      3) Does not support MVC well since the developer is allowed to create &quot;java snippets&quot; within the JSP file
    </p>
  </body>
</html>
</richcontent>
</node>
<node TEXT="Hibernate" ID="ID_411494928" CREATED="1395953377056" MODIFIED="1395954476886"><richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      2 problems currently identified
    </p>
    <p>
      
    </p>
    <p>
      1) Connection string values are embedded in EAR
    </p>
    <p>
      2) Configuration is static
    </p>
  </body>
</html>
</richcontent>
</node>
<node TEXT="Axis2 web service" ID="ID_1289557811" CREATED="1395953382360" MODIFIED="1395953392611"/>
<node TEXT="Struts 1" ID="ID_1678461374" CREATED="1395953396224" MODIFIED="1395954705768"><richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      Has hit end of life and no longer supported by the struts development team
    </p>
  </body>
</html>
</richcontent>
</node>
<node TEXT="CSS2" ID="ID_1006909060" CREATED="1395953558320" MODIFIED="1395954589112"><richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      All CSS needs to be moved to static CSS files for performance reasons
    </p>
  </body>
</html>
</richcontent>
</node>
<node TEXT="JBOSS 7" ID="ID_681258301" CREATED="1395953690255" MODIFIED="1395953697522"/>
<node TEXT="MYSQL 5" ID="ID_483468006" CREATED="1395953869413" MODIFIED="1395953878274"/>
<node TEXT="Cron" ID="ID_403860706" CREATED="1395955397134" MODIFIED="1395955502460"><richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      Need a verification as to what tool is being used for the project.
    </p>
    <p>
      
    </p>
    <p>
      1) Is this a custom batch processing project
    </p>
    <p>
      2) Using Quartz and if so what version
    </p>
  </body>
</html>
</richcontent>
</node>
</node>
<node TEXT="Future" ID="ID_1200942379" CREATED="1395953368149" MODIFIED="1395955562020">
<node TEXT="HTML" ID="ID_1099336837" CREATED="1395953408632" MODIFIED="1395953627864"><richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      1) Force developers to use the MVC paradigm
    </p>
    <p>
      2) Faster displaying
    </p>
  </body>
</html>
</richcontent>
</node>
<node TEXT="JQuery" ID="ID_611754106" CREATED="1395953413551" MODIFIED="1395953676598"><richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      Allows for more client side interaction
    </p>
    <p>
      Cuts down on the amount of network traffic
    </p>
  </body>
</html>
</richcontent>
</node>
<node TEXT="Hibernate" ID="ID_1267690918" CREATED="1395953420397" MODIFIED="1395954339175"><richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      This to work on here
    </p>
    <p>
      
    </p>
    <p>
      1) Remove the properties file
    </p>
    <p>
      2) Use annotations for the data objects
    </p>
    <p>
      3) move the connection string information to a data source which can be dropped in the deploy directory.
    </p>
  </body>
</html>
</richcontent>
</node>
<node TEXT="ESB" ID="ID_1385261089" CREATED="1395953424806" MODIFIED="1395954229041"><richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      A couple of options here include
    </p>
    <p>
      
    </p>
    <p>
      1) Apache Camel
    </p>
    <p>
      2) JBOSS ESB
    </p>
  </body>
</html>
</richcontent>
</node>
<node TEXT="Struts 2" ID="ID_479852361" CREATED="1395953436216" MODIFIED="1395954189765"><richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      There should be few changes to go to this paradigm
    </p>
  </body>
</html>
</richcontent>
</node>
<node TEXT="JSON" ID="ID_13789701" CREATED="1395953526871" MODIFIED="1395954126953"><richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      data format used to communicate with the back end using AJAX
    </p>
  </body>
</html>
</richcontent>
</node>
<node TEXT="AJAX" ID="ID_670995776" CREATED="1395953535903" MODIFIED="1395954013634"><richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      1) Cut down the amount of data on the network.
    </p>
    <p>
      2) Allow for better visual communication with the user
    </p>
  </body>
</html>
</richcontent>
</node>
<node TEXT="CSS4" ID="ID_492738938" CREATED="1395953569847" MODIFIED="1395953948579"><richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      CSS 4 allows for variable substitution
    </p>
  </body>
</html>
</richcontent>
</node>
<node TEXT="JBOSS 7" ID="ID_261229896" CREATED="1395953699014" MODIFIED="1395953701994"/>
<node TEXT="MYSQL" ID="ID_290315535" CREATED="1395953880061" MODIFIED="1395953923546"><richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      Dependent on performance requirements, we may need to review different database options
    </p>
  </body>
</html>
</richcontent>
</node>
<node TEXT="CXF" ID="ID_679373907" CREATED="1395954480079" MODIFIED="1396015759938"><richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      For security purposes, we need to add the WS-SECURITY to all web service calls.
    </p>
    <p>
      
    </p>
    <p>
      See discussion between axis and cxf below:
    </p>
    <p>
      http://stackoverflow.com/questions/14933374/diffrence-between-axis2-webservice-and-cxf-web-service
    </p>
    <p>
      comparision between axis2/cxf/spring ws: http://architects.dzone.com/articles/apache-cxf-vs-apache-axis-vs
    </p>
    <p>
      
    </p>
  </body>
</html>
</richcontent>
</node>
<node TEXT="Quartz 1.8" ID="ID_487403078" CREATED="1395955374927" MODIFIED="1395955395339"><richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      Need to determine if
    </p>
  </body>
</html>
</richcontent>
</node>
<node TEXT="OPENSAML" ID="ID_787187302" CREATED="1395955562022" MODIFIED="1395955570425"/>
</node>
<node TEXT="Aspect Oriented Programming" ID="ID_1744188539" CREATED="1395955153991" MODIFIED="1395955373519"><richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      Need to be able intercept requests prior to being presented to the application so that we can do a couple of things.
    </p>
    <p>
      
    </p>
    <p>
      1) modify the request parameters to protected against Cross Site Scripting and SQL Injection vulnerabilities.
    </p>
    <p>
      2) put in place timer mechanisms which can be used to monitor at a page level any issues that may be coming up.
    </p>
  </body>
</html>
</richcontent>
</node>
</node>
<node TEXT="Architectural Requirements" POSITION="left" ID="ID_220194551" CREATED="1396014762819" MODIFIED="1396014777761">
<node TEXT="Performance" ID="ID_1484093825" CREATED="1396014780701" MODIFIED="1396014787513">
<node TEXT="Concurrency" ID="ID_1735708943" CREATED="1396016488996" MODIFIED="1396016500817"/>
<node TEXT="Response time" ID="ID_1097672041" CREATED="1396016512125" MODIFIED="1396016525016">
<node TEXT="Back end services" ID="ID_1438248598" CREATED="1396016526475" MODIFIED="1396016540040"/>
<node TEXT="UI" ID="ID_970036730" CREATED="1396016542242" MODIFIED="1396016546375"/>
</node>
<node TEXT="Volumn" ID="ID_1194933641" CREATED="1396016555652" MODIFIED="1396016563727">
<node TEXT="Transaction size" ID="ID_308951941" CREATED="1396016581517" MODIFIED="1396016587967"/>
</node>
</node>
<node TEXT="Open Standards" ID="ID_1031835089" CREATED="1396014789349" MODIFIED="1396014831921"/>
<node TEXT="Buy Before Build" ID="ID_1450529400" CREATED="1396014834693" MODIFIED="1396014845601"/>
<node TEXT="MVC" ID="ID_847770320" CREATED="1396017347472" MODIFIED="1396017351181"/>
<node TEXT="Customization" ID="ID_1227786296" CREATED="1396017385176" MODIFIED="1396017391516"/>
<node TEXT="Deployment Scalability" ID="ID_1268220043" CREATED="1396027837061" MODIFIED="1396027850481"/>
</node>
</node>
</map>
