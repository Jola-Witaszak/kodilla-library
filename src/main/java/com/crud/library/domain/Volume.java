package com.crud.library.domain;

import lombok.*;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "volumes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Volume {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "title_id")
    private Title title;

    @OneToMany(mappedBy = "volume",
               fetch = FetchType.LAZY)
    private List<Rental> rentals = new ArrayList<>();

    public Volume(Long id, Status status, Title title) {
        this.id = id;
        this.status = status;
        this.title = title;
    }
}
