import java.util.Scanner;

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

        System.out.println("Please enter your choice??..");

        // Get user input from 1 to 5
        Scanner choice_Scanner = new Scanner(System.console().reader());
        int user_input = 0;

        while (user_input < 1 || user_input > 5) {
            try {
                user_input = choice_Scanner.nextInt();
                if (user_input < 1 || user_input > 5) {
                    System.out.println("Invalid input. Please enter a number from 1 to 5.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number from 1 to 5.");
                choice_Scanner.nextLine();
            }
        }
        choice_Scanner.close();

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
