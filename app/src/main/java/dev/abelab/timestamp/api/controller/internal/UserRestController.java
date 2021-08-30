package dev.abelab.timestamp.api.controller.internal;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.*;
import lombok.*;
import dev.abelab.timestamp.api.response.UsersResponse;
import dev.abelab.timestamp.service.UserService;

@Api(tags = "User")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class UserRestController {

    private final UserService userService;

    /**
     * ユーザ一覧取得API
     *
     * @param loginUser ログインユーザ
     *
     * @return ユーザ一覧レスポンス
     */
    @ApiOperation( //
        value = "ユーザ一覧の取得", //
        notes = "ユーザ一覧を取得する。" //
    )
    @ApiResponses( //
        value = { //
                @ApiResponse(code = 200, message = "取得成功", response = UsersResponse.class), //
                @ApiResponse(code = 401, message = "ユーザがログインしていない"), //
                @ApiResponse(code = 403, message = "ユーザに権限がない") //
        })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public UsersResponse getUsers( //
        @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = true) final String credentials //
    ) {
        return this.userService.getUsers(credentials);
    }

}
