package ru.otus.spring.web.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginSecurityTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void whenRightCredsThenRedirectToMainPage() throws Exception {
        mockMvc.perform(post("/api/login")
                .content("username=admin&password=password")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("Origin","http://localhost:9000/"))

                .andExpect(redirectedUrl("/"));

    }

    @Test
    void whenBadCredsThenRedirectToError() throws Exception {
        mockMvc.perform(post("/api/login")
                .content("username=admin&password=password111")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("Origin","http://localhost:9000/"))

                .andExpect(redirectedUrl("/Login?error"));

    }
}
