package ru.chatrpg.main.services

import ru.chatrpg.main.dto.requestes.LoginRequest
import ru.chatrpg.main.dto.responses.LoginResponse

interface AuthService {
    fun login(loginRequest: LoginRequest): LoginResponse
    fun registration(loginRequest: LoginRequest): LoginResponse
}