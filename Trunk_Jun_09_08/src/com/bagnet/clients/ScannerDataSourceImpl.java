package com.bagnet.clients;

import java.util.Date;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.datasources.ScannerDataSource;
import com.bagnet.nettracer.tracing.dto.ScannerDTO;
import com.bagnet.nettracer.tracing.utils.TracerProperties;

public class ScannerDataSourceImpl implements ScannerDataSource {
protected static Logger logger = Logger.getLogger(ScannerDataSourceImpl.class);
	
	private ScannerDataSource target=new com.bagnet.clients.defaul.ScannerDataSourceImpl();
	

	public ScannerDataSourceImpl() {
		String companyCode = TracerProperties.get("wt.company.code");
		String path = TracerProperties.get(companyCode, "scanner.class.path");
		try {
			Class cls = Class.forName("com.bagnet.clients." + path
					+ ".ScannerDataSourceImpl");
			ScannerDataSource res = (ScannerDataSource) cls.newInstance();
			target = res;
		} catch (Exception x) {

		}

	}

	@Override
	public ScannerDTO getScannerData(Date startDate, Date endDate,
			String bagTagNumber) {
		// TODO Auto-generated method stub
		return target.getScannerData(startDate, endDate, bagTagNumber);
	}


	@Override
	public ScannerDTO getScannerData(Date startDate, Date endDate,
			String bagTagNumber, int timeout) {
		// TODO Auto-generated method stub
		return target.getScannerData(startDate, endDate, bagTagNumber, timeout);
	}

}
