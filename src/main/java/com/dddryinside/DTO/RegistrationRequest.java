package com.dddryinside.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RegistrationRequest {
    private String username;
    private String email;
    private String password;
}