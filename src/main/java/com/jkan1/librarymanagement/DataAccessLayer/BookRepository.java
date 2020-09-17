/**
 *
 * @author kan
 */
package com.jkan1.librarymanagement.DataAccessLayer;

import com.jkan1.librarymanagement.DataAccessLayer.Models.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "SELECT * FROM book WHERE book.name LIKE `%:search%` OR book.author LIKE `%:search%` ", nativeQuery = true)
    List<Book> searchAllByKey(String search);

}
