import tools.AbqConsumer;
import aero.nettracer.fs.model.File;
import aero.nettracer.selfservice.fraud.ClaimBean;


public class ImportSwaThread extends AbqConsumer{
	
	private static ClaimBean b = new ClaimBean();

	@Override
	public void processItem(Object itemToProcess) {
		File f = (File) itemToProcess;
		long l = b.insertFile(f);
		
	}

	@Override
	public void cleanseSessions() {
		// TODO Auto-generated method stub
		
	}

}
