package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class System_Operations {

    // Attributes
    private Connection conn;
    
    // Constructor
    public System_Operations(Connection conn) {
        this.conn = conn;
    }

    // Create Table
    public void createTable(String[] tableNames, String[] tableColumns) {
        // Create Table
        try {
            for (int i = 0; i < tableNames.length; i++) {
                PreparedStatement pstmt =
                        conn.prepareStatement( "create table " + tableNames[i] + tableColumns[i] );
                pstmt.execute();
            } 
            System.out.println("Table successfully created.");
        }
        catch (SQLException e) {
            System.out.println("Error Code: " + e.getErrorCode());
        }
    }

    // Delete Table
    public void deleteTable(String[] tableNames) {
        // Delete Table
        try {
            // Delete all table in the SQL database
            for (String tableName : tableNames) {
                PreparedStatement pstmt =
                        conn.prepareStatement( "drop table " + tableName );
                pstmt.execute();
            }
            System.out.println("Table successfully deleted.");
        }
        catch (SQLException e) {
            System.out.println("Error Code: " + e.getErrorCode());
        
        }
    }

    // Insert Data
    public void insertData() {
        // Insert Data
    }

    // Set System Date
    public void setSystemDate() {
        // Set System Date
    }
    
}
