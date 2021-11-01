package ru.chatrpg.main.dto.responses

data class LoginResponse(
    var token: String = "",
    var errorMessage: MutableList<String> = mutableListOf<String>()
)