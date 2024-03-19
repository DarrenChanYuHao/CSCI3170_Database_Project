package Handlers;

import java.util.Scanner;

public class Option_Handler {

    public int get_options(int num_of_options){
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

        return user_input;
    }
}
