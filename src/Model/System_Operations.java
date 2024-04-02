package Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
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
            System.out.println("Error Code: " + e.getErrorCode());
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
            System.out.println("Error Code: " + e.getErrorCode());
        
        }
    }

    // Insert Data
    public void insertData(String folderPath) {
        // Insert Data
        try {
            // Read all files in the folder
            List<String> FilePaths = getFilePathfromFolder(folderPath);
            List<String> FileLines = readLinesfromFile(FilePaths.get(2));
            FileLines.forEach(System.out::println);
            String entityType = getEntityTypesfromLine(FileLines.get(0));
            System.out.println(entityType);
            // Read lines in each file
            Entity_Model_Interface entity = new Book_Model("1-1234-1234-1","Database I", 100, 50);
            entity.insertinDatabase(conn);
            System.out.println("Data successfully inserted.");
            // Insert data into the SQL database
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // Set System Date
    public void setSystemDate() {
        // Set System Date
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
}
