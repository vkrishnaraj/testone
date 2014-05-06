set START_OFFSET=date_add(now(), INTERVAL -1 DAY)
set END_OFFSET=now()

cd D:\EDW\Batch_Files
DEL /F /Q /S D:\EDW\production\*.csv
DEL /F /Q /S D:\EDW\*.csv
RMDIR D:\EDW\production
mysql -u wn_user -ps3h*PwT!5u -h reporting.nettracer.aero -N -e "SET @start:=%START_OFFSET%; SET @end:=%END_OFFSET%; source D:/EDW/Queries/assisted_device_code.sql;" wn_production > D:\EDW\assisted_device_code_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u wn_user -ps3h*PwT!5u -h reporting.nettracer.aero -N -e "SET @start:=%START_OFFSET%; SET @end:=%END_OFFSET%; source D:/EDW/Queries/bag_deliver_info.sql;" wn_production > D:\EDW\bag_deliver_info_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u wn_user -ps3h*PwT!5u -h reporting.nettracer.aero -N -e "SET @start:=%START_OFFSET%; SET @end:=%END_OFFSET%; source D:/EDW/Queries/bag_description.sql;" wn_production > D:\EDW\bag_description_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u wn_user -ps3h*PwT!5u -h reporting.nettracer.aero -N -e "SET @start:=%START_OFFSET%; SET @end:=%END_OFFSET%; source D:/EDW/Queries/bag_report.sql;" wn_production > D:\EDW\bag_report_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u wn_user -ps3h*PwT!5u -h reporting.nettracer.aero -N -e "SET @start:=%START_OFFSET%; SET @end:=%END_OFFSET%; source D:/EDW/Queries/bag_type_code.sql;" wn_production > D:\EDW\bag_type_code_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u wn_user -ps3h*PwT!5u -h reporting.nettracer.aero -N -e "SET @start:=%START_OFFSET%; SET @end:=%END_OFFSET%; source D:/EDW/Queries/bagdrop.sql;" wn_production > D:\EDW\bagdrop_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u wn_user -ps3h*PwT!5u -h reporting.nettracer.aero -N -e "SET @start:=%START_OFFSET%; SET @end:=%END_OFFSET%; source D:/EDW/Queries/baggage_flight.sql;" wn_production > D:\EDW\baggage_flight_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u wn_user -ps3h*PwT!5u -h reporting.nettracer.aero -N -e "SET @start:=%START_OFFSET%; SET @end:=%END_OFFSET%; source D:/EDW/Queries/baggage_return_info.sql;" wn_production > D:\EDW\baggage_return_info_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u wn_user -ps3h*PwT!5u -h reporting.nettracer.aero -N -e "SET @start:=%START_OFFSET%; SET @end:=%END_OFFSET%; source D:/EDW/Queries/chargeback_code.sql;" wn_production > D:\EDW\chargeback_code_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u wn_user -ps3h*PwT!5u -h reporting.nettracer.aero -N -e "SET @start:=%START_OFFSET%; SET @end:=%END_OFFSET%; source D:/EDW/Queries/check_location_code.sql;" wn_production > D:\EDW\check_location_code_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u wn_user -ps3h*PwT!5u -h reporting.nettracer.aero -N -e "SET @start:=%START_OFFSET%; SET @end:=%END_OFFSET%; source D:/EDW/Queries/damage_bag.sql;" wn_production > D:\EDW\damage_bag_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u wn_user -ps3h*PwT!5u -h reporting.nettracer.aero -N -e "SET @start:=%START_OFFSET%; SET @end:=%END_OFFSET%; source D:/EDW/Queries/damage_type.sql;" wn_production > D:\EDW\damage_type_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u wn_user -ps3h*PwT!5u -h reporting.nettracer.aero -N -e "SET @start:=%START_OFFSET%; SET @end:=%END_OFFSET%; source D:/EDW/Queries/delivery_service_type_code.sql;" wn_production > D:\EDW\delivery_service_type_code_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u wn_user -ps3h*PwT!5u -h reporting.nettracer.aero -N -e "SET @start:=%START_OFFSET%; SET @end:=%END_OFFSET%; source D:/EDW/Queries/expense_type_code.sql;" wn_production > D:\EDW\expense_type_code_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u wn_user -ps3h*PwT!5u -h reporting.nettracer.aero -N -e "SET @start:=%START_OFFSET%; SET @end:=%END_OFFSET%; source D:/EDW/Queries/how_reported.sql;" wn_production > D:\EDW\how_reported_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u wn_user -ps3h*PwT!5u -h reporting.nettracer.aero -N -e "SET @start:=%START_OFFSET%; SET @end:=%END_OFFSET%; source D:/EDW/Queries/iata_description_code.sql;" wn_production > D:\EDW\iata_description_code_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u wn_user -ps3h*PwT!5u -h reporting.nettracer.aero -N -e "SET @start:=%START_OFFSET%; SET @end:=%END_OFFSET%; source D:/EDW/Queries/missing_article.sql;" wn_production > D:\EDW\missing_article_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u wn_user -ps3h*PwT!5u -h reporting.nettracer.aero -N -e "SET @start:=%START_OFFSET%; SET @end:=%END_OFFSET%; source D:/EDW/Queries/missing_article_status_code.sql;" wn_production > D:\EDW\missing_article_status_code_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u wn_user -ps3h*PwT!5u -h reporting.nettracer.aero -N -e "SET @start:=%START_OFFSET%; SET @end:=%END_OFFSET%; source D:/EDW/Queries/payment_code.sql;" wn_production > D:\EDW\payment_code_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u wn_user -ps3h*PwT!5u -h reporting.nettracer.aero -N -e "SET @start:=%START_OFFSET%; SET @end:=%END_OFFSET%; source D:/EDW/Queries/report_expense.sql;" wn_production > D:\EDW\report_expense_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u wn_user -ps3h*PwT!5u -h reporting.nettracer.aero -N -e "SET @start:=%START_OFFSET%; SET @end:=%END_OFFSET%; source D:/EDW/Queries/report_type_code.sql;" wn_production > D:\EDW\report_type_code_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u wn_user -ps3h*PwT!5u -h reporting.nettracer.aero -N -e "SET @start:=%START_OFFSET%; SET @end:=%END_OFFSET%; source D:/EDW/Queries/resolution_code.sql;" wn_production > D:\EDW\resolution_code_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv

