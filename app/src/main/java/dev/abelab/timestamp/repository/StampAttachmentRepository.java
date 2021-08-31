package dev.abelab.timestamp.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import dev.abelab.timestamp.db.entity.StampAttachment;
import dev.abelab.timestamp.db.entity.StampAttachmentExample;
import dev.abelab.timestamp.db.mapper.StampAttachmentMapper;

@RequiredArgsConstructor
@Repository
public class StampAttachmentRepository {

    private final StampAttachmentMapper stampAttachmentMapper;

    /**
     * スタンプ添付ファイル一覧を取得
     *
     * @return スタンプ添付ファイル一覧
     */
    public List<StampAttachment> selectAll() {
        final var stampAttachmentExample = new StampAttachmentExample();
        stampAttachmentExample.setOrderByClause("updated_at desc");
        return this.stampAttachmentMapper.selectByExampleWithBLOBs(stampAttachmentExample);
    }

    /**
     * スタンプIDから添付ファイル一覧を取得
     *
     * @param stampId スタンプID
     */
    public List<StampAttachment> selectByStampId(final int stampId) {
        final var stampAttachmentExample = new StampAttachmentExample();
        stampAttachmentExample.createCriteria().andStampIdEqualTo(stampId);
        return this.stampAttachmentMapper.selectByExampleWithBLOBs(stampAttachmentExample);
    }

    /**
     * スタンプ添付ファイルを作成
     *
     * @param stampAttachment スタンプ添付ファイル
     *
     * @return スタンプ添付ファイルID
     */
    public int insert(final StampAttachment stampAttachment) {
        return this.stampAttachmentMapper.insertSelective(stampAttachment);
    }

    /**
     * スタンプ添付ファイルを一括挿入
     *
     * @param stamps スタンプ添付ファイルリスト
     */
    public void bulkInsert(List<StampAttachment> stampAttachments) {
        if (!stampAttachments.isEmpty()) {
            this.stampAttachmentMapper.bulkInsert(stampAttachments);
        }
    }

}
