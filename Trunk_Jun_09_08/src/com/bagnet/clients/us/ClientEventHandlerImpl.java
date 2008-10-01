package com.bagnet.clients.us;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.bagnet.nettracer.integrations.events.BeornDTO;
import com.bagnet.nettracer.integrations.events.ClientEventHandler;

public class ClientEventHandlerImpl implements ClientEventHandler {

	public void doEventOnBeornWS(BeornDTO dto) {
		
		/*
		 * Example Output Provided by Melody @ USAir:
		 * 
		 * Baggage Forward Message
		 * Tag Number                   0037123456
		 * Final Flight                 US1234/25SEP
		 * Final Destination            PHX
		 * Reason for Loss              42
		 * Mishandling Station          PHX
		 * Telex Address                LAXOOUS
		 */
		
		if (stringExists(dto.getSpecialInstructions())) {
			StringBuffer str = new StringBuffer(256);
			
			str.append("Baggage Forward Message+");
			if (stringExists(dto.getTagNumber())) {
				str.append("Tag Number           " + dto.getTagNumber() + "+");
			} else if (stringExists(dto.getExpediteNumber())) {
				str.append("Expedite Number      " + dto.getExpediteNumber() +"+");
			}

			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(dto.getFinalFlightDepartureDate());
			
			int day = cal.get(Calendar.DAY_OF_MONTH);
			int month = cal.get(Calendar.MONTH);
			String monthStr = "";
			switch (month) {
				case Calendar.JANUARY: monthStr = "JAN"; break;
				case Calendar.FEBRUARY: monthStr = "FEB"; break;
				case Calendar.MARCH: monthStr = "MAR"; break;
				case Calendar.APRIL: monthStr = "APR"; break;
				case Calendar.MAY: monthStr = "MAY"; break;
				case Calendar.JUNE: monthStr = "JUN"; break;
				case Calendar.JULY: monthStr = "JUL"; break;
				case Calendar.AUGUST: monthStr = "AUG"; break;
				case Calendar.SEPTEMBER: monthStr = "SEP"; break;
				case Calendar.OCTOBER: monthStr = "OCT"; break;
				case Calendar.NOVEMBER: monthStr = "NOV"; break;
				case Calendar.DECEMBER: monthStr = "DEC"; break;
			}
			
			
			String finalFlightDate = day + monthStr;
			
			str.append("Final Flight         " + dto.getFinalFlightAirline() + dto.getFinalFlightNumber() +"/" + finalFlightDate + "+");
			str.append("Final Destination    " + dto.getFinalDestination() +"+");
			str.append("Reason for Loss      " + dto.getReasonForLoss() +"+");
			str.append("Mishandling Station  " + dto.getFaultStation() +"+");
			str.append("Telex Address        " + dto.getSpecialInstructions() +"+");
			
			// Call USAir Web Service to send message
			SharesIntegrationWrapper iw = new SharesIntegrationWrapper();
			try {
				iw.sendTelex(str.toString(), dto.getSpecialInstructions());
			} catch (Exception e) {
				// Ignore - there's nothing we can do.
				e.printStackTrace();
			}
		}
	}
	
	private boolean stringExists(String string) {
		if (string != null && string.length() > 0) {
			return true;
		}
		return false;
	}

}
