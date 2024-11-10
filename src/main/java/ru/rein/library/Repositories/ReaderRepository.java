package ru.rein.library.Repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rein.library.Models.Reader;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ReaderRepository extends CrudRepository<Reader, Long> {
}
