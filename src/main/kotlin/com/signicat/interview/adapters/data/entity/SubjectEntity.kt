package com.signicat.interview.adapters.data.entity

import com.signicat.interview.domain.model.UserGroup
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(
    name = "subject",
    indexes = [
        Index(name = "ix_subject_id", columnList = "id"),
        Index(name = "ix_subject_username", columnList = "username")
    ]
)
class SubjectEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @NotNull
    var username: String,

    @NotNull
    var password: String,

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "subjects")
    var groups: MutableList<UserGroupEntity>
)