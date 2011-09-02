package com.bagnet.nettracer.cronjob;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import aero.nettracer.lf.services.LFServiceBean;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.db.lf.LFPhone;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchDetail;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory;
import com.bagnet.nettracer.tracing.dto.LFSearchDTO;

public class EncryptData {
	
	private static List<LFMatchHistory> getMatchHistories(){
		String sql = "from com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory mh ";
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
			
			List<LFMatchHistory> results = q.list();
			sess.close();
			return results;
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(sess != null && sess.isOpen()){
				sess.close();
			}
		}
		return null;
	}
	
	
	private static boolean processLost(LFLost lost, LFServiceBean bean){
		try {
			if(lost != null && lost.getClient() != null){
//				if(lost.getClient().getId() == 159){
//					System.out.println("break point");
//				}
				if(lost.getClient().getAddress() != null){
					lost.getClient().getAddress().setDecryptedAddress1(lost.getClient().getAddress().getAddress1());
					lost.getClient().getAddress().setDecryptedAddress2(lost.getClient().getAddress().getAddress2());
					lost.getClient().getAddress().setDecryptedCity(lost.getClient().getAddress().getCity());
					lost.getClient().getAddress().setDecryptedState(lost.getClient().getAddress().getState());
					lost.getClient().getAddress().setDecryptedProvince(lost.getClient().getAddress().getProvince());
					lost.getClient().getAddress().setDecryptedZip(lost.getClient().getAddress().getZip());
				}
				lost.getClient().setDecryptedEmail(lost.getClient().getEmail());

				if(lost.getClient().getPhones() != null){
					for(LFPhone phone:lost.getClient().getPhones()){
						if(phone != null){
							phone.setDecryptedPhoneNumber(phone.getPhoneNumber());
						}
					}
				}
			}
//			System.out.println(lost.getId());
			
			if(bean.saveOrUpdateLostReport(lost,null) == -1){
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if(lost!=null)System.out.println(lost.getId());
			e.printStackTrace();
			return false;
		}
	}
	
	private static boolean processFound(LFFound found, LFServiceBean bean){
		try {
			if(found != null && found.getClient() != null){
				if(found.getClient().getAddress() != null){
					found.getClient().getAddress().setDecryptedAddress1(found.getClient().getAddress().getAddress1());
					found.getClient().getAddress().setDecryptedAddress2(found.getClient().getAddress().getAddress2());
					found.getClient().getAddress().setDecryptedCity(found.getClient().getAddress().getCity());
					found.getClient().getAddress().setDecryptedState(found.getClient().getAddress().getState());
					found.getClient().getAddress().setDecryptedProvince(found.getClient().getAddress().getProvince());
					found.getClient().getAddress().setDecryptedZip(found.getClient().getAddress().getZip());
				}
				found.getClient().setDecryptedEmail(found.getClient().getEmail());

				if(found.getClient().getPhones() != null){
					for(LFPhone phone:found.getClient().getPhones()){
						if(phone != null){
							phone.setDecryptedPhoneNumber(phone.getPhoneNumber());
						}
					}
				}
			}
			if(bean.saveOrUpdateFoundItem(found, null) == -1){
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if(found!=null)System.out.println(found.getId());
			e.printStackTrace();
			return false;
		}
	}
	
	private static boolean processHistory(LFMatchHistory h, LFServiceBean bean){
		try{
			if(h != null && h.getDetails() != null){
				for(LFMatchDetail detail:h.getDetails()){
					if(detail != null){
						detail.setDecryptedFoundValue(detail.getFoundValue());
						detail.setDecryptedLostValue(detail.getLostValue());
					}
				}
			}
			if(bean.saveOrUpdateTraceResult(h) == -1){
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if(h!=null)System.out.println(h.getId());
			e.printStackTrace();
			return false;
		}
	}
	
	public static void main(String [] args){
		List<LFLost> losts = null;
		List<LFFound> founds = null;
		List<LFMatchHistory> histories = null;
		
		try{
		LFServiceBean bean = new LFServiceBean();
		LFSearchDTO dto = new LFSearchDTO();
		
		dto.setType(TracingConstants.LF_TYPE_LOST);
		losts = bean.searchLost(dto, 0, Integer.MAX_VALUE);
		for(LFLost lost:losts){
			if(EncryptData.processLost(lost, bean)){
				lost.setId(-1);
			}
		}
		
		dto.setType(TracingConstants.LF_TYPE_FOUND);
		founds = bean.searchFound(dto, 0, Integer.MAX_VALUE);
		for(LFFound found:founds){
			if(EncryptData.processFound(found, bean)){
				found.setId(-1);
			}
		}
		
		histories = getMatchHistories();
		for(LFMatchHistory history:histories){
			if(EncryptData.processHistory(history, bean)){
				history.setId(-1);
			}
		}
		
		} catch (Exception e){
			e.printStackTrace();
		} finally{
			if(losts != null){
				System.out.println("failed losts");
				for(LFLost lost:losts){
					if(lost.getId() > -1){
						System.out.println(lost.getId());
					}
				}
			}
			if(founds != null){
				System.out.println("failed founds");
				for(LFFound found: founds){
					if(found.getId() > -1){
						System.out.println(found.getId());
					}
				}
			}
			if(histories != null){
				System.out.println("failed histories");
				for(LFMatchHistory history: histories){
					if(history.getId() > -1){
						System.out.println(history.getId());
					}
				}
			}
		}
	}
}
