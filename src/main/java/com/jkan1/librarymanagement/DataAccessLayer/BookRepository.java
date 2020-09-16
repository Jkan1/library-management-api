/**
 *
 * @author kan
 */
package com.jkan1.librarymanagement.DataAccessLayer;

import com.jkan1.librarymanagement.DataAccessLayer.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
