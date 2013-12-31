/**
 * 
 */
package com.bagnet.nettracer.tracing.bmo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.exception.StaleStateException;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.documents.templates.Template;
import com.bagnet.nettracer.tracing.db.issuance.AuditIssuanceItemInventory;
import com.bagnet.nettracer.tracing.db.issuance.AuditIssuanceItemQuantity;
import com.bagnet.nettracer.tracing.db.issuance.IssuanceCategory;
import com.bagnet.nettracer.tracing.db.issuance.IssuanceItem;
import com.bagnet.nettracer.tracing.db.issuance.IssuanceItemIncident;
import com.bagnet.nettracer.tracing.db.issuance.IssuanceItemInventory;
import com.bagnet.nettracer.tracing.db.issuance.IssuanceItemQuantity;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

/**
 * @author Tyrillius
 *
 */
public class IssuanceItemBMO {

	private static Logger logger = Logger.getLogger(IssuanceItemBMO.class);
	
	/**
	 * Get an IssuanceItemQuantity by id. 
	 * @param id
	 * @return
	 */
	public static IssuanceItemQuantity getQuantifiedItem(String id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			IssuanceItemQuantity qItem = (IssuanceItemQuantity) sess.load(IssuanceItemQuantity.class, Long.parseLong(id));
			return  qItem;
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}
	
	/**
	 * Get quantified items by station. Do not return any inactive items with 0 quantity.
	 * @param station
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<IssuanceItemQuantity> getQuantifiedItems(Station station) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Query query = sess.createQuery("from IssuanceItemQuantity q where q.station.stationcode = :code "
					+ "and not (q.quantity = 0 and (q.issuanceItem.active = 0 or q.issuanceItem.category.active = 0)) "
					+ "order by q.issuanceItem.category.description, q.issuanceItem.description");
			query.setParameter("code", station.getStationcode());
			return  query.list();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}
	
	/**
	 * Get an IssuanceItemInventory by id. 
	 * @param id
	 * @return
	 */
	public static IssuanceItemInventory getInventoriedItem(String id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			IssuanceItemInventory invItem = (IssuanceItemInventory) sess.load(IssuanceItemInventory.class, Long.parseLong(id));
			return  invItem;
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}
	
	/**
	 * Get inventoried items by station and status. Do not return any items that were traded or discarded 7 days ago.
	 * @param station
	 * @param status
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<IssuanceItemInventory> getInventoriedItems(Station station, int status) {
		String queryString = "from IssuanceItemInventory i where i.station.stationcode = :code";
		boolean hasStatus = (status > 0);
		boolean goneStatus = (status == TracingConstants.ISSUANCE_ITEM_INVENTORY_STATUS_ISSUED || status == TracingConstants.ISSUANCE_ITEM_INVENTORY_STATUS_DISCARDED);
		if (hasStatus) {
			queryString += " and i.inventoryStatus.status_ID = :status";
			if (goneStatus) {
				queryString += " and i.issueDate > :daysBack";
			}
		}
		queryString += " order by i.issuanceItem.category.description, i.issuanceItem.description, i.description";
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Query query = sess.createQuery(queryString);
			query.setParameter("code", station.getStationcode());
			if (hasStatus) {
				query.setParameter("status", status);
				if (goneStatus) {
					query.setParameter("daysBack", getDaysBackInt(7));
				}
			}
			List<IssuanceItemInventory> toReturn = query.list();
			if (status == TracingConstants.ISSUANCE_ITEM_INVENTORY_STATUS_AVAILABLE || toReturn.size() > 0) {
				return toReturn;
			}
			return null;
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}
	
	/**
	 * Get an IssuanceCategory by id. 
	 * @param id
	 * @return
	 */
	public static IssuanceCategory getItemCategory(String id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			IssuanceCategory cat = (IssuanceCategory) sess.load(IssuanceCategory.class, Long.parseLong(id));
			return  cat;
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}
	
