package ru.otus.spring.web.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenGetAllBooksAndNoAuthThen401() throws Exception {
        mockMvc.perform(get("/api/book")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(
            username = "user",
            authorities = {"USER"}
    )
    @Test
    void whenGetAllBooksAndWrongPermtionsThen403() throws Exception {
        mockMvc.perform(get("/api/book")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ADMIN"}
    )
    @Test
    void whenGetAllBooksAndEnoughPermtionsThenOk() throws Exception {
        mockMvc.perform(get("/api/book")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenGetCommentsForBookAndNoAuthThen401() throws Exception {
        mockMvc.perform(get("/api/book/3/comments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

    }

    @WithMockUser(
            username = "user",
            authorities = {"USER"}
    )
    @Test
    void whenGetCommentsForBookAndWrongPermitionsThen403() throws Exception {
        mockMvc.perform(get("/api/book/3/comments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

    }

    @WithMockUser(
            username = "admin",
            authorities = {"ADMIN"}
    )
    @Test
    void whenGetCommentsForBookAndEnoughPermitionsThenOk() throws Exception {
        mockMvc.perform(get("/api/book/3/comments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }



}
