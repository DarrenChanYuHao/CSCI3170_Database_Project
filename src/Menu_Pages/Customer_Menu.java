package Menu_Pages;

import java.util.Scanner;

import javax.naming.spi.DirStateFactory.Result;

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
        //int user_input=4;
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

    public void SQLErrorMessage() {
        System.out.println("Data either does not exist or is inaccessible, please check the SQL error code for more information");
    }

    public void bookSearch(){
        Booksearch_SubMenu booksearch_subMenu = new Booksearch_SubMenu(this.conn);
        booksearch_subMenu.show_display();
    }

    public void orderCreation(){
        try {    
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            
            System.out.println("Please enter your customerID??");
            Scanner scanner = new Scanner(System.in);
            String customerID = scanner.nextLine();
            preparedStatement = conn.prepareStatement(
                "SELECT customer_id FROM customer WHERE customer_id = ?"
            );
            preparedStatement.setString(1, customerID);
            resultSet = preparedStatement.executeQuery();

            while(!resultSet.next()) {
                System.out.println("Customer not found. Please enter a valid customerID: ");
                customerID = scanner.nextLine();
                preparedStatement = conn.prepareStatement(
                    "SELECT customer_id FROM customer WHERE customer_id = ?"
                );
                preparedStatement.setString(1, customerID);
                resultSet = preparedStatement.executeQuery();
            }

            System.out.println(">> What books do you want to order??");
            System.out.println(">> Input ISBN and then the quantity.");
            System.out.println(">> You can press \"L\" to see ordered list, or \"F\" to finish ordering.");

            //while loop variables
            Integer numberOfCopies;
            String orderedBooks = "";
            String formattedDate;
            Double charge;
            Integer bookOrdersQuantity = 0;
            Integer newOrderID;
            String formattedNewOrderID;
            String bookOrdersISBN;
            String currentOrderID;

            preparedStatement = conn.prepareStatement(
                "SELECT MAX(order_id) AS LargestOrderID FROM orders"
            );
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            newOrderID = resultSet.getInt("LargestOrderID") + 1;
            formattedNewOrderID = String.format("%08d", newOrderID);

            while(true) {
                System.out.println("Please enter the book's ISBN: ");
                bookOrdersISBN = scanner.nextLine();
                
                if (bookOrdersISBN.equals("F")) {
                    break;
                } if (bookOrdersISBN.equals("L")) {
                    // Show the list of ordered books
                    System.out.print("ISBN          Number:");
                    System.out.println(orderedBooks);
                    
                } else {
                    preparedStatement = conn.prepareStatement(
                        "SELECT b.ISBN, b.title, b.no_of_copies, b.unit_price FROM book b WHERE b.ISBN = ?"
                    );
                    preparedStatement.setString(1, bookOrdersISBN);
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        numberOfCopies = resultSet.getInt("no_of_copies");
                        orderedBooks += "\n" + resultSet.getString("ISBN") + " ";
                        System.out.println("Please enter the quantity of the order: ");
                        //try catch here for integer conversion
                        while (true) {
                            try {
                                bookOrdersQuantity = Integer.parseInt(scanner.nextLine());
                                while (bookOrdersQuantity < 1 || bookOrdersQuantity > numberOfCopies) {
                                    System.out.println("Invalid quantity. Please enter a quantity between 1 and " + numberOfCopies + ": ");
                                    bookOrdersQuantity = Integer.parseInt(scanner.nextLine());
                                }
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid quantity. Please enter a quantity between 1 and " + numberOfCopies + ": ");
                                bookOrdersQuantity = Integer.parseInt(scanner.nextLine());
                            }
                        }
                        orderedBooks += "  " + bookOrdersQuantity;

                        charge = resultSet.getDouble("unit_price") * bookOrdersQuantity;

                        preparedStatement = conn.prepareStatement(
                            "SELECT MAX(order_id) AS LargestOrderID FROM orders"
                        );
                        resultSet = preparedStatement.executeQuery();
                        resultSet.next();
                        currentOrderID = resultSet.getString("LargestOrderID");

                        if (formattedNewOrderID.equals(currentOrderID)) {
                            //find current charge and add the new charge
                            preparedStatement = conn.prepareStatement(
                                "SELECT charge FROM orders WHERE order_id = ?"
                            );
                            preparedStatement.setString(1, formattedNewOrderID);
                            resultSet = preparedStatement.executeQuery();
                            resultSet.next();

                            charge += resultSet.getDouble("charge");
                            
                            preparedStatement = conn.prepareStatement(
                                "UPDATE orders SET charge = ? WHERE order_id = ?"
                            );
                            preparedStatement.setDouble(1, charge);
                            preparedStatement.setString(2, formattedNewOrderID);
                            resultSet = preparedStatement.executeQuery();
                            
                        } else {
                            preparedStatement = conn.prepareStatement(
                                "INSERT INTO orders (order_id, o_date, shipping_status, charge, customer_id) VALUES (?, ?, 'N', ?, ?)"
                            );
                            preparedStatement.setString(1, formattedNewOrderID);
                            preparedStatement.setDate(2, db.getSystemDate());
                            preparedStatement.setDouble(3, charge);
                            preparedStatement.setString(4, customerID);
                            resultSet = preparedStatement.executeQuery();

                        }

                        //if under the same orderid, the same book isbn already exists, append the quantity
                        preparedStatement = conn.prepareStatement(
                            "SELECT quantity FROM ordering WHERE order_id = ? AND ISBN = ?"
                        );
                        preparedStatement.setString(1, formattedNewOrderID);
                        preparedStatement.setString(2, bookOrdersISBN);
                        resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            preparedStatement = conn.prepareStatement(
                                "UPDATE ordering SET quantity = ? WHERE order_id = ? AND ISBN = ?"
                            );
                            preparedStatement.setInt(1, resultSet.getInt("quantity") + bookOrdersQuantity);
                            preparedStatement.setString(2, formattedNewOrderID);
                            preparedStatement.setString(3, bookOrdersISBN);
                            resultSet = preparedStatement.executeQuery();
                        } else {
                            preparedStatement = conn.prepareStatement(
                                "INSERT INTO ordering (order_id, ISBN, quantity) VALUES (?, ?, ?)"
                            );
                            preparedStatement.setString(1, formattedNewOrderID);
                            preparedStatement.setString(2, bookOrdersISBN);
                            preparedStatement.setInt(3, bookOrdersQuantity);
                            resultSet = preparedStatement.executeQuery();
                        }

                        preparedStatement = conn.prepareStatement(
                            "UPDATE book SET no_of_copies = ? WHERE ISBN = ?"
                        );
                        preparedStatement.setInt(1, numberOfCopies - bookOrdersQuantity);
                        preparedStatement.setString(2, bookOrdersISBN);
                        resultSet = preparedStatement.executeQuery();

                    } else {
                        System.out.print("Book not found. ");
                    }
                }
                
            }
        } catch (SQLException e) {
            SQLErrorMessage();
        }
    }

    public String requestAddOrRemove() {
        String addOrRemove;
        System.out.println("input add or remove");
        Scanner scanner = new Scanner(System.in);
        while (true) {    
            addOrRemove = scanner.nextLine();
            //System.out.println("(" + addOrRemove + ")");
            if (!addOrRemove.equals("add") && !addOrRemove.equals("remove")) {
                System.out.println("Invalid input. Please enter either \"add\" or \"remove\": ");
            } else {
                break;
            }
        }
        return addOrRemove;
    }

    public Integer requestAlterBookNo(Integer totalBookNo) {
        System.out.println("Which book you want to alter (input book no.):");
        Scanner scanner = new Scanner(System.in);
        Integer alterBookNo;
        while (true) {
            try {
                alterBookNo = Integer.parseInt(scanner.nextLine());
                if (alterBookNo >= 1 && alterBookNo <= totalBookNo) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and " + totalBookNo + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and " + totalBookNo + ": ");
            }
        }
        return alterBookNo;
    }

    public Integer requestTheNumber(Integer no_of_copies) {
        Integer theNumber;
        System.out.println("Input the number: ");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                theNumber = Integer.parseInt(scanner.nextLine());
                if (theNumber >= 1) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a number bigger than 1: ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number: ");
            }
        }
        return theNumber;   
    }
    
    public void orderAlteration() {
        try {
            System.out.println("Please enter the OrderID that you want to change: ");
            Scanner scanner = new Scanner(System.in);
            String orderIDString;

            PreparedStatement preparedStatement;
            ResultSet resultSet;
            
            //validate orderid from integer/string values
            while (true) {
                orderIDString = scanner.nextLine();
                try {
                    //validate orderid from sql
                    preparedStatement = conn.prepareStatement(
                    "SELECT os.order_id, os.shipping_status, os.charge, os.customer_id, og.quantity, og.ISBN FROM orders os JOIN ordering og ON os.order_id = og.order_id WHERE os.order_id = ?"
                    );
                    preparedStatement.setString(1, orderIDString);
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        break;
                    } else {
                        System.out.println("OrderID not found. Please enter a valid orderID: ");
                    }
                } catch (SQLException e){
                    SQLErrorMessage();
                }
            }
            Integer totalBookNo;

            System.out.println("order_id:" + resultSet.getInt("order_id") + "  shipping:" + resultSet.getString("shipping_status") + "  charge=" + resultSet.getDouble("charge") + "  customerId:" + resultSet.getString("customer_id"));
            do {
                totalBookNo = resultSet.getRow();
                System.out.println("book no: " + totalBookNo + " ISBN = " + resultSet.getString("ISBN") + " quantity = " + resultSet.getInt("quantity"));
            } while (resultSet.next());

            Integer alterBookNo;
            String addOrRemove;
            Integer theNumber;
            ResultSet resultSet2;
            PreparedStatement preparedStatement2;
            PreparedStatement preparedStatement3;
            ResultSet resultSet3;
            Integer no_of_copies;
            
            while(true) {
                alterBookNo = requestAlterBookNo(totalBookNo);
            
                addOrRemove = requestAddOrRemove();

                resultSet2 = preparedStatement.executeQuery();
                for (int rep = 0; rep < alterBookNo; rep++) {
                    resultSet2.next();
                }
                if (resultSet2.getString("shipping_status").equals("Y")) {
                    System.out.println("The books in the order are shipped");
                    break;
                }
                preparedStatement2 = conn.prepareStatement(
                    "SELECT no_of_copies, unit_price FROM book WHERE ISBN = ?"
                );
                preparedStatement2.setString(1, resultSet2.getString("ISBN"));
                
                resultSet3 = preparedStatement2.executeQuery();
                resultSet3.next();
                no_of_copies = resultSet3.getInt("no_of_copies");

                theNumber = requestTheNumber(no_of_copies);
                
                if (addOrRemove.equals("add")) {
                    if (theNumber > no_of_copies) {
                        System.out.println("Number exceeds the available book copies. Book only has " + no_of_copies + ".");
                        continue;
                    }
                    System.out.println("Update is ok!");
                    //add theNumber to the quantity in ordering
                    //update total charge in orders by adding theNumber * unit_price of the book
                    //update no_of_copies in book by subtracting theNumber
                    //update date in orders to current date
                    preparedStatement3 = conn.prepareStatement(
                        "UPDATE ordering SET quantity = ? WHERE order_id = ? AND ISBN = ?"
                    );
                    preparedStatement3.setInt(1, resultSet2.getInt("quantity") + theNumber);
                    preparedStatement3.setString(2, orderIDString);
                    preparedStatement3.setString(3, resultSet2.getString("ISBN"));
                    preparedStatement3.executeQuery();

                    preparedStatement3 = conn.prepareStatement(
                        "UPDATE orders SET charge = ? WHERE order_id = ?"
                    );
                    preparedStatement3.setDouble(1, resultSet2.getDouble("charge") + theNumber * resultSet3.getDouble("unit_price"));
                    preparedStatement3.setString(2, orderIDString);
                    preparedStatement3.executeQuery();

                    preparedStatement3 = conn.prepareStatement(
                        "UPDATE book SET no_of_copies = ? WHERE ISBN = ?"
                    );
                    preparedStatement3.setInt(1, resultSet3.getInt("no_of_copies") - theNumber);
                    preparedStatement3.setString(2, resultSet2.getString("ISBN"));
                    preparedStatement3.executeQuery();

                    preparedStatement3 = conn.prepareStatement(
                        "UPDATE orders SET o_date = ? WHERE order_id = ?"
                    );
                    preparedStatement3.setDate(1, db.getSystemDate());
                    preparedStatement3.setString(2, orderIDString);
                    preparedStatement3.executeQuery();
                    
                } else if (addOrRemove.equals("remove")){
                    if (theNumber > resultSet2.getInt("quantity")) {
                        System.out.println("Number exceeds the total ordered book copies. Book only has " + resultSet2.getInt("quantity") + ".");
                        continue;
                    }
                    System.out.println("Update is ok!");
                    //subtract theNumber to the quantity in ordering
                    //update total charge in orders by subtracting theNumber * unit_price of the book
                    //update no_of_copies in book by adding theNumber
                    //update date in orders to current date
                    preparedStatement3 = conn.prepareStatement(
                        "UPDATE ordering SET quantity = ? WHERE order_id = ? AND ISBN = ?"
                    );
                    preparedStatement3.setInt(1, resultSet2.getInt("quantity") - theNumber);
                    preparedStatement3.setString(2, orderIDString);
                    preparedStatement3.setString(3, resultSet2.getString("ISBN"));
                    preparedStatement3.executeQuery();

                    preparedStatement3 = conn.prepareStatement(
                        "UPDATE orders SET charge = ? WHERE order_id = ?"
                    );
                    preparedStatement3.setDouble(1, resultSet2.getDouble("charge") - theNumber * resultSet3.getDouble("unit_price"));
                    preparedStatement3.setString(2, orderIDString);
                    preparedStatement3.executeQuery();
                    
                    preparedStatement3 = conn.prepareStatement(
                        "UPDATE book SET no_of_copies = ? WHERE ISBN = ?"
                    );
                    preparedStatement3.setInt(1, resultSet3.getInt("no_of_copies") + theNumber);
                    preparedStatement3.setString(2, resultSet2.getString("ISBN"));
                    preparedStatement3.executeQuery();

                    preparedStatement3 = conn.prepareStatement(
                        "UPDATE orders SET o_date = ? WHERE order_id = ?"
                    );
                    preparedStatement3.setDate(1, db.getSystemDate());
                    preparedStatement3.setString(2, orderIDString);
                    preparedStatement3.executeQuery();

                }
                System.out.println("update done!!");
                System.out.println("updated charge");
                resultSet2 = preparedStatement.executeQuery();
                for (int rep = 0; rep < alterBookNo; rep++) {
                    resultSet2.next();
                }
                System.out.println("order_id:" + resultSet2.getInt("order_id") + "  shipping:" + resultSet2.getString("shipping_status") + "  charge=" + resultSet2.getDouble("charge") + "  customerId:" + resultSet2.getString("customer_id"));
                System.out.println("book no: " + alterBookNo + " ISBN = " + resultSet2.getString("ISBN") + " quantity = " + resultSet2.getInt("quantity"));
                break;
            }
            
        } catch (SQLException e) {
            SQLErrorMessage();
        }
    }

    public void orderQuery(){
        try {
            Scanner scanner = new Scanner(System.in);
            
            System.out.println("Please Input CustomerID: ");
            
            //Validate customerID
            String customerID = scanner.nextLine();
            PreparedStatement preparedStatement = conn.prepareStatement(
                "SELECT customer_id FROM customer WHERE customer_id = ?"
            );
            preparedStatement.setString(1, customerID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(!resultSet.next()) {
                System.out.println("Customer not found. Please enter a valid customerID: ");
                customerID = scanner.nextLine();
                preparedStatement = conn.prepareStatement(
                    "SELECT customer_id FROM customer WHERE customer_id = ?"
                );
                preparedStatement.setString(1, customerID);
                resultSet = preparedStatement.executeQuery();
            }

            System.out.println("Please Input the Year: ");
            
            //Validate year
            String year = scanner.nextLine();
            while (!year.matches("^[0-9]{4}$")) {
                System.out.println("Invalid input. Please enter a valid year: ");
                year = scanner.nextLine();
            }

            //Extract order information
            preparedStatement = conn.prepareStatement(
                "SELECT * FROM orders WHERE TO_CHAR(o_date, 'YYYY') = ? AND customer_id = ?"
            );
            preparedStatement.setString(1, year);
            preparedStatement.setString(2, customerID);
            resultSet = preparedStatement.executeQuery();
            
            //Output order information
            if (!resultSet.next()) {
                System.out.println("No orders found.");
                return;
            }
            System.out.println("\nRecord : " + resultSet.getRow());
            System.out.println("OrderID : " + resultSet.getInt("order_id"));
            System.out.println("Order Date : " + resultSet.getString("o_date"));
            System.out.println("Charge : " + resultSet.getDouble("charge"));
            System.out.println("Shipping Status : " + resultSet.getString("shipping_status"));

            while (resultSet.next()) {
                System.out.println("\nRecord : " + resultSet.getRow());
                System.out.println("OrderID : " + resultSet.getInt("order_id"));
                System.out.println("Order Date : " + resultSet.getString("o_date"));
                System.out.println("Charge : " + resultSet.getDouble("charge"));
                System.out.println("Shipping Status : " + resultSet.getString("shipping_status"));
            }
        } catch (SQLException e) {
            SQLErrorMessage();
        }
    }
}
