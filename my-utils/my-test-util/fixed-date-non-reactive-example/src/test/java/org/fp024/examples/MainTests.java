package org.fp024.examples;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockUser;

import java.time.LocalTime;
import lombok.extern.slf4j.Slf4j;
import org.fp024.test.util.fixeddate.extension.FixedDateExtension;
import org.fp024.test.util.fixeddate.extension.annotation.FixedLocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/*
 💡 mockStatic 기능으로 만든 정적 모의 객체가,
    다른 스레드에서는 동작하지 않기 때문에 테스트는 실패한다. 🥲

    참고: https://github.com/mockito/mockito/issues/2142
*/
@Slf4j
@SpringBootTest
@AutoConfigureWebTestClient
@ExtendWith(FixedDateExtension.class)
class MainTests {

  @Autowired private WebTestClient client;

  @FixedLocalTime(hour = 12, minute = 0)
  @Test
  @DisplayName(
      "인증된 사용자 없이 /hello 엔드포인트를 호출 했을 때, " //
          + "애플리케이션은 HTTP 401 인증되지 않음 응답을 반환해야한다."
          + "(요청 시간과는 관계없음)")
  void testCallHelloWithoutUser() {

    client
        .get() //
        .uri("/hello")
        .exchange()
        .expectStatus()
        .isUnauthorized();
  }

  @FixedLocalTime(hour = 12, minute = 0)
  @Test
  @DisplayName(
      " ADMIN 역할을 가지고 있는 Mock 사용자이면 /hello 엔드포인트를 호출 했을 때, " //
          + "애플리케이션은 HTTP 200 OK를 반환해야한다.")
  @WithMockUser(roles = "ADMIN")
  void testCallHelloWithMockUser() {
    client
        .get() //
        .uri("/hello")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(String.class)
        .consumeWith(
            t ->
                assertThat(t.getResponseBody()) //
                    .isEqualTo("Hello user"));
  }

  @FixedLocalTime(hour = 12, minute = 0)
  @Test
  @DisplayName(
      "ADMIN 역할을 가지지 않은 Mock 사용자로 /hello 엔드포인트를 호출 했을 때, " //
          + "애플리케이션은 HTTP 403 FORBIDDEN을 반환해야한다.")
  void testCallHelloWithValidUserWithMockUser() {
    client
        .mutateWith(
            mockUser() //
                .roles("USER")) //
        .get()
        .uri("/hello")
        .exchange()
        .expectStatus()
        .isForbidden();
  }

  @FixedLocalTime(hour = 12, minute = 0)
  @Test
  @DisplayName(
      "ADMIN 역할을 가진 사용자라도 /ciao 엔드포인트를 호출 했을 때, " //
          + "애플리케이션은 HTTP 403 FORBIDDEN을 반환해야한다.")
  @WithMockUser(roles = "ADMIN")
  void testCallCiaoWithValidUserWithMockUser() {
    client
        .get() //
        .uri("/ciao")
        .exchange()
        .expectStatus()
        .isForbidden();
  }

  @FixedLocalTime(hour = 12, minute = 1)
  @Test
  @DisplayName(
      "ADMIN 역할을 가지고 있는 유저가 /hello 엔드포인트를 호출 하더라도, " //
          + " 현재시간이 12:00 이후라면 (12:00 < 현재시간) "
          + "애플리케이션은 HTTP 403 FORBIDDEN을 반환해야한다.")
  @WithMockUser(
      username = "john",
      roles = {"ADMIN"})
  void testCallHelloWithMockUser_Afternoon() {
    LOGGER.info("### {} ###", LocalTime.now());
    client
        .get() //
        .uri("/hello")
        .exchange()
        .expectStatus()
        .isForbidden();
  }
}
