package com.example.okta.testslices.http

import com.example.okta.testslices.entity.StudentScore
import com.example.okta.testslices.repository.StudentScoreRepository
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class StudentsRestController(
        private val scoreRepository: StudentScoreRepository
) {

    @GetMapping("/all")
    fun getAllScores(): List<StudentScore> = scoreRepository.findAll()

    @GetMapping("/mine")
    fun getMyScores(@AuthenticationPrincipal user: OAuth2User): String {
        return "${user.name} ${user.attributes}";
    }

}