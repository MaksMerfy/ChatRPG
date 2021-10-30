package ru.chatrpg.main.services.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.chatrpg.main.config.jwt.JwtProvider
import ru.chatrpg.main.dto.requestes.LoginRequest
import ru.chatrpg.main.dto.responses.LoginResponse
import ru.chatrpg.main.model.User
import ru.chatrpg.main.services.UserService
import ru.chatrpg.main.services.AuthService as AuthService

@Service
class AuthServiceImpl: AuthService {
    @Autowired
    private lateinit var jwtProvider: JwtProvider
    @Autowired
    private lateinit var userService: UserService

    override fun login(loginRequest: LoginRequest): LoginResponse {
        val loginResponse: LoginResponse = LoginResponse("")
        var user: User? = userService.findByUserName(loginRequest.login)
        if (user != null) {
            user = userService.findByUserNameAndPassword(loginRequest.login, loginRequest.password)
            if (user != null) loginResponse.token = jwtProvider.generateToken(user.username)
        }
        return loginResponse
    }

    override fun registration(loginRequest: LoginRequest): LoginResponse {
        val loginResponse: LoginResponse = LoginResponse("")
        var user: User?
        if (loginRequest.login != ""
            && loginRequest.password != "") {
            user = User()
            user.username = loginRequest.login
            user.password = loginRequest.password
            user = userService.saveUser(user)
            if (user != null) { loginResponse.token = jwtProvider.generateToken(user.username) }
        }

        return loginResponse
    }
}