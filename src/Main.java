import java.util.Properties;
import model.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String year = "1970";
        String title = "dune";
        String author = "d";

        BookCollection bookList = new BookCollection();

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

        System.out.println("Goodbye for now!");
        /*
        Properties newBookProps = new Properties();

        newBookProps.setProperty("bookTitle", "The Martian");
        newBookProps.setProperty("author", "Andy Weir");
        newBookProps.setProperty("pubYear", "2011");
        newBookProps.setProperty("status", "Active");

        Book newBook = new Book(newBookProps);

        newBook.save();

        System.out.println("Finding books after year " + year + ":");
        bookList.findBooksNewerThanDate(year);
        bookList.display();
        System.out.println();
        */
    }
}