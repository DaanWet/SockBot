package me.damascus2000.sockapplication.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
@Getter
@Setter
public class ReservationDay {
    private LocalDate day;
    private LocalTime end;
    private ArrayList<Reservation> reservations;

    public ReservationDay(){}
    public ReservationDay(LocalDate day, LocalTime end, ArrayList<Reservation> reservations){
        this.day = day;
        this.end = end;
        this.reservations = reservations;
    }
    public ReservationDay(LocalDate day, LocalTime end){
        this(day, end, new ArrayList<>());
    }

    public ReservationDay(LocalDate day){
        this(day, day.getDayOfWeek() == DayOfWeek.FRIDAY ? LocalTime.of(19, 0) : LocalTime.of(22, 0), new ArrayList<>());
    }

    public ReservationDay(LocalDate day, ArrayList<Reservation> reservations){
        this(day, day.getDayOfWeek() == DayOfWeek.FRIDAY ? LocalTime.of(19, 0) : LocalTime.of(22, 0), reservations);
    }

}
