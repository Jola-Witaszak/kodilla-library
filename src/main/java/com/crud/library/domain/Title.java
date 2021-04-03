package com.crud.library.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "titles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Title {
    @Id
    @GeneratedValue
    @Column(name = "title_id")
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String author;

    private LocalDate publicationYear;

    @OneToMany(mappedBy = "title",
              fetch = FetchType.LAZY)
    private List<Volume> volumes = new ArrayList<>();

    public Title(Long id, String title, String author, LocalDate publicationYear) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
    }
}
