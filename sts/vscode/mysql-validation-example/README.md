# VSCode에서 MySQL 문법 검사가 추가되서 테스트 해보려고 했는데...

>  그냥 해보는 과정에서 testcontainer도 추가되고 Spring Data JDBC도 추가되고 뭔가 혼종이 되었음 😅



실행을 해보니까? 정말로 컨테이너가 생겼다가 자동으로 제거되는 모습이 그냥 켜둔 Docker Desktop 상에서 보였다. 👍

원래는 이걸 테스트 환경에서만 써야하지만, 이 프로젝트는 예제이고 설정을 편하게 하기위해서 

```groovy
implementation platform(libs.testcontainer.bom)
implementation('org.testcontainers:mysql')
```

위의 내용을 implementation로 두었다..



Spring Data JDBC 같은 경우는...

예전에 한번 해봤을 때, 신기해서 다시한번 지금의 레거시 프로젝트에 붙여봤는데, 잘된다.



원래는 예전에 다음과 같이 사용했던... LIKE문이 오류가 아니였던 것 같았는데...

```java
  @Query(
      """
      SELECT id, name, author, register_date
        FROM book
       WHERE name LIKE %:name%
      """)
  List<Book> findBookByName(@Param("name") String name);
```

STS 신버전 나오고 MySQL SQL Validation 기능 추가되면서 문법 오류라고 뜨길레...

진짜인가! 해서 해본건데..

MySQL에서 네이티브 SQL은 

```java
  @Query(
      """
      SELECT id, name, author, register_date
        FROM book
       WHERE name LIKE CONCAT('%', :name, '%')
      """)
  List<Book> findBookByName(@Param("name") String name);
```

`CONCAT()`을 써주는 것이 맞나보다... 😅

먼저께 오류나는 이유가 

```sql
      SELECT id, name, author, register_date
        FROM book
       WHERE name LIKE %'banana'%
```

name을 둘러싼자리에 쉼표가 더 생김





## 참조

* Getting started with Testcontainers for Java
  * https://testcontainers.com/guides/getting-started-with-testcontainers-for-java/

* Spring Data JDBC
  * https://docs.spring.io/spring-data/relational/reference/jdbc/getting-started.html#jdbc.java-config