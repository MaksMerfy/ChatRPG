package ru.chatrpg.main.dto.responses

data class LoginPageResponse(
    var token: String = "",
    var errorMassage: String = ""
)