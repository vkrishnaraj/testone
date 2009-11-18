package com.bagnet.clients.us;

import java.util.Comparator;

import com.bagnet.nettracer.tracing.dto.ScannerDataDTO;

public class ScannerComparator implements Comparator {

	@Override
	public int compare(Object a, Object b) {
		if (a instanceof ScannerDataDTO && b instanceof ScannerDataDTO) {
			long k = ((ScannerDataDTO) a).getTimestamp().getTimeInMillis();
			long l = ((ScannerDataDTO) b).getTimestamp().getTimeInMillis();
			
			if (k < l) {
				return -1;
			} else if (k > l) {
				return 1;
			}
		}
		return 0;
	}

}
