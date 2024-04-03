package Model;
import java.sql.*;

import oracle.net.aso.c;


public class BookStore_Operations {

    // Attributes
    private Connection conn;
    
    // Constructor
    public BookStore_Operations(Connection conn) {
        this.conn = conn;
    }

    // Order Update (Get Order Details)
    public int orderUpdate_getOrderDetails(String input){
        /*
         *   This method is to be used to get the order details of a specific order for use in the order update operations
         *   Input: order_id
         *   Output: order details
         *           0 - order status is Y (shipped)
         *           1 - order status is N (not shipped) and quantity is more than 0 (Shipping Status can be updated)
         *           2 - order status is N (not shipped) and quantity is 0 (TBC : Waiting for clarification from TA)
         *           3 - order does not exist
         *           4 - SQL Exception
         */

        try {

            // For our prepared statement, we will select the shipping status from ORDERS and the quantity from ORDERING
            // We will join the two tables on the order_id and search for the input order_id
            PreparedStatement ptsm = 
                conn.prepareStatement("SELECT o.shipping_status, ord.quantity FROM orders o JOIN ordering ord ON o.order_id = ord.order_id WHERE o.order_id = ?");
            ptsm.setString(1, input);
            ResultSet rs = ptsm.executeQuery();

            int quantity = 0;
            String status = "";

            // We have to iterate through the set as there can be multiple rows with the same order_id due to multiple books in an order
            // We will want to add up all the quantity of the book.
            while (rs.next()) {
                status = rs.getString("shipping_status");
                quantity = quantity + rs.getInt("quantity");
            }

            // If it is last row, print the status and quantity of the order
            if(status != "") {
                System.out.println("Order " + input + " is " + status + " and has " + quantity + " books.");
                
                // If order status is N, and quantity is more than 0, return true
                if (status.equals("N") && quantity > 0) {
                    return 1;
                }

                // If order status is Y, return false
                else if (status.equals("Y")) {
                    return 0;
                }

                // If order status is N, and quantity is 0, return false
                else {
                    return 2;
                }
            
            }
            else{
                System.out.println("Order " + input + " does not exist.");
                return 3;
            }
        }
        catch (SQLException e) {
            System.out.println( e.getErrorCode());
            return 4;
        }
    }

    // Order Update (Update Shipping Status)
    public int orderUpdate_updateShippingStatus(String order_id, String Y_or_No){
        /*
         *  This method is to be used to update the shipping status of an order if the order has not been shipped and the quantity is more than 0.
         *  This is basically part 2 of the order update operation.
         *  Input: order_id, Y_or_No
         *  Output: 1 - Success
         *          0 - Failure
         */

        try {

            // For our prepared statement, we UPDATE the shipping status in ORDERS according to the order_id
            PreparedStatement ptsm = 
                conn.prepareStatement("UPDATE orders SET shipping_status = 'Y' WHERE order_id = ?");
            ptsm.setString(1, order_id);
            ptsm.executeUpdate();

            // Once we execute the above, it is good to check that the operatins actually went through as we expected.
            // Check if database has been successfully updated with the new shipping status
            PreparedStatement ptsm2 = 
                conn.prepareStatement("SELECT shipping_status FROM orders WHERE order_id = ?");
            ptsm2.setString(1, order_id);
            ResultSet rs = ptsm2.executeQuery();

            while (rs.next()) {
                String status = rs.getString("shipping_status");
                if (status.equals("Y")) {
                    return 1;
                }
                else {
                    return 0;
                }
            }
        }
        catch (SQLException e) {
            System.out.println( e.getErrorCode());
        }
        return 0;
    }

    // Order Query
    public void orderQuery(String input) {
        // Order Query
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM orders";
            ResultSet rs = stmt.executeQuery( query );
            int i = 1;
            int sum = 0;
            while ( rs.next() ) {
                String order_id = rs.getString( "order_id" );
                int charge = rs.getInt( "charge" );
                String o_date = rs.getDate("o_date").toString();
                String customer_id = rs.getString( "customer_id" );
                
                if (o_date.contains(input)) {
                    sum += charge;
                    System.out.println("Record: " + i);
                    System.out.println("order_id: " + order_id);
                    System.out.println("customer_id: " + customer_id);
                    System.out.println("date: " + o_date);
                    System.out.println("charge: " + charge);
                    System.out.println();
                    i+=1;
                }
            }
            System.out.println("Total charges of the month is " + sum);
        } 
        catch (SQLException e) {
            System.out.println( e.getErrorCode());
        }

    }
}
