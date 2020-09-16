/**
 *
 * @author kan
 */
package com.jkan1.librarymanagement.DataAccessLayer;

import com.jkan1.librarymanagement.DataAccessLayer.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
