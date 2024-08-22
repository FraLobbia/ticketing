package com.backend.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ACCOUNTS")
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;

    @Column(unique = true, nullable = false)
    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "accounts_roles", joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    @Lob
    private byte[] profilePicture;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Ticket> tickets = new ArrayList<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<>(this.roles);
    }

    @Override
    public String getUsername() {
        return this.email;
    }

}
