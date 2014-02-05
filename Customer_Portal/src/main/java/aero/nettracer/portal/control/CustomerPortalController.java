package aero.nettracer.portal.control;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpSession;

import org.apache.axis2.AxisFault;
import org.apache.log4j.Logger;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import aero.nettracer.portal.faces.util.CaptchaBean;
import aero.nettracer.portal.faces.util.FacesUtil;
import aero.nettracer.portal.faces.util.File;
import aero.nettracer.portal.faces.util.FileHelper;
import aero.nettracer.portal.faces.util.FileUploadBean;
import aero.nettracer.portal.model.FileAndMessage;
import aero.nettracer.portal.model.LoginBean;
import aero.nettracer.portal.model.Message;
import aero.nettracer.portal.model.PassengerBean;
import aero.nettracer.portal.webservices.client.OnlineClaimsWS;

import com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident;
import com.bagnet.nettracer.ws.onlineclaims.xsd.Claim;

@Component("customerPortal")
@Qualifier("customerPortal")
@Scope("request")
public class CustomerPortalController {

	private static Logger logger = Logger.getLogger(CustomerPortalController.class);
	
	private static long MAX_SIZE = 5 * 1024 * 1024; // 5MB

	CaptchaBean captchaBean = new CaptchaBean();
	LoginBean loginBean = new LoginBean();
	private PassengerBean passengerBean;
	private Long baggageState;
	private FileUploadBean fileUploadBean;
	private int uploadsAvailable = 10;
	// ------------------- File Upload Myfaces
	private UploadedFile upFile;
	boolean rendSuccess = false;
	boolean rendFailure = false;
	private DataModel<File> fileDataModelList;
	private List<FileAndMessage> previousObjects;
	// -- End File Upload
	
	@Autowired
	OnlineClaimsWS onlineClaimsWS;

	private static long fileId = 1L;

	@PostConstruct
	public void post() {
		HttpSession session = (HttpSession) FacesUtil.getFacesContext().getExternalContext().getSession(false);

		if (null != session && null != session.getAttribute("loggedPassenger")) {
			baggageState = (Long) session.getAttribute("baggageState");
			passengerBean = (PassengerBean) session.getAttribute("passengerBean");
			if (null == passengerBean.getNewFiles()) {
				passengerBean.setNewFiles(new ArrayList<File>());
			}
			fileDataModelList = new ListDataModel<File>(passengerBean.getNewFiles());
			previousObjects = generatePreviousList();
		} else {
			FacesUtil.addError("Your session has been expired. Please log in again");
			FacesContext context = FacesContext.getCurrentInstance();
			context.getApplication().getNavigationHandler().handleNavigation(context, null, "passengerLogout");
		}
	}
	
	private List<FileAndMessage> generatePreviousList() {
		List<FileAndMessage> toReturn = new ArrayList<FileAndMessage>();
		if (passengerBean != null) {
			if (passengerBean.getMessages() != null) {
				for (Message message : passengerBean.getMessages()) {
					FileAndMessage temp = new FileAndMessage();
					temp.setColumn1(message.getMessage());
					temp.setMessage(true);
					temp.setDateCreated(message.getDateCreated());
					temp.setUsername(getUserString(message.getUsername()));
					temp.setPublish(message.isPublish());
					toReturn.add(temp);
				}
			}
			if (passengerBean.getFiles() != null) {
				for (File file : passengerBean.getFiles()) {
					FileAndMessage temp = new FileAndMessage();
					temp.setColumn1(file.getName());
					temp.setMessage(false);
					temp.setDateCreated(file.getDateUploaded());
					temp.setUsername(file.getStatus() == 3 ? "From You at " : "From Southwest at ");
					temp.setPublish(file.isPublish());
					temp.setPath(file.getPath());
					temp.setStatusId(file.getStatus());
					toReturn.add(temp);
				}
			}
			Collections.sort(toReturn);
		}
		return toReturn;
	}
	
