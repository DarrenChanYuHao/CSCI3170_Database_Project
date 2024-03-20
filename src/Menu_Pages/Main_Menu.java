package Menu_Pages;

import java.sql.Date;
import Builders.String_Builder;
import Handlers.Option_Handler;

public class Main_Menu implements Menu{

    @Override
    public void show_display() {
        // Dummy Date Variable
        Date system_time;

        system_time = getSystemDate();

        String_Builder main_menu = new String_Builder.Build_String()
                .setMenuName("Main_Menu")
                .build();

        System.out.println("The System Date is now:" + system_time);
        System.out.println(main_menu.getMajorMenuHeaders());
        System.out.println(main_menu.getMajorMenuOptionsList());
        choose_option();
    }

    @Override
    public void choose_option() {

        Option_Handler optionHandler = new Option_Handler();
        int user_input = optionHandler.get_options(5);

        // Switch case for user input
        switch (user_input){
            case 1:
                // System Menu
                System_Menu system_menu = new System_Menu();
                system_menu.show_display();
                break;
            case 2:
                // Customer Menu
                Customer_Menu customer_menu = new Customer_Menu();
                customer_menu.show_display();
                break;
            case 3:
                // Bookstore Menu
                Bookstore_Menu bookstore_menu = new Bookstore_Menu();
                bookstore_menu.show_display();
                break;
            case 4:
                // Show System Date
                getSystemDate();
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

    // Dummy Method for getting the system time
    // TODO Main Menu 1: Replace with actual system time method
    public Date getSystemDate(){
        Date system_time = new Date(System.currentTimeMillis());

        return system_time;
    }
}
