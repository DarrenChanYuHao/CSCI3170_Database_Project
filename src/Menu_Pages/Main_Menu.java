package Menu_Pages;

import Model.*;

import java.sql.Connection;
import java.sql.Date;
import Builders.String_Builder;
import Handlers.Option_Handler;

public class Main_Menu implements Menu{
    /*
     * This class is to be used for the main menu displays and operations.
     */

    // Attributes
    private Database db;
    private Connection conn;
    private Date system_time;
    private String system_time_string;

    // Constructor
    public Main_Menu(Database db, Connection conn) {
        // Initialise Database
        this.db = db;
        this.conn = conn;
    }

    @Override
    public void show_display() {

        /*
        *    This method is to be used to display the main menu. Once called, it will display 
        *    the main menu and call the choose_option method to prompt the user to choose an option.
        *
        *    Input: None
        *    Output: None    
        */

        // Get System Date from main
        system_time = db.getSystemDate();
        
        if (system_time == null){
            system_time_string = "0000-00-00";
        }
        else{
            system_time_string = system_time.toString();
        }

        String_Builder main_menu = new String_Builder.Build_String()
                .setMenuName("Main_Menu")
                .build();

        System.out.println("The System Date is now:" + system_time_string);
        System.out.println(main_menu.getMajorMenuHeaders());
        System.out.println(main_menu.getMajorMenuOptionsList());
        choose_option();
    }

    @Override
    public void choose_option() {

        /*
        *    This method is to be used to prompt the user to choose an option from the main menu.
        *    Once the user has chosen an option, it will call the corresponding method.
        *
        *    The options are:
        *    1. System Interface
        *    2. Customer Interface
        *    3. Bookstore Interface
        *    4. Show System Date
        *    5. Quit the system
        *
        *    Input: None
        *    Output: None    
        */

        Option_Handler optionHandler = new Option_Handler();
        int user_input = optionHandler.get_userinput_menu_options(5);
        Main_Menu main_menu = new Main_Menu(this.db, this.conn);

        // Switch case for user input
        switch (user_input){
            case 1:
                // System Menu
                System_Menu system_menu = new System_Menu(this.db, conn);
                system_menu.show_display();
                break;
            case 2:
                // Customer Menu
                Customer_Menu customer_menu = new Customer_Menu(this.db, conn);
                customer_menu.show_display();
                break;
            case 3:
                // Bookstore Menu
                Bookstore_Menu bookstore_menu = new Bookstore_Menu(this.db, conn);
                bookstore_menu.show_display();
                break;
            case 4:
                // Show System Date
                System.out.println("The System Date is now: " + system_time_string);
                main_menu.show_display();
                break;
            case 5:
                // Quit the system
                System.exit(0);
                break;
            default:
                // Invalid input
                System.out.println("Invalid input. Please enter a number from 1 to 5.");
                break;
        }
    }
}
