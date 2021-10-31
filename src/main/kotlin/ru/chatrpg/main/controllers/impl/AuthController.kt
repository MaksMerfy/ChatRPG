package ru.chatrpg.main.controllers.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.chatrpg.main.controllers.AuthControllerApi
import ru.chatrpg.main.dto.requestes.LoginPageRequest
import ru.chatrpg.main.dto.responses.LoginPageResponse
import ru.chatrpg.main.dto.responses.MainAuthResponse
import ru.chatrpg.main.services.AuthService

@RestController
class AuthController: AuthControllerApi {
    @Autowired
    private lateinit var authService: AuthService

    @PostMapping("/login")
    override fun loginPost(@RequestBody loginPageRequest: LoginPageRequest): LoginPageResponse {
        return authService.login(loginPageRequest)
    }

    @GetMapping("/login")
    override fun loginGet(): MainAuthResponse {
        return authService.getNickNameFromAuthenticate()
    }

    @PostMapping("/registration")
    override fun registrationPost(@RequestBody loginPageRequest: LoginPageRequest): LoginPageResponse {
        return authService.registration(loginPageRequest)
    }

    @GetMapping("/registration")
    override fun registrationGet(): MainAuthResponse {
        return authService.getNickNameFromAuthenticate()
    }

}