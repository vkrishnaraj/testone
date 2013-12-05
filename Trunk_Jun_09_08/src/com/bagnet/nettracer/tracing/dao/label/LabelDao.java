package com.bagnet.nettracer.tracing.dao.label;

import java.util.List;

import com.bagnet.nettracer.tracing.db.Label;

public interface LabelDao {
	public long save(Label label);
	public boolean update(Label label);
	public boolean delete(int agentId, long labelId);
	public Label load(long labelId);
	public List<Label> getLabels(int agentId);

}
