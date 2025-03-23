package com.authentication.models.entities;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class CustomUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Relazione molti-a-molti per i ruoli
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "accounts_roles",
        joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();
    
    private String name;
    private String surname;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Override
    public String getUsername() {
        return getEmail();
    }
    
    // Implementazione di getAuthorities() per trasformare i ruoli in GrantedAuthority
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return  new HashSet<>(roles);
    }
}
