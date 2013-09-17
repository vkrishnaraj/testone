package com.bagnet.nettracer.tracing.dao;

import com.bagnet.nettracer.tracing.db.documents.Document;

public interface DocumentDAO {
	public Document load(long documentId);
	public long save(Document document);
	public boolean update(Document document);
	public boolean delete(long documentId);
}
