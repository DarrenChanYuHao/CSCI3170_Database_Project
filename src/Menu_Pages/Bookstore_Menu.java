package Menu_Pages;

import java.util.Date;
import Builders.String_Builder;
import Handlers.Option_Handler;
import Model.Database;

public class Bookstore_Menu implements Menu {

    /*
     * This class is to be used for the bookstore menu displays and operations.
     */

    Date system_time;
    private Database db;
    Option_Handler optionHandler = new Option_Handler();

    public Bookstore_Menu(Database db) {
        // Initialise Database
        this.db = db;    
    }

    @Override
    public void show_display() {
        /*
         *    This method is to be used to display the bookstore menu. Once called, it will display
         *    the bookstore menu and call the choose_option method to prompt the user to choose an option.
         *
         *    Input: None
         *    Output: None
         */
    
        String_Builder bookstore_menu = new String_Builder.Build_String().setMenuName("Bookstore_Menu").build();

        System.out.println(bookstore_menu.getMajorMenuHeaders());
        System.out.println(bookstore_menu.getMajorMenuOptionsList());

        choose_option();
    }

    @Override
    public void choose_option() {
        /*
         *    This method is to be used to prompt the user to choose an option from the bookstore menu.
         *    Once the user has chosen an option, it will call the corresponding method.
         *
         *    The options are:
         *    1. Order Update
         *    2. Order Query
         *    3. N most Popular Book Query
         *    4. Back to Main Menu
         *
         *    Input: None
         *    Output: None
         */

        int user_input = optionHandler.get_userinput_menu_options(4);

        // Switch case for user input
        switch (user_input){
            case 1:
                orderUpdate();
                break;
            case 2:
                orderQuery();
                break;
            case 3:
                nMostPopularBookQuery();
                break;
            case 4:
                // Back to main menu
                Main_Menu main_menu = new Main_Menu(this.db);
                main_menu.show_display();
                break;
        }
    }

    public void orderUpdate(){
        // TODO Bookstore Menu 1: Order Update
        System.out.println("Please Input your order ID: ");
        // do some sort of read
        // display details
        System.out.println("Are you sure to update the shipping status? (Yes=Y)");
        // do some sort of read
        // update the shipping status
        System.out.println("Updated shipping status");
    }

    public void orderQuery(){
        // TODO Bookstore Menu 2: Order Query
        System.out.println("Please input the Month for Order Query (e.g.2005-09):");
        db.book_store_operations("order query");
    }

    public void nMostPopularBookQuery(){
        // TODO Bookstore Menu 3: N most Popular Book Query
        System.out.println("Please input the N popular book number:");
        db.book_store_operations("N most popular");
    }

}
