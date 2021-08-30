package dev.abelab.timestamp.enums;

import lombok.*;

/**
 * The enum user role
 */
@Getter
@AllArgsConstructor
public enum UserRoleEnum {

    /**
     * 管理者
     */
    ADMIN(1, "管理者"),

    /**
     * 利用者
     */
    MEMBER(2, "利用者");

    private final int id;

    private final String name;

}
