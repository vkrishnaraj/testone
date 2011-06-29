package aero.nettracer.general.services;

import java.util.List;

import javax.ejb.Remote;

import com.bagnet.nettracer.tracing.db.CountryCode;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.State;

@Remote
public interface GeneralServiceRemote {  
	public String echo(String s);
	public List<CountryCode> getCountries();
	public List<Station> getStations(String companycode);
	public List<State> getState();
	public List<Station> getStations(String companycode, String associated_airport);
}
