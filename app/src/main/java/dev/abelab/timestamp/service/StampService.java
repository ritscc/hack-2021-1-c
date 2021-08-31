package dev.abelab.timestamp.service;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;

import lombok.*;
import dev.abelab.timestamp.db.entity.User;
import dev.abelab.timestamp.api.request.UserCreateRequest;
import dev.abelab.timestamp.api.request.UserUpdateRequest;
import dev.abelab.timestamp.api.request.LoginUserUpdateRequest;
import dev.abelab.timestamp.api.request.LoginUserPasswordUpdateRequest;
import dev.abelab.timestamp.api.response.StampResponse;
import dev.abelab.timestamp.api.response.StampsResponse;
import dev.abelab.timestamp.model.StampAttachmentModel;
import dev.abelab.timestamp.repository.StampRepository;
import dev.abelab.timestamp.logic.UserLogic;
import dev.abelab.timestamp.util.AuthUtil;
import dev.abelab.timestamp.util.UserRoleUtil;

@RequiredArgsConstructor
@Service
public class StampService {

    private final ModelMapper modelMapper;

    private final StampRepository stampRepository;

    private final UserLogic userLogic;

    /**
     * スタンプ一覧を取得
     *
     * @param credentials 資格情報
     *
     * @return スタンプ一覧レスポンス
     */
    @Transactional
    public StampsResponse getStamps(final String credentials) {
        // ログインユーザを取得
        this.userLogic.getLoginUser(credentials);

        // スタンプ一覧の取得
        final var stamps = this.stampRepository.selectAllWithAttachments();
        final var stampResponses = stamps.stream() //
            .map(stamp -> this.modelMapper.map(stamp, StampResponse.class)) //
            .collect(Collectors.toList());

        return new StampsResponse(stampResponses);
    }

}
