package com.dominogame.backend.model;


import jakarta.persistence.*;

@Entity
@Table(name="players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "playerId")
    private String playerId;

    public String getId() {
        return playerId;
    }
}