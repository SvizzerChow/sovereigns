package ink.chow.web.sovereigns;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan({"ink.chow.web.sovereigns.mapper"})
@EnableTransactionManagement
@EnableConfigurationProperties
public class SovereignsApplication extends ServletInitializer{
    private static Logger logger = LoggerFactory.getLogger(SovereignsApplication.class);

    public static void main(String[] args) {
        new SpringApplicationBuilder(SovereignsApplication.class).run(args);
    }
}
