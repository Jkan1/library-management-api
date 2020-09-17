/**
 *
 * @author kan
 */
package com.jkan1.librarymanagement.Controller;

import com.jkan1.librarymanagement.DataAccessLayer.RecordRepository;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.jkan1.librarymanagement.DataAccessLayer.Models.Record;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RecordController {

    private final static Logger LOGGER = Logger.getLogger("Book");

    @Autowired
    RecordRepository recordOps;

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

}
