cd D:\EDW\Batch_Files
DEL /F /Q /S D:\EDW\local\*.csv
DEL /F /Q /S D:\EDW\*.csv
RMDIR D:\EDW\local
mysql -u root -pnettracer ntwn_testing < D:\EDW\Queries\resolution_code.sql
mysql -u root -pnettracer ntwn_testing < D:\EDW\Queries\assisted_device_code.sql
mysql -u root -pnettracer ntwn_testing < D:\EDW\Queries\bag_deliver_info.sql
mysql -u root -pnettracer ntwn_testing < D:\EDW\Queries\bag_description.sql
mysql -u root -pnettracer ntwn_testing < D:\EDW\Queries\bag_report.sql
mysql -u root -pnettracer ntwn_testing < D:\EDW\Queries\bag_type_code.sql
mysql -u root -pnettracer ntwn_testing < D:\EDW\Queries\bagdrop.sql
mysql -u root -pnettracer ntwn_testing < D:\EDW\Queries\baggage_flight.sql
mysql -u root -pnettracer ntwn_testing < D:\EDW\Queries\baggage_return_info.sql
mysql -u root -pnettracer ntwn_testing < D:\EDW\Queries\chargeback_code.sql
mysql -u root -pnettracer ntwn_testing < D:\EDW\Queries\check_location_code.sql
mysql -u root -pnettracer ntwn_testing < D:\EDW\Queries\damage_bag.sql
mysql -u root -pnettracer ntwn_testing < D:\EDW\Queries\damage_type.sql
mysql -u root -pnettracer ntwn_testing < D:\EDW\Queries\delivery_service_type_code.sql
mysql -u root -pnettracer ntwn_testing < D:\EDW\Queries\expense_type_code.sql
mysql -u root -pnettracer ntwn_testing < D:\EDW\Queries\how_reported.sql
mysql -u root -pnettracer ntwn_testing < D:\EDW\Queries\iata_description_code.sql
mysql -u root -pnettracer ntwn_testing < D:\EDW\Queries\missing_article.sql
mysql -u root -pnettracer ntwn_testing < D:\EDW\Queries\missing_article_status_code.sql
mysql -u root -pnettracer ntwn_testing < D:\EDW\Queries\payment_code.sql
mysql -u root -pnettracer ntwn_testing < D:\EDW\Queries\report_expense.sql
mysql -u root -pnettracer ntwn_testing < D:\EDW\Queries\report_type_code.sql
MKDIR D:\EDW\local
MOVE D:\EDW\*.csv D:\EDW\local
echo y | psftp -i C:\PSFTP\nt2swaEDW.ppk -b D:\EDW\Batch_Files\PSFTP_LOCAL.bat -be ntrcbags@ftp.wnco.com 1>C:\PSFTP\debug.txt 2>C:\PSFTP\errors.txt

