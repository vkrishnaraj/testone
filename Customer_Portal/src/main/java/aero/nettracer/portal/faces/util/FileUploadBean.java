/**
 * 
 */
package aero.nettracer.portal.faces.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

import aero.nettracer.portal.model.PassengerBean;


public class FileUploadBean{
	private static Logger logger = Logger.getLogger(FileUploadBean.class);
	
    private ArrayList<File> files = new ArrayList<File>();
    private int uploadsAvailable = 10;
    private boolean autoUpload = false;
    private boolean useFlash = false;
    public int getSize() {
        if (getFiles().size()>0){
            return getFiles().size();
        }else 
        {
            return 0;
        }
    }

    public FileUploadBean() {
    }

    public void paint(OutputStream stream, Object object) throws IOException {
        stream.write(getFiles().get((Integer)object).getData());
    }
    public void listener(UploadEvent event) throws Exception{
    	logger.info("File Upload Listener called");
    	HttpSession session = (HttpSession) FacesUtil.getFacesContext()
		.getExternalContext().getSession(false);
    	PassengerBean passengerBean = (PassengerBean) session.getAttribute("passengerBean");
        UploadItem item = event.getUploadItem();
        File file = new File();
        file.setLength(new Long(item.getData().length));
        file.setName(item.getFileName());
        file.setData(item.getData());
        files.add(file);
        passengerBean.setFiles(files);
        session.setAttribute("passengerBean",passengerBean);
        uploadsAvailable--;
    }  
      
    public String clearUploadData() {
        files.clear();
        setUploadsAvailable(10);
        return null;
    }
    
    public long getTimeStamp(){
        return System.currentTimeMillis();
    }
    
    public ArrayList<File> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<File> files) { 
        this.files = files;
    }

    public int getUploadsAvailable() {
        return uploadsAvailable;
    }

    public void setUploadsAvailable(int uploadsAvailable) {
        this.uploadsAvailable = uploadsAvailable;
    }

    public boolean isAutoUpload() {
        return autoUpload;
    }

    public void setAutoUpload(boolean autoUpload) {
        this.autoUpload = autoUpload;
    }

    public boolean isUseFlash() {
        return useFlash;
    }

    public void setUseFlash(boolean useFlash) {
        this.useFlash = useFlash;
    }

}