
package com.thanhsaclo.bookstore.ws;

//no chinh la 1 cai Servlet, tra ve data thô JSON 

import com.thanhsaclo.bookstore.dao.Book;
import com.thanhsaclo.bookstore.dao.BookDAO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.*;

//thong qua cac ham cua class
//van de: lam sao goi dc class nay tu app khac, từ nơi khác
//gọi qua URL - mapping
//map url vao 1 method cua 1 class nao do
//tuy nhien, Tomcat chi quan tam den cai request noi chung
//nhu trong project nay, code nay co 2 tp khac biet
//-servlet/jsp/html dung cho web truyen thong
//-phan api tra ve JSON data thô
//lam sao pb cai nao la cai nao
//ta phai cau hinh rieng cái URL dac biet danh cho API
//URL truyen thống se dành cho web app xua nay
//app chung ta dang la 2 in 1: vua cho end-user, vua cho API
//localhost:2511/ten-app -> truyen thong web app

//localhost:2511/ten-app/api/... goi API cua minh
// /api hay /ahihi muon gi cung dc mien cau hinh lai la duoc

@Path("books")
// http://books.com/ -> end-user
// http://books.com/api/books -> den duoc class nay!!!!!!
public class BookWS {
    
    // Cầu nối tới CSDL
    private BookDAO dao = BookDAO.getInstance(); // Sử dụng Singleton design pattern
    
    @GET //sờ hàm nay qua method GET cua http protocol
    @Path("hello") // /api/books/hello
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHelloFromAPI(){
        return "This is 1st message comes from my own first API";
    }
    @GET //sờ hàm nay qua method GET cua http protocol
    @Path("abook") // /api.books/abook
    @Produces(MediaType.APPLICATION_JSON)
    public Book getABook(){
        return new Book("4018690253374", "Sống Thực Tế Giữa Đời Thực Dụng", "Dịch giả B.Nhung", 1, 2018);
    }
    
    public List<Book> getBooks(){
        return dao.getAll();
    }
    @GET //sờ hàm nay qua method GET cua http protocol
    //ham tra ve mot cuon sach bat ky theo ma so sach muon tim
    @Path("{isbn}")//ma isbn thay doi theo du lieu
    @Produces(MediaType.APPLICATION_JSON)
    public Book getABookByIsbn(@PathParam("isbn") String isbn){
        return dao.getOne(isbn);
    }
}
