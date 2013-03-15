package com.bagnet.nettracer.cronjob.datafeed;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bagnet.nettracer.cronjob.bmo.DataFeedLogBmo;
import com.bagnet.nettracer.cronjob.bmo.FlightInfoBmo;
import com.bagnet.nettracer.tracing.db.datafeed.DataFeedLog;
import com.bagnet.nettracer.tracing.db.datafeed.FlightInfo;
import com.bagnet.nettracer.tracing.db.datafeed.DataFeedLog.DataType;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;

import edu.emory.mathcs.backport.java.util.Collections;

public class FlightInfoUpdate {

    private SessionFactory sessionFactory;
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public void setSessionFactory(SessionFactory sessionFactory) {
         this.sessionFactory = sessionFactory;
    }

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public Pattern REPORT_FILE_NAME_PATTERN;

	public static final SimpleDateFormat FILE_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd-HHmm");

	private static final Logger logger = Logger.getLogger(FlightInfoUpdate.class);

	private static final int BATCH_SIZE = 100;

	private FlightInfoBmo fiBmo;
	private String reportFileLocation;
	private String backupLocation;
	private DataFeedLogBmo dflBmo;
	private String companyCode;

	private enum CsvItems {
		FLIGHT_NUM, DEPART_DATE, ARRIVE_DATE, DEPART_CITY, ARRIVE_CITY, TOTAL_PAX, CONN_PAX,
	}

	@Transactional
	public long updateFlightInfo() throws IOException {

		DataFeedLog log = new DataFeedLog();
		log.setDataType(DataType.FLIGHT_INFO);

		log.setImportDate(TracerDateTime.getGMTDate());

		log.setCompanyCode(companyCode);

		Session sess = null;

		long i = 0;
		ZipInputStream zin = null;
		try {
			sess = getSessionFactory().openSession();

			File dataFile = processDataFiles();

			if(dataFile == null) {
				logger.info("No files found to process");
				return -1;
			}

			log.setFileName(dataFile.getName());

			Date fileDate = parseFileDate(dataFile.getName());
			log.setFileDate(fileDate);

			zin = new ZipInputStream(new FileInputStream(dataFile));
			if(zin == null) {
				logger.error("unable to open zip file for " + dataFile.getName());
				return -1;
			}
			ZipEntry entry;
			if((entry = zin.getNextEntry()) != null) {
				Scanner in = new Scanner(zin);

				logger.info(String.format("Reading data from zip: %s and zip-entry: %s", dataFile.getName(), entry
						.getName()));

				String fiDelete = "delete FlightInfo";
				int deleted = sess.createQuery(fiDelete).executeUpdate();
				logger.info("Deleted rows: " + deleted);
				sess.flush();
				sess.clear();

				while (in.hasNextLine()) {
					String line = in.nextLine();
					FlightInfo fi = null;
					try {
						fi = buildFlightInfo(line);
					}
					catch (ParseException e) {
						logger.warn("Unable to parse date for: " + line, e);
					}
					if(fi != null) {
						sess.save(fi);
						i++;
					}
					if(i % BATCH_SIZE == 0) {
						sess.flush();
						sess.clear();
					}
				}
			}
			log.setNumRows(i);
			log.setResult(DataFeedLog.SUCCESS);

		}
		finally {
			try {
				if(zin != null) {
					zin.close();
				}
				if(log.getResult() == null) {
					log.setResult(DataFeedLog.FAIL);
				}

				if(sess != null)
					sess.save(log);
				else
					logger.error("Unable to save DataFeed log entry, no database session");
			}
			catch (Throwable t) {
				logger.error("Unable to save DataFeed log entry");
			} finally {
				if (sess != null) {
					sess.close();
				}
			}
		}
		return i;
	}

	private Date parseFileDate(String fileName) {
		Matcher m = REPORT_FILE_NAME_PATTERN.matcher(fileName);
		Date fileDate = null;
		if(m.find()) {
			try {
				fileDate = FILE_DATE_FORMAT.parse(m.group(1));
			}
			catch (ParseException e) {
				logger.warn("unable to parse date from filename: " + fileName);
			}
		}
		return fileDate;
	}