	/**
	 * Get all IssuanceCategories that belong to a certain company.
	 * @param companyCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<IssuanceCategory> getItemCategories(String companyCode) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Query query = sess.createQuery("from IssuanceCategory c where c.company.companyCode_ID = :code");
			query.setParameter("code", companyCode);
			return  query.list();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}
	
	/**
	 * Get all templates 
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Template> getTemplate() {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Query query = sess.createQuery("from Template ");
			return  query.list();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}
		
	/**
	 * Returns a date that is X days in the past. X is determined by a company variable.
	 * @param station
	 * @return
	 */
	private static Date getDaysBackCSV(Station station) {
		int daysBack = 45;
		if (station != null && station.getCompany() != null && station.getCompany().getVariable() != null) {
			daysBack = station.getCompany().getVariable().getIssuance_edit_last_x_days();
		}
		return getDaysBackInt(daysBack);
	}
	
	/**
	 * Returns a date that is X days in the past. X is a long that is passed in.
	 * @param daysBack
	 * @return
	 */
	private static Date getDaysBackInt(long daysBack) {
		Date toReturn = TracerDateTime.getGMTDate();
		long toMinus = daysBack * 1000 * 60 * 60 * 24; // convert days to milliseconds
		toReturn.setTime(toReturn.getTime() - toMinus);
		return toReturn;
	}
	
	/**
	 * Get all audit data for quantified items for a particular station. Can also provide id to limit the data more.
	 * Only grabs the previous X days of data. X is determined by a company variable.
	 * @param station
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<AuditIssuanceItemQuantity> getAuditQuantifiedItems(Station station, long id) {
		String queryString = "from AuditIssuanceItemQuantity q where q.station.stationcode = :code";
		if (id > 0) {
			queryString += " and q.id = :id";
		}
		queryString += " and q.editDate > :daysBack order by q.editDate desc";
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Query query = sess.createQuery(queryString);
			query.setParameter("code", station.getStationcode());
			if (id > 0) {
				query.setParameter("id", id);
			}
			query.setParameter("daysBack", getDaysBackCSV(station));
			return  query.list();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}
	
	/**
	 * Get all audit data for inventoried items for a particular station. Can also provide id to limit the data more.
	 * Only grabs the previous X days of data. X is determined by a company variable.
	 * @param station
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<AuditIssuanceItemInventory> getAuditInventoriedItems(Station station, long id) {
		String queryString = "from AuditIssuanceItemInventory i where i.station.stationcode = :code";
		if (id > 0) {
			queryString += " and i.id = :id";
		}
		queryString += " and i.editDate > :daysBack order by i.editDate desc";
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Query query = sess.createQuery(queryString);
			query.setParameter("code", station.getStationcode());
			if (id > 0) {
				query.setParameter("id", id);
			}
			query.setParameter("daysBack", getDaysBackCSV(station));
			return  query.list();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}
	
	/**
	 * Edits the data for a quantified item. Only minQuantity and quantity can be edited.
	 * If minQuantity is less than 0 it will not be edited.
	 * If quantity is less than 0 then the items quantity will be decremented by the negative number.
	 * Also provides incidentID and agent for the audit data which gets created for each edit.
	 * This method is only used by the admin pages and will not be called from any incident.
	 * @param id
	 * @param quantity
	 * @param minQuantity
	 * @param user
	 * @param incID
	 */
	public static void editQuantifiedItem(long id, int quantity, int minQuantity, Agent user, String incID) {
		editQuantifiedItem(id, quantity, minQuantity, user, incID, true);
	}
	
