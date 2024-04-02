package Model;

import java.sql.*;

public class Database {
/*
 * This class is to be used for all database operations.
 */

    Connection conn;
    String[] tableNames = {"book", "customer", "orders", "ordering", "book_author"};
    String[] tableColumn = {"(ISBN int, title varchar(50), unit_price int, no_of_copies int)",
                            "(customer_id int, name varchar(50), shipping_address varchar(50), credit_card_no int)",
                            "(order_id int, o_date date, shipping_status varchar(50), charge float, customer_id int)",
                            "(order_id int, ISBN int, quantity int)",
                            "(ISBN int, author_name varchar(50))"};

    // enum for table names
    public enum TableNames {
        book, customer, orders, ordering, book_author
    }

    // enum for table columns
    public enum TableColumns {
        book("(ISBN int, title varchar(50), unit_price int, no_of_copies int)"),
        customer("(customer_id int, name varchar(50), shipping_address varchar(50), credit_card_no int)"),
        orders("(order_id int, o_date date, shipping_status varchar(50), charge float, customer_id int)"),
        ordering("(order_id int, ISBN int, quantity int)"),
        book_author("(ISBN int, author_name varchar(50)");

        private final String column;

        TableColumns(String column) {
            this.column = column;
        }

        public String getColumn() {
            return column;
        }
    }

    public Database() throws SQLException {
    }

    public Connection connectToOracle() throws SQLException {
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

        return conn;
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

    public void system_operations(String operation){
        /*
        *    This method is to be used to call the system operations class.
        *    Input: None
        *    Output: None    
        */
        
        System_Operations sys_ops = new System_Operations(conn);
        
        switch (operation){
            case "createTable":
                sys_ops.createTable(tableNames, tableColumn);
                break;
            case "deleteTable":
                sys_ops.deleteTable(tableNames);
                break;
            case "insertData":
                sys_ops.insertData();
                break;
            case "setdate":
                sys_ops.setSystemDate();
                break;

        }
    }
}
