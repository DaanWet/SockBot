package me.damascus2000.sockapplication.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import me.damascus2000.sockapplication.dtos.ReservationDay;
import me.damascus2000.sockapplication.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


@Service("userDetailsService")
public class MyUserDetailsService extends DataHandler implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MyUserDetailsService(PasswordEncoder passwordEncoder) throws Exception{
        this.path = "./users.json";
        this.passwordEncoder = passwordEncoder;
    }

    private ArrayList<UserDTO> read() throws IOException{
        ArrayList<UserDTO> map = mapper.readValue(new File(path), new TypeReference<>() {});
        return map;
    }


    public void registerDebugAccount(UserDTO accountDto) throws Exception{
        ArrayList<UserDTO> users = read();
        accountDto.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        users.add(accountDto);
        save(users);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        try {
            ArrayList<UserDTO> users = read();
            Iterator<UserDTO> iterator = users.iterator();
            UserDetails usr = null;
            while (usr == null && iterator.hasNext()){
                UserDTO user = iterator.next();
                if (user.getUsername().equalsIgnoreCase(username)){
                    usr = new User(user.getUsername(), user.getPassword(), true, true, true, true, List.of());
                }
            }
            return usr;
        } catch (Exception e){
            throw new UsernameNotFoundException(username);
        }
    }
}
