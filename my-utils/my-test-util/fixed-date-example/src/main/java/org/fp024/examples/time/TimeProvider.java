package org.fp024.examples.time;

import java.time.LocalTime;

public interface TimeProvider {

  LocalTime now();
}
