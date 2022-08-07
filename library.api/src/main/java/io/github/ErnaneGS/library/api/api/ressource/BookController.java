package io.github.ErnaneGS.library.api.api.ressource;

import io.github.ErnaneGS.library.api.api.dto.BookDTO;
import io.github.ErnaneGS.library.api.api.exceptions.ApiErros;
import io.github.ErnaneGS.library.api.exception.BusinessException;
import io.github.ErnaneGS.library.api.model.entity.Book;
import io.github.ErnaneGS.library.api.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookService bookService;

    private ModelMapper modelMapper;

    public BookController(BookService bookService, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@RequestBody @Valid BookDTO bookDTO) {
        Book bookEntity = modelMapper.map(bookDTO, Book.class);
        bookEntity = bookService.save(bookEntity);
        return modelMapper.map(bookEntity, BookDTO.class);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErros handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        return new ApiErros(bindingResult);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErros handleBusinessException (BusinessException ex) {
        return new ApiErros(ex);
    }

}
