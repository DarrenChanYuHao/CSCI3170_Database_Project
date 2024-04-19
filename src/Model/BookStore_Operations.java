package Model;
import java.sql.Connection;
import java.sql.*;
import Handlers.Option_Handler;
import java.sql.PreparedStatement;

public class BookStore_Operations {

    // Attributes
    private Connection conn;
    Option_Handler optionHandler = new Option_Handler();
    
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
            int quantity = 0;
            String status = "";

            PreparedStatement ptsm = 
                conn.prepareStatement("SELECT o.shipping_status, ord.quantity FROM orders o JOIN ordering ord ON o.order_id = ord.order_id WHERE o.order_id = ?");
            ptsm.setString(1, input);
            ResultSet rs = ptsm.executeQuery();

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
                    System.out.println("Please enter another Order ID.");
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
    public int orderUpdate_updateShippingStatus(String order_id, String Y_or_N){
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
    public void orderQuery() {
        // Order Query
        String input = optionHandler.get_user_input_string("YYYY-MM");
        // This query poorly performs (Linear Search) -> Average time complexity: O(n)
        try {
            String query = "SELECT * FROM orders WHERE TO_CHAR(o_date, 'YYYY-MM') =  ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, input);
            ResultSet rs = stmt.executeQuery();
            int i = 1;
            int sum = 0;
            while ( rs.next() ) {
                String order_id = rs.getString( "order_id" );
                int charge = rs.getInt( "charge" );
                String shipping_status = rs.getString("shipping_status");
                String o_date = rs.getDate("o_date").toString();
                String customer_id = rs.getString( "customer_id" );
                
                if ( shipping_status.contains("Y")) {
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

    // N most popular book query
    public void n_Most_Popular() { // Average time complexity: O(n)
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) as row_count FROM (SELECT b.title, ord.ISBN,  sum(ord.quantity) FROM ordering ord JOIN book b ON ord.ISBN = b.ISBN GROUP BY ord.ISBN, b.title ORDER BY sum(ord.quantity))");
            ResultSet rs = stmt.executeQuery();
            int count = 0;
            if ( rs.next() ) 
                count = rs.getInt(1);
            int user_input = optionHandler.get_user_input_Int(count);

            stmt = conn.prepareStatement("SELECT title, ISBN, total_quantity FROM (SELECT b.title, ord.ISBN,  sum(ord.quantity) as total_quantity , RANK() OVER (ORDER BY sum(ord.quantity) DESC) AS rank FROM ordering ord JOIN book b ON ord.ISBN = b.ISBN GROUP BY ord.ISBN, b.title ORDER BY sum(ord.quantity)) WHERE rank <= ? ORDER BY total_quantity DESC");
            stmt.setInt(1, user_input);
            rs = stmt.executeQuery();

            System.out.println("ISBN            Title            Copies");
            while ( rs.next() ) {
                String title = rs.getString( "title" );
                String isbn = rs.getString( "isbn" );
                int quantity = rs.getInt("total_quantity");
                System.out.println(isbn + "   " + title + "      " + quantity);
            }    
        }
        catch (SQLException e) {
            System.out.println( e.getErrorCode());
        }
    }
}
