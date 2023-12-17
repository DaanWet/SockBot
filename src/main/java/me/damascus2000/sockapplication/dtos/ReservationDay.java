package me.damascus2000.sockapplication.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ReservationDay {
    private LocalDate day;
    private LocalTime start;
    private LocalTime end;
    private List<Reservation> reservations;

    public ReservationDay(){}
    public ReservationDay(LocalDate day, LocalTime start, LocalTime end, ArrayList<Reservation> reservations){
        this.day = day;
        this.end = end;
        this.start = start;
        this.reservations = reservations;
    }
    public ReservationDay(LocalDate day, LocalTime start, LocalTime end){
        this(day, start, end, new ArrayList<>());
    }

    public ReservationDay(LocalDate day, LocalTime end){
        this(day, LocalTime.of(8, 0),  end, new ArrayList<>());
    }
    public ReservationDay(LocalDate day){
        this(day, LocalTime.of(8, 0), day.getDayOfWeek() == DayOfWeek.FRIDAY ? LocalTime.of(19, 0) : LocalTime.of(22, 0), new ArrayList<>());
    }

    public ReservationDay(LocalDate day, ArrayList<Reservation> reservations){
        this(day, LocalTime.of(8, 0), day.getDayOfWeek() == DayOfWeek.FRIDAY ? LocalTime.of(19, 0) : LocalTime.of(22, 0), reservations);
    }

}
