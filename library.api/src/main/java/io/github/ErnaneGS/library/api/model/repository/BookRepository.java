package io.github.ErnaneGS.library.api.model.repository;

import io.github.ErnaneGS.library.api.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
