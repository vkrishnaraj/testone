package com.bagnet.nettracer.tracing.actions.lf;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.htmlparser.parserapplications.filterbuilder.Filter;

import aero.nettracer.lf.services.LFServiceBean;

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory;
import com.bagnet.nettracer.tracing.forms.lf.TraceResultsFilter;
import com.bagnet.nettracer.tracing.forms.lf.TraceResultsForm;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class TraceResultsAction extends CheckedAction {
	
	private final int REJECT = 0;
	private final int UNREJECT = 1;
	
	private static final Logger logger = Logger.getLogger(LostFoundManagerAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		TracerUtils.checkSession(session);

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		
		LFServiceBean serviceBean = new LFServiceBean();
		TraceResultsForm trForm = (TraceResultsForm) form;
		if (trForm.getFilter() == null) {
			trForm.setFilter(new TraceResultsFilter());
		}
		
		// TODO: take this logic out back and shoot it in the head!
		String value = request.getParameter("open");
		if (value != null) {
			trForm.getFilter().setOpen(value.equals("true"));
		}
		
		value = request.getParameter("closed");
		if (value != null) {
			trForm.getFilter().setClosed(value.equals("true"));
		}

		value = request.getParameter("confirmed");
		if (value != null) {
			trForm.getFilter().setConfirmed(value.equals("true"));
		}
		
		value = request.getParameter("rejected");
		if (value != null) {
			trForm.getFilter().setRejected(value.equals("true"));
		}
		
		List<LFMatchHistory> matches = new ArrayList<LFMatchHistory>();
		
		if (request.getParameter("matchId") != null) {
			long matchId = 0;
			try {
				matchId = Long.valueOf(request.getParameter("matchId"));
			} catch (NumberFormatException nfe) {
				logger.error(nfe);
			}
			
			if (matchId > 0) {
				if (request.getParameter("details") != null) {
					LFMatchHistory match = serviceBean.getTraceResult(matchId);
					request.setAttribute("match", match);
					return mapping.findForward(TracingConstants.LF_VIEW_TRACE_DETAILS);
				} else if (request.getParameter("reject") != null) {
					serviceBean.rejectMatch(matchId);
				} else if (request.getParameter("unconfirm") != null) {
					serviceBean.undoMatch(matchId);
				} else if (request.getParameter("unreject") != null) {
					serviceBean.unrejectMatch(matchId);
				} else if (request.getParameter("confirm") != null) {
					serviceBean.confirmMatch(matchId);
				}
			}
		} else if (request.getParameter("reject") != null) {
			updateMatchHistories(getSelected(trForm.getMatches()), serviceBean, REJECT);			
		} else if (request.getParameter("unreject") != null) {
			updateMatchHistories(getSelected(trForm.getMatches()), serviceBean, UNREJECT);			
		}
		
		int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, session);
		int currpage = 0;
		
		boolean resultsFiltered = trForm.getFilter().filterResults();
		long rowcount = 0;
		if (resultsFiltered) {
			rowcount = serviceBean.getFilteredTraceResultsCount(user.getStation(), trForm.getFilter());
		} else {
			rowcount = serviceBean.getTraceResultsCount(user.getStation());
		}
		
		currpage = trForm.getCurrpage() != null ? Integer.parseInt(trForm.getCurrpage()) : 0;
		if (trForm.getNextpage() != null && trForm.getNextpage().equals("1"))
			currpage++;
		if (trForm.getPrevpage() != null && trForm.getPrevpage().equals("1"))
			currpage--;
		
		int totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

		if (resultsFiltered) {
			matches = serviceBean.getFilteredTraceResultsPaginatedList(user.getStation(), trForm.getFilter(), (currpage * rowsperpage), rowsperpage);
		} else {
			matches = serviceBean.getTraceResultsPaginated(user.getStation(), (currpage * rowsperpage), rowsperpage);
			trForm.getFilter().setOpen(true);
		}
		
		if (totalpages <= currpage) {
			currpage = 0;
			request.setAttribute("currpage", "0");
		}
		
		boolean end = currpage + 1 == totalpages && totalpages > 1;
		if (end)
			request.setAttribute("end", "1");
		if (totalpages > 1) {
			ArrayList<String> al = new ArrayList<String>();
			for (int j = 0; j < totalpages; j++) {
				al.add(Integer.toString(j));
			}
			request.setAttribute("pages", al);
		}
		
		/***************** end pagination *****************/

		request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
		request.setAttribute("currpage", Integer.toString(currpage));		
		
		trForm.setMatches(new ArrayList<LFMatchHistory>(matches));
		
		session.setAttribute("filter", trForm.getFilter());
		return mapping.findForward(TracingConstants.LF_VIEW_TRACE_RESULTS);
		
	}
	
	private ArrayList<Long> getSelected(ArrayList<LFMatchHistory> matches) {
		ArrayList<Long> selectedMatches = new ArrayList<Long>();
		for (LFMatchHistory match: matches) {
			if (match.isSelected()) {
				selectedMatches.add(match.getId());
			}
		}
		return selectedMatches;
	}
	
	private void updateMatchHistories(ArrayList<Long> matchIds, LFServiceBean serviceBean, int actionType) {
		// I used a switch here due to our recent debate
		switch (actionType) {
			case REJECT:
				for (long id: matchIds) {
					serviceBean.rejectMatch(id);
				}
				break;
			case UNREJECT:
				for (long id: matchIds) {
					serviceBean.unrejectMatch(id);
				}
				break;
			default:
				// do nothing
				break;
		}
	}
	
}