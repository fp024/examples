package org.fp024.example.domain;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@ToString
@Table(name = "book")
public class Book {
  @Id private Long id;
  private String name;
  private String author;

  @Column("register_date")
  @CreatedDate
  private LocalDateTime registerDate;
}
