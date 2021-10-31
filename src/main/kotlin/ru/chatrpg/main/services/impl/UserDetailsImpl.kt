package ru.chatrpg.main.services.impl

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import ru.chatrpg.main.model.User


@Service
class UserDetailsImpl : UserDetails {
    private var login: String = ""
    private var password: String = ""
    private val grantedAuthorities: Collection<GrantedAuthority>? = null
    override fun getAuthorities(): Collection<GrantedAuthority>? {
        return grantedAuthorities
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return login
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    companion object {
        fun fromUserEntityToCustomUserDetails(user: User): UserDetailsImpl {
            val c = UserDetailsImpl()
            c.login = user.username
            c.password = user.password
            return c
        }
    }
}