package aero.nettracer.fs.utilities;

import static org.junit.Assert.*;

import org.junit.Test;

public class UtilTest {
	@Test
	public void testNormalizeGeoNumber(){
		assertTrue("0.00000".equals(Util.normalizeGeoNumber(0)));
		assertTrue("-1.00000".equals(Util.normalizeGeoNumber(-1)));
		assertTrue("1.00000".equals(Util.normalizeGeoNumber(1)));
		assertTrue("1.44444".equals(Util.normalizeGeoNumber(1.444444)));
		assertTrue("1.44444".equals(Util.normalizeGeoNumber(1.444445)));//Java by default uses HALF_EVEN (learn something new everyday)
		assertTrue("1.55556".equals(Util.normalizeGeoNumber(1.555555)));
	}
}
