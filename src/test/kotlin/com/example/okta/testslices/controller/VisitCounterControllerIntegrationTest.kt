package com.example.okta.testslices.controller

import com.example.okta.testslices.entity.ViewerModel
import com.example.okta.testslices.service.ViewersService
import org.hamcrest.Matchers.containsStringIgnoringCase
import org.hamcrest.Matchers.stringContainsInOrder
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.TEXT_HTML
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(VisitCounterController::class)
internal class VisitCounterControllerIntegrationTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var viewersService: ViewersService

    @Test
    fun `renders provided data from the viewersService visiting as a guest`() {
        // given
        `when`(viewersService.averagesViewsPerUser()).thenReturn(42.0)
        `when`(viewersService.allViewers()).thenReturn(
            listOf(
                ViewerModel(1, "Olga", 100),
                ViewerModel(2, "Ketty", 200),
            )
        )

        // when
        mockMvc.get("/") {
            accept = TEXT_HTML
        }.andExpect {
            // then
            status { isOk() }
            content {
                contentTypeCompatibleWith(TEXT_HTML)
                string(containsStringIgnoringCase("G'day, guest"))
                string(containsStringIgnoringCase("there are 42 visits per user"))

                string(containsStringIgnoringCase("Ketty: 200"))
                string(containsStringIgnoringCase("Olga: 100"))
                string(stringContainsInOrder("Olga", "Ketty"))
            }
        }
    }

    @Test
    fun `greet guest`() {
        // when
        mockMvc.get("/") {
            accept = TEXT_HTML
        }.andExpect {
            // then
            status { isOk() }
            content {
                string(containsStringIgnoringCase("G'day, guest"))
            }
        }
    }

    @Test
    fun `greet authenticated user`() {
        // given
        val oauth2User = mock(OAuth2User::class.java).also {
            doReturn("Nikolay").`when`(it).name
        }

        // when
        mockMvc.get("/") {
            accept = TEXT_HTML
            with(oauth2Login().oauth2User(oauth2User))
        }.andExpect {
            // then
            status { isOk() }
            content {
                string(containsStringIgnoringCase("G'day, Nikolay"))
            }
        }
    }

    @Test
    fun `signin redirect works`() {
        // when
        mockMvc.get("/signin") {
            accept = TEXT_HTML
        }.andExpect {
            // then
            status { is3xxRedirection() }
            status { redirectedUrlPattern("**/oauth2/**") }
        }
    }
}
