package aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.connection;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.params.HttpClientParams;

public class WtHttpClient extends HttpClient {

	protected boolean validConnection = false;
	
	public WtHttpClient(HttpClientParams params, HttpConnectionManager httpConnectionManager) {
		super(params, httpConnectionManager);
	}

	public WtHttpClient() {
		super();
	}

	public WtHttpClient(HttpClientParams arg0) {
		super(arg0);
	}

	public WtHttpClient(HttpConnectionManager httpConnectionManager) {
		super(httpConnectionManager);
	}

	public void setValidConnection(boolean validConnection) {
		this.validConnection = validConnection;
	}
	
	public boolean isValidConnection() {
		return validConnection;
	}

	

}
