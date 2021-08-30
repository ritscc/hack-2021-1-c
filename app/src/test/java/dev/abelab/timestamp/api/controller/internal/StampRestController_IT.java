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
import dev.abelab.timestamp.db.entity.StampSample;
import dev.abelab.timestamp.db.entity.StampAttachmentSample;
import dev.abelab.timestamp.enums.UserRoleEnum;
import dev.abelab.timestamp.repository.StampRepository;
import dev.abelab.timestamp.repository.StampAttachmentRepository;
import dev.abelab.timestamp.api.response.StampResponse;
import dev.abelab.timestamp.api.response.StampsResponse;
import dev.abelab.timestamp.exception.ErrorCode;
import dev.abelab.timestamp.exception.UnauthorizedException;

/**
 * StampRestController Integration Test
 */
public class StampRestController_IT extends AbstractRestController_IT {

	// API PATH
	static final String BASE_PATH = "/api/stamps";
	static final String GET_STAMPS_PATH = BASE_PATH;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	StampRepository stampRepository;

	@Autowired
	StampAttachmentRepository stampAttachmentRepository;

	/**
	 * スタンプ一覧取得APIのテスト
	 */
	@Nested
	@TestInstance(PER_CLASS)
	class GetStampsTest extends AbstractRestControllerInitialization_IT {

		@ParameterizedTest
		@MethodSource
		void 正_スタンプ一覧を取得(final UserRoleEnum userRole) throws Exception {
			// login user
			final var loginUser = createLoginUser(userRole);
			final var credentials = getLoginUserCredentials(loginUser);

			final var stamps = Arrays.asList( //
				StampSample.builder().userId(loginUser.getId()).build(), //
				StampSample.builder().userId(loginUser.getId()).build() //
			);
			stampRepository.bulkInsert(stamps);

			final var stampAttachments = Arrays.asList( //
				StampAttachmentSample.builder().stampId(stamps.get(0).getId()).build(), //
				StampAttachmentSample.builder().stampId(stamps.get(0).getId()).build(), //
				StampAttachmentSample.builder().stampId(stamps.get(0).getId()).build() //
			);
			stampAttachmentRepository.bulkInsert(stampAttachments);

			// test
			final var request = getRequest(GET_STAMPS_PATH);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			final var response = execute(request, HttpStatus.OK, StampsResponse.class);

			// verify
			assertThat(response.getStamps().size()).isEqualTo(stamps.size());
			assertThat(response.getStamps()) //
				.extracting(StampResponse::getId, StampResponse::getTitle, StampResponse::getDescription) //
				.containsExactlyInAnyOrderElementsOf(stamps.stream()
					.map(stamp -> tuple(stamp.getId(), stamp.getTitle(), stamp.getDescription())).collect(Collectors.toList()));
			assertThat(response.getStamps().get(0).getAttachments().size()).isEqualTo(3);
			assertThat(response.getStamps().get(1).getAttachments().size()).isEqualTo(0);
		}

		Stream<Arguments> 正_スタンプ一覧を取得() {
			return Stream.of(
				// 管理者
				arguments(UserRoleEnum.ADMIN),
				// メンバー
				arguments(UserRoleEnum.MEMBER));
		}

		@Test
		void 異_無効な認証ヘッダ() throws Exception {
			// test
			final var request = getRequest(GET_STAMPS_PATH);
			request.header(HttpHeaders.AUTHORIZATION, "");
			execute(request, new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN));
		}

	}

}
