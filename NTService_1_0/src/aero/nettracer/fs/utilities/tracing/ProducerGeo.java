package aero.nettracer.fs.utilities.tracing;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;

import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsAddress;
import aero.nettracer.fs.utilities.GeoCode;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;

public class ProducerGeo implements Runnable{

	private static final Logger logger = Logger.getLogger(ProducerGeo.class);

	ConsumerQueueElement element;
	File file;
	boolean isPrimary;
	Date createDate;

	public ProducerGeo(ConsumerQueueElement element, File file, boolean isPrimary, Date createDate){
		this.element = element;
		this.file = file;
		this.isPrimary = isPrimary;
		this.createDate = createDate;
	}


	@Override
	public void run() {
		Date producerGeoStarttime = new Date();
		String sql = "";
		Set<FsAddress> addresses = Consumer.getAddresses(file);
		file.setAddressCache(addresses);
		if (addresses != null && addresses.size() > 0) {
			sql += "select f1.id as f1_id, " +
					"f2.id as f2_id, " +
					"f3.id as f3_id, " +
					"f4.id as f4_id, " +
					"f5.id as f5_id, " +
					"null as f6_id, " +
					"null as f7_id, " +
					"'geo' as type " +
					"from fsaddress ad " + 
					"left outer join person p on ad.person_id = p.id " +
					"left outer join fsclaim c1 on p.claim_id = c1.id " +
					"left outer join FsFile f1 on f1.id = c1.file_id " +

	        "left outer join fsincident i2 on p.incident_id = i2.id " +
	        "left outer join fsclaim c2 on i2.claim_id = c2.id " +
	        "left outer join FsFile f2 on f2.id = c2.file_id " +
	        "left outer join FsFile f3 on f3.id = i2.file_id " +

	        "left outer join reservation r3 on p.reservation_id = r3.id " +
	        "left outer join fsincident i3 on r3.incident_id = i3.id " +
	        "left outer join fsclaim c3 on i3.claim_id = c3.id " +
	        "left outer join FsFile f4 on f4.id = c3.file_id " +
	        "left outer join FsFile f5 on f5.id = i3.file_id " +

	              "where 1=0 ";

			for (FsAddress a: addresses) {
				// If it was geocoded, compare against other geocoded items.
				if (a.getLattitude() != 0) {
					// If the address has been geocoded we will calculate
					// the area (min/max latitude and longitude) in which to
					// search.
					double mileSearch = Producer.MILE_SEARCH_RADIUS;
					if (a.getGeocodeType() == GeoCode.ACCURACY_STREET) {
						a.setGeocodeType(GeoCode.ACCURACY_STREET);
					} else if (a.getGeocodeType() == GeoCode.ACCURACY_CITY) {
						a.setGeocodeType(GeoCode.ACCURACY_CITY);
						mileSearch = Producer.MILE_SEARCH_CITY;
					} else if (a.getGeocodeType() == GeoCode.ACCURACY_ZIP) {
						a.setGeocodeType(GeoCode.ACCURACY_ZIP);
						mileSearch = Producer.MILE_SEARCH_ZIP;
					}
					double latRadius = GeoCode.getLatRadius(mileSearch);
					double longRadius = GeoCode.getLongRadius(a.getLattitude(), mileSearch);
					double y1 = a.getLattitude() - latRadius;
					double y2 = a.getLattitude() + latRadius;
					double x1 = a.getLongitude() - longRadius;
					double x2 = a.getLongitude() + longRadius;

					// If there is a latitude or longitude.
					sql += "or (lattitude >= " + y1 + " and lattitude <= " + y2 + " and longitude >= " + x1+ " and longitude <= " + x2 + ") ";

					// Compare against non-geocoded items.
					// Country / City
					if (a.getCity() != null && a.getCity().trim().length() > 0 && a.getCountry() != null && a.getCountry().trim().length() > 0) {
						sql += "or (lattitude = 0 and longitude = 0 and ad.country = \'" + Producer.format(a.getCountry()) + "\' and ad.city = \'" + Producer.format(a.getCity()) + "\' ) ";
					}
				} else {
					// This else is if the address wasn't geocoded.
					if (a.getCity() != null && a.getCity().trim().length() > 0 && a.getCountry() != null && a.getCountry().trim().length() > 0) {
						sql += "or (ad.country = \'" + Producer.format(a.getCountry()) + "\' and ad.city = \'" + Producer.format(a.getCity()) + "\' ) ";
					}
				}
			}		
		}

		logger.debug("Producer query: " + sql);

		List<Object[]> result = null;

		SQLQuery pq = null;
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			pq = sess.createSQLQuery(sql.toString());
			pq.addScalar("f1_id", StandardBasicTypes.LONG);
			pq.addScalar("f2_id", StandardBasicTypes.LONG);
			pq.addScalar("f3_id", StandardBasicTypes.LONG);
			pq.addScalar("f4_id", StandardBasicTypes.LONG);
			pq.addScalar("f5_id", StandardBasicTypes.LONG);
			pq.addScalar("f6_id", StandardBasicTypes.LONG);
			pq.addScalar("f7_id", StandardBasicTypes.LONG);
			pq.addScalar("type", StandardBasicTypes.STRING);
			result = pq.list();
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if (sess != null){
				sess.close();
			}
		}

		int count = -1;//remove match against self
		
		if(result != null){
			for (Object[] strs : result) {
				Long f1 = (Long) strs[0];
				Long f2 = (Long) strs[1];
				Long f3 = (Long) strs[2];
				Long f4 = (Long) strs[3];
				Long f5 = (Long) strs[4];
				Long f6 = (Long) strs[5];
				Long f7 = (Long) strs[6];
				String type = (String) strs[7];

				if(f1 != null){
					Producer.queueFile(f1, element, file, createDate, isPrimary);
				} else if (f2 != null){
					Producer.queueFile(f2, element, file, createDate, isPrimary);
				} else if (f3 != null){
					Producer.queueFile(f3, element, file, createDate, isPrimary);
				} else if (f4 != null){
					Producer.queueFile(f4, element, file, createDate, isPrimary);
				} else if (f5 != null){
					Producer.queueFile(f5, element, file, createDate, isPrimary);
				} else if (f6 != null){
					Producer.queueFile(f6, element, file, createDate, isPrimary);
				} else if (f7 != null){
					Producer.queueFile(f7, element, file, createDate, isPrimary);
				}
				count++;
			}		
		}

		Date producerGeoEndtime = new Date();
		element.setProducerGeoStart(producerGeoStarttime);
		element.setProducerGeoEnd(producerGeoEndtime);
		element.setProducerGeoCount(count);
		element.setProducerGeoFinished(true);
	}

}
