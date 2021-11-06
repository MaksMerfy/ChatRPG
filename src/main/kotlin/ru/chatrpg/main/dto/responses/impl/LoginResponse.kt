package ru.chatrpg.main.dto.responses.impl

data class LoginResponse(
    var token: String = "",
    var errorMessage: MutableList<String> = mutableListOf<String>()
)