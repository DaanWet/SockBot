package me.damascus2000.sockapplication.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String username;
    private String password;

    public UserDTO(){}
    public UserDTO(String username, String password){
        this.username = username;
        this.password = password;
    }
}
