package dev.abelab.timestamp.api.controller.internal;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.*;
import lombok.*;
import dev.abelab.timestamp.api.request.UserCreateRequest;
import dev.abelab.timestamp.api.request.UserUpdateRequest;
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
     * @param credentials 資格情報
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

    /**
     * ユーザ作成API
     *
     * @param credentials 資格情報
     *
     * @param requestBody ユーザ作成リクエスト
     */
    @ApiOperation( //
        value = "ユーザの作成", //
        notes = "ユーザを作成する。" //
    )
    @ApiResponses( //
        value = { //
                @ApiResponse(code = 201, message = "作成成功"), //
                @ApiResponse(code = 400, message = "無効なパスワード"), //
                @ApiResponse(code = 401, message = "ユーザがログインしていない"), //
                @ApiResponse(code = 403, message = "ユーザに権限がない"), //
                @ApiResponse(code = 409, message = "ユーザが既に存在している"), //
        } //
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser( //
        @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = true) final String credentials, //
        @Validated @ApiParam(name = "body", required = true, value = "新規ユーザ情報") @RequestBody final UserCreateRequest requestBody //
    ) {
        this.userService.createUser(credentials, requestBody);;
    }

    /**
     * ユーザ更新API
     *
     * @param credentials 資格情報
     *
     * @param userId      ユーザID
     *
     * @param requestBody ユーザ更新リクエスト
     */
    @ApiOperation( //
        value = "ユーザの更新", //
        notes = "ユーザを更新する。" //
    )
    @ApiResponses( //
        value = { //
                @ApiResponse(code = 200, message = "更新成功"), //
                @ApiResponse(code = 401, message = "ユーザがログインしていない"), //
                @ApiResponse(code = 403, message = "ユーザに権限がない"), //
                @ApiResponse(code = 404, message = "ユーザが存在しない"), //
        } //
    )
    @PutMapping(value = "/{user_id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser( //
        @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = true) final String credentials, //
        @ApiParam(name = "user_id", required = true, value = "ユーザID") @PathVariable("user_id") final int userId, //
        @Validated @ApiParam(name = "body", required = true, value = "ユーザ更新情報") @RequestBody final UserUpdateRequest requestBody //
    ) {
        this.userService.updateUser(credentials, userId, requestBody);
    }

}
