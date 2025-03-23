package com.authentication.models.entities;

import java.util.List;

import com.app.model.entities.Comment;
import com.app.model.entities.Ticket;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "accounts")
public class Account extends CustomUserDetails {
    private static final long serialVersionUID = 1L;

    @Lob
    private byte[] profilePicture;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;


}
