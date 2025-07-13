package org.fp024.examples.config;

import java.time.LocalTime;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/** 테스트용 환경 설정 */
@TestConfiguration
public class TestConfig {
  @Bean
  @Primary
  TimeProvider timeProvider() {
    return new TestTimeProvider(LocalTime.NOON);
  }
}
