SET jboss_drive=%1

call ant -f build.xml -propertyfile escrow_usair.properties build-central-ustest
cd ..\staging
copy dev\us-central\NTServices_1_0.ear %jboss_drive%\jboss-5.1.0.GA-ntservice\server\ntservices\deploy\NTServices_1_0.ear
