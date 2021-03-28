package com.example.okta.testslices.http

import com.example.okta.testslices.entity.ViewerModel
import com.example.okta.testslices.repository.ViewersRepository
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class VisitCounterController(
        private val viewersRepository: ViewersRepository
) {

    @GetMapping("/")
    fun root(@AuthenticationPrincipal user: OAuth2User?): ModelAndView {
        return ModelAndView("index", mapOf(
                "username" to "boo $user"
        ))
    }

    @GetMapping("/all")
    fun getAllScores(): List<ViewerModel> = viewersRepository.findAll()

    @GetMapping("/mine")
    fun getMyScores(@AuthenticationPrincipal user: OAuth2User): String {
        return "${user.name} ${user.attributes}";
    }

}