package com.fsf.habitup.DTO;

public class AuthResponse {

	private String token;
    private String message;
	public AuthResponse(String token2, String message2) {
		this.token = token2;
        this.message = message2;// TODO Auto-generated constructor stub
	}
	public String getToken() {
		return token;
	}
	public String getMessage() {
		return message;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void setMessage(String message) {
		this.message = message;
}
}
