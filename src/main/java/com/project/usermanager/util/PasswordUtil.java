package com.project.usermanager.util;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.hash.Hashing;

public class PasswordUtil {

    private PasswordUtil(){}

    private static final Logger logger = LoggerFactory.getLogger(PasswordUtil.class);

    public static String encryptPassword(String password) {

        logger.info("encryptPassword - MSG: Started method");

        return Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    }
    
}
