package me.damascus2000.sockapplication.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import me.damascus2000.sockapplication.dtos.UserDTO;
import me.damascus2000.sockapplication.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final MyUserDetailsService myUserDetailsService;

    @Autowired
    public AuthController(MyUserDetailsService myUserDetailsService){
        this.myUserDetailsService = myUserDetailsService;
    }


    @GetMapping("/login")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> loginRequiredPage() {
        UserDTO expectedFormat = new UserDTO();
        expectedFormat.setUsername("String");
        expectedFormat.setPassword("String");
        return ResponseEntity.ok(Map.of(
                "Method", "POST",
                "Content-Type", "multipart/form-data",
                "Expected body", expectedFormat
        ));
    }

    @PostMapping(value = "/signup")
    @ResponseBody
    //WARN: ONLY FOR DEBUGGING! Dangerous in production
    public ResponseEntity<Object> debugSignUp(@RequestBody JsonNode userDTO) throws Exception {
        myUserDetailsService.registerDebugAccount(new UserDTO(userDTO.get("username").asText(), userDTO.get("password").asText()));

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/username")
    @ResponseBody
    public ResponseEntity<String> currentUserName(Authentication authentication) {
        System.out.println(authentication);
        if (authentication == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(authentication.getName());
    }

}
