package com.bagnet.clients.defaul;

import com.bagnet.nettracer.tracing.utils.Precoder;
import com.bagnet.nettracer.tracing.utils.PrecoderResult;
import com.bagnet.nettracer.tracing.db.Incident;

public class PrecoderImpl implements Precoder{

	@Override
	public PrecoderResult getFaultStationAndLossCode(Incident inc) {
		// TODO Auto-generated method stub
		System.out.println("defaul PreCoder");
		return null;
	}

}
