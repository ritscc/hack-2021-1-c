package dev.abelab.timestamp.service;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;

import lombok.*;
import dev.abelab.timestamp.api.response.UserResponse;
import dev.abelab.timestamp.api.response.UsersResponse;
import dev.abelab.timestamp.repository.UserRepository;
import dev.abelab.timestamp.logic.UserLogic;
import dev.abelab.timestamp.util.AuthUtil;

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
        final var loginUser = this.userLogic.getLoginUser(credentials);

        // 管理者かチェック
        AuthUtil.checkAdmin(loginUser);

        // ユーザ一覧の取得
        final var users = this.userRepository.selectAll();
        final var userResponses = users.stream() //
            .map(user -> this.modelMapper.map(user, UserResponse.class)) //
            .collect(Collectors.toList());

        return new UsersResponse(userResponses);
    }

}
