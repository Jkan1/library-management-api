/**
 *
 * @author kan
 */
package com.jkan1.librarymanagement.DataAccessLayer.Models;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "books_allotted")
    private Book[] booksAllotted;

    @Column(name = "created_at")
    private Date date;

    @Column(name = "status")
    private String status;

    @Column(name = "balance")
    private float balance;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Book[] getBooksAllotted() {
        return booksAllotted;
    }

    public void setBooksAllotted(Book[] booksAllotted) {
        this.booksAllotted = booksAllotted;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

}
