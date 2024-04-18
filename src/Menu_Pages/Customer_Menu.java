package Menu_Pages;

import java.util.Scanner;

import Builders.String_Builder;
import Handlers.Option_Handler;
import Model.Database;

import java.sql.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Customer_Menu implements Menu{
    /*
    * This class is to be used for the customer menu displays and operations.
    */

    // Attributes
    private Database db;
    private Connection conn;

    // Constructor
    public Customer_Menu(Database db, Connection conn) {
        // Initialise Database
        this.db = db;    
        this.conn = conn;
    }

    @Override
    public void show_display() {
        /*
        *    This method is to be used to display the customer menu. Once called, it will display
        *    the customer menu and call the choose_option method to prompt the user to choose an option.
        *
        *    Input: None
        *    Output: None
        */

        String_Builder customer_menu = new String_Builder.Build_String().setMenuName("Customer_Menu").build();
        
        System.out.println(customer_menu.getMajorMenuHeaders());
        System.out.println(customer_menu.getMajorMenuOptionsList());
        choose_option();
    }

    @Override
    public void choose_option() {
        /*
        *    This method is to be used to prompt the user to choose an option from the customer menu.
        *    Once the user has chosen an option, it will call the corresponding method.
        *    
        *    The options are:
        *    1. Book Search
        *    2. Order Creation
        *    3. Order Alteration
        *    4. Order Query
        *    5. Back to Main Menu
        *
        *    Input: None
        *    Output: None
        */

        Option_Handler optionHandler = new Option_Handler();
        int user_input = optionHandler.get_userinput_menu_options(5);

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
                Main_Menu main_menu = new Main_Menu(this.db, conn);
                main_menu.show_display();
                break;
        }
    }

    public void bookSearch(){
        Booksearch_SubMenu booksearch_subMenu = new Booksearch_SubMenu(this.conn);
        booksearch_subMenu.show_display();
    }

    public void orderCreation(){
        try {    
            System.out.println("Please enter your customerID??");
            Scanner scanner = new Scanner(System.in);
            String customerID = scanner.nextLine();

            System.out.println(">> What books do you want to order??");
            System.out.println(">> Input ISBN and then the quantity.");
            System.out.println(">> You can press \"L\" to see ordered list, or \"F\" to finish ordering.");

            //while loop variables
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            Integer numberOfCopies;
            String orderedBooks = "";
            LocalDate date;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate;
            Double charge;
            Integer bookOrdersQuantity = 0;
            Integer newOrderID;

            while(true) {
                System.out.println("Please enter the book's ISBN: ");
                String bookOrdersISBN = scanner.nextLine();
                if (bookOrdersISBN.equals("F")) {
                    break;
                } if (bookOrdersISBN.equals("L")) {
                    // Show the list of ordered books
                    System.out.println("ISBN          Number:");
                    System.out.println(orderedBooks);
                    
                } else {
                    preparedStatement = conn.prepareStatement(
                        "SELECT b.ISBN, b.title, b.no_of_copies, b.unit_price FROM book b WHERE b.ISBN = \'" + bookOrdersISBN + "\';"
                    );
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        numberOfCopies = resultSet.getInt("no_of_copies");
                        orderedBooks += resultSet.getString("ISBN") + " ";

                        System.out.println("Please enter the quantity of the order: ");
                        bookOrdersQuantity = Integer.parseInt(scanner.nextLine());
                        while (bookOrdersQuantity < 1 || bookOrdersQuantity > numberOfCopies) {
                            bookOrdersQuantity = Integer.parseInt(scanner.nextLine());
                        }
                        orderedBooks += "  " + bookOrdersQuantity + "\n";
                        
                        date = LocalDate.now();
                        formattedDate = date.format(formatter);

                        charge = resultSet.getDouble("unit_price") * bookOrdersQuantity;

                        newOrderID = resultSet.getInt("order_id") + 1;

                        preparedStatement = conn.prepareStatement(
                            "INSERT INTO orders (order_id, o_date, shipping_status, charge, customer_id) VALUES (" + newOrderID + ", \'" + formattedDate + "\', 'N', " + charge + ", \'" + customerID + "\'');"
                        );
                        resultSet = preparedStatement.executeQuery();
                    }
                }
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void orderAlteration() {
        try {
            System.out.println("Please enter the OrderID that you want to change: ");
            Scanner scanner = new Scanner(System.in);
            String orderID = scanner.nextLine();

            PreparedStatement preparedStatement = conn.prepareStatement(
                "SELECT * FROM orders WHERE order_id = " + orderID + ";"
            );
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                System.out.println("OrderID not found.");
                return;
            } else {
                System.out.println("order_id:" + resultSet.getInt("order_id") + "  shipping:" + resultSet.getString("shipping_status") + "  charge=" + resultSet.getDouble("charge") + "  customerId:" + resultSet.getString("customer_id"));
                //System.out.println("book no: " + bookNo + " ISBN = " + isbn + " quantity = " + quantity);
                System.out.println("Which book you want to alter (input book no.):\n");

            }
            
            

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void orderQuery(){
        try {
            System.out.println("Please Input CustomerID: ");
            Scanner scanner = new Scanner(System.in);
            String customerID = scanner.nextLine();

            System.out.println("Please Input the Year: ");
            String year = scanner.nextLine();
            
            PreparedStatement preparedStatement = conn.prepareStatement(
                "SELECT * FROM orders WHERE TO_CHAR(o_date, 'YYYY') = ? AND customer_id = ?"
            );
            preparedStatement.setString(1, year);
            preparedStatement.setString(2, customerID);
            
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                System.out.println("\nRecord : " + resultSet.getRow());
                System.out.println("OrderID : " + resultSet.getInt("order_id"));
                System.out.println("Order Date : " + resultSet.getString("o_date"));
                System.out.println("Charge : " + resultSet.getDouble("charge"));
                System.out.println("Shipping Status : " + resultSet.getString("shipping_status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