	/**
	 * Edits the data for a quantified item. Only minQuantity and quantity can be edited.
	 * If minQuantity is less than 0 it will not be edited.
	 * If quantity is less than 0 then the items quantity will be decremented by the negative number.
	 * Also provides incidentID and agent for the audit data which gets created for each edit.
	 * @param id
	 * @param quantity
	 * @param minQuantity
	 * @param user
	 * @param incID
	 * @param fromAdmin
	 */
	public static void editQuantifiedItem(long id, int quantity, int minQuantity, Agent user, String incID, boolean fromAdmin) {
			Session sess = null;
			Transaction t = null;
			boolean issued = false;
			try {
				sess = HibernateWrapper.getSession().openSession();
				IssuanceItemQuantity qItem = (IssuanceItemQuantity) sess.load(IssuanceItemQuantity.class, id);
				int oldQuant = qItem.getQuantity();
				if (quantity >= 0) {
					qItem.setQuantity(quantity);
				} else {
					issued = true;
					qItem.setQuantity(qItem.getQuantity() + quantity);
					if (qItem.getQuantity() < 0) {
						qItem.setQuantity(0);
					}
				}
				if (minQuantity > 0) {
					qItem.setMinimuActiveQuantity(minQuantity);
				}
				Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
				AuditIssuanceItemQuantity auditQItem = mapper.map(qItem, AuditIssuanceItemQuantity.class);
				auditQItem.setEditAgent(user);
				auditQItem.setEditDate(TracerDateTime.getGMTDate());
				Incident inc = null;
				if (incID != null && incID.length() > 0) {
					auditQItem.setIncidentID(incID);
					IncidentBMO bmo = new IncidentBMO();
					inc = bmo.findIncidentByID(incID);
					auditQItem.setVerifiedIncident(inc != null);
				}
				auditQItem.setQuantityChange(qItem.getQuantity() - oldQuant);
				t = sess.beginTransaction();
				sess.update(qItem);
				sess.save(auditQItem);
				t.commit();
				if (fromAdmin && auditQItem.isVerifiedIncident() && issued) {
					if (!saveIssuanceItemIncident(qItem, null, user, inc)) {
						t.rollback();
					}
				}
			} catch (Exception e) {
				logger.fatal(e.getMessage());
				t.rollback();
			} finally {
				if (sess != null) {
					try {
						sess.close();
					} catch (Exception e) {
						logger.fatal(e.getMessage());
					}
				}
			}
	}
	
	/**
	 * Adds a new inventoried item to a station. Also creates audit data for this action.
	 * @param type
	 * @param desc
	 * @param barcode
	 * @param tradetype
	 * @param user
	 * @param station
	 * @param cost
	 */
	public static void addInventoriedItem(long type, String desc, String barcode, int tradetype, Agent user, Station station, Double cost) {
			Session sess = null;
			Transaction t = null;
			try {
				sess = HibernateWrapper.getSession().openSession();
				IssuanceItem item = (IssuanceItem) sess.load(IssuanceItem.class, type);
				IssuanceItemInventory iItem = new IssuanceItemInventory();
				iItem.setIssuanceItem(item);
				iItem.setDescription(desc);
				iItem.setBarcode(barcode);
				iItem.setTradeType(tradetype);
				iItem.setStation(station);
				iItem.setCost(cost);
				iItem.setInventoryStatus(StatusBMO.getStatus(TracingConstants.ISSUANCE_ITEM_INVENTORY_STATUS_AVAILABLE));
				t = sess.beginTransaction();
				sess.save(iItem);
				t.commit();
				Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
				AuditIssuanceItemInventory auditIItem = mapper.map(iItem, AuditIssuanceItemInventory.class);
				auditIItem.setEditAgent(user);
				auditIItem.setEditDate(TracerDateTime.getGMTDate());
				auditIItem.setReason("Added Item");
				t = sess.beginTransaction();
				sess.save(auditIItem);
				t.commit();
			} catch (Exception e) {
				logger.fatal(e.getMessage());
				t.rollback();
			} finally {
				if (sess != null) {
					try {
						sess.close();
					} catch (Exception e) {
						logger.fatal(e.getMessage());
					}
				}
			}
	}
	
