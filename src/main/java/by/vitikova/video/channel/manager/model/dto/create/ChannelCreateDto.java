package by.vitikova.video.channel.manager.model.dto.create;

import by.vitikova.video.channel.manager.model.enums.CategoryChannel;
import by.vitikova.video.channel.manager.model.enums.LanguageChannel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChannelCreateDto {

    private String name;
    private String description;
    private Long authorId;
    private LanguageChannel mainLanguage;
    private CategoryChannel category;
}