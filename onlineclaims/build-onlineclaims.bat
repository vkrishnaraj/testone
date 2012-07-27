SET jboss_drive=%1

IF EXIST pom-initial-run.xml GOTO PACKAGE
call mvn install:install-file -DgroupId=OnlineClaimsService-client -DartifactId=OnlineClaimsService-client -Dversion=1.0 -Dpackaging=jar -Dfile=OnlineClaimsService-client.jar
call mvn install
REN pom.xml pom-initial-run.xml
REN usair-escrow-pom2.xml pom.xml
:PACKAGE
call mvn package
copy target\onlineclaims.war %jboss_drive%\jboss-4.2.3.GA-onlineclaims\server\default\deploy\onlineclaims.war