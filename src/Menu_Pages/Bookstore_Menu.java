package Menu_Pages;

import java.sql.Connection;
import java.util.Date;
import Builders.String_Builder;
import Handlers.Option_Handler;
import Model.Database;
import oracle.net.aso.c;

public class Bookstore_Menu implements Menu {

    /*
     * This class is to be used for the bookstore menu displays and operations.
     */

    Date system_time;
    private Database db;
    Option_Handler optionHandler = new Option_Handler();
    Connection conn;

    public Bookstore_Menu(Database db, Connection conn) {
        // Initialise Database
        this.db = db;
        this.conn = conn;    
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
                Main_Menu main_menu = new Main_Menu(this.db, this.conn);
                main_menu.show_display();
                break;
        }
    }

    public void orderUpdate(){
        /*
         *  This method is to be used to update the shipping status of an order if the order has not been shipped and the quantity is more than 0.
         *  Once called, it will prompt the user to input the order ID and check if the order can be updated.
         *  If the order can be updated, it will prompt the user to input the shipping status (Y or N).
         *  Once the user has input the shipping status, it will update the shipping status of the order.
         */

        // Prompt user to input order ID
        int order_status = 0;
        String user_input = "";

        while (true) {
            System.out.println("Please Input your order ID: ");
            user_input = optionHandler.get_user_input_string("order ID");

            // display details as required. return the shipping status of the order
            // 0 - order status is Y (shipped)
            // 1 - order status is N (not shipped) and quantity is more than 0 (Shipping Status can be updated)
            // 2 - order status is N (not shipped) and quantity is 0 (TBC : Waiting for clarification from TA)
            // 3 - order does not exist
            // 4 - order status is N and qua
            order_status = db.book_store_operations("order update (order ID)", user_input);

            if (order_status != 2) break;
        }
        
        // If order is not shipped and quantity is more than 0, prompt user to update shipping status
        if (order_status == 1) {           
            // Get user input for Yes or No
            System.out.println("Are you sure to update the shipping status? (Yes=Y)");

            String user_input_2 = optionHandler.get_user_input_string();
            // Note the above, no input verification needed as anything that is not Y will be considered as No

            // If user input is not Y, return to bookstore menu
            if (!user_input_2.equals("Y")) {
                // Back to bookstore menu if not Y
                System.out.println("Shipping status update cancelled.");
                System.out.flush();
                Bookstore_Menu bookstore_menu = new Bookstore_Menu(this.db, this.conn);
                bookstore_menu.show_display();
                return;
            }

            // Else, we will update the shipping status
            int success_status = db.book_store_operations("order update (update shipping status)", user_input, user_input_2);
            
            // We need to make sure that the update is successful
            // If successful, print success message
            // Else, print failure message
            if (success_status == 1) {
                System.out.println("Updated Shipping Status.");
            }
            else {
                System.out.println("Shipping status update failed. Try again.");
            }
        }

        // If order is already shipped, print message as such.
        if (order_status == 0) {
            System.out.println("Order already shipped. Cannot update shipping status.");
        }
    }

    public void orderQuery(){
        System.out.println("Please inputs the Month for Order Query (e.g.2005-09):");
        db.book_store_operations("order query","");
    }

    public void nMostPopularBookQuery(){
        // TODO Bookstore Menu 3: N most Popular Book Query
        System.out.println("Please input the N popular book number:");
        db.book_store_operations("N most popular","");
    }

}
