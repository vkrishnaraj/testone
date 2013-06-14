package aero.nettracer.fs.utilities;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import aero.nettracer.fs.model.transport.v3.File;
import aero.nettracer.fs.model.transport.v3.FsClaim;
import aero.nettracer.fs.model.transport.v3.detection.AccessRequestDTO;
import aero.nettracer.fs.model.transport.v3.forum.FsForumSearch;
import aero.nettracer.fs.model.transport.v3.forum.FsForumTag;
import aero.nettracer.fs.model.transport.v3.forum.FsForumThread;
import aero.nettracer.fs.model.transport.v3.forum.FsForumThreadInfo;
import aero.nettracer.fs.model.transport.v3.FsAttachment;

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
	
	public static File map(aero.nettracer.fs.model.File ntfile){
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		File fsfile = mapper.map(ntfile, File.class);
		return fsfile;
	}
	
	public static aero.nettracer.fs.model.File map(aero.nettracer.fs.model.transport.v0.File fsfile){
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		return mapper.map(fsfile, aero.nettracer.fs.model.File.class);
	}
	
	public static AccessRequestDTO map(aero.nettracer.fs.model.detection.AccessRequestDTO dto){
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		return mapper.map(dto, AccessRequestDTO.class);
	}

	public static FsForumThread map(aero.nettracer.fs.model.forum.FsForumThread fsft) {
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		return mapper.map(fsft, FsForumThread.class);
	}
	
	public static aero.nettracer.fs.model.forum.FsForumThread map(aero.nettracer.fs.model.transport.v0.forum.FsForumThread fsft) {
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		return mapper.map(fsft, aero.nettracer.fs.model.forum.FsForumThread.class);
	}
	
	public static List<aero.nettracer.fs.model.forum.FsForumTag> mapTags(List<FsForumTag> fsfts) {
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		ArrayList<aero.nettracer.fs.model.forum.FsForumTag> ntft = null;
		if(fsfts != null){
			ntft = new ArrayList<aero.nettracer.fs.model.forum.FsForumTag>();
			for(FsForumTag ft:fsfts){
				ntft.add(mapper.map(ft, aero.nettracer.fs.model.forum.FsForumTag.class));
			}
		}
		return ntft;
	}
	
	public static List<aero.nettracer.fs.model.forum.FsForumThreadInfo> mapThreads(List<FsForumThreadInfo> fsts) {
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		ArrayList<aero.nettracer.fs.model.forum.FsForumThreadInfo> ntti = null;
		if(fsts != null){
			ntti = new ArrayList<aero.nettracer.fs.model.forum.FsForumThreadInfo>();
			for(FsForumThreadInfo ti:fsts){
				ntti.add(mapper.map(ti, aero.nettracer.fs.model.forum.FsForumThreadInfo.class));
			}
		}
		return ntti;
	}
	
	public static FsForumSearch map(aero.nettracer.fs.model.forum.FsForumSearch fsfs) {
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		return mapper.map(fsfs, FsForumSearch.class);
	}
	
	public static aero.nettracer.fs.model.FsAttachment map(aero.nettracer.fs.model.transport.v0.FsAttachment fsat) {
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		return mapper.map((FsAttachment) fsat, aero.nettracer.fs.model.FsAttachment.class);
	}
	
	public static FsAttachment map(aero.nettracer.fs.model.FsAttachment fsat) {
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		return mapper.map(fsat, FsAttachment.class);
	}
	
	public static aero.nettracer.fs.model.FsClaim map(aero.nettracer.fs.model.transport.v0.FsClaim fscl) {
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		return mapper.map((FsClaim) fscl, aero.nettracer.fs.model.FsClaim.class);
	}

}
