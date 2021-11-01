package ru.chatrpg.main.services

import ru.chatrpg.main.dto.requestes.LoginRequest
import ru.chatrpg.main.dto.requestes.RegistrationRequest
import ru.chatrpg.main.dto.responses.LoginResponse
import ru.chatrpg.main.dto.responses.MainAuthResponse

interface AuthService {
    fun login(loginRequest: LoginRequest): LoginResponse
    fun registration(registrationRequest: RegistrationRequest): LoginResponse
    fun validateRegistrationRequest(registrationRequest: RegistrationRequest, loginResponse: LoginResponse)
    fun isAuthenticated(): Boolean
    fun getNickNameFromAuthenticate() : MainAuthResponse
}