package com.bagnet.nettracer.cronjob.tracing;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.bagnet.nettracer.cronjob.tracing.dto.OhdReferenceContainer;

public class OhdReference {
	private HashMap<Date, OhdReferenceContainer> refHash = null;
	private static final int OHD_ID = 0;
	private static final int FOUND_DATE = 1;
	private ArrayList<String> updatedOhds = null;
	private Date lastUpdatedDate = null;
	private ArrayList<OhdReferenceContainer> refArray = null;
	
	public void rebuild(List<Date> dateList, List<Object[]> validOhdList, Date lastUpdatedDate) {
		updatedOhds = new ArrayList();
		refHash = new HashMap<Date, OhdReferenceContainer>();
		refArray = new ArrayList<OhdReferenceContainer>();
		this.lastUpdatedDate = lastUpdatedDate;
		
		int index = 0;
		for (Date date: dateList) {
			ArrayList<String> tmpList = new ArrayList<String>();
			while(validOhdList.size() > 0 && ((Date) validOhdList.get(0)[FOUND_DATE]).equals(date)) {
				tmpList.add((String) validOhdList.remove(0)[OHD_ID]);
			}
			
			OhdReferenceContainer container = new OhdReferenceContainer(date, index, tmpList);
			refArray.add(container);
			refHash.put(date, container);
			++ index;
		}
	}
	
	public void addUpdatedOhd(String ohdId) {
		updatedOhds.add(ohdId);
	}
	
	public List<String> getCompareList(Date date, int forward, int backward) {
		ArrayList<String> list = new ArrayList<String>();
		return list;
	}
}
