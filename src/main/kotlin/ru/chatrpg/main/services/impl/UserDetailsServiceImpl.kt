package ru.chatrpg.main.services.impl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.chatrpg.main.model.User
import ru.chatrpg.main.repositories.UserRepository

@Service
class UserDetailsServiceImpl : UserDetailsService {
    @Autowired
    private lateinit var userRepository: UserRepository
    override fun loadUserByUsername(username: String): UserDetailsImpl? {
        val user: User = userRepository.findByUsername(username) ?: throw UsernameNotFoundException(username)
        if (user.username != username) throw UsernameNotFoundException(username)
        return UserDetailsImpl.fromUserEntityToCustomUserDetails(user)
    }
}