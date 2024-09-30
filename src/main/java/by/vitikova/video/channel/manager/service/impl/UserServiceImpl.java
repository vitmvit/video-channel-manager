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

/**
 * Реализация сервиса пользователя.
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final UserConverter userConverter;
    private final ChannelConverter channelConverter;

    /**
     * Находит пользователя по идентификатору.
     *
     * @param id идентификатор пользователя.
     * @return объект {@link UserDto}, содержащий информацию о пользователе.
     * @throws EntityNotFoundException если пользователь с данным идентификатором не найден.
     */
    @Override
    public UserDto findById(Long id) {
        return userConverter.convert(userRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    /**
     * Создает нового пользователя.
     *
     * @param dto объект {@link UserCreateDto} с данными для создания пользователя.
     * @return объект {@link UserDto}, содержащий информацию о созданном пользователе.
     * @throws OperationException если произошла ошибка при создании пользователя.
     */
    @Override
    @Transactional
    public UserDto create(UserCreateDto dto) {
        try {
            return userConverter.convert(userRepository.save(userConverter.convert(dto)));
        } catch (Exception ex) {
            throw new OperationException("User create error: " + ex.getMessage());
        }
    }

    /**
     * Обновляет информацию о пользователе.
     *
     * @param dto объект {@link UserUpdateDto} с обновленными данными пользователя.
     * @return объект {@link UserDto}, содержащий информацию об обновленном пользователе.
     * @throws EntityNotFoundException если пользователь с данным идентификатором не найден.
     * @throws OperationException      если произошла ошибка при обновлении пользователя.
     */
    @Override
    @Transactional
    public UserDto update(UserUpdateDto dto) {
        try {
            var user = userRepository.findById(dto.getId()).orElseThrow(EntityNotFoundException::new);
            return userConverter.convert(userRepository.save(userConverter.merge(user, dto)));
        } catch (Exception ex) {
            throw new OperationException("User update error: " + ex.getMessage());
        }
    }

    /**
     * Получает список каналов, на которые подписан пользователь.
     *
     * @param id идентификатор пользователя.
     * @return список объектов {@link ChannelNameDto}, представляющих каналы подписки.* @throws EntityNotFoundException если пользователь с данным идентификатором не найден.
     */
    @Override
    public List<ChannelNameDto> getChannelsSubscribe(Long id) {
        var user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return user.getSubscribedChannels().stream().map(channelConverter::convert).collect(Collectors.toList());
    }

    /**
     * Подписывает пользователя на канал.
     *
     * @param nameChannel название канала.
     * @param nickname    никнейм пользователя.
     * @throws EntityNotFoundException если пользователь или канал не найдены.
     * @throws OperationException      если произошла ошибка при подписке.
     */
    @Override
    @Transactional
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

    /**
     * Отписывает пользователя от канала.
     *
     * @param nameChannel название канала.
     * @param nickname    никнейм пользователя.
     * @throws EntityNotFoundException если пользователь или канал не найдены.
     * @throws OperationException      если произошла ошибка при отписке.
     */
    @Override
    @Transactional
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

    /**
     * Удаляет пользователя по идентификатору.
     *
     * @param id идентификатор пользователя.
     * @throws OperationException если произошла ошибка при удалении пользователя.
     */
    @Override
    @Transactional
    public void delete(Long id) {
        channelRepository.deleteAllByAuthorId(id);
        userRepository.deleteById(id);
    }
}