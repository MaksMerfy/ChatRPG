package ru.chatrpg.main.services.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.chatrpg.main.model.Role
import ru.chatrpg.main.model.User
import ru.chatrpg.main.repositories.RoleRepository
import ru.chatrpg.main.repositories.UserRepository
import ru.chatrpg.main.services.UserService

@Service
class UserServiceImpl: UserService {
    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var roleRepository: RoleRepository
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    override fun saveUser(user: User?): User? {
        var userForReturn: User? = user
        if (user != null){
            user.role = roleRepository.findByName("Role_User")
            user.password = passwordEncoder.encode(user.password)
            userForReturn = userRepository.save(user)
        }

        return userForReturn
    }

    override fun findByUserName(userName: String): User? {
        return userRepository.findByUsername(userName)
    }

    override fun findByUserNameAndPassword(userName: String, password: String): User? {
        val user: User? = findByUserName(userName)
        if (user != null && passwordEncoder.matches(password, user.password)) return user;
        return null;
    }

    override fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }
}