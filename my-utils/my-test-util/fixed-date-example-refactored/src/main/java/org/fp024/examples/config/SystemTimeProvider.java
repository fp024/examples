package org.fp024.examples.config;

import java.time.LocalTime;
import org.springframework.stereotype.Component;

/** 시스템의 현재 시간을 제공하는 TimeProvider 구현체 */
@Component
public class SystemTimeProvider implements TimeProvider {

  @Override
  public LocalTime getCurrentTime() {
    return LocalTime.now();
  }
}
