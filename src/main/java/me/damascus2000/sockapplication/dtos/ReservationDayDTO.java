package me.damascus2000.sockapplication.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@Getter
@Setter
public class ReservationDayDTO {
    private LocalDate day;
    private LocalTime end;

    private int availableSlots;

    public ReservationDayDTO(ReservationDay reservationDay){
        this.day = reservationDay.getDay();
        this.end = reservationDay.getEnd();
        this.availableSlots = 15 - reservationDay.getReservations().size();
    }


}
