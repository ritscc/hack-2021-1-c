package dev.abelab.timestamp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageSourceConfig {

    @Bean
    public MessageSource messageSource(@Value("${spring.messages.basename}") String basename,
        @Value("${spring.messages.encoding}") String encoding) {

        final var messageSource = new YamlMessageSource();

        messageSource.setBasenames(basename);
        messageSource.setDefaultEncoding(encoding);

        return messageSource;
    }

}