	private String getUserString(String username) {
		if (username == null) {
			return "";
		}
		if (username.equals(Message.portalUser)) {
			return "From You at ";
		} else {
			return "From Southwest at ";
		}
	}

	/**
	 * 
	 * File Upload Logic
	 * 
	 * @throws IOException
	 */
	public String upload() throws IOException {
		logger.info("upload called");
		try {
			if (upFile != null) {
				int extDot = upFile.getName().lastIndexOf('.');
				if (extDot > 0) {
					String extension = upFile.getName().substring(extDot + 1);
					if (extension.equals("jpg") || extension.equals("gif")
							|| extension.equals("pdf")
							|| extension.equals("jpeg")
							|| extension.equals("doc")
							|| extension.equals("docx")
							|| extension.equals("tiff")
							|| extension.equalsIgnoreCase("png")) {
						if (MAX_SIZE >= upFile.getSize()) {
							String fileName = upFile.getName().contains("\\") ? upFile
									.getName().substring(
											upFile.getName().lastIndexOf("\\") + 1,
											upFile.getName().length()) : upFile
									.getName(); // Fixing the bugs in IE
							File file = new File();
							byte[] data = upFile.getBytes();
							file.setName(fileName);
							file.setLength(upFile.getSize());
							file.setData(data);
							// file.setId(fileId++);
							if (passengerBean.getNewFiles() != null) {
								List<File> existingFiles = passengerBean.getNewFiles();
								for (File existingFile : existingFiles) {
									if (fileName.equals(existingFile.getName())) {
										logger.warn("File is already existing");
										FacesUtil
												.addError("File is already existing");
										return null; //"no";
									}
								}
								FileHelper.saveImage(passengerBean.getIncidentID(),
										fileName, data);
								file.setPath(FileHelper.getPath());
						        file.setPublish(true);
						        file.setDateUploaded(Calendar.getInstance());
								passengerBean.getNewFiles().add(file);
								fileDataModelList = new ListDataModel<File>(
										passengerBean.getNewFiles());
								logger.info("File Uploaded Successfully.");
							}
							file = null;
							return null; //"ok";
						} else {
							logger.error("File is too large.");
							FacesUtil.addError("File is too large. Please use files less than 5MB in size.");
						}
					} else {
						logger.error("File type not supported.");
						FacesUtil.addError("File type not supported. Supported types are: jpg, jpeg, gif, tiff, png, pdf, doc, docx.");
					}
				}

			}
			return null; //"no";
		} catch (Exception ioe) {
			logger.info("File Upload Unsuccessful.");
			ioe.printStackTrace();
			rendSuccess = false;
			rendFailure = true;
			return null; //"no";
		}
	}

	/**
	 * 
	 * Remove a File from the main file list
	 * 
	 */
	public void removeFileListener(ActionEvent event) {
		logger.info("removeFileListener called");
		File file = (File) fileDataModelList.getRowData();
		List<File> files = passengerBean.getNewFiles();
		int fileSize = files.size();
		try {
			FileHelper.deleteImage(passengerBean.getIncidentID(), file.getName(), file.getPath());
			for (int i = fileSize - 1; i >= 0; i--) {
				File f = files.get(i);
				if (f.getName().equals(file.getName())) {
					passengerBean.getNewFiles().remove(i);
				}
			}
			fileDataModelList = null;
			fileDataModelList = new ListDataModel<File>(passengerBean.getNewFiles());
		} catch (IOException e) {
			logger.info("File can not be deleted");
			e.printStackTrace();
		}

	}

	// -------------------End of File Upload
	
