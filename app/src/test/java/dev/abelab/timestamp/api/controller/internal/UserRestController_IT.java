package dev.abelab.timestamp.api.controller.internal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.*;
import static org.junit.jupiter.params.provider.Arguments.*;

import java.util.Arrays;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;

import dev.abelab.timestamp.api.controller.AbstractRestController_IT;
import dev.abelab.timestamp.db.entity.User;
import dev.abelab.timestamp.db.entity.UserSample;
import dev.abelab.timestamp.enums.UserRoleEnum;
import dev.abelab.timestamp.repository.UserRepository;
import dev.abelab.timestamp.api.response.UserResponse;
import dev.abelab.timestamp.api.response.UsersResponse;
import dev.abelab.timestamp.exception.ErrorCode;
import dev.abelab.timestamp.exception.BaseException;
import dev.abelab.timestamp.exception.NotFoundException;
import dev.abelab.timestamp.exception.ConflictException;
import dev.abelab.timestamp.exception.ForbiddenException;
import dev.abelab.timestamp.exception.BadRequestException;
import dev.abelab.timestamp.exception.UnauthorizedException;

/**
 * UserRestController Integration Test
 */
public class UserRestController_IT extends AbstractRestController_IT {

	// API PATH
	static final String BASE_PATH = "/api/users";
	static final String GET_USERS_PATH = BASE_PATH;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	UserRepository userRepository;

	/**
	 * ユーザ一覧取得APIのテスト
	 */
	@Nested
	@TestInstance(PER_CLASS)
	class GetUsersTest extends AbstractRestControllerInitialization_IT {

		@Test
		void 正_管理者がユーザ一覧を取得() throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.ADMIN);
			final var credentials = getLoginUserCredentials(loginUser);

			final var user1 = UserSample.builder().email("email1").build();
			final var user2 = UserSample.builder().email("email2").build();
			userRepository.insert(user1);
			userRepository.insert(user2);

			// test
			final var request = getRequest(GET_USERS_PATH);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			final var response = execute(request, HttpStatus.OK, UsersResponse.class);

			// verify
			final var users = Arrays.asList(loginUser, user1, user2);
			assertThat(response.getUsers().size()).isEqualTo(users.size());
			assertThat(response.getUsers()) //
				.extracting(UserResponse::getId, UserResponse::getEmail, UserResponse::getFirstName, UserResponse::getLastName,
					UserResponse::getRoleId) //
				.containsExactlyInAnyOrderElementsOf(users.stream()
					.map(user -> tuple(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getRoleId()))
					.collect(Collectors.toList()));
		}

		@Test
		void 異_管理者以外はユーザ一覧を取得不可() throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.MEMBER);
			final var credentials = getLoginUserCredentials(loginUser);

			final var user1 = UserSample.builder().email("email1").build();
			final var user2 = UserSample.builder().email("email2").build();
			userRepository.insert(user1);
			userRepository.insert(user2);

			// test
			final var request = getRequest(GET_USERS_PATH);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, new ForbiddenException(ErrorCode.USER_HAS_NO_PERMISSION));
		}

		@Test
		void 異_無効な認証ヘッダ() throws Exception {
			// test
			final var request = getRequest(GET_USERS_PATH);
			request.header(HttpHeaders.AUTHORIZATION, "");
			execute(request, new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN));
		}

	}

}
