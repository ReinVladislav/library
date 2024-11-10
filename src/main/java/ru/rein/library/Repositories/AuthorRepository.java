package ru.rein.library.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rein.library.Models.Author;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
}
