package dev.abelab.timestamp.model;

import lombok.*;

/**
 * ファイルのダウンロードモデル
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileDownloadModel {

    /**
     * ファイル名
     */
    String name;

    /**
     * ファイル内容
     */
    byte[] content;

}
