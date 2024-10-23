package by.vitikova.video.channel.manager.util;

import by.vitikova.video.channel.manager.model.dto.ChannelDto;
import by.vitikova.video.channel.manager.model.dto.ChannelNameDto;
import by.vitikova.video.channel.manager.model.dto.create.ChannelCreateDto;
import by.vitikova.video.channel.manager.model.dto.update.ChannelUpdateDto;
import by.vitikova.video.channel.manager.model.entity.Channel;
import by.vitikova.video.channel.manager.model.entity.User;
import by.vitikova.video.channel.manager.model.enums.CategoryChannel;
import by.vitikova.video.channel.manager.model.enums.LanguageChannel;
import lombok.Builder;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Builder(setterPrefix = "with")
public class ChannelTestBuilder {

    @Builder.Default
    private Long id = 14L;

    @Builder.Default
    private String name = "channel_one";

    @Builder.Default
    private String description = "channel_one";

    @Builder.Default
    private Long authorId = 3L;

    @Builder.Default
    private User author = new User(3L, "name", "name", "email", List.of());

    @Builder.Default
    private List<User> subscribers = List.of();

    @Builder.Default
    private LocalDateTime creationDate = LocalDateTime.of(2024, 1, 22, 5, 34, 45, 23);

    @Builder.Default
    private LanguageChannel mainLanguage = LanguageChannel.EN;

    @Builder.Default
    private String avatar = "ZmFrZSBpbWFnZSBjb250ZW50";
    @Builder.Default
    private CategoryChannel category = CategoryChannel.MUSIC;

    @Builder.Default
    private MultipartFile file = new MockMultipartFile("avatar", "avatar.jpg", MediaType.IMAGE_JPEG_VALUE, "fake image content".getBytes());

    public Channel buildChannel() {
        return new Channel(id, name, description, authorId, author, subscribers, creationDate, mainLanguage, avatar, category);
    }

    public ChannelDto buildChannelDto() {
        return new ChannelDto(id, name, description, authorId, subscribers.size(), creationDate, mainLanguage, avatar, category);
    }

    public ChannelUpdateDto buildChannelUpdateDto() {
        return new ChannelUpdateDto(name, description, authorId, mainLanguage, category);
    }

    public ChannelCreateDto buildChannelCreateDto() {
        return new ChannelCreateDto(name, description, authorId, mainLanguage, category);
    }

    public String buildBase64() {
        return FileUtils.convertMultipartFileToBase64(file);
    }

    public MultipartFile buildMultipartFile() {
        return file;
    }

    public ChannelNameDto buildChannelNameDto() {
        return new ChannelNameDto(id, name);
    }
}