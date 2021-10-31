package ru.chatrpg.main.services

import ru.chatrpg.main.dto.requestes.LoginPageRequest
import ru.chatrpg.main.dto.responses.LoginPageResponse
import ru.chatrpg.main.dto.responses.MainAuthResponse

interface AuthService {
    fun login(loginPageRequest: LoginPageRequest): LoginPageResponse
    fun registration(loginPageRequest: LoginPageRequest): LoginPageResponse
    fun isAuthenticated(): Boolean
    fun getNickNameFromAuthenticate() : MainAuthResponse
}