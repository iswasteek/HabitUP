package com.fsf.habitup.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OtpRegisterRequest {

    @JsonProperty("otpVerificationRequest")
    private OtpVerificationReuest otpVerificationReuest;

    @JsonProperty("registerRequest")
    private RegisterRequest registerRequest;

    public OtpRegisterRequest(OtpVerificationReuest otpVerificationReuest, RegisterRequest registerRequest) {
        this.otpVerificationReuest = otpVerificationReuest;
        this.registerRequest = registerRequest;
    }

    /**
     * @return OtpVerificationReuest return the otpVerificationReuest
     */
    public OtpVerificationReuest getOtpVerificationReuest() {
        return otpVerificationReuest;
    }

    /**
     * @param otpVerificationReuest the otpVerificationReuest to set
     */
    public void setOtpVerificationReuest(OtpVerificationReuest otpVerificationReuest) {
        this.otpVerificationReuest = otpVerificationReuest;
    }

    /**
     * @return RegisterRequest return the registerRequest
     */
    public RegisterRequest getRegisterRequest() {
        return registerRequest;
    }

    /**
     * @param registerRequest the registerRequest to set
     */
    public void setRegisterRequest(RegisterRequest registerRequest) {
        this.registerRequest = registerRequest;
    }

}
