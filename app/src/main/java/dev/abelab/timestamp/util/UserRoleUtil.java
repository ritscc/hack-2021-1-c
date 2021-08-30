package dev.abelab.timestamp.util;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import lombok.*;
import dev.abelab.timestamp.enums.UserRoleEnum;
import dev.abelab.timestamp.exception.ErrorCode;
import dev.abelab.timestamp.exception.NotFoundException;

@RequiredArgsConstructor
@Component
public class UserRoleUtil {

    /**
     * 有効なユーザロールかチェック
     *
     * @param roleId ロールID
     */
    public static void checkForValidRoleId(final int roleId) {
        Arrays.stream(UserRoleEnum.values()).filter(e -> e.getId() == roleId) //
            .findFirst().orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER_ROLE));
    }

}
