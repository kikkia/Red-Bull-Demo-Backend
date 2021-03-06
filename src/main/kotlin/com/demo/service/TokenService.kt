package com.demo.service

import com.demo.config.properties.APIProperties
import com.demo.db.entities.UserEntity
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.util.*
import kotlin.collections.HashMap

@Service
class TokenService(val apiProperties: APIProperties) {
    private val key = Keys.hmacShaKeyFor(apiProperties.keySecret.byteInputStream(StandardCharsets.UTF_8).readAllBytes())

    val JWT_TOKEN_VALIDITY = (24 * 7 * 60 * 60).toLong()

    //retrieve expiration date from jwt token
    fun getExpirationDateFromToken(token: String): Date {
        val claims: Claims = getAllClaimsFromToken(token)
        return claims.expiration
    }

    // Get a claim from token for given name
    fun getStringClaimFromToken(token: String, claim: String) : String {
        val claims: Claims = getAllClaimsFromToken(token)
        return claims[claim] as String
    }

    fun getSubjectFromToken(token: String) : String {
        return getAllClaimsFromToken(token).subject
    }

    //for retrieving any information from token we will need the secret key
    private fun getAllClaimsFromToken(token: String): Claims {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
    }

    //check if the token has expired
    private fun isTokenExpired(token: String): Boolean {
        val expiration: Date = getExpirationDateFromToken(token)
        return expiration.before(Date())
    }

    //generate token for user
    fun generateToken(user: UserEntity): String {
        val claims = HashMap<String, Any>()
        claims["userId"] = user.id
        return doGenerateToken(claims, user.username)
    }

    private fun doGenerateToken(claims: Map<String, Any>, subject: String): String {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setExpiration(
                Date.from(Instant.now().plusSeconds(JWT_TOKEN_VALIDITY)))
            .signWith(key)
            .compact();
    }

    /**
     * True if signature is good and not expired, false if bad sig or expired.
     */
    fun validateToken(token: String): Boolean {
        return try {
            !isTokenExpired(token)
        } catch (e: Exception) {
            when (e) {
                is SignatureException, is MalformedJwtException, is ExpiredJwtException -> {
                    // TODO: Log this
                    false
                }
                else -> throw e
            }
        }
    }
}