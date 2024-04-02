package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Customer_Model implements Entity_Model_Interface{

    private String customer_id;
    private String name;
    private String shipping_address;
    private String credit_card_no;
    private Connection conn;

    public Customer_Model(String customer_id, String name, String shipping_address, String credit_card_no) {
        this.customer_id = customer_id;
        this.name = name;
        this.shipping_address = shipping_address;
        this.credit_card_no = credit_card_no;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
    }

    public String getCredit_card_no() {
        return credit_card_no;
    }

    public void setCredit_card_no(String credit_card_no) {
        this.credit_card_no = credit_card_no;
    }

    @Override
    public void insertinDatabase(Connection conn) {
        // Insert into database
            try {
                PreparedStatement pstmt = conn.prepareStatement("insert into customer values(?,?,?,?)");
                pstmt.setString(1, this.customer_id);
                pstmt.setString(2, this.name);
                pstmt.setString(3, this.shipping_address);
                pstmt.setString(4, this.credit_card_no);
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
