public class String_Builder {

    // Optional Parameters
    private String menu_name;

    // Constructor
    private String_Builder(Build_String build_string) {
        this.menu_name = build_string.menu_name;
    }

    // Builder Class
    public static class Build_String{
        private String menu_name;
        private String options_List_String;
        private String header_String;

        public static Build_String newBuildString(){
            return new Build_String();
        }

        public Build_String setMenuName(String menu_name){
            this.menu_name = menu_name;
            return this;
        }

        public String_Builder build(){
            return new String_Builder(this);
        }

        public String getMenuName(){
            return menu_name;
        }

        public String getOptions_List_String(){
            return options_List_String;
        }

        public String getMenuHeader_String(){
            return header_String;
        }
    }

    public String getMenuOptionsList(){
        String options_List_String = null;

        switch (menu_name){
            case "Main_Menu":
                options_List_String = "1. System interface." + "\n" +
                                            "2. Customer interface." + "\n" +
                                            "3. Bookstore interface." + "\n" +
                                            "4. Show System Date." + "\n" +
                                            "5. Quit the system......";
                                            break;
            case "System_Menu":
                options_List_String = "1. Create Table." + "\n" +
                                                "2. Delete Table." + "\n" +
                                                "3. Insert Data." + "\n" +
                                                "4. Set System Date." + "\n" +
                                                "5. Back to main menu.";
                                            break;
            case "Customer_Menu":
                options_List_String = "1. Book Search." + "\n" +
                                                "2. Order Creation." + "\n" +
                                                "3. Order Altering." + "\n" +
                                                "4. Order Query." + "\n" +
                                                "5. Back to main menu.";
                                                break;
            case "Bookstore_Menu":
                options_List_String = "1. Order Update." + "\n" +
                                                "2. Order Query." + "\n" +
                                                "3. N most Popular Book Query." + "\n" +
                                                "4. Back to main menu.";
                                                break;
            default:
                options_List_String = "Invalid Menu Name.";}

        return options_List_String;
    }

    public String getMenuHeaders(){
        String header_String = null;

        switch (menu_name){
            case "Main_Menu":
                header_String = "<This is the Book Ordering System.>" + "\n" +
                                "------------------------------------";
                break;
            case "System_Menu":
                header_String = "<This is the system interface.>" + "\n" +
                                "--------------------------";
                break;
            case "Customer_Menu":
                header_String = "<This is the customer interface.>" + "\n" +
                                "---------------------------------";
                break;
            case "Bookstore_Menu":
                header_String = "<This is the bookstore interface.>" + "\n" +
                                "-----------------------------------";
                break;
            default:
                header_String = "Invalid Menu Name.";}

        return header_String;
    }

    public String getSubMenu(){
        //TODO String_Builder 1: Submenu
        return "This is the sub menu.";
    }
}
