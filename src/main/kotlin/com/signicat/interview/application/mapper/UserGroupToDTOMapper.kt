package com.signicat.interview.application.mapper

import com.signicat.interview.adapters.data.entity.UserGroupEntity
import com.signicat.interview.domain.model.Subject
import com.signicat.interview.domain.model.UserGroup
import org.springframework.stereotype.Component
import java.util.Collections
import java.util.Optional
import java.util.function.Function
import java.util.stream.Collectors

@Component
class UserGroupToDTOMapper : Function<UserGroupEntity, UserGroup> {

    override fun apply(t: UserGroupEntity): UserGroup {
        return UserGroup(t.id, t.name, Optional.ofNullable(t.subjects)
            .orElse(Collections.emptyList())
            .stream()
            .map { s -> Subject(s.id, s.username, s.password, Collections.emptyList()) }
            .collect(Collectors.toList()))
    }
}