package Model;

import java.sql.*;

public class Database {
/*
 * This class is to be used for all database operations.
 */

    Connection conn;

    public Database() throws SQLException {
    }

    public void connectToOracle() throws SQLException {
    /*
     *    This method is to be used to connect to the Oracle database.
     *    Input: None
     *    Output: None    
     */
    
        // Load the Oracle JDBC driver
        try {
            // Use this to test if your connections are working
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@//db18.cse.cuhk.edu.hk:1521/oradb.cse.cuhk.edu.hk","h006","RukjuArj");
        }
        catch (ClassNotFoundException e) {
            System.out.println("Could not load the driver" + e.getException());
        }
    }
    
    public void sampleInsert() throws SQLException {
        /*
        *    This method is a sample method to insert a new tuple into the Sailors table for testing purposes.
        *    Input: None
        *    Output: None    
        */
    
        try {
            PreparedStatement pstmt =
                    conn.prepareStatement( "insert into Sailors values (?,?,?)" );
            pstmt.setInt ( 1, 0 );
            pstmt.setString ( 2, "Myint" );
            pstmt.setInt ( 3, 7 );
            pstmt.execute();
        }
        catch (SQLException e) {
            System.out.println( e.getErrorCode());
        }
    }

    public void sampleQuery() throws SQLException {
        /*
        *    This method is a sample method to query the Sailors table for testing purposes.
        *    Input: None
        *    Output: None    
        */

        try {
            // Sample code from Lecture.
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM Sailors";
            ResultSet rs = stmt.executeQuery( query );
            // loop through result tuples (rs is a cursor)
            while ( rs.next() ) {
                String s = rs.getString( "name" );
                int n = rs.getInt( "rating" );
                System.out.println( s + " " + n );
            }
        }
        catch (SQLException e) {
            System.out.println( e.getErrorCode());
        }
    }
}
