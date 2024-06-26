package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Book_Model implements Entity_Model_Interface{

    private String title;
    private String isbn;
    private int unit_price;
    private int number_of_copies;
    private Connection conn;

    public Book_Model(String isbn, String title, int unit_price, int number_of_copies) {
        this.isbn = isbn;
        this.title = title;
        this.unit_price = unit_price;
        this.number_of_copies = number_of_copies;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getNumber_of_copies() {
        return number_of_copies;
    }

    public void setNumber_of_copies(int number_of_copies) {
        this.number_of_copies = number_of_copies;
    }

    public int getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(int unit_price) {
        this.unit_price = unit_price;
    }

    @Override
    public void insertinDatabase(Connection conn) {
        // Insert into database
            try {
                PreparedStatement pstmt = conn.prepareStatement("insert into book values(?,?,?,?)");
                pstmt.setString(1, this.isbn);
                pstmt.setString(2, this.title);
                pstmt.setInt(3, this.unit_price);
                pstmt.setInt(4, this.number_of_copies);
                pstmt.execute();
            } catch (Exception e) {
                System.out.println("Error Code: " + e.getMessage());
            }

        }

    @Override
    public boolean checkExistinDatabase(Connection conn) {
        // Check if book exists in database
        try {
            PreparedStatement pstmt = conn.prepareStatement("select * from book where isbn = ?");
            pstmt.setString(1, this.isbn);
            pstmt.execute();

            if(pstmt.getResultSet().next()) {
                System.out.println("Book already exists in the database");
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error Code: " + e.getMessage());
        }
        return false;
    }
}