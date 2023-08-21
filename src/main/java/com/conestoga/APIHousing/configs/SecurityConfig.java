package com.conestoga.APIHousing.configs;

import com.conestoga.APIHousing.service.UserDetailsServiceImpl;
import com.conestoga.APIHousing.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsServiceImpl userDetailsService;
  private final JwtUtil jwtUtil;

  @Autowired
  public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtUtil jwtUtil) {
    this.userDetailsService = userDetailsService;
    this.jwtUtil = jwtUtil;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        // enable cors and disable csrf
        .cors()
        .and()
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers("/**")
        .permitAll()
        .anyRequest()
        .authenticated();
    // .and()
    // .addFilterBefore(jwtAuthorizationFilterBean(), UsernamePasswordAuthenticationFilter.class)
    // .exceptionHandling().authenticationEntryPoint((request, response, authException) ->
    // response.sendError(HttpServletResponse.SC_UNAUTHORIZED));

  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  // Define JwtAuthorizationFilter as a bean with dependencies
  // @Bean
  // public JwtAuthorizationFilter jwtAuthorizationFilterBean() {
  //     return new JwtAuthorizationFilter(jwtUtil);
  // }
}
