package ink.chow.web.sovereigns;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import ink.chow.web.sovereigns.util.GitUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SovereignsApplicationTests {

    @Test
    public void contextLoads() {
        String p = "$2a$10$4Q.ieDL1Cll5lmEyxAdpl./thKasoqg1oUBR.YA0papb54WGsToEy";

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        System.out.println(passwordEncoder.matches("ad21f73dcb4b8c42b1777e0067534ff1", p));

        //GitUtils.pull("D:\\workspace\\work\\seagull-x", "", "");
    }

}
