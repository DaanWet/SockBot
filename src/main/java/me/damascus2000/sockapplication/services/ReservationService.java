package me.damascus2000.sockapplication.services;

import com.fasterxml.jackson.core.type.TypeReference;
import me.damascus2000.sockapplication.dtos.Reservation;
import me.damascus2000.sockapplication.dtos.ReservationDay;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class ReservationService extends DataHandler {

    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public ReservationService() {
        this.path = "./reservations.json";
    }

    public HashMap<String, ReservationDay> read() throws IOException {
        HashMap<String, ReservationDay> map = mapper.readValue(new File(path), new TypeReference<HashMap<String, ReservationDay>>() {
        });
        return map;
    }

    public boolean addReservationDay(ReservationDay day) throws Exception {
        String dayString = day.getDay().format(format);
        HashMap<String, ReservationDay> reservations = read();
        if (!reservations.containsKey(dayString)) {
            reservations.put(dayString, day);
            save(reservations);
            return true;
        }
        return false;
    }

    public boolean removeReservationDay(LocalDate day) throws Exception {
        String dayString = day.format(format);
        HashMap<String, ReservationDay> reservations = read();
        if (reservations.containsKey(dayString)) {
            reservations.remove(dayString);
            save(reservations);
            return true;
        }

        return false;
    }

    public Collection<ReservationDay> getReservations() throws Exception {
        HashMap<String, ReservationDay> reservations = read();
        return reservations.values();
    }

    public HashMap<String, ReservationDay> getReservations(String email) throws Exception {
        HashMap<String, ReservationDay> reservations = read();
        reservations.forEach((key, value) -> {
            value.setReservations(value.getReservations().stream().filter(res -> res.getEmail().equalsIgnoreCase(email)).collect(Collectors.toList()));
        });
        return reservations;
    }

    public void addReservation(String name, String email, LocalDate day, LocalTime start, LocalTime end) throws Exception {
        String dayString = day.format(format);
        HashMap<String, ReservationDay> reservations = read();
        if (reservations.containsKey(dayString)) {
            ReservationDay reservationDay = reservations.get(dayString);
            reservationDay.getReservations().add(new Reservation(name, email, start, end));
            reservations.put(dayString, reservationDay);
        }
        save(reservations);
    }
}
