package aero.nettracer.general.services;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;

import com.bagnet.nettracer.tracing.db.CountryCode;
import com.bagnet.nettracer.tracing.db.State;
import com.bagnet.nettracer.tracing.db.Station;

@Stateless
public class GeneralServiceBean implements GeneralServiceRemote{
	@Override
	public String echo(String s) {
		return "echo: " + s;
	}

	@Override
	public List<CountryCode> getCountries() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Station> getStations(String companycode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<State> getState() {
		// TODO Auto-generated method stub
		return null;
	}
	


}
