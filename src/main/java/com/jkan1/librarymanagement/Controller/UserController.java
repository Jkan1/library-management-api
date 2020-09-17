/**
 *
 * @author kan
 */
package com.jkan1.librarymanagement.Controller;

import com.jkan1.librarymanagement.DataAccessLayer.Models.User;
import com.jkan1.librarymanagement.DataAccessLayer.UserRepository;
import com.jkan1.librarymanagement.Exception.UserNotFoundException;
import com.jkan1.librarymanagement.Utility.UserValidator;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UserController {

    private final static Logger LOGGER = Logger.getLogger("Book");

    @Autowired
    UserRepository userOps;

    UserValidator validator = new UserValidator();

    @GetMapping("/getUser")
    public User getUser(@RequestParam long id) {
        LOGGER.info("API /getUser");
        User resultUser = new User();
        Optional<User> result = userOps.findById(id);
        try {
            if (result.isPresent() && validator.isValid(result.get())) {
                resultUser = result.get();
            } else {
                LOGGER.severe("No User with id " + id + " Found");
                throw new UserNotFoundException(id);
            }
        } catch (UserNotFoundException ex) {
            LOGGER.severe((ex.toString()));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No User Found", ex);
        }
        return resultUser;
    }

    @PostMapping("/addUser")
    @ResponseStatus(HttpStatus.CREATED)
    User newUser(@RequestBody User newUser) {
        LOGGER.info("API /addUser");
        if (validator.isValid(newUser)) {
            newUser.setDate(new Date());
            newUser.setBalance(UserValidator.DEFAULT_BALANCE);
            newUser.setStatus(UserValidator.USER_STATUS.ACTIVE.toString());
            return userOps.save(newUser);
        } else {
            LOGGER.severe("New User is not valid");
            return null;
        }
    }

    @PutMapping("/updateUser")
    @ResponseStatus(HttpStatus.OK)
    User updateUser(@RequestBody User updatedUser) {
        LOGGER.info("API /updateUser");
        User resultUser;
        if (validator.isValid(updatedUser)) {
            try {
                Optional<User> result = userOps.findById(updatedUser.getId());
                if (result.isPresent() && validator.isValid(result.get())) {
                    resultUser = result.get();
                } else {
                    LOGGER.severe("No User with id " + updatedUser.getId() + " Found");
                    throw new UserNotFoundException(updatedUser.getId());
                }
                resultUser.setName(updatedUser.getName());
                resultUser.setDate(new Date());
                return userOps.save(resultUser);
            } catch (UserNotFoundException ex) {
                LOGGER.severe((ex.toString()));
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No User Found", ex);
            }
        }
        LOGGER.severe("New User is not valid");
        return null;
    }

}
