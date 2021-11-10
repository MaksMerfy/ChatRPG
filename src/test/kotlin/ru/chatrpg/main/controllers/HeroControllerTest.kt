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
    private val token: String = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtYWtzbWVyZnkiLCJleHAiOjE2Mzc2MTQ4MDB9.SBH3Fe5Plp15GswdDbDQcS5bGyFlatzQKYoEH180hwNSN3ZivHRymj8PfBj27uzzfSqjqvwMe4goVyaxEM2jGA"

    @Test
    fun heroPostCheckEndPoint() {
        //Test page with Post
        this.mockMvc.perform(MockMvcRequestBuilders.post("/hero"))
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }

    @Test
    fun heroGetCheckEndPoint() {
        //Test page with Get
        val emptyList: MutableList<String> = mutableListOf<String>()
        this.mockMvc.perform(MockMvcRequestBuilders.get("/hero"))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
            .andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value(""))
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessages").value(emptyList))
    }

    @Test
    fun heroGetCheckWrongBearer() {
        //Test page with Get and wrond baerer
        val emptyList: MutableList<String> = mutableListOf<String>()
        this.mockMvc.perform(MockMvcRequestBuilders.get("/hero")
            .header("authorization", "Bearer "))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
            .andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value(""))
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessages").value(emptyList))
    }

    @Test
    fun heroGetCheckValidBearer() {
        //Test page with Get and valid baerer
        val emptyList: MutableList<String> = mutableListOf<String>()
        this.mockMvc.perform(MockMvcRequestBuilders.get("/hero")
            .header("authorization", "Bearer $token"))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
            .andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value("MaksMerfy"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessages").value(emptyList))
    }

    @Test
    fun heroUpdatePostCheckEndPoint() {
        //Test update page with Post
        val emptyList: MutableList<String> = mutableListOf<String>()
        this.mockMvc.perform(MockMvcRequestBuilders.post("/hero/update"))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
            .andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value(""))
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessages").value(emptyList))
    }

    @Test
    fun heroUpdatePostWrongToken() {
        //Test post update page with wrong token
        val emptyList: MutableList<String> = mutableListOf<String>()
        this.mockMvc.perform(MockMvcRequestBuilders.post("/hero/update")
            .header("authorization", "Bearer "))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
            .andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value(""))
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessages").value(emptyList))
    }

    @Test
    fun heroUpdatePostValidToken() {
        //Test post update page with wrong token
        val emptyList: MutableList<String> = mutableListOf<String>()
        this.mockMvc.perform(MockMvcRequestBuilders.post("/hero/update")
            .header("authorization", "Bearer $token"))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
            .andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value("MaksMerfy"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessages").value(emptyList))
    }

    @Test
    fun heroUpdatePostEmptyStat() {
        //Test post update page with wrong token
        val emptyList: MutableList<String> = mutableListOf<String>()
        this.mockMvc.perform(MockMvcRequestBuilders.post("/hero/update?Stat=")
            .header("authorization", "Bearer $token"))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
            .andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value("MaksMerfy"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessages").value(emptyList))
    }

    @Test
    fun heroUpdatePostValidUpdate() {
        //Test post update page with wrong token
        val emptyList: MutableList<String> = mutableListOf<String>()
        this.mockMvc.perform(MockMvcRequestBuilders.post("/hero/update?Stat=dexterity")
            .header("authorization", "Bearer $token"))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
            .andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value("MaksMerfy"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessages").value(emptyList))
    }
}