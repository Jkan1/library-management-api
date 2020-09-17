/**
 *
 * @author kan
 */
package com.jkan1.librarymanagement.Utility;

import com.jkan1.librarymanagement.DataAccessLayer.Models.User;

public class UserValidator {

    public boolean isValid(User user) {
        if (user.getName().equals("") || user.getName() == null) {
            return false;
        }
        return true;
    }

    public static enum USER_STATUS {
        ACTIVE, INACTIVE, DELETED;
    }

    public static final float DEFAULT_BALANCE = 10;
}
