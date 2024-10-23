package by.vitikova.video.channel.manager.model.dto.page;

import by.vitikova.video.channel.manager.model.enums.CategoryChannel;
import by.vitikova.video.channel.manager.model.enums.LanguageChannel;

public record ChannelFilterDto(
        String name,
        LanguageChannel language,
        CategoryChannel category
) {
}