	/**
	 * Edits an existing inventoried item. Also creates audit data for this action.
	 * @param id
	 * @param desc
	 * @param barcode
	 * @param tradetype
	 * @param user
	 * @param cost
	 */
	public static void editInventoriedItem(long id, String desc, String barcode, int tradetype, Agent user, Double cost) {
			Session sess = null;
			Transaction t = null;
			try {
				sess = HibernateWrapper.getSession().openSession();
				IssuanceItemInventory iItem = (IssuanceItemInventory) sess.load(IssuanceItemInventory.class, id);
				iItem.setDescription(desc);
				iItem.setBarcode(barcode);
				iItem.setTradeType(tradetype);
				iItem.setCost(cost);
				Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
				AuditIssuanceItemInventory auditIItem = mapper.map(iItem, AuditIssuanceItemInventory.class);
				auditIItem.setEditAgent(user);
				auditIItem.setEditDate(TracerDateTime.getGMTDate());
				auditIItem.setReason("Edited Item");
				t = sess.beginTransaction();
				sess.update(iItem);
				sess.save(auditIItem);
				t.commit();
			} catch (Exception e) {
				logger.fatal(e.getMessage());
				t.rollback();
			} finally {
				if (sess != null) {
					try {
						sess.close();
					} catch (Exception e) {
						logger.fatal(e.getMessage());
					}
				}
			}
	}
	
	/**
	 * Changes the status on an inventoried item. Also creates audit data for this action, collecting a custom reason as well.
	 * This method is used by the admin pages only and do not come from any incidents.
	 * @param id
	 * @param status_id
	 * @param user
	 * @param incID
	 * @param reason
	 */
	public static void moveInventoriedItem(long id, int status_id, Agent user, String incID, String reason) {
		moveInventoriedItem(id, status_id, user, incID, reason, true);
	}
	
	/**
	 * Changes the status on an inventoried item. Also creates audit data for this action, collecting a custom reason as well.
	 * @param id
	 * @param status_id
	 * @param user
	 * @param incID
	 * @param reason
	 * @param fromAdmin
	 */
	public static void moveInventoriedItem(long id, int status_id, Agent user, String incID, String reason, boolean fromAdmin) {
			Session sess = null;
			Transaction t = null;
			try {
				sess = HibernateWrapper.getSession().openSession();
				IssuanceItemInventory iItem = (IssuanceItemInventory) sess.load(IssuanceItemInventory.class, id);
				if (iItem.getInventoryStatus().getStatus_ID() != status_id) {
					iItem.setInventoryStatus(StatusBMO.getStatus(status_id));
					if (incID != null && incID.length() > 0) {
						iItem.setIncidentID(incID);
					} else if (status_id == TracingConstants.ISSUANCE_ITEM_INVENTORY_STATUS_AVAILABLE
							|| status_id == TracingConstants.ISSUANCE_ITEM_INVENTORY_STATUS_DISCARDED) {
						incID = iItem.getIncidentID();
						iItem.setIncidentID(null);
					}
					if (status_id == TracingConstants.ISSUANCE_ITEM_INVENTORY_STATUS_ISSUED
							|| status_id == TracingConstants.ISSUANCE_ITEM_INVENTORY_STATUS_DISCARDED) {
						iItem.setIssueDate(TracerDateTime.getGMTDate());
					} else {
						iItem.setIssueDate(null);
					}
					IncidentBMO bmo = new IncidentBMO();
					Incident inc = bmo.findIncidentByID(incID);
					iItem.setVerifiedIncident(inc != null);
					Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
					AuditIssuanceItemInventory auditIItem = mapper.map(iItem, AuditIssuanceItemInventory.class);
					auditIItem.setEditAgent(user);
					auditIItem.setEditDate(TracerDateTime.getGMTDate());
					auditIItem.setReason(reason);
					t = sess.beginTransaction();
					sess.update(iItem);
					sess.save(auditIItem);
					t.commit();
					if (fromAdmin && iItem.isVerifiedIncident() && status_id != TracingConstants.ISSUANCE_ITEM_INVENTORY_STATUS_DISCARDED) {
						boolean success = true;
						if (status_id == TracingConstants.ISSUANCE_ITEM_INVENTORY_STATUS_AVAILABLE) {
							success = updateIssuanceItemIncident(iItem, user, incID);
						} else {
							success = saveIssuanceItemIncident(null, iItem, user, inc);
						}
						if (!success) {
							t.rollback();
						}
					}
				}
			} catch (Exception e) {
				logger.fatal(e.getMessage());
				t.rollback();
			} finally {
				if (sess != null) {
					try {
						sess.close();
					} catch (Exception e) {
						logger.fatal(e.getMessage());
					}
				}
			}
	}

