package ru.rein.library.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rein.library.DTO.*;
import ru.rein.library.Services.TransactionService;

import java.time.LocalDateTime;
import java.util.ArrayList;

@RestController
@RequestMapping("/library")
@RequiredArgsConstructor
public class LibraryController {
    private final TransactionService transactionService;

    @PostMapping("/transaction")
    @Operation(summary = "POST transaction",
            description = "Создаёт RETURN транзакцию, если книга находиться у читателя, иначе создаёт TAKE транзакцию")
    public void makeTransaction(@RequestBody MakeTransactionDTO makeTransactionDTO){
        transactionService.makeTransaction(makeTransactionDTO.getIdBook(),makeTransactionDTO.getIdReader());
    }

    @GetMapping("/reader/most-reading")
    @Operation(summary = "POST /reader/most-reading",
            description = "Показывает читателя, который взял больше всего книг")
    public ReaderDto getTheMostReadingReader(){
       return transactionService.getTheMostReadingReader();
    }

    @GetMapping("/reader/list")
    @Operation(summary = "POST /reader/list",
            description = "Показывает список читателей отсортированный(по убыванию) по кол-ву несданных книг")
    public ArrayList<ReaderDto> getListReaderSortByUndeliveredBook(){
        return transactionService.getListReaderSortByUndeliveredBook();
    }

    @PostMapping("/most-popular-author")
    @Operation(summary = "POST /most-popular-author",
            description = "Показывает автора, чьи книги брали чаще всего за указанный период времени")
    public ResponseEntity<AuthorDto> getTheMostPopularAuthor(@RequestBody RangeDateDTO rangeDateDTO){
        try {
            return ResponseEntity
                    .status(200)
                    .body(transactionService.getTheMostPopularAuthor(rangeDateDTO.getStartDate().atStartOfDay(),
                                                                    rangeDateDTO.getFinishDate().atStartOfDay()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(404).body(null);
        }
    }
}
