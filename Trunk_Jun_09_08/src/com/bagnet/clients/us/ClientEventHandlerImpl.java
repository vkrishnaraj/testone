package com.bagnet.clients.us;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import aero.nettracer.serviceprovider.wt_1_0.common.ForwardMessage;
import aero.nettracer.serviceprovider.wt_1_0.common.ForwardOhd;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.integrations.events.BeornDTO;
import com.bagnet.nettracer.integrations.events.ClientEventHandler;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD_Log;
import com.bagnet.nettracer.tracing.db.OHD_Log_Itinerary;
import com.bagnet.nettracer.tracing.db.OnlineIncidentAuthorization;
import com.bagnet.nettracer.tracing.db.ProactiveNotification;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.usairways.lcc.aat_sharesws.services_asmx.Baggage;
import com.usairways.lcc.aat_sharesws.services_asmx.Booking;
import com.usairways.lcc.aat_sharesws.services_asmx.Itinerary;
import com.usairways.lcc.aat_sharesws.services_asmx.Passenger;
import com.usairways.lcc.aat_sharesws.services_asmx.Segment;

public class ClientEventHandlerImpl implements ClientEventHandler {

	private static Logger logger = Logger.getLogger(ClientEventHandlerImpl.class);
	private String newline = "+";
	
	@Override
	public void doEventOnForward(ForwardOhd fw) {
		try {
			ForwardIntegrationImpl fwd = new ForwardIntegrationImpl();
			fwd.sendMessage(fw);
		} catch (Exception e) {
			logger.error("Exception encountered processing forward", e);
		}
	}
	
	@Override
	public void doEventOnForward(ForwardMessage fw) {
		try {
			ForwardIntegrationImpl fwd = new ForwardIntegrationImpl();
			fwd.sendMessage(fw);
		} catch (Exception e) {
			logger.error("Exception encountered processing forward", e);
		}
	}

	
	@Override
	public void doEventOnForward(List<OHD_Log> logs) {
		try {
			ForwardIntegrationImpl fwd = new ForwardIntegrationImpl();
			fwd.sendMessage(logs);
		} catch (Exception e) {
			logger.error("Exception encountered processing forward", e);
		}
	}
	
	@Override
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
		
		ArrayList<OHD_Log> logs = new ArrayList<OHD_Log>();
		logs.add(dto.getLog());
		doEventOnForward(logs);
		
