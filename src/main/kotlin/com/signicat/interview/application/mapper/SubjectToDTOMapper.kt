package com.signicat.interview.application.mapper

import com.signicat.interview.adapters.data.entity.SubjectEntity
import com.signicat.interview.domain.model.Subject
import org.springframework.stereotype.Component
import java.util.*
import java.util.function.Function
import java.util.stream.Collectors

@Component
class SubjectToDTOMapper(
    private val userGroupToDTOMapper: UserGroupToDTOMapper
) : Function<SubjectEntity, Subject> {

    override fun apply(t: SubjectEntity): Subject {
        return Subject(
            t.id, t.username, t.password, Optional.ofNullable(t.groups)
                .orElse(Collections.emptyList())
                .stream()
                .map(userGroupToDTOMapper::apply)
                .collect(Collectors.toList())
        )
    }
}