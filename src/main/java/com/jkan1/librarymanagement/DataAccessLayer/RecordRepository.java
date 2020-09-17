/**
 *
 * @author kan
 */
package com.jkan1.librarymanagement.DataAccessLayer;

import com.jkan1.librarymanagement.DataAccessLayer.Models.Record;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecordRepository extends JpaRepository<Record, Long> {

    @Query(value = "SELECT * FROM record WHERE record.user_id = :id ", nativeQuery = true)
    List<Record> findAllByUserId(@Param("id") long id);

    @Query(value = "SELECT * FROM record WHERE record.user_id = :userId AND record.book_id = :bookId AND record.status = 'ALLOTTED' ", nativeQuery = true)
    Record findActiveRecordById(@Param("userId") long userId, @Param("bookId") long bookId);

}
