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
    private val token: String = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtYWtzbWVyZnkiLCJleHAiOjE2Mzc2MTQ4MDB9.SBH3Fe5Plp15GswdDbDQcS5bGyFlatzQKYoEH180hwNSN3ZivHRymj8PfBj27uzzfSqjqvwMe4goVyaxEM2jGA"

    @Test
    fun loginPostCheckEndPoint() {
        //Test page
        this.mockMvc.perform(post("/login"))
            .andExpect(status().is4xxClientError)
    }

    @Test
    fun loginPostEmptyJson() {
        //Empty json
        this.mockMvc.perform(post("/login")
            .content("{}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(""))
            .andExpect(jsonPath("$.errorMessage").value("Login not found"))
    }

    @Test
    fun loginPostWrongJson(){
        //wrong json
        this.mockMvc.perform(post("/login")
            .content("{ \"username\": \"MaksMerfy\", \"password\": \"\" }")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(""))
            .andExpect(jsonPath("$.errorMessage").value("Login not found"))
    }

    @Test
    fun loginPostWorngLoginAndPassword(){
        //Wrong login and password
        this.mockMvc.perform(post("/login")
            .content("{ \"login\": \"\", \"password\": \"\" }")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(""))
            .andExpect(jsonPath("$.errorMessage").value("Login not found"))
    }

    @Test
    fun loginPostWrongPassword(){
        //Wrong password
        this.mockMvc.perform(post("/login")
            .content("{ \"login\": \"MaksMerfy\", \"password\": \"123456\" }")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(""))
            .andExpect(jsonPath("$.errorMessage").value("Wrong login or password"))
    }

    @Test
    fun loginPostCorrectUsernameAndPassword(){
        //login and password normal
        this.mockMvc.perform(post("/login")
            .content("{ \"login\": \"MaksMerfy\", \"password\": \"789957\" }")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(token))
            .andExpect(jsonPath("$.errorMessage").value(mutableListOf<String>()))
    }

    @Test
    fun loginPostCorrectEmailAndPassword(){
        //login and password normal
        this.mockMvc.perform(post("/login")
            .content("{ \"login\": \"maksmerfy@chatrpg.ru\", \"password\": \"789957\" }")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(token))
            .andExpect(jsonPath("$.errorMessage").value(mutableListOf<String>()))
    }

    @Test
    fun loginGet() {
        doBaseCheck("login")
        doBaseCheckAuth("login")
    }

    @Test
    fun registrationPostCheckEndPoint() {
        //Test page
        this.mockMvc.perform(post("/registration"))
            .andExpect(status().is4xxClientError)
    }

    @Test
    fun registrationPostEmptyJson() {
        val errorList: MutableList<String> = mutableListOf()
        errorList.add("Wrong username")
        errorList.add("Length username must be more 4 symbols")
        errorList.add("Wrong email")
        errorList.add("Length password must be more 6 symbols")

        //Empty json
        this.mockMvc.perform(post("/registration")
            .content("{}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(""))
            .andExpect(jsonPath("$.errorMessage").value(errorList))
    }

    @Test
    fun registrationPostWrongJson() {
        val errorList: MutableList<String> = mutableListOf()
        errorList.add("Wrong username")
        errorList.add("Length username must be more 4 symbols")
        errorList.add("Wrong email")
        errorList.add("Length password must be more 6 symbols")

        //Wrong json
        this.mockMvc.perform(post("/registration")
            .content("{ \"usernames\": \"testUser\"}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(""))
            .andExpect(jsonPath("$.errorMessage").value(errorList))
    }

    @Test
    fun registrationPostWrongUsername() {
        val errorList: MutableList<String> = mutableListOf()
        errorList.add("Length username must be more 4 symbols")
        errorList.add("Wrong email")
        errorList.add("Length password must be more 6 symbols")

        //Wrong username
        this.mockMvc.perform(post("/registration")
            .content("{ \"username\": \"test\"}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(""))
            .andExpect(jsonPath("$.errorMessage").value(errorList))
    }

    @Test
    fun registrationPostCorrectUsername() {
        val errorList: MutableList<String> = mutableListOf()
        errorList.add("Wrong email")
        errorList.add("Length password must be more 6 symbols")

        //Correct username
        this.mockMvc.perform(post("/registration")
            .content("{ \"username\": \"testMyUser\"}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(""))
            .andExpect(jsonPath("$.errorMessage").value(errorList))
    }

    @Test
    fun registrationPostCorrectUsernameInBase() {
        val errorList: MutableList<String> = mutableListOf()
        errorList.add("User with this username is exists")
        errorList.add("Wrong email")
        errorList.add("Length password must be more 6 symbols")

        //Correct username in base
        this.mockMvc.perform(post("/registration")
            .content("{ \"username\": \"testUser\"}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(""))
            .andExpect(jsonPath("$.errorMessage").value(errorList))
    }

    @Test
    fun registrationPostWrongEmail() {
        val errorList: MutableList<String> = mutableListOf()
        errorList.add("Wrong email")
        errorList.add("Length password must be more 6 symbols")

        //Wrong email
        this.mockMvc.perform(post("/registration")
            .content("{ \"username\": \"testMyUser\", \"email\": \"testMyUser\"}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(""))
            .andExpect(jsonPath("$.errorMessage").value(errorList))
    }

    @Test
    fun registrationPostCorrectUserAndEmail() {
        val errorList: MutableList<String> = mutableListOf()
        errorList.add("Length password must be more 6 symbols")

        //Correct user and email
        this.mockMvc.perform(post("/registration")
            .content("{ \"username\": \"testMyUser\", \"email\": \"testMyUser@testMyUser.ru\"}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(""))
            .andExpect(jsonPath("$.errorMessage").value(errorList))
    }

    @Test
    fun registrationPostCorrectUserAndEmailAndWrongPassword() {
        val errorList: MutableList<String> = mutableListOf()
        errorList.add("Length password must be more 6 symbols")
        errorList.add("Password must be equals confirm password")

        //Correct user and email and wrong password
        this.mockMvc.perform(post("/registration")
            .content("{ \"username\": \"testMyUser\", \"email\": \"testMyUser@testMyUser.ru\", \"password\": \"123\"}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(""))
            .andExpect(jsonPath("$.errorMessage").value(errorList))
    }

    @Test
    fun registrationPostCoorectUserAndEmailAndPasswordAndWrongPasswordConfirm() {
        val errorList: MutableList<String> = mutableListOf()
        errorList.add("Password must be equals confirm password")

        //Correct user and email and password and wrong passwordConfirm
        this.mockMvc.perform(post("/registration")
            .content("{ \"username\": \"testMyUser\", \"email\": \"testMyUser@testMyUser.ru\", \"password\": \"123456\"}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(""))
            .andExpect(jsonPath("$.errorMessage").value(errorList))
    }

    @Test
    fun registrationPostCorrectDataButExistsInBase() {
        val errorList: MutableList<String> = mutableListOf()
        errorList.add("User with this username is exists")
        errorList.add("User with this email is exists")

        //CorrectDataButExistsInBase
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
        doBaseCheckAuth("registration")
    }

    fun doBaseCheck(urlTemp: String){
        this.mockMvc.perform(get("/$urlTemp"))
            .andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("$.nickname").value(""))
    }

    fun doBaseCheckAuth(urlTemp: String) {
        this.mockMvc.perform(get("/$urlTemp")
        .header("authorization", "Bearer $token"))
        .andExpect(status().is2xxSuccessful)
        .andExpect(jsonPath("$.nickname").value("MaksMerfy"))}
}