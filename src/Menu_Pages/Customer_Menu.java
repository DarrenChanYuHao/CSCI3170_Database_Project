package Menu_Pages;

import java.util.Scanner;
import Builders.String_Builder;
import Handlers.Option_Handler;

public class Customer_Menu implements Menu{

    @Override
    public void show_display() {
        String_Builder customer_menu = new String_Builder.Build_String().setMenuName("Customer_Menu").build();
        
        System.out.println(customer_menu.getMenuHeaders());
        System.out.println(customer_menu.getMenuOptionsList());
        choose_option();
    }

    @Override
    public void choose_option() {

        Option_Handler optionHandler = new Option_Handler();
        int user_input = optionHandler.get_options(5);

        // Switch case for user input
        switch (user_input){
            case 1:
                // TODO Customer Menu 1: Book Search
                System.out.println("Book Search.");
                break;
            case 2:
                // TODO Customer Menu 2: Order Creation
                System.out.println("Order Creation.");
                break;
            case 3:
                // TODO Customer Menu 3: Order Altering
                System.out.println("Order Altering.");
                break;
            case 4:
                // TODO Customer Menu 4: Order Query
                System.out.println("Order Query.");
                break;
            case 5:
                // Back to main menu
                Main_Menu main_menu = new Main_Menu();
                main_menu.show_display();
                break;
        }
    }

    @Override
    public void quit_current_menu() {

    }

    public void buildString(String[] StrArr){

    }
}
