package Model;
import java.sql.Connection;
import java.sql.Date;
import java.sql.*;


public class BookStore_Operations {

    // Attributes
    private Connection conn;
    
    // Constructor
    public BookStore_Operations(Connection conn) {
        this.conn = conn;
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
