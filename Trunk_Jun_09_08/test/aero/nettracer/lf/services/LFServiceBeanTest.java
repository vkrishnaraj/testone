package aero.nettracer.lf.services;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFLost;

public class LFServiceBeanTest {

	@Test
	public void lostSaveLoadTest(){
		LFServiceBean bean = new LFServiceBean();
		LFLost lost = createLostTestCase();
		long lostId = bean.saveOrUpdateLostReport(lost);
		assertTrue(lostId != -1);
		
		LFLost loaded = bean.getLostReport(lostId);
		assertTrue(loaded != null && loaded.getId() == lostId);
	}
	
	@Test
	public void foundSaveLoadTest(){
		LFServiceBean bean = new LFServiceBean();
		LFFound found = createFoundTestCase();
		long foundId = bean.saveOrUpdateFoundItem(found);
		assertTrue(foundId != -1);
		
		LFFound loaded = bean.getFoundItem(foundId);
		assertTrue(loaded != null && loaded.getId() == foundId);
	}
	
	
	public LFLost createLostTestCase(){
		return new LFLost();
	}
	
	public LFFound createFoundTestCase(){
		return new LFFound();
	}
}
