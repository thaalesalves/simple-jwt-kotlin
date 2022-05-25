package com.signicat.interview.adapters.rest.controller

import com.signicat.interview.application.service.UserService
import com.signicat.interview.domain.exception.PasswordNotStrongEnoughException
import com.signicat.interview.domain.model.PayloadResponse
import com.signicat.interview.domain.model.Subject
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping("/register")
    fun registerUser(@RequestBody newUser: Subject): ResponseEntity<PayloadResponse> {

        return try {
            ResponseEntity.status(HttpStatus.CREATED)
                .body(PayloadResponse(200, "Success", userService.signUp(newUser)))
        } catch (e: PasswordNotStrongEnoughException) {
            ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                .body(PayloadResponse(HttpStatus.PRECONDITION_FAILED.value(), e.message.toString()))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(PayloadResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.message.toString()))
        }
    }
}
