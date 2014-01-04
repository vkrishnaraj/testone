package aero.nettracer.security;

import javax.servlet.http.HttpServletRequest;

public interface SsoUtils {
	public SsoNode getSsoNode(HttpServletRequest request);
}
