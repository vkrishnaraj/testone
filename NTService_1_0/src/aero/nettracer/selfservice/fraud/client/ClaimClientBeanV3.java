package aero.nettracer.selfservice.fraud.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import aero.nettracer.fs.model.transport.v0.File;
import aero.nettracer.fs.model.transport.v0.detection.AccessRequest;
import aero.nettracer.fs.model.transport.v0.detection.AccessRequestDTO;
import aero.nettracer.fs.model.transport.v0.detection.TraceResponse;
import aero.nettracer.fs.utilities.FileUtils;
import aero.nettracer.selfservice.fraud.ClaimBean;

import com.healthmarketscience.rmiio.RemoteInputStream;


@Stateless
public class ClaimClientBeanV3 implements ClaimClientRemoteV3{

	ClaimBean bean = new ClaimBean();
	
	@Override
	public String echoTest(String s) {
		return bean.echoTest("V3"+s);
	}

	@Override
	public long insertFile(File file) {
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		return bean.insertFile(mapper.map((aero.nettracer.fs.model.transport.v3.File)file, aero.nettracer.fs.model.File.class));
	}

	@Override
	public TraceResponse traceFile(long fileId, int maxDelay,
			boolean isPrimary, boolean returnResults) {
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		return mapper.map(bean.traceFile(fileId, maxDelay, isPrimary, returnResults), aero.nettracer.fs.model.transport.v3.detection.TraceResponse.class);
	}

	@Override
	public TraceResponse getFileMatches(long fileId) {
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		return mapper.map(bean.getFileMatches(fileId), aero.nettracer.fs.model.transport.v3.detection.TraceResponse.class);
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
				ntlist.add(mapper.map(fs, aero.nettracer.fs.model.transport.v3.detection.AccessRequest.class));
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
//		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
//		return mapper.map(bean.getFile(fileId, airline), aero.nettracer.fs.model.transport.v2.File.class);
		
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance(); 
		aero.nettracer.fs.model.transport.v3.File file=mapper.map(bean.getFile(fileId, airline), aero.nettracer.fs.model.transport.v3.File.class);
		
		for(aero.nettracer.fs.model.transport.v3.FsClaim claim:file.getClaims())
		{
			List<aero.nettracer.fs.model.FsAttachment> attachList = FileUtils.getAttachmentsByClaimId(claim.getId(), airline);
			Set<aero.nettracer.fs.model.transport.v3.Attachment> returnList = null;
			if(attachList != null){
				returnList = new HashSet();
				for(aero.nettracer.fs.model.FsAttachment fs:attachList){
					if(fs.getClaim_id()==claim.getId())	{
						aero.nettracer.fs.model.transport.v3.Attachment attach=new aero.nettracer.fs.model.transport.v3.Attachment();
						attach.setAttachment_id(fs.getId());
						attach.setDescription(fs.getDescription());
						returnList.add(mapper.map(attach, aero.nettracer.fs.model.transport.v3.Attachment.class));
					}
				}
				claim.setAttachments(returnList);
			}
		}
		return file;
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
		return bean.getAccessRequestsCount(mapper.map((aero.nettracer.fs.model.transport.v3.detection.AccessRequestDTO)dto, aero.nettracer.fs.model.detection.AccessRequestDTO.class));
	}

	@Override
	public List<AccessRequest> getAccessRequests(AccessRequestDTO dto,int begin, int perPage) {
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		List<aero.nettracer.fs.model.detection.AccessRequest> fslist = bean.getAccessRequests(mapper.map((aero.nettracer.fs.model.transport.v3.detection.AccessRequestDTO)dto, aero.nettracer.fs.model.detection.AccessRequestDTO.class),begin,perPage);
		ArrayList<AccessRequest> ntlist = null;
		if(fslist != null){
			ntlist = new ArrayList<AccessRequest>();
			for(aero.nettracer.fs.model.detection.AccessRequest fs:fslist){
				ntlist.add(mapper.map(fs, aero.nettracer.fs.model.transport.v3.detection.AccessRequest.class));
			}
		} 
		return ntlist;
	}

	@Override
	public Map<String, Integer> getMatches(List<String> idList) {
		return bean.getMatches(idList);
	}
	
	@Override
	public int uploadAttachment(java.io.File theFile, int maxSize,String folder,String picpath, long fileid,String airline,long claimid, int filesize, RemoteInputStream ris) {
		return bean.uploadAttachment(theFile, maxSize, folder, picpath, fileid, airline, claimid,filesize, ris);
		//return "";
	}
	
	@Override
	public Object[] getAttachment(int attachID, String airline) {
		return FileUtils.getAttachment(attachID, airline); 
		//return "";
	}
	
//	@Override
//	public File getFileWithAttachments(long fileId, String airline) {
//		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance(); 
//		aero.nettracer.fs.model.transport.v3.File file=mapper.map(bean.getFile(fileId, airline), aero.nettracer.fs.model.transport.v3.File.class);
//		
//		for(aero.nettracer.fs.model.transport.v3.FsClaim claim:file.getClaims())
//		{
//			List<aero.nettracer.fs.model.FsAttachment> attachList = bean.getAttachmentsByClaimId(claim.getId(),fileId,airline);
//			Set<aero.nettracer.fs.model.transport.v3.Attachment> returnList = null;
//			if(attachList != null){
//				returnList = new HashSet();
//				for(aero.nettracer.fs.model.FsAttachment fs:attachList){
//					if(fs.getClaim_id()==claim.getId())	{
//						aero.nettracer.fs.model.transport.v3.Attachment attach=new aero.nettracer.fs.model.transport.v3.Attachment();
//						attach.setAttachment_id(fs.getId());
//						attach.setDescription(fs.getdescription());
//						returnList.add(mapper.map(attach, aero.nettracer.fs.model.transport.v3.Attachment.class));
//					}
//				}
//				claim.setAttachments(returnList);
//			}
//		}
//		return file;
//	}
	
	@Override
	public boolean saveAttachments(List<Integer> attachIDs, long fileid, String airline, long claimId) {
		return bean.saveAttachments(attachIDs, fileid, airline, claimId); 
		//return "";
	}
	
	
	@Override
	public Map<String, Integer> getMatches(List<String> idList, String companycode) {
		return bean.getMatches(idList, companycode);
	}

	@Override
	public void requestAccess(long fileId, long matchId, String agent, String requestingAirline, String message, String contactName, String contactEmail, String contactPhone) {
		bean.requestAccess(fileId, matchId, agent, requestingAirline, message, contactName, contactEmail, contactPhone);
	}
	

	@Override
	public boolean deleteAttachment(int attachID) { 
			return FileUtils.deleteAttachment(attachID);
	}
	
}
