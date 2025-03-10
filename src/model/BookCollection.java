package model;

// system imports

import impresario.IView;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

import java.util.Properties;
import java.util.Vector;


/** The class containing the BookCollection for the ATM application */
//==============================================================
public class BookCollection extends EntityBase implements IView
{
    private static final String myTableName = "Book";

    private Vector<Book> bookList;
    // GUI Components

    // constructor for this class
    //----------------------------------------------------------
    public BookCollection() throws
            Exception {
        super(myTableName);
        bookList = new Vector<Book>();
    }

    public void findBooksOlderThanDate(String year){

        String query = "SELECT * FROM " + myTableName + " WHERE (pubYear <= '" + year + "')";

        processQuery(query);
    }

    public void findBooksNewerThanDate(String year){

        String query = "SELECT * FROM " + myTableName + " WHERE (pubYear >= '" + year + "')";

        processQuery(query);
    }

    public void findBooksWithTitleLike(String title){

        String query = "SELECT * FROM " + myTableName + " WHERE (bookTitle LIKE '%" + title + "%')";

        processQuery(query);
    }

    public void findBooksWithAuthorLike(String author) {

        String query = "SELECT * FROM " + myTableName + " WHERE (author LIKE '%" + author + "%')";

        processQuery(query);
    }

    private void processQuery(String query){

        bookList.clear();

        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null)
        {

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
            {
                Properties nextBookData = (Properties)allDataRetrieved.elementAt(cnt);

                Book book = new Book(nextBookData);

                if (book != null)
                {
                    bookList.add(book);
                }
            }
        }
    }


    /**
     *
     */
    //----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("Books"))
            return bookList;
        else
        if (key.equals("BookList"))
            return this;
        return null;
    }

    //----------------------------------------------------------------
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

    //------------------------------------------------------
    protected void createAndShowView()
    {

        Scene localScene = myViews.get("BookCollectionView");

        if (localScene == null)
        {
            // create our new view
            View newView = ViewFactory.createView("BookCollectionView", this);
            localScene = new Scene(newView);
            myViews.put("BookCollectionView", localScene);
        }
        // make the view visible by installing it into the frame
        swapToView(localScene);

    }

    //-----------------------------------------------------------------------------------
    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }

    public void display(){
        for(int i = 0; i < bookList.size(); i++){
            System.out.println(bookList.elementAt(i));
        }
    }
}
