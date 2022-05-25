package com.signicat.interview.adapters.data.repository

import com.signicat.interview.adapters.data.entity.UserGroupEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserGroupRepository : JpaRepository<UserGroupEntity, Long> {
}