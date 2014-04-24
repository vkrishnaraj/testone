cd D:\EDW\Batch_Files
DEL /F /Q /S D:\EDW\testing1\*.csv
DEL /F /Q /S D:\EDW\*.csv
RMDIR D:\EDW\testing1
mysql -u root -pnettracer -N ntwn_testing < D:\EDW\Queries\assisted_device_code.sql > D:\EDW\assisted_device_code_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u root -pnettracer -N ntwn_testing < D:\EDW\Queries\bag_deliver_info.sql > D:\EDW\bag_deliver_info_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u root -pnettracer -N ntwn_testing < D:\EDW\Queries\bag_description.sql > D:\EDW\bag_description_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u root -pnettracer -N ntwn_testing < D:\EDW\Queries\bag_report.sql > D:\EDW\bag_report_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u root -pnettracer -N ntwn_testing < D:\EDW\Queries\bag_type_code.sql > D:\EDW\bag_type_code_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u root -pnettracer -N ntwn_testing < D:\EDW\Queries\bagdrop.sql > D:\EDW\bagdrop_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u root -pnettracer -N ntwn_testing < D:\EDW\Queries\baggage_flight.sql > D:\EDW\baggage_flight_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u root -pnettracer -N ntwn_testing < D:\EDW\Queries\baggage_return_info.sql > D:\EDW\baggage_return_info_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u root -pnettracer -N ntwn_testing < D:\EDW\Queries\chargeback_code.sql > D:\EDW\chargeback_code_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u root -pnettracer -N ntwn_testing < D:\EDW\Queries\check_location_code.sql > D:\EDW\check_location_code_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u root -pnettracer -N ntwn_testing < D:\EDW\Queries\damage_bag.sql > D:\EDW\damage_bag_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u root -pnettracer -N ntwn_testing < D:\EDW\Queries\damage_type.sql > D:\EDW\damage_type_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u root -pnettracer -N ntwn_testing < D:\EDW\Queries\delivery_service_type_code.sql > D:\EDW\delivery_service_type_code_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u root -pnettracer -N ntwn_testing < D:\EDW\Queries\expense_type_code.sql > D:\EDW\expense_type_code_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u root -pnettracer -N ntwn_testing < D:\EDW\Queries\how_reported.sql > D:\EDW\how_reported_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u root -pnettracer -N ntwn_testing < D:\EDW\Queries\iata_description_code.sql > D:\EDW\iata_description_code_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u root -pnettracer -N ntwn_testing < D:\EDW\Queries\missing_article.sql > D:\EDW\missing_article_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u root -pnettracer -N ntwn_testing < D:\EDW\Queries\missing_article_status_code.sql > D:\EDW\missing_article_status_code_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u root -pnettracer -N ntwn_testing < D:\EDW\Queries\payment_code.sql > D:\EDW\payment_code_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u root -pnettracer -N ntwn_testing < D:\EDW\Queries\report_expense.sql > D:\EDW\report_expense_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u root -pnettracer -N ntwn_testing < D:\EDW\Queries\report_type_code.sql > D:\EDW\report_type_code_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv
mysql -u root -pnettracer -N ntwn_testing < D:\EDW\Queries\resolution_code.sql > D:\EDW\resolution_code_%date:~-4,4%-%date:~-10,2%-%date:~-7,2%.csv

