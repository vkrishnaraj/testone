:: BELOW ACTS LIKE A WAIT COMMAND 
ping 192.0.2.2 -n 1 -w 10000 > nul

DEL /F /Q /S D:\EDW\*.csv~
MKDIR D:\EDW\training
MOVE D:\EDW\*.csv D:\EDW\training
echo y | psftp -i C:\PSFTP\nt2swaEDW.ppk -b D:\EDW\Batch_Files\PSFTP_TRAINING.bat -bc -be -v ntrcbags@ftp.wnco.com 1>C:\PSFTP\debugTraining.txt 2>C:\PSFTP\errorsTraining.txt

