package com.example.okta.testslices.http

import com.example.okta.testslices.repository.StudentScoreRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(StudentsRestController::class)
internal class StudentsRestControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var studentScoreRepository: StudentScoreRepository

    @Test
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