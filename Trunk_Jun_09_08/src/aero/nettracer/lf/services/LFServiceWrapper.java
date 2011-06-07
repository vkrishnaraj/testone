package aero.nettracer.lf.services;


public class LFServiceWrapper {

	private static LFServiceHome instance = new LFServiceBean();
	
	public static LFServiceHome getInstance() {
		return instance;
	}
	
}
