package org.fp024.examples.config;

import static org.springframework.security.config.Customizer.*;

import java.time.LocalTime;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class WebAuthorizationConfig {

  private final TimeProvider timeProvider;

  public WebAuthorizationConfig(TimeProvider timeProvider) {
    this.timeProvider = timeProvider;
  }

  @Bean
  SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

    http.authorizeExchange(
            authorizeExchangeCustomizer ->
                authorizeExchangeCustomizer //
                    .anyExchange()
                    .access(this::getAuthorizationDecisionMono))
        .httpBasic(withDefaults());

    return http.build();
  }

  private Mono<AuthorizationDecision> getAuthorizationDecisionMono(
      Mono<Authentication> a, AuthorizationContext c) {

    String path = getRequestPath(c);

    var now = timeProvider.getCurrentTime();
    LOGGER.info("### TimeProvider.getCurrentTime(): {}", now);
    // 💡 현재 시간이 12:00 이후면 제한된 시간
    boolean restrictedTime = //
        now.isAfter(LocalTime.NOON);

    if (path.equals("/hello")) {
      return a.map(isAdmin()) //
          .map(auth -> auth && !restrictedTime)
          .map(AuthorizationDecision::new);
    }

    // 💡 "/hello" 외의 엔드포인트는 어떤 경우이든. 접근 금지
    return Mono.just(new AuthorizationDecision(false));
  }

  private String getRequestPath(AuthorizationContext c) {
    return c.getExchange() //
        .getRequest()
        .getPath()
        .toString();
  }

  private Function<Authentication, Boolean> isAdmin() {
    return p ->
        p.getAuthorities().stream() //
            .anyMatch(e -> e.getAuthority().equals("ROLE_ADMIN"));
  }
}
