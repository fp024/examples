package org.fp024.examples;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockUser;

import java.time.LocalTime;
import org.fp024.examples.time.MockTimeProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class MainTests {

  @Autowired private WebTestClient client;

  @Autowired private ApplicationContext context;

  private void setFixedTime(LocalTime now) {
    MockTimeProvider timeProvider = context.getBean(MockTimeProvider.class);
    timeProvider.setTime(now);
  }

  @AfterEach
  void afterEach() {
    setFixedTime(LocalTime.of(12, 0));
  }

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

  @Test
  @DisplayName(
      "ADMIN 역할을 가지고 있는 유저가 /hello 엔드포인트를 호출 하더라도, " //
          + " 현재시간이 12:00 이후라면 (12:00 < 현재시간) "
          + "애플리케이션은 HTTP 403 FORBIDDEN을 반환해야한다.")
  @WithMockUser(
      username = "john",
      roles = {"ADMIN"})
  void testCallHelloWithMockUser_Afternoon() {
    setFixedTime(LocalTime.of(12, 1));
    client
        .get() //
        .uri("/hello")
        .exchange()
        .expectStatus()
        .isForbidden();
  }
}
