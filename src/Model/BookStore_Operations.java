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

    // ========================================================================================================
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
