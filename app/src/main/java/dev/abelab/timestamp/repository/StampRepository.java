package dev.abelab.timestamp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import dev.abelab.timestamp.db.entity.Stamp;
import dev.abelab.timestamp.db.entity.StampExample;
import dev.abelab.timestamp.db.mapper.StampMapper;
import dev.abelab.timestamp.exception.ErrorCode;
import dev.abelab.timestamp.exception.NotFoundException;

@RequiredArgsConstructor
@Repository
public class StampRepository {

    private final StampMapper stampMapper;

    /**
     * スタンプ一覧を取得
     *
     * @return スタンプ一覧
     */
    public List<Stamp> selectAll() {
        final var stampExample = new StampExample();
        stampExample.setOrderByClause("updated_at desc");
        return this.stampMapper.selectByExample(stampExample);
    }

    /**
     * スタンプを作成
     *
     * @param stamp スタンプ
     *
     * @return スタンプID
     */
    public int insert(final Stamp stamp) {
        return this.stampMapper.insertSelective(stamp);
    }

    /**
     * スタンプを一括挿入
     *
     * @param stamps スタンプリスト
     */
    public void bulkInsert(List<Stamp> stamps) {
        if (!stamps.isEmpty()) {
            this.stampMapper.bulkInsert(stamps);
        }
    }

    /**
     * スタンプを削除
     *
     * @param stampId スタンプID
     */
    public void deleteById(final int stampId) {
        if (this.existsById(stampId)) {
            this.stampMapper.deleteByPrimaryKey(stampId);
        } else {
            throw new NotFoundException(ErrorCode.NOT_FOUND_USER);
        }
    }

    /**
     * IDからスタンプを検索
     *
     * @param stampId スタンプID
     *
     * @return スタンプ
     */
    public Stamp selectById(final int stampId) {
        return Optional.ofNullable(this.stampMapper.selectByPrimaryKey(stampId)) //
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_STAMP));
    }

    /**
     * スタンプIDの存在確認
     *
     * @param stampId スタンプID
     *
     * @return スタンプIDが存在するか
     */
    public boolean existsById(final int stampId) {
        try {
            this.selectById(stampId);
            return true;
        } catch (NotFoundException e) {
            return false;
        }
    }

}
