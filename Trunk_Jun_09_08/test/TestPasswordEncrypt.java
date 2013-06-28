import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.tracing.utils.TEA;


public class TestPasswordEncrypt {

	private String newPass = "/LucasArts43!";
	
    @org.junit.Test
	public void testReportClaimDate() {

    	System.out.println("SHA1_256 = \"" + StringUtils.sha1_256(newPass, true) + "\"");
    	System.out.println("SHA1 = \"" + StringUtils.sha1(newPass, true) + "\"");
    	System.out.println("TEA = \"" + TEA.encryptTEA(newPass) + "\"");
	}
    
}
