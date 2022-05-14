
package com.thanhsaclo.bookstore.dao;

//BookDAO chua cac ham CRUD table Book

import com.thanhsaclo.bookstore.util.DBUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

//BookDAO se dc khai bao ben controller, class khac khi ta muon CRUD
//Controller/BookWS ta se khai bao bien
//          private BookDAO dao = new BookDAO();
//                          dao.insertBook();
//                          dao.removeBook();
//                          ...
//moi lan new BookDAO() thi se co 1 vung RAM BookDAO duoc tao ra

//xai class nay the nao
//controller/BookWS{
//    BookDAO dao = new BookDAO(); xua
//    BookDAO dao = BookDAO.getInstance();   
//}
//Controller/BookWS khac{
//      BookDAO dao1 = new BookDAO(); ngay xua
//      BookDAO dao1 = BookDAO.getInstance(); bien instance tro vao 
//      vung new BookDAO() truoc do(vung static) 
//      tại mọi thoi diem chi co 1 vung new BookDAO()
//KY THUAT VIET CODE MA CHI CHO PHEP 1 OBJECT XUAT HIEN -- SINGLETON DESIGN PATTERN
//

public class BookDAO{
    
    private static BookDAO instance;
    private Connection conn = DBUtil.makeConnection();

    // Cấm new trực tiếp BookDAO, chỉ đi qua con đường lấy trực tiếp BookDAO từ hàm static
    private BookDAO() {
    }

    public static BookDAO getInstance() {

        if (instance == null) {
            instance = new BookDAO();
        }
        return instance;
    }

    // Lấy ra tất cả sách trong kho
    public List<Book> getAll() {

        PreparedStatement stm;
        ResultSet rs;

        List<Book> bookList = new ArrayList();
        try {

            String sql = "SELECT * FROM BOOK";
            stm = conn.prepareStatement(sql);
            
            rs = stm.executeQuery();
            while (rs.next()) {
                bookList.add(new Book(rs.getString("Isbn"),
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getInt("Edition"),
                        rs.getInt("PublishedYear")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bookList;
    }

    // Lấy ra một cuốn sách dựa trên mã sách
    public Book getOne(String isbn) {

        PreparedStatement stm;
        ResultSet rs;

        try {

            String sql = "SELECT * FROM BOOK WHERE Isbn = ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, isbn);

            rs = stm.executeQuery();
            if (rs.next()) {
                return new Book(rs.getString("Isbn"),
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getInt("Edition"),
                        rs.getInt("PublishedYear"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // Lấy ra các cuốn sách của tác giả nào đó
    public List<Book> getAllByAuthor(String author) {

        PreparedStatement stm;
        ResultSet rs;

        List<Book> bookList = new ArrayList();

        try {

            String sql = "SELECT * FROM BOOK WHERE Author = ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, author);

            rs = stm.executeQuery();
            while (rs.next()) {
                bookList.add(new Book(rs.getString("Isbn"),
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getInt("Edition"),
                        rs.getInt("PublishedYear")));
            }
            return bookList;

        } catch (SQLException ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // Thêm mới 1 cuốn sách vào kho
    public String addOne(Book book) {

        PreparedStatement stm;
        
        try {

            String sql = "INSERT INTO BOOK (Isbn, Title, Author, Edition, PublishedYear) VALUES (?, ?, ?, ?, ?)";
            stm = conn.prepareStatement(sql);

            stm.setString(1, book.getIsbn());
            stm.setString(2, book.getTitle());
            stm.setString(3, book.getAuthor());
            stm.setInt(4, book.getEdition());
            stm.setInt(5, book.getPublishedYear());
            if (stm.executeUpdate() > 0) {
                return book.getIsbn();
            }   // Trả về mã sách vừa thêm thành công vào kho
        } catch (SQLException ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // Cập nhật thông tin một cuốn sách - không cập nhật mã sách
    public boolean updateOne(Book book) {

        PreparedStatement stm;

        try {
            String sql = ""
                    + "UPDATE BOOK "
                    + "SET Title = ?, Author = ?, Edition = ?, PublishedYear = ? "
                    + "WHERE Isbn = ?";
            stm = conn.prepareStatement(sql);
            
            stm.setString(1, book.getTitle());
            stm.setString(2, book.getAuthor());
            stm.setInt(3, book.getEdition());
            stm.setInt(4, book.getPublishedYear());
            stm.setString(5, book.getIsbn());
            if (stm.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    // Xóa một cuốn sách dựa trên mã sách
    public boolean deleteOne(String isbn) {

        PreparedStatement stm;

        try {

            String sql = "DELETE FROM BOOK WHERE Isbn = ?";
            stm = conn.prepareStatement(sql);
            
            stm.setString(1, isbn);
            if (stm.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    ////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {

        // 1. Test select *
//        System.out.println("All of books: \n" + getInstance().getAll()); //gọi thầm tên em
        
        // 2. Test select where isbn = ?
//        System.out.println("A book by id: " + getInstance().getOne("2518407786529"));
        
        // 3. Test select where author = ?
        //System.out.println("Books by author: " + getInstance().getAllByAuthor("Paulo Coelho"));
        
        // 4. Test update a book
        getInstance().updateOne(new Book("2518407786529", "Nhà giả kim", "Paulo Coelho", 2, 2021));
        
        // 3. Test select where author = ? again
        //System.out.println(getInstance().getAllByAuthor("Paulo Coelho"));
        
        // 5. Test delete a book
        //getInstance().deleteOne("2518407786529");
        
        // 1. Test select * again
        //System.out.println("All of books: \n" + getInstance().getAll()); //gọi thầm tên em
        
        // 6. Test add a new book
//        getInstance().addOne(new Book("2518407786529", "Nhà giả kim", "Paulo Coelho", 1, 2013));
        
        // 1. Test select * again
        System.out.println("All of books: \n" + getInstance().getAll()); //gọi thầm tên em
        
    }
}
