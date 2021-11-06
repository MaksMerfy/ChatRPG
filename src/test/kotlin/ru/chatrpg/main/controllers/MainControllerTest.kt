package ru.chatrpg.main.controllers

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
internal class MainControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun mainGetCheckEndPoint() {
        this.mockMvc.perform(get("/"))
            .andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("$.nickname").value(""))
    }

    @Test
    fun mainGetCorrectAuth() {
        this.mockMvc.perform(get("/")
            .header("authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtYWtzbWVyZnkiLCJleHAiOjE2Mzc0NDIwMDB9.xg6YmbXSqBg9LovJXMtnZ9bO4pPFfBq4wEE221DwXZo3Kx19_ma_S2RWAOJKf1qzBtnUGBdcQAI3m3wbxBTsTA"))
            .andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("$.nickname").value("MaksMerfy"))
    }

    @Test
    fun mainPostCheckEndPoint() {
        this.mockMvc.perform(post("/"))
            .andExpect(status().is4xxClientError)
    }

}