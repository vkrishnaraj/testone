package com.bagnet.nettracer.tracing.utils;

import com.bagnet.nettracer.tracing.db.Incident;

public interface Precoder {
	public PrecoderResult getFaultStationAndLossCode(Incident inc);
}
