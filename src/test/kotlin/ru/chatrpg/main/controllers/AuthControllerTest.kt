package ru.chatrpg.main.controllers

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = ["/sql-before-test.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
internal class AuthControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc
    private val token: String = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJNYWtzTWVyZnkiLCJleHAiOjE2MzcwMTAwMDB9.-BoSJgWdKLg5_C1la-LaqyLP7UX0Pq4EF5BjKItvk3PyDpIdTDLrbE6MB4VNH_2pqq4usBeS1GwIQdRpJyFBSg"

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
            .andExpect(jsonPath("$.errorMessage").value("Login not found"))

        //wrong json
        this.mockMvc.perform(post("/login")
            .content("{ \"username\": \"MaksMerfy\", \"password\": \"\" }")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(""))
            .andExpect(jsonPath("$.errorMessage").value("Login not found"))

        //Wrong login and password
        this.mockMvc.perform(post("/login")
            .content("{ \"login\": \"\", \"password\": \"\" }")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(""))
            .andExpect(jsonPath("$.errorMessage").value("Login not found"))

        //Wrong password
        this.mockMvc.perform(post("/login")
            .content("{ \"login\": \"MaksMerfy\", \"password\": \"123456\" }")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(""))
            .andExpect(jsonPath("$.errorMessage").value("Wrong login or password"))

        //login and password normal
        this.mockMvc.perform(post("/login")
            .content("{ \"login\": \"MaksMerfy\", \"password\": \"789957\" }")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(token))
            .andExpect(jsonPath("$.errorMessage").value(mutableListOf<String>()))

    }

    @Test
    fun loginGet() {
        doBaseCheck("login")
    }

    @Test
    fun registrationPost() {
        val errorList: MutableList<String> = mutableListOf()
        errorList.add("Wrong username")
        errorList.add("Length username must be more 4 symbols")
        errorList.add("Wrong email")
        errorList.add("Length password must be more 6 symbols")

        //Test page
        this.mockMvc.perform(post("/registration"))
            .andExpect(status().is4xxClientError)

        //Empty json
        this.mockMvc.perform(post("/registration")
            .content("{}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(""))
            .andExpect(jsonPath("$.errorMessage").value(errorList))

        //wrong json
        this.mockMvc.perform(post("/registration")
            .content("{ \"usernames\": \"testUser\"}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(""))
            .andExpect(jsonPath("$.errorMessage").value(errorList))

        //wrong username
        errorList.clear()
        errorList.add("Length username must be more 4 symbols")
        errorList.add("Wrong email")
        errorList.add("Length password must be more 6 symbols")

        this.mockMvc.perform(post("/registration")
            .content("{ \"username\": \"test\"}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(""))
            .andExpect(jsonPath("$.errorMessage").value(errorList))

        //Correct username
        errorList.clear()
        errorList.add("Wrong email")
        errorList.add("Length password must be more 6 symbols")

        this.mockMvc.perform(post("/registration")
            .content("{ \"username\": \"testMyUser\"}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(""))
            .andExpect(jsonPath("$.errorMessage").value(errorList))

        //Correct username in base
        errorList.clear()
        errorList.add("User with this username is exists")
        errorList.add("Wrong email")
        errorList.add("Length password must be more 6 symbols")

        this.mockMvc.perform(post("/registration")
            .content("{ \"username\": \"testUser\"}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(""))
            .andExpect(jsonPath("$.errorMessage").value(errorList))

        //Wrong email
        errorList.clear()
        errorList.add("Wrong email")
        errorList.add("Length password must be more 6 symbols")

        this.mockMvc.perform(post("/registration")
            .content("{ \"username\": \"testMyUser\", \"email\": \"testMyUser\"}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(""))
            .andExpect(jsonPath("$.errorMessage").value(errorList))

        //Correct user and email
        errorList.clear()
        errorList.add("Length password must be more 6 symbols")

        this.mockMvc.perform(post("/registration")
            .content("{ \"username\": \"testMyUser\", \"email\": \"testMyUser@testMyUser.ru\"}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(""))
            .andExpect(jsonPath("$.errorMessage").value(errorList))

        //Correct user and email and wrong password
        errorList.clear()
        errorList.add("Length password must be more 6 symbols")
        errorList.add("Password must be equals confirm password")

        this.mockMvc.perform(post("/registration")
            .content("{ \"username\": \"testMyUser\", \"email\": \"testMyUser@testMyUser.ru\", \"password\": \"123\"}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(""))
            .andExpect(jsonPath("$.errorMessage").value(errorList))

        //Correct user and email and password and wrong passwordConfirm
        errorList.clear()
        errorList.add("Password must be equals confirm password")

        this.mockMvc.perform(post("/registration")
            .content("{ \"username\": \"testMyUser\", \"email\": \"testMyUser@testMyUser.ru\", \"password\": \"123456\"}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(""))
            .andExpect(jsonPath("$.errorMessage").value(errorList))

        //Correct user and email and password and wrong passwordConfirm
        errorList.clear()
        errorList.add("User with this username is exists")
        errorList.add("User with this email is exists")

        this.mockMvc.perform(post("/registration")
            .content("{ \"username\": \"testUser\", \"email\": \"testUser@testuser.ru\", \"password\": \"123456\", \"passwordConfirm\": \"123456\"}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(""))
            .andExpect(jsonPath("$.errorMessage").value(errorList))
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