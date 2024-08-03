package com.backend.model.DTO;
import lombok.*;
import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Getter
@Setter
public class UserDTO {

    private Long id;
    private String name;
    private String surname;

    private String email;
    private String role;
}
