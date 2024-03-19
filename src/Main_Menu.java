import java.sql.Date;
import java.util.Scanner;

public class Main_Menu implements Menu{

    @Override
    public void show_display() {
        // Dummy Date Variable
        Date system_time;

        system_time = getSystemTime();

        String_Builder main_menu = new String_Builder.Build_String()
                .setMenuName("Main_Menu")
                .build();

        System.out.println("The System Date is now:" + system_time);
        System.out.println(main_menu.getMenuHeaders());
        System.out.println(main_menu.getMenuOptionsList());
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
                System.out.println("Show System Date.");
                break;
            case 5:
                // Quit the system
                System.out.println("Quit the system......");
                break;
            default:
                // Invalid input
                System.out.println("Invalid input. Please enter a number from 1 to 5.");
                break;
        }
    }

    @Override
    public void quit_current_menu() {

    }

    public void buildString(String[] StrArr){

    }



    // Dummy Method for getting the system time
    // TODO: Replace with actual system time method
    public Date getSystemTime(){
        Date system_time = new Date(System.currentTimeMillis());

        return system_time;
    }

}
