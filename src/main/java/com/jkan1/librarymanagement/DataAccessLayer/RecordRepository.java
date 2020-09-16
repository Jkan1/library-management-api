/**
 *
 * @author kan
 */
package com.jkan1.librarymanagement.DataAccessLayer;

import com.jkan1.librarymanagement.DataAccessLayer.Models.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {

}
