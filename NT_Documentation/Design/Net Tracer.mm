<map version="1.0.1">
<!-- To view this file, download free mind mapping software FreeMind from http://freemind.sourceforge.net -->
<node CREATED="1395953148856" ID="ID_534664009" MODIFIED="1395953174509" TEXT="Net Tracer">
<hook NAME="MapStyle">
<properties SHOW_NOTE_ICONS="true"/>
<map_styles>
<stylenode LOCALIZED_TEXT="styles.root_node">
<stylenode LOCALIZED_TEXT="styles.predefined" POSITION="right">
<stylenode COLOR="#000000" LOCALIZED_TEXT="default" MAX_WIDTH="600" STYLE="as_parent">
<font/>
</stylenode>
<stylenode LOCALIZED_TEXT="defaultstyle.details"/>
<stylenode LOCALIZED_TEXT="defaultstyle.note"/>
<stylenode LOCALIZED_TEXT="defaultstyle.floating">
<edge/>
<cloud/>
</stylenode>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.user-defined" POSITION="right">
<stylenode COLOR="#18898b" LOCALIZED_TEXT="styles.topic" STYLE="fork">
<font/>
</stylenode>
<stylenode COLOR="#cc3300" LOCALIZED_TEXT="styles.subtopic" STYLE="fork">
<font/>
</stylenode>
<stylenode COLOR="#669900" LOCALIZED_TEXT="styles.subsubtopic">
<font/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.important">
<icon/>
</stylenode>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.AutomaticLayout" POSITION="right">
<stylenode COLOR="#000000" LOCALIZED_TEXT="AutomaticLayout.level.root">
<font/>
</stylenode>
<stylenode COLOR="#0033ff" LOCALIZED_TEXT="AutomaticLayout.level,1">
<font/>
</stylenode>
<stylenode COLOR="#00b439" LOCALIZED_TEXT="AutomaticLayout.level,2">
<font/>
</stylenode>
<stylenode COLOR="#990000" LOCALIZED_TEXT="AutomaticLayout.level,3">
<font/>
</stylenode>
<stylenode COLOR="#111111" LOCALIZED_TEXT="AutomaticLayout.level,4">
<font/>
</stylenode>
</stylenode>
</stylenode>
</map_styles>
</hook>
<node CREATED="1395953177913" ID="ID_518362160" MODIFIED="1396270491495" POSITION="right" TEXT="Performance">
<node CREATED="1395953305345" ID="ID_1022828901" MODIFIED="1395953310197" TEXT="Concurrency"/>
<node CREATED="1395953312136" ID="ID_1387673948" MODIFIED="1395953322780" TEXT="Response Time per request"/>
<node CREATED="1395953325321" ID="ID_989379258" MODIFIED="1395954732389" TEXT="Data size per request"/>
</node>
<node CREATED="1395953188425" ID="ID_736632079" MODIFIED="1395953206469" POSITION="left" TEXT="Security">
<node CREATED="1395953276697" ID="ID_483258380" MODIFIED="1395955667070" TEXT="Authentication">
<richcontent TYPE="NOTE"><html>
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
</html></richcontent>
</node>
<node CREATED="1395953284521" ID="ID_1885897148" MODIFIED="1396040941228" TEXT="Authorization">
<richcontent TYPE="NOTE"><html>
  <head>
    
  </head>
  <body>
    <p>
      permissions table holds all the permissions that user needs to access any section of the system
    </p>
    <p>
      
    </p>
    <p>
      Agents associated with group and group contains permissions
    </p>
  </body>
</html></richcontent>
</node>
<node CREATED="1395953292145" ID="ID_212076688" MODIFIED="1396273251674" TEXT="Accountablility">
<richcontent TYPE="NOTE"><html>
  <head>
    
  </head>
  <body>
    <p>
      NEED more investigation
    </p>
    <p>
      
    </p>
    <p>
      Agent auditing with many audit table
    </p>
    <p>
      
    </p>
    <p>
      Audit tables are large causing migrations to take a long time
    </p>
  </body>
</html>
</richcontent>
<icon BUILTIN="flag"/>
</node>
</node>
<node CREATED="1395953209497" ID="ID_1270863797" MODIFIED="1396270492916" POSITION="right" TEXT="Development">
<node CREATED="1395954347643" ID="ID_175907763" MODIFIED="1395954988896" TEXT="CVS">
<richcontent TYPE="NOTE"><html>
  <head>
    
  </head>
  <body>
    <p>
      All clients are currently under one project making it difficult to identify customizations between clients.
    </p>
  </body>
