package ru.rein.library.DTO;

import jakarta.persistence.*;
import lombok.Data;
import ru.rein.library.Models.Author;
import ru.rein.library.Models.Book;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class AuthorDto {

    private String name;
    private String surname;
    private LocalDate dateBirth;

    public AuthorDto(Author author) {
        this.name = author.getName();
        this.surname = author.getSurname();
        this.dateBirth = author.getDateBirth();
    }
}
