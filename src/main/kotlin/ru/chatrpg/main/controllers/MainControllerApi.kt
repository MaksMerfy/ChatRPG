package ru.chatrpg.main.controllers

import ru.chatrpg.main.dto.responses.MainAuthResponse
import javax.servlet.http.HttpServletRequest

interface MainControllerApi {
    fun main(request: HttpServletRequest): MainAuthResponse
}