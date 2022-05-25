package com.signicat.interview.domain.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class PayloadResponse(
    var statusCode: Int,
    var message: String,
    var additionalDetails: Any? = null
)
