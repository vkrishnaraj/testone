package com.bagnet.nettracer.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerTransaction;
import com.bagnet.nettracer.tracing.db.wtq.WtqFwdOhd;
import com.bagnet.nettracer.wt.WorldTracerAlreadyClosedException;
import com.bagnet.nettracer.wt.bmo.WtTransactionBmo;
import com.bagnet.nettracer.wt.svc.WorldTracerService;
import com.bagnet.nettracer.wt.svc.WorldTracerService.TxType;

@Aspect
public class WorldTracerLogger {

	public static final Logger logger = Logger.getLogger(WorldTracerLogger.class);

	private WtTransactionBmo txBmo;

	public WorldTracerLogger(WtTransactionBmo txBmo) {
		this.txBmo = txBmo;
	}

	@Around("PointCuts.inWorldTracerService() &&" + "@annotation(tx)")
	public Object logWorldTracer(ProceedingJoinPoint pjp, WorldTracerTx tx) throws Throwable {

		TxType type = tx.type();
		WorldTracerTransaction wttx = new WorldTracerTransaction(type);
		wttx.startTransaction();
		try {
			switch (type) {
			case CREATE_AHL:
			case AMEND_AHL:
			case SUSPEND_AHL:
			case REINSTATE_AHL:
			case CLOSE_AHL:
				Incident inc = (Incident) pjp.getArgs()[0];
				wttx.setIncident(inc);
				break;
			case AMEND_OHD:
			case CLOSE_OHD:
			case CREATE_OHD:
			case REINSTATE_OHD:
			case SUSPEND_OHD:
				OHD ohd = (OHD) pjp.getArgs()[0];
				wttx.setOhd(ohd);
				break;
			case FWD_OHD:
				WtqFwdOhd foh = (WtqFwdOhd) pjp.getArgs()[0];
				if(foh != null) {
					OHD ohd2 = foh.getOhd();
					wttx.setOhd(ohd2);
				}
				break;
			case CREATE_BDO:
				BDO bdo = (BDO) pjp.getArgs()[0];
				if(bdo != null) {
					if(bdo.getOhd() != null) {
						wttx.setOhd(bdo.getOhd());
					}
					if(bdo.getIncident() != null) {
						wttx.setIncident(bdo.getIncident());
					}
				}
				break;
			}
			Object retVal = pjp.proceed();
			wttx.successTransaction(retVal != null ? retVal.toString() : null);
			return retVal;
		}
		catch (WorldTracerAlreadyClosedException ex) {
			if(type.equals(TxType.AMEND_AHL)) {
				wttx.successTransaction("Already Closed");
			}
			else {
				wttx.failTransaction(ex);
			}
			throw ex;
		}
		catch (Throwable e) {
			wttx.failTransaction(e);
			throw e;
		}
		finally {
			try {
				wttx.endTransaction();
				txBmo.saveTransaction(wttx);
			}
			catch (Throwable t) {
				logger.error("Unable to save WorldTracerTransaction Info", t);
			}
		}
	}
}
