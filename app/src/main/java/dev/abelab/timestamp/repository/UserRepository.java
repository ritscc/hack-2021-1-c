package dev.abelab.timestamp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import dev.abelab.timestamp.db.entity.User;
import dev.abelab.timestamp.db.entity.UserExample;
import dev.abelab.timestamp.db.mapper.UserMapper;
import dev.abelab.timestamp.exception.ErrorCode;
import dev.abelab.timestamp.exception.ConflictException;
import dev.abelab.timestamp.exception.NotFoundException;

@RequiredArgsConstructor
@Repository
public class UserRepository {

    private final UserMapper userMapper;

    /**
     * ユーザ一覧を取得
     *
     * @return ユーザ一覧
     */
    public List<User> selectAll() {
        final var userExample = new UserExample();
        userExample.setOrderByClause("updated_at desc");
        return this.userMapper.selectByExample(userExample);
    }

    /**
     * ユーザを作成
     *
     * @param user ユーザ
     *
     * @return ユーザID
     */
    public int insert(final User user) {
        if (this.existsByEmail(user.getEmail())) {
            throw new ConflictException(ErrorCode.CONFLICT_EMAIL);
        }
        return this.userMapper.insertSelective(user);
    }

    /**
     * ユーザを更新
     *
     * @param user ユーザ
     */
    public void update(final User user) {
        user.setUpdatedAt(null);
        this.userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * ユーザを削除
     *
     * @param userId ユーザID
     */
    public void deleteById(final int userId) {
        if (this.existsById(userId)) {
            this.userMapper.deleteByPrimaryKey(userId);
        } else {
            throw new NotFoundException(ErrorCode.NOT_FOUND_USER);
        }
    }

    /**
     * IDからユーザを検索
     *
     * @param userId ユーザID
     *
     * @return ユーザ
     */
    public User selectById(final int userId) {
        return Optional.ofNullable(this.userMapper.selectByPrimaryKey(userId)) //
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER));
    }

    /**
     * メールアドレスからユーザを検索
     *
     * @param email メールアドレス
     *
     * @return ユーザ
     */
    public User selectByEmail(final String email) {
        final var example = new UserExample();
        example.createCriteria().andEmailEqualTo(email);
        return this.userMapper.selectByExample(example).stream().findFirst() //
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER));
    }

    /**
     * ユーザIDの存在確認
     *
     * @param userId ユーザID
     *
     * @return ユーザIDが存在するか
     */
    public boolean existsById(final int userId) {
        try {
            this.selectById(userId);
            return true;
        } catch (NotFoundException e) {
            return false;
        }
    }

    /**
     * emailの存在確認
     *
     * @param email email
     *
     * @return emailが存在するか
     */
    public boolean existsByEmail(final String email) {
        try {
            this.selectByEmail(email);
            return true;
        } catch (NotFoundException e) {
            return false;
        }
    }

}
