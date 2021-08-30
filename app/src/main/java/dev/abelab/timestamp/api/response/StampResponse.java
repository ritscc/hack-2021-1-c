package dev.abelab.timestamp.api.response;

import java.util.List;

import lombok.*;
import dev.abelab.timestamp.model.StampAttachmentSubmitModel;

/**
 * スタンプ情報レスポンス
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StampResponse {

    /**
     * スタンプID
     */
    Integer id;

    /**
     * タイトル
     */
    String title;

    /**
     * 説明文
     */
    String description;

    /**
     * ユーザID
     */
    Integer userId;

    /**
     * 添付ファイル一覧
     */
    List<StampAttachmentSubmitModel> attachments;

}
