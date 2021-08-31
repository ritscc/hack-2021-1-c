package dev.abelab.timestamp.service;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;

import lombok.*;
import dev.abelab.timestamp.db.entity.Stamp;
import dev.abelab.timestamp.api.request.StampCreateRequest;
import dev.abelab.timestamp.api.response.StampResponse;
import dev.abelab.timestamp.api.response.StampsResponse;
import dev.abelab.timestamp.model.FileDownloadModel;
import dev.abelab.timestamp.repository.StampRepository;
import dev.abelab.timestamp.repository.StampAttachmentRepository;
import dev.abelab.timestamp.logic.UserLogic;
import dev.abelab.timestamp.logic.StampLogic;

@RequiredArgsConstructor
@Service
public class StampService {

    private final ModelMapper modelMapper;

    private final StampRepository stampRepository;

    private final StampAttachmentRepository stampAttachmentRepository;

    private final UserLogic userLogic;

    private final StampLogic stampLogic;

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

    /**
     * スタンプを作成
     *
     * @param credentials 資格情報
     *
     * @param requestBody スタンプ作成リクエスト
     */
    @Transactional
    public void createStamp(final String credentials, final StampCreateRequest requestBody) {
        // ログインユーザを取得
        final var loginUser = this.userLogic.getLoginUser(credentials);

        // スタンプを作成
        final var stamp = this.modelMapper.map(requestBody, Stamp.class);
        stamp.setUserId(loginUser.getId());
        this.stampRepository.insert(stamp);

        // 添付ファイルをアップロード
        this.stampLogic.uploadAttachments(stamp.getId(), requestBody.getAttachments());
    }

    /**
     * スタンプを削除
     *
     * @param credentials 資格情報
     *
     * @param stampId     スタンプID
     */
    @Transactional
    public void deleteStamp(final String credentials, final int stampId) {
        // ログインユーザを取得
        final var loginUser = this.userLogic.getLoginUser(credentials);

        // 削除権限があるかチェック
        this.stampLogic.checkEditPermission(loginUser, stampId);

        // スタンプを削除
        this.stampRepository.deleteById(stampId);
    }

    /**
     * 添付ファイルを取得
     *
     * @param credentials  資格情報
     *
     * @param attachmentId 添付ファイルID
     *
     * @return 添付ファイル
     */
    public FileDownloadModel getStampAttachment(final String credentials, final int attachmentId) {
        // ログインユーザを取得
        this.userLogic.getLoginUser(credentials);

        // 添付ファイルを取得
        final var attachment = this.stampAttachmentRepository.selectById(attachmentId);
        return this.modelMapper.map(attachment, FileDownloadModel.class);
    }

}
