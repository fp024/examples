package org.fp024.example.repository;

import java.util.List;
import org.fp024.example.domain.Book;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends CrudRepository<Book, Long> {
  @Query(
      """
      SELECT id, name, author, register_date
        FROM book
       WHERE name LIKE CONCAT('%', :name, '%')
      """)
  List<Book> findBookByName(@Param("name") String name);
}
