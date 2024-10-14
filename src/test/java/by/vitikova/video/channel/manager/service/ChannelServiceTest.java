package by.vitikova.video.channel.manager.service;

import by.vitikova.video.channel.manager.converter.ChannelConverter;
import by.vitikova.video.channel.manager.exception.EntityNotFoundException;
import by.vitikova.video.channel.manager.exception.OperationException;
import by.vitikova.video.channel.manager.model.entity.Channel;
import by.vitikova.video.channel.manager.repository.ChannelRepository;
import by.vitikova.video.channel.manager.service.impl.ChannelServiceImpl;
import by.vitikova.video.channel.manager.util.ChannelTestBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChannelServiceTest {

    @Mock
    private ChannelRepository channelRepository;

    @Mock
    private ChannelConverter channelConverter;

    @InjectMocks
    private ChannelServiceImpl channelService;

    @Test
    void findByIdShouldReturnExpectedChannelWhenFound() {
        var expected = ChannelTestBuilder.builder().build().buildChannel();
        var channelDto = ChannelTestBuilder.builder().build().buildChannelDto();
        var id = expected.getId();

        when(channelRepository.findById(id)).thenReturn(Optional.of(expected));
        when(channelConverter.convertToDto(expected)).thenReturn(channelDto);

        var actual = channelService.findById(id);

        assertThat(actual)
                .isNotNull()
                .hasFieldOrPropertyWithValue(Channel.Fields.id, expected.getId())
                .hasFieldOrPropertyWithValue(Channel.Fields.name, expected.getName())
                .hasFieldOrPropertyWithValue(Channel.Fields.description, expected.getDescription())
                .hasFieldOrPropertyWithValue(Channel.Fields.authorId, expected.getAuthorId())
                .hasFieldOrPropertyWithValue(Channel.Fields.category, expected.getCategory())
                .hasFieldOrPropertyWithValue(Channel.Fields.mainLanguage, expected.getMainLanguage())
                .hasFieldOrPropertyWithValue(Channel.Fields.avatar, expected.getAvatar());
    }

    @Test
    void findByIdShouldThrowEntityNotFoundExceptionWhenChannelNotFound() {
        when(channelRepository.findById(5L)).thenReturn(Optional.empty());

        var exception = assertThrows(EntityNotFoundException.class, () -> channelService.findById(5L));
        assertEquals(exception.getClass(), EntityNotFoundException.class);
    }

    @Test
    void createShouldReturnChannelDtoWhenSuccessful() {
        var channelCreateDto = ChannelTestBuilder.builder().build().buildChannelCreateDto();
        var expected = ChannelTestBuilder.builder().build().buildChannel();
        var channelDto = ChannelTestBuilder.builder().build().buildChannelDto();
        var avatar = ChannelTestBuilder.builder().build().buildMultipartFile();
        var base64 = ChannelTestBuilder.builder().build().buildBase64();
        expected.setAvatar(base64);

        when(channelConverter.convert(channelCreateDto)).thenReturn(expected);
        when(channelRepository.save(expected)).thenReturn(expected);
        when(channelConverter.convertToDto(expected)).thenReturn(channelDto);

        var actual = channelService.create(channelCreateDto, avatar);

        assertThat(actual).isNotNull();
        assertThat(actual.getAvatar()).isEqualTo(base64);
    }

    @Test
    void updateAvatarShouldReturnUpdatedChannelDtoWhenSuccessful() {
        var channel = ChannelTestBuilder.builder().build().buildChannel();
        var expected = ChannelTestBuilder.builder().build().buildChannel();
        var id = channel.getId();
        var avatar = ChannelTestBuilder.builder().build().buildMultipartFile();
        var base64 = ChannelTestBuilder.builder().build().buildBase64();
        var expectedDto = ChannelTestBuilder.builder().build().buildChannelDto();

        when(channelRepository.findById(id)).thenReturn(Optional.of(expected));
        when(channelRepository.save(expected)).thenReturn(expected);
        when(channelConverter.convertToDto(expected)).thenReturn(expectedDto);

        var result = channelService.updateAvatar(id, avatar);

        assertThat(result).isNotNull();
        assertThat(result.getAvatar()).isEqualTo(base64);
    }

    @Test
    void updateAvatarShouldThrowOperationExceptionWhenChannelDoesNotExist() {
        var id = ChannelTestBuilder.builder().build().buildChannel().getId();
        var avatar = ChannelTestBuilder.builder().build().buildMultipartFile();

        when(channelRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> channelService.updateAvatar(id, avatar))
                .isInstanceOf(OperationException.class);
    }

    @Test
    public void updateShouldUpdatedChannel() {
        var channel = ChannelTestBuilder.builder().build().buildChannel();
        var id = channel.getId();
        var chanelToUpdate = ChannelTestBuilder.builder().build().buildChannelUpdateDto();
        var expectedChannel = ChannelTestBuilder.builder().build().buildChannel();
        var expectedChannelDto = ChannelTestBuilder.builder().build().buildChannelDto();

        when(channelRepository.findById(id)).thenReturn(Optional.ofNullable(channel));
        when(channelConverter.merge(channel, chanelToUpdate)).thenReturn(expectedChannel);
        when(channelRepository.save(expectedChannel)).thenReturn(expectedChannel);
        when(channelConverter.convertToDto(expectedChannel)).thenReturn(expectedChannelDto);

        var actual = channelService.update(id, chanelToUpdate);

        assertThat(actual)
                .isNotNull()
                .hasFieldOrPropertyWithValue(Channel.Fields.id, expectedChannelDto.getId())
                .hasFieldOrPropertyWithValue(Channel.Fields.name, expectedChannelDto.getName())
                .hasFieldOrPropertyWithValue(Channel.Fields.category, expectedChannelDto.getCategory())
                .hasFieldOrPropertyWithValue(Channel.Fields.mainLanguage, expectedChannelDto.getMainLanguage())
                .hasFieldOrPropertyWithValue(Channel.Fields.avatar, expectedChannelDto.getAvatar());
    }

    @Test
    void updateShouldThrowOperationExceptionWhenChannelDoesNotExist() {
        var channel = ChannelTestBuilder.builder().build().buildChannel();
        var id = channel.getId();
        var chanelToUpdate = ChannelTestBuilder.builder().build().buildChannelUpdateDto();

        when(channelRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> channelService.update(id, chanelToUpdate))
                .isInstanceOf(OperationException.class);
    }

    @Test
    void delete() {
        var id = ChannelTestBuilder.builder().build().buildChannel().getId();
        doNothing().when(channelRepository).deleteById(id);

        assertDoesNotThrow(() -> channelService.delete(id));
    }
}