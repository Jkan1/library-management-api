/**
 *
 * @author kan
 */
package com.jkan1.librarymanagement.Exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(long id) {
        super("Book with id " + id + " not found!");
    }

}
