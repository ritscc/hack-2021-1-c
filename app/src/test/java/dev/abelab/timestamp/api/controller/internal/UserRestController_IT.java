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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.modelmapper.ModelMapper;

import dev.abelab.timestamp.api.controller.AbstractRestController_IT;
import dev.abelab.timestamp.db.entity.User;
import dev.abelab.timestamp.db.entity.UserSample;
import dev.abelab.timestamp.enums.UserRoleEnum;
import dev.abelab.timestamp.repository.UserRepository;
import dev.abelab.timestamp.api.request.UserCreateRequest;
import dev.abelab.timestamp.api.request.UserUpdateRequest;
import dev.abelab.timestamp.api.request.LoginUserUpdateRequest;
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
	static final String CREATE_USER_PATH = BASE_PATH;
	static final String UPDATE_USER_PATH = BASE_PATH + "/%d";
	static final String DELETE_USER_PATH = BASE_PATH + "/%d";
	static final String GET_LOGIN_USER_PATH = BASE_PATH + "/me";
	static final String UPDATE_LOGIN_USER_PATH = BASE_PATH + "/me";

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;

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

	/**
	 * ユーザ作成APIのテスト
	 */
	@Nested
	@TestInstance(PER_CLASS)
	class CreateUserTest extends AbstractRestControllerInitialization_IT {

		@Test
		void 正_管理者がユーザを作成() throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.ADMIN);
			final var credentials = getLoginUserCredentials(loginUser);

			final var user = UserSample.builder().password(LOGIN_USER_PASSWORD).build();
			final var requestBody = modelMapper.map(user, UserCreateRequest.class);

			// test
			final var request = postRequest(CREATE_USER_PATH, requestBody);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, HttpStatus.CREATED);

			// verify
			final var createdUser = userRepository.selectByEmail(user.getEmail());
			assertThat(createdUser) //
				.extracting(User::getEmail, User::getFirstName, User::getLastName, User::getRoleId) //
				.containsExactly( //
					user.getEmail(), user.getFirstName(), user.getLastName(), user.getRoleId());
			assertThat(passwordEncoder.matches(user.getPassword(), createdUser.getPassword())).isTrue();
		}

		@Test
		void 異_管理者以外はユーザ作成不可() throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.MEMBER);
			final var credentials = getLoginUserCredentials(loginUser);

			final var user = UserSample.builder().build();
			final var requestBody = modelMapper.map(user, UserCreateRequest.class);

			// test
			final var request = postRequest(CREATE_USER_PATH, requestBody);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, new ForbiddenException(ErrorCode.USER_HAS_NO_PERMISSION));
		}

		@Test
		void 異_メールアドレスが既に存在する() throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.ADMIN);
			final var credentials = getLoginUserCredentials(loginUser);

			final var user = UserSample.builder().password(LOGIN_USER_PASSWORD).build();
			final var requestBody = modelMapper.map(user, UserCreateRequest.class);

			// test
			final var request = postRequest(CREATE_USER_PATH, requestBody);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, HttpStatus.CREATED);

			// verify
			execute(request, new ConflictException(ErrorCode.CONFLICT_EMAIL));
		}

		@ParameterizedTest
		@MethodSource
		void 異_無効なユーザロール(final int roleId) throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.ADMIN);
			final var credentials = getLoginUserCredentials(loginUser);

			final var user = UserSample.builder().roleId(roleId).password(LOGIN_USER_PASSWORD).build();
			final var requestBody = modelMapper.map(user, UserCreateRequest.class);

			// test
			final var request = postRequest(CREATE_USER_PATH, requestBody);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, new NotFoundException(ErrorCode.NOT_FOUND_USER_ROLE));
		}

		Stream<Arguments> 異_無効なユーザロール() {
			return Stream.of( //
				arguments(0), //
				arguments(UserRoleEnum.values().length + 1) //
			);
		}

		@ParameterizedTest
		@MethodSource
		void パスワードが有効かどうか(final String password, final BaseException exception) throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.ADMIN);
			final var credentials = getLoginUserCredentials(loginUser);

			final var user = UserSample.builder().password(password).build();
			final var requestBody = modelMapper.map(user, UserCreateRequest.class);

			// test
			final var request = postRequest(CREATE_USER_PATH, requestBody);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			if (exception == null) {
				execute(request, HttpStatus.CREATED);
			} else {
				execute(request, exception);
			}
		}

		Stream<Arguments> パスワードが有効かどうか() {
			return Stream.of( //
				// 有効
				arguments("f4BabxEr", null), //
				arguments("f4BabxEr4gNsjdtRpH9Pfs6Atth9bqdA", null), //
				// 無効：8文字以下
				arguments("f4BabxE", new BadRequestException(ErrorCode.INVALID_PASSWORD_SIZE)), //
				// 無効：33文字以上
				arguments("f4BabxEr4gNsjdtRpH9Pfs6Atth9bqdAN", new BadRequestException(ErrorCode.INVALID_PASSWORD_SIZE)), //
				// 無効：大文字を含まない
				arguments("f4babxer", new BadRequestException(ErrorCode.TOO_SIMPLE_PASSWORD)), //
				// 無効：小文字を含まない
				arguments("F4BABXER", new BadRequestException(ErrorCode.TOO_SIMPLE_PASSWORD)), //
				// 無効：数字を含まない
				arguments("fxbabxEr", new BadRequestException(ErrorCode.TOO_SIMPLE_PASSWORD)) //
			);
		}

		@Test
		void 異_無効な認証ヘッダ() throws Exception {
			// setup
			final var user = UserSample.builder().build();
			final var requestBody = modelMapper.map(user, UserCreateRequest.class);

			// test
			final var request = postRequest(CREATE_USER_PATH, requestBody);
			request.header(HttpHeaders.AUTHORIZATION, "");
			execute(request, new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN));
		}

	}

	/**
	 * ユーザ更新APIのテスト
	 */
	@Nested
	@TestInstance(PER_CLASS)
	class UpdateUserTest extends AbstractRestControllerInitialization_IT {

		@Test
		void 正_管理者がユーザを更新() throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.ADMIN);
			final var credentials = getLoginUserCredentials(loginUser);

			final var user = UserSample.builder().roleId(UserRoleEnum.ADMIN.getId()).password(LOGIN_USER_PASSWORD).build();
			userRepository.insert(user);

			user.setEmail(user.getEmail() + "xxx");
			user.setFirstName(user.getFirstName() + "xxx");
			user.setLastName(user.getLastName() + "xxx");
			user.setRoleId(UserRoleEnum.MEMBER.getId());
			final var requestBody = modelMapper.map(user, UserUpdateRequest.class);

			// test
			final var request = putRequest(String.format(UPDATE_USER_PATH, user.getId()), requestBody);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, HttpStatus.OK);

			// verify
			final var updatedUser = userRepository.selectByEmail(user.getEmail());
			assertThat(updatedUser) //
				.extracting(User::getEmail, User::getFirstName, User::getLastName, User::getRoleId) //
				.containsExactly( //
					user.getFirstName(), user.getLastName(), user.getEmail(), user.getRoleId());
		}

		@Test
		void 異_管理者以外はユーザを更新不可() throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.MEMBER);
			final var credentials = getLoginUserCredentials(loginUser);

			final var user = UserSample.builder().roleId(UserRoleEnum.ADMIN.getId()).password(LOGIN_USER_PASSWORD).build();
			userRepository.insert(user);

			user.setEmail(user.getEmail() + "xxx");
			user.setFirstName(user.getFirstName() + "xxx");
			user.setLastName(user.getLastName() + "xxx");
			user.setRoleId(UserRoleEnum.MEMBER.getId());
			final var requestBody = modelMapper.map(user, UserUpdateRequest.class);

			// test
			final var request = putRequest(String.format(UPDATE_USER_PATH, user.getId()), requestBody);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, new ForbiddenException(ErrorCode.USER_HAS_NO_PERMISSION));
		}

		@ParameterizedTest
		@MethodSource
		void 正_ユーザロールを更新(final UserRoleEnum beforeUserRole, final UserRoleEnum afterUserRole) throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.ADMIN);
			final var credentials = getLoginUserCredentials(loginUser);

			final var user = UserSample.builder().roleId(beforeUserRole.getId()).password(LOGIN_USER_PASSWORD).build();
			userRepository.insert(user);

			user.setRoleId(afterUserRole.getId());
			final var requestBody = modelMapper.map(user, UserUpdateRequest.class);

			// test
			final var request = putRequest(String.format(UPDATE_USER_PATH, user.getId()), requestBody);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, HttpStatus.OK);

			// verify
			final var updatedUser = userRepository.selectByEmail(user.getEmail());
			assertThat(updatedUser.getRoleId()).isEqualTo(afterUserRole.getId());
		}

		Stream<Arguments> 正_ユーザロールを更新() {
			return Stream.of( //
				// 管理者 -> メンバー
				arguments(UserRoleEnum.ADMIN, UserRoleEnum.MEMBER), //
				// メンバー -> 管理者
				arguments(UserRoleEnum.MEMBER, UserRoleEnum.ADMIN) //
			);
		}

		@ParameterizedTest
		@MethodSource
		void 異_無効なユーザロール(final int roleId) throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.ADMIN);
			final var credentials = getLoginUserCredentials(loginUser);

			final var user = UserSample.builder().password(LOGIN_USER_PASSWORD).build();
			userRepository.insert(user);

			user.setRoleId(roleId);
			final var requestBody = modelMapper.map(user, UserCreateRequest.class);

			// test
			final var request = putRequest(String.format(UPDATE_USER_PATH, user.getId()), requestBody);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, new NotFoundException(ErrorCode.NOT_FOUND_USER_ROLE));
		}

		Stream<Arguments> 異_無効なユーザロール() {
			return Stream.of( //
				arguments(0), //
				arguments(UserRoleEnum.values().length + 1) //
			);
		}

		@Test
		void 異_更新後のメールアドレスが既に存在する() throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.ADMIN);
			final var credentials = getLoginUserCredentials(loginUser);

			final var user = UserSample.builder().password(LOGIN_USER_PASSWORD).build();
			userRepository.insert(user);

			user.setEmail(loginUser.getEmail());
			final var requestBody = modelMapper.map(user, UserCreateRequest.class);

			// test
			final var request = postRequest(CREATE_USER_PATH, requestBody);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, new ConflictException(ErrorCode.CONFLICT_EMAIL));
		}

		@Test
		void 異_更新対象ユーザが存在しない() throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.ADMIN);
			final var credentials = getLoginUserCredentials(loginUser);

			final var user = UserSample.builder().password(LOGIN_USER_PASSWORD).build();
			final var requestBody = modelMapper.map(user, UserUpdateRequest.class);

			// test
			final var request = putRequest(String.format(UPDATE_USER_PATH, SAMPLE_INT), requestBody);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, new NotFoundException(ErrorCode.NOT_FOUND_USER));
		}

		@Test
		void 異_無効な認証ヘッダ() throws Exception {
			// setup
			final var user = UserSample.builder().password(LOGIN_USER_PASSWORD).build();
			final var requestBody = modelMapper.map(user, UserUpdateRequest.class);

			// test
			final var request = putRequest(String.format(UPDATE_USER_PATH, SAMPLE_INT), requestBody);
			request.header(HttpHeaders.AUTHORIZATION, "");
			execute(request, new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN));

		}

	}

	/**
	 * ユーザ削除APIのテスト
	 */
	@Nested
	@TestInstance(PER_CLASS)
	class DeleteUserTest extends AbstractRestControllerInitialization_IT {

		@Test
		void 正_管理者がユーザを削除() throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.ADMIN);
			final var credentials = getLoginUserCredentials(loginUser);

			final var user = UserSample.builder().password(LOGIN_USER_PASSWORD).build();
			userRepository.insert(user);

			// test
			final var request = deleteRequest(String.format(DELETE_USER_PATH, user.getId()));
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, HttpStatus.OK);
		}

		@Test
		void 異_管理者以外はユーザを削除不可() throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.MEMBER);
			final var credentials = getLoginUserCredentials(loginUser);

			final var user = UserSample.builder().password(LOGIN_USER_PASSWORD).build();
			userRepository.insert(user);

			// test
			final var request = deleteRequest(String.format(DELETE_USER_PATH, user.getId()));
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, new ForbiddenException(ErrorCode.USER_HAS_NO_PERMISSION));
		}

		@Test
		void 異_削除対象ユーザが存在しない() throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.ADMIN);
			final var credentials = getLoginUserCredentials(loginUser);

			// test
			final var request = deleteRequest(String.format(DELETE_USER_PATH, SAMPLE_INT));
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, new NotFoundException(ErrorCode.NOT_FOUND_USER));
		}

		@Test
		void 異_無効な認証ヘッダ() throws Exception {
			// test
			final var request = deleteRequest(String.format(DELETE_USER_PATH, SAMPLE_INT));
			request.header(HttpHeaders.AUTHORIZATION, "");
			execute(request, new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN));
		}

	}

	/**
	 * ログインユーザ詳細取得APIのテスト
	 */
	@Nested
	@TestInstance(PER_CLASS)
	class GetLoginUserTest extends AbstractRestControllerInitialization_IT {

		@ParameterizedTest
		@MethodSource
		void 正_ログインユーザの詳細を取得(final UserRoleEnum userRole) throws Exception {
			// setup
			final var loginUser = createLoginUser(userRole);
			final var credentials = getLoginUserCredentials(loginUser);

			// test
			final var request = getRequest(GET_LOGIN_USER_PATH);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			final var response = execute(request, HttpStatus.OK, UserResponse.class);

			// verify
			assertThat(response) //
				.extracting(UserResponse::getEmail, UserResponse::getFirstName, UserResponse::getLastName, UserResponse::getRoleId) //
				.containsExactly( //
					response.getEmail(), response.getFirstName(), response.getLastName(), response.getRoleId());
		}

		Stream<Arguments> 正_ログインユーザの詳細を取得() {
			return Stream.of(
				// 管理者
				arguments(UserRoleEnum.ADMIN),
				// メンバー
				arguments(UserRoleEnum.MEMBER));
		}

		@Test
		void 異_無効な認証ヘッダ() throws Exception {
			// test
			final var request = getRequest(GET_LOGIN_USER_PATH);
			request.header(HttpHeaders.AUTHORIZATION, "");
			execute(request, new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN));
		}

	}

	/**
	 * ログインユーザ更新APIのテスト
	 */
	@Nested
	@TestInstance(PER_CLASS)
	class UpdateLoginUserTest extends AbstractRestControllerInitialization_IT {

		@ParameterizedTest
		@MethodSource
		void 正_ログインユーザを更新(final UserRoleEnum userRole) throws Exception {
			// setup
			final var loginUser = createLoginUser(userRole);
			final var credentials = getLoginUserCredentials(loginUser);

			final var user = UserSample.builder().password(LOGIN_USER_PASSWORD).build();
			userRepository.insert(user);

			user.setEmail(user.getEmail() + "xxx");
			user.setFirstName(user.getFirstName() + "xxx");
			user.setLastName(user.getLastName() + "xxx");
			final var requestBody = modelMapper.map(user, LoginUserUpdateRequest.class);

			// test
			final var request = putRequest(UPDATE_LOGIN_USER_PATH, requestBody);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, HttpStatus.OK);

			// verify
			final var updatedUser = userRepository.selectByEmail(requestBody.getEmail());
			assertThat(updatedUser) //
				.extracting(User::getEmail, User::getFirstName, User::getLastName) //
				.containsExactly(user.getEmail(), user.getFirstName(), user.getLastName());
		}

		Stream<Arguments> 正_ログインユーザを更新() {
			return Stream.of(
				// 管理者
				arguments(UserRoleEnum.ADMIN),
				// メンバー
				arguments(UserRoleEnum.MEMBER));
		}

		@Test
		void 異_無効な認証ヘッダ() throws Exception {
			// setup
			final var user = UserSample.builder().password(LOGIN_USER_PASSWORD).build();
			final var requestBody = modelMapper.map(user, LoginUserUpdateRequest.class);

			// test
			final var request = putRequest(UPDATE_LOGIN_USER_PATH, requestBody);
			request.header(HttpHeaders.AUTHORIZATION, "");
			execute(request, new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN));
		}

	}

}
