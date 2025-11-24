// src/test/java/com/uis/collabchat/LoginControllerTest.java
package com.uis.collabchat;

import com.uis.collabchat.dtos.RegistrationRequestDTO;
import com.uis.collabchat.dtos.LoginRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.Import;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@Import(TestSecurityConfig.class)
@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registrationAndLoginFlow() throws Exception {
        String username = "testuser";
        String password = "testpass";

        // Registration
        RegistrationRequestDTO regDto = new RegistrationRequestDTO(username, password);
        mockMvc.perform(post("/registrationBCrypt")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(regDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully"));

        // Login
        LoginRequestDTO loginDto = new LoginRequestDTO();
        loginDto.setUsername(username);
        loginDto.setPassword(password);

        mockMvc.perform(post("/login2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }
}