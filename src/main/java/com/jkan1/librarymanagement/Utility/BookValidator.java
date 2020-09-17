/**
 *
 * @author kan
 */
package com.jkan1.librarymanagement.Utility;

import com.jkan1.librarymanagement.DataAccessLayer.Models.Book;

public class BookValidator {

    public boolean isValid(Book book) {
        if (book == null || book.getName().equals("") || book.getName() == null) {
            return false;
        }
        return true;
    }

    public static enum BOOK_STATUS {
        AVAILABLE, UNAVAILABLE, INACTIVE, DELETED;
    }

}
