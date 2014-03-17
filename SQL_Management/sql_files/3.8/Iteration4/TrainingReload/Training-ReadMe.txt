TRAINING UPDATING DATA

If an update for the Training database is required, such as setting a specific permission. These are the following steps in order to permanent update the database.

1) Take the ntwn_training_reload.sql file from either the DRAPP1 Server (located at path: D:\deployscripts\swaTrainReload) and transfer it to local box or from the rar in this directory (SQL_Management/sql_files/3.8/Iteration4/TrainingReload/ntwn_train_reload.rar)
2) Upload the sql file into a fresh Database and update hibernate properties to point to that database.
3) Build, Deploy, Log in and make the necessary updates. Make sure that Crons aren't running as to not interfere with any of the data.
4) Once the updates are finished. Take a snapshot of that database, rar it up and commit it to the reposity. Then upload the snapshot to the DRAPP1 Server at path: D:\deployscripts\swaTrainReload. Be sure to make a back up of the snapshot currently located there just in case.