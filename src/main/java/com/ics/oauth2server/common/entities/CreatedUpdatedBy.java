package com.ics.oauth2server.common.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "created_updated_by")
public class CreatedUpdatedBy {

    @Id
    @Column(name = "created_updated_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(strategy = "native", name = "native")
    private long id;

    @Column(name="description")
    private String description;

}
