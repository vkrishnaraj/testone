package aero.nettracer.general.services;

import java.util.List;

import org.junit.Test;

import com.bagnet.nettracer.tracing.db.CountryCode;
import com.bagnet.nettracer.tracing.db.State;
import com.bagnet.nettracer.tracing.db.Station;

public class GeneralServiceBeanTest {

	@Test
	public void getCountryTest(){
		GeneralServiceBean bean = new GeneralServiceBean();
		List<CountryCode> countries = bean.getCountries();
		for(CountryCode country: countries){
			System.out.println(country.getCountry());
		}
	}
	
	@Test
	public void getStationTest(){
		GeneralServiceBean bean = new GeneralServiceBean();
		List<Station> stations = bean.getStations("WS");
		for(Station station: stations){
			System.out.println(station.getStationcode());
		}
	}
	
	@Test
	public void getStateTest(){
		GeneralServiceBean bean = new GeneralServiceBean();
		List<State> states = bean.getState();
		for(State state: states){
			System.out.println(state.getState());
		}
	}
}
