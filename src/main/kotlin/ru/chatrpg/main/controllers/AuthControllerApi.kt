package ru.chatrpg.main.controllers

import ru.chatrpg.main.dto.requestes.LoginPageRequest
import ru.chatrpg.main.dto.responses.LoginPageResponse
import ru.chatrpg.main.dto.responses.MainAuthResponse

interface AuthControllerApi {
    fun loginPost(loginPageRequest: LoginPageRequest): LoginPageResponse
    fun loginGet(): MainAuthResponse
    fun registrationPost(loginPageRequest: LoginPageRequest): LoginPageResponse
    fun registrationGet(): MainAuthResponse
}