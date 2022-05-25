package com.signicat.interview.application.service

import com.signicat.interview.adapters.data.repository.UserGroupRepository
import com.signicat.interview.application.mapper.UserGroupToDTOMapper
import com.signicat.interview.application.mapper.UserGroupToEntityMapper
import com.signicat.interview.domain.exception.UserGroupNotFoundException
import com.signicat.interview.domain.model.UserGroup
import org.springframework.stereotype.Service
import java.util.stream.Collectors


@Service
class UserGroupService(
    private val userGroupToEntityMapper: UserGroupToEntityMapper,
    private val userGroupToDTOMapper: UserGroupToDTOMapper,
    private val userGroupRepository: UserGroupRepository
) {
    fun saveUserGroup(userGroup: UserGroup): UserGroup {
        val entity = userGroupToEntityMapper.apply(userGroup)
        return userGroupToDTOMapper.apply(userGroupRepository.save(entity))
    }

    fun retrieveUserGroupById(userGroupId: Long): UserGroup {
        return userGroupToDTOMapper.apply(userGroupRepository.findById(userGroupId)
            .orElseThrow { UserGroupNotFoundException("The user group request could not be found") })
    }

    fun retrieveAllUserGroups(): List<UserGroup> {
        return userGroupRepository.findAll()
            .stream()
            .map(userGroupToDTOMapper::apply)
            .collect(Collectors.toList())
    }

    fun updateUserGroup(userGroup: UserGroup): UserGroup {
        val entity = userGroupToEntityMapper.apply(userGroup)
        return userGroupToDTOMapper.apply(userGroupRepository.save(entity))
    }

    fun deleteUserGroup(userGroupId: Long) {
        userGroupRepository.deleteById(userGroupId)
    }
}