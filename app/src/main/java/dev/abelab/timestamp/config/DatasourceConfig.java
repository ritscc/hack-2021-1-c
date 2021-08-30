package dev.abelab.timestamp.config;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;

import dev.abelab.timestamp.db.entity.User;
import dev.abelab.timestamp.repository.UserRepository;
import dev.abelab.timestamp.logic.UserLogic;
import dev.abelab.timestamp.property.TimeStampProperty;
import dev.abelab.timestamp.enums.UserRoleEnum;

@Profile("prod | local")
@Configuration
public class DatasourceConfig {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserLogic userLogic;

    @Autowired
    TimeStampProperty timeStampProperty;

    @PostConstruct
    public void createDefaultAdmin() {
        // 管理者アカウントが既に存在する
        if (this.userRepository.existsByEmail(this.timeStampProperty.getAdmin().getEmail())) {
            return;
        }

        // 管理者アカウントを作成
        final var adminUser = User.builder() //
            .email(this.timeStampProperty.getAdmin().getEmail()) //
            .password(this.userLogic.encodePassword(this.timeStampProperty.getAdmin().getPassword())) //
            .firstName(this.timeStampProperty.getAdmin().getFirstName()) //
            .lastName(this.timeStampProperty.getAdmin().getLastName()) //
            .roleId(UserRoleEnum.ADMIN.getId()) //
            .build();
        this.userRepository.insert(adminUser);
    }

}
