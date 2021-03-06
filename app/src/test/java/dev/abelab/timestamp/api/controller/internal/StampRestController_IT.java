package dev.abelab.timestamp.api.controller.internal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
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
import org.apache.commons.net.util.Base64;
import org.modelmapper.ModelMapper;

import dev.abelab.timestamp.api.controller.AbstractRestController_IT;
import dev.abelab.timestamp.db.entity.UserSample;
import dev.abelab.timestamp.db.entity.Stamp;
import dev.abelab.timestamp.db.entity.StampAttachment;
import dev.abelab.timestamp.db.entity.StampSample;
import dev.abelab.timestamp.db.entity.StampAttachmentSample;
import dev.abelab.timestamp.model.StampAttachmentSubmitModel;
import dev.abelab.timestamp.enums.UserRoleEnum;
import dev.abelab.timestamp.repository.UserRepository;
import dev.abelab.timestamp.repository.StampRepository;
import dev.abelab.timestamp.repository.StampAttachmentRepository;
import dev.abelab.timestamp.api.request.StampCreateRequest;
import dev.abelab.timestamp.api.response.StampResponse;
import dev.abelab.timestamp.api.response.StampsResponse;
import dev.abelab.timestamp.exception.ErrorCode;
import dev.abelab.timestamp.exception.BaseException;
import dev.abelab.timestamp.exception.NotFoundException;
import dev.abelab.timestamp.exception.ForbiddenException;
import dev.abelab.timestamp.exception.UnauthorizedException;

/**
 * StampRestController Integration Test
 */
public class StampRestController_IT extends AbstractRestController_IT {

	// API PATH
	static final String BASE_PATH = "/api/stamps";
	static final String GET_STAMPS_PATH = BASE_PATH;
	static final String CREATE_STAMP_PATH = BASE_PATH;
	static final String DELETE_STAMP_PATH = BASE_PATH + "/%d";
	static final String GET_STAMP_ATTACHMENT_PATH = BASE_PATH + "/attachments/%d";

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	UserRepository userRepository;

	@Autowired
	StampRepository stampRepository;

	@Autowired
	StampAttachmentRepository stampAttachmentRepository;

	/**
	 * ????????????????????????API????????????
	 */
	@Nested
	@TestInstance(PER_CLASS)
	class GetStampsTest extends AbstractRestControllerInitialization_IT {

		@ParameterizedTest
		@MethodSource
		void ???_???????????????????????????(final UserRoleEnum roleId) throws Exception {
			// login user
			final var loginUser = createLoginUser(roleId);
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

		Stream<Arguments> ???_???????????????????????????() {
			return Stream.of(
				// ?????????
				arguments(UserRoleEnum.ADMIN),
				// ????????????
				arguments(UserRoleEnum.MEMBER));
		}

		@Test
		void ???_????????????????????????() throws Exception {
			// test
			final var request = getRequest(GET_STAMPS_PATH);
			request.header(HttpHeaders.AUTHORIZATION, "");
			execute(request, new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN));
		}

	}

	/**
	 * ??????????????????API????????????
	 */
	@Nested
	@TestInstance(PER_CLASS)
	class CreateStampTest extends AbstractRestControllerInitialization_IT {

		@ParameterizedTest
		@MethodSource
		void ???_?????????????????????(final UserRoleEnum roleId) throws Exception {
			// login user
			final var loginUser = createLoginUser(roleId);
			final var credentials = getLoginUserCredentials(loginUser);

			final var stamp = StampSample.builder().userId(loginUser.getId()).build();
			final var stampAttachments = Arrays.asList( //
				StampAttachmentSample.builder().build(), //
				StampAttachmentSample.builder().build(), //
				StampAttachmentSample.builder().build() //
			);

			final var requestBody = modelMapper.map(stamp, StampCreateRequest.class);
			requestBody.setAttachments(stampAttachments.stream() //
				.map(attachment -> {
					final var attachmentSubmitmodel = modelMapper.map(attachment, StampAttachmentSubmitModel.class);
					attachmentSubmitmodel.setContent(Base64.encodeBase64String(attachment.getContent()));
					return attachmentSubmitmodel;
				}).collect(Collectors.toList()));

			// test
			final var request = postRequest(CREATE_STAMP_PATH, requestBody);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, HttpStatus.CREATED, StampsResponse.class);

			// verify
			final var createdStamp = stampRepository.selectAllWithAttachments().get(0);
			assertThat(createdStamp) //
				.extracting(Stamp::getTitle, Stamp::getDescription, Stamp::getUserId) //
				.containsExactly(stamp.getTitle(), stamp.getDescription(), loginUser.getId());
			assertThat(createdStamp.getAttachments()) //
				.extracting(StampAttachment::getStampId, StampAttachment::getName, StampAttachment::getContent) //
				.containsExactlyInAnyOrderElementsOf(stampAttachments.stream() //
					.map(attachment -> tuple(createdStamp.getId(), attachment.getName(), attachment.getContent())) //
					.collect(Collectors.toList()));
			assertThat(createdStamp.getCreatedAt()).isInSameMinuteAs(stamp.getCreatedAt());
		}

		Stream<Arguments> ???_?????????????????????() {
			return Stream.of(
				// ?????????
				arguments(UserRoleEnum.ADMIN),
				// ????????????
				arguments(UserRoleEnum.MEMBER));
		}

