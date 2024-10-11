package by.vitikova.video.channel.manager.service;

import by.vitikova.video.channel.manager.model.dto.ChannelNameDto;
import by.vitikova.video.channel.manager.model.dto.UserDto;
import by.vitikova.video.channel.manager.model.dto.create.UserCreateDto;
import by.vitikova.video.channel.manager.model.dto.update.UserUpdateDto;

import java.util.List;

public interface UserService {

    UserDto findById(Long id);

    UserDto create(UserCreateDto dto);

    UserDto update(Long id, UserUpdateDto dto);

    List<ChannelNameDto> getChannelsSubscribe(Long id);

    void subscribe(Long channelId, Long userId);

    void unsubscribe(Long channelId, Long userId);

    void delete(Long id);
}