package ru.otus.spring.web.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenGetAllAuthorsAndNoAuthThen401() throws Exception {
        mockMvc.perform(get("/api/author")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

    }

    @WithMockUser(
            username = "user",
            authorities = {"USER"}
    )
    @Test
    void whenGetAllAuthorsAndAndWrongPermitionsThen403() throws Exception {
        mockMvc.perform(get("/api/author")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

    }

    @WithMockUser(
            username = "admin",
            authorities = {"ADMIN"}
    )
    @Test
    void whenGetAllAuthorsAndAndEnoughPermitionsThenOk() throws Exception {
        mockMvc.perform(get("/api/author")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
