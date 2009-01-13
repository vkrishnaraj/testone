package com.bagnet.nettracer.other;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JRVirtualizable;
import net.sf.jasperreports.engine.fill.JRAbstractLRUVirtualizer;
import net.sf.jasperreports.engine.util.JRProperties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.bmo.ReportBMO;

/**
 * Virtualizes data to the filesystem. When this object is finalized, it removes
 * the swap files it makes. The virtualized objects have references to this
 * object, so finalization does not occur until this object and the objects
 * using it are only weakly referenced.
 * 
 * @author John Bindel
 * @version $Id$
 */
public class JRGovernedFileVirtualizer extends JRAbstractLRUVirtualizer {
	
	private static final Log log = LogFactory.getLog(JRGovernedFileVirtualizer.class);

	
	/**
	 * Property used to decide whether {@link File#deleteOnExit() deleteOnExit} should be requested
	 * for temporary files created by the virtualizer.
	 * <p>
	 * Calling  {@link File#deleteOnExit() File.deleteOnExit()} will accumulate JVM process memory
	 * (see this <a href="http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4513817">bug</a>), and this
	 * should abviously be avoided in long-running applications.
	 * <p>
	 * Temporary files will be deleted by explicitly calling {@link #cleanup() cleanup()} or from the virtualizer
	 * <code>finalize()</code> method.
	 */
	public static final String PROPERTY_TEMP_FILES_SET_DELETE_ON_EXIT = JRProperties.PROPERTY_PREFIX + "virtualizer.files.delete.on.exit";

	private final String directory;
	private int maxPageFiles = 0;
	private int currentNumberPages = 0;

	/**
	 * Uses the process's working directory as the location to store files.
	 * 
	 * @param maxSize
	 *            the maximum size (in JRVirtualizable objects) of the paged in
	 *            cache.
	 */
	public JRGovernedFileVirtualizer(int maxSize, int maxPageFiles) {
		this(maxSize, null, maxPageFiles);
	}

	/**
	 * @param maxSize
	 *            the maximum size (in JRVirtualizable objects) of the paged in
	 *            cache.
	 * @param directory
	 *            the base directory in the filesystem where the paged out data
	 *            is to be stored
	 */
	public JRGovernedFileVirtualizer(int maxSize, String directory, int maxPageFiles) {
		super(maxSize);
		
		this.directory = directory;
		this.maxPageFiles = maxPageFiles;
	}

	private String makeFilename(JRVirtualizable o) {
		String uid = o.getUID();
		return "virt" + uid;
	}

	private String makeFilename(String virtualId) {
		return "virt" + virtualId;
	}

	protected void pageOut(JRVirtualizable o) throws IOException {
		// Store data to a file.
		String filename = makeFilename(o);
		File file = new File(directory, filename);
		
		if (file.createNewFile()) {
			if (JRProperties.getBooleanProperty(PROPERTY_TEMP_FILES_SET_DELETE_ON_EXIT)) {
				file.deleteOnExit();
			}
			
			// If file virtualization limitation is on (indicated by maxPageFiles > 0)
			if (maxPageFiles > 0) {
				++currentNumberPages;
				if (currentNumberPages > maxPageFiles) {
					
					throw new JRMaxFilesException("Too many pages being stored to disk: " + currentNumberPages + " Max: " + maxPageFiles);
				}
			}

			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
				BufferedOutputStream bufferedOut = new BufferedOutputStream(fos);
				writeData(o, bufferedOut);
			}
			catch (FileNotFoundException e) {
				log.error("Error virtualizing object", e);
				throw new JRRuntimeException(e);
			}
			finally {
				if (fos != null) {
					fos.close();
				}
			}
		} else {
			if (!isReadOnly(o)) {
				throw new IllegalStateException(
						"Cannot virtualize data because the file \"" + filename
								+ "\" already exists.");
			}
		}
	}

	protected void pageIn(JRVirtualizable o) throws IOException {
		// Load data from a file.
		String filename = makeFilename(o);
		File file = new File(directory, filename);

		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			BufferedInputStream bufferedIn = new BufferedInputStream(fis);
			readData(o, bufferedIn);
		}
		catch (FileNotFoundException e) {
			log.error("Error devirtualizing object", e);
			throw new JRRuntimeException(e);
		}
		finally {
			if (fis != null) {
				fis.close();
			}
		}

		if (!isReadOnly(o)) {
			// Wait until we know it worked before tossing the data.
			file.delete();
		}
	}

	protected void dispose(String virtualId) {
		String filename = makeFilename(virtualId);
		File file = new File(directory, filename);
		file.delete();
	}
	
	
	/**
	 * Called when we are done with the virtualizer and wish to
	 * cleanup any resources it has.
	 */
	public synchronized void cleanup()
	{
		disposeAll();
		reset();
	}
}