		if (stringExists(dto.getSpecialInstructions())) {
			StringBuffer str = new StringBuffer(256);
			
			str.append("Baggage Forward Message+");
			
			str.append("OHD                  " + dto.getOnhand() + "+");
			if (stringExists(dto.getTagNumber())) {
				str.append("Tag Number           " + dto.getTagNumber() + "+");
			} else if (stringExists(dto.getExpediteNumber())) {
				str.append("Expedite Number      " + dto.getExpediteNumber() +"+");
			}

			int day = 0;
			String monthStr = "";
			String finalFlightDate = "";
			if(dto.getFinalFlightDepartureDate() != null) {
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(dto.getFinalFlightDepartureDate());
				
				day = cal.get(Calendar.DAY_OF_MONTH);
				int month = cal.get(Calendar.MONTH);
				
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
				finalFlightDate = "/" + day + monthStr;
			}

						
			
			str.append("Final Flight         " + dto.getFinalFlightAirline() + dto.getFinalFlightNumber() + finalFlightDate + "+");
			
			
			
			str.append("Final Destination    " + dto.getFinalDestination() +"+");
			str.append("Reason for Loss      " + dto.getReasonForLoss() +"+");
			str.append("Mishandling Station  " + dto.getFaultStation() +"+");
			str.append("Telex Address        " + dto.getSpecialInstructions() +"+");
			
			// Call USAir Web Service to send message
			SharesIntegrationWrapper iw = new SharesIntegrationWrapper();
			logger.info(str.toString());
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
	
	@Override
	public void doPcn(OHD_Log ohd_log) {
		if (PropertyBMO.isTrue(PropertyBMO.PROPERTY_PCN_ENABLED)) {
			String refId = ohd_log.getOhd().getClaimnum();
			
			logger.info("Creating PCN for Tag:" + refId);

			SharesIntegrationWrapper iw = new SharesIntegrationWrapper();
			ProactiveNotification pcn = new ProactiveNotification();
			OnlineIncidentAuthorization oia = new OnlineIncidentAuthorization();
			
			// If we have a valid-enough claim check number we can process it.
			if (ohd_log.getOhd().getClaimnum() != null && ohd_log.getOhd().getClaimnum().trim().length() > 0) {
				
				// Get booking information.
				boolean cont = true;
				try {
					cont = iw.getBookingByKey(null, ohd_log.getOhd().getClaimnum());
					
					
				} catch (Exception e) {
					logger.info("Aborting PCN generation: Booking web service exception. "+ refId, e);
					return;
				}
				
				if (!cont) {
					logger.info("Aborting PCN generation: No booking information."+ refId);
					return;
				} else {
					Booking booking = iw.getBooking();
					
					
					
					if (booking.getPassengers() != null && booking.getPassengers().sizeOfPassengerArray() > 0) {
						Passenger pax = booking.getPassengers().getPassengerArray(0);
						
						int passIndex = 0;
						int tempIndex = 0;
						for(Passenger p: booking.getPassengers().getPassengerArray()) {
							for (Baggage bag: p.getBaggageList().getBaggageArray()) {
								if (bag.getBagTag().equalsIgnoreCase(ohd_log.getOhd().getClaimnum())) {
									pax = p;
									passIndex = tempIndex;
									break;
								}
							}
							tempIndex++;
						}
					
						StringBuilder sb = new StringBuilder("");
						StringUtils.appendIfNotNull(sb, pax.getFirstName());
						StringUtils.appendIfNotNull(sb, pax.getLastName());
						pcn.setName(sb.toString());
						pcn.setPassIndex(passIndex);
						oia.setFirstName(pax.getFirstName());
						oia.setLastName(pax.getLastName());
						
					} else {
						logger.info("Aborting PCN generation: No name information."+ refId);
						return;
					}
					
					if (booking.getRecordLocator() != null && booking.getRecordLocator().trim().length() > 0) {
						pcn.setLocator(booking.getRecordLocator());
						oia.setPnr(booking.getRecordLocator());
					} else {
						logger.info("Aborting PCN generation: No record locator."+ refId);
						return;
					}
					
					Status status = new Status();
					status.setStatus_ID(ProactiveNotification.STATUS_OPEN);
					pcn.setStatus(status);
					
					
					if (booking.getPassengerItinerary() != null && booking.getPassengerItinerary().getItineraryArray().length > 0) {
						Itinerary itin = booking.getPassengerItinerary().getItineraryArray(0);
						
						if (itin.getSegments() != null && itin.getSegments().getSegmentArray().length > 0) {
							Segment[] segments = itin.getSegments().getSegmentArray();
							
							String destinationStation = ohd_log.getDestStation();
							
							Station destStation = StationBMO.getStationByCode(destinationStation, "US");
							if (destStation == null) {
								logger.info("Aborting PCN generation: Destination station doesn't exist for US."+ refId);
								return;
							} 

							pcn.setDestinationStation(destStation);
					
							
							Calendar forwardCal = new GregorianCalendar();
							forwardCal.setTime(ohd_log.getForward_time());
								
							// If the segment arrival station matches the destination station from the BEORN
							// then verify dates before populating the PCN information
							for (Segment segment: segments) {
								if (segment.getDepartureEstimated() != null) {
									
									Calendar departureCal = segment.getDepartureEstimated();
									Calendar latestDate = departureCal;
									// If segment is final on the same day as the forward, 
									// assume it's the correct arriving segment.
									if (DateUtils.isSameDate(forwardCal, departureCal) && 
											departureCal.getTimeInMillis() >= latestDate.getTimeInMillis()) {
										
										latestDate = departureCal;	
										pcn.setMissedFlightAirline(segment.getCarrierCode());
										pcn.setMissedFlightDate(segment.getDepartureEstimated().getTime());
										pcn.setMissedFlightNumber(segment.getFlightNumber());
										pcn.setMissedFlightDestination(segment.getArrivalStation());
										Calendar arrivalCal = new GregorianCalendar();
										if (segment.getArrivalActual() != null) {
											arrivalCal = segment.getArrivalActual();
										} else {
											arrivalCal.add(Calendar.HOUR, 4);
										}
										arrivalCal.add(Calendar.HOUR, PropertyBMO.getValueAsInt(PropertyBMO.PROPERTY_OIA_HOURS_BEFORE_EXPIRE));
										oia.setExpireTime(arrivalCal.getTime());
									}
								}
							}
						} else {
							logger.info("Aborting PCN generation: No segments."+ refId);
							return;
						}
						
						if (pcn.getMissedFlightAirline() == null || pcn.getMissedFlightDate() == null || pcn.getMissedFlightNumber() == null) {
							logger.info("Aborting PCN generation: We were not able to determine the final flight returned for the given day in the itinerary."+ refId);
							return;
						}
						
						
					} else {
						logger.info("Aborting PCN generation: No itinerary for "+ refId);
						return;
					}
					
				}
			}
		
			// If I reached here, I have a valid, populated PCN object.  Now I need to verify
			// that there are no existing PCNs that match the record locator, and missing flight details.
			logger.debug("Beginning to save PCN... " + refId);
			Session sess = null;
			Transaction t = null;
			try {
				sess = HibernateWrapper.getSession().openSession();
				ProactiveNotification existingNotification = getExistingPcnIfExists(pcn, sess);
				
				if (existingNotification != null) {
					existingNotification.getOhd_logs().add(ohd_log);
					ohd_log.setPcn(existingNotification);
					t = sess.beginTransaction();
					sess.saveOrUpdate(existingNotification);
					sess.saveOrUpdate(ohd_log);
					t.commit();
					
				} else {
					ohd_log.setPcn(pcn);
					pcn.setOia(oia);
					t = sess.beginTransaction();
					sess.save(oia);
					sess.save(pcn);
					sess.saveOrUpdate(ohd_log);
					t.commit();
				}
			} catch (Exception e) {
				
				logger.error("Exception encountered loading & saving PCN " + refId, e);

				// TODO: Remove this next line
				e.printStackTrace();
						
				if (t != null) {
					t.rollback();
				}
			} finally {
				if (sess != null) {
					sess.close();
				}
			}
		
		}
	}

	private ProactiveNotification getExistingPcnIfExists(ProactiveNotification pcn, Session sess) {
		// Load any PCNs that match the 
		
		StringBuilder query = new StringBuilder();
		query.append("select pcn from com.bagnet.nettracer.tracing.db.ProactiveNotification pcn ");
		query.append(" where ");
		query.append(" pcn.status.status_ID <> :closed");
		query.append(" and pcn.locator = :locator");
		query.append(" and pcn.destinationStation.station_ID = :station");
		query.append(" and pcn.missedFlightAirline = :airline");
		query.append(" and pcn.missedFlightDate = :date");
		query.append(" and pcn.missedFlightNumber = :number");
		
		Query q = sess.createQuery(query.toString());
		
		q.setInteger("closed", ProactiveNotification.STATUS_CLOSED);
		q.setString("locator", pcn.getLocator().trim());
		q.setInteger("station", pcn.getDestinationStation().getStation_ID());
		q.setString("airline", pcn.getMissedFlightAirline().trim());
		q.setDate("date", pcn.getMissedFlightDate());
		q.setString("number", pcn.getMissedFlightNumber().trim());
		
		
		try {
			logger.info("Running query");
			List<ProactiveNotification> list = q.list();
			if (list.size() == 1) {
				logger.info("Returning 1 element");
				return list.get(0);
			}
		} catch (Exception e) {
			logger.error("Error retrieving list of PCNs: ", e);
			// TODO: Remove.
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void printPcn(String address, ProactiveNotification pcn) {
		if (PropertyBMO.isTrue(PropertyBMO.PROPERTY_PCN_ENABLED)) {
			logger.info("Attemptign to print PCN: " + pcn.getPcn_id());
			SharesIntegrationWrapper iw = new SharesIntegrationWrapper();
			
			ArrayList<String> tags = new ArrayList<String>();
			boolean newFlightValuesValidAndPresent = true;
			OHD_Log_Itinerary finalSegment = null;
			
			for (OHD_Log log: pcn.getOhd_logs()) {
				// Grab final segment
				if (finalSegment == null) {
					if (log.getItinerarylist().size() > 0) {
						finalSegment = (OHD_Log_Itinerary) log.getItinerarylist().get(log.getItinerarylist().size() - 1);
					}
				} else if (newFlightValuesValidAndPresent = true) {
					if (log.getItinerarylist().size() > 0) {
						OHD_Log_Itinerary curItin = (OHD_Log_Itinerary) log.getItinerarylist().get(log.getItinerarylist().size() - 1);
						
						if (finalSegment.getDepartdate() != null && curItin.getDepartdate() != null && !finalSegment.getDepartdate().equals(curItin.getDepartdate())) { 
							newFlightValuesValidAndPresent = false;
						} else if (finalSegment.getAirline() != null && curItin.getAirline() != null && !finalSegment.getAirline().equals(curItin.getAirline())) {
							newFlightValuesValidAndPresent = false;
						} else if (finalSegment.getFlightnum() != null && curItin.getFlightnum() != null && !finalSegment.getFlightnum().equals(curItin.getFlightnum())) {
							newFlightValuesValidAndPresent = false;
						}
					}
				}
				
				// Append list of applicable tag numbers
				String claim = log.getOhd().getClaimnum();
				if (claim != null && claim.length() > 0) {
					tags.add(claim);
				}
			}

			StringBuilder message = new StringBuilder();
			message.append("DELAYED BAGGAGE INFORMATION" + newline + newline);
			message.append("CUSTOMER NAME:      " + pcn.getName() + newline);
			message.append("CUSTOMER FLIGHT #:  " + pcn.getMissedFlightAirline() + " " + pcn.getMissedFlightNumber() + " to " + pcn.getMissedFlightDestination() + newline);
			message.append("BAG TAG #:          ");
			message.append(StringUtils.join(tags, ", ") + newline);
			
			finalSegment.set_DATEFORMAT(TracingConstants.DISPLAY_DATEFORMAT);
			finalSegment.set_TIMEFORMAT(TracingConstants.DISPLAY_TIMEFORMAT_C);
			
			if (newFlightValuesValidAndPresent) {
				
				String newFlight = "";
				if (finalSegment.getAirline() != null) {
					newFlight += finalSegment.getAirline() + " ";
				}
				if (finalSegment.getFlightnum() != null) {
					newFlight += finalSegment.getFlightnum();
				}
				
				if (finalSegment.getFlightnum() != null) {
					message.append("NEW FLIGHT #:       " + newFlight + newline);
				  if (finalSegment.getScharrivetime() != null && finalSegment.getArrivedate() != null) {
				  	message.append("ESTIMATED ARRIVAL: " + finalSegment.getDisscharrivetime() 
				  			+ " " + finalSegment.getDisarrivedate() + newline);
				  } else {
				  	message.append(newline);
				  }
				}
			}

			message.append("PLEASE ACCEPT OUR APOLOGY FOR THE DELAY OF YOUR BAG.  PLEASE PROCEED+" +
					           "DIRECTLY TO THE BAGGAGE SERVICE OFFICE ON THE BAGGAGE ARRIVAL LEVEL+" +
					           "TO PROCESS YOUR REPORT." + newline + newline + newline);
			message.append("-------------------");
			
			iw.sendTelex(message.toString(), address);
		}
	}

	@Override
	public Incident sendCrm(String i, Session sess) {
		CrmIntegration integ = new CrmIntegration();
		return integ.sendIncident(i, sess);
	}
}
