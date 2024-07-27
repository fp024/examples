package org.fp024.example.config;

import java.nio.charset.StandardCharsets;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jdbc.core.dialect.JdbcMySqlDialect;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.TransactionManager;
import org.testcontainers.containers.MySQLContainer;

@Configuration
@ComponentScan("org.fp024.example")
@EnableJdbcRepositories("org.fp024.example.repository")
public class BookConfiguration extends AbstractJdbcConfiguration {

  @Bean(destroyMethod = "stop")
  MySQLContainer<?> mySQLContainer() {
    MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.39-debian");
    mysql.start();
    return mysql;
  }

  @Bean
  DataSource dataSource() {

    SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
    dataSource.setDriverClass(net.sf.log4jdbc.sql.jdbcapi.DriverSpy.class);
    dataSource.setUrl(adjustJdbcUrlForLog4Jdbc(mySQLContainer().getJdbcUrl()));
    dataSource.setUsername(mySQLContainer().getUsername());
    dataSource.setPassword(mySQLContainer().getPassword());
    return dataSource;
  }

  private String adjustJdbcUrlForLog4Jdbc(String originUrl) {
    return "jdbc:log4jdbc" + originUrl.replaceFirst("jdbc", "");
  }

  @Bean
  DataSourceInitializer dataSourceInitializer() {
    DataSourceInitializer initializer = new DataSourceInitializer();
    initializer.setDataSource(dataSource());
    initializer.setDatabasePopulator(databasePopulator());
    return initializer;
  }

  @Bean
  ResourceDatabasePopulator databasePopulator() {
    ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
    databasePopulator.setSqlScriptEncoding(StandardCharsets.UTF_8.name());
    databasePopulator.setContinueOnError(false);
    databasePopulator.addScript(new ClassPathResource("schema.sql"));

    return databasePopulator;
  }

  @Bean
  NamedParameterJdbcOperations namedParameterJdbcOperations(DataSource dataSource) {
    return new NamedParameterJdbcTemplate(dataSource);
  }

  @Bean
  TransactionManager transactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }
}
