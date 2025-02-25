package model;

// system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import javax.swing.JFrame;

// project imports
import exception.InvalidPrimaryKeyException;
import database.*;
import model.EntityBase;

import impresario.IView;

import userinterface.View;
import userinterface.ViewFactory;


public class Patron extends EntityBase implements IView {

	private static final String myTableName = "Patron";

	protected Properties dependencies;

	// GUI Components

	private String updateStatusMessage = "";

	// Constructor for this class
	public Patron(String patronId) throws InvalidPrimaryKeyException {
		super(myTableName);

		setDependencies();
		String query = "SELECT * FROM " + myTableName + " WHERE (patronId = " + patronId + ")";

		Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

		// You must get one patron at least
		if (allDataRetrieved != null) {
			int size = allDataRetrieved.size();

			// There should be EXACTLY one patron. More than that is an error.
			if (size != 1) {
				throw new InvalidPrimaryKeyException("Multiple patrons matching id: " + patronId + " found.");
			} else {
				// copy all the retrieved data into persistent state
				Properties retrievedPatronData = allDataRetrieved.elementAt(0);
				persistentState = new Properties();

				Enumeration allKeys = retrievedPatronData.propertyNames();
				while (allKeys.hasMoreElements() == true) {
					String nextKey = (String)allKeys.nextElement();
					String nextValue = retrievedPatronData.getProperty(nextKey);
					// bookId = Integer.parseInt(retrievedBookData.getProperty("bookId"));

					if (nextValue != null) {
						persistentState.setProperty(nextKey, nextValue);
					}
				}
			}
		}
		// If no book found for this bookId, throw an exception
		else {
			throw new InvalidPrimaryKeyException("No patron matching id: " + patronId + " found.");
		}
	}

	public Patron(Properties props){
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
			if (persistentState.getProperty("patronId") != null)
			{
				// update
				Properties whereClause = new Properties();
				whereClause.setProperty("patronId",
						persistentState.getProperty("patronId"));
				updatePersistentState(mySchema, persistentState, whereClause);
				updateStatusMessage = "Patron data for patronId: " + persistentState.getProperty("patronId") + " updated successfully in database!";
			}
			else
			{
				// insert
				Integer patronId =
						insertAutoIncrementalPersistentState(mySchema, persistentState);
				persistentState.setProperty("patronId", "" + patronId.intValue());
				updateStatusMessage = "Patron data for new patron: " +  persistentState.getProperty("patronId")
						+ "installed successfully in database!";
			}
		}
		catch (SQLException ex)
		{
			updateStatusMessage = "Error in installing patron data in database!";
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
		return (String)persistentState.get("patronId") + ", "
				+ (String)persistentState.get("name") + ", "
				+ (String)persistentState.get("address") + ", "
				+ (String)persistentState.get("city") + ", "
				+ (String)persistentState.get("stateCode") + ", "
				+ (String)persistentState.get("zip") + ", "
				+ (String)persistentState.get("email") + ", "
				+ (String)persistentState.get("dateOfBirth") + ", "
				+ (String)persistentState.get("status");
	}
}