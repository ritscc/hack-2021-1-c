package dev.abelab.timestamp.api.request;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;
import dev.abelab.timestamp.model.StampAttachmentSubmitModel;

/**
 * スタンプ作成リクエスト
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StampCreateRequest {

    /**
     * タイトル
     */
    @NotNull
    @Size(max = 255)
    String title;

    /**
     * 説明文
     */
    @NotNull
    @Size(max = 1000)
    String description;

    /**
     * 添付ファイル一覧
     */
    @NotNull
    @Singular
    List<StampAttachmentSubmitModel> attachments;

}
