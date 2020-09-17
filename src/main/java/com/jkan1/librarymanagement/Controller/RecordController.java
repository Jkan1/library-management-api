/**
 *
 * @author kan
 */
package com.jkan1.librarymanagement.Controller;

import com.jkan1.librarymanagement.DataAccessLayer.BookRepository;
import com.jkan1.librarymanagement.DataAccessLayer.Models.Book;
import com.jkan1.librarymanagement.DataAccessLayer.RecordRepository;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.jkan1.librarymanagement.DataAccessLayer.Models.Record;
import com.jkan1.librarymanagement.DataAccessLayer.Models.User;
import com.jkan1.librarymanagement.DataAccessLayer.UserRepository;
import com.jkan1.librarymanagement.Utility.BookValidator;
import com.jkan1.librarymanagement.Utility.RecordValidator;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class RecordController {

    private final static Logger LOGGER = Logger.getLogger("Record");

    @Autowired
    RecordRepository recordOps;

    @Autowired
    BookRepository bookOps;

    @Autowired
    UserRepository userOps;

    @GetMapping("/getRecord")
    public Record getUser(@RequestParam long id) {
        LOGGER.info("API /getRecord");
        Record resultRecord = new Record();
        Optional<Record> result = recordOps.findById(id);
        try {
            if (result.isPresent()) {
                resultRecord = result.get();
            } else {
                LOGGER.severe("No Record with id " + id + " Found");
                throw new Exception();
            }
        } catch (Exception ex) {
            LOGGER.severe((ex.toString()));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Record Found", ex);
        }
        return resultRecord;
    }

    @PostMapping("/issueBook")
    @ResponseStatus(HttpStatus.OK)
    Record issueBook(@RequestBody Record issueData) {
        LOGGER.info("API /issueBook");
        Optional<User> userResult = userOps.findById(issueData.getUserId());
        Optional<Book> bookResult = bookOps.findById(issueData.getBookId());
        if (userResult.isEmpty()) {
            LOGGER.severe("No User with id " + issueData.getUserId() + " Found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No User Found");
        } else if (bookResult.isEmpty()) {
            LOGGER.severe("No Book with id " + issueData.getBookId() + " Found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Book Found");
        }
        Book bookData = bookResult.get();
        if (bookData.getStatus().equals(BookValidator.BOOK_STATUS.AVAILABLE.toString())) {
            bookData.setStatus(BookValidator.BOOK_STATUS.UNAVAILABLE.toString());
            bookData.setDate(new Date());
            bookOps.save(bookData);
            issueData.setAllotmentDate(new Date());
            issueData.setReturnDate(new Date(System.currentTimeMillis() + 864000000));
            issueData.setActualReturnDate(null);
            issueData.setUpdatedAt(new Date());
            issueData.setStatus(RecordValidator.RECORD_STATUS.ALLOTTED.toString());
            return recordOps.save(issueData);
        } else {
            LOGGER.severe("Book with id " + issueData.getBookId() + " Unavailable");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Book Found");
        }
    }

    @PostMapping("/returnBook")
    @ResponseStatus(HttpStatus.OK)
    Record returnBook(@RequestBody Record returnData) {
        LOGGER.info("API /returnBook");
        Optional<User> userResult = userOps.findById(returnData.getUserId());
        Optional<Book> bookResult = bookOps.findById(returnData.getBookId());
        if (userResult.isEmpty()) {
            LOGGER.severe("No User with id " + returnData.getUserId() + " Found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No User Found");
        } else if (bookResult.isEmpty()) {
            LOGGER.severe("No Book with id " + returnData.getBookId() + " Found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Book Found");
        }
        Record activeRecord = recordOps.findActiveRecordById(returnData.getUserId(), returnData.getBookId());
        if (activeRecord == null) {
            LOGGER.severe("No Active Record with user id " + returnData.getUserId()
                    + " & book id " + returnData.getBookId() + " Found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Record Found");
        }
        activeRecord.setActualReturnDate(new Date());
        activeRecord.setUpdatedAt(new Date());
        if (activeRecord.getReturnDate().before(new Date())) {
            activeRecord.setStatus(RecordValidator.RECORD_STATUS.RETURN_DATE_EXPIRED.toString());
        } else {
            activeRecord.setStatus(RecordValidator.RECORD_STATUS.RETURNED.toString());
        }
        Book bookData = bookResult.get();
        bookData.setDate(new Date());
        bookData.setStatus(BookValidator.BOOK_STATUS.AVAILABLE.toString());
        bookOps.save(bookData);
        return recordOps.save(activeRecord);
    }

    @GetMapping("/getUserRecords")
    public List<Record> getUserRecords(@RequestParam long id, @RequestParam(value = "status") String status) {
        LOGGER.info("API /getUserRecords");
        List<Record> resultList = new ArrayList<>();
        try {
            if (status.toUpperCase().equals(RecordValidator.RECORD_STATUS.ALL.toString())) {
                resultList = recordOps.findAllByUserId(id);
                if (resultList.isEmpty()) {
                    LOGGER.severe("No Record with user id " + id + " Found");
                    throw new Exception();
                }
                return resultList;
            } else {
                List<Record> tempList = recordOps.findAllByUserId(id);
                for (Record record : tempList) {
                    if (record.getstatus().equals(status.toUpperCase())) {
                        resultList.add(record);
                    }
                }
                if (resultList.isEmpty()) {
                    LOGGER.severe("No Record with user id " + id + " & status " + status + " Found");
                    throw new Exception();
                }
                return resultList;
            }
        } catch (Exception ex) {
            LOGGER.severe((ex.toString()));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Record Found", ex);
        }
    }

}
