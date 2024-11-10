package ru.rein.library.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.rein.library.Models.Enums.TypeOperation;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "type_operation")
    @Enumerated(EnumType.STRING)
    private TypeOperation typeOperation;

    @Column(name = "date_time_operation")
    private LocalDateTime dateTimeOperation;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "reader_id", nullable = false, foreignKey = @ForeignKey(name = "fk_transaction_reader"))
    private Reader reader;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false, foreignKey = @ForeignKey(name = "fk_transaction_book"))
    private Book book;

    public Transaction(TypeOperation typeOperation, Reader reader, Book book) {
        this.typeOperation = typeOperation;
        this.reader = reader;
        this.book = book;
        dateTimeOperation = LocalDateTime.now();
    }
}
