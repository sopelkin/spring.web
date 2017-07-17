package edu.sibinfo.spring.web.module05.security;

import edu.sibinfo.spring.web.module05.Application05;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
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
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).addFilter(springSecurityFilterChain).build();
    }

    @Test
    @WithAnonymousUser
    public void whenTestAdmin_thenUnauthorized() throws Exception {
        mockMvc
                .perform(get("/api/admin"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void whenTestAdminCredentials_thenOk() throws Exception {
        mockMvc
                .perform(get("/api/admin"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenLoginAsUser_thenOk() throws Exception {
        mockMvc.perform(
                formLogin("/api/user/log")
                        .user("user")
                        .password("user"))
                .andExpect(redirectedUrl("/api/user/general"));
    }

    @Test
    public void whenLoginWithBadCredentials_then403() throws Exception {
        mockMvc.perform(
                formLogin("/api/user/log")
                        .user("fail")
                        .password("bowl"))
                .andExpect(redirectedUrl("/api/user/log?error=loginError"));
    }

    @Test
    @WithMockUser
    public void whenTestMockUser_thenOk() throws Exception {
        mockMvc
                .perform(get("/api/user/general"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenTestUserCredentials_thenOk() throws Exception {
        mockMvc
                .perform(get("/api/user/general"))
                .andExpect(status().isFound());
    }

    @Test
    @WithMockUser
    public void whenUserAccessAdmin_thenForbidden() throws Exception {
        mockMvc
                .perform(get("/api/admin"))
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