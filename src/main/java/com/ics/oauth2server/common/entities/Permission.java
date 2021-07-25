package com.ics.oauth2server.common.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "permission")
public @Data class Permission {

    @Id
    @Column(name = "permission_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(strategy = "native", name = "native")
    private long id;

    @Column(name = "permission_type",unique = true)
    private String name;

}
