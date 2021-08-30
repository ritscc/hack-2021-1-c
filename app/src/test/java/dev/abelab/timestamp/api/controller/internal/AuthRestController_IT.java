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
 * AuthRestController Integration Test
 */
public class AuthRestController_IT extends AbstractRestController_IT {

	// API PATH
	static final String BASE_PATH = "/api";
	static final String LOGIN_PATH = BASE_PATH + "/login";

	/**
	 * ログインAPIのテスト
	 */
	@Nested
	@TestInstance(PER_CLASS)
	class LoginTest extends AbstractRestControllerInitialization_IT {

		@ParameterizedTest
		@MethodSource
		void 正_ユーザがログイン(final UserRoleEnum userRole) throws Exception {
			// setup
			createLoginUser(userRole);

			// login request body
			final var requestBody = LoginRequest.builder() //
				.email(LOGIN_USER_EMAIL) //
				.password(LOGIN_USER_PASSWORD) //
				.build();

			// test
			final var request = postRequest("/api/login", requestBody);
			final var response = execute(request, HttpStatus.OK, AccessTokenResponse.class);

			// verify
			assertThat(response.getAccessToken()).isNotNull();
			assertThat(response.getTokenType()).isEqualTo("Bearer");
		}

		Stream<Arguments> 正_ユーザがログイン() {

			return Stream.of(
				// 管理者
				arguments(UserRoleEnum.ADMIN),
				// メンバー
				arguments(UserRoleEnum.MEMBER));
		}

		@Test
		void 異_存在しないユーザがログイン() throws Exception {
			// setup
			final var loginUser = UserSample.builder().build();

			// login request body
			final var requestBody = LoginRequest.builder() //
				.email(loginUser.getEmail()) //
				.password(loginUser.getPassword()) //
				.build();

			// test
			final var request = postRequest("/api/login", requestBody);
			execute(request, new NotFoundException(ErrorCode.NOT_FOUND_USER));
		}

		@Test
		void 異_パスワードが間違えている() throws Exception {
			// setup
			createLoginUser(UserRoleEnum.MEMBER);

			// login request body
			final var requestBody = LoginRequest.builder() //
				.email(LOGIN_USER_EMAIL) //
				.password(LOGIN_USER_PASSWORD + "dummy") //
				.build();

			// test
			final var request = postRequest("/api/login", requestBody);
			execute(request, new UnauthorizedException(ErrorCode.WRONG_PASSWORD));
		}

	}

}
