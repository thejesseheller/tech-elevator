package com.techelevator.reservation;

import java.time.LocalDate;
import java.util.Objects;

public class Reservation {

    private Long reservationId;
    private Long siteId;
    private String name;
    private LocalDate fromDate;
    private LocalDate toDate;
    private LocalDate createDate = LocalDate.now();

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation)) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(getReservationId(), that.getReservationId()) &&
                Objects.equals(getSiteId(), that.getSiteId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getFromDate(), that.getFromDate()) &&
                Objects.equals(getToDate(), that.getToDate()) &&
                Objects.equals(getCreateDate(), that.getCreateDate());
    }

	@Override
	public String toString() {
		return reservationId + " " + siteId + " " + name + " " + fromDate + " " + toDate; 
	}
    

}
