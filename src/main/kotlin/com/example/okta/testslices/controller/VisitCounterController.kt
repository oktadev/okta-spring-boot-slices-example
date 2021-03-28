package com.example.okta.testslices.controller

import com.example.okta.testslices.service.ViewersService
import org.springframework.security.core.AuthenticatedPrincipal
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.view.RedirectView

@Controller
class VisitCounterController(
    private val viewersService: ViewersService
) {

    @GetMapping("/")
    fun index(@AuthenticationPrincipal user: AuthenticatedPrincipal?): ModelAndView {
        val myUsername = user?.name ?: "guest"

        viewersService.insertOrIncrementViews(myUsername)

        return ModelAndView(
            "index",
            mapOf(
                "avgVisitsPerUser" to viewersService.averagesViewsPerUser().toInt(),
                "username" to myUsername,
                "visitors" to viewersService.allViewers(),
                "isGuest" to user?.name.isNullOrBlank()
            )
        )
    }

    @GetMapping("/signin")
    fun loginRedirector(): RedirectView =
        RedirectView("/oauth2/authorization/okta")
}
