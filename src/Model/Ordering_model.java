package Model;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Ordering_model implements Entity_Model_Interface{
    private Connection conn;

    private String order_id;
    private String isbn;
    private int quantity;

    public Ordering_model(String order_id, String isbn, int quantity) {
        this.order_id = order_id;
        this.isbn = isbn;
        this.quantity = quantity;
    }

    public String get_Order_ID() {
        return order_id;
    }
    public void set_Order_ID(String order_id) {
        this.order_id = order_id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(String quant) {
        this.quantity = quantity;
    }

    @Override
    public void insertinDatabase(Connection conn) {
        // Insert into database
        try {
            PreparedStatement pstmt = conn.prepareStatement("insert into ordering values(?,?,?)");
            pstmt.setString(1, this.order_id);
            pstmt.setString(2, this.isbn);
            pstmt.setInt(3, this.quantity);
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
