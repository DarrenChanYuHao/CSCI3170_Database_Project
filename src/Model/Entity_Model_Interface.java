package Model;

import java.sql.Connection;

public interface Entity_Model_Interface {

    public void insertinDatabase(Connection conn);
    public void updateinDatabase();
    public void readfromDatabase();
    public void deleteinDatabase();
}
