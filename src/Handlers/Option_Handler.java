package Handlers;

import java.util.Scanner;

public class Option_Handler {

    /*
     * This class is to be used for all option handling operations.
     */

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
        Scanner choice_Scanner = new Scanner(System.console().reader());
        int user_input = 0;

        while (user_input < 1 || user_input > num_of_options) {
            try {
                user_input = choice_Scanner.nextInt();
                if (user_input < 1 || user_input > 5) {
                    System.out.println("Invalid input. Please enter a number from 1 to" + num_of_options + ".");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number from 1 to" + num_of_options + ".");
                choice_Scanner.nextLine();
            }
        }

        choice_Scanner.close();

        return user_input;
    }
}
