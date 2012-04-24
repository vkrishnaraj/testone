package aero.nettracer.lfc.control;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
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
		return login(TracingConstants.LF_AB_COMPANY_ID, TracingConstants.LF_AVIS_COMPANY_ID);
	}
	
	public String loginBudget() {
		return login(TracingConstants.LF_AB_COMPANY_ID, TracingConstants.LF_BUDGET_COMPANY_ID);
	}
	
	public String loginSouthwest() {
		return login(TracingConstants.LF_LF_COMPANY_ID, TracingConstants.LF_SWA_COMPANY_ID);
	}
	
	public String loginDemo() {
		return login(TracingConstants.LF_LF_COMPANY_ID, TracingConstants.LF_DEMO_COMPANY_ID);
	}
	
	public String login(String company, String subCompany) {
		LostReportBean report = clientViewService.login(login, company);
		if (report != null) {
			report.setSubCompany(subCompany);
			report.setCompany(company);
			HttpSession session = (HttpSession)FacesContext.getCurrentInstance()
			.getExternalContext().getSession(false);
			session.setAttribute("lostReport", report);
			if(TracingConstants.LF_SWA_COMPANY_ID.equals(subCompany)) {
				if(report.getStatus().equals("Closed")) {
					return "closedform?faces-redirect=true";
				} else {
					session.setAttribute("edit", true);
					return "lostform?faces-redirect=true";
				}
				
			} else {
				return "status?faces-redirect=true";
			}
		}
		return null;
	}
	
	public String goToFormPageAvis() {
		lostReport.setSubCompany(TracingConstants.LF_AVIS_COMPANY_ID);
		lostReport.setCompany(TracingConstants.LF_AB_COMPANY_ID);
		return goToFormPage("lostform");
	}
	
	public String goToFormPageDemo() {
		lostReport.setSubCompany(TracingConstants.LF_DEMO_COMPANY_ID);
		lostReport.setCompany(TracingConstants.LF_LF_COMPANY_ID);
		return goToFormPage("lostform");
	}
	
	public String goToFormPageBudget() {
		lostReport.setSubCompany(TracingConstants.LF_BUDGET_COMPANY_ID);
		lostReport.setCompany(TracingConstants.LF_AB_COMPANY_ID);
		return goToFormPage("lostform");
	}
	
	public String goToFormPageSouthwest() {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
				session.setAttribute("edit", false);
		lostReport.setSubCompany(TracingConstants.LF_SWA_COMPANY_ID);
		lostReport.setCompany(TracingConstants.LF_LF_COMPANY_ID);
		return goToFormPage("bagunchecked");
	}
	
	public String goToCheckedPageSouthwest() {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
				session.setAttribute("edit", false);
		lostReport.setSubCompany(TracingConstants.LF_SWA_COMPANY_ID);
		lostReport.setCompany(TracingConstants.LF_LF_COMPANY_ID);
		return goToFormPage("bagchecked");
	}
	
	public String goToFormPage(String page) {
		if (RemoteService.getLists()) {
			//invalidateSession();
			HttpSession session = (HttpSession)FacesContext.getCurrentInstance()
			.getExternalContext().getSession(false);
			session.setAttribute("lostReport", lostReport);
			return page + "?faces-redirect=true";
		}
		FacesUtil.addError("Server Communication Error.");
		return null;
	}

	public ClientViewService getClientViewService() {
		return clientViewService;
	}

	public void setClientViewService(ClientViewService clientViewService) {
		this.clientViewService = clientViewService;
	}

}
