package Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class System_Operations {

    // Attributes
    private Connection conn;
    
    // Constructor
    public System_Operations(Connection conn) {
        this.conn = conn;
    }

    // Create Table
    public void createTable(String[] tableNames, String[] tableColumns) {
        // Create Table
        try {
            for (int i = 0; i < tableNames.length; i++) {
                PreparedStatement pstmt =
                        conn.prepareStatement( "create table " + tableNames[i] + tableColumns[i] );
                pstmt.execute();
            } 
            System.out.println("Table successfully created.");
        }
        catch (SQLException e) {
            System.out.println("Error Code:" + e.getErrorCode());
            System.out.println("Please check the SQL error code for more information");
        }
    }

    // Delete Table
    public void deleteTable(String[] tableNames) {
        // Delete Table
        try {
            // Delete all table in the SQL database
            for (String tableName : tableNames) {
                PreparedStatement pstmt =
                        conn.prepareStatement( "drop table " + tableName );
                pstmt.execute();
            }
            System.out.println("Table successfully deleted.");
        }
        catch (SQLException e) {
            System.out.println("Error Code:" + e.getErrorCode());
            System.out.println("Please check the SQL error code for more information");
        }
    }

    // Insert Data
    public void insertData(String folderPath) {
        // Insert Data
        try {
            // Read all files in the folder
            List<String> FilePaths = getFilePathfromFolder(folderPath);

            // For each file, read the lines
            for (String filePath : FilePaths) {
                List<String> FileLines = readLinesfromFile(filePath);
                String entityType = getEntityTypesfromLine(FileLines.get(0));
                
                // Check data is in the correct format
                Boolean valid_contents = false;

                valid_contents = checkContentsFromLine(FileLines, entityType);

                if (!valid_contents) {
                    System.out.println("Invalid data format.");
                    return;
                }

                for (String line : FileLines) {
                    List<String> data = getDatafromLine(line);

                    // Insert data depending on the entity type
                    switch (entityType) {
                        case "book":
                            Entity_Model_Interface book = new Book_Model(data.get(0), data.get(1), Integer.parseInt(data.get(2)), Integer.parseInt(data.get(3)));
                            book.insertinDatabase(conn);
                            break;
                        case "customers":
                            Entity_Model_Interface customer = new Customer_Model(data.get(0), data.get(1), data.get(2), data.get(3));
                            customer.insertinDatabase(conn);
                            break;
                        case "orders":
                            Date date = Date.valueOf(data.get(1));
                            Entity_Model_Interface order = new Order_model(data.get(0), date, data.get(2), Integer.parseInt(data.get(3)), data.get(4));
                            order.insertinDatabase(conn);
                            break;
                        case "ordering":
                            Entity_Model_Interface ordering = new Ordering_model(data.get(0), data.get(1), Integer.parseInt(data.get(2)));
                            ordering.insertinDatabase(conn);
                            break;
                        case "book_author":
                            Entity_Model_Interface book_author = new BookAuthor_Model(data.get(0), data.get(1));
                            book_author.insertinDatabase(conn);
                            break;
                        default:
                            System.out.println("Invalid entity type.");
                            break;
                    }
                }

            }
            System.out.println("Data successfully inserted.");
            System.out.println("Data is loaded!");
            // Insert data into the SQL database
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Please check the error code for more information");
        }
    }
    
    public List<String> getFilePathfromFolder(String folderPath) throws IOException {
        // https://stackoverflow.com/questions/1844688/how-can-i-read-all-files-in-a-folder-from-java
        // Get File Path from Folder

        List<String> files = Files.walk(Paths.get(folderPath))
                .filter(Files::isRegularFile)
                .map(Path::toString)
                .collect(Collectors.toList());

        return files;
    }

    public List<String> readLinesfromFile(String filePath) throws IOException {
        // Read Lines from File
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        return lines;
    }

    public String getEntityTypesfromLine(String line) {
        // Get Data Types from line

        // Split line by |
        String[] data = line.split("\\|");

        // Get Entity Type
        if (data.length == 4){
    
            // If the first data is longer than 10 characters and 13 characters exactly, it is a book.
            // This is because ISBN is 13 characters long.
            // Customer ID is maximum 10 characters long.
            if (data[0].length() > 10 && data[0].length() == 13){
                return "book";
            }
            else {
                return "customers";
            }
        }
        else if (data.length == 5){
            // orders
            return "orders";
        }
        else if (data.length == 3){
            // ordering
            return "ordering";
        }
        else if (data.length == 2){
            // book_author
            return "book_author";
        }
        else {
            return "Invalid";
        }
    }

    public List<String> getDatafromLine(String line) {
        // Get Data from Line
        List<String> data = List.of(line.split("\\|"));
        return data;
    }

    public boolean checkContentsFromLine(List<String> lines, String entityType) {
        // Check Contents from Line
        for (String line : lines) {
            List<String> data = getDatafromLine(line);
            switch (entityType) {
                case "book":
                    // Check that it is in ISBN format
                    if (data.get(0).length() != 13) {
                        System.out.println("Invalid data format for book for ISBN.");
                        return false;
                    }

                    // Check that the title does not contain % and _
                    if (data.get(1).contains("%") || data.get(1).contains("_")) {
                        System.out.println("Invalid data format for book for title.");
                        return false;
                    }

                    // Check that the unit price is a number
                    try {
                        Integer.parseInt(data.get(2));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid data format for book for unit price.");
                        return false;
                    }

                    // Check that the number of copies is a number
                    try {
                        Integer.parseInt(data.get(3));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid data format for book for number of copeis.");
                        return false;
                    }
                    break;
                case "customers":
                    // Check that the customer ID is not longer than 10 characters
                    if (data.get(0).length() > 10) {
                        System.out.println("Invalid data format for customers for customer ID.");
                        return false;
                    }

                    // Check that the name does not contain % and _
                    if (data.get(1).contains("%") || data.get(1).contains("_")) {
                        System.out.println("Invalid data format for customers for customer name.");
                        return false;
                    }

                    // Check that the address does not contain % and _
                    if (data.get(2).contains("%") || data.get(2).contains("_")) {
                        System.out.println("Invalid data format for customers or shipping address.");
                        return false;
                    }

                    // Check that the credit card number is not longer than 19 characters
                    if (data.get(3).length() > 19) {
                        System.out.println("Invalid data format for customers for credit card number.");
                        return false;
                    }
                    break;
                case "orders":
                    // Check that the order ID is not longer than 8 characters
                    if (data.get(0).length() > 8) {
                        System.out.println("Invalid data format for orders for order ID.");
                        return false;
                    }

                    // Check that the date is in the format yyyy-mm-dd
                    while (!Pattern.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}", data.get(1))) {
                        System.out.println("Invalid data format for orders for date.");
                        return false;
                    }

                    // Check that shipping status is either Y or N
                    if (!data.get(2).equals("Y") && !data.get(2).equals("N")) {
                        System.out.println("Invalid data format for orders for shipping status.");
                        return false;
                    }

                    // Check that the charge is a number
                    try {
                        Integer.parseInt(data.get(3));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid data format for orders for charge.");
                        return false;
                    }

                    // Check that the customer ID is not longer than 10 characters
                    if (data.get(4).length() > 10) {
                        System.out.println("Invalid data format for orders for customer ID.");
                        return false;
                    }
                    break;
                case "ordering":
                    // Check that the order ID is not longer than 8 characters
                    if (data.get(0).length() > 8) {
                        System.out.println("Invalid data format for ordering for order ID.");
                        return false;
                    }

                    // Check that the ISBN is not longer than 13 characters
                    if (data.get(1).length() != 13) {
                        System.out.println("Invalid data format for ordering for ISBN.");
                        return false;
                    }

                    // Check it is in the ISBN format
                    // x-xxxx-xxxx-x
                    while (!Pattern.matches("[0-9]{1}-[0-9]{4}-[0-9]{4}-[0-9]{1}", data.get(1))) {
                        System.out.println("Invalid data format for ordering for ISBN.");
                        return false;
                    }

                    // Check that the quantity is a number
                    try {
                        Integer.parseInt(data.get(2));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid data format for ordering for quantity.");
                        return false;
                    }
                    break;
                case "book_author":
                    if (data.size() != 2) {
                        System.out.println("Invalid data format for book_author.");
                        return false;
                    }
                    break;
                default:
                    System.out.println("Invalid entity type.");
                    return false;
            }
        }
        return true;
    }
}
