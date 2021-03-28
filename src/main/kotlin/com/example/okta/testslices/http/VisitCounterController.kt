package com.example.okta.testslices.http

import com.example.okta.testslices.entity.ViewerModel
import com.example.okta.testslices.service.ViewersService
import org.springframework.security.core.AuthenticatedPrincipal
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class VisitCounterController(
    private val viewersService: ViewersService
) {

    @GetMapping("/")
    fun index(@AuthenticationPrincipal user: UserDetails?): ModelAndView {
        val myUsername = user?.username ?: "guest"

        viewersService.insertOrIncrementViews(myUsername)

        return ModelAndView("index", mapOf(
            "avgVisitsPerUser" to viewersService.averagesViewsPerUser().toInt(),
            "username" to myUsername,
            "visitors" to viewersService.allViewers()
        ))
    }

    @GetMapping("/all")
    fun getAllScores(): List<ViewerModel> = viewersService.allViewers()

    @GetMapping("/mine")
    fun getMyScores(@AuthenticationPrincipal user: OAuth2User): String {
        return "${user.name} ${user.attributes}";
    }

}