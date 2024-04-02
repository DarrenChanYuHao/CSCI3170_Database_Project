package Model;

import java.sql.Connection;

public class BookStore_Operations {

    // Attributes
    private Connection conn;
    
    // Constructor
    public BookStore_Operations(Connection conn) {
        this.conn = conn;
    }

    // Order Query
    public void orderQuery() {
        // Order Query
        System.out.println("Order Query");
    }

}
