package Menu_Pages;

import java.util.Scanner;
import Builders.String_Builder;
import Handlers.Option_Handler;

public class System_Menu implements Menu {

    @Override
    public void show_display() {
        
        String_Builder system_menu = new String_Builder.Build_String().setMenuName("System_Menu").build();

        System.out.println(system_menu.getMenuHeaders());
        System.out.println(system_menu.getMenuOptionsList());
        choose_option();
    }

    @Override
    public void choose_option() {

        Option_Handler optionHandler = new Option_Handler();
        int user_input = optionHandler.get_options(5);

        // Switch case for user input
        switch (user_input){
            case 1:
                // TODO System Menu 1: Create Table
                System.out.println("Create Table.");
                break;
            case 2:
                // TODO System Menu 2: Delete Table
                System.out.println("Delete Table.");
                break;
            case 3:
                // TODO System Menu 3: Insert Data
                System.out.println("Insert Data.");
                break;
            case 4:
                // TODO System Menu 4: Set System Date
                System.out.println("Set System Date.");
                break;
            case 5:
                // Back to main menu
                Main_Menu main_menu = new Main_Menu();
                main_menu.show_display();
                break;
            default:
                System.out.println("Invalid input. Please enter a number from 1 to 5.");
                break;
        }
    }

    @Override
    public void quit_current_menu() {

    }
}
