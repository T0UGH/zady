package com.edu.neu.zady;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.edu.neu..zady.mapper"}, annotationClass = Mapper.class)
public class ZadyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZadyApplication.class, args);
    }

}
