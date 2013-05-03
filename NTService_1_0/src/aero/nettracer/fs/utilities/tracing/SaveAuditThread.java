package aero.nettracer.fs.utilities.tracing;

import aero.nettracer.fs.model.FsActionAudit;
import aero.nettracer.fs.utilities.AuditUtil;

public class SaveAuditThread implements Runnable{
	FsActionAudit audit;
	public SaveAuditThread(FsActionAudit audit){
		this.audit = audit;
	}
	@Override
	public void run() {
		AuditUtil.saveActionAudit(this.audit);
	}
}
