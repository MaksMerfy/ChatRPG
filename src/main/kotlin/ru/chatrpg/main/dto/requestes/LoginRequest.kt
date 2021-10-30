package ru.chatrpg.main.dto.requestes

data class LoginRequest(
    val login: String = "",
    val password: String = ""
)