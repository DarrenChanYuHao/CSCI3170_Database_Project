import java.util.Scanner;

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
