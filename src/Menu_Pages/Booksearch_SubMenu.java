package Menu_Pages;

import Builders.String_Builder;
import Handlers.Option_Handler;

public class Booksearch_SubMenu implements Menu {

    /*
     * This class is to be used for the book search submenu displays and operations.
     */

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


    // I think this methods can be condensesd into one method but low priority. - Darren
    private void isbnSearch(){
        // TODO Book Search 1: ISBN Search
        System.out.println("Input the ISBN:");
        // do some sort of read
        // process the read
    }

    private void bookTitleSearch(){
        // TODO Book Search 2: Book Title Search
        System.out.println("Input the Book Title:");
        // do some sort of read
        // process the read
    }

    private void authorNameSearch(){
        // TODO Book Search 3: Author Name Search
        System.out.println("Input the Author Name:");
        // do some sort of read
        // process the read
    }
}
