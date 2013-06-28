import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.bagnet.clients.us.wt.UsWorldTracerRuleMap;
import com.bagnet.nettracer.cronjob.BaseErrorHandler;
import com.bagnet.nettracer.cronjob.ErrorHandler;
import com.bagnet.nettracer.cronjob.bmo.WTQueueBmo;
import com.bagnet.nettracer.cronjob.bmo.WT_ActionFileBmo;
import com.bagnet.nettracer.cronjob.wt.WorldTracerQueueWorker;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.wt.svc.DefaultWorldTracerService;
import com.bagnet.nettracer.wt.svc.RuleMapper;
import com.bagnet.nettracer.wt.svc.WorldTracerService;


public class WTQTest {
	private static final Logger logger = Logger.getLogger(WTQTest.class);
	public static final String WT_QUEUE_WORKERS = "wt.queue.workers";

	private WTQueueBmo wtqBmo;
	private String companyCode = "US";

	private WT_ActionFileBmo wafBmo;

	private Agent ogadmin = AdminUtils.getAgentBasedOnUsername("ntadmin", companyCode);;
	
	private RuleMapper wtRuleMap;

	private WorldTracerService wtService;

	private ErrorHandler errorHandler;
	
	@Test
	public void test() {
		logger.debug("starting a queue run");
		Company_Specific_Variable csv = AdminUtils.getCompVariable(companyCode);
		if (csv == null || csv.getWt_enabled() != 1 || csv.getWt_write_enabled() != 1) {
			return;
		}
		int workerThreads = Integer.parseInt(PropertyBMO.getValue(WT_QUEUE_WORKERS));
		
		logger.debug("i think i need to start " + workerThreads + " worker threads");
		
		//proess the queued tasks
		List<Long> qTasks = null;
		try {
			qTasks = wtqBmo.findAllPendingTasks(companyCode);
			logger.debug("I got " + qTasks.size() + " tasks to work");
		}
		catch (Exception e1) {
			logger.error("unable to load world tracer queue tasks, process aborted", e1);
			return;
		}
		if (qTasks == null || qTasks.size() < 1) {
			logger.info("No queued tasks");
			return;
		}
		ConcurrentLinkedQueue<Long> qq = new ConcurrentLinkedQueue<Long>(qTasks);
		wtqBmo = new WTQueueBmo();
		wtqBmo.setSessionFactory(HibernateWrapper.getSession());
		wafBmo = new WT_ActionFileBmo();
		wafBmo.setSessionFactory(HibernateWrapper.getSession());
		DefaultWorldTracerService dwtServ = new DefaultWorldTracerService();
		dwtServ.setWtCompanyCode(companyCode);
		wtService=dwtServ;
		wtRuleMap = new UsWorldTracerRuleMap();
		BaseErrorHandler bEHand = new BaseErrorHandler(companyCode);
		bEHand.setEmailTo("support@nettracer.aero");
		bEHand.setEmailFrom("support@nettracer.aero");
		bEHand.setInstanceName("HI");
		bEHand.setProcessName("WT Queue");
		errorHandler = bEHand;
		WorldTracerQueueWorker ww = new WorldTracerQueueWorker(wtService, companyCode, wtqBmo, ogadmin, wafBmo, qq, wtRuleMap, errorHandler);
		Thread t = new Thread(ww);
		t.start();
	}
	
}