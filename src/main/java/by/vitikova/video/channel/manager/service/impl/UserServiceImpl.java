package by.vitikova.video.channel.manager.service.impl;

import by.vitikova.video.channel.manager.converter.ChannelConverter;
import by.vitikova.video.channel.manager.converter.UserConverter;
import by.vitikova.video.channel.manager.exception.EntityNotFoundException;
import by.vitikova.video.channel.manager.exception.OperationException;
import by.vitikova.video.channel.manager.model.dto.ChannelNameDto;
import by.vitikova.video.channel.manager.model.dto.UserDto;
import by.vitikova.video.channel.manager.model.dto.create.UserCreateDto;
import by.vitikova.video.channel.manager.model.dto.update.UserUpdateDto;
import by.vitikova.video.channel.manager.repository.ChannelRepository;
import by.vitikova.video.channel.manager.repository.UserRepository;
import by.vitikova.video.channel.manager.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final UserConverter userConverter;
    private final ChannelConverter channelConverter;

    @Override
    public UserDto findById(Long id) {
        return userConverter.convert(userRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public UserDto create(UserCreateDto dto) {
        try {
            return userConverter.convert(userRepository.save(userConverter.convert(dto)));
        } catch (Exception ex) {
            throw new OperationException("User create error: " + ex.getMessage());
        }
    }

    @Override
    public UserDto update(UserUpdateDto dto) {
        try {
            var user = userRepository.findById(dto.getId()).orElseThrow(EntityNotFoundException::new);
            userConverter.merge(user, dto);
            return userConverter.convert(userRepository.save(user));
        } catch (Exception ex) {
            throw new OperationException("User update error: " + ex.getMessage());
        }
    }

    @Override
    public List<ChannelNameDto> getChannelsSubscribe(Long id) {
        var user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return user.getSubscribedChannels().stream().map(channelConverter::convert).collect(Collectors.toList());
    }

    @Override
    public void subscribe(String nameChannel, String nickname) {
        try {
            var user = userRepository.findByNickname(nickname).orElseThrow(EntityNotFoundException::new);
            var channel = channelRepository.findByName(nameChannel).orElseThrow(EntityNotFoundException::new);

            user.getSubscribedChannels().add(channel);
            userRepository.save(user);
        } catch (Exception ex) {
            throw new OperationException("Subscribe error: " + ex.getMessage());
        }
    }

    @Override
    public void unsubscribe(String nameChannel, String nickname) {
        try {
            var user = userRepository.findByNickname(nickname).orElseThrow(EntityNotFoundException::new);
            var channel = channelRepository.findByName(nameChannel).orElseThrow(EntityNotFoundException::new);

            channel.getSubscribers().remove(user);
            channelRepository.save(channel);
        } catch (Exception ex) {
            throw new OperationException("Unsubscribe error: " + ex.getMessage());
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        channelRepository.deleteAllByAuthorId(id);
        userRepository.deleteById(id);
    }
}