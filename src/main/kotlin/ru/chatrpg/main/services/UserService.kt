package ru.chatrpg.main.services

import ru.chatrpg.main.model.User

interface UserService {
    fun saveUser(user: User): User?
    fun findByUserName(userName: String): User?
    fun findByUserNameAndPassword(userName: String, password: String): User?

}