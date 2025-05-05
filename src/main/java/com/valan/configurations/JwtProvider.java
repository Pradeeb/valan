package com.valan.configurations;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtProvider {
	
    static SecretKey key = Keys.hmacShaKeyFor(JwtConstatnt.SECRETE_KEY.getBytes());
    
    
    public static String genrateToken(Authentication auth) {
    	Collection<? extends GrantedAuthority> authorities=auth.getAuthorities();
    	String roles =populateAuthorities(authorities);
    	
    	String jwt=Jwts.builder()
    			.setIssuedAt(new Date())
    			.setExpiration(new Date(new Date().getTime()+86400000))
    			.claim("email", auth.getName())
    			.claim("authorities", roles)
    			.signWith(key)
    			.compact();
    	
		return jwt;
    }
    
    public static String getEmailFromToken(String token) {
    	 token=token.substring(7);
    	
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String email = claims.get("email", String.class);
        
        return email;
    }
    
    public static String populateAuthorities(Collection<? extends GrantedAuthority> authority) { 
    	
    	Set<String> auth=new HashSet<>();
    	for(GrantedAuthority ga:authority) {
    		auth.add(ga.getAuthority());
    	}
		return String.join(",", auth);
    	
    }

    
    
}
