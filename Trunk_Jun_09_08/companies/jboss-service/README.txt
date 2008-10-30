Prereq - Make sure the system has JAVA_HOME and JBOSS_HOME environment variables.  If you can't set that on the system or need to use different stuff, then you will need to replace those values in the config with hard coded directories.

1. Place the wrapper specific files into the jboss install
   copy wrapper.exe %JBOSS_HOME%\bin\Wrapper.exe
   copy wrapper.dll %JBOSS_HOME%\lib\Wrapper.dll
   copy wrapper.jar %JBOSS_HOME%\lib\wrapper.jar

2. Set up config folder for each instance that needs to be run as a service
   mkdir %JBOSS_HOME%\server\${INTANCE_NAME}\wrapper

3. Create wrapper.conf file inside %JBOSS_HOME%\server\${INSTANCE_NAME}\wrapper. Use the wrapper.conf.template file in this package.

4. Test the service:

cd %JBOSS_HOME%\bin\
wrapper.exe -c %JBOSS_HOME%\server\${INSTANCE_NAME}\wrapper\wrapper.conf

Install the service:

cd %JBOSS_HOME%\bin\
wrapper.exe -i %JBOSS_HOME%\server\${INSTANCE_NAME}\wrapper\wrapper.conf

Uninstall the service:

cd %JBOSS_HOME%\bin\
wrapper.exe -r %JBOSS_HOME%\server\${INSTANCE_NAME}\wrapper\wrapper.conf

