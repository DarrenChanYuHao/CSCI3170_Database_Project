package Model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class Order_model implements Entity_Model_Interface{

    private Connection conn;

    private String order_id;
    private Date o_date;
    private String shipping_status;
    private int charge;
    private String customer_id;


    public Order_model(String order_id, Date date, String shipping_status, int charge, String customer_id) {
        this.order_id = order_id;
        this.o_date = date;
        this.shipping_status = shipping_status;
        this.charge = charge;
        this.customer_id = customer_id;
    }

    public String get_Order_ID() {
        return order_id;
    }
    public void set_Order_ID(String order_id) {
        this.order_id = order_id;
    }

    public Date get_O_Date() {
        return o_date;
    }
    public void set_O_Date(Date o_date) {
        this.o_date = o_date;
    }

    public String get_Shipping_Status() {
        return shipping_status;
    }
    public void set_Shipping_Status(String shipping_status) {
        this.shipping_status = shipping_status;
    }

    public int get_Charge() {
        return charge;
    }
    public void set_Charge(int charge) {
        this.charge = charge;
    }

    public String get_Customer_ID() {
        return customer_id;
    }
    public void set_Customer_ID(String CID) {
        this.customer_id = CID;
    }

    @Override
    public void insertinDatabase(Connection conn) {
        // Insert into database
        try {
            PreparedStatement pstmt = conn.prepareStatement("insert into orders values(?,?,?,?,?)");
            pstmt.setString(1, this.order_id);
            pstmt.setDate(2, this.o_date);
            pstmt.setString(3, this.shipping_status);
            pstmt.setInt(4, this.charge);
            pstmt.setString(5, this.customer_id);
            pstmt.execute();
            
        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println("Error Code: " + e.getErrorCode());
        }

    }
}
