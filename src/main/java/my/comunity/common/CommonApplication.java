package my.comunity.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("my.comunity.common.mapper")
@SpringBootApplication
public class CommonApplication {



    public static void main(String[] args) {
        SpringApplication.run(CommonApplication.class, args);
    }

}
