package ru.rein.library.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rein.library.Models.Book;

@Repository
public interface BookRepository extends CrudRepository<Book,Long> {
}