	/**
	 * Saves or updates a category, all items associated with that category, and all station items associated with those items.
	 * @param cat
	 * @param user
	 */
	@SuppressWarnings("unchecked")
	public static void saveCategory(IssuanceCategory cat, Agent user) {
		if (cat.getId() == 0) {
			cat.setCompany(CompanyBMO.getCompany(user.getCompanycode_ID()));
		}
		if (!cat.isInventory()) {
			List<Station> stations = TracerUtils.getStationList(user.getCompanycode_ID());
			for (IssuanceItem item : cat.getItems()) {
				for (Station station : stations) {
					if (item.getQuantityItems() == null) {
						item.setQuantityItems(new LinkedHashSet<IssuanceItemQuantity>());
					}
					boolean notCreated = true;
					for (IssuanceItemQuantity q_item : item.getQuantityItems()) {
						if (q_item.getStation().getStation_ID() == station.getStation_ID()) {
							notCreated = false;
						}
					}
					if (notCreated) {
						IssuanceItemQuantity newQI = new IssuanceItemQuantity();
						newQI.setIssuanceItem(item);
						newQI.setStation(station);
						newQI.setMinimuActiveQuantity(0);
						newQI.setQuantity(0);
						item.getQuantityItems().add(newQI);
					}
				}
			}
		}
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			sess.saveOrUpdate(cat);
			t.commit();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			t.rollback();
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}
	
	public static void adjustIssuanceItem(IssuanceItemIncident iItem) {
		boolean isInventoried = (iItem.getIssuanceItemInventory() != null);
		if (iItem.isReturned()) {
			if (isInventoried) {
				moveInventoriedItem(iItem.getIssuanceItemInventory().getId(), TracingConstants.ISSUANCE_ITEM_INVENTORY_STATUS_AVAILABLE, iItem.getIssueAgent(), iItem.getIncident().getIncident_ID(), "Returned from Incident", false);
			} else {
				IssuanceItemQuantity qItem = getQuantifiedItem(iItem.getIssuanceItemQuantity().getId() + "");
				int quantity = qItem.getQuantity() + iItem.getQuantity();
				editQuantifiedItem(iItem.getIssuanceItemQuantity().getId(), quantity, -1, iItem.getIssueAgent(), iItem.getIncident().getIncident_ID(), false);
			}
		} else {
			if (isInventoried) {
				int status_id = iItem.getIssuanceItemInventory().getInventoryStatus().getStatus_ID();
				moveInventoriedItem(iItem.getIssuanceItemInventory().getId(), status_id, iItem.getIssueAgent(), iItem.getIncident().getIncident_ID(), "Issued/Loaned from Incident", false);
			} else {
				int quantity = -1 * iItem.getQuantity();
				editQuantifiedItem(iItem.getIssuanceItemQuantity().getId(), quantity, -1, iItem.getIssueAgent(), iItem.getIncident().getIncident_ID(), false);
			}
		}
	}
	
	private static String generateRemark(IssuanceItemIncident iItem) {
		boolean isInventoried = (iItem.getIssuanceItemInventory() != null);
		String toReturn = "";
		if (iItem.isReturned()) {
			if (isInventoried) {
				IssuanceItemInventory item = iItem.getIssuanceItemInventory();
				toReturn = item.getDescription() + " #" + item.getBarcode() + " WAS RETURNED BY A CUSTOMER ON THIS REPORT\n";
			} else {
				int quantity = iItem.getQuantity();
				String wasWere = " WAS";
				if (quantity > 1) {
					wasWere = "s WERE";
				}
				toReturn = quantity + " " + iItem.getIssuanceItemQuantity().getIssuanceItem().getDescription() + wasWere + " RETURNED BY A CUSTOMER ON THIS REPORT\n";
			}
		} else {
			if (isInventoried) {
				IssuanceItemInventory item = iItem.getIssuanceItemInventory();
				int status_id = item.getInventoryStatus().getStatus_ID();
				String action = "LOANED";
				if (status_id == TracingConstants.ISSUANCE_ITEM_INVENTORY_STATUS_ISSUED) {
					action = "ISSUED";
				}
				toReturn = item.getDescription() + " #" + item.getBarcode() + " WAS " + action + " TO A CUSTOMER ON THIS REPORT\n";
			} else {
				int quantity = iItem.getQuantity();
				String wasWere = " WAS";
				if (quantity > 1) {
					wasWere = "s WERE";
				}
				toReturn = quantity + " " + iItem.getIssuanceItemQuantity().getIssuanceItem().getDescription() + wasWere + " ISSUED TO A CUSTOMER ON THIS REPORT\n";
			}
		}
		return toReturn;
	}
	
