package org.fp024.example.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import org.fp024.example.config.BookConfiguration;
import org.fp024.example.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = BookConfiguration.class)
class BookRepositoryTests {

  @Autowired private BookRepository bookRepository;

  @Test
  void findBookByName() {
    List<Book> result = bookRepository.findBookByName("apple");
    assertThat(result).isNotNull();
  }
}
