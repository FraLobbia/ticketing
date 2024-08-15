package com.backend.model.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class LoginDTO {
    private String email;
    private String password;
}
