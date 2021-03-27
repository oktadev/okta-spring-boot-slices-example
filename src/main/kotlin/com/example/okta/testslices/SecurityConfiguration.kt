package com.example.okta.testslices

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import java.lang.Exception


@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity): Unit = with(http) {
        authorizeRequests().run {
            mvcMatchers("/all").permitAll()
            anyRequest().authenticated()
        }
        oauth2Login().run {
            defaultSuccessUrl("/mine")
        }
        logout().run {
            logoutSuccessUrl("/all")
        }
    }
}