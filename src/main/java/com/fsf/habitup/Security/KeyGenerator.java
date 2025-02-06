package com.fsf.habitup.Security;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class KeyGenerator {

	public byte[] generate256BitKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[32]; // 32 bytes = 256 bits
        secureRandom.nextBytes(key); // Fill the byte array with random bytes
        return key;
    }

    // Method to generate a 256-bit key and return it as a Base64-encoded string
    public String generate256BitKeyBase64() {
        byte[] key = generate256BitKey();
        return Base64.getEncoder().encodeToString(key); // Return Base64 encoded string
    }
}

