package ru.chatrpg.main.controllers

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
internal class AuthControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc
    private val token: String = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJNYWtzTWVyZnkiLCJleHAiOjE2MzY5MjM2MDB9.vYwAmJHiX462pgUBGyLPxG4zNYO0md89Knt5J9z1slCBQnhHZXfrYuc13hvuuhHA44ib6tTfwZMeH5qkGKR4Lw"

    @Test
    fun loginPost() {
        //Test page
        this.mockMvc.perform(post("/login"))
            .andExpect(status().is4xxClientError)

        //Empty json
        this.mockMvc.perform(post("/login")
            .content("{}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(""))
            .andExpect(jsonPath("$.errorMassage").value("Login not found"))

        //wrong json
        this.mockMvc.perform(post("/login")
            .content("{ \"username\": \"MaksMerfy\", \"password\": \"\" }")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(""))
            .andExpect(jsonPath("$.errorMassage").value("Login not found"))

        //Wrong login and password
        this.mockMvc.perform(post("/login")
            .content("{ \"login\": \"\", \"password\": \"\" }")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(""))
            .andExpect(jsonPath("$.errorMassage").value("Login not found"))

        //Wrong password
        this.mockMvc.perform(post("/login")
            .content("{ \"login\": \"MaksMerfy\", \"password\": \"123456\" }")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(""))
            .andExpect(jsonPath("$.errorMassage").value("Wrong login or password"))

        //login and password normal
        this.mockMvc.perform(post("/login")
            .content("{ \"login\": \"MaksMerfy\", \"password\": \"789957\" }")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(token))
            .andExpect(jsonPath("$.errorMassage").value(""))

    }

    @Test
    fun loginGet() {
        doBaseCheck("login")
    }

    @Test
    fun registrationPost() {
    }

    @Test
    fun registrationGet() {
        doBaseCheck("registration")
    }

    fun doBaseCheck(urlTemp: String){
        this.mockMvc.perform(get("/$urlTemp"))
            .andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("$.nickName").value(""))
        this.mockMvc.perform(get("/$urlTemp")
            .header("authorization", "Bearer $token"))
            .andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("$.nickName").value("MaksMerfy"))

    }
}