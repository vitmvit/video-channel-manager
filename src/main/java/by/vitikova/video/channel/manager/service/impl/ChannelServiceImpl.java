package by.vitikova.video.channel.manager.service.impl;

import by.vitikova.video.channel.manager.converter.ChannelConverter;
import by.vitikova.video.channel.manager.exception.EntityNotFoundException;
import by.vitikova.video.channel.manager.exception.OperationException;
import by.vitikova.video.channel.manager.model.dto.ChannelDto;
import by.vitikova.video.channel.manager.model.dto.ChannelInfoDto;
import by.vitikova.video.channel.manager.model.dto.create.ChannelCreateDto;
import by.vitikova.video.channel.manager.model.dto.update.ChannelUpdateDto;
import by.vitikova.video.channel.manager.model.entity.Channel;
import by.vitikova.video.channel.manager.model.enums.CategoryChannel;
import by.vitikova.video.channel.manager.model.enums.LanguageChannel;
import by.vitikova.video.channel.manager.repository.ChannelRepository;
import by.vitikova.video.channel.manager.service.ChannelService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;
    private final ChannelConverter channelConverter;

    @Override
    public ChannelDto findById(Long id) {
        return channelConverter.convertToDto(channelRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public ChannelDto findByName(String name) {
        return channelConverter.convertToDto(channelRepository.findByName(name).orElseThrow(EntityNotFoundException::new));

    }

    @Override
    public Page<ChannelInfoDto> getAll(Integer offset, Integer limit, String name, LanguageChannel language, CategoryChannel category) {
//    public Page<ChannelInfoDto> getAll(Integer offset, Integer limit) {
        Page<Channel> personPage = channelRepository.findAllByNameAndMainLanguageAndCategory(PageRequest.of(offset, limit), name, language, category);
//        Page<Channel> personPage = channelRepository.findAll(PageRequest.of(offset, limit));
        return personPage.map(channelConverter::convertToInfo);
    }

    @Override
    public ChannelDto create(ChannelCreateDto dto) {
        try {
            return channelConverter.convertToDto(channelRepository.save(channelConverter.convert(dto)));
        } catch (Exception ex) {
            throw new OperationException("Channel create error: " + ex.getMessage());
        }
    }

    @Override
    public ChannelDto update(ChannelUpdateDto dto) {
        try {
            var channel = channelRepository.findById(dto.getId()).orElseThrow(jakarta.persistence.EntityNotFoundException::new);
            return channelConverter.convertToDto(channelRepository.save(channelConverter.merge(channel, dto)));
        } catch (Exception ex) {
            throw new OperationException("Channel update error: " + ex.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        channelRepository.deleteById(id);
    }
}