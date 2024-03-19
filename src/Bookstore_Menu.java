import java.util.Date;

public class Bookstore_Menu implements Menu {

    Date system_time;

    @Override
    public void show_display() {
        String menu_string = "The System Date is now:" + system_time;
        System.out.println("The System Date is now:" + system_time);
        System.out.println();
    }

    @Override
    public void choose_option() {

    }

    @Override
    public void quit_current_menu() {

    }

    public void buildString(String[] StrArr){

    }

}
