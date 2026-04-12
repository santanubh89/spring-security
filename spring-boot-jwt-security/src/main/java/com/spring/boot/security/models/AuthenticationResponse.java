package com.spring.boot.security.models;

public class AuthenticationResponse {

	private final String jwt;
	
	public String getJwt() {
		return this.jwt;
	}

	public AuthenticationResponse(String jwt) {
		super();
		this.jwt = jwt;
	}

	@Override
	public String toString() {
		return "AuthenticationResponse [jwt=" + jwt + "]";
	}

}
