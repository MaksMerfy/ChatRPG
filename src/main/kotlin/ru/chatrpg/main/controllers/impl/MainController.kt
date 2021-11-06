package ru.chatrpg.main.controllers.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import ru.chatrpg.main.controllers.MainControllerApi
import ru.chatrpg.main.dto.responses.impl.MainAuthResponse
import ru.chatrpg.main.services.AuthService
import javax.servlet.http.HttpServletRequest

@RestController
class MainController: MainControllerApi {
    @Autowired
    private lateinit var authService: AuthService

    @GetMapping("/")
    override fun main(request: HttpServletRequest): MainAuthResponse {
        return authService.getNickNameFromAuthenticate()
    }



}