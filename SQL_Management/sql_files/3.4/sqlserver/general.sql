alter table fsclaim add claimRemark text;
alter table address add hotelphone varchar(50);

update properties set valueStr='NTServices_1_0/ClaimClientBeanV2/remote' where keyStr='fraud.server.name';

create table FsIPAddress (
	id bigint not null identity,
	ipAddress varchar(20),
	claim_id bigint not null,
	primary key (id));


alter table phone add association varchar(255);
alter table phone add claim_id integer;
alter table fsipaddress add association varchar(255);

GO
CREATE NONCLUSTERED INDEX [claim_id_index]
ON Phone
([claim_id])
WITH
(
PAD_INDEX = OFF,
FILLFACTOR = 100,
IGNORE_DUP_KEY = OFF,
STATISTICS_NORECOMPUTE = OFF,
ONLINE = OFF,
ALLOW_ROW_LOCKS = ON,
ALLOW_PAGE_LOCKS = ON
)
ON [PRIMARY];
GO

CREATE NONCLUSTERED INDEX [claim_id_index]
ON FsIPAddress
([claim_id])
WITH
(
PAD_INDEX = OFF,
FILLFACTOR = 100,
IGNORE_DUP_KEY = OFF,
STATISTICS_NORECOMPUTE = OFF,
ONLINE = OFF,
ALLOW_ROW_LOCKS = ON,
ALLOW_PAGE_LOCKS = ON
)
ON [PRIMARY];
GO
