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
import org.springframework.transaction.annotation.Transactional;

/**
 * Реализация сервиса канала.
 */
@Service
@AllArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;
    private final ChannelConverter channelConverter;

    /**
     * Находит канал по идентификатору.
     *
     * @param id идентификатор канала.
     * @return объект {@link ChannelDto}, содержащий информацию о канале.
     * @throws EntityNotFoundException если канал с данным идентификатором не найден.
     */
    @Override
    public ChannelDto findById(Long id) {
        return channelConverter.convertToDto(channelRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    /**
     * Находит канал по названию.
     *
     * @param name название канала.
     * @return объект {@link ChannelDto}, содержащий информацию о канале.
     * @throws EntityNotFoundException если канал с данным названием не найден.
     */
    @Override
    public ChannelDto findByName(String name) {
        return channelConverter.convertToDto(channelRepository.findByName(name).orElseThrow(EntityNotFoundException::new));
    }

    /**
     * Получает список всех каналов с параметрами пагинации и фильтрации.
     *
     * @param offset   смещение для пагинации.
     * @param limit    предел количества возвращаемых каналов.
     * @param name     название канала для фильтрации.
     * @param language основной язык канала для фильтрации.
     * @param category категория канала для фильтрации.
     * @return страница объектов {@link ChannelInfoDto}, представляющих каналы.
     */
    @Override
    public Page<ChannelInfoDto> getAll(Integer offset, Integer limit, String name, LanguageChannel language, CategoryChannel category) {
        Page<Channel> personPage = channelRepository.findAllByNameAndMainLanguageAndCategory(PageRequest.of(offset, limit), name, language, category);
        return personPage.map(channelConverter::convertToInfo);
    }

    /**
     * Создает новый канал.
     *
     * @param dto объект {@link ChannelCreateDto} с данными для создания канала.
     * @return объект {@link ChannelDto}, содержащий информацию о созданном канале.
     * @throws OperationException если произошла ошибка при создании канала.
     */
    @Override
    @Transactional
    public ChannelDto create(ChannelCreateDto dto) {
        try {
            return channelConverter.convertToDto(channelRepository.save(channelConverter.convert(dto)));
        } catch (Exception ex) {
            throw new OperationException("Channel create error: " + ex.getMessage());
        }
    }

    /**
     * Обновляет информацию о канале.
     *
     * @param dto объект {@link ChannelUpdateDto} с обновленными данными канала.
     * @return объект {@link ChannelDto}, содержащий информацию об обновленном канале.
     * @throws EntityNotFoundException если канал с данным идентификатором не найден.
     * @throws OperationException      если произошла ошибка при обновлении канала.
     */
    @Override
    @Transactional
    public ChannelDto update(ChannelUpdateDto dto) {
        try {
            var channel = channelRepository.findById(dto.getId()).orElseThrow(jakarta.persistence.EntityNotFoundException::new);
            return channelConverter.convertToDto(channelRepository.save(channelConverter.merge(channel, dto)));
        } catch (Exception ex) {
            throw new OperationException("Channel update error: " + ex.getMessage());
        }
    }

    /**
     * Удаляет канал по идентификатору.
     *
     * @param id идентификатор канала.
     */
    @Override
    @Transactional
    public void delete(Long id) {
        channelRepository.deleteById(id);
    }
}