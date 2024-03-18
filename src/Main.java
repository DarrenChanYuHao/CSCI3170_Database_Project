import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        Main app = new Main();
        app.connectToOracle();
    }

    // Connect to Oracle JDBC
    public void connectToOracle() throws SQLException {
        // Load the Oracle JDBC driver
        try {
            // Sample code from Lecture.
            // Use this to test if your connections are working
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@//db18.cse.cuhk.edu.hk:1521/oradb.cse.cuhk.edu.hk","h006","RukjuArj");
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM Sailors";
            ResultSet rs = stmt.executeQuery( query );
            // loop through result tuples (rs is a cursor)
            while ( rs.next() ) {
                String s = rs.getString( "name" );
                int n = rs.getInt( "rating" );
                System.out.println( s + " " + n );
            }

            PreparedStatement pstmt =
                    conn.prepareStatement( "insert into Sailors values (?,?,?)" );
            pstmt.setInt ( 1, 0 );
            pstmt.setString ( 2, "Myint" );
            pstmt.setInt ( 3, 7 );
            pstmt.execute();
        }
        catch (ClassNotFoundException e) {
            System.out.println("Could not load the driver" + e.getException());
        }
    }
}