	/**
	 * This should take a line from a csv file and build a flight info object..
	 * example line: WS0011,2008-09-02 07:30,2008-09-02 08:02,YEG,YVR,93,0
	 * 
	 * @param line
	 *            - string from csv file representing a flight info
	 * @return
	 * @throws ParseException
	 */
	private FlightInfo buildFlightInfo(String line) throws ParseException {

		String[] pieces = line.trim().split(",");
		
		for(int i = 0; i < pieces.length; i++) {
			pieces[i] = pieces[i].replaceAll("(^\")|(\"$)", "");
		}
		if(pieces.length != CsvItems.values().length) {
			logger.warn("invalid number of items in csv line: " + line);
			return null;
		}
		
		

		String flightNum = pieces[CsvItems.FLIGHT_NUM.ordinal()];
		Date departDate = DATE_FORMAT.parse(pieces[CsvItems.DEPART_DATE.ordinal()]);
		Date arrivalDate = DATE_FORMAT.parse(pieces[CsvItems.ARRIVE_DATE.ordinal()]);
		String departCity = pieces[CsvItems.DEPART_CITY.ordinal()];
		String arrivalCity = pieces[CsvItems.ARRIVE_CITY.ordinal()];
		int totalPax = Integer.parseInt(pieces[CsvItems.TOTAL_PAX.ordinal()]);
		int connPax = Integer.parseInt(pieces[CsvItems.CONN_PAX.ordinal()]);

		FlightInfo fi = new FlightInfo();

		fi.setArrivalCity(arrivalCity);
		fi.setArrivalDate(arrivalDate);
		fi.setTotalPax(totalPax);
		fi.setConnPax(connPax);
		fi.setDepartureCity(departCity);
		fi.setDepartureDate(departDate);
		fi.setFlightNum(flightNum);

		return fi;

	}

	private File processDataFiles() throws IOException {
		File f = new File(reportFileLocation);
		if(!f.isDirectory() || !f.canRead()) {
			throw new IOException("Can't open directory: " + reportFileLocation);
		}

		List<String> dataFiles = new ArrayList<String>(Arrays.asList(f.list(new FlightInfoFileFilter())));

		if(dataFiles.size() <= 0) {
			return null;
		}

		Collections.sort(dataFiles);
		Collections.reverse(dataFiles);

		// process the newest one

		Date fileDate = parseFileDate(dataFiles.get(0));
		if(dflBmo.isAlreadyProcessedByType(fileDate, DataType.FLIGHT_INFO, companyCode)) {
			// TODO log that no files were processed
			logger.warn("Found file " + dataFiles.get(0)
					+ " but we have already processed a file with the same or newer date.");
			deleteFiles(dataFiles);
			return null;
		}
		else {
			String newestFile = dataFiles.remove(0);
			deleteFiles(dataFiles);
			File src = new File(reportFileLocation, newestFile);
			File dest = new File(backupLocation, newestFile);
			if(dest.exists()) {
				dest.delete();
			}
			boolean success = src.renameTo(dest);
			if(!success) {
				logger.error("Unable to move target file to archive directory: " + newestFile);
				return null;
			}
			return dest;
		}

	}

	private void deleteFiles(List<String> dataFiles) {
		boolean success = true;
		for(String fName : dataFiles) {
			// move the file
			File temp = new File(reportFileLocation, fName);

			success = temp.delete();

			if(!success) {
				// TODO log that we deleted this file without importing it
				logger.warn("unable to delete file: " + temp.getName());
			}
		}
	}

	private class FlightInfoFileFilter implements FilenameFilter {

		public boolean accept(File dir, String name) {
			Matcher m = REPORT_FILE_NAME_PATTERN.matcher(name);
			return m.find();
		}

	}

	public FlightInfoBmo getFiBmo() {
		return fiBmo;
	}

	public void setFiBmo(FlightInfoBmo fiBmo) {
		this.fiBmo = fiBmo;
	}

	public String getReportFileLocation() {
		return reportFileLocation;
	}

	public void setReportFileLocation(String reportFileLocation) {
		this.reportFileLocation = reportFileLocation;
	}

	public String getBackupLocation() {
		return backupLocation;
	}

	public void setBackupLocation(String backupLocation) {
		this.backupLocation = backupLocation;
	}

	public DataFeedLogBmo getDflBmo() {
		return dflBmo;
	}

	public void setDflBmo(DataFeedLogBmo dflBmo) {
		this.dflBmo = dflBmo;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
		if(companyCode != null) {
			this.REPORT_FILE_NAME_PATTERN = Pattern.compile("^" + companyCode.toLowerCase()
					+ "ep-(\\d{8}-\\d{4})\\.zip$", Pattern.CASE_INSENSITIVE);
		}
	}

}