</html></richcontent>
</node>
<node CREATED="1395954359691" ID="ID_1160052764" MODIFIED="1395954364127" TEXT="Eclipse"/>
<node CREATED="1395954365619" ID="ID_1341489170" MODIFIED="1395954861280" TEXT="Hudson">
<richcontent TYPE="NOTE"><html>
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
</html></richcontent>
</node>
<node CREATED="1395954370267" ID="ID_1727251702" MODIFIED="1395954915273" TEXT="JUnit">
<richcontent TYPE="NOTE"><html>
  <head>
    
  </head>
  <body>
    <p>
      Tests reviewed do not do null testing. Also, TDD is not being followed strictly.
    </p>
  </body>
</html></richcontent>
</node>
<node CREATED="1395954990495" ID="ID_735319666" MODIFIED="1395955122191" TEXT="Ant">
<richcontent TYPE="NOTE"><html>
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
</html></richcontent>
</node>
<node CREATED="1396015953367" ID="ID_1721114344" MODIFIED="1396015961403" TEXT="Identified Issues">
<node CREATED="1396015963311" ID="ID_1353235492" MODIFIED="1396016462717" TEXT="Number of ant tasks">
<richcontent TYPE="NOTE"><html>
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
</html></richcontent>
</node>
<node CREATED="1396015978676" ID="ID_628113508" MODIFIED="1396016036354" TEXT="CVS project missing jars">
<richcontent TYPE="NOTE"><html>
  <head>
    
  </head>
  <body>
    <p>
      all the jackson jars were missing from the eclipse class path.
    </p>
  </body>
</html></richcontent>
</node>
<node CREATED="1396016038135" ID="ID_1109436096" MODIFIED="1396016269788" TEXT="JAR dependency managemnet">
<richcontent TYPE="NOTE"><html>
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
</html></richcontent>
</node>
</node>
</node>
<node CREATED="1395953347057" ID="ID_1429562792" MODIFIED="1395953359260" POSITION="right" TEXT="Architecture">
<node CREATED="1395953361096" ID="ID_507679415" MODIFIED="1395953366436" TEXT="Current">
<node CREATED="1395953373430" ID="ID_333078780" MODIFIED="1395953865484" TEXT="JSP">
<richcontent TYPE="NOTE"><html>
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
      3) Does not support MVC well since the developer is allowed to create "java snippets" within the JSP file
    </p>
  </body>
</html></richcontent>
</node>
<node CREATED="1395953377056" ID="ID_411494928" MODIFIED="1395954476886" TEXT="Hibernate">
<richcontent TYPE="NOTE"><html>
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
</html></richcontent>
</node>
<node CREATED="1395953382360" ID="ID_1289557811" MODIFIED="1395953392611" TEXT="Axis2 web service"/>
<node CREATED="1395953396224" ID="ID_1678461374" MODIFIED="1395954705768" TEXT="Struts 1">
<richcontent TYPE="NOTE"><html>
  <head>
    
  </head>
  <body>
    <p>
      Has hit end of life and no longer supported by the struts development team
    </p>
  </body>
</html></richcontent>
</node>
<node CREATED="1395953558320" ID="ID_1006909060" MODIFIED="1395954589112" TEXT="CSS2">
<richcontent TYPE="NOTE"><html>
  <head>
    
  </head>
  <body>
    <p>
      All CSS needs to be moved to static CSS files for performance reasons
    </p>
  </body>
</html></richcontent>
</node>
<node CREATED="1395953690255" ID="ID_681258301" MODIFIED="1395953697522" TEXT="JBOSS 7"/>
<node CREATED="1395953869413" ID="ID_483468006" MODIFIED="1395953878274" TEXT="MYSQL 5"/>
<node CREATED="1395955397134" ID="ID_403860706" MODIFIED="1395955502460" TEXT="Cron">
<richcontent TYPE="NOTE"><html>
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
</html></richcontent>
</node>
</node>
<node CREATED="1395953368149" ID="ID_1200942379" MODIFIED="1395955562020" TEXT="Future">
<node CREATED="1395953408632" ID="ID_1099336837" MODIFIED="1395953627864" TEXT="HTML">
<richcontent TYPE="NOTE"><html>
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
</html></richcontent>
</node>
<node CREATED="1395953413551" ID="ID_611754106" MODIFIED="1395953676598" TEXT="JQuery">
<richcontent TYPE="NOTE"><html>
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
</html></richcontent>
</node>
<node CREATED="1395953420397" ID="ID_1267690918" MODIFIED="1395954339175" TEXT="Hibernate">
<richcontent TYPE="NOTE"><html>
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
</html></richcontent>
</node>
<node CREATED="1395953424806" ID="ID_1385261089" MODIFIED="1395954229041" TEXT="ESB">
<richcontent TYPE="NOTE"><html>
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
</html></richcontent>
</node>
<node CREATED="1395953436216" ID="ID_479852361" MODIFIED="1395954189765" TEXT="Struts 2">
<richcontent TYPE="NOTE"><html>
  <head>
    
  </head>
  <body>
    <p>
      There should be few changes to go to this paradigm
    </p>
  </body>
