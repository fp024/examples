package org.fp024.examples.config;

import java.time.LocalTime;
import org.fp024.examples.time.MockTimeProvider;
import org.fp024.examples.time.RealTimeProvider;
import org.fp024.examples.time.TimeProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class TimeProviderConfig {
  @Bean
  @Profile("!test")
  public TimeProvider realTimeProvider() {
    return new RealTimeProvider();
  }

  @Bean
  @Profile("test")
  public TimeProvider mockTimeProvider() {
    return new MockTimeProvider(LocalTime.of(12, 0));
  }
}
