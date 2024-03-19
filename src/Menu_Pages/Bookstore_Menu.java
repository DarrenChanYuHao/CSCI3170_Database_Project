package Menu_Pages;

import java.util.Date;
import java.util.Scanner;
import Builders.String_Builder;
import Handlers.Option_Handler;

public class Bookstore_Menu implements Menu {

    Date system_time;

    @Override
    public void show_display() {
        String_Builder bookstore_menu = new String_Builder.Build_String().setMenuName("Bookstore_Menu").build();

        System.out.println(bookstore_menu.getMenuHeaders());
        System.out.println(bookstore_menu.getMenuOptionsList());

        choose_option();
    }

    @Override
    public void choose_option() {

        Option_Handler optionHandler = new Option_Handler();
        int user_input = optionHandler.get_options(4);

        // Switch case for user input
        switch (user_input){
            case 1:
                // TODO Bookstore Menu 1: Order Update
                System.out.println("Order Update.");
                break;
            case 2:
                // TODO Bookstore Menu 2: Order Query
                System.out.println("Order Query.");
                break;
            case 3:
                // TODO Bookstore Menu 3: N most Popular Book Query
                System.out.println("N most Popular Book Query.");
                break;
            case 4:
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
