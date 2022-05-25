package com.signicat.interview.domain.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import java.util.Collections

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Subject (

    var id: Long = 0,
    var username: String = "",
    var password: String = "",
    var groups: List<UserGroup> = Collections.emptyList()
)