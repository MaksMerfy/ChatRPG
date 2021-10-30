package ru.chatrpg.main.model

import lombok.Getter
import lombok.Setter
import org.hibernate.annotations.GenericGenerator
import javax.persistence.*


@Entity
@Table(name = "roles")
@Setter
@Getter
class Role {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id", unique = true)
    var id: String = ""
    var name: String = ""
}