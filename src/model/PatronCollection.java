package model;

import java.util.Properties;
import java.util.Vector;



public class PatronCollection extends EntityBase {
    private static final String myTableName = "Patron";

    private Vector<Patron> patronList;
    // GUI Components

    // constructor for this class
    //----------------------------------------------------------
    public PatronCollection() throws
            Exception {
        super(myTableName);
        patronList = new Vector<Patron>();
    }

    /**
     *
     */
    //----------------------------------------------------------
    public Object getState(String key) {
        if (key.equals("Patrons"))
            return patronList;
        else if (key.equals("PatronList"))
            return this;
        return null;
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {

        myRegistry.updateSubscribers(key, this);
    }


    /**
     * Called via the IView relationship
     */
    //----------------------------------------------------------
    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }

    //------------------------------------------------------


    //-----------------------------------------------------------------------------------
    protected void initializeSchema(String tableName) {
        if (mySchema == null) {
            mySchema = getSchemaInfo(tableName);
        }

    }

    public void findPatronsOlderThan(String date)
    {
        String query = "SELECT * FROM " + myTableName + " WHERE (dateOfBirth < '" + date + "')";

        processQuery(query);
    }

    public void findPatronsYoungerThan(String date)
    {
        String query = "SELECT * FROM " + myTableName + " WHERE (dateOfBirth > '" + date + "')";

        processQuery(query);
    }

    public void findPatronsAtZipCode(String zip)
    {
        String query = "SELECT * FROM " + myTableName + " WHERE (zip = '" + zip + "')";

        processQuery(query);
    }
    public void findPatronsWithNameLike(String name)
    {
        String query = "SELECT * FROM " + myTableName + " WHERE (name LIKE '%" + name + "%')";

        processQuery(query);
    }

    private void processQuery(String query){

        patronList.clear();

        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null)
        {

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
            {
                Properties nextPatronData = (Properties)allDataRetrieved.elementAt(cnt);

                Patron patron = new Patron(nextPatronData);

                if (patron != null)
                {
                    patronList.add(patron);
                }
            }
        }
    }

    public void display(){
        for(int i = 0; i < patronList.size(); i++){
            System.out.println(patronList.elementAt(i));
        }
    }

}
