package com.example.houses;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.*;
import static  org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigurationTest {

    @Autowired
    MockMvc mockMvc;
    @Test
    @WithMockUser(roles = {"USER"})
    void shouldUserGetResponseToGetHouses() throws Exception {
        mockMvc.perform(get("/houses"))
                .andExpect(status().is(200));
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void shouldNotBeAbleToRemoveHouses() throws Exception {
        mockMvc.perform(delete("/houses/remove"))
                .andExpect(status().is(403));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void shouldBeAbleToRemoveHouses() throws Exception {
        mockMvc.perform(delete("/houses/remove/1"))
                .andExpect(status().is(200));
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void shouldNotBeAbleToAddHouses() throws Exception {
        mockMvc.perform(post("/houses/add"))
                .andExpect(status().is(403));
    }
}
