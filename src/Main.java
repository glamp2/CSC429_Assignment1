import java.util.Properties;
import model.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BookCollection bookSet = new BookCollection();

        bookSet.findBooksWithTitleLike("dune");
        bookSet.display();
    }
}