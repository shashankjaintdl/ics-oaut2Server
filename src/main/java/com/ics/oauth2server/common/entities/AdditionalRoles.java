package com.ics.oauth2server.common.entities;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "additional_roles")
public @Data class AdditionalRoles {
    @Id
    @Column(name = "additional_role_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(strategy = "native", name = "native")
    private long id;

    @Column(name = "additional_role_type")
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_acc_id",referencedColumnName = "user_acc_id")
    private UserAccount userAccount;
}
