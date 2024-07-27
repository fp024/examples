package org.fp024.example;

import java.util.List;
import org.fp024.example.config.BookConfiguration;
import org.fp024.example.domain.Book;
import org.fp024.example.repository.BookRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

  public static void main(String[] args) {

    try (AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(BookConfiguration.class)) {

      BookRepository bookRepository = context.getBean(BookRepository.class);

      List<Book> result = bookRepository.findBookByName("banana");

      System.out.println(result);
    }
  }
}
