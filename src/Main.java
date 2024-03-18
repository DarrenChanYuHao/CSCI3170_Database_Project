import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Main app = new Main();
        app.connectToOracle();
        System.out.println("Test?");
    }

    // Connect to Oracle JDBC
    public void connectToOracle() throws SQLException {
        // Load the Oracle JDBC driver
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@//db18.cse.cuhk.edu.hk:1521/oradb.cse.cuhk.edu.hk","h006","RukjuArj");
            Statement stmt = conn.createStatement();
            String query = "SELECT name, rating FROM Sailors";
            ResultSet rs = stmt.executeQuery( query );
            System.out.println("This is rs" + rs);
            // loop through result tuples (rs is a cursor)
            while ( rs.next() ) {
                String s = rs.getString( "name" );
                int n = rs.getInt( "rating" );
                System.out.println( s + " " + n );
                System.out.println("This is s" + s);
            }
        }
        catch (ClassNotFoundException e) {
            System.out.println("Could not load the driver" + e.getException());
        }
    }
}