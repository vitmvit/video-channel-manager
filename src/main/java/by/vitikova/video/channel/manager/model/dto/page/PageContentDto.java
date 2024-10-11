package by.vitikova.video.channel.manager.model.dto.page;

import java.util.List;

public record PageContentDto<E>(
        PageDto page,
        List<E> content
) {
}