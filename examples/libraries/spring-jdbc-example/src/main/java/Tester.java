import org.h2.Driver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

public class Tester {
    public static void main(String[] args) throws SQLException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setUrl("jdbc:h2:mem:Test");
        dataSource.setDriverClassName(Driver.class.getName());

        dataSource.getConnection().createStatement().execute("CREATE TABLE books (id INT PRIMARY KEY AUTO_INCREMENT, author TEXT, title TEXT);");

        Library library = new Library(dataSource);

        library.insert(new Book("1984", "George Orwell"));
        library.insert(new Book("The Lord of the Rings I", "J.R.R. Tolkien"));
        library.insert(new Book("The Lord of the Rings II", "J.R.R. Tolkien"));
        library.insert(new Book("The Lord of the Rings III", "J.R.R. Tolkien"));

        System.out.println(library.listAllBooks());
        System.out.println(library.listBooksByAuthor("J.R.R. Tolkien"));
        System.out.println(library.getBook(1));
    }
}
