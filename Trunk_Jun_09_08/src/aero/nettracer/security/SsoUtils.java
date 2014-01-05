package aero.nettracer.security;

import javax.servlet.http.HttpServletRequest;

public interface SsoUtils {
	/**
	 * Takes in an HttpServletRequest to check in any SSO params exists, and if properly validated, return an SsoNode
	 * 
	 * @param request
	 * @return
	 */
	public SsoNode getSsoNode(HttpServletRequest request);
}
