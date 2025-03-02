package com.fsf.habitup.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OtpRegisterRequest {

    @JsonProperty("otpVerificationRequest")
    private OtpVerificationReuest otpVerificationRequest;

    @JsonProperty("registerRequest")
    private RegisterRequest registerRequest;

    public OtpRegisterRequest(OtpVerificationReuest otpVerificationReuest, RegisterRequest registerRequest) {
        this.otpVerificationRequest = otpVerificationReuest;
        this.registerRequest = registerRequest;
    }

    public OtpVerificationReuest getOtpVerificationRequest() {
        return otpVerificationRequest;
    }

    public void setOtpVerificationRequest(OtpVerificationReuest otpVerificationRequest) {
        this.otpVerificationRequest = otpVerificationRequest;
    }

    public RegisterRequest getRegisterRequest() {
        return registerRequest;
    }

    public void setRegisterRequest(RegisterRequest registerRequest) {
        this.registerRequest = registerRequest;
    }
}
