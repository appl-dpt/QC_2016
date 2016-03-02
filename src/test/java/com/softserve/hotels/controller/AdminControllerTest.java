package com.softserve.hotels.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.servlet.Filter;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.UserRequestPostProcessor;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.softserve.hotels.configuration.AppConfig;
import com.softserve.hotels.configuration.SecurityConfig;
import com.softserve.hotels.configuration.SocialContext;
import com.softserve.hotels.configuration.WebAppConfig;
import com.softserve.hotels.model.Role;
import com.softserve.hotels.service.UserService;

@Test
@WebAppConfiguration
@ContextConfiguration(classes = { AppConfig.class, SecurityConfig.class })
@TestPropertySource(locations = "classpath:testdb.properties")
public class AdminControllerTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private UserService userService;
    
    private MockMvc mockMvc;

    private final UserRequestPostProcessor adminUserStub = user("user@gmail.com")
            .authorities(new SimpleGrantedAuthority("ADMIN"));
    
    @BeforeClass
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).addFilters(springSecurityFilterChain)
                .build();
    }

    @Test
    public void testAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/allUsers")
                .with(user("user@gmail.com").authorities(new SimpleGrantedAuthority("USER"))))
                .andExpect(status().isForbidden());
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/allUsers")
                .with(user("user@gmail.com").authorities(new SimpleGrantedAuthority("RENTER"))))
                .andExpect(status().isForbidden());
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/allUsers")
                .with(user("user@gmail.com").authorities(new SimpleGrantedAuthority("MODERATOR"))))
                .andExpect(status().isForbidden());
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/allUsers")
                .with(adminUserStub))
                .andExpect(status().isOk());
    }

    @Test    
    public void testUserList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/allUsers")
                .with(adminUserStub))
                .andExpect(status().isOk())
                .andExpect(view().name("UserList"))
                .andExpect(model().attribute("currentPage", 1))
                .andExpect(model().attribute("roleList", Role.values()));
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/allUsers").param("page", "2")
                .with(adminUserStub))
                .andExpect(model().attribute("userList", hasSize(1)));
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/allUsers").param("page", "2124124")
                .with(adminUserStub))
                .andExpect(model().attribute("userList", hasSize(0)));
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/allUsers").param("size", "1")
                .with(adminUserStub))
                .andExpect(model().attribute("userList", hasSize(1)))
                .andExpect(model().attribute("lastPageIndex", 6L));
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/allUsers").param("userRole", "ADMIN")
                .with(adminUserStub))
                .andExpect(model().attribute("userList", hasSize(1)));
    }
    
    @Test
    @Transactional
    public void testChangeUserRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/adminChange")
                .with(adminUserStub).with(csrf())
                .param("email", "user@gmail.com")
                .param("role", "RENTER"))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/allUsers")
                .param("userRole", "RENTER")
                .with(adminUserStub))
                .andExpect(model().attribute("userList", hasSize(2)));
    }
    @Test
    @Transactional
    public void testDisableUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/adminChange")
                .with(adminUserStub).with(csrf())
                .param("email", "user@gmail.com")
                .param("enabled", "false"))
                .andExpect(status().isOk());
        Assert.assertEquals(userService.findUserByEmail("user@gmail.com").isEnabled(), false);
    }
}
