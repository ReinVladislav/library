package ru.rein.library.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "author")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "date_birth")
    private LocalDate dateBirth;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="books_authors",
            joinColumns=  @JoinColumn(name="author_id", referencedColumnName="id"),
            inverseJoinColumns= @JoinColumn(name="book_id", referencedColumnName="id") )
    private Set<Book> books = new HashSet<>();
}