</html></richcontent>
</node>
<node CREATED="1395953526871" ID="ID_13789701" MODIFIED="1395954126953" TEXT="JSON">
<richcontent TYPE="NOTE"><html>
  <head>
    
  </head>
  <body>
    <p>
      data format used to communicate with the back end using AJAX
    </p>
  </body>
</html></richcontent>
</node>
<node CREATED="1395953535903" ID="ID_670995776" MODIFIED="1395954013634" TEXT="AJAX">
<richcontent TYPE="NOTE"><html>
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
</html></richcontent>
</node>
<node CREATED="1395953569847" ID="ID_492738938" MODIFIED="1395953948579" TEXT="CSS4">
<richcontent TYPE="NOTE"><html>
  <head>
    
  </head>
  <body>
    <p>
      CSS 4 allows for variable substitution
    </p>
  </body>
</html></richcontent>
</node>
<node CREATED="1395953699014" ID="ID_261229896" MODIFIED="1395953701994" TEXT="JBOSS 7"/>
<node CREATED="1395953880061" ID="ID_290315535" MODIFIED="1395953923546" TEXT="MYSQL">
<richcontent TYPE="NOTE"><html>
  <head>
    
  </head>
  <body>
    <p>
      Dependent on performance requirements, we may need to review different database options
    </p>
  </body>
</html></richcontent>
</node>
<node CREATED="1395954480079" ID="ID_679373907" MODIFIED="1396015759938" TEXT="CXF">
<richcontent TYPE="NOTE"><html>
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
</html></richcontent>
</node>
<node CREATED="1395955374927" ID="ID_487403078" MODIFIED="1395955395339" TEXT="Quartz 1.8">
<richcontent TYPE="NOTE"><html>
  <head>
    
  </head>
  <body>
    <p>
      Need to determine if
    </p>
  </body>
</html></richcontent>
</node>
<node CREATED="1395955562022" ID="ID_787187302" MODIFIED="1395955570425" TEXT="OPENSAML"/>
<node CREATED="1396040328332" ID="ID_307130748" MODIFIED="1396040334380" TEXT="EHCACHE"/>
</node>
<node CREATED="1395955153991" ID="ID_1744188539" MODIFIED="1396040613515" TEXT="Aspect Oriented Programming">
<richcontent TYPE="NOTE"><html>
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
    <p>
      3) Managing caching
    </p>
  </body>
</html></richcontent>
</node>
</node>
<node CREATED="1396014762819" ID="ID_220194551" MODIFIED="1396014777761" POSITION="left" TEXT="Architectural Requirements">
<node CREATED="1396014780701" ID="ID_1484093825" MODIFIED="1396014787513" TEXT="Performance">
<node CREATED="1396016488996" ID="ID_1735708943" MODIFIED="1396016500817" TEXT="Concurrency"/>
<node CREATED="1396016512125" ID="ID_1097672041" MODIFIED="1396016525016" TEXT="Response time">
<node CREATED="1396016526475" ID="ID_1438248598" MODIFIED="1396016540040" TEXT="Back end services"/>
<node CREATED="1396016542242" ID="ID_970036730" MODIFIED="1396016546375" TEXT="UI"/>
</node>
<node CREATED="1396016555652" ID="ID_1194933641" MODIFIED="1396016563727" TEXT="Volumn">
<node CREATED="1396016581517" ID="ID_308951941" MODIFIED="1396016587967" TEXT="Transaction size"/>
</node>
</node>
<node CREATED="1396014789349" ID="ID_1031835089" MODIFIED="1396014831921" TEXT="Open Standards"/>
<node CREATED="1396014834693" ID="ID_1450529400" MODIFIED="1396014845601" TEXT="Buy Before Build"/>
<node CREATED="1396017347472" ID="ID_847770320" MODIFIED="1396017351181" TEXT="MVC"/>
<node CREATED="1396017385176" ID="ID_1227786296" MODIFIED="1396017391516" TEXT="Customization"/>
<node CREATED="1396027837061" ID="ID_1268220043" MODIFIED="1396027850481" TEXT="Deployment Scalability"/>
</node>
</node>
</map>
