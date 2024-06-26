package Handlers;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Option_Handler {

    /*
     * This class is to be used for all option handling operations.
     * 
     * Note regarding scanner resource leak warning:
     * https://stackoverflow.com/questions/15613626/scanner-is-never-closed
     * 
     * It is not necessary to close the scanner. If scanner is closed then System.in will also be closed which
     * prevents further input from the user. This is why the scanner is not closed in our code.
     */

    Scanner choice_Scanner;

    // Constructor
    public Option_Handler() {
        choice_Scanner = new Scanner(System.in);
    }

    public int get_user_input_Int(int book_count) {
        // This method collects user's input in the Integer datatype

        Scanner choice_Scanner = new Scanner(System.in);
        int user_input = 0;
        while (true) {
            try {
                user_input = choice_Scanner.nextInt();
                choice_Scanner.nextLine();
                if (user_input <= 0)
                    System.out.println("Invalid input. Please enter an Integer.");
                else if (user_input > book_count) {
                    user_input = book_count;
                    break;
                }
                else {
                    break;
                }
            }
            catch (Exception e) {
                System.out.println("Invalid input. Please enter an Integer.");
                choice_Scanner.nextLine();
            }
            
        }
        return user_input;
    }

    public int get_userinput_menu_options(int num_of_options){
        /*
         *    This method is to be used to prompt the user to choose a numerical option from either a menu or submenu.
         *    Once the user has chosen an option, it will return the user input.
         * 
         *    Input: num_of_options
         *    Output: user_input
         */

        System.out.println("Please enter your choice??..");

        // Get user input from 1 to num_of_options
        Scanner choice_Scanner = new Scanner(System.in);
        int user_input = 0;
        while (user_input < 1 || user_input > num_of_options) {
            try {
                user_input = Integer.parseInt(choice_Scanner.nextLine());
                if (user_input < 1 || user_input > 5) {
                    System.out.println("Invalid input. Please enter a number from 1 to " + num_of_options + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number from 1 to " + num_of_options + ".");
            }
            
        }
        return user_input;
    }

    public String get_user_input_string(){

        /*
         *    This method is to be used to prompt the user to enter a string when there is NO NEED for input validation.
         *    Once the user has entered a string, it will return the user input.
         * 
         *    Input: None
         *    Output: user_input
         */

        Scanner choice_Scanner = new Scanner(System.in);
        String user_input = choice_Scanner.nextLine();
        return user_input;
    }

    public String get_user_input_string(String Verify_Type) {

        /*
         *    This method is to be used to prompt the user to enter a string.
         *    Once the user has entered a string, it will return the user input.
         * 
         *    Input: String Verify Type
         *    Output: String user_input
         */

         // Get user input from 1 to num_of_options
        Scanner choice_Scanner = new Scanner(System.in);
        String user_input = choice_Scanner.nextLine();

        if (Verify_Type.equals("YYYY-MM")){

            // The date format needs to use mini caps for the year due to the SimpleDateFormat class.
            while (!date_format_checker(user_input, "yyyy-MM")) {
                System.out.println("Invalid input. Please enter a date in the format YYYY-MM.");
                user_input = choice_Scanner.nextLine();
            }
        }

        if (Verify_Type.equals("YYYYMMDD")){

            // The date format needs to use mini caps for the year due to the SimpleDateFormat class.
            while (!date_format_checker(user_input, "yyyyMMdd")) {
                System.out.println("Invalid input. Please enter a date in the format YYYYMMDD.");
                user_input = choice_Scanner.nextLine();
            }
        }

        if (Verify_Type.equals("folder path")){
            while (!isValidPath(user_input)) {
                System.out.println("Invalid input. Please enter a valid folder path.");
                user_input = choice_Scanner.nextLine();
            }
        }

        if (Verify_Type.equals("order ID")){
            while (!Pattern.matches("[0-9]{8}", user_input)) {
                System.out.println("Invalid input. Please enter a valid order ID.");
                user_input = choice_Scanner.nextLine();
            }
        }

        return user_input;
    }

    public boolean date_format_checker(String date, String Date_Format) {
        /*
         *    This method is to be used to check if the date is in the correct format.
         *    Once the date has been checked, it will return a boolean value.
         * 
         *    Input: String date, String Date_Format
         *    Output: boolean
         */

        SimpleDateFormat dateFormatChecker = new SimpleDateFormat(Date_Format);
        dateFormatChecker.setLenient(false);

        try {
            dateFormatChecker.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidPath(String path) {
    // https://stackoverflow.com/questions/468789/is-there-a-way-in-java-to-determine-if-a-path-is-valid-without-attempting-to-cre
    // https://www.baeldung.com/java-file-directory-exists#:~:text=To%20check%20if%20a%20file,that%20Path%20to%20the%20Files.    
        try {
            Path real_path = Paths.get(path);
            if (!Files.exists(real_path)) {
                return false;
            }
        } catch (InvalidPathException | NullPointerException ex) {
            return false;
        }
        return true;
    }
}
