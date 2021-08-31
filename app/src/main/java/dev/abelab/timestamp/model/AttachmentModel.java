package dev.abelab.timestamp.model;

import java.util.UUID;

import lombok.*;

/**
 * 添付ファイルモデル
 */
@Value
@Builder
@RequiredArgsConstructor
public class AttachmentModel {

    /**
     * UUID
     */
    @Builder.Default
    String uuid = UUID.randomUUID().toString();

    /**
     * ファイル名
     */
    String name;

    /**
     * ファイルのバイナリ
     */
    byte[] bytes;

}
