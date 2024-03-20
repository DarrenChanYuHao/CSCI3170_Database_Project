import Menu_Pages.*;
import Model.*;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {

        // // Initialise Database
        // Database db = new Database();
        // db.connectToOracle();

        // Initialise Main Menu
        Main_Menu menu = new Main_Menu();

        // Show Main Menu
        menu.show_display();

    }
}