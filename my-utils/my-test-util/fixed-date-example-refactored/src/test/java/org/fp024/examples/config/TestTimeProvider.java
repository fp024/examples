package org.fp024.examples.config;

import java.time.LocalTime;

public class TestTimeProvider implements TimeProvider {

  private LocalTime fixedTime;

  public TestTimeProvider(LocalTime fixedTime) {
    this.fixedTime = fixedTime;
  }

  @Override
  public LocalTime getCurrentTime() {
    return fixedTime;
  }

  public void setFixedTime(LocalTime fixedTime) {
    this.fixedTime = fixedTime;
  }
}
