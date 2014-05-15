package com.bagnet.nettracer.tracing.service.label;

import java.util.List;

import com.bagnet.nettracer.tracing.dao.label.LabelDao;
import com.bagnet.nettracer.tracing.db.Label;

public class LabelServiceImpl implements LabelService {
	
	private LabelDao dao;
	
	public LabelDao getDao() {
		return dao;
	}

	public void setDao(LabelDao dao) {
		this.dao = dao;
	}
	
	@Override
	public List<Label> getLabels(int agentId) {
		return dao.getLabels(agentId);
	}

	@Override
	public long getLabelCountForAgent(int agentId) {
		return dao.getLabelCountForAgent(agentId);
	}
	
	@Override
	public long save(Label label) {
		return dao.save(label);
	}

	@Override
	public boolean update(Label label) {
		return dao.update(label);
	}

	@Override
	public boolean delete(int agentId, long labelId) {
		return dao.delete(agentId, labelId);
	}

	@Override
	public Label load(long labelId) {
		return dao.load(labelId);
	}

}
