package io.github.ErnaneGS.library.api.service;

import io.github.ErnaneGS.library.api.exception.BusinessException;
import io.github.ErnaneGS.library.api.model.entity.Book;
import io.github.ErnaneGS.library.api.model.repository.BookRepository;
import io.github.ErnaneGS.library.api.service.BookService;
import io.github.ErnaneGS.library.api.service.BookServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {

    BookService bookService;

    @Mock
    BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        this.bookService = new BookServiceImpl(bookRepository);
    }

    @Test
    @DisplayName("Deve salvar um livro")
    public void saveBookTest() {
        //cenário
        Book book = Book.builder().author("Fulano").isbn("123").title("As aventuras").build();
        when(bookRepository.save(book))
                .thenReturn(Book.builder().id(1L).author("Fulano").isbn("123").title("As aventuras").build());

        //execução
        Book saveBook = bookService.save(book);

        //verificação
        assertThat(saveBook.getId()).isNotNull();
        assertThat(saveBook.getIsbn()).isEqualTo("123");
        assertThat(saveBook.getTitle()).isEqualTo("As aventuras");
        assertThat(saveBook.getAuthor()).isEqualTo("Fulano");

    }

    @Test
    @DisplayName("Deve lançar erro de negocio ao tentar salvar um livro com isbn duplicado")
    public void shouldNotSaveABookWithDuplicatedISBN(){
        //cenario
        Book book = createValidBook();
        when( bookRepository.existsByIsbn(Mockito.anyString()) ).thenReturn(true);

        //execucao
        Throwable exception = Assertions.catchThrowable(() -> bookService.save(book));

        //verificacoes
        assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessage("Isbn já cadastrado.");

        Mockito.verify(bookRepository, Mockito.never()).save(book);

    }

    private Book createValidBook() {
        return Book.builder().isbn("123").author("Fulano").title("As aventuras").build();
    }


}
