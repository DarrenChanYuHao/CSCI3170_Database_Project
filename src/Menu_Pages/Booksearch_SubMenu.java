package Menu_Pages;

import Builders.String_Builder;
import Handlers.Option_Handler;

public class Booksearch_SubMenu implements Menu {

    @Override
    public void show_display() {
        String_Builder book_search = new String_Builder.Build_String().setMenuName("BookSearch_Menu").build();
        System.out.println(book_search.getSubMenuHeaders());
        System.out.println(book_search.getSubMenuOptionsList());
    }

    @Override
    public void choose_option() {
        Option_Handler optionHandler = new Option_Handler();
        int user_input = optionHandler.get_options(4);

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
