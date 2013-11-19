USE [NetTracer];
GO

--since reverting the constraint back, we need to delete the data in these tables to prevent a key constraint violation if multiple af_station_id/af_type pairs exists 
delete from [dbo].[actionfile_station_counts];
delete from [dbo].[actionfilestation];

ALTER TABLE [dbo].[actionfile_station_counts]
DROP CONSTRAINT [PK__actionfile_stati__384F51F2];
GO

ALTER TABLE [dbo].[actionfile_station_counts]
ADD CONSTRAINT [PK__actionfile_stati__384F51F2] PRIMARY KEY (af_station_id, af_type);
GO