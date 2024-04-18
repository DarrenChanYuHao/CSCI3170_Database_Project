package Menu_Pages;

import Builders.String_Builder;
import Handlers.Option_Handler;

import java.sql.*;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Booksearch_SubMenu implements Menu {

    private Connection conn;
    /*
     * This class is to be used for the book search submenu displays and operations.
     */

    // Constructor
    public Booksearch_SubMenu(Connection conn) {
        // Initialise Database
        this.conn = conn;
    }

    @Override
    public void show_display() {
        /*
         *    This method is to be used to display the book search submenu. Once called, it will display
         *    the book search submenu and call the choose_option method to prompt the user to choose an option.
         *
         *    Input: None
         *    Output: None
         */
        String_Builder book_search = new String_Builder.Build_String().setMenuName("BookSearch_Menu").build();
        System.out.println(book_search.getSubMenuHeaders());
        System.out.println(book_search.getSubMenuOptionsList());
        choose_option();
    }

    @Override
    public void choose_option() {
        /*
         *    This method is to be used to prompt the user to choose an option from the book search submenu.
         *    Once the user has chosen an option, it will call the corresponding method.
         *
         *    The options are:
         *    1. ISBN Search
         *    2. Book Title Search
         *    3. Author Name Search
         *    4. Back to Customer Menu
         *
         *    Input: None
         *    Output: None
         */
        
        Option_Handler optionHandler = new Option_Handler();
        int user_input = optionHandler.get_userinput_menu_options(4);

        // Switch case for user input
        switch (user_input){
            case 1:
                isbnSearch();
                break;
            case 2:
                bookTitleSearch();
                break;
            case 3:
                authorNameSearch();
                break;
            case 4:
                System.exit(0);
                break;
        }
    }

    private void bookSearchOutput(ResultSet resultSet) {
        try {
            String currentISBN = "";
            Integer recordCount = 0;
            Integer authorCount = 0;
            while (resultSet.next()) {
                if(currentISBN.equals(resultSet.getString("ISBN"))) {
                    authorCount++;
                    System.out.println(authorCount + " :" + resultSet.getString("author_name"));
                } else {
                    recordCount++;
                    authorCount = 1;
                    System.out.println("");
                    System.out.println("Record " + recordCount);
                    currentISBN = resultSet.getString("ISBN");
                    System.out.println("ISBN: " + currentISBN);
                    System.out.println("Book Title:" + resultSet.getString("title"));
                    System.out.println("Unit Price:" + resultSet.getDouble("unit_price"));
                    System.out.println("No. Of Available:" + resultSet.getInt("no_of_copies"));
                    System.out.println("Authors:");
                    System.out.println(authorCount + " :" + resultSet.getString("author_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void isbnSearch(){
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Input the ISBN: ");
            String isbn = scanner.nextLine();

            //ISBN Validation
            PreparedStatement preparedStatement = conn.prepareStatement(
                "SELECT ISBN FROM book WHERE ISBN = ?"
            );
            preparedStatement.setString(1, isbn);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(!resultSet.next()) {
                System.out.println("Book not found. Please enter a valid ISBN: ");
                isbn = scanner.nextLine();
                preparedStatement = conn.prepareStatement(
                    "SELECT ISBN FROM book WHERE ISBN = ?"
                );
                preparedStatement.setString(1, isbn);
                resultSet = preparedStatement.executeQuery();
            }

            //Extract Book Information
            preparedStatement = conn.prepareStatement(
                "SELECT b.ISBN, b.title, b.unit_price, b.no_of_copies, a.author_name FROM book b JOIN book_author a ON b.ISBN = a.ISBN WHERE b.ISBN = ? ORDER BY b.title ASC, b.ISBN ASC, a.author_name ASC"
            );
            preparedStatement.setString(1, isbn);
            resultSet = preparedStatement.executeQuery();
            
            //Output Book Information
            bookSearchOutput(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void bookTitleSearch(){
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Input the Book Title: ");
            String title = scanner.nextLine();
            String cleanTitleString = title.replace("%", "").replace("_", "");

            //Book Title Validation
            PreparedStatement preparedStatement = conn.prepareStatement(
                "SELECT title FROM book WHERE title LIKE ?"
            );
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(!resultSet.next()) {
                System.out.println("Book not found. Please enter a valid Book Title: ");
                title = scanner.nextLine();
                cleanTitleString = title.replace("%", "").replace("_", "");
                preparedStatement = conn.prepareStatement(
                    "SELECT title FROM book WHERE title LIKE ?"
                );
                preparedStatement.setString(1, title);
                resultSet = preparedStatement.executeQuery();
            }

            //Extract Book Information
            preparedStatement = conn.prepareStatement(
                "SELECT b.ISBN, b.title, b.unit_price, b.no_of_copies, a.author_name FROM book b JOIN book_author a ON b.ISBN = a.ISBN WHERE b.title LIKE ? ORDER BY CASE WHEN b.title = ? THEN 0 ELSE 1 END, b.title ASC, b.ISBN ASC, a.author_name ASC"
            );
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, cleanTitleString);
            resultSet = preparedStatement.executeQuery();
            
            //Output Book Information
            bookSearchOutput(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void authorNameSearch(){
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Input the Author Name: ");
            String authorName = scanner.nextLine();
            String cleanAuthorNameString = authorName.replace("%", "").replace("_", "");

            //Author Name Validation
            PreparedStatement preparedStatement = conn.prepareStatement(
                "SELECT author_name FROM book_author WHERE author_name LIKE ?"
            );
            preparedStatement.setString(1, authorName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(!resultSet.next()) {
                System.out.println("Author not found. Please enter a valid Author Name: ");
                authorName = scanner.nextLine();
                cleanAuthorNameString = authorName.replace("%", "").replace("_", "");
                preparedStatement = conn.prepareStatement(
                    "SELECT author_name FROM book_author WHERE author_name LIKE ?"
                );
                preparedStatement.setString(1, authorName);
                resultSet = preparedStatement.executeQuery();
            }

            //Extract Book Information
            preparedStatement = conn.prepareStatement(
                "SELECT b.ISBN, b.title, b.unit_price, b.no_of_copies, a.author_name FROM book b JOIN book_author a ON b.ISBN = a.ISBN WHERE b.ISBN IN ( SELECT ISBN FROM book_author WHERE author_name LIKE ? ) ORDER BY CASE WHEN a.author_name = ? THEN 0 ELSE 1 END, b.title ASC, b.ISBN ASC, a.author_name ASC"
            );
            preparedStatement.setString(1, authorName);
            preparedStatement.setString(2, cleanAuthorNameString);
            resultSet = preparedStatement.executeQuery();
            
            //Output Book Information
            bookSearchOutput(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