	public static void addIssuanceItemRemarks(Incident iDTO, Agent user) {
		if (iDTO != null && iDTO.getIssuanceItemIncidents() != null) {
			boolean saveRemark = false;
			String remarktext = "";
			for (IssuanceItemIncident iItem : iDTO.getIssuanceItemIncidents()) {
				if (iItem.isUpdated()) {
					saveRemark = true;
					remarktext += generateRemark(iItem);
				}
			}	
			if (saveRemark) {
				Remark r = new Remark();
				r.setRemarktext(remarktext);
				r.setRemarktype(TracingConstants.REMARK_REGULAR);
				r.setCreatetime(new SimpleDateFormat(TracingConstants.DB_DATETIMEFORMAT).format(TracerDateTime.getGMTDate()));
				r.setAgent(user);
				r.set_DATEFORMAT(user.getDateformat().getFormat());
				r.set_TIMEFORMAT(user.getTimeformat().getFormat());
				r.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
				r.setIncident(iDTO);
				iDTO.getRemarks().add(r);
			}
		}
	}
	
	private static boolean saveIssuanceItemIncident(IssuanceItemQuantity qItem, IssuanceItemInventory iItem, Agent user, Incident inc) {
		if (inc != null) {
			IssuanceItemIncident iss_inc = new IssuanceItemIncident();
			iss_inc.setIssueAgent(user);
			iss_inc.setIssueDate(TracerDateTime.getGMTDate());
			iss_inc.setIncident(inc);
			iss_inc.setUpdated(true);
			if (qItem != null) {
				iss_inc.setQuantity(1);
				iss_inc.setIssuanceItemQuantity(qItem);
			} else if (iItem != null) {
				iss_inc.setIssuanceItemInventory(iItem);
			}
			inc.getIssuanceItemIncidents().add(iss_inc);
			addIssuanceItemRemarks(inc, user);
			try {
				IncidentBMO bmo = new IncidentBMO();
				bmo.saveAndAuditIncident(true, inc, user, null);
			} catch (StaleStateException e) {
				return false;
			}
		}
		return true;
	}
	
	private static boolean updateIssuanceItemIncident(IssuanceItemInventory iItem, Agent user, String incident_ID) {
		IssuanceItemIncident iss_inc = getIssuanceItemIncident(iItem, incident_ID);
		if (iss_inc != null) {
			iss_inc.setUpdated(true);
			iss_inc.setReturned(true);
			iss_inc.setReturnDate(TracerDateTime.getGMTDate());
			iss_inc.setIssuanceItemInventory(iItem);
			addIssuanceItemRemarks(iss_inc.getIncident(), user);
			try {
				IncidentBMO bmo = new IncidentBMO();
				bmo.saveAndAuditIncident(true, iss_inc.getIncident(), user, null);
			} catch (StaleStateException e) {
				return false;
			}
		}
		return true;
	}
	
	private static IssuanceItemIncident getIssuanceItemIncident(IssuanceItemInventory iItem, String incident_ID) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Query query = sess.createQuery("from IssuanceItemIncident i where i.incident.incident_ID = :incID "
					+ "and i.issuanceItemInventory.id = :issID and i.returned = 0");
			query.setParameter("incID", incident_ID);
			query.setParameter("issID", iItem.getId());
			return  (IssuanceItemIncident) query.uniqueResult();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}
		}
	}

}
