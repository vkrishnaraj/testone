SET jboss_drive=%1

call ant -f build-usair.xml -propertyfile escrow_usair.properties build-dev
cd ..\staging
copy dev\usair\tracer.ear %jboss_drive%\jboss-5.1.0.GA-ntapp\server\default\deploy\tracer.ear
copy dev\dev-tracer-cron.jar %jboss_drive%\nettracer-cron-service\lib\dev-tracer-cron.jar
