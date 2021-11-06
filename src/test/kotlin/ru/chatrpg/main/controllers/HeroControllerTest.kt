package ru.chatrpg.main.controllers

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = ["/sql-before-test.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
internal class HeroControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc
    private val token: String = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtYWtzbWVyZnkiLCJleHAiOjE2Mzc0NDIwMDB9.xg6YmbXSqBg9LovJXMtnZ9bO4pPFfBq4wEE221DwXZo3Kx19_ma_S2RWAOJKf1qzBtnUGBdcQAI3m3wbxBTsTA"

    @Test
    fun heroPostCheckEndPoint() {
        //Test page with Post
        this.mockMvc.perform(MockMvcRequestBuilders.post("/hero"))
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }

    @Test
    fun heroGetCheckEndPoint() {
        //Test page with Get
        this.mockMvc.perform(MockMvcRequestBuilders.get("/hero"))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
            .andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value(""))
            .andExpect(MockMvcResultMatchers.jsonPath("$.hp").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath("$.mana").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath("$.damage").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath("$.armor").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath("$.criticalChance").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath("$.experience").value(0))
    }

    @Test
    fun heroGetCheckWrongBearer() {
        //Test page with Get and wrond baerer
        this.mockMvc.perform(MockMvcRequestBuilders.get("/hero")
            .header("authorization", "Bearer "))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
            .andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value(""))
            .andExpect(MockMvcResultMatchers.jsonPath("$.hp").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath("$.mana").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath("$.damage").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath("$.armor").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath("$.criticalChance").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath("$.experience").value(0))
    }

    @Test
    fun heroGetCheckValidBearer() {
        //Test page with Get and valid baerer
        this.mockMvc.perform(MockMvcRequestBuilders.get("/hero")
            .header("authorization", "Bearer $token"))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
            .andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value("MaksMerfy"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.hp").value(100))
            .andExpect(MockMvcResultMatchers.jsonPath("$.mana").value(10))
            .andExpect(MockMvcResultMatchers.jsonPath("$.damage").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$.armor").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$.criticalChance").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath("$.experience").value(0))
    }
}