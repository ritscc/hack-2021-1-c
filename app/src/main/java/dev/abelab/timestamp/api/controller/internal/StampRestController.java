package dev.abelab.timestamp.api.controller.internal;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.*;
import lombok.*;
import dev.abelab.timestamp.api.response.StampsResponse;
import dev.abelab.timestamp.service.StampService;

@Api(tags = "Stamp")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/stamps", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class StampRestController {

    private final StampService stampService;

    /**
     * スタンプ一覧取得API
     *
     * @param credentials 資格情報
     *
     * @return スタンプ一覧レスポンス
     */
    @ApiOperation( //
        value = "スタンプ一覧の取得", //
        notes = "スタンプ一覧を取得する。" //
    )
    @ApiResponses( //
        value = { //
                @ApiResponse(code = 200, message = "取得成功", response = StampsResponse.class), //
                @ApiResponse(code = 401, message = "ユーザがログインしていない") //
        })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public StampsResponse getStamps( //
        @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = true) final String credentials //
    ) {
        return this.stampService.getStamps(credentials);
    }

}
