package by.vitikova.video.channel.manager.model.dto.update;

import by.vitikova.video.channel.manager.model.dto.create.ChannelCreateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChannelUpdateDto extends ChannelCreateDto {

    private Long id;
}