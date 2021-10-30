package ru.chatrpg.main.repositories

import org.springframework.data.jpa.repository.JpaRepository
import ru.chatrpg.main.model.User

interface UserRepository: JpaRepository<User, String> {
    fun findByUsername(username: String): User?
}