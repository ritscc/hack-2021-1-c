package dev.abelab.timestamp.db.entity.join;

import java.util.List;

import lombok.*;
import dev.abelab.timestamp.db.entity.Stamp;
import dev.abelab.timestamp.db.entity.StampAttachment;

/**
 * スタンプ + 添付ファイルリスト
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StampWithAttachments extends Stamp {

    /**
     * 添付ファイルリスト
     */
    List<StampAttachment> attachments;

}
