package com.lguplus.fleta.jasypt;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JasyptTest {

    @Value("${jasypt.encryptor.password}")
    private String encryptKey;

    @Test
    void encryptAccount() {
        System.out.println(">>> username : " + encryptString("postgre"));
        System.out.println(">>> password : " + encryptString("postgres"));
    }

    public String encryptString(String str) {
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setPassword(encryptKey);
        return pbeEnc.encrypt(str);
    }
}
