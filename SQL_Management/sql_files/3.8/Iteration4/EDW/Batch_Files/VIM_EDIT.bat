CD D:\EDW
vim -c "args *.csv" -c "argdo e ++ff=unix | %%s/\r$//g | update" -c q!

