package ru.chatrpg.main.controllers.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.chatrpg.main.controllers.AuthControllerApi
import ru.chatrpg.main.dto.requestes.LoginRequest
import ru.chatrpg.main.dto.responses.LoginResponse
import ru.chatrpg.main.services.AuthService

@RestController
class AuthController: AuthControllerApi {
    @Autowired
    private lateinit var authService: AuthService

    @PostMapping("/login")
    override fun login(@RequestBody loginRequest: LoginRequest): LoginResponse {
        return authService.login(loginRequest)
    }

    @PostMapping("/registration")
    override fun registration(@RequestBody loginRequest: LoginRequest): LoginResponse {
        return authService.registration(loginRequest)
    }
}