ALTER TABLE expensepayout
  ADD INDEX (expensetype_ID, station_ID);

ALTER TABLE expensepayout
  ADD INDEX (bdo_id);

ALTER TABLE item
  ADD INDEX (lossCode);
  
ALTER TABLE category
  ADD INDEX (categoryVal);

ALTER TABLE incident
  ADD INDEX (checkedlocation);
  
ALTER TABLE incident
  ADD INDEX (agentassigned_ID);

ALTER TABLE audit_issuance_item_inventory
  ADD INDEX (station_id, inventory_status_id);

 ALTER TABLE bdo
  ADD INDEX (delivercompany_ID);

ALTER TABLE audit_item
  ADD INDEX (faultStation_id);
  
ALTER TABLE audit_issuance_item_inventory
  ADD INDEX (station_id, inventory_status_id, editagent_id);
