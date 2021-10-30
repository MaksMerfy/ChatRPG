package ru.chatrpg.main.repositories

import org.springframework.data.jpa.repository.JpaRepository
import ru.chatrpg.main.model.Role

interface RoleRepository: JpaRepository<Role, String> {
    fun findByName(roleName: String): Role?
}