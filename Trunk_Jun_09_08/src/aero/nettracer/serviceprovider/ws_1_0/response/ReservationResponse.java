package aero.nettracer.serviceprovider.ws_1_0.response;

import aero.nettracer.serviceprovider.ws_1_0.common.Reservation;

public class ReservationResponse extends GenericResponse {
	private Reservation reservation = null;

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
}
