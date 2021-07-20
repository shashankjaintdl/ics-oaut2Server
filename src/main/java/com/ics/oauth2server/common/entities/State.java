package com.ics.oauth2server.common.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "state")

public @Data class State {

    @Id
    @Column(name = "state_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(strategy = "native", name = "native")
    private long id;

    @Column(name="state_description")
    private String description;

}
