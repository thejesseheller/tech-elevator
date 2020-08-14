package com.techelevator.reservation;

import java.time.LocalDate;
import java.util.List;

import com.techelevator.site.Site;

public interface ReservationDAO {

    List<Reservation> getAllReservations();
    Reservation createReservation(Reservation reservation);
    Reservation searchForReservationByReservationId(Long resIdSearch);
    List<Reservation> searchUpcomingRes(Long parkId);


}
