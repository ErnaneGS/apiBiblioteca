package io.github.ErnaneGS.library.api.service;

import io.github.ErnaneGS.library.api.model.entity.Book;
import io.github.ErnaneGS.library.api.model.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(Book book) {
        if (bookRepository.save())
            return bookRepository.save(book);
    }

}
