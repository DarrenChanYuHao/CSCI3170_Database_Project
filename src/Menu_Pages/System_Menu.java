package Menu_Pages;

import java.sql.Connection;

import Builders.String_Builder;
import Handlers.Option_Handler;
import Model.Database;

public class System_Menu implements Menu {

    /*
     * This class is to be used for the system menu displays and operations.
     */

    // Attributes
    private Database db;
    Option_Handler optionHandler;
    Connection conn;

    // Constructor
    public System_Menu(Database db, Connection conn) {
        // Initialise Database
        this.db = db;
        this.conn = conn;
        optionHandler = new Option_Handler();
    }

    @Override
    public void show_display() {
        
        String_Builder system_menu = new String_Builder.Build_String().setMenuName("System_Menu").build();

        System.out.println(system_menu.getMajorMenuHeaders());
        System.out.println(system_menu.getMajorMenuOptionsList());
        choose_option();
    }

    @Override
    public void choose_option() {
        int user_input = optionHandler.get_userinput_menu_options(5);

        // Switch case for user input
        switch (user_input){
            case 1:
                createTable();
                break;
            case 2:
                deleteTable();
                break;
            case 3:
                insertData();
                break;
            case 4:
                setSystemDate();
            case 5:
                // Back to main menu
                Main_Menu main_menu = new Main_Menu(this.db, this.conn);
                main_menu.show_display();
                break;
            default:
                System.out.println("Invalid input. Please enter a number from 1 to 5.");
                break;
        }
    }

    public void createTable(){
        // TODO System Menu 1: Create Table
        System.out.println("Create Table.");
        db.system_operations("createTable");
    }

    public void deleteTable(){
        // TODO System Menu 2: Delete Table
        System.out.println("Delete Table.");
        db.system_operations("deleteTable");
    }

    public void insertData(){
        System.out.println("Please enter the folder path");

        // do some sort of read
        // TO UNCOMMENT WHEN SUBMITTING:!!!!!
        String user_input = optionHandler.get_user_input_string("folder path");
        
        // TO REMOVE:
        // Use dummy path
        //String user_input = "C:\\Users\\littl\\IdeaProjects\\Database_Project\\test_data";

        System.out.println("Processing...");
        db.system_operations("insertData", user_input);
    }

    public void setSystemDate(){
        // TODO System Menu 4: Set System Date
        System.out.println("Please Input the date (YYYYMMDD) : ");
        // do some sort of read
        String user_input = optionHandler.get_user_input_string("YYYYMMDD");
        db.system_operations("setdate", user_input);
    }


}
