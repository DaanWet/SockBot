package me.damascus2000.sockapplication.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import me.damascus2000.sockapplication.dtos.ReservationDay;
import me.damascus2000.sockapplication.dtos.ReservationDayDTO;
import me.damascus2000.sockapplication.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final DateTimeFormatter dateFormatter;
    private final DateTimeFormatter timeFormatter;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    }

    @GetMapping("")
    @Autowired
    public ResponseEntity<List<ReservationDayDTO>> getPossibleReservations(@Value("${app.places}") int places) throws Exception {
        Collection<ReservationDay> reservations = reservationService.getReservations();
        List<ReservationDayDTO> collect = reservations.stream().map(m -> new ReservationDayDTO(m, places)).collect(Collectors.toList());
        return ResponseEntity.ok(collect);
    }

    @PostMapping("")
    public ResponseEntity<Object> postReservation(@RequestBody JsonNode json) throws Exception {
        String name = json.get("name").asText();
        String email = json.get("email").asText();
        JsonNode n = json.get("reservations");
        System.out.println(n);
        for (JsonNode next : n) {
            System.out.println(next);
            String dayS = next.get("day").asText();
            String startS = next.get("start").asText();
            String endS = next.get("end").asText();
            LocalDate day = LocalDate.from(dateFormatter.parse(dayS));
            LocalTime start = LocalTime.from(timeFormatter.parse(startS));
            LocalTime end = LocalTime.from(timeFormatter.parse(endS));
            reservationService.addReservation(name, email, day, start, end);
        }

        return ResponseEntity.accepted().build();
    }

    @GetMapping("/person")
    public ResponseEntity<HashMap<String, ReservationDay>> getReservations(@RequestBody JsonNode node) throws Exception {
        if (!node.has("email")) {
            return ResponseEntity.badRequest().build();
        }
        HashMap<String, ReservationDay> reservations = reservationService.getReservations(node.get("email").asText());
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/results")
    public ResponseEntity<List<ReservationDay>> getAllReservations(Authentication auth) throws Exception {
        if (!auth.isAuthenticated()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservationService.getReservations().stream().toList());
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addReservationDay(Authentication authentication, @RequestBody JsonNode node) throws Exception {
        if (!authentication.isAuthenticated()) {
            return ResponseEntity.notFound().build();
        }
        String dayS = node.get("day").asText();
        LocalDate day = LocalDate.from(dateFormatter.parse(dayS));
        ReservationDay reservationDay = new ReservationDay(day);
        if (node.has("end")) {
            String endS = node.get("end").asText();
            LocalTime end = LocalTime.from(timeFormatter.parse(endS));
            reservationDay.setEnd(end);
        }
        if (node.has("start")) {
            String startS = node.get("start").asText();
            LocalTime start = LocalTime.from(timeFormatter.parse(startS));
            reservationDay.setStart(start);
        }
        reservationService.addReservationDay(reservationDay);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/delete")
    public ResponseEntity<Object> removeReservationDay(Authentication authentication, @RequestBody JsonNode node) throws Exception {
        if (!authentication.isAuthenticated()) {
            return ResponseEntity.notFound().build();
        }
        String dayS = node.get("day").asText();
        LocalDate day = LocalDate.from(dateFormatter.parse(dayS));
        reservationService.removeReservationDay(day);
        return ResponseEntity.accepted().build();
    }
}
