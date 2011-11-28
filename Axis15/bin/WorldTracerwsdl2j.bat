call wsdl2Java -uri DelayedBagService.wsdl -ss -sd -g -d xmlbeans -o service
call wsdl2Java -uri OnhandBagService.wsdl -ss -sd -g -d xmlbeans -o service
call wsdl2Java -uri RushBagService.wsdl -ss -sd -g -d xmlbeans -o service
call wsdl2Java -uri InboxService.wsdl -ss -sd -g -d xmlbeans -o service
cd C:\workspace\Axis15\bin\service
call c:\ant\bin\ant.bat
cd build
cd lib
rename DelayedBagService-test-client.jar WorldTracerServiceClient.jar