		@Test
		void ???_????????????????????????() throws Exception {
			// setup
			final var stamp = StampSample.builder().build();
			final var stampAttachments = Arrays.asList( //
				StampAttachmentSample.builder().build(), //
				StampAttachmentSample.builder().build(), //
				StampAttachmentSample.builder().build() //
			);

			final var requestBody = modelMapper.map(stamp, StampCreateRequest.class);
			requestBody.setAttachments(stampAttachments.stream() //
				.map(attachment -> modelMapper.map(attachment, StampAttachmentSubmitModel.class)) //
				.collect(Collectors.toList()));

			// test
			final var request = postRequest(CREATE_STAMP_PATH, requestBody);
			request.header(HttpHeaders.AUTHORIZATION, "");
			execute(request, new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN));
		}

	}

	/**
	 * ??????????????????API????????????
	 */
	@Nested
	@TestInstance(PER_CLASS)
	class DeleteStampTest extends AbstractRestControllerInitialization_IT {

		@ParameterizedTest
		@MethodSource
		void ???_??????????????????????????????(final UserRoleEnum roleId) throws Exception {
			// setup
			final var loginUser = createLoginUser(roleId);
			final var credentials = getLoginUserCredentials(loginUser);

			final var stamp = StampSample.builder().userId(loginUser.getId()).build();
			stampRepository.insert(stamp);

			// test
			final var request = deleteRequest(String.format(DELETE_STAMP_PATH, stamp.getId()));
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, HttpStatus.OK);

			// verify
			final var occurredException = assertThrows(NotFoundException.class, () -> stampRepository.selectById(stamp.getId()));
			assertThat(occurredException.getErrorCode()).isEqualTo(ErrorCode.NOT_FOUND_STAMP);

		}

		Stream<Arguments> ???_??????????????????????????????() {
			return Stream.of(
				// ?????????
				arguments(UserRoleEnum.ADMIN),
				// ????????????
				arguments(UserRoleEnum.MEMBER));
		}

		@ParameterizedTest
		@MethodSource
		void ??????????????????????????????(final UserRoleEnum roleId, final BaseException exception) throws Exception {
			// setup
			final var loginUser = createLoginUser(roleId);
			final var credentials = getLoginUserCredentials(loginUser);

			final var stampOwner = UserSample.builder().build();
			userRepository.insert(stampOwner);

			final var stamp = StampSample.builder().userId(stampOwner.getId()).build();
			stampRepository.insert(stamp);

			// test
			final var request = deleteRequest(String.format(DELETE_STAMP_PATH, stamp.getId()));
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			if (exception == null) {
				execute(request, HttpStatus.OK);
			} else {
				execute(request, exception);
			}
		}

		Stream<Arguments> ??????????????????????????????() {
			return Stream.of(
				// ?????????
				arguments(UserRoleEnum.ADMIN, null),
				// ????????????
				arguments(UserRoleEnum.MEMBER, new ForbiddenException(ErrorCode.USER_HAS_NO_PERMISSION)));
		}

		@Test
		void ???_??????????????????????????????????????????() throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.MEMBER);
			final var credentials = getLoginUserCredentials(loginUser);

			// test
			final var request = deleteRequest(String.format(DELETE_STAMP_PATH, SAMPLE_INT));
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, new NotFoundException(ErrorCode.NOT_FOUND_STAMP));
		}

		@Test
		void ???_????????????????????????() throws Exception {
			// test
			final var request = deleteRequest(String.format(DELETE_STAMP_PATH, SAMPLE_INT));
			request.header(HttpHeaders.AUTHORIZATION, "");
			execute(request, new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN));
		}

	}

	/**
	 * ????????????????????????????????????API????????????
	 */
	@Nested
	@TestInstance(PER_CLASS)
	class DownloadStampAttachmentTest extends AbstractRestControllerInitialization_IT {

		@ParameterizedTest
		@MethodSource
		void ???_???????????????????????????????????????(final UserRoleEnum roleId) throws Exception {
			// login user
			final var loginUser = createLoginUser(roleId);
			final var credentials = getLoginUserCredentials(loginUser);

			final var stamp = StampSample.builder().userId(loginUser.getId()).build();
			stampRepository.insert(stamp);

			final var stampAttachment = StampAttachmentSample.builder().stampId(stamp.getId()).build();
			stampAttachmentRepository.insert(stampAttachment);

			// test
			final var request = getRequest(String.format(GET_STAMP_ATTACHMENT_PATH, stampAttachment.getId()));
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			final var response = execute(request, HttpStatus.OK);

			// verify
			assertThat(response.getResponse().getContentLength()).isEqualTo(stampAttachment.getContent().length);
		}

		Stream<Arguments> ???_???????????????????????????????????????() {
			return Stream.of(
				// ?????????
				arguments(UserRoleEnum.ADMIN),
				// ????????????
				arguments(UserRoleEnum.MEMBER));
		}

		@Test
		void ???_????????????????????????() throws Exception {
			// test
			final var request = getRequest(String.format(GET_STAMP_ATTACHMENT_PATH, SAMPLE_INT));
			request.header(HttpHeaders.AUTHORIZATION, "");
			execute(request, new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN));
		}

	}

}
