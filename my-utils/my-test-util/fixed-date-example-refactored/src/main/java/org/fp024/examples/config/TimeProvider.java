package org.fp024.examples.config;

import java.time.LocalTime;

/** 현재 시간을 제공하는 인터페이스 테스트에서 시간을 모킹할 수 있도록 의존성 주입 패턴을 사용합니다. */
public interface TimeProvider {

  /**
   * 현재 시간을 반환합니다.
   *
   * @return 현재 LocalTime
   */
  LocalTime getCurrentTime();
}
