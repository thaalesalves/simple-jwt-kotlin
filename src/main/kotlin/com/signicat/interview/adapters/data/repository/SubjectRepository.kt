package com.signicat.interview.adapters.data.repository

import com.signicat.interview.adapters.data.entity.SubjectEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SubjectRepository : JpaRepository<SubjectEntity, Long> {

    fun findByUsername(username: String): SubjectEntity
}