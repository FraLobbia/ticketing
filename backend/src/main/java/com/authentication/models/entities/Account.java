package com.authentication.models.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.app.models.entities.Comment;
import com.app.models.entities.Ticket;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account extends CustomUserDetails {

    private static final long serialVersionUID = 1L;

    // Ridefiniamo l'email per garantire l'unicità a livello di database.
    @Column(unique = true, nullable = false)
    @Override
    public String getUsername() {
        return super.getEmail();
    }

    // Relazione molti-a-molti per i ruoli
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "accounts_roles",
        joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    // Immagine del profilo, salvata come BLOB; in alternativa, puoi salvare un URL
    @Lob
    private byte[] profilePicture;

    // Relazioni con Ticket e Comment (assicurati che queste entità siano definite nel tuo progetto)
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    // Implementazione di getAuthorities() per trasformare i ruoli in GrantedAuthority
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return  new HashSet<>(roles);
    }
}
