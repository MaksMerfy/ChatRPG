package ru.chatrpg.main.controllers

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath


@SpringBootTest
@AutoConfigureMockMvc
internal class MainControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun main() {
        this.mockMvc.perform(get("/"))
            .andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("$.nickName").value(""))

        this.mockMvc.perform(post("/"))
            .andExpect(status().is4xxClientError)

        this.mockMvc.perform(get("/")
            .header("authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJNYWtzTWVyZnkiLCJleHAiOjE2MzY5MjM2MDB9.vYwAmJHiX462pgUBGyLPxG4zNYO0md89Knt5J9z1slCBQnhHZXfrYuc13hvuuhHA44ib6tTfwZMeH5qkGKR4Lw"))
            .andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("$.nickName").value("MaksMerfy"))
    }

}