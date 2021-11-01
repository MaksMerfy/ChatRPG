package ru.chatrpg.main.controllers

import ru.chatrpg.main.dto.requestes.LoginRequest
import ru.chatrpg.main.dto.requestes.RegistrationRequest
import ru.chatrpg.main.dto.responses.LoginResponse
import ru.chatrpg.main.dto.responses.MainAuthResponse

interface AuthControllerApi {
    fun loginPost(loginRequest: LoginRequest): LoginResponse
    fun loginGet(): MainAuthResponse
    fun registrationPost(registrationRequest: RegistrationRequest): LoginResponse
    fun registrationGet(): MainAuthResponse
}