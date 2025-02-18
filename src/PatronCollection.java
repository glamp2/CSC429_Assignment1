import event.Event;
import exception.InvalidPrimaryKeyException;
import model.Patron;
import model.PatronHolder;
import model.EntityBase;

import java.util.Properties;
import java.util.Vector;



public class PatronCollection extends EntityBase {
    private static final String myTableName = "Patron";

    private Vector<Patron> patrons;
    // GUI Components

    // constructor for this class
    //----------------------------------------------------------
    public PatronCollection(PatronHolder cust) throws
            Exception {
        super(myTableName);

        if (cust == null) {
            new Event(Event.getLeafLevelClassName(this), "<init>",
                    "Missing patron information", Event.FATAL);
            throw new Exception
                    ("UNEXPECTED ERROR: AccountCollection.<init>: Patron information is null");
        }

        String PatronId = (String) cust.getState("ID");

        if (PatronId == null) {
            new Event(Event.getLeafLevelClassName(this), "<init>",
                    "Data corrupted: Patron has no id in database", Event.FATAL);
            throw new Exception
                    ("UNEXPECTED ERROR: AccountCollection.<init>: Data corrupted: account holder has no id in repository");
        }

        String query = "SELECT * FROM " + myTableName + " WHERE (OwnerID = " + PatronId + ")";

        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null) {
            patrons = new Vector<Patron>();

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++) {
                Properties nextPatronData = (Properties) allDataRetrieved.elementAt(cnt);

                Patron account = new Patron(nextPatronData);

                if (account != null) {
                    addPatron(account);
                }
            }

        } else {
            throw new InvalidPrimaryKeyException("No Patrons for customer : "
                    + PatronId + ". Name : " + cust.getState("Name"));
        }

    }

    //----------------------------------------------------------------------------------
    private void addPatron(Patron a) {
        //accounts.add(a);
        int index = findIndexToAdd(a);
        patrons.insertElementAt(a, index); // To build up a collection sorted on some key
    }

    //----------------------------------------------------------------------------------
    private int findIndexToAdd(Patron a) {
        //users.add(u);
        int low = 0;
        int high = patrons.size() - 1;
        int middle;

        while (low <= high) {
            middle = (low + high) / 2;

            Patron midSession = patrons.elementAt(middle);

            int result = Patron.compare(a, midSession);

            if (result == 0) {
                return middle;
            } else if (result < 0) {
                high = middle - 1;
            } else {
                low = middle + 1;
            }


        }
        return low;
    }


    /**
     *
     */
    //----------------------------------------------------------
    public Object getState(String key) {
        if (key.equals("Accounts"))
            return patrons;
        else if (key.equals("AccountList"))
            return this;
        return null;
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {

        myRegistry.updateSubscribers(key, this);
    }

    //----------------------------------------------------------
    public Patron retrieve(String accountNumber) {
        Patron retValue = null;
        for (int cnt = 0; cnt < patrons.size(); cnt++) {
            Patron nextAcct = patrons.elementAt(cnt);
            String nextAccNum = (String) nextAcct.getState("AccountNumber");
            if (nextAccNum.equals(accountNumber) == true) {
                retValue = nextAcct;
                return retValue; // we should say 'break;' here
            }
        }

        return retValue;
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
        String query = "SELECT * FROM " + myTableName + " WHERE (DateOfBirth > '" + date + "')";
        Vector allDataRetrieved = getSelectQueryResult(query);
        if (allDataRetrieved != null)
        {
            patrons = new Vector<Patron>();

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++) {
                Properties nextPatronData = (Properties) allDataRetrieved.elementAt(cnt);

                Patron Patron = new Patron(nextPatronData);

                if (Patron != null)
                {
                    patrons.addElement(Patron);
                }

            }

        }

    }

    public void findPatronsYoungerThan(String date)
    {
        String query = "SELECT * FROM " + myTableName + " WHERE (DateOfBirth < '" + date + "')";
        Vector allDataRetrieved = getSelectQueryResult(query);
        if (allDataRetrieved != null)
        {
            patrons = new Vector<Patron>();

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++) {
                Properties nextPatronData = (Properties) allDataRetrieved.elementAt(cnt);

                Patron Patron = new Patron(nextPatronData);

                if (Patron != null)
                {
                    patrons.addElement(Patron);
                }

            }

        }

    }

    public void findPatronsAtZipCode(String zip)
    {
        String query = "SELECT * FROM " + myTableName + " WHERE (zip = '" + zip + "')";
        Vector allDataRetrieved = getSelectQueryResult(query);
        if (allDataRetrieved != null)
        {
            patrons = new Vector<Patron>();

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++) {
                Properties nextPatronData = (Properties) allDataRetrieved.elementAt(cnt);

                Patron Patron = new Patron(nextPatronData);

                if (Patron != null)
                {
                    patrons.addElement(Patron);
                }

            }

        }

    }
    public void findPatronsWithNameLike(String name)
    {
        String query = "SELECT * FROM " + myTableName + " WHERE (DateOfBirth = " + name + ")";
        Vector allDataRetrieved = getSelectQueryResult(query);
        if (allDataRetrieved != null)
        {
            patrons = new Vector<Patron>();

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++) {
                Properties nextPatronData = (Properties) allDataRetrieved.elementAt(cnt);

                Patron Patron = new Patron(nextPatronData);

                if (Patron != null)
                {
                    patrons.addElement(Patron);
                }

            }

        }

    }

}
