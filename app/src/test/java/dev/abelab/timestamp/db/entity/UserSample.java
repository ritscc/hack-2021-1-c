package dev.abelab.timestamp.db.entity;

import java.util.Date;

/**
 * User Sample Builder
 */
public class UserSample extends AbstractSample {

    public static UserSampleBuilder builder() {
        return new UserSampleBuilder();
    }

    public static class UserSampleBuilder {

        private Integer id = SAMPLE_INT;
        private String email = SAMPLE_STR;
        private String password = SAMPLE_STR;
        private String firstName = SAMPLE_STR;
        private String lastName = SAMPLE_STR;
        private Integer roleId = SAMPLE_INT;
        private Date createdAt = SAMPLE_DATE;
        private Date updatedAt = SAMPLE_DATE;

        public UserSampleBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public UserSampleBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserSampleBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserSampleBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserSampleBuilder lastName(String lastName) {
            this.firstName = lastName;
            return this;
        }

        public UserSampleBuilder roleId(Integer roleId) {
            this.roleId = roleId;
            return this;
        }

        public UserSampleBuilder createdAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserSampleBuilder updatedAt(Date updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public User build() {
            return User.builder() //
                .id(this.id) //
                .email(this.email) //
                .password(this.password) //
                .firstName(this.firstName) //
                .lastName(this.lastName) //
                .roleId(this.roleId) //
                .createdAt(this.createdAt) //
                .updatedAt(this.updatedAt) //
                .build();
        }

    }

}
