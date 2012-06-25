package aero.nettracer.fs.utilities;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;


public class TransportMapper {
	
	public static aero.nettracer.fs.model.detection.TraceResponse map(aero.nettracer.fs.model.transport.v0.detection.TraceResponse fstr){
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		return mapper.map(fstr, aero.nettracer.fs.model.detection.TraceResponse.class);
	}
	
	public static List<aero.nettracer.fs.model.detection.AccessRequest> map(List<aero.nettracer.fs.model.transport.v0.detection.AccessRequest> fsar){
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		ArrayList<aero.nettracer.fs.model.detection.AccessRequest> ntar = null;
		if(fsar != null){
			ntar = new ArrayList<aero.nettracer.fs.model.detection.AccessRequest>();
			for(aero.nettracer.fs.model.transport.v0.detection.AccessRequest ar:fsar){
				ntar.add(mapper.map(ar, aero.nettracer.fs.model.detection.AccessRequest.class));
			}
		}
		return ntar;
	}
	
	public static aero.nettracer.fs.model.transport.v1.File map(aero.nettracer.fs.model.File ntfile){
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		aero.nettracer.fs.model.transport.v1.File fsfile = mapper.map(ntfile, aero.nettracer.fs.model.transport.v1.File.class);
		return fsfile;
	}
	
	public static aero.nettracer.fs.model.File map(aero.nettracer.fs.model.transport.v0.File fsfile){
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		return mapper.map(fsfile, aero.nettracer.fs.model.File.class);
	}
	
	public static aero.nettracer.fs.model.transport.v1.detection.AccessRequestDTO map(aero.nettracer.fs.model.detection.AccessRequestDTO dto){
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		return mapper.map(dto, aero.nettracer.fs.model.transport.v1.detection.AccessRequestDTO.class);
	}
}
