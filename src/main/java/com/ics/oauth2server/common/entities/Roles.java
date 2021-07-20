package com.ics.oauth2server.common.entities;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
public @Data class Roles {

        @Id
        @Column(name = "role_id")
        @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
        @GenericGenerator(strategy = "native", name = "native")
        private long id;

        @Column(name = "role_type")
        private String name;

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "permission_role", joinColumns = {
                @JoinColumn(name = "role_id", referencedColumnName = "role_id") }, inverseJoinColumns = {
                @JoinColumn(name = "permission_id", referencedColumnName = "permission_id") })

        private List<Permission> permissions;
}
