package dev.abelab.timestamp.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan("dev.abelab.timestamp.db.mapper")
@Configuration
public class MyBatisConfig {
}