	public String sendToNT() {
		logger.info("gotoFraudQuestion method is called");

		HttpSession session = (HttpSession) FacesUtil.getFacesContext().getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("loggedPassenger")) {
			return saveToNT(session);
		} else {
			FacesUtil.addError("Your session has been expired. Please log in again");
			return "passengerLogout";
		}
	}
	

	public String saveToNT(HttpSession session) {
		logger.debug("saveToNT method is called");
		if (passengerBean.getCurrentMessage() == null || passengerBean.getCurrentMessage().trim().length() == 0) {
			FacesUtil.addError("Message is required to send data to Southwest.");
			return null;
		}
		
			try {
				
				boolean saveFile = onlineClaimsWS.sendToNT(passengerBean, (Claim) session.getAttribute("claim"));
				if (saveFile) {
					FacesUtil.addInfo("File infomation saved successfully.");
					logger.info("File infomation saved successfully.");
				} else {
					logger.error("Error in persisting the Data");
					FacesUtil.addError("Error in persisting the Data");
				}

				WSPVAdvancedIncident passData = getPassengerData();
				Claim claim=onlineClaimsWS.getClaim(passData,passengerBean.getPassengers().get(0).getLastName(), passengerBean.getPassengers().get(0).getFirstName());
				passengerBean = onlineClaimsWS.getPassengerData(passData,claim);
				passengerBean.setPassengerData(passData);
				passengerBean.setIncidentID(passData.getIncidentID());
				session.setAttribute("claim", claim);
				session.setAttribute("passengerBean", passengerBean);
				if (null == passengerBean.getNewFiles()) {
					passengerBean.setNewFiles(new ArrayList<File>());
				}
				fileDataModelList = new ListDataModel<File>(passengerBean.getNewFiles());
				previousObjects = generatePreviousList();
				
				return null;
			} catch (AxisFault e) {
				e.printStackTrace();
				FacesUtil.addError("Error in persisting the Data");
				return null;
			} catch (RemoteException e) {
				e.printStackTrace();
				FacesUtil.addError("Connection failure, Please try again");
				return null;
			}
	}

	public CaptchaBean getCaptchaBean() {
		return captchaBean;
	}

	public void setCaptchaBean(CaptchaBean captchaBean) {
		this.captchaBean = captchaBean;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public PassengerBean getPassengerBean() {
		return passengerBean;
	}

	public void setPassengerBean(PassengerBean passengerBean) {
		this.passengerBean = passengerBean;
	}

	public Long getBaggageState() {
		return baggageState;
	}

	public void setBaggageState(Long baggageState) {
		this.baggageState = baggageState;
	}

	public OnlineClaimsWS getOnlineClaimsWS() {
		return onlineClaimsWS;
	}

	public void setOnlineClaimsWS(OnlineClaimsWS onlineClaimsWS) {
		this.onlineClaimsWS = onlineClaimsWS;
	}
	
	public FileUploadBean getFileUploadBean() {
		return fileUploadBean;
	}

	public void setFileUploadBean(FileUploadBean fileUploadBean) {
		this.fileUploadBean = fileUploadBean;
	}

	public int getUploadsAvailable() {
		return uploadsAvailable;
	}

	public void setUploadsAvailable(int uploadsAvailable) {
		this.uploadsAvailable = uploadsAvailable;
	}

	public UploadedFile getUpFile() {
		return upFile;
	}

	public void setUpFile(UploadedFile upFile) {
		this.upFile = upFile;
	}

	public DataModel<File> getFileDataModelList() {
		return fileDataModelList;
	}

	public void setFileDataModelList(DataModel<File> fileDataModelList) {
		this.fileDataModelList = fileDataModelList;
	}

	public static long getFileId() {
		return fileId;
	}

	public boolean isRendSuccess() {
		return rendSuccess;
	}

	public void setRendSuccess(boolean rendSuccess) {
		this.rendSuccess = rendSuccess;
	}

	public boolean isRendFailure() {
		return rendFailure;
	}

	public void setRendFailure(boolean rendFailure) {
		this.rendFailure = rendFailure;
	}

	public List<FileAndMessage> getPreviousObjects() {
		return previousObjects;
	}

	public void setPreviousObjects(List<FileAndMessage> previousObjects) {
		this.previousObjects = previousObjects;
	}

	public static void setFileId(long fileId) {
		CustomerPortalController.fileId = fileId;
	}

	public WSPVAdvancedIncident getPassengerData() {
		if (passengerBean != null && passengerBean.getPassengerData() != null) {
			return passengerBean.getPassengerData();
		}
		return null;
	}
}
