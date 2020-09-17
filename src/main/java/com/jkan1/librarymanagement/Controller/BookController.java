/**
 *
 * @author kan
 */
package com.jkan1.librarymanagement.Controller;

import com.jkan1.librarymanagement.DataAccessLayer.BookRepository;
import com.jkan1.librarymanagement.DataAccessLayer.Models.Book;
import com.jkan1.librarymanagement.Exception.BookNotFoundException;
import com.jkan1.librarymanagement.Utility.BookValidator;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class BookController {

    private final static Logger LOGGER = Logger.getLogger("Book");

    @Autowired
    BookRepository bookOps;

    BookValidator validator = new BookValidator();

    @GetMapping("/getBooks")
    public List<Book> getBooks() {
        LOGGER.info("API /getBooks");
        List<Book> result = new ArrayList<>();
        try {
            result = bookOps.findAll();
            if (result.isEmpty()) {
                LOGGER.severe("No Books Found");
                throw new BookNotFoundException(0);
            }
        } catch (BookNotFoundException ex) {
            LOGGER.severe((ex.toString()));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Book Found", ex);
        }
        return result;
    }

    @GetMapping("/getBook")
    public Book getBook(@RequestParam long id) {
        LOGGER.info("API /getBook");
        Book resultBook = new Book();
        Optional<Book> result = bookOps.findById(id);
        try {
            if (result.isPresent() && validator.isValid(result.get())) {
                resultBook = result.get();
            } else {
                LOGGER.severe("No Book with id " + id + " Found");
                throw new BookNotFoundException(id);
            }
        } catch (BookNotFoundException ex) {
            LOGGER.severe((ex.toString()));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Book Found", ex);
        }
        return resultBook;
    }

    @GetMapping(value = "/searchBooks")
    public List<Book> searchBooks(@RequestParam(value = "q") String search) {
        LOGGER.info("API /searchBooks");
        List<Book> searchResult = new ArrayList<>();
        try {
            searchResult = bookOps.searchAllByKey(search);
            if (searchResult.isEmpty()) {
                LOGGER.severe("No Books Found");
                throw new BookNotFoundException(0);
            }
        } catch (BookNotFoundException ex) {
            LOGGER.severe((ex.toString()));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Book Found", ex);
        }
        return searchResult;
    }

    @PostMapping("/addBook")
    @ResponseStatus(HttpStatus.CREATED)
    Book newBook(@RequestBody Book newBook) {
        LOGGER.info("API /addBook");
        if (validator.isValid(newBook)) {
            newBook.setDate(new Date());
            newBook.setStatus(BookValidator.BOOK_STATUS.AVAILABLE.toString());
            return bookOps.save(newBook);
        } else {
            LOGGER.severe("New Book is not valid");
            return null;
        }
    }

}
