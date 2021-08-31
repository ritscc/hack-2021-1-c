package dev.abelab.timestamp.exception;

import lombok.*;

/**
 * The enum error code
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {

    /**
     * Internal Server Error: 1000~1099
     */
    UNEXPECTED_ERROR(1000, "exception.internal_server_error.unexpected_error"),

    FAILED_TO_SEND_SLACK(1001, "exception.internal_server_error.failed_to_send_slack"),

    /**
     * Not Found: 1100~1199
     */
    NOT_FOUND_API(1100, "exception.not_found.api"),

    NOT_FOUND_USER(1101, "exception.not_found.user"),

    NOT_FOUND_USER_ROLE(1102, "exception.not_found.user_role"),

    NOT_FOUND_STAMP(1103, "exception.not_found.stamp"),

    NOT_FOUND_STAMP_ATTACHMENT(1104, "exception.not_found.stamp_attachment"),

    /**
     * Conflict: 1200~1299
     */
    CONFLICT_EMAIL(1200, "exception.conflict.email"),

    /**
     * Forbidden: 1300~1399
     */
    USER_HAS_NO_PERMISSION(1300, "exception.forbidden.user_has_no_permission"),

    /**
     * Bad Request: 1400~1499
     */
    VALIDATION_ERROR(1400, "exception.bad_request.validation_error"),

    INVALID_PASSWORD_SIZE(1401, "exception.bad_request.invalid_password_size"),

    TOO_SIMPLE_PASSWORD(1402, "exception.bad_request.too_simple_password"),

    INVALID_EXPIRED_AT(1403, "exception.bad_request.invalid_expired_at"),

    /**
     * Unauthorized: 1500~1599
     */
    USER_NOT_LOGGED_IN(1500, "exception.unauthorized.user_not_logged_in"),

    WRONG_PASSWORD(1501, "exception.unauthorized.wrong_password"),

    INVALID_ACCESS_TOKEN(1502, "exception.unauthorized.invalid_access_token"),

    EXPIRED_ACCESS_TOKEN(1503, "exception.unauthorized.expired_access_token");

    private final int code;

    private final String messageKey;

}
