package com.backend.model.DTO.request;

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
