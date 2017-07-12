package edu.sibinfo.spring.web.module05.security;

import edu.sibinfo.spring.web.module05.Application05;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = Application05.class)
public class MultipleEntryPointsIntegrationTest {

  @Autowired
  private WebApplicationContext wac;

  @SuppressWarnings("SpringJavaAutowiringInspection")
  @Autowired
  private FilterChainProxy springSecurityFilterChain;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(springSecurityFilterChain).build();
  }

  @Test
  public void whenTestAdminCredentials_thenOk() throws Exception {
    mockMvc
        .perform(get("/api/admin"))
        .andExpect(status().isUnauthorized());

    mockMvc
        .perform(get("/api/admin")
            .with(httpBasic("admin", "admin")))
        .andExpect(status().isOk());

    mockMvc
        .perform(get("/api/user/general")
            .with(user("admin")
                .password("admin")
                .roles("ADMIN")))
        .andExpect(status().isForbidden());
  }

  @Test
  public void whenTestUserCredentials_thenOk() throws Exception {
    mockMvc
        .perform(get("/api/user/general"))
        .andExpect(status().isFound());

    mockMvc
        .perform(get("/api/user/general")
            .with(user("user")
                .password("user")
                .roles("USER")))
        .andExpect(status().isOk());

    mockMvc
        .perform(get("/api/admin")
            .with(user("user")
                .password("user")
                .roles("USER")))
        .andExpect(status().isForbidden());
  }

  @Test
  public void givenAnyUser_whenGetGuestPage_thenOk() throws Exception {
    mockMvc
        .perform(get("/api/guest"))
        .andExpect(status().isOk());

    mockMvc
        .perform(get("/api/guest")
            .with(user("user")
                .password("user")
                .roles("USER")))
        .andExpect(status().isOk());

    mockMvc
        .perform(get("/api/guest")
            .with(httpBasic("admin", "admin")))
        .andExpect(status().isOk());
  }
}