package com.signicat.interview.application.service

import com.signicat.interview.adapters.data.entity.SubjectEntity
import com.signicat.interview.adapters.data.repository.SubjectRepository
import com.signicat.interview.application.mapper.SubjectToDTOMapper
import com.signicat.interview.application.mapper.SubjectToEntityMapper
import com.signicat.interview.domain.exception.PasswordNotStrongEnoughException
import com.signicat.interview.domain.model.Subject
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.Collections
import java.util.Optional
import java.util.regex.Pattern

@Service
class UserService(
    private val subjectToDTOMapper: SubjectToDTOMapper,
    private val subjectToEntityMapper: SubjectToEntityMapper,
    private val subjectRepository: SubjectRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) : UserDetailsService {

    fun signUp(subject: Subject): Subject {
        val passwordPattern = Pattern
            .compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
        if (!passwordPattern.matcher(subject.password).matches()) {
            throw PasswordNotStrongEnoughException("The provided password is not strong enough.")
        }
        subject.password = passwordEncoder.encode(subject.password)
        val entity = subjectToEntityMapper.apply(subject)
        return subjectToDTOMapper.apply(subjectRepository.save(entity))
    }

    fun retrieveByUsername(username: String?): Subject {
        return subjectToDTOMapper.apply(subjectRepository.findByUsername(username!!))
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String?): UserDetails? {
        return Optional.ofNullable(subjectRepository.findByUsername(username!!))
            .map { subject -> User(subject.username, subject.password, Collections.emptyList()) }
            .orElseThrow { UsernameNotFoundException(username) }
    }
}