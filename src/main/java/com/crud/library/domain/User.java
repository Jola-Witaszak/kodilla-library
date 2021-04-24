package com.crud.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private LocalDate created;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private final Set<Rental> rentals = new HashSet<>();

    public User( @NotNull String firstName, @NotNull String lastName, @NotNull String email, LocalDate created) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.created = created;
    }
}
