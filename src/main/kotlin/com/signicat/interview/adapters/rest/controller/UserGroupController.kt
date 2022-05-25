package com.signicat.interview.adapters.rest.controller

import com.signicat.interview.application.service.UserGroupService
import com.signicat.interview.domain.exception.UserGroupNotFoundException
import com.signicat.interview.domain.model.PayloadResponse
import com.signicat.interview.domain.model.UserGroup
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/groups")
class UserGroupController(
    private val userGroupService: UserGroupService
) {
    @PostMapping("/save")
    fun save(@RequestBody userGroup: UserGroup?): ResponseEntity<PayloadResponse> {

        return try {
            val savedUserGroup = userGroupService!!.saveUserGroup(userGroup!!)
            ResponseEntity.status(HttpStatus.CREATED)
                .body(PayloadResponse(HttpStatus.CREATED.value(), "User group saved succesfully", savedUserGroup))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                    PayloadResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "There was an error saving the new user group"
                    )
                )
        }
    }

    @GetMapping("/get/{id}")
    fun listById(@PathVariable("id") userGroupId: Long): ResponseEntity<PayloadResponse> {
        return try {
            ResponseEntity.status(HttpStatus.OK)
                .body(
                    PayloadResponse(
                        HttpStatus.OK.value(),
                        "The item request was found successfully",
                        userGroupService.retrieveUserGroupById(userGroupId)
                    )
                )
        } catch (e: UserGroupNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                    PayloadResponse(HttpStatus.NOT_FOUND.value(), "The user group requested was not found")
                )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                    PayloadResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "There was an error retrieving the user group"
                    )
                )
        }
    }

    @GetMapping("/get")
    fun listAll(): ResponseEntity<PayloadResponse> {
        return try {
            ResponseEntity.status(HttpStatus.OK)
                .body(
                    PayloadResponse(
                        HttpStatus.OK.value(),
                        "The item request was found successfully",
                        userGroupService!!.retrieveAllUserGroups()
                    )
                )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                    PayloadResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "There was an error retrieving the user groups"
                    )
                )
        }
    }

    @PutMapping("/update")
    fun update(@RequestBody userGroup: UserGroup): ResponseEntity<PayloadResponse> {
        return try {
            val updatedUserGroup = userGroupService!!.updateUserGroup(userGroup)
            ResponseEntity.status(HttpStatus.OK)
                .body(
                    PayloadResponse(
                        HttpStatus.OK.value(),
                        "User group saved succesfully",
                        updatedUserGroup

                    )
                )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                    PayloadResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "There was an error updating the user group with id " + userGroup.id
                    )
                )
        }
    }

    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable("id") id: Long): ResponseEntity<PayloadResponse> {
        userGroupService!!.deleteUserGroup(id)
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                PayloadResponse(
                    HttpStatus.OK.value(),
                    "User group deleted succesfully"
                )
            )
    }
}
