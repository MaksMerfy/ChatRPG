package ru.chatrpg.main.services.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import ru.chatrpg.main.config.jwt.JwtProvider
import ru.chatrpg.main.dto.requestes.LoginPageRequest
import ru.chatrpg.main.dto.responses.LoginPageResponse
import ru.chatrpg.main.dto.responses.MainAuthResponse
import ru.chatrpg.main.model.User
import ru.chatrpg.main.services.AuthService
import ru.chatrpg.main.services.UserService


@Service
class AuthServiceImpl: AuthService {
    @Autowired
    private lateinit var jwtProvider: JwtProvider
    @Autowired
    private lateinit var userService: UserService

    override fun login(loginPageRequest: LoginPageRequest): LoginPageResponse {
        val loginPageResponse = LoginPageResponse("")
        var user: User? = userService.findByUserName(loginPageRequest.login)
        if (user != null) {
            user = userService.findByUserNameAndPassword(loginPageRequest.login, loginPageRequest.password)
            if (user != null) {
                loginPageResponse.token = jwtProvider.generateToken(user.username)
            } else {
                loginPageResponse.errorMassage = "Wrong login or password"
            }
        } else {
            loginPageResponse.errorMassage = "Login not found"
        }
        return loginPageResponse
    }

    override fun registration(loginPageRequest: LoginPageRequest): LoginPageResponse {
        val loginPageResponse = LoginPageResponse("")
        var user: User?
        if (loginPageRequest.login != ""
            && loginPageRequest.password != "") {
            user = User()
            user.username = loginPageRequest.login
            user.password = loginPageRequest.password
            user = userService.saveUser(user)
            if (user != null) { loginPageResponse.token = jwtProvider.generateToken(user.username) }
        }

        return loginPageResponse
    }

    override fun isAuthenticated(): Boolean {
        val authentication = SecurityContextHolder.getContext().authentication ?: return false
        return authentication.isAuthenticated
    }

    override fun getNickNameFromAuthenticate(): MainAuthResponse {
        val mainAuthResponse = MainAuthResponse()
        val authentication: Authentication? = SecurityContextHolder.getContext().authentication
        if (authentication != null && authentication.isAuthenticated) {
            mainAuthResponse.nickName = authentication.name
            mainAuthResponse.nickName = mainAuthResponse.nickName.replace("anonymousUser", "")
        }
        return mainAuthResponse
    }
}