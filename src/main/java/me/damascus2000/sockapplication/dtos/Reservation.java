package me.damascus2000.sockapplication.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
@Getter
@Setter
public class Reservation {
    private String name;
    private String email;
    private LocalTime start;
    private LocalTime end;

    public Reservation(String name, String email, LocalTime start, LocalTime end){
        this.name = name;
        this.email = email;
        this.start = start;
        this.end = end;
    }


}
