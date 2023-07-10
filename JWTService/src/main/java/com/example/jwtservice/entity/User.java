package com.example.jwtservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID user_id;

    @Column(nullable = false)
    String login;

    @Column(nullable = false)
    String password;

    @Column
    String email;
}
