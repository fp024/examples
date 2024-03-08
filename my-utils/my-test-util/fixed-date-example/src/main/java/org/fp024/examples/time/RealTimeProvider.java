package org.fp024.examples.time;

import java.time.LocalTime;

public class RealTimeProvider implements TimeProvider {

  @Override
  public LocalTime now() {
    return LocalTime.now();
  }
}
