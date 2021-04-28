package ru.otus.spring.web.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
            username = "notuser",
            authorities = {"NOTUSER"}
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
