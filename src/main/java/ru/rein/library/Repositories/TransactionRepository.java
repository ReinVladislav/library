package ru.rein.library.Repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.rein.library.Models.Book;
import ru.rein.library.Models.Reader;
import ru.rein.library.Models.Transaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    Optional<Transaction> findTopByBookAndReaderOrderByDateTimeOperationDesc(Book book, Reader reader);

    @Query("SELECT t.reader.id FROM Transaction t WHERE t.typeOperation = 'TAKE' GROUP BY t.reader.id ORDER BY COUNT(DISTINCT t.book.id) DESC LIMIT 1")
    Optional<Long> findIdTheMostReadingReader();

    @Query("SELECT " +
            "(COUNT(CASE WHEN t.typeOperation = 'TAKE' THEN 1 END) - " +
            "COUNT(CASE WHEN t.typeOperation = 'RETURN' THEN 1 END)) " +
            "FROM Transaction t WHERE t.reader.id = :idReader")
    Integer getTransactionDifferenceForReader(@Param("idReader") Long idReader);

    @Query("SELECT a.id " +
            "FROM Transaction t " +
            "JOIN Book b ON t.book.id = b.id " +
            "JOIN BooksAuthors ba ON ba.book.id = b.id " +
            "JOIN Author a ON ba.author.id = a.id " +
            "WHERE t.typeOperation = 'TAKE' " +
            "AND t.dateTimeOperation BETWEEN :startDate AND :endDate " +
            "GROUP BY a.id " +
            "ORDER BY COUNT(t.id) DESC LIMIT 1")
    Optional<Long> findMostFrequentAuthorIdForTakeOperationBetweenDates(@Param("startDate") LocalDateTime startDate,
                                                                        @Param("endDate") LocalDateTime endDate);
}
