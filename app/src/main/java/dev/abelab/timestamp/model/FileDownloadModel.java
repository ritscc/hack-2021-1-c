package dev.abelab.timestamp.model;

import lombok.*;

/**
 * ファイルのダウンロードモデル
 */
@Value
@Builder
@RequiredArgsConstructor
public class FileDownloadModel {

    /**
     * ファイル名
     */
    String name;

    /**
     * ファイルタイプ
     */
    String type;

    /**
     * ファイル内容
     */
    byte[] content;

}
