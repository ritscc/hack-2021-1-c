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

    /*
     * UUID
     */
    @Builder.Default
    UUID uuid = UUID.randomUUID();

    /*
     * ファイルのバイナリ
     */
    byte[] bytes;

}
