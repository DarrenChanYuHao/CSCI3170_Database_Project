import Menu_Pages.*;
import Model.*;

import java.sql.*;

public class Main {

    public static String system_date = "0000-00-00";

    public static void main(String[] args) throws SQLException {

        Connection conn = null;
        Database db = null;

        // // Initialise Database
        db = new Database();
        conn = db.connectToOracle();

        // Initialise Main Menu
        Main_Menu menu = new Main_Menu(db, conn);

        // Show Main Menu
        menu.show_display();

    }
}