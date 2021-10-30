package ru.chatrpg.main.controllers

import ru.chatrpg.main.dto.requestes.LoginRequest
import ru.chatrpg.main.dto.responses.LoginResponse

interface AuthControllerApi {
    fun login(loginRequest: LoginRequest): LoginResponse
    fun registration(loginRequest: LoginRequest): LoginResponse
}