USE [NetTracer];
GO

ALTER TABLE [dbo].[actionfile_station_counts]
DROP CONSTRAINT [PK__actionfile_stati__384F51F2];
GO

ALTER TABLE [dbo].[actionfile_station_counts] alter column af_seq varchar(3) not null;

ALTER TABLE [dbo].[actionfile_station_counts]
ADD CONSTRAINT [PK__actionfile_stati__384F51F2] PRIMARY KEY (af_station_id, af_type, af_seq);
GO