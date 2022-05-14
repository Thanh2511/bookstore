
package com.thanhsaclo.bookstore.dao;

import java.io.Serializable;

//Có vài loại Interface:
//1. Marker Interface: loai interface ma khong y/c class implements 
//no phai co code
//interface k co ham abstract-bao hieu cho JVM biet can phai lam gi
//Serializable: co nghia la tuan tu
//bao hieu cho JVM biet la chi dua dac tinh chu khong dua hanh vi
//bẻ từng byte trong dac tinh bien thanh cai luong Serializable
//tuan tu dua vao DB
//cho nay k co Serializable tao đố mày xuống DB được
//2. Functional Interface: chi co duy nhat 1 ham abstract
//dung Anonymous -> chuyen sang lamda luon
//functional interface chi choi voi Lamda Expression
//Comparable/Comparator
//3. Nhung interface con lai: implements, viet code cho tat ca cac ham...

//Dependency Lombok, giup phan Constructure, Get/Set ngan gon hon

public class Book implements Serializable{
    
    private String isbn;
    private String title;
    private String author;
    private int edition;
    private int publishedYear;

    public Book() {
    }

    public Book(String isbn, String title, String author, int edition, int publishedYear) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.edition = edition;
        this.publishedYear = publishedYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }
    
    
    
}
