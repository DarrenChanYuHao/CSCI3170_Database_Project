package Menu_Pages;

import java.util.Date;
import Builders.String_Builder;
import Handlers.Option_Handler;

public class Bookstore_Menu implements Menu {

    Date system_time;

    @Override
    public void show_display() {
        String_Builder bookstore_menu = new String_Builder.Build_String().setMenuName("Bookstore_Menu").build();

        System.out.println(bookstore_menu.getMajorMenuHeaders());
        System.out.println(bookstore_menu.getMajorMenuOptionsList());

        choose_option();
    }

    @Override
    public void choose_option() {

        Option_Handler optionHandler = new Option_Handler();
        int user_input = optionHandler.get_options(4);

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
                Main_Menu main_menu = new Main_Menu();
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
        // do some sort of read
        // display details
    }

    public void nMostPopularBookQuery(){
        // TODO Bookstore Menu 3: N most Popular Book Query
        System.out.println("Please input the N popular book number:");
        // do some sort of read
        // display details
    }

}
