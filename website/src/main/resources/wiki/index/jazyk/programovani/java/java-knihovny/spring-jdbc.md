## Spring JDBC

Spring JDBC je nadstavba nad JDBC, což je standardní knihovna v JDK sloužící pro přístup k relační databázi. Tato knihovna není příliš uživatelsky přívětivá a proto si většina programátorů dříve či později vytvoří nějakou abstrakci. To samé udělali i autoři frameworku Spring a připravili tak vrstvu, kterou lze s úspěchem použít například v menších projektech.

### Spojení s databází

Nejdůležitější prerekvizitou pro práci s databází je definice způsobu, jakým lze získat spojení. Spring JDBC využívá standardního rozhraní *DataSource*, což je zobecněná tovární třída instancí typu *Connection*. Nejjednodušší (ale zároveň také nejméně výkonnou) implementací je *SimpleDriverDataSource* nebo *DriverManagerDataSource*. 

Pokud chcete využít pokročilé koncepty, například pooling, je možné implementace poskytované různými dalšími knihovnami, například [HikariCP](https://github.com/brettwooldridge/HikariCP), [DBCP](https://commons.apache.org/proper/commons-dbcp/) nebo [C3PO](https://github.com/swaldman/c3p0).

```java
DataSource dataSource = new SimpleDriverDataSource(
    new org.h2.Driver(), 
    "jdbc:h2:mem:ExampleDatabase", 
    "user", 
    "password"
);
```

```java
DriverManagerDataSource dataSource = new DriverManagerDataSource();
dataSource.setUsername("user");
dataSource.setPassword("password");
dataSource.setUrl("jdbc:h2:mem:ExampleDatabase");
dataSource.setDriverClassName(org.h2.Driver.class.getName());
```

### SELECT: čtení jedné hodnoty

```java
public int getBookCount() {
    return jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM book",
            Integer.class
    );
}
```

### SELECT: čtení jednoho řádku

Pro převod jednoho řádku databázové tabulky na instanci doménového objektu se používají instance rozhraní *RowMapper*. Každý tento převodník implementuje metodu *mapRow*, která jako argument získá *ResultSet* a číslo řádku a má za úkol vytvořit novou instanci doménového objektu:

```java
public class BookRowMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(final ResultSet resultSet, final int i) throws SQLException {
        final Book book = new Book();
        book.setId(resultSet.getInt("id"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        return book;
    }
}
```

Jakmile je tento převodník připraven, lze jej využít pro načítání objektu:

```java
public Book getBook(final int bookId) {
    return new JdbcTemplate(dataSource).queryForObject(
            "SELECT * FROM books WHERE id = ?",
            new Object[]{bookId},
            new BookRowMapper()
    );
}
```

### SELECT: čtení většího počtu řádků

```java
List<Actor> actors = this.jdbcTemplate.query(
        "select first_name, last_name from t_actor",
        new RowMapper<Actor>() {
            public Actor mapRow(ResultSet rs, int rowNum) throws SQLException {
                Actor actor = new Actor();
                actor.setFirstName(rs.getString("first_name"));
                actor.setLastName(rs.getString("last_name"));
                return actor;
            }
        });
```

### INSERT: Vkládání nových řádků

```java
public void insert2(Person person) {
        new JdbcTemplate(dataSource)
                .update(
                        "INSERT INTO person (first_name, last_name) VALUES (?, ?)",
                        person.getFirstName(),
                        person.getLastName()
                );
    }
```

```java
public Number insert(Person person) {
    return new SimpleJdbcInsert(dataSource)
      .withTableName("person")
      .usingColumns("first_name", "last_name")
      .usingGeneratedKeyColumns("id")
      .executeAndReturnKey(
              new MapSqlParameterSource()
                      .addValue("first_name", person.getFirstName())
                      .addValue("last_name", person.getLastName())
      );
}
```

### UPDATE: Změna existujícího řádku

```java
this.jdbcTemplate.update(
        "update t_actor set last_name = ? where id = ?",
        "Banjo", 5276L);
```

### DELETE: Smazání existujícího řádků

```java
this.jdbcTemplate.update(
        "delete from t_actor where id = ?",
        5276L);
```

### CALL: volání procedur

```java
this.jdbcTemplate.update(
        "call SUPPORT.REFRESH_ACTORS_SUMMARY(?)",
        Long.valueOf(unionId));
```

### Transakce

```java
new TransactionTemplate(new DataSourceTransactionManager(dataSource)).execute(new TransactionCallbackWithoutResult() {
  @Override
  protected void doInTransactionWithoutResult(final TransactionStatus transactionStatus) {
    // do something
  }
});
```

### Reference

- http://docs.spring.io/spring/docs/current/spring-framework-reference/html/jdbc.html
