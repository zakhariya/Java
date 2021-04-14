package com.slinemotors.slineproject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Base64;

@SpringBootTest
@ActiveProfiles("local")
@TestPropertySource(locations = "classpath:./application-local.properties")
class SLineProjectApplicationTests {

//	@Test
//	void contextLoads() {
//	}

    @Test
    void testConfigureGlobal() throws Exception {
        String pass = "dXNlcjpwYXNzd29yZA==";
        System.out.println(new String(Base64.getDecoder().decode(pass)));
    }

}
