package ru.chatrpg.main.config.jwt

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils.hasText
import org.springframework.web.filter.GenericFilterBean
import ru.chatrpg.main.services.impl.UserDetailsImpl
import ru.chatrpg.main.services.impl.UserDetailsServiceImpl
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

@Component
class JwtFilter : GenericFilterBean() {
    @Autowired
    private lateinit var jwtProvider: JwtProvider
    @Autowired
    private lateinit var userDetailsServiceImpl: UserDetailsServiceImpl

    private val AUTHORIZATION: String = "Authorization"

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, filterChain: FilterChain) {
        val token = getTokenFromRequest(servletRequest as HttpServletRequest)
        if (token != null && jwtProvider.validateToken(token)) {
            val userName: String = jwtProvider.getLoginFromToken(token).lowercase(Locale.getDefault())
            val userDetailsService: UserDetailsImpl = userDetailsServiceImpl.loadUserByUsername(userName) ?: throw UsernameNotFoundException(userName)
            val auth =
                UsernamePasswordAuthenticationToken(userDetailsService, null, userDetailsService.authorities)
            SecurityContextHolder.getContext().authentication = auth
        }
        filterChain.doFilter(servletRequest, servletResponse)
    }

    private fun getTokenFromRequest(request: HttpServletRequest): String? {
        val bearer = request.getHeader(AUTHORIZATION)
        return if (hasText(bearer) && bearer.startsWith("Bearer ")) {
            bearer.substring(7)
        } else null
    }
}