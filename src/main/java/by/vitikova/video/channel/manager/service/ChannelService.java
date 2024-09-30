package by.vitikova.video.channel.manager.service;

import by.vitikova.video.channel.manager.model.dto.ChannelDto;
import by.vitikova.video.channel.manager.model.dto.ChannelInfoDto;
import by.vitikova.video.channel.manager.model.dto.create.ChannelCreateDto;
import by.vitikova.video.channel.manager.model.dto.update.ChannelUpdateDto;
import by.vitikova.video.channel.manager.model.enums.CategoryChannel;
import by.vitikova.video.channel.manager.model.enums.LanguageChannel;
import org.springframework.data.domain.Page;

public interface ChannelService {

    ChannelDto findById(Long id);

    ChannelDto findByName(String name);

    Page<ChannelInfoDto> getAll(Integer offset, Integer limit, String name, LanguageChannel language, CategoryChannel category);

    ChannelDto create(ChannelCreateDto dto);

    ChannelDto update(ChannelUpdateDto dto);

    void delete(Long id);
}