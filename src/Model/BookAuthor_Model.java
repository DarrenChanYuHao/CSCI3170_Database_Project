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
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkExistinDatabase(Connection conn) {
        // Check if the book_author exists in the database
        try {
            PreparedStatement pstmt = conn.prepareStatement("select * from book_author where isbn = ? and author_name = ?");
            pstmt.setString(1, this.isbn);
            pstmt.setString(2, this.author_name);
            pstmt.execute();

            if (pstmt.getResultSet().next()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
