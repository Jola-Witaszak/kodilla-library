package com.crud.library.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String eMail;

    @NotNull
    private LocalDate accountCreated;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Rental> rentals = new HashSet<>();

    public User(String firstName, String lastName, String eMail, LocalDate accountCreated) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.accountCreated = accountCreated;
    }
}
