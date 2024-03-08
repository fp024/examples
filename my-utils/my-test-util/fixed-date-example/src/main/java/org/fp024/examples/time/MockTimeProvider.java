package org.fp024.examples.time;

import java.time.LocalTime;
import lombok.Setter;

@Setter
public class MockTimeProvider implements TimeProvider {
  private LocalTime time;

  public MockTimeProvider(LocalTime time) {
    this.time = time;
  }

  @Override
  public LocalTime now() {
    return time;
  }
}
