package by.vitikova.video.channel.manager.service;

import by.vitikova.video.channel.manager.model.dto.ChannelDto;
import by.vitikova.video.channel.manager.model.dto.ChannelInfoDto;
import by.vitikova.video.channel.manager.model.dto.create.ChannelCreateDto;
import by.vitikova.video.channel.manager.model.dto.page.ChannelFilterDto;
import by.vitikova.video.channel.manager.model.dto.page.PageContentDto;
import by.vitikova.video.channel.manager.model.dto.page.PageParamDto;
import by.vitikova.video.channel.manager.model.dto.update.ChannelUpdateDto;
import org.springframework.web.multipart.MultipartFile;

public interface ChannelService {

    ChannelDto findById(Long id);

    PageContentDto<ChannelInfoDto> getAll(PageParamDto param, ChannelFilterDto filter);

    ChannelDto create(ChannelCreateDto dto, MultipartFile avatar);

    ChannelDto update(Long id, ChannelUpdateDto dto);

    ChannelDto updateAvatar(Long id, MultipartFile avatar);

    void delete(Long id);
}