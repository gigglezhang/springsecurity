package com.jc.spring.oauth2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.web.authentication.ui.DefaultLogoutPageGeneratingFilter;
import org.springframework.util.Base64Utils;
import sun.misc.BASE64Encoder;

@SpringBootTest
class Oauth2ApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {

        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jccc.keystore"),"123456".toCharArray());
        System.out.println(Base64Utils.encodeToString(keyStoreKeyFactory.getKeyPair("jccc").getPublic().getEncoded()));
        System.out.println(Base64Utils.encodeToString(keyStoreKeyFactory.getKeyPair("jccc").getPrivate().getEncoded()));


    }
}
