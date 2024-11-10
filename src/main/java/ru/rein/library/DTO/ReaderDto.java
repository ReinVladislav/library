package ru.rein.library.DTO;

import jakarta.persistence.*;
import lombok.Data;
import ru.rein.library.Models.Enums.Gender;
import ru.rein.library.Models.Reader;
import ru.rein.library.Models.Transaction;

import java.util.List;

@Data
public class ReaderDto {
    private String phoneNumber;

    private String name;

    private String surname;

    private Gender gender;

    public ReaderDto(Reader reader){
        phoneNumber = reader.getPhoneNumber();
        name = reader.getName();
        surname = reader.getSurname();
        gender = reader.getGender();
    }
}
