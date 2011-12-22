package aero.nettracer.lfc.control;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import aero.nettracer.lfc.faces.util.FacesUtil;
import aero.nettracer.lfc.model.LoginBean;
import aero.nettracer.lfc.model.LostReportBean;
import aero.nettracer.lfc.remote.RemoteService;
import aero.nettracer.lfc.service.ClientViewService;

import com.bagnet.nettracer.tracing.constant.TracingConstants;

@Component("lostAndFoundLogin")
@Qualifier("lostAndFoundLogin")
@Scope("request")
public class LostAndFoundLoginController {
 
	@Autowired
	private ClientViewService clientViewService;
	
	private LoginBean login = new LoginBean();
	private LostReportBean lostReport = new LostReportBean();

	public LoginBean getLogin() {
		return login;
	}

	public void setLogin(LoginBean login) {
		this.login = login;
	}

	public LostReportBean getLostReport() {
		return lostReport;
	}

	public void setLostReport(LostReportBean lostReport) {
		this.lostReport = lostReport;
	}
	
	public String loginAvis() {
		lostReport.setCompany(TracingConstants.LF_AVIS_COMPANY_ID);
		return login();
	}
	
	public String loginBudget() {
		lostReport.setCompany(TracingConstants.LF_BUDGET_COMPANY_ID);
		return login();
	}
	
	public String loginSouthwest() {
		lostReport.setCompany("SWA");
		return login();
	}
	
	public String login() {
		LostReportBean lostReport = clientViewService.login(login, getCompany());
		if (lostReport != null) {
			//invalidateSession();
			HttpSession session = (HttpSession)FacesContext.getCurrentInstance()
			.getExternalContext().getSession(false);
			session.setAttribute("lostReport", lostReport);
			return "status?faces-redirect=true";
		}
		return null;
	}
	
	public String goToFormPageAvis() {
		lostReport.setCompany(TracingConstants.LF_AVIS_COMPANY_ID);
		return goToFormPage();
	}
	
	public String goToFormPageBudget() {
		lostReport.setCompany(TracingConstants.LF_BUDGET_COMPANY_ID);
		return goToFormPage();
	}
	
	public String goToFormPageSouthwest() {
		lostReport.setCompany("SWA");
		return goToFormPage();
	}
	
	public String goToFormPage() {
		if (RemoteService.getLists(getCompany())) {
			//invalidateSession();
			HttpSession session = (HttpSession)FacesContext.getCurrentInstance()
			.getExternalContext().getSession(false);
			session.setAttribute("lostReport", lostReport);
			return "lostform?faces-redirect=true";
		}
		FacesUtil.addError("Server Communication Error.");
		return null;
	}
	
	private String getCompany() {
		if (lostReport != null) {
			return lostReport.getCompany();
		}
		return null;
	}

	public ClientViewService getClientViewService() {
		return clientViewService;
	}

	public void setClientViewService(ClientViewService clientViewService) {
		this.clientViewService = clientViewService;
	}

}
