package dev.abelab.timestamp.api.response;

import java.util.Date;
import java.util.List;

import lombok.*;
import dev.abelab.timestamp.model.StampAttachmentModel;

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
     * 投稿日
     */
    Date createdAt;

    /**
     * 添付ファイル一覧
     */
    List<StampAttachmentModel> attachments;

}
