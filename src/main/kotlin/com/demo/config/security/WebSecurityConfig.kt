package com.demo.config.security

import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.lang.Exception

@EnableWebSecurity
open class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Configuration
    @Order(1)
    open class ApiWebSecurityConfig: WebSecurityConfigurerAdapter() {
        @Throws(Exception::class)
        override fun configure(http: HttpSecurity) {
            http.requestMatchers().antMatchers("/**")
                .and()
                .addFilterAfter(AuthenticationFilter(), BasicAuthenticationFilter::class.java)
                .csrf().disable()
        }
    }
}