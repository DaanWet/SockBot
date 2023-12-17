package me.damascus2000.sockapplication.dtos;

import lombok.Getter;
import lombok.Setter;
import me.damascus2000.sockapplication.configurations.BotConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.LocalTime;
@Getter
@Setter
public class ReservationDayDTO {
    private LocalDate day;
    private LocalTime start;

    private LocalTime end;

    private int availableSlots;


    public ReservationDayDTO(ReservationDay reservationDay, int places){
        this.day = reservationDay.getDay();
        this.end = reservationDay.getEnd();
        this.start = reservationDay.getStart();
        this.availableSlots = places - reservationDay.getReservations().size();
    }


}
