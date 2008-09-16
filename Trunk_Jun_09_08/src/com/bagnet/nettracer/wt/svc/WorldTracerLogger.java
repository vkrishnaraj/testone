package com.bagnet.nettracer.wt.svc;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.bagnet.nettracer.cronjob.wt.WorldTracerTx;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerTransaction;
import com.bagnet.nettracer.wt.bmo.WtTransactionBmo;
import com.bagnet.nettracer.wt.svc.WorldTracerService.TxType;

@Aspect
public class WorldTracerLogger {

	public static final Logger logger = Logger.getLogger(WorldTracerLogger.class);

	private WtTransactionBmo txBmo;

	public WorldTracerLogger(WtTransactionBmo txBmo) {
		this.txBmo = txBmo;
	}

	@Pointcut("within(com.bagnet.nettracer.wt.svc..*)")
	public void inWorldTracerService() {
	}

	@Around("com.bagnet.nettracer.wt.svc.WorldTracerLogger.inWorldTracerService() &&" + "@annotation(tx)")
	public Object logWorldTracer(ProceedingJoinPoint pjp, WorldTracerTx tx) throws Throwable {

		TxType type = tx.type();
		WorldTracerTransaction wttx = new WorldTracerTransaction(type);
		wttx.startTransaction();
		try {
			switch(type) {
			case CREATE_AHL:
			case AMEND_AHL:
			case SUSPEND_AHL:
			case REINSTATE_AHL:
				Incident inc = (Incident) pjp.getArgs()[0];
				wttx.setIncident(inc);
				break;
			case AMEND_OHD:
			case CLOSE_OHD:
			case CREATE_OHD:
			case REINSTATE_OHD:
				OHD ohd = (OHD) pjp.getArgs()[0];
				wttx.setOhd(ohd);
				break;
			}
			Object retVal = pjp.proceed();
			wttx.successTransaction(retVal != null ? retVal.toString() : null);
			return retVal;
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
