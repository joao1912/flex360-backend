package com.flex360.api_flex360.models;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.flex360.api_flex360.enums.UserRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 26)
    private String nome;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false, length = 30, unique = true)
    private String email;

    @Column(nullable = false)
    private UserRole role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "carrinho_id", referencedColumnName = "id")
    private Carrinho carrinho;

    public Usuario(String nome, String senha, String email, UserRole role, Carrinho carrinho) {
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.role = role;
        this.carrinho = carrinho;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        if (this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));

    }

    @Override
    public String getPassword() {
        
        return senha;
    }

    @Override
    public String getUsername() {
        
        return email;
    }


    
}
