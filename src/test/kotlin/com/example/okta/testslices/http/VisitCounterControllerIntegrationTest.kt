package com.example.okta.testslices.http

import com.example.okta.testslices.entity.ViewerModel
import com.example.okta.testslices.repository.ViewersRepository
import com.example.okta.testslices.service.ViewersService
import org.hamcrest.Matchers
import org.hamcrest.Matchers.containsStringIgnoringCase
import org.hamcrest.Matchers.stringContainsInOrder
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.http.MediaType.TEXT_HTML
import org.springframework.security.core.userdetails.User
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.security.Principal

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
        `when`(viewersService.allViewers()).thenReturn(listOf(
            ViewerModel(1, "Olga", 100),
            ViewerModel(2, "Ketty", 200),
        ))

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
            with(anonymous())
        }.andExpect {
            // then
            status { isOk() }
            content {
                string(containsStringIgnoringCase("G'day, guest"))
            }
        }
    }

    @WithMockUser("Nikolay")
    @Test
    fun `greet authenticated user`() {
        // when
        mockMvc.get("/") {
            accept = TEXT_HTML
            with(user("Nikolay"))
        }.andExpect {
            // then
            status { isOk() }
            content {
                string(containsStringIgnoringCase("G'day, Nikolay"))
            }
        }
    }


    //    @Test
    fun `can get all without as a guest`() {
        // given
//        studentScoreRepository


        // when & then
        mockMvc.get("/all") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content {
                jsonPath("\$[*].name") {
                    exists()
                    isArray()
                    value("Ann, Bob")
                }
            }
        }
    }
}