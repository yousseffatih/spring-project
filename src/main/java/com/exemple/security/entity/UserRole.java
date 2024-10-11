package com.exemple.security.entity;

import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users_role")
public class UserRole extends ClassEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;
    
    public UserRole(Long id, User user,Role role,String libelle, String code, String statut, Date dateCreation, Date dateModification, Date dateDesactivation) {
        super(libelle,code, statut, dateCreation, dateModification, dateDesactivation); 
        this.id = id;
        this.user = user;
        this.role = role;
    }
}

