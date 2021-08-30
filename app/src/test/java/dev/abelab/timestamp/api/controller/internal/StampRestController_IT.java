package dev.abelab.timestamp.api.controller.internal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.*;
import static org.junit.jupiter.params.provider.Arguments.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;

import dev.abelab.timestamp.api.controller.AbstractRestController_IT;
import dev.abelab.timestamp.db.entity.UserSample;
import dev.abelab.timestamp.api.request.LoginRequest;
import dev.abelab.timestamp.api.response.AccessTokenResponse;
import dev.abelab.timestamp.enums.UserRoleEnum;
import dev.abelab.timestamp.exception.ErrorCode;
import dev.abelab.timestamp.exception.NotFoundException;
import dev.abelab.timestamp.exception.UnauthorizedException;

/**
 * StampRestController Integration Test
 */
public class StampRestController_IT extends AbstractRestController_IT {

	// API PATH
	static final String BASE_PATH = "/api/stamps";
	static final String GET_STAMPS_PATH = BASE_PATH;

	/**
	 * スタンプ一覧取得APIのテスト
	 */
	@Nested
	@TestInstance(PER_CLASS)
	class GetStampsTest extends AbstractRestControllerInitialization_IT {
	}

}
