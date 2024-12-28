package Model;

import java.sql.*;

public class Database {
/*
 * This class is to be used for all database operations.
 */

    Connection conn;
    String[] tableNames = {"book", "customer", "orders", "ordering", "book_author"};
    String[] tableColumn = {"(ISBN varchar(13) PRIMARY KEY, title varchar(100), unit_price int CHECK (unit_price >= 0), no_of_copies int CHECK (no_of_copies >= 0))",
                            "(customer_id varchar(10) PRIMARY KEY, name varchar(50), shipping_address varchar(200), credit_card_no varchar(19))",
                            "(order_id varchar(8) PRIMARY KEY, o_date date, shipping_status varchar(1), charge int CHECK (charge >= 0), customer_id varchar(10))",
                            "(order_id varchar(8), ISBN varchar(13), quantity int CHECK (quantity >= 0), PRIMARY KEY(order_id, ISBN))",
                            "(ISBN varchar(13), author_name varchar(50), PRIMARY KEY(ISBN, author_name))"};
    Date system_date;

    public Database() throws SQLException {
       // system_date = Date.valueOf("0000-00-00");
    }

    public Connection connectToOracle() throws SQLException {
    /*
     *    This method is to be used to connect to the Oracle database.
     *    Input: None
     *    Output: None    
     */
    
        System.out.println("Connecting to database");

        // Load the Oracle JDBC driver
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // [Deprecated] The connection string here was for the CUHK Oracle Database
            // conn = DriverManager.getConnection("jdbc:oracle:thin:@//db18.cse.cuhk.edu.hk:1521/oradb.cse.cuhk.edu.hk","h006","RukjuArj");

            // To try this program, you should install Oracle Database and run it locally.
            // You can do this by setting up as per instructions here:
            // https://www.oracle.com/database/technologies/appdev/xe/quickstart.html
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XE","system","Password123");

            System.out.println("Connected to Oracle Database");
            System.out.flush();
        }
        catch (ClassNotFoundException e) {
            System.out.println("Failed to connect to database");
            System.out.println("Error as follows:" + e.getException());
        }

        return conn;
    }

    // Get System Date
    public Date getSystemDate() {
        return system_date;
    }

    // Set System Date
    public void setSystemDate(String new_date) {
        // Extract the year, month, and day from the input string
        String year = new_date.substring(0, 4);
        String month = new_date.substring(4, 6);
        String day = new_date.substring(6, 8);

        // Create the formatted date string with hyphens
        String formatted_new_date = year + "-" + month + "-" + day;

        // Get latest order date
        Date latest_order_date = getLatestOrderDate();
        
        if (latest_order_date != null && latest_order_date.after(Date.valueOf(formatted_new_date))) {
            System.out.println("Error: The new system date is before the latest order date.");
            System.out.println("Latest date in orders: " + latest_order_date);
            return;
        }
        else {
            system_date = Date.valueOf(formatted_new_date);
            System.out.println("Latest date in orders: " + latest_order_date);
            System.out.println("Today is " + system_date);
        }
    }

    // Get latest order date
    public Date getLatestOrderDate() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(o_date) FROM orders");
            rs.next();
            return rs.getDate(1);
        } catch (SQLException e) {
            System.out.println("Error Code: " + e.getErrorCode());
        }
        return null;
    }

    // For System Operations
    // ========================================================================================================
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
        }
    }

    // Overloaded method for insertData and setdate
    public void system_operations(String operation, String user_input){
        
        System_Operations sys_ops = new System_Operations(conn);
        
        switch (operation){
            case "insertData":
                sys_ops.insertData(user_input);
                break;
            case "setdate":
                setSystemDate(user_input);
            break;
        }
    }
    // ========================================================================================================

    // For Book Store Operations
    // ========================================================================================================
    public int book_store_operations(String operation, String user_input){
        /*
        *    This method is to be used to call the book store operations class.
        *    Input: None
        *    Output: (Only for order update (order ID))
        *            0 - order status is Y (shipped)
        *            1 - order status is N (not shipped) and quantity is more than 0 (Shipping Status can be updated)
        *            2 - order status is N (not shipped) and quantity is 0 (TBC : Waiting for clarification from TA)
        *            3 - order does not exist    
        */
        
        BookStore_Operations bs_ops = new BookStore_Operations(conn);
        
        switch (operation){
            case "order update (order ID)":
                int order_details = bs_ops.orderUpdate_getOrderDetails(user_input);
                return order_details;
            case "order query":
                bs_ops.orderQuery();
                break;
            
            case "N most popular":
                bs_ops.n_Most_Popular();
                break;
        }

        return 0;
    }

    public int book_store_operations(String operation, String user_input, String user_input_2){
        /*
        *    This method is to be used to call the book store operations class.
        *    This is overload for order update (update shipping status) as that requires 2 inputs (order_id, shipping_status).
        *    Input: operations, user_input, user_input_2
        *    Output: (Only for order update (update shipping status))
        *            1 - Success
        *            0 - Failure    
        */
        
        BookStore_Operations bs_ops = new BookStore_Operations(conn);
        
        switch (operation){
            case "order update (update shipping status)":
                int success_status = bs_ops.orderUpdate_updateShippingStatus(user_input, user_input_2);
                return success_status;
        }        

        return 0;
    }
}
