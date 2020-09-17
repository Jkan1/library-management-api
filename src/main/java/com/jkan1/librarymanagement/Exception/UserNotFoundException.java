/**
 *
 * @author kan
 */
package com.jkan1.librarymanagement.Exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(long id) {
        super("User with id " + id + " not found!");
    }
}
