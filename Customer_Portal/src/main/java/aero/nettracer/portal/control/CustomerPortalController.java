package aero.nettracer.portal.control;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("customerPortal")
@Qualifier("customerPortal")
@Scope("view")
public class CustomerPortalController {

	private static Logger logger = Logger.getLogger(CustomerPortalController.class);

	//@Autowired
	//private ClientViewService clientViewService;

	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
	}

}
