package com.qcc.hallow.util;

import java.security.SecureRandom;

public class OtpGenerator {

    public static String generateOtpCode() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}
