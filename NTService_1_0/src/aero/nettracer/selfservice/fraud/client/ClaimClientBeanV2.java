package aero.nettracer.selfservice.fraud.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import aero.nettracer.fs.model.transport.v0.detection.AccessRequestDTO;
import aero.nettracer.fs.model.transport.v0.detection.AccessRequest;
import aero.nettracer.fs.model.transport.v0.detection.TraceResponse;
import aero.nettracer.fs.model.transport.v0.File;
import aero.nettracer.selfservice.fraud.ClaimBean;


@Stateless
public class ClaimClientBeanV2 implements ClaimClientRemoteV2{

	ClaimBean bean = new ClaimBean();
	
	@Override
	public String echoTest(String s) {
		return bean.echoTest("V2"+s);
	}

	@Override
	public long insertFile(File file) {
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		return bean.insertFile(mapper.map((aero.nettracer.fs.model.transport.v2.File)file, aero.nettracer.fs.model.File.class));
	}

	@Override
	public TraceResponse traceFile(long fileId, int maxDelay,
			boolean isPrimary, boolean returnResults) {
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		return mapper.map(bean.traceFile(fileId, maxDelay, isPrimary, returnResults), aero.nettracer.fs.model.transport.v2.detection.TraceResponse.class);
	}

	@Override
	public TraceResponse getFileMatches(long fileId) {
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		return mapper.map(bean.getFileMatches(fileId), aero.nettracer.fs.model.transport.v2.detection.TraceResponse.class);
	}

	@Override
	public int getIncidentCacheSize() {
		return bean.getIncidentCacheSize();
	}

	@Override
	public int getClaimCacheSize() {
		return bean.getClaimCacheSize();
	}

	@Override
	public void requestAccess(long fileId, long matchId, String agent,
				String requestingAirline, String message) {
		bean.requestAccess(fileId, matchId, agent, requestingAirline, message);
	}
	/*
		bean.requestAccess(fileId, matchId, agent, requestingAirline, message, "");
	}

	@Override
	public void requestAccess(long fileId, long matchId, String agent,
				String requestingAirline, String message, String contact) {
		bean.requestAccess(fileId, matchId, agent, requestingAirline, message, contact);
	}*/

	@Override
	public List<AccessRequest> getOutstandingRequests(String airlineId,
			int begin, int perPage) {
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		List<aero.nettracer.fs.model.detection.AccessRequest> fslist = bean.getOutstandingRequests(airlineId, begin, perPage);
		ArrayList<AccessRequest> ntlist = null;
		if(fslist != null){
			ntlist = new ArrayList<AccessRequest>();
			for(aero.nettracer.fs.model.detection.AccessRequest fs:fslist){
				ntlist.add(mapper.map(fs, aero.nettracer.fs.model.transport.v2.detection.AccessRequest.class));
			}
		} 
		return ntlist;
	}

	@Override
	public int getOutstandingRequetsCount(String airlineId) {
		return bean.getOutstandingRequetsCount(airlineId);
	}

	@Override
	public void approveRequest(long requestId, String message, String agent) {
		bean.approveRequest(requestId, message, agent);
	}

	@Override
	public void denyRequest(long requestId, String message, String agent) {
		bean.denyRequest(requestId, message, agent);
	}

	@Override
	public File getFile(long fileId, String airline) {
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		return mapper.map(bean.getFile(fileId, airline), aero.nettracer.fs.model.transport.v2.File.class);
	}

	@Override
	public boolean deleteMatch(long matchId) {
		return bean.deleteMatch(matchId);
	}

	@Override
	public boolean deleteMatch(Set<Long> matchIds) {
		return bean.deleteMatch(matchIds);
	}
	
	@Override
	public int getAccessRequestsCount(AccessRequestDTO dto){
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		return bean.getAccessRequestsCount(mapper.map((aero.nettracer.fs.model.transport.v2.detection.AccessRequestDTO)dto, aero.nettracer.fs.model.detection.AccessRequestDTO.class));
	}

	@Override
	public List<AccessRequest> getAccessRequests(AccessRequestDTO dto,int begin, int perPage) {
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		List<aero.nettracer.fs.model.detection.AccessRequest> fslist = bean.getAccessRequests(mapper.map((aero.nettracer.fs.model.transport.v2.detection.AccessRequestDTO)dto, aero.nettracer.fs.model.detection.AccessRequestDTO.class),begin,perPage);
		ArrayList<AccessRequest> ntlist = null;
		if(fslist != null){
			ntlist = new ArrayList<AccessRequest>();
			for(aero.nettracer.fs.model.detection.AccessRequest fs:fslist){
				ntlist.add(mapper.map(fs, aero.nettracer.fs.model.transport.v2.detection.AccessRequest.class));
			}
		} 
		return ntlist;
	}

	@Override
	public Map<String, Integer> getMatches(List<String> idList) {
		return bean.getMatches(idList);
	}
	
	@Override
	public Map<String, Integer> getMatches(List<String> idList, String companycode) {
		return bean.getMatches(idList, companycode);
	}
	
}
