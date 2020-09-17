/**
 *
 * @author kan
 */
package com.jkan1.librarymanagement.Utility;

import com.jkan1.librarymanagement.DataAccessLayer.Models.Book;

public class BookValidator {

    public boolean isValid(Book book) {
        if (book == null || book.getName() == null || book.getName().equals("")) {
            return false;
        }
        return true;
    }

    public static enum BOOK_STATUS {
        AVAILABLE, UNAVAILABLE, INACTIVE, DELETED;
    }

}
