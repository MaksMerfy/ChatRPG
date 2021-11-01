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
    var id: String = ""
    var username: String = ""
    var email: String = ""
    var nickname: String = ""
    var password: String = ""
    var passwordConfirm: String = ""
    @ManyToOne
    var role: Role? = Role()
}