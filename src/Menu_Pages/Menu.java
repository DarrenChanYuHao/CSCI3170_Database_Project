package Menu_Pages;

import java.io.IOException;

public interface Menu {
    /*
     * This interface is to be used for all menu operations.
     */

    public void show_display();
    public void choose_option() throws IOException;
}
