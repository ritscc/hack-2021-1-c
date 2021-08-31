package dev.abelab.timestamp.api.response;

import java.util.List;

import lombok.*;

/**
 * スタンプ一覧レスポンス
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StampsResponse {

    /**
     * スタンプリスト
     */
    List<StampResponse> stamps;

}
