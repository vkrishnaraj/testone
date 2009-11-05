/**
 * 
 */
package aero.nettracer.serviceprovider.common.exceptions;

/**
 * @author byron
 *
 */
public class BagtagException extends Exception {

	public static final String INVALID_FORMAT_MESSAGE = "The provided bag tag was not in an expected format."; 
	public static final String NO_MATCH_MESSAGE = "No match was found for the provided airline code.";
	/**
	 * 
	 */
	public BagtagException() {
	}

	/**
	 * @param message
	 */
	public BagtagException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public BagtagException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public BagtagException(String message, Throwable cause) {
		super(message, cause);
	}

}
