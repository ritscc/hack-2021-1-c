package dev.abelab.timestamp.model;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.*;

/**
 * スタンプの添付ファイルの提出モデル
 */
@Value
@Builder
@RequiredArgsConstructor
public class StampAttachmentSubmitModel {

    /**
     * UUID
     */
    @NotNull
    String uuid;

    /**
     * ファイル内容 (Base64)
     */
    @NotNull
    String content;

}
