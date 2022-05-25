package com.signicat.interview.adapters.data.entity

import com.signicat.interview.domain.model.UserGroup
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "subject")
class SubjectEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var username: String,
    var password: String,

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "subjects")
    var groups: MutableList<UserGroupEntity>
)