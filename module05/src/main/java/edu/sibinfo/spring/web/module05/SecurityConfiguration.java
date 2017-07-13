package edu.sibinfo.spring.web.module05;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.intercept.RunAsImplAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.client.RestOperations;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
@Order(4)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsService;
  private final DataSource dataSource;

  @Autowired
  public SecurityConfiguration(UserDetailsService userDetailsService, @SuppressWarnings("SpringJavaAutowiringInspection") DataSource dataSource) {
    this.userDetailsService = userDetailsService;
    this.dataSource = dataSource;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(runAsAuthenticationProvider());
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    auth.authenticationProvider(customAuthProvider());
    auth.inMemoryAuthentication().withUser("inmem").password("pass").roles("USER");
  }

  private AuthenticationProvider runAsAuthenticationProvider() {
    RunAsImplAuthenticationProvider authProvider = new RunAsImplAuthenticationProvider();
    authProvider.setKey("MyRunAsKey");
    return authProvider;
  }

  private AuthenticationProvider customAuthProvider() {
    return new CustomAuthProvider();
  }

  @SuppressWarnings("SpringJavaAutowiringInspection")
  @Bean
  public RestOperations restTemplate(RestTemplateBuilder builder) {
    return builder.build();
  }

  @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  public PasswordEncoder noopEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

  @Bean
  public PasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/css/**");
  }

  // @formatter:off
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .headers().disable()
        .authorizeRequests()
        .antMatchers("/ping").authenticated()
        .antMatchers("/client/register").access("hasAuthority('PRIVELEGE_USER')")
        .antMatchers("/client/edit").hasRole("ADMIN")
        //.antMatchers("/api/**", "/**").permitAll()
      .and()
        .formLogin()
        .loginPage("/login")
        .defaultSuccessUrl("/")
        .failureUrl("/login-err")
        .permitAll()
      .and()
        .rememberMe()
        .tokenRepository(tokenRepository())
        .rememberMeParameter("custom-remember-me")
        .rememberMeCookieName("CustomRememberMe")
        .tokenValiditySeconds(5 * 60)
      .and()
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login");
  }

  private PersistentTokenRepository tokenRepository() {
    JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
    repository.setDataSource(dataSource);
    repository.setCreateTableOnStartup(true);
    return repository;
  }

  @Configuration
  @Order(1)
  public static class AdminConfigurationAdapter extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      //@formatter:off
      http.antMatcher("/api/admin**")
        .authorizeRequests()
          .anyRequest().hasRole("ADMIN")
        .and()
          .httpBasic()
          .authenticationEntryPoint(authenticationEntryPoint())
        .and()
          .exceptionHandling()
          .accessDeniedPage("/api/403");
      //@formatter:on
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
      BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
      entryPoint.setRealmName("Admin realm");
      return entryPoint;
    }
  }

  @Configuration
  @Order(2)
  public static class UserConfigurationAdapter extends WebSecurityConfigurerAdapter {

    protected void configure(HttpSecurity http) throws Exception {
      //@formatter:off
      http
          .antMatcher("/api/user/**").authorizeRequests()
          .anyRequest().hasRole("USER")
        .and()
          .formLogin()
          .loginProcessingUrl("/api/user/log")
          .failureUrl("/api/user/log?error=loginError")
          .defaultSuccessUrl("/api/user/general")
          .permitAll()
        .and()
          .logout()
          .logoutUrl("/api/user/logout")
          .logoutSuccessUrl("/api/links")
          .deleteCookies("JSESSIONID")
        .and()
          .exceptionHandling()
          .defaultAuthenticationEntryPointFor(privateEntryPoint(),  new AntPathRequestMatcher("/api/user/priv**"))
          .defaultAuthenticationEntryPointFor(entryPoint(), new AntPathRequestMatcher("/api/user/gen**"))
          .accessDeniedPage("/api/403")
        .and()
          .csrf().disable();
      //@formatter:on
    }

    @Bean
    public AuthenticationEntryPoint entryPoint() {
      return new LoginUrlAuthenticationEntryPoint("/api/login");
    }

    @Bean
    public AuthenticationEntryPoint privateEntryPoint() {
      return new LoginUrlAuthenticationEntryPoint("/api/loginPrivate");
    }
  }
  // @formatter:on

  @Configuration
  @Order(3)
  public static class App3ConfigurationAdapter extends WebSecurityConfigurerAdapter {

    protected void configure(HttpSecurity http) throws Exception {
      http.antMatcher("/api/guest**")
          .authorizeRequests()
          .anyRequest().permitAll();
    }
  }
}
