package by.vitikova.video.channel.manager.service;

import by.vitikova.video.channel.manager.converter.ChannelConverter;
import by.vitikova.video.channel.manager.converter.UserConverter;
import by.vitikova.video.channel.manager.exception.EntityNotFoundException;
import by.vitikova.video.channel.manager.exception.OperationException;
import by.vitikova.video.channel.manager.model.entity.Channel;
import by.vitikova.video.channel.manager.model.entity.User;
import by.vitikova.video.channel.manager.repository.ChannelRepository;
import by.vitikova.video.channel.manager.repository.UserRepository;
import by.vitikova.video.channel.manager.service.impl.UserServiceImpl;
import by.vitikova.video.channel.manager.util.ChannelTestBuilder;
import by.vitikova.video.channel.manager.util.UserTestBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ChannelRepository channelRepository;
    @Mock
    private ChannelConverter channelConverter;

    @Mock
    private UserConverter userConverter;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void findByIdShouldReturnExpectedUserWhenFound() {
        var expected = UserTestBuilder.builder().build().buildUser();
        var userDto = UserTestBuilder.builder().build().buildUserDto();
        var id = expected.getId();

        when(userRepository.findById(id)).thenReturn(Optional.of(expected));
        when(userConverter.convert(expected)).thenReturn(userDto);

        var actual = userService.findById(id);

        assertThat(actual)
                .isNotNull()
                .hasFieldOrPropertyWithValue(User.Fields.id, expected.getId())
                .hasFieldOrPropertyWithValue(User.Fields.name, expected.getName())
                .hasFieldOrPropertyWithValue(User.Fields.nickname, expected.getNickname())
                .hasFieldOrPropertyWithValue(User.Fields.email, expected.getEmail());
    }

    @Test
    void findByIdShouldThrowEntityNotFoundExceptionWhenUserNotFound() {
        when(userRepository.findById(5L)).thenReturn(Optional.empty());

        var exception = assertThrows(EntityNotFoundException.class, () -> userService.findById(5L));
        assertEquals(exception.getClass(), EntityNotFoundException.class);
    }

    @Test
    void getChannelsSubscribeShouldReturnSubscribedChannels() {
        var user = UserTestBuilder.builder().build().buildUser();
        var userId = user.getId();
        var channel = ChannelTestBuilder.builder().build().buildChannel();
        var newList = new ArrayList<>(user.getSubscribedChannels());
        newList.add(channel);
        user.setSubscribedChannels(newList);
        var channelNameDto = ChannelTestBuilder.builder().build().buildChannelNameDto();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(channelConverter.convert(any(Channel.class))).thenReturn(channelNameDto);

        var result = userService.getChannelsSubscribe(userId);

        assertThat(result.get(0).getId()).isEqualTo(channel.getId());
    }

    @Test
    void getChannelsSubscribeShouldThrowEntityNotFoundExceptionWhenUserNotFound() {
        when(userRepository.findById(5L)).thenReturn(Optional.empty());

        var exception = assertThrows(EntityNotFoundException.class, () -> userService.getChannelsSubscribe(5L));
        assertEquals(exception.getClass(), EntityNotFoundException.class);
    }

    @Test
    void createShouldReturnUserDtoWhenSuccessful() {
        var userCreateDto = UserTestBuilder.builder().build().buildUserCreateDto();
        var userDto = UserTestBuilder.builder().build().buildUserDto();
        var expected = UserTestBuilder.builder().build().buildUser();

        when(userConverter.convert(userCreateDto)).thenReturn(expected);
        when(userRepository.save(expected)).thenReturn(expected);
        when(userConverter.convert(expected)).thenReturn(userDto);

        var actual = userService.create(userCreateDto);

        assertThat(actual)
                .isNotNull()
                .hasFieldOrPropertyWithValue(User.Fields.id, expected.getId())
                .hasFieldOrPropertyWithValue(User.Fields.name, expected.getName())
                .hasFieldOrPropertyWithValue(User.Fields.nickname, expected.getNickname())
                .hasFieldOrPropertyWithValue(User.Fields.email, expected.getEmail());
    }

    @Test
    void subscribeShouldAddChannelToUserSubscriptions() {
        var user = UserTestBuilder.builder().build().buildUser();
        var channel = ChannelTestBuilder.builder().build().buildChannel();
        var userId = user.getId();
        var channelId = channel.getId();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(channelRepository.findById(channelId)).thenReturn(Optional.of(channel));
        when(userRepository.save(user)).thenReturn(user);

        userService.subscribe(channelId, userId);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void subscribeShouldThrowEntityNotFoundExceptionWhenUserNotFound() {
        var userId = 1L;
        var channelId = 2L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.subscribe(channelId, userId))
                .isInstanceOf(OperationException.class);
    }

    @Test
    void unsubscribeShouldAddChannelToUserSubscriptions() {
        var user = UserTestBuilder.builder().build().buildUser();
        var channel = ChannelTestBuilder.builder().build().buildChannel();
        var userId = user.getId();
        var channelId = channel.getId();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(channelRepository.findById(channelId)).thenReturn(Optional.of(channel));
        when(userRepository.save(user)).thenReturn(user);

        userService.unsubscribe(channelId, userId);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void unsubscribeShouldThrowEntityNotFoundExceptionWhenUserNotFound() {
        var userId = 1L;
        var channelId = 2L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.unsubscribe(channelId, userId))
                .isInstanceOf(OperationException.class);
    }

    @Test
    public void updateShouldUpdatedUser() {
        var user = UserTestBuilder.builder().build().buildUser();
        var id = user.getId();
        var userToUpdate = UserTestBuilder.builder().build().buildUserUpdateDto();
        var expectedUser = UserTestBuilder.builder().build().buildUser();
        var expectedUserDto = UserTestBuilder.builder().build().buildUserDto();

        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(user));
        when(userConverter.merge(user, userToUpdate)).thenReturn(expectedUser);
        when(userRepository.save(expectedUser)).thenReturn(expectedUser);
        when(userConverter.convert(expectedUser)).thenReturn(expectedUserDto);

        var actual = userService.update(id, userToUpdate);

        assertThat(actual)
                .isNotNull()
                .hasFieldOrPropertyWithValue(User.Fields.id, expectedUserDto.getId())
                .hasFieldOrPropertyWithValue(User.Fields.name, expectedUserDto.getName())
                .hasFieldOrPropertyWithValue(User.Fields.nickname, expectedUserDto.getNickname())
                .hasFieldOrPropertyWithValue(User.Fields.email, expectedUserDto.getEmail());
    }

    @Test
    void updateShouldThrowOperationExceptionWhenUserDoesNotExist() {
        var user = UserTestBuilder.builder().build().buildUser();
        var id = user.getId();
        var userToUpdate = UserTestBuilder.builder().build().buildUserUpdateDto();

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.update(id, userToUpdate))
                .isInstanceOf(OperationException.class);
    }

    @Test
    void delete() {
        var id = UserTestBuilder.builder().build().buildUser().getId();

        doNothing().when(channelRepository).deleteAllByAuthorId(id);
        doNothing().when(userRepository).deleteById(id);

        assertDoesNotThrow(() -> userService.delete(id));
    }
}