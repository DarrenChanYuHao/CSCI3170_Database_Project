package Menu_Pages;

import Builders.String_Builder;
import Handlers.Option_Handler;

public class Customer_Menu implements Menu{

    @Override
    public void show_display() {
        String_Builder customer_menu = new String_Builder.Build_String().setMenuName("Customer_Menu").build();
        
        System.out.println(customer_menu.getMajorMenuHeaders());
        System.out.println(customer_menu.getMajorMenuOptionsList());
        choose_option();
    }

    @Override
    public void choose_option() {

        Option_Handler optionHandler = new Option_Handler();
        int user_input = optionHandler.get_options(5);

        // Switch case for user input
        switch (user_input){
            case 1:
                bookSearch();
                break;
            case 2:
                orderCreation();
                break;
            case 3:
                orderAlteration();
                break;
            case 4:
                orderQuery();
                break;
            case 5:
                // Back to main menu
                Main_Menu main_menu = new Main_Menu();
                main_menu.show_display();
                break;
        }
    }

    public void bookSearch(){
        // TODO Customer Menu 1: Book Search
        Booksearch_SubMenu booksearch_subMenu = new Booksearch_SubMenu();
        booksearch_subMenu.show_display();
    }

    public void orderCreation(){
        // TODO Customer Menu 2: Order Creation
        System.out.println("Please enter your customerID???");
        // do some sort of read
        // process the read

        // Use builder pattern to create a new order here
        System.out.println("What books do you want to order??");
        System.out.println("Input the ISBN and then the quantity.");
        System.out.println("You can press \"L\" to see the ordered list, or \"F\" to finish ordering.");
        System.out.println("Please enter the book's ISBN: ");
        System.out.println("Please enter the quantity of the order: ");
        // do some sort of read
        // process the read
    }

    public void orderAlteration(){
        // TODO Customer Menu 3: Order Altering
        System.out.println("Please enter the OrderID that you want to change: ");
        // do some sort of read
        // process the read

        // Show the order details blah blah write methods later
        System.out.println("Which book do you want to alter (input book no.): ");
        // do some sort of read
        // process the read

        System.out.println("Input add or remove");
        // do some sort of read
        // process the read

        System.out.println("Input the number: ");
        // do some sort of read
        // process the read

        System.out.println("Update is ok!");
        System.out.println("update is done!!");
        System.out.println("updated charge");
        // Show new order details blah blah write methods later
    }

    public void orderQuery(){
        // TODO Customer Menu 4: Order Query
        System.out.println("Please Input CustomerID: ");
        // do some sort of read
        // process the read

        System.out.println("Please Input the Year: ");
        // do some sort of read
        // process the read

        // Show the order details blah blah write methods later
    }
}
