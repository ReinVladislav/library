package ru.rein.library.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rein.library.DTO.AuthorDto;
import ru.rein.library.DTO.ReaderDto;
import ru.rein.library.Models.Book;
import ru.rein.library.Models.Enums.TypeOperation;
import ru.rein.library.Models.Reader;
import ru.rein.library.Models.Transaction;
import ru.rein.library.Repositories.AuthorRepository;
import ru.rein.library.Repositories.BookRepository;
import ru.rein.library.Repositories.ReaderRepository;
import ru.rein.library.Repositories.TransactionRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final AuthorRepository authorRepository;


    public void makeTransaction(long idBook, long idReader){
        Optional<Reader> readerOptional = readerRepository.findById(idReader);
        Optional<Book> bookOptional = bookRepository.findById(idBook);
        if(readerOptional.isPresent() && bookOptional.isPresent()){
            Reader reader = readerOptional.get();
            Book book = bookOptional.get();
            Transaction newTransaction;
            Optional<Transaction> transactionOptional = transactionRepository
                    .findTopByBookAndReaderOrderByDateTimeOperationDesc(book,reader);
            if(transactionOptional.isPresent()){
                if(transactionOptional.get().getTypeOperation()==TypeOperation.TAKE){
                    newTransaction = new Transaction(TypeOperation.RETURN,reader,book);
                }else {
                    newTransaction = new Transaction(TypeOperation.TAKE,reader,book);
                }
            }else {
                newTransaction = new Transaction(TypeOperation.TAKE,reader,book);
            }
            transactionRepository.save(newTransaction);
        }else{
            throw new IllegalArgumentException("Некорректный ID");
        }
    }

    public ReaderDto getTheMostReadingReader(){
        Optional<Long> idReader = transactionRepository.findIdTheMostReadingReader();
        if(idReader.isPresent()){
            Reader reader = readerRepository.findById(idReader.get()).get();
            return new ReaderDto(reader);
        }else {
            throw new IllegalArgumentException("Читатель не найден");
        }

    }
    public AuthorDto getTheMostPopularAuthor(LocalDateTime start, LocalDateTime finish) throws Exception {
        Optional<Long> authorID = transactionRepository.findMostFrequentAuthorIdForTakeOperationBetweenDates(start,finish);
        if(authorID.isPresent()){
            return new AuthorDto(authorRepository.findById(authorID.get()).get());
        }else {
            throw new IllegalArgumentException("За выбранный промежуток времени книги никто не брал");
        }

    }

    public ArrayList<ReaderDto> getListReaderSortByUndeliveredBook(){
        ArrayList<ReaderDto> readerDtos = new ArrayList<>();
        HashMap<Reader, Integer> readers = new HashMap<>();
        ArrayList<Reader> allReaders = (ArrayList<Reader>) readerRepository.findAll();
        for(Reader reader:allReaders){
            readers.put(reader,transactionRepository.getTransactionDifferenceForReader(reader.getId()));
        }
        List<Map.Entry<Reader, Integer>> list = new ArrayList<>(readers.entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        for (Map.Entry<Reader, Integer> entry : list) {
            readerDtos.add(new ReaderDto(entry.getKey()));
        }
        return readerDtos;
    }

}
