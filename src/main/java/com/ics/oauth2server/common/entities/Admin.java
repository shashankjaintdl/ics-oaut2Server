package com.ics.oauth2server.common.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity(name = "Admin")
@Table(name = "admin")
public @Data class Admin extends BaseEntity{

    @Id
    @Column(name = "admin_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(strategy = "native", name = "native")
    private Long id;

    @Column(name = "username",
            nullable = false,
            unique = true,
            updatable = false,
            columnDefinition = "TEXT",
            length = 30)
    private String username;

    @Column(name = "email_id",
            nullable = false,
            unique = true,
            updatable = false,
            columnDefinition = "TEXT",
            length = 100)
    private String emailId;


    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", emailId='" + emailId + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
