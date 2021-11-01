package ru.chatrpg.main.services.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import ru.chatrpg.main.config.jwt.JwtProvider
import ru.chatrpg.main.dto.requestes.LoginRequest
import ru.chatrpg.main.dto.requestes.RegistrationRequest
import ru.chatrpg.main.dto.responses.LoginResponse
import ru.chatrpg.main.dto.responses.MainAuthResponse
import ru.chatrpg.main.model.User
import ru.chatrpg.main.services.AuthService
import ru.chatrpg.main.services.UserService
import java.util.regex.Pattern


@Service
class AuthServiceImpl: AuthService {
    @Autowired
    private lateinit var jwtProvider: JwtProvider
    @Autowired
    private lateinit var userService: UserService

    private val emailPatern = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})")

    override fun login(loginRequest: LoginRequest): LoginResponse {
        val loginResponse = LoginResponse()
        var user: User? = userService.findByUserName(loginRequest.login)
        if (user != null) {
            user = userService.findByUserNameAndPassword(loginRequest.login, loginRequest.password)
            if (user != null) {
                loginResponse.token = jwtProvider.generateToken(user.username)
            } else {
                loginResponse.errorMessage.add("Wrong login or password")
            }
        } else {
            loginResponse.errorMessage.add("Login not found")
        }
        return loginResponse
    }

    override fun registration(registrationRequest: RegistrationRequest): LoginResponse {
        val loginResponse = LoginResponse()
        validateRegistrationRequest(registrationRequest, loginResponse)
        if (loginResponse.errorMessage.isEmpty()) {
            val user = User()
            user.username = registrationRequest.username
            user.email = registrationRequest.email
            user.nickname = registrationRequest.nickname
            if (user.nickname == "") user.nickname = user.username
            user.password = registrationRequest.password
            val userSaved: User? = userService.saveUser(user)
            if (userSaved != null) {
                loginResponse.token = jwtProvider.generateToken(userSaved.username)
            } else
            {
                loginResponse.errorMessage += "Can't register user"
            }
        }

        return loginResponse
    }

    override fun validateRegistrationRequest(registrationRequest: RegistrationRequest, loginResponse: LoginResponse) {
        if (registrationRequest.username == "") loginResponse.errorMessage.add("Wrong username")
        if (registrationRequest.username.length < 5 ) loginResponse.errorMessage.add("Length username must be more 4 symbols")
        if (userService.findByUserName(registrationRequest.username) != null) loginResponse.errorMessage.add("User with this username is exists")
        if (!emailPatern.matcher(registrationRequest.email).matches()) loginResponse.errorMessage.add("Wrong email")
        if (userService.findByEmail(registrationRequest.email) != null) loginResponse.errorMessage.add("User with this email is exists")
        if (registrationRequest.password.length < 6) loginResponse.errorMessage.add("Length password must be more 6 symbols")
        if (registrationRequest.password != registrationRequest.passwordConfirm) loginResponse.errorMessage.add("Password must be equals confirm password")
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