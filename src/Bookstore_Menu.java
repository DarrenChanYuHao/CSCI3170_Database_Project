import java.util.Date;
import java.util.Scanner;

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

        System.out.println("Please enter your choice??..");

        // Get user input from 1 to 5
        Scanner choice_Scanner = new Scanner(System.console().reader());
        int user_input = 0;

        while (user_input < 1 || user_input > 4) {
            try {
                user_input = choice_Scanner.nextInt();
                if (user_input < 1 || user_input > 5) {
                    System.out.println("Invalid input. Please enter a number from 1 to 4.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number from 1 to 4.");
                choice_Scanner.nextLine();
            }
        }

        choice_Scanner.close();

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
