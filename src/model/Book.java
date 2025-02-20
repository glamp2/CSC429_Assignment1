package model;

// system imports

import exception.InvalidPrimaryKeyException;
import impresario.IView;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;


public class Book extends EntityBase implements IView {

    private static final String myTableName = "Book";

    protected Properties dependencies;

    // GUI Components

    private String updateStatusMessage = "";

    // Constructor for this class
    public Book(String bookId) throws InvalidPrimaryKeyException {
        super(myTableName);

        setDependencies();
        String query = "SELECT * FROM " + myTableName + " WHERE (bookId = " + bookId + ")";

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        // You must get one book at least
        if (allDataRetrieved != null) {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one book. More than that is an error.
            if (size != 1) {
                throw new InvalidPrimaryKeyException("Multiple books matching id: " + bookId + " found.");
            } else {
                // copy all the retrieved data into persistent state
                Properties retrievedBookData = allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedBookData.propertyNames();
                while (allKeys.hasMoreElements() == true) {
                    String nextKey = (String)allKeys.nextElement();
                    String nextValue = retrievedBookData.getProperty(nextKey);
                    // bookId = Integer.parseInt(retrievedBookData.getProperty("bookId"));

                    if (nextValue != null) {
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }
            }
        }
        // If no book found for this bookId, throw an exception
        else {
            throw new InvalidPrimaryKeyException("No book matching id: " + bookId + " found.");
        }
    }

    public Book(Properties props){
        super(myTableName);

        setDependencies();
        persistentState = new Properties();
        Enumeration allKeys = props.propertyNames();
        while (allKeys.hasMoreElements() == true){
            String nextKey = (String)allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);

            if (nextValue != null){
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }

    public void save() {
        updateStateInDatabase();
    }

    private void updateStateInDatabase()
    {
        try
        {
            if (persistentState.getProperty("bookId") != null)
            {
                // update
                Properties whereClause = new Properties();
                whereClause.setProperty("bookId", persistentState.getProperty("bookId"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Book data for book id: " + persistentState.getProperty("bookId") + " updated successfully in database!";
            }
            else
            {
                // insert
                Integer bookId =
                        insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("bookId", "" + bookId.intValue());
                updateStatusMessage = "Book data for new book: " +  persistentState.getProperty("bookId")
                        + "installed successfully in database!";
            }
        }
        catch (SQLException ex)
        {
            updateStatusMessage = "Error in installing book data in database!";
        }
        //DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
    }

    private void setDependencies()
    {
        dependencies = new Properties();

        myRegistry.setDependencies(dependencies);
    }

    public Object getState(String key)
    {
        if (key.equals("UpdateStatusMessage") == true)
            return updateStatusMessage;

        return persistentState.getProperty(key);
    }

    public void stateChangeRequest(String key, Object value)
    {

        myRegistry.updateSubscribers(key, this);
    }

    /** Called via the IView relationship */
    //----------------------------------------------------------
    public void updateState(String key, Object value)
    {
        stateChangeRequest(key, value);
    }

    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }

    @Override
    public String toString() {
        return (String)persistentState.get("bookId") + ", "
                + (String)persistentState.get("bookTitle") + ", "
                + (String)persistentState.get("author") + ", "
                + (String)persistentState.get("pubYear") + ", "
                + (String)persistentState.get("status");
    }
}
