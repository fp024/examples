package org.fp024.example.test_helper;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

@Component
public class DBTestUtils {
  @Autowired private ResourceDatabasePopulator dbPopulator;
  @Autowired private DataSource dataSource;

  /** 데이터 베이스 초기화 */
  public void resetDB() {
    dbPopulator.execute(dataSource);
  }
}
