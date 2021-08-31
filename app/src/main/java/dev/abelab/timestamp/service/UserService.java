package dev.abelab.timestamp.service;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;

import lombok.*;
import dev.abelab.timestamp.db.entity.User;
import dev.abelab.timestamp.api.request.UserCreateRequest;
import dev.abelab.timestamp.api.request.UserUpdateRequest;
import dev.abelab.timestamp.api.request.LoginUserUpdateRequest;
import dev.abelab.timestamp.api.request.LoginUserPasswordUpdateRequest;
import dev.abelab.timestamp.api.response.UserResponse;
import dev.abelab.timestamp.api.response.UsersResponse;
import dev.abelab.timestamp.repository.UserRepository;
import dev.abelab.timestamp.logic.UserLogic;
import dev.abelab.timestamp.util.AuthUtil;
import dev.abelab.timestamp.util.UserRoleUtil;

@RequiredArgsConstructor
@Service
public class UserService {

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final UserLogic userLogic;

    /**
     * ユーザ一覧を取得
     *
     * @param credentials 資格情報
     *
     * @return ユーザ一覧レスポンス
     */
    @Transactional
    public UsersResponse getUsers(final String credentials) {
        // ログインユーザを取得
        this.userLogic.getLoginUser(credentials);

        // ユーザ一覧の取得
        final var users = this.userRepository.selectAll();
        final var userResponses = users.stream() //
            .map(user -> this.modelMapper.map(user, UserResponse.class)) //
            .collect(Collectors.toList());

        return new UsersResponse(userResponses);
    }

    /**
     * ユーザを作成
     *
     * @param credentials 資格情報
     *
     * @param requestBody ユーザ作成リクエスト
     */
    @Transactional
    public void createUser(final String credentials, final UserCreateRequest requestBody) {
        // ログインユーザを取得
        final var loginUser = this.userLogic.getLoginUser(credentials);

        // 管理者かチェック
        AuthUtil.checkAdmin(loginUser);

        // 有効なユーザロールかチェック
        UserRoleUtil.checkForValidRoleId(requestBody.getRoleId());

        // 有効なパスワードかチェック
        AuthUtil.validatePassword(requestBody.getPassword());

        // ユーザを作成
        final var user = this.modelMapper.map(requestBody, User.class);
        user.setPassword(this.userLogic.encodePassword(requestBody.getPassword()));
        this.userRepository.insert(user);
    }

    /**
     * ユーザを更新
     *
     * @param credentials 資格情報
     *
     * @param userId      ユーザID
     *
     * @param requestBody ユーザ更新リクエスト
     */
    @Transactional
    public void updateUser(final String credentials, final int userId, final UserUpdateRequest requestBody) {
        // ログインユーザを取得
        final var loginUser = this.userLogic.getLoginUser(credentials);

        // 管理者かチェック
        AuthUtil.checkAdmin(loginUser);

        // 有効なユーザロールかチェック
        UserRoleUtil.checkForValidRoleId(requestBody.getRoleId());

        // ユーザを更新
        final var user = this.userRepository.selectById(userId);
        user.setFirstName(requestBody.getFirstName());
        user.setLastName(requestBody.getLastName());
        user.setEmail(requestBody.getEmail());
        user.setRoleId(requestBody.getRoleId());
        this.userRepository.update(user);
    }

    /**
     * ユーザを削除
     *
     * @param credentials 資格情報
     *
     * @param userId      ユーザID
     */
    @Transactional
    public void deleteUser(final String credentials, final int userId) {
        // ログインユーザを取得
        final var loginUser = this.userLogic.getLoginUser(credentials);

        // 管理者かチェック
        AuthUtil.checkAdmin(loginUser);

        // ユーザを削除
        this.userRepository.deleteById(userId);
    }

    /**
     * ログインユーザ詳細を取得
     *
     * @param credentials 資格情報
     *
     * @return ユーザ詳細レスポンス
     */
    @Transactional
    public UserResponse getLoginUser(final String credentials) {
        // ログインユーザを取得
        final var loginUser = this.userLogic.getLoginUser(credentials);

        return this.modelMapper.map(loginUser, UserResponse.class);
    }

    /**
     * ログインユーザを更新
     *
     * @param credentials 資格情報
     */
    @Transactional
    public void updateLoginUser(final String credentials, final LoginUserUpdateRequest requestBody) {
        // ログインユーザを取得
        final var loginUser = this.userLogic.getLoginUser(credentials);

        // ログインユーザを更新
        loginUser.setEmail(requestBody.getEmail());
        loginUser.setFirstName(requestBody.getFirstName());
        loginUser.setLastName(requestBody.getLastName());
        this.userRepository.update(loginUser);
    }

    /**
     * ログインユーザのパスワードを更新
     *
     * @param credentials 資格情報
     */
    @Transactional
    public void updateLoginPasswordUser(final String credentials, final LoginUserPasswordUpdateRequest requestBody) {
        // ログインユーザを取得
        final var loginUser = this.userLogic.getLoginUser(credentials);

        // 現在のパスワードチェック
        this.userLogic.verifyPassword(loginUser, requestBody.getCurrentPassword());

        // 有効なパスワードかチェック
        AuthUtil.validatePassword(requestBody.getNewPassword());

        // ログインユーザのパスワードを更新
        loginUser.setPassword(this.userLogic.encodePassword(requestBody.getNewPassword()));
        this.userRepository.update(loginUser);
    }

}
