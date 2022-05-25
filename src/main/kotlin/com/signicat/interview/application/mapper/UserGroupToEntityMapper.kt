package com.signicat.interview.application.mapper

import com.signicat.interview.adapters.data.entity.SubjectEntity
import com.signicat.interview.adapters.data.entity.UserGroupEntity
import com.signicat.interview.domain.model.UserGroup
import org.springframework.stereotype.Component
import java.util.*
import java.util.function.Function
import java.util.stream.Collectors

@Component
class UserGroupToEntityMapper : Function<UserGroup, UserGroupEntity> {

    override fun apply(t: UserGroup): UserGroupEntity {
        return UserGroupEntity(t.id, t.name, Optional.ofNullable(t.subjects)
            .orElse(Collections.emptyList())
            .stream()
            .map { s -> SubjectEntity(s.id, s.username, s.password, Collections.emptyList()) }
            .collect(Collectors.toList()))
    }
}