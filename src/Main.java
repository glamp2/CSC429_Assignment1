import java.util.Properties;
import model.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String year = "1970";
        String title = "dune";
        String author = "d";

        String date = "1990-01-01";
        String name = "dav";
        String zip = "12201";

        BookCollection bookList = new BookCollection();
        PatronCollection patronList = new PatronCollection();

        System.out.println("Finding books before year " + year + ":");
        bookList.findBooksOlderThanDate(year);
        bookList.display();
        System.out.println();

        System.out.println("Finding books after year " + year + ":");
        bookList.findBooksNewerThanDate(year);
        bookList.display();
        System.out.println();

        System.out.println("Finding books with title like '" + title + "'" + ":");
        bookList.findBooksWithTitleLike(title);
        bookList.display();
        System.out.println();

        System.out.println("Finding books with author like '" + author + "'" + ":");
        bookList.findBooksWithAuthorLike(author);
        bookList.display();
        System.out.println();

        System.out.println("Finding patrons with date of birth before '" + date + "'" + ":");
        patronList.findPatronsOlderThan(date);
        patronList.display();
        System.out.println();

        System.out.println("Finding patrons with date of birth after '" + date + "'" + ":");
        patronList.findPatronsYoungerThan(date);
        patronList.display();
        System.out.println();

        System.out.println("Finding patrons with name like '" + name + "'" + ":");
        patronList.findPatronsWithNameLike(name);
        patronList.display();
        System.out.println();

        System.out.println("Finding patrons with zip code '" + zip + "'" + ":");
        patronList.findPatronsAtZipCode(zip);
        patronList.display();
        System.out.println();

        /*
        System.out.println("Adding new book 'The Martian'...");

        Properties newBookProps = new Properties();

        newBookProps.setProperty("bookTitle", "The Martian");
        newBookProps.setProperty("author", "Andy Weir");
        newBookProps.setProperty("pubYear", "2011");

        Book newBook = new Book(newBookProps);

        newBook.save();

        System.out.println("Finding books after year " + year + ":");
        bookList.findBooksNewerThanDate(year);
        bookList.display();
        System.out.println();

        System.out.println("Adding new patron 'Bob Marley'...");

        Properties newPatronProps = new Properties();

        newPatronProps.setProperty("name", "Bob Marley");
        newPatronProps.setProperty("address","420 Happy Lane");
        newPatronProps.setProperty("city", "Albany");
        newPatronProps.setProperty("stateCode", "NY");
        newPatronProps.setProperty("zip", "12201");
        newPatronProps.setProperty("email", "bobmarley@gmail.com");
        newPatronProps.setProperty("dateOfBirth", "1945-02-06");

        Patron newPatron = new Patron(newPatronProps);

        newPatron.save();

        System.out.println("Finding patrons with date of birth before '" + date + "'" + ":");
        patronList.findPatronsOlderThan(date);
        patronList.display();
        System.out.println();
        */

        System.out.println("Goodbye for now!");
    }
}