package Model;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class BookAuthor_Model implements Entity_Model_Interface{
    private Connection conn;

    private String isbn;
    private String author_name;

    public BookAuthor_Model(String isbn, String author_name){
        this.isbn = isbn;
        this.author_name = author_name;
    }

    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthorName() {
        return author_name;
    }
    public void setAuthorName(String name) {
        this.author_name = name;
    }

    @Override
    public void insertinDatabase(Connection conn) {
        // Insert into database
        try {
            PreparedStatement pstmt = conn.prepareStatement("insert into book_author values(?,?)");
            pstmt.setString(1, this.isbn);
            pstmt.setString(2, this.author_name);
            pstmt.execute();
            
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    @Override
    public void updateinDatabase() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateinDatabase'");
    }

    @Override
    public void readfromDatabase() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readfromDatabase'");
    }

    @Override
    public void deleteinDatabase() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteinDatabase'");
    }
}
