update lost_found set report_status_id=41, close_date=now(), closing_agent_id=1908 where 
create_date < '2012-11-01 00:00:00' and isnull(close_date);