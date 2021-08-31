package dev.abelab.timestamp.logic;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.net.util.Base64;
import org.springframework.stereotype.Component;

import lombok.*;
import dev.abelab.timestamp.db.entity.User;
import dev.abelab.timestamp.db.entity.StampAttachment;
import dev.abelab.timestamp.model.AttachmentModel;
import dev.abelab.timestamp.model.StampAttachmentSubmitModel;
import dev.abelab.timestamp.enums.UserRoleEnum;
import dev.abelab.timestamp.repository.StampRepository;
import dev.abelab.timestamp.repository.StampAttachmentRepository;
import dev.abelab.timestamp.exception.ErrorCode;
import dev.abelab.timestamp.exception.ForbiddenException;

@RequiredArgsConstructor
@Component
public class StampLogic {

    private final StampRepository stampRepository;

    private final StampAttachmentRepository stampAttachmentRepository;

    /**
     * 添付ファイルをアップロード
     *
     * @param stampId     スタンプID
     *
     * @param attachments 添付ファイル一覧
     */
    public void uploadAttachments(final int stampId, final List<StampAttachmentSubmitModel> attachments) {
        // バイナリにデコード
        final var attachmentModels = attachments.stream().map(attachment -> AttachmentModel.builder() //
            .name(attachment.getName()) //
            .bytes(Base64.decodeBase64(attachment.getContent())) //
            .build()).collect(Collectors.toList());

        // DBに保存
        final var stampAttachments = attachmentModels.stream().map(attachment -> StampAttachment.builder() //
            .stampId(stampId) //
            .name(attachment.getName()) //
            .uuid(attachment.getUuid()) //
            .build()).collect(Collectors.toList());
        this.stampAttachmentRepository.bulkInsert(stampAttachments);

        // TODO: Cloud Storageにアップロード
    }

    /**
     * スタンプの編集権限をチェック
     *
     * @param loginUser ログインユーザ
     *
     * @param stampId   スタンプID
     */
    public void checkEditPermission(final User loginUser, final int stampId) {
        final var stamp = this.stampRepository.selectById(stampId);

        if (loginUser.getRoleId() != UserRoleEnum.ADMIN.getId() && !stamp.getUserId().equals(loginUser.getId())) {
            throw new ForbiddenException(ErrorCode.USER_HAS_NO_PERMISSION);
        }
    }

}
