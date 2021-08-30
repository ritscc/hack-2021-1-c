package dev.abelab.timestamp.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.*;

@Data
@Configuration
@ConfigurationProperties("timestamp")
public class TimeStampProperty {

    /**
     * protocol
     */
    String protocol;

    /**
     * host name
     */
    String hostname;

    /**
     * Admin
     */
    Admin admin;

    @Data
    public static class Admin {

        /**
         * Admin first name
         */
        String firstName;

        /**
         * Admin last name
         */
        String lastName;

        /**
         * Admin email
         */
        String email;

        /**
         * Admin password
         */
        String password;

    }

}
