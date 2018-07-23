import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;

public class Library {
    private final JdbcOperations jdbcTemplate;
    private final TransactionTemplate transactionTemplate;
    private final SimpleJdbcInsert insertBookTemplate;

    public Library(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.transactionTemplate = new TransactionTemplate(new DataSourceTransactionManager(dataSource));

        this.insertBookTemplate = new SimpleJdbcInsert(dataSource)
                .withTableName("books")
                .usingColumns("author", "title")
                .usingGeneratedKeyColumns("id");
    }

    public List<Integer> insertMultipleBooks(final List<Book> books) {
        return transactionTemplate.execute(transactionStatus -> books
                .stream()
                .peek(this::insert)
                .map(Book::getId)
                .collect(Collectors.toList())
        );
    }

    public void updateMultipleBooks(final List<Book> books) {
        transactionTemplate.execute(transactionStatus -> {
            books.forEach(this::update);
            return null;
        });
    }

    public int getBookCount() {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM book",
                Integer.class
        );
    }

    public Book getBook(final int bookId) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM books WHERE id = ?",
                new Object[]{bookId},
                new BookRowMapper()
        );
    }

    public List<Book> listBooksByAuthor(final String author) {
        return jdbcTemplate.query(
                "SELECT * FROM books WHERE author = ? ORDER BY title",
                new Object[]{author},
                new BookRowMapper()
        );
    }

    public List<Book> listAllBooks() {
        return jdbcTemplate.query(
                "SELECT * FROM books ORDER BY title",
                new BookRowMapper()
        );
    }

    public int insert(final Book book) {
        final Number newId = insertBookTemplate.executeAndReturnKey(
                new MapSqlParameterSource()
                        .addValue("author", book.getAuthor())
                        .addValue("title", book.getTitle())
        );

        book.setId(newId.intValue());
        return book.getId();
    }

    public void update(final Book book) {
        jdbcTemplate.update(
                "UPDATE books SET author = ?, title = ? WHERE id = ?",
                book.getAuthor(),
                book.getTitle(),
                book.getId()
        );
    }

    public void delete(final int bookId) {
        jdbcTemplate.update(
                "DELETE FROM books WHERE id = ?",
                bookId
        );
    }
}
