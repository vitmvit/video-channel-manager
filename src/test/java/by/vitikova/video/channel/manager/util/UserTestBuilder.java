package by.vitikova.video.channel.manager.util;

import by.vitikova.video.channel.manager.model.dto.UserDto;
import by.vitikova.video.channel.manager.model.dto.create.UserCreateDto;
import by.vitikova.video.channel.manager.model.dto.update.UserUpdateDto;
import by.vitikova.video.channel.manager.model.entity.Channel;
import by.vitikova.video.channel.manager.model.entity.User;
import lombok.Builder;

import java.util.List;

@Builder(setterPrefix = "with")
public class UserTestBuilder {

    @Builder.Default
    private Long id = 3L;

    @Builder.Default
    private String name = "channel_one";

    @Builder.Default
    private String nickname = "channel_one";

    @Builder.Default
    private String email = "channel_one";

    @Builder.Default
    private List<Channel> subscribedChannels = List.of();

    public User buildUser() {
        return new User(id, nickname, name, email, subscribedChannels);
    }

    public UserDto buildUserDto() {
        return new UserDto(id, nickname, name, email);
    }

    public UserUpdateDto buildUserUpdateDto() {
        var user = new UserUpdateDto();
        user.setEmail(email);
        user.setName(name);
        user.setNickname(nickname);
        return user;
    }

    public UserCreateDto buildUserCreateDto() {
        return new UserCreateDto(nickname, name, email);
    }
}