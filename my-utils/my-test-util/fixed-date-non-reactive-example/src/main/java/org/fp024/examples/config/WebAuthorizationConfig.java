package org.fp024.examples.config;

import static org.springframework.security.config.Customizer.*;

import java.time.LocalTime;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

@Slf4j
@Configuration
public class WebAuthorizationConfig {

  @Bean
  SecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {

    http.authorizeHttpRequests(
            authorizeHttpRequestsCustomizer ->
                authorizeHttpRequestsCustomizer //
                    .anyRequest()
                    .access(this::getAuthorizationDecision))
        .httpBasic(withDefaults());

    return http.build();
  }

  private AuthorizationDecision getAuthorizationDecision(
      Supplier<Authentication> a, RequestAuthorizationContext c) {

    String path = getRequestPath(c);

    var now = LocalTime.now();
    LOGGER.info("### LocalTime.now(): {}", now);
    // 💡 현재 시간이 12:00 이후면 제한된 시간
    boolean restrictedTime = //
        now.isAfter(LocalTime.NOON);

    if (path.equals("/hello")) {
      return new AuthorizationDecision(isAdmin(a) && !restrictedTime);
    }

    // 💡 "/hello" 외의 엔드포인트는 어떤 경우이든. 접근 금지
    return new AuthorizationDecision(false);
  }

  private String getRequestPath(RequestAuthorizationContext c) {
    return c.getRequest().getRequestURI();
  }

  private Boolean isAdmin(Supplier<Authentication> p) {
    return p.get().getAuthorities().stream() //
        .anyMatch(e -> e.getAuthority().equals("ROLE_ADMIN"));
  }
}
