package com.qcc.hallow.util;

import java.util.UUID;


public class IdGenerator {

    public static String generateId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
