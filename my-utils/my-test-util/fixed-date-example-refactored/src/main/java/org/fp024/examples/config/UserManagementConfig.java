package org.fp024.examples.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserManagementConfig {
  @Bean
  ReactiveUserDetailsService userDetailsService() {

    var u =
        User.withUsername("john") //
            .password("{noop}12345")
            .roles("ADMIN")
            .build();

    return new MapReactiveUserDetailsService(u);
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}
