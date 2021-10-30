package ru.chatrpg.main.model

import org.hibernate.annotations.GenericGenerator
import javax.persistence.*


@Entity
@Table(name = "users")
class User {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id", unique = true)
    val id: String = ""
    val username: String = ""
    val password: String = ""
    val passwordConfirm: String = ""
    @ManyToOne
    val role: Role = Role()
}