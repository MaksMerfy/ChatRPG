package ru.chatrpg.main.config.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

@Component
@Slf4j
class JwtProvider {
    private val jwtSecret: String = "YourJWTTokenOnProd"
    fun generateToken(login: String?): String {
        val date = Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant())
        return Jwts.builder()
            .setSubject(login)
            .setExpiration(date)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
    }

    fun validateToken(token: String?): Boolean {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token)
            return true
        } catch (e: Exception) {
            val logger = LoggerFactory.getLogger("ru.chatrpg.main.config.jwt.JwtProvider")
            logger.warn("In JwtProvider : $e")
        }
        return false
    }

    fun getLoginFromToken(token: String?): String {
        val claims: Claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).body
        return claims.subject
    }
}