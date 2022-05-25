package com.signicat.interview.application.mapper

import com.signicat.interview.adapters.data.entity.SubjectEntity
import com.signicat.interview.domain.model.Subject
import org.springframework.stereotype.Component
import java.util.*
import java.util.function.Function
import java.util.stream.Collectors

@Component
class SubjectToEntityMapper(
    private val userGroupToEntityMapper: UserGroupToEntityMapper
) : Function<Subject, SubjectEntity> {

    override fun apply(t: Subject): SubjectEntity {
        return SubjectEntity(
            t.id, t.username, t.password, Optional.ofNullable(t.groups)
                .orElse(Collections.emptyList())
                .stream()
                .map(userGroupToEntityMapper::apply)
                .collect(Collectors.toList())
        )
    }
}