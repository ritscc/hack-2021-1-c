package dev.abelab.timestamp.api.controller.internal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.*;
import lombok.*;
import dev.abelab.timestamp.api.request.LoginRequest;
import dev.abelab.timestamp.api.response.AccessTokenResponse;
import dev.abelab.timestamp.service.AuthService;

@Api(tags = "Auth")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class AuthRestController {

    private final AuthService authService;

    /**
     * ログイン処理API
     *
     * @param requestBody ログイン情報
     */
    @ApiOperation(value = "ログイン", //
        notes = "ユーザのログイン処理を行う。" //
    )
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "ログイン成功", response = AccessTokenResponse.class), //
            @ApiResponse(code = 401, message = "パスワードが間違っている"), //
            @ApiResponse(code = 404, message = "ユーザが存在しない"), //
    })
    @PostMapping(value = "/login")
    @ResponseStatus(HttpStatus.OK)
    public AccessTokenResponse login( //
        @Validated @ApiParam(name = "body", required = true, value = "ログイン情報") @RequestBody final LoginRequest requestBody //
    ) {
        return this.authService.login(requestBody);
    }
}
