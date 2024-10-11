package by.vitikova.video.channel.manager.model.dto;

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
public class ChannelInfoDto {

    private Long id;
    private String name;
    private int subscribersCount;
    private LanguageChannel mainLanguage;
    private String avatar;
    private CategoryChannel category;
}